
  --机构合作还款表====================================================================
  --添加创建日期
  ALTER TABLE BIZ_PROJECT_PARTNER_REFUND ADD CREATE_TIME  TIMESTAMP(6);
  comment on column BIZ_PROJECT_PARTNER_REFUND.CREATE_TIME is '创建日期';
  -- 添加更新日期
  ALTER TABLE BIZ_PROJECT_PARTNER_REFUND ADD UPDATE_TIME TIMESTAMP(6);
  comment on column BIZ_PROJECT_PARTNER_REFUND.UPDATE_TIME is '更新日期'; 
 
