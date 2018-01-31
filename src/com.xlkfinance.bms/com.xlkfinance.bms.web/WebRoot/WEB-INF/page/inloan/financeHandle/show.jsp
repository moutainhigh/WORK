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
<title>财务办理查看</title>
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
table,td,tr{margin:0;padding:0;word-break:break-all;margin-top: 35px;margin-left:10px;}
input[type="submit"],
input[type="reset"],
input[type="button"],
input[type="text"],
select,
button {
-webkit-appearance: none;
}

.table_css{ border:1px #95B8E7 solid;}
.table_css td{ padding:7px 10px; font-size:12px; border-bottom:1px #ddd solid; border-right:1px #ddd solid;}
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
.srk{ border: 1px solid #95b8e7;border-radius: 6px;font-size:14px; width:100%; padding:3px 0}
.srk1{ border: 1px solid #95b8e7;border-radius: 6px;font-size:14px; width:99%;}
.jl{ padding:10px 15px 15px 15px;}
.submit{background:#f9bf00; border:1px #f9bf00 solid;height:40px; line-height:40px;width:90px;box-shadow:none;font-size:1em;outline:none;-webkit-appearance: none; color:#fff; border-radius:5px; display: inline-block; text-align:center;
}
.back{background:#fff; border:1px #ddd solid; height:40px; line-height:40px;width:88px;box-shadow:none;font-size:1em;outline:none;-webkit-appearance: none; color:#333; border-radius:5px; display: inline-block; text-align:center; margin-left:10px;}
</style>
</head>

<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto；">
   <input type="hidden" id="pid" name="pid" value="-1" />
   <div title="财务受理信息" id="tab1">
   <table style="margin-top: 30px">
   <tr>
    <td height="35" width="300" align="center">项目名称: <a href="#" onclick="formatterProjectName.disposeClick(${projectId},'${projectNumber}')"><font color='blue'> ${projectName }</font></a></td>
    <td><div class="iniTitle">项目编号:${projectNumber}</div></td>
   </tr>
  </table>
    <table border="0" cellpadding="0" cellspacing="0" class="table_css" width="1039px;">
     <tr class="bt">
      <td width="150px">收款项目</td>
      <td>收款金额</td>
      <td style="width:250px">收款帐号</td>
      <td>收款日期</td>
      <td style="width:80px">款项来源</td>
      <td style="width:80px">操作员</td>
     </tr>
     <tr>
      <td>咨询费</td>
      <form action="#" id="financeHandleForm_1" name="financeHandleForm_1"  method="post">
      <td><input name="recMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${applyFinanceHandleMap['1'].recMoney }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
        <select class="easyui-combobox" disabled="disabled" id="recAccount" name="recAccount" style="width:250px">
        <option value="" >未选择</option>
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['1'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['1'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['1'].recDate }" disabled="disabled"   type="text" class="srk easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['1'].resource }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td style="border-right:0;"><input name="operator" value="${applyFinanceHandleMap['1'].operator }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <input type="hidden" name="remark" id="remark1" value="${applyFinanceHandleMap['1'].remark }" >
      <input type="hidden" name="recPro" value="1" >
      <input type="hidden" name="pid" value="${applyFinanceHandleMap['1'].pid }" >
      </form>
     </tr>
     <tr>
      <td>手续费</td>
      <form action="#" id="financeHandleForm_2" name="financeHandleForm_2"  method="post">
      <td><input name="recMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${applyFinanceHandleMap['2'].recMoney }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <select class="easyui-combobox" disabled="disabled" id="recAccount" name="recAccount" style="width:250px">
        <option value="" >未选择</option>
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['2'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['2'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['2'].recDate }" disabled="disabled"   type="text" class="srk easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['2'].resource }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td style="border-right:0;"><input name="operator" value="${applyFinanceHandleMap['2'].operator }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <input type="hidden" name="remark" id="remark2" value="${applyFinanceHandleMap['2'].remark }" >
      <input type="hidden" name="recPro" value="2" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['2'].pid }" >
      </form>
     </tr>
     <tr>
      <td style="border-bottom: 0;">备注</td>
      <td colspan="5" style="border-bottom: 0;"><textarea disabled="disabled"   onchange="changeRemark('1,2',this.value)" type="text" class="srk1" onfocus="this.style.color='#000';"
        onBlur="this.style.color='#8d8d8d';"
       >${applyFinanceHandleMap['2'].remark }</textarea></td>
     </tr>

    </table>

<!--     <p class="jl">
     <input type="button" class="submit" onclick="subForm('1,2')" id="" value="保存"> <input type="button" class="back" value="返回">
    </p> -->
 <table border="0" cellpadding="0" cellspacing="0" class="table_css" width="1039px;">
     <tr class="bt">
      <td width="150px">放款项目</td>
      <td>放款金额</td>
      <td style="width:250px">放款帐号</td>
      <td>放款日期</td>
      <td style="width:80px">款项来源</td>
      <td style="width:80px">操作员</td>
     </tr>
     <tr>
      <td>第一次放款</td>
      <form action="#" id="financeHandleForm_3" name="financeHandleForm_3"  method="post">
      <td><input name="recMoney" value="${applyFinanceHandleMap['3'].recMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
        <select class="easyui-combobox" disabled="disabled" id="recAccount" name="recAccount" style="width:250px">
        <option value="" >未选择</option>
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['3'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['3'].recDate }" disabled="disabled"   type="text" class="srk easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['3'].resource }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td style="border-right:0;"><input name="operator" value="${applyFinanceHandleMap['3'].operator }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <input type="hidden" name="remark" id="remark3" value="${applyFinanceHandleMap['31'].remark }" >
      <input type="hidden" name="recPro" value="3" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['3'].pid }" >
      </form>
     </tr>
     <tr>
      <td>一次赎楼余额转二次放款</td>
      <form action="#" id="financeHandleForm_4" name="financeHandleForm_4"  method="post">
      <td><input name="recMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${applyFinanceHandleMap['4'].recMoney }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
       <select class="easyui-combobox" disabled="disabled" id="recAccount" name="recAccount" style="width:250px">
       <option value="" >未选择</option>
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['4'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
     </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['4'].recDate }" disabled="disabled"   type="text" class="srk easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['4'].resource }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td style="border-right:0;"><input name="operator" value="${applyFinanceHandleMap['4'].operator }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <input type="hidden" name="remark" id="remark4" value="${applyFinanceHandleMap['4'].remark }" >
      <input type="hidden" name="recPro" value="4" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['4'].pid }" >
      </form>
     </tr>
     <tr>
      <td>第二次放款</td>
      <form action="#" id="financeHandleForm_5" name="financeHandleForm_5"  method="post">
      <td><input name="recMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${applyFinanceHandleMap['5'].recMoney }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
      <select class="easyui-combobox" disabled="disabled" id="recAccount" name="recAccount" style="width:250px">
      <option value="" >未选择</option>
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['5'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['5'].recDate }" disabled="disabled"   type="text" class="srk easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['5'].resource }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td style="border-right:0;"><input name="operator" value="${applyFinanceHandleMap['5'].operator }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <input type="hidden" name="remark" id="remark5" value="${applyFinanceHandleMap['5'].remark }" >
      <input type="hidden" name="recPro" value="5" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['5'].pid }" >
      </form>
     </tr>
     <tr>
      <td>监管（客户）资金转入</td>
      <form action="#" id="financeHandleForm_6" name="financeHandleForm_6"  method="post">
      <td><input name="recMoney" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value="${applyFinanceHandleMap['6'].recMoney }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td>
       <select class="easyui-combobox" disabled="disabled" id="recAccount" name="recAccount" style="width:250px">
       <option value="" >未选择</option>
        <option value="41033100040019324" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '41033100040019324' }">selected </c:if>>农业凤凰支行41033100040019324&nbsp;&nbsp;</option>
        <option value="44201533400052532193" <c:if test="${applyFinanceHandleMap['6'].recAccount eq  '44201533400052532193' }">selected </c:if>>建设东海支行44201533400052532193</option>
       </select>
      </td>
      <td><input name="recDate" value="${applyFinanceHandleMap['6'].recDate }" disabled="disabled"   type="text" class="srk easyui-datebox" editable="false" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td><input name="resource" value="${applyFinanceHandleMap['6'].resource }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <td style="border-right:0;"><input name="operator" value="${applyFinanceHandleMap['6'].operator }" disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
      <input type="hidden" name="remark" id="remark6" value="${applyFinanceHandleMap['6'].remark }" >
      <input type="hidden" name="recPro" value="6" >
       <input type="hidden" name="pid" value="${applyFinanceHandleMap['6'].pid }" >
      </form>
     </tr>
     <tr>
      <td>一次放款合计</td>
      <td colspan="5"><input name="oneTotalMoney" value="${applyFinanceHandleMap['3'].recMoney+applyFinanceHandleMap['6'].recMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"  disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
     </tr>
     <tr>
      <td>二次放款合计</td>
      <td colspan="5"><input name="twoTotalMoney" value="${applyFinanceHandleMap['4'].recMoney+applyFinanceHandleMap['5'].recMoney+applyFinanceHandleMap['6'].recMoney }" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"  disabled="disabled"   type="text" class="srk" onfocus="this.style.color='#000';" onBlur="this.style.color='#8d8d8d';"></td>
     </tr>
     <tr>
      <td style="border-bottom: 0;" >备注</td>
      <td colspan="5" style="border-bottom: 0;"><textarea disabled="disabled"   onchange="changeRemark('3,4,5,6',this.value)" type="text" class="srk1" onfocus="this.style.color='#000';"
        onBlur="this.style.color='#8d8d8d';"
       >${applyFinanceHandleMap['6'].remark }</textarea></td>
     </tr>
    </table>

    <!-- <p class="jl">
     <input type="button" class="submit" onclick="subForm('3,4,5,6')" id="" value="保存"> <input type="button" class="back" value="返回">
    </p> -->
   </div>

  </div>
 </div>
</body>
</html>