--数据字典添加关系人类型,添加内外单数据

alter table CUS_PERSON 
add (
	PROPORTION_PROPERTY  NUMBER
);
comment on column CUS_PERSON.PROPORTION_PROPERTY is
'产权占比';

alter table BIZ_PROJECT_FORECLOSURE
add (
  new_rece_account   VARCHAR2(255),
   new_rece_name      VARCHAR2(255),
   new_rece_bank      VARCHAR2(255),
   supersion_rece_account VARCHAR2(255),
   supersion_rece_name VARCHAR2(255),
   supersion_rece_bank VARCHAR2(255)
);

comment on column BIZ_PROJECT_FORECLOSURE.OLD_OWED_AMOUNT is
'原贷款欠款金额';

comment on column BIZ_PROJECT_FORECLOSURE.OLD_LOAN_TIME is
'原贷款结束时间';

comment on column BIZ_PROJECT_FORECLOSURE.FORE_ACCOUNT is
'赎楼账号';

comment on column BIZ_PROJECT_FORECLOSURE.SUPERVISE_ACCOUNT is
'监管账号';

comment on column BIZ_PROJECT_FORECLOSURE.THIRD_BORROWER is
'第三人借款人';

comment on column BIZ_PROJECT_FORECLOSURE.THIRD_BORROWER_CARD is
'第三人借款人身份证号';

comment on column BIZ_PROJECT_FORECLOSURE.THIRD_BORROWER_PHONE is
'第三人借款人手机号码';

comment on column BIZ_PROJECT_FORECLOSURE.THIRD_BORROWER_ADDRESS is
'第三人借款人地址';
comment on column BIZ_PROJECT_FORECLOSURE.new_rece_account is
'新贷款收款账号';

comment on column BIZ_PROJECT_FORECLOSURE.new_rece_name is
'新贷款收款户名';

comment on column BIZ_PROJECT_FORECLOSURE.new_rece_bank is
'新贷款收款开户行';

comment on column BIZ_PROJECT_FORECLOSURE.supersion_rece_account is
'资金监管收款账号';

comment on column BIZ_PROJECT_FORECLOSURE.supersion_rece_name is
'资金监管收款户名';

comment on column BIZ_PROJECT_FORECLOSURE.supersion_rece_bank is
'资金监管收款开户行';

alter table CUS_PER_CREDIT
add (
  	CREDIT_LIMIT         NUMBER,
    CREDIT_USED_LIMIT    NUMBER
);

comment on column CUS_PER_CREDIT.CREDIT_LIMIT is
'信用卡授信额度';

comment on column CUS_PER_CREDIT.CREDIT_USED_LIMIT is
'银行信用卡已用额度';

   
alter table BIZ_PROJECT
add (
 AUDITOR_OPINION       VARCHAR2(2048),
 IS_SELLER       INTEGER,
 DECLARATION VARCHAR2(255)
);
comment on column BIZ_PROJECT.DECLARATION is
'报单员';
comment on column BIZ_PROJECT.IS_SELLER is
'是否为买卖家';
comment on column BIZ_PROJECT.AUDITOR_OPINION is
'审查员意见';

comment on column BIZ_PROJECT.BUSINESS_SOURCE_NO is
'业务来源数据';

comment on column BIZ_PROJECT.COLLECT_FILE_STATUS is
'收件状态';

comment on column BIZ_PROJECT.PROJECT_SOURCE is
'项目来源';

alter table BIZ_PROJECT_GUARANTEE 
add(
      FEE_RATE  NUMBER
);
comment on column BIZ_PROJECT_GUARANTEE.FEE_RATE is
'费率';
alter table BIZ_PROJECT_PROPERTY 
add(
     PURPOSE VARCHAR2(500)
);
 comment on column BIZ_PROJECT_PROPERTY.PURPOSE is
'用途';

create table sys_adv_pic_info 
(
   pid                NUMBER   not null,
   title             VARCHAR2(500),
   content           VARCHAR2(2048),
   URL               VARCHAR2(500),
   org_id            NUMBER,
   create_id         NUMBER,
   create_date       TIMESTAMP,
   status            NUMBER,
   remark            VARCHAR2(2048),
   picture_url       VARCHAR2(500),
   order_index INTEGER,
   constraint PK_SYS_ADV_PIC_INFO primary key (pid)
);

comment on table sys_adv_pic_info is
'广告信息';

comment on column sys_adv_pic_info.pid is
'广告编号';

comment on column sys_adv_pic_info.title is
'广告标题';

comment on column sys_adv_pic_info.content is
'广告内容';

comment on column sys_adv_pic_info.URL is
'URL';

comment on column sys_adv_pic_info.org_id is
'城市id';

comment on column sys_adv_pic_info.create_id is
'创建人';

comment on column sys_adv_pic_info.create_date is
'创建日期';

comment on column sys_adv_pic_info.status is
'状态';

comment on column sys_adv_pic_info.remark is
'备注';

comment on column sys_adv_pic_info.picture_url is
'图片地址';
comment on column SYS_ADV_PIC_INFO.order_index is
'排序位置';
create sequence SEQ_SYS_ADV_PIC_INFO
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;
-- 系统银行列表
create table SYS_BANK_INFO 
(
   PID                  INTEGER              not null,
   PARENT_ID            INTEGER,
   BANK_NAME            VARCHAR2(255),
   TYPE                 INTEGER,
   BANK_SHORTHAND       VARCHAR2(255),
   status         INTEGER,
   constraint PK_SYS_BANK_INFO primary key (PID)
);

comment on table SYS_BANK_INFO is
'系统银行分行表';

comment on column SYS_BANK_INFO.PID is
'主键';

comment on column SYS_BANK_INFO.PARENT_ID is
'从属银行ID';

comment on column SYS_BANK_INFO.BANK_NAME is
'银行名称';

comment on column SYS_BANK_INFO.TYPE is
'类型';

comment on column SYS_BANK_INFO.BANK_SHORTHAND is
'银行简称';
comment on column SYS_BANK_INFO.STATUS
  is '状态，1：显示2：不显示';
create sequence SEQ_SYS_BANK_INFO
minvalue 1
maxvalue 9999999999999999999999999999
start with 1000
increment by 1
cache 20;

--修改买卖方电话数据类型
alter table biz_project_property rename column BUYER_PHONE to BUYER_PHONE_one;
alter table biz_project_property add BUYER_PHONE varchar2(255);
update biz_project_property set BUYER_PHONE=trim(BUYER_PHONE_one);
alter table biz_project_property drop column BUYER_PHONE_one;

alter table biz_project_property rename column SELLER_PHONE to SELLER_PHONE_one;
alter table biz_project_property add SELLER_PHONE varchar2(255);
update biz_project_property set SELLER_PHONE=trim(SELLER_PHONE_one);
alter table biz_project_property drop column SELLER_PHONE_one;


--添加支行字段
alter table BIZ_PROJECT_FORECLOSURE
add (
 OLD_LOAN_BANK_BRANCH       VARCHAR2(255),
 NEW_LOAN_BANK_BRANCH       VARCHAR2(255),
 SUPERVISE_DEPARTMENT_BRANCH VARCHAR2(255)
);

comment on column BIZ_PROJECT_FORECLOSURE.OLD_LOAN_BANK_BRANCH is
'旧贷款支行';

comment on column BIZ_PROJECT_FORECLOSURE.NEW_LOAN_BANK_BRANCH is
'新贷款支行';

comment on column BIZ_PROJECT_FORECLOSURE.SUPERVISE_DEPARTMENT_BRANCH is
'监管银行支行';


insert into sys_permission (PID, PERMIS_TYPE, PERMIS_NAME, PERMIS_DESC, PERMIS_CODE, MENU_ID, STATUS)
values (SEQ_SYS_PERMISSION.NEXTVAL, 'CDM', '新增、编辑贷款信息', '客户经理可以新增和编辑贷前申请信息', 'ADD_UPDATE_FORE', '0', '1');

insert into sys_permission (PID, PERMIS_TYPE, PERMIS_NAME, PERMIS_DESC, PERMIS_CODE, MENU_ID, STATUS)
values (SEQ_SYS_PERMISSION.NEXTVAL, 'CDM', '修改贷前申请信息', '风控总监以及执行岗可以在放款前修改贷前信息', 'UPDATE_FORE', '0', '1');

insert into sys_permission (PID, PERMIS_TYPE, PERMIS_NAME, PERMIS_DESC, PERMIS_CODE, MENU_ID, STATUS)
values (SEQ_SYS_PERMISSION.NEXTVAL, 'CDM', '编辑审查员意见', '审查员编辑审查员意见', 'ADD_UPDATE_AUDITOR', '0', '1');
insert into sys_permission (PID, PERMIS_TYPE, PERMIS_NAME, PERMIS_DESC, PERMIS_CODE, MENU_ID, STATUS)
values (SEQ_SYS_PERMISSION.NEXTVAL, 'CDM', '要件修改', null, 'ADD_UPDATE_ELEMENT', '0', '1');

insert into sys_permission (PID, PERMIS_TYPE, PERMIS_NAME, PERMIS_DESC, PERMIS_CODE, MENU_ID, STATUS)
values (SEQ_SYS_PERMISSION.NEXTVAL, 'CDM', '要件归还', null, 'RETURN_ELEMENT', '0', '1');

alter table BIZ_PROJECT_EXTENSION 
add (
  EXTENSION_DAYS  INTEGER
);
comment on column BIZ_PROJECT_EXTENSION.EXTENSION_DAYS is
'展期天数';
