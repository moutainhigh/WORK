<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<link href="/BMS/upload/css/fileUpload.css" type="text/css"rel="stylesheet" />

<script type="text/javascript">

var  projectId = ${projectId};
var i=0;
//增加诉讼
function addLegal() {
		var trIndex = $("#legal_public tr").length - 1;
		if (trIndex >= 10) {
			$.messager.alert("提示", "诉讼最多为10条！", "info");
			return false;
		}
		var str = '<tr id="legal_tr'+trIndex+'" name="legal_tr'+trIndex+'" style="border:1px solid #999;">'
				+ '<td align="center">'
				+ '<input type="checkbox" id = "legalList'+trIndex+'_pid"  name = "legalList['+trIndex+'].pid" class="isChecked" />'
				+ '</td>'
				+ '<td align="center">'
				+ '<select  id = "legalList'+trIndex+'_status"  name = "legalList['+trIndex+'].status"  class="select_style"  style="width:120px;"> '
				+ '<option value="--请选择--">--请选择--</option>'
				+ '<option value="1">个人诉讼</option>'
				+ '<option value="2">企业诉讼</option>'
				+ '</select>'
				+ '</td>'
				+ '<td align="center">'
				+ '<textarea class="text text_style" style="height: 35px; width: 100%;" maxlength="800" name="legalList['+trIndex+'].legalContent" id="legalList'+trIndex+'_legalContent">'
	 			+ '</textarea>'
				+ '</td>' + '</tr>';
		$.parser.parse($("#legal_public").append(str));
	}
//删除诉讼
function removeLegal(){
	var trIndex = $("#legal_public tr").length - 1;
	var flag=false;
	$.each($("#legal_public").find("tr:gt(0)"), function(k, v) {
		$.each($(v).find(":checkbox:checked"),function(m, n) {
			if(trIndex == 1){
				$.messager.alert('操作提示', "系统将为您自动保留一条数据！", 'info');
				return false;
			}else{
				$(v).remove();
				location.reload();
				flag = true;
			}
			});
	});
	if (flag == false && trIndex == 1) {
		$.messager.alert('操作提示', "系统将为您自动保留一条数据！", 'info');
	}else if(flag == false && trIndex != 1){
		$.messager.alert('操作提示', "请选择一条数据删除！", 'info');
	}
}
//保存提交
function saveReport()
{
	var unAssureCondition =  $("#unAssureCondition").val();
	var houseProperyCondition =  $("#houseProperyCondition").val();
	var trIndex = $("#legal_public tr").length - 1;
	for(var i = 0; i < trIndex; i++){
		var legalStatusId = "legalList"+i+"_status";
		var legalContentId = "legalList"+i+"_legalContent";
		var legalStatus = $("#"+legalStatusId).val();
		var legalContent = $("#"+legalContentId).val();
		var legalPid = "legalList"+i+"_pid";
		if(document.getElementById(legalPid).checked){
			 $("#"+legalPid).attr("checked",false);
		}
		if(legalStatus== "--请选择--"){
			$.messager.alert("提示",'请选择诉讼类型！','info');
			return false;
		}
		if(legalContent=="" || legalContent == null){
			$.messager.alert("提示",'诉讼内容不能为空！','info');
			return false;
		}
	}
	
	if(unAssureCondition == "" || unAssureCondition == null){
		$.messager.alert("提示", "反担保措施不能为空", "info");
		return false;
	}
	if(houseProperyCondition == "" || houseProperyCondition == null){
		$.messager.alert("提示", "房产状态不能为空", "info");
		return false;
	}
	// 保存提交
	$('#myform').form('submit', {
		url : "addApplyReport.action",
		onSubmit : function() {
			return true;	 
		},
		success : function(result) {
			var ret = eval("("+result+")");
		//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if(ret && ret.header["success"] ){
					alert("保存成功！");
					cancelProduct();
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');	
				}
				
		},error : function(result){
			alert("保存失败！");
		}
	});
	
}

//刷新上一页
function cancelProduct(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','异常业务列表');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '异常业务列表', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}
	
	parent.$('#layout_center_tabs').tabs('close','新增申请报告');
}

function majaxReset() {
	$(".ecoTradeId1").hide();
	$('#myform').form('reset')
}
</script>
<div style="padding:10px 10px 10px 10px">
<div id = "add_report" class="easyui-panel" data-options="title:'新增申请报告',collapsible:true" style="padding: 5px 5px 5px 10px">
<form id="myform" action="${basePath}foreAfterLoanController/addApplyReport.action" method="post"> 
<table class="beforeloanTable common_flow" style="width:96%" >
<tbody>
					<tr>
						<td class="label_right"><font color="red">*</font>反担保措施：</td>
						<td><textarea class="text text_style"
								style="height: 60px; width: 508px;" maxlength="600"
								name="unAssureCondition" id="unAssureCondition"
								data-options="multiline:true,validType:'length[0,500]'"></textarea></td>
					</tr>
					<tr>
						<td class="label_right">诉讼：</td>
						<td>
							<div id="dlg_addOrDelete">
								<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-add" onclick="addLegal()">增加诉讼</a> <a
									href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-remove" onclick="removeLegal()">删除诉讼</a>
							</div>
								<table class="cus_table" id="legal_public">
									<tr style="border: 1px solid #999;">
										<td width="75px" align="center">选择</td>
										<td width="150px" align="center"><font color="red">*</font>诉讼类型</td>
										<td width="278px" align="center"><font color="red">*</font>诉讼内容</td>
									</tr>
									 <tr id="legal_tr0" style="border:1px solid #999;">
										<td align="center">
											<input type="checkbox" id = "legalList0_pid"  name = "legalList[0].pid" class="isChecked" />
										</td>
										<td align="center">
											<select  id = "legalList0_status"  name = "legalList[0].status"    style="width:120px;"  >
												<option value="--请选择--">--请选择--</option>
												<option value="1">个人诉讼</option>
												<option value="2">企业诉讼</option>
											</select>
										</td>
										<td align="center">
										<textarea class="text text_style" style="height: 35px; width: 100%;" maxlength="600" name="legalList[0].legalContent" id="legalList0_legalContent"></textarea>
										</td>
									</tr>
								</table>
						</td>
					</tr>

					<tr>
						<td class="label_right"><font color="red">*</font>房产状态:</td>
						<td><textarea class="text text_style"
								style="height: 60px; width: 508px;" maxlength="600"
								name="houseProperyCondition" id="houseProperyCondition"
								data-options="multiline:true,validType:'length[0,500]'"></textarea>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td><textarea class="text text_style"
								style="height: 60px; width: 508px;" maxlength="600"
								name="remark" id="remark"
								data-options="multiline:true,validType:'length[0,500]'"></textarea></td>
					</tr>
					<tr>
						<td></td>
						<td class="align_center" height="50">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" id="save" class="save_data text_btn"
							value="保存" onclick="saveReport()" /> <input type="button"
							id="save" class="save_data text_btn" value="重置"
							onclick="majaxReset()" /> 
							<input name="projectId" id="projectId"
							type="hidden" value=${projectId}>
						</td>
						<td></td>
					</tr>
				</tbody>
</table>
		</form>
	</div>
