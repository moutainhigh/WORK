
/**机构客户银行开户表序列*/
CREATE SEQUENCE SEQ_BIZ_PARTNER_CUSTOMER_BANK;

 

/** 机构客户银行开户表*/
CREATE TABLE BIZ_PARTNER_CUSTOMER_BANK
(
   PID                  INTEGER    PRIMARY KEY,
   PARTNER_NO           VARCHAR2(50),
   ACCT_ID              INTEGER,
   CUSTOMER_ID_CARD     VARCHAR2(50),
   CUSTOMER_NAME        VARCHAR2(50),
   MOBILE_NO            VARCHAR2(50),
   BANK_CARD            VARCHAR2(50),
   BANK_MOBILE_NO       VARCHAR2(50),
   IP                   VARCHAR2(50),
   MAC                  VARCHAR2(100),
   STATUS               INTEGER,
   OPERATOR             INTEGER,
   CREATE_TIME          TIMESTAMP(6),
   UPDATE_TIME          TIMESTAMP(6)
);
/
 
/**添加唯一约束*/
comment on table BIZ_PARTNER_CUSTOMER_BANK is '机构客户银行开户表';
comment on column BIZ_PARTNER_CUSTOMER_BANK.PID is '主键';
comment on column BIZ_PARTNER_CUSTOMER_BANK.PARTNER_NO is '资金机构 ';
comment on column BIZ_PARTNER_CUSTOMER_BANK.ACCT_ID is '客户帐号ID ';
comment on column BIZ_PARTNER_CUSTOMER_BANK.CUSTOMER_ID_CARD is '客户身份证';
comment on column BIZ_PARTNER_CUSTOMER_BANK.CUSTOMER_NAME is '客户姓名';
comment on column BIZ_PARTNER_CUSTOMER_BANK.MOBILE_NO is '客户手机号';
comment on column BIZ_PARTNER_CUSTOMER_BANK.BANK_CARD is '银行卡号';
comment on column BIZ_PARTNER_CUSTOMER_BANK.BANK_MOBILE_NO is '银行预留手机号';
comment on column BIZ_PARTNER_CUSTOMER_BANK.IP is '客户设备IP';
comment on column BIZ_PARTNER_CUSTOMER_BANK.MAC is '客户MAC地址';
comment on column BIZ_PARTNER_CUSTOMER_BANK.STATUS is '绑卡状态 1:成功   2：失败';
comment on column BIZ_PARTNER_CUSTOMER_BANK.OPERATOR is '操作人';
comment on column BIZ_PARTNER_CUSTOMER_BANK.CREATE_TIME is '创建时间';
comment on column BIZ_PARTNER_CUSTOMER_BANK.UPDATE_TIME is '更新时间';
 
 
 