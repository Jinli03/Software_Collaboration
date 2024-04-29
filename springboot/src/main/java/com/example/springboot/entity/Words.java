/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/2 15:23
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
@TableName("words")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Words {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String word;
    private String chinese;
}
