<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/config.jsp"%>
<script type="text/javascript" charset="utf-8">
	function logoutFun() {		
		$.messager.confirm('友情提示','确定退出系统?',function(r){
		    if (r){
		    	window.location = 'logout.action';
		    }
		});	
	}
	
	// 个人信息
	function readUserInfoFun(falg){
		$('#fm_user').form('reset');
		var url = '<%=basePath%>sysUserController/userInfo.action';
		$.post(url,function(result){
			
			var user = eval("("+result+")");
			$('#fm_user').form('load',{
				pid: user.pid,
				userName: user.userName,
				realName: user.realName,
				department: user.department,
				memberId: user.memberId,
				jobTitle: user.jobTitle,
				phone: user.phone,
				mail: user.mail,
				enterpriseQQ: user.enterpriseQQ,
				personalQQ: user.personalQQ,
				workPhone: user.workPhone,
				extension: user.extension,
				superiorId: user.superiorId,
				orgId:user.orgId,
				status:user.status
			});
		});
		if(falg && falg == 1 ){
			$('#fm_user input').attr("readonly",true);
			$('#dlg').dialog({
				buttons:[]
			});
			$('#dlg').dialog('open').dialog('setTitle', "个人信息");
		}else if(falg && falg == 2){
			$('#fm_user input').attr("readonly",true);
			$('#fm_user .edit input').attr("readonly",false);
			$('#dlg').dialog({
				buttons:[{
		        	 iconCls: "icon-save",
		        	 text:'保存',
		        	 handler:function(){
	        			// 新增  or 更新
	        		 	$("#fm_user").form('submit',
	        		 		{
	        		 		url : '<%=basePath%>sysUserController/update.action',
	        		 		success : function(result) {
	        		 			// 转换成json格式的对象
	        		 			var ret = eval("("+result+")");
	        		 			if(ret && ret.header["success"]){
	        		 				$("#dlg").dialog('close');
	        		 				$.messager.alert('操作提示',ret.header["msg"],'info');
	        		 			}else{
	        		 				$.messager.alert('操作提示',ret.header["msg"],'info');	
	        		 			}
	        		 		}
	        		 	}); 
		        	 }
		         },
		         {
		        	 iconCls: "icon-cancel",
		        	 text:'取消',
		        	 handler:function(){
		        		 $('#dlg').dialog('close');
		        	 } 
		         }]
			});
			$('#dlg').dialog('open').dialog('setTitle', "修改信息");
		}
	}

	/**
	 *修改密码
	 */
	function editPwd(){
		$('#fm_pwd_edit').form('reset');
		$('#dlg_pwd_edit').dialog({
			buttons:[{
	        	 iconCls: "icon-save",
	        	 text:'保存',
	        	 handler:function(){
        			// 修改密码
        		 	$("#fm_pwd_edit").form('submit',
        		 		{
        		 		url : '<%=basePath%>sysUserController/editPassword.action',
        		 		success : function(result) {
        		 			// 转换成json格式的对象
        		 			var ret = eval("("+result+")");
        		 			if(ret && ret.header["success"]){
        		 				$("#dlg_pwd_edit").dialog('close');
        		 				$.messager.alert('操作提示',ret.header["msg"],'info');
        		 			}else{
        		 				$.messager.alert('操作提示',ret.header["msg"],'info');	
        		 			}
        		 		}
        		 	}); 
	        	 }
	         },
	         {
	        	 iconCls: "icon-cancel",
	        	 text:'取消',
	        	 handler:function(){
	        		 $('#dlg_pwd_edit').dialog('close');
	        	 } 
	         }]
		});
		$('#dlg_pwd_edit').dialog('open').dialog('setTitle', "修改密码");
	}
</script>
<div style="padding:12px 0 0 20px;"><%-- 
	<img src="${ctx}/p/xlkfinance/images/logo1.jpg" /> --%>
	<table>
		<tr>
			<td><img src="${ctx}/p/xlkfinance/images/logo.png" alt="Q房金融业务管理平台"/></td>
			<%-- <td><img src="${ctx}/p/xlkfinance/images/line.png" /></td>
			<td><b style="font-size: 18px;color:#fff; font-family: 'Microsoft YaHei', '微软雅黑';line-height: 40px;"></b></td> --%>
		</tr>
	</table>
</div>
<div style="position: absolute; right: 20px; bottom:20px;">
	<span style="color: white;">欢迎 <font color="white">${currUser.realName}</font> 登录系统！</span>
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a> 
	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-logout">注销</a>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="readUserInfoFun(1);">个人信息</div>
	<div onclick="readUserInfoFun(2);">修改信息</div>
	<div onclick="editPwd();">修改密码</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="logoutFun();">退出系统</div>
</div>
	<!--窗口 -->
	<div id="dlg" class="easyui-dialog" style="width:700px;height:310px;padding:20px 20px" 
		closed="true" >
     	<form id="fm_user" method="POST">
     		<div  class="fitem" id="update">
             	<label style="text-align:right;">员工账户：</label>
             	<input name="userName" id="userName" class="easyui-textbox"  missingMessage="请填写用户名!">
                <label style="text-align:right;">手机号码：</label>
                <input name="phone" id="phone" class="easyui-textbox " data-options="required:true,validType:'phone'" missingMessage="请填写手机号码!">
     			<input type="hidden" name="pid" />
     			<input type="hidden" name="orgId" />
     			<input type="hidden" name="status" />
     	  	</div>
     	  	<div class="fitem" >
             	<label style="text-align:right;">真实姓名：</label>
             	<input name="realName" id="realName" class="easyui-textbox" data-options="required:true" missingMessage="请填写真实姓名!">
             	<label style="text-align:right;">部门：</label>
             	<input name="department" class="easyui-textbox">
     	  	</div>
         	<div class="fitem">
             	<label style="text-align:right;">员工工号：</label>
             	<input name="memberId" class="easyui-textbox" data-options="required:true" missingMessage="请填写员工工号!" >
             	<label style="text-align:right;">职位：</label>
             	<input name="jobTitle" class="easyui-textbox" >
         	</div>
         	<div class="fitem edit">
	            <label style="text-align:right;">邮箱：</label>
	            <input name="mail" class="easyui-textbox " data-options="required:true,validType:'email'" missingMessage="请填写邮箱!">
	        </div>
         	<div class="fitem edit">
             	<label style="text-align:right;">企业QQ：</label>
             	<input name="enterpriseQQ" class="easyui-numberbox " data-options="required:true" missingMessage="请填写企业QQ!">
             	<label style="text-align:right;">个人QQ：</label>
             	<input name="personalQQ" class="easyui-numberbox ">
         	</div>
         	<div class="fitem edit">
             	<label style="text-align:right;">办公 电话：</label>
             	<input name="workPhone" class="easyui-textbox " >
             		分机-
             	<input name="extension" class="easyui-textbox " style="width: 40px;">
             	<input name="superiorId"  id="superioridValue_id" type="hidden">
         	</div>
    	</form>
	</div>
	
	<!-- 修改密码 -->
	<div id="dlg_pwd_edit" class="easyui-dialog" style="width:300px;height:240px;padding:20px 20px" 
		closed="true" >
     	<form id="fm_pwd_edit" method="POST">
     	  	<div class="fitem" >
             	<label style="text-align:right;">旧密码：</label>
             	<input name="oldPwd" id="oldPwd" class="easyui-textbox" data-options="required:true" missingMessage="请请输入旧密码!">
     	  	</div>
     	  	<div class="fitem" >
             	<label style="text-align:right;">新密码：</label>
             	<input name="newPwd" id="newPwd" class="easyui-textbox" data-options="required:true,validType:'englishOrNum'" missingMessage="请请输入新密码,数字或者字母，6到18个字符!">
     	  	</div>
     	  	<div class="fitem" >
             	<label style="text-align:right;">确认新密码：</label>
             	<input name="affirmPwd" id="affirmPwd" class="easyui-textbox" data-options="required:true,validType:'englishOrNum'" missingMessage="确认密码!">
     	  	</div>
    	</form>
	</div>
