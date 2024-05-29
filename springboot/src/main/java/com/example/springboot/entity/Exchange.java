/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/22 11:37
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
@TableName("exchange")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String boss;
    private String name;
    private String sub;
    private Integer price;
    private String des;
    private String time;
    private String state;
    private String buyer;
    private String shopcar;
}
