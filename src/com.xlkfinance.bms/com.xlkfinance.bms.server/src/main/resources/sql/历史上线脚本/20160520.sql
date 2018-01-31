-- 修改监管单位数据
declare
  -- Local variables here
  cursor c_job is
     select f.pid fid, f.supervise_department,s.pid sid 
    from biz_project_foreclosure f ,sys_lookup_val s ,sys_lookup l
where f.supervise_department like '%'|| s.lookup_desc and 
s.lookup_id = l.pid and l.lookup_type='BANK_NAME';
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
    update biz_project_foreclosure t set t.supervise_department=c_row.sid where t.pid = c_row.fid;
	commit;
  end loop;
end;
/
-- 修改新贷款银行
declare
  -- Local variables here
  cursor c_job is
    select p.pid as fid,s.pid as sid,s.lookup_val,b.pid as bankId,b.bank_name 
    from biz_project_foreclosure p,sys_lookup_val s,sys_bank_info b
 where p.new_loan_bank = to_char(s.pid) and s.lookup_val = b.bank_shorthand order by p.pid;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
   dbms_output.put_line('pid: '||c_row.fid ||'  value:'||c_row.sid ||'  value:'||c_row.bankid );
   update biz_project_foreclosure set new_loan_bank = c_row.bankid where pid = c_row.fid;
  commit;
   end loop;

end;
/
-- 修改原贷款银行
declare
  -- Local variables here
  cursor c_job is
    select p.pid as fid,s.pid as sid,s.lookup_val,b.pid as bankId,b.bank_name 
    from biz_project_foreclosure p,sys_lookup_val s,sys_bank_info b
 where p.old_loan_bank = to_char(s.pid) and s.lookup_val = b.bank_shorthand order by p.pid;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
   dbms_output.put_line('pid: '||c_row.fid ||'  value:'||c_row.sid ||'  value:'||c_row.bankid );
   update biz_project_foreclosure set old_loan_bank = c_row.bankid where pid = c_row.fid;
  	commit;
   end loop;

end;
/
-- 修改公积金银行
declare
  -- Local variables here
  cursor c_job is
    select p.pid as fid,s.pid as sid,s.lookup_val,b.pid as bankId,b.bank_name 
    from biz_project_foreclosure p,sys_lookup_val s,sys_bank_info b
 where p.old_loan_bank = to_char(s.pid) and s.lookup_val = b.bank_shorthand order by p.pid;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
   dbms_output.put_line('pid: '||c_row.fid ||'  value:'||c_row.sid ||'  value:'||c_row.bankid );
   update biz_project_foreclosure set old_loan_bank = c_row.bankid where pid = c_row.fid;
  commit;
   end loop;

end;
/
-- 修改监管单位数据
declare
  -- Local variables here
  cursor c_job is
    select p.pid as fid,s.pid as sid,s.lookup_val,b.pid as bankId,b.bank_name 
    from biz_project_foreclosure p,sys_lookup_val s,sys_bank_info b
 where p.supervise_department = to_char(s.pid) and s.lookup_val = b.bank_shorthand order by p.pid;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
   dbms_output.put_line('pid: '||c_row.fid ||'  value:'||c_row.sid ||'  value:'||c_row.bankid );
   update biz_project_foreclosure set supervise_department = c_row.bankid where pid = c_row.fid;
 commit;
   end loop;

end;
/