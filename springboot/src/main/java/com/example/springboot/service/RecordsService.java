/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:20
 */
package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Department;
import com.example.springboot.entity.Records;
import com.example.springboot.mapper.RecordsMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordsService extends ServiceImpl<RecordsMapper, Records> {

    public int hasRecordByNameAndDoctor(String name, String doctor) {
        // 创建 QueryWrapper 对象，并添加查询条件
        QueryWrapper<Records> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .eq("doctor", doctor)
                .ne("state", "取消预约"); // 添加条件排除状态为"取消预约"的记录

        // 调用 MyBatis Plus 的查询方法，注意这里使用的是 this.count 方法
        int count = this.count(queryWrapper);

        // 返回记录数
        return count;
    }



}
