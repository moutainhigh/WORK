<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<style type="text/css">
.label_right1{width: 100px;}
</style>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}
	function formatterType(val, row) {
		if (val == 1) {
			return "收保证金";
		} else if (val == 2) {
			return "退保证金";
		} else {
			return "未知";
		}
	}
	function formatterRemark(value, row, index) {
		if (value==null) {
			return "";
		}
        var abValue = value;
        if (value.length>=12) {
           abValue = value.substring(0,10) + "...";
        }
		var va = '<a href="#" title="' + value + '" class="note">' + abValue + '</a>';
        return va;
	}
	function openOrgAssureFundRecord() {
		// 重新加载数据
		$("#grid2").datagrid('load', {});
		// 清空所选择的行数据
		clearSelectRows("#grid2");
		$('#orgAssureFundRecordForm').form('reset');
		$('#orgAssureFundRecord_dialog').dialog('open').dialog('setTitle',
				"保证金记录");
	}
	//根据保证金所选的账户名填充账号
	function getAccount(){
	 var accountValue = $("#accountName1").combobox("getValue");
		setAccountCombobox("account1","1",accountValue,"1");
	}
	
	function subOrgAssureFundRecordForm() {
		var row = $('#grid2').datagrid('getSelections');
		if (row.length != 1) {
			$.messager.alert('操作提示', "请选择机构", 'error');
			return;
		}
		var orgId=row[0].pid;
		$("#orgId").val(orgId);
		
		var accountName = $("#accountName1").combobox('getText');
		$("#orgAssureFundRecordForm input[name='accountName']").val(accountName);
		
		// 提交表单
		$("#orgAssureFundRecordForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.confirm('操作提示', '保存成功!', function(r) {
						$('#orgAssureFundRecord_dialog').dialog('close');
						// 必须重置 datagr 选中的行 
						$("#grid").datagrid("clearChecked");
						$("#grid").datagrid('reload');
					});

				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	$(function() {
		$("#orgName").textbox({
		    "onChange":function(){
		    	var orgName=$("#orgName").textbox("getValue");
				if (orgName==null) {
					orgName="";
				}
				// 重新加载数据
				$("#grid2").datagrid('load', {"orgName":orgName});
				// 清空所选择的行数据
				clearSelectRows("#grid2");
		    }
		  });
	});
</script>
<title>保证金记录</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>orgAssureFundRecordController/list.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td align="right" height="28">账户名称：</td>
        <td><input class="easyui-textbox" name="accountName" data-options="validType:'length[0,20]'"/></td>
        <td align="right" height="28">银行：</td>
        <td><input class="easyui-textbox" name="bank" data-options="validType:'length[0,20]'"/></td>
        <td align="right" height="28"  width="80">机构名称：</td>
        <td><input class="easyui-textbox" name="orgName" data-options="validType:'length[0,20]'"/></td>
       </tr>
       <tr>
        <td align="right" height="28">到账日期：</td>
        <td><input class="easyui-datebox" editable="false" name="recDate" /> 至 <input class="easyui-datebox" editable="false" name="recDateEnd" /></td>
        <td align="right" height="28" width="80">类型：</td>
        <td><select class="select_style easyui-combobox" editable="false" name="type" style="width:150px;">
          <option value=-1 selected="selected">--请选择--</option>
          <option value=1>收保证金</option>
          <option value=2>退保证金</option>
        </select></td>
       </tr>
       <tr>
        <td colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
     <shiro:hasAnyRoles name="ADD_ASSURE_FUND_RECORD,ALL_BUSINESS">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openOrgAssureFundRecord()">添加</a>
     </shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="保证金记录列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>orgAssureFundRecordController/list.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'money',formatter:formatMoney,sortable:true" align="center" halign="center">金额</th>
       <th data-options="field:'accountName',sortable:true" align="center" halign="center">账户名</th>
       <th data-options="field:'account',sortable:true" align="center" halign="center">账号</th>
       <th data-options="field:'recDate',sortable:true,formatter:convertDate" align="center" halign="center">到账日期</th>
       <th data-options="field:'bank',sortable:true" align="center" halign="center">银行</th>
       <th data-options="field:'type',sortable:true,formatter:formatterType" align="center" halign="center">类型</th>
       <th data-options="field:'orgName',sortable:true" align="center" halign="center">机构名称</th>
       <th data-options="field:'createrAuthor',sortable:true" align="center" halign="center">创建人</th>
       <th data-options="field:'createrDate',sortable:true,formatter:convertDate" align="center" halign="center">创建时间</th>
       <th data-options="field:'remark',sortable:true,formatter:formatterRemark" align="center" halign="center">备注</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="orgAssureFundRecord_dialog" class="easyui-dialog" buttons="#subOrgAssureFundRecordDiv" style="width: 500px; height: 430px; padding: 10px;"
    closed="true"
   >
    <form id="orgAssureFundRecordForm" name="orgAssureFundRecordForm" action="${basePath}orgAssureFundRecordController/add.action"
     method="post"
    >
     <input name="SHIRO_FLAG" value="1" type="hidden">
     <table border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td class="label_right1"><font color="red">*</font>金额:</td>
       <td><input name="money" id="money" class="easyui-numberbox" data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"></td>
       <td class="label_right1"><font color="red">*</font>账户名:</td>
       <td>
 		 <input id="accountName1" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'pid',textField:'lookupDesc',value:'-1',onChange:getAccount,url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=INSTITUTIONAL_MARGIN'"/>
       	 <input name="accountName" type="hidden">
       </td>
      </tr>
      <tr>
       <td class="label_right1"><font color="red">*</font>账号:</td>
       <td>
           <input id="account1" name="account"  class="easyui-textbox" data-options="validType:'selrequired'" editable="false" style="width:129px;" />
       </td>
       <td class="label_right1">银行:</td>
       <td><input name="bank" id="bank" class="easyui-textbox"  data-options="validType:'length[0,20]'"/></td>
      </tr>
      <tr>
       <td class="label_right1"><font color="red">*</font>类型:</td>
       <td><select class="select_style easyui-combobox" data-options="required:true" editable="false" name="type" style="width:150px;">
         <option value=1>收保证金</option>
         <option value=2>退保证金</option>
       </select></td>
       <td class="label_right1"><font color="red">*</font>到账日:</td>
       <td><input name="recDate" id="recDate" editable="false" data-options="required:true" class="easyui-datebox"/></td>
      </tr>
      <tr>
       <td class="label_right1">备注:</td>
       <td colspan="3">
       <textarea rows="2" id="remark" name="remark" maxlength="500" class="easyui-textbox" style="height:40px;width: 400px;"></textarea>
       </td>
      </tr>
      <tr>
       <td class="label_right1">机构名:</td>
       <td><input name="orgName" id="orgName" data-options="prompt:'查询条件'" class="easyui-textbox"/></td>
      </tr>
     </table>
     <input type="hidden" name="orgId" id="orgId">
    </form>
    <table id="grid2" title="请选择机构" class="easyui-datagrid" style="height: 100%; width: 450px;"
     data-options="
      url: '<%=basePath%>orgAssetsCooperationController/orgList.action?cooperateStatus=2',
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
       <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'orgName',sortable:true" align="center" halign="center">机构</th>
       <th data-options="field:'code',sortable:true" align="center" halign="center">统一社会信用代码</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="subOrgAssureFundRecordDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subOrgAssureFundRecordForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#orgAssureFundRecord_dialog').dialog('close')"
    >取消</a>
   </div>

  </div>
 </div>
</body>
</html>
