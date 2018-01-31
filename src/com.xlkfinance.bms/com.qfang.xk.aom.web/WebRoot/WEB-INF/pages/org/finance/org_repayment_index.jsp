<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>回款列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	function openRepaymentModal() {
		$("div[name='repaymentRecordList']").remove();//清空页面回款历史数据
		var id = $("#table_list").jqGrid('getGridParam', 'selrow');
		if (id == null) {
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
		var jqGridIds = $("#table_list").jqGrid('getGridParam',
		'selarrrow');
		if (jqGridIds.length>1) {
			layer.alert('请不要多选数据', {
				icon : 0
			});
			return;
		}
		var rowData = $("#table_list").jqGrid('getRowData', id);
		//加载回款历史数据
		$
				.ajax({
					url : "${ctx}financeController/getRepaymentInfoHis.action",
					cache : true,
					type : "POST",
					data : {
						'projectId' : rowData.projectId
					},
					async : false,
					success : function(data, status) {
						var result = eval("(" + data + ")");
						//初始化回款记录
						var repaymentRecordList = result['repaymentRecordList'];
						var repaymentMoneyTotal = 0;
						for (var i = 0; i < repaymentRecordList.length; i++) {
							var repaymentMoney = repaymentRecordList[i].repaymentMoney;
							var repaymentDate = repaymentRecordList[i].repaymentDate;
							var accountNo = repaymentRecordList[i].accountNo;
							repaymentMoneyTotal = repaymentMoney
									+ repaymentMoneyTotal;
							var htmlStr = "<div name='repaymentRecordList'><span>  回款金额:"
									+ repaymentMoney
									+ "（元） 回款日期:"
									+ repaymentDate
									+ "  账号:"
									+ accountNo
									+ "</span><div>";
							$("#repaymentHis").append(htmlStr);
						}
						$("#repaymentHis")
								.append(
										"<div name='repaymentRecordList'><b>总计："
												+ repaymentMoneyTotal
												+ "（元）</b></div>");
					}
				});
		$("#repaymentModalLabel").text("回款明细-" + rowData.projectName);//项目名称
		$('#repaymentModal').modal('show');
	}

	$(document)
			.ready(
					function() {
						$.jgrid.defaults.styleUI = "Bootstrap";
						$("#table_list")
								.jqGrid(
										{
											url : "${ctx}financeController/orgRepaymentList.action",
											datatype : "json",
											mtype : "POST",
											caption : "回款列表",
											height : 450,
											autowidth : true,
											shrinkToFit : true,
											multiselect : true,
											multiboxonly : true,
											viewrecords : true,
											postData : $
													.serializeObject($("#searchFrom")),
											rowNum : 10,
											rowList : [ 10, 20, 30 ],
											pager : "#pager_list",
											colNames : [ "repaymentId",
													"projectId", "项目名称",
													"实际回款金额", "实际回款日期",
													"预计回款金额", "预计回款日期", "回款状态",
													"逾期费", "逾期天数", "物业名称",
													"买方姓名", "卖方姓名" ],
											colModel : [
													{
														name : "repaymentId",
														index : "repaymentId",
														hidden : true
													},
													{
														name : "projectId",
														index : "projectId",
														hidden : true
													},
													{
														name : "projectName",
														index : "projectName",
														width : 230,
														sortable : false,
														formatter : function(cellvalue, options, row) {
															return '<a href="#" onclick=openTab("${ctx }projectController/toEditProject.action?pid='+ row.projectId+ '&editType=3","查看业务申请","toLookUpProject.action") >'+row.projectName+'</a>';
														},
														unformat : function(cellvalue, options, row) {
															return cellvalue;
														}
													},
													{
														name : "realRepaymentMoney",
														index : "realRepaymentMoney",
														formatter : formatterMoney2,
														sortable : false
													},
													{
														name : "realRepaymentDate",
														index : "realRepaymentDate",
														formatter : 'date',
														formatoptions : {
															srcformat : 'Y-m-d H:i:s',
															newformat : 'Y-m-d'
														},
														sortable : false
													},
													{
														name : "planRepaymentMoney",
														index : "planRepaymentMoney",
														formatter : formatterMoney2,
														sortable : false
													},
													{
														name : "planRepaymentDate",
														index : "planRepaymentDate",
														formatter : 'date',
														formatoptions : {
															srcformat : 'Y-m-d H:i:s',
															newformat : 'Y-m-d'
														},
														sortable : false
													},
													{
														name : "repaymentStatus",
														index : "repaymentStatus",
														formatter : formatterRepaymentStatus,
														sortable : false
													},
													{
														name : "overdueFee",
														index : "overdueFee",
														formatter : formatterMoney2,
														sortable : false
													}, {
														name : "overdueDay",
														index : "overdueDay",
														sortable : false
													}, {
														name : "houseName",
														index : "houseName",
														sortable : false
													}, {
														name : "buyerName",
														index : "buyerName",
														sortable : false
													}, {
														name : "sellerName",
														index : "sellerName",
														sortable : false
													} ],
											gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
											}
										});
						// 设置jqgrid的宽度  
				        $(window).bind("resize",function() {
				    	    var width = $(".jqGrid_wrapper").width();
				    	    $("#table_list").setGridWidth(width);
				        })
						$("#table_list").jqGrid("navGrid", "#pager_list", {
							edit : false,
							add : false,
							del : false,
							search : false,
							refresh : true
						});
					});
</script>
</head>
<body class="gray-bg">
 <div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
   <div class="col-sm-12">
    <div class="row">
     <div class="col-sm-12">
      <div class="ibox float-e-margins">
       <div class="ibox-title">
        <h5>搜索</h5>
        <div class="ibox-tools">
         <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
         </a>
        </div>
       </div>
       <div class="ibox-content ibox-line">

        <form action="#" method="post" id="searchFrom" name="searchFrom" class="form-horizontal">
         <!-- 查询条件 -->
         <div style="padding: 5px">
          <div class="row">
           <div class="col-md-12">
            <label class="col-md-1 control-label" for="projectName">项目名称:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="projectName" id="projectName" placeholder="项目名称">
            </div>
            <label class="col-md-1 control-label" for="houseName">物业:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="houseName" id="houseName" placeholder="物业名称">
            </div>
            <label class="col-md-1 control-label" for="buyerName">买方:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="buyerName" id="buyerName" placeholder="买方">
            </div>
            <label class="col-md-1 control-label" for="sellerName">卖方:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="sellerName" id="sellerName" placeholder="卖方">
            </div>
           </div>
          </div>
          <br>
          <div class="row">
           <div class="col-md-12">
            <label class="col-md-1 control-label" for="realRepaymentDate" style="padding-top: 0;">实际回款日期:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm laydate-icon layer-date" name="realRepaymentDate" id="realRepaymentDate"
              onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="开始日期"
             >
            </div>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm laydate-icon layer-date" name="realRepaymentDateEnd" id="realRepaymentDateEnd"
              onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="结束日期"
             >
            </div>
            <label class="col-md-1 control-label" for="planRepaymentDate" style="padding-top: 0;">预计回款日期:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm laydate-icon layer-date" name="planRepaymentDate" id="planRepaymentDate"
              onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="开始日期"
             >
            </div>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm laydate-icon layer-date" name="planRepaymentDateEnd" id="planRepaymentDateEnd"
              onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="结束日期"
             >
            </div>
           </div>
          </div>
          <br>
          <div class="row  forum-info">
           <!-- 操作按钮 -->
           <button class="btn btn-primary an_bk" type="button" onclick="searchList('#table_list')">
            <i class="fa fa-check"></i> <span class="bold">查询</span>
           </button>
           <button class="btn btn-w-m btn-white cz_an" type="reset">
            <i class="fa fa-warning"></i> <span class="bold">重置</span>
           </button>
          </div>
         </div>
        </form>
       </div>
      </div>
     </div>
    </div>

    <div class="row">
     <div class="col-sm-12">
      <div class="ibox float-e-margins">
       <div class="ibox-content">
       <a class="btn btn-outline btn-primary" onclick="openRepaymentModal()">查看回款明细</a>
           <!-- <a class="btn btn-outline btn-danger" onclick="checkProof()">查看凭证</a> -->
        <div class="jqGrid_wrapper">
         <table id="table_list"></table>
         <div id="pager_list"></div>
        </div>
       </div>
      </div>
     </div>
    </div>

   </div>
  </div>
 </div>
 <div class="modal fade" id="repaymentModal" tabindex="-1" role="dialog" aria-labelledby="repaymentModalLabel" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content animated flipInY">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     <h4 class="modal-title" id="repaymentModalLabel">回款明细</h4>
    </div>
    <div class="modal-body">
     <div id="repaymentHis" style="line-height: 25px;"></div>

    </div>
    <div class="modal-footer"></div>
   </div>
  </div>
 </div>
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>