# 考研情报局

## 介绍
考研情报局是一个为考研学生提供全方位服务的平台。它包括院校专业信息查询、考研课程和书籍销售、考研交流论坛等功能，旨在帮助考研学生更好地准备考试。

## 目录
- [介绍](#介绍)
- [背景](#背景)
- [功能](#功能)
- [安装](#安装)
- [使用](#使用)
- [依赖项](#依赖项)
- [配置](#配置)
- [示例](#示例)
- [故障排除](#故障排除)
- [贡献者](#贡献者)
- [许可证](#许可证)

## 背景
本项目由上海理工大学光电信息与计算机工程学院计算机科学与技术2021级学生开发。

本项目部署在腾讯云服务器上，面向用户包括考研学生、网站管理员和商店管理员。考研情报局通过爬虫技术获取考研信息，提供给学生使用。

## 功能
- 院校专业信息查询
- 考研课程和书籍销售
- 考研交流论坛
- 用户登录和注册
- 数据管理和维护

## 安装
1. 克隆项目代码：
    ```bash
    git clone https://github.com/your-repository/kaoyan-qingbaoju.git
    cd kaoyan-qingbaoju
    ```
2. 安装依赖项：
    ```bash
    cd springboot
    ./mvnw install
    cd ../view
    npm install
    ```
3. 配置数据库：
   在`springboot/src/main/resources/application.properties`文件中配置MySQL数据库连接信息。
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/kaoyan
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    ```

4. 运行项目：
    ```bash
    cd springboot
    ./mvnw spring-boot:run
    cd ../view
    npm run serve
    ```

## 使用
1. 用户注册和登录。
2. 浏览院校专业信息，购买考研课程和书籍。
3. 在考研交流论坛中发布帖子和回复其他用户的帖子。

## 依赖项
- Java 8
- Spring Boot
- MySQL
- Vue.js
- 腾讯云服务器

## 配置
1. 数据库配置：
   在`springboot/src/main/resources/application.properties`文件中配置MySQL数据库连接信息。
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/kaoyan
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    ```

2. 服务器配置：
   使用腾讯云服务器部署项目，确保服务器具备以下配置：
    - 处理器：Intel Core i7-14700K
    - 内存：32 GB DDR5 RAM
    - 硬盘：1 TB SSD

## 示例
以下是一个简单的使用示例：
1. 用户注册并登录。
2. 浏览院校信息并选择感兴趣的专业。
3. 购买相关考研课程和书籍。
4. 在论坛中发布和查看帖子。

## 故障排除
1. **数据库连接失败**：
    - 确保MySQL服务正在运行并检查数据库连接信息是否正确。
2. **前端页面无法加载**：
    - 确保前端依赖项已正确安装并且Vue.js服务正在运行。
3. **服务器启动失败**：
    - 检查Spring Boot日志以获取详细错误信息，并确保所有依赖项已正确安装。

## 贡献者
- Jinli03
- yinian3shuhongwentangniaoxizaogou
- ning-hardworking


## 许可证
此项目采用MIT许可证，详见LICENSE文件。
