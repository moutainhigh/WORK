

//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
		
	}
	//formatMoney
	function formatMoy(value,row,index){
		if(value){
			return accounting.formatMoney(value/10000, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	//获取累计笔数
	var totalSquareCount = 0;
	function formatSquareCount(value, row, index){
		var value = row.squareCount;
		totalSquareCount+=value;
		return totalSquareCount;
	}
	
	//获取累计金额
	var totalSquareMoney = 0.00;
	function formatSquareMoney(value, row, index){
		var value = row.squareMoney;
		totalSquareMoney+=value;
		return accounting.formatMoney(totalSquareMoney/10000, "", 2, ",", ".");
	}
	
	function cxportForeclosure() {
		
		 var rows = $('#grid').datagrid('getSelections');
		 var rePaymentMonthIds = "";
		 if (rows.length==0) {
			 $.messager.alert("操作提示", "请选择数据", "error");
			 return;
		   }
			for (var i = 0; i < rows.length; i++) {
				if (i == 0) {
					rePaymentMonthIds += rows[i].rePaymentMonthId;
				} else {
					rePaymentMonthIds += "," + rows[i].rePaymentMonthId;
				}
			}
			
			$.ajax({
					url:BASE_PATH+'templateFileController/checkFileUrl.action',
					data:{templateName:'SLDYWTJ'},
					dataType:'json',
					success:function(data){
						if(data==1){
					
							var fromMonth =$("#fromMonth").textbox('getValue');
							var endMonth =$("#endMonth").textbox('getValue');
							var fromWeek =$("#fromWeek").textbox('getValue');
							var endWeek =$("#endWeek").textbox('getValue');
							debugger;
							var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
							if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
								mothOrWekNum = 0
							}
							var grid = $('#grid');  
							var options = grid.datagrid('getPager').data("pagination").options;  
							var page = options.pageNumber;  
							var rows = options.pageSize; 
						window.location.href ="${basePath}reportController/foreclosureExportList.action?rePaymentMonthIds="+rePaymentMonthIds+"&fromMonth="+fromMonth+"&endMonth="+endMonth+"&fromWeek="+fromWeek+"&endWeek="+endWeek+"&page="+page+"&rows="+rows+"&mothOrWekNum="+mothOrWekNum;
						}else{
							alert('赎楼贷业务统计的导出模板不存在，请上传模板后重试');
						}
					}
				})
	}

	//初始化
	$(document).ready(function() {
		//赎楼贷业务统计窗口关闭时，清空数据
	 	$('#feedback-edit').dialog({
	 	    onClose:function(){
	 	    	$('#grid_hisDifferWarnList').datagrid('loadData',{total:0,rows:[]}); 
	 	    }
	 	});
	 	
	 	$('#grid').datagrid({  
        	//当列表数据加载完毕，则取累计笔数清0，累计金额清0.00
            onLoadSuccess:function(){
            	debugger;
            	var allNewCount = compute("newCount");
            	var allNewMoney = compute("newMoney");
            	var allSquareCount = compute("squareCount");
            	var allSquareMoney = compute("squareMoney");
            	var allCount = '-';
            	var allMoney = '-';
            	 $('#grid').datagrid('appendRow', {
            		 rePaymentMonthId:'-',
            		 reMonth: '合计',
            		 newCount: allNewCount,
            		 newMoney: allNewMoney,
            		 squareCount:allSquareCount,
            		 squareMoney: allSquareMoney,
            		 ingCount: '-',
            		 ingMoney: '-',
            		 totalSquareCount:allCount,
            		 totalSquareMoney: allMoney
    	         });
             	totalSquareCount=0;
            	totalSquareMoney=0.00;
            }
        }) ;
	 	//指定列求和
//            	 debugger;
	 	function compute(colName) {
	 		var rows = $('#grid').datagrid('getRows');
	 		var total = 0;
	 		for (var i = 0; i < rows.length; i++) {
	 			total += parseFloat(rows[i][colName]);
	 		}
	 		return total;
	 	}

	});
 	
	
	function assignMents(val)
	{
		document.getElementsByName('chooseMonthOrWeek').value=val;
	}
	changeToDay = function(key){
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		document.getElementById('radioMonthBtn').checked=false;
		document.getElementById('radioWeekBtn').checked=false;
		
		document.getElementById('radioDayBtn').checked=true;

		var value = document.getElementById("radioDayBtn").value;
		assignMents(value);
	}
	
	changeToWeek = function(key){
// 		debugger;
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		document.getElementById('radioMonthBtn').checked=false;
		document.getElementById('radioDayBtn').checked=false;
		document.getElementById('radioWeekBtn').checked=true;

		var value = document.getElementById("radioWeekBtn").value;
		assignMents(value);
	}
	
	changeToMonth = function(key){
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset');
		document.getElementById('radioWeekBtn').checked=false;
		document.getElementById('radioDayBtn').checked=false;
		document.getElementById('radioMonthBtn').checked=true;
		
		var value = document.getElementById("radioMonthBtn").value;
		assignMents(value);
	}

    function onSelect(date) {
//     	debugger;
    	var endMonthStr = $("#endMonth").datebox("getValue");
    	var endWeekStr = $("#endWeek").datebox("getValue");
    	var endDayStr = $("#endDay").datebox("getValue");
    	if(""!= endMonthStr && null != endMonthStr){
    		document.getElementById('input_show').value = endMonthStr;
    	}else if(""!= endWeekStr && null != endWeekStr){
    		document.getElementById('input_show').value = endWeekStr;
    	}else if(""!= endDayStr && null != endDayStr){
    		document.getElementById('input_show').value = endDayStr;
    	}else{
    		 var now = new Date();
    	        var year = now.getYear();
    	        var month = now.getMonth();
    	        var day = now.getDate();
    	        var time_str = "2" + year + "-" + month + "-" + day;
    	        document.getElementById('input_show').value=time_str;
    	        //一秒刷新一次显示时间
    	        var timeID = setTimeout(showLeftTime, 5000);
    	}
    }
    //新增笔数详情
	function searchNewCount(value, row, index) {
		var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
		if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
			mothOrWekNum = 0
		}
		var va="<a href='javascript:void(0)' onclick = 'formatterCountOverView.disposeClick("+row.rePaymentMonthId+","+1+","+mothOrWekNum+")'> <font color='blue'>"+row.newCount+"</font></a>"
		return va;
	}
	 //在途笔数详情
	function searchSquareCount(value, row, index) {
		var mothOrWekNum = $('input[name="chooseMonthOrWeek"]:checked').val();
		if((mothOrWekNum =='' || mothOrWekNum ==null) && mothOrWekNum !=2){
			mothOrWekNum = 0
		}
		var va="<a href='javascript:void(0)' onclick = 'formatterCountOverView.disposeClick("+row.rePaymentMonthId+","+2+","+mothOrWekNum+")'> <font color='blue'>"+row.squareCount+"</font></a>"
		return va;
	}
	var formatterCountOverView={
			// 新增or在途详情
			disposeClick:function (rePaymentMonthId,newOrSquare,mothOrWekNum) {
				var url = "";
				var titleName = "";
		
				if(newOrSquare == 1){
					url = BASE_PATH+'reportController/getNewOrSquareOverView.action?rePaymentMonthId='+rePaymentMonthId+'&mothOrWekNum='+mothOrWekNum+'&newOrSquare=1';
					parent.openNewTab(url,"新增笔数详情",true);
				}else {
					url = BASE_PATH+'reportController/getNewOrSquareOverView.action?rePaymentMonthId='+rePaymentMonthId+'&mothOrWekNum='+mothOrWekNum+'&newOrSquare=2'
					parent.openNewTab(url,"在途笔数详情",true);
				}
			
			}
		}
	
	
	
	