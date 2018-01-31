/*要件借出详情表*/
create table BIZ_ELEMENT_LEND_DETAILS 
(
   pid                INTEGER              PRIMARY KEY,
   lend_id            INTEGER,
   element_file_id    INTEGER,
   element_file_name  VARCHAR2(255),
   lend_time          TIMESTAMP(6),
   return_time        TIMESTAMP(6),
   status             INTEGER
);

comment on table BIZ_ELEMENT_LEND_DETAILS is
'要件借出详情表';

comment on column BIZ_ELEMENT_LEND_DETAILS.pid is
'主键';

comment on column BIZ_ELEMENT_LEND_DETAILS.lend_id is
'要件借_主键';

comment on column BIZ_ELEMENT_LEND_DETAILS.element_file_id is
'要件ID';

comment on column BIZ_ELEMENT_LEND_DETAILS.element_file_name is
'要件名称';

comment on column BIZ_ELEMENT_LEND_DETAILS.lend_time is
'借出时间';

comment on column BIZ_ELEMENT_LEND_DETAILS.return_time is
'归还时间';

comment on column BIZ_ELEMENT_LEND_DETAILS.status is
'状态（借出、已归还、已签收）';

alter table BIZ_ELEMENT_LEND_DETAILS
   add constraint FK_BIZ_ELEM_REFERENCE_BIZ_ELEM foreign key (lend_id)
      references BIZ_ELEMENT_LEND (PID);

-- Create sequence 
create sequence SEQ_ELEMENT_LEND_DETAILS
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;