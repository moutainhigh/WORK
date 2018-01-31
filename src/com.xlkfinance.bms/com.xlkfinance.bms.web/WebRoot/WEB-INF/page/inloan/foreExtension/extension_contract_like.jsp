<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div class="contract_dis" style="padding: 10px 15px;width:1039px">
		<div class="easyui-panel" title="担保措施合同"  style="height:760px;padding:15px 5px 15px 10px;" data-options="collapsible:true">			
		<div id="contract22" style="height:225px;width:1010px; ">				
		<div id="contractList22" style="height:225px ">	
			
			<div id="toolbar222" class="easyui-panel" >				 
				<form action="perListUrl.action" method="post" id="searchFrom22">
					<!-- 查询条件 -->
					<div style="padding: 5px">
					<table>
						<tr>
								<td>【抵押合同】：</td>
								<td width="60"><input name="customerType" type="radio" value="1" onchange="changeEvent('DY',1)" checked="checked" > 最高额</td>
								<td width="60"><input name="customerType" type="radio" value="2" onchange="changeEvent('DY',2)"> 单笔</td>
							 </tr>
					</table>
					</div>
				</form>
				<!-- 操作按钮 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openCreditsWindow(2)">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCreditContract('DY')">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCreditContract('DY')">删除</a>
			</div>
			<div class="contractListDiv" id="mortgageContractDiv" style="height:225px">
				<table id="grid_contract22" class="easyui-datagrid" 
							style="height:100%;width: auto;"
							data-options="
							    url: '${basePath}creditsContractController/getContractListUrl.action?customerType=1&projectId='+newProjectId+'&contractCatelogKey=DY&oldProjectId='+projectId,
							    method: 'POST',
							    rownumbers: true,
							    singleSelect: false,
							    pagination: true,
							    toolbar: '#toolbar222',
							    idField: 'pid',
							    fitColumns:true,
							    collapsible:true">
							<!-- 表头 -->
							<thead><tr>
							    <th data-options="field:'pid',checkbox:true,width:50" ></th>
							    <th data-options="field:'contractTemplateId'"  hidden="true"></th>
							    <th data-options="field:'contractUrl'"  hidden="true"></th>
							    <th data-options="field:'mortgageBranch',width:100"  >抵押登记部门</th>
							    <th data-options="field:'contractTypeText',width:100"  >合同类型</th>
							    <th data-options="field:'contractName',width:100"  >合同名称</th>
							    <th data-options="field:'contractNo',width:100"  >合同编号</th>
							    <th data-options="field:'num',width:100"  >合同份数</th>
							    <th data-options="field:'firstNum',width:50" >甲方</th>
							    <th data-options="field:'secondNum',width:50"  >乙方</th>
							    <th data-options="field:'cz',formatter:contractOperationFilter" >操作</th>
							</tr></thead>
				</table>
			</div>
		</div>
		 <%-- 企业股东信息列表 --%>
		<div id="contractList33" style="height:225px;padding-top: 10px;">				
			<div id="toolbar33" class="easyui-panel" >		
				<form action="perListUrl.action" method="post" id="searchFrom33">
				<!-- 查询条件 -->
				<div style="padding: 5px">
				<table>
					<tr>
						<td>【质押合同】 </td>
						<td width="60"><input name="customerType" type="radio" value="1" onchange="changeEvent('ZY',1)" checked="checked" > 最高额</td>
						<td width="60"><input name="customerType" type="radio" value="2" onchange="changeEvent('ZY',2)"> 单笔</td>
					 </tr>
				</table>
				</div>
				</form>
				<!-- 操作按钮 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openCreditsWindow(3)">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCreditContract('ZY')">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCreditContract('ZY')">删除</a>
			</div>
			<div class="contractListDiv" id="pledgeContractDiv" style="height:100%">
				<table id="grid_contract33" class="easyui-datagrid" 
							style="height:100%;width: auto;"
							data-options="
							    url: '${basePath}creditsContractController/getContractListUrl.action?customerType=1&projectId='+newProjectId+'&contractCatelogKey=ZY&oldProjectId='+projectId,
							    method: 'POST',
							    rownumbers: true,
							    singleSelect: false,
							    pagination: true,
							    toolbar: '#toolbar33',
							    idField: 'pid',
							    fitColumns:true,
							    collapsible:true">
							<!-- 表头 -->
							<thead><tr>
							    <th data-options="field:'pid',checkbox:true,width:50" ></th>
							    <th data-options="field:'contractTemplateId'"  hidden="true"></th>
							    <th data-options="field:'contractUrl'"  hidden="true"></th>
							    <th data-options="field:'mortgageBranch',width:100"  >质押登记部门</th>
							    <th data-options="field:'contractTypeText',width:100"  >合同类型</th>
							    <th data-options="field:'contractName',width:100"  >合同名称</th>
							    <th data-options="field:'contractNo',width:100"  >合同编号</th>
							    <th data-options="field:'num',width:100"  >合同份数</th>
							    <th data-options="field:'firstNum',width:50" >甲方</th>
							    <th data-options="field:'secondNum',width:50"  >乙方</th>
							    <th data-options="field:'cz',formatter:contractOperationFilter" >操作</th>
							</tr></thead>
				</table>
			</div>
		</div>
 
			<%-- 企业股东信息列表 --%>
		<div id="contractList44" style="height:225px;padding-top: 10px;">
			<div id="toolbar44" class="easyui-panel" >
				<form action="perListUrl.action" method="post" id="searchFrom44">
					<!-- 查询条件 -->
					<div style="padding: 5px">
					<table>
						<tr>
							<td>【保证合同】： </td>
							<td width="60"><input name="customerType" type="radio" value="1" onchange="changeEvent('ZKBZ',1)" checked="checked" > 最高额</td>
							<td width="60"><input name="customerType" type="radio" value="2" onchange="changeEvent('ZKBZ',2)"> 单笔</td>
						 </tr>
					</table>
					</div>
				</form>
				<!-- 操作按钮 -->
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openCreditsWindow(4)">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCreditContract('ZKBZ')">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCreditContract('ZKBZ')">删除</a>
			</div>
			<div class="contractListDiv" id="guaranteeContractDiv" style="height:100%">
				<table id="grid_contract44" class="easyui-datagrid" 
							style="height:100%;width: auto;"
							data-options="
							    url: '${basePath}creditsContractController/getContractListUrl.action?customerType=1&projectId='+newProjectId+'&contractCatelogKey=ZKBZ&oldProjectId='+projectId,
							    method: 'POST',
							    rownumbers: true,
							    singleSelect: false,
							    pagination: true,
							    toolbar: '#toolbar44',
							    idField: 'pid',
							    fitColumns:true,
							    collapsible:true">
							<!-- 表头 -->
							<thead><tr>
							    <th data-options="field:'pid',checkbox:true,width:50" ></th>
							    <th data-options="field:'contractTemplateId'"  hidden="true"></th>
							    <th data-options="field:'contractUrl'"  hidden="true"></th>
							    <th data-options="field:'contractTypeText',width:100"  >合同类型</th>
							    <th data-options="field:'contractName',width:100"  >合同名称</th>
							    <th data-options="field:'contractNo',width:100"  >合同编号</th>
							    <th data-options="field:'num',width:100"  >合同份数</th>
							    <th data-options="field:'firstNum',width:100" >甲方</th>
							    <th data-options="field:'secondNum',width:100"  >乙方</th>
							    <th data-options="field:'cz',formatter:contractOperationFilter" >操作</th>
							</tr></thead>
				</table>
			</div>
		</div>
	</div>		
	</div>
 </div>

 
 <script>
 	$(document).ready(function(){
		// 老项目的保证合同信息是不能修改的
		applyCommon.guaranteeContract_gridOnLoadSuccess(projectId);
		// 老项目的质押合同信息是不能修改的
		applyCommon.pledgeContract_gridOnLoadSuccess(projectId);
		// 老项目的抵押合同信息是不能修改的
		applyCommon.mortgageContract_gridOnLoadSuccess(projectId);
		
		if(projectName){
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
			$(".contract_dis input[type=radio]").attr('disabled',false);
			$(".contract_dis input[type=radio]").removeClass('l-btn-disabled');
	 	}
		if("task_LoanRequest"==workflowTaskDefKey){
			
		}else{
			$('.contract_dis .easyui-combobox').attr('disabled','disabled');
			$('.contract_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.contract_dis .easyui-textbox').attr('disabled','disabled');
			$('.contract_dis .easyui-linkbutton ,.datum a').removeAttr('onclick');
			$('.contract_dis a font').attr('color','gray');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input,.contract_dis  textarea').attr('disabled','disabled');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input,.contract_dis  textarea').attr('readOnly','readOnly');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input,.contract_dis  textarea').addClass('l-btn-disabled');
			$(".contract_dis input[type=radio]").attr('disabled',false);
			$(".contract_dis input[type=radio]").removeClass('l-btn-disabled');
		}
	})
 
 </script>