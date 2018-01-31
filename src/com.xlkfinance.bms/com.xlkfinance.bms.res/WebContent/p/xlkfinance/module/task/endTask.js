
/**
 * 已结办任务前端js对象
 */
var endTaskJsObj = {
	/**
	 * 加载任务管理列表数据
	 */
	loadEndTaskList	: function(parm){
		$('#end_Task_grid').datagrid({
			toolbar: end_Task_toolbar,
		 	width: '100%',
		 	fitColumns: true,
		 	height:'100%',            
	        striped: true,  
	        url: BASE_PATH+'taskController/searchEndTaskList.action', 
	        loadMsg: '数据加载中请稍后……',  
	        pagination: true,  
	        nowrap: false,
	        rownumbers: true,
	        checkOnSelect: false,
	        queryParams:parm,
	        onClickRow:function(rowIndex,rowData){
	        	$("#end_Task_grid").datagrid("clearSelections");
			},
		    columns:[[
		        {field:'pid',hidden:true},
		        {field:'workflowTaskDefkey',hidden:true},
		        {field:'workflowInstanceId',hidden:true},
		        {field:'workflowTaskId',hidden:true},
				{field:'projectName',title:'项目名称',width:100,align:'center',
		        	formatter:function(value,rowData,rowIndex){
						var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='disposeClick("+JSON.stringify(rowData)+")' href='#'>" +
	            		"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
						return btn;   
		        	}
		        },
				{field:'taskName',title:'任务名称',width:100,align:'center'},
				{field:'executeDttm',title:'处理时间',width:100,align:'center'},
				{field:'description',title:'操作',width:120,align:'center', 
					formatter:function(value,rowData,rowIndex){  
			            var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='endTaskJsObj.processLogging("+JSON.stringify(rowData)+")' href='#'>" +
			            		"<span style='color:blue; '>处理记录查看</span></a>";  
			            return btn;  
					}  
				}
		    ]]
		});
	},

	/**
	 * 项目跳转
	 * @param data
	 */
	projecgtSkip : function (){
		//引用ID
		var refId = rowData.refId;
		//流程定义ID
		var workflowProcessDefkeyId = rowData.workflowProcessDefkey;
		var res = workflowProcessDefkeyId.split(":");
		var workflowProcessDefkey = res[0];
		var projectName = "-";
		//贷前流程流程控制跳转
		if(workflowProcessDefkey == "loanRequestProcess"){
			
		//提前还款申请工作流
		}else if(workflowProcessDefkey == "prepaymentRequestProcess"){
			
		//利率变更流程控制跳转
		}else if(workflowProcessDefkey == "interestChangeRequestProcess"){
		
		//违约处理工作流层控制跳转
		}else if('breachOfContractRequestProcess' == workflowProcessDefkey){
			
		//额度冻结与解冻工作流
		}else if('creditFreezeOrThawRequestProcess' == workflowProcessDefkey){
		
		//额度提款申请工作流
		}else if('creditWithdrawalRequestProcess' == workflowProcessDefkey){
			
		//展期申请工作流
		}else if('extensionRequestProcess' == workflowProcessDefkey){
			
		//费用减免工作流
		}else if('feeWaiversRequestProcess' == workflowProcessDefkey){
			
		//挪用处理工作流
		}else if('misappropriateRequestProcess' == workflowProcessDefkey){
			
		}
	},

	/**
	 * 任务详情
	 * @param data
	 */
	processLogging : function(data){
		this.loadProcessLoggingGrid(data);
		var height = document.body.clientHeight*0.9;
		var width = document.body.clientWidth*0.9;
		$('#end_Task_win').window({
			width:width,
			height:400,
			title : "处理记录查看",
			modal:true,
			collapsible:false,
			minimizable:false,
			maximizable:false
		});
	},
	/**
	 * 刷新已结任务列表
	 * @returns
	 */
	seachEndTaskList : function (){
		endTaskJsObj.loadEndTaskList({
			projectName:$.trim($('#projectName').textbox('getValue')),
			executeDttm:$.trim($("#executeDttm").datebox('getValue'))
		});
	},
	/**
	 * 查询表单重置
	 * @returns
	 */
	resetForm : function (){
		$('#task_from').form('reset');
	},
	/**
	 * 加载任务管理-任务处理详情列表数据
	 */
	loadProcessLoggingGrid : function (data){
		$('#process_logging_grid').datagrid({
		 	width: '100%',
		 	height: 360,            
	        striped: true,  
	        url: BASE_PATH+'taskController/searchHiTaskList.action', 
	        queryParams: {"workflowInstanceId":data.workflowInstanceId},
	        loadMsg: '数据加载中请稍后……',  
	        pagination: true,  
	        rownumbers: true,
	        checkOnSelect: false,
	        fitColumns:false,
	        nowrap:false,
	        onClickRow:function(rowIndex,rowData){
	        	$("#process_logging_grid").datagrid("clearSelections");
			},
			columns:[[
		        {field:'pid',hidden:true},
		        {field:'executeUserName',hidden:true},
		        {field:'taskName',title:'任务描述',align:'center'},
				{field:'executeUserRealName',title:'执行人',align:'center'},
				{field:'executeDttm',title:'任务时间',align:'center'},
				{field:'approvalStatus',title:'任务状态',align:'center'},
				{field:'message',width:700,title:'意见与说明',align:'left'}
			]]
		});
	}
};

/**
 * 初始化js 
 */
$(document).ready(function() {
	endTaskJsObj.loadEndTaskList({});
});
	