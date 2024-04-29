/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 15:07
 */
package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type=IdType.AUTO)
    @Alias("序号")
    private Integer id;
    @Alias("姓名")
    private String name;
    @Alias("密码")
    private String password;
    @Alias("电话")
    private String phone;
    @Alias("邮箱")
    private String email;
    @Alias("头像")
    private String title;
    @Alias("角色")
    private String role;

    @TableField(exist = false)
    private String token;
}
