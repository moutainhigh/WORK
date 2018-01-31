<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>业务申请列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script type="text/javascript">
	function formatterStatus(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "未提交";
		} else if (cellvalue == 2) {
			return "待审核";
		}else if (cellvalue == 3) {
			return "审核中";
		}else if (cellvalue == 4) {
			return "审核通过";
		}else if (cellvalue == 5) {
			return "审核失败";
		}
	}
	function formatterClosed(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			return "已关闭";
		} else if (cellvalue == 2) {
			return "正常";
		}
	}
	
	function formatterReject(cellvalue, options, rowObject) {
		if (cellvalue == 1) {
			var htmlStr = '<a href="#" onclick=openRejectInfo('+rowObject.pid+')>&nbsp已驳回&nbsp</a>';
			return htmlStr;
		} else if (cellvalue == 2) {
			return "正常";
		}else if (cellvalue == 4) {
			var htmlStr = '<a href="#" onclick=openRefuseInfo('+rowObject.pid+')>&nbsp已拒单&nbsp</a>';
			return htmlStr;
		}else if (cellvalue == 3) {
			var htmlStr = '<a href="#" onclick=openRejectInfo('+rowObject.pid+')>&nbsp已驳回-补充资料&nbsp</a>';
			return htmlStr;
		}
	}
	
	$(document).ready(function() {
		$.jgrid.defaults.styleUI = "Bootstrap";
		$("#table_list").jqGrid({
			url : "${ctx}projectController/getProjectList.action",
			datatype : "json",
			mtype : "POST",
			caption : "业务申请列表",
			height : 450,
			autowidth : true,
			shrinkToFit : true,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			postData : $.serializeObject($("#searchFrom")),
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : "#pager_list",
			colNames : [ "pid", "订单编号", "提交人", "产品名称","客户名称", "身份证号","提单日期", "借款金额(元)","审批状态","关闭状态","业务状态", "操作" ],
			colModel : [ {
				name : "pid",
				index : "pid",
				hidden : true
			}, {
				name : "projectNumber",
				index : "projectNumber",
				sortable : false
			}, {
				name : "applyUserName",
				index : "applyUserName",
				width:80,
				sortable : false
			}, {
				name : "productName",
				index : "productName",
				width:120,
				sortable : false
			}, {
				name : "orgCustomerName",
				index : "orgCustomerName",
				width:80,
				sortable : false
			}, {
				name : "orgCustomerCard",
				index : "orgCustomerCard",
				sortable : false
			}, {
				name : "requestDttm",
				index : "requestDttm",
				sortable : false
			}, {
				name : "planLoanMoney",
				index : "planLoanMoney",
				formatter : formatterMoney,
				sortable : false
			}, {
				name : "requestStatus",
				index : "requestStatus",
				formatter : formatterStatus,
				width:80,
				sortable : false
			}, {
				name : "isClosed",
				index : "isClosed",
				formatter : formatterClosed,
				width:80,
				sortable : false
			}, {
				name : "isReject",
				index : "isReject",
				formatter : formatterReject,
				width:80,
				sortable : false
			}, {
				name : "operate",
				index : "operate",
				sortable : false,
				formatter : function(cellvalue, options, row) {
				var htmlStr = "";
				if((row.requestStatus == 1 || row.requestStatus == 5 || row.isReject == 1 || row.isReject == 3) && row.isClosed == 2){
					htmlStr += '<a href="#" onclick=openTab("${ctx }projectController/toEditProject.action?pid='+ row.pid+ '&editType=2","编辑业务申请","toEditProject.action")>&nbsp编辑&nbsp</a>';
				}
				htmlStr += '<a href="#" onclick=openTab("${ctx }projectController/toEditProject.action?pid='+ row.pid+ '&editType=3","查看业务申请","toLookUpProject.action")>&nbsp查看&nbsp</a>';
				if(row.isClosed == 2 && (row.requestStatus == 1 || row.requestStatus == 5|| row.isReject == 1)){
					htmlStr += '<a href="#" onclick="updateClosed('+row.pid+')">&nbsp关闭&nbsp</a>';
				}
				
				return htmlStr;
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
	//关闭订单
	function updateClosed(pid){
		layer.confirm("确定要关闭当前业务申请吗？", {
			icon : 3,
			btn : [ '是','否' ]
		//按钮
		}, function() {
			$.ajax({
				url : "${ctx}/projectController/updateClosed.action?pid="+pid+"&isClosed=1",
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
	//打开驳回记录页面
function openRejectInfo(projectId){
		if(projectId>0){
			$.ajax({
				url : "${ctx}projectController/getRejectInfo.action",
				type : "POST",
				data : {
					projectId : projectId
				},
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					$("#rejectTime").val(ret.examineDate);
					$("#examineOpinion").val(ret.examineOpinion);
					$('#myModal').modal('show');
				}
			});
		}
	}
	
//打开拒单记录页面
function openRefuseInfo(projectId){
		if(projectId>0){
			$.ajax({
				url : "${ctx}projectController/getRejectInfo.action",
				type : "POST",
				data : {
					projectId : projectId
				},
				async : false,
				success : function(result) { //表单提交后更新页面显示的数据
					var ret = eval("(" + result + ")");
					$("#refuseTime").val(ret.examineDate);
					$("#refuseReason").val(ret.examineOpinion);
					$('#refuseModal').modal('show');
				}
			});
		}
	}
	/* 
	//打开驳回记录页面
	function openRejectInfo(){
		var jqGridIds = $("#table_list").jqGrid('getGridParam','selarrrow');
		
		if(jqGridIds.length ==1){
			var isReject = $("#table_list").jqGrid('getRowData',
					jqGridIds[0]).isReject;
			var projectId = $("#table_list").jqGrid('getRowData',
					jqGridIds[0]).pid;
			if(isReject == "已驳回"){
				$.ajax({
					url : "${ctx}projectController/getRejectInfo.action",
					type : "POST",
					data : {
						projectId : projectId
					},
					async : false,
					success : function(result) { //表单提交后更新页面显示的数据
						var ret = eval("(" + result + ")");
						$("#rejectTime").val(ret.examineDate);
						$("#examineOpinion").val(ret.examineOpinion);
						$('#myModal').modal('show');
					}
				});
				
			}else{
				layer.alert('只有驳回的业务申请才能查看驳回信息！', {
					icon : 0
				});
				return;
			}
		}else if(jqGridIds.length >1){
			layer.alert('每次只能选中一条数据进行操作', {
				icon : 0
			});
			return;
		}else{
			layer.alert('请选择数据', {
				icon : 0
			});
			return;
		}
	} */
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
            <label class="col-md-1 control-label" for="userName">订单编号:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="projectNumber" id="projectNumber" placeholder="订单编号">
            </div>
            <label class="col-md-1 control-label" for="realName">客户姓名:</label>
            <div class="col-md-2">
             <input type="text" class="form-control input-sm" name="orgCustomerName" id="orgCustomerName" placeholder="姓名">
            </div>
            <label class="col-md-1 control-label" for="status">状态:</label>
            <div class="col-md-2">
             <select class="form-control input-sm" name="requestStatus" id="requestStatus">
              <option value=-1 selected="selected">--请选择--</option>
              <option value=1>未提交</option>
              <option value=2>待审核</option>
              <option value=3>审核中</option>
              <option value=4>审核通过</option>
              <option value=5>审核失败</option>
             </select>
            </div>
            <label for="productId" class="col-sm-1 control-label">产品名称:</label>
		 	<div class="col-md-2">
		  		<select class="form-control" name="productId" id="productId">
			    	<option value="-1">--请选择--</option>
					<c:forEach items="${productList }" var="product" varStatus="status">
						<option value="${product.pid}" productNum="${product.productNumber }" >${product.productName}</option>
					</c:forEach>
		 		</select>
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
       	<a class="btn btn-outline btn-primary" href="#" onclick="openTab('${ctx }projectController/toEditProject.action?editType=1','新增业务申请','toAddProject.action')" >增加</a> 
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
 <!-- 驳回开始 -->
  <div class="modal form-horizontal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content animated bounceInRight">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal">
      <span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
     </button>
     <h4 class="modal-title">驳回信息 </h4>
    </div>
    <div class="modal-body">
     <div class="form-group" style=" height: auto; overflow: hidden;"> 
      	<label for="rejectTime" class="col-sm-2 control-label">驳回时间:</label>
       	<div class="col-sm-7">
        	<input type="text" class="form-control" readonly="readonly" name="rejectTime" id="rejectTime">
       	</div>
     </div>
     <div class="form-group"  style=" height: auto; overflow: hidden;">
		<label for="examineOpinion" class="col-sm-2 control-label red">驳回意见:</label>
		<div class="col-sm-7">
			  <textarea rows="4" cols="10" class="form-control" readonly="readonly" name="examineOpinion" id="examineOpinion"  maxlength="200"></textarea>
		</div>
	 </div>
    </div>
    <div class="modal-footer forum-info">
     <button type="button" class="btn btn-w-m btn-white cz_an" data-dismiss="modal">关闭</button>
   </div>
  </div>
 </div>
 <!-- 拒单开始 -->
 <div class="modal form-horizontal" id="refuseModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content animated bounceInRight">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal">
      <span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
     </button>
     <h4 class="modal-title">拒单信息 </h4>
    </div>
    <div class="modal-body">
     <div class="form-group" style=" height: auto; overflow: hidden;"> 
      	<label for="rejectTime" class="col-sm-2 control-label">拒单时间:</label>
       	<div class="col-sm-7">
        	<input type="text" class="form-control" readonly="readonly" name="rejectTime" id="refuseTime">
       	</div>
     </div>
     <div class="form-group"  style=" height: auto; overflow: hidden;">
		<label for="examineOpinion" class="col-sm-2 control-label red">拒单原因:</label>
		<div class="col-sm-7">
			  <textarea rows="4" cols="10" class="form-control" readonly="readonly" name="refuseReason" id="refuseReason"  maxlength="200"></textarea>
		</div>
	 </div>
    </div>
    <div class="modal-footer forum-info">
     <button type="button" class="btn btn-w-m btn-white cz_an" data-dismiss="modal">关闭</button>
   </div>
  </div>
 </div>
 <!-- 需要延迟加载的资源 -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
</html>