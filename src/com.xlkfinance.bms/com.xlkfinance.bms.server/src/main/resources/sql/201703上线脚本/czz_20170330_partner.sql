


/**--------------------------项目物业信息表 -----------------------------------------------*/

/**新贷款申请人*/
ALTER TABLE BIZ_PROJECT_FORECLOSURE ADD NEW_RECE_PERSON  VARCHAR2(255);
comment on column BIZ_PROJECT_FORECLOSURE.NEW_RECE_PERSON is '新贷款申请人';


/**购房余款*/
ALTER TABLE BIZ_PROJECT_ESTATE ADD PURCHASE_BALANCE  NUMBER;
comment on column BIZ_PROJECT_ESTATE.PURCHASE_BALANCE is '购房余款';

 

