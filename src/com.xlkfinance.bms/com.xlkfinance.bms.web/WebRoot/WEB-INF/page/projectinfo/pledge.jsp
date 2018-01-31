<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<div>
	<div class="fitem" style="">
		<div class="easyui-panel" style="border: 0;">
			<div id="pledge_toolbar" style="border-bottom: 0; padding-bottom: 10px;">
				<!-- type:1 评估 default 新增修改 -->
				<c:choose>
					<c:when test="${project.foreclosureStatus ==  2}">
						<a href="javascript:void(0)" id="houseAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="toEval()">抵押物评估</a>
					</c:when>
					<c:when test="${project.foreclosureStatus <= 1 }">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="toAdd()">添加抵押物</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="toEdit()">修改抵押物</a>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<table id="pledge_grid" data-options="url: '',method: 'post',rownumbers: true,fitColumns:true,singleSelect: true,toolbar: '#pledge_toolbar', pagination: false"
			style="width: 1000px;height: auto;" fitColumns="true" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'pid',hidden:'true'"></th>
				    <th data-options="field:'houseName'" align="center" halign="center" >物业名称</th>
					<th data-options="field:'houseAddress'" align="center" halign="center">物业城市</th>
				    <th data-options="field:'landUse'" align="center" halign="center">土地用途</th>
				    <th data-options="field:'estateUse'" align="center" halign="center">房产用途</th>
				    <th data-options="field:'propertyLife'" align="center" halign="center">产权年限</th>
				    <th data-options="field:'houseAge'" align="center" halign="center">房龄</th>
				    <th data-options="field:'landSurplusLife'" align="center" halign="center">土地剩余年限</th>
				    <th data-options="field:'area'" align="center" halign="center">建筑面积</th>
				    <th data-options="field:'useArea'" align="center" halign="center">室内面积</th>
				    <th data-options="field:'housePropertyCard'" align="center" halign="center">房产证号</th>
				    <th data-options="field:'costMoney',formatter:formatMoney" align="center" halign="center">登记价格</th>
				    <th data-options="field:'evaluationPrice',formatter:formatMoney" align="center" halign="center">最终房屋评估价</th>
				    <th data-options="field:'pledgelist_talbe_op',formatter:caozuofiter" align="center" halign="center">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
	
<script>

/* 查看评估详情 */
function showEval(index){
	var row = $('#pledge_grid').datagrid('getData').rows[index];
	openEvalDialog(2,row);
}

/* 评估 */
function toEval(){
	
	var row = $('#pledge_grid').datagrid('getSelections');
 	if (row.length == 1) {
		$("#pledge_eval_form").find("input[name='estate']").val(row[0].houseId);
 		$('#pledge_form').form('reset');
 		openEvalDialog(1,row[0]);			 		
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能选中一条数据进行编辑,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
}

function openEvalDialog(type,data){
	
	var projectId = $("#personalForm input[name='pid']").val();
	if(projectId){
		$('#pledge_eval_form input[name="projectId"]').val(projectId);
	}
	
	if($("#pledge_eval_form").attr("houseId") != data.houseId){
		$("#pledge_eval_form").form('reset');
		$("#pledge_eval_form").attr("houseId",data.houseId);
		//获得评估价列表
		$.ajax({
			type: "POST",
	        url: "${basePath}projectInfoController/getEvalDictList.action",
	        data:{"estate":data.houseId},
	        dataType: "json",
	        success: function(ret){
	        	if(ret.total){
	        		renderEvalList(ret);
	        	}
	        }
		});
	}
	
	if(type == 2){
		$("#dlg-buttons_eval_save").linkbutton("disable");
	}else{
		$("#dlg-buttons_eval_save").linkbutton("enable");
	}
	
	$("#dialog_eval").dialog({
		 title: "抵押物评估",
		 width: 650,
     modal: true,
     top: 200,
	});
	
	$("#dialog_eval").dialog("open").dialog("center");
}

/**
 * 拆分数组
 */
var chunk = function(arr, num){
	  num = num*1 || 1;
	  var ret = [];
	  arr.forEach(function(item, i){
	    if(i % num === 0){
	      ret.push([]);
	    }
	    ret[ret.length - 1].push(item);
	  });
	  return ret;
	};
/**
 * 渲染评估列表
 */
var renderEvalList = function(ret){
	var list = chunk(ret.rows,2);
	var $tbody = $("#pledge_eval_form").find("tbody");
	var $finalPrice = $("#final_eaval_price");
	var trStr = "<tr>";
	var lableStr = "最终房屋评估价  = ";
	var step = 0;
	var finalEvalPrice = 0.00;
	for(var i = 0; i< list.length;i++){
		for(var j=0;j<list[i].length;j++){
			var tdStr = "<td class='label_right'><font color='red'>*</font>"+ list[i][j].evaluationSource +"</td>" +
						"<td><input type='hidden' name='evaluations["+step+"].pid' value='"+list[i][j].pid+"'>"
						+"<input type='hidden' name='evaluations["+step+"].evaluationProportion' value='"+list[i][j].evaluationProportion+"'>"
						+"<input type='hidden' name='evaluations["+step+"].evaluationSource' value='"+list[i][j].evaluationSource+"'>"
						+"<input style='width: 110px;' id='eval_input_"+i+j+"' "
						+" name='evaluations["+step+"].evaluationPrice' "
						+" value='"+ list[i][j].evaluationPrice +"'"
						+" rate='"+ list[i][j].evaluationProportion +"'  class='easyui-numberbox' "
						+" data-options=\"required:true,min:0,max:9999999999,precision:2,groupSeparator:','\"/>元</td>";
			
			trStr += tdStr;
			step ++;
			lableStr += list[i][j].evaluationSource + "*" + list[i][j].evaluationProportion + "%"
			finalEvalPrice += parseFloat(list[i][j].evaluationPrice * list[i][j].evaluationProportion / 100);
		}
		if(i != list.length -1){
			trStr += "</tr><tr>";
			lableStr += "+";
		}
	}
	trStr += "</tr>";
	var $alltr = $(trStr);
	$tbody.find("tr").not($("tr.eval_split_line")).detach();
	$tbody.prepend($alltr);
	
	$finalPrice.html(lableStr);
	
	finalEvalPrice = finalEvalPrice.toFixed(2);
	$("#evaluationPrice").numberbox("setValue",finalEvalPrice);
	
	//预计下户时间
	if(ret.shouldSpotTime){
		$("#pledge_eval_form").find("input[id='shouldSpotTime']").datebox("setValue",ret.shouldSpotTime);	
	}
	
	$alltr.find("input[id*='eval_input_']").each(function(){
		$(this).numberbox({
			onChange: function(){
				var sum = 0;
				$("input[id*='eval_input_']").each(function(){
					var val = $(this).numberbox("getValue");
					if(val == "")return true;
					var rate = $(this).attr("rate");
					sum += parseFloat(val * rate)/100;
				});
				
				$("#evaluationPrice").numberbox("setValue",sum);
			}
		});
	});
}

/* End 评估 */

/**
 * 抵押物列表操作按钮
 */
function caozuofiter(value,row,index){
	if(workflowTaskDefKey != "task_MortgageLoanRequest"){
		var dataDelDom = "<a class='download' onclick='showEval("+index+")'><font color='blue'>评估详情</font></a>";
	}else{
		var dataDelDom = "<a href='javascript:void(0)' class='download'  onclick='delpPledge("+row.houseId+")'><font color='blue'>删除</font></a>";
	}
	return dataDelDom;
}

/**
 * 删除物业信息
 */
function delpPledge(houseId){
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}beforeLoanController/delPledge.action",
	        data:{"houseIds":houseId},
	        dataType: "json",
	        success: function(ret){
	        	//var ret = eval("("+data+")");
	        	if(ret && ret.header["success"]){
					// 操作提示
					$.messager.alert('操作提示',ret.header["msg"]);
					//重新加载
					loadHouseInfo("");
	        	}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');
				}
	        }
		});
	}
}
//新增抵押物
function toAdd(){
	$('#pledge_form').form('reset');
	setAreaCombobox("live_province_code","1","","");
	setAreaCombobox("live_city_code","2","","");
	setAreaCombobox("live_district_code","3","","");
	PopPledgeDialog(1);
}
//修改抵押物
function toEdit(){
	var row = $('#pledge_grid').datagrid('getSelections');
 	if (row.length == 1) {
 		$('#pledge_form').form('reset');
 		
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
			 		
 		$("#pledge_form").initForm(row[0]);
 		PopPledgeDialog(2);
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
		$('#pledge_grid').datagrid('options').url = url;
		$('#pledge_grid').datagrid('reload');		
	}
	
});
	
function PopPledgeDialog(type){
	var projectId = $("#personalForm input[name='pid']").val();
	if(projectId){
		$('#pledge_form input[name="projectId"]').val(projectId);
	}
	if(type == 1)$('#pledge_form input[name="houseId"]').val("");
	var title = type == 1 ? "新增抵押物" : "修改抵押物";
	
	$("#dialog_edit").dialog({
		 title: title,
		 width: 850,
               modal: true,
               top: 200,
	});
	
	$("#dialog_edit").dialog("open").dialog("center");
}

//保存物业信息
function savePledge(){
	$('#pledge_form').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				// 关闭窗口
				$('#add_house').dialog('close');
				var houseId = ret.header["houseId"];//返回的物业ID
				//重新加载物业信息
				loadHouseInfo(houseId);
				//关闭弹窗
				$("#dialog_edit").dialog("close");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}

//进行评估
function saveEval(){
	$('#pledge_eval_form').form('submit', {
		onSubmit : function() {
				return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
				// 关闭窗口
				$('#dialog_eval').dialog('close');
				var houseId = ret.header["houseId"];//返回的物业ID
				//重新加载物业信息
				//loadHouseInfo(houseId);
				$("#pledge_grid").datagrid("reload");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}

/**
 * 重新加载抵押物信息
 */
function loadHouseInfo(houseId){
	
	var rows = $('#pledge_grid').datagrid('getRows');
	// 物业信息
	var houseIds = "";
	if(rows.length > 0){
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		houseIds += row.houseId+",";
	  	}
	}
	houseIds+=houseId;
	// 重新加载物业信息
	var url = "<%=basePath%>beforeLoanController/getHouseList.action?houseIds="+houseIds;
	$('#pledge_grid').datagrid('options').url = url;
	$('#pledge_grid').datagrid('reload');
}
	
</script>
