/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/5 23:49
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Wishes;
import com.example.springboot.service.WishesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishesController {

    @Resource
    WishesService wishesService;


    @ApiOperation("收藏院校到心愿单")
    @PutMapping("/save")
    public Result save(@RequestBody Wishes wish) {
        // 检查是否存在与要添加的记录相同的记录
        List<Wishes> existingWishes = wishesService.list(Wrappers.<Wishes>lambdaQuery()
                        .eq(Wishes::getSchool, wish.getSchool())
                        .eq(Wishes::getSub, wish.getSub())
                        .eq(Wishes::getCity, wish.getCity())
                        .eq(Wishes::getName, wish.getName())
                // 按需添加其他条件
        );

        if (!existingWishes.isEmpty()) {
            // 如果存在相同记录，则不执行保存操作
            return Result.error("Record already exists.");
        }else{
            // 执行保存操作
            wishesService.save(wish);
            return Result.success("Wish saved successfully.");
        }
    }

    @ApiOperation("获取登录用户心愿单随机三个心愿学校专业")
    @GetMapping("/selectThree")
    public Result selectThreeSchools(@RequestParam String name) {
        // 查询数据库中符合条件的记录，并随机排序，选择前三条记录
        List<Wishes> wishesList = wishesService.list(Wrappers.<Wishes>lambdaQuery()
                .eq(Wishes::getName, name)
                .orderByAsc(Wishes::getId)
                .last("LIMIT 3")
        );

        // 如果查询结果不为空，则返回成功结果和查询结果；否则，返回失败结果
        if (!wishesList.isEmpty()) {
            return Result.success(wishesList);
        } else {
            return Result.error("No records found.");
        }
    }

    @ApiOperation("获取登录用户所有心愿学校专业")
    @GetMapping("/selectAll/{name}")
    public Result selectAllSchools(@PathVariable String name) {
        QueryWrapper<Wishes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        // 执行查询
        List<Wishes> wishesList = wishesService.list(queryWrapper);

        // 返回结果
        return Result.success(wishesList);
    }

    @ApiOperation("删除心愿学校专业")
    @DeleteMapping("/delete/{id}")
    public Result deleteWishById(@PathVariable Integer id) {
        try {
            // 调用 service 方法执行删除操作
            boolean success = wishesService.removeById(id);

            // 判断是否删除成功，并返回相应结果
            if (success) {
                return Result.success("删除成功");
            } else {
                return Result.error("删除失败");
            }
        } catch (Exception e) {
            // 捕获异常，返回删除失败的结果
            return Result.error("删除失败：" + e.getMessage());
        }
    }

}
