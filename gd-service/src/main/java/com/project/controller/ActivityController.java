package com.project.controller;

import com.project.common.BaseResponse;
import com.project.service.ActivityService;
import com.project.vo.activity.QueryActivityVO;
import com.project.vo.activity.QueryOneActivityVO;
import com.project.vo.lost.QueryLostVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
     * 查询活动数量
     */
    @GetMapping("/queryCount")
    @ApiOperation(value = "查询活动数量")
    public BaseResponse<Integer> queryCount() {
        Integer count = activityService.queryCount();
        return BaseResponse.success(count);
    }
}
