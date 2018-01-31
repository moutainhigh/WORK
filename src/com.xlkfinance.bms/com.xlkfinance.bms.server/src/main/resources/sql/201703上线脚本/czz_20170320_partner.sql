


/**--------------------------项目物业信息表 -----------------------------------------------*/

/**首期款金额*/
ALTER TABLE BIZ_PROJECT_ESTATE ADD DOWN_PAYMENT  NUMBER;
comment on column BIZ_PROJECT_ESTATE.DOWN_PAYMENT is '首期款金额';

/**购房定金*/
ALTER TABLE BIZ_PROJECT_ESTATE ADD PURCHASE_DEPOSIT  NUMBER;
comment on column BIZ_PROJECT_ESTATE.PURCHASE_DEPOSIT is '购房定金';

