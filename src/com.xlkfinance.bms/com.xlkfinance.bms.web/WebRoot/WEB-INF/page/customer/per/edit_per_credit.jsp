<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/sitedata.bas.js" charset="utf-8"></script>

<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/customer/css/rewriteStyle.css" type="text/css"></link>
<script type="text/javascript">
function saveCreditInfo(){
   $('#creditInfo').form('submit', {
		url : "savePerCredit.action",
		onSubmit : function() {return  $(this).form('validate'); },
		success : function(result) {
			alert("保存成功！");
			window.location='${basePath }customerController/listPerCredit.action?acctId=${acctId}';
		},error : function(result){
			alert("保存失败！");
		}
	}); 
   
}

function creditCancel(){
	window.location='${basePath }customerController/listPerCredit.action?acctId=${acctId}';
}


function loadprovince(provincediv,citydiv){
	var province = document.getElementById(provincediv);
	var city = document.getElementById(citydiv);
	for ( var i = 0; i < arrCity.length; i++) {
		province.options[i] = new Option(arrCity[i].name, arrCity[i].name);
	}
	city.options[0] = new Option("请选择", "请选择");
}

function loadcity(provincediv,citydiv){
	var province = document.getElementById(provincediv);
	var city = document.getElementById(citydiv);
	var region1_obj = arrCity[province.selectedIndex];
	var region2_arr = region1_obj.sub;
	city.options.length = 0;
	for ( var i = 0; i < region2_arr.length; i++) {
		city.options[i] = new Option(region2_arr[i].name, region2_arr[i].name);
	}
}

 function editprov(){
	<c:if test="${not empty cusPerCreditDTO.creditAddrs}">
	<c:forEach var="addr" items="${cusPerCreditDTO.creditAddrs}" varStatus="status">
		var prov="${addr.province}";
		var city="${addr.city}";
		$("#province${status.index+1}").val(prov);
		loadcity("province${status.index+1}","city${status.index+1}");
		$("#city${status.index+1}").val(city);
	</c:forEach>
	</c:if>
} 
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<div>
<form id="creditInfo" action="savePerCredit.action" method="POST">
<input type="hidden" name="cusPerCredit.pid" value="${cusPerCreditDTO.cusPerCredit.pid}"/>
<input type="hidden" name="cusPerCredit.status" value="1"/>
<input type="hidden" name="cusPerCredit.cusPerBase.pid" value="${perId}"/>

<div class=" easyui-panel" title="征信记录" data-options="collapsible:true">
<table class="cus_table2">
  <tr>
     <td class="align_right" >信用报告编号：</td>
	 <td colspan="3"><input type="text" class="text_style easyui-validatebox" name="cusPerCredit.creNo" value="${cusPerCreditDTO.cusPerCredit.creNo}"/></td>  
     <%-- <td class="align_right"  style="width:120px;"><span class="cus_red">*</span>查询日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="cusPerCredit.queryDate" value="${cusPerCreditDTO.cusPerCredit.queryDate}" data-options="required:true"/></td>   --%>
     <td class="align_right" style="width:120px;"><span class="cus_red">*</span>报告查询日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="cusPerCredit.repQueryDate" value="${cusPerCreditDTO.cusPerCredit.repQueryDate}" data-options="required:true"/></td>  
  </tr>
  
</table>
</div>
<c:if test="${empty cusPerCreditDTO.creditAddrs}">
<c:forEach begin="0" end="1" varStatus="status">
 <c:if test="${status.index==0 }">
 	<div class="pt10"></div>
	<div class=" easyui-panel" title="现住地址信息" data-options="collapsible:true">
	<input type="hidden" name="creditAddrs[${status.index}].addrType" value="1"/>
</c:if>
<c:if test="${status.index==1 }">
		<div class="pt10"></div>
		<div class=" easyui-panel" title="前住地址信息" data-options="collapsible:true">
		<input type="hidden" name="creditAddrs[${status.index}].addrType" value="2"/>
</c:if>
<input type="hidden" name="creditAddrs[${status.index}].status" value="1"/>
<table class="cus_table2">
  <tr>
     <td class="align_right" >省：</td>
	  <td>
         <select  class="select_style" style="border:1px solid #95b8e7;" editable="false" id="province${status.index+1 }" name="creditAddrs[${status.index}].province">
			<option value="1">请选择</option>
	     </select>
	 </td>
	 <td class="align_right" >市：</td>
	  <td> 
         <select  class="select_style" style="border:1px solid #95b8e7;" editable="false" id="city${status.index+1 }"  name="creditAddrs[${status.index}].city">
			<option value="1">请选择</option>
	     </select>
	 </td>  
  </tr>
   <tr>
      <td class="align_right" style="width:120px;">区/县：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].county"/></td>  
	  <td class="align_right" style="width:120px;">路名：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].roadName" /></td>   
  </tr>
  <tr>
      <td class="align_right" >路号：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].roadNo" /></td>  
	  <td class="align_right" >社区名：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].cmtName" /></td>   
  </tr>
  <tr>
      <td class="align_right" >邮编：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].zipCode" /></td>  
	  <td class="align_right" >居住信息获取时间：</td>
	  <td><input type="text" class="text_style easyui-datebox" name="creditAddrs[${status.index}].liveDate" /></td>   
  </tr>
</table>
</div>
</c:forEach>

</c:if>

<c:if test="${not empty cusPerCreditDTO.creditAddrs}">
<c:forEach var="addr" items="${cusPerCreditDTO.creditAddrs}" varStatus="status">
<div class="pt10"></div>
 	<c:if test="${addr.addrType==1 }">
		<div class=" easyui-panel" title="现住地址信息" data-options="collapsible:true">
	</c:if>
	 <c:if test="${addr.addrType==2 }">
		<div class=" easyui-panel" title="前住地址信息" data-options="collapsible:true">
	</c:if>

<input type="hidden" name="creditAddrs[${status.index}].pid" value="${addr.pid }"/>
<input type="hidden" name="creditAddrs[${status.index}].status" value="${addr.status }"/>
<input type="hidden" name="creditAddrs[${status.index}].addrType" value="${addr.addrType }"/>
<input type="hidden" name="creditAddrs[${status.index}].cusPerCredit.pid" value="${addr.cusPerCredit.pid }"/>
<table class="cus_table2">
  <tr>
     <td class="align_right" >省：</td>
	  <td>
         <select  class="select_style"  style="border:1px solid #95b8e7;" editable="false" id="province${status.index+1 }" name="creditAddrs[${status.index}].province">
			<option value="1">请选择</option>
	     </select>
	 </td>
	 <td class="align_right"  >市：</td>
	  <td>
         <select  class="select_style" style="border:1px solid #95b8e7;" editable="false" id="city${status.index+1 }" name="creditAddrs[${status.index}].city">
			<option value="1">请选择</option>
	     </select>
	 </td>  
  </tr>
   <tr>
      <td class="align_right" style="width:120px;">区/县：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].county"  value="${addr.county}"/></td>  
	  <td class="align_right" style="width:120px;">路名：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].roadName" value="${addr.roadName}"/></td>   
  </tr>
  <tr>
      <td class="align_right" >路号：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].roadNo" value="${addr.roadNo}"/></td>  
	  <td class="align_right" >社区名：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].cmtName" value="${addr.cmtName}"/></td>   
  </tr>
  <tr>
      <td class="align_right" >邮编：</td>
	  <td><input type="text" class="text_style" name="creditAddrs[${status.index}].zipCode" value="${addr.zipCode}"/></td>  
	  <td class="align_right" >居住信息获取时间：</td>
	  <td><input type="text" class="text_style easyui-datebox" name="creditAddrs[${status.index}].liveDate" value="${addr.liveDate}"/></td>   
  </tr>
</table>
</div>
</c:forEach>

</c:if>

<div class="pt10"></div>
<div class=" easyui-panel" title="使用情况" data-options="collapsible:true">
<table class="cus_table2">
   
    <tr>
      <td class="align_right" style="width:200px;"><span class="cus_red">*</span>银行信贷账户数：</td>
	  <td><input type="text"  class="text_style2 easyui-validatebox" data-options="required:true,validType:'integ'" name="cusPerCredit.creAccNum" value="${empty cusPerCreditDTO.cusPerCredit.creAccNum ?0:cusPerCreditDTO.cusPerCredit.creAccNum}"/></td>  
	  <td class="align_right" style="width:200px;"><span class="cus_red">*</span>银行授信额度：</td>
	  <td><input type="text" id="creQuota" class="text_style2 easyui-numberbox" data-options="required:true,min:0.00" min='0.00' precision="2" groupSeparator=","  name="cusPerCredit.creQuota" value="${cusPerCreditDTO.cusPerCredit.creQuota >0?cusPerCreditDTO.cusPerCredit.creQuota:0.00}"/></td>   
  </tr>
  <tr>
      <td class="align_right" style="width:200px;"><span class="cus_red">*</span>银行信用卡授信额度：</td>
	  <td><input type="text"  class="text_style2 easyui-numberbox" data-options="required:true,min:0.00" precision="2" groupSeparator="," name="cusPerCredit.creditLimit" value="${cusPerCreditDTO.cusPerCredit.creditLimit>0 ?cusPerCreditDTO.cusPerCredit.creditLimit:0.00}"/></td>  
	  <td class="align_right" style="width:200px;"><span class="cus_red">*</span>银行信用卡已用额度：</td>
	  <td><input type="text" id=creditUsedLimit class="text_style2 easyui-numberbox" data-options="required:true,min:0.00" min='0.00' precision="2" groupSeparator=","  name="cusPerCredit.creditUsedLimit" value="${cusPerCreditDTO.cusPerCredit.creditUsedLimit >0?cusPerCreditDTO.cusPerCredit.creditUsedLimit:0.00}"/></td>   
  </tr>
  <tr>
      <td class="align_right" ><span class="cus_red">*</span>银行贷款余额：</td>
	  <td><input type="text" id="loanSurp" class="text_style2 easyui-numberbox" precision="2" data-options="required:true" groupSeparator="," name="cusPerCredit.loanSurp" value="${empty cusPerCreditDTO.cusPerCredit.loanSurp?0.0:cusPerCreditDTO.cusPerCredit.loanSurp}"/></td>  
	  <td class="align_right" ><span class="cus_red">*</span>最近24月开立的账户数：</td>
	  <td><input type="text" class="text_style2 easyui-validatebox" data-options="required:true,validType:'integ'" name="cusPerCredit.openAccNum" value="${empty cusPerCreditDTO.cusPerCredit.openAccNum?0:cusPerCreditDTO.cusPerCredit.openAccNum}"/></td>   
  </tr>
  <tr>
     <td class="align_right" ><span class="cus_red">*</span>最近24月除结清外其他任何<br>形态的透支账户数：</td>
	  <td><input type="text" class="text_style2 easyui-validatebox" data-options="required:true,validType:'integ'" name="cusPerCredit.otherAccNum" value="${empty cusPerCreditDTO.cusPerCredit.otherAccNum?0:cusPerCreditDTO.cusPerCredit.otherAccNum}"/></td>  
	 <td class="align_right" ><span class="cus_red">*</span>最近24月结清的账户数：</td>
	  <td><input type="text" class="text_style2 easyui-validatebox" data-options="required:true,validType:'integ'" name="cusPerCredit.clearAccNum" value="${empty cusPerCreditDTO.cusPerCredit.clearAccNum?0:cusPerCreditDTO.cusPerCredit.clearAccNum}"/></td>   
  </tr>
  
  <c:if test="${fn:length(cusPerCreditDTO.deficits)>=fn:length(cusPerCreditDTO.settles)}">
  	<c:forEach var="def" items="${fn:length(cusPerCreditDTO.deficits)>=fn:length(cusPerCreditDTO.settles)?cusPerCreditDTO.deficits:cusPerCreditDTO.settles}" varStatus="i">
  	<tr>
  		<td class="align_right"><span class="cus_red">*</span>
  			<c:if test="${fn:length(cusPerCreditDTO.deficits)>=(i.index+1) }">
		 		${cusPerCreditDTO.deficits[i.index].dayNameDesc}：
		 	</c:if>
		 	<c:if test="${fn:length(cusPerCreditDTO.deficits)<(i.index+1) }">
		 		&nbsp;
		 	</c:if>
  		</td>
  		<td><span class="cus_red">*</span>
  			<c:if test="${fn:length(cusPerCreditDTO.deficits)>=(i.index+1) }">
  			<c:if test="${not empty cusPerCreditDTO.cusPerCredit }">
			 	<input type="hidden" class="text_style2" name="deficits[${i.index}].pid" value="${cusPerCreditDTO.deficits[i.index].pid }" />
			 	<input type="hidden" class="text_style2" name="deficits[${i.index}].dayType" value="1" />
			</c:if>
			 	<input type="text" class="text_style2 easyui-validatebox" data-options="validType:'integ'" name="deficits[${i.index}].dayVal" value="${cusPerCreditDTO.deficits[i.index].dayVal==0?'0':cusPerCreditDTO.deficits[i.index].dayVal}"/>
			 	<input type="hidden" class="text_style2" name="deficits[${i.index}].dayNameDesc" value="${cusPerCreditDTO.deficits[i.index].dayNameDesc }" />
			 	<input type="hidden" class="text_style2" name="deficits[${i.index}].dayIndex" value="${i.index+1 }"/>
			 	<input type="hidden" class="text_style2" name="deficits[${i.index}].cusPerCredit.pid" value="${cusPerCreditDTO.deficits[i.index].cusPerCredit.pid }" />
		 	</c:if>
		 	<c:if test="${fn:length(cusPerCreditDTO.deficits)<(i.index+1) }">
		 		&nbsp;
		 	</c:if>
		</td>  
	 	<td class="align_right"><span class="cus_red">*</span>
	 	<c:if test="${fn:length(cusPerCreditDTO.settles)>=(i.index+1) }">
	 		${cusPerCreditDTO.settles[i.index].dayNameDesc}：
	 	</c:if>
	 	<c:if test="${fn:length(cusPerCreditDTO.settles)<(i.index+1) }">
	 		&nbsp;
	 	</c:if>
	 	</td>
		<td><span class="cus_red">*</span>
			<c:if test="${fn:length(cusPerCreditDTO.settles)>=(i.index+1) }">
			<c:if test="${not empty cusPerCreditDTO.cusPerCredit }">
			 	<input type="hidden" class="text_style2" name="settles[${i.index}].pid" value="${cusPerCreditDTO.settles[i.index].pid}"/>
			 	<input type="hidden" class="text_style2" name="settles[${i.index}].dayType" value="2"/>
			</c:if>
			 	<input type="text" class="text_style2 easyui-validatebox" data-options="validType:'integ'" name="settles[${i.index}].dayVal" value="${cusPerCreditDTO.settles[i.index].dayVal==0?'0':cusPerCreditDTO.settles[i.index].dayVal}"/>
			 	<input type="hidden" class="text_style2" name="settles[${i.index}].dayNameDesc" value="${cusPerCreditDTO.settles[i.index].dayNameDesc}" />
			 	<input type="hidden" class="text_style2" name="settles[${i.index}].dayIndex" value="${i.index+1 }"/>
			 	<input type="hidden" class="text_style2" name="settles[${i.index}].cusPerCredit.pid" value="${cusPerCreditDTO.settles[i.index].cusPerCredit.pid }" />
			</c:if>
		 	<c:if test="${fn:length(cusPerCreditDTO.settles)<(i.index+1) }">
		 		&nbsp;
		 	</c:if>
		 </td>
	 </tr>  
  	</c:forEach>
  
  </c:if>
  
</table>
  <div class="pb10" style="text-align: center;">
  	<input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveCreditInfo();"/>&nbsp;&nbsp;&nbsp; 
  	<input type="button" class="cus_save_btn" name="cusCancel" value="取     消" onclick="creditCancel();"/>
  </div>
</div>
</form>
</div>
</div>
<script type="text/javascript">
$(function(){
	loadprovince("province1","city1");
	loadprovince("province2","city2");
	editprov();
	$("#province1").change(function(){
		loadcity("province1","city1");
	});
	$("#province2").change(function(){
		loadcity("province2","city2");
	});
	var pid = "${cusPerCreditDTO.cusPerCredit.pid}";
	if(pid >0){
		
	}else{
		$("#creQuota").numberbox('setValue', 0.00);
		$("#loanSurp").numberbox('setValue', 0.00);
	}
	/*  var creQuota = "${cusPerCreditDTO.cusPerCredit.creQuota}";
	if(creQuota || creQuota<=0){
		$("#creQuota").numberbox('setValue', 0.00);
	}
	
	var loanSurp = "${cusPerCreditDTO.cusPerCredit.loanSurp}";
	if(loanSurp || loanSurp<=0){
		$("#loanSurp").numberbox('setValue', 0.00);
	}  */
});

</script>
</div>
</body>