/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/4 20:36:31                            */
/*==============================================================*/


drop table if exists goods;

drop table if exists miusa_goods;

drop table if exists `order`;

drop table if exists order_info;

drop table if exists user;

/*==============================================================*/
/* Table: goods                                                 */
/*==============================================================*/
create table goods
(
   id                   bigint not null auto_increment,
   goods_name            varchar(512),
   goods_title           varchar(512),
   goods_img             varchar(512),
   goods_detail          text,
   goods_price           double,
   goods_stock           int,
   primary key (id)
);

/*==============================================================*/
/* Table: miusa_goods                                            */
/*==============================================================*/
create table miusa_goods
(
   id                   bigint not null auto_increment,
   goods_id              bigint,
   stock_count           int,
   start_date            datetime,
   end_date              datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: `order`                                               */
/*==============================================================*/
create table `order`
(
   id                   bigint not null auto_increment,
   user_id               bigint,
   order_id              bigint,
   goods_id              bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: order_info                                             */
/*==============================================================*/
create table order_info
(
   id                   bigint not null auto_increment,
   user_id               bigint,
   goods_id              bigint,
   delivery_addr_id       bigint,
   goods_name            varchar(512),
   goods_count           int,
   goods_price           double,
   order_channel         int,
   status               int,
   create_date           datetime,
   pay_date              datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   bigint not null auto_increment,
   nickname             varchar(512),
   password             varchar(512),
   salt                 varchar(256),
   head                 varchar(512),
   register_date         datetime,
   last_login_date        datetime,
   login_count           int,
   primary key (id)
);

