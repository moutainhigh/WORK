
DECLARE
  SEQ_BEFORE_LOAN_PROCESS_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_HANDLE_WORK_PROCESS_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_LOAN_TERM_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_PRODUCT_TYPE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_FDD_COLLECT_FEE_PRO INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_FDD_LOAN_TYPE INTEGER := SEQ_SYS_LOOKUP.Nextval;
BEGIN
  
  INSERT INTO SYS_LOOKUP (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
	values (SEQ_BEFORE_LOAN_PROCESS_ID, 'BEFORE_LOAN_PROCESS', '贷前流程', '贷前流程', '1', SYSDATE);
  
  INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_BEFORE_LOAN_PROCESS_ID, 'creditLoanRequestProcess', '1', '小科赎楼贷贷前流程', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_BEFORE_LOAN_PROCESS_ID, 'foreLoanRequestProcess', '1', '万通赎楼贷贷前流程', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_BEFORE_LOAN_PROCESS_ID, 'businessApplyRequestProcess', '1', 'AOM端提单贷前流程', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_BEFORE_LOAN_PROCESS_ID, 'mortgageLoanForeAppRequestProcess', '1', '抵押贷贷前申请工作流', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_BEFORE_LOAN_PROCESS_ID, 'consumeLoanForeAppRequestProcess', '1', '消费贷贷前申请工作流', SYSDATE, null);



INSERT INTO SYS_LOOKUP (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
values (SEQ_HANDLE_WORK_PROCESS_ID, 'HANDLE_WORK_PROCESS', '业务办理审批流程', '业务办理审批流程', '1', SYSDATE);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_HANDLE_WORK_PROCESS_ID, 'nonTransactionCashRedemptionHomeProcess', '1', '非交易现金赎楼工作流', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_HANDLE_WORK_PROCESS_ID, 'transactionCashRedemptionHomeProcess', '1', '交易现金赎楼工作流', SYSDATE, null);


INSERT INTO SYS_LOOKUP (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
values (SEQ_LOAN_TERM_ID, 'LOAN_TERM_CAL', '期限', '可贷金额计算器中的期限', '1', SYSDATE);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_LOAN_TERM_ID, '1', '1', '36', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_LOAN_TERM_ID, '2', '1', '24', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_LOAN_TERM_ID, '3', '1', '12', SYSDATE, null);


INSERT INTO SYS_LOOKUP (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
values (SEQ_PRODUCT_TYPE_ID, 'PRODUCT_TYPE', '产品类型', '产品类型', '1', SYSDATE);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_PRODUCT_TYPE_ID, '1', '1', '信贷', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_PRODUCT_TYPE_ID, '2', '1', '赎楼', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_PRODUCT_TYPE_ID, '3', '1', '抵押', SYSDATE, null);



INSERT INTO SYS_LOOKUP (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
values (SEQ_FDD_COLLECT_FEE_PRO, 'FDD_COLLECT_FEE_PRO', '房抵贷收费项目', '收费模块', '1', SYSDATE);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_FDD_COLLECT_FEE_PRO, '10', '1', '下户费', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_FDD_COLLECT_FEE_PRO, '11', '1', '居间服务费', SYSDATE, null);



INSERT INTO SYS_LOOKUP (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
values (SEQ_FDD_LOAN_TYPE, 'FDD_LOAN_TYPE', '房抵贷放款条件', '房抵贷放款条件', '1', SYSDATE);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_FDD_LOAN_TYPE, '1', '1', '凭他证放款', SYSDATE, null);

INSERT INTO SYS_LOOKUP_VAL (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
values (SEQ_SYS_LOOKUP_VAL.NEXTVAL, SEQ_FDD_LOAN_TYPE, '2', '1', '凭回执放款', SYSDATE, null);


INSERT INTO SYS_ROLE (PID, ROLE_NAME, ROLE_DESC, ROLE_CODE,STATUS,PARENT_ID)
  values (SEQ_SYS_ROLE.Nextval, '运营专员', '运营专员', 'OPERATION_COMMISSIONER',1,0);

END;
/



CREATE OR REPLACE VIEW BUSINESS_SOURCE_V AS
(SELECT F.PID,
       (CASE
         WHEN F.BUSINESS_SOURCE_STR IS NULL THEN
          F.LOOKUP_DESC
         ELSE
          F.LOOKUP_DESC || '--' || F.BUSINESS_SOURCE_STR
       END) AS BUSINESS_SOURCE_STR
  FROM (SELECT A.PID,
               (CASE
                 WHEN A.BUSINESS_SOURCE = 13782 THEN
                  B.ORG_NAME
                 WHEN A.BUSINESS_SOURCE = 13859 THEN
                  A.MY_MAIN
                 ELSE
                  C.LOOKUP_DESC
               END) AS BUSINESS_SOURCE_STR,
               D.LOOKUP_DESC
          FROM BIZ_PROJECT                 A,
               SYS_LOOKUP_VAL              D,
               ORG_ASSETS_COOPERATION_INFO b,
               SYS_LOOKUP_VAL              c
         WHERE A.BUSINESS_SOURCE = D.PID
           and A.BUSINESS_SOURCE_NO = B.PID(+)
           and A.BUSINESS_SOURCE_NO = C.PID(+)
           and A.PROJECT_TYPE IN (2, 4, 6)) F
UNION ALL
SELECT T.PID,
       (CASE
         WHEN T.ORG_NAME IS NULL THEN
          T.LOOKUP_DESC
         ELSE
          T.LOOKUP_DESC || '--' || T.ORG_NAME
       END) AS BUSINESS_SOURCE_STR
  FROM (SELECT BP.PID, SLU.LOOKUP_DESC, OAC.ORG_NAME
          FROM BIZ_PROJECT BP
          LEFT JOIN (SELECT SLV.LOOKUP_VAL, SLV.LOOKUP_DESC
                      FROM SYS_LOOKUP SL, SYS_LOOKUP_VAL SLV
                     WHERE SL.PID = SLV.LOOKUP_ID
                       AND SL.LOOKUP_TYPE = 'MORTGAGE_LOAN_BUSINESS_SOURCE') SLU
            ON BP.BUSINESS_SOURCE = SLU.LOOKUP_VAL
          LEFT JOIN ORG_ASSETS_COOPERATION_INFO OAC
            ON BP.BUSINESS_SOURCE_NO = OAC.PID
         WHERE BP.PROJECT_TYPE in (8,10)) T);


INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '抵押贷新增申请按钮权限',
   '抵押贷新增申请按钮权限',
   'ADD_FDD_PERMISSION',
   0,
   1);
   
   INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '抵押贷编辑申请按钮权限',
   '抵押贷编辑申请按钮权限',
   'UPDATE_FDD_PERMISSION',
   0,
   1);
   

   INSERT INTO BIZ_PRODUCT
  (PID,
   CREATER_ID,
   PRODUCT_TYPE,
   PRODUCT_NAME,
   CITY_ID,
   PRODUCT_NUM,
   CREATE_DATE,
   STATUS,
   TRADE_TYPE,
   LOAN_WORK_PROCESS_ID,
   BIZHANDLE_WORK_PROCESS_ID,
   PRODUCT_SOURCE,
   IS_FORECLOSURE)
values
  (SEQ_BIZ_PRODUCT.NEXTVAL,
   '20005',
   '3',
   '抵押贷',
   '2',
   'FDD001',
   SYSDATE,
   '1',
   '13756',
   'mortgageLoanForeAppRequestProcess',
   'nonTransactionCashRedemptionHomeProcess',
   '1',
   '2');

INSERT INTO BIZ_PRODUCT
  (PID,
   CREATER_ID,
   PRODUCT_TYPE,
   PRODUCT_NAME,
   CITY_ID,
   PRODUCT_NUM,
   CREATE_DATE,
   STATUS,
   TRADE_TYPE,
   LOAN_WORK_PROCESS_ID,
   BIZHANDLE_WORK_PROCESS_ID,
   PRODUCT_SOURCE,
   IS_FORECLOSURE)
values
  (SEQ_BIZ_PRODUCT.NEXTVAL,
   '21017',
   '1',
   '租赁贷',
   '2',
   'XFD001',
   SYSDATE,
   '1',
   '13756',
   'consumeLoanForeAppRequestProcess',
   'nonTransactionCashRedemptionHomeProcess',
   '1',
   '2');
commit;
/