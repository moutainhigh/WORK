
/*==============================================================*/
/* Table: ORG_ADDITIONAL_COOPERATION_INF                        */
/*==============================================================*/
create table ORG_ADDITIONAL_COOPERATION_INF 
(
   PID                  NUMBER               not null,
   COOPERATION_ID       NUMBER,
   CONTRACT_NO          VARCHAR2(50),
   CONTRACT_URL         VARCHAR2(300),
   CREDIT_CONTRACT_ID   VARCHAR2(50),
   CREDIT_CONTRACT_URL  VARCHAR2(300),
   GURANTEE_NO          VARCHAR2(50),
   GURANTEE_PATH        VARCHAR2(300),
   CREATOR_ID           NUMBER,
   CREATED_DATETIME     TIMESTAMP,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   REMARK               VARCHAR2(1024),
   constraint PK_ORG_ADDITIONAL_COOPERATION_ primary key (PID)
);
comment on table ORG_ADDITIONAL_COOPERATION_INF is
'机构合作附加信息';
comment on column ORG_ADDITIONAL_COOPERATION_INF.PID is
'附加信息编号';
comment on column ORG_ADDITIONAL_COOPERATION_INF.COOPERATION_ID is
'合作编号';
comment on column ORG_ADDITIONAL_COOPERATION_INF.CONTRACT_NO is
'合同编号';
comment on column ORG_ADDITIONAL_COOPERATION_INF.CONTRACT_URL is
'合同存放地址';
comment on column ORG_ADDITIONAL_COOPERATION_INF.CREDIT_CONTRACT_ID is
'授信合同编号';
comment on column ORG_ADDITIONAL_COOPERATION_INF.CREDIT_CONTRACT_URL is
'授信合同存放地址';
comment on column ORG_ADDITIONAL_COOPERATION_INF.GURANTEE_NO is
'保证函编号';
comment on column ORG_ADDITIONAL_COOPERATION_INF.GURANTEE_PATH is
'保证函存放地址';
comment on column ORG_ADDITIONAL_COOPERATION_INF.CREATOR_ID is
'创建者';
comment on column ORG_ADDITIONAL_COOPERATION_INF.CREATED_DATETIME is
'创建时间';
comment on column ORG_ADDITIONAL_COOPERATION_INF.UPDATE_ID is
'更新者';
comment on column ORG_ADDITIONAL_COOPERATION_INF.UPDATE_DATE is
'更新时间';
comment on column ORG_ADDITIONAL_COOPERATION_INF.REMARK is
'备注';

--机构合作合同
create table ORG_COOPERATION_CONTRACT
(
   PID                  NUMBER  primary key,
   COOPERATION_ID       NUMBER,
   CONTRACT_NAME          VARCHAR2(50),
   CONTRACT_NO          VARCHAR2(50),
   FILE_ID              NUMBER,
   CONTRACT_TYPE        NUMBER,
   CREATOR_ID           NUMBER,
   CREATED_DATETIME     TIMESTAMP,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   REMARK               VARCHAR2(1024)
);
comment on table ORG_COOPERATION_CONTRACT is
'机构合作合同';
comment on column ORG_COOPERATION_CONTRACT.PID is
'附加信息编号';
comment on column ORG_COOPERATION_CONTRACT.COOPERATION_ID is
'合作编号';
comment on column ORG_COOPERATION_CONTRACT.CONTRACT_NAME is
'合同名称';
comment on column ORG_COOPERATION_CONTRACT.CONTRACT_NO is
'合同编号';
comment on column ORG_COOPERATION_CONTRACT.FILE_ID is
'文件id';
comment on column ORG_COOPERATION_CONTRACT.CONTRACT_TYPE is
'合同类型：合作合同=10,授信合同=20';
comment on column ORG_COOPERATION_CONTRACT.CREATOR_ID is
'创建者';
comment on column ORG_COOPERATION_CONTRACT.CREATED_DATETIME is
'创建时间';
comment on column ORG_COOPERATION_CONTRACT.UPDATE_ID is
'更新者';
comment on column ORG_COOPERATION_CONTRACT.UPDATE_DATE is
'更新时间';
comment on column ORG_COOPERATION_CONTRACT.REMARK is
'备注';
/*==============================================================*/
/* Table: ORG_ASSETS_COOPERATION_INFO                           */
/*==============================================================*/
create table ORG_ASSETS_COOPERATION_INFO 
(
   PID                  NUMBER               not null,
   ORG_NAME             VARCHAR2(100),
   CODE                 VARCHAR2(100),
   ADDRESS              VARCHAR2(300),
   EMAIL                VARCHAR2(200),
   CONTACT              VARCHAR2(50),
   PHONE                VARCHAR2(20),
   AUDIT_STATUS         NUMBER  default 1,
   COOPERATE_STATUS     NUMBER  default 1,
   BIZ_ADVISER_ID       NUMBER,
   PARTNER_ID           NUMBER,
   "LEVEL"              NUMBER,
   CREATOR_ID           NUMBER,
   CREATED_DATE         TIMESTAMP,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   REMARK               VARCHAR2(1024),
   AUDIT_DESC           VARCHAR2(1024),
   constraint PK_ORG_ASSETS_COOPERATION_INFO primary key (PID)
);
comment on table ORG_ASSETS_COOPERATION_INFO is
'机构管理平台--资产合作机构信息';
comment on column ORG_ASSETS_COOPERATION_INFO.PID is
'机构ID(和机构用户id同主键)';
comment on column ORG_ASSETS_COOPERATION_INFO.ORG_NAME is
'机构名称';
comment on column ORG_ASSETS_COOPERATION_INFO.CODE is
'机构代码';
comment on column ORG_ASSETS_COOPERATION_INFO.ADDRESS is
'地址';
comment on column ORG_ASSETS_COOPERATION_INFO.EMAIL is
'公司邮箱';
comment on column ORG_ASSETS_COOPERATION_INFO.CONTACT is
'联系人';
comment on column ORG_ASSETS_COOPERATION_INFO.PHONE is
'联系电话';
comment on column ORG_ASSETS_COOPERATION_INFO.AUDIT_STATUS is
'审核状态1:未认证,2表示认证中3、已认证';
comment on column ORG_ASSETS_COOPERATION_INFO.COOPERATE_STATUS is
'合作状态,1:未合作,2表示已合作,3表示已过期';
comment on column ORG_ASSETS_COOPERATION_INFO.BIZ_ADVISER_ID is
'商务顾问,关联商务顾问表';
comment on column ORG_ASSETS_COOPERATION_INFO.PARTNER_ID is
'合伙人,关联合伙人表';
comment on column ORG_ASSETS_COOPERATION_INFO."LEVEL" is
'等级';
comment on column ORG_ASSETS_COOPERATION_INFO.CREATOR_ID is
'创建者';
comment on column ORG_ASSETS_COOPERATION_INFO.CREATED_DATE is
'创建时间';
comment on column ORG_ASSETS_COOPERATION_INFO.UPDATE_ID is
'更新者';
comment on column ORG_ASSETS_COOPERATION_INFO.UPDATE_DATE is
'更新时间';
comment on column ORG_ASSETS_COOPERATION_INFO.REMARK is
'备注';
comment on column ORG_ASSETS_COOPERATION_INFO.AUDIT_DESC is
'认证说明';
/*==============================================================*/
/* Table: ORG_ASSURE_FUND_FLOW_INFO                             */
/*==============================================================*/
create table ORG_ASSURE_FUND_FLOW_INFO 
(
   PID                  NUMBER,
   APPLY_ID             NUMBER,
   OLD_ASSURE_MONEY     NUMBER(12,2),
   CUR_ASSURE_MONEY     NUMBER(12,2),
   UPDATE_DATE          TIMESTAMP,
   OPERATOR             NUMBER,
   CREATED_DATETIME     TIMESTAMP,
   "AUDIT"              VARCHAR2(1024),
   AUDIT_DATE           TIMESTAMP,
   AUDIT_ID             NUMBER,
   STATUS               NUMBER,
   OLD_CREDIT_LIMIT     NUMBER(12,2),
   CUR_CREDIT_LIMIT     NUMBER(12,2)
);
comment on table ORG_ASSURE_FUND_FLOW_INFO is
'合作机构保证金额变更流水';
comment on column ORG_ASSURE_FUND_FLOW_INFO.UPDATE_DATE is
'变更日期';
comment on column ORG_ASSURE_FUND_FLOW_INFO.OPERATOR is
'操作人';
comment on column ORG_ASSURE_FUND_FLOW_INFO.CREATED_DATETIME is
'操作时间';
comment on column ORG_ASSURE_FUND_FLOW_INFO."AUDIT" is
'审核意见';
comment on column ORG_ASSURE_FUND_FLOW_INFO.AUDIT_DATE is
'审核日期';
comment on column ORG_ASSURE_FUND_FLOW_INFO.AUDIT_ID is
'审核人';
comment on column ORG_ASSURE_FUND_FLOW_INFO.STATUS is
'状态,10表示同意变更,20表示不同意变更,1待审核';
comment on column ORG_ASSURE_FUND_FLOW_INFO.OLD_CREDIT_LIMIT is
'原授信额度';
comment on column ORG_ASSURE_FUND_FLOW_INFO.CUR_CREDIT_LIMIT is
'当前授信额度';
/*==============================================================*/
/* Table: ORG_BANK_ACCOUNT_INFO                                 */
/*==============================================================*/
create table ORG_BANK_ACCOUNT_INFO 
(
   PID                  NUMBER               not null,
   USER_ID              NUMBER,
   USER_TYPE            NUMBER,
   BANK_NAME            VARCHAR2(100),
   ACCOUNT_NAME         VARCHAR2(50),
   CARD_NO              VARCHAR2(20),
   PHONE                VARCHAR2(300),
   GUANRANTEE_URL       VARCHAR2(300),
   BINDING_DATE         TIMESTAMP,
   CREATE_ID            NUMBER,
   CREATE_DATE          TIMESTAMP,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   REMARK               VARCHAR2(1024),
   constraint PK_ORG_BANK_ACCOUNT_INFO primary key (PID)
);
comment on table ORG_BANK_ACCOUNT_INFO is
'开户行信息';
comment on column ORG_BANK_ACCOUNT_INFO.USER_ID is
'用户编号';
comment on column ORG_BANK_ACCOUNT_INFO.USER_TYPE is
'用户类型,1表示合伙人,2表示合作机构';
comment on column ORG_BANK_ACCOUNT_INFO.BANK_NAME is
'开户行名称';
comment on column ORG_BANK_ACCOUNT_INFO.ACCOUNT_NAME is
'账户名称';
comment on column ORG_BANK_ACCOUNT_INFO.CARD_NO is
'卡号';
comment on column ORG_BANK_ACCOUNT_INFO.PHONE is
'银行预留手机';
comment on column ORG_BANK_ACCOUNT_INFO.GUANRANTEE_URL is
'保证函URL';
comment on column ORG_BANK_ACCOUNT_INFO.BINDING_DATE is
'绑定日期';
comment on column ORG_BANK_ACCOUNT_INFO.CREATE_ID is
'创建者';
comment on column ORG_BANK_ACCOUNT_INFO.CREATE_DATE is
'创建时间';
comment on column ORG_BANK_ACCOUNT_INFO.UPDATE_ID is
'更新者';
comment on column ORG_BANK_ACCOUNT_INFO.UPDATE_DATE is
'更新时间';
comment on column ORG_BANK_ACCOUNT_INFO.REMARK is
'备注';
/*==============================================================*/
/* Table: ORG_BIZ_ADVISER_ALLOCATION_INF                        */
/*==============================================================*/
create table ORG_BIZ_ADVISER_ALLOCATION_INF 
(
   PID                  NUMBER               not null,
   SERVICE_OBJ_ID       NUMBER,
   BIZ_ADVISER_ID       NUMBER,
   TYPE                 NUMBER,
   REMARK          VARCHAR2(2048),
   CREATER_DATE     TIMESTAMP,
   CREATER_ID       INTEGER,
   UPDATE_ID        INTEGER,
   UPDATE_DATE      TIMESTAMP,
   constraint PK_ORG_BIZ_ADVISER_ALLOCATION_ primary key (PID)
);
comment on table ORG_BIZ_ADVISER_ALLOCATION_INF is
'商务顾问分配表';
comment on column ORG_BIZ_ADVISER_ALLOCATION_INF.PID is
'编号';
comment on column ORG_BIZ_ADVISER_ALLOCATION_INF.SERVICE_OBJ_ID is
'服务对象编号(机构系统用户id)';
comment on column ORG_BIZ_ADVISER_ALLOCATION_INF.BIZ_ADVISER_ID is
'商务顾问编号(ERP后台系统用户id)';
comment on column ORG_BIZ_ADVISER_ALLOCATION_INF.TYPE is
'1表示合作机构,2表示合伙人';
comment on column ORG_BIZ_ADVISER_ALLOCATION_INF.REMARK is
'备注';
COMMENT ON COLUMN ORG_BIZ_ADVISER_ALLOCATION_INF.CREATER_ID IS 
'创建人ID(ERP后台系统用户id)'; 
COMMENT ON COLUMN ORG_BIZ_ADVISER_ALLOCATION_INF.CREATER_DATE IS 
'创建时间'; 
COMMENT ON COLUMN ORG_BIZ_ADVISER_ALLOCATION_INF.UPDATE_ID IS 
'更新人ID(ERP后台系统用户id)'; 
COMMENT ON COLUMN ORG_BIZ_ADVISER_ALLOCATION_INF.UPDATE_DATE IS 
'更新时间';

/*==============================================================*/
/* Table: ORG_COOPERATE_CITY_INFO                               */
/*==============================================================*/
create table ORG_COOPERATE_CITY_INFO 
(
   PID                  NUMBER,
   AREA_CODE            VARCHAR2(10),
   CITY_NAME            VARCHAR2(255),
   ORG_ID               NUMBER,
   constraint PK_ORG_COOPERATE_CITY_INFO_ primary key (PID)
);
comment on table ORG_COOPERATE_CITY_INFO is
'合作城市信息表';

comment on column ORG_COOPERATE_CITY_INFO.PID is
'PID';

comment on column ORG_COOPERATE_CITY_INFO.AREA_CODE is
'城区编码';

comment on column ORG_COOPERATE_CITY_INFO.CITY_NAME is
'城市名称';

comment on column ORG_COOPERATE_CITY_INFO.ORG_ID is
'机构编号';
/*==============================================================*/
/* Table: ORG_COOPERAT_COMPANY_APPLY_INF                        */
/*==============================================================*/
create table ORG_COOPERAT_COMPANY_APPLY_INF 
(
   PID                  NUMBER,
   ORG_ID               NUMBER,
   USER_ID              NUMBER,
   START_TIME           TIMESTAMP,
   END_TIME             TIMESTAMP,
   CREDIT_LIMIT         NUMBER(12,2),
   AVAILABLE_LIMIT      NUMBER(12,2),
   ASSURE_MONEY         NUMBER(12,2),
   REAL_ASSURE_MONEY         NUMBER(12，2),
   STATUS               NUMBER,
   ITEM                 VARCHAR2(2048),
   CREATOR_ID           NUMBER,
   CREATED_DATE         TIMESTAMP,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   REMARK               VARCHAR2(1024),
   DATA_VERSION    NUMBER default 1,
   ACTUAL_FEE_RATE      NUMBER(12,2),
   FUND_SIZE_MONEY      NUMBER(12,2),
   IS_NEED_HANDLE       INTEGER DEFAULT 2,
   RATE                 NUMBER(12,2),
   SINGLE_UPPER_LIMIT   NUMBER(12,2),
   APPLY_STATUS         NUMBER,
   constraint PK_ORG_COOPERAT_COMPANY_APPLY_ primary key (PID)
);
comment on table ORG_COOPERAT_COMPANY_APPLY_INF is
'机构管理平台---机构合作申请信息';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.PID is
'合作编号';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.ORG_ID is
'机构编号,关联资产机构合作信息表';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.USER_ID is
'用户编号,关联用户表';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.START_TIME is
'合作开始时间';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.END_TIME is
'合作结束时间';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.CREDIT_LIMIT is
'授信额度';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.AVAILABLE_LIMIT is
'可用额度';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.ASSURE_MONEY is
'保证金';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.REAL_ASSURE_MONEY is
'实收保证金';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.STATUS is
'保证金额调整状态,1表示正常（不做调整）,2表示待审核,3表示已调整';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.ITEM is
'合作条款';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.CREATOR_ID is
'创建者';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.CREATED_DATE is
'创建时间';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.UPDATE_ID is
'更新者';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.UPDATE_DATE is
'更新时间';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.REMARK is
'备注';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.DATA_VERSION
  is '版本,用于处理并发';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.IS_NEED_HANDLE is
'是否需要办理贷中1、办理2、不办理';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.RATE is
'预收费率';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.SINGLE_UPPER_LIMIT is
'每笔订单的上限';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.APPLY_STATUS is
'申请状态 1、未提交2、审核中3、审核通过4、审核不通过';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.ACTUAL_FEE_RATE is
'实际收费费率';
comment on column ORG_COOPERAT_COMPANY_APPLY_INF.FUND_SIZE_MONEY is
'机构资金出款规模，大于规模金额使用实收费率，小于出款规模使用预收费率';
/*==============================================================*/
/* Table: ORG_EMPY_BTN_INFO                                     */
/*==============================================================*/
create table ORG_EMPY_BTN_INFO 
(
   PID                  NUMBER               not null,
   USER_ID              NUMBER,
   BTN_ID               NUMBER,
   BTN_CODE             VARCHAR2(50),
   constraint PK_ORG_EMPY_BTN_INFO primary key (PID)
);
comment on table ORG_EMPY_BTN_INFO is
'机构员工按钮权限表';
comment on column ORG_EMPY_BTN_INFO.USER_ID is
'用户编号';
comment on column ORG_EMPY_BTN_INFO.BTN_ID is
'按钮编号';
comment on column ORG_EMPY_BTN_INFO.BTN_CODE is
'按钮编码';


/*==============================================================*/
/* Table: ORG_FUND_FLOW_INFO                                    */
/*==============================================================*/
create table ORG_FUND_FLOW_INFO 
(
   PID                  NUMBER               not null,
   TRANS_TYPE           NUMBER,
   TRANS_MONEY          NUMBER(12,2),
   USER_ACCOUNT_ID      NUMBER,
   TRANS_DATE           TIMESTAMP,
   TRANS_NO             VARCHAR2(50),
   TOTAL_MONEY          NUMBER(12,2),
   AVAILABLE_MONEY      NUMBER(12,2),
   PAYMENT_TYPE         NUMBER,
   STATUS               NUMBER,
   constraint PK_ORG_FUND_FLOW_INFO primary key (PID)
);
comment on table ORG_FUND_FLOW_INFO is
'资金账户交易流水(充值/提现)';
comment on column ORG_FUND_FLOW_INFO.PID is
'流水号';
comment on column ORG_FUND_FLOW_INFO.TRANS_TYPE is
'交易类型';
comment on column ORG_FUND_FLOW_INFO.TRANS_MONEY is
'交易金额';
comment on column ORG_FUND_FLOW_INFO.USER_ACCOUNT_ID is
'用户帐户';
comment on column ORG_FUND_FLOW_INFO.TRANS_DATE is
'交易日期';
comment on column ORG_FUND_FLOW_INFO.TRANS_NO is
'交易流水';
comment on column ORG_FUND_FLOW_INFO.TOTAL_MONEY is
'当前余额';
comment on column ORG_FUND_FLOW_INFO.AVAILABLE_MONEY is
'当前可用余额';
comment on column ORG_FUND_FLOW_INFO.PAYMENT_TYPE is
'支付方式,1表示线下转账,2表示网银支付,其他方式一次递增';
comment on column ORG_FUND_FLOW_INFO.STATUS is
'状态,1表示交易失败,2表示交易成功,3表示交易中';
/*==============================================================*/
/* Table: ORG_PAGE_BUTTON_INFO                                  */
/*==============================================================*/
create table ORG_PAGE_BUTTON_INFO 
(
   PID                  NUMBER               not null,
   BTN_NAME             VARCHAR2(20),
   BTN_CODE             VARCHAR2(50),
   MODULE               VARCHAR2(50),
   PAGE                 VARCHAR2(100),
   REMARK               VARCHAR2(1024),
   constraint PK_ORG_PAGE_BUTTON_INFO primary key (PID)
);
comment on table ORG_PAGE_BUTTON_INFO is
'机构管理平台操作按钮信息';
comment on column ORG_PAGE_BUTTON_INFO.BTN_NAME is
'按钮名称';
comment on column ORG_PAGE_BUTTON_INFO.BTN_CODE is
'按钮编码,此值唯一';
comment on column ORG_PAGE_BUTTON_INFO.MODULE is
'所属模块';
comment on column ORG_PAGE_BUTTON_INFO.PAGE is
'所属页面';
comment on column ORG_PAGE_BUTTON_INFO.REMARK is
'备注';
/*==============================================================*/
/* Table: ORG_PARTNER_INO                                       */
/*==============================================================*/
create table ORG_PARTNER_INFO 
(
  PID                NUMBER NOT NULL,
  USER_ID            NUMBER,
  START_TIME         TIMESTAMP(6),
  END_TIME           TIMESTAMP(6),
  CARD_NO            VARCHAR2(20),
  PAYEE_ACCOUNT      VARCHAR2(50),
  RATE               NUMBER(12,2),
  COOPERATION_STATUS NUMBER,
  CONTRACT_ID        NUMBER,
  URL                VARCHAR2(300),
  COOPERATION_DESC   VARCHAR2(1024),
  STATUS             NUMBER  DEFAULT 1,
  BIZ_ADVISER_ID     NUMBER,
  CREATOR_ID         NUMBER,
  CREATE_DATE        TIMESTAMP(6),
  UPDATE_ID          NUMBER,
  UPDATE_DATE        TIMESTAMP(6),
  REMARK             VARCHAR2(1024),
  AUDIT_DESC         VARCHAR2(1024),
  REVIEW_STATUS      NUMBER,
  REVIEW_DESC        VARCHAR2(1024),
  REVIEW_ID          NUMBER,
  REVIEW_TIME        TIMESTAMP(6),
  APPLY_TIME         TIMESTAMP(6),
   constraint ORG_PARTNER_INFO primary key (PID)
);
comment on table ORG_PARTNER_INFO is
'机构管理平台--合伙人信息';
comment on column ORG_PARTNER_INFO.PID is
'合伙人编号';
comment on column ORG_PARTNER_INFO.USER_ID is
'用户编号';
comment on column ORG_PARTNER_INFO.START_TIME is
'合作开始时间';
comment on column ORG_PARTNER_INFO.END_TIME is
'合作结束时间';
comment on column ORG_PARTNER_INFO.CARD_NO is
'身份证号码';
comment on column ORG_PARTNER_INFO.PAYEE_ACCOUNT is
'收款账号';
comment on column ORG_PARTNER_INFO.RATE is
'提成比例';
comment on column ORG_PARTNER_INFO.COOPERATION_STATUS is
'合伙人合作申请状态 1、未申请10、审核中20、审核通过30、审核不通过';
comment on column ORG_PARTNER_INFO.CONTRACT_ID is
'合同编号';
comment on column ORG_PARTNER_INFO.URL is
'合同存放地址';
comment on column ORG_PARTNER_INFO.COOPERATION_DESC is
'合作说明';
comment on column ORG_PARTNER_INFO.STATUS is
'认证状态1:未认证,2表示认证中3、已认证';
comment on column ORG_PARTNER_INFO.BIZ_ADVISER_ID is
'商务顾问,关联商务顾问表';
comment on column ORG_PARTNER_INFO.CREATOR_ID is
'创建者';
comment on column ORG_PARTNER_INFO.CREATE_DATE is
'创建时间';
comment on column ORG_PARTNER_INFO.UPDATE_ID is
'更新者';
comment on column ORG_PARTNER_INFO.UPDATE_DATE is
'更新时间';
comment on column ORG_PARTNER_INFO.REMARK is
'备注';
comment on column ORG_PARTNER_INFO.AUDIT_DESC
  is '认证说明';
comment on column ORG_PARTNER_INFO.REVIEW_STATUS
  is '审核状态1:未申请、10、审核中20、审核通过30、审核不通过';
comment on column ORG_PARTNER_INFO.REVIEW_DESC
  is '审核意见';
comment on column ORG_PARTNER_INFO.REVIEW_ID
  is '审核人';
comment on column ORG_PARTNER_INFO.REVIEW_TIME
  is '审核时间';
comment on column ORG_PARTNER_INFO.APPLY_TIME
  is '合作申请时间';
/*==============================================================*/
/* Table: ORG_SYS_LOG_INFO                                      */
/*==============================================================*/
create table ORG_SYS_LOG_INFO 
(
   PID                  NUMBER,
   ORDER_ID             NUMBER,
   TYPE                 NUMBER,
   CONTENT              VARCHAR2(2048),
   IP_ADDRESS           VARCHAR2(50),
   OPERATOR             NUMBER,
   CREATE_DATE          TIMESTAMP
);
comment on table ORG_SYS_LOG_INFO is
'系统访问日志';
comment on column ORG_SYS_LOG_INFO.ORDER_ID is
'订单id';
comment on column ORG_SYS_LOG_INFO.TYPE is
'操作类型,1表示添加,2表示删除,3表示修改,4表示登录,4表示登出';
comment on column ORG_SYS_LOG_INFO.CONTENT is
'操作内容';
comment on column ORG_SYS_LOG_INFO.IP_ADDRESS is
'IP地址';
comment on column ORG_SYS_LOG_INFO.OPERATOR is
'操作人';
comment on column ORG_SYS_LOG_INFO.CREATE_DATE is
'操作日期';
/*==============================================================*/
/* Table: ORG_SYS_MENU_INFO                                     */
/*==============================================================*/
create table ORG_SYS_MENU_INFO 
(
   PID                  NUMBER               not null,
   MENU_NAME            VARCHAR2(50),
   PARENT_ID            NUMBER,
   "LEVEL"              NUMBER,
   URL                  VARCHAR2(300),
   STATUS               NUMBER  default 1,
   MENU_INDEX           NUMBER,
   ICON_CLS             VARCHAR2(50),
   USER_TYPE             NUMBER,
   constraint PK_ORG_SYS_MENU_INFO primary key (PID)
);
comment on table ORG_SYS_MENU_INFO is
'机构管理平台系统菜单';
comment on column ORG_SYS_MENU_INFO.PID is
'菜单编号';
comment on column ORG_SYS_MENU_INFO.MENU_NAME is
'菜单名称';
comment on column ORG_SYS_MENU_INFO.PARENT_ID is
'父级菜单编号,顶级菜单默认为0';
comment on column ORG_SYS_MENU_INFO."LEVEL" is
'菜单层级';
comment on column ORG_SYS_MENU_INFO.URL is
'URL';
comment on column ORG_SYS_MENU_INFO.STATUS is
'状态:有效=1,无效=2';
comment on column ORG_SYS_MENU_INFO.MENU_INDEX is
'索引';
comment on column ORG_SYS_MENU_INFO.ICON_CLS is
'图标样式';
comment on column ORG_SYS_MENU_INFO.USER_TYPE is
'菜单归属于用户类型：机构=1,合伙人=2';
/*==============================================================*/
/* Table: ORG_SYS_ROLE_INFO                                     */
/*==============================================================*/
create table ORG_SYS_ROLE_INFO 
(
   PID                  NUMBER,
   ROLE_NAME            VARCHAR2(50),
   ROLE_CODE            VARCHAR2(50),
   CREATOR_ID           NUMBER,
   CREATED_DATE         TIMESTAMP,
   ISDEL                NUMBER,
   STATUS               NUMBER  default 1
);
comment on table ORG_SYS_ROLE_INFO is
'机构平台系统角色信息';
comment on column ORG_SYS_ROLE_INFO.PID is
'角色编号';
comment on column ORG_SYS_ROLE_INFO.ROLE_NAME is
'角色名称';
comment on column ORG_SYS_ROLE_INFO.ROLE_CODE is
'角色编码,英文编码';
comment on column ORG_SYS_ROLE_INFO.CREATOR_ID is
'创建者';
comment on column ORG_SYS_ROLE_INFO.CREATED_DATE is
'创建日期';
comment on column ORG_SYS_ROLE_INFO.ISDEL is
'是否允许删除,1表示不允许删除,2表示允许删除,默认2';
comment on column ORG_SYS_ROLE_INFO.STATUS is
'状态,1表示有效,2表示无效';
/*==============================================================*/
/* Table: ORG_USER_FUN_ACCOUNT                                  */
/*==============================================================*/
create table ORG_USER_FUN_ACCOUNT 
(
   PID                  NUMBER,
   USER_ID              NUMBER,
   TOTAL_MONEY          NUMBER(12,2),
   AVAILABLE_MONEY      NUMBER(12,2),
   FREEZE_MONEY         NUMBER(12,2),
   TRADER_PASSWORD      VARCHAR2(50),
   UPDATE_DATE          TIMESTAMP,
   STATUS               NUMBER  default 1
);
comment on table ORG_USER_FUN_ACCOUNT is
'机构管理平台用户资金帐户';
comment on column ORG_USER_FUN_ACCOUNT.PID is
'账号编号';
comment on column ORG_USER_FUN_ACCOUNT.USER_ID is
'用户编号';
comment on column ORG_USER_FUN_ACCOUNT.TOTAL_MONEY is
'总资金';
comment on column ORG_USER_FUN_ACCOUNT.AVAILABLE_MONEY is
'可用资金';
comment on column ORG_USER_FUN_ACCOUNT.FREEZE_MONEY is
'冻结资金';
comment on column ORG_USER_FUN_ACCOUNT.TRADER_PASSWORD is
'交易密码';
comment on column ORG_USER_FUN_ACCOUNT.UPDATE_DATE is
'更新日期';
comment on column ORG_USER_FUN_ACCOUNT.STATUS is
'状态,1表示有效,2表示无效';
/*==============================================================*/
/* Table: ORG_USER_INFO                                         */
/*==============================================================*/
create table ORG_USER_INFO 
(
   PID                  NUMBER               not null,
   USER_NAME            VARCHAR2(100),
   PASSWORD             VARCHAR2(50),
   REAL_NAME            VARCHAR2(100),
   NICK_NAME            VARCHAR2(50),
   JOB_NO               VARCHAR2(20),
   DEPT_NAME            VARCHAR2(50),
   PHONE                VARCHAR2(20),
   EMAIL                VARCHAR2(100),
   ORG_ID               NUMBER,
   ROLE                 NUMBER,
   STATUS               NUMBER default 1,
   USER_TYPE            NUMBER,
   DATE_SCOPE           NUMBER default 1,
   CREATE_ID            NUMBER,
   CREATE_DATE          TIMESTAMP,
   UPDATE_ID            NUMBER,
   UPDATE_DATE          TIMESTAMP,
   REMARK               VARCHAR2(1024),
   constraint PK_ORG_USER_INFO primary key (PID)
);
comment on table ORG_USER_INFO is
'机构管理平台--用户表';
comment on column ORG_USER_INFO.USER_NAME is
'用户名';
comment on column ORG_USER_INFO.PASSWORD is
'登录密码';
comment on column ORG_USER_INFO.REAL_NAME is
'真实姓名';
comment on column ORG_USER_INFO.NICK_NAME is
'昵称';
comment on column ORG_USER_INFO.JOB_NO is
'工号';
comment on column ORG_USER_INFO.DEPT_NAME is
'部门';
comment on column ORG_USER_INFO.PHONE is
'联系电话';
comment on column ORG_USER_INFO.EMAIL is
'Email';
comment on column ORG_USER_INFO.ORG_ID is
'所属机构,当用户为合伙人时,机构编号为0';
comment on column ORG_USER_INFO.ROLE is
'角色';
comment on column ORG_USER_INFO.STATUS is
'状态,1表示启用,2表示禁用';
comment on column ORG_USER_INFO.USER_TYPE is
'用户类型,1表示机构,2表示合伙人,2表示员工(机构下的员工)';
comment on column ORG_USER_INFO.DATE_SCOPE is
'数据权限,1表示私有,2表示所有';
comment on column ORG_USER_INFO.CREATE_ID is
'创建者';
comment on column ORG_USER_INFO.CREATE_DATE is
'创建时间';
comment on column ORG_USER_INFO.UPDATE_ID is
'更新者';
comment on column ORG_USER_INFO.UPDATE_DATE is
'更新时间';
comment on column ORG_USER_INFO.REMARK is
'备注';
/*==============================================================*/
/* Table: ORG_USER_MENU_INFO                                    */
/*==============================================================*/
create table ORG_USER_MENU_INFO 
(
   PID                   NUMBER               not null,
   USER_ID              NUMBER,
   ORG_ID               NUMBER,
   MENU_ID              NUMBER,
   constraint PK_ORG_USER_MENU_INFO primary key (PID)
);
comment on table ORG_USER_MENU_INFO is
'员工与菜单关联表';
comment on column ORG_USER_MENU_INFO.PID is
'关联编号';
comment on column ORG_USER_MENU_INFO.USER_ID is
'员工编号';
comment on column ORG_USER_MENU_INFO.ORG_ID is
'机构编号';
comment on column ORG_USER_MENU_INFO.MENU_ID is
'菜单编号';

/*==============================================================*/
/* Table: T_MENU_ROLE                                           */
/*==============================================================*/
create table ORG_MENU_ROLE 
(
   PID                  NUMBER               not null,
   MENU_ID              NUMBER,
   ROLE_ID              NUMBER,
   ORG_ID               NUMBER,
   constraint PK_ORG_MENU_ROLE primary key (PID)
);
comment on table ORG_MENU_ROLE is
'菜单角色关联信息';

comment on column ORG_MENU_ROLE.PID is
'编号';

comment on column ORG_MENU_ROLE.MENU_ID is
'菜单编号';

comment on column ORG_MENU_ROLE.ROLE_ID is
'角色编号';

comment on column ORG_MENU_ROLE.ORG_ID is
'机构编号';
/*==============================================================*/
/* Table: t_sms_validate_code_info                            */
/*==============================================================*/
create table T_SMS_VALIDATE_CODE_INFO 
(
   PID                   NUMBER               not null,
   TELPHONE             varchar(20)          not null,
   CODE                 varchar(10)          not null,
   SEND_DATE            timestamp,
   CREATE_DATE          timestamp            not null,
   CREATOR_ID           NUMBER,
   CATEGORY             NUMBER,
   STATUS               NUMBER               default 1 not null,
   IP_ADDRESS               varchar(55),
   constraint "PK_t_sms_validate_code_info" primary key (PID)
);
comment on table T_SMS_VALIDATE_CODE_INFO is
'短信验证码信息表';
comment on column T_SMS_VALIDATE_CODE_INFO.PID is
'编号';
comment on column T_SMS_VALIDATE_CODE_INFO.TELPHONE is
'手机号码';
comment on column T_SMS_VALIDATE_CODE_INFO.CODE is
'验证码';
comment on column T_SMS_VALIDATE_CODE_INFO.SEND_DATE is
'发送时间';
comment on column T_SMS_VALIDATE_CODE_INFO.CREATE_DATE is
'创建时间';
comment on column T_SMS_VALIDATE_CODE_INFO.CREATOR_ID is
'创建者id';
comment on column T_SMS_VALIDATE_CODE_INFO.CATEGORY is
'短信验证码类型,1表示注册验证,2表示密码找回,其他类型依次递增';
comment on column T_SMS_VALIDATE_CODE_INFO.STATUS is
'状态,1表示有效,2表示失效';
comment on column T_SMS_VALIDATE_CODE_INFO.IP_ADDRESS is
'IP地址';
CREATE SEQUENCE SEQ_SMS_VALIDATE_CODE_INFO;--短信验证码表

CREATE SEQUENCE SEQ_ORG_USER_INFO;
CREATE SEQUENCE SEQ_ORG_SYS_ROLE_INFO;
CREATE SEQUENCE SEQ_ORG_USER_FUN_ACCOUNT;
CREATE SEQUENCE SEQ_ORG_SYS_MENU_INFO;--
CREATE SEQUENCE SEQ_ORG_COOPERATION_CONTRACT;--
CREATE SEQUENCE SEQ_ORG_PARTNER_INFO;
CREATE SEQUENCE SEQ_ORG_ADDITIONAL_COOPERATION;
CREATE SEQUENCE SEQ_ORG_ASSETS_COOPERATION;
CREATE SEQUENCE SEQ_ORG_ASSURE_FUND_FLOW_INFO;
CREATE SEQUENCE SEQ_ORG_COOPERATE_CITY_INFO;
CREATE SEQUENCE SEQ_ORG_COOPERAT_COMPANY_APPLY;
CREATE SEQUENCE SEQ_ORG_FEE_SETTLE_INFO;
CREATE SEQUENCE SEQ_ORG_CASH_FLOW_APPLY_INFO;
CREATE SEQUENCE SEQ_ORG_USER_MENU_INFO;
CREATE SEQUENCE SEQ_ORG_SYS_LOG_INFO;
CREATE SEQUENCE SEQ_ORG_USER_FILE;
CREATE SEQUENCE SEQ_ORG_ASSURE_FUND_RECORD;
CREATE SEQUENCE SEQ_ORG_ADVISER_ALLOCATION_INF;

--角色表初始数据
insert into ORG_SYS_ROLE_INFO (PID,ROLE_NAME,ROLE_CODE,CREATED_DATE,ISDEL,STATUS)
values(SEQ_ORG_SYS_ROLE_INFO.NEXTVAL,'机构','ORG_ASSETS',SYSDATE,1,1);
insert into ORG_SYS_ROLE_INFO (PID,ROLE_NAME,ROLE_CODE,CREATED_DATE,ISDEL,STATUS)
values(SEQ_ORG_SYS_ROLE_INFO.NEXTVAL,'合伙人','ORG_PARTNER',SYSDATE,1,1);
--菜单初始数据
   
create table CUS_RELATION 
(
   PID                INTEGER               NOT NULL,
   ACCT_ID            INTEGER,
   ORG_ID             INTEGER,
   ORG_TYPE           INTEGER default 1,
   PM_USER_ID         INTEGER,
   constraint PK_CUS_RELATION primary key (pid)
);

CREATE SEQUENCE SEQ_CUS_RELATION;

comment on column CUS_RELATION.PID is
'主键';

comment on column CUS_RELATION.ACCT_ID is
'关联客户cus_acct表主键';

comment on column CUS_RELATION.ORG_ID is
'所属机构ID';

comment on column CUS_RELATION.ORG_TYPE is
'机构类型（1、小科以及万通2、外部机构）';

comment on column CUS_RELATION.PM_USER_ID is
'所属用户ID ';


 --费用结算主表
 CREATE TABLE ORG_FEE_SETTLE 
(
   PID                INTEGER              NOT NULL,
   ORG_ID             INTEGER NOT NULL,
   PARTNER_ID         INTEGER,
   APPLY_MONEY_TOTAL  NUMBER(12,2),
   LOAN_MONEY_TOTAL   NUMBER(12,2),
   PAYMENT_MONEY_TOTAL NUMBER(12,2),
   SETTLE_DATE        TIMESTAMP,
   REBATE_RATE        NUMBER(12,2),
   RETURN_COMM_RATE   NUMBER(12,2),
   REFUND_MONEY_TOTAL NUMBER(12,2),
   RETURN_COMM_TOTAL  NUMBER(12,2),
   CHARGE_MONEY_TOTAL NUMBER(12,2),
   REBATE_MONEY_TOTAL NUMBER(12,2),
   REBATE_TYPE        INTEGER,
   EXTIMATE_FEE_RATE    NUMBER(12,2),
   ACTUAL_FEE_RATE      NUMBER(12,2),
   CONSTRAINT PK_ORG_FEE_SETTLE PRIMARY KEY (PID)
);
CREATE SEQUENCE SEQ_ORG_FEE_SETTLE;
COMMENT ON COLUMN ORG_FEE_SETTLE.PID IS
'息费结算编号';

COMMENT ON COLUMN ORG_FEE_SETTLE.ORG_ID IS
'机构ID';

COMMENT ON COLUMN ORG_FEE_SETTLE.PARTNER_ID IS
'合伙人ID';

COMMENT ON COLUMN ORG_FEE_SETTLE.APPLY_MONEY_TOTAL IS
'借款总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.LOAN_MONEY_TOTAL IS
'放款总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.PAYMENT_MONEY_TOTAL IS
'回款总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.SETTLE_DATE IS
'结算日期(年月)';

COMMENT ON COLUMN ORG_FEE_SETTLE.REBATE_RATE IS
'返点率';

COMMENT ON COLUMN ORG_FEE_SETTLE.RETURN_COMM_RATE IS
'返佣率';

COMMENT ON COLUMN ORG_FEE_SETTLE.REFUND_MONEY_TOTAL IS
'退费总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.RETURN_COMM_TOTAL IS
'返佣总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.CHARGE_MONEY_TOTAL IS
'收费总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.REBATE_MONEY_TOTAL IS
'返点总金额';

COMMENT ON COLUMN ORG_FEE_SETTLE.REBATE_TYPE IS
'返点状态1、有返点2、无返点';
comment on column ORG_FEE_SETTLE.EXTIMATE_FEE_RATE is
'预计收费费率';
comment on column ORG_FEE_SETTLE.ACTUAL_FEE_RATE is
'实际收费费率';
alter table ORG_FEE_SETTLE
  add constraint FK_AHI_ORG_ID foreign key (ORG_ID)
  references ORG_ASSETS_COOPERATION_INFO (PID);
alter table ORG_FEE_SETTLE
  add constraint FK_AHI_PARTNER_ID foreign key (PARTNER_ID)
  references ORG_PARTNER_INFO (PID);
  
--费用结算明细表
CREATE TABLE ORG_FEE_SETTLE_DETAIL 
(
   PID                INTEGER  NOT NULL,
   SETTLE_ID          INTEGER NOT NULL,
   PROJECT_ID         INTEGER NOT NULL,
   LOAN_MONEY         NUMBER(12,2),
   PAYMENT_MONEY      NUMBER(12,2),
   CHARGE             NUMBER(12,2),
   REFUND             NUMBER(12,2),
   REBATE_MONEY       NUMBER(12,2),
   RETURN_COMM        NUMBER(12,2),
   PAYMENT_DATE       TIMESTAMP,
   LOAN_DATE          TIMESTAMP,
   RETURN_COMM_RATE   NUMBER(12,2),
   REBATE_RATE        NUMBER(12,2),
   SOLUTION_DATE      TIMESTAMP,
   EXTIMATE_FEE_RATE    NUMBER(12,2),
   ACTUAL_FEE_RATE      NUMBER(12,2),
   PLAN_LOAN_DAYS       NUMBER,
   ACTUAL_LOAN_DAYS     NUMBER,
   REAL_CHARGE_MONEY    NUMBER(12,2),
   CONSTRAINT PK_ORG_FEE_SETTLE_DETAIL PRIMARY KEY (PID)
);
CREATE SEQUENCE SEQ_ORG_FEE_SETTLE_DETAIL;
COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.PID IS
'结算明细编号';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.SETTLE_ID IS
'结算编号';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.PROJECT_ID IS
'项目编号';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.LOAN_MONEY IS
'借款金额';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.PAYMENT_MONEY IS
'回款金额';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.CHARGE IS
'收费金额';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.REFUND IS
'退费金额';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.REBATE_MONEY IS
'返点金额';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.RETURN_COMM IS
'返佣金额';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.PAYMENT_DATE IS
'回款日期';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.LOAN_DATE IS
'借款日期';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.RETURN_COMM_RATE IS
'返佣率';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.REBATE_RATE IS
'返点率';

COMMENT ON COLUMN ORG_FEE_SETTLE_DETAIL.SOLUTION_DATE IS
'解保日期';
comment on column ORG_FEE_SETTLE_DETAIL.EXTIMATE_FEE_RATE is
'预计收费费率';

comment on column ORG_FEE_SETTLE_DETAIL.ACTUAL_FEE_RATE is
'实际收费费率';

comment on column ORG_FEE_SETTLE_DETAIL.PLAN_LOAN_DAYS is
'计划借款天数';

comment on column ORG_FEE_SETTLE_DETAIL.ACTUAL_LOAN_DAYS is
'实际借款天数';

comment on column ORG_FEE_SETTLE_DETAIL.REAL_CHARGE_MONEY is
'应收费金额';

alter table ORG_FEE_SETTLE_DETAIL
  add constraint FK_AHI_SETTLE_ID foreign key (SETTLE_ID)
  references ORG_FEE_SETTLE (PID);
 
alter table ORG_FEE_SETTLE_DETAIL
  add constraint FK_AHI_PROJECT_ID foreign key (PROJECT_ID)
  references BIZ_PROJECT (PID);
--费用信息表添加唯一性约束,机构ID,时间以及合伙人ID
ALTER TABLE ORG_FEE_SETTLE
	ADD CONSTRAINT UNIQUE_ORG_FEE
	UNIQUE (ORG_ID, SETTLE_DATE,PARTNER_ID);
--费用详情表添加唯一性约束,费用表ID,项目ID
ALTER TABLE ORG_FEE_SETTLE_DETAIL
	ADD CONSTRAINT UNIQUE_ORG_FEE_DETAIL
	UNIQUE (SETTLE_ID,PROJECT_ID);
	
--业务申请驳回信息表
create table ORDER_REJECT_INFO 
(
   PID                  NUMBER               not null,
   ORDER_ID             NUMBER,
   EXAMINE_USER         NUMBER,
   EXAMINE_DATE         TIMESTAMP,
   EXAMINE_OPINION      VARCHAR2(2048),
   constraint PK_ORDER_REJECT_INFO primary key (PID)
);
CREATE SEQUENCE SEQ_ORDER_REJECT_INFO;
comment on table ORDER_REJECT_INFO is
'订单驳回信息表';
comment on column ORDER_REJECT_INFO.PID is
'编号';
comment on column ORDER_REJECT_INFO.ORDER_ID is
'订单编号';
comment on column ORDER_REJECT_INFO.EXAMINE_USER is
'操作人';
comment on column ORDER_REJECT_INFO.EXAMINE_DATE is
'操作日期';
comment on column ORDER_REJECT_INFO.EXAMINE_OPINION is
'驳回意见';

-- 机构用户资料附件表
create table ORG_USER_FILE
(
  PID           NUMBER NOT NULL,
  USER_ID    NUMBER,
  FILE_ID       NUMBER,
  STATUS        NUMBER DEFAULT 1
);
-- Add comments to the table 
comment on table ORG_USER_FILE
  is '机构用户资料附件表';
-- Add comments to the columns 
comment on column ORG_USER_FILE.PID
  is '主键';
comment on column ORG_USER_FILE.USER_ID
  is '用户ID';
comment on column ORG_USER_FILE.FILE_ID
  is '文件ID';
comment on column ORG_USER_FILE.STATUS
  is '状态：有效=1,失效=2';
alter table ORG_USER_FILE
  add constraint FK_UF_U_ID foreign key (USER_ID)
  references ORG_USER_INFO (PID);

-- 保证金记录表
CREATE TABLE ORG_ASSURE_FUND_RECORD
(
   PID                  NUMBER,
   MONEY             NUMBER(12,2),
   REC_DATE          TIMESTAMP,
   ACCOUNT_NAME    VARCHAR2(50),
   "ACCOUNT"       VARCHAR2(50),
   BANK            VARCHAR2(50),
   "TYPE"             NUMBER  default 1,
   ORG_ID             NUMBER,
   REMARK          VARCHAR2(2048),
   CREATER_DATE     TIMESTAMP,
   CREATER_ID       INTEGER,
   UPDATE_ID        INTEGER,
   UPDATE_DATE      TIMESTAMP
);
COMMENT ON TABLE ORG_ASSURE_FUND_FLOW_INFO IS
'保证金记录表';
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.MONEY
  IS '金额'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.REC_DATE
  IS '到账日期'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.ACCOUNT_NAME
  IS '账户名称'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.ACCOUNT
  IS '账号'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.BANK
  IS '银行'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.TYPE
  IS '类型：收保证金=1,退保证金=2'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.ORG_ID
  IS '机构ID'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.REMARK
  IS '备注'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.CREATER_ID
  IS '创建人ID'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.CREATER_DATE
  IS '创建时间'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.UPDATE_ID
  IS '更新人ID'; 
COMMENT ON COLUMN ORG_ASSURE_FUND_RECORD.UPDATE_DATE
  IS '更新时间';

COMMENT ON COLUMN CUS_ACCT.CUS_TYPE
  IS '客户类型:个人=1，企业=2，机构企业=3';
  
--资产机构信息表  
create table CAPITAL_ORG_INFO 
(
   PID                  INTEGER              not null,
   ORG_NAME             VARCHAR2(255),
   ORG_CODE             VARCHAR2(255),
   LOAN_MONEY_TOTAL     NUMBER(12,2),
   STATUS               INTEGER,
   CREATE_DATE          TIMESTAMP,
   CREATE_ID            INTEGER,
   UPDATE_ID            INTEGER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_CAPITAL_ORG_INFO primary key (PID)
);
CREATE SEQUENCE SEQ_CAPITAL_ORG_INFO;
comment on column CAPITAL_ORG_INFO.PID is
'主键';

comment on column CAPITAL_ORG_INFO.ORG_NAME is
'机构名称';

comment on column CAPITAL_ORG_INFO.ORG_CODE is
'机构编码';

comment on column CAPITAL_ORG_INFO.LOAN_MONEY_TOTAL is
'总放款金额';

comment on column CAPITAL_ORG_INFO.STATUS is
'状态(1、启用2、禁用)';

comment on column CAPITAL_ORG_INFO.CREATE_DATE is
'创建时间';

comment on column CAPITAL_ORG_INFO.CREATE_ID is
'创建人';

comment on column CAPITAL_ORG_INFO.UPDATE_ID is
'修改人';

comment on column CAPITAL_ORG_INFO.UPDATE_DATE is
'修改时间';

create table ORG_CAPITAL_COST 
(
   PID                  INTEGER              not null,
   ORG_ID               INTEGER,
   YEAR_RATE            NUMBER(12,2),
   STEP_AMOUNT          NUMBER(12,2),
   CREATE_DATE          TIMESTAMP,
   CREATE_ID            INTEGER,
   UPDATE_ID            INTEGER,
   UPDATE_DATE          TIMESTAMP,
   constraint PK_ORG_CAPITAL_COST primary key (PID)
);
CREATE SEQUENCE SEQ_ORG_CAPITAL_COST;
comment on column ORG_CAPITAL_COST.PID is
'主键';

comment on column ORG_CAPITAL_COST.ORG_ID is
'机构id';

comment on column ORG_CAPITAL_COST.YEAR_RATE is
'年利率';

comment on column ORG_CAPITAL_COST.STEP_AMOUNT is
'阶梯金额';

comment on column ORG_CAPITAL_COST.CREATE_DATE is
'创建时间';

comment on column ORG_CAPITAL_COST.CREATE_ID is
'创建人';

comment on column ORG_CAPITAL_COST.UPDATE_ID is
'修改人';

comment on column ORG_CAPITAL_COST.UPDATE_DATE is
'修改时间';

alter table ORG_CAPITAL_COST
   add constraint FK_ORG_CAPI_REFERENCE_CAPITAL_ foreign key (ORG_ID)
      references CAPITAL_ORG_INFO (PID);