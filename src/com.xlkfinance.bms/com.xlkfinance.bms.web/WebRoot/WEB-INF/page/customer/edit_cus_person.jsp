<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%-- <%@ include file="/common.jsp"%> --%>
<%@ include file="/layout/taglibs.jsp"%>
<%-- <link rel="stylesheet" href="${ctx}/p/xlkfinance/css/customer/css/rewriteStyle.css" type="text/css"></link> --%>
<script type="text/javascript">
function save(){
	   $('#PersonInfo').form('submit', {
			url : "saveCusPerson.action",
			onSubmit : function() { 
				return $(this).form('validate');
				},
			success : function(result) {
					window.location.reload();
			}
		}); 
}

function cancle(){
	$("#addper").dialog('close');
}

$(function(){
	//绑定下拉框的值
/* 	setCombobox("cert_type","CERT_TYPE","${cusPerson.certType}");
	setCombobox("education","EDUCATION","${cusPerson.education}");
	setCombobox("degree","DEGREE","${cusPerson.degree}");
	setCombobox("pol_face","POL_FACE","${cusPerson.polFace}");
	setCombobox("occ_name","OCC_NAME","${cusPerson.occName}");
	setCombobox("trade","TRADE","${cusPerson.trade}");
	setCombobox("pay_way","PAY_WAY","${cusPerson.payWay}");
	setCombobox("staff_num","STAFF_NUM","${cusPerson.staffNum}"); */
	setCombobox("relationType","RELATION","${cusPerson.relationType}");
	//setCombobox("marr_status","MARR_STATUS","${cusPerson.marrStatus}");
	setCombobox("sex","SEX","${cusPerson.sex}");
	 $('#relationType').combobox({
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType=RELATION',
        method:'get',
        valueField:'lookupVal',
        textField:'lookupDesc',
        loadFilter:function(data){
        	var bbc = new Array();
		 	for(var i=0;i<data.length;i++){
		 		if(parseInt(data[i].lookupVal)>4 || data[i].pid==-1){
		 			if(data[i].lookupVal==""){
		 				data[i].lookupVal=-1;
		 			}
		 			bbc.push(data[i]);
		 		}
		 	}
		 	return bbc;
		}
	});
});

function getBrithDate(){
	var certType = '身份证';
	var certNumber = $("#cert_Number").val();
	if(certType=='身份证'){
		$("#age").attr('readonly',true);
	}else{
		$("#sex").combobox('enable');
		$("#age").attr('readonly',false);
	}
	
	if( certType=='身份证' &&certNumber.length>0 &&(!IdCardValidate(certNumber)))
    {
		$.messager.alert("操作提示","输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。","error");
        $("#cert_Number").val('');
        return false;
    }
	if(certType=='身份证' && certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			$.messager.alert("操作提示","请输入正确的身份证号！","error");
			$("#cert_Number").val('');
	        return false;
		}
		else{
			var year=certNumber.substring(6,10);
			var month=certNumber.substring(10,12);
			var date=certNumber.substring(12,14);
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
		}
	}
};

</script>

<div id="main_body" style="width:700px;float: left;">
<div id="cus_content" class="pt15" style="width:700px;">
<div>
<form id="PersonInfo" action="customerController/saveCusPerson.action" method="POST">

<table class="cus_table" style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
	<input type="hidden" name="pid"	value="${cusPerson.pid }" />
	<input type="hidden" name="status"	value="1" />
	<%-- <input type="hidden" name="relationType"	value="${relationType}" /> --%>
	<input type="hidden" name="cusAcct.pid"	value="${acctId}" />
	<input type="hidden" id="cert_type" name="certType" value="13088" />
	<input type="hidden" id="birth_Date" name="birthDate"  value="${cusPerson.birthDate}"/>
	<input type="hidden" id="age" name="age" value="${cusPerson.age}" />
	<tr>
	 <td class="align_right" style="width:120px;"><span class="cus_red">*</span>中文名称：</td>
	 <td><input type="text" class="text_style easyui-validatebox" data-options="required:true" id="chinaName" name="chinaName" value="${cusPerson.chinaName}"/></td>
	 <td class="align_right"><span class="cus_red">*</span>性别：</td>
    <td>
		<select id="sex" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="sex" style="width:150px;" />
    </td>
	 <%-- <td class="align_right" style="width:105px;">拼音/英文姓名：</td>
	 <td><input type="text" class="text_style" id="engName" name="engName" value="${cusPerson.engName}"/></td> --%>
  </tr>
  <tr>
  	<td class="align_right"><span class="cus_red">*</span>关系：</td>
     <%-- td><input type="text"  name="relation" value="${cusPerson.relation}" class="text_style"/></td> --%>
     <td>
     	<select id="relationType" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="relationType" style="width:150px;"/>
    </td>
    <td class="align_right"><span class="cus_red">*</span>身份证号码：</td>
	 <td><input type="text" id="cert_Number" class="text_style easyui-validatebox" name="certNumber" value="${cusPerson.certNumber}" data-options="required:true" onblur="getBrithDate();"/></td>
  </tr>
  <tr>
  	<td class="align_right" ><span class="cus_red">*</span>手机号码：</td>
     <td><input type="text" class="text_style easyui-validatebox" required="true" data-options="required:true,validType:'telephone'" name="telephone" value="${cusPerson.telephone}"/></td>
  	<td class="align_right">产权占比：</td>
    <td><input type="text" class="text_style2 easyui-numberbox" data-options="min:0,max:100,validType:'number'" precision="2" groupSeparator=","  name="proportionProperty" value="${cusPerson.proportionProperty}"/>%</td>
  </tr>
  <%-- <tr>
    <td class="align_right"><span class="cus_red">*</span>性别：</td>
    <td>
		<select id="sex" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="sex" style="width:220px;" />
    </td>
    <c:if test="${relationType eq 4}">
    <td class="align_right"><span class="cus_red">*</span>是否董事会成员：</td>   
    <td>
    	<input type="hidden" name="relationType"	value="${relationType}" />
        <input type="radio" name="boardMember" value="1" <c:if test="${cusPerson.boardMember==1 || empty cusPerson.boardMember}">checked </c:if>/>是 &nbsp; &nbsp;
	    <input type="radio" name="boardMember"  value="2" <c:if test="${cusPerson.boardMember==2 || empty cusPerson.boardMember}">checked </c:if> />否
    </td>
    </c:if>
     <c:if test="${relationType >4}">
     <td class="align_right"><span class="cus_red">*</span>关系：</td>
     <td><input type="text"  name="relation" value="${cusPerson.relation}" class="text_style"/></td>
     <td>
    	<input id="relationType" class="easyui-combobox" data-options="validType:'selrequired'" name="relationType" style="width:220px;" required="true"/>
    </td>
     </c:if>
   </tr>
   <tr>
     <td class="align_right"><span class="cus_red">*</span>证件类型：</td>
	 <td>
			<select id="cert_type" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="certType" style="width:220px;"   />
	 </td>
	 <td class="align_right"><span class="cus_red">*</span>证件号码：</td>
	 <td><input type="text" id="cert_Number" class="text_style easyui-validatebox" name="certNumber" value="${cusPerson.certNumber}" data-options="required:true" onchange="getBrithDate();"/></td>
   </tr>
   <tr>
     <td class="align_right"><span class="cus_red">*</span>出生日期：</td>
	 <td><input type="text" id="birth_Date" editable="false" class="text_style easyui-datebox" name="birthDate" value="${cusPerson.birthDate}"/></td>
   </tr>
   <tr>
      <td class="align_right">最高学历：</td>
      <td>
			<select id="education" class="easyui-combobox" editable="false" name="education" style="width:220px;"   />
	  </td>
	  <td class="align_right">毕业院校：</td>
      <td>
			<input type="text" class="text_style"  name="graduSchool" value="${cusPerson.graduSchool}"/>
      </td>
   </tr>
   <tr>
      <td class="align_right">最高学位：</td>
      <td>
			<select id="degree" class="easyui-combobox" editable="false" name="degree" style="width:220px;"  />
	  </td>
	  <td class="align_right">政治面貌：</td>
      <td>
			<select id="pol_face" class="easyui-combobox" editable="false" name="polFace" style="width:220px;"   />
	  </td>
   </tr>
   <tr>
     <td class="align_right"><span class="cus_red">*</span>职业：</td>
     <td><input type="text" class="text_style easyui-validatebox" name="occupation" value="${cusPerson.occupation}" data-options="required:true"/></td>	
     <td class="align_right" ><span class="cus_red">*</span>是否知悉此贷款：</td>
     <td>
	    <input type="radio" name="knowLoan" value="1" <c:if test="${cusPerson.knowLoan==1 || empty cusPerson.knowLoan}">checked </c:if>/>是&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="radio" name="knowLoan" value="2" <c:if test="${cusPerson.knowLoan==2}">checked </c:if>/>否
	 </td>
   </tr>
   <tr>
     <td class="align_right"><span class="cus_red">*</span>工作单位：</td>
     <td colspan="3"><input type="text" class="text_style easyui-validatebox" data-options="required:true" style="width:603px;"  name="workUnit" value="${cusPerson.workUnit}"/></td>
   </tr>
   <tr>
     <td class="align_right"><span class="cus_red">*</span>单位地址：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="required:true"  name="unitAddr" value="${cusPerson.unitAddr}"/></td>
     <td class="align_right">邮政编码：</td>
     <td><input type="text" class="text_style easyui-validatebox"    name="unitCode"  value="${cusPerson.unitCode}"/></td>
   </tr>
   <tr>
     <td class="align_right">工作单位注册资本：</td>
     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator=","  name="regCapital" value="${cusPerson.regCapital}"/>&nbsp;万元</td>
     <td class="align_right">主营业务：</td>
     <td><input type="text" class="text_style"  name="mainBus" value="${cusPerson.mainBus}"/></td>
   </tr>
   <tr>
     <td class="align_right" >电话：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="validType:'phone'" name="mobilePhone" value="${cusPerson.mobilePhone}"/></td>
     <td class="align_right" >手机：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="validType:'telephone'" name="telephone" value="${cusPerson.telephone}"/></td>
   </tr>
   <tr>
		   <td class="align_right"><span class="cus_red">*</span>户籍地址：</td>
		   <td colspan="3"><input type="text" style="width: 460px;" class="text_style" name="censusAddr" value="${cusPerson.censusAddr}" data-options="required:true"/>
		   <span class="pl9">邮编：</span>
		   <input type="text" style="width: 60px;" class="text_style" name="censusCode" value="${cusPerson.censusCode}"/></td>
  </tr>
  <tr>
		   <td class="align_right"><span class="cus_red">*</span>通信地址：</td>
		   <td colspan="3"><input type="text" style="width: 460px;" class="text_style easyui-validatebox" name="commAddr" value="${cusPerson.commAddr}" data-options="required:true"/>
		   <span class="pl9">邮编：</span>
		   <input type="text" style="width: 60px;" class="text_style" name="commCode" value="${cusPerson.commCode}"/></td>
  </tr>
  <tr>
		   <td class="align_right"><span class="cus_red">*</span>现居住地址：</td>
		   <td colspan="3"><input type="text" style="width: 460px;" class="text_style easyui-validatebox" name="liveAddr" value="${cusPerson.liveAddr}" data-options="required:true"/>
		   <span class="pl9">邮编：</span>
		   <input type="text" style="width: 60px;" class="text_style" name="liveCode" value="${cusPerson.liveCode}"/></td>
  </tr>
   <tr>
     <td class="align_right" >传真号码：</td>
     <td><input type="text" class="text_style" name="fax" value="${cusPerson.fax}"/></td>
     <td class="align_right" >单位电话：</td>
     <td><input type="text" class="text_style easyui-validatebox" data-options="validType:'phone'" name="unitPhone" value="${cusPerson.unitPhone}"/></td>
   </tr>
    <tr>
       <td class="align_right" >职位：</td>
       <td><input type="text" class="text_style" name="job" value="${cusPerson.job}"/>
       </td>
       <td class="align_right">职务：</td>
       <td><input type="text" class="text_style" name="workService" value="${cusPerson.workService}"/>
	   </td>
	</tr>
	<tr>
       <td class="align_right" >职称：</td>
       <td>
			<select id="occ_name" class="easyui-combobox" editable="false" name="occName" style="width:220px;" />
       </td>
       <td class="align_right" >是否公务员：</td>
       <td>
  			  <input type="radio" name="servant" value="1" <c:if test="${cusPerson.servant==1 }">checked </c:if>/>是
  			  <input type="radio" name="servant"  value="2" <c:if test="${cusPerson.servant==2 || empty cusPerson.servant}">checked </c:if>/>否
	   </td>
	</tr>
	<tr>
       <td class="align_right" >所属部门：</td>
       <td><input type="text" class="text_style" name="deptment" value="${cusPerson.deptment}"/></td>
       <td class="align_right" >是否缴社保：</td>
       <td>
  			  <input type="radio" name="paySocSec" value="1"  <c:if test="${cusPerson.paySocSec==1 || empty cusPerson.paySocSec}">checked </c:if>/>是
  			  <input type="radio" name="paySocSec"  value="2"  <c:if test="${cusPerson.paySocSec==2}">checked </c:if>/>否
	   </td>
	 </tr>
	 <tr>
	     <td class="align_right" >月收入：</td>
	     <td><input type="text" class="text_style2 easyui-numberbox" precision="2" groupSeparator="," name="monthIncome" value="${cusPerson.monthIncome}"/>&nbsp;万元</td>
	     <td class="align_right" >所属行业：</td>
	     <td>
			<select id="trade" class="easyui-combobox" editable="false" name="trade" style="width:220px;" />	
	       </td>
	 </tr>
	 <tr>
	      <td class="align_right" >入职时间：</td>
	      <td><input type="text" class="text_style easyui-datebox" editable="false" name="entryTime" value="${cusPerson.entryTime}"/>&nbsp;</td>
	      <td class="align_right" >发薪形式：</td>
	      <td>
				<select id="pay_way" class="easyui-combobox" editable="false" name="payWay" style="width:220px;"  />	
	       </td>
	   </tr>
	   <tr>
	      <td class="align_right" >每月发薪日：</td>
	      <td>
	         <select class="select_style easyui-combobox" editable="false" name="monthPayDay">
				<option value="1" <c:if test="${cusPerson.monthPayDay==1 }">selected </c:if>>1</option>
				<option value="2" <c:if test="${cusPerson.monthPayDay==2 }">selected </c:if>>2</option>
				<option value="3" <c:if test="${cusPerson.monthPayDay==3 }">selected </c:if>>3</option>
				<option value="4" <c:if test="${cusPerson.monthPayDay==4 }">selected </c:if>>4</option>
				<option value="5" <c:if test="${cusPerson.monthPayDay==5 }">selected </c:if>>5</option>
				<option value="6" <c:if test="${cusPerson.monthPayDay==6 }">selected </c:if>>6</option>
				<option value="7" <c:if test="${cusPerson.monthPayDay==7 }">selected </c:if>>7</option>
				<option value="8" <c:if test="${cusPerson.monthPayDay==8 }">selected </c:if>>8</option>
				<option value="9" <c:if test="${cusPerson.monthPayDay==9 }">selected </c:if>>9</option>
				<option value="10" <c:if test="${cusPerson.monthPayDay==10 }">selected </c:if>>10</option>
				<option value="11" <c:if test="${cusPerson.monthPayDay==11 }">selected </c:if>>11</option>
				<option value="12" <c:if test="${cusPerson.monthPayDay==12 }">selected </c:if>>12</option>
				<option value="13" <c:if test="${cusPerson.monthPayDay==13 }">selected </c:if>>13</option>
				<option value="14" <c:if test="${cusPerson.monthPayDay==14 }">selected </c:if>>14</option>
				<option value="15" <c:if test="${cusPerson.monthPayDay==15 }">selected </c:if>>15</option>
				<option value="16" <c:if test="${cusPerson.monthPayDay==16 }">selected </c:if>>16</option>
				<option value="17" <c:if test="${cusPerson.monthPayDay==17 }">selected </c:if>>17</option>
				<option value="18" <c:if test="${cusPerson.monthPayDay==18 }">selected </c:if>>18</option>
				<option value="19" <c:if test="${cusPerson.monthPayDay==19 }">selected </c:if>>19</option>
				<option value="20" <c:if test="${cusPerson.monthPayDay==20 }">selected </c:if>>20</option>
				<option value="21" <c:if test="${cusPerson.monthPayDay==21 }">selected </c:if>>21</option>
				<option value="22" <c:if test="${cusPerson.monthPayDay==22 }">selected </c:if>>22</option>
				<option value="23" <c:if test="${cusPerson.monthPayDay==23 }">selected </c:if>>23</option>
				<option value="24" <c:if test="${cusPerson.monthPayDay==24 }">selected </c:if>>24</option>
				<option value="25" <c:if test="${cusPerson.monthPayDay==25 }">selected </c:if>>25</option>
				<option value="26" <c:if test="${cusPerson.monthPayDay==26 }">selected </c:if>>26</option>
				<option value="27" <c:if test="${cusPerson.monthPayDay==27 }">selected </c:if>>27</option>
				<option value="28" <c:if test="${cusPerson.monthPayDay==28 }">selected </c:if>>28</option>
				<option value="29" <c:if test="${cusPerson.monthPayDay==29 }">selected </c:if>>29</option>
				<option value="30" <c:if test="${cusPerson.monthPayDay==30 }">selected </c:if>>30</option>
				<option value="31" <c:if test="${cusPerson.monthPayDay==31 }">selected </c:if>>31</option>
			 </select> 	
	       </td>
	      <td class="align_right">雇员人数：</td>
	      <td>
				<select id="staff_num" class="easyui-combobox" editable="false" name="staffNum" style="width:220px;" />	
	       </td>
	   </tr>
	   <tr>
       <td class="align_right" >婚姻状况：</td>
       <td>
		 <select id="marr_status" class="easyui-combobox" editable="false" name="marrStatus"  style="width:150px;" />
       </td>
        <td class="align_right" style="width:100px;">现住址居住时间：</td>
		<td  ><input type="text" class="text_style easyui-numberbox"  name="liveDate" value="${cusPerson.liveDate}"/></td>
	</tr>
	<tr>
       <td class="align_right" >其它号码：</td>
       <td><input type="text" class="text_style" style="width: 240px;" id="otherPhone" name="otherPhone" value="${cusPerson.otherPhone}"/></td>
        <td class="align_right" style="width:100px;">家庭电话：</td>
		<td><input type="text" class="text_style easyui-validatebox" name="familyPhone" data-options="validType:'phone'" value="${cusPerson.familyPhone}"/></td>
	</tr>
		<tr>
		   <td class="align_right">社区名：</td>
		   <td colspan="1"><input type="text" style="width: 151px;" class="text_style" name="communityName" value="${cusPerson.communityName}"/></td>
		     <td class="align_right" style="width: 85px;">社保卡电脑号：</td>
		   <td ><input type="text" class="text_style" name="socSecNumber" value="${cusPerson.socSecNumber}"/></td>	
		</tr> --%>
	   
 </table>
   <div class="pb10" style="text-align: center;">
   	<!-- <input type="button" class="cus_save_btn" name="save" value="保     存" onclick="savePerson();"/>&nbsp;&nbsp;&nbsp;
   	<input type="button" class="cus_save_btn" name="cancel" value="取     消" onclick="cusCancel();"/> -->
   	</div>
</form>	    
</div>
</div>	 
