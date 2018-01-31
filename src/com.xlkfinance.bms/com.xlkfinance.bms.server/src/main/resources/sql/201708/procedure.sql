 /*增加提房贷产品，二次放款修改逾期费计算函数*/
  CREATE OR REPLACE FUNCTION GET_OVERDUE_FEE(IN_PROJECT_ID         IN INT,
                                           V_NEW_REPAYMENT_MONEY IN NUMBER)
  RETURN NUMBER IS
  V_REAL_LOAN           NUMBER(12, 2);
  V_OVERDUE_FEE         NUMBER(12, 2);
  V_REMAINING_REC_MONEY NUMBER(12, 2);
  V_PRINCIPAL           NUMBER(12, 2);
  V_OVERDUE_DAY         INT;
BEGIN
  /*查询放款金额*/
  SELECT SUM(AFH.REC_MONEY)
    INTO V_REAL_LOAN
    FROM BIZ_LOAN_FINANCE_HANDLE FH
   INNER JOIN BIZ_LOAN_APPLY_FINANCE_HANDLE AFH
      ON FH.PID = AFH.FINANCE_HANDLE_ID
   WHERE AFH.REC_PRO IN (3, 4, 5, 6,9)
     AND FH.PROJECT_ID = IN_PROJECT_ID
     AND AFH.APPLY_STATUS=5;
  DBMS_OUTPUT.PUT_LINE('放款金额' || V_REAL_LOAN);
    /*查询逾期天数（实际回款日期-预计回款日期）*/
  SELECT
          (TO_DATE(TO_CHAR(A.NEW_REPAYMENT_DATE, 'yyyy-MM-dd'), 'yyyy-MM-dd') -
          TO_DATE(TO_CHAR(B.PAYMENT_DATE, 'yyyy-MM-dd'), 'yyyy-MM-dd'))
         END
    INTO V_OVERDUE_DAY
    FROM BIZ_LOAN_REPAYMENT A
   INNER JOIN BIZ_PROJECT_FORECLOSURE B
      ON A.PROJECT_ID = B.PROJECT_ID
   WHERE A.PROJECT_ID = IN_PROJECT_ID;
  DBMS_OUTPUT.PUT_LINE('逾期天数' || V_OVERDUE_DAY);
    /*查询未回款金额*/
  SELECT A.REPAYMENT_MONEY
    INTO V_REMAINING_REC_MONEY
    FROM BIZ_LOAN_REPAYMENT A
   WHERE A.PROJECT_ID = IN_PROJECT_ID;
  V_REMAINING_REC_MONEY := V_REAL_LOAN - V_REMAINING_REC_MONEY+V_NEW_REPAYMENT_MONEY;
  DBMS_OUTPUT.PUT_LINE('未回款金额' || V_REMAINING_REC_MONEY);
      /*用于计算的赎楼本金=新增加的回款金额*/
  V_PRINCIPAL:=V_NEW_REPAYMENT_MONEY;
   /*未回款金额小于回款金额，则用于计算的赎楼本金=未回款金额*/
  if(V_REMAINING_REC_MONEY<V_NEW_REPAYMENT_MONEY) then
     V_PRINCIPAL :=  V_REMAINING_REC_MONEY;
  end if;
  IF (V_OVERDUE_DAY < 0) THEN
    V_OVERDUE_FEE := 0;
    /*实际回款日期-预计回款日期）>5 逾期费=5*0.15%*逾期本金+（逾期天数-5）*0.2%*逾期本金*/
  ELSE
    IF (V_OVERDUE_DAY > 5 AND V_PRINCIPAL > 0) THEN
      V_OVERDUE_FEE := 5 * 0.0015 * V_PRINCIPAL +
                       (V_OVERDUE_DAY - 5) * 0.002 * V_PRINCIPAL;
    ELSE
      IF (V_OVERDUE_DAY > 5) THEN
        /*逾期费=5*0.15%+（逾期天数-5）*0.2%*/
        
        V_OVERDUE_FEE := 5 * 0.0015 + (V_OVERDUE_DAY - 5) * 0.002;
      ELSE
        IF (V_OVERDUE_DAY <= 5 AND V_PRINCIPAL > 0) THEN
        /*(实际回款日期-预计回款日期)<=5    逾期费= 逾期本金*0.15%*逾期天数*/
          V_OVERDUE_FEE := V_PRINCIPAL * 0.0015 * V_OVERDUE_DAY;
        ELSE
          IF (V_OVERDUE_DAY <= 5) THEN
          /*(实际回款日期-预计回款日期)<=5    逾期费= 0.15%*逾期天数*/
            V_OVERDUE_FEE := 0.0015 * V_OVERDUE_DAY;
          END IF;
        END IF;
      END IF;
    END IF;
  END IF;
  RETURN(V_OVERDUE_FEE);
END GET_OVERDUE_FEE;
/