/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:18
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Department;
import com.example.springboot.entity.Records;
import com.example.springboot.entity.User;
import com.example.springboot.service.DepartmentService;
import com.example.springboot.service.PriodService;
import com.example.springboot.service.RecordsService;
import com.example.springboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/records") //通过/web/hello访问
public class RecordsController {
    @Resource
    RecordsService recordsService;
    @Resource
    PriodService priodService;
    @Resource
    UserService userService;


    @ApiOperation("查询全部挂号记录")
    @GetMapping("/select")
    public Result selectRecords() {
        List<Records> records = recordsService.list(new QueryWrapper<Records>().orderByDesc("id"));
        return Result.success(records);
    }

    @ApiOperation("根据状态查询记录")
    @GetMapping("/selectByState")
    public Result selectByDepartment(@RequestParam() String state) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", state); // 查询 role 字段为 doctor 的记录，并按 id 降序排序

        // 调用 userService 的 list 方法进行查询
        List<Records> records = recordsService.list(queryWrapper);

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("根据名字和医生查询记录")
    @GetMapping("/selectByNameAndDoctor")
    public Result selectByDepartment(@RequestParam() String name, @RequestParam String doctor) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .eq("doctor", doctor); // 查询 role 字段为 doctor 的记录，并按 id 降序排序

        // 调用 userService 的 list 方法进行查询
        List<Records> records = recordsService.list(queryWrapper);

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("添加挂号记录")
    @PostMapping("/addRecord")
    public Result addRecord(@RequestBody Records record) {
        String doctor = record.getDoctor();
        String name = record.getName();
        User user = userService.getByName(name);
        int balance = user.getBalance();
        int result = recordsService.hasRecordByNameAndDoctor(name,doctor);
        if (result > 0) {
            return Result.error("今日已预约");
        } else{
            if (balance >= 20) {
                balance = balance - 20;
                user = userService.setBalance(name, balance);
                userService.updateUser(user);
                recordsService.save(record);
                return Result.success("预约成功");
            }
            else {
                return Result.error("余额不足");
            }
        }
    }

    @ApiOperation("根据姓名查询挂号记录")
    @GetMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        // 调用 recordsService 的 list 方法进行查询
        List<Records> records = recordsService.list(queryWrapper);

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("根据姓名查询挂号记录")
    @GetMapping("/selectByNameDoctor")
    public Result selectByNameDoctor(@RequestParam String doctor) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", doctor);

        // 调用 recordsService 的 list 方法进行查询
        List<Records> records = recordsService.list(queryWrapper);

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("根据姓名和状态查询挂号记录")
    @GetMapping("/selectByNameAndState")
    public Result selectByNameAndState(@RequestParam String name, @RequestParam String state) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .eq("state", state);

        // 调用 recordsService 的 list 方法进行查询
        List<Records> records = recordsService.list(queryWrapper);

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("根据姓名和状态查询挂号记录")
    @GetMapping("/selectByNameAndStateDoctor")
    public Result selectByNameAndStateDoctor(@RequestParam String doctor, @RequestParam String state) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", doctor)
                .eq("state", state);

        // 调用 recordsService 的 list 方法进行查询
        List<Records> records = recordsService.list(queryWrapper);

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("叫号改变状态")
    @GetMapping("/editState")
    public Result editState(@RequestParam Integer id, @RequestParam String state) {
        // 根据 ID 查询实体对象
        Records records = recordsService.getById(id);

        if (records == null) {
            return Result.error("根据 ID 找不到记录");
        }

        // 设置新的状态
        records.setState(state);

        // 更新数据库
        boolean updated = recordsService.updateById(records);
        if (updated) {
            return Result.success("更新记录状态成功");
        } else {
            return Result.error("更新记录状态失败");
        }
    }

    @ApiOperation("id寻找就诊记录")
    @GetMapping("/getById")
    public Result getById(@RequestParam Integer id) {
        // 根据 ID 查询实体对象
        Records records = recordsService.getById(id);

        if (records == null) {
            return Result.error("根据 ID 找不到记录");
        }

        // 返回查询结果
        return Result.success(records);
    }

    @ApiOperation("管理员上传科室")
    @PostMapping("/add")
    public Result add(@RequestBody Records record) {
        record.setDoctor(record.getDoctor());
        record.setDepartment(record.getDepartment());
        record.setTime(record.getTime());
        // 保存到数据库
        boolean isSaved = recordsService.save(record);
        if (isSaved) {
            return Result.success("上传成功");
        } else {
            return Result.error("排班上传失败");
        }
    }

    @ApiOperation("更新记录")
    @PutMapping("/updateRecord")
    public Result updateRecord(@RequestBody Records record) {
        boolean updated = recordsService.updateById(record);
        if (updated) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    @ApiOperation("删除就诊记录")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        recordsService.removeById(id);
        return Result.success();
    }

    @ApiOperation("计算每个科室的挂号次数")
    @GetMapping("/countByDepartment")
    public Result countByDepartment() {
        List<Records> records = recordsService.list();
        Map<String, Long> countByDepartment = records.stream()
                .collect(Collectors.groupingBy(Records::getDepartment, Collectors.counting()));

        // 转换为列表并排序
        List<Map.Entry<String, Long>> sortedCountByDepartment = new ArrayList<>(countByDepartment.entrySet());
        sortedCountByDepartment.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // 转换为结果数组
        List<Map<String, Object>> result = sortedCountByDepartment.stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("department", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                }).collect(Collectors.toList());

        return Result.success(result);
    }

    @ApiOperation("查询患者的挂号次数，按次数从小到大排列，只选出前三个")
    @GetMapping("/countRecordsByNameSorted")
    public Result countRecordsByNameSorted() {
        // 使用 MyBatis Plus 的 QueryWrapper 创建查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();

        // 分组查询，并按次数升序排列
        queryWrapper.select("name, count(*) as count")
                .groupBy("name")
                .orderByAsc("count")
                .last("limit 3"); // 只选出前三个

        // 调用 recordsService 的 listMaps 方法执行查询
        List<Map<String, Object>> result = recordsService.listMaps(queryWrapper);

        return Result.success(result);
    }

    @ApiOperation("根据状态为取消预约，统计每个人的挂号次数，从高到低，统计前三位")
    @GetMapping("/countRecordsByRefuse")
    public Result countRecordsByRefuse() {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name, count(*) as count")
                .eq("state", "取消挂号")
                .groupBy("name")
                .orderByDesc("count(*)")
                .last("limit 3"); // 只选出前三个

        // 调用 recordsService 的 listMaps 方法执行查询
        List<Map<String, Object>> result = recordsService.listMaps(queryWrapper);

        return Result.success(result);
    }

    @ApiOperation("根据医生查询待就诊记录数")
    @GetMapping("/countPendingByDoctor")
    public Result countPendingByDoctor(@RequestParam String doctor) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", doctor)
                .eq("state", "待叫号");

        // 调用 recordsService 的 count 方法进行查询
        int count = recordsService.count(queryWrapper);

        // 返回查询结果
        return Result.success(count);
    }

    @ApiOperation("根据医生查询待就诊记录数")
    @GetMapping("/countAllByDoctor")
    public Result countAllByDoctor(@RequestParam String doctor) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor", doctor);

        // 调用 recordsService 的 count 方法进行查询
        int count = recordsService.count(queryWrapper);

        // 返回查询结果
        return Result.success(count);
    }

    @ApiOperation("根据患者查询待就诊记录数")
    @GetMapping("/countAllByPatient")
    public Result countAllByPatient(@RequestParam String name) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        // 调用 recordsService 的 count 方法进行查询
        int count = recordsService.count(queryWrapper);

        // 返回查询结果
        return Result.success(count);
    }

    @ApiOperation("查询符合传入name的id最大的那条记录所有信息")
    @GetMapping("/selectMaxIdRecordByName")
    public Result selectLatestRecordByName(@RequestParam String name) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .orderByDesc("id")
                .last("limit 1"); // 只取第一条记录

        // 调用 recordsService 的 getOne 方法进行查询
        Records record = recordsService.getOne(queryWrapper);

        if (record == null) {
            return Result.error("没有找到符合条件的记录");
        }

        // 返回查询结果
        return Result.success(record);
    }

}


