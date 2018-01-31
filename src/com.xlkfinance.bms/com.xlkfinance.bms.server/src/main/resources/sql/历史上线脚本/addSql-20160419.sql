alter table biz_project modify(BUSINESS_CONTACTS varchar2(255));

alter table BIZ_PROJECT_FORECLOSURE modify(OLD_LOAN_PERSON varchar2(255));
alter table BIZ_PROJECT_FORECLOSURE modify(NEW_LOAN_PERSON varchar2(255));
alter table BIZ_PROJECT_FORECLOSURE modify(THIRD_BORROWER varchar2(255));


declare
  -- Local variables here
  cursor c_job is
    select s.pid,s.lookup_val  from sys_lookup t,sys_lookup_val s 
    where t.pid = s.lookup_id and 
    t.lookup_type='INTERMEDIARY';PARTNER_NAME
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
   dbms_output.put_line('pid: '||c_row.pid ||'  value:'||c_row.lookup_val);

  end loop;

end;