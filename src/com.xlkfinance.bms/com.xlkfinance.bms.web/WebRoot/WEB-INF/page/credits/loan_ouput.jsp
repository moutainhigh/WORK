<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<div class="panel-none">
<input type="hidden" id="loan_stype">
<div class="easyui-panel output_dis" title="财务放款" data-options="collapsible:true">
	<%@ include file="loan_output_list.jsp"%>
	<!-- 查询条件 -->
	<form 
		action="${basePath}beforeLoanController/insertLoadOutputinfo.action"
		method="post" id="loadOutputbutton">
		<input type="hidden" name="loanId" id="loanId" value="10007" /> 
		<input type="hidden" name="ftBankAcctId" id="ftBankAcctId" value="10007" />
		<div style="padding: 5px">
			<table>
				<tr>
					<td class="label_right">项目类别：</td>
					<td><input class="easyui-textbox" name="projectType"
						id="projectType" readonly="readonly" /> </td>
					<td class="label_right">小贷银行账号:</td>
					<td colspan="3"><!-- <input class="easyui-textbox" name="bank"
						id="bank" /> <input class="easyui-textbox" name="bankNum"
						id="bankNum" /> -->
						  <input name="bank"  id="bank" class="easyui-combobox" editable="false" panelHeight="auto" 
				            data-options="valueField:'pid',textField:'chargeName',value:'-1',url:'${basePath}secondBeforeLoanController/getFinanceAcctManage.action',onSelect:getAccountByPrimaryKeyAccrual1" />
				            <input name="bankNum" id="bankNum"  type="text" class="text_style"  disabled="true" />
				            <input type="hidden" name="ftBankAcctId"
						id="ftBankAcctId"  />
						</td>
						

				</tr>
				<tr>
					<td class="label_right">应放款金额:</td>
					<td><input class="easyui-numberbox" precision="2" groupSeparator="," name="shouldAmt" readonly="readonly"
						id="shouldAmt" /></td>
					<td class="label_right">应放款日期:</td>
					<td><input  class="easyui-datebox" name="shouldDate" readonly="readonly"
						id="shouldDate"  /></td>
					<td class="label_right">还款期数:</td>
					<td><input class="easyui-textbox" name="repayCycle" readonly="readonly"
						id="repayCycle"  /></td>
				</tr>
				<tr>
					<td class="label_right">放款金额:</td>
					<td><input class="easyui-numberbox" precision="2" groupSeparator="," name="ftAmt" id="ftAmt" data-options="onChange:editing" required="ture"/></td>
					<td class="label_right">放款日期 ：</td>
					<td><input  class="easyui-datebox" name="ftDate" id="ftDate"  data-options="onSelect:loanEndDtInfo"  
						/></td>
					<td class="label_right">还款日期 ：</td>
					<td><input  class="easyui-datebox" name="repayCycleDate"
						id="repayCycleDateInfo"   readonly="readonly" /></td>
				</tr>
				<tr>
					<td class="label_right">差额：</td>
					<td><input class="easyui-numberbox" precision="2" groupSeparator="," name="difAmt" id="difAmt"  readonly="readonly"/>
					</td>
					<td class="label_right">放款备注：</td>
					<td colspan="3"><input class="easyui-textbox"
						name="outputDesc" id="outputDesc"  />
					</td>
				</tr>
				<tr>
					<td class="label_right">放款账号：</td>
					<td><input class="easyui-textbox" name="loanCardId" id="loanCardId"
					readonly="readonly" />
					</td>
					<td class="label_right">开户名称：</td>
					<td colspan="3"><input class="easyui-textbox" name="accName"
						id="accName" readonly="readonly" /></td>
				</tr>
				<tr>
					<td class="label_right">账户类型：</td>
					<td><input class="easyui-textbox" name="accType" id="accType"
						 readonly="readonly" readonly="readonly" />
					</td>
					<td class="label_right">银行开户类别：</td>
					<td colspan="3"><input class="easyui-textbox"
						name="bankAccCate" id="bankAccCate" 
						readonly="readonly" /></td>
				</tr>
				<tr>
					<td class="label_right">银行名称：</td>
					<td><input class="easyui-textbox" name="bankName"
						id="bankName"  readonly="readonly"
						readonly="readonly" /></td>
					<td class="label_right">网点名称：</td>
					<td colspan="3"><input class="easyui-textbox"
						name="branchName" id="branchName" 
						readonly="readonly" /></td>
				</tr>

				<tr>
					<td class="label_right">开户地区：</td>
					<td colspan="5"><textarea rows="1" style="width: 594px;height:26px;" 
							name="accArea" id="accArea" class="text_style" readonly="readonly"> </textarea>
					</td>
				</tr>
				<tr>
					<td class="label_right">银行备注：</td>
					<td colspan="5"><textarea rows="4" style="width: 594px;height:70px;"
							name="remark" id="loanRemark" class="text_style" readonly="readonly"> </textarea></td>

				</tr>
			</table>

			<div style="width: 500px; text-align: right;">
				<p>
					<a onclick="beforLoanbutton()" data-options="iconCls:'icon-save'"
						class="easyui-linkbutton">保存</a>
						<a href="#" class="easyui-linkbutton" style="" iconCls="icon-calculation" onclick="loancalcs()">贷款试算</a>

				</p>
			</div>
		</div>
	</form>
</div>
</div>
<div id="dlg_spgsc_but">		
		    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveSpgsceditfk()">保存</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_spgscfk').dialog('close')">关闭</a>
		</div>
<div id="dlg_spgscfk" class="easyui-dialog" style="width:550px;height:300px;padding:20px 20px ;text-align: center;" 
			closed="true" buttons="#dlg_spgsc_but" data-options="modal:true">
	     	<form id="fm_spgscfk" method="post" action="${basePath}creditsContractController/updateLoadOutputinfo.action">
     			<table>
     				<tr>
     					<td class="label_right"><font color="red">*</font>应放款金额：</td>
     					<td>
     						<input  readonly="readonly" class="easyui-textbox" name="shouldAmt" id="shouldAmtdialog" value=""  />
     					</td>
     				</tr>
     				<!-- <tr>
     					<td class="label_right"><font color="red">*</font>应放日期：</td>
     					<td>
     						 <input  class="easyui-datebox" name="shouldDate" readonly="readonly" id="shouldDatedialog" value="" />
     					</td>
     				</tr> -->
     				<tr>
     					<td class="label_right"><font color="red">*</font>未放款金额：</td>
     					<td>
     						<input  class="easyui-textbox" name="difAmt" readonly="readonly"
						id="difAmtdialog" value="" /> <input type="hidden" name="pId"
						id="pIddialog" value=""  />
     					</td>
     				</tr>
     				<tr>
     					<td class="label_right"><font color="red">*</font>放款金额：</td>
     					<td  class="ftAmtdialog">
     						 <input  class="easyui-textbox" name="ftAmt" id="ftAmtdialog" value="" />
     					</td>
     				</tr>
     				<tr>
     					<td class="label_right"><font color="red">*</font>交易日期：</td>
     					<td>
     						<input  class="easyui-datebox" name="ftDate"
						id="ftDatedialog" value="" readonly="readonly" /><input  type="hidden" name="ftBankAcctId"
							id="ftBankAcctIddialog" value="" />
     					</td>
     				</tr>
     			</table>
	     		
	    	</form>
		</div>
<script type="text/javascript">
/*  var loanId=10005;
var projectId=10016; */ 
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays  
    aDate  =  sDate1.split("-")  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]) //转换为12-18-2006格式  
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
    iDays  =  parseInt(Number(oDate1  -  oDate2)  /  1000  /  60  /  60  /24) //把相差的毫秒数转换为天数  
    return  iDays  
}
function addDay(sdate, days) {
    var sDate = sdate.split("-");
    var nDate = new Date(sDate[1] + '-' + sDate[2] + '-' + sDate[0]); //转换为MM-DD-YYYY格式  
    var millSeconds = /* Math.abs(nDate) */Number(nDate) + (days * 24 * 60 * 60 * 1000);
    var rDate = new Date(millSeconds);
    var year = rDate.getFullYear();
    var month = rDate.getMonth() + 1;
    if (month < 10) month = "0" + month;
    var day = rDate.getDate();
    if (day < 10) day = "0" + day;
    return (year + "-" + month + "-" + day);
}
var repayCycleDate=null;
var d = new Date();
var datestr=null;
function beforLoanbutton() {
	var	shouldDate = $('#shouldDate').textbox('getValue');
	var	shouldAmt =$('#shouldAmt').numberbox('getValue');
	var	ftAmt =$('#ftAmt').numberbox('getValue');
	if(ftAmt=='' || ftAmt==0){
		$.messager.alert('操作提示', "请输入放款金额！", 'info');
		return false;
	}
	var	difAmt =$('#difAmt').numberbox('getValue');
	if(Number(difAmt)<0){
		$.messager.alert('操作提示', "超出应放款金额！", 'info');
		return false;
	}
	var	outputDesc =$('#outputDesc').textbox('getValue');
	var	ftDate =$('#ftDate').textbox('getValue');
	var row= $("#loanOutputGrid").datagrid('getRows');
	
	var loanid = applyCommon.getLoanId(newProjectId);

	var ftBankAcctId=$('#ftBankAcctId').val();
	repayCycleDate=$('#repayCycleDateInfo').textbox('getValue');
	 if(row.length==0&&ftDate!=shouldDate){
				$.messager.confirm('友情提示','实际放款日与应放款日期不相等是否更新还款计划表！',function(r){
					//$('#repayCycleDate').textbox('setValue',addDay(repayCycleDate ,DateDiff(ftDate,shouldDate)))	;
					if (r){
						var url = "${basePath}beforeLoanController/getProjectCredtiEndDt.action?projectId="+newProjectId;
						$.post(url,//通过 项目ID得到还款方式id 
							  	function(data){
								var str= new Array();   
								  str=data.credtiEndDt.split(" ");   
								  var endDate = str[0];
								  if(repayCycleDate>str[0]){//如果 当日还款日期大于贷款基本信息的还款日期的话就给提示 
									  $.messager.confirm('确认提示','放款日期与实际放款日期不一致的，以实际放款日期为准，还款日期顺延。顺延后还款日期超过授信期限，请确认！',function(r){
										  if(r){//确认 
										 	 $.post("${basePath}beforeLoanController/insertLoadOutputinfo.action",{
												shouldDate:shouldDate,
												shouldAmt:shouldAmt,
												ftAmt:ftAmt,
												difAmt:difAmt,
												outputDesc:outputDesc,
												ftDate:ftDate,
												repayCycleDate:repayCycleDate,
												ftBankAcctId:ftBankAcctId,
												loanId:loanid
											},function(data) {
												if (data > 0) {
													$("#loanOutputGrid").datagrid('reload');
													$("#shouldAmt").numberbox('setValue', difAmt);
													$("#difAmt").numberbox('setValue', difAmt-ftAmt);
												//  $("#ftDate").datebox('setValue',""); 
													//更新计划还款变
												 var  ftDt=	$("#ftDate").datebox('getValue');
													
													operRepaymentList("${basePath}beforeLoanController/updateRepaymentPlan.action?pId="+newProjectId+"&planRepayLoanDt="+repayCycleDate+"&loanId="+loanid+"&planOutLoanDt="+ftDt+"&creditEndDate="+repayCycleDate); 
													$.messager.alert('操作提示', "保存成功！", 'info');
													$("#ftAmt").numberbox('setValue', 0);
													$("#difAmt").numberbox('setValue', 0);
													var  v=$("#ftDate").datebox('getValue');
													$("#shouldDate").datebox('setValue', v);
												} else {
													$.messager.alert('操作提示', "保存失败!", 'info');

												}
											});  
										  }
									  });
									  
								  }else{//小于的话还是按照老方法执行 
								     	$.post("${basePath}beforeLoanController/insertLoadOutputinfo.action",{
											shouldDate:shouldDate,
											shouldAmt:shouldAmt,
											ftAmt:ftAmt,
											difAmt:difAmt,
											outputDesc:outputDesc,
											ftDate:ftDate,
											repayCycleDate:repayCycleDate,
											ftBankAcctId:ftBankAcctId,
											loanId:loanId
										},function(data) {
											if (data > 0) {
												$("#loanOutputGrid").datagrid('reload');
												$("#shouldAmt").numberbox('setValue', difAmt);
												$("#difAmt").numberbox('setValue', difAmt-ftAmt);
											//  $("#ftDate").datebox('setValue',""); 
												//更新计划还款变
											 var  ftDt=	$("#ftDate").datebox('getValue');
												
												operRepaymentList("${basePath}beforeLoanController/updateRepaymentPlan.action?pId="+newProjectId+"&planRepayLoanDt="+repayCycleDate+"&loanId="+loanid+"&planOutLoanDt="+ftDt+"&creditEndDate="+"");
												$.messager.alert('操作提示', "保存成功！", 'info');
												$("#ftAmt").numberbox('setValue', 0);
												$("#difAmt").numberbox('setValue', 0);
												var  v=$("#ftDate").datebox('getValue');
												$("#shouldDate").datebox('setValue', v);
											} else {
												$.messager.alert('操作提示', "保存失败!", 'info');

											}
										}); 
								  //******
								  }
							}, "json");
				    }
				});	
				/* $("#ftAmt").numberbox('setValue', 0);
				$("#difAmt").numberbox('setValue', 0);
			 */
					
				
	 } else{
	
		$.post("${basePath}beforeLoanController/insertLoadOutputinfo.action",{
			shouldDate:shouldDate,
			shouldAmt:shouldAmt,
			ftAmt:ftAmt,
			difAmt:difAmt,
			outputDesc:outputDesc,
			ftDate:ftDate,
			repayCycleDate:repayCycleDate,
			ftBankAcctId:ftBankAcctId,
			loanId:loanid
		},function(data) {
			if (data>0) {
				$("#loanOutputGrid").datagrid('reload');
				$("#shouldAmt").numberbox('setValue', difAmt);
				$("#difAmt").numberbox('setValue', difAmt-ftAmt);
				$("#ftDate").datebox('setValue',datestr);
				$.messager.alert('操作提示', "保存成功！", 'info');
				$("#ftAmt").numberbox('setValue', 0);
				$("#difAmt").numberbox('setValue', 0);
			} else {
				$.messager.alert('操作提示', "保存失败！", 'info');

			}
		});
		$("#ftAmt").numberbox('setValue', 0);
		$("#difAmt").numberbox('setValue', 0);
	
	 }
	}
	function dateftSta(value, row, index) {
	
			 if ( (row.shouldAmt-row.ftAmt)>0){
				return "部分";
			}
			 else{
					return "全部";
					}
		}
	function dateSplit(value, row, index) {
		if (null != row.shouldDate && "" != row.shouldDate) {
			return row.shouldDate.split(" ")[0];
		}
		return row.shouldDate;
	}
	function dateftSplit(value, row, index) {
		if (null != row.ftDate && "" != row.ftDate) {
			return row.ftDate.split(" ")[0];
		}
		return row.ftDate;
	}
	function deOrestore(value, row, index) {
		var  cg="<a onclick='chengeOutput("+JSON.stringify(row)+","+index+")' href='#'><font color='blue'>编辑</font></a>|"
	     var  de="<a onclick='delOutput("+row.pId+","+index+")' href='#'><font color='blue'>删除</font></a>"
		return cg+de;
	}
	function chengeOutput(v,ind) {
		var row= $("#loanOutputGrid").datagrid('getRows');
		if((ind+1)!=row.length){
			$.messager.alert('操作提示', "只能编辑最后一条放款信息！", 'info');
			return false;
		}
		$("#shouldAmtdialog").textbox('setValue', v.shouldAmt);
		$("#shouldDatedialog").datebox('setValue', v.shouldDate);
		$("#ftDatedialog").datebox('setValue', v.ftDate);
		$("#ftBankAcctIddialog").val(v.ftBankAcctId);
		$("#ftAmtdialog").textbox('setValue', v.ftAmt);//--
		$("#difAmtdialog").textbox('setValue',v.difAmt);
		$("#pIddialog").val(v.pId);
		$('#dlg_spgscfk').dialog('open').dialog('setTitle', "新增放款前落实条件");
	}
	function delOutput(v,ind) {
		
	
		var row= $("#loanOutputGrid").datagrid('getRows');
		if((ind+1)!=row.length){
			$.messager.alert('操作提示', "只能删除最后一条放款信息！", 'info');
			return false;
		}
		if (confirm("是否确认删除?")) {
			$.post("${basePath}creditsContractController/deleteLoadOutputinfo.action", {
				pId :v
			},function(data){
				$("#loanOutputGrid").datagrid('reload');
				
				$.post("${basePath}beforeLoanController/loadOutputinfo.action", {
					pid :newProjectId
				},function(data){
				
					/*  （授信=1、授信贷款=2、额度贷款=3、贷款展期=4  */
					var da = $.parseJSON(data);
					if (da.projectType == 1) {
						$("#projectType").textbox('setValue', "授信");
					}
					if (da.projectType == 2) {
						$("#projectType").textbox('setValue', "授信贷款");
					}
					if (da.projectType == 3) {
						$("#projectType").textbox('setValue', "额度贷款");
					}
					if (da.projectType == 4) {
						$("#projectType").textbox('setValue', "贷款展期");
					}
					if (da.projectType == 5) {
						$("#projectType").textbox('setValue', "授信&贷款");
					}
					$("#shouldAmt").numberbox('setValue', da.creditAmt);
					$("#shouldDate").datebox('setValue', da.planOutLoanDt);
					$("#repayCycleDateInfo").datebox('setValue', da.planRepayLoanDt);
					var dataUrl = "${basePath}sysLookupController/getSysLookupValInfo.action?pid="+da.accType;
					$.post(	dataUrl, //通过还款方式id得到还款方式名称 
						function(lookupValName){
							 var name  = lookupValName;//得到还款方式名称
							$("#accType").textbox('setValue', name);
						}, "json");
					//$("#accType").textbox('setValue', da.accType);
					$("#loanCardId").textbox('setValue', da.loanCardId);
					$("#bank").combobox('setValue',da.bank);
					$("#ftBankAcctId").val(da.bank);
					$("#bankNum").val(da.bankNum);
					datestr	 = d.getFullYear()+ ((d.getMonth()<10)?("-0"+(d.getMonth()+1)):("-"+(d.getMonth()+1)))+((d.getDate()<10)?("-0"+d.getDate()):("-"+d.getDate()));
					$("#ftDate").datebox('setValue',$("#shouldDate").datebox('getValue'));
					
					$("#bankName").textbox('setValue', da.bankName);
					$("#accName").textbox('setValue', da.accName);
					$("#branchName").textbox('setValue', da.branchName);
					var dataUrl = "${basePath}sysLookupController/getSysLookupValInfo.action?pid="+da.bankAccCate;
					$.post(	dataUrl, //通过还款方式id得到还款方式名称 
						function(lookupValName){
							 var name  = lookupValName;//得到还款方式名称
							$("#bankAccCate").textbox('setValue', name);
						}, "json");
					//$("#bankAccCate").textbox('setValue', da.bankAccCate);
					$("#repayCycle").textbox('setValue', da.repayCycle);
					$("#accArea").text(da.accArea==null?"":da.accArea); 
					$("#loanRemark").text((da.remark==null||da.remark=='null')?'':da.remark); 
					$("#difAmt").numberbox('setValue',0);
					$("#ftAmt").numberbox('setValue',0);
				});
				$.messager.alert('操作提示', "删除成功！", 'info');
				var row= $("#loanOutputGrid").datagrid('getRows');
				 if(row.length==1){
					 var sd=$("#shouldDate").datebox('getValue');
					$("#ftDate").datebox('setValue',sd);
				//loanEndDtInfo();111111111111111
				}
			});
		}	
	}
	
	$(function(){
		0.
		
		$('body').delegate('.ftAmtdialog > span','blur',function(){

			 
				var ftAmtdialog =  $(this).find(' > input').val();//获取放款金额的值 
				var shouldAmtdialog = $("#shouldAmtdialog").textbox('getValue');//应放金额 
				var difAmtdialog = $("#difAmtdialog").textbox('getValue');//未放款
				if( Number(shouldAmtdialog) >= Number(ftAmtdialog)  ){
					$("#difAmtdialog").textbox('setValue',$("#shouldAmtdialog").textbox('getValue')-ftAmtdialog);
				}else{
					$.messager.alert('操作提示', "放款金额大于应放款金额！", 'info');
					$("#ftAmtdialog").textbox('setValue',0.00)
					return ;
				}
				/*
			var row = $("#loanOutputGrid").datagrid('getRows');//获取放款列表
			var   ftAmtdialog =  $(this).find(' > input').val();//获取还款期限
			if(ftAmtdialog>$("#shouldAmtdialog").textbox('getValue')){
				$.messager.alert('操作提示', "放款金额大于应放款金额！", 'info');
				$("#ftAmtdialog").textbox('setValue',0.00)
				return ;
			}  
			if($("#difAmtdialog").textbox('getValue')==0&&row.length!=1){
				return;
			}
			if(ftAmtdialog > $("#difAmtdialog").textbox('getValue') &&row.length!=1){
				$.messager.alert('操作提示', "放款金额大于未应放款金额！", 'info');
				$("#ftAmtdialog").textbox('setValue',0.00)
				return ;
			}
			
				$("#difAmtdialog").textbox('setValue',$("#shouldAmtdialog").textbox('getValue')-ftAmtdialog); */
			
		});
		if(projectName){		
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body a').removeAttr('onclick');
			$('.panel-body a font').attr('color','gray');
	 	}
		
		
		$('#loanOutputGrid').datagrid({
			url:'${basePath}beforeLoanController/loadOutputinfoList.action?projectId='+newProjectId
		})
		 
		$.post("${basePath}beforeLoanController/loadOutputinfo.action", {
			pid :newProjectId
		},function(data){
			/*  （授信=1、授信贷款=2、额度贷款=3、贷款展期=4  */
			var da = $.parseJSON(data);
			if (da.projectType == 1) {
				$("#projectType").textbox('setValue', "授信");
			}
			if (da.projectType == 2) {
				$("#projectType").textbox('setValue', "授信贷款");
			}
			if (da.projectType == 3) {
				$("#projectType").textbox('setValue', "额度贷款");
			}
			if (da.projectType == 4) {
				$("#projectType").textbox('setValue', "贷款展期");
			}
			if (da.projectType == 5) {
				$("#projectType").textbox('setValue', "授信&贷款");
			}
			$("#shouldAmt").numberbox('setValue', da.creditAmt);
			$("#shouldDate").datebox('setValue', da.planOutLoanDt);
			$("#repayCycleDateInfo").datebox('setValue', da.planRepayLoanDt);
			var dataUrl = "${basePath}sysLookupController/getSysLookupValInfo.action?pid="+da.accType;
			$.post(	dataUrl, //通过还款方式id得到还款方式名称 
				function(lookupValName){
					 var name  = lookupValName;//得到还款方式名称
					$("#accType").textbox('setValue', name);
				}, "json");
			//$("#accType").textbox('setValue', da.accType);
			$("#loanCardId").textbox('setValue', da.loanCardId);
			$("#bank").combobox('setValue',da.bank);
			$("#ftBankAcctId").val(da.bank);
			$("#bankNum").val(da.bankNum);
			datestr	 = d.getFullYear()+ ((d.getMonth()<10)?("-0"+(d.getMonth()+1)):("-"+(d.getMonth()+1)))+((d.getDate()<10)?("-0"+d.getDate()):("-"+d.getDate()));
			$("#ftDate").datebox('setValue',$("#shouldDate").datebox('getValue'));
			
			$("#bankName").textbox('setValue', da.bankName);
			$("#accName").textbox('setValue', da.accName);
			$("#branchName").textbox('setValue', da.branchName);
			var dataUrl = "${basePath}sysLookupController/getSysLookupValInfo.action?pid="+da.bankAccCate;
			$.post(	dataUrl, //通过还款方式id得到还款方式名称 
				function(lookupValName){
					 var name  = lookupValName;//得到还款方式名称
					$("#bankAccCate").textbox('setValue', name);
				}, "json");
			//$("#bankAccCate").textbox('setValue', da.bankAccCate);
			$("#repayCycle").textbox('setValue', da.repayCycle);
			$("#accArea").text(da.accArea==null?"":da.accArea); 
			$("#loanRemark").text((da.remark==null||da.remark=='null')?'':da.remark); 
			
			//如果勾选额度申请的话，隐藏财务放款页签
			var url = "${basePath}beforeLoanController/getLoanProjectByProjectId.action";
	    	$.post(url,
		    		   {projectId : newProjectId},
						function(data){
		    			   if(data.projectType==1){
		    				   $('.panel-none').addClass("none");
		    				   $("#loan_stype").val(1);
		    			   }
		    		   },"json");
		});

	});
	function saveSpgsceditfk(){
		
		$("#fm_spgscfk").form('submit',
		 		{
		 		success : function(result) {
		 			$("#shouldAmt").numberbox("setValue",result);
		 			$("#loanOutputGrid").datagrid('reload');
		 			$('#dlg_spgscfk').dialog('close')
		 			$.messager.alert('操作提示', "更新成功！", 'info');
		 		}
		 	}); 
	}
	function getAccountByPrimaryKeyAccrual1(rec){
		// 判断是不是请选择,如果是直接退出
		if(rec.pid == -1){
			return;
		}
		// 设置帐号
		$("#bankNum").val(rec.bankNum);
		$("#ftBankAcctId").val(rec.pid);
	}
	
	function editing(newValue,oldValue){
		$('#difAmt').numberbox('setValue',Number($('#shouldAmt').textbox('getValue'))-Number(newValue));
	}

	function outputdis(){
		if("task_FinancialLoans"==workflowTaskDefKey){
		}else{
			$("#loadOutputbutton .easyui-numberbox").numberbox("disableValidation"); 
			$('.output_dis .easyui-combobox').attr('disabled','disabled');
			$('.output_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.output_dis .easyui-textbox').attr('disabled','disabled');
			$('.output_dis .easyui-linkbutton ,.output_dis a').removeAttr('onclick');
			$('.output_dis a font').attr('color','gray');
			$('.output_dis .easyui-linkbutton ,.output_dis  input,.output_dis  textarea').attr('disabled','disabled');
			$('.output_dis .easyui-linkbutton ,.output_dis  input,.output_dis  textarea').attr('readOnly','readOnly');
			$('.output_dis .easyui-linkbutton ,.output_dis  input,.output_dis  textarea').addClass('l-btn-disabled');
		}
	}
	function editingdialogant(){
		if($("#shouldAmtdialog").textbox('getValue')<$("#ftAmtdialog").textbox('getValue')){
			$.messager.alert('操作提示', "放款金额大于应放款金额！", 'info');
			$("#ftAmtdialog").textbox('setValue',0.00)
			return ;
		}else{
			$("#difAmtdialog").textbox('setValue',$("#shouldAmtdialog").textbox('getValue')-$("#ftAmtdialog").textbox('getValue'));
		} 
	
	}
	function  loanEndDtInfo(da) {
		var row = $("#loanOutputGrid").datagrid('getRows');//获取放款列表
		var shouldDate = $("#shouldDate").datebox('getValue');//应放款日期 
	 	var ftDate = $("#ftDate").datebox('getValue');//放款日期 
		var repayCycle = $("#repayCycle").textbox('getValue');//期限 
		var repayCycleDate = "";//$("#repayCycleDate").datebox('getValue');//还款日期 
		if(row.length==0){//列表没有数据才能进行计算 
			var url = "${basePath}beforeLoanController/getLoanProjectByProjectId.action?projectId="+newProjectId;
			$.post(url,//通过 项目ID得到还款方式id 
			  	function(data){
					var repayFun = data.repayFun;//获取还款方式ID
					repayCycleDate = data.planRepayLoanDt;
					var dataUrl = "${basePath}sysLookupController/getSysLookupValInfo.action?pid="+repayFun;
					$.post(	dataUrl, //通过还款方式id得到还款方式名称 
						function(lookupValName){
	 					 var name  = lookupValName;//得到还款方式名称
						 var day =0;
						 var year = 0;
						 if(name=="等额本金"|| name=="等额本息" || name=="等本等息"||name=="其他还款方式"||name=="按月-先息后本"){
							 year = 1;
						 };
						 if(year==1){//表示按月还款 
							  for(var i=0;i<repayCycle;i++){
									if(i==(repayCycle-1)){
										var endMoth  = addMoth(shouldDate, Number(repayCycle));
										var startDate = DateAdd("-d", 1, dateInfo(endMoth)); // 在减一天
										var timeInfo = startDate.format('yyyy-MM-dd');// 转换格式
										
										// alert("加上期限得到的时期----"+timeInfo);
										
										var month = addMoth(shouldDate, i);// 加上 i个月 获取到倒数第二个的日期 
										var startDateInfo = DateAdd("-d", 1, dateInfo(month)); // 在减一天
										var dates = startDateInfo.format('yyyy-MM-dd');// 转换格式
										//****得到倒数第二个的日期 
										// alert("第二个加上期限得到的时期----"+dates);
										
										var newMonth = addMoth(dates, 1);//得到 倒数第二个日期之后 在加上1个月看是否大于还款日期 
										var newMonthTime = DateAdd("-d", 0, dateInfo(newMonth));  
										var newMonthDate = newMonthTime.format('yyyy-MM-dd');// 转换格式
										
										 //如果加了最后一期日期相等就是整期不用计算 
										if(newMonthDate== repayCycleDate){
											var repayDateInfo = addMoth(ftDate, Number(repayCycle));//直接加上期限
											var repayDateTime = DateAdd("-d", 1, dateInfo(repayDateInfo)); // 在减一天
											var repayDate = repayDateTime.format('yyyy-MM-dd');// 转换格式
											$("#repayCycleDateInfo").datebox('setValue',repayDate);//赋值  
										
										}else{
											var daysx =  DateDiff(dates,repayCycleDate);//i期 零 daysx天 
											var checkTime  = addMoth(ftDate, Number(i)); 
											//alert(checkTime); //是否减一 daysx 
											var startDate1 = DateAdd("d", Number(daysx)-1, dateInfo(checkTime)); //在减一天
											var timeInfox = startDate1.format('yyyy-MM-dd');// 转换格式
											var info = timeInfox.substring(5, 7);
											//alert(timeInfox);
											//alert(i+"--------"+daysx); 
											$("#repayCycleDateInfo").datebox('setValue',timeInfox);//赋值 
										}
									}  
							  }
							 
						 }else{//按天计算
							 var day =  DateDiff(shouldDate,repayCycleDate);//得出两个 日期相差的天数 
							 var newDate   =   DateAdd( "d",Number(day),dateInfo(ftDate));  //加上天数 
							 var dates = newDate.format('yyyy-MM-dd');//转换格式 
							 $("#repayCycleDateInfo").datebox('setValue',dates);//赋值 
						 }
						}, "json");
				}, "json");
		}
		  
	}

//计算两个日期相差多少天
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays  
    aDate  =  sDate1.split("-")  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
    return  iDays  
}    

//贷款试算
function loancalcs(){
	if(loanId==-1){
		$.messager.alert('操作提示','请先保存财务放款信息','info');
	}else{
		operRepaymentList('${basePath}beforeLoanController/loanCalc.action?loanId='+loanId);
	}
}
</script>