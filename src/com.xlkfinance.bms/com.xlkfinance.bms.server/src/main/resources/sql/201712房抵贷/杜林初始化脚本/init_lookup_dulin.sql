DECLARE
  SEQ_BUSINESS_SOURCE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_REPAYMENT_TYPE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_USAGE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_FIVE_CLASSIFY_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_EMPLOY_SITUATION_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_ENTERPRISE_TYPE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_CHARGES_TYPE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_PAYMENT_SOURCE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_FILE_PROPERTY_ID INTEGER;
BEGIN
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_BUSINESS_SOURCE_ID, 'MORTGAGE_LOAN_BUSINESS_SOURCE', '房抵贷业务来源', '房抵贷业务来源（Q房业务中心、机构业务中心、渠道业务中心）', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_BUSINESS_SOURCE_ID, '1', 1, 'Q房业务中心', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_BUSINESS_SOURCE_ID, '2', 1, '机构业务中心', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_BUSINESS_SOURCE_ID, '3', 1, '渠道业务中心', '', null);
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_REPAYMENT_TYPE_ID, 'MORTGAGE_LOAN_REPAYMENT_TYPE', '房抵贷还款方式', '房抵贷还款方式', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_REPAYMENT_TYPE_ID, '1', 1, '先息后本', '', null);

  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_USAGE_ID, 'MORTGAGE_LOAN_USAGE', '房抵贷借款用途', '房抵贷借款用途', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_USAGE_ID, '1', 1, '消费', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_USAGE_ID, '2', 1, '经营', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_USAGE_ID, '3', 1, '资金周转', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_USAGE_ID, '4', 1, '转贷款', '', null);

  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_FIVE_CLASSIFY_ID, 'MORTGAGE_LOAN_FIVE_CLASSIFY', '房抵贷五级分类', '房抵贷五级分类', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FIVE_CLASSIFY_ID, '1', 1, '正常', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FIVE_CLASSIFY_ID, '2', 1, '关注', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FIVE_CLASSIFY_ID, '3', 1, '次级', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FIVE_CLASSIFY_ID, '4', 1, '可疑', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FIVE_CLASSIFY_ID, '5', 1, '损失', '', null);

  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_EMPLOY_SITUATION_ID, 'MORTGAGE_LOAN_EMPLOY_SITUATION', '房抵贷雇佣情况', '房抵贷雇佣情况', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_EMPLOY_SITUATION_ID, '1', 1, '自雇', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_EMPLOY_SITUATION_ID, '2', 1, '雇佣', '', null);
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_ENTERPRISE_TYPE_ID, 'MORTGAGE_LOAN_ENTERPRISE_TYPE', '房抵贷企业类型', '房抵贷企业类型', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '1', 1, '制造业', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '2', 1, '贸易零售业', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '3', 1, '房地产', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '4', 1, '餐饮', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '5', 1, '金融', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '6', 1, '酒店', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '7', 1, '运输', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '8', 1, '建筑工程', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_ENTERPRISE_TYPE_ID, '9', 1, 'IT', '', null);

  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_CHARGES_TYPE_ID, 'MORTGAGE_LOAN_CHARGES_TYPE', '房抵贷收费节点', '房抵贷收费节点值', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CHARGES_TYPE_ID, '1', 1, '下户前收费', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CHARGES_TYPE_ID, '2', 1, '放款前收费', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CHARGES_TYPE_ID, '3', 1, '任意节点收费', '', null);
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_PAYMENT_SOURCE_ID, 'MORTGAGE_LOAN_PAYMENT_SOURCE', '房抵贷还款来源', '房抵贷还款来源', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_PAYMENT_SOURCE_ID, '1', 1, '经营收入', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_PAYMENT_SOURCE_ID, '2', 1, '资产变卖', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_PAYMENT_SOURCE_ID, '3', 1, '转贷款', '', null);
  
  
  SELECT PID INTO SEQ_FILE_PROPERTY_ID FROM sys_lookup WHERE LOOKUP_TYPE = 'FILE_PROPERTY';
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '身份证（借款人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '婚姻证明（借款人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '户口本（借款人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '个人征信（借款人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '经营证明（自雇人士提供）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '个人银行流水（借款人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '一押原贷款合同', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '资产证明（借款人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '身份证（担保人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '户口本（担保人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '个人银行流水（担保人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '资产证明（担保人）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '房产证', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '房屋产权信息登记单', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '8', 1, '其它信息', '', null);
  
  INSERT INTO SYS_ROLE R (R.PID, R.ROLE_NAME, R.ROLE_DESC, R.ROLE_CODE, R.PARENT_ID, R.STATUS)
  VALUES (seq_sys_role.nextval, '评估', '房抵贷贷前流程评估', 'MORTGAGE_ASSESSMENT', 0, 1);

  INSERT INTO SYS_ROLE R (R.PID, R.ROLE_NAME, R.ROLE_DESC, R.ROLE_CODE, R.PARENT_ID, R.STATUS)
  VALUES (seq_sys_role.nextval, '下户', '房抵贷申请贷前下户', 'MORTGAGE_INVESTIGATION', 0, 1);
END;
/