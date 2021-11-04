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

CREATE TABLE CUSTOMER(
customer_id varchar2(200) not null,
customer_name VARCHAR2(200) not null,
address varchar2(200) not null,
phno varchar2(200) not null, 
wallet_id varchar2(200) not null)


create table rewards(
reward_cat_code varchar2(200) not null,
brand_id VARCHAR2(200) not null,
datavalue varchar2(200) not null,
no_instances int not null)


create table activities(
act_cat_code varchar2(200) not null,
act_name varchar2(200) not null)

alter table customer add(
constraint cust_pk primary key(customer_id));

alter table customer modify customer_id raw(16);

alter table customer modify customer_id default sys_guid();

create table cust_wallet(
wallet_id varchar2(200),
lp_code varchar2(200),
points_acc varchar2(200),
primary key(wallet_id))

alter table customer add(
constraint wallet_id_fk foreign key(wallet_id) references cust_wallet(wallet_id));


CREATE TABLE BRAND(
brand_id varchar2(200) not null,
brand_name varchar2(200) not null,
join_date DATE not null,
address varchar2(200) not null, 
lp_code varchar2(200) not null,
lp_name varchar2(200) not null,
tier1 varchar2(200),
tier2 varchar2(200),
tier3 varchar2(200),
mult1 int,
mult2 int,
mult3 int,
PRIMARY KEY(brand_id)
)

alter table activities add(
constraint act_cat_code_pk primary key(act_cat_code));

alter table rewards add(
constraint reward_cat_code_pk primary key(reward_cat_code));

alter table rewards add(
constraint brand_id_fk foreign key(brand_id) references brand(brand_id));

CREATE TABLE RE_Rules(
re_rule_code varchar2(200) not null,
brand_id VARCHAR2(200) not null,
act_cat_code varchar2(200) not null,
rule_version int not null, 
points int not null,
primary key(re_rule_code,rule_version),
foreign key(brand_id) references brand(brand_id)
)

alter table re_rules add(
constraint act_cat_code_fk foreign key(act_cat_code) references activities(act_cat_code));

CREATE TABLE RR_Rules(
rr_rule_code varchar2(200) not null,
brand_id VARCHAR2(200) not null,
reward_cat_code varchar2(200) not null,
rule_version int not null, 
points int not null,
primary key(rr_rule_code,rule_version),
foreign key(brand_id) references brand(brand_id),
foreign key(reward_cat_code) references rewards(reward_cat_code)
)

create table customer_activity_log(
act_cat_code varchar2(200) not null,
cust_id raw(16),
datavalue varchar2(200) not null,
date_of_activity date not null,
foreign key(act_cat_code) references activities(act_cat_code),
foreign key(cust_id) references customer(customer_id))

create table customer_reward_log(
reward_cat_code varchar2(200) not null,
cust_id raw(16),
datavalue varchar2(200) not null,
date_of_reward date not null,
foreign key(reward_cat_code) references rewards(reward_cat_code),
foreign key(cust_id) references customer(customer_id))