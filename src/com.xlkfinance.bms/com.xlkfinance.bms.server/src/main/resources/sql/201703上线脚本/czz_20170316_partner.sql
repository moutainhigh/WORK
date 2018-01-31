
/**客户征信信息表-----------------------------------------------*/
create table CUS_CREDIT_INFO
(
   PID 	INTEGER  PRIMARY KEY,
   PARTNER_NO VARCHAR2(50),
   BUSINESS_TYPE INTEGER,
   ACCT_ID INTEGER ,
   CREDIT_DEAL_ID  VARCHAR2(50),
   RECORD        VARCHAR2(50),
   OVERDUE   VARCHAR2(50),
   RESULT1   VARCHAR2(50),
   RESULT2   VARCHAR2(50),
   RESULT3   VARCHAR2(50),
   RESULT4   VARCHAR2(50),
   RESULT5   VARCHAR2(50),
   RESULT6   VARCHAR2(50),
   RESULT7   VARCHAR2(50),
   CREATE_TIME TIMESTAMP(6),
   UPDATE_TIME TIMESTAMP(6)
);


/**添加客户征信信息序列*/
CREATE SEQUENCE SEQ_CUS_CREDIT_INFO;


/**备注*/
comment on table CUS_CREDIT_INFO is '客户征信信息表';
comment on column CUS_CREDIT_INFO.PID is '主键';
comment on column CUS_CREDIT_INFO.ACCT_ID is '客户帐号ID';
comment on column CUS_CREDIT_INFO.PARTNER_NO is '资金机构';
comment on column CUS_CREDIT_INFO.BUSINESS_TYPE is '交易类型 （1：交易  2:非交易）';
comment on column CUS_CREDIT_INFO.CREDIT_DEAL_ID is '征信交易编号';
comment on column CUS_CREDIT_INFO.RECORD is '征信白户识别码，人行征信为白户（未查询到客户征信记录）';
comment on column CUS_CREDIT_INFO.OVERDUE is '当前逾期否决码，单笔负债（含贷记卡）当前逾期且逾期金额大于1000元';
comment on column CUS_CREDIT_INFO.RESULT1 is 'M4否决码，近2年内单笔负债（含贷记卡）曾见4及以上';
comment on column CUS_CREDIT_INFO.RESULT2 is '1年综合否决码，近1年内单笔曾见1个4、2个3、3个2或4个1及以上';
comment on column CUS_CREDIT_INFO.RESULT3 is '半年综合否决码，近半年内单笔曾见1个3、2个2或3个1及以上';
comment on column CUS_CREDIT_INFO.RESULT4 is '异常负债否决码，单笔负债曾出现“保证人代偿”、“资产处置”、“以资抵债”、“呆帐”、“冻结”、“止付”、“次级”、“可疑”、“损失”等特殊情况';
comment on column CUS_CREDIT_INFO.RESULT5 is '对外担保负债否决码，单笔对外担保负债五级分类曾出现“次级”、“可疑”、“损失”等特殊情况';
comment on column CUS_CREDIT_INFO.RESULT6 is '失信信息否决码，列入失信被执行人名单';
comment on column CUS_CREDIT_INFO.RESULT7 is '查询记录否决码，近2个月内有6次查询记录且原因是“贷款审批”';
comment on column CUS_CREDIT_INFO.CREATE_TIME is '创建时间';
comment on column CUS_CREDIT_INFO.UPDATE_TIME is '更新时间';



/**-----------------------------资金机构项目表 -----------------------------------------------*/

/**资金项目编号*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD PARTNER_ORDER_CODE  VARCHAR2(50);
comment on column BIZ_PROJECT_PARTNER.PARTNER_ORDER_CODE is '资金项目编号';

/**平台申请到资方业务编号*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD PARTNER_PLATFORM_ORDER_CODE  VARCHAR2(50);
comment on column BIZ_PROJECT_PARTNER.PARTNER_PLATFORM_ORDER_CODE is '平台申请到资方业务编号';

/**借款期限*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD LOAN_PERIOD_LIMIT  INTEGER;
comment on column BIZ_PROJECT_PARTNER.LOAN_PERIOD_LIMIT is '借款期限';

/**客户在他行有无未结清的信用贷款*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD IS_CREDIT_LOAN  INTEGER;
comment on column BIZ_PROJECT_PARTNER.IS_CREDIT_LOAN is '客户在他行有无未结清的信用贷款（1：有 2：无）';

/**借款人银行预留手机号码*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD PAYMENT_BANK_PHONE  VARCHAR2(50);
comment on column BIZ_PROJECT_PARTNER.PAYMENT_BANK_PHONE is '借款人银行预留手机号码';

/**借款人银行联行行号*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD PAYMENT_BANK_LINE_NO  VARCHAR2(50);
comment on column BIZ_PROJECT_PARTNER.PAYMENT_BANK_LINE_NO is '借款人银行联行行号';




/**还款总金额*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD REFUND_TOTAL_AMOUNT  NUMBER(12,2);
comment on column BIZ_PROJECT_PARTNER.REFUND_TOTAL_AMOUNT is '还款总金额';

/**还款违约金*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD REFUND_PENALTY  NUMBER(12,2);
comment on column BIZ_PROJECT_PARTNER.REFUND_PENALTY is '还款违约金';
/**还款罚息*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD REFUND_FINE  NUMBER(12,2);
comment on column BIZ_PROJECT_PARTNER.REFUND_FINE is '还款罚息';

/**还款复利*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD REFUND_COMPDINTE  NUMBER(12,2);
comment on column BIZ_PROJECT_PARTNER.REFUND_COMPDINTE is '还款复利';




/**--------------------------资金机构项目文件表 -----------------------------------------------*/

/**第三方平台文件URL*/
ALTER TABLE BIZ_PROJECT_PARTNER_FILE ADD THIRD_FILE_URL  VARCHAR2(500);
comment on column BIZ_PROJECT_PARTNER_FILE.THIRD_FILE_URL is '第三方平台文件URL';


/**--------------------------客户基本信息表 -----------------------------------------------*/
/**身份证发证机关所在地*/
ALTER TABLE CUS_PERSON ADD CERT_ADDR  VARCHAR2(255);
comment on column CUS_PERSON.CERT_ADDR is '身份证发证机关所在地';

 

/**--------------------------物业信息表添加权属人和产权占比*/
ALTER TABLE BIZ_PROJECT_ESTATE ADD PROPERTY_RATIO  VARCHAR2(255);
comment on column BIZ_PROJECT_ESTATE.PROPERTY_RATIO is '权属人和占比，多个用英文逗号隔开';







	
 









 