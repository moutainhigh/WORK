<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>新增资金机构</title>
<style type="text/css">
#baseInfo {
		width: 500px;
}
#baseInfo label {
	width: 250px;
}
#baseInfo label.error, #baseInfo input.submit {
	margin-left: 253px;
}
</style>
<script type="text/javascript">

function saveCapitalInfo(){
	$('#advPicInfoForm').form('submit', {
		url : "saveOrUpdate.action",
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#pid").val(ret.header["pid"]);
				$.messager.alert('操作提示',"保存成功!",'info');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		},error : function(result){
			alert("保存失败！");
		}
	});
}
//formatAomMoney
function formatAomMoney(value,row,index){
	return accounting.formatMoney(value, "", 2, ",", ".");
}
function caozuofiter(value,row,index){

	var dataEditDom = "<a href='javascript:void(0)' class='download' onclick=editCapitalCost('"+row.pid+"','"+ row.orgId+"','"+ row.stepAmount+"','"+ row.yearRate+"') ><font color='blue'>编辑</font></a> | ";
	var dataDelDom = "<a href='javascript:void(0)' class='download'  onclick='delCapitalCost("+row.pid+")'><font color='blue'>删除</font></a>";	
	return dataEditDom+dataDelDom;
}
//编辑资金成本
function editCapitalCost(pid,orgId,stepAmount,yearRate){
	$('#personInfo').form('reset');
	$("#personInfo input[name='pid']").val(pid);
	$("#personInfo input[name='orgId']").val(orgId);
	$("#stepAmount").numberbox('setValue',stepAmount);
	$("#yearRate").numberbox('setValue',yearRate);
	$('#add_capital').dialog('open').dialog('setTitle', "编辑资金成本信息");
}
function delCapitalCost(pid){
	$.messager.confirm("操作提示", "确定删除此数据吗?", function(r) {
		if (r) {
			$.post("deleteCapitalCost.action", {
				"pid" : pid
			}, function(ret) {
				//  此方法，不需要换转换json格式数据,因为传过来的值就是json格式的数据 
				if (ret && ret.header["success"]) {
					$("#additionFile_grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}, 'json');
		}
	});
}

function saveCopitalCost(){
	// 提交表单
	$("#personInfo").form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
                //关闭窗口
				$('#add_capital').dialog('close');
				// 刷新
				var orgId = $("#pid").val();
				var url = "<%=basePath%>capitalOrgInfoController/getCapitalCostList.action?orgId="+orgId;
	        	$('#additionFile_grid').datagrid('options').url = url;
				$("#additionFile_grid").datagrid("clearChecked");
				$("#additionFile_grid").datagrid('reload');
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'error');
			}
		}
	});
}
//打开新增资金成本框
function openOrgCapitalCost(){
	var pid = $("#pid").val();
	if(pid >0){
		$('#personInfo').form('reset');
		$("#personInfo input[name='orgId']").val(pid);
		$('#add_capital').dialog('open').dialog('setTitle', "新增资金成本信息");
	}else{
		$.messager.alert('操作提示','请先保存','error');
	}
}

$
.extend(
		$.fn.validatebox.defaults.rules,
		{
			//检查登录名
			checkOrgName : {//value值为文本框中的值
				validator : function(value) {
					var r = false;
					var pid=$("#pid").val();
					value = value.trim();
					$
							.ajax({
								url : "${basePath}capitalOrgInfoController/checkOrgNameIsExist.action",
								data : {
									"orgName" : value,
									"pid":pid
								},
								dataType : "json",
								async : false,
								success : function(ret) {
									r = ret;
								}
							});
					return r;
				},
				message : '机构名已存在!'
			},
			//检查组织代码
			checkOrgCode : {//value值为文本框中的值
				validator : function(value) {
					var r = false;
					var pid=$("#pid").val();
					value = value.trim();
					$.ajax({
								url : "${basePath}capitalOrgInfoController/checkOrgCodeIsExist.action",
								data : {
									"orgCode" : value,
									"pid" : pid
								},
								dataType : "json",
								async : false,
								success : function(ret) {
									r = ret;
								}
							});
					return r;
				},
				message : "统一社会信用代码已存在!"
			}
		});
		
$(function() {
	var editType = "${editType}";
	if(editType == '3'){
		$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
		$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
		$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
	
});
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
  <div title="资金机构信息" id="tab1">
	<div style="padding: 30px 10px 0 10px;">
	  <div class="easyui-panel" title="资金机构信息" data-options="collapsible:true" width="1039px;">
		<div id="personal" style="padding:10px 0 15px 0;">
	<form id="advPicInfoForm" action="<%=basePath%>capitalOrgInfoController/saveOrUpdate.action" method="POST">
	<input type="hidden" name="status" value="${capitalOrgInfo.status }"/>
	<input type="hidden" id="pid" name="pid" value="${capitalOrgInfo.pid }"/>
	
	<table class="cus_table">
		<tr>
			<td class="label_right"><span class="cus_red">*</span>机构名称：</td>
			<td>
				<input class="easyui-textbox" data-options="required:true,validType:['length[1,20]','checkOrgName']" name="orgName" value="${capitalOrgInfo.orgName}"  style="width:150px;"/
			</td>
			<td class="label_right">统一社会信用代码：</td>
			<td>
				<input class="easyui-textbox" data-options="required:true,validType:['length[0,20]','checkOrgCode']" name="orgCode" value="${capitalOrgInfo.orgCode}"  style="width:150px;"/>
			</td>
			<td class="label_right"><span class="cus_red">*</span>放款总金额：</td>
			<td>
				<input value="${capitalOrgInfo.loanMoneyTotal }" name="loanMoneyTotal" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入该机构放款总金额!" style="width:129px;"/>
			</td>
		</tr>
	  </table>
	  	<div style="padding-bottom: 20px;padding-top: 10px;">
			<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveCapitalInfo()">保存</a>
		</div>
	</div>
	</div>
</form>  
  <div class="pt10"></div>
	<div class="dueInfoDiv easyui-panel " title="机构资金成本" data-options="collapsible:true" style="width:1039px;">	
		<div id="additionFileTable" style="padding-left: 30px;padding-top:10px;height: auto;width: 939px;" >
			<div id="toolbar_cooperateFile"  style="border-bottom: 0;">
				<a href="javascript:void(0)" id="gtjkrAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openOrgCapitalCost()">新增资金成本信息</a>
			</div>
			<table id="additionFile_grid"  class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="url: '<%=basePath%>capitalOrgInfoController/getCapitalCostList.action?orgId='+${capitalOrgInfo.pid },
				    method: 'POST',
				    singleSelect: false,
				    pagination: false,
				    sortOrder:'asc',
				    remoteSort:false,
				    toolbar: '#toolbar_cooperateFile',
				    resizable: false,
				    idField: 'pid'
				    ">
				<!-- 表头 -->
				<thead>
					<tr>
					    <th data-options="field:'pid',hidden:true" ></th>
					    <th width="80" data-options="field:'stepAmount',formatter:formatAomMoney,width:100,align:'center'">阶梯金额(元)</th>
					    <th width="140" data-options="field:'yearRate',formatter:formatAomMoney,width:100,align:'center'">阶梯年利率(%)</th>
					    <th width="140" data-options="field:'createDate',formatter:convertDateTime,width:100,align:'center'">创建时间</th>
					    <th width="160" data-options="field:'cz',width:120,formatter:caozuofiter">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
		<!-- 编辑 -->
		<div id="add_person_info">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveCopitalCost()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#add_capital').dialog('close')">关闭</a>
		</div>
		<div id="add_capital" class="easyui-dialog" fitColumns="true"  title="新增借款人"
		     style="width:850px;top:100px;padding:10px;" buttons="#add_person_info" closed="true" >
			<form id="personInfo" action="<%=basePath%>capitalOrgInfoController/saveCapitalCost.action" method="POST">
				<table class="beforeloanTable">
					<input type="hidden" name="pid"/>
					<input type="hidden" name="orgId" id="orgId"/>
					<tr>
					 <td class="label_right1"><span class="cus_red">*</span>阶梯金额：</td>
					 <td><input id="stepAmount" name="stepAmount" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入该阶梯金额金额!" style="width:129px;"/></td>
					<td class="label_right1"><span class="cus_red">*</span>阶梯年利率：</td>
				    <td>
					 <input name="yearRate" id="yearRate" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入阶梯年利率!" style="width:129px;"/>%
				   </td>
				  </tr>
				 </table>
			</form>	  
		</div>
   </div>
  </div>
</body>
</html>  