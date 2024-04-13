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

**项目链接：** http://192.144.225.240:3001/

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
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/59820098-da22-4745-957c-6e3abe150f26)


## 项目部署
**使用Docker部署前后端项目**

1、安装Docker
```java
// 先将依赖的环境全部下载 
yum -y install yum-utils device-mapper-persistent-data lvm2
// 默认下载Docker会去国外服务器下载，速度较慢，我们可以设置为阿里云镜像源，速度更快
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
// 更新 yum 软件源缓存，并安装 docker-ce
yum install docker-ce docker-ce-cli containerd.io
// 启动 docke 并设置开机启动
systemctl start docker
systemctl enable docker
// Docker 配置阿里云镜像加速器
vim /etc/docker/daemon.json
// 加入下面配置， 登录阿里云，获取自己的镜像加速器，这是我的镜像加速器地址。
// 阿里云镜像加速器地址： https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors
{
  "registry-mirrors": ["https://bomyw6lm.mirror.aliyuncs.com"]
}
```

2、安装 MySQL
```java
// 拉取MySQL镜像
docker pull mysql:8.0.20
// 运行 MySQL 容器
docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password  -d mysql:8.0.20
// 进入到 MySQL 容器内
docker exec -it docker-mysql bash
// 设置用户权限，完成远程连接
mysql -u root -p
grant all privileges on *.* to 'root'@'%';
flush privileges;
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '密码';
```

3、安装Redis
```java
// 拉取Redis镜像，并运行Redis 容器
docker run --restart=always -p 6379:6379 --name myredis -d redis:7.0.12  --requirepass xmzpassword
```

4、下载安装Maven
```java
// 一键下载maven
wget http://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
// 解压
tar -zxvf apache-maven-3.8.4-bin.tar.gz
// 配置环境变量，编辑/etc/profile, 配置MVN_HOME环境变量
mv apache-maven-3.8.4 /usr/local/apache-maven-3.8.4
vim /etc/profile
// 添加下面变量到最后一行
export MVN_HOME=/usr/local/apache-maven-3.8.4
export PATH=$MVN_HOME/bin:$PATH
```

5、安装JDK
```java
// 一键安装
yum install java-1.8.0-openjdk-devel.x86_64
// 查看是否安装成功
java -version
java
javac
```

7、创建部署后端项目的DockerFile
```java
// 创建存放后端项目的文件夹
mkdir -p /usr/docker/backend
// 创建 Dockerfile
vim Dockerfile
// 下面是 Dockerfile 文件内容
FROM openjdk:8-jre //从 Docker Hub 上拉取带有 Java 8 运行环境（Java Runtime Environment, JRE）的官方 OpenJDK 镜像。这意味着你的容器将基于 Java 8 运行环境。

ENV PORT=8101   // 后端项目运行的端口号
EXPOSE ${PORT}   //Docker 在运行容器时开放 8101 端口
RUN mkdir /usr/local/work   //在容器内部创建一个目录。这个目录将用作工作目录，用于存放应用程序文件。
WORKDIR /usr/local/work   //设置容器内的当前工作目录。后续的 COPY 或 RUN 命令都会在这个目录下执行。
COPY xmzbi-0.0.1-SNAPSHOT.jar ./app.jar   //将宿主机（你构建 Docker 镜像的机器）当前目录下的 xmzbi-0.0.1-SNAPSHOT.jar 文件复制到容器的当前工作目录，并重命名为 app.jar。
ENTRYPOINT java -jar ./app.jar --server.port=${PORT}   //定义了容器启动后执行的命令。使用 java -jar 命令启动名为 app.jar 的 Java 应用，并通过 --server.port=${PORT} 参数指定应用监听的端口为环境变量 PORT 所设置的值（8101）。
```

8、打包后端项目并上传到后端Dockerfile文件所在的目录
```java
// 在 /usr/docker/backend 路径下
// rz 上传后端项目
rz
// 将后端项目打包成镜像
docker build -t xmzbi .
// 通过镜像运行后端项目容器
docker run -d -p 8101:8101 --name xmzbi0.1 xmzbi
// 查看后端项目运行日志
docker logs xmzbi0.1
```

9、创建前端项目的Dockerfile
```java
// 创建存放前端项目的文件夹
mkdir -p /usr/docker/frontend
// 创建 Dockerfile
vim Dockerfile
// 下面是 Dockerfile 文件内容
FROM nginx:latest    // 从 Docker Hub 上拉取最新版本的官方 Nginx 镜像作为基础镜像

RUN mkdir /dist   // 在容器内部创建一个名为 /dist 的目录。这个目录将用于存放你希望在容器中使用的文件或数据。
COPY ./dist /dist   // 将宿主机（你构建 Docker 镜像的机器）中当前目录下的 dist 目录中的内容复制到容器内的 /dist 目录中。
COPY ./nginx.conf.template /   // 将宿主机中当前目录下的 nginx.conf.template 文件复制到容器的根目录。这个文件是一个 Nginx 配置文件模板
 
CMD envsubst < /nginx.conf.template > /etc/nginx/nginx.conf \   
	&& cat /etc/nginx/nginx.conf \
	&& nginx -g 'daemon off;'
// 这一行定义了容器启动后将执行的命令：
//envsubst < /nginx.conf.template > /etc/nginx/nginx.conf：使用 envsubst 命令处理 /nginx.conf.template 文件，替换其中的环境变量，然后将结果输出到 /etc/nginx/nginx.conf。这样，Nginx 就会使用这个新生成的配置文件。
//&& cat /etc/nginx/nginx.conf：通过 cat 命令打印出 /etc/nginx/nginx.conf 文件的内容，主要是用于调试，以确保配置文件的内容是正确的。
//&& nginx -g 'daemon off;'：最后，运行 Nginx 服务器，并通过 -g 'daemon off;' 参数确保 Nginx 在前台运行，这是在 Docker 容器中运行服务的推荐方式（而不是作为后台服务）。
```

10、在前端项目Dockerfile 文件所在目录中，创建nginx.conf.template 文件,内容为
```java
user nginx;
 
#user  nobody;
worker_processes  1;
 
#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;
 
#pid        logs/nginx.pid;
 
 
events {
    worker_connections  1024;
}
 
 
http {
    include       mime.types;
    default_type  application/octet-stream;
 
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';
 
    #access_log  logs/access.log  main;
 
    sendfile        on;
    #tcp_nopush     on;
 
    #keepalive_timeout  0;
    keepalive_timeout  65;
 
    #gzip  on;
 
    server {
        listen       80;
        server_name  localhost;
 
        #charset koi8-r;
 
        #access_log  logs/host.access.log  main;
 
        location / {
            root   /dist;
			#try_files $uri /index.html; #解决路由重定向跳转 404 页面配置
            index  index.html index.htm;
        }
 
        #error_page  404              /404.html;
 
        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
 
 
    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;
 
    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
 
 
    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;
 
    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;
 
    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;
 
    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;
 
    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
 
}
```

11、打包前端项目压缩后放在/usr/docker/frontend 文件夹下,并运行启动前端项目
```java
// 到这个目录中
cd /usr/docker/frontend
// 构建镜像
docker build -f Dockerfile -t bitest1 .
// 设置容器运行镜像
docker run -d --name bitest -p 3000:80 --restart=always bitest1
```

## 项目截图
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/2c68fab7-a95f-406c-a7d8-c64e805e34f2)
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/34506240-3035-41b9-9423-0dc19138f88b)
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/f88cb85b-a73c-4733-9439-d3db33fee63a)
![image](https://github.com/xmzdog/xmzbi-backend/assets/137482123/c092f035-7a14-4ccc-8a90-e2f26ec9b635)


