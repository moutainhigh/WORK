<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>息费明细列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	function openOrgFeeSettleModal() {
		var id = $("#table_list").jqGrid('getGridParam', 'selrow');
		if (id == null) {
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
	}

	$(document)
			.ready(
					function() {
						$.jgrid.defaults.styleUI = "Bootstrap";
						$("#table_list")
								.jqGrid(
										{
											url : "${ctx}financeController/partnerFeeSettleDetailList.action",
											datatype : "json",
											mtype : "POST",
											caption : "息费明细结算列表",
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
											colNames : [ "feeSettleDetailId",
													"settleId", "projectId", "项目名称",
													"返佣金额", "返佣率", "放款金额","放款日期",
													"回款金额", "回款日期", "解保日期"],
											colModel : [ {
												name : "feeSettleDetailId",
												index : "feeSettleDetailId",
												hidden : true
											}, {
												name : "settleId",
												index : "settleId",
												hidden : true
											}, {
												name : "projectId",
												index : "projectId",
												hidden : true
											}, {
												name : "projectName",
												index : "projectName",
												sortable : false
											}, {
												name : "returnComm",
												index : "returnComm",
												formatter : formatterMoney2,
												sortable : false
											}, {
												name : "returnCommRate",
												index : "returnCommRate",
												sortable : false
											},{
												name : "loanMoney",
												index : "loanMoney",
												formatter : formatterMoney2,
												sortable : false
											}, {
												name : "loanDate",
												index : "loanDate",
												formatter : 'date',
												formatoptions : {
													srcformat : 'Y-m-d H:i:s',
													newformat : 'Y-m-d'
												},
												sortable : false
											}, {
												name : "paymentMoney",
												index : "paymentMoney",
												formatter : formatterMoney2,
												sortable : false
											}, {
												name : "paymentDate",
												index : "paymentDate",
												formatter : 'date',
												formatoptions : {
													srcformat : 'Y-m-d H:i:s',
													newformat : 'Y-m-d'
												},
												sortable : false
											}, {
												name : "solutionDate",
												index : "solutionDate",
												formatter : 'date',
												formatoptions : {
													srcformat : 'Y-m-d H:i:s',
													newformat : 'Y-m-d'
												},
												sortable : false
											}],
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
           <input type="hidden" name="settleId" value="${settleId}"> 
            <label class="col-md-1 control-label" for="projectName">项目名称:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="projectName" id="projectName" placeholder="项目名称">
            </div>
           </div>
          </div>
          <br> <br>
          <div class="row forum-info">
           <!-- 操作按钮 -->
           <button class="btn btn-primary  an_bk" type="button" onclick="searchList('#table_list')">
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
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>