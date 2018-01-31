
/**合并到 partner.sql 增量脚本中*/
/**==============项目机构合作表================================*/
/*
/**保费*/
alter table BIZ_PROJECT_PARTNER add	PREMIUM  NUMBER(12,2);
comment on column BIZ_PROJECT_PARTNER.PREMIUM is '保费';

/*担保费*/
comment on column BIZ_PROJECT_PARTNER.GUARANTEE_FEE is '担保费';

/**复议理由*/
alter table BIZ_PROJECT_PARTNER add	RE_APPLY_REASON  VARCHAR2(2048);
comment on column BIZ_PROJECT_PARTNER.RE_APPLY_REASON is '复议理由';

/*息费状态*/
comment on column BIZ_PROJECT_PARTNER.XIFEE_STATUS is '息费状态(1:未发送，2：已发送3:未到账4：已到账5:数据不存在)';

/*审批金额*/
alter table BIZ_PROJECT_PARTNER add APPROVE_MONEY NUMBER(12,2);
comment on column BIZ_PROJECT_PARTNER.APPROVE_MONEY is '审批金额';

/*确认要款状态*/
alter table BIZ_PROJECT_PARTNER add CONFIRM_LOAN_STATUS  INTEGER  DEFAULT 1;
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_STATUS is '确认要款状态 （1：未发送  2：审核中 3：审核通过 4 审核不通过 ） ';


/*确认要款请求时间*/
alter table BIZ_PROJECT_PARTNER add CONFIRM_LOAN_REQUEST_TIME  TIMESTAMP(6);
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_REQUEST_TIME is '确认要款请求时间 ';

/*确认要款通知时间*/
alter table BIZ_PROJECT_PARTNER add CONFIRM_LOAN_RESULT_TIME  TIMESTAMP(6);
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_RESULT_TIME is '确认要款通知时间 ';

/**确认要款复议理由*/
alter table BIZ_PROJECT_PARTNER add CONFIRM_LOAN_REASON  VARCHAR2(2048);
comment on column BIZ_PROJECT_PARTNER.CONFIRM_LOAN_REASON is '确认要款复议理由';





/**==============项目机构审核表================================*/

/**复议理由*/
alter table BIZ_PARTNER_APPROVAL_RECORD add	RE_APPLY_REASON  VARCHAR2(2048);
comment on column BIZ_PARTNER_APPROVAL_RECORD.RE_APPLY_REASON is '复议理由';

/*审批状态*/
comment on column BIZ_PARTNER_APPROVAL_RECORD.APPROVAL_STATUS is '审批状态 1:审核中 2:审核通过 3:审核未通过 4:驳回补件';

/*审批金额*/
alter table BIZ_PARTNER_APPROVAL_RECORD add APPROVE_MONEY NUMBER(12,2);
comment on column BIZ_PARTNER_APPROVAL_RECORD.APPROVE_MONEY is '审批金额';

/*是否回调通知*/
alter table BIZ_PARTNER_APPROVAL_RECORD add IS_NOTIFY INTEGER DEFAULT 1;
comment on column BIZ_PARTNER_APPROVAL_RECORD.IS_NOTIFY is '是否回调通知  1：否，2：是';

/*回调类型*/
alter table BIZ_PARTNER_APPROVAL_RECORD add NOTIFY_TYPE INTEGER ;
comment on column BIZ_PARTNER_APPROVAL_RECORD.NOTIFY_TYPE is '回调类型 (1: 申请 2: 复议 3: 确认要款 4: 复议确认要款)';
*/


