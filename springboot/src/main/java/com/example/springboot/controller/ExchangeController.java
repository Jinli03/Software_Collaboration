/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/3/30 23:18
 */
package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Exchange;
import com.example.springboot.entity.Forum;
import com.example.springboot.service.ExchangeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/exchange") //通过/web/hello访问
public class ExchangeController {
    @Resource
    ExchangeService exchangeService;

    @ApiOperation("获取书籍数据")
    @GetMapping("/allBooks")
    public Result selectAllBooks() {
        List<Exchange> exchangesList = exchangeService.list(
                new QueryWrapper<Exchange>().orderByDesc("time")
        );
        return Result.success(exchangesList);
    }

}
