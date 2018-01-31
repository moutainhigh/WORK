<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
	function saveBaseInfo(){
	var a = $("#comOwnId").val();
	var tempFlag=0;
	var type=false;
	var busLicCert = $("#busLic_Cert").val();
	var cusComId = '${cusComBaseDTO.cusComBase.pid}';
	$.ajax({
		url : '${basePath}customerController/validateBusLicCert.action?busLicCert='
			+ busLicCert
   			+ '&cusComId='
   			+ cusComId,
		type : 'post',
		cache: false,
		success : function(result) {
			var ret = eval("("+result+")");
			var flags=ret.header["flags"]; 
			if(flags>0){
				tempFlag=3;
			}
			if(tempFlag==3){
				alert("系统已存在此营业执照号码！");
				$("#busLic_Cert").focus();
				return type;
			}
			//判断是否先保存了法人基本信息
			else{
					   $('#baseInfo').form('submit', {
							url : "saveComBaseInfo.action",
							onSubmit : function() {
								return $(this).form('validate'); 
							},
							success : function(result) {
								alert("保存成功！");
								var ret = eval("("+result+")");
								var templateId1=ret.header["acctId"];
								var templateId2=ret.header["comId"];
								var flag='${flag}';
								var type='${type}';
								if(type=='4'){
									cancelComBase();
								}
								else{
									window.location='${basePath}customerController/editComBases.action?acctId='+templateId1+'&comId='+templateId2+'&flag=${flag}'+'&type=${type}'+'&currUserPid='+${currUser.pid};
								}
								//cancelComBase();
							},error : function(result){
								alert("保存失败！");
							}
						}); 
					}
		}
	});
	};
	//定义一个方法用于选中datagrid对应的标签Tab zhengxiu
	function reloadTabGrid(title)
    {
        if (parent.$("#layout_center_tabs" ).tabs('exists', title)) {
     	   parent.$( '#layout_center_tabs').tabs('select' , title);
           window.top.reload_Abnormal_Monitor.call();
       }
    }
	function cancelComBase(){
		var type='${type}';
		var flag='${flag}';
		if(type>3){
			var myObj = parent.$('#layout_center_tabs').tabs('getTab','修改个人客户');
			//获取iframe的URL
			var url = myObj[0].innerHTML;			
			url=$(url).attr('src',$(url).attr('src')+'&tab=4')[0];
			reloadTabGrid( "修改个人客户" );//调用刷新datagrid 方法 zhengxiu
			
			//打开数据
			/* 
			parent.layout_center_addTabFun({
				title : '修改个人客户', //tab的名称
				closable : true, //是否显示关闭按钮
				content : url,//加载链接
				falg:true
			});   */
			if(type=='4'){
				parent.$('#layout_center_tabs').tabs('close','新增个人旗下公司');
			}else if(type=="5"){
				parent.$('#layout_center_tabs').tabs('close','修改个人旗下公司');
			}
		}
		else{
		var myObj = parent.$('#layout_center_tabs').tabs('getTab','企业客户管理');
		//获取iframe的URL
		var url = myObj[0].innerHTML;
		//打开数据
		parent.layout_center_addTabFun({
			title : '企业客户管理', //tab的名称
			closable : true, //是否显示关闭按钮
			content : url,//加载链接
			falg:true
		});
		if(flag=='1'){
			parent.$('#layout_center_tabs').tabs('close','新增企业客户');
		}else if(flag=="2"){
			parent.$('#layout_center_tabs').tabs('close','修改企业客户');
		}
		}
	}

  function addLineBtn(){
	  var tb = document.getElementById("addtr");
	  var trIndex=$("#addtr tr:last").attr("name");
	  var trLength=$("#addtr tr").length-2;
	  trIndex++;
      var str='<tr id="tr'+trIndex+'" name="'+trIndex+'">'+
            '<td><input type="checkbox" name="isChecked" id="isChecked" class="isChecked" value="'+trIndex+'"/></td>'+
         '<td>'+trLength+'</td>'+
         '<td>'+
          '<select  class="select_style" name="cusComShares['+trIndex+'].shareType" id="share_Type_'+trIndex+'"  class="text_style" style="width:100px;">'+
// 		  '<option value="1">企业</option>'+
// 		  '<option value="2">个人</option>'+
		 	'</select>'+
		  '</td>'+
		  '<td>'+
		     '<input type="text" name="cusComShares['+trIndex+'].shareName" value="${cusComShares.shareName}"  class="text_style" style="width:100px;"/>'+
		  '</td>'+	
		   '<td>'+
		     '<input type="text" name="cusComShares['+trIndex+'].invMoney" value="${cusComShares.invMoney}"  class="text_style" style="width:100px;"/>'+
		  '</td>'+
		  ' <td>'+
		    '<select  class="select_style" name="cusComShares['+trIndex+'].invWay" id="inv_Way_'+trIndex+'"  class="text_style" style="width:100px;">'+
// 		   '<option value="1">现金</option>'+
// 		   '<option value="2">机械设备</option>'+
		 	'</select>'+
		  '</td>'+		
		   '<td>'+
		     '<input type="text" name="cusComShares['+trIndex+'].shareRatio" value="${cusComShares.shareRatio}"  class="text_style" style="width:100px;"/>'+
		  '</td>'+
		  '<td>'+
		     '<input type="text" name="cusComShares['+trIndex+'].remark" value="${cusComShares.remark}"  class="text_style" style="width:100px;"/>'+
		  '</td>'+
        '</tr>';
	$("#addtr").append(str);
	setCombobox("share_Type_"+trIndex,"SHARE_TYPE","");  
	setCombobox("inv_Way_"+trIndex,"FUND_WAY","");  
}
function deleteLineBtn(){
		var isChecked = document.getElementsByName("isChecked");
		var flag=false;
		var num=isChecked.length;
			 for(var i=num-1;i>=0;i--){
				 if(isChecked[i].checked){
					 if(confirm("确定要删除此数据？")){
					 $("#tr"+isChecked[i].value).remove();}
					 flag=true;
			 }
		}
		 if(flag==false){
			 alert("请选择一条数据删除！");
		 }
	 }
//绑定赋值
$(function(){
	 setCombobox("eco_Trade","ECO_TRADE","${cusComBaseDTO.cusComBase.ecoTrade}");//1:id 2:数据字典 3：赋值 
	 setCombobox("staff_Num","STAFF_NUM","${cusComBaseDTO.cusComBase.staffNum}");
	 setCombobox("reg_Currency","REG_CURRENCY","${cusComBaseDTO.cusComBase.regCurrency}");
	 setCombobox("mge_SiteType","MGE_SITE_TYPE","${cusComBaseDTO.cusComBase.mgeSiteType}"); 
	 setCombobox("unit_nature","UNIT_NATURE","${cusComBaseDTO.cusComBase.unitNature}"); 
	 setCombobox("com_all_nature","UNIT_NATURE","${cusComBaseDTO.cusComBase.comAllNature}"); 
	 setCombobox("cus_Level","CUS_LV","${cusComBaseDTO.cusComBase.cusLevel}"); 
	 setCombobox("cpy_Scale","ENTERPRISE_SCALE","${cusComBaseDTO.cusComBase.cpyScale}"); 
	 setCombobox("basHou_Status","BASIC_USER_STATUS","${cusComBaseDTO.cusComBase.basHouStatus}"); 
	 setCombobox("org_Status","ENTERPRISE_STATUS","${cusComBaseDTO.cusComBase.orgStatus}"); 
	
	 setCombobox("share_Type_1","SHARE_TYPE","");
	 setCombobox("inv_Way_1","FUND_WAY","");
	 <c:forEach var="share" items="${cusComBaseDTO.cusComShares}" varStatus="status">
		setCombobox("share_Type_${status.index+1}","SHARE_TYPE","${share.shareType}");
		setCombobox("inv_Way_${status.index+1}","FUND_WAY","${share.invWay}");
	</c:forEach>
	if(${num} > 0){
		setAcctManager('${pmUserId}');
	}
	
	//下拉框请选择值为0
	function setAcctManager(selVal){
		if(${num} > 0){
	 		var cusAcctManagerId = ${currUser.pid};
	 		// 客户ID 去找客户经理
	 		var urlCusAcctManagerId = '${basePath}customerController/getCusManagerNames.action?cusAcctManagerId='+cusAcctManagerId;
		$('#cusAcctManagerId').combobox({    
			url:urlCusAcctManagerId,    
		    valueField:'pids',    
		    textField:'userName',
		    value:cusAcctManagerId,
		    onLoadSuccess: function(rec){
		    	if(selVal!='' && selVal!='0' && selVal!='-1'){
		    		$("#cusAcctManagerId").combobox('setValue',selVal);
		    	}
	        }
		});  
	}
	}
	
});
//删除文件
function delFile(imgurl){
	$("#"+imgurl).val("");
	var srcurl="${ctx}/p/xlkfinance/images/u53.png";
	$("#"+imgurl+"_img").attr("src",srcurl);
}
//新增法人代表
function addComOwn(){
	var cusPersonId=$("#cusComPid").val();
	if(cusPersonId==''){
		$('#fm').form('clear'); 
	}
	$('#dlg').dialog('open').dialog('setTitle', "新增法人代表信息");
}

		
</script>
<!-- <body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div"> -->
<div id="main_body">
<div id="cus_content">
<jsp:include page="cus_com_tab.jsp">
<jsp:param value="1" name="tab"/>
</jsp:include>
<div style="width:780px;float:left">
<form id="baseInfo" action="customerController/edit_per_under_cpy" method="POST">
    <input type="hidden" name="cusAcct.status" value="1"/>
<!-- 	<input type="hidden" name="cusAcct.pmUserId" value="1"/> -->
	<input type="hidden" name="cusAcct.cusType" value="2"/>
	<input type="hidden" name="cusPerson.pid" value="" id="personId"/>
	
	<input type="hidden" name="cusAcct.cusStatus" value="${empty cusComBaseDTO.cusAcct?1:cusComBaseDTO.cusAcct.cusStatus}"/>
	<input type="hidden" name="cusAcct.pid" value="${acctId}" />
	<input type="hidden" name="cusComBase.pid" value="${cusComBaseDTO.cusComBase.pid }" />
	<input type="hidden" name="cusComBase.status" value="1"/>
	<input type="hidden" id="cusComPid" name="cusComBase.comOwnId" value ="${cusComBaseDTO.cusComBase.comOwnId }"/>
	<c:if test="${not empty cusComBaseDTO.cusComBase}">
        	<input type="hidden" name="cusComBase.cusAcct.pid" value="${cusComBaseDTO.cusComBase.cusAcct.pid}"/>
        </c:if>
<table class="cus_table2" id="cus_com_table"> 
    <tr style="height: 25px;">
		<th colspan="4">填写企业基本信息</th>
		
   </tr>
   <tr>
    <td class="align_right" ><span class="cus_red">*</span>企业名称：</td>
	<td ><input type="text" style="width: 200px;" class="text_style easyui-validatebox" name="cusComBase.cpyName" value="${cusComBaseDTO.cusComBase.cpyName}" data-options="required:true"/></td>   
 	<c:if test="${num > 0}" >
		 <td class="align_right" style="width: 85px;"><span class="cus_red">*</span>客户经理：</td>
	  	 <td >
	  	 	<select id="cusAcctManagerId" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="cusAcct.pmUserId" style="width:150px;" />
	  	 </td>
	</c:if>
	<c:if test="${num==0 }">
		<input type="hidden" name="cusAcct.pmUserId" value="${empty cusPerBaseDTO.cusAcct?currUser.pid:cusPerBaseDTO.cusAcct.pmUserId }"/>
	</c:if>
  </tr>
  <tr>
    <td class="align_right" ><span class="cus_red"></span>企业外文名称：</td>
	<td colspan="3"><input type="text" style="width: 500px;" class="text_style easyui-validatebox" name="cusComBase.cpyEngName" value="${cusComBaseDTO.cusComBase.cpyEngName}"/></td>    
  </tr>
  <tr>
    <td class="align_right" style="width:130px;"><span class="cus_red">*</span>企业法人：</td>
	<td ><input type="text"  class="text_style easyui-validatebox" name="cusComBase.comOwnName" value="${cusComBaseDTO.cusComBase.comOwnName}" data-options="required:true"/></td>
	<td class="align_right" style="width:130px;"><span class="cus_red">*</span>法人身份证号：</td>
	<td ><input type="text"  class="text_style" name="cusComBase.comOwnCard" value="${cusComBaseDTO.cusComBase.comOwnCard}" data-options="required:true,validType:'length[18]'"/></td>
  </tr>
  <tr>
    <td class="align_right" ><span class="cus_red">*</span>法人手机：</td>
	<td ><input type="text"  class="text_style" name="cusComBase.comOwnPhone" value="${cusComBaseDTO.cusComBase.comOwnPhone}" data-options="required:true,validType:'phone'"/></td>
  </tr>
  <tr>
    <td class="align_right" >企业规模：</td>
	<td>
		<select  class="select_style easyui-combobox" editable="false" id="cpy_Scale" name="cusComBase.cpyScale">
	  </select>
	</td>
	<td class="align_right" >客户级别：</td>
	<td>
	<select  class="select_style easyui-combobox" editable="false" id="cus_Level" name="cusComBase.cusLevel">
	  </select>
<%-- 	<input type="text"  class="text_style" name="cusComBase.cusLevel" value="${cusComBaseDTO.cusComBase.cusLevel}"/> --%>
	</td>
  </tr>
   <tr>
    <td class="align_right" >经济行业类别：</td>
	<td>
	  <select  class="select_style easyui-combobox" editable="false" id="eco_Trade" name="cusComBase.ecoTrade">
	  </select>
	</td>
	<td class="align_right" >员工人数：</td>
	<td>
		<select  class="select_style easyui-combobox" editable="false" id="staff_Num" name="cusComBase.staffNum">
<!-- 			<option value="1">20人以下</option> -->
<!-- 			<option value="2">20~50人</option> -->
	  </select>
	</td>
  </tr>
  <tr>
    <td class="align_right" >基本户状态：</td>
	 <td>
		<select  class="select_style easyui-combobox" editable="false" id="basHou_Status" name="cusComBase.basHouStatus">
	 </td>
	<td class="align_right" >机构状态：</td>
	<td>
	   <select  class="select_style easyui-combobox" editable="false" id="org_Status" name="cusComBase.orgStatus">
	</td>
  </tr>
   <tr>
    <td class="align_right" ><span class="cus_red">*</span>营业执照号码：</td>
	 <td><input type="text" id="busLic_Cert" class="text_style" name="cusComBase.busLicCert" value="${cusComBaseDTO.cusComBase.busLicCert}" data-options="required:true,validType:'integer'"/></td>
	 
	<td class="align_right" >组织机构代码：</td>
	<td><input type="text"  class="text_style" name="cusComBase.orgCode" value="${cusComBaseDTO.cusComBase.orgCode}"/></td>
  </tr>
  <tr>
    <td class="align_right">国税登记证号码：</td>
	 <td><input type="text"  class="text_style" name="cusComBase.natCert" value="${cusComBaseDTO.cusComBase.natCert}"/></td>
	<td class="align_right">地税登记证号码：</td>
	<td><input type="text"  class="text_style" name="cusComBase.landCert" value="${cusComBaseDTO.cusComBase.landCert}"/></td>
  </tr>
   <tr>
    <td class="align_right">机构信用代码：</td>
	 <td><input type="text"  class="text_style" name="cusComBase.creditCode" value="${cusComBaseDTO.cusComBase.creditCode}"/></td>
	<td class="align_right">进出口许可证：</td>
	<td><input type="text"  class="text_style" name="cusComBase.exitCert" value="${cusComBaseDTO.cusComBase.exitCert}"/></td>
  </tr>
  <tr>
    <td class="align_right" ><span class="cus_red">*</span>登记注册地址：</td>
	 <td><input type="text"  class="text_style easyui-validatebox" name="cusComBase.regAddr" value="${cusComBaseDTO.cusComBase.regAddr}" data-options="required:true"/></td>
	<td class="align_right" >注册地址邮编：</td>
	<td><input type="text"  class="text_style" name="cusComBase.regCode" value="${cusComBaseDTO.cusComBase.regCode}"/></td>
  </tr>
  <tr>
    <td class="align_right" >登记注册行政规划：</td>
	 <td><input type="text"  class="text_style" name="cusComBase.regPlan" value="${cusComBaseDTO.cusComBase.regPlan}"/></td>
	<td class="align_right" >注册资金币种：</td>
	<td>
	  <select  class="select_style easyui-combobox" editable="false" id="reg_Currency" name="cusComBase.regCurrency">
<!-- 			<option value="1">人民币</option> -->
	  </select>
	</td>
  </tr>
  <tr>
    <td class="align_right" ><span class="cus_red">*</span>联系电话：</td>
	 <td><input type="text"  class="text_style easyui-validatebox" name="cusComBase.telephone" value="${cusComBaseDTO.cusComBase.telephone}" data-options="required:true,validType:'number'"/></td>
	<td class="align_right">工商注册资金(万)：</td>
	<td><input type="text"  class="text_style" name="cusComBase.regMoney" value="${cusComBaseDTO.cusComBase.regMoney}"/></td>
  </tr>
   <tr>
    <td class="align_right" >传真：</td>
	 <td><input type="text"  class="text_style" name="cusComBase.fax" value="${cusComBaseDTO.cusComBase.fax}"/></td>
	<td class="align_right" >占股比例%：</td>
	<td><input type="text"  class="text_style" name="cusComBase.shareRatio" value="${cusComBaseDTO.cusComBase.shareRatio}"/></td>
  </tr>
  <tr>
    <td class="align_right">经营场所：</td>
	 <td > 
	   <select  class="select_style easyui-combobox" editable="false" id="mge_SiteType" name="cusComBase.mgeSiteType" style="width: 70px;">
	  </select>
	  价值（万）：<input type="text" class="text_style" style="width: 60px;"/>
	 </td>
	<td class="align_right">面积:（m2）：</td>
	<td><input type="text"  class="text_style" name="cusComBase.area" value="${cusComBaseDTO.cusComBase.area}"/></td> 
  </tr>
   <tr>
    <td class="align_right">房租（元）：</td>
	 <td><input type="text"  class="text_style" name="cusComBase.rent" value="${cusComBaseDTO.cusComBase.rent}"/></td>
	<td class="align_right">企业成立日期：</td>
	<td><input type="text"  class="text_style easyui-datebox" editable="false" name="cusComBase.foundDate" value="${cusComBaseDTO.cusComBase.foundDate}"/></td>
  </tr>
  <tr>
    <td class="align_right">经营所在地城市：</td>
	 <td >
	    <input type="text" class="text_style" name="cusComBase.mgeCity" value="${cusComBaseDTO.cusComBase.mgeCity}">
	 </td>
 	 <td class="align_right">单位性质：</td> 
 	 <td> 
 	 	<select  class="select_style easyui-combobox" editable="false" id="unit_nature" name="cusComBase.unitNature"></select>
 	 </td> 
  </tr>
    <tr>
    <td class="align_right">实际经营地址：</td>
	<td><input type="text"  class="text_style" name="cusComBase.actBizAdd" value="${cusComBaseDTO.cusComBase.actBizAdd}"/></td>
	<td class="align_right">经营地址邮编：</td>
	<td><input type="text"  class="text_style" name="cusComBase.actBizCode" value="${cusComBaseDTO.cusComBase.actBizCode}"/></td>
  </tr>
   <tr>
    <td class="align_right"><span class="cus_red">*</span>通信地址：</td>
	<td><input type="text"  class="text_style easyui-validatebox" name="cusComBase.commAddr" value="${cusComBaseDTO.cusComBase.commAddr}" data-options="required:true"/></td>
	<td class="align_right">通讯地址邮编：</td>
	<td><input type="text"  class="text_style" name="cusComBase.commCode" value="${cusComBaseDTO.cusComBase.commCode}"/></td>
  </tr>
   <tr>
    <td class="align_right"><span class="cus_red">*</span>通信收件人：</td>
	<td><input type="text"  class="text_style easyui-validatebox" name="cusComBase.recPerson" value="${cusComBaseDTO.cusComBase.recPerson}" data-options="required:true"/></td>
	<td class="align_right">电子邮箱：</td>
	<td><input type="text"  class="text_style" name="cusComBase.mail" value="${cusComBaseDTO.cusComBase.mail}"/></td>
  </tr>
  <tr>
    <td class="align_right" ><span class="cus_red">*</span>企业网址：</td>
	<td ><input type="text" class="text_style easyui-validatebox" name="cusComBase.cpyUrl" value="${cusComBaseDTO.cusComBase.cpyUrl}" data-options="required:true"/></td>   
  	<td class="align_right">所有制性质：</td> 
 	 <td> 
 	 	<select  class="select_style easyui-combobox" editable="false" id="com_all_nature" name="cusComBase.comAllNature"></select>
 	 </td> 
  </tr>
  <tr>
    <td class="align_right" ><span class="cus_red"></span>企业介绍：</td>
    <td colspan="3" style="height: 80px;"><textarea rows="4" cols="60"  name="cusComBase.cusIntro" class="easyui-validatebox" >${cusComBaseDTO.cusComBase.cusIntro}</textarea></td>
  </tr>
</table>
<br/>
<input type="hidden" id="comOwn" value="0"/>
<p/>
<table class="cus_table" id="cus_com_table" style="border:none;">
   <tr style="height: 25px;">
     <th colspan="8">公司资料扫描件</th>
   </tr>
  <tr>
    <td class="align_right" >营业执照副本</td>
    <td>
	    <input type="button" class="text_btn" value="上传" name="cusComBase.busLicCertUrl" onclick="openFileUpload('cus_busLicCert_url',680);"/>
	    <input type="button" class="text_btn" value="删除" onclick="delFile('cus_busLicCert_url');"/>
	    <input type="hidden" id="cus_busLicCert_url"  name="cusComBase.busLicCertUrl" value="${cusComBaseDTO.cusComBase.busLicCertUrl}"/>
    </td>
    <td class="align_right" style="width: 90px;">组织机构代码证</td>
    <td>
       <input type="button" class="text_btn" value="上传" name="cusComBase.orgCodeCertUrl" onclick="openFileUpload('cus_orgCodeCert_url',680);"/>
       <input type="button" class="text_btn" value="删除" onclick="delFile('cus_orgCodeCert_url');" />
       <input type="hidden" id="cus_orgCodeCert_url"  name="cusComBase.orgCodeCertUrl" value="${cusComBaseDTO.cusComBase.orgCodeCertUrl}"/>
    </td>
    <td class="align_right">税务证扫描件</td>
    <td>
	    <input type="button" class="text_btn" value="上传" name="cusComBase.taxCertUrl"  onclick="openFileUpload('cus_taxCert_url',680);"/>
	    <input type="button" class="text_btn" value="删除" onclick="delFile('cus_taxCert_url');"/>
	    <input type="hidden" id="cus_taxCert_url"  name="cusComBase.taxCertUrl" value="${cusComBaseDTO.cusComBase.taxCertUrl}"/>
	</td>
    <td class="align_right">贷款卡扫描件</td>
    <td>
	    <input type="button" class="text_btn" value="上传" name="cusComBase.loanCardUrl" onclick="openFileUpload('cus_loanCard_url',680);"/>
	    <input type="button" class="text_btn" value="删除" onclick="delFile('cus_loanCard_url');"/>
	    <input type="hidden" id="cus_loanCard_url"  name="cusComBase.loanCardUrl" value="${cusComBaseDTO.cusComBase.loanCardUrl}"/>
    </td>
  </tr>
 
  
  <tr>
	  <td colspan="2" style="height:150px;">
	  	<c:if test="${empty cusComBaseDTO.cusComBase.busLicCertUrl||cusComBaseDTO.cusComBase.busLicCertUrl=='' }">
		   		<img id="cus_busLicCert_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="160px" height="120px">
		</c:if>
	  	<c:if test="${not empty cusComBaseDTO.cusComBase.busLicCertUrl && cusComBaseDTO.cusComBase.busLicCertUrl !='' }">
		   		<img id="cus_busLicCert_url_img" alt="头像" src="${basePath}orgPartnerInfoController/download.action?path=${cusComBaseDTO.cusComBase.busLicCertUrl}" width="160px" height="120px">
		</c:if>
	  </td>
	  <td colspan="2">
	    <c:if test="${empty cusComBaseDTO.cusComBase.orgCodeCertUrl||cusComBaseDTO.cusComBase.orgCodeCertUrl=='' }">
		   		<img id="cus_orgCodeCert_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="160px" height="120px">
		</c:if>
	  	<c:if test="${not empty cusComBaseDTO.cusComBase.orgCodeCertUrl && cusComBaseDTO.cusComBase.orgCodeCertUrl !='' }">
		   		<img id="cus_orgCodeCert_url_img" alt="头像" src="${basePath}orgPartnerInfoController/download.action?path=${cusComBaseDTO.cusComBase.orgCodeCertUrl}" width="160px" height="120px">
		</c:if>
	  </td>
	  <td colspan="2">
	    <c:if test="${empty cusComBaseDTO.cusComBase.taxCertUrl||cusComBaseDTO.cusComBase.taxCertUrl=='' }">
		   		<img id="cus_taxCert_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="160px" height="120px">
		</c:if>
	  	<c:if test="${not empty cusComBaseDTO.cusComBase.taxCertUrl && cusComBaseDTO.cusComBase.taxCertUrl !='' }">
		   		<img id="cus_taxCert_url_img" alt="头像" src="${basePath}orgPartnerInfoController/download.action?path=${cusComBaseDTO.cusComBase.taxCertUrl}" width="160px" height="120px">
		</c:if>
	  </td>
	  <td colspan="2">
	   <c:if test="${empty cusComBaseDTO.cusComBase.loanCardUrl||cusComBaseDTO.cusComBase.loanCardUrl=='' }">
		   		<img id="cus_loanCard_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="160px" height="120px">
		</c:if>
	  	<c:if test="${not empty cusComBaseDTO.cusComBase.loanCardUrl && cusComBaseDTO.cusComBase.loanCardUrl !='' }">
		   		<img id="cus_loanCard_url_img" alt="头像" src="${basePath}orgPartnerInfoController/download.action?path=${cusComBaseDTO.cusComBase.loanCardUrl}" width="160px" height="120px">
		</c:if>
	  </td>
 </tr>
 <tr>
 	<td colspan="6">机构代码信用证
    	<input type="button" class="text_btn" value="上传" name="cusComBase.orgCodeCreitsUrl" onclick="openFileUpload('org_code_creits_url',680);"/>
	    <input type="button" class="text_btn" value="删除" onclick="delFile('org_code_creits_url');"/>
	    <input type="hidden" id="org_code_creits_url"  name="cusComBase.orgCodeCreitsUrl" value="${cusComBaseDTO.cusComBase.orgCodeCreitsUrl}"/>
    </td>
  </tr>
  <tr>
   <td colspan="6">
       <c:if test="${empty cusComBaseDTO.cusComBase.orgCodeCreitsUrl||cusComBaseDTO.cusComBase.orgCodeCreitsUrl=='' }">
		   		<img id="org_code_creits_url_img" alt="头像" src="${ctx}/p/xlkfinance/images/u53.png" width="160px" height="120px">
		</c:if>
	  	<c:if test="${not empty cusComBaseDTO.cusComBase.orgCodeCreitsUrl && cusComBaseDTO.cusComBase.orgCodeCreitsUrl !='' }">
		   		<img id="org_code_creits_url_img" alt="头像" src="${basePath}orgPartnerInfoController/download.action?path=${cusComBaseDTO.cusComBase.orgCodeCreitsUrl}" width="160px" height="120px">
		</c:if>
   </td>
 </tr>
</table>
	
	
	    <div class="staff_table">
     		<table class="cus_table" id="addtr">
     		<input type="hidden" name="cusComShare.pid" value="1"/>
     		<input type="hidden" name="cusComShares.status" value="1"/>
     		   <tr style="height: 25px;">
     		     <th colspan="8">股东（投资人）信息</th>
     		   </tr>
     		   <tr>
     		   	<td colspan="8">
     		   		<div style="margin-left:10px">
     		   			<a class="easyui-linkbutton" iconCls="icon-add" plain="true" name="addBtn"  onclick="addLineBtn();" >新增</a>
     		   			<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" name="delBtn"  onclick="deleteLineBtn();" >删除</a>
					</div>
     		   	</td>
     		   </tr>
        	  <tr>
	            <td>
	             <input type="checkbox" name="isCheckeds" id="isCheckeds" class="isCheckeds" value=""/>
	             <input type="hidden" size="6" value=""/>
	            </td>
	            <td width="40px">序号</td>
	            <td>股东（投资人）类别</td>
	            <td>股东（投资人）名称</td>
	            <td>出资额(万)</td>
	            <td>出资方式</td>
	            <td>持股比例%</td>
	            <td>备注</td>
       		  </tr>
       	<c:if test="${empty cusComBaseDTO.cusComShares }">
          <tr id="tr1" name="1">
              <td><input type="checkbox" name="isChecked"  class="isChecked" value="1"/></td>
	          <td>1</td>
	          <td>
	           <select panelHeight="90" panelMaxHeight="90" editable="false" class="select_style" name="cusComShares[1].shareType" id="share_Type_1" class="text_style easyui-combobox" style="width:100px;">
	 		 	</select>
	 		  </td>
	 		  <td>
	 		     <input type="text" name="cusComShares[1].shareName" class="text_style" style="width:100px;"/>
	 		  </td>	
	 		   <td>
	 		     <input type="text" name="cusComShares[1].invMoney"   class="text_style" style="width:100px;"/>
	 		  </td>
	 		   <td>
	 		    <select panelHeight="90" panelMaxHeight="90" editable="false" class="select_style" name="cusComShares[1].invWay" id="inv_Way_1" class="text_style"  style="width:100px;"/>
	 		 	</select>
	 		  </td>		
	 		   <td>
	 		     <input type="text" name="cusComShares[1].shareRatio"   class="text_style" style="width:100px;"/>
	 		  </td>
	 		  <td>
	 		     <input type="text" name="cusComShares[1].remark"   class="text_style" style="width:100px;"/>
	 		  </td>
          </tr>
         </c:if>
         
       	<c:if test="${not empty cusComBaseDTO.cusComShares }">
       	<c:forEach var="share" items="${cusComBaseDTO.cusComShares }" varStatus="status">
       		<tr id="tr${status.index+1 }" name="${status.index+1 }">
       	   		<input type="hidden" name="cusComShares[${status.index+1 }].pid" value="${share.pid}"/>
              <td><input type="checkbox" name="isChecked"  class="isChecked" value="${status.index+1 }"/></td>
	          <td>${status.index+1 }</td>
	          <td>
	           <select  class="select_style" panelHeight="90" editable="false" panelMaxHeight="90"  name="cusComShares[${status.index+1 }].shareType" id="share_Type_${status.index+1}"  value="${share.shareType}"  class="text_style" style="width:100px;">
	 		 	</select>
	 		  </td>
	 		  <td>
	 		     <input type="text" name="cusComShares[${status.index+1 }].shareName" value="${share.shareName}" class="text_style" style="width:100px;"/>
	 		  </td>	
	 		   <td>
	 		     <input type="text" name="cusComShares[${status.index+1 }].invMoney"  value="${share.invMoney}" class="text_style" style="width:100px;"/>
	 		  </td>
	 		   <td>
	 		    <select  class="select_style" panelHeight="90" editable="false" panelMaxHeight="90" name="cusComShares[${status.index+1 }].invWay" id="inv_Way_${status.index+1}" value="${share.invWay}" class="text_style" style="width:100px;"/>
	 		 	</select>
	 		  </td>		
	 		   <td>
	 		     <input type="text" name="cusComShares[${status.index+1 }].shareRatio" value="${share.shareRatio}"  class="text_style" style="width:100px;"/>
	 		  </td>
	 		  <td>
	 		     <input type="text" name="cusComShares[${status.index+1 }].remark"  value="${share.remark}" class="text_style" style="width:100px;"/>
	 		  </td>
          </tr>
         </c:forEach>
       	</c:if>
         
       </table>
       </div>
         <div style="text-align: center;padding:15px 0 50px 0">
            <input type="button" class="cus_save_btn" name="save" value="保     存" onclick="saveBaseInfo();"/>&nbsp;&nbsp;&nbsp; 
            <input type="button" class="cus_save_btn" name="cancel" id="can_cle" value="取     消" onclick="cancelComBase();"/></div>
	</form>	
	</div> 
</div>
</div>
<!--  </div>
</div>
</body> -->