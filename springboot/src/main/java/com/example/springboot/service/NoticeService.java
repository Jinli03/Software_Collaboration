/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/2 15:21
 */
package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Notice;
import com.example.springboot.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    @Resource
    NoticeMapper noticeMapper;

}
