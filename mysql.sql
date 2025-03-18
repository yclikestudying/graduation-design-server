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
    user_id     bigint                             not null comment '用户id',
    avatar      varchar(1024)                      not null comment '头像地址',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    is_delete   tinyint  default 0                 null comment '0-存在 1-删除'
);

# 关系表
create table if not exists friend
(
    id          bigint auto_increment comment '主键'
        primary key,
    follower_id bigint                             not null comment '关注者id',
    followee_id bigint                             not null comment '被关注者id',
    create_time datetime default CURRENT_TIMESTAMP null comment '建立关系时间',
    is_delete   int      default 0                 null comment '0-存在，1-删除'
)
    comment '关系表';

# 商品表
create table if not exists goods
(
    id              bigint auto_increment comment '主键'
        primary key,
    user_id         bigint                             not null comment '发表用户id',
    goods_content   text                               null comment '商品内容',
    goods_photos    varchar(10240)                     null comment '商品图片',
    goods_old_price int                                null comment '商品原价格',
    goods_price     int                                null comment '商品新价格',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    is_show         tinyint  default 1                 not null comment '1-同意，0-拒绝',
    is_delete       tinyint  default 0                 not null comment '0-存在 1-已删除'
);

# 跑腿服务表
create table if not exists express
(
    id              bigint auto_increment comment '主键'
        primary key,
    user_id         bigint                             not null comment '发表用户id',
    express_content text                               null comment '代取内容文本',
    create_time     datetime default CURRENT_TIMESTAMP null comment '发布时间',
    is_show         tinyint  default 1                 not null comment '1-同意，0-拒绝',
    is_delete       tinyint  default 0                 null comment '0-存在 1-已删除'
);

# 寻物启事表
create table if not exists lost
(
    id               bigint auto_increment comment '主键'
        primary key,
    user_id          bigint                             not null comment '发布用户id',
    lost_type        varchar(20)                        not null comment '丢失物品类型（失物招领、寻物启事）',
    lost_name        varchar(64)                        not null comment '丢失物品名称',
    lost_description varchar(256)                       not null comment '丢失物品描述',
    lost_photo       varchar(1024)                      not null comment '丢失物品图片',
    create_time      datetime default CURRENT_TIMESTAMP null comment '发布时间',
    is_show          tinyint  default 1                 not null comment '1-同意，0-拒绝',
    is_delete        tinyint  default 0                 not null comment '0-存在 1-已删除'
);

CREATE TABLE IF NOT EXISTS activity
(
    id                   BIGINT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    user_id              BIGINT                             NOT NULL COMMENT '发布用户id',
    activity_name        VARCHAR(128)                       NOT NULL COMMENT '活动名称',
    activity_description VARCHAR(1024)                      NOT NULL COMMENT '活动描述',
    create_time          DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '发布时间',
    is_show              TINYINT  DEFAULT 1                 NOT NULL COMMENT '1-显示，0-隐藏',
    is_delete            TINYINT  DEFAULT 0                 NOT NULL COMMENT '0-存在，1-已删除'
) COMMENT '活动表';

CREATE TABLE IF NOT EXISTS activity_relation
(
    id          BIGINT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    activity_id BIGINT NOT NULL COMMENT '活动id',
    user_id     BIGINT NOT NULL COMMENT '用户id',
    join_time   DATETIME DEFAULT CURRENT_TIMESTAMP NULL COMMENT '加入时间',
    is_delete   TINYINT  DEFAULT 0 NOT NULL COMMENT '0-存在，1-已删除',
    UNIQUE KEY uk_activity_user (activity_id, user_id) COMMENT '唯一索引，防止重复加入'
) COMMENT '活动关系表';