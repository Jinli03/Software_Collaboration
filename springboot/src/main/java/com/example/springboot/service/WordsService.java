/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/2 15:27
 */
package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Words;
import com.example.springboot.mapper.WordsMapper;
import org.springframework.stereotype.Service;

@Service
public class WordsService extends ServiceImpl<WordsMapper, Words> {
}
