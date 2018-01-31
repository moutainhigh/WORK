ALTER TABLE BIZ_LOAN_REPAYMENT_RECORD
ADD (
  ACCOUNT_NO             VARCHAR2(255)
);
COMMENT ON COLUMN BIZ_LOAN_REPAYMENT_RECORD.ACCOUNT_NO
  IS '回款账号';
ALTER TABLE BIZ_LOAN_OVERDUE_FEE
ADD (
 IS_CONFIRM               INTEGER DEFAULT 1
);
COMMENT ON COLUMN BIZ_LOAN_OVERDUE_FEE.IS_CONFIRM
  IS '逾期费确认：未确认=1,已确认=2';
  
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

CREATE SEQUENCE SEQ_USER_SIGN;
CREATE SEQUENCE SEQ_BIZ_DYNAMIC_EVALUATE;
-- 赎楼驳回 start
ALTER TABLE BIZ_LOAN_HANDLE_INFO
ADD (
 FORECLOSURE_TURN_DOWN_REMARK  VARCHAR2(255)
);
COMMENT ON COLUMN BIZ_LOAN_HANDLE_INFO.FORECLOSURE_TURN_DOWN_REMARK
  IS '赎楼驳回备注';
COMMENT ON COLUMN BIZ_LOAN_HANDLE_INFO.FORECLOSURE_STATUS
  IS '赎楼状态：未赎楼=1,已赎楼=2,已驳回=3';
  
COMMENT ON COLUMN BIZ_CHECK_DOCUMENT.APPROVAL_STATUS
  IS '审批状态：未审批=1,不通过=2,通过=3,重新查档=4'; 
-- 赎楼驳回 end

-- 收件需求 start
ALTER TABLE biz_Collect_files
ADD (
  fname             VARCHAR2(255),
  fcode             VARCHAR2(255),
  collect_Date             TIMESTAMP
);
COMMENT ON COLUMN biz_Collect_files.fname
  IS '收件名称';
COMMENT ON COLUMN biz_Collect_files.fcode
  IS '收件编码';
COMMENT ON COLUMN biz_Collect_files.collect_Date
  IS '收件日期';
alter table biz_Collect_files drop column FILE_ID;
-- 收件需求 end

-- 退余款等退款确认需求 start
ALTER TABLE biz_loan_refund_fee
ADD (
  is_confirm               INTEGER default 1,
  confirm_MONEY             NUMBER(12,2),
  confirm_date               TIMESTAMP
);
comment on column biz_loan_refund_fee.confirm_MONEY
  is '确认金额';
comment on column biz_loan_refund_fee.is_confirm
  is '退款确认：未确认=1,已确认=2';
comment on column biz_loan_refund_fee.confirm_date
  is '确认日期';
-- 退余款等退款确认需求 end

--执行岗备注
CREATE TABLE BIZ_LOAN_PERFORM_JOB_REMARK
(
   PID                 INTEGER PRIMARY KEY,
   PROJECT_ID         INTEGER,
   REMARK             VARCHAR2(1024),
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
'状态';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.UPDATE_DATE IS
'更新时间';
COMMENT ON COLUMN BIZ_LOAN_PERFORM_JOB_REMARK.UPDATE_ID IS
'更新者';
CREATE SEQUENCE SEQ_PERFORM_JOB_REMARK;

--中途划转
create table biz_Intermediate_transfer
(
  pid                  INTEGER PRIMARY KEY,
  project_id           INTEGER not null,
  apply_status               INTEGER default 1,
  rec_money             NUMBER(12,2),
  rec_account      VARCHAR2(55),
  rec_date          TIMESTAMP,
  transfer_money             NUMBER(12,2),
  transfer_account      VARCHAR2(55),
  transfer_date          TIMESTAMP,
  remark             VARCHAR2(1024),
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
comment on column biz_Intermediate_transfer.rec_money
  is '到账金额';  
comment on column biz_Intermediate_transfer.rec_account
  is '到账账户'; 
comment on column biz_Intermediate_transfer.rec_date
  is '到账时间';
comment on column biz_Intermediate_transfer.transfer_money
  is '划出金额';  
comment on column biz_Intermediate_transfer.transfer_account
  is '划出账户'; 
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
CREATE SEQUENCE SEQ_Intermediate_transfer;
  
-- 查诉讼表
create table biz_Check_litigation
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
CREATE SEQUENCE SEQ_Check_litigation;


-- 添加初审后的查诉讼
declare
  -- Local variables here
  cursor c_job is
    select a.pid
      from biz_project a where a.foreclosure_status>=6;
  c_row c_job%rowtype;
begin
  for c_row in c_job loop
    insert into biz_Check_litigation(pid,project_id,creater_date) values(seq_Check_litigation.Nextval,c_row.pid,sysdate);
    insert into BIZ_LOAN_PERFORM_JOB_REMARK(pid,project_id,UPDATE_DATE) values(SEQ_PERFORM_JOB_REMARK.Nextval,c_row.pid,sysdate);
  end loop;
commit;
end;
/
--update biz_Check_litigation t set t.check_status=2 ,t.approval_status=3 where t.project_id=11394;
--update BIZ_LOAN_PERFORM_JOB_REMARK t set t.status=2 ,t.remark_date=sysdate where t.project_id=11394;

alter table BIZ_COLLECT_FILES modify REMARK VARCHAR2(1024);

-- 添加收件备注
DECLARE
  -- Local variables here
  CURSOR C_JOB IS
    SELECT A.PID,
           A.PROJECT_ID,
           A.REMARK,
           A.CREATER_DATE,
           A.CREATER_ID,
           A.UPDATE_ID,
           A.UPDATE_DATE
      FROM BIZ_COLLECT_FILES A
      LEFT JOIN BIZ_PROJECT B
        ON A.PROJECT_ID = B.PID
     WHERE B.COLLECT_FILE_STATUS = 2;
  C_ROW C_JOB%ROWTYPE;
BEGIN
  FOR C_ROW IN C_JOB LOOP
     insert into biz_Collect_files(pid,project_id,fname,Fcode,remark,Creater_Date,creater_id,Update_Id,Update_Date,Collect_Date) 
         values(seq_collect_files.Nextval,c_row.project_id,'其他','ORDER',c_row.remark,c_row.Creater_Date,c_row.creater_id,c_row.Update_Id,c_row.Update_Date,c_row.Update_Date);
         update biz_Collect_files a set a.collect_date=c_row.Update_Date where a.project_id=c_row.project_id;
  END LOOP;
  commit;
END;
/
