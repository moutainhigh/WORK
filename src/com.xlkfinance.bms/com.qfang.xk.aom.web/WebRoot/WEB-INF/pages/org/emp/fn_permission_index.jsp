<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>员工列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	function formatterStatus(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "启用";
		} else if (cellvalue == 2) {
			return "禁用";
		} else {
			return "未知";
		}
	}
	function formatterFnPermissionStatus(cellvalue, options, rowObject) {
		if (cellvalue == 0) {
			return "未授权";
		} else if (cellvalue > 0) {
			return "已授权";
		} else {
			return "未知";
		}
	}
	function formatterDateScope(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "私有";
		} else if (cellvalue == 2) {
			return "所有";
		} else {
			return "未知";
		}
	}
	//修改用户权限
	function updateFnPermisseion(status) {
		var id = $("#emp_table_list").jqGrid('getGridParam', 'selrow');
		if (id == null) {
			layer.alert('请选择用户', {
				icon : 0
			});
			return;
		}
		var jqGridIds = $("#emp_table_list").jqGrid('getGridParam',
		'selarrrow');
		if (jqGridIds.length>1) {
			layer.alert('请不要多选用户', {
				icon : 0
			});
			return;
		}
		//获取用户id
		var userId = $("#emp_table_list").jqGrid('getRowData', id).pid;

		var jqGridIds = $("#fn_permission_table_list").jqGrid('getGridParam',
				'selarrrow');
		//获取选择的权限id
		var fnPids = "";
		for (var i = 0; i < jqGridIds.length; i++) {
			var pid = $("#fn_permission_table_list").jqGrid('getRowData',
					jqGridIds[i]).menuId;
			if (i == 0) {
				fnPids += pid;
			} else {
				fnPids += "," + pid;
			}
		}
		if (fnPids == "") {
			layer.alert('请选择权限', {
				icon : 0
			});
			return;
		}
		$.ajax({
			url : "${ctx}empController/updateFnPermisseionByIds.action",
			type : "POST",
			data : {
				fnPids : fnPids,
				userId : userId,
				status : status
			//1：取消授权。2：赋予权限
			},
			async : false,
			success : function(result) { //表单提交后更新页面显示的数据
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					searchList('#fn_permission_table_list');
				} else {
					layer.alert(ret.header["msg"], {
						icon : 5
					});
				}
			}
		});

	}
	function initEmpTableList() {
		$("#emp_table_list").jqGrid({
			url : "${ctx}empController/emp_list.action",
			datatype : "json",
			mtype : "POST",
			caption : "员工列表",
			height : 450,
			autowidth : true,
			shrinkToFit : true,
			multiselect : true,
			multiboxonly : true,
			viewrecords : false,
			postData : $.serializeObject($("#searchFrom")),
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : "#pager_emp_list",
			colNames : [ "pid", "用户名", "姓名", "工号", "状态", "数据权限" ],
			colModel : [ {
				name : "pid",
				index : "pid",
				hidden : true
			}, {
				name : "userName",
				index : "userName",
				sortable : false
			}, {
				name : "realName",
				index : "realName",
				sortable : false
			}, {
				name : "jobNo",
				index : "jobNo",
				sortable : false
			}, {
				name : "status",
				index : "status",
				formatter : formatterStatus,
				sortable : false
			}, {
				name : "dateScope",
				index : "dateScope",
				formatter : formatterDateScope,
				sortable : false
			} ],
			gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
			},
			onSelectRow : function(id) {
				//选择用户的时候根据用户加载权限列表数据
				var pid = $("#emp_table_list").jqGrid('getRowData', id).pid;
				$("#fn_permission_table_list").jqGrid('setGridParam', {
					url : '${ctx}empController/fnPermissionList.action',
					datatype : 'json',
					postData : {
						userId : pid
					}, //发送数据  
					page : 1
				}).trigger("reloadGrid"); //重新载入
			},
		});
		 // 设置jqgrid的宽度  
        $(window).bind("resize",function() {
    	    var width = $(".jqGrid_wrapper").width();
    	    $("#emp_table_list").setGridWidth(width);
        })
		$("#emp_table_list").jqGrid("navGrid", "#pager_emp_list", {
			edit : false,
			add : false,
			del : false,
			search : false,
			refresh : true
		});
	}
	
	function afterCompleteFunction() {
		//获取列表数据
		var ids = $("#fn_permission_table_list").jqGrid("getDataIDs");
		var rowDatas = $("#fn_permission_table_list").jqGrid("getRowData");
		for (var i = 0; i < rowDatas.length; i++) {
			var rowData = rowDatas[i];
			var status=rowData.status;
			if (status=='已授权') {
			    $("#fn_permission_table_list #" + ids[i] + " td").css("background-color", "#50C965");
			}
		}
		return true;
	}
	
	function initFnPermissionTableList() {
		$("#fn_permission_table_list").jqGrid({
			url : "#",
			datatype : "json",
			mtype : "POST",
			caption : "权限列表",
			height : 450,
			autowidth : true,
			shrinkToFit : true,
			multiselect : true,
			viewrecords : false,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : "#pager_fn_permission_list",
			colNames : [ "menuId","parentId", "权限名称", "状态" ],
			colModel : [ {
				name : "menuId",
				index : "menuId",
				hidden : true
			}, {
				name : "parentId",
				index : "parentId",
				hidden : true
			},  {
				name : "menuName",
				index : "menuName",
				sortable : false
			}, {
				name : "status",
				index : "status",
				formatter : formatterFnPermissionStatus,
				sortable : false
			} ],
			gridComplete : function() { //列表生成后,给某一列绑定操作 例如删除操作
				afterCompleteFunction();
			}
		});
		$("#fn_permission_table_list").jqGrid("navGrid",
				"#pager_fn_permission_list", {
					edit : false,
					add : false,
					del : false,
					search : false,
					refresh : true
				});
	}
	$(document).ready(function() {
		$.jgrid.defaults.styleUI = "Bootstrap";
		initEmpTableList();
		initFnPermissionTableList();
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
         <!-- 查询条件 -->
         <div style="padding: 5px">
          <div class="row">
           <div class="col-md-12">
            <label class="col-md-1 control-label" for="userName">用户名:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="userName" id="userName" placeholder="用户名">
            </div>
            <label class="col-md-1 control-label" for="realName">姓名:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="realName" id="realName" placeholder="姓名">
            </div>
            <label class="col-md-1 control-label" for="jobNo">工号:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="jobNo" id="jobNo" placeholder="工号">
            </div>
            <label class="col-md-1 control-label" for="status">状态:</label>
            <div class="col-md-2">
             <select class="form-control input-sm" name="status" id="status">
              <option value=-1 selected="selected">--请选择--</option>
              <option value=1>启用</option>
              <option value=2>禁用</option>
             </select>
            </div>
           </div>
          </div>
          <br>
          <div class="row forum-info">
           <!-- 操作按钮 -->
           <button class="btn btn-primary an_bk" type="button" onclick="searchList('#emp_table_list')">
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
      <div class="col-sm-6">
       <div class="ibox float-e-margins">
        <div class="ibox-content">
        <a class="btn btn-outline btn-primary" onclick="updateFnPermisseion(2)">授权</a> 
           <a class="btn btn-outline btn-danger" onclick="updateFnPermisseion(1)">取消授权</a>
         <div class="jqGrid_wrapper">
          <table id="emp_table_list"></table>
          <div id="pager_emp_list"></div>
         </div>
        </div>
       </div>
      </div>
      <div class="col-sm-6">
       <div class="ibox float-e-margins">
        <div class="ibox-content">
         <div class="jqGrid_wrapper">
          <table id="fn_permission_table_list"></table>
          <div id="pager_fn_permission_list"></div>
         </div>
        </div>
       </div>
      </div>
     </div>
    </div>

   </div>
  </div>
 </div>
 <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content animated bounceInRight">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal">
      <span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
     </button>
     <small class="font-bold">数据权限分配。 </small>
    </div>
    <div class="modal-body">
     <div class="form-group">
      权限类型：<input type="radio" name="dateScope" value="1" checked="checked">私有 <input type="radio" name="dateScope" value="2">所有
     </div>
    </div>
    <div class="modal-footer">
     <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
     <button type="button" class="btn btn-primary" onclick="updateDateScope()">保存</button>
    </div>
   </div>
  </div>
 </div>
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>