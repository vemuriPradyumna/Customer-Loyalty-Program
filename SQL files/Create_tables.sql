create table admin(
user_id varchar2(200) NOT NULL,
pwd varchar2(200) NOT NULL)


create table market_place(
user_id varchar2(200) primary key,
pwd varchar2 (200) NOT NULL,
user_type varchar2(200) NOT NULL)


select table_name from user_tables;
select * from admin;
select * from market_place;

create table wallet_table(
wallet_id varchar2(200),
lyl_prgm_code varchar2(200),
points_acc varchar2(200),
primary key(wallet_id,lyl_prgm_code))

CREATE TABLE CUSTOMER(
customer_id varchar2(200) not null,
customer_name VARCHAR2(200) not null,
address varchar2(200) not null,
phno varchar2(200) not null, 
wallet_id varchar2(200) not null)

CREATE TABLE BRAND(
brand_id varchar2(200) not null,
lp_code VARCHAR2(200) not null,
join_date DATE not null,
address varchar2(200) not null, 
brand_name varchar2(200) not null)

CREATE TABLE loyalty_program(
brand_id varchar2(200) not null,
level_no VARCHAR2(200) not null,
multiplier INT not null)

CREATE TABLE RE_Rules(
re_rule_code varchar2(200) not null,
brand_id VARCHAR2(200) not null,
act_cat_code varchar2(200) not null,
rule_version int not null, 
points int not null)

create table RR_Rules(
rr_rule_code varchar2(200) not null,
brand_id VARCHAR2(200) not null,
reward_cat_code varchar2(200) not null,
rule_version int not null, 
points int not null)

create table rewards(
reward_cat_code varchar2(200) not null,
brand_id VARCHAR2(200) not null,
datavalue varchar2(200) not null,
no_instances int not null)

create table customer_reward_log(
reward_cat_code varchar2(200) not null,
cust_id VARCHAR2(200) not null,
datavalue varchar2(200) not null,
date_of_reward date not null)

create table activities(
act_cat_code varchar2(200) not null,
act_name varchar2(200) not null)

create table customer_activity_log(
act_cat_code varchar2(200) not null,
cust_id VARCHAR2(200) not null,
datavalue varchar2(200) not null,
date_of_activity date not null)