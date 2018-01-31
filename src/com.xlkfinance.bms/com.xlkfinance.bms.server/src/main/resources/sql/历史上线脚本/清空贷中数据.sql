delete from biz_handle_WORKFLOW;
delete from biz_loan_apply_handle_info;--申请业务处理信息
delete from biz_loan_house_balance;--赎楼及余额回转
delete from biz_loan_handle_dynamic_file;--办理动态文件
delete from biz_loan_handle_differ_warn;--差异预警处理表
delete from biz_HIS_HANDLE_DIFFER_WARN;
delete from biz_loan_handle_dynamic;--办理动态
delete from BIZ_LOAN_REFUND_DETAILS;-- 财务退款明细
delete from biz_loan_handle_info;--业务处理主表
delete from biz_loan_refund_fee;--退手续费
delete from biz_Collect_files;--收件表
delete from biz_Check_Document;--查档表
delete from biz_loan_repayment_record;
delete from biz_loan_repayment;
delete from biz_loan_overdue_Fee;
 
--设置财务办理为未申请
delete from biz_loan_apply_finance_handle;
delete from  biz_loan_finance_handle t ;
delete from biz_loan_house_balance t;
delete from biz_loan_handle_info t ;
/*update biz_loan_finance_handle t  set t.status=1;
update biz_loan_house_balance t set t.balance_confirm=1;
update biz_loan_handle_info t set t.foreclosure_status=1;*/
--删除退款流程关联
SELECT * FROM BIZ_WORKFLOW_PROJECT T WHERE T.PROCESS_DEFINITION_KEY like '%refund%' FOR UPDATE;
--把项目的赎楼状态改为已审批
update biz_project a set a.foreclosure_status=13,a.collect_file_status=1 where a.pid in(
SELECT a.pid FROM biz_project a inner join biz_loan_finance_handle b on a.pid=b.project_id ) and a.foreclosure_status>=11;
