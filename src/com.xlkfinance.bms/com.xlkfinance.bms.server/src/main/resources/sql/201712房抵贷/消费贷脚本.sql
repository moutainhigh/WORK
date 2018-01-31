comment on column BMS_INLOAN_TABLE.BIZ_PROJECT.PROJECT_TYPE is
'项目类型（赎楼贷款=2、贷款展期=4 ,6机构提交业务 8=抵押贷,10=消费贷）';
/**
 * 项目联系人信息
 */
create table BIZ_PROJECT_CONTACTS 
(
   PID                  INTEGER              not null,
   PROJECT_ID           NUMBER,
   ACCT_ID              NUMBER,
   CONTACTS_NAME        VARCHAR2(255),
   CONTACTS_PHONE       VARCHAR2(55),
   CONTACTS_RALATION    VARCHAR2(55),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_BIZ_PROJECT_CONTACTS primary key (PID)
);

comment on table BIZ_PROJECT_CONTACTS is
'项目联系人信息';

comment on column BIZ_PROJECT_CONTACTS.PID is
'主键';

comment on column BIZ_PROJECT_CONTACTS.PROJECT_ID is
'项目ID';

comment on column BIZ_PROJECT_CONTACTS.ACCT_ID is
'客户ID';

comment on column BIZ_PROJECT_CONTACTS.CONTACTS_NAME is
'联系人姓名';

comment on column BIZ_PROJECT_CONTACTS.CONTACTS_PHONE is
'联系人电话';

comment on column BIZ_PROJECT_CONTACTS.CONTACTS_RALATION is
'联系人关系';

comment on column BIZ_PROJECT_CONTACTS.CREATE_DATE is
'创建时间';

comment on column BIZ_PROJECT_CONTACTS.CREATER_ID is
'创建人ID';

comment on column BIZ_PROJECT_CONTACTS.UPDATE_ID is
'更新人ID';

comment on column BIZ_PROJECT_CONTACTS.UPDATE_DATE is
'更新时间';


ALTER TABLE CUS_CREDENTIALS ADD (
   CUSTOMER_NATURE      VARCHAR2(55),
   HOUSE_NAME           VARCHAR2(255),
   LAND_NATURE          VARCHAR2(255),
   ESTATE_USE           VARCHAR2(255),
   IS_CALL_PERSON       INTEGER,
   IS_CALL_UNIT         INTEGER,
   IS_CALL_CONTACT      INTEGER,
   MONTHLY_INCOME       NUMBER(12,2),
   MONTHLY_RETURN       NUMBER(12,2),
   DEBT_RADIO           NUMBER(12,2),
   LEASE_TERM           NUMBER(12,2),
   TRIAL_QUOTA          NUMBER(12,2),
   LOAN_MONTHLY_RETURN  NUMBER(12,2)
   RISK_ONE_OPINION     NVARCHAR2(2000),
   RISK_OVER_OPINION    NVARCHAR2(2000),
   EMPLOY_SITUATION     VARCHAR2(255)
   );
 
comment on column CUS_CREDENTIALS.CUSTOMER_NATURE is
'客户性质';

comment on column CUS_CREDENTIALS.HOUSE_NAME is
'房产名称';

comment on column CUS_CREDENTIALS.LAND_NATURE is
'土地性质';

comment on column CUS_CREDENTIALS.ESTATE_USE is
'房产用途';

comment on column CUS_CREDENTIALS.IS_CALL_PERSON is
'是否致电个人';

comment on column CUS_CREDENTIALS.IS_CALL_UNIT is
'是否致电单位';

comment on column CUS_CREDENTIALS.IS_CALL_CONTACT is
'是否致电联系人';

comment on column CUS_CREDENTIALS.MONTHLY_INCOME is
'核定月收入';

comment on column CUS_CREDENTIALS.MONTHLY_RETURN is
'月还款额(无抵/质押贷款)';

comment on column CUS_CREDENTIALS.DEBT_RADIO is
'收入负债比';

comment on column CUS_CREDENTIALS.LEASE_TERM is
'租赁期';

comment on column CUS_CREDENTIALS.TRIAL_QUOTA is
'初审额度';

comment on column CUS_CREDENTIALS.LOAN_MONTHLY_RETURN is
'贷款月还款额';
comment on column CUS_CREDENTIALS.RISK_ONE_OPINION is
'风控初审意见';
comment on column CUS_CREDENTIALS.RISK_OVER_OPINION is
'风控复审意见';
comment on column CUS_CREDENTIALS.EMPLOY_SITUATION is
'雇佣情况';
ALTER TABLE BIZ_SPOT_INFO ADD (
   LEASE_USE_PHONE      VARCHAR2(55),
   RENT_PRICE           NUMBER(12,2),
   RENT_EVALUATION_PRICE NUMBER(12,2),
   EVALUATION_SOURCE    VARCHAR2(255),
   EVALUATION_SOURCE_DETAIL VARCHAR2(255),
   VACANT_DAYS          NUMBER(12,2),
   VACANT_RATE          NUMBER(12,2));

   comment on column BIZ_SPOT_INFO.HOUSE_PATTERN_DETAIL is
'房产格局详情（几房几厅或者商铺等信息）';

comment on column BIZ_SPOT_INFO.LEASE_USE_PHONE is
'承租人电话';

comment on column BIZ_SPOT_INFO.RENT_PRICE is
'已租价格';

comment on column BIZ_SPOT_INFO.RENT_EVALUATION_PRICE is
'租金评估价';

comment on column BIZ_SPOT_INFO.EVALUATION_SOURCE is
'评估价格来源';

comment on column BIZ_SPOT_INFO.EVALUATION_SOURCE_DETAIL is
'评估价格来源详情';

comment on column BIZ_SPOT_INFO.VACANT_DAYS is
'空置天数';

comment on column BIZ_SPOT_INFO.VACANT_RATE is
'空置率';
comment on column BIZ_SPOT_INFO.RENT_PAYMENT_WAY is
'租金缴纳方式（压几付几）';

ALTER TABLE BIZ_PROJECT_ESTATE ADD (
	HOUSE_RENT           NUMBER(12,2));
	
comment on column BIZ_PROJECT_ESTATE.HOUSE_RENT is
'物业租金';

ALTER TABLE BIZ_PROJECT_PROPERTY ADD (
	HOUSE_RENT_TOTAL           NUMBER(12,2));
	
comment on column BIZ_PROJECT_PROPERTY.HOUSE_RENT_TOTAL is
'物业总租金';



ALTER TABLE BIZ_PROJECT_GUARANTEE ADD (
	RENT_RETRIAL_PRICE  NUMBER(12,2),
   CONTRACT_PRICE       NUMBER(12,2),
   DEBT_RADIO           NUMBER(12,2),
   LEASE_TERM           NUMBER(12,2),
   MONTHLY_RETURN_MONEY       NUMBER(12,2),
   LOAN_RADIO           NUMBER(12,2),
   ESTIMATE_MONTHLY_INCOME       NUMBER(12,2)
   );

comment on column BIZ_PROJECT_GUARANTEE.RENT_RETRIAL_PRICE is
'租金复审价格';

comment on column BIZ_PROJECT_GUARANTEE.CONTRACT_PRICE is
'租物业最终签约价格';

comment on column BIZ_PROJECT_GUARANTEE.DEBT_RADIO is
'复审收入负债比';

comment on column BIZ_PROJECT_GUARANTEE.LEASE_TERM is
'租赁期限';

comment on column BIZ_PROJECT_GUARANTEE.MONTHLY_RETURN_MONEY is
'复审月还款额';

comment on column BIZ_PROJECT_GUARANTEE.LOAN_RADIO is
'贷款系数';

comment on column BIZ_PROJECT_GUARANTEE.LOAN_MONEY is
'贷款金额（借款金额）（终审额度）';
comment on column BIZ_PROJECT_GUARANTEE.ESTIMATE_MONTHLY_INCOME
  is '评估月收入';
create sequence SEQ_BIZ_PROJECT_CONTACTS;
