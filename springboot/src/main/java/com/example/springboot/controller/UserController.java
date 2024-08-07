/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 15:18
 */
package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Records;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //添加
    @ApiOperation("添加用户数据")
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        try {
            userService.save(user);
        } catch (Exception e){
            if (e instanceof DuplicateFormatFlagsException){
                return Result.error("插入数据错误");
            } else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    //更新
    @ApiOperation("更新用户数据")
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateById(user);
        boolean updated = userService.updateById(user);
        if (updated) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

    //单个删除
    @ApiOperation("单个删除用户数据")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    //批量删除
    @ApiOperation("批量删除用户数据")
    @DeleteMapping("/delete/batch")
    public Result batchdelete(@RequestBody List<Integer> ids) {
        userService.removeByIds(ids);
        return Result.success();
    }

    //查询全部
    @ApiOperation("查询全部用户数据")
    @GetMapping("/select")
    public Result selectUser() {
        List<User> user = userService.list(new QueryWrapper<User>().orderByDesc("id"));
        return Result.success(user);
    }

    //单条件查询
    @ApiOperation("根据id查询用户数据")
    @GetMapping("/selectById/{id}")
    public Result selectByIdUser(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

//    //多条件查询
//    @GetMapping("/selectMore")
//    public Result selectMoreUser(@RequestParam Integer id, @RequestParam String name) {
//        User user = userService.selectMoreUser(id, name);
//        return Result.success(user);
//    }
//
//    //模糊查询
//    @GetMapping("/selectMo")
//    public Result selectMo(@RequestParam String name) {
//        List<User> user = userService.selectMo(name);
//        return Result.success(user);
//    }

    //分页查询
    @ApiOperation("分页查询用户数据")
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String name,
                               @RequestParam String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name)
                .and(StrUtil.isNotBlank(phone), i -> i.like("phone", phone));

        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    @ApiOperation("导出用户数据")
    @GetMapping("/export")
    public void exportData(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String phone,
                           @RequestParam(required = false) String ids,
                           HttpServletResponse res) throws IOException {
        try (ExcelWriter writer = ExcelUtil.getWriter(true)) {
            List<User> list;
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
            if (StrUtil.isNotBlank(ids)) {
                List<Integer> idArr = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
                queryWrapper.in("id",idArr);
            } else {
                queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
                queryWrapper.like(StrUtil.isNotBlank(phone), "phone", phone);
            }
            // 全部导出
            list = userService.list(queryWrapper);
            writer.write(list, true);
            res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            res.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("用户信息表","UTF-8") +".xlsx");
            ServletOutputStream outputStream = res.getOutputStream();
            writer.flush(outputStream, true);
            writer.close();
            outputStream.flush();
            outputStream.close();
        }
    }

    @ApiOperation("导入用户数据")
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<User> userList = reader.readAll(User.class);
        try{
            userService.saveBatch(userList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("数据导入错误");
        }
        return Result.success();
    }

    @ApiOperation("查询全部医生数据")
    @GetMapping("/selectAllAdmins")
    public Result selectAllAdmins() {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", "admin"); // 查询 role 字段为 doctor 的记录，并按 id 降序排序

        // 调用 userService 的 list 方法进行查询
        List<User> admins = userService.list(queryWrapper);

        // 返回查询结果
        return Result.success(admins);
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

    @ApiOperation("查询全部医生数据")
    @GetMapping("/selectAllPatients")
    public Result selectAllPatients() {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", "patient"); // 查询 role 字段为 doctor 的记录，并按 id 降序排序

        // 调用 userService 的 list 方法进行查询
        List<User> patients = userService.list(queryWrapper);

        // 返回查询结果
        return Result.success(patients);
    }

    @ApiOperation("根据ID查询医生信息")
    @GetMapping("/selectDoctorByName")
    public Result selectDoctorById(@RequestParam("name") String name) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name).eq("role", "doctor");

        // 调用 userService 的 getOne 方法进行查询
        User doctor = userService.getOne(queryWrapper);

        // 检查查询结果是否为空
        if (doctor != null) {
            // 返回查询结果
            return Result.success(doctor);
        } else {
            // 返回错误信息
            return Result.error("Doctor not found");
        }
    }

    @ApiOperation("id寻找用户信息")
    @GetMapping("/getById")
    public Result getById(@RequestParam Integer id) {
        // 根据 ID 查询实体对象
        User user = userService.getById(id);

        if (user == null) {
            return Result.error("根据 ID 找不到记录");
        }

        // 返回查询结果
        return Result.success(user);
    }

    @ApiOperation("更新用户余额")
    @PutMapping("/updateBalance")
    public Result updateBalance(@RequestParam String name, @RequestParam Integer balance) {
        User user = userService.getByName(name);
        if (user == null) {
            return Result.error("用户未找到");
        }
        user.setBalance(user.getBalance() + balance); // 更新余额
        boolean updated = userService.updateById(user);
        if (updated) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }

}
