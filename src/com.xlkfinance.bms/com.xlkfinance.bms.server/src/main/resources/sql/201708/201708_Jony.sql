  /*创建国家法定节假日表 */
   DROP TABLE BIZ_WORK_DAY;
   CREATE TABLE BIZ_WORK_DAY
   (
     PID          INTEGER PRIMARY KEY,
     CORRECT_YEAR INTEGER,
     CORRECT_DATE VARCHAR2(55),
     IS_HOLIDAYS  INTEGER,
     REMARK       VARCHAR2(255),
     CREAT_ID     INTEGER,
     CREAT_DATE   TIMESTAMP(6),
     UPDATE_ID    INTEGER,
     UPDATE_DATE  TIMESTAMP(6)
   );
  /*ADD COMMENTS TO THE TABLE */
   COMMENT ON TABLE BIZ_WORK_DAY IS '国家法定节假日表';
   COMMENT ON COLUMN BIZ_WORK_DAY.PID IS '表主键';
   COMMENT ON COLUMN BIZ_WORK_DAY.CORRECT_YEAR IS '节假日年份';
   COMMENT ON COLUMN BIZ_WORK_DAY.CORRECT_DATE IS '具体日期';
   COMMENT ON COLUMN BIZ_WORK_DAY.IS_HOLIDAYS IS '(1=是；2=否)';
   COMMENT ON COLUMN BIZ_WORK_DAY.REMARK IS '备注';
   COMMENT ON COLUMN BIZ_WORK_DAY.CREAT_ID IS '创建人';
   COMMENT ON COLUMN BIZ_WORK_DAY.CREAT_DATE IS '创建时间';
   COMMENT ON COLUMN BIZ_WORK_DAY.UPDATE_ID IS '修改人';
   COMMENT ON COLUMN BIZ_WORK_DAY.UPDATE_DATE IS '修改时间';
   /*创建法定节假日序列*/
   DROP SEQUENCE SEQ_BIZ_WORK_DAY;
   CREATE SEQUENCE SEQ_BIZ_WORK_DAY;
     
 /*产品信息表增加是否赎楼字段*/
 ALTER TABLE BIZ_PRODUCT
    ADD (
    IS_FORECLOSURE INTEGER
    );
  COMMENT ON COLUMN BIZ_PRODUCT.IS_FORECLOSURE IS '是否赎楼（1=是；2=否）';
  
   /*交易有赎楼提放贷*/   
   INSERT INTO BIZ_PRODUCT
            (PID,CREATER_ID,
             PRODUCT_TYPE,
             PRODUCT_NAME,
             CITY_ID,
             PRODUCT_NUM,
             CREATE_DATE,
             UPDATE_DATE,
             STATUS,
             TRADE_TYPE,
             LOAN_WORK_PROCESS_ID,
             BIZHANDLE_WORK_PROCESS_ID,
             PRODUCT_SOURCE,
             IS_FORECLOSURE
            )
          VALUES
            (SEQ_BIZ_PRODUCT.NEXTVAL,
             20005,
             2,
             '交易有赎楼提放贷',
             2,
             'JYYSLTFD',
             sysdate,
             sysdate,
             1,
             13755,
             'creditLoanRequestProcess',
             'transactionCashRedemptionHomeProcess',
             1,
             1
             );
               commit;
   /*交易无赎楼提放贷*/   
   INSERT INTO BIZ_PRODUCT
            (PID,
             CREATER_ID,
             PRODUCT_TYPE,
             PRODUCT_NAME,
             CITY_ID,
             PRODUCT_NUM,
             CREATE_DATE,
             UPDATE_DATE,
             STATUS,
             TRADE_TYPE,
             LOAN_WORK_PROCESS_ID,
             BIZHANDLE_WORK_PROCESS_ID,
             PRODUCT_SOURCE,
             IS_FORECLOSURE
            )
          VALUES
            (SEQ_BIZ_PRODUCT.NEXTVAL,
             20005,
             2,
             '交易无赎楼提放贷',
             2,
             'JYWSLTFD',
             sysdate,
             sysdate,
             1,
             13755,
             'creditLoanRequestProcess',
             'transactionCashRedemptionHomeProcess',
             1,
             2
             );
             commit;
   /*非交易有赎楼提放贷*/   
   INSERT INTO BIZ_PRODUCT
            (PID,
             CREATER_ID,
             PRODUCT_TYPE,
             PRODUCT_NAME,
             CITY_ID,
             PRODUCT_NUM,
             CREATE_DATE,
             UPDATE_DATE,
             STATUS,
             TRADE_TYPE,
             LOAN_WORK_PROCESS_ID,
             BIZHANDLE_WORK_PROCESS_ID,
             PRODUCT_SOURCE,
             IS_FORECLOSURE
            )
          VALUES
            (SEQ_BIZ_PRODUCT.NEXTVAL,
             20005,
             2,
             '非交易有赎楼提放贷',
             2,
             'FJYYSLTFD',
             sysdate,
             sysdate,
             1,
             13756,
             'creditLoanRequestProcess',
             'nonTransactionCashRedemptionHomeProcess',
             1,
             1
             );
             commit; 
    /*非交易无赎楼提放贷*/   
   INSERT INTO BIZ_PRODUCT
            (PID,
             CREATER_ID,
             PRODUCT_TYPE,
             PRODUCT_NAME,
             CITY_ID,
             PRODUCT_NUM,
             CREATE_DATE,
             UPDATE_DATE,
             STATUS,
             TRADE_TYPE,
             LOAN_WORK_PROCESS_ID,
             BIZHANDLE_WORK_PROCESS_ID,
             PRODUCT_SOURCE,
             IS_FORECLOSURE
            )
          VALUES
            (SEQ_BIZ_PRODUCT.NEXTVAL,
             20005,
             2,
             '非交易无赎楼提放贷',
             2,
             'FJYWSLTFD',
             sysdate,
             sysdate,
             1,
             13756,
             'creditLoanRequestProcess',
             'nonTransactionCashRedemptionHomeProcess',
             1,
             2
             );
              commit;
  /* 项目担保信息表增加周转金额、赎楼金额 、收费类型*/
  ALTER TABLE BIZ_PROJECT_GUARANTEE
    ADD (
      TURNOVER_MONEY NUMBER(12,2),
      FORECLOSURE_MONEY NUMBER(12,2),
      CHARGE_TYPE INTEGER
    );
  COMMENT ON COLUMN BIZ_PROJECT_GUARANTEE.TURNOVER_MONEY IS '周转金额';
  COMMENT ON COLUMN BIZ_PROJECT_GUARANTEE.FORECLOSURE_MONEY IS '赎楼金额';
  COMMENT ON COLUMN BIZ_PROJECT_GUARANTEE.CHARGE_TYPE IS '收费类型';

  /* 赎楼项目信息表增加周转资金户名、周转资金账号、周转资金开户行*/
   ALTER TABLE BIZ_PROJECT_FORECLOSURE
     ADD (
        TURNOVER_CAPITAL_NAME NVARCHAR2(255),
        TURNOVER_CAPITAL_ACCOUNT NVARCHAR2(255),
        TURNOVER_CAPITAL_BANK NVARCHAR2(255)
     );

  COMMENT ON COLUMN BIZ_PROJECT_FORECLOSURE.TURNOVER_CAPITAL_NAME IS '周转资金户名';
  COMMENT ON COLUMN BIZ_PROJECT_FORECLOSURE.TURNOVER_CAPITAL_ACCOUNT IS '周转资金账号';
  COMMENT ON COLUMN BIZ_PROJECT_FORECLOSURE.TURNOVER_CAPITAL_BANK IS '周转资金开户行';
  