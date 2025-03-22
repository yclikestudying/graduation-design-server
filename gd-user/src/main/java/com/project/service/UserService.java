package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.User;
import com.project.dto.user.UserLoginRequest;
import com.project.dto.user.UserRegisterRequest;
import com.project.vo.user.QueryUserVO;
import com.project.vo.user.UserVO;
import com.project.vo.visit.QueryVisitVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    /**
     * 用户注册
     */
    boolean register(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     */
    Map<String, Object> login(UserLoginRequest userLoginRequest);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     */
    UserVO getUser(Long userId);

    /**
     * 用户注销账号
     */
    boolean userDeleteAccount();

    /**
     * 修改用户个人信息
     */
    boolean updateUser(Map<String, Object> map);

    /**
     * 用户名模糊查询用户
     *
     * @param keyword 关键字
     */
    List<QueryUserVO> queryUser(String keyword);

    /**
     * 查询用户头像
     * @param userId 用户id
     */
    String getAvatar(Long userId);

    /**
     * 上传头像
     * @param userId 用户id
     * @param file 文件数据
     */
    String uploadAvatar(Long userId, MultipartFile file);

    /**
     * 查询关注用户
     * @param userId 用户id
     */
    List<QueryUserVO> queryFriend(Long userId);

    /**
     * 查询粉丝用户
     * @param userId 用户id
     */
    List<QueryUserVO> queryFans(Long userId);

    /**
     * 查询互关用户
     * @param userId 用户id
     */
    List<QueryUserVO> queryEach(Long userId);

    /**
     * 添加访客记录
     */
    boolean addVisit(Long userId);

    /**
     * 查询访客记录
     */
    List<QueryVisitVO> queryVisit();
}
