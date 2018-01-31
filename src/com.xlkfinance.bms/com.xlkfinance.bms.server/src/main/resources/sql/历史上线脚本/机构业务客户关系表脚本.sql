declare
  -- Local variables here
  cursor c_job is
   select ca.pid as acct_id,ca.pm_user_id as pm_user_Id,
(SELECT 
        org.id
    FROM 
        sys_org_info org 
    WHERE org.layer =2
    START WITH org.id = su.org_id 
       CONNECT BY PRIOR org.parent_id = org.id) as org_id
from CUS_ACCT ca ,sys_user su 
where ca.cus_type = 1 and ca.status = 1 and ca.pm_user_id = su.pid;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
   INSERT INTO CUS_RELATION (PID,ACCT_ID,PM_USER_ID,ORG_ID,ORG_TYPE) 
 	values(SEQ_CUS_RELATION.NEXTVAL,c_row.acct_id,c_row.pm_user_Id,c_row.org_id,1);
  end loop;
commit;
end;
/

declare
  -- Local variables here
  cursor c_job is
    SELECT LV.LOOKUP_VAL,LV.LOOKUP_DESC
        FROM SYS_LOOKUP L,SYS_LOOKUP_VAL LV
        WHERE LV.STATUS = 1  AND L.PID = LV.LOOKUP_ID 
        AND L.LOOKUP_TYPE = 'LOAN_SOURCES_OF_FUNDS';
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
      INSERT INTO CAPITAL_ORG_INFO (PID,ORG_NAME,STATUS,CREATE_DATE,LOAN_MONEY_TOTAL) VALUES(c_row.LOOKUP_VAL,c_row.LOOKUP_DESC,1,sysdate,0.00);
  end loop;
commit;
end;
/