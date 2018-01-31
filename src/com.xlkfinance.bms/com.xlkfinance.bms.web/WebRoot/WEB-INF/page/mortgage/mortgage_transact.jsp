<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
		
		<div id="dlg_buttons_bldj">
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMortgageOperation(5,'#dzywxxForm_lc','#dlg_dzywxx_lc')">保存</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_dzywxx_lc').dialog('close')">关闭</a>
		</div>
		
	<div id="dlg_dzywxx_lc" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_bldj" 
 		style="width:850px;padding: 10px;height: 480px;" data-options="modal:true" closed="true" >
		<h4>办理抵质押登记</h4>
		<hr />
		<form id="dzywxxForm_lc"  method="post">
			<input type="hidden" name="pid" id="pid_ass" />
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
						<input id="objectLocation_bldj" type="text" class="no_frame text_style" disabled="true" style="width: 510px;" />
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
						<input name="assessValue" type="text" class="no_frame text_style" style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right">登记价（元）：</td>
					<td >
						<input name="regPrice" type="text" class="no_frame text_style" style="width: 200px;"/>
					</td>
					<td class="label_right">抵质押率：</td>
					<td>
						<input name="mortgageRate" type="text" class="no_frame text_style" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">抵押登记编号：</td>
					<td>
						<input name="regNumber" class="easyui-textbox"  />
					</td>
					<td class="label_right">登记时间：</td>
					<td>
						<input name="regDt" class="easyui-datebox" style="width: 200px;" editable="false" /> 			
					</td>
				</tr>
				<tr>
					<td class="label_right">登记机关名称：</td>
					<td colspan="3">
						<input name="regOrgName" class="easyui-textbox" style="width:600px;"  />
					</td>
				</tr>
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
			</table>
			
			<h4>抵质押物办理资料上传</h4>
			<hr />
			<div id="bldj_toolbar">
				<div style="padding-bottom:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="insertFileDizhiya('抵质押物办理类型','#dzywxxForm_lc')">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="delFileDizhiya('#bldj-datagrid')">删除</a>
				</div>
			</div>
			<table id="bldj-datagrid" class="easyui-datagrid" style="width:auto;height:auto;"
				data-options="
						url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    toolbar: '#bldj_toolbar',
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
					<th data-options="field:'cz',formatter:doFileterBldj" >操作</th>
				</thead>
			</table>
		</form>
	</div>
	
	
	<script type="text/javascript">
	
		function doFileterBldj(value,row,index){
			var downloadDom = "<a href='${basePath}contractInfoController/download.action?path="+row.fileUrl+"'><font color='blue'>下载</font></a> | ";
			var editDom = "<a href='javascript:void(0)' onclick='editFileDi("+row.pid+",\""+row.fileDesc+"\",1,\""+row.fileProperty+"\")'><font color='blue'>编辑</font></a> | ";
			var reload = "<a href='javascript:void(0)' onclick='editFileDi("+row.pid+",\""+row.fileDesc+"\",2,\""+row.fileProperty+"\")'><font color='blue'>重新上传</font></a> | ";
			var projectDom = "<a href='javascript:void(0)' onclick='delFileDi("+row.pid+")'><font color='blue'> 删除</font></a>";
			return downloadDom+editDom+reload+projectDom;
			//var ss=$('#baseId').val();
			//${'#pid'}.val(ss);
		}

	</script>
