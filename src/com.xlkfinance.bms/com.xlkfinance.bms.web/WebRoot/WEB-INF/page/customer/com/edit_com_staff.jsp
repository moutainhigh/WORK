<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
   function saves(){
	   $('#baseInfo').form('submit', {
			url : "saveComStaff.action",
			success : function(result) {
				alert("保存成功！");
				window.location='${basePath}customerController/editComStaff.action?comId=${comId}&acctId=${acctId}';
			},error : function(result){
				alert("保存失败！");
			}
		}); 
   } 
   
   //重置
   function cancles(){
	   $("#collegeNum").val('');
	   $("#collegePercent").val('');
	   $("#undergraduatNum").val('');
	   $("#underPercent").val('');
	   $("#mastersNum").val('');
	   $("#mastersPercent").val('');
	   $("#drPercent").val('');
	   $("#drNum").val('');
	   $("#drPercent").val('');
	   $("#postdoctoralNum").val('');
	   $("#postdoctoralPercent").val('');
	   $("#heightTitleNum").val('');
	   $("#titlePercent").val('');
	   $("#intermediateNum").val('');
	   $("#intermediatePercent").val('');
	   $("#otherNum").val('');
	   $("#otherPercent").val('');
	   $("#managerNum").val('');
	   $("#managerPercent").val('');
	   $("#technicalNum").val('');
	   $("#technicalPercent").val('');
	   $("#otherNums").val('');
	   $("#otherPercents").val('');
	   $("#ageNum").val('');
	   $("#agePercent").val('');
	   $("#ageNum1").val('');
	   $("#agePercent1").val('');
	   $("#ageNum2").val('');
	   $("#agePercent2").val('');
	   $("#ageNum3").val('');
	   $("#agePercent3").val('');
   }
   
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<jsp:include page="cus_com_tab.jsp">
<jsp:param value="3" name="tab"/>
</jsp:include>
<div style="width:780px;float:left">  
<table class="cus_table2">
<tr><th>填写公司员工结构</th></tr> 
<tr>
<td>
<form action="customerController/saveComStaff.action" id="baseInfo" method="post">
<div style="padding:10px 0">
	    <input type="button" name="save" value="保存" class="cus_save_btn"  onclick="saves();"/>
   		<input type="button" name="cancle" value="重置" class="cus_save_btn" onclick="javascript:cancles();"/> 
    </div>
<table  class="cus_table staff_table"  cellspacing="0" cellPadding="0" style="border-collapse:collapse;">
 	<tr>
 	 <input type="hidden" name="cusComBase.pid" value="${comId}"/>
	 <td style="width:12%" align="center">分类标准</td>
	 <td style="width:15%">类别</td>
	 <td style="width:16%">人数</td>
	 <td style="width:16%">所占比例%</td>
  </tr>
  <tr>
  <td bgcolor="#FFCC66" rowspan="5" align="center">教育程度</td>
   <td class="align_right">大专及本科以下</td>
    <td><input type="text" class="text_style" name="staffs[0].personNum" id="collegeNum" value="${cusComStaffDTO.staffs[0].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[0].ratio" id="collegePercent" value="${cusComStaffDTO.staffs[0].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[0].sortNum"  value="1"/>
    	 <input type="hidden" name="staffs[0].cusComBase.pid" value="${comId}"/>
    	 <input type="hidden" name="staffs[0].pid" value="${cusComStaffDTO.staffs[0].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">本科</td>
    <td><input type="text" class="text_style" name="staffs[1].personNum" id="undergraduatNum" value="${cusComStaffDTO.staffs[1].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[1].ratio" id="underPercent" value="${cusComStaffDTO.staffs[1].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[1].sortNum"  value="2"/>
    	 <input type="hidden" name="staffs[1].cusComBase.pid" value="${comId}"/>
    	  <input type="hidden" name="staffs[1].pid" value="${cusComStaffDTO.staffs[1].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">硕士</td>
    <td><input type="text" class="text_style" name="staffs[2].personNum" id="mastersNum" value="${cusComStaffDTO.staffs[2].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[2].ratio" id="mastersPercent" value="${cusComStaffDTO.staffs[2].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[2].sortNum"  value="3"/>
    	<input type="hidden" name="staffs[2].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[2].pid" value="${cusComStaffDTO.staffs[2].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">博士</td>
    <td><input type="text" class="text_style" name="staffs[3].personNum" id="drNum" value="${cusComStaffDTO.staffs[3].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[3].ratio" id="drPercent" value="${cusComStaffDTO.staffs[3].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[3].sortNum"  value="4"/>
    	<input type="hidden" name="staffs[3].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[3].pid" value="${cusComStaffDTO.staffs[3].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">博士后</td>
    <td><input type="text" class="text_style" name="staffs[4].personNum" id="postdoctoralNum" value="${cusComStaffDTO.staffs[4].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[4].ratio" id="postdoctoralPercent" value="${cusComStaffDTO.staffs[4].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[4].sortNum"  value="5"/>
    	<input type="hidden" name="staffs[4].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[4].pid" value="${cusComStaffDTO.staffs[4].pid}"/>
    </td>
  </tr>
  <tr>
  <td bgcolor="##0099FF" rowspan="3" align="center">技术职称</td>
   <td class="align_right">高级职称</td>
    <td><input type="text" class="text_style" name="staffs[5].personNum" id="heightTitleNum" value="${cusComStaffDTO.staffs[5].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[5].ratio" id="titlePercent" value="${cusComStaffDTO.staffs[5].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[5].sortNum"  value="6"/>
    	<input type="hidden" name="staffs[5].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[5].pid" value="${cusComStaffDTO.staffs[5].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">中级职称</td>
    <td><input type="text" class="text_style" name="staffs[6].personNum" id="intermediateNum" value="${cusComStaffDTO.staffs[6].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[6].ratio" id="intermediatePercent" value="${cusComStaffDTO.staffs[6].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[6].sortNum"  value="7"/>
    	<input type="hidden" name="staffs[6].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[6].pid" value="${cusComStaffDTO.staffs[6].pid}"/>
    </td>
  </tr>
   <tr>
   <td class="align_right">其它</td>
    <td><input type="text" class="text_style" name="staffs[7].personNum" id="otherNum" value="${cusComStaffDTO.staffs[7].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[7].ratio" id="otherPercent" value="${cusComStaffDTO.staffs[7].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[7].sortNum"  value="8"/>
    	<input type="hidden" name="staffs[7].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[7].pid" value="${cusComStaffDTO.staffs[7].pid}"/>
    </td>
  </tr>
  <tr>
   <td bgcolor="#FF66FF" rowspan="3" align="center">工作性质</td>
   <td class="align_right">管理人员</td>
    <td><input type="text" class="text_style" name="staffs[8].personNum" id="managerNum" value="${cusComStaffDTO.staffs[8].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[8].ratio" id="managerPercent" value="${cusComStaffDTO.staffs[8].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[8].sortNum"  value="9"/>
    	<input type="hidden" name="staffs[8].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[8].pid" value="${cusComStaffDTO.staffs[8].pid}"/>
    </td>
  </tr>
  <tr>
 
   <td class="align_right">技术人员</td>
    <td><input type="text" class="text_style" name="staffs[9].personNum" id="technicalNum" value="${cusComStaffDTO.staffs[9].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[9].ratio" id="technicalPercent" value="${cusComStaffDTO.staffs[9].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[9].sortNum"  value="10"/>
    	<input type="hidden" name="staffs[9].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[9].pid" value="${cusComStaffDTO.staffs[9].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">其它</td>
    <td><input type="text" class="text_style" name="staffs[10].personNum" id="otherNums" value="${cusComStaffDTO.staffs[10].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[10].ratio" id="otherPercents" value="${cusComStaffDTO.staffs[10].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[10].sortNum"  value="11"/>
    	<input type="hidden" name="staffs[10].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[10].pid" value="${cusComStaffDTO.staffs[10].pid}"/>
    </td>
  </tr>
  <tr>
   <td bgcolor="#CCCCCC" rowspan="4" align="center">年龄段</td>
   <td class="align_right">20-30</td>
    <td><input type="text" class="text_style" name="staffs[11].personNum" id="ageNum" value="${cusComStaffDTO.staffs[11].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[11].ratio" id="agePercent" value="${cusComStaffDTO.staffs[11].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[11].sortNum"  value="12"/>
    	<input type="hidden" name="staffs[11].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[11].pid" value="${cusComStaffDTO.staffs[11].pid}"/>
    </td>
  </tr>
  <tr>
  
   <td class="align_right">30-40</td>
    <td><input type="text" class="text_style" name="staffs[12].personNum" id="ageNum1" value="${cusComStaffDTO.staffs[12].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[12].ratio" id="agePercent1" value="${cusComStaffDTO.staffs[12].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[12].sortNum"  value="13"/>
    	<input type="hidden" name="staffs[12].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[12].pid" value="${cusComStaffDTO.staffs[12].pid}"/>
    </td>
  </tr>
   <tr>
   <td class="align_right">40-50</td>
    <td><input type="text" class="text_style" name="staffs[13].personNum"  id="ageNum2" value="${cusComStaffDTO.staffs[13].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[13].ratio" id="agePercent2" value="${cusComStaffDTO.staffs[13].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[13].sortNum"  value="14"/>
    	<input type="hidden" name="staffs[13].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[13].pid" value="${cusComStaffDTO.staffs[13].pid}"/>
    </td>
  </tr>
  <tr>
   <td class="align_right">50岁以上</td>
    <td><input type="text" class="text_style" name="staffs[14].personNum" id="ageNum3" value="${cusComStaffDTO.staffs[14].personNum}"/></td>
    <td><input type="text" class="text_style" name="staffs[14].ratio" id="agePercent3" value="${cusComStaffDTO.staffs[14].ratio}"/>
    	<input type="hidden" class="text_style" name="staffs[14].sortNum"  value="15"/>
    	<input type="hidden" name="staffs[14].cusComBase.pid" value="${comId}"/>
    	<input type="hidden" name="staffs[14].pid" value="${cusComStaffDTO.staffs[14].pid}"/>
    </td>
  </tr>
</table>
</form>
</td>
</tr>
</table>	
</div>
</div>
</div>
</div>
</div>
</body>