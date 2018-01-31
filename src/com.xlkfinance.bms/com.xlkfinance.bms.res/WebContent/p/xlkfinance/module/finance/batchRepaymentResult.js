//批量对账的js
$(document).ready(function(){
	$('.batchRepaymentTable').datagrid({
		collapsible:true,
		rownumbers:true,
		title:'客户批量还款预处理结果',
		toolbar:'#toolbarDiv',
		selectOnCheck:false,
		checkOnSelect:false,
		columns:[[
                  {field:'refPid',hidden:true},
                  {field:'sType',hidden:true},
                  {field:'financeType',hidden:true},
		          {field:'loanId',title:'',checkbox:true,width:80,align:'center'},
		          {field:'projectName',title:'项目名称',width:120,align:'center'},
		          {field:'projectNum',title:'项目编号',width:100,align:'center'},
		          {field:'limitDate',title:'应收日期',formatter:convertDate,width:80,align:'center'},
		          {field:'balanceDate',title:'实收日期',formatter:convertDate,width:80,align:'center'},
		          {field:'itemName',title:'期数',width:100,align:'center'},
		          {field:'totalAmt',title:'应收合计',width:80,align:'right',formatter:formatMoney},
		          {field:'balanceAmt',title:'实收金额',width:80,align:'right',formatter:formatMoney},
		          {field:'unBalanceAmt',title:'未对账金额',width:80,align:'right',formatter:formatMoney},
		          {field:'remainingAmt',title:'对账后余额',width:80,align:'right',formatter:formatMoney}
		          ]],
		          onCheck:clickRow,
		          onUncheck:unClickRow
	});
	
	// 加载数据
	loadTableData();
});

function clickRow(rowIndex,rowData)
{
	onClickBox(true,rowIndex,rowData);
}

function unClickRow(rowIndex,rowData)
{
	onClickBox(false,rowIndex,rowData);
}

// 避免循环点击导致的死循环
var fcheck = false;
// 是程序调用设置触发的时间
var sset = false;
function onClickBox(checked,rowIndex,rowData)
{
	if(sset || fcheck)
	{  
		fcheck = false;
		return false;
	}
	
	 // 如果是合计列
	if(rowData.projectNum=="<b>合计</b>")
	{
		var rowsData  =  $('.batchRepaymentTable').datagrid("getRows");
		for(var i=0;i<rowIndex;i++)
		{
			// 如果是同一个贷款
			sset = true;
		   if(rowsData[i].loanId == rowData.loanId)
		   {
			   $('.batchRepaymentTable').datagrid(checked?'checkRow':'uncheckRow',i);
		   }
		}
		
		sset = false;
	}
	 

	 var t_totalAmt = 0;  // 应收合计
	 var t_balanceAmt = 0; // 实收合计
	 var t_remainingAmt = 0;  // 剩余合计
	 var receivablesTotal=0 ;// 收款合计
	 
	 var rowsData  =  $('.batchRepaymentTable').datagrid("getRows");
	 var totalRowIndex =-1;  // 当前贷款的合计列
	for(var i=0;i<=(rowIndex+1);i++)
	{
		if(i>=rowsData.length)
		{
		   break;
		}
        
		
		// 如果是同一个贷款
	   if(rowsData[i].loanId == rowData.loanId)
	   {
            // 合计列,更新合计列
		  if(rowsData[i].projectNum=="<b>合计</b>")
		  {
		     receivablesTotal = Number(rowsData[i].itemName);
			   $('.batchRepaymentTable').datagrid('updateRow',{
				   index: i,
					   row:{
					   refPid:rowsData[i].refPid,
					   sType:rowsData[i].sType, 
					   loanId:rowsData[i].loanId, 
					   totalAmt:t_totalAmt,
					   balanceAmt:t_balanceAmt,
					   remainingAmt:(receivablesTotal-t_balanceAmt)
				 }
			   }); 
			   
			   totalRowIndex = i;
			}
		   else
		   {
			   if(rowIndex != i ||  checked)
			  {
				   // 累计
				   t_totalAmt += rowsData[i].totalAmt;
				   t_balanceAmt += rowsData[i].balanceAmt;
				   t_remainingAmt += rowsData[i].remainingAmt;
			  }	   
		   }
	   }
	}
	
	 // 找到对应的合计列
	 if(totalRowIndex>0)
     {
		 // 如果点击的是合计列，
		 if(rowIndex==totalRowIndex)
		 {
			 var bindex = totalRowIndex-1; // 合计的前一行记录
			 // 并且是取消
			 if(!checked)
			 {
	             fcheck = true;
	             $('.batchRepaymentTable').datagrid('uncheckRow',totalRowIndex);
				 if(bindex>=0)
			     {
					 $('.batchRepaymentTable').parent().find('tr[datagrid-row-index="'+bindex+'"]').find("input[type='checkbox']").attr("disabled","disabled");
			     }	
			 }	 
		    else
	    	{
				 if(bindex>=0)
			     {
					 $('.batchRepaymentTable').parent().find('tr[datagrid-row-index="'+bindex+'"]').find("input[type='checkbox']").removeAttr("disabled");
			     }	
		    	
	    	}
		 }
		 else
		 {
			  fcheck = true;
			  $('.batchRepaymentTable').datagrid('checkRow',totalRowIndex);
		 }
		
     }		 
}

var waitLoadData = new Array();
function loadTableData()
{
	//获取路径
	var url = decodeURI(window.location.href);
	var arr = url.split("=");
	var str=arr[1];
	var dataArray = $.parseJSON(str);
	for(var i=0;i< dataArray.length;i++)
	{
		waitLoadData[waitLoadData.length]=dataArray[i];
	}
	
	loanTableData();
}

function loanTableData(){
	// 没有要加载的项目
	if(waitLoadData.length==0)
	{
		// 所有的选中
		$('.batchRepaymentTable').datagrid('checkAll');
		// 所有的不可点击
		$('.batchRepaymentTable').parent().find("input[type='checkbox']").attr("disabled","disabled");
		
		var rowsDate = $('.batchRepaymentTable').datagrid('getRows');
		var index = 0;
		for(var i=0;i<rowsDate.length;i++)
		{
			 // 合计列
		     if(rowsDate[i].projectNum=="<b>合计</b>")
		     {
		 		 $('.batchRepaymentTable').parent().find('tr[datagrid-row-index="'+index+'"]').find("input[type='checkbox']").removeAttr("disabled");
		         
		 		 if(index>0)
	 			 {
			 		 $('.batchRepaymentTable').parent().find('tr[datagrid-row-index="'+(index-1)+'"]').find("input[type='checkbox']").removeAttr("disabled");
	 			 }
		     }	 
		     index++;
		}
	
		return false;
	}	
	var loadDataObject = waitLoadData[0]
	var postData= {"loanId":loadDataObject.loanId,"currentDt":loadDataObject.currentDt,"amount":loadDataObject.amount};
	waitLoadData.shift();
	
	// 加载数据
	$.ajax({
		type: "POST",
        data:postData,
		url : "getBatchRepaymentItem.action",
		dataType: "json",
	    success: function(data){
	    	addRowToTable(data,loadDataObject.currentDt,loadDataObject.projectName,loadDataObject.projectNumber);
	    	loanTableData();
		},error : function(result){
			$.messager.alert("提示","预算批量还款失败","error");
		}
	});
}

function addRowToTable(data,currentDt,projectName,projectNumber)
{
	var  t_totalAmt = 0;
	var  t_balanceAmt =0;
	var  t_remainingAmt =0;
	var loanId;
	var pName;
	var pNum;
	for(var i=0;i<data.length;i++)
	{
		var itemNameStr = data[i].itemName;
		if(Number(data[i].overdueDays)>0)
		{
			itemNameStr ="<span class='red'>"+itemNameStr+"</span>";
		}
	
		if(i==0)
		{
			pName = data[i].projectName;
			pNum = data[i].projectNum;
		}
		else
		{
			pName ="";
			pNum='';
		}	
         
		$('.batchRepaymentTable').datagrid('appendRow',{
			refPid:data[i].refPid,
			sType:data[i].sType,
			financeType:data[i].financeType,
			projectName:projectName,
			projectNum:projectNumber,
			loanId:data[i].loanId,
			limitDate:data[i].limitDate,
			balanceDate:currentDt,
			itemName:itemNameStr,
			totalAmt:data[i].totalAmt,
			balanceAmt:data[i].balanceAmt,
			unBalanceAmt:data[i].unBalanceAmt,
			remainingAmt:''
		});
		
		loanId = data[i].loanId; 
		t_totalAmt += Number(data[i].totalAmt);
		t_balanceAmt += Number(data[i].balanceAmt);
		t_remainingAmt += Number(data[i].remainingAmt);
	}	
	
    // 保留2位小数
	t_totalAmt = t_totalAmt.toFixed(2);
	t_balanceAmt = t_balanceAmt.toFixed(2);
	t_remainingAmt = t_remainingAmt.toFixed(2);
	var itemNameAmt = Number(t_balanceAmt)+Number(t_remainingAmt);
	// 合计
	$('.batchRepaymentTable').datagrid('appendRow',{
		refPid:2222,
		sType:1,
		loanId:loanId,
		projectName:'',
		projectNum:'<b>合计</b>',
		limitDate:'',
		balanceDate:"",
		itemName:itemNameAmt,
		totalAmt:t_totalAmt,
		balanceAmt:t_balanceAmt,
		remainingAmt:t_remainingAmt
	});
}

// 保存对账
function saveBatchRepayment()
{
	var allBatchData = new Array();
	// 获取所有选中的行
	var rowsData = $('.batchRepaymentTable').datagrid('getChecked');
	var rowData;
	var loanId="";
	var cDetails = new Array(); // 当前循环使用的存放细节的array
	var loanParam;
	for(var i=0;i<rowsData.length;i++)
	{
		rowData = rowsData[i];
		
		// 应收未0
		if(rowData.totalAmt == 0)
		{
			//continue;
		}	
		
		// 如果当前的loanId不一样了，说明到了下一个贷款记录
		if(loanId != rowData.loanId)
		{
			// 如果第一行就是合计，直接跳过了
			if(rowData.projectNum=="<b>合计</b>")
			{
			   continue;
			}
			
			loanId = rowData.loanId;
			var details = new Array();
			loanParam={"loanId":rowData.loanId,"currentDt":rowData.balanceDate,"details":details};
			allBatchData[allBatchData.length]= loanParam;
			cDetails = details;
		}
		else
		{
			// 如果是合计列，记录收款总金额，跳过
			if(rowData.projectNum=="<b>合计</b>")
			{
				loanParam["amount"]=rowData.itemName;
				loanParam["remainingAmt"] = rowData.remainingAmt;
				continue;
			}	
		}	
		
		cDetails[cDetails.length] = {"loanId":rowData.loanId,"financeType":rowData.financeType,"refPid":rowData.refPid,"sType":rowData.sType,"totalAmt":rowData.totalAmt,"balanceAmt":rowData.balanceAmt};
	}
	
	// 保存提交
	$.ajax({
		type: "POST",
        data:{"paramStr":JSON.stringify(allBatchData)},
		url : "saveBatchRepayment.action",
		dataType: "json",
	    success: function(data){
	    	
	    	if(data.header.success)
	    	{
	    		$.messager.alert("提示","批量还款保存成功","info");
	    		window.location.href='acctBatchRepayment.action';
	    	}
	    	// 保存失败
	    	else
    		{
	    		$.messager.alert("提示",data.header.msg,"info");
    		}
		},error : function(result){
			$.messager.alert("提示","预算批量还款失败","info");
		}
	});
	
}
