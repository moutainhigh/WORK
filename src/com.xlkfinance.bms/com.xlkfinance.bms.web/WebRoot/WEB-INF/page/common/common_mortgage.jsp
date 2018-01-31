<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<!-- begin 查看抵质押物详情 -->
	<div id="dlg_buttons_seeAssBase">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_seeAssBase').dialog('close')">关闭</a>
	</div>
	<div id="dlg_seeAssBase" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_seeAssBase" 
 		style="width:980px;height:500px;padding: 10px;" data-options="modal:true" closed="true"  >
		<form id="ck_seeAssBaseForm" method="post" >
 		<%-- begin 抵质押物基础信息 --%>
		<div  style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="抵质押物基础信息" data-options="collapsible:true">
			<table width="95%">
				<tr>
					<td class="label_right">所有权人：</td>
					<td width="230">
						<input name="ownNameText" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
					<td class="label_right">抵质押物类型：</td>
					<td width="230">
						<input name="mortgageTypeText" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">所有人类型：</td>
					<td>
						<input name="ownTypeText" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
					<td class="label_right" style="width: 160px;">权证编号(房地产证号)：</td>
					<td>
						<input name="warrantsNumber" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">共有人列表：</td>
					<td colspan="3">
						<table id="seeAssBase_grid" class="easyui-datagrid"  		   
					    	style="height: auto;width:610px;" 	
						 	data-options="
						    url: '',
						    method: 'post',
						    rownumbers: true,
						    singleSelect: false,
						    fitColumns:true,
						    idField: 'pid'
						    ">
							<thead>
								<tr>
									<th data-options="field:'acctName'" width="40%" halign="center" align="center" resizable="false" >客户名称</th>
									<th data-options="field:'acctTypeText'" width="10%" halign="center" align="center" resizable="false" >客户类别</th>
									<th data-options="field:'certType'" width="20%" halign="center" align="center" resizable="false" >证件类型</th>
									<th data-options="field:'certNumber'" width="25%" halign="center" align="center" resizable="false" >证件号码</th>
								</tr>
							</thead>
					   	</table>
					</td>
				</tr>
				<tr>
					<td class="label_right">物件地点：</td>
					<td colspan="3">
						<input id="seeAssBase_address" type="text" class="no_frame text_style" disabled="true" style="width: 510px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">抵质押物名称：</td>
					<td colspan="3">
						<input name="itemName" type="text" class="no_frame text_style" disabled="true" style="width: 510px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">用途：</td>
					<td>
						<input name="purpose"  type="text" class="no_frame text_style" disabled="true" style="width: 200px;" disabled="true" />
					</td>
					<td class="label_right">登记时间：</td>
					<td>
						<input name="regDt" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">登记价（元）：</td>
					<td >
						<input name="regPrice" type="text" class="no_frame text_style" disabled="true" style="width: 200px;"/>
					</td>
					<td class="label_right">评估净值（元）：</td>
					<td>
						<input name="assessValue" type="text" class="no_frame text_style" disabled="true" style="width: 200px;"/>
					</td>
				<tr>
					<td class="label_right">抵质押价值（元）：</td>
					<td >
						<input name="mortgageValue" type="text" class="no_frame text_style" disabled="true" style="width: 200px;"/>
					</td>
					<td class="label_right">抵质押率：</td>
					<td>
						<input name="mortgageRate" type="text" class="no_frame text_style" disabled="true" style="width: 200px;"/>%
					</td>
				</tr>
				<tr>
					<td class="label_right">公允价值（元）：</td>
					<td >
						<input name="fairValue" type="text" class="no_frame text_style" disabled="true" style="width: 200px;"/>
					</td>
					<td class="label_right">公允价值获取方式：</td>
					<td>
						<input name="fairValueGetMethod" type="text" class="no_frame text_style" disabled="true" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">备注：</td>
					<td colspan="3">
						<input name="remark" class="easyui-textbox" disabled="true" style="width:600px;height:60px" data-options="multiline:true" />			
					</td>
				</tr>
			</table>
			
			<div id="mortgageTypeDiv_seeAssBase">
				
			</div>
		
		</div>
		</div>
		<%-- end 抵质押物基础信息 --%>
		
		
		<%-- begin 抵质押物登记信息 --%>
		<div  style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="抵质押物登记信息 " data-options="collapsible:true,collapsed:true">
			<table>
				<tr>
					<td class="label_right">抵押登记编号：</td>
					<td>
						<input name="regNumber" class="no_frame text_style" disabled="true"  />
					</td>
					<td class="label_right">登记时间：</td>
					<td>
						<input name="regDt" class="no_frame text_style" disabled="true" style="width: 200px;" editable="false" /> 			
					</td>
				</tr>
				<tr>
					<td class="label_right">登记机关名称：</td>
					<td colspan="3">
						<input name="regOrgName" class="no_frame text_style" disabled="true" style="width:600px;"  />
					</td>
				</tr>
				<tr>
					<td class="label_right">经办人：</td>
					<td colspan="3">
						<input name="operator" class="no_frame text_style" disabled="true" style="width:200px;"  />
					</td>
				</tr>
				<tr>
					<td class="label_right">办理说明：</td>
					<td colspan="3">
						<input id="ck_transactExplain" name="transactExplain" class="easyui-textbox" disabled="true" style="width:600px;height:60px" data-options="multiline:true" />			
					</td>
				</tr>
			</table>
			
			<h4>抵质押物办理资料</h4>
			<hr />
			<table id="ck_bldj-datagrid" class="easyui-datagrid" style="width:850px;height:auto;padding: 5px;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    idField: 'pid',
					    fitColumns:true ">
				<thead>
					<th data-options="field:'fileUrl',hidden:true"></th>
					<th data-options="field:'fileType',width:150" align="center" halign="center" >文件种类</th>
					<th data-options="field:'fileName',width:200" align="center" halign="center" >文件名称</th>
					<th data-options="field:'fileSize',width:150,formatter:sizeFileter" align="center" halign="center" >大小</th>
					<th data-options="field:'uploadDttm',width:150,formatter:convertDate" align="center" halign="center" >上传时间</th>
					<th data-options="field:'fileDesc',width:150" align="center" halign="center" >备注</th>
					<th data-options="field:'cz',formatter:doFileterCaoZuo" >操作</th>
				</thead>
			</table>
			
		</div>
		</div>
		<%-- end 抵质押物登记信息 --%>
		
		
		<%-- begin 抵质押物保管信息 --%>
		<div  style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="抵质押物保管信息 " data-options="collapsible:true,collapsed:true">
		
			<table id="ck_bgcl-datagrid" class="easyui-datagrid" style="width:850px;height:auto;padding: 5px;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    idField: 'pid',
					    fitColumns:true ">
				<thead>
					<th data-options="field:'saveDttm',formatter:convertDate" width="20%" align="center" halign="center" >保存时间</th>
					<th data-options="field:'saveUserName'" width="20%" align="center" halign="center" >保管处理人</th>
					<th data-options="field:'saveRemark'" width="40%"  align="center" halign="center" >保管备注</th>
				</thead>
			</table>
			
		</div>
		</div>
		<%-- end 抵质押物保管信息 --%>
		
		
		<%-- begin 抵质押物提取信息 --%>
		<div  style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="抵质押物提取信息 " data-options="collapsible:true,collapsed:true">
			
			<table id="ck_tq-datagrid" class="easyui-datagrid" style="width:auto;height:auto;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    idField: 'pid',
					    fitColumns:true ">
				<thead>
					<th data-options="field:'applyDttm',formatter:convertDate" width="10%" align="center" halign="center" >提取申请时间</th>
					<th data-options="field:'applyUserName'" width="10%" align="center" halign="center" >提取申请人</th>
					<th data-options="field:'applyRemark'" width="20%" align="center" halign="center" >提取申请备注</th>
					<th data-options="field:'handleDttm',formatter:convertDate" width="10%" align="center" halign="center" >提取处理时间</th>
					<th data-options="field:'handleUserName'" width="10%" align="center" halign="center" >提取处理人</th>
					<th data-options="field:'handleRemark'" width="20%" align="center" halign="center" >提取处理备注</th>
					<th data-options="field:'cz',formatter:openExtraction" width="10%" align="center" halign="center" >查看申请资料</th>
				</thead>
			</table>
			
		</div>
		</div>
		<%-- end 抵质押物提取信息 --%>
		
		
		<%-- begin 抵质押物处理信息 --%>
		<div  style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="抵质押物处理信息 " data-options="collapsible:true,collapsed:true">
			
			<table id="ck_cl-datagrid" class="easyui-datagrid" style="width:auto;height:auto;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    idField: 'pid',
					    fitColumns:true ">
				<thead>
					<th data-options="field:'handleDttm',formatter:convertDate" width="15%" align="center" halign="center" >处理时间</th>
					<th data-options="field:'handleUserName'" width="15%" align="center" halign="center" >处理人</th>
					<th data-options="field:'handleRemark'" width="40%" align="center" halign="center" >处理备注</th>
					<th data-options="field:'cz',formatter:openHandle" width="10%" align="center" halign="center" >查看申请资料</th>
				</thead>
			</table>
			
		</div>
		</div>
		<%-- end 抵质押物处理信息 --%>
		
		
		<%-- begin 抵质押物解除处理信息 --%>
		<div  style="padding: 10px 10px 0 10px;">
		<div class="easyui-panel" title="抵质押物解除处理信息 " data-options="collapsible:true,collapsed:true">
			
			<table>
				<tr>
					<td class="label_right">解除时间：</td>
					<td>
						<input name="removeDttm" type="text" class="no_frame text_style"  disabled="disabled" />
					</td>
					<td class="label_right">解除处理人：</td>
					<td>
						<input name="proposerText" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" /> 			
					</td>
				</tr>
				<tr>
					<td class="label_right">解除备注：</td>
					<td colspan="3">
						<input id="removeRemark" name="transactExplain" disabled="disabled" class="easyui-textbox" style="width:600px;height:60px" data-options="multiline:true" />			
					</td>
				</tr>
			</table>
			
			<h4>抵质押物解除资料</h4>
			<hr />
			<table id="ck_jc-datagrid" class="easyui-datagrid" style="width:850px;height:auto;padding: 5px;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    idField: 'pid',
					    fitColumns:true ">
				<thead>
					<th data-options="field:'fileUrl',hidden:true"></th>
					<th data-options="field:'fileType',width:150" align="center" halign="center" >文件种类</th>
					<th data-options="field:'fileName',width:200" align="center" halign="center" >文件名称</th>
					<th data-options="field:'fileSize',width:150,formatter:sizeFileter" align="center" halign="center" >大小</th>
					<th data-options="field:'uploadDttm',width:150,formatter:convertDate" align="center" halign="center" >上传时间</th>
					<th data-options="field:'fileDesc',width:150" align="center" halign="center" >备注</th>
					<th data-options="field:'cz',formatter:doFileterCaoZuo" >操作</th>
				</thead>
			</table>
			
		</div>
		</div>
		<%-- end 抵质押物解除处理信息 --%>
		
		
		</form>
	</div>
	<!-- end 查看抵质押物详情 -->
	
	<!-- 查看资料接口 -->
	<div id="dlg_material" class="easyui-dialog" fitColumns="true" 
		style="width:850px;height:480px;padding: 10px;" data-options="modal:true" closed="true" > 
		<table id="ck_material" class="easyui-datagrid" style="width:750px;height:auto;padding: 5px;"
			data-options="
					url: '',
				    method: 'POST',
				    rownumbers: true,
				    singleSelect: false,
				    idField: 'pid',
				    fitColumns:true ">
			<thead>
				<th data-options="field:'fileUrl',hidden:true"></th>
				<th data-options="field:'fileType',width:150" align="center" halign="center" >文件种类</th>
				<th data-options="field:'fileName',width:200" align="center" halign="center" >文件名称</th>
				<th data-options="field:'fileSize',width:150,formatter:sizeFileter" align="center" halign="center" >大小</th>
				<th data-options="field:'uploadDttm',width:150,formatter:convertDate" align="center" halign="center" >上传时间</th>
				<th data-options="field:'fileDesc',width:150" align="center" halign="center" >备注</th>
				<th data-options="field:'cz',formatter:doFileterCaoZuo" >操作</th>
			</thead>
		</table>
	</div>
	
	
	<script type="text/javascript">
		
		// 格式化操作
		function doFileterCaoZuo(value,row,index){
			return "<a href='${basePath}contractInfoController/download.action?path="+row.fileUrl+"'><font color='blue'>下载</font></a>";
		}
		
		// 打开查看提取文件资料窗口
		function openExtraction(value, row){
			// 拼接A标签
			var btn = "<a href='#' class='easyui-linkbutton' onclick='clickMaterial("+row.pid+","+2+")' >" +
					"<span style='color:blue; '>查看资料</span></a>";  
			return btn;   
		}
		
		// 打开查看处理文件资料窗口
		function openHandle(value, row){
			// 拼接A标签
			var btn = "<a href='#' class='easyui-linkbutton' onclick='clickMaterial("+row.pid+","+4+")' >" +
					"<span style='color:blue; '>查看资料</span></a>";  
			return btn;   
		}
		
		/**
		 * 根据抵质押物ID获取相对应的抵质押物信息and保管信息
		 * @param refId 相关联的ID(提取ID或者处理ID)
		 * @param type 抵质押物类型(提取类型,处理类型)
		 */
		function clickMaterial(refId,type){
			// 打开窗口
			$('#dlg_material').dialog('open').dialog('setTitle', "查看抵质物处理资料");
			// 加载datagrid
			$('#ck_material').datagrid({
				url:"getProjectAssFile.action?baseId="+refId+"&fileTyle="+type,
	 			method:'post'
			}); 
		}
	</script>
	
	
	