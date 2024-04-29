/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/2 16:16
 */
package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Notice {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Integer userid;
    private String time;
    private Boolean open;
    @TableField(exist = false)
    private String user;

}
