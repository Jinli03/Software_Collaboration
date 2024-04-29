/**
 * 功能：
 * 作者：JInli
 * 日期： 2024/2/19 21:19
 */
package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATh = System.getProperty("user.dir") + File.separator + "files";// D:\vue\files

    @ApiOperation("上传文件")
    @PostMapping("/upload")//上传
    public Result upload(MultipartFile file) throws IOException {
        String originFilename = file.getOriginalFilename();//文件原始名称
        String mainname = FileUtil.mainName(originFilename);//不包含后缀 aaa
        String extName = FileUtil.extName(originFilename); //png
        //String fileName = originFilename; //实际存储到磁盘的文件名称
        if (!FileUtil.exist(ROOT_PATh)) {//当前文件父级目录不存在，则创建目录
            FileUtil.mkdir(ROOT_PATh);
        }
        if (FileUtil.exist(ROOT_PATh + File.separator + originFilename)) {//当前上传文件名已存在，则重命名
            originFilename = System.currentTimeMillis() + "_" + mainname + "." + extName;
        }
        File saveFile = new File(ROOT_PATh + File.separator + originFilename); // D:\vue\files\56526_aaa.png
        System.out.println("文件路径："+ROOT_PATh + File.separator + originFilename);
        file.transferTo(saveFile);//存储到本地磁盘
        String url = "http://" + ip + ":" + port + "/file/download/" + originFilename;
        return Result.success(url);
    }

    @AuthAccess
    @ApiOperation("下载文件")
    @GetMapping("/download/{fileName}")//下载
    public void download(@PathVariable String fileName, HttpServletResponse response) throws Exception{
        //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));//下载
        //response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));//预览
        String filePath = ROOT_PATh + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/editor/upload")//上传
    public Dict editorUpload(@RequestParam MultipartFile file, @RequestParam String type) throws IOException {
        String originFilename = file.getOriginalFilename();//文件原始名称
        String mainname = FileUtil.mainName(originFilename);//不包含后缀 aaa
        String extName = FileUtil.extName(originFilename); //png
        //String fileName = originFilename; //实际存储到磁盘的文件名称
        if (!FileUtil.exist(ROOT_PATh)) {//当前文件父级目录不存在，则创建目录
            FileUtil.mkdir(ROOT_PATh);
        }
        if (FileUtil.exist(ROOT_PATh + File.separator + originFilename)) {//当前上传文件名已存在，则重命名
            originFilename = System.currentTimeMillis() + "_" + mainname + "." + extName;
        }
        File saveFile = new File(ROOT_PATh + File.separator + originFilename); // D:\vue\files\56526_aaa.png
        file.transferTo(saveFile);//存储到本地磁盘
        String url = "http://" + ip + ":" + port + "/file/download/" + originFilename;
        if ("img".equals(type)){
            return Dict.create().set("errno", 0).set("data", CollUtil.newArrayList(Dict.create().set("url", url)));
        } else if ("video".equals(type)){
            return Dict.create().set("errno", 0).set("data", Dict.create().set("url", url));
        }
        return Dict.create().set("errno", 0);
    }
}
