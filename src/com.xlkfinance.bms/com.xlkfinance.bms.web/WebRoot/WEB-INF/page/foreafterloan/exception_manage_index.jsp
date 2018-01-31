<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<script type="text/javascript">
	//项目名称format
	function formatProjectName(value, row, index) {
		var va = "<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("
				+ row.projectId
				+ ")'> <font color='blue'>"
				+ row.projectName
				+ "</font></a>"
		return va;
	}
	//距离回款天数
	function formatterRepaymentDateDiff(val, row) {
		if (parseInt(val) <= 3 && row.repaymentStatus == 1) {
			return "<span style='color:red;'>" + val + "</span>";
		} else {
			return "<span style='color:blue;'>" + val + "</span>";
		}
	}

	//风险等级:正常状态=1,正常风险=2,重大风险=3,紧急风险=4
	function formatDangerLevel(val, row) {
		 if (val == 2) {
			return "<span style='color:red;'>重大风险</span>";
		} else if (val == 3) {
			return "<span style='color:red;'>紧急风险</span>";
		} else if (val == 1){
			return "正常风险";
		}else{
			return "待跟进";
		}
	}
	//异常监控状态：正常=1，异常=2，异常转正常=3
	function formatterMonitorStatus(val, row) {
		if (val == 3) {
			return "<span style='color:red;'>异常转正常</span>";
		} else if (val == 2) {
			return "<span style='color:red;'>异常</span>";
		} else {
			return "正常";
		}
	}
	//查看 打印页面
	function openCheckView() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var businessSourceStr = row[0].businessSourceStr;
			var projectId = row[0].projectId;
			var url= "${basePath}foreAfterLoanController/checkView.action?projectId="
					+ projectId + "&businessSourceStr=" + businessSourceStr;
			parent.openNewTab(url, "打印报告", true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//打开回归正常确认窗口
	function openCheckLitigationDialog() {
		$("#checkLitigationRemark").val('');
		$("#checkLitigationForm input[name='approvalStatus']").removeAttr(
				"checked");
		$("#checkLitigationStatus").combobox('select', '-1');
		$(
				'#checkLitigationForm input[type=radio][name=approvalStatus][value=3]')
				.prop('checked', true);
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var projectId = row[0].projectId;
			var projectName = row[0].projectName;
			var returnNormalRemark = row[0].returnNormalRemark;
			 $("#returnNormalRemark").textbox('setValue',returnNormalRemark);
			$("#projectId").val(projectId);
			$('#check_litigation_dialog').dialog('open').dialog('setTitle',
					"回归正常--" + projectName);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//提交修改监控状态表单
	function subCheckLitigationForm() {
		// 保存提交
		$('#checkLitigationForm').form('submit', {
			url : "updateMonitorStatus.action",
			onSubmit : function() {
				return true;
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"]);
					// 清空所选择的行数据
					clearSelectRows("#grid");
					var pid = ret.header["pid"];
					$("#pid").val(pid);
					$("#check_litigation_dialog").dialog('close');
					$('#grid').datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			},
			error : function(result) {
				alert("保存失败！");
			}
		});
	}

	//----------------------------自动查档开始

	//设置自动查档显示/隐藏
	function setCheckDocShow(id) {
		if ($("#" + id).is(":hidden")) {
			//显示
			$("#check_doc_a_showbtn").text("自动查档-收起");
			$("#" + id).show();
			//调整高度
			$("#check_document_dialog").height(520);
			$("#check_document_dialog").panel("move", {
				top : 50
			});
		} else {
			//隐藏
			$("#check_doc_a_showbtn").text("自动查档-展开");
			$("#" + id).hide();
			$("#check_document_dialog").height(258);
			$("#check_document_dialog").panel("move", {
				top : 150
			});
		}
	}

	/** 使用方法: 
	 * 开启:MaskUtil.mask(); 
	 * 关闭:MaskUtil.unmask(); 
	 *  
	 * MaskUtil.mask('其它提示文字...'); 
	 */
	var MaskUtil = (function() {
		var $mask, $maskMsg;
		var defMsg = '正在处理，请稍待。。。';
		function init() {
			if (!$mask) {
				$mask = $("<div class=\"datagrid-mask mask_index\"></div>")
						.appendTo("body");
			}
			if (!$maskMsg) {
				$maskMsg = $(
						"<div class=\"datagrid-mask-msg mask_index\">" + defMsg
								+ "</div>").appendTo("body").css({
					'font-size' : '12px'
				});
			}

			$mask.css({
				width : "100%",
				height : $(document).height()
			});

			var scrollTop = $(document.body).scrollTop();

			$maskMsg.css({
				left : ($(document.body).outerWidth(true) - 190) / 2,
				top : (($(window).height() - 45) / 2) + scrollTop
			});
		}
		return {
			mask : function(msg) {
				init();
				$mask.show();
				$maskMsg.html(msg || defMsg).show();
			},
			unmask : function() {
				$mask.hide();
				$maskMsg.hide();
			}
		}
	}());
	 
	$(document).ready(function() {
		
		$('#house_list_grid').datagrid({
			//当物业列表数据加载完毕
			onLoadSuccess : function(data) {
				//系统自动选中第一条并打开查档窗口
				$(this).datagrid('selectRow', 0);
				openCheckDocumentDialog(true);
			},
			onClickRow : function(rowIndex, rowData) {
				openCheckDocumentDialog(false);
			}
		});
	});

	//重置按钮
	function majaxReset() {
		$('#searchFrom').form('reset');
	}
	
	//导出
	 function exportExceptionManageList() {
			$.ajax({url : BASE_PATH
				+ 'templateFileController/checkFileUrl.action',
				data:{templateName : 'YCYWCL'},
				dataType:'json',
				success : function(data) {
					if (data == 1) {
						var projectName =$("#projectName").textbox('getValue');
						var businessSourceStr =$("#businessSourceStr").textbox('getValue');
						var monitorStatus =$("#monitorStatus").combobox('getValue');
						window.location.href ="${basePath}foreAfterLoanController/exportExceptionManageList.action?businessSourceStr="+businessSourceStr+"&monitorStatus="+monitorStatus+"&projectName="+projectName+"&page=-1&type=2";
					} else {
						alert('异常业务监控导出模板不存在....，请上传模板后重试');
					}
				}
			}) 
		}
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
			<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form
					action="<%=basePath%>foreAfterLoanController/transitMonitorIndexList.action"
					method="post" id="searchFrom" name="searchFrom">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table>
							<tr>
								<td width="80" align="right" height="28">项目名称：</td>
								<td><input class="easyui-textbox" name="projectName"
									id="projectName" /></td>
								<td width="80" align="right" height="28">业务来源：</td>
								<td><input class="easyui-textbox" name="businessSourceStr"
									id="businessSourceStr" /></td>
								<td width="80" align="right" height="28">监控状态：</td>
								<td><select class="select_style easyui-combobox"
									width="129px;" editable="false" name="monitorStatus" id="monitorStatus" style="width: 95px">
										<option value=-1 selected="selected">--请选择--</option>
										<option value=1>正常</option>
										<option value=2>异常</option>
										<option value=3>异常转正常</option>
								</select></td>
							</tr>
							<tr>
								<td colspan="3"><a href="#" class="easyui-linkbutton"
									iconCls="icon-search"
									onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a
									href="#" onclick="majaxReset()" class="easyui-linkbutton"
									style="width: 60px;">重置</a></td>
							</tr>
						</table>
					</div>
				</form>

				<!-- 操作按钮 -->
				<div style="padding-bottom: 5px">
					<shiro:hasAnyRoles name="RETURN_NORMAL,ALL_BUSINESS">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-setting" plain="true"
							onclick="openCheckLitigationDialog()">回归正常</a>
					</shiro:hasAnyRoles>
					<shiro:hasAnyRoles name="PRINT_REPORT,ALL_BUSINESS">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-print" plain="true" onclick="openCheckView()">查看（打印报告）</a>
					</shiro:hasAnyRoles>
				</div>
				<div style="padding-bottom: 5px">
				   <shiro:hasAnyRoles name="EXPORT_MANAGE_EXCEPTION,ALL_BUSINESS">
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportExceptionManageList()">导出</a>
				   </shiro:hasAnyRoles>
			    </div>
		</div>
			<div class="listDiv" style="height: 100%;">
				<table id="grid" title="异常监控处理列表" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="
		    url: '<%=basePath%>foreAfterLoanController/toTransitExceptionIndexList.action?type=2',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true">
					<!-- 表头 -->
					<thead data-options="frozen:true">
						<tr>
							<th data-options="field:'projectId',checkbox:true"></th>
							<th data-options="field:'projectName',formatter:formatProjectName,sortable:true" align="center" halign="center">项目名称</th>
						</tr>
					</thead>
					<thead>
						<tr>
							<!-- <th data-options="field:'projectId',checkbox:true"></th>
							<th
								data-options="field:'projectName',formatter:formatProjectName,sortable:true"
								align="center" halign="center">项目名称</th> -->
							<th data-options="field:'businessSourceStr',sortable:true"
								align="center" halign="center">业务来源</th>
							<th data-options="field:'buyerName',sortable:true" align="center"
								halign="center">买方</th>
							<th data-options="field:'sellerName',sortable:true"
								align="center" halign="center">卖方</th>
							<th data-options="field:'houseName',sortable:true" align="center"
								halign="center">物业名称</th>
							<th data-options="field:'housePropertyCard',sortable:true"
								align="center" halign="center">房产证号</th>
							<th
								data-options="field:'loanMoney',sortable:true,formatter:formatMoney"
								align="center" halign="center">借款金额</th>
							<th data-options="field:'loanDays',sortable:true" align="center"
								halign="center">借款天数</th>
							<th
								data-options="field:'dangerLevel',sortable:true ,formatter:formatDangerLevel"
								align="center" halign="center">风险等级</th>
							<th
								data-options="field:'monitorStatus',sortable:true,formatter:formatterMonitorStatus"
								align="center" halign="center">监控状态</th>
							<th hidden="hidden" data-options="field:'returnNormalRemark',sortable:true" align="center" halign="center">回归正常说明</th>
						</tr>
					</thead>
				</table>
			</div>
			<div id="check_litigation_dialog" class="easyui-dialog"
				data-options="modal:true" buttons="#submitDiv3"
				style="width: 353px; height: 250px; padding: 10px;" closed="true">
				<form id="checkLitigationForm" name="checkLitigationForm"
					action="${basePath}foreAfterLoanController/updateMonitorStatus.action"
					method="post">
					<table style="width:100%;">
						<tr style="height:35px;">
							<td align="right">处理结果：</td>
							<td><select class="select_style easyui-combobox"
								editable="false" name="monitorStatus"  style="width:85%;">
									<option value=3>正常回撤</option>
							</select></td>
						</tr>
						<tr>
							<td align="right">说明：</td>
							<td><input name="returnNormalRemark" id="returnNormalRemark" class="easyui-textbox"
								style="width:85%; height: 60px"
								data-options="multiline:true,validType:'length[0,200]'">
							</td>
						</tr>
					</table>
					<input type="hidden" id="projectId" name="projectId"
						value="${projectId}" />
				</form>
			</div>
			<div id="submitDiv3">
				<a id="F" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok" onclick="subCheckLitigationForm()">提交</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#check_litigation_dialog').dialog('close')">取消</a>
			</div>

			<div id="fileUploadDialog" class="easyui-dialog"
				data-options="modal:true" buttons="#uploanFileOperateDiv"
				style="width: 430px; height: 330px; padding: 10px" closed="true">
				<form id="fileUploadForm" name="fileUploadForm" method="post">
					<input type="hidden" id="isUpdateFile" name="isUpdateFile"
						value="0" />
					<div class="uploadmutilFile" style="margin-top: 10px;">
						<input type="file" name="uploadify" id="uploadify" />
					</div>
					<%--用来作为文件队列区域--%>
					<div id="fileQueue"
						style="max-height: 200; overflow: scroll; right: 50px; bottom: 100px; z-index: 999"></div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
