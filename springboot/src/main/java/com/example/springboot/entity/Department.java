/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/7/1 14:16
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
@TableName("department")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private String des;
    private String title;
}
