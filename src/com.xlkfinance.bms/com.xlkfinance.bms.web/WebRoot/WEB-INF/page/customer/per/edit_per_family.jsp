<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
var flag='${isLook}';
function saveFamilyInfo(){
	   $('#familyInfo').form('submit', {
			url : "savePerFamily.action",
			onSubmit : function() {
				//checkForm();
				return $(this).form('validate'); 
			},
			success : function(result) {
				alert("保存成功！");
				window.location='${basePath }customerController/editPerFamily.action?perId=${perId}&acctId=${acctId}';
			},error : function(result){
				alert("保存失败！");
			}
		}); 
}

function checkForm(){
	var chinaName=$("#chinaName").val();
	var engName=$("#engName").val();
	if(chinaName=='' && engName==''){
		alert("中文名称和英文名称不能同时为空！");
		$("#chinaName").focus();
		return false;
	}
}
//格式所有datebox的长度,默认全部设置为100px
$(document).ready(function(){
	$('.easyui-datebox').datebox({    
		width:220   
	});  

});
$(function(){
	setCombobox("live_status","LIVE_STATUS","${cusPerFamilyDTO.cusPerFamily.liveStatus}");
	setCombobox("house_shape","HOUSE_SHAPE","${cusPerFamilyDTO.cusPerFamily.houseShape}");
	setCombobox("relation","RELATION","${cusPerFamilyDTO.cusPerson.relation}");
	setCombobox("cert_type","CERT_TYPE","${cusPerFamilyDTO.cusPerson.certType}");
	setCombobox("sex","SEX","${cusPerFamilyDTO.cusPerson.sex}");
	setCombobox("education","EDUCATION","${cusPerFamilyDTO.cusPerson.education}");
	setCombobox("degree","DEGREE","${cusPerFamilyDTO.cusPerson.degree}");
	setCombobox("pol_face","POL_FACE","${cusPerFamilyDTO.cusPerson.polFace}");
	setCombobox("occ_name","OCC_NAME","${cusPerFamilyDTO.cusPerson.occName}");
	setCombobox("trade","TRADE","${cusPerFamilyDTO.cusPerson.trade}");
	setCombobox("pay_way","PAY_WAY","${cusPerFamilyDTO.cusPerson.payWay}");
	setCombobox("staff_num","STAFF_NUM","${cusPerFamilyDTO.cusPerson.staffNum}");
	if(flag == '3'){
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
		$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
});
function quxiao(){
	parent.removePanel();
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<%-- <%@ include file="cus_per_tab.jsp"%> --%>
<jsp:include page="cus_per_tab.jsp">
<jsp:param value="2" name="tab"/>
</jsp:include>
<div>
<form id="familyInfo" action="savePerFamily.action" method="POST">
<input type="hidden"  name="cusPerFamily.status" value="1"/>
<input type="hidden" name="cusPerFamily.cusPerBase.pid" value="${perId}"/>
<c:if test="${not empty cusPerFamilyDTO.cusPerFamily}">
    <input type="hidden" name="cusPerFamily.pid" value="${cusPerFamilyDTO.cusPerFamily.pid}"/>
</c:if>

<div class=" easyui-panel" title="填写个人家庭信息" data-options="collapsible:true">
<table class="cus_table2">
  <tr>
    <td class="align_right" style="width: 120px;">是否户主：</td>
	       <td>
   			  <input type="radio" name="cusPerFamily.houseMain" value="1" <c:if test="${cusPerFamilyDTO.cusPerFamily.houseMain==1 || empty cusPerFamilyDTO.cusPerFamily.houseMain}">checked </c:if>/>是&nbsp;&nbsp;&nbsp;&nbsp;
   			  <input type="radio" name="cusPerFamily.houseMain" value="2" <c:if test="${cusPerFamilyDTO.cusPerFamily.houseMain==2}">checked </c:if>/>否
		   </td>
		   <td colspan="2"></td>
  </tr>
  <tr>
    <td class="align_right">子女数：</td>
	<td><input type="text"  class="text_style" name="cusPerFamily.childNum" value="${cusPerFamilyDTO.cusPerFamily.childNum}"/></td>
	<td colspan="2"></td>
  </tr>
  <tr>
    <td class="align_right" >居住状况：</td>
	<td>
		<select id="live_status" class="easyui-combobox" editable="false" name="cusPerFamily.liveStatus" style="width:220px;"/>
	</td>
	<td class="align_right">月租：</td>
	<td><input type="text" class="text_style" data-options="validType:'integer'" name="cusPerFamily.monthRent" value="${cusPerFamilyDTO.cusPerFamily.monthRent}"/>&nbsp;元</td>
  </tr>
  <tr>
    <td class="align_right">现住宅形式：</td>
	<td>
		<select id="house_shape" class="easyui-combobox" editable="false" name="cusPerFamily.houseShape" style="width:220px;" />
	</td>
	<td class="align_right">现住宅面积：</td>
	<td><input type="text"  class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamily.houseArea" value="${cusPerFamilyDTO.cusPerFamily.houseArea}"/>&nbsp;平方米</td>
  </tr>
</table>
</div>

<div class="pt10"></div>
<div class=" easyui-panel" title="填写个人家庭经济情况" data-options="collapsible:true">
	<input type="hidden"  name="cusPerFamilyFinance.status" value="1"/>
    <input type="hidden" name="cusPerFamilyFinance.cusPerBase.pid" value="${perId}"/>
    <c:if test="${not empty cusPerFamilyDTO.cusPerFamilyFinance}">
       	<input type="hidden" name="cusPerFamilyFinance.pid" value="${cusPerFamilyDTO.cusPerFamilyFinance.pid}"/>
    </c:if>
<table class="cus_table2">
   <tr>
      <td class="align_right" style="width: 140px;">月供款(元)：</td>
      <td><input type="text"  class="easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamilyFinance.monthlyPayment" value="${cusPerFamilyDTO.cusPerFamilyFinance.monthlyPayment}"/></td>
      <td class="align_right" style="width: 140px;">信用卡透支金额(元)：</td>
      <td><input type="text"  class="easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamilyFinance.overdraft" value="${cusPerFamilyDTO.cusPerFamilyFinance.overdraft}"/></td>
   </tr>
   <tr>
      <td class="align_right" style="width: 140px;">总资产(万元)：</td>
      <td><input type="text"  class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamilyFinance.totalAssets" value="${cusPerFamilyDTO.cusPerFamilyFinance.totalAssets}"/></td>
      <td class="align_right" style="width:120px;">总负债(万元)：</td>
      <td><input type="text"  class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamilyFinance.totalLiab" value="${cusPerFamilyDTO.cusPerFamilyFinance.totalLiab}"/></td>
   </tr>
   <tr>
      <td class="align_right">资产明细：</td>
      <td style="height: 60px;"><textarea rows="4" cols="60"  name="cusPerFamilyFinance.assetsDetail" class="easyui-validatebox" >${cusPerFamilyDTO.cusPerFamilyFinance.assetsDetail}</textarea></td>
      <td class="align_right">负债明细：</td>
      <td style="height: 60px;"><textarea rows="4" cols="60"  name="cusPerFamilyFinance.liabDetail" class="easyui-validatebox" >${cusPerFamilyDTO.cusPerFamilyFinance.liabDetail}</textarea></td>
   </tr>
   <tr>
      <td class="align_right" >家庭财产(万元)：</td>
      <td><input type="text"  class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamilyFinance.familyAssets" value="${cusPerFamilyDTO.cusPerFamilyFinance.familyAssets}"/></td>
      <td class="align_right" >年总支出(万元)：</td>
      <td><input type="text"  class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerFamilyFinance.yearPay" value="${cusPerFamilyDTO.cusPerFamilyFinance.yearPay}"/></td>
   </tr>
   <tr>
      <td class="align_right" >月平均工资(元)：</td>
      <td><input type="text"  class="easyui-numberbox" precision="2" name="cusPerFamilyFinance.monthWage" value="${cusPerFamilyDTO.cusPerFamilyFinance.monthWage}"/></td>
      <td class="align_right" >家庭税后月收入(元)：</td>
      <td><input type="text"  class="easyui-numberbox" precision="2"  name="cusPerFamilyFinance.familyIncome" value="${cusPerFamilyDTO.cusPerFamilyFinance.familyIncome}"/></td>
   </tr>
   <tr>
      <td class="align_right" >家庭月可支配资金(元)：</td>
      <td><input type="text"  class="easyui-numberbox" precision="2" name="cusPerFamilyFinance.familyControl" value="${cusPerFamilyDTO.cusPerFamilyFinance.familyControl}"/></td>
      <td colspan=" 2"></td>
   </tr>
</table>
</div>

	<input type="hidden"  name="cusPerson.status" value="1"/>
    <input type="hidden"  name="cusPerson.relationType" value="2"/>
    <input type="hidden" name="cusPerson.cusAcct.pid" value="${acctId}"/>
    <c:if test="${not empty cusPerFamilyDTO.cusPerson}">
       <input type="hidden" name="cusPerson.pid" value="${cusPerFamilyDTO.cusPerson.pid}"/>
    </c:if>
    <c:if test="${marrStatus eq 'MARRIED'}">
<div class="pt10"></div>
<div class=" easyui-panel" title="配偶信息" data-options="collapsible:true">
 <table class="cus_table2">
   <tr>
	 <td class="align_right" style="width:120px;">中文名称：</td>
	 <td><input type="text" class="text_style easyui-validatebox" id="chinaName" name="cusPerson.chinaName" value="${cusPerFamilyDTO.cusPerson.chinaName}"/></td>
	 <td class="align_right" style="width:105px;">拼音/英文姓名：</td>
	 <td><input type="text" class="text_style" id="engName" name="cusPerson.engName" value="${cusPerFamilyDTO.cusPerson.engName}"/></td>
  </tr>
  <tr>
    <td class="align_right">性别：</td>
    <td>
    	<%-- <input type="hidden" name="cusPerson.sex" value="${cusPerBaseDTO.cusPerson.sex }" id="sex_text"> --%>
		<select id="sex" class="easyui-combobox" name="cusPerson.sex" editable="false" style="width:220px;"  />
    </td>
    <td class="align_right">关系：</td>
    <td>
    	<!-- <select id="relation" class="easyui-combobox" name="cusPerson.relation" style="width:220px;" /> -->
    	配偶
    </td>
   </tr>
   <tr>
     <td class="align_right">证件类型：</td>
	 <td>
		<select id="cert_type" class="easyui-combobox" editable="false" name="cusPerson.certType" style="width:220px;"  />
	 </td>
	 <td class="align_right">证件号码：</td>
	 <td><input type="text" id="cert_Number" class="text_style easyui-validatebox" name="cusPerson.certNumber" value="${cusPerFamilyDTO.cusPerson.certNumber}" onchange="getBrithDate();"/></td>
   </tr>
   <tr>
     <td class="align_right">出生日期：</td>
     	
	 <td>
	 <input id="birth_Date" name="cusPerson.birthDate" editable="false" class="text_style easyui-datebox" value="${cusPerFamilyDTO.cusPerson.birthDate}"/></td>
   </tr>
   <tr>
      <td class="align_right">最高学历：</td>
      <td>
		 <select id="education" class="easyui-combobox" editable="false" name="cusPerson.education" style="width:220px;"/>
	  </td>
	  <td class="align_right">毕业院校：</td>
      <td>
		<input type="text" class="text_style"  name="cusPerson.graduSchool" value="${cusPerFamilyDTO.cusPerson.graduSchool}"/>
      </td>
   </tr>
   <tr>
      <td class="align_right">最高学位：</td>
      <td>
		 <select id="degree" class="easyui-combobox" editable="false" name="cusPerson.degree" style="width:220px;"   />
	  </td>
	  <td class="align_right">政治面貌：</td>
      <td>
			<select id="pol_face" class="easyui-combobox" editable="false" name="cusPerson.polFace" style="width:220px;" />
	  </td>
   </tr>
   <tr>
     <td class="align_right">职业：</td>
     <td><input type="text" class="text_style easyui-validatebox" name="cusPerson.occupation" value="${cusPerFamilyDTO.cusPerson.occupation}"/></td>	
     <td class="align_right" >是否知悉此贷款：</td>
     <td>
	    <input type="radio" name="cusPerson.knowLoan" value="1" <c:if test="${cusPerFamilyDTO.cusPerson.knowLoan !=2}">checked </c:if>/>是&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="radio" name="cusPerson.knowLoan" value="2" <c:if test="${cusPerFamilyDTO.cusPerson.knowLoan==2}">checked </c:if>/>否
	 </td>
   </tr>
   <tr>
     <td class="align_right">工作单位：</td>
     <td colspan="3"><input type="text" class="text_style easyui-validatebox" style="width:603px;"  name="cusPerson.workUnit" value="${cusPerFamilyDTO.cusPerson.workUnit}"/></td>
   </tr>
   <tr>
     <td class="align_right">单位地址：</td>
     <td><input type="text" class="text_style easyui-validatebox"  name="cusPerson.unitAddr" value="${cusPerFamilyDTO.cusPerson.unitAddr}"/></td>
     <td class="align_right">邮政编码：</td>
     <td><input type="text" class="text_style easyui-validatebox"    name="cusPerson.unitCode"  value="${cusPerFamilyDTO.cusPerson.unitCode}"/></td>
   </tr>
   <tr>
     <td class="align_right">工作单位注册资本：</td>
     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator=","  name="cusPerson.regCapital" value="${cusPerFamilyDTO.cusPerson.regCapital}"/>&nbsp;万元</td>
     <td class="align_right">主营业务：</td>
     <td><input type="text" class="text_style"  name="cusPerson.mainBus" value="${cusPerFamilyDTO.cusPerson.mainBus}"/></td>
   </tr>
   <tr>
     <td class="align_right" >电话：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="validType:'phone'" name="cusPerson.mobilePhone" value="${cusPerFamilyDTO.cusPerson.mobilePhone}"/></td>
     <td class="align_right" >手机：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="validType:'telephone'"  name="cusPerson.telephone" value="${cusPerFamilyDTO.cusPerson.telephone}"/></td>
   </tr>
   <tr>
	   <td class="align_right">户籍地址：</td>
	   <td colspan="4">
	   <input type="text" style="width: 400px;" class="text_style easyui-validatebox" name="cusPerson.censusAddr" value="${cusPerFamilyDTO.cusPerson.censusAddr}" />
	    	<span class="pl9">邮编：</span>
	   <input type="text" style="width:50px" class="text_style" name="cusPerson.censusCode" value="${cusPerFamilyDTO.cusPerson.censusCode}"/></td>
   </tr>
	<tr>
		   <td class="align_right">通信地址：</td>
		   <td colspan="4" >
		   <input type="text" style="width: 400px;" class="text_style easyui-validatebox" name="cusPerson.commAddr" value="${cusPerFamilyDTO.cusPerson.commAddr}" />
		   <span class="pl9">邮编：</span>
		   <input type="text" style="width:50px" class="text_style" name="cusPerson.commCode" value="${cusPerFamilyDTO.cusPerson.commCode}"/></td>
	</tr>
	<tr>
		   <td class="align_right">现居住地址：</td>
		   <td colspan="4">
		   <input type="text" style="width: 400px;" class="text_style easyui-validatebox" name="cusPerson.liveAddr" value="${cusPerFamilyDTO.cusPerson.liveAddr}" />
		  	 <span class="pl9">邮编：</span>
		   <input type="text" style="width:50px"  class="text_style" name="cusPerson.liveCode" value="${cusPerFamilyDTO.cusPerson.liveCode}"/></td>
	</tr>
   <tr>
     <td class="align_right" >传真号码：</td>
     <td><input type="text" class="text_style" name="cusPerson.fax" value="${cusPerFamilyDTO.cusPerson.fax}"/></td>
     <td class="align_right" >单位电话：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="validType:'phone'" name="cusPerson.unitPhone" value="${cusPerFamilyDTO.cusPerson.unitPhone}"/></td>
   </tr>
    <tr>
       <td class="align_right" >职位：</td>
       <td><input type="text" class="text_style" name="cusPerson.job" value="${cusPerFamilyDTO.cusPerson.job}"/>
       </td>
       <td class="align_right">职务：</td>
       <td><input type="text" class="text_style" name="cusPerson.workService" value="${cusPerFamilyDTO.cusPerson.workService}"/>
	   </td>
	</tr>
	<tr>
       <td class="align_right" >职称：</td>
       <td>
		 <select id="occ_name" class="easyui-combobox" editable="false" name="cusPerson.occName" style="width:220px;" />
       </td>
       <td class="align_right" >是否公务员：</td>
       <td>
  			  <input type="radio" name="cusPerson.servant" value="1"  <c:if test="${cusPerFamilyDTO.cusPerson.servant==1}">checked </c:if>/>是
  			  <input type="radio" name="cusPerson.servant"  value="2"  <c:if test="${cusPerFamilyDTO.cusPerson.servant==2  || empty cusPerFamilyDTO.cusPerson.servant}">checked </c:if>/>否
	   </td>
	</tr>
	<tr>
       <td class="align_right" >所属部门：</td>
       <td><input type="text" class="text_style" name="cusPerson.deptment" value="${cusPerFamilyDTO.cusPerson.deptment}"/></td>
       <td class="align_right" >是否缴社保：</td>
       <td>
  			  <input type="radio" name="cusPerson.paySocSec" value="1" <c:if test="${cusPerFamilyDTO.cusPerson.paySocSec==1 || empty cusPerFamilyDTO.cusPerson.paySocSec}">checked </c:if>/>是
  			  <input type="radio" name="cusPerson.paySocSec"  value="2" <c:if test="${cusPerFamilyDTO.cusPerson.paySocSec==2}">checked </c:if>/>否
	   </td>
	 </tr>
	 <tr>
	     <td class="align_right" >月收入：</td>
	     <td><input type="text"  class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="cusPerson.monthIncome" value="${cusPerFamilyDTO.cusPerson.monthIncome}"/>&nbsp;万元</td>
	     <td class="align_right" >所属行业：</td>
	     <td>
			<select id="trade" class="easyui-combobox" editable="false" name="cusPerson.trade" style="width:220px;"  />
	       </td>
	 </tr>
	 <tr>
	      <td class="align_right" >入职时间：</td>
	      <td><input type="text" class="text_style easyui-datebox" editable="false" name="cusPerson.entryTime" value="${cusPerFamilyDTO.cusPerson.entryTime}"/>&nbsp;</td>
	      <td class="align_right" >发薪形式：</td>
	      <td>
			<select id="pay_way" class="easyui-combobox" editable="false" name="cusPerson.payWay" style="width:220px;" />
	       </td>
	   </tr>
	   <tr>
	      <td class="align_right" >每月发薪日：</td>
	      <td>
	         <select class="select_style easyui-combobox" editable="false" name="cusPerson.monthPayDay">
				<option value="1" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==1 }">selected </c:if>>1</option>
				<option value="2" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==2 }">selected </c:if>>2</option>
				<option value="3" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==3 }">selected </c:if>>3</option>
				<option value="4" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==4 }">selected </c:if>>4</option>
				<option value="5" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==5 }">selected </c:if>>5</option>
				<option value="6" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==6 }">selected </c:if>>6</option>
				<option value="7" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==7 }">selected </c:if>>7</option>
				<option value="8" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==8 }">selected </c:if>>8</option>
				<option value="9" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==9 }">selected </c:if>>9</option>
				<option value="10" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==10 }">selected </c:if>>10</option>
				<option value="11" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==11 }">selected </c:if>>11</option>
				<option value="12" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==12 }">selected </c:if>>12</option>
				<option value="13" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==13 }">selected </c:if>>13</option>
				<option value="14" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==14 }">selected </c:if>>14</option>
				<option value="15" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==15 }">selected </c:if>>15</option>
				<option value="16" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==16 }">selected </c:if>>16</option>
				<option value="17" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==17 }">selected </c:if>>17</option>
				<option value="18" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==18 }">selected </c:if>>18</option>
				<option value="19" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==19 }">selected </c:if>>19</option>
				<option value="20" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==20 }">selected </c:if>>20</option>
				<option value="21" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==21 }">selected </c:if>>21</option>
				<option value="22" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==22 }">selected </c:if>>22</option>
				<option value="23" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==23 }">selected </c:if>>23</option>
				<option value="24" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==24 }">selected </c:if>>24</option>
				<option value="25" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==25 }">selected </c:if>>25</option>
				<option value="26" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==26 }">selected </c:if>>26</option>
				<option value="27" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==27 }">selected </c:if>>27</option>
				<option value="28" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==28 }">selected </c:if>>28</option>
				<option value="29" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==29 }">selected </c:if>>29</option>
				<option value="30" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==30 }">selected </c:if>>30</option>
				<option value="31" <c:if test="${cusPerFamilyDTO.cusPerson.monthPayDay==31 }">selected </c:if>>31</option>
			 </select> 	
	       </td>
	      <td class="align_right">雇员人数：</td>
	      <td>
			 <select id="staff_num" class="easyui-combobox" editable="false" name="cusPerson.staffNum" style="width:220px;" />
	       </td>
	   </tr>
 </table>
  </div>
 </c:if>
   <div class="pt10" style="text-align: center;padding-bottom:150px;"><input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveFamilyInfo();"/>&nbsp;&nbsp;&nbsp; <input type="button" class="cus_save_btn" name="cancel" value="取     消" onclick="quxiao();"/></div>
	
 </form>
 </div>	    
</div>
</div>	
</div>
</div>
</body>