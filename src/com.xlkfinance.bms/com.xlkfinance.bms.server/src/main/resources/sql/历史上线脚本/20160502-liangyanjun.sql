--万通需求
ALTER TABLE biz_Intermediate_transfer
ADD (
 special_type               INTEGER default 1,
 rec_account_name      VARCHAR2(55),
 transfer_account_name      VARCHAR2(55)
);
comment on column biz_Intermediate_transfer.special_type
  is '特殊情况类型：客户自由资金到账=1,客户首期款优先=2';  
comment on column biz_Intermediate_transfer.rec_account
  is '到账账号'; 
comment on column biz_Intermediate_transfer.rec_account_name
  is '到账账号户名';
comment on column biz_Intermediate_transfer.transfer_account
  is '划出账号'; 
comment on column biz_Intermediate_transfer.transfer_account_name
  is '划出账户户名'; 

--万通需求:查档复核
alter table BIZ_CHECK_DOCUMENT 
add (
  re_check_status  INTEGER default 1,
  re_check_remark  VARCHAR2(2048),
  RE_CHECK_DATE    TIMESTAMP,
  RE_CHECK_user_id    INTEGER
);
comment on column biz_Check_Document.re_check_status is
'查档复核状态：未复核=1,重新复核=2,同意=3,不同意=4';
comment on column biz_Check_Document.re_check_remark is
'查档复核备注';
comment on column biz_Check_Document.RE_CHECK_DATE is
'查档复核时间'; 
comment on column biz_Check_Document.RE_CHECK_user_id is
'查档复核人id'; 
-- Created on 2016/5/6 星期五 by ADMINISTRATOR 
DECLARE
  CURSOR C_JOB IS
    SELECT B.PID,
           B.APPROVAL_STATUS      AS DOCUMENT_APPROVAL_STATUS,
           C.APPROVAL_STATUS      AS LITIGATION_APPROVAL_STATUS,
           D.FORECLOSURE_STATUS, A.FORECLOSURE_STATUS AS PROJECT_FORECLOSURE_STATUS
      FROM BIZ_PROJECT A
     INNER JOIN BIZ_CHECK_DOCUMENT B
        ON A.PID = B.PROJECT_ID
     INNER JOIN BIZ_CHECK_LITIGATION C
        ON C.PROJECT_ID = A.PID
      LEFT JOIN BIZ_LOAN_HANDLE_INFO D
        ON D.PROJECT_ID = A.PID;
  C_ROW C_JOB%ROWTYPE;
BEGIN
  FOR C_ROW IN C_JOB LOOP
    IF (C_ROW.PID IS NOT NULL) THEN
      -- 查档和查诉讼都通过并且已放款,查档复核修改为同意
      IF (C_ROW.DOCUMENT_APPROVAL_STATUS = 3 AND
         C_ROW.LITIGATION_APPROVAL_STATUS = 3 AND
         C_ROW.PROJECT_FORECLOSURE_STATUS >= 11) THEN
        UPDATE BIZ_CHECK_DOCUMENT T
           SET T.RE_CHECK_STATUS = 3
         WHERE T.PID = C_ROW.PID;
      ELSE
        -- 赎楼状态为驳回,查档复核状态修改为重新复核
        IF (C_ROW.FORECLOSURE_STATUS = 3) THEN
          UPDATE BIZ_CHECK_DOCUMENT T
             SET T.RE_CHECK_STATUS = 2
           WHERE T.PID = C_ROW.PID;
        ELSE
          IF (1 = 1) THEN
            -- 其他的查档复核状态为未复核
            UPDATE BIZ_CHECK_DOCUMENT T
               SET T.RE_CHECK_STATUS = 1
             WHERE T.PID = C_ROW.PID;
          END IF;
        END IF;
      END IF;
    END IF;
  END LOOP;

END;
/
--办理动态添加办理人
alter table BIZ_LOAN_HANDLE_DYNAMIC 
add (
  current_Handle_User_Id  INTEGER
);
comment on column BIZ_LOAN_HANDLE_DYNAMIC.current_Handle_User_Id
  is '该办理动态的办理人';
--赎楼添加办理人  
alter table BIZ_LOAN_HOUSE_BALANCE 
add (
  HANDLE_USER_ID  INTEGER
);
comment on column BIZ_LOAN_HOUSE_BALANCE.HANDLE_USER_ID
  is '办理人id';
--把办理人初始化成haofangfang
UPDATE BIZ_LOAN_HOUSE_BALANCE T
   SET T.HANDLE_USER_ID = 20185
 WHERE T.HANDLE_ID IN (SELECT B.PID
                         FROM BIZ_LOAN_HANDLE_INFO B
                        WHERE B.FORECLOSURE_STATUS = 2);
--要件收取
alter table biz_Collect_files 
add (
  BUYER_SELLER_TYPE   INTEGER,
  BUYER_SELLER_NAME   VARCHAR2(55)
);
comment on column biz_Collect_files.BUYER_SELLER_TYPE
  is '买卖类型：买方=1,卖方=2';
comment on column biz_Collect_files.BUYER_SELLER_NAME
  is '买卖方姓名';
--正在预警备注需求
alter table BIZ_LOAN_HANDLE_DIFFER_WARN 
add (
  handle_type  INTEGER
);
comment on column BIZ_LOAN_HANDLE_DIFFER_WARN.handle_type
  is '处理类型：客户经理处理数据=1,其他=2';

alter table BIZ_HIS_HANDLE_DIFFER_WARN 
add (
  handle_type  INTEGER
);
comment on column BIZ_HIS_HANDLE_DIFFER_WARN.handle_type
  is '处理类型：客户经理处理数据=1,其他=2';
  
UPDATE BIZ_LOAN_HANDLE_DIFFER_WARN T SET T.HANDLE_TYPE=1;
UPDATE BIZ_HIS_HANDLE_DIFFER_WARN T SET T.HANDLE_TYPE=1;  

--退要件需求
alter table biz_Collect_files 
add (
  REFUND_DATE   TIMESTAMP,
  REFUND_STATUS   INTEGER DEFAULT 1
);
comment on column biz_Collect_files.REFUND_DATE
  is '退件时间';
comment on column biz_Collect_files.REFUND_STATUS
  is '退件状态：未退件=1,已退件=2';
  
alter table biz_project
add (
  REFUND_FILE_STATUS   INTEGER DEFAULT 1
);
comment on column biz_project.REFUND_FILE_STATUS
  is '退件完结状态：未退件=1,已退件=2';

comment on column biz_loan_apply_finance_handle.rec_pro
  is '收款项目:咨询费=1,手续费=2,第一次放款=3,一次赎楼余额转二次放款=4,第二次放款=5,监管（客户）资金转入=6,佣金=7,展期费=8';
--项目总览需求
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
CREATE SEQUENCE SEQ_BIZ_DYNAMIC;

alter table biz_loan_apply_finance_handle 
add (
    update_date  TIMESTAMP
);
alter table BIZ_LOAN_HOUSE_BALANCE 
add (
    CREATER_DATE  TIMESTAMP
);

comment on column BIZ_LOAN_HOUSE_BALANCE.CREATER_DATE is 
'创建时间';
comment on column biz_loan_apply_finance_handle.update_date is 
'更新时间';


comment on column biz_loan_refund_fee.type
  is '退款类型：退手续费=1,退咨询费=2,退尾款=3,退佣金=4';  
COMMENT ON TABLE BIZ_DYNAMIC IS
'业务动态';

comment on table BIZ_HIS_HANDLE_DIFFER_WARN
  is '差异预警历史处理表';
comment on table biz_loan_repayment_record
  is '回款记录表';  
  
--撤单需求
alter table BIZ_PROJECT
add (
    CHECHAN_DATE     TIMESTAMP,
    CHECHAN_USER_ID  INTEGER,
    CHECHAN_CAUSE    VARCHAR2(2048)
);

comment on column BIZ_PROJECT.CHECHAN_DATE is 
'撤单时间';
comment on column BIZ_PROJECT.CHECHAN_USER_ID is 
'撤单用户id';
comment on column BIZ_PROJECT.CHECHAN_CAUSE is 
'撤单原因';

-- 收件查档权限
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '收件权限',
   '收件权限',
   'COLLECT_FILE',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '执行岗备注权限',
   '执行岗备注权限',
   'PERFORM_JOB_REMARK',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '查诉讼权限',
   '查诉讼权限',
   'CHECK_LITIGATION',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '查档权限',
   '查档权限',
   'CHECK_DOCUMENT',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '查诉讼日志权限',
   '查诉讼日志权限',
   'CHECK_LITIGATION_LOG',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '查档日志权限',
   '查档日志权限',
   'CHECK_DOCUMENT_LOG',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '查档复核权限',
   '查档复核权限',
   'CHECK_DOCUMENT_RE_CHECK',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '预警跟进备注权限',
   '预警跟进备注权限',
   'DIFFER_WARN_TRACK_REMARK',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '退要件权限',
   '退要件权限',
   'REFUND_COLLECT_FILE',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '预警统计报表导出权限',
   '预警统计报表导出权限',
   'CXPORT_DIFFER_WARN_REPORT',
   0,
   1);
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '业务汇总报表导出权限',
   '业务汇总报表导出权限',
   'CXPORT_BUSINESS_SUMMARY_REPORT',
   0,
   1);
   INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '退费确认权限',
   '退费确认权限',
   'REFUNDFEE_CONFIRM',
   0,
   1);
   INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '贷中办理反馈权限',
   '贷中办理反馈权限',
   'DYNAMIC_FEEDBACK',
   0,
   1);
   INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '贷中归档权限',
   '贷中归档权限',
   'ARCHIVED',
   0,
   1);
   INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '项目撤单权限',
   '项目撤单权限',
   'PROJECT_CHECHAN',
   0,
   1);   
INSERT INTO SYS_PERMISSION
  (PID,
   PERMIS_TYPE,
   PERMIS_NAME,
   PERMIS_DESC,
   PERMIS_CODE,
   MENU_ID,
   STATUS)
VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,
   'CDM',
   '撤单报表导出权限',
   '撤单报表导出权限',
   'CHE_CHAN_REPORT',
   0,
   1);   
   
   update SYS_USER_ORG_INFO a
   set a.data_scope = 1
 where a.user_id in (SELECT b.pid
                       FROM sys_user b
                      where b.member_id in ('wtdb0121',
                                            'wtdb0122',
                                            'wtdb0123',
                                            'wtdb0124',
                                            'wtdb0125',
                                            'wtdb0126',
                                            'wtdb0127',
                                            'wtdb0128',
                                            'wtdb0129',
                                            'wtdb0130',
                                            'wtdb0131',
                                            'wtdb0132',
                                            'wtdb0133',
                                            'wtdb0134',
                                            'wtdb0135',
                                            'wtdb0136',
                                            'wtdb0137',
                                            'wtdb0138',
                                            'wtdb0139',
                                            'wtdb0140',
                                            'wtdb0141',
                                            'wtdb0142',
                                            'wtdb0143',
                                            'wtdb0144',
                                            'wtdb0145',
                                            'wtdb0146',
                                            'wtdb0147',
                                            'wtdb0148',
                                            'wtdb0149',
                                            'wtdb0150',
                                            'wtdb0151',
                                            'wtdb0152',
                                            'wtdb0153',
                                            'wtdb0154',
                                            'wtdb0155',
                                            'wtdb0156',
                                            'wtdb0158',
                                            'wtdb0159',
                                            'wtdb0160',
                                            'wtdb0161',
                                            'wtdb0162',
                                            'wtdb0163',
                                            'wtdb0164',
                                            'wtdb0165',
                                            'wtdb0166',
                                            'wtdb0168',
                                            'wtdb0169',
                                            'wtdb0170',
                                            'wtdb0171',
                                            'wtdb0172',
                                            'wtdb0173',
                                            'wtdb0174',
                                            'wtdb0175',
                                            'wtdb0176',
                                            'wtdb0177',
                                            'wtdb0178',
                                            'wtdb0179',
                                            'wtdb0180',
                                            'wtdb0181',
                                            'wtdb0183',
                                            'wtdb0184',
                                            'wtdb0185',
                                            'wtdb0186',
                                            'wtdb0187',
                                            'wtdb0188',
                                            'wtdb0189',
                                            'wtdb0190',
                                            'wtdb0191',
                                            'wtdb0192',
                                            'wtdb0194',
                                            'wtdb0195',
                                            'wtdb0196',
                                            'wtdb0197',
                                            'wtdb0198',
                                            'wtdb0199',
                                            'wtdb0200',
                                            'wtdb0201',
                                            'wtdb0202',
                                            'wtdb0203',
                                            'wtdb0204'));
update biz_loan_apply_finance_handle t set t.pro_resource='8' where t.pro_resource='资安';
update biz_loan_apply_finance_handle t set t.pro_resource='9' where t.pro_resource='升锦';
-- 修改放款后的预计回款时间-2
UPDATE BIZ_PROJECT_FORECLOSURE F
   SET F.PAYMENT_DATE = F.PAYMENT_DATE - 2
 WHERE F.PROJECT_ID in(SELECT A.PID
          FROM BIZ_PROJECT A
         INNER JOIN BIZ_LOAN_FINANCE_HANDLE B
            ON A.PID = B.PROJECT_ID
         WHERE A.FORECLOSURE_STATUS IN (11, 12, 13)
           AND A.PROJECT_TYPE = 2);
-- 修改陈志玲的逾期费数据
update biz_loan_overdue_Fee t set t.overdue_fee=7987.5,t.overdue_day=5 where t.project_id=11465;
--修改吴巧儿的退手续费金额10改为0
update biz_loan_refund_fee t set t.return_fee=0 where t.project_id=11405 and t.type=1;