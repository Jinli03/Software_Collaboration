/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:20
 */
package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Exchange;
import com.example.springboot.mapper.ExchangeMapper;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService extends ServiceImpl<ExchangeMapper, Exchange> {
}
