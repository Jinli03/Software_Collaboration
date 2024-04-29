/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/2 15:16
 */
package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("tables")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tablese {
    @TableId(type= IdType.AUTO)
    private Integer id;//不用管
    private String school;//学校
    private String sub;//专业
    private Integer subcode;//专业代码
    private String city;//城市
    private String square;//地区 eg华东
    private Integer person23;//招收人数
    private Integer reperson23;//复试人数
    private Integer rescore23;//复试分数线
    private Integer person22;
    private Integer reperson22;
    private Integer rescore22;
    private Integer person21;
    private Integer reperson21;
    private Integer rescore21;
    private String pic;
    @TableField(exist = false)
    private Integer userId;
    @TableField(exist = false)
    private String user;
}
