<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>客户列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	var selValShowArray;
	$(document).ready(function() {
		$.jgrid.defaults.styleUI = "Bootstrap";
		$("#table_list").jqGrid({
			url : "${basePath}/customerController/perListUrl.action",
			datatype : "json",
			mtype : "POST",
			caption : "客户列表",
			height : 450,
			width : 980,
			shrinkToFit : true,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			postData : $.serializeObject($("#searchFrom")),
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : "#pager_list",
			colNames : [ "pid", "姓名", "性别", "证件类型", "证件号码", "手机号码" ,"婚姻状况","操作"],
			colModel : [{
				name : "value1",
				index : "value1",
				hidden : true
			},  {
				name : "value2",
				index : "value2",
				width:120
			}, {
				name : "value3",
				index : "value3",
				width:80,
				sortable : false
			}, {
				name : "value4",
				index : "value4",
				width:80,
				sortable : false
			}, {
				name : "value5",
				index : "value5",
				width:180,
				sortable : false
			}, {
				name : "value11",
				width:100,
				index : "value11",
				sortable : false
			}, {
				name : "value15",
				width:100,
				index : "value15"
			}, {
				name : "operate",
				index : "operate",
				sortable : false,
				formatter : function(cellvalue, options, row) {
				var htmlStr = "";
				htmlStr += '<a onclick="lookupItem('+row.pid+');">&nbsp查看&nbsp</a>';
				htmlStr += '<a onclick="editItem('+row.pid+');">&nbsp编辑&nbsp</a>';
				htmlStr += '<a onclick="removeItem('+row.pid+','+row.value9+');">&nbsp删除&nbsp</a>';
				return htmlStr;
				}
			}],
			gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
			}
		});
		$("#table_list").jqGrid("navGrid", "#pager_list", {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : true
		});
	});
	$(function() {
		setCombobox("sex", "SEX", "");
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

        <form action="studentAction_list.do" method="post" id="searchFrom" name="searchFrom" class="form-horizontal"> 
         <input type="hidden" name="cusAcct.orgId" value="${loginUser.orgId}"/>
         <!-- 查询条件 -->
         <div style="padding: 5px">
          <div class="row">
           <div class="col-md-12">
            <label class="col-md-1 control-label" for="cusName">客户姓名:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="cusName" id="cusName" placeholder="姓名">
            </div>
            <label class="col-md-1 control-label" for="sexId">客户性别:</label>
            <div class="col-md-2">
             <select class="form-control input-sm" name="sexId" id="sex"></select>
            </div>
            <label class="col-md-1 control-label" for="certNumber">证件号码:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="certNumber" id="certNumber" placeholder="证件号码">
            </div>
           </div>
          </div>
          <br>
          <div class="row" style="text-align: center;">
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
       	<a class="btn btn-outline btn-primary" onclick="addItem()">新增</a> 
      	<!-- <a class="btn btn-outline btn-primary " href="#" onclick="openRejectInfo()" >驳回信息</a> -->
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
</body>
<script type="text/javascript">
//新增
function addItem() {
	openTab("${basePath}/customerController/editPerBase.action?editType=1","新增客户",'toAddPerBase.action');
}
//编辑
function editItem(pid) {
	openTab('${basePath}/customerController/editPerBase.action?editType=2&acctId='+ pid,"修改客户",'toEditPerBase.action');
}
//查看
function lookupItem(pid){
	openTab('${basePath}/customerController/editPerBase.action?editType=3&acctId='+ pid,"查看客户",'toLookUpPerBase.action');
}
//删除客户信息
function removeItem(pid,cusStatus){
	if(cusStatus != '' && cusStatus == 4){
		layer.alert('借贷中的客户不允许删除！', {
			icon : 2
		});
		return false;
	}
	layer.confirm("确定删除选择人信息吗?", {
		icon : 3,
		btn : [ '是','否' ]
	//按钮
	}, function() {
		$.ajax({									
			url : "${basePath}/customerController/deleteCusAcct.action?pids="+pid,
			type : "POST",
			async : false,
			success : function(result) { //表单提交后更新页面显示的数据
					window.location.reload();
				}
		});
	}, function() {
	});
	
}
</script>
<!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</html>