<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<script type="text/javascript">
	function save(){
	   $('#baseInfo').form('submit', {
			url : "saveComTeam.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				window.location.href="listComTeam.action?acctId=${acctId}&comId=${comId}";
			}
		}); 
	}	
	//绑定赋值
	$(function(){
		 setCombobox("cert_Type","CERT_TYPE","${cusComTeam.certType}");//1:id 2:数据字典 3：赋值 
		 setCombobox("edu_cation","EDUCATION","${cusComTeam.education}");
		 setCombobox("skill_Occ","OCC_NAME","${cusComTeam.skillOcc}");
	});
</script>
<div id="main_body">
<div id="cus_content">
<form id="baseInfo" action="customerController/saveComTeam.action" method="POST">
<table  class="cus_table" style="border:none;">
 	<tr>
 	 <input type="hidden" name="pid" value="${cusComTeam.pid}"/>
 	 <input type="hidden" name="cusComTeam.status" value="1"/> 
 	 <input type="hidden" name="cusComBase.pid" value="${comId}"/>
 	 <input type="hidden" name="cusAcct.pid" value="${acctId}"/>
 	 
	 <td class="align_right" style="width: 150px;"><span class="cus_red">*</span>姓名：</td>
	 <td><input type="text" class="text_style easyui-validatebox" data-options="required:true" name="name" value="${cusComTeam.name}"/></td>
	 <td class="align_right" style="width: 150px;"><span class="cus_red">*</span>性别：</td>
	 <td>
	    <input type="radio" name="sex"  value="1" <c:if test="${cusComTeam.sex==1 || empty cusComTeam.sex}">checked </c:if>/>男 &nbsp; &nbsp;
	    <input type="radio" name="sex"  value="2" <c:if test="${cusComTeam.sex==2 || empty cusComTeam.sex}">checked </c:if>/>女
	 </td>
  </tr>
  <tr>
	 <td class="align_right"><span class="cus_red">*</span>证件类型：</td>
	 <td>
	   <select  class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" name="certType" id="cert_Type">
				</select>
     </td>
	 <td class="align_right" ><span class="cus_red">*</span>证件号码：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="certNo" value="${cusComTeam.certNo}" data-options="required:true" /></td>
  </tr>
  <tr>
	 <td class="align_right">固定电话：</td>
	 <td><input type="text" class="text_style" name="fixedPhone" value="${cusComTeam.fixedPhone}"/></td>
	 <td class="align_right" >传真号码：</td>
	 <td><input type="text" class="text_style" name="fax" value="${cusComTeam.fax}"/></td>
  </tr>
    <tr>
	 <td class="align_right">手机号码：</td>
	 <td><input type="text" class="text_style" name="telephone" value="${cusComTeam.telephone}"/></td>
	 <td class="align_right" >年龄：</td>
	 <td><input type="text" class="text_style" name="age" maxlength="3" value="${cusComTeam.age}"/></td>
  </tr>
  <tr>
	 <td class="align_right">学历：</td>
	 <td>
	   <select  class="select_style easyui-combobox" editable="false" name="education" id="edu_cation">
	   </select>
	 </td>
	 <td class="align_right" >毕业院校：</td>
	 <td><input type="text" class="text_style" name="graSchool" value="${cusComTeam.graSchool}"/></td>
  </tr>
  <tr>
	 <td class="align_right">现工作单位：</td>
	 <td colspan="3"><input type="text" style="width: 535px;" class="text_style" name="workUnit" value="${cusComTeam.workUnit}"/></td>
  </tr>
   <tr>
	 <td class="align_right">职务：</td>
	 <td><input type="text" class="text_style" name="duty" value="${cusComTeam.duty}"/></td>
	  <td class="align_right">任职年限：</td>
	 <td><input type="text" class="text_style" name="dutyYear" value="${cusComTeam.dutyYear}"/></td>
  </tr>
   <tr>
	 <td class="align_right">是否董事会成员：</td>
	 <td>
  	    <input type="radio" name="boardMember" value="1" <c:if test="${cusComTeam.boardMember==1 || empty cusComTeam.boardMember}">checked </c:if>/>是 &nbsp; &nbsp;
	    <input type="radio" name="boardMember"  value="2" <c:if test="${cusComTeam.boardMember==2 || empty cusComTeam.boardMember}">checked </c:if> />否
	 </td>
	 <td class="align_right">工作年限：</td>
	 <td><input type="text" class="text_style" name="workYear" value="${cusComTeam.workYear}"/></td>
  </tr>
  <tr>
	 <td class="align_right">技术职称：</td>
	 <td>
  	    <select  class="select_style easyui-combobox" editable="false" name="skillOcc" id="skill_Occ">
	  </select>
	 </td>
	 <td class="align_right">行业经验：</td>
	 <td><input type="text" class="text_style" name="tradeSuffer" value="${cusComTeam.tradeSuffer}"/></td>
  </tr>
  <tr>
	 <td class="align_right">管理经验：</td>
	 <td><input type="text" class="text_style" name="manSuffer" value="${cusComTeam.manSuffer}"/></td>
	 <td class="align_right">主要履历：</td>
	 <td><input type="text" class="text_style" name="record" value="${cusComTeam.record}" /></td>
  </tr>
   <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px;">
		<textarea rows="4"  cols="74" name="remark">${cusComTeam.remark}</textarea>
	</td>
  </tr>
</table>
   </form>	    
</div>
</div>