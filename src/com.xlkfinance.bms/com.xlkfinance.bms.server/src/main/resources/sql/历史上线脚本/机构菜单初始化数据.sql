
insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (4, '合作管理', null, 1, '"URL"', 1, 1, 'fa fa-home cooperate', 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (2, '员工管理', null, 1, '"URL"', 1, 4, 'fa fa-home staff', 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (3, '员工列表', 2, 1, 'empController/toEmpIndex.action', 1, 1, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (5, '申请加入', 4, 1, 'orgController/toCooperatApply.action', 1, 1, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (6, '合作信息', 4, 1, 'orgController/toCooperatInfo.action', 1, 2, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (12, '权限管理', 2, 2, 'empController/toFnPermissionIndex.action', 1, 2, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (13, '合作管理', null, 1, '"URL"', 1, 1, 'fa fa-home cooperate', 2);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (14, '申请加入', 13, 2, 'partnerController/toCooperatApply.action', 1, 1, null, 2);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (15, '合作信息', 13, 2, 'partnerController/toCooperatInfo.action', 1, 2, null, 2);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (7, '业务管理', null, 1, '"URL"', 1, 2, 'fa fa-home business', 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (8, '业务列表', 7, 2, 'projectController/toProjectIndex.action', 1, 1, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (9, '财务放款', 22, 2, 'financeController/toOrgMakeLoansIndex.action', 1, 2, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (10, '财务回款', 22, 2, 'financeController/toOrgRepaymentIndex.action', 1, 3, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (18, '财务管理', null, 1, '"URL"', 1, 2, 'fa fa-home finance', 2);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (19, '息费结算', 18, 2, 'financeController/toPartnerFeeSettleIndex.action', 1, 1, null, 2);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (22, '财务管理', null, 1, '"URL"', 1, 3, 'fa fa-home finance', 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (23, '保后监管', 7, 2, 'bizHandleController/toBizHandleIndex.action', 1, 2, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (11, '息费结算', 22, 2, 'financeController/toOrgFeeSettleIndex.action', 1, 4, null, 1);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (16, '客户管理', null, 1, '"URL"', 1, 3, 'fa fa-home customer', 2);

insert into ORG_SYS_MENU_INFO (PID, MENU_NAME, PARENT_ID, "LEVEL", "URL", STATUS, MENU_INDEX, ICON_CLS, USER_TYPE)
values (17, '客户列表', 16, 2, 'partnerController/toCustomerIndex.action', 1, 1, null, 2);

commit;
