/** 赎楼贷资金方业务统计->业务笔数明细*/
INSERT INTO BIZ_FILE(PID,FILE_NAME, FILE_TYPE, FILE_SIZE,UPLOAD_USER_ID, FILE_URL, UPLOAD_DTTM, STATUS)
VALUES (SEQ_BIZ_FILE.NEXTVAL, '资金方详情导出模板', 'xlsx', 12, 1, '', SYSDATE, 1);

INSERT INTO SYS_LOOKUP_VAL (PID,LOOKUP_ID,LOOKUP_VAL,STATUS,LOOKUP_DESC,CREATE_DATETIME)
values(SEQ_SYS_LOOKUP_VAL.NEXTVAL,14332,'ZJFXQDCMB',1,'资金方详情导出模板',SYSDATE);

/** 赎楼贷合作机构业务统计->业务笔数明细*/
INSERT INTO BIZ_FILE(PID,FILE_NAME, FILE_TYPE, FILE_SIZE,UPLOAD_USER_ID, FILE_URL, UPLOAD_DTTM, STATUS)
VALUES (SEQ_BIZ_FILE.NEXTVAL, '赎楼贷合作机构业务详情笔数导出模板', 'xlsx', 12, 1, '', SYSDATE, 1);

INSERT INTO SYS_LOOKUP_VAL (PID,LOOKUP_ID,LOOKUP_VAL,STATUS,LOOKUP_DESC,CREATE_DATETIME)
values(SEQ_SYS_LOOKUP_VAL.NEXTVAL,14334,'HZJGYWBSXQ',1,'赎楼贷合作机构业务笔数导出模板',SYSDATE);

/** 资金机构管理->机构合作项目*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','资金机构管理->机构合作项目权限','资金机构管理->机构合作项目','PARTNER_INDEX',0,1);
/** 资金机构管理->机构审核列表*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','资金机构管理->机构审核列表权限','资金机构管理->机构审核列表权限','APPROVAL_INDEX',0,1);
/** 资金机构管理->放款申请*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','资金机构管理->放款申请权限','资金机构管理->放款申请权限','LOAN_INDEX',0,1);
/** 资金机构管理->还款回购*/
INSERT INTO SYS_PERMISSION(PID,PERMIS_TYPE,PERMIS_NAME,PERMIS_DESC,PERMIS_CODE,MENU_ID,STATUS) VALUES
  (SEQ_SYS_PERMISSION.NEXTVAL,'CDM','资金机构管理->还款回购权限','资金机构管理->还款回购权限','RE_PAYMENT_INDEX',0,1);


