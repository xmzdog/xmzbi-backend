<p align="center">
   基于Spring Boot + MQ + AIGC 的智能数据分析平台
</p>

<p align="center">
   <a target="_blank" href="https://github.com/ttkican/Blog">
      <img src="https://img.shields.io/badge/JDK-8-green"/>
      <img src="https://img.shields.io/badge/springboot-2.7.2-green"/>
      <img src="https://img.shields.io/badge/react-green"/>
      <img src="https://img.shields.io/badge/mysql-8.0-green"/>
      <img src="https://img.shields.io/badge/mybatis--plus-3.5.2-green"/>
      <img src="https://img.shields.io/badge/redis-7.0.12-green"/>
      <img src="https://img.shields.io/badge/nacos-2.2.3-green"/>
   </a>
</p>

## 项目介绍
区别于传统 BI，用户只需要导入原始数据集、并输入分析需求，就能自动生成可视化图表及分析结论，实现数据分析的将本增效。

## 在线地址

**项目链接：** 待上线

**测试账号：** xmz66，**密码**：12345678

**Github 地址：** [https://github.com/xmzdog/xmz-api-backend.git](https://github.com/xmzdog/xmzbi-backend)

**作者：** Xiangmz 

## 本地运行
1. MySQL版本为`8.0`，node版本为`v16.19.1`
2. SQL 文件位于后端根目录下的`ddl.sql`，将其中的数据导入到自己本地数据库中
3. 修改后端配置文件中的数据库等连接信息.
4. 项目启动后，使用`xmz66`管理员账号登录，密码为`12345678`

## 项目特点
**后端**
1. 后端自定义Prompt预设模板并封装用户输入的数据和分析诉求,通过对接AIGC接口生成可视化图表json配置和分析结论,返回给前端渲染。
2. 由于AIGC的输入Token限制,使用Easy Excel解析用户上传的XLSX表格数据文件并压缩为CSV ,实测提高了20%的单次输入数据量并节约了成本。
3. 为保证系统的安全性,对用户上传的原始数据文件进行了后缀名、大小内容等多重校验.
4. 为防止某用户恶意占用系统资源,基纡Redisson的RateLimiter实现分布式限流,控制单用户访问的频率。
5. 考虑到单个图表的原始数据量较大,基纡MyBatis +业务层构建自定义SQL实现了对每份原始数据的分表存储,提高查询性能和系统的可扩展性.
6. 由于AIGC的响应时间较长,基于自定义IO密集型线程池 +任务队列实现了AIGC的并发执行和异步化,提交任务后即可响应前端,提高用户体验。
7. 由于本地任务队列重启丢失数据,使用RabbitMQ (分布式消息队列)来接受并持久化任务消息,通过Direct交换机转发给解耦的AI生成模块消费并处理任务,提高了系统的可靠性。

 **前端**
 
 前端使用Ant Design Pro 脚手架搭建初始项目，用React 进行业务开发，使用Umi OpenAPI 插件根据后端Swagger接口文档生成代码来提高效率，使用Echarts库渲染可视化图表。

## 技术介绍

**前端：** React + Ant Design pro +Echarts + OpenApi插件

**后端：** SpringBoot + Mysql + Redis + Nacos + Swagger2&knif4j + MyBatisPlus + RabbitMQ

## 运行环境

**服务器：** XXXX
**对象存储：** XXXXX

## 架构图
![架构图](https://github.com/xmzdog/xmzbi-backend/assets/137482123/ca623620-78ea-45a8-a1dc-81e096cc8211)
![业务流程图](https://github.com/xmzdog/xmzbi-backend/assets/137482123/def8ba51-41b0-4405-a45b-b27cf3f80537)


## 项目截图
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/2c68fab7-a95f-406c-a7d8-c64e805e34f2)
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/34506240-3035-41b9-9423-0dc19138f88b)
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/f88cb85b-a73c-4733-9439-d3db33fee63a)
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/c092f035-7a14-4ccc-8a90-e2f26ec9b635)


