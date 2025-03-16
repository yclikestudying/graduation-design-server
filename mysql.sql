# 创建用户表
create table if not exists user
(
    id              bigint                             not null primary key auto_increment comment '用户id',
    user_name       varchar(32)                        null comment '用户名称',
    user_phone      varchar(11)                        null comment '用户手机号码',
    user_password   varchar(32)                        null comment '用户密码',
    user_avatar     varchar(1024)                      null comment '用户头像地址',
    user_gender     tinyint                            null comment '0-女 1-男 2-暂无',
    user_birthday   char(10)                           null comment '用户生日',
    user_profile    varchar(64)                        null comment '用户简介',
    user_location   varchar(64)                        null comment '用户所在地',
    user_hometown   varchar(64)                        null comment '用户家乡',
    user_profession varchar(64)                        null comment '用户专业',
    user_tags       varchar(1024)                      null comment '用户标签',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    user_role       varchar(10)                        null comment '用户权限(user,admin)',
    is_delete       tinyint  default 0                 null comment '0-存在 1-删除'
);

# 动态表
create table article
(
    id              bigint auto_increment comment '动态id'
        primary key,
    user_id         bigint                             not null comment '发表用户id',
    article_content text                               null comment '动态内容',
    article_photos  varchar(10240)                     null comment '动态图片',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    is_show         tinyint  default 1                 not null comment '1-同意，0-拒绝',
    is_delete       tinyint  default 0                 null comment '0-存在 1-删除'
);

# 头像表
create table if not exists avatar
(
    id          bigint auto_increment primary key comment '主键',
    user_id     bigint not null comment '用户id',
    avatar      varchar(1024)                      not null comment '头像地址',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    is_delete   tinyint  default 0                 null comment '0-存在 1-删除'
)