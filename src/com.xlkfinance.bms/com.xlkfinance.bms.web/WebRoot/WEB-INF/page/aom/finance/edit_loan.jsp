<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<title>放款详情</title>
<style type="text/css">
#tabs .tabs-header .tabs{
  height: 26px;
  position: fixed;
  top:0px;
  background: #fff;
  z-index: 9999999;
  padding-top;2px;
}
.{padding-top:7px;}
</style>
</head>
<script type="text/javascript">

var pid = "${cashFlowApplyInfo.pid}";
var editType = "${editType}";
var fileUrl = "${cashFlowApplyInfo.attachmentUrl}";
var fileName = "${cashFlowApplyInfo.attachmentName}";
$(function() {
	setCombobox3("areaCode","COOPERATION_CITY","${bizProject.areaCode}");
	$("#areaCode").combobox("disable");
	if(fileUrl != ""){
		var url = '${basePath}beforeLoanController/downloadData.action?path='+fileUrl+"&fileName="+fileName;
		$("#fileUrl").attr("href",url);
	}
	if(editType == '3'){
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}

});

function cancelLoanInfo(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','放款管理');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '放款管理', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}
	parent.$('#layout_center_tabs').tabs('close','放款确认');
}

function saveLoanInfo(){
	var money = $("#money").numberbox("getValue");
	if(money >0){
	}else{
		$.messager.alert('操作提示',"放款金额不允许为0!",'error');	
		return;
	}
	
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示','保存信息成功','info');
				cancelLoanInfo();
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}

function openUpload(){
	if(pid == ''){
		$.messager.alert('操作提示',"请先保存申请信息！",'error');
		return;
	}

	$('#dlg_extensionFile').dialog('open').dialog('setTitle','上传合同');
}

function uploadFile(){
	$('#additionFileForm').form('submit', {
		url : "${basePath}aomFinanceController/uploadFile.action?pid="+pid,
		onSubmit : function() {
			return checkFileForm();
		},
		success:function(data){
			var ret = eval("("+data+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示','凭证上传成功','info');
				fileUrl = ret.header["fileUrl"];
				fileName = ret.header["fileName"];
				var url = '${basePath}beforeLoanController/downloadData.action?path='+fileUrl+"&fileName="+fileName;
				$("#fileUrl").attr("href",url);
				$("#fileUrl").text(fileName);
				$('#dlg_extensionFile').dialog("close");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		}
	})
}

function checkFileForm(){
	var flag = true;
	var fileurl=$("#txt1").val();
	if(fileurl==''){
		alert("请选择文件！");
		return false;
	}else{
		var fileT=fileurl.substring(fileurl.lastIndexOf("."));
		if(fileT ==".gif" || fileT==".jpg" || fileT==".jpeg" || fileT ==".png" ){
			return flag=true;
		}
		else{
			alert("文件格式错误，系统只支持上传后缀名为.jpg,.jpeg,.gif,.png的图片！");
			return flag=false;
		}
		return flag;
	}
}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="放款详细信息" id="tab1">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="业务基本信息" data-options="collapsible:true" width="1039px;">
			<div id="personal" style="padding:10px 0 15px 0;">
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right">客户姓名：</td>
						<td>
							<input class="easyui-textbox" name="orgCustomerName" value="${bizProject.orgCustomerName }" style="width:150px;"  readonly="true"/>
						</td>
						<td class="label_right">客户电话：</td>
						<td>
							<input  class="easyui-textbox" value="${bizProject.orgCustomerPhone}" name="orgCustomerPhone" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">身份证号：</td>
						<td>
							<input  class="easyui-textbox" value="${bizProject.orgCustomerCard}" name="orgCustomerCard" style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">借款金额：</td>
						<td>
							<input  class="easyui-numberbox" name="planLoanMoney" value="${bizProject.planLoanMoney}" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" readonly="true" style="width:150px;"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">借款天数：</td>
						<td>
							<input  class="easyui-numberbox" value="${bizProject.projectForeclosure.loanDays}" name="projectForeclosure.loanDays"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"  style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">借款利率：</td>
						<td>
							<input  class="easyui-numberbox" value="${bizProject.loanRate}" name="loanRate" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','"  style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">所属城市：</td>
						<td>
							<input name="areaCode" id="areaCode" data-options="validType:'selrequired'" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
						</td>
						<td class="label_right">希望放款日期：</td>
						<td>
							<input value="${bizProject.planLoanDate}"  class="easyui-datebox" name="planLoanDate" readonly="true" editable="false" style="width:150px;"  />
						</td>
					</tr>
					
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${bizProject.surveyResult}" name="surveyResult" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 放款信息开始 -->
		<div style="padding:10px 0 15px 0;">
		<div class=" easyui-panel" title="放款休息" data-options="collapsible:true" width="1039px;">
		<div style="padding:10px 0 10px 10px;height: auto;line-height: 35px;" >
			<form action="<%=basePath%>aomFinanceController/saveLoanInfo.action" id="orgForm" name="orgForm" method="post">
			<input type="hidden" name="pid" value="${cashFlowApplyInfo.pid}">
			<input type="hidden" name="orderId" value="${bizProject.pid}">
			<table class="beforeloanTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>到账银行：</td>
					<td>
						<input class="easyui-textbox"  value="${cashFlowApplyInfo.bankName }" name="bankName" required="true" data-options="required:true,validType:'length[0,20]'" style="width:129px;" />
					</td>
					<td class="label_right1"><font color="red">*</font>到账户名：</td>
					<td>
						<input class="easyui-textbox" value="${cashFlowApplyInfo.accountName }" name="accountName" required="true"  data-options="required:true,validType:'length[0,20]'" style="width:129px;"/>
					</td>
					<td class="label_right"><font color="red">*</font>到账卡号：</td>
					<td>
						 <input name="bankCardNo" value="${cashFlowApplyInfo.bankCardNo }" class="easyui-textbox" required="true"  data-options="required:true,validType:'length[0,50]'" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>放款日期：</td>
					<td>
						 <input name="accountDate"  editable="false" class="easyui-datebox" value="${cashFlowApplyInfo.accountDate }" required="true" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>放款金额：</td>
					<td>
						<input name="money" id="money" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" value="${cashFlowApplyInfo.money == 0 ?bizProject.planLoanMoney:cashFlowApplyInfo.money }" required="true" class="easyui-numberbox" style="width:129px;"/>
					</td>
					<td class="label_right">放款操作人：</td>
					<td>
						 <input type="hidden" name="auditId" value="${cashFlowApplyInfo.auditId == 0?login.pid:cashFlowApplyInfo.auditId}">
						 <input name="auditName" value="${empty cashFlowApplyInfo.auditName?login.realName:cashFlowApplyInfo.auditName}" readonly="true"  class="easyui-textbox" style="width:129px;"/>
					</td>
				</tr>
				
				<tr style="padding-top:10px;">
					<td class="label_right"><font color="red">*</font>放款状态：</td>
					<td>
						<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="status" style="width:129px;">
							<option value="-1">--请选择--</option>
							<option value="2" <c:if test="${cashFlowApplyInfo.status==2 }">selected </c:if>>审核中</option>
							<option value="3" <c:if test="${cashFlowApplyInfo.status==3 }">selected </c:if>>已放款</option>
						</select>
					</td>
					<td class="label_right"><font color="red">*</font>放款凭证：</td>
					<td>
						<a href='#' id="fileUrl" style="color:blue;float: left;width: 145px;padding-left: 5px; ">${cashFlowApplyInfo.attachmentName}</a>
						<a class="easyui-linkbutton personalbtn"  id="readtype1" onclick="openUpload()">上传</a>
					</td>
				</tr>
				<tr>
					<td class="label_right">审核意见：</td>
					<td colspan="4">
						<input class="easyui-textbox" name="auditDesc" value="${cashFlowApplyInfo.auditDesc}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
					</td>
				</tr>
			</table>
		</div>
		</div>
		</div>
		
		<div style="padding-top: 10px;">
			<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveLoanInfo()">保存</a>
		</div>
		</form>
		
		<!--  附件上传start -->
		<div id="dlg_buttons_extensionFile">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="uploadFile()">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_extensionFile').dialog('close')">关闭</a>
		</div>
		<div id="dlg_extensionFile" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_extensionFile" 
		 	style="width:500px;top:100px;padding: 10px;" closed="true" >
			<form id="additionFileForm" method="post" enctype="multipart/form-data">
				<table>
					<tr id="fileUrlTr">
						<td align="right" width="120" height="28"><font color="red">*</font>文件选择：</td>
						<td>
							<input class="text_style" type="hidden" id="txt1" name="txt1"/>
					        <input class="text_style" size="30" type="file" onchange="txt1.value=this.value" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" id="fileName" name="fileName" style="width:240px;" required="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!--  附件上传end -->
	</div>
</div>

</div>
</div>
</div>
</body>
</html>