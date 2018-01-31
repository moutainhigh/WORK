--SLD201600189	韦娓	用款天数	万通系统，原用款5天后纸质申请改10天系统未改
update biz_project_foreclosure t set t.loan_days = 10 where t.project_id = 11599 and t.loan_days = 5;
--SLD201600178	郭永表	 11,000.00 	 8,250.00 	咨询费	万通系统
update biz_project_guarantee t set t.guarantee_fee = 8250 where t.project_id = 11588;
--SLD201600177	表治国	用款天数	小科系统，用款天数录错，实际申请用款40天
update biz_project_foreclosure t set t.loan_days = 40 where t.project_id = 11587 and t.loan_days = 30;
--SLD201600287	赵钦鹏	借款天数	小科系统，借款天数为10天，系统为5天
update biz_project_foreclosure t set t.loan_days = 10 where t.project_id = 11617 and t.loan_days = 5;

--SLD201600154	朱晓玲	 9,000.00 	 3,000.00   逾期费用  小科系统，业务线上逾期线下展期，9000逾期费用已确认
update biz_loan_overdue_fee t set t.overdue_fee=3000  where t.project_id=11550;
--SLD201600183  赵军   33,060.00   45240-36192＝9048  逾期费用  小科系统，业务申请15天后展期30天收费45240，提前还款后退费36192，实际收展期费用9048元，已按33060元误确认。
update biz_loan_overdue_fee t set t.overdue_fee=9048  where t.project_id=11593;
--SLD201600146  傅华   10,575.00    3,525.00   逾期费用  小科系统，线上逾期线下展期，实收展期费用3525元
update biz_loan_overdue_fee t set t.overdue_fee=3525  where t.project_id=11542;
----SLD201600057  林松金   12,870.00    -     逾期费用  小科系统，客户提前一天还款无逾期，此业务查看不到整个业务流程，逾期费已误确认
update biz_loan_overdue_fee t set t.overdue_fee=0,t.overdue_day=0 where t.project_id=11446;
--SLD201600291  韦志威  6月14日  7月14日  放款日期  小科系统，放款日期录错，实际7月14日放款
update biz_loan_apply_finance_handle afh set afh.rec_date=to_date('2016-07-14','yyyy-MM-dd') where afh.finance_handle_id =
(SELECT fh.pid FROM biz_loan_finance_handle fh where fh.project_id=11701);
--SLD201600287  赵钦鹏  5  10  借款天数  小科系统，借款天数为10天，系统为5天，实际提前回款无逾期，现系统显示逾期费用7200已误确认7200.0  0  逾期费用  
update biz_loan_overdue_fee t set t.overdue_fee=0,t.overdue_day=0 where t.project_id=11617;
--SLD201600295  张韶军   16,150.00    8,760.00   逾期费用  万通系统，线下展期线上逾期，16150费用已误确认
update biz_loan_overdue_fee t set t.overdue_fee=8760 where t.project_id=11705;
--SLD201600057  林松金   12,870.00    -     逾期费用  小科系统，客户提前一天还款无逾期，此业务查看不到整个业务流程，逾期费已误确认
update biz_loan_overdue_fee t set t.overdue_fee=0,t.overdue_day=0 where t.project_id=11446;


