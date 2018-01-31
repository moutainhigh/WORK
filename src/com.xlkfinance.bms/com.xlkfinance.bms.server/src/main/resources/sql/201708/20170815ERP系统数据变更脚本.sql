


/**李秀存2017080301*/
update biz_project_partner set 
       LOAN_STATUS = 4 ,
       PARTNER_ORDER_CODE = '201708031415000308' ,
       PARTNER_PLATFORM_ORDER_CODE = '9B78CA7800012A0A' ,
       PARTNER_LOAN_DATE = to_date('2017-08-03','yyyy-mm-dd') ,
       LOAN_RESULT_TIME = to_timestamp('2017-08-04 09:50:49.29999', 'yyyy-mm-dd hh24:mi:ss:ff')
       where pid = 3454;
       
/**李剑光2017073101*/
update biz_project_partner set 
       LOAN_STATUS = 4 ,
       PARTNER_ORDER_CODE = '201708011650070726' ,
       PARTNER_PLATFORM_ORDER_CODE = '9B78CA7800006F0A' ,
       PARTNER_LOAN_DATE = to_date('2017-08-03','yyyy-mm-dd') ,
       LOAN_RESULT_TIME = to_timestamp('2017-08-04 09:50:49.29999', 'yyyy-mm-dd hh24:mi:ss:ff')
       where pid = 3447;  
       
       
commit;