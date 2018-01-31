<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<!-- 弹窗窗口 -->

<!-- 新增/修改 弹窗 -->
<div id="dialog_edit" class="easyui-dialog" buttons="#dlg-buttons_edit" closed="true">
	<form id="contact_form" method="post" action="<%=basePath%>consumeProjectInfoController/modContacts.action">
		<table style="width: 98%;">
			<tbody>
				<tr>
					<input name="pid" type="hidden"/>
					<input type="hidden" name="projectId" value="${project.pid}">
					<input type="hidden" name="acctId" value="${project.acctId}">
					
					
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>联系人：</td>
					<td>
						<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="contactsName" value="" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>联系电话：</td>
					<td style="width: 165px">
						<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="contactsPhone" value="" class="easyui-textbox" data-options="required:true,validType:'telephone'"/>
					</td> 
				</tr>
				<tr>
					<td class="label_right"><font color="red">*</font>关系：</td>
					<td>
						<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="contactsRalation" class="easyui-combobox" editable="false" panelHeight="auto" value=""
							data-options="validType:'selrequired',valueField:'lookupDesc',textField:'lookupDesc',
								url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=CONTACT_RELATION'" />
					</td>
				</tr>
				
				
			</tbody>
		</table>
	</form>
</div>


<!-- 新增/修改 弹窗 -->
<div id="estate_dialog" class="easyui-dialog" buttons="#estate-buttons_edit" closed="true">
	<form id="estate_form" method="post" action="<%=basePath%>beforeLoanController/savePledge.action">
		<table class="beforeloanTable">
			<tbody>
				<tr>
					<input name="houseId" type="hidden"/>
					<input type="hidden" name="projectId" value="${project.pid}">
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>产权证编号：</td>
					<td colspan="3">
						<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="housePropertyCard" value="" class="easyui-textbox" data-options="required:true,validType:'length[1,20]'"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>地址：</td>
					<td>
					   	<select id="live_province_code" name="houseProvinceCode" class="easyui-combobox" data-options="required:true,validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td>
					   	<select id="live_city_code" name="houseCityCode" class="easyui-combobox" data-options="required:true,validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
					<td>
					   	<select id="live_district_code" name="houseDistrictCode" class="easyui-combobox" data-options="required:true,validType:'selrequired'" editable="false" style="width:129px;" />
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>详细地址：</td>
					<td colspan="3">
						<input <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="houseName" value="" class="easyui-textbox" data-options="required:true,validType:'length[1,50]'" style="width: 392px;"/>
                    </td>
					
				</tr>
				
				
			</tbody>
		</table>
	</form>
</div>

<!-- End 新增/修改 弹窗  -->			
