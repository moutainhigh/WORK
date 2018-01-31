alter table biz_project
add (
   ORG_ID               INTEGER,
   ORG_CUSTOMER_NAME    VARCHAR2(255),
   ORG_CUSTOMER_PHONE   VARCHAR2(255),
   ORG_CUSTOMER_CARD    VARCHAR2(255),
   PLAN_LOAN_DATE       TIMESTAMP,
   PLAN_LOAN_MONEY      NUMBER,
   LOAN_RATE            NUMBER,
   MAX_LOAN_RATE        NUMBER,
   IS_CLOSED            INTEGER  default 2,
   APPLY_STATUS         INTEGER default 10,
   AREA_CODE			VARCHAR2(255)
);
comment on column BIZ_PROJECT.PROJECT_TYPE is
'项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5，6机构提交业务）';
comment on column BIZ_PROJECT.ORG_ID is
'机构ID';

comment on column BIZ_PROJECT.ORG_CUSTOMER_NAME is
'客户姓名';

comment on column BIZ_PROJECT.ORG_CUSTOMER_PHONE is
'客户电话';

comment on column BIZ_PROJECT.ORG_CUSTOMER_CARD is
'客户身份证';

comment on column BIZ_PROJECT.PLAN_LOAN_DATE is
'希望放款日期';

comment on column BIZ_PROJECT.PLAN_LOAN_MONEY is
'借款金额';

comment on column BIZ_PROJECT.LOAN_RATE is
'借款利率';

comment on column BIZ_PROJECT.MAX_LOAN_RATE is
'最高承受利率';

comment on column BIZ_PROJECT.IS_CLOSED is
'是否关闭1、关闭2、正常';
comment on column BIZ_PROJECT.apply_status
  is '业务申请状态:10、待客户经理提交20、待风控初审
 30、待风控复审40、待风控终审50、待总经理审批60、已审批70、已放款80、已回款90、已结算100、已归档';
 comment on column BIZ_PROJECT.area_code
  is '业务申请所属城市编码';
  
  --公司表中添加机构ID
alter table CUS_COM_BASE
add (
  ORG_ID   INTEGER,
  COM_OWN_NAME         VARCHAR2(255),
  COM_OWN_PHONE        VARCHAR2(255),
  COM_OWN_CARD         VARCHAR2(255)
);
comment on column CUS_COM_BASE.ORG_ID is
'机构ID';

comment on column CUS_COM_BASE.COM_OWN_NAME is
'法人';

comment on column CUS_COM_BASE.COM_OWN_PHONE is
'手机号码';

comment on column CUS_COM_BASE.COM_OWN_CARD is
'身份证号码';