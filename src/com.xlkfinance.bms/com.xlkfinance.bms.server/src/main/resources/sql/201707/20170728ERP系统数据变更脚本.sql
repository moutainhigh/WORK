


update biz_project_partner set 
       REPAYMENT_REPURCHASE_TYPE = 1 ,
       REPAYMENT_REPURCHASE_TIME = to_timestamp('2017-07-28 09:27:49.29999', 'yyyy-mm-dd hh24:mi:ss:ff'),
       REFUND_DATE = to_date('2017-07-28','yyyy-mm-dd'),
       PARTNER_REAL_REFUND_DATE = to_date('2017-07-28','yyyy-mm-dd'),
       REFUND_LOAN_AMOUNT = 3530000,
       REFUND_XIFEE = 8825,
       REFUND_TOTAL_AMOUNT = 3538825,
       PAY_ACCT_NAME ='深圳前海小科互联网金融服务有限公司',
       PAY_ACCT_NO = '41033100040019324' where pid = 3381;
       