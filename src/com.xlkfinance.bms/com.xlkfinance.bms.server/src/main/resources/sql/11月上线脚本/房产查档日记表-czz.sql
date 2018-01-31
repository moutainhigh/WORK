


--房产查档表
/*==============================================================*/
/* Table: BIZ_HOUSE_CHECK_DOC                            */
/*==============================================================*/
create table BIZ_HOUSE_CHECK_DOC
(
   PID               INTEGER              PRIMARY KEY,
   USER_ID           INTEGER,
   QUERY_TYPE        INTEGER,
   CERT_TYPE         INTEGER,
   YEAR              VARCHAR2(50),
   CERT_NO           VARCHAR2(50),
   PERSON_INFO       VARCHAR2(255),
   CREATE_TIME	TIMESTAMP,
   UPDATE_TIME	TIMESTAMP,
   IS_DEL            INTEGER
);



comment on table BIZ_HOUSE_CHECK_DOC is '房产查档日记表';
comment on column BIZ_HOUSE_CHECK_DOC.PID is '主键';
comment on column BIZ_HOUSE_CHECK_DOC.USER_ID is '用户id';
comment on column BIZ_HOUSE_CHECK_DOC.QUERY_TYPE is '查档类型（1：分户,2：分栋）';
comment on column BIZ_HOUSE_CHECK_DOC.CERT_TYPE is '产权证书类型（1：房地产权证 书,2：不动产权证书）';
comment on column BIZ_HOUSE_CHECK_DOC.YEAR is '不动产权证书时的年份';
comment on column BIZ_HOUSE_CHECK_DOC.CERT_NO is '房产证号';
comment on column BIZ_HOUSE_CHECK_DOC.PERSON_INFO is '身份证号/权利人姓名';
comment on column BIZ_HOUSE_CHECK_DOC.CREATE_TIME is '创建时间';
comment on column BIZ_HOUSE_CHECK_DOC.UPDATE_TIME is '更新时间';
comment on column BIZ_HOUSE_CHECK_DOC.IS_DEL is '是否删除：1：是 2：否';

create sequence SEQ_HOUSE_CHECK_DOC
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;


--房产查档日记详情表
/*==============================================================*/
/* Table: BIZ_HOUSE_CHECK_DOC_DETAILS                      */
/*==============================================================*/
create table BIZ_HOUSE_CHECK_DOC_DETAILS
(
   PID               INTEGER              PRIMARY KEY,
   HOUSE_CHECK_DOC_ID           INTEGER,
   CHECK_DOC_TIME    VARCHAR2(50),
   CHECK_DOC_CONTENT VARCHAR2(2000),
   CHECK_STATUS            INTEGER,
   CREATE_TIME	TIMESTAMP
);


comment on table BIZ_HOUSE_CHECK_DOC_DETAILS is '房产查档日记详情表';
comment on column BIZ_HOUSE_CHECK_DOC_DETAILS.PID is '主键';
comment on column BIZ_HOUSE_CHECK_DOC_DETAILS.CHECK_DOC_TIME is '查档时间 yyyy-MM-dd HH:mm';
comment on column BIZ_HOUSE_CHECK_DOC_DETAILS.CHECK_DOC_CONTENT is '查档结果';
comment on column BIZ_HOUSE_CHECK_DOC_DETAILS.CHECK_STATUS is '查档状态：1：成功 2：失败';
comment on column BIZ_HOUSE_CHECK_DOC_DETAILS.CREATE_TIME is '创建时间';

create sequence SEQ_HOUSE_CHECK_DOC_DETAILS
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;
