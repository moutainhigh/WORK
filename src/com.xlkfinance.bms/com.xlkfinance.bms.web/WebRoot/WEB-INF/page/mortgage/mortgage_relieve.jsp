<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<div id="dlg_buttons_jcdzy">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMortgageOperation(4,'#jcdzyForm','#dlg_jcdzy')">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_jcdzy').dialog('close')">关闭</a>
	</div>
	
	<%-- 抵质押解除 --%>
	<div id="dlg_jcdzy" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_jcdzy" 
	 	style="width:850px;height:480px;padding: 10px;" data-options="modal:true" closed="true"  >
		<h4>抵质押解除</h4>
		<hr />
		<form id="jcdzyForm" method="post" >
			<input type="hidden" name="pid"/>
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
						<input id="objectLocation_jcdzy" type="text" class="no_frame text_style" disabled="true" style="width: 510px;" />
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
					<td class="label_right"><font color="red">*</font>解除时间：</td>
					<td colspan="3">
						<input name="removeDttm" class="easyui-datetimebox" style="width: 200px;" editable="false" required="true" />
					</td>
				</tr>
				<tr>
					<td class="label_right">解除备注：</td>
					<td colspan="3">
						<input name="removeRemark" class="easyui-textbox" style="width:510px;height:60px" data-options="multiline:true" />
					</td>
				</tr>
			</table>
			
			<h4>抵质押解除资料上传</h4>
			<hr />
			<div id="jcdzy_toolbar">
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="insertFileDizhiya('抵质押解除类型','#jcdzyForm')">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="delFileDizhiya('#jcdzy-datagrid')">删除</a>
				</div>
			</div>
			<table id="jcdzy-datagrid" class="easyui-datagrid" style="width:auto;height:auto;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    toolbar: '#jcdzy_toolbar',
					    idField: 'pid',
					    fitColumns:true ">
				<thead>
					<th data-options="field:'pid',checkbox:true" align="center" ></th>
					<th data-options="field:'fileUrl',hidden:true"></th>
					<th data-options="field:'fileProperty',hidden:true"></th> 
					<th data-options="field:'fileType',width:150" align="center" halign="center" >文件种类</th>
					<th data-options="field:'fileName',width:200" align="center" halign="center" >文件名称</th>
					<th data-options="field:'fileSize',width:150,formatter:sizeFileter" align="center" halign="center" >大小</th>
					<th data-options="field:'uploadDttm',width:150,formatter:convertDate" align="center" halign="center" >上传时间</th>
					<th data-options="field:'fileDesc',width:150" align="center" halign="center" >备注</th>
					<th data-options="field:'cz',formatter:doFileterJcdzy" >操作</th>
				</thead>
			</table>
		</form>
	</div>
	
	
	<script type="text/javascript">
	
		function doFileterJcdzy(value,row,index){
			var downloadDom = "<a href='${basePath}contractInfoController/download.action?path="+row.fileUrl+"'><font color='blue'>下载</font></a> | ";
			var editDom = "<a href='javascript:void(0)' onclick='editFileDi("+row.pid+",\""+row.fileDesc+"\",1,\""+row.fileProperty+"\")'><font color='blue'>编辑</font></a> | ";
			var reload = "<a href='javascript:void(0)' onclick='editFileDi("+row.pid+",\""+row.fileDesc+"\",2,\""+row.fileProperty+"\")'><font color='blue'>重新上传</font></a> | ";
			var projectDom = "<a href='javascript:void(0)' onclick='delFileDi("+row.pid+")'><font color='blue'> 删除</font></a>";
			return downloadDom+editDom+reload+projectDom;
		}

	</script>
