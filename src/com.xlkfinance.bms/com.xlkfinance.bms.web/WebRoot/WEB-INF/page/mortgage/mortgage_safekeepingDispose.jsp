<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<div id="dlg_buttons_bgcl">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMortgageOperation(1,'#bgclForm','#dlg_bgcl')">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_bgcl').dialog('close')">关闭</a>
	</div>
	
	<%-- 抵质押物保管处理 --%>
	<div id="dlg_bgcl" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_bgcl" 
	 	style="width:700px;height:400px;padding: 10px;" data-options="modal:true" closed="true"  >
		<h4>抵质押物保管处理</h4>
		<hr />
		<form id="bgclForm" method="post" >
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
						<input id="objectLocation_bgcl" type="text" class="no_frame text_style" disabled="true" style="width: 510px;" />
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
					<td class="label_right"><font color="red">*</font>保管时间：</td>
					<td colspan="3">
						<input name="saveDttm" class="easyui-datetimebox" style="width: 200px;" editable="false" required="true" />
					</td>
				</tr>
				<tr>
					<td class="label_right">保管备注：</td>
					<td colspan="3">
						<input name="saveRemark" class="easyui-textbox" style="width:510px;height:60px" data-options="multiline:true" />
					</td>
				</tr>
			</table>
		</form>
	</div>
