<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>业务办理列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$.jgrid.defaults.styleUI = "Bootstrap";
						$("#table_list")
								.jqGrid(
										{
											url : "${ctx}bizHandleController/bizHandlePageList.action",
											datatype : "json",
											mtype : "POST",
											caption : "业务办理列表",
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
											colNames : [ "bizHandleId",
													"projectId", "项目名称",
													"贷款金额", "放款金额", "项目状态",
													"提交人", "物业名称", "买方姓名",
													"卖方姓名" ],
											colModel : [ {
												name : "bizHandleId",
												index : "bizHandleId",
												hidden : true
											}, {
												name : "projectId",
												index : "projectId",
												hidden : true
											}, {
												name : "projectName",
												index : "projectName",
												sortable : false,
												formatter : function(cellvalue, options, row) {
													return '<a href="#" onclick=openTab("${ctx }projectController/toEditProject.action?pid='+ row.projectId+ '&editType=3","查看业务申请","toLookUpProject.action") >'+row.projectName+'</a>';
												}
											}, {
												name : "loanMoney",
												index : "loanMoney",
												formatter : formatterMoney2,
												sortable : false
											}, {
												name : "realLoan",
												index : "realLoan",
												formatter : formatterMoney2,
												sortable : false
											}, {
												name : "projectStatus",
												index : "projectStatus",
												formatter : formatterProjectStatus,
												sortable : false
											}, {
												name : "applyUserName",
												index : "applyUserName",
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
	
	function showBizHandle(){
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
		var bizHandleId=rowData.bizHandleId;
		openTab('${ctx}bizHandleController/toShowBizHandle.action?bizHandleId='+bizHandleId,'保后监管查看','${ctx}bizHandleController/toShowBizHandle.action')
	}
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
       <a class="btn btn-outline btn-primary" onclick="showBizHandle()">查看</a>
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