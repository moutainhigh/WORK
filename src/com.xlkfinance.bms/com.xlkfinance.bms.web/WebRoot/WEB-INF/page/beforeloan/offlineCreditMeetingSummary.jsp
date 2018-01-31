<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<link href="${basePath}upload/css/fileUpload.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/beforeloan/offlineMeetingFile.js"></script>
<style>
.underlinecss{
text-decoration: underline;
}
</style>

<script type="text/javascript">
	var BASE_PATH = "${basePath}";
	var replaceRecordUserId = "-1";
	var replaceUserName = "-1";
	var isRecordUserIdChange = false;
	var nProjectId = projectId;
	var meetingId = -1;
	$(function(){
		nProjectId = projectId;
		var curUserPidss = ${currUser.pid};
		
		var height = document.body.clientHeight;
		$('#opFileTable').datagrid({ 
			height:(height)
		});
		loadOfflineMeetingFileTable(); 
	
		$('#recordUserId').textbox('setValue',curUserPidss);    
		$('#recordUserId_init').combobox({
				url:'${basePath}sysUserController/getAllSysUserForcombox.action',
				method:'post',
				valueField:'pid',
				textField:'realName',
				
				onSelect:function(){
					isRecordUserIdChange = true;
					var recordUserId_init = $('#recordUserId_init').combobox('getValue');
					$("#recordUserId").textbox('setValue',recordUserId_init); 
				}
		});
		
		initBizMeetingInfo(curUserPidss);
		
		$('.easyui-filebox').filebox({ 
			 buttonText: '选择文件', 
			 missingMessage:'选择一个文件'
			}) ;
			
		 $('#w').window({   
			    width:400,   
			    height:400,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:false,
			    modal:true
		 });   
		 
		 $('#opFileTable').datagrid({
			 onLoadSuccess:function(){
				 if("task_OfflineMeetingResult"==workflowTaskDefKey){ 
						//线下待审会会议
						
				 }else{
					$('.control_disoffline .easyui-combobox').attr('disabled','disabled');
					$('.control_disoffline input[type="checkbox"]').attr('disabled','disabled');
					$('.control_disoffline .easyui-textbox').attr('disabled','disabled');
					$('.control_disoffline .easyui-linkbutton ,.control_disoffline a:not(.download)').removeAttr('onclick');
					$('.control_disoffline .easyui-linkbutton ,.control_disoffline input,.control_disoffline textarea,.control_disoffline a:not(.download)').attr('disabled','disabled');
					$('.control_disoffline .easyui-linkbutton ,.control_disoffline input,.control_disoffline textarea,.control_disoffline a:not(.download)').attr('readOnly','readOnly');
					$('.control_disoffline .easyui-linkbutton ,.control_disoffline input,.control_disoffline textarea,.control_disoffline a:not(.download)').addClass('l-btn-disabled');
				 }		
			 }
		 });
	});
	 
		function submitOfflineMeetingForm(){
			$("#offlineMeeting_projectId").val(projectId);
			if($("#meetingResult").val().trim()==""){
				$.messager.alert('操作','会议结果不能为空!','error');
				return false;
			}
			$("#project_Id").val(projectId);
			
			// 判断是什么操作
			 var url1 = "<%=basePath%>beforeLoanController/saveOfflineMeetingInfo.action"; 
			 if($("#meetingId").val() != -1 && $("#meetingId").val() != 0){
				url1 = "<%=basePath%>beforeLoanController/updateOfflineMeetingInfo.action";
			} 
			 
			$('#offlineMeetingForm').attr('enctype','');
			$('#offlineMeetingForm').form('submit',{
				//url:'${basePath}beforeLoanController/obtainOfflineMeetingNoFile.action',
				url:url1,
				success:function(data) {
						var str = eval("("+data+")"); 
						if(str.msg=="fail"){
							$.messager.alert('操作','保存失败!','error');
						}else{
							applyCommon.offlineMeeting = 1;
							$.messager.alert('操作','保存成功!','info');
							meetingId=str.meetingId;
							$("#meetingId").val(str.meetingId);
						}
						$("#opFileTable").datagrid("reload");
						initBizMeetingInfo();
				}
			}, "json");
			
		}
			 
	 
	function submitFileForm(){
		$("#project_Ids").val(projectId);
		if($("#offlineMeetingFiles").textbox('getValue')!=''){
			$('#editfileUploadFormedit').attr('enctype','multipart/form-data');
			$('#editfileUploadFormedit').form('submit',{
				/* url:'${basePath}beforeLoanController/obtainOfflineMeeting.action', */
				url:'${basePath}beforeLoanController/saveFileInfo.action?meetingId='+$("#meetingId").val()+"&projectId="+projectId,
				success:function(data) {
						if(data!="saveSucc"){
							$.messager.alert('操作','保存失败!','error');
						}else{
							applyCommon.offlineMeeting = 1;
							$.messager.alert('操作','保存成功!','info');
							$("#upload-edit").dialog("close");
						}
						$("#opFileTable").datagrid("reload");
						$("#offlineMeetingFiles").textbox('setValue','');
				},
				
			});
		}else{
			$.messager.alert('操作','请选择文件!','info');
		} 
	}
	//选择参会人员，填入到文本框中
	function selectBizMeetingMinutesMember(){
		var allBizMeetingMinutesMember = $("#allBizMeetingMinutesMember").textbox('getValue'); 	
		var allMeetingMembersPID = $("#allMeetingMembersPID").textbox('getValue'); 
        var meetingMembers = $("#meetingMemberTable").datagrid('getSelections');
        if(allMeetingMembersPID==""){
        	for(var i=0;i<meetingMembers.length;i++){
       			meetingMemberNameTemp = meetingMembers[i].realName;
       			allBizMeetingMinutesMember+=meetingMemberNameTemp+';';
       			
       			var meetingMemberPId = meetingMembers[i].pid;
       			allMeetingMembersPID+=meetingMemberPId+';';
       		}
        }else{
        	for(var i=0;i<meetingMembers.length;i++){
        		if(allMeetingMembersPID.indexOf(meetingMembers[i].pid)>-1){
        			
        		}else{
        			meetingMemberNameTemp = meetingMembers[i].realName;
           			allBizMeetingMinutesMember+=meetingMemberNameTemp+';';
           			
           			var meetingMemberPId = meetingMembers[i].pid;
           			allMeetingMembersPID+=meetingMemberPId+';';
        		}
       		}
        }
	   	$("#allMeetingMembersPID").textbox('setValue',allMeetingMembersPID); 	
		$("#allBizMeetingMinutesMember").textbox('setValue',allBizMeetingMinutesMember); 
		$('#ws').window('close', true); 
	}
	
	function clearOfflineMeetingForm(){
		$('#offlineMeetingForm').form('reset');
	}
	
	function openBizMeetingWindow(){
		$('#ws').window('open');
	}
	
	function initBizMeetingInfo(curUserPidss){
		$.ajax({				
			type: "POST",			
	        url: BASE_PATH+"beforeLoanController/initOfflineMeeting.action?projectId="+projectId,					
			success : function(data) {	
				
					var bizProjectMeetingDTO = $.parseJSON(data);
					if(bizProjectMeetingDTO.meetingDttm && bizProjectMeetingDTO.meetingDttm != "" && bizProjectMeetingDTO.meetingDttm !=null){
						applyCommon.offlineMeeting = 1;
					}
					$("#meetingDttm").datetimebox('setValue',bizProjectMeetingDTO.meetingDttm);
					$("#meetingLocation").textbox('setValue',bizProjectMeetingDTO.meetingLocation); 
					if(bizProjectMeetingDTO.recordUserId!=0){
						$("#recordUserId_init").combobox('select',bizProjectMeetingDTO.recordUserId);
						replaceRecordUserId = bizProjectMeetingDTO.recordUserId;
					}else{
						$("#recordUserId_init").combobox('select',curUserPidss);
					}
					replaceUserName = bizProjectMeetingDTO.userName;
					$("#allMeetingMembersPID").textbox('setValue',bizProjectMeetingDTO.allMeetingMembersPID); 
					$("#allBizMeetingMinutesMember").textbox('setValue',bizProjectMeetingDTO.allBizMeetingMinutesMember); 
					if(bizProjectMeetingDTO.meetingResult &&bizProjectMeetingDTO.meetingResult!=''&&bizProjectMeetingDTO.meetingResult!=null){
						$("#meetingResult").text(bizProjectMeetingDTO.meetingResult);
					}
					if(bizProjectMeetingDTO.meetingNum){
						$(".meetingNum").text(bizProjectMeetingDTO.meetingNum);
					}
					$("#meetingId").val(bizProjectMeetingDTO.meetingId);
				}			
			});	
	}
	
	function fcmsReset(){
		$('#fcmsForm #realName').textbox('setValue','');
		$('#fcmsForm #userName').textbox('setValue','');
	}
</script>
<div class="easyui-panel control_disoffline" title="线下贷审会会议纪要" data-options="collapsible:true" style="width:auto">
	<div style="padding:10px 60px 20px 60px">
		<form method="post" id="offlineMeetingForm">
			<input type="hidden" id="offlineMeeting_projectId" name="offlineMeeting_projectId" value="">
			<input type="hidden" id="project_Id" name="projectId" value="">
				<input type="hidden" id="meetingId" name="meetingId" >
			<table cellpadding="5" style="height: auto;width: 100%">
				 <tr>
				 	<td width="145" height="28" align="right"><span>贷审会决议编号：</span></td>
				 	<td><span class="cus_red meetingNum"></span></td>
				 </tr>
				 <tr>
					<td height="28" align="right"><span class="cus_red">*</span>会议时间：</td>
					<td><input class="easyui-datetimebox offlineMeetingInput" name="meetingDttm" id="meetingDttm" value="" required="true"></td>
					<td align="right"><span class="cus_red">*</span>记录人员：</td>
					<td>
						<div style="display: none;"> 
							<input class="easyui-textbox offlineMeetingInput" id="recordUserId" name="recordUserId"/>
						</div>
						<input class="easyui-combobox offlineMeetingInput" id="recordUserId_init" name="recordUserId_init" editable="false" style="width:100px" required="true"/>
					</td>
				</tr>
				<tr>
					<td height="28" align="right"><span class="cus_red">*</span>会议地点：</td>
				    <td><input class="easyui-textbox offlineMeetingInput"  id="meetingLocation" name="meetingLocation" style="width:367px;" required="true"/></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right" height="28"><span class="cus_red">*</span>参与人员：</td>
					<td>
					<div style="display:none;">
					<input class="easyui-textbox offlineMeetingInput" id="allMeetingMembersPID" name="allMeetingMembersPID"></input>
					</div>
					<input class="easyui-textbox offlineMeetingInput" id="allBizMeetingMinutesMember" name="allBizMeetingMinutesMember" style="height:28px;width:365px;" required="true" readonly="readonly"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="openBizMeetingWindow()">选择</a>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><span class="cus_red">*</span>会议结果：</td>
					<td align="left">
					<textarea rows="4" style="width: 365px;height:60px;"
							name="meetingResult" id="meetingResult" class="text_style" required="true"></textarea>
					</td>
					<td></td>
					<td></td>
				</tr> 
					<tr>
					<td></td>
					<td align="center" colspan="2">
						<a href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton offlineMeetingInput" onclick="submitOfflineMeetingForm()">保存</a>
						<a href="javascript:void(0)" iconcls="icon-reload" class="easyui-linkbutton offlineMeetingInput_but" onclick="clearOfflineMeetingForm()">重置</a>
					</td>
					<td></td>			
				</tr>
				<tr>
					<td align="right" colspan="4" height="28">
						<div><table class="offlineMeetingInput" id="opFileTable" title="会议纪要文件"></table></div>
					</td>
				</tr>  
				
			
			</table>
		</form>
	
		<div id="ws" class="easyui-window" title="人员选择---" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:750px;height:350px;padding:10px;">
			<table id="meetingMemberTable" class="easyui-datagrid" title="" style="width:auto;height:290px"
				data-options="collapsible:true,
							  height: '100%',
							  loadMsg: '数据加载中请稍后……',
							  toolbar: '#toolbar_fcms',
							  rownumbers: true,
							  pagination: true,
	       					  checkOnSelect: false,
							  url:'${basePath}sysUserController/getAllSysUser.action',
							  method:'post',
							  idField: 'pid'">
					<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'pid',width:80" hidden="true">pid</th>
						<th data-options="field:'department',width:80,align:'center'">部门</th>
						<th data-options="field:'userName',width:100,align:'center'">用户名</th>
						<th data-options="field:'realName',width:100,align:'center'">姓名</th>
					</tr>
					</thead>
			</table>
		</div>
		<div id="toolbar_fcms">
		<form id="fcmsForm" action="${basePath}sysUserController/getAllSysUser.action">
		 	<table class="beforeloanTable">
		 		<tr>
		 			<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectBizMeetingMinutesMember()">选 择</a></td>
		 			<td class="label_right">真实姓名：</td>
		 			<td><input name="realName" class="easyui-textbox" id="realName"/></td>
		 			<td class="label_right">用户账户：</td>
		 			<td><input name="userName" class="easyui-textbox" id="userName"/></td>
		 			<td>
		 				<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearchPage('#meetingMemberTable','#fcmsForm')">查询</a>
						<a href="#" onclick="fcmsReset();" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
		 			</td>
		 		</tr>
		 	</table>
		</form>
	   </div>
		
		<div id="upload-edit" class="easyui-dialog" buttons="#upFileinfo" style="width: 650px; height: 150px; padding: 10px 20px;" closed="true">
			<form id="editfileUploadFormedit" name="fileUploadeditForm"  method="post" >
				<input type="hidden" id="project_Ids" name="projectId" value="">
				<input type="hidden" id="meetingIdInfo" name="meetingId" >
				<table>
					<tr>
						<td  ><span class="cus_red">*</span>会议纪要：</td>
						<td >
							<input class="easyui-filebox offlineMeetingInput"  name="offlineMeetingFile" id="offlineMeetingFiles" style="width:425px">
						</td>
					</tr>
					</table>
			</form>
				
				
		
	</div>
	<div id="upFileinfo">
		<a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitFileForm()">保存</a> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#upload-edit').dialog('close')">取消</a>
	</div>
	</div>
</div>