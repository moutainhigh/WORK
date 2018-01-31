/*原贷款信息表*/
create table BIZ_ORIGINAL_LOAN 
(
   PID                  INTEGER     PRIMARY KEY,
   PROJECT_ID           INTEGER,
   LOAN_TYPE            INTEGER,
   OLD_LOAN_BANK        VARCHAR2(255),
   OLD_LOAN_BANK_BRANCH VARCHAR2(255),
   OLD_LOAN_MONEY       NUMBER,
   OLD_OWED_AMOUNT      NUMBER,
   OLD_LOAN_TIME        TIMESTAMP,
   OLD_LOAN_START_TIME  TIMESTAMP,
   OLD_LOAN_PERSON      VARCHAR2(255),
   OLD_LOAN_PHONE       VARCHAR2(255),
   OLD_LOAN_ACCOUNT     VARCHAR2(255),
   CREATER_DATE         TIMESTAMP,
   CREATER_ID           INTEGER,
   UPDATE_ID            INTEGER,
   UPDATE_DATE          TIMESTAMP,
   STATUS               INTEGER  DEFAULT 1,
   ESTATE_ID     INTEGER
);

comment on table BIZ_ORIGINAL_LOAN is
'原贷款信息';

comment on column BIZ_ORIGINAL_LOAN.PID is
'主键';

comment on column BIZ_ORIGINAL_LOAN.PROJECT_ID is
'项目ID';

comment on column BIZ_ORIGINAL_LOAN.LOAN_TYPE is
'原贷款类型';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_BANK is
'原贷款银行';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_BANK_BRANCH is
'原贷款支行';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_MONEY is
'原贷款金额';

comment on column BIZ_ORIGINAL_LOAN.OLD_OWED_AMOUNT is
'原贷款欠款金额';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_TIME is
'原贷款结束时间';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_START_TIME is
'原贷款时间';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_PERSON is
'原贷款联系人(银行客户经理)';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_PHONE is
'原贷款联系电话';

comment on column BIZ_ORIGINAL_LOAN.OLD_LOAN_ACCOUNT is
'供楼账号';

comment on column BIZ_ORIGINAL_LOAN.CREATER_DATE is
'创建时间';

comment on column BIZ_ORIGINAL_LOAN.CREATER_ID is
'创建人ID';

comment on column BIZ_ORIGINAL_LOAN.UPDATE_ID is
'更新人ID';

comment on column BIZ_ORIGINAL_LOAN.UPDATE_DATE is
'更新时间';

comment on column BIZ_ORIGINAL_LOAN.STATUS is
'数据状态1、有效2无效';

comment on column BIZ_ORIGINAL_LOAN.ESTATE_ID is
'物业ID';

create SEQUENCE SEQ_BIZ_ORIGINAL_LOAN;

/*项目物业信息表*/
create table BIZ_PROJECT_ESTATE 
(
   PID                  INTEGER    PRIMARY KEY,
   PROJECT_ID           INTEGER,
   HOUSE_NAME          VARCHAR2(500),
   COST_MONEY           NUMBER,
   AREA                 NUMBER,
   HOUSE_PROPERTY_CARD  VARCHAR2(500),
   PURPOSE              VARCHAR2(500),
   TRANASCTION_MONEY    NUMBER,
   EVALUATION_PRICE     NUMBER,
   HOUSE_PROVINCE_CODE  VARCHAR2(255),
   HOUSE_CITY_CODE      VARCHAR2(255),
   HOUSE_DISTRICT_CODE  VARCHAR2(255),
   STATUS               INTEGER DEFAULT 1 ,
   CREATER_DATE         TIMESTAMP,
   CREATER_ID           INTEGER,
   UPDATE_ID            INTEGER,
   UPDATE_DATE          TIMESTAMP,
   EVALUATION_NET       NUMBER
);

comment on table BIZ_PROJECT_ESTATE is
'项目物业信息';

comment on column BIZ_PROJECT_ESTATE.PID is
'主键';

comment on column BIZ_PROJECT_ESTATE.PROJECT_ID is
'项目ID';

comment on column BIZ_PROJECT_ESTATE.HOUSE_NAME is
'物业名称';

comment on column BIZ_PROJECT_ESTATE.COST_MONEY is
'登记价';

comment on column BIZ_PROJECT_ESTATE.AREA is
'面积';

comment on column BIZ_PROJECT_ESTATE.HOUSE_PROPERTY_CARD is
'房产证号';

comment on column BIZ_PROJECT_ESTATE.PURPOSE is
'用途';

comment on column BIZ_PROJECT_ESTATE.TRANASCTION_MONEY is
'成交价';

comment on column BIZ_PROJECT_ESTATE.EVALUATION_PRICE is
'评估价';

comment on column BIZ_PROJECT_ESTATE.HOUSE_PROVINCE_CODE is
'物业省编码';

comment on column BIZ_PROJECT_ESTATE.HOUSE_CITY_CODE is
'物业市编码';

comment on column BIZ_PROJECT_ESTATE.HOUSE_DISTRICT_CODE is
'物业区编码';

comment on column BIZ_PROJECT_ESTATE.STATUS is
'数据状态1、有效2无效';

comment on column BIZ_PROJECT_ESTATE.CREATER_DATE is
'创建时间';

comment on column BIZ_PROJECT_ESTATE.CREATER_ID is
'创建人ID';

comment on column BIZ_PROJECT_ESTATE.UPDATE_ID is
'更新人ID';

comment on column BIZ_PROJECT_ESTATE.UPDATE_DATE is
'更新时间';

comment on column BIZ_PROJECT_ESTATE.EVALUATION_NET is
'评估净值';
create SEQUENCE SEQ_BIZ_PROJECT_ESTATE;

/*修改记录表*/
create table BIZ_CHANGE_RECORD 
(
   PID                  INTEGER  PRIMARY KEY,
   RELATION_ID          INTEGER,
   MODIFY_USER          INTEGER,
   MODIFY_TIME          TIMESTAMP,
   MODIFY_REASON        NVARCHAR2(2000),
   OLD_VALUE            NVARCHAR2(2000),
   NEW_VALUE            NVARCHAR2(2000),
   VALUE_TYPE           INTEGER,
   CHANGE_TYPE          INTEGER,
   OPERATION_IP         VARCHAR2(50)
);

comment on table BIZ_CHANGE_RECORD is
'信息变更记录';

comment on column BIZ_CHANGE_RECORD.PID is
'主键';

comment on column BIZ_CHANGE_RECORD.RELATION_ID is
'变更关联ID';

comment on column BIZ_CHANGE_RECORD.MODIFY_USER is
'修改人';

comment on column BIZ_CHANGE_RECORD.MODIFY_TIME is
'修改时间';

comment on column BIZ_CHANGE_RECORD.MODIFY_REASON is
'修改原因';

comment on column BIZ_CHANGE_RECORD.OLD_VALUE is
'原值';

comment on column BIZ_CHANGE_RECORD.NEW_VALUE is
'变更值';

comment on column BIZ_CHANGE_RECORD.VALUE_TYPE is
'值类型1、借款金额2、接口天数3、费率4、咨询费5、授信额度6、启用授信额度7、可用授信额度8、预收费率9、实际收费费率10、出款标准11、单笔上限12、保证金比例13、保证金金额';

comment on column BIZ_CHANGE_RECORD.CHANGE_TYPE is
'操作类型1、贷前业务数据变更2、机构合作信息变更';

comment on column BIZ_CHANGE_RECORD.OPERATION_IP is
'操作IP';
create SEQUENCE SEQ_BIZ_CHANGE_RECORD;

/*原贷款银行信息*/
CREATE OR REPLACE VIEW OLD_LOAN_BANK_V AS(
SELECT (SELECT TO_CHAR(WM_CONCAT(S.BANK_NAME))
                          FROM BIZ_ORIGINAL_LOAN BO, SYS_BANK_INFO S
                         WHERE S.PID = BO.OLD_LOAN_BANK
                           AND BO.PROJECT_ID = P.PID
                           AND BO.STATUS = 1) AS BANK_NAME,P.PID AS PID FROM BIZ_PROJECT P
);

/*机构端已用额度视图*/
CREATE OR REPLACE VIEW ORG_USED_LIMIT AS(
SELECT O.PID AS ORG_ID,
       (CASE
         WHEN D.USED_LIMIT IS NULL THEN
          0
         ELSE
          D.USED_LIMIT
       END) AS USED_LIMIT
  FROM ORG_ASSETS_COOPERATION_INFO O
  LEFT JOIN (
             SELECT T.ORG_ID,
                     SUM((T.LOAN_MONEY - T.REPAYMENT_MONEY)) AS USED_LIMIT
               FROM (SELECT A.ORG_ID,
                             B.LOAN_MONEY,
                             A.PID,
                             (CASE
                               WHEN C.REPAYMENT_MONEY IS NULL THEN
                                0
                               ELSE
                                C.REPAYMENT_MONEY
                             END) AS REPAYMENT_MONEY
                        FROM BIZ_PROJECT A
                        LEFT JOIN BIZ_PROJECT_GUARANTEE B
                          ON A.PID = B.PROJECT_ID
                        LEFT JOIN BIZ_LOAN_REPAYMENT C
                          ON A.PID = C.PROJECT_ID
                       WHERE A.PROJECT_TYPE = 6
                         AND A.STATUS = 1
                         AND A.IS_CLOSED = 2
                         AND A.IS_REJECT IN (2, 3)
                         AND A.IS_CHECHAN = 0
                         AND A.REQUEST_STATUS IN (2, 3, 4, 5)
                         AND (C.STATUS IS NULL OR C.STATUS = 1)) T
              GROUP BY T.ORG_ID) D
    ON O.PID = D.ORG_ID
   );
   
   /*项目放款视图，查询结果为项目ID，放款总金额，放款来源，放款来源金额*/
   CREATE OR REPLACE VIEW V_PROJECT_LOAN AS(
	SELECT PID,PROJECT_ID,REC_DATE,PRO_RESOURCE,REC_MONEY,PRO_RESOURE_MONEY
  FROM (SELECT FH.PID,
               FH.PROJECT_ID,
               AFH.REC_DATE,
               AFH.PRO_RESOURCE,
               AFH.REC_MONEY,
               AFH.REC_MONEY AS PRO_RESOURE_MONEY
          FROM BIZ_LOAN_FINANCE_HANDLE FH
          LEFT JOIN BIZ_LOAN_APPLY_FINANCE_HANDLE AFH
            ON FH.PID = AFH.FINANCE_HANDLE_ID
         WHERE AFH.REC_PRO = 3
           AND FH.STATUS = 3
           AND AFH.PRO_RESOURCE_MONEY_2 IS NULL
        UNION ALL
        SELECT FH.PID,
               FH.PROJECT_ID,
               AFH.REC_DATE,
               AFH.PRO_RESOURCE_2 AS PRO_RESOURCE,
               AFH.REC_MONEY,
               AFH.PRO_RESOURCE_MONEY_2 AS PRO_RESOURCE_MONEY
          FROM BIZ_LOAN_FINANCE_HANDLE FH
          LEFT JOIN BIZ_LOAN_APPLY_FINANCE_HANDLE AFH
            ON FH.PID = AFH.FINANCE_HANDLE_ID
         WHERE AFH.REC_PRO = 3
           AND FH.STATUS = 3
           AND AFH.PRO_RESOURCE_MONEY_2 > 0
           AND AFH.PRO_RESOURCE_MONEY IS NULL
        UNION ALL
        SELECT * FROM 
        (SELECT A.PID,
               A.PROJECT_ID,
               B.REC_DATE,
               PRO_RESOURCE,
               B.REC_MONEY,
               B.PRO_RESOURCE_MONEY
          FROM BIZ_LOAN_FINANCE_HANDLE A, BIZ_LOAN_APPLY_FINANCE_HANDLE B
         WHERE A.PID = B.FINANCE_HANDLE_ID
           AND B.REC_PRO = 3
           AND A.STATUS = 3
           AND PRO_RESOURCE_MONEY IS NOT NULL
           AND PRO_RESOURCE_MONEY_2 IS NOT NULL
        UNION ALL
        SELECT A.PID,
               A.PROJECT_ID,
               B.REC_DATE,
               PRO_RESOURCE_2,
               B.REC_MONEY,
               B.PRO_RESOURCE_MONEY_2
          FROM BIZ_LOAN_FINANCE_HANDLE A, BIZ_LOAN_APPLY_FINANCE_HANDLE B
         WHERE A.PID = B.FINANCE_HANDLE_ID
           AND B.REC_PRO = 3
           AND A.STATUS = 3
           AND PRO_RESOURCE_MONEY IS NOT NULL
           AND PRO_RESOURCE_MONEY_2 IS NOT NULL
         ORDER BY 1,2) A));