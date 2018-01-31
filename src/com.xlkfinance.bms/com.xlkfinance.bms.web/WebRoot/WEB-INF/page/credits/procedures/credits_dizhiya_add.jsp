<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<div class="dizhiya_panel_none">
 	<%-- 抵质押物列表 --%>
 	<div  class="easyui-panel dizhiya_dis" title="抵质押办理情况" data-options="collapsible:true" style="padding:10px" style="width:auto">
		
		<!-- 操作按钮 -->
		<div id="toolbar_bldzyw"  class="easyui-panel" style="border-bottom: 5px;padding:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="openItem()">办理抵质押登记</a>
		</div>
		
		<div class="contractListDiv" style="height:auto;width: auto;">
			<table id="grid_loadZy" class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="
					    url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: true,
					    pagination: true,
					    toolbar: '#toolbar_bldzyw',
					    idField: 'pid',
					    fitColumns:true ">
					<!-- 表头 -->
					<thead><tr>
						<th data-options="field:'pid',checkbox:true" align="center" ></th>
						<th data-options="field:'mortgageGuaranteeType',width:100,formatter:formatterBldzywMortGuaType" align="center" >担保类型</th>
						<th data-options="field:'mortgageTypeText',width:100" align="center" >抵押物类型</th>
						<th data-options="field:'ownNameText',width:100" align="center" >权利人</th>
						<th data-options="field:'itemName',formatter:projectAssBaseHyperlink,width:100" align="center" >物件名称（房地产名称）</th>
						<th data-options="field:'purpose',width:100" align="center" >用途</th>
						<th data-options="field:'remark',width:100" align="center" >备注</th>
						<th data-options="field:'status',width:100,formatter:formatterBldzywStatus" align="center" >办理抵质押物状态</th>	
						<th data-options="field:'regNumber',width:100" align="center" >办理登记号</th>	
						<th data-options="field:'transactExplain',width:100" align="center" >办理说明</th>		
					</tr></thead>
			</table>
		</div>
	
		<%-- 办理抵质押物 --%>
		<div id="dlg_dzywxx_lc" class="easyui-dialog" fitColumns="true" 
		 	style="width:850px;top:10px;padding: 10px;height: 500px;" data-options="modal:true" closed="true" >
			<h2>办理抵质押登记</h2>
			<form id="dzywxxForm_lc" action="<%=basePath%>mortgageController/transactProjectAssBase.action" method="post">
			<input type="hidden" name="pid" value="-1"/>
			<table>
				<tr>
					<td class="label_right" style="width: 150px;">所有权人：</td>
					<td colspan="3">
						<input name="ownNameText"  class="easyui-textbox"  />
					</td>
				</tr>
				<tr>
					<td class="label_right">权证编号（房地产证号）：</td>
					<td>
						<input name="warrantsNumber" class="easyui-textbox" />
					</td>
					<td class="label_right">建筑面积：</td>
					<td>
						<input class="easyui-textbox" name="constructionArea" />
					</td>
				</tr>
				<tr>
					<td class="label_right">物件地点：</td>
					<td colspan="3">
						<input class="easyui-textbox" id="thingPlace" style="width: 600px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">物件名称：</td>
					<td colspan="3">
						<input name="itemName" class="easyui-textbox" style="width: 600px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">用途：</td>
					<td>
						<input name="purpose" class="easyui-textbox" />
					</td>
					<td class="label_right">评估净值：</td>
					<td>
						<input name="assessValue"  class="easyui-textbox" />				
					</td>
				</tr>
				<tr>
					<td class="label_right">登记价（元）：</td>
					<td colspan="3">
						<input name="regPrice" class="easyui-numberbox" precision="2" groupSeparator="," />
					</td>
				</tr>
				<tr>
					<td class="label_right">抵押登记编号：</td>
					<td>
						<input name="regNumber" class="easyui-textbox"  />
					</td>
					<td class="label_right">登记时间：</td>
					<td>
						<input name="regDt" class="easyui-datetimebox" style="width: 200px;" editable="false" /> 			
					</td>
				</tr>
				<tr>
					<td class="label_right">登记机关名称：</td>
					<td colspan="3">
						<input name="regOrgName" class="easyui-textbox" style="width:600px;"  />
					</td>
				</tr>
				<tr>
				<tr>
					<td class="label_right">经办人：</td>
					<td colspan="3">
						<input name="operator" class="easyui-textbox" style="width:200px;"  />
					</td>
				</tr>
				<tr>
					<td class="label_right">办理说明：</td>
					<td colspan="3">
						<input name="transactExplain" class="easyui-textbox" style="width:600px;height:60px" data-options="multiline:true" />			
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center" height="35">
						<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDzywxx()">保存</a>
				 	</td>
				</tr>
			</table>
			</form>
			
	<!-- gaoWen上传开始 -->		
			
			
			
	<div id="toolbar" class="easyui-panel" style="border: 0;"></div>
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0);"
		class="easyui-linkbutton" onclick="uploadrepayfile()">上传</a> <a
		href="javascript:void(0);" class="easyui-linkbutton"
		onclick="delDivert()">删除</a>
</div>
<div id="status"></div>
<div id="dlg-buttons">
	<center>
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="submitForm()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#upload').dialog('close')">取消</a>
	</center>
</div>
<div id="upload" class="easyui-dialog"
	style="width: 500px; height: 300px; padding: 10px 20px;" closed="true"
	buttons="#dlg-buttons">
	<form id="fileUploadForm" name="fileUploadForm"
		action="${basePath}beforeLoanController/uploadDivertapply.action"
		method="post" enctype="multipart/form-data" target="hidden_frame">
		<p>
			文件类型： <select name="filekinds" id="selectAge"
				style="border: 1px solid #000">
				<option value="1">营业执照</option>
				<option value="2">调查资料</option>
				<option value="3">其他</option>
			</select>
		</p>
		<p style="display: none;">
			<input name="baseId" id="baseId" />
		</p>
		<p>
			选择文件：<input class="input_text" type="text" id="txt1"
				name="fileToUpload" /> <input type="button" name="uploadfile2"
				id="uploadfile2" style="padding-left: 10px;" value="浏览..." /> <input
				class="input_file" size="30" type="file" name="file1" id="file1"
				onchange="txt1.value=this.value" />
		</p>
		<p>
			<span style="margmargin-top: 10px;">上传说明:</span>
			<textarea rows="4" style="width: 300px;" name="fileDesc"></textarea>
		</p>
	</form>
</div>
<!-- queryRegAdvapplyFile.action?preRepayId='+preRepayId -->
<div style="width: 100%; padding-top: 20px; text-align: center;">
	<table id="up_data" class="easyui-datagrid"
		data-options="
	    url: '',
	    method: 'POST',
	    idField: 'pid',
	    fitColumns:true,
		checkOnSelect: false,
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar'
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
				<th data-options="field:'pid',checkbox:true"></th>
				<th data-options="field:'fileType',width:100">文件类型</th>
				<th
					data-options="field:'fileFunType',width:150,formatter:uploadType">文件种类</th>
				<th data-options="field:'fileName',width:150">文件名称</th>
				<th data-options="field:'fileSize',width:150">大小</th>
				<th
					data-options="field:'uploadDttm',width:150,formatter:dateupSplit">上传时间</th>
				<th data-options="field:'fileDesc',width:150">上传说明</th>
				<th data-options="field:'yyyyy',width:300,formatter:uploadadvoperat">操作</th>
			</tr>
		</thead>
	</table>
</div>
<div style="height: 40px"></div>		
			
			
			
			
			
<!-- gaoWen上传结束 -->				
			
			
			
			
			
			
			
			
		</div>
	</div>

</div>

	<!-- begin 查看抵质押物详情 -->
	<%-- 查看抵质押物详细信息 --%>
	<%@ include file="../../common/common_mortgage.jsp" %>		
	<%-- 上传附件资料 --%>
	<%@ include file="../../mortgage/mortgage_file.jsp" %>
	<!-- end 查看抵质押物详情 -->

<script type="text/javascript">

// 初始化
$(document).ready(function(){
	// 设置 办理抵制押物信息数据请求url
	$("#grid_loadZy").datagrid({url:'<%=basePath%>mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId='+newProjectId+'&mortgageGuaranteeType=-1&oldProject='+projectId});
	// 老项目的办理抵制押物信息是不能修改的
	applyCommon.mortgage_gridOnLoadSuccess(projectId);
})

//打开办理页面	
function openItem(){
	var row = $('#grid_loadZy').datagrid('getSelected');
	// 判断选取的数量
 	if (!row) {
		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
	// 判断状态是否是未办理抵押登记
	if(row.status != 1){
		$.messager.alert("操作提示","只能对未办理的担保信息进行抵押登记!","error");
		return;
	}
	// 重置  and 赋值 form表单
	$("#dzywxxForm_lc").form("reset");
	$("#dzywxxForm_lc").form('load',row);
	// 设置物件地址    -- 拼接
	$("#thingPlace").textbox("setValue",row.addressProvince+row.addressCity+row.addressArea+row.addressDetail);
	$('#dlg_dzywxx_lc').dialog('open').dialog('setTitle', "办理抵质押登记");
}

// 办理抵质押物
function saveDzywxx(){
	// 提交表单
	$("#dzywxxForm_lc").form('submit',{
		success:function(result){
			var ret = eval("("+result+")");
			// 判断是否成功
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示',ret.header["msg"],'info');
	    		$("#grid_loadZy").datagrid('reload');
	    		$("#dlg_dzywxx_lc").dialog('close');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		}
	});
} 

// 根据状态判断什么状态
function formatterBldzywStatus(value,row){
	if(value == 1){
		return "等待办理登记";
	}else if(value == 2){
		return "已办理登记";
	}else if(value == 3){
		return "已保管";
	}else if(value == 4){
		return "提取申请中";
	}else if(value == 5){
		return "已提取";
	}else if(value == 6){
		return "已解除";
	}
}

// 判断是抵押还是质押
function formatterBldzywMortGuaType(value,row){
	if(value == 1){
		return "抵押";
	}else if(value == 2){
		return "质押";
	}
}

</script>
<script>

function uploadapply() {
	$("#uploadadvapply").submit();
}
function selectAll() {
	$("#applyrepayfile").click(function() {
		if (this.checked) {
			$("input[name='applyrepayfile']").each(function() {
				this.checked = true;
			});
		} else {
			$("input[name='applyrepayfile']").each(function() {
				this.checked = false;
			});
		}
	});
}

var path = null;
function uploadadvoperat(value, row, index) {
	path = row.filePath;
	var downloadDom = "<a href='${basePath}rePaymentController/downloadadvapply.action?path="
			+ row.filePath + "'><font color='blue'>点击下载</font></a> ";
	var downd = "<span  style='display: none;' id='"+row.pId+"'>"
			+ row.filePath
			+ "</span><a href='javascript:void(0)' onclick='delfilebypath("
			+ row.pId + ")'><font  color='blue'> 删除</font></a>";
	return downloadDom + downd;
}
function delfilebypath(dta) {
	$
			.post(
					'${basePath}rePaymentController/delectUpdateAdvapply.action',
					{
						pId : dta,
						path : $("#" + dta).text()
					},
					function(data) {
						$.messager.alert("操作提示", "删除成功！", "success");
						$
						.post(
								"${basePath}beforeLoanController/queryAssBaseFile.action",
								{
									baseId :$("#dzywxxForm_lc input[name='pid']").val()
								},
								function(dt) {
									$('#up_data')
											.datagrid(
													'loadData',
													eval("{"
															+ dt
															+ "}"));
								});
					});
}
//上传文件
function submitForm() {
	var fileName = $("#file1").val();
	if (fileName == "" || null == fileName) {
		$.messager.alert("操作提示", "请选择要上传的文件！", "error");
		return false;
	}
	$("#baseId").val($("#dzywxxForm_lc input[name='pid']").val());
	$('#fileUploadForm')
			.form(
					'submit',
					{
						success : function(data) {
							$.messager.alert("操作提示", "上传成功！", "success");
							$('#upload').dialog('close')
							$
									.post(
											"${basePath}beforeLoanController/queryAssBaseFile.action",
											{
												baseId :$("#dzywxxForm_lc input[name='pid']").val()
											},
											function(dt) {
												$('#up_data')
														.datagrid(
																'loadData',
																eval("{"
																		+ dt
																		+ "}"));
											});

						}
					});
}

function delDivert() {

	var row = $('#up_data').datagrid('getSelections');
	if (row.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}// 获取选中的系统用户名 
	var pIds = "";
	for (var i = 0; i < row.length; i++) {
		pIds += row[i].pId;
		pIds += ",";
	}
	var str = pIds.substring(0, pIds.length - 1); // 取字符串。  
	$
			.post(
					"${basePath}rePaymentController/delectUpdateAdvapply.action",
					{
						pId : str
					},
					function(data) {
						$.messager.alert("操作提示", "删除成功！", "success");
						$
						.post(
								"${basePath}beforeLoanController/queryAssBaseFile.action",
								{
									baseId :$("#dzywxxForm_lc input[name='pid']").val()
								},
								function(dt) {
									$('#up_data')
											.datagrid(
													'loadData',
													eval("{"
															+ dt
															+ "}"));
								});
					});
}
function uploadType(value, row, index) {
	if (1 == row.fileFunType) {
		return "营业执照";
	}
	if (2 == row.fileFunType) {
		return "调查资料";
	}
	return "其他";
}
function dateupSplit(value, row, index) {
	if (null != row.uploadDttm && "" != row.uploadDttm) {
		return row.uploadDttm.split(" ")[0];
	}
	return row.uploadDttm;
}
function uploadrepayfile() {
	var baseId=$("#dzywxxForm_lc input[name='pid']").val();
	
	if (typeof(baseId) == "undefined"||baseId < 1||baseId==''||baseId==null) {
		$.messager.alert("操作提示", "请先保存数据！", "info");
		return false;
	}
	$('#upload').dialog('open').dialog('setTitle', "修改模板文件路径");
	$("#upload").dialog("move", {
		top : $(document).scrollTop() + 260 * 0.5
	});
}
	</script>