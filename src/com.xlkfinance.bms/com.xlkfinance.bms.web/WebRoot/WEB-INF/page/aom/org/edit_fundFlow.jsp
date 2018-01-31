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
<title>保证金变更信息</title>
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

var pid = "${orgAssureFundFlowInfo.pid}";
var editType = "${editType}";

$(function() {
	setCooperationCity();
	if(editType == '3'){
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}

});

function setCooperationCity(){
	var citys = "${orgCooperateInfo.cooperationCitys}";
	$('#cooperationCity').combobox({    
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType=COOPERATION_CITY',    
		valueField:'lookupVal',    
		textField:'lookupDesc',
		loadFilter: function(data){
			var bbc = new Array();
		 	for(var i=0;i<data.length;i++){
	 			if(data[i].lookupVal!=""){
	 				bbc.push(data[i]);
	 			}
		 	}
		 	if(citys != ""){
		 		$('#cooperationCity').combobox('setValues',citys.split(','))
		 	}
			return bbc;
		}
	});
}
/**
 * 保存保证金变更信息
 */
function saveOrgFundFlowInfo(){
	
	var cusCreditLimit = $("#cusCreditLimit").numberbox("getValue");
	var curAssureMoney = $("#curAssureMoney").numberbox("getValue");
	var creditLimit = $("#creditLimit").numberbox("getValue");//原额度
	var availableLimit = $("#availableLimit").numberbox("getValue");//原可用额度
	var number = creditLimit - availableLimit;//原已冻结额度
	if(curAssureMoney>0){
		
	}else{
		$.messager.alert('操作提示','变更后保证金金额必须大于0','error');	
		return;
	}
	
	if(cusCreditLimit >0){
		
	}else{
		$.messager.alert('操作提示','变更后授信额度必须大于0','error');	
		return;
	}
	
	var result = cusCreditLimit - number;
	if(result>0){
		
	}else{
		$.messager.alert('操作提示','调整后的授信额度必须大于已冻结授信额度','error');	
		return;
	}
	
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				alert("保存成功！");
				pid = ret.header["pid"];//合作申请ID
				$("#pid").val(pid);
				cancelPartner();
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}

function cancelPartner(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','保证金变更管理');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '保证金变更管理', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}

	if(pid>0){
		parent.$('#layout_center_tabs').tabs('close','修改保证金变更');
	}else{
		parent.$('#layout_center_tabs').tabs('close','新增保证金变更');
	}
}

</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-tabs" id="tabs" data-options="border:false" style="width:auto;height:auto">
	<div title="机构信息" id="tab1">
		<div style="padding: 30px 10px 0 10px;">
		<div class="easyui-panel" title="机构基本信息" data-options="collapsible:true" width="1039px;">
			<div id="personal" style="padding:10px 0 15px 0;">
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
					<tr>
						<td class="label_right">机构名称：</td>
						<td>
							<input class="easyui-textbox" readonly="true" value="${orgAssetsInfo.orgName}" style="width:150px;" />
						</td>
						<td class="label_right">组织代码：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.code}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">公司邮箱：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.email}"  style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">所属合伙人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.partnerName}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">联系人：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.contact}" style="width:150px;" readonly="true"/>
						</td>
						<td class="label_right">联系人手机号码：</td>
						<td>
							<input  class="easyui-textbox" value="${orgAssetsInfo.phone}" style="width:150px;" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">认证说明：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgAssetsInfo.auditDesc}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td colspan="4">
							<input  class="easyui-textbox" value="${orgAssetsInfo.remark}" readonly="true" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'"/>
						</td>
					</tr>
				</table>
				<form action="<%=basePath%>orgAssureFundFlowController/saveOrUpdateFundFlow.action" id="orgForm" name="orgForm" method="post">
					<input type="hidden" name="applyId" value="${orgCooperateInfo.pid }">
					<input type="hidden" name="pid" id="pid" value="${orgAssureFundFlowInfo.pid }">
					<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
						<tr>
							<td class="label_right"><font color="red">*</font>合作开始时间：</td>
							<td>
								<input value="${orgCooperateInfo.startTime}" class="easyui-datebox" readonly="readonly" id="startTime" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作开始日期!"/>
							</td>
							<td class="label_right"><font color="red">*</font>合作结束时间：</td>
							<td>
								<input value="${orgCooperateInfo.endTime}" readonly="readonly" validType="checkDateRange['#startTime']" class="easyui-datebox" editable="false" style="width:150px;"   required="true" missingMessage="请输入合作结束日期!"/>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>授信额度：</td>
							<td>
								<input value="${orgCooperateInfo.creditLimit }" readonly="readonly" name="oldCreditLimit" id="creditLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入授信额度!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>可用授信额度：</td>
							<td>
								<input value="${orgCooperateInfo.availableLimit }" id="availableLimit" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入可用授信额度!" style="width:129px;"/>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>单笔上限：</td>
							<td>
								<input value="${orgCooperateInfo.singleUpperLimit }" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入单笔借款上限!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>费率：</td>
							<td>
								<input value="${orgCooperateInfo.rate }" readonly="readonly" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入收费费率!" style="width:129px;"/>%
							</td>
						</tr>
						<tr>
							<td class="label_right"><font color="red">*</font>保证金金额：</td>
							<td>
								 <input value="${orgCooperateInfo.assureMoney }" readonly="readonly" name="oldAssureMoney" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入保证金金额!" style="width:129px;"/>
							</td>
							<!-- <td class="label_right1"><font color="red">*</font>合作城市：</td>
							<td>
								<input id="cooperationCity" readonly="readonly" data-options="validType:'selrequired',multiple : true" class="easyui-combobox" editable="false" panelHeight="auto" required="true"/>
							</td> -->
						</tr>
						<tr>
						<td class="label_right">合作说明：</td>
						<td colspan="4">
							<input class="easyui-textbox" readonly="readonly" value="${orgCooperateInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
					</table>
					
					<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
						<tr>
							<td class="label_right"><font color="red">*</font>变更后保证金：</td>
							<td>
								<input value="${orgAssureFundFlowInfo.curAssureMoney }" name="curAssureMoney" id="curAssureMoney" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入变更后保证金!" style="width:129px;"/>
							</td>
							<td class="label_right1"><font color="red">*</font>变更后授信额度：</td>
							<td>
								<input value="${orgAssureFundFlowInfo.curCreditLimit }" name="curCreditLimit" id="cusCreditLimit" class="easyui-numberbox" required="true"  data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入变更后授信额度!" style="width:129px;"/>
							</td>
						</tr>
						<tr>
							<td class="label_right1"><font color="red">*</font>审核状态：</td>
							<td>
								<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="status" style="width:129px;">
									<option value="-1">--请选择--</option>
									<option value="1" <c:if test="${orgAssureFundFlowInfo.status==1 }">selected </c:if>>待审核</option>
									<option value="10" <c:if test="${orgAssureFundFlowInfo.status==10 }">selected </c:if>>审核通过</option>
									<option value="20" <c:if test="${orgAssureFundFlowInfo.status==20 }">selected </c:if>>审核不通过</option>
								</select>
							</td>
						</tr>
						<td class="label_right">审核意见：</td>
						<td colspan="4">
							<input  class="easyui-textbox" name="audit" value="${orgAssureFundFlowInfo.audit}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
						</td>
					</tr>
					</table>
					<div style="padding-bottom: 20px;padding-top: 10px;padding-left: 100px;">
						<a href="#" class="easyui-linkbutton" style="margin-left: 80px;" iconCls="icon-save" onclick="saveOrgFundFlowInfo()">保存</a>
					</div>
				</form>
			</div>
		</div>
		</div>
	</div>
</div>
</div>
</body>
</html>