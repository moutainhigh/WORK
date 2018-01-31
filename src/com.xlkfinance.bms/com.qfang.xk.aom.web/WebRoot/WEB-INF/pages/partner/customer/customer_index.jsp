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
	function formatterAuditStatus(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "未认证";
		} else if (cellvalue == 2) {
			return "认证中";
		} else if (cellvalue == 3) {
			return "已认证";
		} else if (cellvalue == 4) {
			return "认证未通过";
		} else {
			return "未知";
		}
	}
	function formatterCooperateStatus(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "未合作";
		} else if (cellvalue == 2) {
			return "已合作";
		} else if (cellvalue == 3) {
			return "已过期";
		} else if (cellvalue == 4) {
			return "合作待确认";
		}  else {
			return "未知";
		}
	}
	function formatterCooperateApplyStatus(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "未申请";
		} else if (cellvalue == 2) {
			return "已提交";
		} else if (cellvalue == 3) {
			return "审核中";
		} else if (cellvalue == 4) {
			return "审核通过";
		} else if (cellvalue == 5) {
			return "审核不通过";
		} else {
			return "未申请";
		}
	}
	//删除机构
	function deleteByOrgId(orgId) {
		layer.confirm("确认删除?", {
			icon : 6,
			btn : [ '是','否' ]
		//按钮
		}, function() {
			$.ajax({
				url : "${ctx }orgController/deleteByOrgId.action?orgId="+ orgId,
				type : "POST",
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						layer.alert(ret.header["msg"], {
							icon : 6
						});
						searchList('#table_list');
					} else {
						layer.alert(ret.header["msg"], {
							icon : 5
						});
					}
				}
			});
		}, function() {
		});
	}
	$(document)
			.ready(
					function() {
						$.jgrid.defaults.styleUI = "Bootstrap";
						$("#table_list")
								.jqGrid(
										{
											url : "${ctx}partnerController/customerIndexList.action",
											datatype : "json",
											mtype : "POST",
											caption : "客户列表",
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
											colNames : [ "orgId", "登录名","机构名称",
													"统一社会信用代码", "联系人", "联系人电话",
													"认证状态", "合作状态", "合作申请状态",
													"操作" ],
											colModel : [
													{
														name : "orgId",
														index : "orgId",
														hidden : true
													},
													{
														name : "userName",
														index : "userName",
														sortable : false
													},
													{
														name : "orgName",
														index : "orgName",
														sortable : false
													},
													{
														name : "orgCode",
														index : "orgCode",
														sortable : false
													},
													{
														name : "contact",
														index : "contact",
														sortable : false
													},
													{
														name : "phone",
														index : "phone",
														sortable : false
													},
													{
														name : "auditStatus",
														index : "auditStatus",
														sortable : false,
														formatter : formatterAuditStatus
													},
													{
														name : "cooperateStatus",
														index : "cooperateStatus",
														sortable : false,
														formatter : formatterCooperateStatus
													},
													{
														name : "cooperateApplyStatus",
														index : "cooperateApplyStatus",
														sortable : false,
														formatter : formatterCooperateApplyStatus
													},
													{
														name : "operate",
														index : "operate",
														formatter : function(
																cellvalue,
																options, row) {
															var str = '<a onclick=openTab("${ctx }partnerController/toCustomerAddOrUpdate.action?orgId='+ row.orgId+ '","编辑客户","toCustomerUpdate.action") >&nbsp编辑&nbsp</a>';
															//没有认证之前可以删除
															if (row.auditStatus != 3) {
																str = str
																		+ "<a href='#' onclick='deleteByOrgId("+ row.orgId+ ")'>&nbsp删除&nbsp</a>"
															}
															return str;
														}
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
            <label class="col-md-1 control-label" for="orgName">机构名称:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="orgName" id="orgName" placeholder="机构名称">
            </div>
            <label class="col-md-1 control-label" for="orgCode">统一社会信用代码:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="orgCode" id="orgCode" placeholder="统一社会信用代码">
            </div>
            <label class="col-md-1 control-label" for="contact">联系人:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="contact" id="contact" placeholder="联系人">
            </div>
            <label class="col-md-1 control-label" for="auditStatus">认证状态:</label>
            <div class="col-md-2">
             <select class="form-control input-sm" name="auditStatus" id="auditStatus">
              <option value=-1 selected="selected">--请选择--</option>
              <option value=1>未认证</option>
              <option value=2>认证中</option>
              <option value=3>已认证</option>
              <option value=4>认证未通过</option>
             </select>
            </div>
           </div>
           <div class="col-md-12">
            <label class="col-md-1 control-label" for="cooperateStatus">合作状态:</label>
            <div class="col-md-2">
             <select class="form-control input-sm" name="cooperateStatus" id="cooperateStatus">
              <option value=-1 selected="selected">--请选择--</option>
              <option value=1>未合作</option>
              <option value=4>合作待确认</option>
              <option value=2>已合作</option>
              <option value=3>已过期</option>
             </select>
            </div>
            <!-- <label class="col-md-1 control-label" for="cooperateApplyStatus">合作申请状态:</label>
            <div class="col-md-2">
             <select class="form-control input-sm" name="cooperateApplyStatus" id="cooperateApplyStatus">
              <option value=-1 selected="selected">--请选择--</option>
              <option value=1>未申请</option>
              <option value=2>已提交</option>
              <option value=3>审核中</option>
              <option value=4>审核通过</option>
              <option value=5>审核不通过</option>
             </select>
            </div> -->
           </div>
          </div>
          <br> <br>
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
       <a class="btn btn-outline btn-primary" onclick="openTab('${ctx }partnerController/toCustomerAddOrUpdate.action?orgId=0','增加客户','toCustomerAdd.action')">增加</a>
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
 <script src="${ctx }js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>