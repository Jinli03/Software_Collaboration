/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:16
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
@TableName("forum")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Forum {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String author;
    private String tag;
    private String comment;
    private String time;
    private String avatr;
}
