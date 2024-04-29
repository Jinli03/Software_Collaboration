/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 11:21
 */
package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Notice;
import com.example.springboot.entity.User;
import com.example.springboot.service.NoticeService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notice") //通过/web/hello访问
public class NoticeController {

    @Resource
    NoticeService noticeService;

    @Autowired
    UserService userService;

    @AuthAccess //token放行
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        User currentUser = TokenUtils.getCurrentUser();
        notice.setUserid(currentUser.getId());
        notice.setTime(DateUtil.now());
        noticeService.save(notice);
        return Result.success();
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return Result.success();
    }

    //单个删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return Result.success();
    }

    //批量删除
    @DeleteMapping("/delete/batch")
    public Result batchdelete(@RequestBody List<Integer> ids) {
        noticeService.removeByIds(ids);
        return Result.success();
    }

    //查询全部
    @GetMapping("/select")
    public Result selectNotice() {
        List<Notice> notice = noticeService.list(new QueryWrapper<Notice>().orderByDesc("id"));
        return Result.success(notice);
    }

    //单条件查询
    @GetMapping("/selectById/{id}")
    public Result selectByIdNotice(@PathVariable Integer id) {
        Notice notice = noticeService.getById(id);
        return Result.success(notice);
    }

    @GetMapping("/selectUserData")
    public Result selectUserData() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().orderByDesc("id");
        queryWrapper.eq("open",1);
        List<Notice> userList = noticeService.list(queryWrapper);
        return Result.success(userList);
    }


    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String title) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(title), "title", title);
        Page<Notice> page = noticeService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Notice> records = page.getRecords();
        for (Notice record : records) {
            Integer authorid = record.getUserid();
            User user = userService.getById(authorid);
            if (user != null) {
                record.setUser(user.getName());
            }
        }
        return Result.success(page);
    }

}
