delete from biz_handle_WORKFLOW;
delete from biz_loan_apply_handle_info;--����ҵ������Ϣ
delete from biz_loan_house_balance;--��¥������ת
delete from biz_loan_handle_dynamic_file;--����̬�ļ�
delete from biz_loan_handle_differ_warn;--����Ԥ�������
delete from biz_HIS_HANDLE_DIFFER_WARN;
delete from biz_loan_handle_dynamic;--����̬
delete from BIZ_LOAN_REFUND_DETAILS;-- �����˿���ϸ
delete from biz_loan_handle_info;--ҵ��������
delete from biz_loan_refund_fee;--��������
delete from biz_Collect_files;--�ռ���
delete from biz_Check_Document;--�鵵��
delete from biz_loan_repayment_record;
delete from biz_loan_repayment;
delete from biz_loan_overdue_Fee;
 
--���ò������Ϊδ����
delete from biz_loan_apply_finance_handle;
delete from  biz_loan_finance_handle t ;
delete from biz_loan_house_balance t;
delete from biz_loan_handle_info t ;
/*update biz_loan_finance_handle t  set t.status=1;
update biz_loan_house_balance t set t.balance_confirm=1;
update biz_loan_handle_info t set t.foreclosure_status=1;*/
--ɾ���˿����̹���
SELECT * FROM BIZ_WORKFLOW_PROJECT T WHERE T.PROCESS_DEFINITION_KEY like '%refund%' FOR UPDATE;
--����Ŀ����¥״̬��Ϊ������
update biz_project a set a.foreclosure_status=13,a.collect_file_status=1 where a.pid in(
SELECT a.pid FROM biz_project a inner join biz_loan_finance_handle b on a.pid=b.project_id ) and a.foreclosure_status>=11;
