<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
<title>合伙人合作信息</title>
<style type="text/css">
#tabs .tabs-header .tabs {
	height: 26px;
	position: fixed;
	top: 0px;
	background: #fff;
	z-index: 9999999;
	padding-top;
	2
	px;
}
.{padding-top:7px;}
</style>
</head>
<script type="text/javascript">

var pid = "${partnerInfo.pid}";
var editType = "${editType}";
$(function() {
	initUploadify();
	fileUploadDialogListener()
	if(editType == '3'){
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('disabled','disabled');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').attr('readOnly','readOnly');
		$('#tabs .easyui-linkbutton ,#tabs input,#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').attr('readOnly', 'readOnly');
		$('#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea').addClass('l-btn-disabled');
		$('#tabs .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}

	$('#cardNo').textbox({
		  onChange: function(value){
			  checkCertNumber();
		  }
		});
});
//文件上传对话框监听器
function fileUploadDialogListener() {
	$('#fileUploadDialog').dialog({
	    onClose:function(){
    		if($("#fileQueue .fileName") != null && $("#fileQueue .fileName").length  > 0 ){
    			$.messager.confirm('友情提示','关闭窗口前请取消未上传文件',function(r){
    			    $('#fileUploadDialog').dialog('open').dialog('setTitle', "文件上传");
    			});	
    		}
    		// 重新加载
			$("#grid_uploanFilelist").datagrid('load');
	    }
	  });
}
function initUploadify() {
	//多文件上传  //兼容火狐 需要  sessionid，否则拦截登陆302
	var uploadFileCount = 0 ;
    $("#uploadify").uploadify({  
    	'swf'       : '${basePath}/upload/uploadify.swf',
        'uploader'  : '${basePath}orgPartnerInfoController/uploadUserFile.action;jsessionid=${pageContext.session.id}',    
        //'folder'         : '/upload',  
        'queueID'        : 'fileQueue',
        'cancelImg'      : '${ctx}/p/xlkfinance/plug-in/uploadify/css/img/uploadify-cancel.png',
        'buttonText'     : '上传文件',
        'auto'           : false, //设置true 自动上传 设置false还需要手动点击按钮 
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'simUploadLimit' : 9,  
        'fileSizeLimit'   : '200MB',
        'fileTypeExts'        : '*.txt;*.doc;*.pdf;*.bmp;*.jpeg;*.jpg;*.png;*.gif;*.xls;*.wps;*.rtf;*.docx;*.xlsx;*.zip;*.ral;*.pptx;*.tiff;*.tif;*.rar',  
        'fileTypeDesc'       : 'All Files',
        'method'       : 'get',
		onUploadSuccess : function(file, data, response) {
			 uploadFileCount++;
		},
		onQueueComplete : function(file) { //当队列中的所有文件全部完成上传时触发 
			$.messager.alert("操作提示", "上传完成"+uploadFileCount+"个文件");
			uploadFileCount = 0;
		}
	});
}
//打开上传文件对口框
function openUploadFileDialog(){
	$('#fileUploadDialog').dialog('open').dialog('setTitle', "文件上传");
}
//上传文件
function uploadFile(){
	if($("#fileQueue .fileName") == null || $("#fileQueue .fileName").length  == 0 ){
		$.messager.alert("请选择文件");
		return false;
	}
	//多文件上传方式
	$('#uploadify').uploadify('settings','formData',{"orgPartnerId":${partnerInfo.orgUserInfo.pid}});
	$('#uploadify').uploadify('upload','*');
}
var uploanFilelist = {
		formatFileUrl : function(value, row, index) {
			
			var fileName = row.fileName;
			if(fileName!='' && fileName!=null &&fileName!=undefined){
				fileName = fileName.replace(/\ +/g,"");
			}
			var va = "<a onclick=downLoadFileByPath('"+row.fileUrl+"','"+fileName+"') href='#'> <font color='blue'>下载</font></a>";
			var lookUpDom = "";
			if(row.fileUrl!='' && row.fileUrl!=null &&row.fileUrl!=undefined){
				var path = row.fileUrl;
				var fileType=path.substring(path.lastIndexOf(".")+1);
				fileType = fileType.toLowerCase();
				if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
					lookUpDom = " | <a href='javascript:void(0)' onclick=lookUpFileByPath('"+row.fileUrl+"','"+fileName+"') class='download'><font color='blue'>预览</font></a> ";
				}
			}
			return va+lookUpDom;
		}
	}
function cancelPartner(){ 
	//parent.removePanel();
	//获取tabs对象
	var myObj = parent.$('#layout_center_tabs').tabs('getTab','合伙人合作管理');
	if(myObj){
	//获取iframe的URL
	var url = myObj[0].innerHTML;
	//打开数据
	parent.layout_center_addTabFun({
		title : '合伙人合作管理', //tab的名称
		closable : true, //是否显示关闭按钮
		content : url,//加载链接
		falg:true
	});
	}

	parent.$('#layout_center_tabs').tabs('close','修改合伙人合作信息');
}

/**
 * 保存合伙人合作信息
 */
function savePartnerCooperateInfo(){
	var rate = $("#rate").numberbox("getValue");
	if(rate >0){
		
	}else{
		$.messager.alert('操作提示','合作提成标准必须大于0！','error');	
		return;
	}
	$("#orgForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				alert("保存信息成功");
				cancelPartner();
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
}
/**
 * 校验身份证号码
 */
function checkCertNumber(){
	var certNumber = $("#cardNo").val();
	if(certNumber.length>0 &&!(IdCardValidate(certNumber)))
    {
		$.messager.alert('操作提示',"输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。",'error');	
        $("#cardNo").textbox('setValue','');
        return;
    }
	if(certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			$.messager.alert('操作提示',"请输入正确的身份证号！",'error');
			$("#cardNo").textbox('setValue','');
			 return;
		}
	}
}

</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="合伙人信息" id="tab1">
    <div style="padding: 30px 10px 0 10px;">
     <div class="easyui-panel" title="合伙人基本信息" data-options="collapsible:true" width="1039px;">
      <div id="personal" style="padding: 10px 0 15px 0;">
       <table class="beforeloanTable" style="width: 100%; padding: 10px 0 15px 0; border-bottom: 1px #ddd dashed; margin-bottom: 15px;">
        <tr>
         <td class="label_right"><font color="red">*</font>登录名：</td>
         <td><input class="easyui-textbox" id="loginName" readonly='readonly' required="true" name="orgUserInfo.userName"
          value="${partnerInfo.orgUserInfo.userName}" data-options="required:true,validType:['checkLoginName','englishOrNum']"
         /></td>
         <td class="label_right"><font color="red">*</font>合伙人姓名：</td>
         <td><input class="easyui-textbox" readonly="true" required="true" value="${partnerInfo.orgUserInfo.realName}"
          data-options="required:true,validType:'length[1,20]'"
         /></td>
        </tr>
        <tr>
         <td class="label_right"><font color="red">*</font>手机号码：</td>
         <td><input class="easyui-textbox" readonly="true" id="phone" required="true" id="phone"
          value="${partnerInfo.orgUserInfo.phone}" data-options="required:true,validType:['checkOrgPhone','phone']" missingMessage="请填写手机号码!"
         /></td>
         <td class="label_right"><font color="red">*</font>邮箱：</td>
         <td><input class="easyui-textbox" readonly="true" id="email" required="true" value="${partnerInfo.orgUserInfo.email}"
          data-options="required:true,validType:['checkOrgEmail','email']"
         /></td>
        </tr>
        <tr>
         <td class="label_right">认证说明：</td>
         <td><input class="easyui-textbox" readonly="true" value="${partnerInfo.auditDesc}" style="width: 65%; height: 60px"
          data-options="multiline:true,validType:'length[0,200]'"
         /></td>
        </tr>
        <tr>
         <td class="label_right"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="openUploadFileDialog()" iconCls="icon-add">资料上传</a></td>
        </tr>
       </table>

       <form action="<%=basePath%>orgPartnerInfoController/savePartnerCooperateInfo.action" id="orgForm" name="orgForm" method="post">
        <input type="hidden" name="pid" id="pid" value="${partnerInfo.pid }"> <input type="hidden" name="userId" id="userId"
         value="${partnerInfo.userId }"
        >
        <table class="beforeloanTable" style="width: 100%; padding: 10px 0 15px 0; border-bottom: 1px #ddd dashed; margin-bottom: 15px;">
         <tr>
          <td class="label_right"><font color="red">*</font>身份证号码：</td>
          <td><input class="easyui-textbox" id="cardNo" value="${partnerInfo.cardNo}" name="cardNo" data-options="required:true,validType:'length[1,18]'"/></td>
          <td class="label_right"><font color="red">*</font>收款账号：</td>
          <td><input class="easyui-textbox" required="true" name="payeeAccount" value="${partnerInfo.payeeAccount}" data-options="required:true,validType:'length[1,20]'"/></td>
          </tr>
          <tr>
          <td class="label_right"><font color="red">*</font>合作开始时间：</td>
          <td><input value="${partnerInfo.startTime}" name="startTime" class="easyui-datebox" id="startTime" editable="false" style="width: 150px;" required="true" missingMessage="请输入合作开始日期!"/></td>
          <td class="label_right1"><font color="red">*</font>合作提成标准：</td>
          <td>
          	<input value="${partnerInfo.rate }" name="rate" id="rate" class="easyui-numberbox" required="true" data-options="required:true,min:0,max:100,precision:2,groupSeparator:','" missingMessage="请输入合作提成标准!" style="width: 129px;"/>%
          </td>
         </tr>
         <tr>
          <td class="label_right">合作说明：</td>
          <td colspan="4"><input class="easyui-textbox" name="cooperationDesc" value="${partnerInfo.cooperationDesc}" style="width: 65%; height: 60px"
           data-options="multiline:true,validType:'length[0,200]'"
          /></td>
         </tr>
         <tr>
			<td class="label_right">备注：</td>
			<td>
				<input class="easyui-textbox" name="remark" value="${partnerInfo.remark}" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,200]'"/>
			</td>
		</tr>
        </table>

        <table class="beforeloanTable" style="width: 100%; padding: 10px 0 15px 0; border-bottom: 1px #ddd dashed; margin-bottom: 15px;">
         <tr>
          <td class="label_right"><font color="red">*</font>审核状态：</td>
          <td><select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" required="true" name="reviewStatus"
           style="width: 129px;"
          >
            <option value="-1">--请选择--</option>
            <option value="1" <c:if test="${partnerInfo.reviewStatus==1 }">selected </c:if>>未申请</option>
            <option value="10" <c:if test="${partnerInfo.reviewStatus==10 }">selected </c:if>>审核中</option>
            <option value="20" <c:if test="${partnerInfo.reviewStatus==20 }">selected </c:if>>审核通过</option>
            <option value="30" <c:if test="${partnerInfo.reviewStatus==30 }">selected </c:if>>审核不通过</option>
          </select></td>
         </tr>
         <tr>
          <td class="label_right">审核意见：</td>
          <td colspan="4"><input class="easyui-textbox" name="reviewDesc" value="${partnerInfo.reviewDesc}" style="width: 65%; height: 60px"
           data-options="multiline:true,validType:'length[0,200]'"
          /></td>
         </tr>
        </table>
        <div style="padding-bottom: 20px; padding-top: 10px; padding-left: 100px;">
         <a href="#" class="easyui-linkbutton" style="margin-left: 80px;" iconCls="icon-save" onclick="savePartnerCooperateInfo()">保存</a>
        </div>
       </form>
       <div id="fileUploadDialog" class="easyui-dialog" buttons="#uploanFileOperateDiv" style="width: 430px; height: 330px; padding: 10px" closed="true">
        <form id="fileUploadForm" name="fileUploadForm" method="post">
         <input type="hidden" id="isUpdateFile" name="isUpdateFile" value="0" />
         <div class="uploadmutilFile" style="margin-top: 10px;">
          <input type="file" name="uploadify" id="uploadify" />
         </div>
         <%--用来作为文件队列区域--%>
         <div id="fileQueue" style="max-height: 200; overflow: scroll; right: 50px; bottom: 100px; z-index: 999"></div>
        </form>
       </div>
       <div id="uploanFileOperateDiv">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="uploadFile()">开始上传</a> <a href="javascript:void(0)"
         class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#uploadify').uploadify('cancel','*')"
        >取消上传</a>
       </div>
      </div>
     </div>
    </div>
    <div class="easyui-panel" title="合伙人基本信息" data-options="collapsible:true" width="1039px;">
      <table id="grid_uploanFilelist" class="easyui-datagrid"
     data-options="
      url: '<%=basePath%>orgPartnerInfoController/userFileList.action?userId=${partnerInfo.orgUserInfo.pid }',
      method: 'POST',
      rownumbers: true,
      singleSelect: true,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'fileName',sortable:true" align="center" halign="center">文件名</th>
       <th data-options="field:'fileType',sortable:true" align="center" halign="center">类型</th>
       <th data-options="field:'fileSize',sortable:true" align="center" halign="center">大小</th>
       <th data-options="field:'uploadDttm',sortable:true,formatter:convertDateTime" align="center" halign="center">上传时间</th>
       <th data-options="field:'fileUrl',formatter:uploanFilelist.formatFileUrl,sortable:true" align="center" halign="center">下载</th>
      </tr>
     </thead>
    </table>
    </div>
   </div>
  </div>
</body>
</html>