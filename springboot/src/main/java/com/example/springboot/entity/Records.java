/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/7/1 18:54
 */
package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("records")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Records {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private String doctor;
    private String department;
    private String time;
    private String state;
    private String prescription;
}
