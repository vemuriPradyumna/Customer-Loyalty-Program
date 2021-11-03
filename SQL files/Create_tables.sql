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
