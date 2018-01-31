<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<!-- 弹窗窗口 -->

<!-- 评估 弹窗 -->
<div id="dialog_eval" class="easyui-dialog" buttons="#dlg-buttons_eval" closed="true">
	<form id="pledge_eval_form" method="post" action="<%=basePath%>projectInfoController/saveEvaluation.action">
		<!-- 抵押物ID/项目ID -->
		<input type="hidden" name="estate">
		<input type="hidden" name="projectId" value="${project.pid}">
		
		<table style="width: 100%;">
			<tbody>
				
				<tr class="eval_split_line">
					<td colspan="4" align="center" style="padding: 10px 0; border-width:1px;
					border-color:#999; border-bottom-style:dashed; border-top-style: dashed;">
						<div>
							<!-- 最终房屋评估价  = -->
							<label id="final_eaval_price"></label>
							<div style="text-align: left;padding-left: 102px;">
								<input style="width: 110px;" readonly name="evaluationPrice" id="evaluationPrice" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"/>元
							</div>
						</div>
					</td>
				</tr>
				<tr class="eval_split_line">
					<td class="label_right"><font color="red">*</font>预计下户时间:</td>
					<td>
						<input class="easyui-datebox" id="shouldSpotTime" name="shouldSpotTime" editable=false data-options="required:true"/>
					</td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<!-- 评估按钮 -->
<div id="dlg-buttons_eval">
	<a id="dlg-buttons_eval_save" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEval()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_eval').dialog('close')">关闭</a>
</div>

<!-- End 新增/修改 弹窗  -->	
		
<!-- 新增/修改 弹窗 -->
<div id="dialog_edit" class="easyui-dialog" buttons="#dlg-buttons_edit" closed="true">
	<form id="pledge_form" method="post" action="<%=basePath%>beforeLoanController/savePledge.action">
		<table style="width: 98%;">
			<tbody>
				<tr>
					<td class="label_right1"><font color="red">*</font>物业名称:</td>
					<td colspan="5">
						<input style="width: 70%" name="houseName" id="houseName" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'"/>
						<input name="houseId" type="hidden"/>
						<input type="hidden" name="projectId" value="${project.pid}">
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>物业城市:</td>
					<td>
					   	<select id="live_province_code" name="houseProvinceCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td>
					   	<select id="live_city_code" name="houseCityCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td>
					   	<select id="live_district_code" name="houseDistrictCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>土地用途:</td>
					<td><input name="landUse" id="landUse" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'"/></td>
					<td class="label_right1"><font color="red">*</font>房产用途:</td>
					<td><input name="estateUse" id="estateUse" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'"/></td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>产权年限:</td>
					<td><input name="propertyLife" id="propertyLife" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/></td>
					<td class="label_right1"><font color="red">*</font>房龄:</td>
					<td><input name="houseAge" id="houseAge" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/></td>
					<td class="label_right1"><font color="red">*</font>土地剩余年限:</td>
					<td><input name="landSurplusLife" id="landSurplusLife" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/></td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>建筑面积:</td>
					<td><input name="area" style="width: 129px;"  id="area" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2"/><span>㎡</span></td>
					<td class="label_right1"><font color="red">*</font>室内面积:</td>
					<td><input name="useArea" style="width: 129px;" id="useArea" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2"/>㎡</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>房产证号:</td>
					<td><input name="housePropertyCard" id="housePropertyCard" class="easyui-textbox" data-options="required:true,validType:'length[0,50]'"/></td>
					<td class="label_right1"><font color="red">*</font>登记价格:</td>					
					<td><input name="costMoney" style="width: 129px;" id="costMoney" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>元</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<!-- 保存/关闭按钮 -->
<div id="dlg-buttons_edit">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="savePledge()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_edit').dialog('close')">关闭</a>
</div>

<!-- End 新增/修改 弹窗  -->			
