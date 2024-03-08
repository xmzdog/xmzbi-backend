# 数据库初始化
# @author <a href="https://github.com/xmzdog/xmzbi-backend">程序员xmz</a>
#

-- 创建库
create database if not exists my_bi_db;

-- 切换库
use my_bi_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
    ) comment '用户' collate = utf8mb4_unicode_ci;

-- auto-generated definition
create table chart
(
    id         bigint                             not null comment 'id
'
        primary key,
    goal       text                               null comment '分析目标
',
    chartData  text                               null comment '图表数据',
    `name`      varchar(256)                       null comment '图表名称',
    chartType  varchar(256)                       null comment '图表类型',
    genChart   text                               null comment '生成的表数据',
    genResult  text                               null comment '生成的分析结论',
    status      varchar(128)                        not null default 'wait' comment 'wait,running,succeed,failed',
    executeMessage  text                            null comment '执行信息',
    creatTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    userId     bigint                             null comment '创建用户 id'
)
    comment '图表信息表' collate = utf8mb4_unicode_ci;



