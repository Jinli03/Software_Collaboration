/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/5 23:48
 */
package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Wishes;
import com.example.springboot.mapper.WishesMapper;
import org.springframework.stereotype.Service;

@Service
public class WishesService extends ServiceImpl<WishesMapper, Wishes> {
}
