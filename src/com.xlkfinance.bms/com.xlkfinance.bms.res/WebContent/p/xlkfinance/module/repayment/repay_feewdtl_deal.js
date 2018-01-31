/**
 * 费用减免JS
 * @returns {Boolean}
 */
var RepayFeewdtl={
		/**
		 * 费用减免Button click 事件
		 * @returns {Boolean}
		 */
		feewdtlckClick:function(){
			var row = $('#loanCaculateGrid').datagrid('getSelections');
			if ( row.length == 0 ) {
				$.messager.alert("操作提示","请选择数据","error");
				return;
			}
			var checkType=false ;
			for(var i=0;i<row.length;i++){
				var operType= row[i].operType;
				//即时表 操作类型 operType 挪用罚息=1、违约罚金=2。坏账=3、退管理费=4、退其他费用=5、退利息=6、提前还款=7、提前还款罚金=8、利率变更=9、手续费=10
				if(operType==1){
					$.messager.alert("操作提示","挪用罚息 不能费用减免，请确认。","error");
					return;
				}else if (operType==2){
					$.messager.alert("操作提示","违约罚金 不能费用减免，请确认。","error");
					return;
				}else if (operType==7){
					$.messager.alert("操作提示","提前还款 不能费用减免，请确认。","error");
					return;
				}else if (operType==8){
					$.messager.alert("操作提示","提前还款罚金 不能费用减免，请确认。","error");
					return;
				}
				//对账状态 isReconciliation
				if(row[i].isReconciliation==1){
					$.messager.alert("操作提示","已对账期数不能做费用减免，请确认。","error");
					return ;
				}
				//数据类型  1 即时表 2 还款计划表 3 展期即时数据 4 展期数据
				if(row[i].isExtension==2){
					$.messager.alert("操作提示","展期期数请在展期项目中做费用减免，请确认。","error");
					return;
				}
				//判断是否是管理费
				if(row[i].dataType == 1 || row[i].dataType == 3 || row[i].dataType == 4){
					$.messager.alert("操作提示","非期数数据不能减免，请确认。","error");
					return;
				}
				if(row[i].operType>0){
					checkType=true;
					break;
				}
				if(row[i].isReconciliation==1){
					checkType=true;
					break;
				}
			}
			if(checkType){
				$.messager.alert("操作提示","选择期数非法","error");
				return false;
			}
			
			//用来保存费用减免列表的临时数据
			var rowData = [];
			//费用减免所有行
			var freeInfoRows = $('#free_info_grid').datagrid("getRows");
			for (var i = 0; i < row.length; i++) {
				var bool = true;
				for (var j = 0; j < freeInfoRows.length; j++) {
					if(freeInfoRows[j].planCycleNum == row[i].planCycleNum){
						bool = false;
					}
				}
				if(bool){
					var data = {"pId":row[i].pId,"planCycleNum":row[i].planCycleNum,
							"shouldPrincipal":row[i].shouldPrincipal,"shouldInterest":row[i].shouldInterest,
							"shouldOtherCost":row[i].shouldOtherCost,"shouldMangCost":row[i].shouldMangCost,
							"overdueFine":row[i].overdueFine,"overdueInterest":row[i].overdueInterest,
							"total":row[i].accountsTotal,"typeName":"应收"
						};
					rowData[rowData.length] = data;
					data = {"pId":0,"planCycleNum":row[i].planCycleNum,
							"shouldPrincipal":0,"shouldInterest":0,
							"shouldOtherCost":0,"shouldMangCost":0,
							"overdueFine":0,"overdueInterest":0,
							"total":0,"typeName":"减免"
						   };
					rowData[rowData.length] = data;
					data = {"pId":row[i].pId,"planCycleNum":row[i].planCycleNum,
							"shouldPrincipal":row[i].shouldPrincipal,"shouldInterest":row[i].shouldInterest,
							"shouldOtherCost":row[i].shouldOtherCost,"shouldMangCost":row[i].shouldMangCost,
							"overdueFine":row[i].overdueFine,"overdueInterest":row[i].overdueInterest,
							"total":row[i].accountsTotal,"typeName":"减免后应收"
						};
					rowData[rowData.length] = data;
				}
			}
			for (var i = 0; i < rowData.length; i++) {
				$('#free_info_grid').datagrid('appendRow',rowData[i]);
			}
		}
};
