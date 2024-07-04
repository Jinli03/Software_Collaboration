/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/7/2 10:04
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
@TableName("priod")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Priod {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String doctor;
    private String department;
    private String time;
    private String state;
}
