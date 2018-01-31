
/***/

/**合作机构-添加推送帐号*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD PARTNER_PUSH_ACCOUNT  INTEGER;
COMMENT ON COLUMN BIZ_PROJECT_PARTNER.PARTNER_PUSH_ACCOUNT IS '机构推送帐号  1：华安万通  2：华安小科';

/**合作机构-添加执行利率*/
ALTER TABLE BIZ_PROJECT_PARTNER ADD PARTNER_GROSS_RATE NUMBER(12,6);
COMMENT ON COLUMN BIZ_PROJECT_PARTNER.PARTNER_GROSS_RATE IS '执行利率';



/**更新历史数据*/
UPDATE BIZ_PROJECT_PARTNER SET PARTNER_PUSH_ACCOUNT = 1  WHERE PARTNER_PUSH_ACCOUNT IS NULL AND PARTNER_NO = 'hnbx';
UPDATE BIZ_PROJECT_PARTNER SET PARTNER_GROSS_RATE = 0.0725  WHERE PARTNER_GROSS_RATE IS NULL AND PARTNER_NO = 'hnbx';