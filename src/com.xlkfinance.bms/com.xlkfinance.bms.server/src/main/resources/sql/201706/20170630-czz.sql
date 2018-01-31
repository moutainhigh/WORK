 
 
 /**添加 客户征信报告记录序列*/
CREATE SEQUENCE SEQ_CUS_CREDIT_REPORT_HIS;

/*==============================================================*/
/* Table: CUS_CREDIT_REPORT_HIS       客户征信报告记录                           */
/*==============================================================*/
create table CUS_CREDIT_REPORT_HIS 
(
   PID                  INTEGER PRIMARY KEY,
   ACCT_ID              INTEGER,
   DATA_SOURCE          INTEGER,
   REPORT_TYPE          INTEGER,
   QUERY_RESON_ID       VARCHAR2(50),
   QUERY_NAME           VARCHAR2(200),
   QUERY_DOCUMENT_NO    VARCHAR2(50),
   QUERY_PHONE          VARCHAR2(50),
   QUERY_STATUS         INTEGER,
   QUERY_STATUS_MSG     VARCHAR2(200),
   REPORT_NO            VARCHAR2(50),
   QUERY_RESULT         CLOB,
   OPERATOR             INTEGER,
   CREATE_TIME          TIMESTAMP(6),
   CREATOR              INTEGER,
   REMARK               VARCHAR2(500)
);

comment on table CUS_CREDIT_REPORT_HIS is '客户征信报告记录';

comment on column CUS_CREDIT_REPORT_HIS.PID is '主键';

comment on column CUS_CREDIT_REPORT_HIS.ACCT_ID is '个人客户ID';

comment on column CUS_CREDIT_REPORT_HIS.DATA_SOURCE is '信息来源(1:鹏元征信)';

comment on column CUS_CREDIT_REPORT_HIS.REPORT_TYPE is '报告类型 1:个人信用报告 2：个人风险汇总 3：个人反欺诈分析';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_RESON_ID is
'查询原因
	101、贷款审批  102、贷款贷后管理  103、贷款催收   104、审核担保人信用   105、担保/融资审批
	201、信用卡审批  202、信用卡贷后管理  203、信用卡催收
	301、加强税源基础管理	302、追缴欠税  303、商户信用  304、申报创新人才奖  305、失业人员小额贷款担保审批  306、深圳市外来务工人员积分入户申请
	401、车贷保证保险审批	402、审核车贷保证保险担保人信用
	501、求职	502、招聘	503、异议处理
	901、了解个人信用	999、其他';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_NAME is '姓名';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_DOCUMENT_NO is '证件号码';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_PHONE is '手机号码';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_STATUS is '查询状态 (1:成功，2：失败)';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_STATUS_MSG is '查询状态信息';

comment on column CUS_CREDIT_REPORT_HIS.REPORT_NO is '报告编号';

comment on column CUS_CREDIT_REPORT_HIS.QUERY_RESULT is '查询数据结果JSON';

comment on column CUS_CREDIT_REPORT_HIS.OPERATOR is '操作人';

comment on column CUS_CREDIT_REPORT_HIS.CREATE_TIME is '创建时间';

comment on column CUS_CREDIT_REPORT_HIS.CREATOR is '创建人';

comment on column CUS_CREDIT_REPORT_HIS.REMARK is '备注';




/**添加权限*/
/** 客户管理>客户征信报告>查询报告*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','客户管理>客户征信报告>查询报告','客户管理>客户征信报告>查询报告','CUS_QUERY_REPORT',0,1);
/** 客户管理>客户征信报告>查看报告*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','客户管理>客户征信报告>查看报告','客户管理>客户征信报告>查看报告','CUS_VIEW_REPORT',0,1);








