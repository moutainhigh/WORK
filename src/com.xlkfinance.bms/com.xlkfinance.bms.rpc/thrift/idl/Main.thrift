/**
 * @Title: Main.thrift
 * @Description: 编译入口
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月12日 下午1:36:23
 * @version V1.0
 */

//	通用模块
include "Common.thrift"

//	系统管理
include "System.thrift"

// 客户管理模块
include "Customer.thrift"

// 贷前管理模块
include "Beforeloan.thrift" 

// 工作流引擎管理
include "Workflow.thrift"

// 还款管理
include "Repayment.thrift" 

//合同管理
include "Contract.thrift"

// 额度管理(授信)
include "Credits.thrift"

// 抵质押物管理
include "Mortgage.thrift"

// 财务管理
include "Finance.thrift"

// 任务管理
include "Task.thrift"

// 贷后管理
include "AfterLoan.thrift"

// 贷中管理
include "Inloan.thrift" 
// 贷中管理
include "Product.thrift" 
// 消息推送管理
include "MessagePush.thrift"
// 统计分析报表
include "Report.thrift"
//合作机构项目
include "Partner.thrift"

//机构管理平台--分页参数
//include "aom/Page.thrift"

//机构管理平台--资产合作用户
include "aom/System.thrift"
//机构管理平台--资产合作机构信息
include "aom/Org.thrift"

//机构管理平台--合伙人
include "aom/Partner.thrift"

//机构管理平台--业务申请
include "aom/Project.thrift"

//机构管理平台--财务相关
include "aom/Finance.thrift"

//机构管理平台--费用相关
include "aom/Fee.thrift"

//自动查档管理
include "CheckApi.thrift"

//修改记录管理
include "Record.thrift"
//赎楼贷后管理
include "ForeAfterLoan.thrift"
//房抵贷贷后管理
include "FddAfterLoan.thrift"

// 统计分析报表
include "OtherReport.thrift"

// 法定节假日管理
include "WorkDay.thrift"

include "Project.thrift"

include "MortgageLoan.thrift"
