--机构合作项目表
create table BIZ_PROJECT_PARTNER 
(
   PID                  INTEGER               PRIMARY KEY,
   PROJECT_ID           INTEGER,
   LOAN_ID              VARCHAR2(55),
   REQUEST_STATUS       INTEGER,
   PARTNER_NO           NUMBER,
   REQUEST_FILES        VARCHAR2(255),
   REMARK               VARCHAR2(2048),
   REQUEST_TIME         TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   APPROVAL_TIME        TIMESTAMP,
   APPROVAL_COMMENT     VARCHAR2(2048),
   SUBMIT_APPROVAL_TIME TIMESTAMP,
   IS_CLOSED            INTEGER,
   LOAN_STATUS          INTEGER,
   LOAN_TIME            TIMESTAMP,
   LOAN_RESULT_TIME     TIMESTAMP,
   REPAYMENT_REPURCHASE_TYPE NUMBER,
   REPAYMENT_REPURCHASE_STATUS INTEGER,
   REPAYMENT_REPURCHASE_TIME TIMESTAMP,
   RP_RESULT_TIME       TIMESTAMP,
   LOAN_AMOUNT          NUMBER(12,2),
   TOTAL_COST           NUMBER(12,2),
   GUARANTEE_FEE        NUMBER(12,2),
   XIFEE_REQUEST_TIME   TIMESTAMP,
   XIFEE_RESULT_TIME    TIMESTAMP,
   XIFEE_STATUS         INTEGER,
   APPROVAL_STATUS 	INTEGER,
   PM_USER_ID INTEGER,
   REPAYMENT_VOUCHER_PATH varchar2(255),
   XIFEE_VOUCHER_PATH  varchar2(255),
   PREMIUM				NUMBER(12,2),
   RE_APPLY_REASON      VARCHAR2(2048),
   APPROVE_MONEY		NUMBER(12,2),
   CONFIRM_LOAN_STATUS 	INTEGER,
   CONFIRM_LOAN_REQUEST_TIME	TIMESTAMP,
   CONFIRM_LOAN_RESULT_TIME		TIMESTAMP,
   CONFIRM_LOAN_REASON	VARCHAR2(2048)
);

comment on table BIZ_PROJECT_PARTNER is '机构合作项目表';
comment on column BIZ_PROJECT_PARTNER.PID is '主键';
comment on column BIZ_PROJECT_PARTNER.PROJECT_ID is '项目ID';
comment on column BIZ_PROJECT_PARTNER.LOAN_ID is '审批编号';
comment on column BIZ_PROJECT_PARTNER.REQUEST_STATUS is '申请状态';
comment on column BIZ_PROJECT_PARTNER.PARTNER_NO is '合作机构';
comment on column BIZ_PROJECT_PARTNER.REQUEST_FILES is '提交的材料';
comment on column BIZ_PROJECT_PARTNER.REMARK is '备注';
comment on column BIZ_PROJECT_PARTNER.REQUEST_TIME is '申请时间';
comment on column BIZ_PROJECT_PARTNER.UPDATE_TIME is '修改时间';
comment on column BIZ_PROJECT_PARTNER.APPROVAL_TIME is '审核时间';
comment on column BIZ_PROJECT_PARTNER.APPROVAL_COMMENT is '审核意见';
comment on column BIZ_PROJECT_PARTNER.SUBMIT_APPROVAL_TIME is '提交审核时间';
comment on column BIZ_PROJECT_PARTNER.IS_CLOSED is '是否关闭';
comment on column BIZ_PROJECT_PARTNER.LOAN_STATUS is '放款状态(1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败)';
comment on column BIZ_PROJECT_PARTNER.LOAN_TIME is '请求放款时间';
comment on column BIZ_PROJECT_PARTNER.LOAN_RESULT_TIME is '放款结果通知时间';
comment on column BIZ_PROJECT_PARTNER.REPAYMENT_REPURCHASE_TYPE is '还款、回购类型';
comment on column BIZ_PROJECT_PARTNER.REPAYMENT_REPURCHASE_STATUS is '还款、回购状态(1:未申请2:已申请3:确认收到；4：未收到; 5：确认中)';
comment on column BIZ_PROJECT_PARTNER.REPAYMENT_REPURCHASE_TIME is '还款回购时间';
comment on column BIZ_PROJECT_PARTNER.RP_RESULT_TIME is '还款、回购结果通知时间';
comment on column BIZ_PROJECT_PARTNER.LOAN_AMOUNT is '借款金额';
comment on column BIZ_PROJECT_PARTNER.TOTAL_COST is '总成本';
comment on column BIZ_PROJECT_PARTNER.GUARANTEE_FEE is '担保费';
comment on column BIZ_PROJECT_PARTNER.XIFEE_REQUEST_TIME is '息费结算请求时间';
comment on column BIZ_PROJECT_PARTNER.XIFEE_RESULT_TIME is '息费结算确认时间';
comment on column BIZ_PROJECT_PARTNER.XIFEE_STATUS is '息费状态(1:未发送，2：已发送3:未到账4：已到账5:数据不存在)';
comment on column BIZ_PROJECT_PARTNER.APPROVAL_STATUS is '审核状态(1:审核中 2:审核通过 3:审核未通过 4:驳回补件 )';
comment on column BIZ_PROJECT_PARTNER.PM_USER_ID is '经办人';
comment on column BIZ_PROJECT_PARTNER.REPAYMENT_VOUCHER_PATH is '还款、回购凭证';
comment on column BIZ_PROJECT_PARTNER.XIFEE_VOUCHER_PATH is '息费凭证';

comment on column BIZ_PROJECT_PARTNER.PREMIUM is '保费';
comment on column BIZ_PROJECT_PARTNER.RE_APPLY_REASON is '复议理由';
comment on column BIZ_PROJECT_PARTNER.APPROVE_MONEY is '审批金额';
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_STATUS is '确认要款状态 （1：未发送  2：审核中 3：审核通过 4 审核不通过 ） ';
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_REQUEST_TIME is '确认要款请求时间 ';
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_RESULT_TIME is '确认要款通知时间 ';
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_REASON is '确认要款复议理由';



--合作机构审批记录
create table BIZ_PARTNER_APPROVAL_RECORD 
(
   PID                  INTEGER               PRIMARY KEY,
   PARTNER_ID           INTEGER,
   PROJECT_ID           INTEGER,
   LOAN_ID				INTEGER,
   APPROVAL_STATUS      INTEGER,
   APPROVAL_COMMENT     varchar2(2048),
   APPROVAL_TIME        TIMESTAMP,
   SUBMIT_APPROVAL_TIME TIMESTAMP,
   RE_APPLY_REASON     varchar2(2048),
   APPROVE_MONEY		NUMBER(12,2),
   IS_NOTIFY			INTEGER DEFAULT 1,
   NOTIFY_TYPE 			INTEGER
);


comment on table BIZ_PARTNER_APPROVAL_RECORD is '合作机构审批记录';
comment on column BIZ_PARTNER_APPROVAL_RECORD.PID is '主键';
comment on column BIZ_PARTNER_APPROVAL_RECORD.PARTNER_ID is '机构合作项目ID';
comment on column BIZ_PARTNER_APPROVAL_RECORD.PROJECT_ID is '项目ID';
comment on column BIZ_PARTNER_APPROVAL_RECORD.LOAN_ID is '审批编号';
comment on column BIZ_PARTNER_APPROVAL_RECORD.APPROVAL_STATUS is '审核状态(1:审核中 2:审核通过 3:审核未通过 4:驳回补件)';
comment on column BIZ_PARTNER_APPROVAL_RECORD.APPROVAL_COMMENT is '审批意见';
comment on column BIZ_PARTNER_APPROVAL_RECORD.APPROVAL_TIME is '审批时间';
comment on column BIZ_PARTNER_APPROVAL_RECORD.SUBMIT_APPROVAL_TIME is '提交审核时间';

comment on column BIZ_PARTNER_APPROVAL_RECORD.RE_APPLY_REASON is '复议理由';
comment on column BIZ_PARTNER_APPROVAL_RECORD.APPROVE_MONEY is '审批金额';
comment on column BIZ_PARTNER_APPROVAL_RECORD.IS_NOTIFY is '是否回调通知  1：否，2：是';
comment on column BIZ_PARTNER_APPROVAL_RECORD.NOTIFY_TYPE is '回调类型 (1: 申请 2: 复议 3: 复议确认要款 4:放款)';



create sequence SEQ_BIZ_PROJECT_PARTNER
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence SEQ_BIZ_APPROVAL_RECORD
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;
 
