  
-- 业务处理主表
create table BIZ_LOAN_HANDLE_INFO
(
  pid                 INTEGER primary key,
  project_id          INTEGER not null,
  apply_handle_status INTEGER default 1,
  rec_status          INTEGER default 1,
  creater_id          INTEGER not null,
  creater_date   TIMESTAMP,
  foreclosure_status   INTEGER default 1,
  foreclosure_Turn_Down_remark  VARCHAR2(255)
);
-- Add comments to the table 
comment on table BIZ_LOAN_HANDLE_INFO
  is '业务处理主表';
-- Add comments to the columns 
comment on column BIZ_LOAN_HANDLE_INFO.pid
  is '主键';
comment on column BIZ_LOAN_HANDLE_INFO.project_id
  is '项目ID';
comment on column BIZ_LOAN_HANDLE_INFO.apply_handle_status
  is '申请办理状态：未申请=1,已申请=2,已完成（已解保）=3,已归档=4';
comment on column BIZ_LOAN_HANDLE_INFO.rec_status
  is '到账状态：未到账=1,已到账=2';
comment on column BIZ_LOAN_HANDLE_INFO.creater_date
  is '创建时间';
comment on column BIZ_LOAN_HANDLE_INFO.creater_id
  is '创建者ID';
comment on column BIZ_LOAN_HANDLE_INFO.foreclosure_status
  is '赎楼状态：未赎楼=1,已赎楼=2,已驳回=3';
comment on column BIZ_LOAN_HANDLE_INFO.foreclosure_Turn_Down_remark
  is '赎楼驳回备注';
alter table BIZ_LOAN_HANDLE_INFO
  add constraint FK_HI_P_PID foreign key (PROJECT_ID)
  references BIZ_PROJECT (PID);
alter table BIZ_LOAN_HANDLE_INFO
  add constraint FK_HI_U_PID foreign key (CREATER_ID)
  references SYS_USER (PID);
-- 申请业务处理信息
-- Create table
create table BIZ_LOAN_APPLY_HANDLE_INFO
(
  pid            INTEGER primary key,
  handle_id      INTEGER not null,
  sub_date       TIMESTAMP(6),
  contact_person VARCHAR2(55),
  contact_phone  VARCHAR2(55),
  handle_date    TIMESTAMP(6),
  special_case   VARCHAR2(2048),
  remark         VARCHAR2(2048),
  feedback       VARCHAR2(2048),
  creater_id     INTEGER not null
);
-- Add comments to the table 
comment on table BIZ_LOAN_APPLY_HANDLE_INFO
  is '申请业务处理信息';
-- Add comments to the columns 
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.pid
  is '主键';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.handle_id
  is '赎楼业务处理ID';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.sub_date
  is '合同及放款确认书提交时间';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.contact_person
  is '联系人';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.contact_phone
  is '联系电话';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.handle_date
  is '办理时间';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.special_case
  is '特殊情况说明';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.remark
  is '备注';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.feedback
  is '办理反馈';
comment on column BIZ_LOAN_APPLY_HANDLE_INFO.creater_id
  is '创建人ID';

alter table BIZ_LOAN_APPLY_HANDLE_INFO
  add constraint FK_AHI_HI_PID foreign key (HANDLE_ID)
  references BIZ_LOAN_HANDLE_INFO (PID);
alter table BIZ_LOAN_APPLY_HANDLE_INFO
  add constraint FK_AHI_U_PID foreign key (CREATER_ID)
  references SYS_USER (PID);
  
-- 赎楼及余额回转
create table BIZ_LOAN_HOUSE_BALANCE
(
  pid              INTEGER primary key,
  handle_id        INTEGER not null,
  principal        NUMBER(12,2),
  pay_date         TIMESTAMP(6),
  house_clerk      VARCHAR2(55),
  interest         NUMBER(12,2),
  default_interest NUMBER(12,2),
  balance          NUMBER(12,2),
  back_account     VARCHAR2(255),
  count            INTEGER,
  remark               VARCHAR2(2048),
  creater_id       INTEGER not null,
  balance_confirm       INTEGER default 1,
  HANDLE_USER_ID  INTEGER
);
-- Add comments to the table 
comment on table BIZ_LOAN_HOUSE_BALANCE
  is '赎楼及余额回转';
-- Add comments to the columns 
comment on column BIZ_LOAN_HOUSE_BALANCE.pid
  is '主键';
comment on column BIZ_LOAN_HOUSE_BALANCE.handle_id
  is '赎楼业务处理ID';
comment on column BIZ_LOAN_HOUSE_BALANCE.principal
  is '赎楼本金（付款金额）';
comment on column BIZ_LOAN_HOUSE_BALANCE.pay_date
  is '付款日期';
comment on column BIZ_LOAN_HOUSE_BALANCE.house_clerk
  is '赎楼员';
comment on column BIZ_LOAN_HOUSE_BALANCE.interest
  is '利息';
comment on column BIZ_LOAN_HOUSE_BALANCE.default_interest
  is '罚息';
comment on column BIZ_LOAN_HOUSE_BALANCE.balance
  is '赎楼余额';
comment on column BIZ_LOAN_HOUSE_BALANCE.back_account
  is '转回账号';
comment on column BIZ_LOAN_HOUSE_BALANCE.balance_confirm
  is '余额确认：未确认=1,已确认=2';
comment on column BIZ_LOAN_HOUSE_BALANCE.count
  is '赎楼次数';
comment on column BIZ_LOAN_HOUSE_BALANCE.remark
  is '备注';
comment on column BIZ_LOAN_HOUSE_BALANCE.creater_id
  is '创建人ID';
comment on column BIZ_LOAN_HOUSE_BALANCE.HANDLE_USER_ID
  is '办理人id';
alter table BIZ_LOAN_HOUSE_BALANCE
  add constraint FK_LHB_HI_PID foreign key (HANDLE_ID)
  references BIZ_LOAN_HANDLE_INFO (PID);
alter table BIZ_LOAN_HOUSE_BALANCE
  add constraint FK_LHB_U_PID foreign key (CREATER_ID)
  references SYS_USER (PID);

alter table BIZ_LOAN_HOUSE_BALANCE 
add (
    CREATER_DATE  TIMESTAMP
);
comment on column BIZ_LOAN_HOUSE_BALANCE.CREATER_DATE is 
'创建时间';
-- 办理流程条目
create table BIZ_LOAN_HANDLE_FLOW
(
  pid                INTEGER primary key,
  name               VARCHAR2(55),
  fix_day            INTEGER,
  advance_notice_day INTEGER,
  notice_type        INTEGER,
  one_level          INTEGER,
  two_level          INTEGER,
  three_level        INTEGER
);
-- Add comments to the table 
comment on table BIZ_LOAN_HANDLE_FLOW
  is '办理流程条目';
-- Add comments to the columns 
comment on column BIZ_LOAN_HANDLE_FLOW.pid
  is '主键';
comment on column BIZ_LOAN_HANDLE_FLOW.name
  is '办理流程名称';
comment on column BIZ_LOAN_HANDLE_FLOW.fix_day
  is '规定天数';
comment on column BIZ_LOAN_HANDLE_FLOW.advance_notice_day
  is '提前通知天数';
comment on column BIZ_LOAN_HANDLE_FLOW.notice_type
  is '提醒类型：有差异提醒备注一次=1,有差异每天都提醒写备注直到完成=2';
comment on column BIZ_LOAN_HANDLE_FLOW.one_level
  is '一级预警';
comment on column BIZ_LOAN_HANDLE_FLOW.two_level
  is '二级预警';
comment on column BIZ_LOAN_HANDLE_FLOW.three_level
  is '三级预警';
insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (1, '发放贷款', 1, 1, 1, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (2, '赎楼', 1, 2, 2, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (3, '取旧证', 1, 2, 2, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (4, '注销抵押', 1, 2, 2, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (5, '过户', 1, 1, 2, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (6, '取新证', 1, 2, 2, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (7, '抵押', 1, 2, 2, 2, 4, 6);

insert into biz_loan_handle_flow (PID, NAME, FIX_DAY, ADVANCE_NOTICE_DAY, NOTICE_TYPE, ONE_LEVEL, TWO_LEVEL, THREE_LEVEL)
values (10, '回款', 1, 2, 2, 2, 4, 6);
-- 办理动态
create table BIZ_LOAN_HANDLE_DYNAMIC
(
  pid                  INTEGER primary key,
  handle_id            INTEGER not null,
  handle_flow_id       INTEGER,
  finish_date          TIMESTAMP(6),
  handle_day           INTEGER,
  operator             VARCHAR2(55),
  differ               INTEGER,
  differ_monitor_count INTEGER,
  create_date          TIMESTAMP(6),
  remark               VARCHAR2(2048),
  creater_id           INTEGER not null，
  current_Handle_User_Id           INTEGER
);
-- Add comments to the table 
comment on table BIZ_LOAN_HANDLE_DYNAMIC
  is '办理动态';
-- Add comments to the columns 
comment on column BIZ_LOAN_HANDLE_DYNAMIC.pid
  is '主键';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.handle_id
  is '赎楼业务处理ID';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.handle_flow_id
  is '办理流程条目ID';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.finish_date
  is '完成日期';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.handle_day
  is '操作天数';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.operator
  is '操作人';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.differ
  is '差异';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.differ_monitor_count
  is '差异跟进次数';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.create_date
  is '创建时间';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.remark
  is '备注';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.creater_id
  is '创建人ID';
comment on column BIZ_LOAN_HANDLE_DYNAMIC.current_Handle_User_Id
  is '该办理动态的办理人';
alter table BIZ_LOAN_HANDLE_DYNAMIC
  add constraint FK_HD_HF_PID foreign key (HANDLE_FLOW_ID)
  references BIZ_LOAN_HANDLE_FLOW (PID);
alter table BIZ_LOAN_HANDLE_DYNAMIC
  add constraint FK_HD_HI_PID foreign key (HANDLE_ID)
  references BIZ_LOAN_HANDLE_INFO (PID);
alter table BIZ_LOAN_HANDLE_DYNAMIC
  add constraint FK_HD_U_PID foreign key (CREATER_ID)
  references SYS_USER (PID);

-- 业务办理和任务关联
create table biz_handle_WORKFLOW
(
  pid                  INTEGER primary key,
  handle_id    INTEGER,
  EXECUTION_ID              VARCHAR2(55),
  project_id              INTEGER,
  STATUS        INTEGER default 1
);
-- Add comments to the table 
comment on table biz_handle_WORKFLOW
  is '业务办理和任务关联';
-- Add comments to the columns 
comment on column biz_handle_WORKFLOW.pid
  is '主键'; 
comment on column biz_handle_WORKFLOW.handle_id
  is '业务办理ID'; 
comment on column biz_handle_WORKFLOW.EXECUTION_ID
  is '流程实例ID'; 
comment on column biz_handle_WORKFLOW.status
  is '状态：有效=1,无效=-1';  
alter table biz_handle_WORKFLOW
  add constraint FK_HW_HI_PID foreign key (handle_id)
  references BIZ_LOAN_HANDLE_INFO (PID);  
alter table biz_handle_WORKFLOW
  add constraint FK_HW_P_PID foreign key (project_id)
  references BIZ_PROJECT (PID); 
--办理动态文件
create table BIZ_LOAN_HANDLE_DYNAMIC_FILE
(
  pid                        INTEGER primary key,
  handle_dynamic_id INTEGER,
  file_id                    INTEGER
);
-- Add comments to the table 
comment on table BIZ_LOAN_HANDLE_DYNAMIC_FILE
  is '办理动态文件';
-- Add comments to the columns 
comment on column BIZ_LOAN_HANDLE_DYNAMIC_FILE.pid
  is '主键';
comment on column BIZ_LOAN_HANDLE_DYNAMIC_FILE.handle_dynamic_id
  is '办理动态ID';
comment on column BIZ_LOAN_HANDLE_DYNAMIC_FILE.file_id
  is '文件ID';

alter table BIZ_LOAN_HANDLE_DYNAMIC_FILE
  add constraint FK_HDF_F_PID foreign key (FILE_ID)
  references BIZ_FILE (PID);
alter table BIZ_LOAN_HANDLE_DYNAMIC_FILE
  add constraint FK_HDF_HD_PID foreign key (handle_dynamic_id)
  references BIZ_LOAN_HANDLE_DYNAMIC (PID); 

-- 差异预警处理表
create table BIZ_LOAN_HANDLE_DIFFER_WARN
(
  pid               INTEGER primary key,
  handle_dynamic_id INTEGER not null,
  project_id       INTEGER not null,
  project_name      VARCHAR2(55),
  handle_type       INTEGER，
  differ            INTEGER,
  status            INTEGER default 1,
  handle_date       TIMESTAMP(6),
  handle_author     INTEGER,
  create_date       TIMESTAMP(6),
  remark            VARCHAR2(2048)
);
-- Add comments to the table 
comment on table BIZ_LOAN_HANDLE_DIFFER_WARN
  is '差异预警处理表';
-- Add comments to the columns 
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.pid
  is '主键';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.handle_dynamic_id
  is '办理动态ID';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.project_id
  is '项目ID';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.project_name
  is '项目名称ID';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.differ
  is '差异';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.status
  is '状态：失效=-1,未处理=1,已处理=2';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.handle_date
  is '处理时间';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.handle_author
  is '处理者';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.create_date
  is '创建时间';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.remark
  is '备注';
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.handle_type
  is '处理类型：必须处理=1，非必须处理=2';
  
alter table BIZ_LOAN_HANDLE_DIFFER_WARN
  add constraint FK_HDW_HD_PID foreign key (HANDLE_DYNAMIC_ID)
  references BIZ_LOAN_HANDLE_DYNAMIC (PID);
alter table BIZ_LOAN_HANDLE_DIFFER_WARN
  add constraint FK_HDW_p_PID foreign key (project_id)
  references biz_project (PID);
-- 差异预警历史处理表
create table BIZ_HIS_HANDLE_DIFFER_WARN
(
  pid               INTEGER primary key,
  handle_dynamic_id INTEGER not null,
  project_id       INTEGER not null,
  project_name      VARCHAR2(55),
  handle_type       INTEGER，
  differ            INTEGER,
  handle_date       TIMESTAMP(6),
  handle_author     INTEGER,
  create_date       TIMESTAMP(6),
  remark            VARCHAR2(2048)
);
-- Add comments to the table 
comment on table BIZ_HIS_HANDLE_DIFFER_WARN
  is '差异预警历史处理表';
-- Add comments to the columns 
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.pid
  is '主键';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.handle_dynamic_id
  is '办理动态ID';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.project_id
  is '项目ID';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.project_name
  is '项目名称ID';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.differ
  is '差异';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.handle_date
  is '处理时间';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.handle_author
  is '处理者';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.create_date
  is '创建时间';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.remark
  is '备注';
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.handle_type
  is '处理类型：必须处理=1，非必须处理=2';
alter table BIZ_HIS_HANDLE_DIFFER_WARN
  add constraint FK_HHW_HD_PID foreign key (HANDLE_DYNAMIC_ID)
  references BIZ_LOAN_HANDLE_DYNAMIC (PID);
alter table BIZ_HIS_HANDLE_DIFFER_WARN
  add constraint FK_HHW_p_PID foreign key (project_id)
  references biz_project (PID);  
  -- 财务退款明细
create table BIZ_LOAN_REFUND_DETAILS
(
  pid          INTEGER primary key,
  handle_id    INTEGER not null,
  refund_pro   INTEGER not null,
  refund_money NUMBER(12,2),
  rec_account  VARCHAR2(255),
  rec_name     VARCHAR2(55),
  pay_date     TIMESTAMP(6),
  operator     VARCHAR2(55),
  creater_id   INTEGER not null
);
-- Add comments to the table 
comment on table BIZ_LOAN_REFUND_DETAILS
  is '财务退款明细';
-- Add comments to the columns 
comment on column BIZ_LOAN_REFUND_DETAILS.pid
  is '主键';
comment on column BIZ_LOAN_REFUND_DETAILS.handle_id
  is '赎楼业务处理ID';
comment on column BIZ_LOAN_REFUND_DETAILS.refund_pro
  is '退款项目:监管资金转出=1,退付余额=2,应付佣金=3,应付手续费=4';
comment on column BIZ_LOAN_REFUND_DETAILS.refund_money
  is '退款金额';
comment on column BIZ_LOAN_REFUND_DETAILS.rec_account
  is '收款账号';
comment on column BIZ_LOAN_REFUND_DETAILS.rec_name
  is '收款户名';
comment on column BIZ_LOAN_REFUND_DETAILS.pay_date
  is '付款日期';
comment on column BIZ_LOAN_REFUND_DETAILS.operator
  is '操作员';
comment on column BIZ_LOAN_REFUND_DETAILS.creater_id
  is '创建人ID';

alter table BIZ_LOAN_REFUND_DETAILS
  add constraint FK_RD_HI_PID foreign key (HANDLE_ID)
  references BIZ_LOAN_HANDLE_INFO (PID);
alter table BIZ_LOAN_REFUND_DETAILS
  add constraint FK_RD_U_PID foreign key (CREATER_ID)
  references SYS_USER (PID);
 

-- 财务受理
create table biz_loan_finance_handle
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  status        INTEGER default 1,
  house_Clerk_Id        INTEGER,
  creater_id    INTEGER  not null,
  creater_date TIMESTAMP
);

-- Add comments to the table 
comment on table biz_loan_finance_handle
  is '财务受理信息';
-- Add comments to the columns 
comment on column biz_loan_finance_handle.pid
  is '主键'; 
comment on column biz_loan_finance_handle.project_id
  is '项目ID';   
comment on column biz_loan_finance_handle.status
  is '收费状态：未收费=1,已收费=2,已放款=3';
comment on column biz_loan_finance_handle.house_Clerk_Id
  is '赎楼员ID'; 
comment on column biz_loan_finance_handle.creater_id
  is '创建者ID';
comment on column biz_loan_finance_handle.creater_date
  is '创建时间';
alter table biz_loan_finance_handle
add constraint FK_fh_p_pid foreign key (project_id)
references biz_project(pid);  
alter table biz_loan_finance_handle
add constraint FK_fh_u_pid foreign key (creater_id)
references sys_user(pid); 

-- 申请财务受理信息
create table biz_loan_apply_finance_handle
(
  pid                  INTEGER PRIMARY KEY,
  finance_handle_id           INTEGER not null,
  rec_pro      INTEGER,
  rec_money       NUMBER(12,2),
  rec_account      VARCHAR2(255),
  rec_date         TIMESTAMP,
  PRO_RESOURCE             VARCHAR2(255),
  "OPERATOR"            VARCHAR2(55),
  remark        VARCHAR2(2048),
  creater_id    INTEGER not null,
  update_date         TIMESTAMP
);

-- Add comments to the table 
comment on table biz_loan_apply_finance_handle
  is '申请财务受理信息';
-- Add comments to the columns 
comment on column biz_loan_apply_finance_handle.pid
  is '主键';       
comment on column biz_loan_apply_finance_handle.finance_handle_id
  is '财务受理ID';  
comment on column biz_loan_apply_finance_handle.rec_pro
  is '收款项目:咨询费=1，手续费=2，第一次放款=3，一次赎楼余额转二次放款=4，第二次放款=5，监管（客户）资金转入=6，佣金=7，展期费=8';
-- 咨询费=1，手续费=2，第一次放款=3，一次赎楼余额转二次放款=4，第二次放款=5，监管（客户）资金转入=6，佣金=7，展期费=8;  
comment on column biz_loan_apply_finance_handle.rec_money
  is '收款金额';  
comment on column biz_loan_apply_finance_handle.rec_account
  is '收款账号';  
comment on column biz_loan_apply_finance_handle.rec_date
  is '收款日期';  
comment on column biz_loan_apply_finance_handle.PRO_RESOURCE
  is '款项来源';  
comment on column biz_loan_apply_finance_handle."OPERATOR"
  is '操作员'; 
comment on column biz_loan_apply_finance_handle.remark
  is '备注';
comment on column biz_loan_apply_finance_handle.creater_id
  is '创建者ID';
comment on column biz_loan_apply_finance_handle.update_date
  is '更新时间';
alter table biz_loan_apply_finance_handle
add constraint FK_afh_p_pid foreign key (finance_handle_id)
references biz_loan_finance_handle(pid); 
alter table biz_loan_apply_finance_handle
add constraint FK_afh_u_pid foreign key (creater_id)
references sys_user(pid); 
comment on column biz_loan_apply_finance_handle.rec_pro
  is '收款项目:咨询费=1,手续费=2,第一次放款=3,一次赎楼余额转二次放款=4,第二次放款=5,监管（客户）资金转入=6,佣金=7,展期费=8';
--放款增加项目来源明细
--放款增加财务审批工作流
ALTER TABLE BIZ_LOAN_APPLY_FINANCE_HANDLE
ADD (
 PRO_RESOURCE_MONEY           NUMBER(12,2),
 PRO_RESOURCE_MONEY_2         NUMBER(12,2),
 PRO_RESOURCE_2               VARCHAR2(255),
 APPLY_STATUS                 INTEGER DEFAULT 1,
 FUND_MANAGER_REMARK          VARCHAR2(1024),
 FINANCE_DIRECTOR_REMARK      VARCHAR2(1024)
);
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.PRO_RESOURCE
  IS '款项来源1';
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.PRO_RESOURCE_MONEY
  IS '款项来源1：金额';
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.PRO_RESOURCE_2
  IS '款项来源2';
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.PRO_RESOURCE_MONEY_2
  IS '款项来源2：金额';
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.FUND_MANAGER_REMARK
  IS '资金主管审批意见';
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.FINANCE_DIRECTOR_REMARK
  IS '财务总监审批意见';
COMMENT ON COLUMN BIZ_LOAN_APPLY_FINANCE_HANDLE.APPLY_STATUS
  IS '申请状态：未申请=1,申请中=2,驳回=3,审核中=4,审核通过=5,审核不通过=6,已归档=7'; 
  
COMMENT ON COLUMN BIZ_LOAN_FINANCE_HANDLE.STATUS
  IS '收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4，放款申请中=5';  
-- 退款信息表
create table biz_loan_refund_fee
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  apply_status               INTEGER default 1,
  confirm_MONEY             NUMBER(12,2),
  is_confirm               INTEGER default 1,
  confirm_date               TIMESTAMP,
  type               INTEGER,
  return_fee              NUMBER(12,2),
  rec_account      VARCHAR2(55),
  bank_name             VARCHAR2(255),
  rec_account_name       VARCHAR2(55),
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
);

-- Add comments to the table 
comment on table biz_loan_refund_fee
  is '退款信息表';
-- Add comments to the columns 
comment on column biz_loan_refund_fee.pid
  is '主键'; 
comment on column biz_loan_refund_fee.project_id
  is '项目ID'; 
comment on column biz_loan_refund_fee.apply_status
  is '申请状态：未申请=1,申请中=2,驳回=3,审核中=4,审核通过=5,审核不通过=6,已归档=7';  
comment on column biz_loan_refund_fee.confirm_MONEY
  is '确认金额';
comment on column biz_loan_refund_fee.is_confirm
  is '退款确认：未确认=1,已确认=2';
comment on column biz_loan_refund_fee.confirm_date
  is '确认日期';
comment on column biz_loan_refund_fee.type
  is '退款类型：退手续费=1,退咨询费=2,退尾款=3,退佣金=4';  
comment on column biz_loan_refund_fee.return_fee
  is '退费金额';  
comment on column biz_loan_refund_fee.rec_account_name
  is '收款人户名'; 
comment on column biz_loan_refund_fee.bank_name
  is '开户行';
comment on column biz_loan_refund_fee.rec_account
  is '收款人账号'; 
comment on column biz_loan_refund_fee.creater_id
  is '创建人id'; 
comment on column biz_loan_refund_fee.creater_date
  is '创建时间'; 
comment on column biz_loan_refund_fee.update_id
  is '更新人id'; 
comment on column biz_loan_refund_fee.update_date
  is '更新时间'; 

alter table biz_loan_refund_fee
add constraint FK_rf_p_pid foreign key (project_id)
references biz_project(pid);  

-- 收件表
create table biz_Collect_files
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  fname             VARCHAR2(255),
  fcode             VARCHAR2(255),
  collect_Date             TIMESTAMP,
  status               INTEGER default 1,
  remark             VARCHAR2(2048),
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP,
  BUYER_SELLER_TYPE   INTEGER,
  BUYER_SELLER_NAME   VARCHAR2(55)，
  REFUND_DATE   TIMESTAMP,
  REFUND_STATUS   INTEGER
); 
-- Add comments to the table 
comment on table biz_Collect_files
  is '收件表';
-- Add comments to the columns 
comment on column biz_Collect_files.pid
  is '主键';   
comment on column biz_Collect_files.project_id
  is '项目id';  
COMMENT ON COLUMN biz_Collect_files.fname
  IS '收件名称';
COMMENT ON COLUMN biz_Collect_files.fcode
  IS '收件编码';
COMMENT ON COLUMN biz_Collect_files.collect_Date
  IS '收件日期';
comment on column biz_Collect_files.status
  is '状态：无效=-1,有效=1';  
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
comment on column biz_Collect_files.BUYER_SELLER_TYPE
  is '买卖类型：买方=1，卖方=2';
comment on column biz_Collect_files.BUYER_SELLER_NAME
  is '买卖方姓名';
comment on column biz_Collect_files.REFUND_DATE
  is '退件时间';
comment on column biz_Collect_files.REFUND_STATUS
  is '退件状态：未退件=1，已退件=2';
alter table biz_Collect_files
add constraint FK_cf_p_pid foreign key (project_id)
references biz_project(pid);
-- 要件查档表
create table biz_Check_Document
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  check_status               INTEGER default 1,
  approval_status               INTEGER default 1,
  check_date          TIMESTAMP,
  remark             VARCHAR2(2048),
  attachment_id       INTEGER,
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP,
  re_check_status  INTEGER,
  re_check_remark  VARCHAR2(2048)，
  RE_CHECK_DATE    TIMESTAMP
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
  is '审批状态：未审批=1,不通过=2,通过=3,重新查档=4';  
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
comment on column biz_Check_Document.re_check_status
 is  '查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4';
comment on column biz_Check_Document.re_check_remark 
 is  '查档复核备注';
comment on column biz_Check_Document.RE_CHECK_DATE 
 is  '查档复核时间'; 
alter table biz_Check_Document
add constraint FK_cd_p_pid foreign key (project_id)
references biz_project(pid);

--万通需求:查档复核
alter table BIZ_CHECK_DOCUMENT 
add (
  RE_CHECK_user_id    INTEGER
);
comment on column biz_Check_Document.RE_CHECK_user_id is
'查档复核人id'; 
-- 回款表
create table biz_loan_repayment
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  repayment_money           NUMBER(12,2),
  new_repayment_date       TIMESTAMP,
  status               INTEGER default 1, 
  remark             VARCHAR2(2048),
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
  account_no             VARCHAR2(255),
  remark             VARCHAR2(2048),
  creater_date       TIMESTAMP,
  creater_id       INTEGER
);
-- Add comments to the table 
comment on table biz_loan_repayment_record
  is '回款记录表';
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
comment on column biz_loan_repayment_record.account_no
  is '回款账号';
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
  is_confirm               INTEGER default 1,
  payment_way               INTEGER,
  remark             VARCHAR2(2048),
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
comment on column biz_loan_overdue_Fee.is_confirm
  is '逾期费确认：未确认=1,已确认=2';
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

create table sys_user_sign_info 
(
   PID                 NUMBER               not null,
   user_id            NUMBER,
   create_date        TIMESTAMP,
   constraint PK_SYS_USER_SIGN_INFO primary key (PID)
);
comment on table sys_user_sign_info is
'系统用户签到信息';
comment on column sys_user_sign_info.PID is
'签到编号';
comment on column sys_user_sign_info.user_id is
'用户编号';
comment on column sys_user_sign_info.create_date is
'签到日期';
alter table sys_user_sign_info
  add constraint FK_sign_U_PID foreign key (user_id)
  references SYS_USER (PID);
--业务办理动态评级管理
create table biz_DYNAMIC_evaluate_info 
(
   pid                 NUMBER               not null,
   project_id         NUMBER,
   biz_manger_id      number,
   handle_dynamic_id         number,
   category           number,
   create_date        timestamp,
   constraint PK_biz_DYNAMIC_evaluate_info primary key (pid)
);

comment on table biz_DYNAMIC_evaluate_info is
'业务办理动态评级管理';
comment on column biz_DYNAMIC_evaluate_info.pid is
'评价编号';
comment on column biz_DYNAMIC_evaluate_info.project_id is
'项目编号';
comment on column biz_DYNAMIC_evaluate_info.biz_manger_id is
'业务经理(评价人)';
comment on column biz_DYNAMIC_evaluate_info.handle_dynamic_id is
'办理流程编号,对应办理动态ID';
comment on column biz_DYNAMIC_evaluate_info.category is
'评价类型,1表示点赞,2表示差评';
comment on column biz_DYNAMIC_evaluate_info.create_date is
'评价日期';
alter table biz_DYNAMIC_evaluate_info
add constraint FK_dei_p_pid foreign key (project_id)
references biz_project(pid);
-- 查诉讼表
create table biz_Check_litigation
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  check_status               INTEGER default 1,
  approval_status               INTEGER default 1,
  check_date          TIMESTAMP,
  remark             VARCHAR2(2048),
  attachment_id       INTEGER,
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
);

-- Add comments to the table 
comment on table biz_Check_litigation
  is '查诉讼表';
-- Add comments to the columns 
comment on column biz_Check_litigation.pid
  is '主键';  
comment on column biz_Check_litigation.project_id
  is '项目id';  
comment on column biz_Check_litigation.check_status
  is '诉讼状态：未查诉讼=1,无新增诉讼=2,有增诉讼=3,有新增诉讼非本人=4';  
comment on column biz_Check_litigation.approval_status
  is '审批状态：未审批=1,不通过=2,通过=3,重新查诉讼=4';  
comment on column biz_Check_litigation.check_date
  is '查法院网时间';  
comment on column biz_Check_litigation.remark
  is '审批意见';  
comment on column biz_Check_litigation.attachment_id
  is '附件id';  
comment on column biz_Check_litigation.creater_id
  is '创建人id'; 
comment on column biz_Check_litigation.creater_date
  is '创建时间'; 
comment on column biz_Check_litigation.update_id
  is '更新人id'; 
comment on column biz_Check_litigation.update_date
  is '更新时间';
alter table biz_Check_litigation
add constraint FK_cl_p_pid foreign key (project_id)
references biz_project(pid);  
--中途划转
create table biz_Intermediate_transfer
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  apply_status               INTEGER default 1,
  special_type               INTEGER default 1,
  rec_money             NUMBER(12,2),
  rec_account      VARCHAR2(55),
  rec_account_name      VARCHAR2(55),
  rec_date          TIMESTAMP,
  transfer_money             NUMBER(12,2),
  transfer_account      VARCHAR2(55),
  transfer_account_name      VARCHAR2(55),
  transfer_date          TIMESTAMP,
  remark             VARCHAR2(2048),
  creater_date       TIMESTAMP,
  creater_id       INTEGER,
  update_id        INTEGER,
  update_date      TIMESTAMP
);

-- Add comments to the table 
comment on table biz_Intermediate_transfer
  is '中途划转表';
-- Add comments to the columns 
comment on column biz_Intermediate_transfer.pid
  is '主键'; 
comment on column biz_Intermediate_transfer.project_id
  is '项目ID'; 
comment on column biz_Intermediate_transfer.apply_status
  is '申请状态：未申请=1,申请中=2,驳回=3,审核中=4,审核通过=5,审核不通过=6,已归档=7';  
comment on column biz_Intermediate_transfer.special_type
  is '特殊情况类型：客户自由资金到账=1,客户首期款优先=2';   
comment on column biz_Intermediate_transfer.rec_money
  is '到账金额';  
comment on column biz_Intermediate_transfer.rec_account
  is '到账账号'; 
comment on column biz_Intermediate_transfer.rec_account_name
  is '到账账号户名';
comment on column biz_Intermediate_transfer.rec_date
  is '到账时间';
comment on column biz_Intermediate_transfer.transfer_money
  is '划出金额';  
comment on column biz_Intermediate_transfer.transfer_account
  is '划出账号'; 
comment on column biz_Intermediate_transfer.transfer_account_name
  is '划出账户户名'; 
comment on column biz_Intermediate_transfer.transfer_date
  is '划出时间';
comment on column biz_Intermediate_transfer.remark
  is '备注';
comment on column biz_Intermediate_transfer.creater_id
  is '创建人id'; 
comment on column biz_Intermediate_transfer.creater_date
  is '创建时间'; 
comment on column biz_Intermediate_transfer.update_id
  is '更新人id'; 
comment on column biz_Intermediate_transfer.update_date
  is '更新时间';
alter table biz_Intermediate_transfer
add constraint FK_it_p_pid foreign key (project_id)
references biz_project(pid);     
/* -- APP版本信息（版本升级）
CREATE TABLE SYS_APP_VERSION_INFO
(
  PID                 INTEGER PRIMARY KEY,
  APP_NAME            VARCHAR2(55),
  APP_DESCRIPTION            VARCHAR2(2048),
  APP_VERSION             VARCHAR2(20),
  STATUS              INTEGER,
  FILE_ID              INTEGER,
  DOWNLOAN_COUNT       INTEGER,
  CREATER_DATE       TIMESTAMP,
  CREATER_ID       INTEGER,
  UPDATE_ID        INTEGER,
  UPDATE_DATE      TIMESTAMP
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE SYS_APP_VERSION_INFO
  IS 'APP版本信息（版本升级）';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN SYS_APP_VERSION_INFO.APP_NAME
  IS 'app名称';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.APP_DESCRIPTION
  IS '描述';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.APP_VERSION
  IS '版本';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.STATUS
  IS '状态';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.FILE_ID
  IS '文件ID';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.DOWNLOAN_COUNT
  IS '下载次数';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.CREATER_DATE
  IS '创建时间';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.CREATER_ID
  IS '创建者';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.UPDATE_ID
  IS '更新者';
COMMENT ON COLUMN SYS_APP_VERSION_INFO.UPDATE_DATE
  IS '更新时间';*/


--执行岗备注
CREATE TABLE BIZ_LOAN_PERFORM_JOB_REMARK
(
   PID                 INTEGER PRIMARY KEY,
   PROJECT_ID         INTEGER,
   REMARK             VARCHAR2(2048),
   REMARK_DATE             TIMESTAMP,
   STATUS             INTEGER default 1,
   UPDATE_DATE        TIMESTAMP,
   UPDATE_ID        INTEGER
);

COMMENT ON TABLE BIZ_LOAN_PERFORM_JOB_REMARK IS
'执行岗备注';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.PROJECT_ID IS
'项目ID';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.REMARK IS
'备注';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.REMARK_DATE IS
'备注时间';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.STATUS IS
'状态：未备注=1，已备注=2';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.UPDATE_DATE IS
'更新时间';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.UPDATE_ID IS
'更新者';
alter table BIZ_LOAN_PERFORM_JOB_REMARK
add constraint FK_pjob_p_pid foreign key (project_id)
references biz_project(pid);
--问题反馈
CREATE TABLE SYS_PROBLEM_FEEDBACK
(
   PID                 INTEGER PRIMARY KEY,
   FEEDBACK_CONTENT    VARCHAR2(2048),
   PROBLEM_source      INTEGER,
   CREATER_DATE        TIMESTAMP,
   CREATER_ID          INTEGER
);
COMMENT ON TABLE SYS_PROBLEM_FEEDBACK IS
'问题反馈';
COMMENT ON COLUMN SYS_PROBLEM_FEEDBACK.PID
  IS 'ID';
COMMENT ON COLUMN SYS_PROBLEM_FEEDBACK.FEEDBACK_CONTENT
  IS '反馈内容';
COMMENT ON COLUMN SYS_PROBLEM_FEEDBACK.PROBLEM_source
  IS '问题来源：PC=1,安卓=2,IOS=3';
COMMENT ON COLUMN SYS_PROBLEM_FEEDBACK.CREATER_DATE
  IS '创建时间';
COMMENT ON COLUMN SYS_PROBLEM_FEEDBACK.CREATER_ID
  IS '创建者';
alter table SYS_PROBLEM_FEEDBACK
  add constraint FK_pf_U_PID foreign key (CREATER_ID)
  references SYS_USER (PID);
--业务动态
CREATE TABLE BIZ_DYNAMIC
(
   PID                 INTEGER PRIMARY KEY,
   PROJECT_ID          INTEGER,
   MODUEL_NUMBER      INTEGER,
   DYNAMIC_NUMBER      INTEGER,
   DYNAMIC_NAME      VARCHAR2(255),
   PARENT_DYNAMIC_NUMBER      INTEGER,
   STATUS              INTEGER default 1,
   FINISH_DATE         TIMESTAMP,
   REMARK              LONG,
   CREATER_DATE        TIMESTAMP,
   UPDATE_DATE        TIMESTAMP，
   handle_author_ID      INTEGER
);
COMMENT ON TABLE BIZ_DYNAMIC IS
'业务动态';
COMMENT ON COLUMN BIZ_DYNAMIC.PID
  IS 'ID';
COMMENT ON COLUMN BIZ_DYNAMIC.PROJECT_ID
  IS '项目ID';
COMMENT ON COLUMN BIZ_DYNAMIC.MODUEL_NUMBER
  IS '模块编号';
COMMENT ON COLUMN BIZ_DYNAMIC.DYNAMIC_NUMBER
  IS '动态编号';
COMMENT ON COLUMN BIZ_DYNAMIC.DYNAMIC_NAME
  IS '动态名';
COMMENT ON COLUMN BIZ_DYNAMIC.PARENT_DYNAMIC_NUMBER
  IS '父级动态编号';
COMMENT ON COLUMN BIZ_DYNAMIC.STATUS
  IS '状态：未完成=1，进行中=2，已完成=3，失效=4';
COMMENT ON COLUMN BIZ_DYNAMIC.FINISH_DATE
  IS '完成时间';
COMMENT ON COLUMN BIZ_DYNAMIC.REMARK
  IS '备注';
COMMENT ON COLUMN BIZ_DYNAMIC.CREATER_DATE
  IS '创建时间';
COMMENT ON COLUMN BIZ_DYNAMIC.UPDATE_DATE
  IS '更新时间';
comment on column BIZ_DYNAMIC.handle_author_ID
  is '处理用户id';
alter table BIZ_DYNAMIC
add constraint FK_dyn_p_pid foreign key (project_id)
references biz_project(pid);


--查档文件文件
CREATE TABLE BIZ_LOAN_CHECK_DOCUMENT_FILE
(
  PID                        INTEGER PRIMARY KEY,
  FILE_CATEGORY                 INTEGER,
  PROJECT_ID                 INTEGER,
  FILE_ID                    INTEGER
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE BIZ_LOAN_CHECK_DOCUMENT_FILE
  IS '查档文件关联';
-- ADD COMMENTS TO THE COLUMNS 
COMMENT ON COLUMN BIZ_LOAN_CHECK_DOCUMENT_FILE.PID
  IS '主键';
COMMENT ON COLUMN BIZ_LOAN_CHECK_DOCUMENT_FILE.FILE_CATEGORY
  IS '文件类型：查档=1，查档复合=2';
COMMENT ON COLUMN BIZ_LOAN_CHECK_DOCUMENT_FILE.PROJECT_ID
  IS '项目ID';
COMMENT ON COLUMN BIZ_LOAN_CHECK_DOCUMENT_FILE.FILE_ID
  IS '文件ID';
alter table BIZ_LOAN_CHECK_DOCUMENT_FILE
add constraint FK_CDF_p_pid foreign key (PROJECT_ID)
references biz_project(pid);   
   --收费历史表
CREATE TABLE BIZ_LOAN_COLLECT_FEE_HIS
(
   PID                 INTEGER PRIMARY KEY,
   FINANCE_HANDLE_ID   INTEGER,
   PROJECT_ID          INTEGER,
   CONSULTING_FEE      NUMBER(12,2),
   POUNDAGE            NUMBER(12,2),
   BROKERAGE           NUMBER(12,2),
   REC_DATE            TIMESTAMP,
   PRO_RESOURCE        VARCHAR2(255),
   REC_ACCOUNT          VARCHAR2(255),
   CREATER_ID          INTEGER,
   CREATER_DATE        TIMESTAMP
);
-- ADD COMMENTS TO THE TABLE 
COMMENT ON TABLE BIZ_LOAN_COLLECT_FEE_HIS
  IS '收费历史表';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.PID
  IS 'ID';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.PROJECT_ID
  IS '项目ID';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.FINANCE_HANDLE_ID
  IS '财务办理ID';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.CONSULTING_FEE
  IS '咨询费';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.POUNDAGE
  IS '手续费';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.BROKERAGE
  IS '佣金';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.REC_DATE
  IS '收费日期';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.REC_DATE
  IS '款项来源';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.REC_DATE
  IS '收费账号';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.CREATER_ID
  IS '操作人';
COMMENT ON COLUMN BIZ_LOAN_COLLECT_FEE_HIS.CREATER_DATE
  IS '操作日期';
alter table BIZ_LOAN_COLLECT_FEE_HIS
add constraint FK_CFHIS_p_pid foreign key (project_id)
references biz_project(pid); 
alter table BIZ_LOAN_COLLECT_FEE_HIS
add constraint FK_CFHIS_hp_pid foreign key (finance_handle_id)
references biz_loan_finance_handle(pid); 
   --放款延期记录
CREATE TABLE BIZ_LOAN_SUSPEND_RECORD
(
   PID                 INTEGER PRIMARY KEY,
   PROJECT_ID          INTEGER,
   START_DATE        TIMESTAMP,
   END_DATE        TIMESTAMP,
   SUSPEND_DAY          INTEGER,
   REMARK              NVARCHAR2(500),
   CREATER_ID          INTEGER,
   CREATER_DATE        TIMESTAMP
);
COMMENT ON TABLE BIZ_LOAN_SUSPEND_RECORD
  IS '放款延期记录';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.PID
  IS 'ID';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.PROJECT_ID
  IS '项目ID';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.START_DATE
  IS '开始日期';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.END_DATE
  IS '结束日期';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.SUSPEND_DAY
  IS '挂起天数';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.REMARK
  IS '备注';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.CREATER_ID
  IS '操作人';
COMMENT ON COLUMN BIZ_LOAN_SUSPEND_RECORD.CREATER_DATE
  IS '操作日期';
CREATE SEQUENCE SEQ_LOANS_SUSPEND_RECORD;
alter table BIZ_LOAN_SUSPEND_RECORD
add constraint FK_SUSPEND_p_pid foreign key (project_id)
references biz_project(pid); 

--
create sequence seq_finance_handle;
create sequence seq_apply_finance_handle;
create sequence seq_handle_info;
create sequence seq_APPLY_HANDLE_INFO;

CREATE SEQUENCE SEQ_apply_handle;
CREATE SEQUENCE SEQ_HOUSE_BALANCE;
CREATE SEQUENCE SEQ_REFUND_DETAILS;
CREATE SEQUENCE SEQ_handle_flow;
CREATE SEQUENCE SEQ_HANDLE_DYNAMIC;
CREATE SEQUENCE SEQ_HANDLE_DIFFER_WARN;
CREATE SEQUENCE SEQ_HIS_HANDLE_DIFFER_WARN
CREATE SEQUENCE SEQ_biz_handle_WORKFLOW;
CREATE SEQUENCE SEQ_HANDLE_DYNAMIC_FILE;
CREATE SEQUENCE SEQ_refund_fee;
CREATE SEQUENCE SEQ_Collect_files;
CREATE SEQUENCE SEQ_Check_Document;
CREATE SEQUENCE SEQ_repayment;
CREATE SEQUENCE SEQ_repayment_record;
CREATE SEQUENCE SEQ_overdue_Fee;
CREATE SEQUENCE SEQ_user_sign;
CREATE SEQUENCE SEQ_biz_DYNAMIC_evaluate;
CREATE SEQUENCE SEQ_Check_litigation;
CREATE SEQUENCE SEQ_Intermediate_transfer;
CREATE SEQUENCE SEQ_APP_VERSION_INFO;
CREATE SEQUENCE SEQ_PERFORM_JOB_REMARK;
CREATE SEQUENCE SEQ_PROBLEM_FEEDBACK;
CREATE SEQUENCE SEQ_BIZ_DYNAMIC;


drop SEQUENCE seq_biz_loan_finance_handle;
drop SEQUENCE SEQ_APPLY_FINANCE_HANDLE;
drop SEQUENCE seq_handle_info;
drop SEQUENCE seq_APPLY_HANDLE_INFO;
drop SEQUENCE SEQ_apply_handle;
drop SEQUENCE SEQ_HOUSE_BALANCE;
drop SEQUENCE SEQ_REFUND_DETAILS;
drop SEQUENCE SEQ_handle_flow;
drop SEQUENCE SEQ_HANDLE_DYNAMIC;
drop SEQUENCE SEQ_HANDLE_DIFFER_WARN;
drop SEQUENCE SEQ_HIS_HANDLE_DIFFER_WARN
drop SEQUENCE SEQ_biz_handle_WORKFLOW;
drop SEQUENCE SEQ_HANDLE_DYNAMIC_FILE;
drop SEQUENCE SEQ_refund_fee;
drop SEQUENCE SEQ_Collect_files;
drop SEQUENCE SEQ_Check_Document;
drop SEQUENCE SEQ_repayment;
drop SEQUENCE SEQ_repayment_record;
drop SEQUENCE SEQ_overdue_Fee;
drop SEQUENCE SEQ_user_sign;
drop SEQUENCE SEQ_biz_DYNAMIC_evaluate;
drop SEQUENCE SEQ_Check_litigation;
drop SEQUENCE SEQ_BIZ_DYNAMIC;
--删除
--DROP TABLE biz_loan_product_handle_flow
/*
delete from biz_handle_WORKFLOW;
delete from biz_loan_apply_handle_info;--申请业务处理信息
delete from biz_loan_house_balance;--赎楼及余额回转
delete from biz_loan_handle_dynamic_file;--办理动态文件
delete from biz_loan_handle_differ_warn;--差异预警处理表
delete from biz_HIS_HANDLE_DIFFER_WARN;
delete from biz_loan_handle_dynamic;--办理动态
-- delete from biz_loan_handle_flow;-- 办理流程条目
delete from BIZ_LOAN_REFUND_DETAILS;-- 财务退款明细
delete from biz_loan_handle_info;--业务处理主表
delete from biz_loan_apply_finance_handle;
delete from biz_loan_finance_handle;--财务受理信息
delete from biz_loan_refund_fee;--退手续费
SELECT a.*
  FROM biz_workflow_project a
  where a.project_id in(SELECT b.project_id FROM biz_loan_finance_handle b) for update;
*/


/*SELECT P.PID,p.project_number,p.project_name,p.status FROM biz_project p,biz_loan_finance_handle f where p.pid=f.project_id;
SELECT * FROM BIZ_PROJECT
SELECT * FROM biz_loan_finance_handle t where t.pid=75 for update;
SELECT * FROM biz_loan_apply_finance_handle t where t.finance_handle_id=67 for update;
delete from biz_loan_finance_handle t where t.pid not in(53)
SELECT pid,finance_handle_id,rec_pro,rec_money,rec_account,rec_date,pro_resource,operator,remark 
FROM biz_loan_apply_finance_handle t where t.finance_handle_id=53;
SELECT * FROM biz_loan_finance_handle a ,biz_loan_apply_finance_handle b where a.pid=b.finance_handle_id
insert into biz_loan_finance_handle 
values(seq_biz_loan_finance_handle.nextval,10418,1,20040);

SELECT hi.pid,hi.project_id,p.project_number,p.project_name,p.request_status,hi.account_status,hi.status 
FROM BIZ_PROJECT P,BIZ_LOAN_HANDLE_INFO HI 
WHERE P.Pid=hi.project_id;
SELECT * FROM BIZ_LOAN_HANDLE_INFO t for update;
SELECT * FROM biz_loan_finance_handle t for update;
SELECT * FROM BIZ_LOAN_APPLY_HANDLE_INFO t for update;
insert into BIZ_LOAN_HANDLE_INFO(pid,PROJECT_ID,STATUS,ACCOUNT_STATUS) 
values(seq_handle_info.nextval,10381,1,2);
insert into BIZ_LOAN_HANDLE_INFO(pid,PROJECT_ID,STATUS,ACCOUNT_STATUS) 
values(seq_handle_info.nextval,10435,1,2);

INSERT into BIZ_LOAN_APPLY_HANDLE_INFO(pid,Handle_Id)
values(seq_APPLY_HANDLE_INFO.Nextval,2);
INSERT into BIZ_LOAN_APPLY_HANDLE_INFO(pid,Handle_Id)
values(seq_APPLY_HANDLE_INFO.Nextval,3);*/
/*
备注：
BIZ_PROJECT：加Collect_file_status收件状态,未收件=1,已收件=2,


*/
