/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/5 23:41
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
@TableName("wishes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wishes {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private String school;
    private String sub;
    private String subcode;
    private String city;
    private String square;
    private Integer person21;
    private Integer person22;
    private Integer person23;
    private Integer reperson21;
    private Integer reperson22;
    private Integer reperson23;
    private Integer rescore21;
    private Integer rescore22;
    private Integer rescore23;
    private Integer max;
    private Integer min;
    private String pic;
}
