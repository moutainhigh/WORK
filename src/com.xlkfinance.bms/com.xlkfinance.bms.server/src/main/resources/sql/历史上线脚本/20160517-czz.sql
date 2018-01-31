/*项目合作机构附件表*/
create table BIZ_PROJECT_PARTNER_FILE 
(
   PID 		INTEGER  PRIMARY KEY,
   PROJECT_ID INTEGER,
   PARTNER_NO    	  VARCHAR2(55),
   ACCESSORY_TYPE VARCHAR2(50),
   FILE_NAME  VARCHAR2(255),
   FILE_TYPE  VARCHAR2(50),
   FILE_SIZE  INTEGER,
   FILE_URL  VARCHAR2(255),
   UPDATE_TIME TIMESTAMP(6),
   STATUS     INTEGER,
   REMARK VARCHAR2(255)
);

comment on table BIZ_PROJECT_PARTNER_FILE is '项目合作机构附件表';
comment on column BIZ_PROJECT_PARTNER_FILE.PID is '主键';
comment on column BIZ_PROJECT_PARTNER_FILE.PROJECT_ID is '项目ID';
comment on column BIZ_PROJECT_PARTNER_FILE.PARTNER_NO is '合作机构编号';
comment on column BIZ_PROJECT_PARTNER_FILE.ACCESSORY_TYPE is '附件类型';
comment on column BIZ_PROJECT_PARTNER_FILE.FILE_NAME is '文件名';
comment on column BIZ_PROJECT_PARTNER_FILE.FILE_TYPE is '文件类型';
comment on column BIZ_PROJECT_PARTNER_FILE.FILE_SIZE is '文件大小';
comment on column BIZ_PROJECT_PARTNER_FILE.FILE_URL  is '文件路径';
comment on column BIZ_PROJECT_PARTNER_FILE.UPDATE_TIME is '更新时间';
comment on column BIZ_PROJECT_PARTNER_FILE.STATUS 	 is '状态 1:正常 2: 删除';
comment on column BIZ_PROJECT_PARTNER_FILE.REMARK is '备注';


/* 创建序例*/
create sequence SEQ_BIZ_PROJECT_PARTNER_FILE 
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

 

/**项目合作表添加字段*/

/**公正信息文件*/
alter table BIZ_PROJECT_PARTNER add LOAN_JUSTICE_FILES  VARCHAR2(100);
comment on column BIZ_PROJECT_PARTNER.LOAN_JUSTICE_FILES is '公正信息文件';

/**卡片信息文件*/
alter table BIZ_PROJECT_PARTNER add LOAN_BLANK_FILES  VARCHAR2(100);
comment on column BIZ_PROJECT_PARTNER.LOAN_BLANK_FILES is '卡片信息文件';

/**其他信息文件*/
alter table BIZ_PROJECT_PARTNER add LOAN_OTHER_FILES  VARCHAR2(100);
comment on column BIZ_PROJECT_PARTNER.LOAN_OTHER_FILES is '其他信息文件';
 

/**申请放款日期*/
alter table BIZ_PROJECT_PARTNER add APPLY_LOAN_DATE  DATE;
comment on column BIZ_PROJECT_PARTNER.APPLY_LOAN_DATE is '申请放款日期';

/**放款备注*/
alter table BIZ_PROJECT_PARTNER add LOAN_REMARK  VARCHAR2(255);
comment on column BIZ_PROJECT_PARTNER.LOAN_REMARK is '放款备注';




