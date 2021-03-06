/** 业务台账->业务明细*/
INSERT INTO BIZ_FILE(PID,FILE_NAME, FILE_TYPE, FILE_SIZE,UPLOAD_USER_ID, FILE_URL, UPLOAD_DTTM, STATUS)
VALUES (SEQ_BIZ_FILE.NEXTVAL, '业务审批台账业务审批明细统计', 'xlsx', 12, 1, '', SYSDATE, 1);

INSERT INTO SYS_LOOKUP_VAL (PID,LOOKUP_ID,LOOKUP_VAL,STATUS,LOOKUP_DESC,CREATE_DATETIME)
values(SEQ_SYS_LOOKUP_VAL.NEXTVAL,14317,'YWSPTZMXTJ',1,'业务审批台账业务审批明细统计导出模板',SYSDATE);

/** 在途业务监控*/
INSERT INTO BIZ_FILE(PID,FILE_NAME, FILE_TYPE, FILE_SIZE,UPLOAD_USER_ID, FILE_URL, UPLOAD_DTTM, STATUS)
VALUES (SEQ_BIZ_FILE.NEXTVAL, '在途业务监控统计', 'xlsx', 12, 1, '', SYSDATE, 1);

INSERT INTO SYS_LOOKUP_VAL (PID,LOOKUP_ID,LOOKUP_VAL,STATUS,LOOKUP_DESC,CREATE_DATETIME)
values(SEQ_SYS_LOOKUP_VAL.NEXTVAL,14319,'ZTYWJK',1,'在途业务监控统计导出模板',SYSDATE);

/** 异常业务监控*/
INSERT INTO BIZ_FILE(PID,FILE_NAME, FILE_TYPE, FILE_SIZE,UPLOAD_USER_ID, FILE_URL, UPLOAD_DTTM, STATUS)
VALUES (SEQ_BIZ_FILE.NEXTVAL, '异常业务监控统计', 'xlsx', 12, 1, '', SYSDATE, 1);

INSERT INTO SYS_LOOKUP_VAL (PID,LOOKUP_ID,LOOKUP_VAL,STATUS,LOOKUP_DESC,CREATE_DATETIME)
values(SEQ_SYS_LOOKUP_VAL.NEXTVAL,14321,'YCYWJK',1,'异常业务监控统计导出模板',SYSDATE);


/** 异常业务处理*/
INSERT INTO BIZ_FILE(PID,FILE_NAME, FILE_TYPE, FILE_SIZE,UPLOAD_USER_ID, FILE_URL, UPLOAD_DTTM, STATUS)
VALUES (SEQ_BIZ_FILE.NEXTVAL, '异常业务处理', 'xlsx', 12, 1, '', SYSDATE, 1);

INSERT INTO SYS_LOOKUP_VAL (PID,LOOKUP_ID,LOOKUP_VAL,STATUS,LOOKUP_DESC,CREATE_DATETIME)
values(SEQ_SYS_LOOKUP_VAL.NEXTVAL,14323,'YCYWCL',1,'异常业务处理导出模板',SYSDATE);


/** 权限问题(贷后)*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','异常监控权限','异常监控权限','TRANSIT_EXCEPTION',0,1); 
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','申请报告权限','申请报告权限','APPLY_REPORT',0,1); 
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','异常处理列表回归正常权限','异常处理列表回归正常权限','RETURN_NORMAL',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','异常处理列表打印报告权限','异常处理列表打印报告权限','PRINT_REPORT',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','贷后在途业务监控导出权限','贷后在途业务监控导出权限','EXPORT_TRANSIT_MONITOR',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','贷后异常业务列表导出权限','贷后异常业务列表导出权限','EXPORT_TRANSIT_EXCEPTION',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','贷后异常业务处理列表导出权限','贷后异常业务处理列表导出权限','EXPORT_MANAGE_EXCEPTION',0,1);
  
/** 统计分析导出权限*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','赎楼贷业务统计导出权限','赎楼贷业务统计导出权限','EXPORT_FORECLOSURE_INDEX',0,1);
 
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','财务报表统计导出权限','财务报表统计导出权限','EXPORT_FINANCIAL_STATISTICS_INDEX',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','收退咨询费报表导出权限','收退咨询费报表导出权限','EXPORT_REFUND_FEES_INDEX',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','赎楼贷机构业务量统计导出权限','赎楼贷机构业务量统计导出权限','EXPORT_FORECLOSURE_ORGANIZATION',0,1);

INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','业务审批台账导出权限','业务审批台账导出权限','EXPORT_BUSINESS_APPROVAL_BILL',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','业务审批明细导出权限','业务审批明细导出权限','EXPORT_BUSINESS_APPROVAL_DETAIL',0,1);

INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','赎楼贷业务统计新增笔数导出权限','赎楼贷业务统计新增笔数导出权限','EXPORT_FORECLOSURE_NEW',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','赎楼贷业务统计结清笔数导出权限','赎楼贷业务统计结清笔数导出权限','EXPORT_FORECLOSURE_SQUARE',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','业务审批台账下业务笔数导出权限','业务审批台账下业务笔数导出权限','EXPORT_BUSINESS_BILL_COUNT',0,1);
  
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','赎楼贷资金方业务统计导出权限','赎楼贷资金方业务统计导出权限','EXPORT_FORECLOSURE_CAPITAL',0,1);
  /** 项目表增加回归正常说明*/
ALTER TABLE biz_project
	ADD (
	 		return_normal_remark NVARCHAR2(2000)
	);
COMMENT ON COLUMN biz_project.return_normal_remark
  IS '回归正常说明';
  /** 财务统计报表资方来源增加南粤银行*/
  update CAPITAL_ORG_INFO c set c.is_push = 1 where c.org_code = 'nyyh';
  