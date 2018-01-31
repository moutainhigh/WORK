DECLARE
  SEQ_CONTACT_RELATION_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_EVALUATION_PRICE_SOURCE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_AGENCY_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_INFO_PLATFORM_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_HOUSE_KEPPER_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
 
  SEQ_VACANT_RATE_ID INTEGER := SEQ_SYS_CONFIG.Nextval;
  SEQ_FILE_PROPERTY_ID INTEGER;
  
  SEQ_CUSTOMER_NATURE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_LOAN_TYPE_ID INTEGER := SEQ_SYS_LOOKUP.Nextval;
  SEQ_CONSUME_LOANTERM INTEGER := SEQ_SYS_LOOKUP.Nextval;
BEGIN

  insert into SYS_CONFIG (PID, CONFIT_NAME, CONFIT_VALUE, STATUS, DESCRIPTION)
  values (SEQ_VACANT_RATE_ID, 'VACANT_RATE', '8.22', 1,'消费贷空置率');
 
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_CONTACT_RELATION_ID, 'CONTACT_RELATION', '项目联系人关系', '项目联系人关系（配偶、父母、子女、亲戚、朋友、同事、其他）', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '配偶', 1, '配偶', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '父母', 1, '父母', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '子女', 1, '子女', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '亲戚', 1, '亲戚', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '朋友', 1, '朋友', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '同事', 1, '同事', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONTACT_RELATION_ID, '其他', 1, '其他', '', null);
  
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_EVALUATION_PRICE_SOURCE_ID, 'EVALUATION_PRICE_SOURCE', '评估价格来源','评估价格来源（中介公司、信息平台）', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_EVALUATION_PRICE_SOURCE_ID, 'AGENCY', 1, '中介公司', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_EVALUATION_PRICE_SOURCE_ID, 'INFO_PLATFORM', 1, '信息平台', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_EVALUATION_PRICE_SOURCE_ID, 'HOUSE_KEPPER', 1, '房屋管家', '', null);

   
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_AGENCY_ID, 'AGENCY', '中介公司','评估价格来源（中介公司）', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_AGENCY_ID, '1', 1, '链家', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_AGENCY_ID, '2', 1, '家家顺', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_AGENCY_ID, '3', 1, 'Q房', '', null);
   
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_AGENCY_ID, '4', 1, '中原', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_AGENCY_ID, '5', 1, '其他', '', null);
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_INFO_PLATFORM_ID, 'INFO_PLATFORM', '信息平台','评估价格来源（信息平台）', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_INFO_PLATFORM_ID, '1', 1, '58同城', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_INFO_PLATFORM_ID, '2', 1, '赶集网', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_INFO_PLATFORM_ID, '3', 1, '其他', '', null);
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_HOUSE_KEPPER_ID, 'HOUSE_KEPPER', '房屋管家','评估价格来源（房屋管家）', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_HOUSE_KEPPER_ID, '1', 1, '自如', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_HOUSE_KEPPER_ID, '2', 1, '小米', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_HOUSE_KEPPER_ID, '3', 1, '小猪', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_HOUSE_KEPPER_ID, '4', 1, '其他', '', null);

  
  -- 消费贷贷资料上传类型
  SELECT PID INTO SEQ_FILE_PROPERTY_ID FROM sys_lookup WHERE LOOKUP_TYPE = 'FILE_PROPERTY';
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '借款人身份证照片（正）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '借款人身份证照片（反）', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '借款人手持身份证正面照片', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '借款人手持收款银行卡正面照片', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '借款申请表', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '征信报告', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '个人征信查询授权书', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_FILE_PROPERTY_ID, '10', 1, '已婚证明（如：结婚证/户口本）', '', null);

  
  
  
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_CUSTOMER_NATURE_ID, 'CONSUME_CUSTOMER_NATURE', '客户性质', '消费贷客户性质', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CUSTOMER_NATURE_ID, '1', 1, '自雇', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CUSTOMER_NATURE_ID, '2', 1, '受薪', '', null);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CUSTOMER_NATURE_ID, '3', 1, '优良职业', '', null);
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_LOAN_TYPE_ID, 'CONSUME_LOAN_REPAYMENT_TYPE', '还款方式', '消费贷还款方式', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_LOAN_TYPE_ID , '1', 1, '等额本息', '', null);
  
  insert into sys_lookup (PID, LOOKUP_TYPE, LOOKUP_NAME, LOOKUP_DESC, STATUS, CREATE_DATETIME)
  values (SEQ_CONSUME_LOANTERM, 'CONSUME_LOANTERM', '期限', '消费贷贷款期限', 1, sysdate);
  
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONSUME_LOANTERM , '12', 1, '12', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONSUME_LOANTERM , '24', 1, '24', '', null);
  insert into sys_lookup_val (PID, LOOKUP_ID, LOOKUP_VAL, STATUS, LOOKUP_DESC, CREATE_DATETIME, SHOW_INDEX)
  values (SEQ_SYS_LOOKUP_VAL.Nextval, SEQ_CONSUME_LOANTERM , '36', 1, '36', '', null);

  insert into SYS_ROLE (PID, ROLE_NAME, ROLE_DESC, ROLE_CODE,STATUS,PARENT_ID)
  values (SEQ_SYS_ROLE.Nextval, '消费贷风控初审角色', '消费贷风控初审角色', 'CONSUME_RISK_ONE',1,0);
  
  insert into SYS_ROLE (PID, ROLE_NAME, ROLE_DESC, ROLE_CODE,STATUS,PARENT_ID)
  values (SEQ_SYS_ROLE.Nextval, '消费贷申请贷前下户', '消费贷贷申请贷前下户', 'CONSUME_INVESTIGATION',1,0);

  insert into SYS_ROLE (PID, ROLE_NAME, ROLE_DESC, ROLE_CODE,STATUS,PARENT_ID)
  values (SEQ_SYS_ROLE.Nextval, '消费贷复审角色', '消费贷复审角色', 'CONSUME_RISK_TWO',1,0);

END;
/