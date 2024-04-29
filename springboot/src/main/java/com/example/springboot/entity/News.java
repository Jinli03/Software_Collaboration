/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/29 19:41
 */
package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("news")
public class News {
    @TableId(type= IdType.AUTO)
    @Alias("序号")
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Integer authorid;
    private String time;
    @TableField(exist = false)
    private String author;
}
