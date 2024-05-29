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
import com.example.springboot.entity.Tablese;
import com.example.springboot.service.ExchangeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("管理员上传图书")
    @PostMapping("/addBook")
    public Result addBook(@RequestBody Exchange exchange) {
        exchange.setBoss(exchange.getBoss());
        exchange.setName(exchange.getName());
        exchange.setPrice(exchange.getPrice());
        exchange.setSub(exchange.getSub());
        exchange.setDes(exchange.getDes());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        exchange.setTime(formattedDate);
        exchange.setState("待检查");
        exchange.setBuyer(exchange.getBuyer() != null ? exchange.getBuyer() : "");

        // 保存到数据库
        boolean isSaved = exchangeService.save(exchange);
        if (isSaved) {
            return Result.success("上传成功");
        } else {
            return Result.error("书籍上传失败");
        }
    }

    @ApiOperation("根据state查询所有图书")
    @GetMapping("/selectByState/{state}")
    public Result selectByState(@PathVariable String state) {
        List<Exchange> exchanges = exchangeService.list(new QueryWrapper<Exchange>().eq("state", state));
        return Result.success(exchanges);
    }

    @ApiOperation("根据state和boss查询所有图书")
    @GetMapping("/selectByMerchantState")
    public Result selectByStateAndBoss(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String state,
            @RequestParam String boss
    ) {
        QueryWrapper<Exchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", state)
                .eq("boss", boss);

        // 执行分页查询
        Page<Exchange> page = exchangeService.page(new Page<>(pageNum, pageSize), queryWrapper);

        // 从分页查询结果中获取记录列表
        List<Exchange> records = page.getRecords();

        // 对记录列表进行处理（如果需要的话）
        for (Exchange record : records) {
            // 这里可以添加需要的逻辑
        }

        return Result.success(page);
    }

    @ApiOperation("根据state查询所有图书")
    @GetMapping("/selectByManagerState")
    public Result selectByManagerState(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String state
    ) {
        QueryWrapper<Exchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", state);

        // 执行分页查询
        Page<Exchange> page = exchangeService.page(new Page<>(pageNum, pageSize), queryWrapper);

        // 从分页查询结果中获取记录列表
        List<Exchange> records = page.getRecords();

        // 对记录列表进行处理（如果需要的话）
        for (Exchange record : records) {
            // 这里可以添加需要的逻辑
        }

        return Result.success(page);
    }

    @ApiOperation("根据id下架某本书")
    @DeleteMapping("/removeShelves/{id}")
    public Result remove(@PathVariable Integer id) {
        // 根据ID查询对应的书籍记录
        Exchange book = exchangeService.getById(id);

        // 如果找到了书籍记录，则更新其状态为下架
        if (book != null) {
            book.setState("下架");
            exchangeService.updateById(book);
            return Result.success("成功下架书籍！");
        } else {
            return Result.error("未找到指定ID的书籍！");
        }
    }

    @ApiOperation("根据id上架某本书")
    @DeleteMapping("/upShelves/{id}")
    public Result up(@PathVariable Integer id) {
        // 根据ID查询对应的书籍记录
        Exchange book = exchangeService.getById(id);

        // 如果找到了书籍记录，则更新其状态为下架
        if (book != null) {
            book.setState("上架");
            exchangeService.updateById(book);
            return Result.success("成功上架书籍！");
        } else {
            return Result.error("未找到指定ID的书籍！");
        }
    }

    @ApiOperation("根据id下架某本不合规书")
    @DeleteMapping("/downShelves/{id}")
    public Result down(@PathVariable Integer id) {
        // 根据ID查询对应的书籍记录
        Exchange book = exchangeService.getById(id);

        // 如果找到了书籍记录，则更新其状态为下架
        if (book != null) {
            book.setState("不合规");
            exchangeService.updateById(book);
            return Result.success("成功下架书籍！");
        } else {
            return Result.error("未找到指定ID的书籍！");
        }
    }




    @ApiOperation("根据id购买书籍")
    @PutMapping ("/purchaseShelves/{id}")
    public Result purchase(@PathVariable Integer id) {
        // 根据ID查询对应的书籍记录
        Exchange book = exchangeService.getById(id);

        // 如果找到了书籍记录，则更新其购买状态为已购买
        if (book != null) {
            book.setBuyer("已购买");
            exchangeService.updateById(book);
            return Result.success("购买成功！");
        } else {
            return Result.error("购买失败");
        }
    }



    @ApiOperation("根据buyer查询所有图书")
    @GetMapping("/selectByBuyState")
    public Result selectByBuyer(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String buyer

    ) {
        QueryWrapper<Exchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer", buyer);


        // 执行分页查询
        Page<Exchange> page = exchangeService.page(new Page<>(pageNum, pageSize), queryWrapper);

        // 从分页查询结果中获取记录列表
        List<Exchange> records = page.getRecords();

        // 对记录列表进行处理（如果需要的话）
        for (Exchange record : records) {
            // 这里可以添加需要的逻辑
        }

        return Result.success(page);
    }



    @ApiOperation("根据id添加至购物车")
    @PutMapping ("/AddToShopCar/{id}")
    public Result Add(@PathVariable Integer id) {
        // 根据ID查询对应的书籍记录
        Exchange book = exchangeService.getById(id);

        // 如果找到了书籍记录，则更新其购买状态为已购买
        if (book != null) {
            book.setShopcar("是");
            exchangeService.updateById(book);
            return Result.success("添加成功！");
        } else {
            return Result.error("添加失败");
        }
    }


}


