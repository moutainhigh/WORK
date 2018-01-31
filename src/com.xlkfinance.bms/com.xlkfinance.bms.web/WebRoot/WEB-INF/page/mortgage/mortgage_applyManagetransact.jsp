<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<div id="dlg_buttons_tqcl">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMortgageOperation(3,'#tqclForm','#dlg_tqcl')">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_tqcl').dialog('close')">关闭</a>
	</div>
	
	<%-- 抵质押物提取处理 --%>
	<div id="dlg_tqcl" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_tqcl" 
	 	style="width:850px;height:500px;padding: 10px;" data-options="modal:true" closed="true"  >
		<h4>抵质押物提取处理</h4>
		<hr />
		<form id="tqclForm" method="post" >
			<input type="hidden" name="pid"/>
			<input type="hidden" name="baseId"/>
			<table>
				<tr>
					<td class="label_right" style="width: 140px;">所有权人：</td>
					<td>
						<input name="ownNameText" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">权证编号(房地产证号)：</td>
					<td>
						<input name="warrantsNumber" type="text" class="no_frame text_style" disabled="true"  class="easyui-textbox" />
					</td>
					<td class="label_right">物件描述：</td>
					<td>
						<input name="constructionArea" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">物件地点：</td>
					<td colspan="3">
						<input id="objectLocation_tqcl" type="text" class="no_frame text_style" disabled="true" style="width: 510px;" />
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
						<input name="purpose"  type="text" class="no_frame text_style" style="width: 200px;" disabled="true" class="easyui-textbox" />
					</td>
					<td class="label_right">评估净值：</td>
					<td>
						<input name="assessValue" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">抵押登记编号：</td>
					<td>
						<input name="regNumber" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" class="easyui-textbox" />
					</td>
					<td class="label_right">登记时间：</td>
					<td>
						<input name="regDt" type="text" class="no_frame text_style" disabled="true"  style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">保管时间：</td>
					<td>
						<input name="saveDttm" type="text" class="no_frame text_style" disabled="true"  style="width: 200px;" />
					</td>
					<td class="label_right">保管备注备注：</td>
					<td>
						<input name="saveRemark" type="text" class="no_frame text_style" disabled="true"  style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">提取申请事由：</td>
					<td colspan="3">
						<input id="applyRemark" name="applyRemark" class="easyui-textbox" style="width:510px;height:60px" disabled="true" data-options="multiline:true" />
					</td>
				</tr>
				<tr>
					<td class="label_right">申请人：</td>
					<td colspan="3">
						<input name="applyUserName" type="text" class="no_frame text_style" disabled="true" style="width: 200px;" />
					</td>
				</tr>
				
				<tr>
					<td class="label_right">申请材料：</td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td colspan="4" style="padding-left: 50px;" >
						<table id="tqcl-datagrid" class="easyui-datagrid" style="width:auto;height:auto;"
							data-options="
									url: '',
								    method: 'POST',
								    rownumbers: true,
								    singleSelect: false,
								    idField: 'pid',
								    fitColumns:true ">
							<thead>
								<th data-options="field:'pid',hidden:true" ></th>
								<th data-options="field:'fileUrl',hidden:true"></th>
								<th data-options="field:'fileProperty',hidden:true"></th> 
								<th data-options="field:'fileType',width:150" align="center" halign="center" >文件种类</th>
								<th data-options="field:'fileName',width:200" align="center" halign="center" >文件名称</th>
								<th data-options="field:'fileSize',width:150,formatter:sizeFileter" align="center" halign="center" >大小</th>
								<th data-options="field:'uploadDttm',width:150,formatter:convertDate" align="center" halign="center" >上传时间</th>
								<th data-options="field:'fileDesc',width:150" align="center" halign="center" >备注</th>
								<th data-options="field:'cz',formatter:doFileterTqcl" >操作</th>
							</thead>
						</table>
					</td>
				</tr>
			
				<tr>
					<td class="label_right"><font color="red">*</font>提取时间：</td>
					<td colspan="3">
						<input name="handleDttm" class="easyui-datetimebox" style="width: 200px;" editable="false" required="true" />
					</td>
				</tr>
				<tr>
					<td class="label_right">提取备注：</td>
					<td colspan="3">
						<input name="handleRemark" class="easyui-textbox" style="width:510px;height:60px" data-options="multiline:true" />
					</td>
				</tr>
			</table>
			
		</form>
	</div>
	
	<script type="text/javascript">
		
		function doFileterTqcl(value,row,index){
			var downloadDom = "<a href='${basePath}contractInfoController/download.action?path="+row.fileUrl+"'><font color='blue'>下载</font></a> ";
			return downloadDom;
		}

	</script>
