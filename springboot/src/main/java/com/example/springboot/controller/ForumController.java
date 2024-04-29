/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:18
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Forum;
import com.example.springboot.entity.Tablese;
import com.example.springboot.service.ForumService;
import com.example.springboot.common.AuthAccess;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/forum") //通过/web/hello访问
public class ForumController {
    @Resource
    ForumService forumService;

    @ApiOperation("获取评论数据")
    @GetMapping("/select")
    public Result selectComments() {
        List<Forum> forumList = forumService.list(
                new QueryWrapper<Forum>().orderByDesc("time").last("LIMIT 4")
        );
        return Result.success(forumList);
    }


    @ApiOperation("发布评论")
    @PostMapping("/publish")
    public Result publishComment(@RequestBody Forum forum) {
        boolean saved = forumService.save(forum);
        if (saved) {
            return Result.success("发布成功");
        } else {
            return Result.error("发布失败.");
        }
    }

    /**
     * 获取所有评论数据
     */
    @ApiOperation("获取所有评论数据")
    @GetMapping("/allComments")
    public Result getAllComments(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {
        QueryWrapper<Forum> queryWrapper = new QueryWrapper<>();
        Page<Forum> page = forumService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

}
