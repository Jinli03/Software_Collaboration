package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.News;
import com.example.springboot.entity.News;
import com.example.springboot.entity.User;
import com.example.springboot.service.NewsService;
import com.example.springboot.service.NewsService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

public class MyController {
    @Resource
    NewsService newsService;
    
    UserService userService;

    @AuthAccess //token放行
    @PostMapping("/add")
    public Result add(@RequestBody News news) {
        User currentUser = TokenUtils.getCurrentUser();
        news.setAuthorid(currentUser.getId());
        news.setTime(DateUtil.now());
        newsService.save(news);
        return Result.success();
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody News news) {
        newsService.updateById(news);
        return Result.success();
    }

    //单个删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        newsService.removeById(id);
        return Result.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public Result batchdelete(@RequestBody List<Integer> ids) {
        newsService.removeByIds(ids);
        return Result.success();
    }

    //查询全部
    @GetMapping("/select")
    public Result selectNews() {
        List<News> news = newsService.list(new QueryWrapper<News>().orderByDesc("id"));
        return Result.success(news);
    }

    //单条件查询
    @GetMapping("/selectById/{id}")
    public Result selectByIdNews(@PathVariable Integer id) {
        News news = newsService.getById(id);
        return Result.success(news);
    }


    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String title) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<News>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(title), "title", title);
        Page<News> page = newsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<News> records = page.getRecords();
        for (News record : records) {
            Integer authorid = record.getAuthorid();
            User user = userService.getById(authorid);
            if (user != null) {
                record.setAuthor(user.getName());
            }
        }
        return Result.success(page);
    }
}
