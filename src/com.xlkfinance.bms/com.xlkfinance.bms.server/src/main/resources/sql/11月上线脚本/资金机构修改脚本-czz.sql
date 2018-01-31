

/**资金机构表 添加是否推送字段*/
alter table CAPITAL_ORG_INFO add IS_PUSH  INTEGER;
comment on column CAPITAL_ORG_INFO.IS_PUSH is '是否推送 （1:是  2：否）';

/**调整数据*/
update CAPITAL_ORG_INFO set IS_PUSH = 1 where ORG_CODE in ('xiaoying','tlxt','dianrongwang');
update CAPITAL_ORG_INFO set IS_PUSH = 2 where ORG_CODE not in ('xiaoying','tlxt','dianrongwang');
 



DECLARE
  -- LOCAL VARIABLES HERE
  CURSOR C_JOB IS
   SELECT A.PRO_RESOURCE_2,B.ORG_NAME,B.ORG_CODE ,A.PID
FROM BIZ_LOAN_APPLY_FINANCE_HANDLE A,CAPITAL_ORG_INFO B
WHERE A.PRO_RESOURCE_2 = B.PID AND A.REC_PRO IN (3,4,5,6);
  C_ROW C_JOB%ROWTYPE;
BEGIN
  FOR C_ROW IN C_JOB LOOP
  UPDATE BIZ_LOAN_APPLY_FINANCE_HANDLE A SET A.PRO_RESOURCE_2 = C_ROW.ORG_CODE WHERE A.PID = C_ROW.PID;
  
  END LOOP;
  COMMIT;
END;
/


DECLARE
  -- LOCAL VARIABLES HERE
  CURSOR C_JOB IS
   SELECT A.PRO_RESOURCE,B.ORG_NAME,B.ORG_CODE ,A.PID
FROM BIZ_LOAN_APPLY_FINANCE_HANDLE A,CAPITAL_ORG_INFO B
WHERE A.PRO_RESOURCE = B.PID AND A.REC_PRO IN (3,4,5,6);
  C_ROW C_JOB%ROWTYPE;
BEGIN
  FOR C_ROW IN C_JOB LOOP
  UPDATE BIZ_LOAN_APPLY_FINANCE_HANDLE A SET A.PRO_RESOURCE = C_ROW.ORG_CODE WHERE A.PID = C_ROW.PID;
  
  END LOOP;
  COMMIT;
END;
/



/*调整资金机构合作项目-机构代码*/
UPDATE BIZ_PROJECT_PARTNER SET PARTNER_NO = 'xiaoying' where PARTNER_NO  = '小赢';
UPDATE BIZ_PROJECT_PARTNER SET PARTNER_NO = 'tlxt' where PARTNER_NO  = '统联';
UPDATE BIZ_PROJECT_PARTNER SET PARTNER_NO = 'dianrongwang' where PARTNER_NO  = '点融';


/*调整资金机构合作项目附件-机构代码*/
UPDATE BIZ_PROJECT_PARTNER_FILE SET PARTNER_NO = 'xiaoying' where PARTNER_NO  = '小赢';
UPDATE BIZ_PROJECT_PARTNER_FILE SET PARTNER_NO = 'tlxt' where PARTNER_NO  = '统联';
UPDATE BIZ_PROJECT_PARTNER_FILE SET PARTNER_NO = 'dianrongwang' where PARTNER_NO  = '点融';

/*调整资金机构合作项目分期还款列表-机构代码*/
UPDATE BIZ_PROJECT_PARTNER_REFUND SET PARTNER_NO = 'xiaoying' where PARTNER_NO  = '小赢';
UPDATE BIZ_PROJECT_PARTNER_REFUND SET PARTNER_NO = 'tlxt' where PARTNER_NO  = '统联';
UPDATE BIZ_PROJECT_PARTNER_REFUND SET PARTNER_NO = 'dianrongwang' where PARTNER_NO  = '点融';

/*调整历史小赢放款成功日期默认为通知结果时间*/
update biz_project_partner 
       set PARTNER_LOAN_DATE  = TO_DATE(TO_CHAR(loan_result_time, 'YYYY-MM-DD'),  'YYYY-MM-DD') 
       where partner_no = 'xiaoying' and loan_status =  4 and PARTNER_LOAN_DATE is null
       
/*调整统联历史确认放款天数和确认放款金额 -默认为申请一致(审批通过)*/
update biz_project_partner 
       set CONFIRM_LOAN_MONEY  = APPROVE_MONEY 
       where partner_no in ('tlxt','dianrongwang') and APPROVAL_STATUS =  2 and CONFIRM_LOAN_MONEY is null
update biz_project_partner 
       set CONFIRM_LOAN_DAYS  = APPLY_DATE 
       where partner_no in ('tlxt','dianrongwang')  and APPROVAL_STATUS =  2 and CONFIRM_LOAN_DAYS is null

/*调整点融历史还款日期*/
update biz_project_partner p set p.refund_date = (
       select pr.curr_real_refund_date from biz_project_partner_refund pr 
       where pr.curr_no  = 1  and pr.partner_id= p.pid
)where p.partner_no = 'dianrongwang' and  p.repayment_repurchase_status = 3
      and p.refund_date is null 
       




