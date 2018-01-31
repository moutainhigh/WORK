<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/config.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!-- 尽职调查报告 -->
<form action="<%=basePath%>projectInfoController/saveSpotInfo.action" id="spotInfoForm" name="surveyReportInfo" method="post" >
	<div style="padding: 30px 10px 0 10px;width: 1039px;">
		
			<div class="fitem">
				<c:if test="${projectSpotInfoList != null && fn:length(projectSpotInfoList) > 0}">
					<c:forEach items="${projectSpotInfoList}" var="item" varStatus="status">
						<div style="padding: 10px 0 0 0;">
							<div class="easyui-panel" title="${item.houseName} - - 下户调查报告" data-options="collapsible:true">
								<div style="padding: 10px ;height: auto;line-height: 35px;">
									<table class="" style="border-bottom: 1px #ddd dashed;width: 100%;">
										<tbody>
											<tr>
												<td class="label_right" style="width: 108px;">预计下户时间:</td>
												<td>
													
													<input type="hidden" name="spotInfos[${status.index}].projectId" value="${projectId}" />
													<input type="hidden" name="spotInfos[${status.index}].eastateId" value="${item.houseId}">
													<c:if test="${not empty item.bizSpotInfo && item.bizSpotInfo.pid != 0}">
														<input type="hidden" name="spotInfos[${status.index}].pid" value="${item.bizSpotInfo.pid}" />
													</c:if>
													<c:if test="${not empty item.bizSpotInfo && not empty item.bizSpotInfo.shouldSpotTime}">
														<!-- name="spotInfos[${status.index}].shouldSpotTime" -->
														<input value="${item.bizSpotInfo.shouldSpotTime}" readonly class="easyui-datebox" data-options=""/>
													</c:if>
												</td>
												<td class="label_right required">实际下户时间:</td>
												<td>
													<input <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> name="spotInfos[${status.index}].actualSpotTime" class="easyui-datebox" data-options="required:true,editable:false" value="${item.bizSpotInfo.actualSpotTime}"/>
												</td>
											</tr>
										</tbody>
									</table>
									<table style="border-bottom: 1px #ddd dashed;width: 100%;">
										<tbody>
											<tr>
												<td class="label_right required" style="width: 108px;">装修情况:</td>
												<td>	
													<input id="spotInfo_form_decorationSituation_${status.index}" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> name="spotInfos[${status.index}].decorationSituation" class="easyui-combobox" editable="false" panelHeight="auto" value="${item.bizSpotInfo.decorationSituation }"/>
												</td>
												<td class="label_right required">小区总栋数:</td>
												<td>
													<input name="spotInfos[${status.index}].houseTotals" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0" value="${item.bizSpotInfo.houseTotals}" />
												</td>
												<td class="label_right required">总楼层数:</td>
												<td>
													<input name="spotInfos[${status.index}].floorTotals" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0" value="${item.bizSpotInfo.floorTotals}"/>
												</td>
											</tr>
											<tr>
												<td class="label_right required" style="width: 108px;">结构:</td>
												<td>
													<label><input style="width: auto;" class="easyui-validatebox" validType="inputRadio[':input[name=&#34;spotInfos[${status.index}].houseStructure&#34;]']"  name="spotInfos[${status.index}].houseStructure" <c:if test="${foreclosureStatus != 3}">disabled="disabled"</c:if> type="radio" value="电梯" <c:if test="${item.bizSpotInfo.houseStructure == '电梯' }">checked = 'checked'</c:if> />电梯</label>
													<label><input style="width: auto;" class="easyui-validatebox" name="spotInfos[${status.index}].houseStructure" <c:if test="${foreclosureStatus != 3}">disabled="disabled"</c:if> type="radio" value="楼梯" <c:if test="${item.bizSpotInfo.houseStructure == '楼梯' }">checked = 'checked'</c:if> />楼梯</label>
												</td>
												<td class="label_right">电梯数:</td>
												<td>
													<div>
														<input name="spotInfos[${status.index}].elevatorsNums" style="width: 50px;" class="easyui-numberbox" data-options="min:0,max:99,precision:0" value="${item.bizSpotInfo.elevatorsNums}"/>
														梯
														<input name="spotInfos[${status.index}].householdNums" style="width: 50px;" class="easyui-numberbox" data-options="min:0,max:99,precision:0" value="${item.bizSpotInfo.householdNums}"/>
														户
													</div>
												</td>
											</tr>
											<tr>
												<td class="label_right1" style="width: 108px;"><font class="red">*</font>房产格局:</td>
												<td colspan="4">
													<input id="spotInfo_housePattern_${status.index}" 
													 <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if>
													 name="spotInfos[${status.index}].housePattern" class="easyui-combobox"
													 editable="false" panelHeight="auto" detail='${item.bizSpotInfo.housePatternDetail}' value="${item.bizSpotInfo.housePattern}"/>
													 
													<div class="housePatternDetail none table-inline">
														<input id="spotInfo_housePatternDetail_${status.index}" style="width: 120px;"
														 <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if>
														 class="easyui-combobox" panelHeight="auto" panelHeight="auto"/>
													</div>
													<div class="housePatternDetail1 none table-inline">
														<input style="width: 120px;"
														 <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if>
														 name="spotInfos[${status.index}].housePatternDetail" class="easyui-textbox" value="${item.bizSpotInfo.housePatternDetail}"
														 panelHeight="auto" panelHeight="auto"/>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
									<table style="border-bottom: 1px #ddd dashed;width: 100%; padding-bottom:10px; ">
										<tbody>
											<tr>
												<td class="label_right1"><font class="red">*</font>商场数(个):</td>
												<td>
													<input name="spotInfos[${status.index}].shoppingNums" value="${item.bizSpotInfo.shoppingNums}" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/>
												</td>
												<td class="label_right"><font class="red">*</font>学校数(所):</td>
												<td>
													<input name="spotInfos[${status.index}].schoolNums" value="${item.bizSpotInfo.schoolNums}" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/>
												</td>
												<td class="label_right"><font class="red">*</font>医院数(所):</td>
												<td>
													<input name="spotInfos[${status.index}].hospitalNums" value="${item.bizSpotInfo.hospitalNums}" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/>
												</td>
												<td class="label_right1"><font class="red">*</font>银行数(家):</td>
												<td>
													<input name="spotInfos[${status.index}].bankNums" value="${item.bizSpotInfo.bankNums}" class="easyui-numberbox" data-options="required:true,min:0,max:99,precision:0"/>
												</td>
											</tr>
											<tr>
												<td class="label_right1"><font class="red">*</font>交通情况:</td>
												<td colspan="7">
													<input id="spotInfo_form_trafficSituation_${status.index}" type="hidden" value="${item.bizSpotInfo.trafficSituation}" />
													<label data="公交"><input style="width: auto;" class="easyui-validatebox" validType="checkBox[':input[name=&#34;spotInfos[${status.index}].trafficSituation&#34;]']" name="spotInfos[${status.index}].trafficSituation" type="checkbox" value="公交" />公交</label>
													<label data="地铁"><input style="width: auto;" class="easyui-validatebox" name="spotInfos[${status.index}].trafficSituation" type="checkbox"  value="地铁" />地铁</label>
													<label data="火车站"><input style="width: auto;" class="easyui-validatebox" name="spotInfos[${status.index}].trafficSituation" type="checkbox" value="火车站" />火车站</label>
													<label data="飞机场"><input style="width: auto;" class="easyui-validatebox" name="spotInfos[${status.index}].trafficSituation" type="checkbox" value="飞机场" />飞机场</label>
													<label data="港口"><input style="width: auto;" class="easyui-validatebox" name="spotInfos[${status.index}].trafficSituation" type="checkbox" value="港口" />港口</label>
												</td>
											</tr>
											<tr>
												<td class="label_right1 required">周边环境综合描述:</td>
												<td colspan="7">
													<input name="spotInfos[${status.index}].aroundEnvironmental" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> value="${item.bizSpotInfo.aroundEnvironmental}" class="easyui-textbox" data-options="multiline:true,validType:'length[1,500]'" style="width: 560px;height: 50px;"/>
												</td>
											</tr>
										</tbody>
									</table>
									<table style="width: 100%;padding-bottom: 10px;">
										<tbody>
											<tr>
												<td class="label_right required">中介单价:</td>
												<td>
													<input name="spotInfos[${status.index}].agencyPrice" value="${item.bizSpotInfo.agencyPrice}" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
													元/㎡
												</td>
												<td class="label_right required">总价值:</td>
												<td>
													<input name="spotInfos[${status.index}].totalPrice" value="${item.bizSpotInfo.totalPrice}" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"/>
													元
												</td>
											</tr>
											<tr>
												<td class="label_right required">使用情况:</td>
												<td>
													<input id="spotInfo_form_useSituation_${status.index}" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> name="spotInfos[${status.index}].useSituation" class="easyui-combobox" editable="false" panelHeight="auto" value="${item.bizSpotInfo.useSituation }"/>
												</td>
												<td class="label_right required">实际用途:</td>
												<td>
													<input id="spotInfo_form_actualPurpose_${status.index}" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> name="spotInfos[${status.index}].actualPurpose" class="easyui-combobox" editable="false" panelHeight="auto" value="${item.bizSpotInfo.actualPurpose }"/>
												</td>
											</tr>
											<tr>
												<td class="label_right required">使用人:</td>
												<td colspan="3">
													<input id="spotInfo_form_userType_${status.index}" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> name="spotInfos[${status.index}].userType" class="easyui-combobox" editable="false" panelHeight="auto" value="${item.bizSpotInfo.userType }"/>
													<div class="userSituation_container" style="display:inline-flex;">
														<div class="item1 none">
															<input class="easyui-textbox userSituation" name="spotInfos[${status.index}].userSituation" data-options="validType:'length[1,50]'" style="width: 250px;" value="${item.bizSpotInfo.userSituation}">
														</div>
														<div class="item2 none" data-potions="checkbox">
															<label data="权利人"><input onclick="changeUserSituation(this)" type="checkbox" value="权利人"/>权利人</label>
															<label data="父母"><input onclick="changeUserSituation(this)" type="checkbox" value="父母"/>父母</label>
															<label data="子女"><input onclick="changeUserSituation(this)" type="checkbox" value="子女"/>子女</label>
															<label data="亲戚"><input onclick="changeUserSituation(this)" type="checkbox" value="亲戚"/>亲戚</label>
															<label data="承租人"><input onclick="changeUserSituation(this)" type="checkbox" value="承租人" />承租人</label>
															<input name="spotInfos[${status.index}].leaseUse" value="${item.bizSpotInfo.leaseUse}" class="easyui-textbox" style="width: 290px;" data-options="validType:'length[1,50]'"/>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<td class="label_right">租赁时间:</td>
												<td colspan="3">
													<input name="spotInfos[${status.index}].leaseTimeStart" <c:if test="${foreclosureStatus != 3}">disabled="disabled"</c:if> value="${item.bizSpotInfo.leaseTimeStart}"  class="easyui-datebox startDate" editable="false" />
													~
													<input name="spotInfos[${status.index}].leaseTimeEnd" <c:if test="${foreclosureStatus != 3}">disabled="disabled"</c:if> value="${item.bizSpotInfo.leaseTimeEnd}" class="easyui-datebox endDate" editable="false" />
												</td>
											</tr>
											<tr>
												<td class="label_right">租金缴交方式:</td>
												<td>
													<input id="spotInfo_form_zujin_${status.index}" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> name="spotInfos[${status.index}].rentPaymentWay" class="easyui-combobox rentPaymentWay" editable="false" panelHeight="auto" value="${item.bizSpotInfo.rentPaymentWay }"/>
												</td>
											</tr>
											<tr>
												<td class="label_right">承租人信息:</td>
												<td colspan="3">
													<input name="spotInfos[${status.index}].lessee" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> class="easyui-textbox lessee" value="${item.bizSpotInfo.lessee}" data-options="multiline:true,validType:'length[1,500]'" style="height: 50px;width: 560px;"/>
												</td>
											</tr>
											<tr>
												<td class="label_right" style="width: 108px;">其他实察情况说明:</td>
												<td colspan="3">
													<input name="spotInfos[${status.index}].othenRemark" <c:if test="${foreclosureStatus != 3}">readonly="readonly"</c:if> value="${item.bizSpotInfo.othenRemark}" class="easyui-textbox" data-options="multiline:true,validType:'length[1,500]'" style="height: 50px;width: 560px;"/>
												</td>
											</tr>
										</tbody>
									</table>
									<!-- 文件上传 -->
									<div style="" class="datum">
										<div id="spotinfo_files_toolbar_${status.index}" style=" background: #FFF;">
											<c:if test="${foreclosureStatus == 3}">
												<a class="easyui-linkbutton" data="<c:if test='${empty item.bizSpotInfo}'>0</c:if>${item.bizSpotInfo.pid}" data-index="${status.index}" iconCls="icon-add" onclick="addFileData(this)" plain="true">上传图片</a>
												<%-- <div class="uploadmutilFile" style="margin-top: 10px;">
											        <input type="file"  name="uploadify" data="<c:if test='${empty item.bizSpotInfo}'>0</c:if>${item.bizSpotInfo.pid}" data-index="${status.index}" id="uploadify_${status.index}"/> 
											    </div> --%>												
											</c:if>
										</div> 
										<table id="grid_data_file_${status.index}" data-index="${status.index}" title="资料上传列表" class="easyui-datagrid" style="overflow: hidden;"
											 data-options="
											    url: '${basePath}projectInfoController/getSpotFileListBySpotId.action',
											    method: 'post',
											    queryParams:{spotId:<c:if test="${empty item.bizSpotInfo}">0</c:if>${item.bizSpotInfo.pid}},
											    collapsible:true,
											    rownumbers: true,
											    singleSelect: true,
											    pagination: true,
											    toolbar: '#spotinfo_files_toolbar_${status.index}',
											    idField: 'dataId',
											    selectOnCheck:true,
												checkOnSelect: true,">
											<thead>
												<tr>
													<th data-options="checkbox:true,field:'dataId',width:30"></th>
													<th hidden="true" data-options="field:'pid'">
													<th data-options="field:'fileName',width:220" >图片</th>
												    <th data-options="field:'cz',width:160,formatter:spot_files_caozuofiter">操作</th><!-- formatter:caozuofiter -->
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:if>
			</div>
			<div id="">
				<div style="padding: 10px 10px 0 10px;">
					<div style="padding-bottom: 20px;padding-top: 10px;">
						<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveSpotInfo();" id="fore_spot_info_click">保存</a>
					</div>
				</div>
			</div>
		
	</div>
</form>

<div id="dialog_file_data2"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-file_buttons2">
	<form id="dialog_file_form" action="${basePath}/projectInfoController/uploadSpotFile.action" name="addOrupdate2" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
				<td>
					<input type="hidden" name="spotId">
					<input class="easyui-filebox" class="download" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" name="file" accept=".png,.jpg,.gif" style="width:240px;" required="true"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="data-file_buttons2">
	<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addFileDataSave(this)" >保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_file_data2').dialog('close')">取消</a>
</div>

<script type="text/javascript">

function addFileData(_this){
	$('#dialog_file_form').form('clear');
	var spotId = $(_this).attr("data");
	$('#dialog_file_form').attr("index",$(_this).attr("data-index"));
	$('#dialog_file_form').find("input[name='spotId']").val(spotId);
	$('#dialog_file_data2').dialog('open').dialog('setTitle','上传图片');
}

function addFileDataSave(_this){
	$(_this).linkbutton("disable");
	$('#dialog_file_form').form('submit', {
		onSubmit : function() {
			var flag = true
			if($(this).form('validate')){
				var filepath = $('#dialog_file_form').find("input[name='file']").val();
				var prefix = "." + filepath.replace(/.+\./, "");
				if(!prefix)flag = false;
				else{
					prefix = prefix.toLowerCase();
					flag = (prefix == ".png" || prefix == ".gif" ||prefix == ".jpg" || prefix == "jpeg");
				}
			}
			if(!flag){
				$.messager.alert('操作提示',"请上传图片文件!",'error');
			}
			if(!flag){
				$(_this).linkbutton("enable");
			}
			return flag; 
		},
		success : function(result) {
			$(_this).linkbutton("enable");
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$('#dialog_file_data2').dialog('close');
				// 操作提示
				var fileUploadindex = $('#dialog_file_form').attr("index");
				$.messager.alert('操作提示',ret.header["msg"]);
				$("#grid_data_file_" + fileUploadindex).datagrid("reload");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$(_this).linkbutton("enable");
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}

$(function(){
	
	//交通情况
	$("input[id*=spotInfo_form_trafficSituation_]").each(function(){
		var val = $(this).val();
		if(val){
			var valList = val.split(",");
			$(this).siblings("label").each(function(){
				for(var i =0;i<valList.length;i++){
					if($(this).attr("data") == valList[i]){
						$(this).children("input").attr("checked",true);
					}
				}
			});				
		}
	});
	
	
	var uploadFileCount = 0;
	setTimeout(function(){
		$("input[id*=uploadify_]").each(function(){
			var _this = this;
	       	var spotId = $(_this).attr("data");
			$(this).uploadify({  
		        'swf'       : '${ctx}/p/xlkfinance/plug-in/uploadify/uploadify.swf?ver'+Math.random(),  
		        'uploader'  : '${basePath}/projectInfoController/uploadSpotFile.action;jsessionid=${pageContext.session.id}',    
		        'queueID'        : 'fileQueue',
		        'cancelImg'      : '${ctx}/p/xlkfinance/plug-in/uploadify/css/img/uploadify-cancel.png',
		        'buttonText'     : '上传文件',
		        'auto'           : true, //设置true 自动上传 设置false还需要手动点击按钮 
		        'multi'          : false,  
		        'wmode'          : 'transparent',  
		        'simUploadLimit' : 999,  
		        'fileTypeExts'        : '*.gif; *.jpg; *.png',  
		        'fileTypeDesc'       : 'All Picture Files',
		        'method'       : 'get',
		        onUploadStart : function(settings){
		        	
		        	var formData = {"spotId":spotId};
		        	
	                $(_this).uploadify("settings","formData",formData ); 
		        },
				onUploadSuccess : function(file, data, response) {
					 uploadFileCount++;
					 var ret = eval("("+data+")");
					 if(ret && ret.header["success"]){
						// 重新加载
						var $grid = $("#grid_data_file_"+$(_this).attr("data-index"));
						$grid.datagrid('reload');
						// 操作提示
						$.messager.alert('操作提示',ret.header["msg"]);
					 }else{
						$.messager.alert('操作提示',ret.header["msg"],'error');
					 }
				},
				onQueueComplete : function(file) { //当队列中的所有文件全部完成上传时触发 
					//$.messager.alert("操作提示", "上传完成"+uploadFileCount+"个文件");
				}
			});
		});
	});
	
	function rederPatternDetail(_this,data,type){
		var selectShow = true; 
		
		var $housePatternDetail = $(_this).siblings("div.housePatternDetail").find("input:first");
		var $housePatternDetail1 = $(_this).siblings("div.housePatternDetail1").find("input:first");
		
		var this_val = $housePatternDetail1.val();
		
		if(data.lookupVal != 2){
			$(_this).siblings("div.housePatternDetail").removeClass("none").addClass("table-inline");
			$(_this).siblings("div.housePatternDetail1").addClass("none").removeClass("table-inline");
		}else{
			$(_this).siblings("div.housePatternDetail1").removeClass("none").addClass("table-inline");
			$(_this).siblings("div.housePatternDetail").addClass("none").removeClass("table-inline");
		}
		
		if(data.lookupVal == 1){//住宅
			$housePatternDetail.combobox({
				validType:'selrequired',
				valueField:'lookupVal',
				textField:'lookupDesc',
				editable:false,
				onSelect:function(data){
					$housePatternDetail1.textbox("setValue",data.lookupVal)
				},
				url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_HOUSE_IN_COUNT',
			});
		}else if(data.lookupVal == 2){//办公
			type == 1 ?$housePatternDetail1.textbox("setValue","") : $housePatternDetail1.textbox("setValue",this_val);
			$housePatternDetail1.textbox({editable:true,validType:['length[0,50]']}).textbox('enable');
			
			selectShow = false;
		}else if(data.lookupVal == 3){//商铺
			$housePatternDetail.combobox({
				validType:'selrequired',
				valueField:'lookupVal',
				textField:'lookupDesc',
				editable:false,
				onSelect:function(data){
					$housePatternDetail1.textbox("setValue",data.lookupVal)	
				},
				url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_HOUSE_IN_SHOP',
			});
		}
		
		if(data.lookupVal != 2) $housePatternDetail.combobox("setValue",this_val);
		
	}
	
	//房产格局
	$("input[id*=spotInfo_housePattern_]").each(function(){
		var _this = this;
		$(this).combobox({
			validType:'selrequired',
			valueField:'lookupVal',
			textField:'lookupDesc',
			url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_HOUSE_PATTERN',
			onSelect:function(data){
				rederPatternDetail(_this,data,1);
				$(_this).siblings("div.housePatternDetail").find("input:first").combobox("setValue","");
				$(_this).siblings("div.housePatternDetail").find("input:first").textbox("setValue","");
			},
			onLoadSuccess:function(data){
				var val = {lookupVal:$(_this).val()};
				rederPatternDetail(_this,val);
			}
		});
	});
	
	//装修情况
	$("input[id*=spotInfo_form_decorationSituation_]").each(function(){
		$(this).combobox({
			validType:'selrequired',
			valueField:'lookupVal',
			textField:'lookupDesc',
			url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_DECORATION_SITUATION',
		});
	});
	
	//渲染使用情况
	function renderUseSituation(_this,val){
		
		var $startDate = $(_this).closest("table").find("input.startDate");
		var $endDate = $(_this).closest("table").find("input.endDate");
		var $rentPaymentWay = $(_this).closest("table").find("input.rentPaymentWay");
		var $lessee = $(_this).closest("table").find("input.lessee");
		if(val == "2"){
			$startDate.datebox({required:true});
			$endDate.datebox({required:true});
			$rentPaymentWay.combobox({validType:'selrequired'});
			$lessee.textbox({required:true});
		}else{
			$startDate.datebox({required:false});
			$endDate.datebox({required:false});
			$rentPaymentWay.combobox({validType:false});
			$lessee.textbox({required:false});
		}
		$.parser.parse($startDate);
		$.parser.parse($endDate);
		$.parser.parse($rentPaymentWay);
		$.parser.parse($lessee);
	}
	
	//使用情况
	$("input[id*=spotInfo_form_useSituation_]").each(function(){
		var _this = this;
		$(this).combobox({
			validType:'selrequired',
			valueField:'lookupVal',
			textField:'lookupDesc',
			url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_USE_SITUATION',
			onSelect:function(data){
				renderUseSituation(_this,data.lookupVal);
			},
			onLoadSuccess:function(){
				var val = $(_this).combobox("getValue");
				renderUseSituation(_this,val);
			}
		});
	});
	//实际使用
	$("input[id*=spotInfo_form_actualPurpose_]").each(function(){
		$(this).combobox({
			validType:'selrequired',
			valueField:'lookupVal',
			textField:'lookupDesc',
			url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_ACTUALPURPOSE',
		});
	});
	//缴费方式
	$("input[id*=spotInfo_form_zujin_]").each(function(){
		$(this).combobox({
			validType:'selrequired',
			valueField:'lookupVal',
			textField:'lookupDesc',
			url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_RENT_PAYMENT_WAY',
		});
	});
	
	function renderSituation(_this,val){
		if(val == "1"){
			var value = $(_this).siblings("div.userSituation_container").find("input.userSituation").textbox("getValue");
			if($.trim(value)){
				var list = value.split(",");
				listVal = list;
				for(var i=0;i<list.length;i++){
					$(_this).siblings("div.userSituation_container").find("label[data='"+list[i]+"']").children("input").attr("checked",true);
				}
			}else{
				$(_this).siblings("div.userSituation_container").find("label").children("input").attr("checked",false);
			}
			$(_this).siblings("div.userSituation_container").children("div.item1").addClass("none").parent().children("div.item2").removeClass("none");
		}else if(val == "2" || val == "3"){
			$(_this).siblings("div.userSituation_container").children("div.item2").addClass("none").parent().children("div.item1").removeClass("none");
		}
	}
	//使用人
	$("input[id*=spotInfo_form_userType_]").each(function(){
		var _this = this;
		$(this).combobox({
			validType:'selrequired',
			valueField:'lookupVal',
			textField:'lookupDesc',
			url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=SPOT_INFO_USER_TYPE',
			onSelect:function(data){
				//清空原来的记录
				$(this).siblings("div.userSituation_container").find("input.userSituation").textbox("setValue","");
				renderSituation(this,data.lookupVal);
			},
			onLoadSuccess:function(){
				var val = $(this).textbox("getValue");
				renderSituation(this,val);
			}
		});
	});
	
	//流程控制
	if("task_InvestigationCheck"==workflowTaskDefKey){
		
	}else{
		//申请办理
		setTimeout(function(){
			
			$('#spotInfoForm .easyui-combobox').attr('disabled','disabled');
			$('#spotInfoForm input[type="checkbox"]').attr('disabled','disabled');
			$('#spotInfoForm .easyui-textbox').attr('disabled','disabled');
			$('#spotInfoForm input:not(.download)').attr('readOnly','readOnly');
			$('#spotInfoForm .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('disabled','disabled');
			$('#spotInfoForm .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('readOnly','readOnly');
			$('#spotInfoForm .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').addClass('l-btn-disabled');
			$('#fore_spot_info_click').attr("disabled",true);
			
			
			$('#fore_spot_info_click').removeAttr('onclick');
		});
	}
	
	
});

var listVal = [];

function changeUserSituation(_this){
	var val = $(_this).val();
	var $cheng = $(_this).parent().next("input");
	if($(_this)[0].checked){
		if(val == "承租人"){
			$cheng.textbox({required: true});
		}
		listVal.push(val);
	}else{
		for(var i=0;i<listVal.length;i++){
			if(listVal[i] == val){
				listVal.splice(i,1)
			}
		}
		if(val == "承租人"){
			$cheng.textbox({required: false});
		}
	}
	$.parser.parse($cheng);
	$(_this).closest("div.userSituation_container").find("input.userSituation").textbox("setValue",listVal.join(","));
}

/**
 * 文件操作formatter
 */
function spot_files_caozuofiter(value,row,index){
	var dataDelDom = "";
	if(workflowTaskDefKey == "task_InvestigationCheck"){
		dataDelDom = "<a onclick=lookUpFileByPath('"+row.fileUrl+"','"+ row.fileName+"') class='download'><font color='blue'>预览</font></a>&nbsp;";
		dataDelDom += "<a href='${basePath}/customerController/download.action?path="+row.fileUrl+"' class='download'><font color='blue'>下载</font></a>&nbsp;";
		dataDelDom += "<a href='javascript:void(0)' class='download'  onclick='delSpotFile("+row.pid+",this)'><font color='blue'>删除</font></a>";
	}else{
		dataDelDom = "<a onclick=lookUpFileByPath('"+row.fileUrl+"','"+ row.fileName+"') class='download'><font color='blue'>预览</font></a>&nbsp;";
		dataDelDom += "<a href='${basePath}/customerController/download.action?path="+row.fileUrl+"' class='download'><font color='blue'>下载</font></a>&nbsp;";
		dataDelDom += "<a href='javascript:void(0)' class='download'><font color='gray'>删除</font></a>";
		
	}
	return dataDelDom;
}

function delSpotFile(dId,_this){
	if(dId=="" || null==dId){
		 $.messager.alert("操作提示","该类型文件不存在，无法删除！","error"); 
		 return false;
	}else{
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "${basePath}projectInfoController/delSpotFile.action",
		        data:{"pid":dId},
		        dataType: "json",
		        success: function(data){
	        		$.messager.alert("操作提示",data.delStatus,"success");
	        		//刷新当前网格
		        	var $table = $(_this).closest("div.datagrid-view").find("table[id*=grid_data_file_]");
		        	$table.datagrid("reload");
		        }
			});
		}
	}
}

/**
 * 保存下户信息
 */
function saveSpotInfo(){
	$('#spotInfoForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate'); 
		},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				// 操作提示
				$.messager.alert('操作提示',ret.header["msg"]);
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');
			}
		},error : function(result){
			$.messager.alert('操作提示',"保存失败，请重新操作!",'error');
		}
	});
}
	

</script>
