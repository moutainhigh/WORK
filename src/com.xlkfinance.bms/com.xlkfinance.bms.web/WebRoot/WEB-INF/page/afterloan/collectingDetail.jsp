<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<title>还款催收</title>
</head>

<style type="text/css">
.labelClass{
	width:120px;
	text-align: right;
}

.no_frame{
	border:0;
	background: white;
}

</style>
<script type="text/javascript">

//获取路径
var url = decodeURI(window.location.href);
var arr = url.split("=");
var projectId=arr[1].substring(0,arr[1].indexOf("&",1));
var planId=arr[2].substring(0,arr[2].indexOf("&",1));
var loanId=arr[3];

$(document).ready(function(){
	$(".reminder_plan_id").val(planId);
})

//查询催收记录
function queryRecordList(projectId){
	window.location.href='getCollectionRecord.action?projectId='+projectId;
}

//设置值
function setSendValue(){
	if($(".method_paper:checked").val()=="0"){
		 $(".method_paper").val("1");
	 }
	
	if($(".method_phone:checked").val()=="0"){
		 $(".method_phone").val("1");
	 }
	
	if($(".method_msg:checked").val()=="0"){
		 $(".method_msg").val("1");
	 }
	if($(".method_mail:checked").val()=="0"){
		 $(".method_mail").val("1");
	 }
}

//提交任务 保存催收记录
function commitTask(){
	setSendValue();
	if($(".cycle_num").val()==""){
		$.messager.alert('操作提示','请选择期数生成催收内容','error');
	}else{
		$("#cr").form('submit', {
			url : "saveCollectionRecord.action",
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("("+result+")");
				if(ret && ret.header["flag"]=="success"){
					$.messager.alert('操作提示','提交成功','info',function(){
						parent.removePanel();
					});
					
				}else{
					$.messager.alert('操作提示','提交失败');	
				}
			}
		}); 
		
	}
	
}

//生成催收内容
	function createCollection() {
		var acctName = $(".acct_name").val();
		var rows = $('#collGrid').datagrid('getChecked');
		var str = "尊敬的" + acctName + "：\n您好!";
		str = str + " \n您的借款";
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		var cycleNum = "0";//期数
		var shouldPrincipal="";//本金
		var shouldInterest="";//利息
		var shouldMangCost="";//管理费
		var shouldOtherCost="";//其他费用
		var overdueInterest = ""; //逾期利息
		var overdueFine="";   //逾期罚息
		var planRepayDt="";//还款日期
		var endStr="即将到期，请注意还款日期，按时还款";
		var remark="\n           Q房金融\n";
		var total=0;//合计
		var reminderSubject="";
		var result = {};
		var parm = {};
		for (var i = 0; i < rows.length; i++) {
			cycleNum = rows[i].planCycleNum;
			
			if(rows[i].dataType == 1 ){
				parm = {loanInfoId:rows[i].loanInfoId,realtimeId:rows[i].pId,reconciliationCycleNum:-1};
			}else{
				parm = {loanInfoId:rows[i].loanInfoId,reconciliationCycleNum:rows[i].planCycleNum};
				str=str+"期数："+cycleNum;
			}
			$.ajax({
				type: "POST",
		        data:parm,
		        async:false, 
		    	url : BASE_PATH+'rePaymentController/getReconciliationDtl.action',
				dataType: "json",
			    success: function(resultData){
			    	result = resultData;
				}
			});  
			
			
			shouldPrincipal=rows[i].shouldPrincipal-result.shouldPrincipal;
			shouldInterest=rows[i].shouldInterest-result.shouldInterest;
			planRepayDt=rows[i].planRepayDt;
			shouldMangCost=rows[i].shouldMangCost-result.shouldMangCost;
			shouldOtherCost=rows[i].shouldOtherCost-result.shouldOtherCost;
			overdueInterest=rows[i].overdueInterest-result.overdueInterest;
			overdueFine=rows[i].overdueFine-result.overdueFine;
			
			if(shouldPrincipal>0){
				str=str+"  本金:"+shouldPrincipal;
				total+=shouldPrincipal;
			}
			if(shouldInterest>0){
				str=str+"  利息:"+shouldInterest;
				total+=shouldInterest;
			}
			
			if(shouldMangCost>0){
				str=str+"  管理费:"+shouldMangCost;
				total+=shouldMangCost;
			}
			
			if(shouldOtherCost>0){
				if(rows[i].dataType == 1 ){
					str=str+"  "+rows[i].planCycleName+" :"+shouldOtherCost;
				}else{
					str=str+"  其他费用:"+shouldOtherCost;
				}
				total+=shouldOtherCost;
			}
			
			if(overdueInterest>0){
				str=str+"  逾期利息:"+overdueInterest;
				total+=overdueInterest;
			}
			
			if(overdueFine>0){
				str=str+"  逾期罚息:"+overdueFine;
				total+=overdueFine;
			}
			str=str+"  \n合计:"+total+"  还款日期："+planRepayDt+"\n";
		}
		str=str+endStr+remark;
		$(".cycle_num").val(cycleNum);
		$(".reminder_msg").val(str);
	}
	
function recordDetail(value,row,index){
	return '<textarea cols="85"  rows="4" readonly="readonly" disabled="disabled" >'+value+'</textarea>';
}

function closeCollectingTab(){
	if (parent.$("#layout_center_tabs" ).tabs('exists', "还款催收")) {
  	   parent.$( '#layout_center_tabs').tabs('close' , "还款催收");
    }
}
</script>
<body>

<div id="tabs" style="width:auto;height:auto">
		<input type="hidden" id="projectId" name="projectId" value="${acct.projectId}" />
		<input type="hidden" id="" name="" value="" />
		<div style="width:auto;padding-top:10px;">
		<%-- begin 企业模版 --%>
		<div id="enterprise" style="line-height: 35px;" >
		<table>
			<tr>
				<td class="label_right">客户名称：<input type="hidden" class="method_tip acct_name" id="acctName" name="acctName" value="${acct.acctName}"/> </td>
				<td width="180">${acct.acctName}</td>
				<td class="label_right">电话号码：</td>
				<td width="180">${acct.telephone }</td>
				<td class="label_right">电子邮箱：</td>
				<td>${acct.email }</td>
			</tr>
			<tr>
				<td class="label_right">收件人：</td>
				<td width="180">${acct.recipient}</td>
				<td class="label_right">通信地址：</td>
				<td width="180">${acct.address}</td>
				<td class="label_right">邮政编码：</td>
				<td>${acct.postalCode}</td>
			</tr>
		</table>
		

			<c:if test="${acct.acctType!=1}">  <!-- 客户类别为企业客户时才有联系人，个人客户是没有联系人 -->
			<!-- ===================================================================================== -->
			<div class="contactInfoDiv">
					<div id="roleDiv" >
					<!-- 操作按钮 -->
					<table id="contactGrid" title="联系人" class="easyui-datagrid" 
					style="height:150;width: auto;"
					data-options="
					    url: 'getCollectionCusContact.action?acctId=${acct.acctId}',
					    method: 'POST',
					    rownumbers: true,
					    toolbar: '#toolbar',
					    idField: 'pid',
					    fitColumns:true,
					    singleSelect: true,
					    singleOnCheck: true,
						selectOnCheck: false,
						checkOnSelect: false
					    ">
					<!-- 表头 -->
						<thead><tr>
						 	<th data-options="field:'cttName',width:100" align="center">联系人姓名</th>
						    <th data-options="field:'duty',width:100" align="center">职务</th>
						    <th data-options="field:'fixedPhone',width:100" align="center">固定电话</th>
						    <th data-options="field:'movePhone',width:100" align="center">移动电话</th>
						    <th data-options="field:'mainCtt',width:100" align="center">是否主动联系</th>
						    <th data-options="field:'familyAddr',width:110" align="center">家庭住址</th>
						</tr></thead>
					</table>
					</div>
			</div>
			</c:if>
			<div class="clear pt10"></div>
			<!-- ===================================================================================== -->
			<div class="createCollectionDiv">					
					<div id="createbar" class="createbar">
						<a class="easyui-linkbutton" href="#reminderMsg" data-options="plain:true,iconCls:'icon-save'" onclick="createCollection()">生成催收内容</a>
					</div>
					<!-- 操作按钮 -->
					<table id="collGrid" title="生成催收内容" class="easyui-datagrid" 
					style="height:350;width: auto;"
					data-options="
					    url: 'getReceivablelist.action?loadId='+loanId,
					    method: 'POST',
					    rownumbers: true,
					    toolbar: '#createbar',
					    idField: 'pId',
					    fitColumns:true,
					    singleSelect: true,
					    singleOnCheck: true,
						selectOnCheck: false,
						checkOnSelect: false
					    ">
					<!-- 表头 -->
						<thead><tr>
							<th data-options="field:'pId',checkbox:true,width:100" ></th>
						 	<th data-options="field:'planCycleName',width:100" align="center">期数</th>
						    <th data-options="field:'planRepayDt',width:100" align="center">计划还款时间</th>
						    <th data-options="field:'shouldPrincipal',width:100,formatter:formatMoney" align="center">应收本金</th>
						    <th data-options="field:'shouldInterest',width:100,formatter:formatMoney" align="center">应收利息</th>
						    <th data-options="field:'shouldMangCost',width:100,formatter:formatMoney" align="center">应收管理费</th>
						    <th data-options="field:'shouldOtherCost',width:100,formatter:formatMoney" align="center">应收其他费用</th>
						    <th data-options="field:'overdueInterest',width:100,formatter:formatMoney" align="center">应收逾期利息</th>
						    <th data-options="field:'overdueFine',width:100,formatter:formatMoney" align="center">应收逾期罚息</th>
						    <th data-options="field:'accountsTotal',width:100,formatter:formatMoney" align="center">应收合计</th>
						    <th data-options="field:'receivedTotal',width:100,formatter:formatMoney" align="center">已收合计</th>
						    <th data-options="field:'uncollectedTotal',width:100,formatter:formatMoney" align="center">末收合计</th>
						</tr></thead>
					</table>
			</div>
			
				<!-- ===================================================================================== -->
			<div class="clear pt10"></div>
			<div class="recordDiv">				
					<div class="querybar" id="querybar">
						<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-view'" onclick="queryRecordList(${acct.projectId})">查看全部催收记录</a>
					</div>
					
					
					<!-- 操作按钮 -->
					<table id="rcGrid" title="查看催收记录" class="easyui-datagrid" 
					style="height:200;width: auto;"
					data-options="
					    url: 'getCollectionRecordlist.action?projectId=${acct.projectId}&page=1&rows=1',
					    method: 'POST',
					    toolbar: '#querybar',
					    idField: 'pid',
					    fitColumns:true
					    ">
					<!-- 表头 -->
						<thead><tr>
						 	<th data-options="field:'cycleNum',width:100" align="center">期数</th>
						    <th data-options="field:'reminderDt',width:100" align="center">催收时间</th>
						    <th data-options="field:'reminderUser',width:100" align="center">催收人</th>
						    <th data-options="field:'subject',width:100" align="center">标题</th>
						    <th data-options="field:'content',formatter:recordDetail,width:650" align="center">催收内容</th>
						</tr></thead>
					</table>
			</div>
			
		<!-- ===================================================================================== -->
			<div class="clear pt10"></div>
			<div class="dueInfoDiv">
					<div class="easyui-panel" data-options="title:'立即催收'">
					<form id="cr" method="post" action="saveCollectionRecord.action">
						<table class="#">
						  	<tr>
							    <td class="label_right" width="100" style="line-height: 50px;">催收方式：</td>
							    <td style="line-height: 50px;">
							   		<input type="checkbox" class="method_tip method_mail"  id="methodMail" name="methodMall" value="0" /><span style="font-size: 10px;">发送邮件(系统)</span>&nbsp;&nbsp;&nbsp;
							   		<input type="checkbox" class="method_tip method_msg"  id="methodSms" name="methodSms" value="0"/><span style="font-size: 10px;">发送短信(系统)</span>&nbsp;&nbsp;&nbsp;
							   		<input type="checkbox" class="method_tip method_phone"  id="methodPhone" name="methodPhone" value="0"/><span style="font-size: 10px;">电话催收</span>&nbsp;&nbsp;&nbsp;
							   		<input type="checkbox" class="method_tip method_paper"  id="methodPaper" name="methodPaper" value="0"/><span style="font-size: 10px;">纸质邮件</span>
							    </td>
						  	</tr>
						  	
						  	<tr>
							    <td class="label_right" style="line-height: 50px;">催收标题：</td>
							    <td style="line-height: 50px;">
							   		<input type="text" id="reminderSubject" name="reminderSubject" style="width: 400px;" class="method_tip reminder_subject text_style" data-options="required:true" missingMessage="请填写标题!"> <p><p>
							    </td>
						  	</tr>

						  	<tr>
						  		<td class="label_right">催收内容：</td>
							    <td>
									<textarea style="height:100px;width:400px;"  class="method_tip reminder_msg text_style" id="reminderMsg" name="reminderMsg" data-options="required:true" missingMessage="请填写内容!"></textarea>
							    </td>
						  	</tr>
						  	<tr>
						  		<td><input type="hidden" name="cycleNum" class="method_tip cycle_num"/><input type="hidden" name="telephone" class="method_tip telephone" value="${acct.telephone }"/>
						  		<input type="hidden" name="reminderPlanId" class="method_tip reminder_plan_id"/><input type="hidden" name="mailAdds" class="method_tip mail_Adds" value="${acct.email }"/>
						  		<input type="hidden" name="acctId" class="method_tip acct_id" value="${acct.acctId}"/></td>
							    <td class="align_center" style="line-height: 50px;" >
									<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="commitTask()">提交任务</a>
							    	<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="closeCollectingTab()">取消</a>
							    </td>
						  	</tr>
						</table>
						</form>
					</div>
			</div>
			<div class="clear pt10"></div>
		</div>
		</div>
</div>
</body>
</html>
