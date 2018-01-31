
--赎楼表增加字段
alter table biz_project_foreclosure
add (
  old_owed_amount         NUMBER,
  old_loan_time           TIMESTAMP(6),
  fore_account            VARCHAR2(55),
  supervise_account       VARCHAR2(55),
  third_borrower          VARCHAR2(55),
  third_borrower_card     VARCHAR2(55),
  third_borrower_phone    VARCHAR2(55),
  third_borrower_address  VARCHAR2(55)
);
--物业表增加字段
alter table biz_project_property add(evaluation_price NUMBER,fore_rate NUMBER);
alter table biz_project
add (
  business_source_no   INTEGER,
  collect_file_status  INTEGER default 1
);
-- 收件表
create table biz_Collect_files
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  status               INTEGER default 1,
  file_id       INTEGER,
  remark             VARCHAR2(255),
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
); 
-- Add comments to the table 
comment on table biz_Collect_files
  is '收件表';
-- Add comments to the columns 
comment on column biz_Collect_files.pid
  is '主键';   
comment on column biz_Collect_files.project_id
  is '项目id';  
comment on column biz_Collect_files.status
  is '状态：无效=-1,有效=1';  
comment on column biz_Collect_files.file_id
  is '文件id'; 
comment on column biz_Collect_files.remark
  is '备注'; 
comment on column biz_Collect_files.creater_id
  is '创建人id'; 
comment on column biz_Collect_files.creater_date
  is '创建时间'; 
comment on column biz_Collect_files.update_id
  is '更新人id'; 
comment on column biz_Collect_files.update_date
  is '更新时间';


-- 要件查档表
create table biz_Check_Document
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  check_status               INTEGER default 1,
  approval_status               INTEGER default 1,
  check_date          TIMESTAMP,
  remark             VARCHAR2(255),
  attachment_id       INTEGER,
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
);

-- Add comments to the table 
comment on table biz_Check_Document
  is '要件查档表';
-- Add comments to the columns 
comment on column biz_Check_Document.pid
  is '主键';  
comment on column biz_Check_Document.project_id
  is '项目id';  
comment on column biz_Check_Document.check_status
  is '查档状态：未查档=1,抵押=2,有效=3,无效=4,查封=5,抵押查封=6,轮候查封=7';  
comment on column biz_Check_Document.approval_status
  is '审批状态：未审批=1,不通过=2,通过=3';  
comment on column biz_Check_Document.check_date
  is '查档时间';  
comment on column biz_Check_Document.remark
  is '审批意见';  
comment on column biz_Check_Document.attachment_id
  is '附件id';  
comment on column biz_Check_Document.creater_id
  is '创建人id'; 
comment on column biz_Check_Document.creater_date
  is '创建时间'; 
comment on column biz_Check_Document.update_id
  is '更新人id'; 
comment on column biz_Check_Document.update_date
  is '更新时间';
   
-- 回款表
create table biz_loan_repayment
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  repayment_money           NUMBER(12,2),
  new_repayment_date       TIMESTAMP,
  status               INTEGER default 1,
  remark             VARCHAR2(255),
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
);
-- Add comments to the table 
comment on table biz_loan_repayment
  is '回款表';
-- Add comments to the columns 
comment on column biz_loan_repayment.pid
  is '主键';  
comment on column biz_loan_repayment.project_id
  is '项目id';  
comment on column biz_loan_repayment.repayment_money
  is '回款总金额';
comment on column biz_loan_repayment.new_repayment_date
  is '最新的回款时间';
comment on column biz_loan_repayment.remark
  is '备注';
comment on column biz_loan_repayment.status
  is '状态：未回款=1,已回款=2';
comment on column biz_loan_repayment.creater_id
  is '创建人id'; 
comment on column biz_loan_repayment.creater_date
  is '创建时间'; 
comment on column biz_loan_repayment.update_id
  is '更新人id'; 
comment on column biz_loan_repayment.update_date
  is '更新时间'; 
alter table biz_loan_repayment
add constraint FK_repay_p_pid foreign key (project_id)
references biz_project(pid);   


-- 回款记录表
create table biz_loan_repayment_record
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  repayment_id           INTEGER not null,
  repayment_money           NUMBER(12,2),
  repayment_date       TIMESTAMP,
  remark             VARCHAR2(255),
  creater_date       TIMESTAMP,
  creater_id       INTEGER
);
-- Add comments to the table 
comment on table biz_loan_repayment_record
  is '回款表';
-- Add comments to the columns 
comment on column biz_loan_repayment_record.pid
  is '主键';  
comment on column biz_loan_repayment_record.repayment_id
  is '回款id';  
comment on column biz_loan_repayment_record.project_id
  is '项目id';  
comment on column biz_loan_repayment_record.repayment_money
  is '回款金额';
comment on column biz_loan_repayment_record.repayment_date
  is '回款时间';
comment on column biz_loan_repayment_record.remark
  is '备注';
comment on column biz_loan_repayment_record.creater_id
  is '创建人id'; 
comment on column biz_loan_repayment_record.creater_date
  is '创建时间'; 
alter table biz_loan_repayment_record
add constraint FK_rr_p_pid foreign key (project_id)
references biz_project(pid); 
alter table biz_loan_repayment_record
add constraint FK_rr_r_pid foreign key (repayment_id)
references biz_loan_repayment(pid);     


-- 逾期费表
create table biz_loan_overdue_Fee
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  status               INTEGER default 1,
  account_no      VARCHAR2(55),
  bank_name             VARCHAR2(255),
  account_name       VARCHAR2(55),
  overdue_Fee           NUMBER(12,2),
  overdue_day           INTEGER,
  overdue_rate           NUMBER(12,2),
  payment_way               INTEGER,
  remark             VARCHAR2(255),
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
);
-- Add comments to the table 
comment on table biz_loan_overdue_Fee
  is '逾期费表';
-- Add comments to the columns 
comment on column biz_loan_overdue_Fee.pid
  is '主键';  
comment on column biz_loan_overdue_Fee.project_id
  is '项目id';  
comment on column biz_loan_overdue_Fee.account_name
  is '户名'; 
comment on column biz_loan_overdue_Fee.bank_name
  is '开户行';
comment on column biz_loan_overdue_Fee.account_no
  is '账号';
comment on column biz_loan_overdue_Fee.overdue_Fee
  is '逾期费';
comment on column biz_loan_overdue_Fee.overdue_day
  is '逾期天数';
comment on column biz_loan_overdue_Fee.overdue_rate
  is '逾期费率';
comment on column biz_loan_overdue_Fee.payment_way
  is '付款方式：从尾款扣=1,转账收费=2';
comment on column biz_loan_overdue_Fee.status
  is '状态：无效=-1,有效=1';
comment on column biz_loan_overdue_Fee.remark
  is '备注';
comment on column biz_loan_overdue_Fee.creater_id
  is '创建人id'; 
comment on column biz_loan_overdue_Fee.creater_date
  is '创建时间'; 
comment on column biz_loan_overdue_Fee.update_id
  is '更新人id'; 
comment on column biz_loan_overdue_Fee.update_date
  is '更新时间'; 
alter table biz_loan_overdue_Fee
add constraint FK_of_p_pid foreign key (project_id)
references biz_project(pid);  

CREATE SEQUENCE SEQ_Collect_files;
CREATE SEQUENCE SEQ_Check_Document;
CREATE SEQUENCE SEQ_repayment;
CREATE SEQUENCE SEQ_repayment_record;
CREATE SEQUENCE SEQ_overdue_Fee;

-- 2016-03-23 补充字段
alter table biz_loan_finance_handle
add (
  house_Clerk_Id        INTEGER
);
comment on column biz_loan_finance_handle.house_Clerk_Id
  is '赎楼员ID'; 
  
alter table BIZ_LOAN_HANDLE_INFO
add (
  foreclosure_status   INTEGER default 1
);
comment on column BIZ_LOAN_HANDLE_INFO.foreclosure_status
  is '赎楼状态：未赎楼=1，已赎楼=2';

alter table BIZ_LOAN_HOUSE_BALANCE
add (
  balance_confirm       INTEGER default 1
);
comment on column BIZ_LOAN_HOUSE_BALANCE.balance_confirm
  is '余额确认：未确认=1，已确认=2';