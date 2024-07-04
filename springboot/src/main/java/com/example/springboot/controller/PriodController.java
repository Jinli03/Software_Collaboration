/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:18
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Priod;
import com.example.springboot.entity.Records;
import com.example.springboot.service.PriodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/priod") //通过/web/hello访问
public class PriodController {
    @Resource
    PriodService priodService;

    @ApiOperation("根据姓名和状态查询挂号记录")
    @GetMapping("/selectByDoctor")
    public Result selectByName(@RequestParam String name) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Priod> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", name);

        // 调用 recordsService 的 list 方法进行查询
        List<Priod> priods = priodService.list(queryWrapper);

        // 返回查询结果
        return Result.success(priods);
    }

    @ApiOperation("根据 ID 设置挂号记录状态")
    @PutMapping("/setStateById")
    public Result setStateById(@RequestParam Integer id) {
        // 根据 ID 查询实体对象
        Priod priod = priodService.getById(id);

        if (priod == null) {
            return Result.error("根据 ID 找不到挂号记录");
        }

        // 设置 State
        priod.setState("已预约");

        // 更新数据库
        boolean updated = priodService.updateById(priod);
        if (updated) {
            return Result.success("更新挂号记录状态成功");
        } else {
            return Result.error("更新挂号记录状态失败");
        }
    }

    @ApiOperation("根据医生和时间更新记录状态")
    @GetMapping("/editState")
    public Result editState(@RequestParam String doctor, @RequestParam String time, @RequestParam String state) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Priod> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", doctor).eq("time", time);

        // 获取记录
        Priod priod = priodService.getOne(queryWrapper);
        if (priod == null) {
            return Result.error("未找到相关记录");
        }

        // 更新状态
        priod.setState(state);
        boolean updated = priodService.updateById(priod);

        if (updated) {
            return Result.success("记录状态更新成功");
        } else {
            return Result.error("记录状态更新失败");
        }
    }

    @ApiOperation("查询所有挂号记录")
    @GetMapping("/selectAllPriods")
    public Result selectAllPriods() {
        // 获取所有记录
        List<Priod> priods = priodService.list();

        // 返回查询结果
        return Result.success(priods);
    }

}

