--添加项目来源（万通或者小科）
alter table biz_project
add (
  project_source   INTEGER
);


alter table biz_project modify(address varchar2(255));
alter table biz_project modify(MY_MAIN varchar2(255));

alter table biz_element_lend modify(PORPUSE varchar2(2048));

alter table biz_project_foreclosure modify(PAYMENT_ACCOUNT varchar2(255));
alter table biz_project_foreclosure modify(PAYMENT_NAME varchar2(255));
alter table biz_project_foreclosure modify(FORE_ACCOUNT varchar2(255));
alter table biz_project_foreclosure modify(SUPERVISE_ACCOUNT varchar2(255));
alter table biz_project_foreclosure modify(THIRD_BORROWER_ADDRESS varchar2(255));
alter table biz_project_foreclosure modify(THIRD_BORROWER_CARD varchar2(255));

alter table biz_project_property modify(BUYER_CARD_NO varchar2(255));
alter table biz_project_property modify(SELLER_CARD_NO varchar2(255));
--改为来源小科
update biz_project set project_source = 2;
commit;
--设置申请办理表外键可为空
ALTER TABLE BIZ_LOAN_APPLY_HANDLE_INFO MODIFY HANDLE_ID INTEGER NULL; 
alter table BIZ_LOAN_APPLY_HANDLE_INFO
add (
  project_id   INTEGER
);
--添加展期费率
alter table BIZ_PROJECT_EXTENSION
add (
  EXTENSION_RATE   number
);
--修补线上数据(胡益)
update Biz_Project_Guarantee set GUARANTEE_FEE =4538 where project_id = 11401 ;

update Biz_Project_Foreclosure set OLD_LOAN_BANK = 13730,OLD_LOAN_MONEY = 880000,NEW_LOAN_BANK = 13733,NEW_LOAN_MONEY = 2470000,FUNDS_MONEY = 910000,
SUPERVISE_DEPARTMENT = 13733,PAYMENT_TYPE =1,OLD_OWED_AMOUNT = 812463,OLD_LOAN_TIME = to_date('2041-01-10','yyyy-MM-DD'),FORE_ACCOUNT = '476693801881564501',SUPERVISE_ACCOUNT = '6226200600647771' where project_id = 11401;

update Biz_Project_Property set HOUSE_NAME ='嘉洲豪园二期1栋5-C',AREA = 80.67,COST_MONEY = 410874,TRANASCTION_MONEY = 3530000,SELLER_NAME='胡益、卓秋雪',SELLER_CARD_NO='362401198211023236/44030719850202482X',SELLER_PHONE='15817213914',
SELLER_ADDRESS='广东省深圳市嘉洲豪园503',BUYER_NAME='李赟',BUYER_CARD_NO = '440306198910210051',HOUSE_PROPERTY_CARD='3000639138',EVALUATION_PRICE = 3646284,FORE_RATE = 22.62 where project_id = 11401;
update biz_loan_apply_finance_handle set rec_money = 4538 where pid = 845;
commit;
--修补线上数据(薛扬)
update Biz_Project_Guarantee set GUARANTEE_FEE =3337 where project_id = 11399 ;

update Biz_Project_Foreclosure set OLD_LOAN_BANK = 13929,OLD_LOAN_MONEY = 980000,NEW_LOAN_BANK = 13729,NEW_LOAN_MONEY = 3450000,FUNDS_MONEY = 990000,
SUPERVISE_DEPARTMENT = 13729,PAYMENT_TYPE =1,OLD_OWED_AMOUNT = 906700,OLD_LOAN_TIME = to_date('2014-01-17','yyyy-MM-DD'),FORE_ACCOUNT = '6227007200050739533',SUPERVISE_ACCOUNT = '6217007200039711935' where project_id = 11399;

update Biz_Project_Property set HOUSE_NAME ='前海天朗风清家园A栋天芳园1001',AREA = 66.88,COST_MONEY = 365540,TRANASCTION_MONEY = 4930000,SELLER_NAME='薛扬',SELLER_CARD_NO='230203198206020031',SELLER_PHONE='15999534007',
SELLER_ADDRESS='广东省深圳市天朗风清天方园1001',BUYER_NAME='卢厚桢',BUYER_CARD_NO = '420123197311202479',HOUSE_PROPERTY_CARD='4000479407',EVALUATION_PRICE = 4514400,FORE_RATE = 20.16 where project_id = 11399;
update biz_loan_apply_finance_handle set rec_money = 3337 where pid = 824;
commit;

-------------------------------------------
--赎楼清单表
create table BIZ_PROJECT_FORE_INFORMATION 
(
   PID                INTEGER              not null,
   PROJECT_ID         INTEGER,
   FORE_ID            INTEGER,
   ORIGINAL_NUMBER    INTEGER,
   COPY_NUMBER        INTEGER,
   REMARK             VARCHAR2(2048),
   constraint BIZ_PROJECT_FORE_INFORMATION primary key (PID)
);

comment on table BIZ_PROJECT_FORE_INFORMATION is
'赎楼清单';

comment on column BIZ_PROJECT_FORE_INFORMATION.PID is
'主键';

comment on column BIZ_PROJECT_FORE_INFORMATION.PROJECT_ID is
'项目id';

comment on column BIZ_PROJECT_FORE_INFORMATION.FORE_ID is
'赎楼资料id';

comment on column BIZ_PROJECT_FORE_INFORMATION.ORIGINAL_NUMBER is
'原件份数';

comment on column BIZ_PROJECT_FORE_INFORMATION.COPY_NUMBER is
'复印件份数';

comment on column BIZ_PROJECT_FORE_INFORMATION.REMARK is
'备注';

create sequence SEQ_BIZ_PROJECT_FORE_INFO
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

-- 添加project_id值
declare
  -- Local variables here
  cursor c_job is
    select b.project_id, a.handle_id
      from biz_loan_apply_handle_info a
     inner join biz_loan_handle_info b
        on a.handle_id = b.pid;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
    update biz_loan_apply_handle_info t set t.project_id=c_row.project_id where t.handle_id=c_row.handle_id;

  end loop;
	commit;
end;
/


-- 添加赎楼清单表数据
declare 
  pid integer; 
  fore_id integer;
 cursor c_job is
    select pid from biz_project;
  c_row c_job%rowtype;
  
  cursor c_job1 is
    SELECT LV.PID AS FORE_ID
        FROM SYS_LOOKUP L,SYS_LOOKUP_VAL LV
        WHERE LV.STATUS = 1  AND L.PID = LV.LOOKUP_ID 
        AND L.LOOKUP_TYPE = 'PROJECT_FORE_INFORMATION';
  c_row1 c_job1%rowtype;
begin
 for c_row in c_job loop
     pid:=c_row.pid;
     for c_row1 in c_job1 loop 
         fore_id:=c_row1.fore_id;
         insert into biz_project_fore_information(pid,PROJECT_ID,fore_id) 
values(SEQ_BIZ_PROJECT_FORE_INFO.nextval,pid,fore_id);
     end loop;
  end loop;
  commit;
 end;
/


-- 添加业务办理信息
declare
  -- Local variables here
  cursor c_job is
    select a.pid from biz_project a  left join biz_loan_apply_handle_info b
on b.project_id=a.pid where b.pid is null and a.status =1 and a.foreclosure_status != 1 and a.is_chechan = 0;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
    insert into biz_loan_apply_handle_info (pid,project_id,creater_id) values(SEQ_APPLY_HANDLE.Nextval,c_row.pid,20172);

  end loop;
	commit;
end;
/