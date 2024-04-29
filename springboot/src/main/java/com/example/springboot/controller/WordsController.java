/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/4/2 15:28
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Words;
import com.example.springboot.service.WordsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/words")
public class WordsController {
    @Resource
    WordsService wordsService;

    @ApiOperation("从单词表随机选择6个单词")
    @GetMapping("/select")
    public Result selectComments() {
        // 构建 QueryWrapper
        QueryWrapper<Words> queryWrapper = new QueryWrapper<>();
        // 使用原生 SQL 来实现随机排序
        queryWrapper.last("ORDER BY RAND() LIMIT 6");
        List<Words> words = wordsService.list(queryWrapper);
        return Result.success(words);
    }
}
