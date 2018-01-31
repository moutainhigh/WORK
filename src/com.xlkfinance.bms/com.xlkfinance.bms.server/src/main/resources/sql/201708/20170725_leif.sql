insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID,"LEVEL", "URL", STATUS, MENU_INDEX, USER_TYPE)
values (SEQ_ORG_SYS_MENU_INFO.Nextval, '客户管理', 7, 2, 'customerController/perList.action', 1, 0, 1);
commit;

ALTER TABLE CUS_ACCT ADD (CUS_SOURCE INTEGER , ORG_ID INTEGER);

