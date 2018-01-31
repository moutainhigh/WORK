<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>新增客户</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style type="text/css">
.col-sm-3{padding-right: 15px;}
 .btn-primary[disabled]{ background-color:#fff; border-color:#005E98;}
 .row{margin-right: -5px;margin-left: -5px;}
 .col-sm-4{position: relative;}
 .col-sm-4 i{position: absolute;right: -5px;top: 7px;font-style: normal;}
 .modal-body .control-label {padding-top: 7px;margin-bottom: 0; text-align: right;}
 .table {border: 1px solid #DDD;}
 .table th{border-right: 1px solid #DDD;background-color: #F5F5F6;}
 .table th:last-child{border-right: none;}
 .table th, .table td{ font-size: 13px;}
  .table td{border-bottom: 1px solid #DDD;border-right: 1px solid #DDD;}
 .table td:last-child{border-bottom:none;border-right: none;}
 .left_bj{margin-left: 2%;}
</style>
</head>

<body class="gray-bg" onload="onLoadFun();">
 <div class="wrapper wrapper-content animated fadeInRight" id="customerInfo">
 <form role="form" id="baseInfoForm" class="form-horizontal" action="#" method="post">
 	<input type="hidden" name="cusAcct.status" value="1"/>
	<input type="hidden" name="cusAcct.cusType" value="1"/>
	<input type="hidden" name="cusAcct.pid" value="${empty cusPerBaseDTO.cusAcct?0:cusPerBaseDTO.cusAcct.pid }" />
    <input type="hidden" name="cusAcct.pmUserId" value="${empty cusPerBaseDTO.cusAcct?loginUser.pid:cusPerBaseDTO.cusAcct.pmUserId }"/>
	<input type="hidden" name="cusAcct.cusStatus" value="${empty cusPerBaseDTO.cusAcct?1:cusPerBaseDTO.cusAcct.cusStatus}"/>
	<input type="hidden" name="cusAcct.cusSource" value="${empty cusPerBaseDTO.cusAcct?1: cusPerBaseDTO.cusAcct.cusSource}" />
	<input type="hidden" name="cusAcct.orgId" value="${empty cusPerBaseDTO.cusAcct?loginUser.orgId: cusPerBaseDTO.cusAcct.orgId}" />
  	
  	<input type="hidden"  name="cusPerson.status" value="${empty cusPerBaseDTO.cusPerson?1:cusPerBaseDTO.cusPerson.status}"/>
    <input type="hidden" name="cusPerson.pid" value="${empty cusPerBaseDTO.cusPerson?0:cusPerBaseDTO.cusPerson.pid}"/>
    <input type="hidden" name="cusPerson.cusAcct.pid" value="${empty cusPerBaseDTO.cusPerson?0:cusPerBaseDTO.cusPerson.cusAcct.pid}"/>
    <input type="hidden"  name="cusPerson.relationType" value="${empty cusPerBaseDTO.cusPerson?1:cusPerBaseDTO.cusPerson.relationType}"/>
  
  	<input type="hidden" name="cusPerBase.pid" value="${empty cusPerBaseDTO.cusPerBase?0:cusPerBaseDTO.cusPerBase.pid}"/>
  	<input type="hidden" name="cusPerBase.cusAcct.pid" value="${empty cusPerBaseDTO.cusPerBase?0:cusPerBaseDTO.cusPerBase.cusAcct.pid}"/>
  	<input type="hidden"  name="cusPerBase.status" value="${empty cusPerBaseDTO.cusPerBase?1:cusPerBaseDTO.cusPerBase.status}"/>
   <input type="hidden"  name="cusPerSocSec.status" value="1"/>
    <input type="hidden" name="cusPerSocSec.pid" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.pid}"/>
    <input type="hidden" name="cusPerSocSec.cusPerBase.pid" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.cusPerBase.pid}"/>
	<input type="hidden" name="cusPerSocSec.safeUnit" value="${empty cusPerBaseDTO.cusPerSocSec ?'':cusPerBaseDTO.cusPerSocSec.safeUnit}"/>
	<input type="hidden" name="cusPerSocSec.totalSafeTime" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.totalSafeTime}"/>
	<input type="hidden" name="cusPerSocSec.medMoney" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.medMoney}"/>
	<input type="hidden" name="cusPerSocSec.safeNum" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.safeNum}"/>
	<input type="hidden" name="cusPerSocSec.penMoney" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.penMoney}"/>
	<input type="hidden" name="cusPerSocSec.suspend" value="${empty cusPerBaseDTO.cusPerSocSec?0:cusPerBaseDTO.cusPerSocSec.suspend}" />
  <div class="row" id="projectRow">
   <div class="ibox float-e-margins"> 
    <div class="ibox-title">
     <h5>基础信息</h5>
     <div class="ibox-tools">
      <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
      </a> 
     </div>
    </div>
    <div class="ibox-content ibox-line">
     	<div class="row">
			<div class="col-sm-10">
				<div class="form-group">
				   <label for="chinaName" class="col-sm-3 control-label red"><b>*</b>中文姓名:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control required isChinese" value="${cusPerBaseDTO.cusPerson.chinaName}" maxlength="20" id="chinaName" name="cusPerson.chinaName" placeholder="中文姓名">
					</div>
				   <label for="engName" class="col-sm-3 control-label red">英文姓名:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control isEnglish" value="${cusPerBaseDTO.cusPerson.engName}" id="engName" name="cusPerson.engName" onkeyup="value=value.replace(/[^\w\.\ ]/ig,'')" placeholder="英文姓名">
					</div>
				</div>
				<div class="form-group">
					<label for="cus_level" class="col-sm-3 control-label red">客户等级:</label>
					<div class="col-sm-3">
						<select class="form-control" name="cusPerBase.cusLevel" id="cus_level"></select>
					</div>
					<label for="telephone" class="col-sm-3 control-label red"><b>*</b>手机号码:</label>
					<div class="col-sm-3">
				  		<input type="text" class="form-control required isMobile" value="${cusPerBaseDTO.cusPerson.telephone}" id="telephone" name="cusPerson.telephone" placeholder="手机号码">
					</div>
				</div>
				<div class="form-group">
				   <label for="cert_type" class="col-sm-3 control-label red"><b>*</b>证件类型:</label>
				   	<div class="col-sm-3">
				  		<select class="form-control selectNone" name="cusPerson.certType" id="cert_type"></select>
					</div>
				   <label for="cert_Number" class="col-sm-3 control-label red"><b>*</b>证件号码:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control required" value="${cusPerBaseDTO.cusPerson.certNumber}" id="cert_Number" name="cusPerson.certNumber"  onblur="getBrithDate(1);"  placeholder="证件号码">
					</div>
				</div>
				<div class="form-group">
					<label for="sex" class="col-sm-3 control-label red"><b>*</b>性别:</label>
					<div class="col-sm-3">
						<select class="form-control selectNone" id="sex"></select>
						<input type="hidden" name="cusPerson.sex" value="${cusPerBaseDTO.cusPerson.sex }" id="sex_text">
					</div>
					<label for="cusPerson.marrStatus" class="col-sm-3 control-label red"><b>*</b>婚姻状况:</label>
					<div class="col-sm-3">
				  		<select class="form-control selectNone" name="cusPerson.marrStatus" id="marr_status" onchange="addSpousePerson();"></select>
					</div>
				</div>
				<div class="form-group">
				   <label for="birth_Date" class="col-sm-3 control-label red">出生年月:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control input-sm laydate-icon layer-date" value="${cusPerBaseDTO.cusPerson.birthDate}"  onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" id="birth_Date" name="cusPerson.birthDate"  placeholder="出生年月">
					</div>
				   <label for="age" class="col-sm-3 control-label red"><b>*</b>年龄:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control required checkAge" value="${cusPerBaseDTO.cusPerson.age}" id="age" name="cusPerson.age"  placeholder="年龄">
					</div>
				</div>
				<div class="form-group">
					<label for="mail" class="col-sm-3 control-label red">电子邮箱:</label>
					<div class="col-sm-3">
				  		<input type="text" class="form-control email" value="${cusPerBaseDTO.cusPerson.mail}" id="mail" name="cusPerson.mail" placeholder="电子邮箱">
					</div>
					<label for="liveCode" class="col-sm-3 control-label red">邮编:</label>
					<div class="col-sm-3">
				  		<input type="text" class="form-control isZipCode" value="${cusPerBaseDTO.cusPerson.liveCode}" id="liveCode" name="cusPerson.liveCode" placeholder="邮编">
					</div>
				</div>
				<div class="form-group">
				   <label for="live_province_code" class="col-sm-3 control-label red"><b>*</b>现居住地址:</label>
				   	<div class="col-sm-3">
				  		<select class="form-control selectNone" name="cusPerson.liveProvinceCode" id="live_province_code" ></select>
					</div>
				   	<div class="col-sm-3">
				  		<select class="form-control selectNone" name="cusPerson.liveCityCode" id="live_city_code" ></select>
					</div>
					<div class="col-sm-3">
				  		<select class="form-control selectNone" name="cusPerson.liveDistrictCode" id="live_district_code" ></select>
					</div>
				</div>
				<div class="form-group">
					<label for="live_province_code" class="col-sm-3 control-label red"></label>
					<div class="col-sm-3">
				  		<input type="text" class="form-control required" value="${cusPerBaseDTO.cusPerson.liveAddr}" id="liveAddr" name="cusPerson.liveAddr" placeholder="详细地址">
					</div>
					<%-- <label for="liveCode" class="col-sm-3 control-label red"><b>*</b>备注:</label>
					<div class="col-sm-3">
				  		<input type="text" class="form-control" value="${cusPerBaseDTO.cusPerson.liveCode}" id="liveCode" name="cusPerson.liveCode" placeholder="邮编">
					</div> --%>
				</div>
				
	    	</div>
    	</div>
    </div>
    </div>
</div>
<!-- 配偶信息开始 -->
<div class="row" id="spousePersonRow">
   <div class="ibox float-e-margins"> 
    <div class="ibox-title">
     <h5>配偶信息</h5>
     <div class="ibox-tools">
      <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
      </a> 
     </div>
    </div>
    <div class="ibox-content ibox-line">
     	<div class="row">
			<div class="col-sm-10">
				<div class="form-group">
				   <label for="chinaName" class="col-sm-3 control-label red"><b>*</b>中文姓名:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control required isChinese" value="${spousePerson.chinaName}" id="spouseChinaName" name="spousePerson.chinaName" placeholder="中文姓名">
						<input type="hidden"  name="spousePerson.status" value="${empty cusPerBaseDTO.spousePerson?1:cusPerBaseDTO.spousePerson.status}"/>
					    <input type="hidden" name="spousePerson.pid" value="${empty cusPerBaseDTO.spousePerson?0:cusPerBaseDTO.spousePerson.pid}"/>
					    <input type="hidden" name="spousePerson.cusAcct.pid" value="${empty cusPerBaseDTO.spousePerson?0:cusPerBaseDTO.spousePerson.cusAcct.pid}"/>
					    <input type="hidden"  name="spousePerson.relationType" value="${empty cusPerBaseDTO.spousePerson?2:cusPerBaseDTO.spousePerson.relationType}"/>
					</div>
				   <label for="engName" class="col-sm-3 control-label red">英文姓名:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control isEnglish" value="${spousePerson.engName}" id="spouseEngName" name="spousePerson.engName" onkeyup="value=value.replace(/[^\w\.\ ]/ig,'')" placeholder="英文姓名">
					</div>
				</div>
				<div class="form-group">
				   <label for="cert_type" class="col-sm-3 control-label red"><b>*</b>证件类型:</label>
				   	<div class="col-sm-3">
				  		<select class="form-control selectNone" name="spousePerson.certType" id="spouseCert_type"></select>
					</div>
				   <label for="cert_Number" class="col-sm-3 control-label red"><b>*</b>证件号码:</label>
				   	<div class="col-sm-3">
				  		<input type="text" class="form-control required" value="${spousePerson.certNumber}" id="spouseCert_Number" name="spousePerson.certNumber"  onblur="getBrithDate(2);"  placeholder="证件号码">
					</div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-3 control-label red"><b>*</b>手机号码:</label>
					<div class="col-sm-3">
				  		<input type="text" class="form-control required isMobile" value="${spousePerson.telephone}" id="spouseTelephone" name="spousePerson.telephone" placeholder="手机号码">
					</div>
					<label for="spouseSex" class="col-sm-3 control-label red"><b>*</b>性别:</label>
					<div class="col-sm-3">
						<select class="form-control selectNone" id="spouseSex"></select>
						<input type="hidden" id="spouseSex_text" name="spousePerson.sex" value="${spousePerson.sex }">
					</div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-3 control-label red"><b>*</b>是否熟知此贷款:</label>
					<div class="col-sm-3">
						<div class="radio">
					  		<input name="spousePerson.knowLoan" type="radio" value="2" <c:if test="${2 eq spousePerson.knowLoan}">checked=true</c:if> style="margin-top: 2px"/>是
					  	</div>
					  	<div class="radio">
					  		<input name="spousePerson.knowLoan" type="radio" value="1" <c:if test="${1 eq spousePerson.knowLoan}">checked=true</c:if> style="margin-top: 2px"/>否
					  	</div>
					</div>
				</div>
	    	</div>
    	</div>
    </div>
    </div>
</div>
<c:if test="${editType == 1 || editType == 2|| editType == 4}">
	<div class="form-group forum-info" style="padding-bottom: 10px;"> 
	<input type="button" class="btn btn-w-m btn-white cz_an" style="margin: 0 5px 0 0;" value="保存"  onclick="saveBaseInfo()" name="commit"> 
	<%-- <input type="button" class="btn btn-primary  an_bk" onclick="window.location='${basePath}/customerController/perList.action'"  value="取消" name="commit"> --%>
	</div>
</c:if>
 <!-- 配偶信息结束 -->     
 <!-- 关系人开始 -->     
<div class="row" id="relationInfo">
<div class="ibox float-e-margins">
<div class="ibox-title">
	<h5>关系人信息</h5>
	<div class="ibox-tools">
	<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
	</div>
</div>
  <div class="ibox-content ibox-line">
     <div class="row">
     <div class="form-group"> 
     	<c:if test="${editType == 1 || editType == 2|| editType == 4}">
        	<input type="button" class="btn btn-outline btn-primary left_bj" value="新增" name="commit" onclick="openAddRelationModal();"> 
        </c:if>
       </div>
	   <table class="table" id="houseInfoList">
	    <thead>
	     <tr>
	      <th width="10%">姓名</th>
	      <th width="10%">性别</th>
	      <th width="10%">证件类型</th>
	      <th width="20%">证件号码</th>
	      <th width="15%">手机号码</th>
	      <th width="10%">产权占比</th>
	      <th width="10%">关系</th>
	      <c:if test="${editType == 1 || editType == 2|| editType == 4}">
	      	<th width="15%">操作</th>
	      </c:if>
	     </tr>
	    </thead>
	    <tbody>
	    <c:forEach items="${noSpouseList}" var="item" varStatus="status">
	     <tr>
	      <td>${item.chinaName}</td>
	      <td>${item.sexText}</td>
	      <td>${item.certTypeText}</td>
	      <td>${item.certNumber}</td>
	      <td>${item.telephone}</td>
	      <td>${item.proportionProperty}%</td>
	      <td>${item.relationTypeText}</td>
	      <c:if test="${editType == 1 || editType == 2|| editType == 4}">
		      <td>
		      	<a href="#" onclick="editAddRelationModal('${item.pid}','${item.chinaName}','${item.sex}','${item.relationType}','${item.certNumber}','${item.telephone}','${item.proportionProperty}',0);">修改</a>
		      	<a href="#" onclick="editAddRelationModal('${item.pid}','${item.chinaName}','${item.sex}','${item.relationType}','${item.certNumber}','${item.telephone}','${item.proportionProperty}',1);">查看</a>
		      	<a href="#" onclick="deleteRelationModal('${item.pid}');">删除</a>
		      </td>
	      </c:if>
	     </tr>
	     </c:forEach>
	    </tbody>
	   </table>
      </div>
     </div>
    </div>
   </div>
 </form>
 </div>
 </div>
 
<!-- 关系人信息弹出开始 -->
 <div class="modal fade" id="relationInfoModal" tabindex="-1" role="dialog" data-backdrop="static" aria-labelledby="relationInfoModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width: 680px;">
   <div class="modal-content animated bounceInRight">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
     <h4 class="modal-title" id="relationInfoModalLabel">关系人信息</h4>
    </div>
    <form id="relationInfoForm" name="relationInfoForm" method="post">
     <div class="modal-body" style="height: auto; overflow: hidden;">
     	<div class="form-group" style="width:100%; height: auto; overflow: hidden;">
     	
     		<input type="hidden" name="pid" id="otherPid" value="0">
     		<input type="hidden" name="status"	value="1" />
			<input type="hidden" name="cusAcct.pid"	value="${empty cusPerBaseDTO.cusAcct.pid?0:cusPerBaseDTO.cusAcct.pid}" />
			<input type="hidden" id="otherCert_type" name="certType" value="13088" />
     		
     		
         	<label for="chinaName" class="col-sm-2 control-label red"><b>*</b>中文名称:</label>
         	<div class="col-sm-4">
          		<input type="text" class="form-control required isChinese" id="otherChinaName" name="chinaName" value='' maxlength="50" placeholder="中文名称">
         	</div>
         	<label for="sex" class="col-sm-2 control-label red"><b>*</b>性别:</label>
         	<div class="col-sm-4">
         		<select class="form-control selectNone" id="otherSex" value=''></select>
				<input type="hidden" name="sex" value='' id="otherSex_text">
						
         	</div>
        </div>
        <div class="form-group" style="width:100%; height: auto; overflow: hidden;">
         	<label for="certNumber" class="col-sm-2 control-label red"><b>*</b>身份证号:</label>
         	<div class="col-sm-4">
          		<input type="text" class="form-control required" id="otherCertNumber" name="certNumber" onblur="getBrithDate(3);" value='' maxlength="50" placeholder="身份证号">
         	</div>
         	<label for="relationType" class="col-sm-2 control-label red"><b>*</b>关系:</label>
         	<div class="col-sm-4">
         		<select class="form-control selectNone" name="relationType" id="otherRelationType" value=''></select>
         	</div>
        </div>
       <div class="form-group" style="width:100%;height: auto;">
         	<label for="telephone" class="col-sm-2 control-label red"><b>*</b>手机号码:</label>
         	<div class="col-sm-4">
           		<input type="text" id="otherTelephone" class="form-control required isMobile" name="telephone" value='' maxlength="50" placeholder="手机号码">
         	</div>
         	<label for="telephone" class="col-sm-2 control-label red">产权占比:</label>
         	<div class="col-sm-4">
           		<input type="text" id="otherProportionProperty" class="form-control iszFloatGtRate" name="proportionProperty" value="0" maxlength="50" placeholder="产权占比" onblur="if(this.value==''){this.value=0;}">
           		<i>%</i>
         	</div>
        </div>
     </div>
     <div class="modal-footer">
      <a id="uploadHandleDynamicFileButton" onclick="saveRelationInfo()" class="btn btn-primary an_bk" >保存</a> 
      <a data-dismiss="modal" class="btn btn-w-m btn-white cz_an">取消</a>
     </div>
    </form>
   </div>
  </div>
 </div>
 <!-- 关系人信息弹出结束 -->
 
 <!-- 需要延迟加载的资源 -->
  <script src="${ctx }js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
 <%@ include file="../../../../delayLoadCommon.jsp"%>
</body>
<script type="text/javascript">
//编辑类型，1新增 2修改 3查看
var editType = '${editType}';
$(function(){
	var certType = '${cusPerBaseDTO.cusPerson.certType}';
	/* if(certType || certType == ''){
		certType = 13088;
	} */
	//绑定下拉框的值
	setCombobox("cus_level","CUS_LV","${cusPerBaseDTO.cusPerBase.cusLevel}");
	setCombobox("cert_type","CERT_TYPE",certType);
	setCombobox("sex","SEX","${cusPerBaseDTO.cusPerson.sex}");
	setCombobox("spouseSex","SEX","${cusPerBaseDTO.spousePerson.sex}");
	setCombobox("spouseCert_type","CERT_TYPE","${cusPerBaseDTO.spousePerson.certType}");
	setCombobox("marr_status","MARR_STATUS","${cusPerBaseDTO.cusPerson.marrStatus}");
	setAreaCombobox("live_province_code","1","","${cusPerBaseDTO.cusPerson.liveProvinceCode}");
	setAreaCombobox("live_city_code","2","${cusPerBaseDTO.cusPerson.liveProvinceCode}","${cusPerBaseDTO.cusPerson.liveCityCode}");
	setAreaCombobox("live_district_code","3","${cusPerBaseDTO.cusPerson.liveCityCode}","${cusPerBaseDTO.cusPerson.liveDistrictCode}");
	//查看操作
	if(editType == 3){
		$("input").each(function(){
		    $(this).attr("disabled","disabled");  //遍历得到的每一个jquery对象
		 });
		$("select").each(function(){
		    $(this).attr("disabled","disabled");  
		 });
	}else{
		$("input").each(function(){
		    $(this).removeAttr("disabled");  //遍历得到的每一个jquery对象
		 });
		$("select").each(function(){
		    $(this).removeAttr("disabled");
		 });
	}
	//居住地址省市区
	$('#live_province_code').change(function(){
        var provinceCode = $(this).children('option:selected').val();
       	if(provinceCode != ""){
       		$("#live_district_code option").empty();
       		setAreaCombobox("live_city_code","2",provinceCode,"0");
       	 	var cityCode = $("#live_city_code").children('option:selected').val();
       		setAreaCombobox("live_district_code","3",cityCode,"0");
   		}
    });
	$('#live_city_code').change(function(){
		$("#live_district_code option").empty();
        var cityCode = $(this).children('option:selected').val();
        setAreaCombobox("live_district_code","3",cityCode,"0");
    });
})
$('#sex').change(function(){
	var sex = $("#sex").val();
    $("#sex_text").val(sex);
}); 
//身份证格式校验,自动计算性别年龄
function getBrithDate(type){
	var certNumberTag;
	var birthDateTag;
	var sexTag;
	var sexTextTag;
	var ageTag;
	var certType;
	var certNumber;
	var birthDate;
	if(type == 1){
		 certNumberTag = $("#cert_Number");
		 birthDateTag = $("#birth_Date");
		 sexTag = $("#sex");
		 sexTextTag = $("#sex_text");
		 ageTag = $("#age");
		 certType = $("#cert_type option:selected").html();
		 certNumber = $("#cert_Number").val();
		 birthDate = $("#birth_Date").val();
	}else if(type == 2){
		certNumberTag = $("#spouseCert_Number");
		sexTag = $("#spouseSex");
		sexTextTag = $("#spouseSex_text");
		certType = $("#spouseCert_type option:selected").html();
		certNumber = $("#spouseCert_Number").val();
	}else if(type == 3){
		certNumberTag = $("#otherCertNumber");
		sexTag = $("#otherSex");
		sexTextTag = $("#otherSex_text");
		certType = "身份证";
		certNumber = $("#otherCertNumber").val();
	}
	if(certType=="--请选择--"){
		layer.alert('请选择证件类型！', {
			icon : 2
		});
		return false;
	}
	if( certType=='身份证' &&(!IdCardValidate(certNumber)))
    {
		layer.alert('输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X！', {
			icon : 2
		});
		certNumberTag.val('');
        return false;
    }
	if(certType=='身份证' && certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			layer.alert('请输入正确的身份证号！', {
				icon : 2
			});
			return false;
		}
		else{
			if(sexTag != undefined){
				//判断性别
				if (parseInt(certNumber.substr(16, 1)) % 2 == 1) {
					//男
					sexTag.val("13098");
					sexTextTag.val("13098");
					} else {
					//女
					sexTag.val("13099");
					sexTextTag.val("13099");
				}
			}
			if(birthDateTag != undefined){
				var year=certNumber.substring(6,10);
				var month=certNumber.substring(10,12);
				var date=certNumber.substring(12,14);
				birthDateTag.val(year+"-"+month+"-"+date);
			}
			if(ageTag != undefined){
				//获取年龄
				var myDate = new Date();
				var mon = myDate.getMonth() + 1;
				var day = myDate.getDate();
				
				var age = myDate.getFullYear() - certNumber.substring(6, 10) - 1;
				if (certNumber.substring(10, 12) < mon || certNumber.substring(10, 12) == mon && certNumber.substring(12, 14) <= day) {
				age++;
				}
				ageTag.val(age);
			}
			if(birthDateTag != undefined && birthDateTag.val()!=year+"-"+month+"-"+date){
				birthDateTag.val('');
				layer.alert('请输入正确的身份证号！', {
					icon : 2
				});
				certNumberTag.val('');
				certNumberTag.focus();
				 return false;
			}
		}
	}
	//validateCertNumber();
	if(certType=='身份证'){
		sexTag.attr('disabled',true);
		$("#age").attr('readonly',true);
	}else{
		sexTag.attr('disabled',false);
		$("#age").attr('readonly',false);
	}
}
//中文名失去焦点验证合法性
function checkChineseName(){
	var chinaName = $("#chinaName").val();
	chinaName=chinaName.replace(/[^\u4E00-\u9FA5]/g,'');
	$("#chinaName").val(chinaName);
}
//表单提交校验
function checkForm(){
	var marrStatus = $("#marr_status option:selected").html();
	if(marrStatus == '已婚'){
		 var sknowLoan=$('input:radio[name="spousePerson.knowLoan"]:checked').val();
	     if(sknowLoan==null){
	    	 layer.alert('请选择是否熟知此贷款！', {icon : 2});
	         return false;
	     }
	}
	return true;
}
//根据机构编码+身份证编码做唯一性检验
function checkCusExist(){
	var orgId = '${loginUser.orgId}';
	var certNumber = $("#cert_Number").val();
	var existFlag = true;
	//alert("orgId="+orgId+"certNumber="+certNumber);
	$.ajax({
		url : "${basePath}/customerController/checkCusExist.action?orgId="+orgId+"&certNumber="+certNumber,
		dataType : 'json',
		type : 'post',
		async : false,
		success : function(result) {
			var pid = result.header["pid"];
			if (pid>0) {
				layer.alert('客户已存在！', {icon : 5});
				existFlag = false;
			} 
		},error : function(result){
			layer.alert('保存失败！', {icon : 5});
			existFlag = false;
		}
	});
	return existFlag;
}
//保存
function saveBaseInfo(){
	var existFlag = true;
	//根据机构编码+身份证编码做唯一性检验
	var editType = '${editType}';
	if(editType==1){
		existFlag = checkCusExist();
	}
	if(!existFlag){
		return false;
	}
	var flag = checkForm();
	//alert("existFlag="+existFlag+" editType="+editType);
	if($("#baseInfoForm").validate().form() && flag){
		$.ajax({
			url : "${basePath}/customerController/saveCusPer.action",
			dataType : 'json',
			data : $('#baseInfoForm').serialize(),
			type : 'post',
			async : false,
			success : function(data) {
				var acctId = data.header["acctId"];
				layer.confirm('保存成功', {
					icon : 6,
					btn : [ '是' ]
				//按钮
				}, function() {
					if(editType==1){
						refreshTab("toAddPerBase.action","${ctx}customerController/editPerBase.action?editType=4&acctId="+ acctId);
					}else if(editType==4){
						refreshTab("toAddPerBase.action","${ctx}customerController/editPerBase.action?editType=4&acctId="+ acctId);
					}else{
						refreshTab("toEditPerBase.action","${ctx}customerController/editPerBase.action?editType=2&acctId="+ acctId);
					}
					refreshTab("${ctx}customerController/perList.action","${ctx}customerController/perList.action");
					//closeTab();
					//refreshPage();
					 <%-- <% if(updateUser==null){%>
					<%}else{%>
					refreshPage();
					<%}%> --%>
					//window.location.reload();
				});
			},error : function(result){
				layer.alert('保存失败！', {icon : 5});
			}
		});
	}
} 
//打开关系人信息页面
function openAddRelationModal(){
	$("#relationInfoModal input").removeAttr("disabled");
	$("#relationInfoModal select").removeAttr("disabled");
	var pid = '${cusPerBaseDTO.cusAcct.pid }';
	$("#otherPid").val(0);
	if(pid == ''){
		layer.alert('请先保存客户基础信息！', {icon : 2});
		return false;
	}
	//关系人页面加载
	setReCombobox("otherRelationType","RELATION","");
	setCombobox("otherSex","SEX","");
	$('#relationInfoForm').resetForm();
	$('#relationInfoModal').modal('show');
}
//编辑关系人信息页面
function editAddRelationModal(pid,chinaName,sex,relationType,certNumber,telephone,proportionProperty,operate){
	$("#otherPid").val(pid);
	$("#otherChinaName").val(chinaName);
	$("#otherSex_text").val(sex);
	$("#otherRelationType").val(relationType);
	$("#otherCertNumber").val(certNumber);
	$("#otherTelephone").val(telephone);
	$("#otherProportionProperty").val(proportionProperty);
	//alert("operate="+operate);
	if(operate == 1){
		$("#relationInfoModal input").attr("disabled","disabled");
		$("#relationInfoModal select").attr("disabled","disabled");
	}else{
		$("#relationInfoModal input").removeAttr("disabled");
		$("#relationInfoModal select").removeAttr("disabled");
	}
	//关系人页面加载
	setReCombobox("otherRelationType","RELATION",relationType);
	setCombobox("otherSex","SEX",sex);
	$('#relationInfoModal').modal('show');
}
//保存关系人信息
function saveRelationInfo(){
	//alert($("#relationInfoForm").validate().form());
	if($("#relationInfoForm").validate().form()){
		$.ajax({
			url : "${basePath}/customerController/saveCusPerson.action",
			type : "POST",
			data : $("#relationInfoForm").serialize(),
			async : false,
			success : function(data) {
				layer.confirm('保存成功', {
					icon : 6,
					btn : [ '是' ]
				//按钮
				}, function() {
					window.location.reload();
				});
			},error : function(result){
				layer.alert('保存失败！', {icon : 5});
			}
		});
	}
}
//删除关系人信息
function deleteRelationModal(pid){
	layer.confirm("确定删除选择人信息吗?", {
		icon : 3,
		btn : [ '是','否' ]
	//按钮
	}, function() {
		$.ajax({
			url : "${basePath}/customerController/deleteCusPerson.action?pids="+pid,
			type : "POST",
			async : false,
			success : function(result) { //表单提交后更新页面显示的数据
					window.location.reload();
			}
		});
	}, function() {
	});
	
}

$('#otherSex').change(function(){
	var sex = $("#otherSex").val();
    $("#otherSex_text").val(sex);
});
//配偶相关
$('#spouseSex').change(function(){
	var sex = $("#spouseSex").val();
    $("#spouseSex_text").val(sex);
});
//客户已婚则需要添加配偶信息
function addSpousePerson(){
	var marrStatus = $("#marr_status option:selected").html();
	if(marrStatus == '已婚'){
		if(editType != 3){
			$("#spousePersonRow input").removeAttr("disabled");
			$("#spousePersonRow select").removeAttr("disabled");
			$("#spousePersonRow radio").removeAttr("disabled");
		}
		$("#spousePersonRow").show();
	}else{
		$("#spousePersonRow  input").attr("disabled","disabled");
		$("#spousePersonRow  select").attr("disabled","disabled");
		$("#spousePersonRow  radio").attr("disabled","disabled");
		$("#spousePersonRow").hide();
	}
}
function disEditBrith(){
	var marrStatus = $("#marr_status option:selected").html();
	//编辑页面置灰身份证相关回填值
	if(marrStatus == '已婚'){
		getBrithDate(1);
		getBrithDate(2);
	}
}
function onLoadFun(){
	addSpousePerson();
	disEditBrith();
}
</script>
</html>