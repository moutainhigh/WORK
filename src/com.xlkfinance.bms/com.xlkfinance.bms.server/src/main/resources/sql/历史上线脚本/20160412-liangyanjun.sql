alter table biz_Collect_files modify remark varchar2(2048) ;
alter table biz_Check_Document modify remark varchar2(2048) ;
alter table biz_loan_repayment modify remark varchar2(2048) ;
alter table biz_loan_repayment_record modify remark varchar2(2048) ;
alter table biz_loan_overdue_Fee modify remark varchar2(2048) ;
alter table biz_Check_litigation modify remark varchar2(2048) ;
alter table biz_Intermediate_transfer modify remark varchar2(2048) ;
alter table BIZ_LOAN_PERFORM_JOB_REMARK modify remark varchar2(2048) ;
alter table BIZ_LOAN_HANDLE_INFO modify foreclosure_Turn_Down_remark varchar2(2048) ;

CREATE OR REPLACE FUNCTION GET_EXTENSION_FEE(IN_EXTENSION_PROJECT_ID IN INT)
  RETURN NUMBER IS
  EXTENSION_FEE       NUMBER(12, 2);
  TOTAL_EXTENSION_FEE NUMBER(12, 2);
  -- 根据被展期项目id查询财务已收展期费的财务id
  CURSOR C_EXTENSION IS
    SELECT C.PID AS FINANCE_ID
      FROM BIZ_PROJECT_EXTENSION A
      LEFT JOIN BIZ_PROJECT B
        ON B.PID = A.EXTENSION_PROJECT_ID
      LEFT JOIN BIZ_LOAN_FINANCE_HANDLE C
        ON C.PROJECT_ID = A.PROJECT_ID
     WHERE A.EXTENSION_PROJECT_ID = IN_EXTENSION_PROJECT_ID;
  C_ROW C_EXTENSION%ROWTYPE;
BEGIN
  FOR C_ROW IN C_EXTENSION LOOP
    IF (C_ROW.FINANCE_ID IS NOT NULL) THEN
      --根据财务id查询收取的展期费，并累加
      SELECT A.REC_MONEY
        INTO EXTENSION_FEE
        FROM BIZ_LOAN_APPLY_FINANCE_HANDLE A
       WHERE A.REC_PRO = 8
         AND A.FINANCE_HANDLE_ID = C_ROW.FINANCE_ID;
      TOTAL_EXTENSION_FEE := EXTENSION_FEE + TOTAL_EXTENSION_FEE;
      DBMS_OUTPUT.PUT_LINE('财务id' || C_ROW.FINANCE_ID || ',金额' ||
                           EXTENSION_FEE);
    END IF;
  END LOOP;
  RETURN(TOTAL_EXTENSION_FEE);
END GET_EXTENSION_FEE;
/