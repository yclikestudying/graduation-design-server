package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.project.common.CodeEnum;
import com.project.constant.RedisConstant;
import com.project.constant.RoleConstant;
import com.project.domain.User;
import com.project.dto.user.UserLoginRequest;
import com.project.dto.user.UserRegisterRequest;
import com.project.exception.BusinessExceptionHandler;
import com.project.mapper.UserMapper;
import com.project.service.UserService;
import com.project.utils.*;
import com.project.vo.user.QueryUserVO;
import com.project.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private final Gson gson = new Gson();

    /**
     * 用户注册
     * @param userRegisterRequest 注册信息
     */
    @Override
    public boolean register(UserRegisterRequest userRegisterRequest) {
        // 获取参数
        String userPhone = userRegisterRequest.getUserPhone();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // 校验密码和确认密码是否一致
        if (!Objects.equals(userPassword, checkPassword)) {
            log.error("用户注册----->两次密码不一致");
            throw new BusinessExceptionHandler(400, "两次密码不一致");
        }
        // 查询数据库用户注册是否重复
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("user_phone", userPhone));
        if (count > 0) {
            log.error("用户注册----->用户已注册");
            throw new BusinessExceptionHandler(400, "用户已注册");
        }
        // 注册用户
        User user = new User();
        user.setUserPhone(userPhone);
        user.setUserPassword(MD5Util.calculateMD5(userPassword));
        // 查询用户数量设置用户默认名
        User one = userMapper.selectOne(new QueryWrapper<User>().select("id").orderByDesc("id").last("limit 1"));
        long count1 = one == null ? 0 : one.getId();
        user.setUserName("用户" + (count1 + 1));
        // todo 后续从表中查询管理员设置的用户默认头像
        user.setUserAvatar("https://campusmarket.oss-cn-chengdu.aliyuncs.com/avatar/2025-03-16/2e8ef60f-f1e5-459d-b56b-1725ac51f922Image_108900991352360.webp?Expires=4895722987&OSSAccessKeyId=LTAI5tHFTp1DAhhY5XMQb25q&Signature=F%2BakjrVe3dspXZ9ob2VzbqxzRhw%3D");
        // 用户默认简介
        user.setUserProfile("用户什么也没有留下");
        // 用户默认权限
        user.setUserRole(RoleConstant.ROLE_USER);
        int insert = userMapper.insert(user);
        if (insert == 0) {
            log.error("用户注册----->用户注册失败");
            throw new BusinessExceptionHandler(400, "用户注册失败");
        }
        return true;
    }

    /**
     * 用户登录
     * @param userLoginRequest 登录信息
     */
    @Override
    public Map<String, Object> login(UserLoginRequest userLoginRequest) {
        // 获取参数
        String userPhone = userLoginRequest.getUserPhone();
        String userPassword = userLoginRequest.getUserPassword();
        // 查询数据库登录用户是否已经注册
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_phone", userPhone));
        if (user == null) {
            // 未注册，抛出异常
            log.error("用户登录----->用户未注册");
            throw new BusinessExceptionHandler(CodeEnum.USER_NOT_REGISTER); // 用户未注册
        }
        // 已注册，校验加密密码
        if (!Objects.equals(MD5Util.calculateMD5(userPassword), user.getUserPassword())) {
            // 密码不一致，抛出异常
            log.error("用户登录----->用户或密码错误");
            throw new BusinessExceptionHandler(400, "用户或密码错误");
        }
        // 登录成功，生成 token
        String token = TokenUtil.createToken(user.getId(), user.getUserPhone());
        // 用户信息数据脱敏
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        // 存储 Redis
        redisUtil.setRedisData(RedisConstant.getRedisKey(RedisConstant.USER_TOKEN, user.getId()), token);
        redisUtil.setRedisData(RedisConstant.getRedisKey(RedisConstant.USER_INFO, user.getId()), gson.toJson(userVO));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userVO);
        return map;
    }

    /**
     * 获取用户信息
     * @param userId 用户id
     */
    @Override
    public UserVO getUser(Long userId) {
        // 判断是查询自己的用户信息还是别人的用户信息
        userId = checkUserId(userId);
        // 查询 Redis 是否存在该用户信息
        String redisKey = RedisConstant.getRedisKey(RedisConstant.USER_INFO, userId);
        String redisData = redisUtil.getRedisData(redisKey);
        User user = gson.fromJson(redisData, User.class);
        // Redis 不存在该用户信息，查询数据库
        UserVO userVO = new UserVO();
        if (user == null) {
            User one = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
            if (one == null) {
                log.error("获取用户信息----->该用户不存在，获取失败");
                throw new BusinessExceptionHandler(400, "该用户不存在，获取失败");
            }
            // 进行数据脱敏
            BeanUtils.copyProperties(one, userVO);
            // 更新 Redis
            redisUtil.setRedisData(redisKey, gson.toJson(userVO));
            return userVO;
        }
        // Redis 存在该用户信息，脱敏，返回
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 用户注销账号
     */
    @Override
    public boolean userDeleteAccount() {
        // 获取自己的id
        Long userId = UserContext.getUserId();
        // 数据库删除用户
        int result = userMapper.deleteById(userId);
        if (result == 0) {
            log.error("用户注销账号------>账号注销失败");
            throw new BusinessExceptionHandler(400, "账号注销失败");
        }
        return true;
    }

    /**
     * 修改用户个人信息
     */
    @Override
    public boolean updateUser(Map<String, Object> map) {
        // 获取自己的id
        Long userId = UserContext.getUserId();
        // 获取要修改的参数名和参数值
        String key = (String) map.get("key");
        if (StringUtils.isBlank(key)) {
            log.error("修改用户个人信息----->修改参数错误");
            throw new BusinessExceptionHandler(400, "数据不能为空");
        }
        String value = null;
        Integer gender = null;
        if (!Objects.equals(key, "userGender")) {
            value = (String) map.get("value");
            if (StringUtils.isBlank(value)) {
                log.error("修改用户个人信息----->修改参数错误");
                throw new BusinessExceptionHandler(400, "数据不能为空");
            }
        } else {
            gender = (Integer) map.get("value");
            if (gender == null) {
                log.error("修改用户个人信息----->修改参数错误");
                throw new BusinessExceptionHandler(400, "数据不能为空");
            }
        }

        // 选择要修改的数据
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        User user = new User();
        switch (key) {
            // 修改用户名
            case "userName":
                updateWrapper.set("user_name", value);
                user.setUserName(value);
                break;
            // 修改密码
            case "userPassword":
                // todo 旧密码匹对 新密码加密修改
                updateWrapper.set("user_password", value);
                user.setUserPassword(value);
                break;
            // 修改性别
            case "userGender":
                updateWrapper.set("user_gender", gender);
                user.setUserGender(gender);
                break;
            // 修改生日
            case "userBirthday":
                updateWrapper.set("user_birthday", value);
                user.setUserBirthday(value);
                break;
            // 修改简介
            case "userProfile":
                updateWrapper.set("user_profile", value);
                user.setUserProfile(value);
                break;
            // 修改所在地
            case "userLocation":
                updateWrapper.set("user_location", value);
                user.setUserLocation(value);
                break;
            // 修改家乡
            case "userHometown":
                updateWrapper.set("user_hometown", value);
                user.setUserHometown(value);
                break;
            // 修改专业
            case "userProfession":
                updateWrapper.set("user_profession", value);
                user.setUserProfession(value);
                break;
            // 修改标签
            case "userTags":
                updateWrapper.set("user_tags", value);
                user.setUserTags(value);
                break;
            default:
                log.error("修改用户个人信息----->修改参数错误");
                throw new BusinessExceptionHandler(400, "数据错误");
        }

        // 修改数据库数据
        int result = userMapper.update(user, updateWrapper);
        if (result == 0) {
            log.error("修改用户个人信息----->用户信息修改失败");
            throw new BusinessExceptionHandler(400, "信息修改失败");
        }

        // 更新 Redis 缓存
        String redisKey = RedisConstant.getRedisKey(RedisConstant.USER_INFO, userId);
        redisUtil.redisTransaction(redisKey);
        redisUtil.setRedisData(redisKey, gson.toJson(getUser(userId)));
        return true;
    }

    /**
     * 用户名模糊查询用户
     *
     * @param keyword 关键字
     */
    @Override
    public List<QueryUserVO> queryUser(String keyword) {
        // 获取自己的id
        Long userId = UserContext.getUserId();
        if (StringUtils.isBlank(keyword)) {
            log.error("模糊查询用户----->参数不能为空");
            throw new BusinessExceptionHandler(400, "参数不能为空");
        }
        // 查询数据库
        return userMapper.queryUser(keyword, userId);
    }

    /**
     * 查询用户头像
     * @param userId 用户id
     */
    @Override
    public String getAvatar(Long userId) {
        // 校验参数
        userId = checkUserId(userId);
        // 查询 Redis 记录
        String redisKey = RedisConstant.getRedisKey(RedisConstant.USER_INFO, userId);
        String redisData = redisUtil.getRedisData(redisKey);
        UserVO userVO = gson.fromJson(redisData, UserVO.class);
        // Redis 为空
        if (userVO == null) {
            // 查询数据库记录
            User user = null;
            try {
                user = userMapper.selectOne(new QueryWrapper<User>().select("user_avatar").eq("id", userId));
            } catch (Exception e) {
                log.error("查询用户头像----->数据库查询失败");
                throw new RuntimeException(e);
            }
            if (user == null) {
                log.error("查询用户头像----->用户不存在");
                throw new BusinessExceptionHandler(400, "用户不存在");
            }
            return user.getUserAvatar();
        }
        return userVO.getUserAvatar();
    }

    /**
     * 上传头像
     * @param userId 用户id
     * @param file 文件数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadAvatar(Long userId, MultipartFile file) {
        // 校验参数
        if (userId <= 0) {
            log.error("上传头像----->参数错误");
            throw new BusinessExceptionHandler(400, "参数错误");
        }
        // 上传阿里云
        try {
            String avatar = Upload.uploadAvatar(file, "avatar");
            try {
                this.update(new UpdateWrapper<User>().eq("id", userId).set("user_avatar", avatar));
            } catch (Exception e) {
                log.error("上传头像----->新头像地址保存到user表失败");
                throw new RuntimeException(e);
            }
            try {
                userMapper.saveAvatar(userId, avatar);
            } catch (Exception e) {
                log.error("上传头像----->新头像地址保存到avatar表失败");
                throw new RuntimeException(e);
            }
            // 更新缓存
            String redisKey = RedisConstant.getRedisKey(RedisConstant.USER_INFO, userId);
            redisUtil.redisTransaction(redisKey);
            redisUtil.setRedisData(redisKey, gson.toJson(getUser(userId)));
            return avatar;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询关注用户
     * @param userId 用户id
     */
    @Override
    public List<QueryUserVO> queryFriend(Long userId) {
        userId = checkUserId(userId);
        // 查询自己关注用户的id
        List<Long> idList = null;
        try {
            idList = userMapper.queryFriendIds(userId);
        } catch (Exception e) {
            log.error("查询关注用户 -----> 数据库查询关注用户id失败");
            throw new RuntimeException(e);
        }
        if (idList.isEmpty()) {
            log.error("查询关注用户 -----> 没有关注");
            throw new BusinessExceptionHandler(400, "暂无关注");
        }
        // 根据id查询用户信息
        try {
            return userMapper.queryFriend(idList);
        } catch (Exception e) {
            log.error("查询关注用户 -----> 数据库查询关注用户信息失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询粉丝用户
     * @param userId 用户id
     */
    @Override
    public List<QueryUserVO> queryFans(Long userId) {
        userId = checkUserId(userId);
        // 查询自己粉丝用户的id
        List<Long> idList = null;
        try {
            idList = userMapper.queryFansIds(userId);
        } catch (Exception e) {
            log.error("查询粉丝用户 -----> 数据库查询粉丝用户id失败");
            throw new RuntimeException(e);
        }
        if (idList.isEmpty()) {
            log.error("查询粉丝用户 -----> 没有粉丝");
            throw new BusinessExceptionHandler(400, "暂无粉丝");
        }
        // 根据id查询用户信息
        try {
            return userMapper.queryFans(idList);
        } catch (Exception e) {
            log.error("查询粉丝用户 -----> 数据库查询粉丝用户信息失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验是根据自己的id还是其他人的id进行操作
     */
    private Long checkUserId(Long userId) {
        return userId == null ? UserContext.getUserId() : userId;
    }
}
