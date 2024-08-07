/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:20
 */
package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Department;
import com.example.springboot.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends ServiceImpl<DepartmentMapper, Department> {
}
