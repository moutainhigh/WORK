<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
 <%@ include file="/tree_common.jsp"%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<title>系统机构管理</title>
 
<!-- zTree树形菜单 -->
<script type="text/javascript">
	
	var zTree;  
	var treeNodes;  
	var submitFalg = true;
	var existsFlag = false;
	var url = "";
	
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		edit: {
			enable: true,
			showRemoveBtn: false,  
	        showRenameBtn: false,
			drag: {
				isCopy: false,
				isMove: true
			}
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: "",
				autoParam: ["id","name"]
			}
		},
		callback: {		
	           beforeClick:beforeClick,
	           onDrop: onDrop, //捕获节点拖拽操作结束的事件回调函数    
		}
	};
	 var className = "dark";
	 function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {  
	        className = (className === "dark" ? "":"dark");  
	        //拖拽结束之后  
// 	      alert("拖拽节点的id:"+treeNodes[0].id);  
// 	      alert("拖拽节点的新父节点的id:"+targetNode.id);  
	        $.ajax({  
	            type:'post',        
	            url: "updateApplicationTree.action?id="+treeNodes[0].id+"&parentId="+targetNode.id,
	            dataType: "text",  
	            async: false,  
	            success: function (data) {
	            },  
	            error: function (msg) {
	            }  
	        });  
	    }  
	
	/**
	 * 初始化加载数据(构造树)
	 */
	function initTree() {
		$.ajax({
			url : "<%=basePath%>sysOrgInfoController/treelist.action",
			type : 'post',
			cache: false,
			dataType : "json",
			success : function(result) {
				treeNodes = result;
				zTree = $.fn.zTree.init($("#tree"), setting, treeNodes);
				zTree.expandAll(false);
			},
			error:function(){
				alert("请求失败!");
			}
		});
	};
	
	
	$(document).ready(function(){
		//var t = $("#tree");
		//t = $.fn.zTree.init(t, setting, zNodes);
		//demoIframe = $("#testIframe");
		//demoIframe.bind("load", loadReady);
		//var zTree = $.fn.zTree.getZTreeObj("tree");
		//zTree.selectNode(zTree.getNodeByParam("id", 101));
		 initTree();
	});
	
	function beforeClick(treeId, treeNode, clickFlag) {
		$("#parentId").val(treeNode.id);
		return (treeNode.click != false);
	}
	
	// 新增
	function addItem() {
		
		$('#fm').form('reset');
		
		var orgId = $("#parentId").val();
		if(orgId == ""){
			
			$.messager.alert('操作提示', "请选择机构", 'info');
		}else{
			// 将 url 地址改变
			url = "<%=basePath%>sysOrgInfoController/insertOrg.action";
			$('#dlg').dialog('open').dialog('setTitle', "新增");
			
		}
	}
	// 新增  or 更新
	function saveItem() {
		
		$("#fm").form("submit",{
			url : "<%=basePath%>sysOrgInfoController/checkSysOrgExists.action",
			success : function(result){
				existsFlag = result;
			}
		});
		
		if (submitFalg && !existsFlag) {
			$("#fm").form('submit', {
				url : url,
				success : function(result) {
					// 转换成json格式的对象
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						$("#dlg").dialog('close');
						$("#fm").form('clear');
						initTree();
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'info');
					}
				}
			});
		} else {
			$.messager.alert('操作提示', "请检查一下数据", 'info');
		}
	}
	// 编辑
	function editItem() {
		var orgId = $('#parentId').val();
		
		var myOrg;
		if (orgId != "") {
			$.post("getSysOrgInfo.action",{
				orgId : orgId
			},function(data) {
				myOrg = eval("(" + data + ")");
				//设置机构数据至文本框
				$("#name").textbox("setValue",myOrg.name);
				$("#code").textbox("setValue",myOrg.code);
				$("#category").combobox("setValue",myOrg.category);
				$("#parentId").val(myOrg.parentId);
				$("#orgId").val(myOrg.id);
			});
			// 将 url 地址改变
			url = "updateOrg.action";
			$('#dlg').dialog('open').dialog('setTitle', "编辑");
		} else {
			$.messager.alert("操作提示", "请选择一条机构数据！", "info");
		}
	}
	
	// 删除
	function removeItem() {
		var orgId = $("#parentId").val();
		if (orgId == "") {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		
		$.messager.confirm("操作提示", "确定删除这个机构吗?", function(r) {
			if (r) {
				$.post("deleteOrg.action", {
					orgId : orgId
				}, function(ret) {
					//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					if (ret && ret.header["success"]) {
						$.messager.alert('操作提示',"删除成功!");	
						$("#dlg").dialog('close');
						initTree();
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}, 'json');
			}
		});
	}
	//取消方法
	function cancelFunc() {
		$("#fm").form('clear');
		$('#dlg').dialog('close');
	}
</script>
</head>
<body  class="easyui-layout">
<div data-options="region:'center',border:false" style="padding:20px;">
<table width="100%">
  <tr>
	<td width="25%" colspan="100%" style="border: 1px #95B8E7 solid;vertical-align:top;padding: 10px;height:auto; min-height:500px; overflow-x: hidden;"><ul id="tree" class="ztree"></ul></td>
	<td width="70%" style="vertical-align:top; padding-left:10px;">
	<!--图标按钮 -->
		<div id="toolbar" class="easyui-panel" style="border: 0;">
			<!-- 操作按钮 -->
			<div style="padding-bottom: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="addItem()">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true" onclick="editItem()">修改</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a> 
			</div>
		</div>
	
	</td>
  </tr>
 </table>
 </div>
<!-- 保存 和取消按钮 -->
<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-save" onclick="saveItem()">保存</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel" onclick="cancelFunc()">取消</a>
</div>
<!--窗口 -->
<div id="dlg" class="easyui-dialog"
	style="width: 700px; height: 310px; padding: 20px 20px"
	closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<input type="hidden" name="id" id="orgId" />
		<input type="hidden" name="parentId" id="parentId" />
		<div class="fitem" id="update">
			<label style="text-align: right;"><font color="red">*</font>机构名称：</label>
			<input type="text" name="name" id="name" class="easyui-textbox"	missingMessage="请填写机构名称!" value="${org.name}" required="true"> 
		</div>
		<div class="fitem">
			<label style="text-align: right;"><font color="red">*</font>城市编码：</label>
			<input type="text" name="code" id="code" class="easyui-textbox"	data-options="required:true" missingMessage="请填写机构编码!"> 
		</div>
		
<!-- 		<div class="fitem"> -->
<!-- 			<label style="text-align: right;"><font color="red">*</font>所属城市：</label> -->
<!-- 			<input type="text" name="code" id="code" class="easyui-textbox"	data-options="required:true" missingMessage="请填写机构编码!">  -->
<!-- 		</div> -->
		
		<div class="fitem">
			<label style="text-align: right;"><span class="cus_red">*</span>机构类型：</label>
			<select class="select_style easyui-combobox" editable="false" data-options="validType:'selrequired'" name="category" id="category">
				<option value="0" >--请选择--</option>
				<option value="1" >业务线</option>
				<option value="2" >风控线</option>
				<option value="3" >财务线</option>
			</select>
		</div>
	</form>
</div>
 
</body>

</html>