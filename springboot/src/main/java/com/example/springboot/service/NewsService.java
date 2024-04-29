/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/17 15:17
 */
package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.News;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.NewsMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsService extends ServiceImpl<NewsMapper, News> {
    @Resource
    NewsMapper newsMapper;
}
