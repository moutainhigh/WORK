<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
#baseInfo {
		width: 500px;
}
#baseInfo label {
	width: 250px;
}
#baseInfo label.error, #baseInfo input.submit {
	margin-left: 253px;
}

</style>
<script type="text/javascript">
var flag='${isLook}';
function saveBaseInfo(){
	var tempFlag=1;
	var certNumber = $("#cert_Number").val();
	//var certType = $("#cert_type").combobox('getText');
	var personId = '${cusPerBaseDTO.cusPerson.pid}';
	$("#birthDate").val($("#birth_Date").datebox("getValue"));
	$("#sex_text").val($("#sex").combobox("getValue"));
	var type=false;
	$.ajax({
		url : '${basePath}customerController/validateCeryNumber.action?certNumber='
			+ certNumber
			+ '&personId='
			+ personId,
		type : 'post',
		cache: false,
		success : function(result) {
			var ret = eval("("+result+")");
			var flags=ret.header["flags"]; 
			if(flags>0){
				tempFlag=3;
			}
			if(tempFlag==3){
				$.messager.alert('操作提示',"系统已存在此证件号码的用户！",'error');
				$("#cert_Number").focus();
			}
			else{
				$('#baseInfoForm').form('submit', {
					url : "saveCusPer.action",
					onSubmit : function() {
						if(checkForm()){
							return $(this).form('validate'); 
						}
						else{
							return false;
						}
					},
					success : function(result) {
						$.messager.alert('操作提示',"保存成功！",'info');
						var ret = eval("("+result+")");
						var templateId=ret.header["acctId"];
						window.location='${basePath}customerController/editPerBase.action?acctId='
							+ templateId+'&flag='+${flag}+'&type=1'+'&currUserPid='+${currUser.pid};
					},error : function(result){
						$.messager.alert('操作提示',"保存失败！",'error');
					}
				}); 
			}
		}
	});
	
	
}
function checkForm(){
	var chinaName=$("#chinaName").val();
	var engName=$("#engName").val();
	if(chinaName==''){
		$.messager.alert('操作提示',"中文名称不能为空！",'error');
		$("#chinaName").focus();
		return false;
	}
	
	var otherPhone=$("#otherPhone").val();
	if(otherPhone !=''){
		var arr=otherPhone.split(","); 
	    for (var i=0;i<arr.length ;i++ ){   
	    	var reg = /^(\d{3,4})?-?\d{7,8}$/; 
	    	var reg2 = /^1[3|4|5|8|9]\d{9}$/;
	    	if(!reg.test(arr[i]) && !reg2.test(arr[i])){
	    		$.messager.alert('操作提示',"其他号码格式错误！",'error');
	    		$("#otherPhone").focus();
	    		return false;
	    	}
	    	else{
	    		return true;
	    	}
	    }
	}
	else{
		return true;
	}
    
}

function cancelPerBase(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','个人客户管理');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '个人客户管理', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}
	//var tital = "修改个人客户"
	if(flag=='1'){
		parent.$('#layout_center_tabs').tabs('close','新增个人客户');
	}else if(flag=='2'){
		parent.$('#layout_center_tabs').tabs('close','修改个人客户');
	}else if(flag=='3'){
		parent.$('#layout_center_tabs').tabs('close','查看个人客户');
	}
}

$(function(){
	var certType = '${cusPerBaseDTO.cusPerson.certType}';
	if(certType == ''){
		certType = 13088;
	}
	
	//绑定下拉框的值
	setCombobox("cus_level","CUS_LV","${cusPerBaseDTO.cusPerBase.cusLevel}");
	setCombobox("cert_type","CERT_TYPE",certType);
	setCombobox("sex","SEX","${cusPerBaseDTO.cusPerson.sex}");
	setCombobox("nation","NATION","${cusPerBaseDTO.cusPerson.nation}");
	setCombobox("marr_status","MARR_STATUS","${cusPerBaseDTO.cusPerson.marrStatus}");
	setCombobox("education","EDUCATION","${cusPerBaseDTO.cusPerson.education}");
	setCombobox("degree","DEGREE","${cusPerBaseDTO.cusPerson.degree}");
	setCombobox("pol_face","POL_FACE","${cusPerBaseDTO.cusPerson.polFace}");
	setCombobox("unit_nature","UNIT_NATURE","${cusPerBaseDTO.cusPerson.unitNature}");
	setCombobox("occ_name","OCC_NAME","${cusPerBaseDTO.cusPerson.occName}");
	setCombobox("trade","TRADE","${cusPerBaseDTO.cusPerson.trade}");
	setCombobox("pay_way","PAY_WAY","${cusPerBaseDTO.cusPerson.payWay}");
	setCombobox("staff_num","STAFF_NUM","${cusPerBaseDTO.cusPerson.staffNum}");
	
	setAreaCombobox("live_province_code","1","","${cusPerBaseDTO.cusPerson.liveProvinceCode}");
	setAreaCombobox("live_city_code","2","${cusPerBaseDTO.cusPerson.liveProvinceCode}","${cusPerBaseDTO.cusPerson.liveCityCode}");
	setAreaCombobox("live_district_code","3","${cusPerBaseDTO.cusPerson.liveCityCode}","${cusPerBaseDTO.cusPerson.liveDistrictCode}");

	//居住地址省市区
  	$("#live_province_code").combobox({ 
		onSelect:function(){
        	var provinceCode = $("#live_province_code").combobox("getValue");
        	//console.log("provinceCode:"+provinceCode);
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
		 	
	// 格式所有datebox的长度,默认全部设置为100px
	$(document).ready(function(){
		$('.easyui-datebox').datebox({    
			width:150   
		});  

	});
	
	if(flag == '3'){
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
		$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
		$('.cus_save_btn').removeAttr('onclick');
		$('.cus_save_btn').attr('disabled', 'disabled');
		$('.cus_save_btn').addClass('l-btn-disabled');
	}
	
	$("#cert_type").combobox({

		onChange: function (newVal,oldVal) {

			if(newVal=='13088'){
				$("#age").attr('readonly',true);
			}else{
				$("#age").attr('readonly',false);
			}

		}

		});
	
// 	$("#telephone").change(function(){
// 		$("#person_telephone").val($(this).val());
// 	})
	
});

//删除文件
function delFile(imgurl){
	$("#"+imgurl).val("");
	var srcurl="${ctx}/p/xlkfinance/images/u53.png";
	$("#"+imgurl+"_img").attr("src",srcurl);
};
// 中文名失去焦点验证合法性
function checkChineseName(){
	var chinaName = $("#chinaName").val();
	chinaName=chinaName.replace(/[^\u4E00-\u9FA5]/g,'');
	$("#chinaName").val(chinaName);
}
//校验证件类型和证件号码是否存在
function validateCertNumber(){
	var certNumber = $("#cert_Number").val();
	var certType = $("#cert_type").combobox('getText');
	var personId = '${cusPerBaseDTO.cusPerson.pid}';
	var type=false;
	$.ajax({
		url : '${basePath}customerController/validateCeryNumber.action?certNumber='
			+ certNumber
			+ '&personId='
			+personId,
		type : 'post',
		cache: false,
		//dataType : "json",
		success : function(result) {
			var ret = eval("("+result+")");
			var flags=ret.header["flags"];
			var blackFlags=ret.header["blackFlags"];
			var cusPerBaseDTO = ret.header["cusPerBaseDTO"];
			if(flags>0 && blackFlags == 0){
				$.messager.alert('操作提示',"系统已存在此证件号码的用户,请保存！",'error');
				var acctId = cusPerBaseDTO.cusAcct.pid;
				window.location='${basePath}customerController/editPerBase.action?acctId='
					+ acctId +'&flag=1&type=1'+'&currUserPid='+${currUser.pid};
			}else if(flags>0 && blackFlags > 0){
				$.messager.confirm("操作提示","此客户已加入系统黑名单,确定进行操作吗?",
						function(r) {
							if (r) {
								var acctId = cusPerBaseDTO.cusAcct.pid;
								window.location='${basePath}customerController/editPerBase.action?acctId='
									+ acctId +'&flag=1&type=1'+'&currUserPid='+${currUser.pid};
							}else{
								$("#cert_Number").val('');
							}
						});
			}
		}
	});
}

function getBrithDate(){
	var certType = $("#cert_type").combobox('getText');
	var certNumber = $("#cert_Number").val();
	var birthDate = $("#birth_Date").val();
	if(certType=="--请选择--"){
		$.messager.alert('操作提示',"请选择证件类型！",'error');
		return false;
	}
	
	if( certType=='身份证' &&(!IdCardValidate(certNumber)))
    {
		$.messager.alert('操作提示',"输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X！",'error');
        $("#cert_Number").val('');
        return false;
    }
	if(certType=='身份证' && certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			$.messager.alert('操作提示',"请输入正确的身份证号！",'error');
			return false;
		}
		else{
			var year=certNumber.substring(6,10);
			var month=certNumber.substring(10,12);
			var date=certNumber.substring(12,14);
			$("#birth_Date").datebox("setValue",year+"-"+month+"-"+date);
			//判断性别
			if (parseInt(certNumber.substr(16, 1)) % 2 == 1) {
				//男
				$("#sex").combobox("setValue","13098");
				} else {
				//女
				$("#sex").combobox("setValue","13099");
				} 
			//获取年龄
			var myDate = new Date();
			var mon = myDate.getMonth() + 1;
			var day = myDate.getDate();
			
			var age = myDate.getFullYear() - certNumber.substring(6, 10) - 1;
			if (certNumber.substring(10, 12) < mon || certNumber.substring(10, 12) == mon && certNumber.substring(12, 14) <= day) {
			age++;
			}
			$("#age").val(age);
			
			if($("#birth_Date").datebox("getValue")!=year+"-"+month+"-"+date){
				$("#birth_Date").datebox("setValue",'');
				$.messager.alert('操作提示',"请输入正确的身份证号！",'error');
				 $("#cert_Number").val('');
				 $("#cert_Number").focus();
				 return false;
			}
		}
	}
	
	validateCertNumber();
	if(certType=='身份证'){
		$("#sex").combobox('disable');
		$("#age").attr('readonly',true);
	}else{
		$("#sex").combobox('enable');
		$("#age").attr('readonly',false);
	}
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<jsp:include page="cus_per_tab.jsp">
<jsp:param value="1" name="tab"/>
</jsp:include>
<div >
<form id="baseInfoForm" action="{basePath}customerController/saveCusPer" method="POST">
	<input type="hidden" name="cusAcct.status" value="1"/>
	<input type="hidden" name="cusAcct.cusType" value="1"/>
	<input type="hidden" name="cusAcct.cusStatus" value="${empty cusPerBaseDTO.cusAcct?1:cusPerBaseDTO.cusAcct.cusStatus}"/>
	<input type="hidden" name="cusAcct.pid" value="${cusPerBaseDTO.cusAcct.pid }" />
	
	<div class=" easyui-panel" title="填写个人基础信息" data-options="collapsible:true">
	<table class="cus_table">
		<input type="hidden"  name="cusPerBase.status" value="1"/>
        <c:if test="${not empty cusPerBaseDTO.cusPerBase}">
        	<input type="hidden" name="cusPerBase.pid" value="${cusPerBaseDTO.cusPerBase.pid}"/>
        	<input type="hidden" name="cusPerBase.cusAcct.pid" value="${cusPerBaseDTO.cusPerBase.cusAcct.pid}"/>
        </c:if>
		
		<tr>
			<td class="align_right"><span class="cus_red">*</span>中文名称：</td>
			<td><input type="text" class="text_style" id="chinaName" onblur="checkChineseName();" name="cusPerson.chinaName" value="${cusPerBaseDTO.cusPerson.chinaName}" /></td>
			<td class="align_right">英文名称：</td>
			<td><input type="text" class="text_style" onkeyup="value=value.replace(/[^\w\.\ ]/ig,'')" id="engName" name="cusPerson.engName" value="${cusPerBaseDTO.cusPerson.engName}" /></td>
			<td class="align_right">客户级别：</td>
			<td>
				<select id="cus_level" class="easyui-combobox" name="cusPerBase.cusLevel" editable="false" style="width:150px;" />
			</td>
		</tr>
		<tr>
			<td class="align_right"><span class="cus_red">*</span>证件类型：</td>
			<td>
				<select id="cert_type" editable="false" class="easyui-combobox" data-options="validType:'selrequired'" name="cusPerson.certType" style="width:150px;" />
			
			</td>
			<td class="align_right"><span class="cus_red">*</span>证件号码：</td>
			<td><input type="text" class="text_style easyui-validatebox" id="cert_Number" name="cusPerson.certNumber" value="${cusPerBaseDTO.cusPerson.certNumber}" data-options="required:true" onblur="getBrithDate();" /> </td>
			<input type="hidden" name="cusAcct.pmUserId" value="${empty cusPerBaseDTO.cusAcct?currUser.pid:cusPerBaseDTO.cusAcct.pmUserId }"/>
		</tr>
		<tr>
		  <td class="align_right"><span class="cus_red">*</span>性别：</td>
		   <td>
		   	<input type="hidden" name="cusPerson.sex" value="${cusPerBaseDTO.cusPerson.sex }" id="sex_text">
			 <select id="sex" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" style="width:150px;" />
		   </td>
		   <td class="align_right">出生日期：</td>
		   <td>
		   		<input type="hidden" name="cusPerson.birthDate" value="${cusPerBaseDTO.cusPerson.birthDate }" id="birthDate">
		   		<input type="text" id="birth_Date" editable="false" class="text_style easyui-datebox" value="${cusPerBaseDTO.cusPerson.birthDate}"/>
		   </td>
		   <td class="align_right">年龄：</td>
		   <td><input type="text" id="age" class="text_style" editable="false" data-options="validType:'integer'" name="cusPerson.age" value="${cusPerBaseDTO.cusPerson.age}"/></td>
		   
		</tr>
		<tr>
		  <td class="align_right">来本地时间：</td>
		   <td><input type="text" class="text_style easyui-datebox" editable="false" name="cusPerBase.toLocalDate" value="${cusPerBaseDTO.cusPerBase.toLocalDate}"/></td>
		
		  <td class="align_right">民族：</td>
		  <td>
			 <select id="nation" editable="false" class="easyui-combobox" name="cusPerson.nation" style="width:150px;" />
		  </td>
		  <td class="align_right"><span class="cus_red">*</span>婚姻状况：</td>
		  <td>
			 <select id="marr_status" editable="false" class="easyui-combobox" name="cusPerson.marrStatus"  data-options="validType:'selrequired'" style="width:150px;" />
		  </td>
		   
		</tr>
		<tr>
		<td class="align_right" style="width:100px;">现住址居住年限：</td>
		   <td  ><input type="text" class="text_style easyui-numberbox"  name="cusPerson.liveDate" value="${cusPerBaseDTO.cusPerson.liveDate}"/></td>
		  <td class="align_right"><span class="cus_red">*</span>手机号码：</td>
		  <td><input type="text" class="text_style easyui-validatebox"  name="cusPerson.telephone" value="${cusPerBaseDTO.cusPerson.telephone}" data-options="required:true,validType:'phone'" /></td>
		  <td class="align_right">其它号码：</td>
		  <td colspan="2"><input type="text" class="text_style" style="width: 240px;" id="otherPhone" name="cusPerson.otherPhone" value="${cusPerBaseDTO.cusPerson.otherPhone}"/></td>
		  <td class="align_right" style="text-align: left;color: #f00">（多个号码必须用,隔开）</td>
 		</tr>
		<tr>
		   <td class="align_right">家庭电话：</td>
		   <td><input type="text" class="text_style easyui-validatebox" name="cusPerson.familyPhone" data-options="validType:'phone'" value="${cusPerBaseDTO.cusPerson.familyPhone}"/></td>
		   <td class="align_right">电子邮箱：</td>
		   <td><input type="text" class="text_style easyui-validatebox" name="cusPerson.mail" data-options="validType:'email'" value="${cusPerBaseDTO.cusPerson.mail}"/></td>
		   <td  class="align_right"></td>
<!-- 		  <td><input type="hidden" name="pictureUrl"/></td>这里为头像上传的隐藏域，备用 -->
		  <td >头像上传：<input type="button" class="text_btn" onclick="openFileUpload('cus_picture_url',150);" value="上传"/>&nbsp;<input type="button" onclick="delFile('cus_picture_url');" class="text_btn"  value="删除"/></td>
		          <input type="hidden" id="cus_picture_url"  name="cusPerBase.pictureUrl" value="${cusPerBaseDTO.cusPerBase.pictureUrl }"/>
		</tr>
		<tr>
		   <td class="align_right">QQ：</td>
		   <td><input type="text" class="text_style" name="cusPerson.qq" value="${cusPerBaseDTO.cusPerson.qq}"/></td>
		   <td class="align_right">微信：</td>
		   <td><input type="text" class="text_style" name="cusPerson.wechat" value="${cusPerBaseDTO.cusPerson.wechat}"/></td>
		   <td></td>
		   <td rowspan="5" align="left" >
		   	<div class="pt10 pb10">
		   	<c:if test="${empty cusPerBaseDTO.cusPerBase.pictureUrl||cusPerBaseDTO.cusPerBase.pictureUrl=='' }">
		   		<img id="cus_picture_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="150px" height="150px">
		    </c:if>
		    <c:if test="${not empty cusPerBaseDTO.cusPerBase.pictureUrl && cusPerBaseDTO.cusPerBase.pictureUrl!='' }">
		   		<img id="cus_picture_url_img" alt="头像" src="${serverUrl}/${cusPerBaseDTO.cusPerBase.pictureUrl }" width="150px" height="150px">
		    </c:if>
		    </div>
		   </td>
		</tr>
		<tr>
		   <td class="align_right"><span class="cus_red">*</span>现居住地址：</td>
		   <td >
		   	<select id="live_province_code" name="cusPerson.liveProvinceCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
		   	</td>
		   <td >
		   	<select id="live_city_code" name="cusPerson.liveCityCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
		   	</td>
		   <td >
		   	<select id="live_district_code" name="cusPerson.liveDistrictCode" class="easyui-combobox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
		   	</td>
		</tr>
		<!-- 现居住详细地址 -->
		<tr>
		   <td class="align_right"></td>
		   <td colspan="4">
		   <input type="text" style="width: 400px;" class="text_style easyui-validatebox" name="cusPerson.liveAddr" value="${cusPerBaseDTO.cusPerson.liveAddr}" data-options="required:true"/>
		  	 <!--span class="pl9">邮编：</span>
		   <input type="text" style="width:50px"  class="text_style" name="cusPerson.liveCode" value="${cusPerBaseDTO.cusPerson.liveCode}"/></td>-->
		</tr>
		<tr>
		   <td class="align_right">户籍地址：</td>
		   <td colspan="4">
		   <input type="text" style="width: 400px;" class="text_style easyui-validatebox" name="cusPerson.censusAddr" value="${cusPerBaseDTO.cusPerson.censusAddr}" />
		   	<!--  span class="pl9">邮编：</span>
		   <input type="text" style="width:50px" class="text_style" name="cusPerson.censusCode" value="${cusPerBaseDTO.cusPerson.censusCode}"/></td>-->
		</tr>
		<tr>
		   <td class="align_right">通信地址：</td>
		   <td colspan="4" >
		   <input type="text" style="width: 400px;" class="text_style easyui-validatebox" name="cusPerson.commAddr" value="${cusPerBaseDTO.cusPerson.commAddr}"/>
		   <!-- span class="pl9">邮编：</span>
		   <input type="text" style="width:50px" class="text_style" name="cusPerson.commCode" value="${cusPerBaseDTO.cusPerson.commCode}"/></td>-->
		</tr>
		<tr>
		   <td class="align_right">社区名：</td>
		   <td><input type="text" style="width: 151px;" class="text_style" name="cusPerson.communityName" value="${cusPerBaseDTO.cusPerson.communityName}"/></td>
		   <td class="align_right" style="width: 85px;">社保卡电脑号：</td>
		   <td><input type="text" class="text_style" name="cusPerson.socSecNumber" value="${cusPerBaseDTO.cusPerson.socSecNumber}"/></td>	
			<td colspan="2"></td>
		</tr>
	</table>
	</div>
	<div class="pt10"></div>
	<div class=" easyui-panel" title="身份证件扫描上传" data-options="collapsible:true">
	<table class="cus_table">
	
	   <tr>
	      <td align="center"><input type="button" class="text_btn"  onclick="openFileUpload('cus_cert_url',350);" value="上传"/>&nbsp;<input onclick="delFile('cus_cert_url');" type="button" class="text_btn"  value="删除"/></td>
	      <td >&nbsp;</td>
	   </tr>
	   <tr>
	      <td height="160px"  align="center">
	      	 <c:if test="${empty cusPerBaseDTO.cusPerson.certUrl||cusPerBaseDTO.cusPerson.certUrl=='' }">
	     	 	<img id="cus_cert_url_img" alt="身份证件" src="${ctx}/p/xlkfinance/images/u53.png" width="300px" height="180px">
	     	 </c:if>
	     	 <c:if test="${not empty cusPerBaseDTO.cusPerson.certUrl && cusPerBaseDTO.cusPerson.certUrl!='' }">
	     	 	 <img id="cus_cert_url_img" alt="身份证件" src="${serverUrl}/${cusPerBaseDTO.cusPerson.certUrl}" width="300px" height="180px">
	     	 </c:if>
	      </td>
	      <td>&nbsp;<input type="hidden" id="cus_cert_url" name="cusPerson.certUrl" value="${cusPerBaseDTO.cusPerson.certUrl}"/></td>
	      
	   </tr>
	</table>
	</div>
      <input type="hidden"  name="cusPerson.status" value="1"/>
      <input type="hidden"  name="cusPerson.relationType" value="1"/>
      <c:if test="${not empty cusPerBaseDTO.cusPerson}">
      	<input type="hidden" name="cusPerson.pid" value="${cusPerBaseDTO.cusPerson.pid}"/>
      	<input type="hidden" name="cusPerson.cusAcct.pid" value="${cusPerBaseDTO.cusPerson.cusAcct.pid}"/>
      </c:if>
     <div class="pt10"></div>
	<div class=" easyui-panel" title="填写个人教育/工作信息" data-options="collapsible:true">
	<table class="cus_table2">
		
	   <tr>
	      <td class="align_right" style="width:122px;">毕业院校：</td> 
	      <td><input type="text" class="text_style" name="cusPerson.graduSchool" value="${cusPerBaseDTO.cusPerson.graduSchool}"/></td>
	      <td class="align_right" style="width:90px;">最高学历：</td>
	      <td>
			<select id="education" editable="false" class="easyui-combobox" name="cusPerson.education" style="width:220px;" />
		  </td>
	   </tr>
	   <tr>
	     <td class="align_right">最高学位：</td>
	     <td>
			<select id="degree" editable="false" class="easyui-combobox" name="cusPerson.degree" style="width:220px;"  />
	     </td>
	     <td class="align_right">政治面貌：</td>
	     <td>
			<select id="pol_face" editable="false" class="easyui-combobox" name="cusPerson.polFace" style="width:220px;" />
		</td>
	   </tr>
	    <tr>
	     <td class="align_right">工作单位：</td>
	     <td><input type="text" class="text_style easyui-validatebox" name="cusPerson.workUnit" value="${cusPerBaseDTO.cusPerson.workUnit}" /></td>
	     <td class="align_right">单位性质：</td>
	     <td>
			<select id="unit_nature"  editable="false" class="easyui-combobox" name="cusPerson.unitNature" style="width:220px;" />
	     </td>
	   </tr>
	   <tr>
	       <td class="align_right">单位地址：</td>
	       <td colspan="3"> <input type="text"  style="width: 460px;" class="text_style easyui-validatebox" name="cusPerson.unitAddr" value="${cusPerBaseDTO.cusPerson.unitAddr}"/>
	        <span class="pl9">邮政编码：</span><input type="text" style="width: 60px;" class="text_style" name="cusPerson.unitCode" value="${cusPerBaseDTO.cusPerson.unitCode}"/></td>
	   </tr>
	   <tr>
	       <td class="align_right" >工作单位注册资本：</td>
	       <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerson.regCapital"  value="${cusPerBaseDTO.cusPerson.regCapital}" />&nbsp;万元</td>
	       <td class="align_right" rowspan="2">主营业务：</td>
	       <td height="50px;" rowspan="2"><textarea rows="2" style="width:220px;height: 50px;" name="cusPerson.mainBus">${cusPerBaseDTO.cusPerson.mainBus}</textarea></td>
<%-- 	       <td><input type="text" class="text_style" name="cusPerson.mainBus" value="${cusPerBaseDTO.cusPerson.mainBus}"/></td> --%>
	   </tr>
	   <tr>
	     <td class="align_right">电话：</td>
	     <td ><input type="text" class="text_style easyui-validatebox" name="cusPerson.mobilePhone" data-options="validType:'phone'" value="${cusPerBaseDTO.cusPerson.mobilePhone}"/>
	     	<input type="hidden"  id="person_telephone" value="${cusPerBaseDTO.cusPerson.telephone}"/> 
	     </td>
	     
	   </tr>
	   <tr>
	       <td class="align_right" >传真号码：</td>
	       <td><input type="text" class="text_style" name="cusPerson.fax" value="${cusPerBaseDTO.cusPerson.fax}"/>&nbsp;</td>
	       <td class="align_right">单位电话：</td>
	       <td><input type="text" class="text_style easyui-validatebox" name="cusPerson.unitPhone" data-options="validType:'phone'" value="${cusPerBaseDTO.cusPerson.unitPhone}"/></td>
	   </tr>
	   <tr>
	       <td class="align_right" >职业：</td>
	       <td>
	       		<input type="text" class="text_style" name="cusPerson.occupation" value="${cusPerBaseDTO.cusPerson.occupation}"/>
	       </td>
	       <td class="align_right">职务：</td>
	       <td>
	       		<input type="text" class="text_style" name="cusPerson.workService" value="${cusPerBaseDTO.cusPerson.workService}"/>
		   </td>
	   </tr>
	     <tr>
	       <td class="align_right" >职称：</td>
	       <td>
			 <select id="occ_name" editable="false" class="easyui-combobox" name="cusPerson.occName" style="width:220px;"  />
	       </td>
	       <td class="align_right" >是否公务员：</td>
	       <td>
   			  <input type="radio" name="cusPerson.servant" value="1"  <c:if test="${cusPerBaseDTO.cusPerson.servant==1}">checked </c:if>/>是
   			  <input type="radio" name="cusPerson.servant" value="2" <c:if test="${cusPerBaseDTO.cusPerson.servant==2  || empty cusPerBaseDTO.cusPerson.servant}">checked </c:if>/>否
		   </td>
	   </tr>
	   <tr>
	       <td class="align_right" >所属部门：</td>
	       <td><input type="text" class="text_style" name="cusPerson.deptment"  value="${cusPerBaseDTO.cusPerson.deptment}"/></td>
	       <td class="align_right" >是否缴社保：</td>
	       <td>
   			  <input type="radio" name="cusPerson.paySocSec" value="1"  <c:if test="${cusPerBaseDTO.cusPerson.paySocSec==1 || empty cusPerBaseDTO.cusPerson.paySocSec}">checked </c:if>/>是
   			  <input type="radio" name="cusPerson.paySocSec" value="2"  <c:if test="${cusPerBaseDTO.cusPerson.paySocSec==2}">checked </c:if>/>否
		   </td>
	   </tr>
	   <tr>
	     <td class="align_right" >月收入：</td>
	     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerson.monthIncome"  value="${cusPerBaseDTO.cusPerson.monthIncome}"/>&nbsp;万元</td>
	     <td class="align_right" >所属行业：</td>
	     <td>
			 <select id="trade" editable="false" class="easyui-combobox" name="cusPerson.trade" style="width:220px;" />
	       </td>
	   </tr>
	   <tr>
	      <td class="align_right" >入职时间：</td>
	      <td><input type="text" class="text_style easyui-datebox" editable="false" name="cusPerson.entryTime" value="${cusPerBaseDTO.cusPerson.entryTime}"/>&nbsp;</td>
	      <td class="align_right" >发薪形式：</td>
	      <td>
			 <select id="pay_way" editable="false" class="easyui-combobox" name="cusPerson.payWay" style="width:220px;"  />
	       </td>
	   </tr>
	   <tr>
	      <td class="align_right" >每月发薪日：</td>
	      <td>
	         <select class="select_style easyui-combobox" editable="false" name="cusPerson.monthPayDay">
				<option value="1" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==1 }">selected </c:if>>1</option>
				<option value="2" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==2 }">selected </c:if>>2</option>
				<option value="3" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==3 }">selected </c:if>>3</option>
				<option value="4" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==4 }">selected </c:if>>4</option>
				<option value="5" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==5 }">selected </c:if>>5</option>
				<option value="6" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==6 }">selected </c:if>>6</option>
				<option value="7" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==7 }">selected </c:if>>7</option>
				<option value="8" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==8 }">selected </c:if>>8</option>
				<option value="9" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==9 }">selected </c:if>>9</option>
				<option value="10" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==10 }">selected </c:if>>10</option>
				<option value="11" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==11 }">selected </c:if>>11</option>
				<option value="12" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==12 }">selected </c:if>>12</option>
				<option value="13" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==13 }">selected </c:if>>13</option>
				<option value="14" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==14 }">selected </c:if>>14</option>
				<option value="15" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==15 }">selected </c:if>>15</option>
				<option value="16" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==16 }">selected </c:if>>16</option>
				<option value="17" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==17 }">selected </c:if>>17</option>
				<option value="18" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==18 }">selected </c:if>>18</option>
				<option value="19" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==19 }">selected </c:if>>19</option>
				<option value="20" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==20 }">selected </c:if>>20</option>
				<option value="21" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==21 }">selected </c:if>>21</option>
				<option value="22" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==22 }">selected </c:if>>22</option>
				<option value="23" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==23 }">selected </c:if>>23</option>
				<option value="24" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==24 }">selected </c:if>>24</option>
				<option value="25" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==25 }">selected </c:if>>25</option>
				<option value="26" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==26 }">selected </c:if>>26</option>
				<option value="27" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==27 }">selected </c:if>>27</option>
				<option value="28" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==28 }">selected </c:if>>28</option>
				<option value="29" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==29 }">selected </c:if>>29</option>
				<option value="30" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==30 }">selected </c:if>>30</option>
				<option value="31" <c:if test="${cusPerBaseDTO.cusPerson.monthPayDay==31 }">selected </c:if>>31</option>
			 </select> 	
	       </td>
	      <td class="align_right">雇员人数：</td>
	      <td>
			 <select id="staff_num" class="easyui-combobox" name="cusPerson.staffNum" style="width:220px;"  />
	       </td>
	   </tr>
	   <tr>
	     <td class="align_right" >信用记录情况及说明：</td>
	     <td colspan="3" height="80px;"><textarea rows="4" style="width:610px;" name="cusPerBase.creditSituation">${cusPerBaseDTO.cusPerBase.creditSituation}</textarea></td>
	   </tr>
	</table>	
	</div>
       <input type="hidden"  name="cusPerSocSec.status" value="1"/>
       <c:if test="${not empty cusPerBaseDTO.cusPerSocSec}">
       	<input type="hidden" name="cusPerSocSec.pid" value="${cusPerBaseDTO.cusPerSocSec.pid}"/>
       	<input type="hidden" name="cusPerSocSec.cusPerBase.pid" value="${cusPerBaseDTO.cusPerSocSec.cusPerBase.pid}"/>
       </c:if>
     <div class="pt10"></div>
	<div class=" easyui-panel" title="个人社保情况" data-options="collapsible:true">
	<table class="cus_table2" style="width:850px;">
	  <tr>
	     <td class="align_right">参保单位：</td>
	     <td><input type="text" class="text_style easyui-validatebox" name="cusPerSocSec.safeUnit" value="${cusPerBaseDTO.cusPerSocSec.safeUnit}"/></td>
	     <td class="align_right">总参保时间：</td>
	     <td><input type="text" class="text_style2 easyui-numberbox" data-options="validType:'integ'" name="cusPerSocSec.totalSafeTime" value="${cusPerBaseDTO.cusPerSocSec.totalSafeTime}"/>&nbsp;月</td>
	     <td class="align_right1" >本单位参保时间：</td>
	     <td><input type="text" class="text_style easyui-datebox" editable="false" name="cusPerSocSec.safeTime" value="${cusPerBaseDTO.cusPerSocSec.safeTime}"/></td>
	  		
	  </tr>
	  <tr>
	     <td class="align_right" >医疗余额：</td>
	     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator="," id="medMoney" name="cusPerSocSec.medMoney" value="${cusPerBaseDTO.cusPerSocSec.medMoney}"/></td>
	     <td class="align_right" >参保基数：</td>
	     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator="," id="safeNum"  name="cusPerSocSec.safeNum" value="${cusPerBaseDTO.cusPerSocSec.safeNum}"/></td>
	  </tr>
	  <tr>
	     <td class="align_right" >养老金额：</td>
	     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerSocSec.penMoney" value="${cusPerBaseDTO.cusPerSocSec.penMoney}"/></td>
	     <td class="align_right" >是否中断：</td>
	     <td>
   			  <input type="radio" name="cusPerSocSec.suspend" value="1"  <c:if test="${cusPerBaseDTO.cusPerSocSec.suspend==1}">checked</c:if>/>是
   			  <input type="radio" name="cusPerSocSec.suspend" value="2"  <c:if test="${cusPerBaseDTO.cusPerSocSec.suspend==2  || empty cusPerBaseDTO.cusPerSocSec}">checked </c:if>/>否
		 </td>
	  </tr>
	</table>   
      

</form>
 </div>
	<div class="pb10" style="text-align: center; padding-top: 15px;">
	<input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveBaseInfo();"/>&nbsp;&nbsp;&nbsp; 
	<input type="button" class="cus_save_btn" id="can_cle" name="cancel" value="取     消" onclick="cancelPerBase();"/>
	</div>	    
</div>
</div>	   
</div>
</div>
</body> 
	
