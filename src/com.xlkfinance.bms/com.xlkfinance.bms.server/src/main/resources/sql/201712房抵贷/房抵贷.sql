ALTER TABLE CUS_PER_FAMILY_FINANCE ADD (MONTHLY_PAYMENT NUMBER(12,2), OVERDRAFT NUMBER(12,2));
comment on column CUS_PER_FAMILY_FINANCE.MONTHLY_PAYMENT is
'月供款（元）';
comment on column CUS_PER_FAMILY_FINANCE.OVERDRAFT is
'信用卡透支金额（元）';

comment on column BIZ_LOAN_REPAYMENT.status
  is '状态：未回款=1,已回款=2(抵押贷1=正常还款中2=已结清3=逾期还款中4=已逾期)';

ALTER TABLE BIZ_PROJECT ADD (NEXT_USER_ID         VARCHAR2(255),
   CAPITAL_NAME         VARCHAR2(255),
   LOAN_TYPE            VARCHAR2(255));
   
comment on column BIZ_PROJECT.PROJECT_TYPE is
'项目类型（贷款=2、贷款展期=4 ,6机构提交业务 8=抵押贷）';

comment on column BIZ_PROJECT.PM_USER_ID is
'项目经理（收单人）';

comment on column BIZ_PROJECT.PRODUCT_ID is
'产品ID（关联产品表）';

comment on column BIZ_PROJECT.BUSINESS_SOURCE_NO is
'业务来源数据（具体的第三方公司名称）';

comment on column BIZ_PROJECT.BUSINESS_CONTACTS is
'机构联系人';

comment on column BIZ_PROJECT.CONTACTS_PHONE is
'机构联系人电话';

comment on column BIZ_PROJECT.MANAGERS is
'经办人（废弃）';

comment on column BIZ_PROJECT.MANAGERS_PHONE is
'经办人电话（废弃）';

comment on column BIZ_PROJECT.AREA_CODE is
'城市编码';

comment on column BIZ_PROJECT.NEXT_USER_ID is
'待办人';

comment on column BIZ_PROJECT.CAPITAL_NAME is
'资金方';

comment on column BIZ_PROJECT.LOAN_TYPE is
'放款条件';

comment on column BMS_INLOAN_TABLE.BIZ_PROJECT.FORECLOSURE_STATUS is
'项目状态1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、业务办理已完成13、已归档（解保）（房抵贷项目：1=待提交2=待评估3=带下户6=待复审7=待终审8=待放款申请9=待放款复核10=待资金放款11=还款中12=已结清）
10、已审批11、已放款12、业务办理已完成13、已归档（解保）（房抵贷项目：1=待提交2=待评估3=带下户6=待复审7=待终审8=待放款申请9=待放款复核10=待资金放款11=还款中12=已结清）
';
ALTER TABLE BIZ_PROJECT_GUARANTEE ADD (
   LOAN_TERM            NUMBER,
   REPAYMENT_TYPE       VARCHAR2(255),
   MORTGAGE_RATE        NUMBER(12,2),
   LOAN_USAGE           VARCHAR2(255),
   PAYMENT_SOURCE       VARCHAR2(255),
   MONTH_MAID_RATE      NUMBER(12,2),
   OVERDUE_RATE         NUMBER(12,2),
   PREPAYMENT_RATE      NUMBER(12,2),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP);
comment on column BIZ_PROJECT_GUARANTEE.guarantee_money
  is '首次申请金额，房抵贷产品使用';
comment on column BIZ_PROJECT_GUARANTEE.POUNDAGE is
'手续费(收费金额)';

comment on column BIZ_PROJECT_GUARANTEE.CHARGES_TYPE is
'收费方式（收费节点）';

comment on column BIZ_PROJECT_GUARANTEE.LOAN_MONEY is
'贷款金额（借款金额）';
comment on column BIZ_PROJECT_GUARANTEE.FEE_RATE is
'费率';
comment on column BIZ_PROJECT_GUARANTEE.LOAN_TERM is
'借款期限';

comment on column BIZ_PROJECT_GUARANTEE.REPAYMENT_TYPE is
'还款方式';

comment on column BIZ_PROJECT_GUARANTEE.MORTGAGE_RATE is
'抵押率';

comment on column BIZ_PROJECT_GUARANTEE.LOAN_USAGE is
'借款用途';

comment on column BIZ_PROJECT_GUARANTEE.PAYMENT_SOURCE is
'还款来源';

comment on column BIZ_PROJECT_GUARANTEE.MONTH_MAID_RATE is
'月返佣费率';

comment on column BIZ_PROJECT_GUARANTEE.OVERDUE_RATE is
'逾期日罚息率';

comment on column BIZ_PROJECT_GUARANTEE.PREPAYMENT_RATE is
'提前还款费率';

comment on column BIZ_PROJECT_GUARANTEE.CREATE_DATE is
'创建时间';

comment on column BIZ_PROJECT_GUARANTEE.CREATER_ID is
'创建人ID';

comment on column BIZ_PROJECT_GUARANTEE.UPDATE_ID is
'更新人ID';

comment on column BIZ_PROJECT_GUARANTEE.UPDATE_DATE is
'更新时间';

comment on column BIZ_PROJECT_PROPERTY.EVALUATION_PRICE
  is '评估价';
comment on column BIZ_PROJECT_PROPERTY.FORE_RATE
  is '赎楼成数';
comment on column BIZ_PROJECT_PROPERTY.PURPOSE
  is '用途';
comment on column BIZ_PROJECT_PROPERTY.BUYER_PHONE
  is '买家手机号';
comment on column BIZ_PROJECT_PROPERTY.SELLER_PHONE
  is '卖家手机号';

ALTER TABLE BIZ_PROJECT_ESTATE ADD (
   LAND_USE             VARCHAR2(255),
   ESTATE_USE           VARCHAR2(255),
   HOUSE_AGE            VARCHAR2(255),
   PROPERTY_LIFE        VARCHAR2(255),
   LAND_SURPLUS_LIFE    VARCHAR2(255),
   USE_AREA             NUMBER(12,2));
comment on column BIZ_PROJECT_ESTATE.LAND_USE is
'土地用途';

comment on column BIZ_PROJECT_ESTATE.ESTATE_USE is
'房产用途';

comment on column BIZ_PROJECT_ESTATE.HOUSE_AGE is
'房龄';

comment on column BIZ_PROJECT_ESTATE.PROPERTY_LIFE is
'产权年限';

comment on column BIZ_PROJECT_ESTATE.LAND_SURPLUS_LIFE is
'土地剩余年限';

comment on column BIZ_PROJECT_ESTATE.USE_AREA is
'使用面积';

comment on column BIZ_PROJECT_ESTATE.AREA is
'建筑面积';


ALTER TABLE BIZ_LOAN_REPAYMENT_RECORD ADD (
   REPAYMENT_TYPE       VARCHAR2(255)  default '1',
   PLAN_CYCLE_NUM       INTEGER  default 1);
   
comment on column BIZ_LOAN_REPAYMENT_RECORD.REPAYMENT_TYPE is
'回款类型';
comment on column BIZ_LOAN_REPAYMENT_RECORD.plan_cycle_num
  is '回款期数';

ALTER TABLE BIZ_LOAN_PRE_REPAY ADD (
   PROJECT_ID           INTEGER,
   LOAN_PLAN_ID         INTEGER,
   SHOULD_PREPAYMENT_FEE NUMBER(12,2),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP);

comment on column BIZ_LOAN_PRE_REPAY.SURPLUS is
'应收剩余本金';

comment on column BIZ_LOAN_PRE_REPAY.PRE_REPAY_AMT is
'实收剩余本金';

comment on column BIZ_LOAN_PRE_REPAY.FINE_RATES is
'提前还款罚金比例';

comment on column BIZ_LOAN_PRE_REPAY.FINE is
'实收提前还款费';

comment on column BIZ_LOAN_PRE_REPAY.REPAY_DATE is
'提前还款日期';

comment on column BIZ_LOAN_PRE_REPAY.PROJECT_ID is
'项目ID';

comment on column BIZ_LOAN_PRE_REPAY.LOAN_PLAN_ID is
'还款计划ID';

comment on column BIZ_LOAN_PRE_REPAY.SHOULD_PREPAYMENT_FEE is
'应收提前还款费';

comment on column BIZ_LOAN_PRE_REPAY.CREATE_DATE is
'创建时间';

comment on column BIZ_LOAN_PRE_REPAY.CREATER_ID is
'创建人ID';

comment on column BIZ_LOAN_PRE_REPAY.UPDATE_ID is
'更新人ID';

comment on column BIZ_LOAN_PRE_REPAY.UPDATE_DATE is
'更新时间';


ALTER TABLE BIZ_LOAN_REPAYMENT_PLAN ADD (
   PROJECT_ID           INTEGER,
   REBATE_FEE           NUMBER(12,2),
   MONTH_MAID_RATE      NUMBER(12,2));
   
comment on column BIZ_LOAN_REPAYMENT_PLAN.PROJECT_ID is
'项目ID';

comment on column BIZ_LOAN_REPAYMENT_PLAN.REBATE_FEE is
'返佣利息';

comment on column BIZ_LOAN_REPAYMENT_PLAN.MONTH_MAID_RATE is
'月返佣费率';

ALTER TABLE BIZ_LOAN_COLLECT_FEE_HIS ADD (
   REC_PRO  NUMBER  default 1);
   
comment on column BIZ_LOAN_COLLECT_FEE_HIS.REC_PRO is
'收费项目，設置旧数据为1';

comment on column BIZ_LOAN_COLLECT_FEE_HIS.CONSULTING_FEE is
'咨询费（收费金额）';


create table CUS_ENTERPRISE_INFO 
(
   PID                  NUMBER               not null,
   PROJECT_ID           NUMBER,
   ACCT_ID              NUMBER,
   ENTERPRISE_NAME      VARCHAR2(255),
   LEGAL_REPRESENTATIVE VARCHAR2(255),
   FOUND_DATE           DATE,
   REG_MONEY            VARCHAR2(255),
   STOCKHOLDER_TYPE     VARCHAR2(255),
   STAFF_NUM            NUMBER,
   OPERATION_SCOPE      VARCHAR2(2048),
   EMPLOY_SITUATION     VARCHAR2(255),
   ENTERPRISE_TYPE      VARCHAR2(255),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_CUS_ENTERPRISE_INFO primary key (PID)
);
comment on table CUS_ENTERPRISE_INFO is
'客户企业信息';

comment on column CUS_ENTERPRISE_INFO.PID is
'主键';

comment on column CUS_ENTERPRISE_INFO.PROJECT_ID is
'项目ID';

comment on column CUS_ENTERPRISE_INFO.ACCT_ID is
'客户ID';

comment on column CUS_ENTERPRISE_INFO.ENTERPRISE_NAME is
'企业名称';

comment on column CUS_ENTERPRISE_INFO.LEGAL_REPRESENTATIVE is
'法人代表';

comment on column CUS_ENTERPRISE_INFO.FOUND_DATE is
'成立时间';

comment on column CUS_ENTERPRISE_INFO.REG_MONEY is
'注册资金';

comment on column CUS_ENTERPRISE_INFO.STOCKHOLDER_TYPE is
'股东类型';

comment on column CUS_ENTERPRISE_INFO.STAFF_NUM is
'员工人数';

comment on column CUS_ENTERPRISE_INFO.OPERATION_SCOPE is
'经营范围';

comment on column CUS_ENTERPRISE_INFO.EMPLOY_SITUATION is
'雇用情况';

comment on column CUS_ENTERPRISE_INFO.ENTERPRISE_TYPE is
'企业类型';

comment on column CUS_ENTERPRISE_INFO.CREATE_DATE is
'创建时间';

comment on column CUS_ENTERPRISE_INFO.CREATER_ID is
'创建人ID';

comment on column CUS_ENTERPRISE_INFO.UPDATE_ID is
'更新人ID';

comment on column CUS_ENTERPRISE_INFO.UPDATE_DATE is
'更新时间';
create sequence SEQ_CUS_ENTERPRISE_INFO;
alter table CUS_ENTERPRISE_INFO
  add constraint UNIQUE_CUS_ENTERPRISE_INFO unique (PROJECT_ID);


create table CUS_CREDENTIALS 
(
   PID                  NUMBER               not null,
   ACCT_ID              NUMBER,
   PROJECT_ID           NUMBER,
   FIVE_CLASSIFY        NUMBER,
   OVERDUE_NUMS_THREE   NUMBER,
   OVERDUE_NUMS_TWO     NUMBER,
   LOAN_TOTAL           NUMBER,
   IS_OVERDUE           NUMBER,
   OVERDUE_MONEY        NUMBER(12,2),
   OVERDUE_TOTAL_MONEY  NUMBER(12,2),
   OVERDUE_PERIODS      NUMBER,
   LOAN_APPROVALS_NUMS  NUMBER,
   LOAN_APPROVALS_NUMS_TWO NUMBER,
   CREDIT_APPROVALS_NUMS NUMBER,
   CREDIT_APPROVALS_NUMS_THREE NUMBER,
   INQUIRIES_NUMS       NUMBER,
   SOCIAL_SECURITY      NUMBER,
   ACCUMULATION_FUND    NUMBER,
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_CUS_CREDENTIALS primary key (PID)
);

comment on table CUS_CREDENTIALS is
'客户资质信息';

comment on column CUS_CREDENTIALS.PID is
'主键';

comment on column CUS_CREDENTIALS.ACCT_ID is
'客户ID';

comment on column CUS_CREDENTIALS.PROJECT_ID is
'项目ID';

comment on column CUS_CREDENTIALS.FIVE_CLASSIFY is
'五级分类';

comment on column CUS_CREDENTIALS.OVERDUE_NUMS_THREE is
'三年逾期次数';

comment on column CUS_CREDENTIALS.OVERDUE_NUMS_TWO is
'两年逾期次数';

comment on column CUS_CREDENTIALS.LOAN_TOTAL is
'总贷款笔数';

comment on column CUS_CREDENTIALS.IS_OVERDUE is
'是否有逾期';

comment on column CUS_CREDENTIALS.OVERDUE_MONEY is
'逾期金额';

comment on column CUS_CREDENTIALS.OVERDUE_TOTAL_MONEY is
'逾期总金额';

comment on column CUS_CREDENTIALS.OVERDUE_PERIODS is
'逾期最长期数';

comment on column CUS_CREDENTIALS.LOAN_APPROVALS_NUMS is
'1月内贷款审批次数';

comment on column CUS_CREDENTIALS.LOAN_APPROVALS_NUMS_TWO is
'3月内贷款审批次数';

comment on column CUS_CREDENTIALS.CREDIT_APPROVALS_NUMS is
'1个月内信用卡审批次数';

comment on column CUS_CREDENTIALS.CREDIT_APPROVALS_NUMS_THREE is
'3个月内信用卡审批次数';

comment on column CUS_CREDENTIALS.INQUIRIES_NUMS is
'2月征信查询次数';

comment on column CUS_CREDENTIALS.SOCIAL_SECURITY is
'社保情况';

comment on column CUS_CREDENTIALS.ACCUMULATION_FUND is
'公积金情况';

comment on column CUS_CREDENTIALS.CREATE_DATE is
'创建时间';

comment on column CUS_CREDENTIALS.CREATER_ID is
'创建人ID';

comment on column CUS_CREDENTIALS.UPDATE_ID is
'更新人ID';

comment on column CUS_CREDENTIALS.UPDATE_DATE is
'更新时间';
create sequence SEQ_CUS_CREDENTIALS;
alter table CUS_CREDENTIALS
  add constraint UNIQUE_CUS_CREDENTIALS unique (PROJECT_ID);



create table BIZ_HIS_LOAN_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   LOAN_MONEY           NUMBER(12,2),
   LOAN_TERM            NUMBER,
   REPAYMENT_TYPE       VARCHAR2(255),
   MORTGAGE_RATE        NUMBER(12,2),
   LOAN_USAGE           VARCHAR2(255),
   PAYMENT              VARCHAR2(255),
   MONTH_MAID_RATE      NUMBER(12,2),
   OVERDUE_RATE         NUMBER(12,2),
   PREPAYMENT_RATE      NUMBER(12,2),
   FEE_RATE             NUMBER(12,2),
   CREATER_ID           NUMBER,
   CREATE_DATE          TIMESTAMP,
   CREATE_NODE          NUMBER,
   constraint PK_BIZ_HIS_LOAN_INFO primary key (PID)
);

comment on table BIZ_HIS_LOAN_INFO is
'贷款申请信息历史记录';

comment on column BIZ_HIS_LOAN_INFO.PID is
'主键';

comment on column BIZ_HIS_LOAN_INFO.PROJECT_ID is
'项目ID';

comment on column BIZ_HIS_LOAN_INFO.LOAN_MONEY is
'借款金额';

comment on column BIZ_HIS_LOAN_INFO.LOAN_TERM is
'借款期限';

comment on column BIZ_HIS_LOAN_INFO.REPAYMENT_TYPE is
'还款方式';

comment on column BIZ_HIS_LOAN_INFO.MORTGAGE_RATE is
'抵押率';

comment on column BIZ_HIS_LOAN_INFO.LOAN_USAGE is
'借款用途';

comment on column BIZ_HIS_LOAN_INFO.PAYMENT is
'还款来源';

comment on column BIZ_HIS_LOAN_INFO.MONTH_MAID_RATE is
'月返佣费率';

comment on column BIZ_HIS_LOAN_INFO.OVERDUE_RATE is
'逾期日罚息率';

comment on column BIZ_HIS_LOAN_INFO.PREPAYMENT_RATE is
'提前还款费率';

comment on column BIZ_HIS_LOAN_INFO.FEE_RATE is
'月利率';

comment on column BIZ_HIS_LOAN_INFO.CREATER_ID is
'操作人';

comment on column BIZ_HIS_LOAN_INFO.CREATE_DATE is
'操作时间';

comment on column BIZ_HIS_LOAN_INFO.CREATE_NODE is
'操作节点';
create sequence SEQ_BIZ_HIS_LOAN_INFO;

create table BIZ_SPOT_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   EASTATE_ID           INTEGER,
   SHOULD_SPOT_TIME     TIMESTAMP,
   ACTUAL_SPOT_TIME     TIMESTAMP,
   DECORATION_SITUATION VARCHAR2(255),
   HOUSE_TOTALS         NUMBER,
   FLOOR_TOTALS         NUMBER,
   HOUSE_STRUCTURE      VARCHAR2(255),
   ELEVATORS_NUMS       NUMBER,
   HOUSEHOLD_NUMS       NUMBER,
   HOUSE_PATTERN        VARCHAR2(255),
   SHOPPING_NUMS        NUMBER,
   SCHOOL_NUMS          NUMBER,
   BANK_NUMS            NUMBER,
   HOSPITAL_NUMS        NUMBER,
   TRAFFIC_SITUATION    VARCHAR2(255),
   AROUND_ENVIRONMENTAL NVARCHAR2(2000),
   AGENCY_PRICE         NUMBER(12,2),
   TOTAL_PRICE          NUMBER(12,2),
   USE_SITUATION        VARCHAR2(255),
   ACTUAL_PURPOSE       VARCHAR2(255),
   USER_TYPE            VARCHAR2(255),
   USER_SITUATION       VARCHAR2(255),
   LEASE_USE            VARCHAR2(255),
   UPDATE_DATE          TIMESTAMP,
   LEASE_TIME_START     TIMESTAMP,
   LEASE_TIME_END       TIMESTAMP,
   DEPOSIT_MONEY        NUMBER,
   RENT_MONEY           NUMBER,
   LESSEE               NVARCHAR2(2000),
   OTHEN_REMARK         NVARCHAR2(2000),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   HOUSE_PATTERN_DETAIL VARCHAR2(255),
   RENT_PAYMENT_WAY     VARCHAR2(255),
   constraint PK_BIZ_SPOT_INFO primary key (PID));

comment on table BIZ_SPOT_INFO is
'下户调查信息';

comment on column BIZ_SPOT_INFO.PID is
'主键';

comment on column BIZ_SPOT_INFO.PROJECT_ID is
'项目ID';

comment on column BIZ_SPOT_INFO.EASTATE_ID is
'物业ID';

comment on column BIZ_SPOT_INFO.SHOULD_SPOT_TIME is
'预计下户时间';

comment on column BIZ_SPOT_INFO.ACTUAL_SPOT_TIME is
'实际下户时间';

comment on column BIZ_SPOT_INFO.DECORATION_SITUATION is
'装修情况';

comment on column BIZ_SPOT_INFO.HOUSE_TOTALS is
'小区总栋数';

comment on column BIZ_SPOT_INFO.FLOOR_TOTALS is
'总楼层数';

comment on column BIZ_SPOT_INFO.HOUSE_STRUCTURE is
'房屋结构';

comment on column BIZ_SPOT_INFO.ELEVATORS_NUMS is
'电梯数（梯）';

comment on column BIZ_SPOT_INFO.HOUSEHOLD_NUMS is
'电梯数（户）';

comment on column BIZ_SPOT_INFO.HOUSE_PATTERN is
'房产格局';

comment on column BIZ_SPOT_INFO.SHOPPING_NUMS is
'商场数';

comment on column BIZ_SPOT_INFO.SCHOOL_NUMS is
'学校数';

comment on column BIZ_SPOT_INFO.BANK_NUMS is
'银行数';

comment on column BIZ_SPOT_INFO.HOSPITAL_NUMS is
'医院数';

comment on column BIZ_SPOT_INFO.TRAFFIC_SITUATION is
'交通情况';

comment on column BIZ_SPOT_INFO.AROUND_ENVIRONMENTAL is
'周边环境';

comment on column BIZ_SPOT_INFO.AGENCY_PRICE is
'中介单价';

comment on column BIZ_SPOT_INFO.TOTAL_PRICE is
'总价值';

comment on column BIZ_SPOT_INFO.USE_SITUATION is
'使用情况';

comment on column BIZ_SPOT_INFO.ACTUAL_PURPOSE is
'实际用途';

comment on column BIZ_SPOT_INFO.USER_TYPE is
'使用人类型';

comment on column BIZ_SPOT_INFO.USER_SITUATION is
'使用人具体情况';

comment on column BIZ_SPOT_INFO.LEASE_USE is
'承租人';

comment on column BIZ_SPOT_INFO.UPDATE_DATE is
'更新时间';

comment on column BIZ_SPOT_INFO.LEASE_TIME_START is
'租赁时间开始';

comment on column BIZ_SPOT_INFO.LEASE_TIME_END is
'租赁时间开始结束';

comment on column BIZ_SPOT_INFO.DEPOSIT_MONEY is
'押金缴纳（月）';

comment on column BIZ_SPOT_INFO.RENT_MONEY is
'租金缴纳（元）';

comment on column BIZ_SPOT_INFO.LESSEE is
'承租人信息';

comment on column BIZ_SPOT_INFO.OTHEN_REMARK is
'其他情况说明';

comment on column BIZ_SPOT_INFO.CREATE_DATE is
'创建时间';

comment on column BIZ_SPOT_INFO.CREATER_ID is
'创建人ID';

comment on column BIZ_SPOT_INFO.UPDATE_ID is
'更新人ID';

comment on column BIZ_SPOT_INFO.HOUSE_PATTERN_DETAIL is
'格局详情';

comment on column BIZ_SPOT_INFO.RENT_PAYMENT_WAY is
'租金缴交方式';

create sequence SEQ_BIZ_SPOT_INFO;
alter table BIZ_SPOT_INFO
  add constraint UNIQUE_BIZ_SPOT_INFO unique (PROJECT_ID,EASTATE_ID);


create table BIZ_SPOT_FILE 
(
   PID                  INTEGER              not null,
   SPOT_ID              INTEGER,
   FILE_ID              INTEGER,
   STATUS               NUMBER,
   constraint PK_BIZ_SPOT_FILE primary key (PID)
);

comment on table BIZ_SPOT_FILE is
'下户调查文件';

comment on column BIZ_SPOT_FILE.PID is
'主键';

comment on column BIZ_SPOT_FILE.SPOT_ID is
'下户报告ID';

comment on column BIZ_SPOT_FILE.FILE_ID is
'文件ID';

comment on column BIZ_SPOT_FILE.STATUS is
'状态(1、有效0、无效)';
create sequence SEQ_BIZ_SPOT_FILE;

create table BIZ_MORTGAGE_EVALUATION 
(
   PID                  INTEGER              not null,
   ESTATE               INTEGER,
   EVALUATION_PRICE     NUMBER(12,2),
   EVALUATION_SOURCE    VARCHAR2(255),
   EVALUATION_PROPORTION NUMBER(12,2),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_BIZ_MORTGAGE_EVALUATION primary key (PID)
);

comment on table BIZ_MORTGAGE_EVALUATION is
'抵押物评估信息';

comment on column BIZ_MORTGAGE_EVALUATION.PID is
'主键';

comment on column BIZ_MORTGAGE_EVALUATION.ESTATE is
'物业ID';

comment on column BIZ_MORTGAGE_EVALUATION.EVALUATION_PRICE is
'评估价';

comment on column BIZ_MORTGAGE_EVALUATION.EVALUATION_SOURCE is
'评估价来源';

comment on column BIZ_MORTGAGE_EVALUATION.EVALUATION_PROPORTION is
'评估价占比';

comment on column BIZ_MORTGAGE_EVALUATION.CREATE_DATE is
'创建时间';

comment on column BIZ_MORTGAGE_EVALUATION.CREATER_ID is
'创建人ID';

comment on column BIZ_MORTGAGE_EVALUATION.UPDATE_ID is
'更新人ID';

comment on column BIZ_MORTGAGE_EVALUATION.UPDATE_DATE is
'更新时间';
create sequence SEQ_BIZ_MORTGAGE_EVALUATION;

create table BIZ_PROJECT_OVERDUE 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   LOAN_PLAN_ID         INTEGER,
   OVERDUE_MONEY        NUMBER(12,2),
   SHOULD_PENALTY       NUMBER(12,2),
   ACTUAL_PENALTY       NUMBER(12,2),
   ACTUAL_PENALTY_TIME  TIMESTAMP,
   ACTUAL_OVERDUE_MONEY NUMBER(12,2),
   SHOULD_OVERDUE_MONEY NUMBER(12,2),
   REMARK               NVARCHAR2(2000),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   OVERDUE_RATE         NUMBER(12,2),
   OVERDUE_DAY          INTEGER,
   constraint PK_BIZ_PROJECT_OVERDUE primary key (PID)
);

comment on table BIZ_PROJECT_OVERDUE is
'逾期信息表';

comment on column BIZ_PROJECT_OVERDUE.PID is
'主键';

comment on column BIZ_PROJECT_OVERDUE.PROJECT_ID is
'项目ID';

comment on column BIZ_PROJECT_OVERDUE.LOAN_PLAN_ID is
'还款计划ID';

comment on column BIZ_PROJECT_OVERDUE.OVERDUE_MONEY is
'本期逾期金额';

comment on column BIZ_PROJECT_OVERDUE.SHOULD_PENALTY is
'应收罚息';

comment on column BIZ_PROJECT_OVERDUE.ACTUAL_PENALTY is
'实收罚息';

comment on column BIZ_PROJECT_OVERDUE.ACTUAL_PENALTY_TIME is
'实收罚息日期';

comment on column BIZ_PROJECT_OVERDUE.ACTUAL_OVERDUE_MONEY is
'已收逾期金额';

comment on column BIZ_PROJECT_OVERDUE.SHOULD_OVERDUE_MONEY is
'应收逾期金额';

comment on column BIZ_PROJECT_OVERDUE.remark
  is '备注';
  
comment on column BIZ_PROJECT_OVERDUE.CREATE_DATE is
'创建时间';

comment on column BIZ_PROJECT_OVERDUE.CREATER_ID is
'创建人ID';

comment on column BIZ_PROJECT_OVERDUE.UPDATE_ID is
'更新人ID';

comment on column BIZ_PROJECT_OVERDUE.UPDATE_DATE is
'更新时间';

comment on column BIZ_PROJECT_OVERDUE.OVERDUE_RATE is
'逾期日罚息率';

comment on column BIZ_PROJECT_OVERDUE.OVERDUE_DAY is
'逾期天数';
create sequence SEQ_BIZ_PROJECT_OVERDUE;
alter table BIZ_PROJECT_OVERDUE
  add constraint UNIQUE_BIZ_PROJECT_OVERDUE unique (PROJECT_ID,LOAN_PLAN_ID);



create table BIZ_PAYMENT_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID          INTEGER,
   LOAN_PLAN_ID         INTEGER,
   ACTUAL_PRINCIPAL     NUMBER(12,2),
   ACTUAL_INTEREST      NUMBER(12,2),
   ACTUAL_TOTAL         NUMBER(12,2),
   ACTUAL_PRINCIPAL_TIME TIMESTAMP,
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_BIZ_PAYMENT_INFO primary key (PID)
);

comment on table BIZ_PAYMENT_INFO is
'正常还款信息表';

comment on column BIZ_PAYMENT_INFO.PID is
'主键';

comment on column BIZ_PAYMENT_INFO.PROJECT_ID is
'项目ID';

comment on column BIZ_PAYMENT_INFO.LOAN_PLAN_ID is
'还款计划ID';

comment on column BIZ_PAYMENT_INFO.ACTUAL_PRINCIPAL is
'实收本金';

comment on column BIZ_PAYMENT_INFO.ACTUAL_INTEREST is
'实收利息';

comment on column BIZ_PAYMENT_INFO.ACTUAL_TOTAL is
'实收合计';

comment on column BIZ_PAYMENT_INFO.ACTUAL_PRINCIPAL_TIME is
'实收本息日期';

comment on column BIZ_PAYMENT_INFO.CREATE_DATE is
'创建时间';

comment on column BIZ_PAYMENT_INFO.CREATER_ID is
'创建人ID';

comment on column BIZ_PAYMENT_INFO.UPDATE_ID is
'更新人ID';

comment on column BIZ_PAYMENT_INFO.UPDATE_DATE is
'更新时间';
create sequence SEQ_BIZ_PAYMENT_INFO;
alter table BIZ_PAYMENT_INFO
  add constraint UNIQUE_BIZ_PAYMENT_INFO unique (PROJECT_ID,LOAN_PLAN_ID);


create table BIZ_INTERVIEW_FILE 
(
   PID                  INTEGER              not null,
   INTERVIEW_ID         INTEGER,
   UPLOAD_CODE          VARCHAR2(255),
   FILE_ID              INTEGER,
   STATUS               INTEGER,
   constraint PK_BIZ_INTERVIEW_FILE primary key (PID)
);

comment on table BIZ_INTERVIEW_FILE is
'面签管理文件';

comment on column BIZ_INTERVIEW_FILE.PID is
'主键';

comment on column BIZ_INTERVIEW_FILE.INTERVIEW_ID is
'面签ID';

comment on column BIZ_INTERVIEW_FILE.UPLOAD_CODE is
'上传节点';

comment on column BIZ_INTERVIEW_FILE.FILE_ID is
'文件ID';

comment on column BIZ_INTERVIEW_FILE.STATUS is
'状态(1、有效0、无效)';
create sequence SEQ_BIZ_INTERVIEW_FILE;

create table BIZ_INTERVIEW_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   INTERVIEW_ID         NUMBER,
   INTERVIEW_TIME       TIMESTAMP,
   INTERVIEW_PLACE      VARCHAR2(255),
   NOTARIZATION_TYPE    VARCHAR2(255),
   RECEIVE_TIME         TIMESTAMP,
   NOTARIZATION_ID      NUMBER,
   HANDING_TIME         TIMESTAMP,
   MORTGAGE_NAME        VARCHAR2(255),
   MORTGAGE_CODE        VARCHAR2(255),
   MORTGAGE_TIME        TIMESTAMP,
   MORTGAGE_USER        NUMBER,
   MORTGAGE_HAND_TIME   TIMESTAMP,
   HIS_WARRANT          VARCHAR2(255),
   HIS_WARRANT_TIME     TIMESTAMP,
   HIS_WARRANT_USER     NUMBER,
   INTERVIEW_STATUS     NUMBER,
   NOTARIZATION_STATUS  NUMBER,
   MORTGAGE_STATUS      NUMBER,
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_BIZ_INTERVIEW_INFO primary key (PID)
);
comment on table BIZ_INTERVIEW_INFO is
'面签管理信息';

comment on column BIZ_INTERVIEW_INFO.PID is
'主键';

comment on column BIZ_INTERVIEW_INFO.PROJECT_ID is
'项目ID';

comment on column BIZ_INTERVIEW_INFO.INTERVIEW_ID is
'面签人员';

comment on column BIZ_INTERVIEW_INFO.INTERVIEW_TIME is
'面签时间';

comment on column BIZ_INTERVIEW_INFO.INTERVIEW_PLACE is
'面签地点';

comment on column BIZ_INTERVIEW_INFO.NOTARIZATION_TYPE is
'公证类型';

comment on column BIZ_INTERVIEW_INFO.RECEIVE_TIME is
'取证时间';

comment on column BIZ_INTERVIEW_INFO.NOTARIZATION_ID is
'公证人员';

comment on column BIZ_INTERVIEW_INFO.HANDING_TIME is
'办理时间';

comment on column BIZ_INTERVIEW_INFO.MORTGAGE_NAME is
'抵押权人';

comment on column BIZ_INTERVIEW_INFO.MORTGAGE_CODE is
'抵押回执编号';

comment on column BIZ_INTERVIEW_INFO.MORTGAGE_TIME is
'抵押取证时间';

comment on column BIZ_INTERVIEW_INFO.MORTGAGE_USER is
'抵押人员';

comment on column BIZ_INTERVIEW_INFO.MORTGAGE_HAND_TIME is
'抵押办理日期';

comment on column BIZ_INTERVIEW_INFO.HIS_WARRANT is
'他项权证';

comment on column BIZ_INTERVIEW_INFO.HIS_WARRANT_TIME is
'他项权证领取时间';

comment on column BIZ_INTERVIEW_INFO.HIS_WARRANT_USER is
'他项权证经办人';

comment on column BIZ_INTERVIEW_INFO.INTERVIEW_STATUS is
'面签状态';

comment on column BIZ_INTERVIEW_INFO.NOTARIZATION_STATUS is
'公证状态';

comment on column BIZ_INTERVIEW_INFO.MORTGAGE_STATUS is
'抵押状态';

comment on column BIZ_INTERVIEW_INFO.CREATE_DATE is
'创建时间';

comment on column BIZ_INTERVIEW_INFO.CREATER_ID is
'创建人ID';

comment on column BIZ_INTERVIEW_INFO.UPDATE_ID is
'更新人ID';

comment on column BIZ_INTERVIEW_INFO.UPDATE_DATE is
'更新时间';

create sequence SEQ_BIZ_INTERVIEW_INFO;
alter table BIZ_INTERVIEW_INFO
  add constraint UNIQUE_BIZ_INTERVIEW_INFO unique (PROJECT_ID);


create table CUS_CARD_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   ACCT_ID              INTEGER,
   RECE_BANK_CARD_NAME  VARCHAR2(255),
   RECE_BANK_CARD_CODE  VARCHAR2(255),
   RECE_BANK_NAME       VARCHAR2(255),
   REPAYMENT_BANK_CARD_NAME VARCHAR2(255),
   REPAYMENT_BANK_CARD_CODE VARCHAR2(255),
   REPAYMENT_BANK_NAME  VARCHAR2(255),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_CUS_CARD_INFO primary key (PID)
);

comment on table CUS_CARD_INFO is
'客户银行卡信息';

comment on column CUS_CARD_INFO.PID is
'主键';

comment on column CUS_CARD_INFO.PROJECT_ID is
'项目ID';

comment on column CUS_CARD_INFO.ACCT_ID is
'客户ID';

comment on column CUS_CARD_INFO.RECE_BANK_CARD_NAME is
'收款银行卡户名';

comment on column CUS_CARD_INFO.RECE_BANK_CARD_CODE is
'收款银行卡卡号';

comment on column CUS_CARD_INFO.RECE_BANK_NAME is
'收款银行卡开户行';

comment on column CUS_CARD_INFO.REPAYMENT_BANK_CARD_NAME is
'还款银行卡户名';

comment on column CUS_CARD_INFO.REPAYMENT_BANK_CARD_CODE is
'还款银行卡卡号';

comment on column CUS_CARD_INFO.REPAYMENT_BANK_NAME is
'还款银行卡开户行';

comment on column CUS_CARD_INFO.CREATE_DATE is
'创建时间';

comment on column CUS_CARD_INFO.CREATER_ID is
'创建人ID';

comment on column CUS_CARD_INFO.UPDATE_ID is
'更新人ID';

comment on column CUS_CARD_INFO.UPDATE_DATE is
'更新时间';
create sequence SEQ_CUS_CARD_INFO;
alter table CUS_CARD_INFO
  add constraint UNIQUE_CUS_CARD_INFO unique (PROJECT_ID);


create table CUS_HIS_CARD_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   ACCT_ID              INTEGER,
   RECE_BANK_CARD_NAME  VARCHAR2(255),
   RECE_BANK_CARD_CODE  VARCHAR2(255),
   RECE_BANK_NAME       VARCHAR2(255),
   REPAYMENT_BANK_CARD_NAME VARCHAR2(255),
   REPAYMENT_BANK_CARD_CODE VARCHAR2(255),
   REPAYMENT_BANK_NAME  VARCHAR2(255),
   CREATER_ID           NUMBER,
   CREATE_DATE          TIMESTAMP,
   CREATE_NODE          NUMBER,
   constraint PK_CUS_HIS_CARD_INFO primary key (PID)
);

comment on table CUS_HIS_CARD_INFO is
'客户银行卡历史记录';

comment on column CUS_HIS_CARD_INFO.PID is
'主键';

comment on column CUS_HIS_CARD_INFO.PROJECT_ID is
'项目ID';

comment on column CUS_HIS_CARD_INFO.ACCT_ID is
'客户ID';

comment on column CUS_HIS_CARD_INFO.RECE_BANK_CARD_NAME is
'收款银行卡户名';

comment on column CUS_HIS_CARD_INFO.RECE_BANK_CARD_CODE is
'收款银行卡卡号';

comment on column CUS_HIS_CARD_INFO.RECE_BANK_NAME is
'收款银行卡开户行';

comment on column CUS_HIS_CARD_INFO.REPAYMENT_BANK_CARD_NAME is
'还款银行卡户名';

comment on column CUS_HIS_CARD_INFO.REPAYMENT_BANK_CARD_CODE is
'还款银行卡卡号';

comment on column CUS_HIS_CARD_INFO.REPAYMENT_BANK_NAME is
'还款银行卡开户行';

comment on column CUS_HIS_CARD_INFO.CREATER_ID is
'操作人';

comment on column CUS_HIS_CARD_INFO.CREATE_DATE is
'操作时间';

comment on column CUS_HIS_CARD_INFO.CREATE_NODE is
'操作节点';
create sequence SEQ_CUS_HIS_CARD_INFO;

create table BIZ_CAPITAL_SEL_RECORD 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   CAPITAL_NAME         VARCHAR2(255),
   CAPITAL_APPROVAL_STATUS NUMBER,
   CAPITAL_LOAN_STATUS  NUMBER,
   CREATER_ID           NUMBER,
   CREATE_DATE          TIMESTAMP,
   REVIEW_ID            NUMBER,
   REVIEW_TIME          TIMESTAMP,
   constraint PK_BIZ_CAPITAL_SEL_RECORD primary key (PID)
);

comment on table BIZ_CAPITAL_SEL_RECORD is
'资方选择记录';

comment on column BIZ_CAPITAL_SEL_RECORD.PID is
'主键';

comment on column BIZ_CAPITAL_SEL_RECORD.PROJECT_ID is
'项目ID';

comment on column BIZ_CAPITAL_SEL_RECORD.CAPITAL_NAME is
'资方名称';

comment on column BIZ_CAPITAL_SEL_RECORD.CAPITAL_APPROVAL_STATUS is
'资方审批状态';

comment on column BIZ_CAPITAL_SEL_RECORD.CAPITAL_LOAN_STATUS is
'资方放款状态';

comment on column BIZ_CAPITAL_SEL_RECORD.CREATER_ID is
'操作人';

comment on column BIZ_CAPITAL_SEL_RECORD.CREATE_DATE is
'操作时间';

comment on column BIZ_CAPITAL_SEL_RECORD.REVIEW_ID is
'复核人';

comment on column BIZ_CAPITAL_SEL_RECORD.REVIEW_TIME is
'复核时间';
create sequence SEQ_BIZ_CAPITAL_SEL_RECORD;

create table BIZ_PROJECT_MORTGAGE 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   MORTGAGE_STATUS      NUMBER,
   REGISTER_TIME        TIMESTAMP,
   REGISTER_ID          NUMBER,
   ISSUE_TIME           TIMESTAMP,
   ISSUE_ID             NUMBER,
   ISSUE_USER_NAME      VARCHAR2(255),
   ISSUE_MATERIAL       VARCHAR2(255),
   CANCEL_ID            NUMBER,
   CANCEL_USER_NAME      VARCHAR2(255),
   CANCEL_TIME          TIMESTAMP,
   CANCEL_MATERIAL      VARCHAR2(255),
   RETURN_ID            NUMBER,
   RETURN_USER_NAME      VARCHAR2(255),
   RETURN_TIME          TIMESTAMP,
   RETURN_MATERIAL      VARCHAR2(255),
   CREATE_DATE          TIMESTAMP,
   CREATER_ID           NUMBER,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_BIZ_PROJECT_MORTGAGE primary key (PID)
);

comment on table BIZ_PROJECT_MORTGAGE is
'抵押管理信息';

comment on column BIZ_PROJECT_MORTGAGE.PID is
'主键';

comment on column BIZ_PROJECT_MORTGAGE.PROJECT_ID is
'项目ID';

comment on column BIZ_PROJECT_MORTGAGE.MORTGAGE_STATUS is
'抵押物状态(1、待登记入库2=待出库申请3=待出具注销材料4=待退证登记5=已完结)';

comment on column BIZ_PROJECT_MORTGAGE.REGISTER_TIME is
'入库登记时间';

comment on column BIZ_PROJECT_MORTGAGE.REGISTER_ID is
'入库登记人';

comment on column BIZ_PROJECT_MORTGAGE.ISSUE_TIME is
'出库申请时间';

comment on column BIZ_PROJECT_MORTGAGE.ISSUE_ID is
'出库申请人';
comment on column BIZ_PROJECT_MORTGAGE.ISSUE_USER_NAME is
'出库签收人';

comment on column BIZ_PROJECT_MORTGAGE.ISSUE_MATERIAL is
'出库签收材料';

comment on column BIZ_PROJECT_MORTGAGE.CANCEL_ID is
'出具注销人';
comment on column BIZ_PROJECT_MORTGAGE.CANCEL_USER_NAME is
'出具注销签收人';

comment on column BIZ_PROJECT_MORTGAGE.CANCEL_TIME is
'出具注销时间';

comment on column BIZ_PROJECT_MORTGAGE.CANCEL_MATERIAL is
'出具注销材料';

comment on column BIZ_PROJECT_MORTGAGE.RETURN_ID is
'退证登记人';
comment on column BIZ_PROJECT_MORTGAGE.RETURN_USER_NAME is
'退证登记签收人';

comment on column BIZ_PROJECT_MORTGAGE.RETURN_TIME is
'退证登记时间';

comment on column BIZ_PROJECT_MORTGAGE.RETURN_MATERIAL is
'退证登记材料';

comment on column BIZ_PROJECT_MORTGAGE.CREATE_DATE is
'创建时间';

comment on column BIZ_PROJECT_MORTGAGE.CREATER_ID is
'创建人ID';

comment on column BIZ_PROJECT_MORTGAGE.UPDATE_ID is
'更新人ID';

comment on column BIZ_PROJECT_MORTGAGE.UPDATE_DATE is
'更新时间';
create sequence SEQ_BIZ_PROJECT_MORTGAGE;
alter table BIZ_PROJECT_MORTGAGE
  add constraint UNIQUE_BIZ_PROJECT_MORTGAGE unique (PROJECT_ID);


create table BIZ_STORAGE_INFO 
(
   PID                  INTEGER              not null,
   PROJECT_ID           INTEGER,
   MORTGAGE_ID          INTEGER,
   FILE_NAME            VARCHAR2(255),
   REGISTER_TIME        TIMESTAMP,
   FILE_DESC            VARCHAR2(2048),
   CREATE_ID            NUMBER,
   CREATE_DATE          TIMESTAMP,
   STATUS               INTEGER,
   constraint PK_BIZ_STORAGE_INFO primary key (PID)
);

comment on table BIZ_STORAGE_INFO is
'入库材料清单';

comment on column BIZ_STORAGE_INFO.PID is
'主键';

comment on column BIZ_STORAGE_INFO.PROJECT_ID is
'项目ID';

comment on column BIZ_STORAGE_INFO.MORTGAGE_ID is
'抵押物管理ID';

comment on column BIZ_STORAGE_INFO.FILE_NAME is
'资料名称';

comment on column BIZ_STORAGE_INFO.REGISTER_TIME is
'入库时间';

comment on column BIZ_STORAGE_INFO.FILE_DESC is
'资料说明';

comment on column BIZ_STORAGE_INFO.CREATE_ID is
'操作人';

comment on column BIZ_STORAGE_INFO.CREATE_DATE is
'操作时间';

comment on column BIZ_STORAGE_INFO.STATUS is
'状态（1=未出库2=已出库）';
create sequence SEQ_BIZ_STORAGE_INFO;
