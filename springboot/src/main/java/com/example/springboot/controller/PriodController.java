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
import java.util.Map;

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

    @ApiOperation("管理员上传")
    @PostMapping("/add")
    public Result add(@RequestBody Priod priod) {
        priod.setDoctor(priod.getDoctor());
        priod.setDepartment(priod.getDepartment());
        priod.setTime(priod.getTime());
        priod.setState("未预约");
        // 保存到数据库
        boolean isSaved = priodService.save(priod);
        if (isSaved) {
            return Result.success("上传成功");
        } else {
            return Result.error("排班上传失败");
        }
    }

    @ApiOperation("更新记录")
    @PutMapping("/update")
    public Result update(@RequestBody Priod priod) {
        boolean updated = priodService.updateById(priod);
        if (updated) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    @ApiOperation("根据医生查询排班记录数")
    @GetMapping("/countAllByDoctor")
    public Result countAllByDoctor(@RequestParam String doctor) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Priod> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", doctor);

        // 调用 recordsService 的 count 方法进行查询
        int count = priodService.count(queryWrapper);

        // 返回查询结果
        return Result.success(count);
    }

    @ApiOperation("根据 ID 设置挂号记录状态")
    @PutMapping("/updateStateById")
    public Result updateStateById(@RequestBody Map<String, Object> request) {
        Integer id = (Integer) request.get("id");
        String state = (String) request.get("state");

        // 根据 ID 查询实体对象
        Priod priod = priodService.getById(id);

        if (priod == null) {
            return Result.error("根据 ID 找不到挂号记录");
        }

        // 设置新的状态
        priod.setState(state);

        // 更新数据库
        boolean updated = priodService.updateById(priod);
        if (updated) {
            return Result.success("更新挂号记录状态成功");
        } else {
            return Result.error("更新挂号记录状态失败");
        }
    }


}


