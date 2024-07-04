/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:18
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Department;
import com.example.springboot.entity.Tablese;
import com.example.springboot.entity.User;
import com.example.springboot.service.DepartmentService;
import com.example.springboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/department") //通过/web/hello访问
public class DepartmentController {
    @Resource
    DepartmentService departmentService;

    @Resource
    UserService userService;

    @ApiOperation("查询全部部门数据")
    @GetMapping("/select")
    public Result selectDepartment() {
        List<Department> departments = departmentService.list(new QueryWrapper<Department>().orderByDesc("id"));
        return Result.success(departments);
    }

    @ApiOperation("管理员上传科室")
    @PostMapping("/add")
    public Result add(@RequestBody Department department) {
        department.setName(department.getName());
        department.setDes(department.getDes());
        department.setTitle(department.getTitle());
        // 保存到数据库
        boolean isSaved = departmentService.save(department);
        if (isSaved) {
            return Result.success("上传成功");
        } else {
            return Result.error("科室上传失败");
        }
    }

    @ApiOperation("删除科室数据")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        departmentService.removeById(id);
        return Result.success();
    }

    @ApiOperation("更新科室数据")
    @PutMapping("/update")
    public Result update(@RequestBody Department department) {
        departmentService.updateById(department);
        return Result.success();
    }

    @ApiOperation("查询全部医生数据")
    @GetMapping("/selectAllDoctors")
    public Result selectAllDoctors() {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", "doctor"); // 查询 role 字段为 doctor 的记录，并按 id 降序排序

        // 调用 userService 的 list 方法进行查询
        List<User> doctors = userService.list(queryWrapper);

        // 返回查询结果
        return Result.success(doctors);
    }

    @ApiOperation("根据部门查询医生")
    @GetMapping("/selectByDepartment")
    public Result selectByDepartment(@RequestParam() String department) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department", department); // 查询 role 字段为 doctor 的记录，并按 id 降序排序

        // 调用 userService 的 list 方法进行查询
        List<User> doctors = userService.list(queryWrapper);

        // 返回查询结果
        return Result.success(doctors);
    }
}


