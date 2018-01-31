<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<div>
	<div class="fitem" style="">
		<div class="easyui-panel" style="border: 0;">
       <font color="red">*</font>物业租金（月）：
       		<input type="hidden"  name="projectProperty.pid" value="${project.projectProperty.pid }"  />
		<input id="projectProperty.houseRentTotal" <c:if test="${project.foreclosureStatus > 1}">readonly="readonly"</c:if> name="projectProperty.houseRentTotal" value="${project.projectProperty.houseRentTotal }" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>元
		<div id="estate_toolbar" style="border-bottom: 0; padding-bottom: 10px;">
				<c:choose>			
					<c:when test="${project.foreclosureStatus <= 1 }">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="toAddEstate()">新增</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="toEditEstate()">修改</a>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<table id="estate_grid" data-options="url: '',method: 'post',rownumbers: true,fitColumns:true,singleSelect: true,toolbar: '#estate_toolbar', pagination: false"
			style="width: 1000px;height: auto;" fitColumns="true" class="easyui-datagrid">
			 <thead>
					<tr>
						<th data-options="field:'houseId',hidden:'true'"></th>
					    <th data-options="field:'housePropertyCard'" align="center" halign="center" >产权证编号</th>
						<th data-options="field:'address',formatter:getFullAddress" align="center" halign="center">地址</th>
					    <th data-options="field:'cz',formatter:estate_caozuofiter" align="center" halign="center">操作</th>
					</tr>
			</thead>
		</table>
	</div>
</div>





<!-- 保存/关闭按钮 -->
<div id="estate-buttons_edit">
	<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveEstate()">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#estate_dialog').dialog('close')">关闭</a>
</div>


<script>


/**
 * 操作按钮
 */
function estate_caozuofiter(value,row,index){
	var status="${project.foreclosureStatus}";
	var showType="${showType}";
	if(status>1 || showType==2){
		var dataDelDom = "<a href='javascript:void(0)' class='consumerDelete'  onclick=''><font color='gray'>删除</font></a>";
	}else{
		var dataDelDom = "<a href='javascript:void(0)' class='consumerDelete'  onclick='delEstate("+row.houseId+")'><font color='blue'>删除</font></a>";
	}
	return dataDelDom;
}

/**
 * 返回物业全名称
 */
function getFullAddress(value,row,index){
	return row.houseAddress+row.houseName;
}

/**
 * 删除联系人信息
 */
function delEstate(houseIds){
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}beforeLoanController/delPledge.action",
	        data:{"houseIds":houseIds},
	        dataType: "json",
	        success: function(ret){
	        	//var ret = eval("("+data+")");
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					//重新加载
					loadEstateInfo("");
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	}
}
//新增抵押物
function toAddEstate(){
	$('#estate_form').form('reset');
	setAreaCombobox("live_province_code","1","","");
	setAreaCombobox("live_city_code","2","","");
	setAreaCombobox("live_district_code","3","","");
	estateContactDialog(1);
}
//修改抵押物
function toEditEstate(){
	var row = $('#estate_grid').datagrid('getSelections');
 	if (row.length == 1) {
 		$('#estate_form').form('reset');
 		var houseProvinceCode = row[0].houseProvinceCode;//省编码
 		var houseCityCode = row[0].houseCityCode;//市编码
 		var houseDistrictCode = row[0].houseDistrictCode;//区编码
 		if(houseProvinceCode == null ){
 			houseProvinceCode = "";
 		}
 		if(houseCityCode == null ){
 			houseCityCode = "";
 		}
 		if(houseDistrictCode == null ){
 			houseDistrictCode = "";
 		}
 		setAreaCombobox("live_province_code","1","",houseProvinceCode);
 		setAreaCombobox("live_city_code","2",houseProvinceCode,houseCityCode);
 		setAreaCombobox("live_district_code","3",houseCityCode,houseDistrictCode);
 		$("#estate_form").initForm(row[0]);
 		estateContactDialog(2);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
	
	
$(document).ready(function(){
	
	//居住地址省市区
  	$("#live_province_code").combobox({ 
		onSelect:function(){
        	var provinceCode = $("#live_province_code").combobox("getValue");
        	console.log("provinceCode:"+provinceCode);
        	if(provinceCode != ""){
        		setAreaCombobox("live_city_code","2",provinceCode,"");
        		setAreaCombobox("live_district_code","3","0","");
    		}
        }
	}); 
	$("#live_city_code").combobox({ 
		onSelect:function(){
        	var cityCode = $("#live_city_code").combobox("getValue");
        	if(cityCode != ""){
        		setAreaCombobox("live_district_code","3",cityCode,"");
    		}
        }
	});
	//获取物业信息
	var projectId = ${project.pid};
	if(projectId){
		//$("#house_info").datagrid
		var url = "<%=basePath%>beforeLoanController/getHouseByProjectId.action?projectId="+projectId;
		$('#estate_grid').datagrid('options').url = url;
		$('#estate_grid').datagrid('reload');		
	}
	
});
	
function estateContactDialog(type){
	//获取物业信息
	var projectId = ${project.pid};
	if(projectId){
		$('#estate_form input[name="projectId"]').val(projectId);
	}
	if(type == 1)$('#estate_form input[name="houseId"]').val("");
	var title = type == 1 ? "新增物业信息" : "修改物业信息";
	
	$("#estate_dialog").dialog({
		 title: title,
		 width: 650,
               modal: true,
               top: 200,
	});
	
	$("#estate_dialog").dialog("open").dialog("center");
}

//保存物业信息
function saveEstate(){
	$('#estate_form').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);			
				var houseId = ret.header["houseId"];//返回的项目联系人ID
				//重新加载物业信息
				loadEstateInfo(houseId);
				//关闭弹窗
				$("#estate_dialog").dialog("close");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}


/**
 * 重新加载联系人信息
 */
function loadEstateInfo(houseId){
	
	var rows = $('#estate_grid').datagrid('getRows');
	// 联系人信息
	var houseIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		houseIds += row.houseId+",";
	  	}
	}
	houseIds+=houseId;
	// 重新加载联系人信息
	var url = "<%=basePath%>beforeLoanController/getHouseList.action?houseIds="+houseIds;
	$('#estate_grid').datagrid('options').url = url;
	$('#estate_grid').datagrid('reload');
}
	
</script>
