/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 11:21
 */
package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
//二级路由@RequestMapping("/web") 通过/web/hello访问
public class WebController {

    @Resource
    UserService userService;

    @AuthAccess //token放行
    @GetMapping("/")//获取
    public Result hello() {
        return Result.success("nihao2");
    }
    @PostMapping("/post")//新增
    public Result post(@RequestBody Obj obj) {//传json
        return Result.success(obj);
    }
    @PutMapping("/put")//更新
    public Result put(@RequestBody Obj obj) {//传json
        return Result.success(obj);
    }
    @DeleteMapping("/delete/{id}")//删除
    public Result delete(@PathVariable Integer id) {//传json
        return Result.success(id);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getName()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("数据错误");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getName()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("数据错误");
        }
        user = userService.register(user);
        return Result.success(user);
    }

    //重置密码
    @AuthAccess
    @PutMapping("/password")
    public Result password(@RequestBody User user) {
        userService.resetPassword(user);
        return Result.success("重置成功");
    }

}
