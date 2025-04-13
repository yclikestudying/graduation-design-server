package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ActivityService;
import com.project.vo.activity.QueryActivityVO;
import com.project.vo.activity.QueryOneActivityVO;
import com.project.vo.activityRelation.ActivityRelationVO;
import com.project.vo.lost.QueryLostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
@Api(tags = "活动模块")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    /**
     * 创建活动
     */
    @PostMapping("/upload")
    @ApiOperation(value = "创建活动")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file,
                                       @RequestParam("title") String title,
                                       @RequestParam("text") String description,
                                       @RequestParam("max") Integer max){
        boolean result = activityService.uplaod(file, title, description, max);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 查询用户创建的活动
     */
    @GetMapping("/queryActivity")
    @ApiOperation(value = "查询用户创建的活动")
    public BaseResponse<List<QueryActivityVO>> queryActivity(@RequestParam(value = "userId", required = false) Long userId) {
        List<QueryActivityVO> list = activityService.queryActivity(userId);
        return BaseResponse.success(list);
    }

    /**
     * 查询用户所参见的活动（包括自己创建的）
     */
    @GetMapping("/queryJoinedActivity")
    @ApiOperation(value = "查询所参加的活动（包括自己创建的）")
    public BaseResponse<List<QueryActivityVO>> queryJoinedActivity() {
        List<QueryActivityVO> list = activityService.queryJoinedActivity();
        return BaseResponse.success(list);
    }

    /**
     * 查询所有活动
     */
    @GetMapping("/queryAllActivity")
    @ApiOperation(value = "查询所有活动")
    public BaseResponse<List<QueryActivityVO>> queryAllActivity() {
        List<QueryActivityVO> list = activityService.queryAllActivity();
        return BaseResponse.success(list);
    }

    /**
     * 关键字模糊查询活动
     */
    @GetMapping("/queryActivityByKeyword")
    @ApiOperation(value = "关键字模糊查询活动")
    public BaseResponse<List<QueryActivityVO>> queryActivityByKeyword(@RequestParam("keyword") String keyword) {
        List<QueryActivityVO> list = activityService.queryActivityByKeyword(keyword);
        return BaseResponse.success(list);
    }

    /**
     * 根据id删除活动
     */
    @DeleteMapping("/deleteActivity")
    @ApiOperation(value = "根据id删除活动")
    public BaseResponse<String> deleteActivity(@RequestParam("activityId") Long activityId) {
        boolean result = activityService.deleteActivity(activityId);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 根据活动id查询活动
     */
    @GetMapping("/queryActivityById")
    @ApiOperation(value = "根据活动id查询活动")
    public BaseResponse<QueryOneActivityVO> queryActivityById(@RequestParam("activityId") Long activityId) {
        QueryOneActivityVO list = activityService.queryActivityById(activityId);
        return BaseResponse.success(list);
    }

    /**
     * 获取群聊名称和人数
     */
    @GetMapping("/queryNameAndCount/{activityId}")
    @ApiOperation(value = "获取群聊名称和人数")
    public BaseResponse<ActivityRelationVO> queryNameAndCount(@PathVariable("activityId") Long activityId) {
        ActivityRelationVO activityRelationVO = activityService.queryNameAndCount(activityId);
        return BaseResponse.success(activityRelationVO);
    }

    /**
     * 分页查询群聊
     */
    @GetMapping("/queryGroupChatByPage")
    @ApiOperation(value = "分页查询群聊")
    public BaseResponse<Map<String, Object>> queryGroupChatByPage(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = activityService.queryGroupChatByPage(current, size);
        return BaseResponse.success(map);
    }

    /**
     * 批量删除群聊
     */
    @PutMapping("/deleteActivityBatch")
    @ApiOperation(value = "批量删除群聊")
    public BaseResponse<String> deleteActivityBatch(@RequestBody List<Long> activityIdList){
        boolean result = activityService.deleteActivityBatch(activityIdList);
        return result ? BaseResponse.success() : BaseResponse.fail();
    }

    /**
     * 按时间搜索群聊
     */
    @GetMapping("/queryActivityByTime")
    @ApiOperation(value = "按时间搜索群聊")
    public BaseResponse<Map<String, Object>> queryActivityByTime(@RequestParam("time") String time, @RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        Map<String, Object> map = activityService.queryActivityByTime(time, current, size);
        return BaseResponse.success(map);
    }
}
