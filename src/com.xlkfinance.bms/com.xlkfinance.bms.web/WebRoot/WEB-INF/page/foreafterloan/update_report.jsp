<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<link href="/BMS/upload/css/fileUpload.css" type="text/css"
	rel="stylesheet" />

<script type="text/javascript">
	var projectId = ${projectId};
	var i = 0;
	var length = ${legalList.size()};
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
	function removeLegal() {
		var flag = false;
		var pids = "";
		var pid = "";
		
		var trIndex = $("#legal_public tr").length - 1;
		$.each($("#legal_public").find("tr:gt(0)"), function(k, v) {
			$.each($(v).find(":checkbox:checked"), function(m, n) {
				var pid=$(n).val();
				if (pid!=null) {
					pids=pids+","+pid;
				}
				if(trIndex ==1 ||length==$("input[type='checkbox']:checked").length){
					alert("请至少保留一条数据！");
					return;
				}else{
					$(v).remove();
					location.reload();
					flag = true;
					if(pids != null && pids != ',on'){
				    	   $('#myform').form('submit', {
							url : "deleteLegalIndexByIds.action?ids="+pids,
							onSubmit : function() {
								return true;
							}
						}); 
				      }
				}
			});
		});
		 if(flag == false && $("input[type='checkbox']:checked").length ==0){
			$.messager.alert('操作提示', "请选择一条数据删除！", 'info');
			return;
		}
		location.reload();
	}
	//保存修改提交
	function saveReport() {
		var unAssureCondition =  $("#unAssureCondition").val();
		var houseProperyCondition =  $("#houseProperyCondition").val();
		var trIndex = $("#legal_public tr").length - 1;
		if(unAssureCondition == "" || unAssureCondition == null){
			$.messager.alert("提示", "反担保措施不能为空", "info");
			return false;
		}
		if(houseProperyCondition == "" || houseProperyCondition == null){
			$.messager.alert("提示", "房产状态不能为空", "info");
			return false;
		}
		for(var i = length; i < trIndex; i++){
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
		var flag = false;
		var pids = "";
		$.each($("#legal_public").find("tr:gt(0)"), function(k, v) {
			$.each($(v).find(":checkbox"), function(m, n) {
				var pid=$(n).val();
				
				//获得该页面所有的诉讼pids
				if (pid!=null && pid != 'on') {
					pids=pids+","+pid;
				}
			});
		});
		// 保存提交
		$('#myform').form('submit', {
			url : "updateApplyReport.action?pids="+pids,
			onSubmit : function() {
				return true;
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if (ret && ret.header["success"]) {
					   $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {  
					      if (data) { 
// 					    	  closeTab();
					    	  alert("保存成功！");
					    	  cancelProduct();
					        }  
					   	});  
					// 清空所选择的行数据
					clearSelectRows("#grid");
					var pid = ret.header["pid"];
					$("#pid").val(pid);
					$('#myform').datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			},
			error : function(result) {
				alert("保存失败！");
			}
		});
	}
	function closeTab(){
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
		parent.$('#layout_center_tabs').tabs('close', index);
	}
	
	//刷新上一页
	function cancelProduct(){ 
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
		parent.$('#layout_center_tabs').tabs('close','修改申请报告');
	}
	function majaxReset() {
// 		$(".ecoTradeId1").hide();
		$('#myform').form('reset')
	}
</script>
<div style="padding: 10px 10px 10px 10px">
	<div class="easyui-panel"
		data-options="title:'修改申请报告',collapsible:true"
		style="padding: 5px 5px 5px 10px">
		<form id="myform"
			action="${basePath}foreAfterLoanController/updateApplyReport.action"
			method="post">
			<table  style="width: 96%" class="beforeloanTable common_flow">
				<tbody>
					<tr>
						<td class="label_right"><font color="red">*</font>反担保措施：</td>
						<td><textarea class="text text_style"
								style="height: 60px; width: 508px;" maxlength="600"
								name="unAssureCondition" id="unAssureCondition"
								data-options="multiline:true,validType:'length[0,500]'">${applyReport.unAssureCondition}</textarea>
						</td>
					</tr>
					<tr id="legal_tr">
						<td class="label_right">诉讼：</td>
						<td>
							<div id="dlg_addOrDelete">
									<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-add" onclick="addLegal()">增加诉讼</a> 
									<a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-remove" onclick="removeLegal()">删除诉讼</a>
							</div>
							<div class="easyui-panel" title="增加诉讼"
								data-options="collapsible:true" style="width: 99%;">
								<table class="cus_table" id="legal_public">

									<tr style="border: 1px solid #999;">
										<td width="75px" align="center">选择</td>
										<td width="150px" align="center"><font color="red">*</font>诉讼类型</td>
										<td width="278px" align="center"><font color="red">*</font>诉讼内容</td>
									</tr>
									
									<c:forEach items="${legalList}" var="item">
										<tr>
											<td align="center">
												<input type="checkbox" id="legalList${item.pid}_pid"name="${item.pid}" class="isChecked" value="${item.pid}" />
											</td>
											<td align="center">
												<select id="legalList${item.pid}_status" name="${item.pid}status"
													data-options="validType:\'selrequired\'"
													class="easyui-combobox combobox-f combo-f textbox-f"
													style="width: 120px;">
														<c:if test="${item.status==1 }">
															<option value="1" checked="true">个人诉讼</option>
															<option value="2">企业诉讼</option>
														</c:if>
														<c:if test="${item.status==2 }">
															<option value="2" checked="true">企业诉讼</option>
															<option value="1">个人诉讼</option>
														</c:if>
												</select>
											</td>
											<td align="center">
												<textarea class="text text_style"
														style="height: 35px; width: 100%;" maxlength="600"
														name="${item.pid}legalContent" id="legalList${item.pid}_legalContent"
														data-options="multiline:true,validType:'length[0,500]'">${item.legalContent}</textarea>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label_right"><font color="red">*</font>房产状态:</td>
						<td><textarea class="text text_style"
								style="height: 60px; width: 508px;" maxlength="2000"
								name="houseProperyCondition" id="houseProperyCondition"
								data-options="multiline:true,validType:'length[0,500]'">${applyReport.houseProperyCondition}</textarea>
						</td>
					</tr>
					<tr>
						<td class="label_right">备注：</td>
						<td><textarea class="text text_style"
								style="height: 60px; width: 508px;" maxlength="2000"
								name="remark" id="remark"
								data-options="multiline:true,validType:'length[0,500]'">${applyReport.remark}</textarea></td>
					</tr>
					<tr>
						<td></td>
						<td class="align_center" height="50">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" id="save" class="save_data text_btn"
							value="保存" onclick="saveReport()" /> 
							 <input name="projectId" id="projectId"
							type="hidden" value=${projectId}>
						</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
		