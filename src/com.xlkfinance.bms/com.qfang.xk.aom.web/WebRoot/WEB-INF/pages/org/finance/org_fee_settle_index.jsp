<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>息费列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	function formatterSettleStatus(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "未申请";
		} else if (cellvalue == 2) {
			return "审核中";
		} else if (cellvalue == 3) {
			return "已结算";
		} else if (cellvalue == 4) {
			return "审核驳回";
		} else if (cellvalue == 5) {
			return "审核否决";
		} else {
			return "未知";
		}
	}
	function openOrgFeeSettleModal() {
		var id = $("#table_list").jqGrid('getGridParam', 'selrow');
		if (id == null) {
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
		$("#orgFeeSettleForm").resetForm();//清空缓存
		var rowData = $("#table_list").jqGrid('getRowData', id);
		$("#orgFeeSettleId").val(rowData.orgFeeSettleId);//息费结算id
		$("#money").val(rowData.money);
		$("#accountDate").val(rowData.accountDate);
		$("#bankName").val(rowData.bankName);
		$("#accountName").val(rowData.accountName);
		$("#bankCardNo").val(rowData.bankCardNo);
		$("#remark").val(rowData.remark);
		$("#orgFeeSettleModalLabel").text("息费结算-" + rowData.projectName);//项目名称
		$('#orgFeeSettleModal').modal('show');
	}
	//查看凭证
	function checkProof() {
		var id = $("#table_list").jqGrid('getGridParam', 'selrow');
		if (id == null) {
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
		var rowData = $("#table_list").jqGrid('getRowData', id);
		var proofUrl = rowData.proofUrl;
		if (proofUrl == null || proofUrl == "") {
			layer.alert('未上传凭证，请上传后在查看', {
				icon : 0
			});
			return;
		}
		window.location.href = "${ctx }download.action?path=" + proofUrl;
	}

	
	$(document)
			.ready(
					function() {
						$('#file-pretty input[type="file"]').prettyFile();
						$.jgrid.defaults.styleUI = "Bootstrap";
						$("#table_list")
								.jqGrid(
										{
											url : "${ctx}financeController/orgFeeSettleList.action",
											datatype : "json",
											mtype : "POST",
											caption : "息费结算列表",
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
											colNames : [ "pid", "结算日期", "借款金额",
													"放款金额", "回款金额", "退费金额", "收费金额",
													 "操作" ],
											colModel : [
													{
														name : "pid",
														index : "pid",
														hidden : true
													},
													{
														name : "settleDate",
														index : "settleDate",
														formatter : 'date',
														formatoptions : {
															srcformat : 'Y-m-d H:i:s',
															newformat : 'Y-m'
														},
														sortable : false
													},
													{
														name : "applyMoneyTotal",
														index : "applyMoneyTotal",
														sortable : false,
														formatter : formatterMoney2
													},
													{
														name : "loanMoneyTotal",
														index : "loanMoneyTotal",
														sortable : false,
														formatter : formatterMoney2
													},
													{
														name : "paymentMoneyTotal",
														index : "paymentMoneyTotal",
														sortable : false,
														formatter : formatterMoney2
													},
													{
														name : "refundMoneyTotal",
														index : "refundMoneyTotal",
														sortable : false,
														formatter : formatterMoney2
													},
													{
														name : "chargeMoneyTotal",
														index : "chargeMoneyTotal",
														sortable : false,
														formatter : formatterMoney2
													},
													{
														name : "operate",
														index : "operate",
														formatter : function(cellvalue,options, row) {
															var str = '<a onclick=openTab("${ctx }financeController/toOrgFeeSettleDetailIndex.action?settleId='+row.pid+'","息费明细","toOrgFeeSettleDetailIndex.action")>明细</a>';
															return str;
														}
													}  ],
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
            <label class="col-md-1 control-label" for="settleDate">结算日期:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm laydate-icon layer-date" name="settleDate" id="settleDate"
              onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="开始日期"
             >
            </div>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm laydate-icon layer-date" name="settleDateEnd" id="settleDateEnd"
              onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="结束日期"
             >
            </div>
           </div>
          </div>
          <br>
          <div class="row forum-info">
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
 <div class="modal fade" id="orgFeeSettleModal" tabindex="-1" role="dialog" aria-labelledby="orgFeeSettleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content animated flipInY">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     <h4 class="modal-title" id="orgFeeSettleModalLabel">息费结算</h4>
    </div>
    <form role="form" class="form-horizontal" id="orgFeeSettleForm" name="orgFeeSettleForm" method="post" action="#">
     <div class="modal-body">
      <input type="hidden" name="pid" id="orgFeeSettleId">
      <div class="form-group">
       <label for="money" class="col-sm-2 control-label">金额:</label>
       <div class="col-sm-4">
        <input type="text" class="form-control input-sm" id="money" name="money" placeholder="息费结算金额" maxlength="20">
       </div>
       <label for="accountDate" class="col-sm-2 control-label">到账日期:</label>
       <div class="col-sm-4">
        <input type="text" class="form-control input-sm laydate-icon layer-date" id="accountDate" name="accountDate" placeholder="到账日期" maxlength="20"
         onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
        >
       </div>
      </div>
      <div class="form-group">
       <label for="bankName" class="col-sm-2 control-label">银行名称:</label>
       <div class="col-sm-4">
        <input type="text" class="form-control input-sm" id="bankName" name="bankName" placeholder="开户行名称" maxlength="20">
       </div>
       <label for="accountName" class="col-sm-2 control-label">帐户姓名:</label>
       <div class="col-sm-4">
        <input type="text" class="form-control input-sm" id="accountName" name="accountName" placeholder="帐户姓名" maxlength="20">
       </div>
      </div>
      <div class="form-group">
       <label for="bankCardNo" class="col-sm-2 control-label">银行卡号:</label>
       <div class="col-sm-4">
        <input type="text" class="form-control input-sm" id="bankCardNo" name="bankCardNo" placeholder="银行卡号" maxlength="20">
       </div>
       <label for="remark" class="col-sm-2 control-label">备注:</label>
       <div class="col-sm-4">
        <input type="text" class="form-control input-sm" id="remark" name="remark" placeholder="备注" maxlength="20">
       </div>
      </div>
      <div class="form-group">
       <label for="offlineMeetingFile" class="col-sm-2 control-label">上传凭证:</label>
       <div class="col-sm-10">
        <div id="file-pretty">
         <input type="file" class="form-control" name="offlineMeetingFile" id="offlineMeetingFiles">
        </div>
       </div>
      </div>
     </div>
     <div class="modal-footer">
      <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      <button type="submit" class="btn btn-primary">提交</button>
     </div>
    </form>
   </div>
  </div>
 </div>
 <!-- 需要延迟加载的资源 -->
 <script src="${ctx }js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>