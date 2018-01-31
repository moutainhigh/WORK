<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<style type="text/css">
.table_css th {
	background: linear-gradient(to bottom, #EFF5FF 0, #E0ECFF 100%);
	background-repeat: repeat-x;
	padding: 7px 5px;
	font-size: 12px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}

.table_css tr {
	background: #fff;
}

.table_css tr:nth-child(even) {
	background: #fff;
}

.table_css tr:nth-child(odd) {
	background: #f9f9f9;
}

.table_css td {
	padding: 7px 5px;
	font-size: 12px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}

.hidden_css {
	display: none;
}
</style>
<table cellpadding="0" cellspacing="0" class="table_css"
	style="width: 100%; border-top: 1px #ddd solid; border-left: 1px #ddd solid;">
	<tr>
		<th width="4%" align="center">序号</th>
		<th width="5%" align="center">状态</th>
		<th width="10%">办理步骤</th>
		<th align="center">内容</th>
		<th width="8%" align="center">跟进备注</th>
		<th width="6%" align="center">操作人</th>
		<th width="13%" align="center">操作日期</th>
	</tr>
	<c:forEach var="dynamic" items="${dynamicList}" varStatus="status">
	<c:if test="${dynamic.moduelNumber != 'c' }">
		<tr>
			<td align="center">${status.index+1}</td>
			<td align="center">
				<!-- 状态：未完成=1，进行中=2，已完成=3，失效=4 --> <c:choose>
					<c:when test="${dynamic.status==4}">
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_4.png"
							width="16" height="16" />
					</c:when>
					<c:when test="${dynamic.status==3}">
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_3.png"
							width="16" height="16" />
					</c:when>
					<c:otherwise>
						<img src="${ctx}/p/xlkfinance/images/dynamicStatus_1.png"
							width="16" height="16" />
					</c:otherwise>
				</c:choose>
			</td>
			<td>${dynamic.dynamicName}</td>
			<td>${dynamic.remark}<c:if
					test="${dynamic.dynamicNumber eq 'b' && dynamic.moduelNumber eq 'b'}">
					<a href="#" onclick="openCollectFileDialog()" style="color: blue;">查看要件</a>
				</c:if> <c:if
					test="${dynamic.dynamicNumber eq 'e' && dynamic.moduelNumber eq 'b'}">
					<!-- 查看查档文件 -->
					<a href="#" onclick="openCheckDocumentDialog(1)"
						style="color: blue;">查看文件</a>
				</c:if> <c:if
					test="${dynamic.dynamicNumber eq 'f' && dynamic.moduelNumber eq 'b'}">
					<!-- 查看查档复合文件 -->
					<a href="#" onclick="openCheckDocumentDialog(2)"
						style="color: blue;">查看文件</a>
				</c:if>
				<c:if test="${dynamic.dynamicNumber eq 'a' && dynamic.moduelNumber eq 'b'}">
					<a href="#" onclick="openCollectFeeHisDialog()" style="color: blue;">查看收费历史</a>
				</c:if> 
			</td>
			<td align="center"><c:if test="${dynamic.moduelNumber eq 'b'}">
					<c:if
						test="${dynamic.dynamicNumber eq 'g'||dynamic.dynamicNumber eq 'h'||dynamic.dynamicNumber eq 'j'||dynamic.dynamicNumber eq 'k'||dynamic.dynamicNumber eq 'l'||dynamic.dynamicNumber eq 'm'||dynamic.dynamicNumber eq 'n'||dynamic.dynamicNumber eq 'o' }">
						<a href="#"
							onclick="openHisDifferWarnListDialog(${dynamic.projectId},'${dynamic.moduelNumber}','${dynamic.dynamicNumber}')"
							style="color: blue;">查看备注</a>
					</c:if>
				</c:if></td>
			<td align="center">${dynamic.handleAuthorName}</td>
			<td align="center">${dynamic.updateDate}</td>
		</tr>
	</c:if>
	</c:forEach>
</table>
<div id="hisDifferWarnListDialog" class="easyui-dialog"
	style="width: 1013px; height: 448px; padding: 10px 20px;" closed="true">
	<table id="grid_hisDifferWarnList" class="easyui-datagrid"
		style="height: 100%; width: auto;"
		data-options="
      method: 'POST',
      rownumbers: true,
      singleSelect: true,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true">
		<!-- 表头 -->
		<thead>
			<tr>
				<th data-options="field:'projectName',sortable:true" align="center"
					halign="center">项目名称</th>
				<th data-options="field:'flowName',sortable:true" align="center"
					halign="center">办理动态</th>
				<th data-options="field:'differ',sortable:true" align="center"
					halign="center">差异</th>
				<th data-options="field:'handleAuthorName',sortable:true"
					align="center" halign="center">处理者</th>
				<th
					data-options="field:'handleDate',sortable:true,formatter:convertDateTime"
					align="center" halign="center">处理时间</th>
				<th data-options="field:'remark',sortable:true,width:120"
					align="center" halign="center">备注</th>
			</tr>
		</thead>
	</table>
</div>
<div id="collect_file_dialog" class="easyui-dialog"
	buttons="#submitDiv1" style="width: 433px; height: 180px;"
	closed="true">
	<div style="margin-left: 30px; margin-top: 10px; line-height: 35px;">
		<form id="collectFileForm" name="collectFileForm" action="#"
			method="post">
			<div style="padding: 5px;">
				<div id="sellerNameBtns">选择卖方：</div>
				<div id="buyerNameBtns">选择买方：</div>
			</div>
			<input type="hidden" id="projectId" name="projectId" />
		</form>
	</div>
</div>
<div id="submitDiv1">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:$('#collect_file_dialog').dialog('close')">关闭</a>
</div>
<div id="collect_file_list_dialog" class="easyui-dialog"
	buttons="#submitCollectFileListDialogDiv"
	style="width: 533px; height: 500px;" closed="true">
	<div style="margin-left: 30px; margin-top: 10px; line-height: 35px;">
		<form id="collectFileListForm" name="collectFileListForm" action="#"
			method="post">
			<table border="0" cellpadding="0" class="cus_table" cellspacing="0"
				style="width: 90%;">
				<tr class="buyerSellerType2">
					<td width="120px"><input type="checkbox"
						value="SELLER_CARD_NO" id="SELLER_CARD_NO"
						name="collectFileMapList[0].code">卖方身份证：</td>
					<td><input id="SELLER_CARD_NO_REMARK" style="width: 200px;"
						name="collectFileMapList[0].remark" class="easyui-textbox"></td>
				</tr>


				<tr class="buyerSellerType2">
					<td><input type="checkbox" value="REPAYMENT_CARD"
						id="REPAYMENT_CARD" name="collectFileMapList[1].code">回款卡/折：</td>
					<td><input id="REPAYMENT_CARD_REMARK" style="width: 200px;"
						name="collectFileMapList[1].remark" class="easyui-textbox"></td>
				</tr>

				<tr class="buyerSellerType2">
					<td><input type="checkbox" value="FORECLOSURE_CARD"
						id="FORECLOSURE_CARD" name="collectFileMapList[2].code">赎楼卡/折：</td>
					<td><input id="FORECLOSURE_CARD_REMARK"
						name="collectFileMapList[2].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>

				<tr class="buyerSellerType1">
					<td width="120px"><input type="checkbox" value="BUYER_CARD_NO"
						id="BUYER_CARD_NO" name="collectFileMapList[31].code">买方身份证：</td>
					<td><input id="BUYER_CARD_NO_REMARK" style="width: 200px;"
						name="collectFileMapList[31].remark" class="easyui-textbox"></td>
				</tr>

				<tr class="buyerSellerType1">
					<td><input type="checkbox" value="SUPERVISE_CARD"
						id="SUPERVISE_CARD" name="collectFileMapList[32].code">监管卡/折：</td>
					<td><input id="SUPERVISE_CARD_REMARK"
						name="collectFileMapList[32].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>
				<tr>
					<td><input type="checkbox" value="PAYMENT_NAME" id="PAYMENT_NAME"
						name="collectFileMapList[33].code">回款账号名：</td>
					<td><input id="PAYMENT_NAME_REMARK"
						name="collectFileMapList[33].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>

				<tr>
					<td><input type="checkbox" value="ID_CARD_NUMBER" id="ID_CARD_NUMBER"
						name="collectFileMapList[34].code">回款人身份证号：</td>
					<td><input id="ID_CARD_NUMBER_REMARK"
						name="collectFileMapList[34].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>
				



				<tr>
					<td><input type="checkbox" value="BANK_CARD" id="BANK_CARD"
						name="collectFileMapList[3].code">银行卡/折：</td>
					<td><input id="BANK_CARD_REMARK"
						name="collectFileMapList[3].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>

				<tr>
					<td><input type="checkbox" value="NET_SILVER" id="NET_SILVER"
						name="collectFileMapList[4].code">网上银行：</td>
					<td><input id="NET_SILVER_REMARK"
						name="collectFileMapList[4].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>

				<tr>
					<td><input type="checkbox" value="SUPER_NET_SILVER"
						id="SUPER_NET_SILVER" name="collectFileMapList[5].code">超级网银：</td>
					<td><input id="SUPER_NET_SILVER_REMARK"
						name="collectFileMapList[5].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>

				<tr>
					<td><input type="checkbox" value="BANK_PHONE" id="BANK_PHONE"
						name="collectFileMapList[6].code">银行预留手机号：</td>
					<td><input id="BANK_PHONE_REMARK"
						name="collectFileMapList[6].remark" style="width: 200px;"
						class="easyui-textbox" data-options="validType:'phone'"></td>
				</tr>

				<tr>
					<td><input type="checkbox" value="TRANSFER_LIMIT_MONEY"
						id="TRANSFER_LIMIT_MONEY" name="collectFileMapList[7].code">转账限额：</td>
					<td><input id="TRANSFER_LIMIT_MONEY_REMARK"
						name="collectFileMapList[7].remark" style="width: 50px;"
						class="easyui-numberbox"></td>
				</tr>

				<tr>
					<td><input type="checkbox" value="COMPANY_NAME"
						id="COMPANY_NAME" name="collectFileMapList[8].code">公司名称：</td>
					<td><input id="COMPANY_NAME_REMARK"
						name="collectFileMapList[8].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>
				<tr>
					<td><input type="checkbox" value="E_BANK_RELATE_ACCOUNT"
						id="E_BANK_RELATE_ACCOUNT" name="collectFileMapList[30].code">网银关联账户：</td>
					<td><input id="E_BANK_RELATE_ACCOUNT_REMARK"
						name="collectFileMapList[30].remark" style="width: 200px;"
						class="easyui-textbox"></td>
				</tr>
			</table>

			<table border="0" cellpadding="0" class="cus_table" cellspacing="0"
				style="display: none; width: 90%; height: 177px;"
				id="collectFilePanel">
				<tr>
					<td><input type="checkbox" value="OFFICIAL_SEAL"
						id="OFFICIAL_SEAL" name="collectFileMapList[9].code">公章<input
						id="OFFICIAL_SEAL_REMARK" name="collectFileMapList[9].remark"
						style="width: 50px;" class="easyui-textbox"></td>
					<td><input type="checkbox" value="PERSONAL_SEAL"
						id="PERSONAL_SEAL" name="collectFileMapList[10].code">私章<input
						id="PERSONAL_SEAL_REMARK" name="collectFileMapList[10].remark"
						style="width: 50px;" class="easyui-textbox"></td>
					<td><input type="checkbox" value="FINANCE_SEAL"
						id="FINANCE_SEAL" name="collectFileMapList[11].code">财务章</td>
				</tr>
				<tr>
					<td><input type="checkbox" value="OPEN_ACCOUNT_LICENCE"
						id="OPEN_ACCOUNT_LICENCE" name="collectFileMapList[12].code">开户许可证</td>
					<td><input type="checkbox" value="RESERVED_SIGNATURE_CARD"
						id="RESERVED_SIGNATURE_CARD" name="collectFileMapList[13].code">预留印鉴卡</td>
					<td><input type="checkbox" value="APPLY_SEAL" id="APPLY_SEAL"
						name="collectFileMapList[14].code">刻章申请</td>
				</tr>
				<tr>
					<td><input type="checkbox" value="BANK_DEBIT_CARD"
						id="BANK_DEBIT_CARD" name="collectFileMapList[15].code">银行结算卡</td>
					<td><input type="checkbox" value="BUSINESS_LICENSE_ORIGINAL"
						id="BUSINESS_LICENSE_ORIGINAL" name="collectFileMapList[16].code">营业执照正本
						<input type="checkbox" value="BUSINESS_LICENSE_COPY"
						id="BUSINESS_LICENSE_COPY" name="collectFileMapList[17].code">副本</td>
					<td><input type="checkbox" value="TAX_REGISTRATION_ORIGINAL"
						id="TAX_REGISTRATION_ORIGINAL" name="collectFileMapList[18].code">税务登记证正本
						<input type="checkbox" value="TAX_REGISTRATION_COPY"
						id="TAX_REGISTRATION_COPY" name="collectFileMapList[19].code">副本</td>
				</tr>
				<tr>
					<td><input type="checkbox" value="ORG_CODE__LICENSE_ORIGINAL"
						id="ORG_CODE__LICENSE_ORIGINAL" name="collectFileMapList[20].code">组织机构代码证正本</td>
					<td><input type="checkbox" value="ORG_CODE__LICENSE_COPY"
						id="ORG_CODE__LICENSE_COPY" name="collectFileMapList[21].code">副本</td>
					<td><input type="checkbox" value="CREDIT_ORG_CODE_LICENSE"
						id="CREDIT_ORG_CODE_LICENSE" name="collectFileMapList[22].code">信用机构代码证</td>
				</tr>
				<tr>
					<td><input type="checkbox" value="PUBLIC_CONVERSION_PRIVATE"
						id="PUBLIC_CONVERSION_PRIVATE" name="collectFileMapList[23].code">公转私</td>
					<td><input type="checkbox" value="CHEQUE" id="CHEQUE"
						name="collectFileMapList[24].code">支票 剩余<input
						id="CHEQUE_REMARK" name="collectFileMapList[24].remark"
						style="width: 50px;" class="easyui-numberbox">张</td>
					<td><input type="checkbox" value="CHEQUE_PWD_MACHINE"
						id="CHEQUE_PWD_MACHINE" name="collectFileMapList[25].code">支票密码器</td>
				</tr>
				<tr>
					<td><input type="checkbox" value="POSTING_FEE"
						id="POSTING_FEE" name="collectFileMapList[26].code">过账费</td>
					<td><input type="checkbox" value="WECHAT_PAY" id="WECHAT_PAY"
						name="collectFileMapList[27].code">微信支付</td>
					<td><input type="checkbox" value="PAY_TREASURE"
						id="PAY_TREASURE" name="collectFileMapList[28].code">支付宝</td>
				</tr>
				<tr>
					<td colspan="3"><input type="checkbox" value="ORDER"
						id="ORDER" name="collectFileMapList[29].code">其他 <textarea
							rows="2" id="ORDER_REMARK" name="collectFileMapList[29].remark"
							maxlength="500" style="width: 250px;"></textarea></td>
				</tr>
			</table>
			<div class="stretch_box">
				<p class="slide">
					<a href="javascript:;" class="btn-slide active"></a>
				</p>
			</div>
			<input type="hidden" id="collectFileListProjectId" name="projectId" />
			<input type="hidden" id="buyerSellerType" name="buyerSellerType" />
			<input type="hidden" id="buyerSellerName" name="buyerSellerName" />
		</form>
	</div>
</div>
<div id="submitCollectFileListDialogDiv">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-print" plain="true" onclick="collectFilePrint()">打印</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:$('#collect_file_list_dialog').dialog('close')">关闭</a>
</div>
<div id="checkDocumentFileListDialog" class="easyui-dialog"
	data-options="modal:true"
	style="width: 450px; height: 320px; padding: 10px;" closed="true">
	<table id="grid_checkDocumentFileList" title="文件列表"
		class="easyui-datagrid" style="height: 270px; width: auto;"
		data-options="
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'pid',checkbox:true" />
				<th data-options="field:'projectId',hidden:true" />
				<th
					data-options="field:'file.fileName',formatter:formatFileName,sortable:true,width:167"
					align="center" halign="center">文件名</th>
				<th
					data-options="field:'file.uploadDttm',formatter:formatUploadDttm,sortable:true,width:167"
					align="center" halign="center">上传时间</th>
				<th
					data-options="field:'fileUrl',formatter:formatCheckDocumentFileListOperate"
					align="center" halign="center">操作</th>
			</tr>
		</thead>
	</table>
</div>
<div id="collectFeeHisDialog" class="easyui-dialog"
	data-options="modal:true"
	style="width: 750px; height: 320px; padding: 10px;" closed="true">
	<!-- 财务收费历史列表(分页查询) -->
    <table id="collectFeeHis_grid"  title="财务收费历史列表" class="easyui-datagrid" style="height: 270px; width: auto;"
     data-options="
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true" />
       <th data-options="field:'consultingFee',formatter:formatMoney" align="center" halign="center">咨询费</th>
       <th data-options="field:'poundage',formatter:formatMoney" align="center" halign="center">手续费</th>
       <th data-options="field:'brokerage',formatter:formatMoney" align="center" halign="center">佣金</th>
       <th data-options="field:'recDate',formatter:convertDate" align="center" halign="center">收款日期</th>
       <th data-options="field:'recAccount'" align="center" halign="center">收款账号</th>
       <th data-options="field:'proResource'" align="center" halign="center">款项来源</th>
       <th data-options="field:'createrName'" align="center" halign="center">操作人</th>
       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center">操作时间</th>
      </tr>
     </thead>
    </table>
</div>
<script type="text/javascript">
//预警备注历史列表
function openHisDifferWarnListDialog(projectId,moduelNumber,dynamicNumber) {
	if (moduelNumber!='b') {
		return;
	}
	var map={'g':1,'h':2,'j':3,'k':4,'l':5,'m':6,'n':7,'o':10};
	var flowId=map[dynamicNumber];
	if (flowId==null) {
		return;
	}
	var url = "";// 路径
	$('#grid_hisDifferWarnList').datagrid({  
		url:'<%=basePath%>bizHandleController/hisDifferWarnList.action?projectId='+projectId+'&flowId='+flowId,  
	}); 
	$('#hisDifferWarnListDialog').dialog('open').dialog('setTitle', "预警备注历史列表");
}
 //打开要件窗口
 function openCollectFileDialog() {
    $.ajax({
     url : "${basePath}integratedDeptController/getBuyerSellerByProjectId.action",
     cache : true,
     type : "POST",
     data : {'projectId' : projectId},
     async : false,
     success : function(data, status) {
      var result = eval("("+data+")");
      buyerMap = result['buyerMap'];
      sellerMap = result['sellerMap'];
      supersionReceAccount = result['supersionReceAccount'];
      paymentAccount = result['paymentAccount'];
      foreAccount = result['foreAccount'];
      //初始化买方姓名按钮
      $.parser.parse($("#buyerNameBtns").append(appendBuyerSellerButs(buyerMap,1)));
      //初始化卖方姓名按钮
      $.parser.parse($("#sellerNameBtns").append(appendBuyerSellerButs(sellerMap,2))); 
               $("#collectFileDate").datebox('setValue',formatterDate(new Date()));
         $("#refundFileDateDiv").addClass("hidden_css");
         $("#collectDateDiv").removeClass("hidden_css");
               $('#collect_file_dialog').dialog('open').dialog('setTitle', "收件信息");
     }
    });
     }
   //通过传入的买卖方list，返回按钮的html
  function appendBuyerSellerButs(buyerSellerMap,buyerSellerType){
   if (buyerSellerMap==null||buyerSellerMap.length==0) {
    return "";
   }
   var html="";
   $.each(buyerSellerMap, function(key, value) {
    var name=key;
    html+="<a href='#' onclick=\"initCollectFile('"+name+"','"+buyerSellerType+"')\" style='margin-left: 5px' class='easyui-linkbutton buyer_seller_linkbutton' data-options=\"toggle:true,group:'g2',plain:true\">"+name+"</a>";
   }); 
   return html;
  }
  //点击买卖方按钮时，初始化该买卖方的收件数据
  function initCollectFile(name,buyerSellerType){
     if (buyerSellerType==1) {//等于1显示class 为buyerSellerType的买方属性，等于2的隐藏
       $(".buyerSellerType2").addClass("hidden_css");
       $(".buyerSellerType1").removeClass("hidden_css");
   }else{
       $(".buyerSellerType1").addClass("hidden_css");
       $(".buyerSellerType2").removeClass("hidden_css");
   } 
  $("#collectFileListProjectId").val(projectId);//项目id
  $("#buyerSellerType").val(buyerSellerType);//买卖类型：买方=1，卖方=2
  $("#buyerSellerName").val(name);//买卖方姓名
       $.ajax({
     url : "${basePath}integratedDeptController/getCollectFileInfo.action",
     cache : true,
     type : "POST",
     data : {'projectId' : projectId,'buyerSellerType' : buyerSellerType,'buyerSellerName' : name},
     async : false,
     success : function(data, status) {
      var result = eval("("+data+")");
      var collectFileList = result['collectFileList'];//获取已经收取的要件
      //初始化已经收取的要件信息
      for (var i = 0; i < collectFileList.length; i++) {
       $('#'+collectFileList[i].code).prop('checked',true);
       if(collectFileList[i].code=="ORDER"){
           $('#'+collectFileList[i].code+'_REMARK').val(collectFileList[i].remark);
       }else{
           $('#'+collectFileList[i].code+'_REMARK').textbox('setValue',collectFileList[i].remark);
       }
      }
      $('#collect_file_list_dialog').dialog('open').dialog('setTitle', "收取要件");
     }
    }); 
   }
 //收件打印
 function collectFilePrint(){
  var openType=1; //打开方式：收取要件=1.，退还要件=2
  var projectId=$("#collectFileListProjectId").val(); 
  var buyerSellerName=$("#buyerSellerName").val(); 
  var buyerSellerType=$("#buyerSellerType").val(); 
  var url = "${basePath}integratedDeptController/toCollectFilePrint.action?projectId="+projectId+"&buyerSellerName="+buyerSellerName+"&buyerSellerType="+buyerSellerType+"&printType="+openType;
  window.open(url);
 }
 $(document).ready(function() {
  $('#collectFileListForm input').prop("disabled","disabled");
   //收件展开收起
     $(".btn-slide").click(function(){
    $("#collectFilePanel").slideToggle("slow");
    $(".btn-slide").toggleClass("active"); return false;
   }); 
  //收件窗口关闭时，清空表单数据
   $('#collect_file_dialog').dialog({
       onClose:function(){
         buyerMap=null;
         sellerMap=null;
         $(".buyer_seller_linkbutton").remove();//把买卖双方的姓名按钮移除
       }
   });
	$('#collect_file_list_dialog').dialog({
 	    onClose:function(){
 	    	$('#collectFileListForm').form('reset');
 	    	$('#collectFileListForm input:checkbox').attr("checked",false);
 	    }
 	});
 });
//根据查档文件类型和项目id初始化文件列表
	function initCheckDocumentFileList(fileCategory){
		$('#grid_checkDocumentFileList').datagrid({  
			    url:'<%=basePath%>integratedDeptController/checkDocumentFileList.action',  
			    queryParams:{  
			    	fileCategory : fileCategory,
			    	projectId : ${project.pid}
			    }
		}); 
	}
	//打开查档窗口
	function openCheckDocumentDialog(fileCategory) {
		initCheckDocumentFileList(fileCategory);//加载已经上传文件列表
     $('#checkDocumentFileListDialog').dialog('open').dialog('setTitle', "查档文件");
	}
	//打开收费历史窗口
	function openCollectFeeHisDialog(fileCategory) {
		initCollectFeeHisList();//加载收费历史列表
     $('#collectFeeHisDialog').dialog('open').dialog('setTitle', "收费历史");
	}
	//根据项目id收费历史列表
	function initCollectFeeHisList(){
		$('#collectFeeHis_grid').datagrid({  
			    url:'<%=basePath%>financeHandleController/collectFeeHisList.action',  
			    queryParams:{  
			    	projectId : ${project.pid}
			    }
		}); 
	}
	function formatFileName(val, row) {
		return row.file.fileName;
}
	function formatUploadDttm(val, row) {
			return convertDateTime(row.file.uploadDttm);
	}
	//格式化查档文件操作
	function formatCheckDocumentFileListOperate(val, row) {
		var fileType=row.file.fileType;
		var fileUrl=row.file.fileUrl;
		var fileName=row.file.fileName;
		fileType = fileType.toLowerCase();
		var va = "<a onclick=downLoadFileByPath('"+fileUrl+"','"+fileName+"') href='#'> <font color='blue'>下载</font></a>";	
		if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
			va = va+" <a onclick=lookUpFileByPath('"+fileUrl+"','"+fileName+"') href='#'> <font color='blue'>预览</font></a>";
		}
		return va;
	}
   </script>
