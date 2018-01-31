<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

	<%-- begin 抵押DIV --%>
	<div id="mortgageNews">
	
		<h4>抵押信息：</h4>
		<div id="toolbar_dyxx" class="easyui-panel" style="border-bottom: 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openAdd(1)">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEdit(1)">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDzywxx(1)">删除</a>
		</div>
		<%-- 抵押信息列表 --%>
		<div id="dyxxDiv" style="padding-left: 20px;" >
		    <table id="dyxx_grid" class="easyui-datagrid" style="width: auto;height: auto;"	    	
			 	data-options="
				 	url: '<%=basePath%>mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId='+newProjectId+'&mortgageGuaranteeType=1&oldProject='+projectId,
				    method: 'POST',
				    singleSelect: false,
				    sortOrder:'asc',
				    remoteSort:false,
				    toolbar: '#toolbar_dyxx',
				    resizable: false,
		    		fitColumns:true,
				    idField: 'pid'
			    ">
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true" ></th>
						<th data-options="field:'ownNameText'" width="8%" align="center" halign="center"  >权利人</th>
						<th data-options="field:'itemName',formatter:projectAssBaseHyperlink" width="15%" align="center" halign="center"   >物件名称（房地产名称）</th>
						<th data-options="field:'warrantsNumber'" width="10%" align="center" halign="center"   >权证编号</th>
						<th data-options="field:'constructionArea'" width="10%" align="center" halign="center"   >物件描述</th>
						<th data-options="field:'assessValue'" width="10%" align="center" halign="center"   >评估净值</th>
						<th data-options="field:'regPrice'" width="10%" align="center" halign="center"   >登记价（元）</th>
						<th data-options="field:'regDt',formatter:convertDate" width="10%" align="center" halign="center"   >登记日期</th>
						<th data-options="field:'addressProvince',formatter:formatTheObject" width="20%"  halign="center"   >物件地址</th>
						<th data-options="field:'purpose'" width="20%" halign="center"   >用途</th>
						<th data-options="field:'remark'"  width="20%" halign="center"  >备注</th>	
					</tr>
				</thead>
		   	</table>
		</div>
	</div><%-- end 抵押DIV --%>
	
	
	<%-- begin 质押DIV --%>
	<div id="pledgeNews">
		
		<h4>质押信息：</h4>
		<div id="toolbar_zyxx" class="easyui-panel" style="border-bottom: 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openAdd(2)">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEdit(2)">编辑</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDzywxx(2)">删除</a>
		</div>
		<%-- 质押信息列表 --%>
		<div id="zyxxDiv" style="padding-left: 20px;" >
		    <table id="zyxx_grid" class="easyui-datagrid" style="width: auto;height: auto;"	    	
			 	data-options="
					url: '<%=basePath%>mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId='+newProjectId+'&mortgageGuaranteeType=2&oldProject='+projectId,
				    method: 'POST',
				    singleSelect: false,
				    sortOrder:'asc',
				    remoteSort:false,
				    toolbar: '#toolbar_zyxx',
				    resizable: false,
		    		fitColumns:true,
				    idField: 'pid'
			    ">
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true" ></th>
						<th data-options="field:'ownNameText'" width="8%" align="center" halign="center"  >权利人</th>
						<th data-options="field:'itemName',formatter:projectAssBaseHyperlink" width="15%" align="center" halign="center"   >物件名称（房地产名称）</th>
						<th data-options="field:'warrantsNumber'" width="10%" align="center" halign="center"   >权证编号</th>
						<th data-options="field:'constructionArea'" width="10%" align="center" halign="center"   >物件描述</th>
						<th data-options="field:'assessValue'" width="10%" align="center" halign="center"   >评估净值</th>
						<th data-options="field:'regPrice'" width="10%" align="center" halign="center"   >登记价（元）</th>
						<th data-options="field:'regDt',formatter:convertDate" width="10%" align="center" halign="center"   >登记日期</th>
						<th data-options="field:'addressProvince',formatter:formatTheObject" width="20%"  halign="center"   >物件地址</th>
						<th data-options="field:'purpose'" width="20%" halign="center"   >用途</th>
						<th data-options="field:'remark'"  width="20%" halign="center"  >备注</th>
					</tr>
				</thead>
		   	</table>
		</div>
		
	</div><%-- end 质押DIV --%>
	
	
	<%-- begin 个人保证信息DIV --%>
	<div id="personalGuaranteeNews">
		
		<h4>个人保证信息：</h4>
		<div id="toolbar_news_personalGuarantee" class="easyui-panel" style="border-bottom: 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openAddPerGuarantee(1)">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="deleteProjectAssure(1)">删除</a>
		</div>
		<div id="personalGuarantee_div" style="padding-left: 20px;" >
			<table id="perGuarantee_grid" class="easyui-datagrid"  
		    	style="height: auto;width: auto;"
			 	data-options="
				    url: '<%=basePath%>secondBeforeLoanController/getProjectAssureByPersonal.action?projectId='+newProjectId+'&oldProject='+projectId,
				    method: 'post',
				    rownumbers: true,
				    singleSelect: false,
				    toolbar: '#toolbar_news_personalGuarantee',
		    		fitColumns:true,
				    idField: 'pid'
			    ">
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true" ></th>
						<th data-options="field:'chinaName'" width="8%" align="center" halign="center" >姓名</th>
						<th data-options="field:'sexText'" width="8%" align="center" halign="center" >性别</th>
						<th data-options="field:'certTypeText'" width="8%" align="center" halign="center" >客户关系</th>
						<th data-options="field:'mobilePhone'" width="15%" align="center" halign="center" >电话</th>
						<th data-options="field:'telephone'" width="15%" align="center" halign="center" >手机</th>
						<th data-options="field:'occupation'" width="10%" align="center" halign="center" >职业</th>
						<th data-options="field:'workUnit'" width="15%" halign="center" >单位</th>
						<th data-options="field:'unitPhone'" width="15%" halign="center" >单位电话</th>
						<th data-options="field:'unitAddr'" width="20%" halign="center" >单位地址</th>
					</tr>
				</thead>
		   	</table>
  		</div>
	</div><%-- end 个人保证信息DIV --%>
	
	
	<%-- begin 法人保证信息DIV --%>
	<div id="corporationGuaranteeNews">
	
		<h4>法人保证信息：</h4>
		<div id="toolbar_news_corporationGuarantee" class="easyui-panel" style="border-bottom: 0;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openAddPerGuarantee(2)">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="deleteProjectAssure(2)">删除</a>
		</div>
		<div id="corporationGuarantee_div" style="padding-left: 20px;" >
			<table id="corGuarantee_grid" class="easyui-datagrid"  
		    	style="height: auto;width: auto;"
			 	data-options="
			    url: '<%=basePath%>secondBeforeLoanController/getProjectAssureByEnterprise.action?projectId='+newProjectId+'&oldProject='+projectId,
			    method: 'post',
			    rownumbers: true,
			    singleSelect: false,
			    toolbar: '#toolbar_news_corporationGuarantee',
	    		fitColumns:true,
			    idField: 'pid'
			    ">
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true" ></th>
						<th data-options="field:'cpyName'" width="15%" align="center" halign="center" >企业名称</th>
						<th data-options="field:'orgCode'" width="15%" align="center" halign="center" >组织机构代码</th>
						<th data-options="field:'busLicCert'" width="10%" align="center" halign="center" >营业执照号</th>
						<th data-options="field:'comAllNatureText'" width="10%" align="center" halign="center" >所有制性质</th>
						<th data-options="field:'comOwnName'" width="8%" align="center" halign="center" >法人代表</th>
						<th data-options="field:'regMoney'" width="15%" align="center" halign="center" >注册资金（万元）</th>
						<th data-options="field:'cusTelephone'" width="15%" align="center" halign="center" >联系电话</th>
						<th data-options="field:'foundDate',formatter:convertDate" width="15%" align="center" halign="center" >企业成立日期</th>
					</tr>
				</thead>
		   	</table>
  		</div>
	</div><%-- end 法人保证信息DIV --%>
			
	
	<div id="dlg_buttons_dzywxx">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDzyw()">保存</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_dzywxx').dialog('close')">关闭</a>
	</div>
	<%-- 新增or编辑抵质押物信息 --%>
	<div id="dlg_dzywxx" class="easyui-dialog" fitColumns="true"  buttons="#dlg_buttons_dzywxx" 
	 	style="width:850px;height:480px;padding: 10px;" data-options="modal:true" closed="true"  >
		<h4>填写抵质押物基础信息</h4>
		<hr />
		<form id="dzywxxForm" method="post" >
		<input type="hidden" name="pid" value="-1"/>
		<input type="hidden" name="mortgageGuaranteeType" />
		<input type="hidden" name="projectId" />
		<input type="hidden" name="content" />
		<table id="table_dzywxx">
			<tr>
				<td class="label_right"><font color="red">*</font>权证编号：</td>
				<td colspan="3">
					<input name="warrantsNumber" style="width: 240px;"  class="easyui-textbox" required="true" missingMessage="请输入权证编号!"  />
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>抵质押物类型：</td>
				<td>
					<input name="mortgageType" class="easyui-combobox" style="width: 180px;" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_MORTGAGE_TYPE',onSelect:getAssDtl" />
				</td>
				<td class="label_right"><font color="red">*</font>所有人类型：</td>
				<td>
					<input name="ownType" id="ownType" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE',onSelect:changeOwnType" />
					<input id="ownTypeHidden" type="hidden" value="-1" />
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>所有权人：</td>
				<td>
					<input name="ownNameText" id="ownNameText" style="width: 130px" class="easyui-textbox" required="true" readonly="readonly"  />
					<input name="ownName" type="hidden">
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="openAcctDiv()">选择客户</a>
				</td>
				<td class="label_right"><font color="red">*</font>共有人：</td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="openOwnItem()">查看共有人</a>
					<input name="owns" id="owns"  type="hidden">
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>物件地点：</td>
				<td colspan="3">
					<select class="select_style" style="border:1px solid #95b8e7;width: 80px;" id="addressProvince" name="addressProvince">
						<option value="-1">请选择</option>
					</select>
					<select class="select_style" style="border:1px solid #95b8e7;width: 120px;" id="addressCity" name="addressCity">
						<option value="-1">请选择</option>
					</select>
					<select class="select_style" style="border:1px solid #95b8e7;width: 100px;" id="addressArea" name="addressArea">
						<option value="-1">请选择</option>
					</select>
					<input class="easyui-textbox" name="addressDetail" style="width: 200px;" required="true" missingMessage="请输入物件的详细地点!"  />
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>物件名称：</td>
				<td colspan="3">
					<input name="itemName" class="easyui-textbox" style="width: 600px;" required="true" missingMessage="请输入物件名称!" />
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>用途：</td>
				<td>
					<input name="purpose" class="easyui-textbox" required="true" missingMessage="请输入物件用途!"  />
				</td>
				<td class="label_right"><font color="red">*</font>登记时间：</td>
				<td>
					<input name="regDt"  class="easyui-datebox" required="true" editable="false" missingMessage="请录入登记时间!"  />				
				</td>
			</tr>
			<tr>
				<td class="label_right"><font color="red">*</font>登记价（元）：</td>
				<td>
					<input name="regPrice" class="easyui-numberbox" precision="2" required="true" groupSeparator="," missingMessage="请输入登记价!"  />
				</td>
				<td class="label_right"><font color="red">*</font>评估净值（元）：</td>
				<td>
					<input name="assessValue" id="assessValue" data-options="onChange:applyCommon.mortgageValueInfo"  class="easyui-numberbox" precision="2" required="true" groupSeparator="," missingMessage="请录入评估净值!"  />				
				</td>
			</tr>
			<tr>
				<td class="label_right">抵质押价值（元）：</td>
				<td>
					<input name="mortgageValue" class="easyui-numberbox" precision="2" groupSeparator=","  />
				</td>
				<td class="label_right">抵质押率：</td>
				<td>
					<input name="mortgageRate" id="mortgageRate" class="easyui-numberbox" precision="4"  /> %				
				</td>
			</tr>
			<tr>
				<td class="label_right">公允价值（元）：</td>
				<td>
					<input name="fairValue" class="easyui-numberbox" precision="2" groupSeparator=","  />
				</td>
				<td class="label_right">公允价值获取方式：</td>
				<td>
					<input name="fairValueGetMethod" class="easyui-textbox" />				
				</td>
			</tr>
			<tr>
				<td class="label_right">备注：</td>
				<td colspan="3">
					<input name="remark" class="easyui-textbox" style="width:600px;height:60px" data-options="multiline:true" />			
				</td>
			</tr>
			
		</table>
		<div id="mortgageTypeDiv">
			
		</div>
		
		</form>
	</div><%-- end 抵押信息 --%>
	
	
	<%-- 新增or编辑个人保证信息 --%>
	<div id="dlg-buttons_personalGuarantee">		
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveProjectAssure(1,newProjectId)">选择</a>
	</div>
	<div id="dlg_personalGuarantee" class="easyui-dialog" fitColumns="true"  title="个人客户保证人查询"
	     style="width:850px;" data-options="modal:true" buttons="#dlg-buttons_personalGuarantee" closed="true" >
	    <table id="personalGuarantee_grid" class="easyui-datagrid"  
	    	style="height: 300px;width: auto;"
		 	data-options="
		    url: '<%=basePath%>customerController/getAllCusPerson.action',
		    pageSize: 10,
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
	    	pagination: true,
		    toolbar: '#toolbar_personalGuarantee',
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'chinaName'" >姓名</th>
					<th data-options="field:'sexText'" >性别</th>
					<th data-options="field:'certTypeText'" >证件类型</th>
					<th data-options="field:'certNumber'" >证件号号码</th>
					<th data-options="field:'workUnit'" >工作单位</th>
					<th data-options="field:'relationTypeText'" >客户关系</th>
					<th data-options="field:'realName'" >客户经理</th>
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_personalGuarantee">
	        <form method="post" action="<%=basePath%>customerController/getAllCusPerson.action"  id="searchFromPersonalGuarantee" name="searchFromPersonalGuarantee"  style="padding: 0px 0px;">
	     		<div style="margin:5px">
				 	<table class="beforeloanTable">
				 		<tr>
				 			<td class="label_right">姓名：</td>
				 			<td><input name="chinaName" id="chinaName" class="easyui-textbox" /></td>
				 			<td class="label_right">证件类型：</td>
				 			<td colspan="2"><input name="certType" id="certType" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CERT_TYPE'" /></td>
				 		</tr>
				 		<tr>
				 			<td class="label_right">性别：</td>
				 			<td><input name="sex" id="sex" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=SEX'" /></td>
				 			<td class="label_right">证件号码：</td>
				 			<td><input name="certNumber" id="certNumber" class="easyui-textbox" /></td>
				 			<td>
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#personalGuarantee_grid','#searchFromPersonalGuarantee')">查询</a>
								<a href="#" onclick="javascript:$('#searchFromPersonalGuarantee').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
				 			</td>
				 		</tr>
				 	</table>				 		
        		</div> 
		 	</form>
	   </div>
	</div>
	<%-- end 个人保证信息 --%>
	
	<%-- 自定义公共的提交form --%>
 	<form id="customProjectAssure" action="<%=basePath%>secondBeforeLoanController/addProjectAssure.action" method="post">
 		<input type="hidden" name="refIds" />
 		<input type="hidden" name="assureType" />
 		<input type="hidden" name="projectId" />
 	</form>
	
	<%-- 新增or编辑法人保证信息 --%>
	<div id="dlg-buttons_corporationGuarantee">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveProjectAssure(2,newProjectId)">选择</a>
	</div>
	<%-- 企业法人选取 --%>
	<div id="dlg_corporationGuarantee" class="easyui-dialog" fitColumns="true"  title="法人保证查询"
	     style="width:850px;" data-options="modal:true" buttons="#dlg-buttons_corporationGuarantee"  closed="true" >
	    <table id="corporationGuarantee_grid" class="easyui-datagrid"  		   
	    	style="height: 300px;width: auto;" 	
		 	data-options="
		    url: '<%=basePath%>customerController/getEnterpriseLegalPersonList.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
	    	pagination: true,
		    toolbar: '#toolbar_corporationGuarantee',
		    fitColumns:true,
		    idField: 'acctId'
		    ">
			<thead>
				<tr>
					<th data-options="field:'acctId',checkbox:true" ></th>
					<th data-options="field:'cpyName'" >企业名称</th>
					<th data-options="field:'orgCode'" >组织机构代码</th>
					<th data-options="field:'busLicCert'" >营业执照号</th>
					<th data-options="field:'comAllNatureText'" >所有制性质</th>
					<th data-options="field:'chinaName'" >法人代表</th>
					<th data-options="field:'cusRegMoney'" >注册资金（万元）</th>
					<th data-options="field:'cusTelephone'" >联系电话</th>
					<th data-options="field:'cusFoundDate',formatter:convertDate" >企业成立日期</th>
					<th data-options="field:'cusStatusVal'" >企业状态</th>	
					<th data-options="field:'realName'" >客户经理</th>	
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_corporationGuarantee">
	        <form method="post" action="<%=basePath%>customerController/getEnterpriseLegalPersonList.action"  id="searchFromCorporationGuarantee" name="searchFromCorporationGuarantee"  style="padding: 0px 0px;">
	     		<div style="margin:5px">
				 	<table class="beforeloanTable">
				 		<tr>
				 			<td class="label_right">客户名称：</td>
				 			<td><input name="cpyName" id="cpyNamemm" class="easyui-textbox" /></td>
				 			<td class="label_right">经济行业类别：</td>
				 			<td><input name="ecoTrade" id="ecoTrademm" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
				 			<td>
				 				<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearchPage('#corporationGuarantee_grid','#searchFromCorporationGuarantee')">查询</a>
								<a href="#" onclick="javascript:$('#searchFromCorporationGuarantee').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
				 			</td>
				 		</tr>
				 	</table>
			 		
        		</div> 
		 	</form>
	   </div>
	</div>	
	<%-- end 法人保证信息 --%>
				
	<%-- begin 客户选取 --%>
	<div id="dlg-buttons_acct">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveOwnName()">选择</a>
	</div>
	<div id="dlg_acct" class="easyui-dialog" fitColumns="true"  title="客户选取"
     style="width:720px;" data-options="modal:true" buttons="#dlg-buttons_acct" closed="true" >
	    <table id="acct_grid" class="easyui-datagrid"  		   
	    	style="height: 320px;width: auto;" 	
		 	data-options="
		    url: '<%=basePath%>customerController/getAllAcct.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: true,
	    	pagination: true,
		    toolbar: '#toolbar_acct',
		    fitColumns:true,
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'acctName'" width="60%" halign="center" align="center" >客户名称</th>
					<th data-options="field:'acctTypeText'" width="25%" halign="center" align="center" >客户类别</th>
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_acct">
	        <form method="post" action="<%=basePath%>customerController/getAllAcct.action"  id="searchFromAcct" name="searchFromAcct"  style="padding: 0px 0px;">
	     		<div style="margin:5px">
				 	<table class="beforeloanTable" style="width: 640px;">
				 		<tr>
				 			<td class="label_right">客户名称：</td>
				 			<td colspan="2"><input name="acctName" id="acctName" class="easyui-textbox" style="width: 280px;"/></td>
				 		</tr>
				 		<tr>
				 			<input name="cusType" id="cusType" type="hidden" />
				 			<td colspan="2">
					 			<div class="ecoTradeId100">
					 				<table>
										<tr>
								 			<td class="label_right">经济行业类别：</td>
								 			<td><input name="ecoTrade" id="ecoTradeGYR" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
					 					</tr>
					 				</table>
					 			</div>
				 			</td>
				 			<td  style="width: 200px;">
				 				<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearchPage('#acct_grid','#searchFromAcct')">查询</a>
								<a href="#" onclick="javascript:$('#searchFromAcct').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
				 			</td>
				 		</tr>
				 	</table>
        		</div> 
		 	</form>
	   </div>
	</div>
	<%-- end 所有权人选取 --%>
	
	<%-- begin 共有人列表 --%>
	<div id="toolbar_own" class="easyui-panel" style="border-bottom: 0;">
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="openAddOwnItem()">新增共有人</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeAssOwn()">删除</a>
		</div>
	</div>
	<div id="dlg_own" class="easyui-dialog" fitColumns="true"  title="查看共有人"
     style="width:720px;padding: 10px;" data-options="modal:true"  buttons="#toolbar_buttons_own" closed="true" >
		<h4>查看设定共有人信息</h4>
		<hr />
	    <table id="acct_set_own_grid" class="easyui-datagrid"  		   
	    	style="height: 320px;width: auto;" 	
		 	data-options="
		    url: '',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
		    toolbar: '#toolbar_own',
		    fitColumns:true,
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'acctName'" width="40%" halign="center" align="center" >客户名称</th>
					<th data-options="field:'acctTypeText'" width="10%" halign="center" align="center" >客户类别</th>
					<th data-options="field:'certType'" width="20%" halign="center" align="center" >证件类型</th>
					<th data-options="field:'certNumber'" width="20%" halign="center" align="center" >证件号码</th>
				</tr>
			</thead>
	   	</table>
   		<div id="toolbar_buttons_own">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_own').dialog('close')">关闭</a>
	   	</div>
	</div>
	<%-- end 共有人列表 --%>

	
	<%-- begin 查询公有客户列表 --%>
	<div id="dlg-buttons_acct_own">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="selectAssOwn()">选择</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_acct_own').dialog('close')">关闭</a>
	</div>
	<div id="dlg_acct_own" class="easyui-dialog" fitColumns="true"  title="共有人选取"
     style="width:720px;" data-options="modal:true" buttons="#dlg-buttons_acct_own" closed="true" >
	    <table id="acct_own_grid" class="easyui-datagrid"  		   
	    	style="height: 320px;width: auto;" 	
		 	data-options="
		    url: '<%=basePath%>mortgageController/getAllProjectAssOwnByOwnType.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
	    	pagination: true,
		    toolbar: '#toolbar_acct_own',
		    fitColumns:true,
		    idField: 'pid'
		    ">
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true" ></th>
					<th data-options="field:'acctName'" width="40%" halign="center" align="center" >客户名称</th>
					<th data-options="field:'acctTypeText'" width="10%" halign="center" align="center" >客户类别</th>
					<th data-options="field:'certType'" width="20%" halign="center" align="center" >证件类型</th>
					<th data-options="field:'certNumber'" width="20%" halign="center" align="center" >证件号码</th>
				</tr>
			</thead>
	   	</table>
		<div id="toolbar_acct_own">
	        <form method="post" action="<%=basePath%>mortgageController/getAllProjectAssOwnByOwnType.action"  id="searchFromAcctOwn" name="searchFromAcctOwn"  style="padding: 0px 0px;">
	     		<div style="margin:5px">
				 	<table class="beforeloanTable" style="width: 640px;">
				 		<tr>
				 			<td class="label_right">客户名称：</td>
				 			<td colspan="2"><input name="acctName" id="acctName" class="easyui-textbox" style="width: 410px;"/></td>
				 		</tr>
				 		<tr>
				 			<input name="cusType" type="hidden" />
				 			<td colspan="2">
					 			<div class="ecoTradeId100">
					 				<table>
										<tr>
								 			<td class="label_right">经济行业类别：</td>
								 			<td><input name="ecoTrade" id="ecoTradeGYR" class="easyui-combobox" panelHeight="auto" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
					 					</tr>
					 				</table>
					 			</div>
				 			</td>
				 			<td  style="width: 200px;">
				 				<a href="#" class="easyui-linkbutton" iconCls="icon-search"  onclick="ajaxSearchPage('#acct_own_grid','#searchFromAcctOwn')">查询</a>
								<a href="#" onclick="javascript:$('#searchFromAcctOwn').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
				 			</td>
				 		</tr>
				 	</table>
        		</div> 
		 	</form>
	   </div>
	</div>
	<%-- end 客户列表 --%>
	
	<!-- begin 查看抵质押物详情 -->
	<%-- 查看抵质押物详细信息 --%>
	<%@ include file="../common/common_mortgage.jsp" %>		
	<%-- 上传附件资料 --%>
	<%@ include file="../mortgage/mortgage_file.jsp" %>
	<!-- end 查看抵质押物详情 -->
	
	<script type="text/javascript">
		
		// 所有权人改变事件
		function changeOwnType(rec){
			var oldValue = $("#ownTypeHidden").val();
			// 判断类型,显示或关闭经纪行业类别
			if(rec.pid == 1){
				$(".ecoTradeId100").hide();
			}else{
				$(".ecoTradeId100").show();
			}
			// 如果是从 请选择 操作,直接退出
			if(oldValue == -1){;
				// 重新赋值所有权人类型的隐藏域
				$("#ownTypeHidden").val(rec.pid);
				return;
			}
			$.messager.confirm("操作提示","确认切换所有人类型吗？会重置你所选择的所有权人和共有人信息！",
				function(r) {
	 				if(r){
	 					// 判断是什么操作
	 					if($("#dzywxxForm input[name='pid']").val() != -1){
	 						// 如果是编辑操作,就删除当前所选择的抵质押物的所有共有人
	 						$.post("<%=basePath%>mortgageController/deleteProjectAssOwn.action",{baseId : $("#dzywxxForm input[name='pid']").val()}, 
	 								function(ret) {
	 								},'json');
	 					}else{
	 						// 如果是新增操作,直接把共有人属性设置为空
	 						$("#owns").val("");
	 					}
							// 只要改变了,就清空所选取的所有权人信息
							$("#ownNameText").textbox("setValue","");
							$("#dzywxxForm input[name='ownName']").val("");
							// 重新赋值所有权人类型的隐藏域
							$("#ownTypeHidden").val(rec.pid);
							$.messager.alert('操作提示',"已经重置你所选择的所有权人和共有人信息,请重新填写！");
	 				}else{
	 					// 重新赋值
							$("#ownType").combobox('setValue',oldValue);
	 				}
			});
		}

		// 客户选取
		function openAcctDiv(){
			// 判断是否选取所有权人 
	 		if($("#dzywxxForm input[name='ownType']").val() == -1){
	 			$.messager.alert('操作提示',"请先选择所有人类型,再选取客户!",'error');
	 			return;
	 		}
			// 重置form数据
			$("#searchFromAcct").form('reset');
			$("#cusType").val($("#dzywxxForm input[name='ownType']").val());
			// 打开窗口
			$('#dlg_acct').dialog('open').dialog('setTitle', "客户选取");
			//$("#dlg_acct").dialog("move",{top:$(document).scrollTop()+((window.screen.availHeight-134)*0.5-($("#dlg_acct").parent().height()+12)*0.5)});
			ajaxSearchPage('#acct_grid','#searchFromAcct');
		}
		
		// 所有权人获取 
		function saveOwnName(){
			// 获取选中的数据
			var row = $("#acct_grid").datagrid('getSelected');
			// 判断是否选中
			if(!row){
				$.messager.alert('操作提示',"请选择数据",'error');
				return;
			}
			// 赋值
			$("#ownNameText").textbox("setValue",row.acctName);
			$("#dzywxxForm input[name='ownName']").val(row.pid);
			// 调用清除选中数据的函数
			clearSelectRows("#acct_grid");
			// 关闭窗口
			$('#dlg_acct').dialog('close');
		}
	
	 	// 保存抵质押物
	 	function saveDzyw(){
	 		// 判断是否选取抵质押物类型
	 		if($("#dzywxxForm input[name='mortgageType']").val() == -1){
	 			$.messager.alert('操作提示',"请选择抵质押物类型,请填写!",'error');
	 			return;
	 		}
	 		// 判断是否选取所有权人类型
	 		if($("#dzywxxForm input[name='ownType']").val() == -1){
	 			$.messager.alert('操作提示',"请选择所有人类型,请填写!",'error');
	 			return;
	 		}
	 		// 判断物件地点:省份是否选择
	 		if($("#addressProvince").val() == "请选择"){
	 			$.messager.alert('操作提示',"请选择物件地点的省份!",'error');
	 			return;
	 		}
	 		// 判断午间地点:城市,直辖市(区的选择)
	 		if($("#addressCity").val() == "请选择"){
	 			$.messager.alert('操作提示',"请选择物件地点的城市或直辖市的区!",'error');
	 			return;
	 		}
	 		// 建筑面积
	 		var bool = false;
	 		// 动态参数
	 		var content = "";
	 		//  获取动态生成的数据
	 		$("#mortgageTypeDiv").find("input").each(function (i){
				var key = $(this).attr("id");
				var value = $(this).val();
				var flag = $(this).attr("name");
				// 判断:建筑面积是否填写
				if($(this).attr("title") == "*建筑面积.m2" && (null == value || value == "")){
					bool = true;
					return;
				}
				// 判断:单位是否填写
				if($(this).attr("title") == "单位" && (null == value || value == "")){
					bool = true;
					return;
				}
				content = content + key + ":" + value + ":" + flag + ",";
			});
			// 判断是否退出方法
	 		if(bool){
	 			$.messager.alert('操作提示',"必须填写建筑面积和单位信息,请输入!",'error');
				return;
	 		}
	 		// 赋值抵质押物详细信息
	 		$("#dzywxxForm input[name='content']").val(content);
	 		// 赋值项目ID--抵质押物新增
	 		$("#dzywxxForm input[name='projectId']").val(newProjectId);
	 		// 判断是执行什么操作
	 		var urlDzyw = "<%=basePath%>mortgageController/addProjectAssBase.action";
	 		if($("#dzywxxForm input[name='pid']").val() != -1){
	 			urlDzyw = "<%=basePath%>mortgageController/updateProjectAssBase.action";
	 		}
	 		$("#dzywxxForm").form('submit', {
	 			url : urlDzyw,
	 			onSubmit : function() {return $(this).form('validate');},
	 			success : function(result) {
	 				var ret = eval("("+result+")");
	 				if(ret && ret.header["success"]){
	 					// 关闭窗口
	 					$('#dlg_dzywxx').dialog('close');
	 					// 操作提示
	 					$.messager.alert('操作提示',ret.header["msg"]);
	 					// 刷新datagrid
	 					if($("#dzywxxForm input[name='mortgageGuaranteeType']").val() == 1){
	 						// 重新加载抵押信息
	 						$("#dyxx_grid").datagrid('load');
	 						// 清除datagrid的函数
	 						clearSelectRows("#dyxx_grid");
	 					}else{
	 						// 重新加载质押信息
	 						$("#zyxx_grid").datagrid('load');
	 						// 清除datagrid的函数
	 						clearSelectRows("#zyxx_grid");
	 					}
	 					// 重置form数据
	 					$("#dzywxxForm").form('reset');
	 				}else{
	 					$.messager.alert('操作提示',ret.header["msg"],'error');
	 				}
	 			}
	 		});
	 	}
	  
 	</script>
</body>