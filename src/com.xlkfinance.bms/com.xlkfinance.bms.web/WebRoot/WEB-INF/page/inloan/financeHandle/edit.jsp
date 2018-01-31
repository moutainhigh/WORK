<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>财务办理</title>
<style type="text/css">
#tabs .tabs-header .tabs{
  height: 26px;
  position: fixed;
  top:0px;
  background: #E0ECFF;
  z-index: 9999999;
  padding-top;2px;
}
.{padding-top:7px;}
button,input,select,textarea,label{vertical-align:middle;color:#626262;outline:none;text-decoration: none;}
table,td,tr{margin:0;padding:0;word-break:break-all;margin-left: 5px;}
input[type="submit"],
input[type="reset"],
input[type="button"],
input[type="text"],
select,
button {
-webkit-appearance: none;
}

.table_css{ border:1px #95B8E7 solid;}
.table_css td{ padding:4px 10px; font-size:12px;border-bottom:1px #ddd solid;  border-right:1px #ddd solid;}
.table_css td:last-child{  border-right:0;}
.bt{ background-color: #E0ECFF;
background: -webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -moz-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -o-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: linear-gradient(to bottom,#EFF5FF 0,#E0ECFF 100%);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
.bt td{padding:4px 10px;border-bottom:1px #95B8E7 solid;}
.srk1{ height:60px;width:99%;}
.jl{ padding:10px 15px 15px 15px;}
.back{background:#fff; border:1px #ddd solid; height:40px; line-height:40px;width:88px;box-shadow:none;font-size:1em;outline:none;-webkit-appearance: none; color:#333; border-radius:5px; display: inline-block; text-align:center; margin-left:10px;}

</style>
</head>
<script type="text/javascript">


	function subForm(formIds) {
		var issucc = false;
		var ids = formIds.split(",");
		for (var i = 0; i < ids.length; i++) {
			var formName = "financeHandleForm_" + ids[i];
			var formObj = document.getElementById(formName);
			//检查是否已收费，不收费不可以改收款信息
			if (ids[i]>=3) {
				var status=getFinanceHandleStatus(formObj.financeHandleId.value);
				if (status==<%=com.xlkfinance.bms.common.constant.Constants.REC_STATUS_NO_CHARGE%>) {
					$.messager.alert("操作提示","请先保存咨询费收款项目信息","error");
					return;
				}
			}
			var isSub = true;
			if (checkForm(formObj)) {
				isSub = false;
				continue;
			}
			if (isSub) {
				$
						.ajax({
							url : "${basePath}financeHandleController/updateApplyFinanceHandle.action",
							cache : true,
							type : "POST",
							data : $("#" + formName).serialize(),
							async : false,
							success : function(data, status) {
								if (data == "1") {
									$("#"+formName+" .subMsg").attr("style","color: green").html("（保存成功）").show(300).delay(3000).hide(1300);
								}else{
									$("#"+formName+" .subMsg").attr("style","color: red").html("（保存失败）").show(300).delay(3000).hide(1300);
								}
								
							}
						});
			}
		}
	}

	function getFinanceHandleStatus(financeHandleId) {
		var financeHandleStatus="";
		$.ajax({
			url : "${basePath}financeHandleController/"+financeHandleId+"/getFinanceHandleStatus.action",
			cache : true,
			type : "POST",
			async : false,
			success : function(data, status) {
				financeHandleStatus= data;
			}
		});
		return financeHandleStatus;
	}
	function changeRemark(formIds, valStr) {
		var ids = formIds.split(",");
		for (var i = 0; i < ids.length; i++) {
			document.getElementById("remark" + ids[i]).value = valStr;
		}
	}
	function checkForm(formObj) {
		var recMoneyIsNotNull = (typeof (formObj.recMoney) != 'undefined' && formObj.recMoney.value.length > 0);
		var recDateIsNotNull = (typeof (formObj.recDate) != 'undefined' && formObj.recDate.value.length > 0);
		var resourceIsNotNull = (typeof (formObj.resource) != 'undefined' && formObj.resource.value.length > 0);
		var operatorIsNotNull = (typeof (formObj.operator) != 'undefined' && formObj.operator.value.length > 0);
		var array = [{
			'IsNotNull' : recMoneyIsNotNull,
			'obj' : formObj.recMoney
		}, {
			'IsNotNull' : recDateIsNotNull,
			'obj' : formObj.recDate
		}, {
			'IsNotNull' : resourceIsNotNull,
			'obj' : formObj.resource
		}, {
			'IsNotNull' : operatorIsNotNull,
			'obj' : formObj.operator
		} ];
		var isNotNullCount = 0;
		//遍历输入框，如果某一退款项目填了一项，则该项输入框全部为必填项，否则为非必填项
		if (recMoneyIsNotNull || recDateIsNotNull
				|| resourceIsNotNull || operatorIsNotNull) {
			for (var i = 0; i < array.length; i++) {
				if (!array[i].IsNotNull) {
					$(array[i].obj.parentNode).attr("style","background-color: #fff3f3;border-color: #ffa8a8");
					isNotNullCount++;
				} else {
					$(array[i].obj.parentNode).removeAttr("style");
				}
			}
		}
		//如果全部填完或者一个都没填返回false
		if (isNotNullCount == 0 || isNotNullCount == 4) {
			return false;
		}
		return true;
	}
	/**
	 * 关闭窗口
	 */
	function closeWindow(){
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex',tab);
		parent.$('#layout_center_tabs').tabs('close',index);
	}
	$(document).ready(function() {
	    $('#recMoney3,#recMoney4,#recMoney5,#recMoney6').numberbox({  
	        onChange:function(){  
	        	var financeHandleForm_3=document.getElementById("financeHandleForm_3");
	    		var financeHandleForm_4=document.getElementById("financeHandleForm_4");
	    		var financeHandleForm_5=document.getElementById("financeHandleForm_5");
	    		var financeHandleForm_6=document.getElementById("financeHandleForm_6");
	    		var recMoney3=0;//第一次放款金额
	    		if (financeHandleForm_3.recMoney.value.length>0) {
	    			recMoney3=parseFloat(financeHandleForm_3.recMoney.value);
				}
	    		var recMoney4=0;//一次赎楼余额转二次放款 金额
	    		if (financeHandleForm_4.recMoney.value.length>0) {
	    			recMoney4=parseFloat(financeHandleForm_4.recMoney.value);
				}
	    		var recMoney5=0;//第二次放款 金额
	    		if (financeHandleForm_5.recMoney.value.length>0) {
	    			recMoney5=parseFloat(financeHandleForm_5.recMoney.value);
				}
	    		var recMoney6=0;//监管（客户）资金转入 金额
	    		if (financeHandleForm_6.recMoney.value.length>0) {
	    			recMoney6=parseFloat(financeHandleForm_6.recMoney.value);
				}
	    		$("#oneTotalMoney").numberbox('setValue',recMoney3+recMoney6);//一次放款合计
	    		$("#twoTotalMoney").numberbox('setValue',recMoney4+recMoney5+recMoney6);//二次放款合计
	        }  
	    });  
	});
</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="财务受理信息" id="tab1" style="padding:10px;">
  <table style="margin-top: 20px">
   <tr>
    <td height="35" width="300" align="center">项目名称: <a href="#" onclick="formatterProjectName.disposeClick(${projectId},'${projectNumber}')"><font color='blue'> ${projectName }</font></a></td>
    <td><div class="iniTitle">项目编号:${projectNumber}</div></td>
   </tr>
  </table>
     <form action="#" id="financeHandleForm_1" name="financeHandleForm_1"  method="post">
    <table width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="margin-top: 30px;border-bottom: none;">
     <tr class="bt">
      <td width="150px">收款项目</td>
      <td>收款金额</td>
      <td>收款帐号</td>
      <td>收款日期</td>
      <td>款项来源</td>
      <td>操作员</td>
     </tr>
     <tr>
      <td>咨询费<span class="subMsg"></span></td>
      <td><input name="recMoney" value="${applyFinanceHandleMap['1'].recMoney }"  class="easyui-numberbox"  data-options="min:0,max:9999999999,precision:2,groupSeparator:','"></td>
      <td>
        <select class="easyui-combobox" id="recAccount" name="recAccount" style="width:250px">
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['1'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['1'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['1'].recDate }"  class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['1'].resource }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <td style="border-right:0;"><input name="operator" disabled="disabled" value="${applyFinanceHandleMap['1'].operator }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <input type="hidden" name="remark" id="remark1"  value="${applyFinanceHandleMap['1'].remark }" >
      <input type="hidden" name="recPro" value="1" >
      <input type="hidden" name="financeHandleId" value="${applyFinanceHandleMap['1'].financeHandleId }" >
      <input type="hidden" name="pid" value="${applyFinanceHandleMap['1'].pid }" >
     </tr>
     </table>
     </form>
      <form action="#" id="financeHandleForm_2" name="financeHandleForm_2"  method="post">
     <table  width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-top: none;">
     <tr>
      <td width="150px">手续费<span class="subMsg" style="color: red;"></span></td>
      <td><input name="recMoney" value="${applyFinanceHandleMap['2'].recMoney }"  class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
        <select class="easyui-combobox" id="recAccount" name="recAccount" style="width:250px">
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['2'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['2'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['2'].recDate }"  class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['2'].resource }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <td style="border-right:0;"><input name="operator" disabled="disabled" value="${applyFinanceHandleMap['2'].operator }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <input type="hidden" name="remark" id="remark2" value="${applyFinanceHandleMap['2'].remark }" >
      <input type="hidden" name="recPro" value="2" >
      <input type="hidden" name="financeHandleId" value="${applyFinanceHandleMap['2'].financeHandleId }" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['2'].pid }" >
     </tr>
     <tr>
      <td style="border-bottom: 0;">备注</td>
      <td colspan="5" style="border-bottom: 0;"><textarea onchange="changeRemark('1,2',this.value)" maxlength="500"  class="srk1 text_style" onfocus="this.style.color='#000';"
        onBlur="this.style.color='#8d8d8d';"
       >${applyFinanceHandleMap['2'].remark }</textarea></td>
     </tr>
    </table>
     </form>

    <p class="jl">
      <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="subForm('1,2')">保存</a> 
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
    </p>
    <form action="#" id="financeHandleForm_3" name="financeHandleForm_3"  method="post">
    <table  width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-bottom: none;">
     <tr class="bt">
      <td width="150px">放款项目</td>
      <td>放款金额</td>
      <td>放款帐号</td>
      <td>放款日期</td>
      <td>款项来源</td>
      <td>操作员</td>
     </tr>
     <tr>
      <td>第一次放款<span class="subMsg"></span></td>
      <td><input name="recMoney" id="recMoney3" value="${applyFinanceHandleMap['3'].recMoney }"  class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <select class="easyui-combobox" id="recAccount" name="recAccount" style="width:250px">
        <option value="4100627824511118" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '4100627824511118' }">selected </c:if>>招行文锦渡(黄云苏)4100627824511118&nbsp;&nbsp;</option>
        <option value="6228460128001280075" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '6228460128001280075' }">selected </c:if>>农行凤凰(邢欢)6228460128001280075</option>
        <option value="6217007200019250656" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '6217007200019250656' }">selected </c:if>>建行东海(包桂兴)6217007200019250656</option>
        <option value="6214680130764598" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '6214680130764598' }">selected </c:if>>北京香蜜(水俊)6214680130764598</option>
        <option value="6230580000066090551" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '6230580000066090551' }">selected </c:if>>平安香蜜湖(水俊)6230580000066090551</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['3'].recDate }"  class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['3'].resource }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <td style="border-right:0;"><input name="operator" disabled="disabled" value="${applyFinanceHandleMap['3'].operator }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <input type="hidden" name="remark" id="remark3"  value="${applyFinanceHandleMap['31'].remark }" >
      <input type="hidden" name="recPro" value="3" >
      <input type="hidden" name="financeHandleId" value="${applyFinanceHandleMap['3'].financeHandleId }" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['3'].pid }" >
     </tr>
     </table>
     </form>
     <form action="#" id="financeHandleForm_4" name="financeHandleForm_4"  method="post">
     <table width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-bottom: none;border-top: none;">
     <tr>
      <td width="150px">一次赎楼余额转二次放款<span class="subMsg"></span></td>
      <td><input name="recMoney" id="recMoney4" value="${applyFinanceHandleMap['4'].recMoney }"  class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <select class="easyui-combobox" id="recAccount" name="recAccount" style="width:250px">
        <option value="4100627824511118" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '4100627824511118' }">selected </c:if>>招行文锦渡(黄云苏)4100627824511118&nbsp;&nbsp;</option>
        <option value="6228460128001280075" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '6228460128001280075' }">selected </c:if>>农行凤凰(邢欢)6228460128001280075</option>
        <option value="6217007200019250656" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '6217007200019250656' }">selected </c:if>>建行东海(包桂兴)6217007200019250656</option>
        <option value="6214680130764598" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '6214680130764598' }">selected </c:if>>北京香蜜(水俊)6214680130764598</option>
        <option value="6230580000066090551" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '6230580000066090551' }">selected </c:if>>平安香蜜湖(水俊)6230580000066090551</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['4'].recDate }"  class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['4'].resource }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <td style="border-right:0;"><input name="operator" disabled="disabled" value="${applyFinanceHandleMap['4'].operator }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <input type="hidden" name="remark" id="remark4"  value="${applyFinanceHandleMap['4'].remark }" >
      <input type="hidden" name="recPro" value="4" >
      <input type="hidden" name="financeHandleId" value="${applyFinanceHandleMap['4'].financeHandleId }" >
      <input type="hidden" name="pid" value="${applyFinanceHandleMap['4'].pid }" >
     </tr>
      </table>
      </form>
     <form action="#" id="financeHandleForm_5" name="financeHandleForm_5"  method="post">
     <table width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-bottom: none;border-top: none;">
     <tr>
      <td width="150px">第二次放款<span class="subMsg"></span></td>
      <td><input name="recMoney" id="recMoney5" value="${applyFinanceHandleMap['5'].recMoney }"  class="easyui-numberbox" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" missingMessage="请输入逾期贷款利率!" precision="2" groupSeparator="," onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <select class="easyui-combobox" id="recAccount" name="recAccount" style="width:250px">
        <option value="4100627824511118" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '4100627824511118' }">selected </c:if>>招行文锦渡(黄云苏)4100627824511118&nbsp;&nbsp;</option>
        <option value="6228460128001280075" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '6228460128001280075' }">selected </c:if>>农行凤凰(邢欢)6228460128001280075</option>
        <option value="6217007200019250656" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '6217007200019250656' }">selected </c:if>>建行东海(包桂兴)6217007200019250656</option>
        <option value="6214680130764598" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '6214680130764598' }">selected </c:if>>北京香蜜(水俊)6214680130764598</option>
        <option value="6230580000066090551" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '6230580000066090551' }">selected </c:if>>平安香蜜湖(水俊)6230580000066090551</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['5'].recDate }"  class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['5'].resource }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <td style="border-right:0;"><input name="operator" disabled="disabled" value="${applyFinanceHandleMap['5'].operator }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <input type="hidden" name="remark" id="remark5"  value="${applyFinanceHandleMap['5'].remark }" >
      <input type="hidden" name="recPro" value="5" >
      <input type="hidden" name="financeHandleId" value="${applyFinanceHandleMap['5'].financeHandleId }" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['5'].pid }" >
     </tr>
     </table>
     </form>
      <form action="#" id="financeHandleForm_6" name="financeHandleForm_6"  method="post">
     <table width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-bottom: none;border-top: none;">
     <tr>
      <td width="150px">监管（客户）资金转入<span class="subMsg"></span></td>
      <td><input name="recMoney" id="recMoney6"  value="${applyFinanceHandleMap['6'].recMoney }"  class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <select class="easyui-combobox" id="recAccount" name="recAccount" style="width:250px">
        <option value="4100627824511118" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '4100627824511118' }">selected </c:if>>招行文锦渡(黄云苏)4100627824511118&nbsp;&nbsp;</option>
        <option value="6228460128001280075" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '6228460128001280075' }">selected </c:if>>农行凤凰(邢欢)6228460128001280075</option>
        <option value="6217007200019250656" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '6217007200019250656' }">selected </c:if>>建行东海(包桂兴)6217007200019250656</option>
        <option value="6214680130764598" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '6214680130764598' }">selected </c:if>>北京香蜜(水俊)6214680130764598</option>
        <option value="6230580000066090551" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '6230580000066090551' }">selected </c:if>>平安香蜜湖(水俊)6230580000066090551</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['6'].recDate }"  class="easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['6'].resource }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <td style="border-right:0;"><input name="operator" disabled="disabled" value="${applyFinanceHandleMap['6'].operator }"  class="easyui-textbox" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';" style="width:80px"></td>
      <input type="hidden" name="remark" id="remark6" value="${applyFinanceHandleMap['6'].remark }" >
      <input type="hidden" name="recPro" value="6" >
      <input type="hidden" name="financeHandleId" value="${applyFinanceHandleMap['6'].financeHandleId }" >
      <input type="hidden" name="pid" value="${applyFinanceHandleMap['6'].pid }" >
     </tr>
      </table>
      </form>
     <table width="1039px;" border="0" cellpadding="0" cellspacing="0" class="table_css" style="border-top: none;">
     <tr>
      <td width="150px">一次放款合计</td>
      <td colspan="5"><input name="oneTotalMoney" id="oneTotalMoney" value="${applyFinanceHandleMap['3'].recMoney+applyFinanceHandleMap['6'].recMoney }"  class="easyui-numberbox" data-options="min:0,max:9999999999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
     </tr>
     <tr>
      <td>二次放款合计</td>
      <td colspan="5"><input name="twoTotalMoney"  id="twoTotalMoney" value="${applyFinanceHandleMap['4'].recMoney+applyFinanceHandleMap['5'].recMoney+applyFinanceHandleMap['6'].recMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999999999,precision:2,groupSeparator:','" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
     </tr>
     <tr>
      <td style="border-bottom: 0;" >备注</td>
      <td colspan="5" style="border-bottom: 0;"><textarea onchange="changeRemark('3,4,5,6',this.value)" maxlength="500"  class="srk1 text_style" onfocus="this.style.color='#000';"
        onBlur="this.style.color='#8d8d8d';"
       >${applyFinanceHandleMap['6'].remark }</textarea></td>
     </tr>
    </table>

    <p class="jl">
      <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="subForm('3,4,5,6')">保存</a> 
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
    </p>
   </div>

  </div>
 </div>
</body>
</html>