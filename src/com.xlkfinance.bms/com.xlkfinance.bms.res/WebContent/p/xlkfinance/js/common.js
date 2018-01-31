
var checkInfo =1;
var layout_center_tabs = $('#layout_center_tabs').length ?  $('#layout_center_tabs') :  $('#layout_center_tabs',parent.document);
/**
 * 刷新 Tab
*/
function layout_center_refreshTab(title) {
	$('#layout_center_tabs').tabs('getTab', title).panel('refresh');
}
/**
* 添加 Tab
*/
function layout_center_addTabFun(opts) {
	if(opts.falg){
		$('#layout_center_tabs').tabs('close',opts.title);//tab
		$('#layout_center_tabs').tabs('add', opts);//tab
	}else{
		if ($('#layout_center_tabs').tabs('exists', opts.title)) {
			$('#layout_center_tabs').tabs('select', opts.title);
		} else {
			$('#layout_center_tabs').tabs('add', opts);//tab
		}
	}
}
/**
* 刷新我的任务列表
*/
function reload_My_Agent_Task_grid(){
var c = $('#my_Agent_Task_grid');
var d = $('#layout_center_tabs');
$('#my_Agent_Task_grid').datagrid('reload');
}
$(function(){
	$.extend($.fn.validatebox.defaults.rules, {
		checkDateRange: {
			validator: function(value,param){
				return value > $(param[0]).datebox('getValue');
			},
			message: '结束时间必须大于开始时间.'
		},
		checkDateEqualRange: {
			validator: function(value,param){
				return value >= $(param[0]).datebox('getValue');
			},
			message: '结束时间必须大于等于开始时间.'
		}
	});
	$(".DateTime").focus(function(){
		new WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'});
	});
	$(".Date").click(function(){
		new WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'});
	});
	/* $("form :input[type='text']").each(function(i) {
	    if( $(this).val()==0 ||$(this).val()==0.0||$(this).val()==0.00){
	 	   $(this).val("");
	    };       
	});*/
	/*if(checkInfo==1){//利率为0时做判断 让界面能正常显示0
		$(".easyui-numberbox").each(function(i) {
			var v=$(this).numberbox('getValue');
		    if( v=='0' ||v=='0.0'||v=='0.00'||v=='0.0000'){
		    	$(this).numberbox('setValue','');
		    };       
		}); 
	}*/
	checkValueScript();//过滤输入内容
});

//绑定下拉框的值
function loadSel(selId,selVal){
	$("#"+selId).combobox('setValue',selVal);
}
//下拉框请选择值为-1
function setCombobox(inputId,lookupType,selVal){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType='+lookupType,    
	    valueField:'pid',    
	    textField:'lookupDesc',
	    value:'-1',
	    onLoadSuccess: function(rec){
	    	if(selVal!='' && selVal!='0' && selVal!='-1'){
	    		loadSel(inputId,selVal);
	    	}
        }
	});  
}

//下拉框请选择值为空
function setCombobox3(inputId,lookupType,selVal){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType='+lookupType,    
		valueField:'lookupVal',    
		textField:'lookupDesc',
		filter: function(q, row){//模糊匹配
			var opts = $(this).combobox('options');
			//只要输入的内容，即可匹配
			return row[opts.textField].indexOf(q) >-1;
		},
		onLoadSuccess: function(rec){
			if(selVal!='' && selVal!='0' && selVal!='-1'){
				loadSel(inputId,selVal);
			}
		}
	});  
}

//设置账号下拉框
function setAccountCombobox(inputId,levelNo,accountValue,selVal){
	$('#'+inputId).combobox({
		url:BASE_PATH+'sysLookupController/getSysLookupValByPid.action?pid='+accountValue,    
	    valueField:'lookUpVal',
        textField:'lookUpVal',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,rec);
			}else{
				loadSel(inputId,"--请选择--");
			}
		}
	}); 
}
//根据text绑定选项
function setComboboxByText(inputId,lookupType,selText){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType='+lookupType,    
		valueField:'lookupVal',    
		textField:'lookupDesc',
		filter: function(q, row){//模糊匹配
			var opts = $(this).combobox('options');
			//只要输入的内容，即可匹配
			return row[opts.textField].indexOf(q) >-1;
		},
		onLoadSuccess: function(rec){
			for (var i = 0; i < rec.length; i++) {
				var lookupVal=rec[i].lookupVal;
				var lookupDesc=rec[i].lookupDesc;
				if (selText==lookupDesc) {
					loadSel(inputId,lookupVal);
					break;
				}
			}
		}
	});  
}

function setRelationCombobox(inputId,lookupType,selVal){
	$('#'+inputId).combobox({
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType=RELATION',
        method:'get',
        valueField:'lookupVal',
        textField:'lookupDesc',
        loadFilter:function(data){
        	var bbc = new Array();
		 	for(var i=0;i<data.length;i++){
		 		if(parseInt(data[i].lookupVal)>1 || data[i].pid==-1){
		 			if(data[i].lookupVal==""){
		 				data[i].lookupVal=-1;
		 			}
		 			bbc.push(data[i]);
		 		}
		 	}
		 	$("#"+inputId).combobox('setValue',selVal);
		 	return bbc;
		}
	});
}
//总行数据下拉框
function setBankCombobox(inputId,parentId,selVal){
	if(isNaN(parentId)){
		parentId = 0;
	}
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysBankInfoController/getAllBankInfo.action?parentId='+parentId,    
		valueField:'pid',    
		textField:'bankName',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}else{
				loadSel(inputId,-1);
			}
		}
	}); 
}
//设置银行下拉框，key-value =名称
function setBankCombobox2(inputId,parentId,selVal){
	if(isNaN(parentId)){
		parentId = 0;
	}
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysBankInfoController/getAllBankInfo.action?parentId='+parentId,    
		valueField:'bankName',    
		textField:'bankName',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}else{
				loadSel(inputId,-1);
			}
		}
	}); 
}


//设置地区下拉框
function setAreaCombobox(inputId,levelNo,parentCode,selVal){
	if(isNaN(levelNo)){
		levelNo = "1";
	}
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysAreaInfoController/getSysAreaInfo.action?levelNo='+levelNo+"&parentCode="+parentCode,    
		valueField:'areaCode',
		textField:'areaName',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}else{
				loadSel(inputId,"--请选择--");
			}
		}
	}); 
}



////设置地区下拉框
function setAreaCombobox(inputId,levelNo,parentCode,selVal){
	if(isNaN(levelNo)){
		levelNo = "1";
	}
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysAreaInfoController/getSysAreaInfo.action?levelNo='+levelNo+"&parentCode="+parentCode,    
		valueField:'areaCode',    
		textField:'areaName',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}else{
				loadSel(inputId,"--请选择--");
			}
		}
	}); 
}

//贷前申请列表 城市设置 根据机构Id
function setApplyCityCombobox(inputId,orgId,selVal){
	$('#'+inputId).combobox({
		url:BASE_PATH+'orgCooperatCompanyApplyController/getCityByOrgId.action?orgId='+orgId,    
		valueField:'areaCode',    
		textField:'cityName',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}else{
				loadSel(inputId,"--请选择--");
			}
		}
	}); 
}
function setAreaCombobox(inputId,levelNo,parentCode,selVal){
	if(isNaN(levelNo)){
		levelNo = "1";
	}
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysAreaInfoController/getSysAreaInfo.action?levelNo='+levelNo+"&parentCode="+parentCode,    
		valueField:'areaCode',    
		textField:'areaName',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}else{
				loadSel(inputId,"--请选择--");
			}
		}
	}); 
}

//封装下拉业务员  luozhonghua  BIZ_DIRECTOR
function setSalesList(inputId,roleCode,selVal){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'systemRoleController/findUsersByRoleCode.action?roleCode='+roleCode,    
	    valueField:'pid',    
	    textField:'realName',
	    value:'-1',
	    onLoadSuccess: function(rec){
	    	if(selVal!='' && selVal!='0' && selVal!='-1'){
	    		loadSel(inputId,selVal);
	    	}
        }
	});  
}

//下拉框请选择值为0
function setCombobox2(inputId,lookupType,selVal){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'sysLookupController/getSysLookupValByLookTypeThree.action?lookupType='+lookupType,    
	    valueField:'pid',    
	    textField:'lookupDesc',
	    value:'0',
	    onLoadSuccess: function(rec){
	    	if(selVal!='' && selVal!='0' && selVal!='-1'){
	    		loadSel(inputId,selVal);
	    	}
        }
	});  
}

//下拉框请选择值为0
function setCapitalOrgInfo(inputId,selVal){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'capitalOrgInfoController/getAllCapitalOrg.action',    
	    valueField:'orgCode',    
	    textField:'orgName',
	    value:'0',
	    onLoadSuccess: function(rec){
	    	if(selVal!='' && selVal!='0' && selVal!='-1'){
	    		loadSel(inputId,selVal);
	    	}
        }
	});  
}
//下拉框请选择值为0
function setCapitalOrgInfo2(inputId,selVal){
	$('#'+inputId).combobox({    
		url:BASE_PATH+'capitalOrgInfoController/getAllCapitalOrg.action',    
		valueField:'orgName',    
		textField:'orgName',
		value:'',
		onLoadSuccess: function(rec){
			if(selVal!=''){
				loadSel(inputId,selVal);
			}
		}
	});  
}

function center_addTab(opts) {
	var t = $('#layout_center_tabs');
	if (t.tabs('exists', opts.title)) {
		t.tabs('select', opts.title);
	} else {
		t.tabs('add', opts);//tab
	}
}

//添加tab
function addTab(url,title){
	
	center_addTab({
		title : title, //tab的名称
		closable : true, //是否显示关闭按钮
		//iconCls : node.iconCls,//图标
		content : '<iframe src="'
				+ url
				+ '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>' //加载链接
	});
}


/**
 * 包含easyui的扩展和常用的方法
 * 
 * @author zh
 */
//搜索条件中日期输入的比较，后面的日期 要比前面的日期大  by zhengxiu
function compareDate(beginDt,endDt){
	var starTime=$('#'+beginDt).datebox('getValue');
	var endTime=$('#'+endDt).datebox('getValue');
	
	if(starTime>endTime){
		$('#'+endDt).datebox('setValue','');
		$.messager.alert("提示",'请选择正确的日期范围');
		return false;
	}
	return true;
}
//得到当前日期
function formatterDate(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
};
/** 
 * 计算2个日期相差的天数，包含今天，如：2016-12-13到2016-12-15，相差3天 
 * @param startDateString 
 * @param endDateString 
 * @returns 
 */  
function dateDiffIncludeToday(startDateString, endDateString){  
    var separator = "-"; //日期分隔符  
    var startDates = startDateString.split(separator);  
    var endDates = endDateString.split(separator);  
    var startDate = new Date(startDates[0], startDates[1]-1, startDates[2]);  
    var endDate = new Date(endDates[0], endDates[1]-1, endDates[2]);  
    if (endDate<startDate) {
    	return parseInt(Math.abs(endDate - startDate ) / 1000 / 60 / 60 /24)*-1;//把相差的毫秒数转换为天数   
	}else{
		return parseInt(Math.abs(endDate - startDate ) / 1000 / 60 / 60 /24) + 1;//把相差的毫秒数转换为天数   
	}
};  
function stripValueScript(s) 
{ 
	var pattern = new RegExp("[`\\[\\]<>]") ;       //格式 RegExp("[在中间定义特殊过滤字符]")
	var rs = ""; 
	for (var i = 0; i < s.length; i++) { 
	 rs = rs+s.substr(i, 1).replace(pattern, ''); 
	}
	return rs;
}
$.fn.textbox.defaults.formatter = function (v) {
	 return stripValueScript(v);
   };
function checkValueScript(){
	$('body').delegate('textarea','blur',function(){
		var v=stripValueScript($(this).val());
		$(this).val(v);
	})
}
//datagrid 清除√ 
function dataGridClearSelections(id)
 {
	 $(id).datagrid('clearSelections');
 }

var sy = $.extend({}, sy);/* 全局对象 */

$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
	} catch (e) {
	}	
};

$.fn.panel.defaults.loadingMessage = '数据正在努力为您加载，请稍候....';
$.fn.datagrid.defaults.loadMsg = '数据正在努力为您加载，请稍候....';

var easyuiErrorFunction = function(XMLHttpRequest) {
	/* $.messager.progress('close'); */
	/* alert(XMLHttpRequest.responseText.split('<script')[0]); */
	//$.messager.alert('错误', XMLHttpRequest.responseText.split('<script')[0]);
	$.messager.alert('提示框', '后台错误请与管理员联系!','warning',function(){
		//document.location = "${pageContext.request.contextPath}/logout.do?method=logout";
		});
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;


 $.extend($.fn.datagrid.defaults.editors, {
     datetimebox: {//datetimebox就是你要自定义editor的名称
         
         	init : function(container, options) {
			var editor = $('<input/>').appendTo(container);
			editor.datetimebox(options);
			return editor;
		},
		destroy : function(target) {
			$(target).datetimebox('destroy');
		},
		getValue : function(target) {
			return $(target).parent().find('input.combo-value').val();
		},
		setValue : function(target, value) {
			$(target).datetimebox("setValue",value);
		},
		resize : function(target, width) {
			$(target).datetimebox('resize', width);
		}
		
     }
});

$.extend($.fn.datagrid.defaults.editors, {
	combocheckboxtree : {
		init : function(container, options) {
			var editor = $('<input/>').appendTo(container);
			options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues', sy.getList(value));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	}
});

/**
 * 获得项目根路径
 * 
 * 使用方法：sy.bp();
 */
sy.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * 获得项目名称
 * 
 * 使用方法：sy.bp();
 */
sy.bpName = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (projectName);
};

/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sy.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sy.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 生成UUID
 */
sy.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
sy.UUID = function() {
	return (sy.random4() + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + sy.random4() + sy.random4());
};

/**
 * 获得URL参数
 */
sy.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

sy.getList = function(value) {
	if (value) {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

sy.png = function() {
	var imgArr = document.getElementsByTagName("IMG");
	for ( var i = 0; i < imgArr.length; i++) {
		if (imgArr[i].src.toLowerCase().lastIndexOf(".png") != -1) {
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imgArr[i].src + "', sizingMethod='auto')";
			imgArr[i].src = "images/blank.gif";
		}
		if (imgArr[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = imgArr[i].currentStyle.backgroundImage.substring(5, imgArr[i].currentStyle.backgroundImage.length - 2);
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			imgArr[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};
sy.bgPng = function(bgElements) {
	for ( var i = 0; i < bgElements.length; i++) {
		if (bgElements[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = bgElements[i].currentStyle.backgroundImage.substring(5, bgElements[i].currentStyle.backgroundImage.length - 2);
			bgElements[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			bgElements[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};

sy.isLessThanIe8 = function() {/* 判断浏览器是否是IE并且版本小于8 */
	return ($.browser.msie && $.browser.version < 8);
};

$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {/* 扩展AJAX出现错误的提示 */
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText.split('<script')[0]);
	}
});

/*
  
 var easyuiPanelOnMove = function(left, top) {
 	var right,bottom;
 	var bodyWidth = $('body').width();
	var bodyHeight = $('body').height();
	var dialogwidth = $(this).width();
	var dialogHeight = $(this).height();
	if (left < 0) {
		$(this).window('move', {
			left:0,top:top
		});
	}
	
	if (top < 0) {
		$(this).window('move', {
			left:left,top:0
		});
	}
};
//$.fn.panel.defaults.onMove = easyuiPanelOnMove;
//$.fn.window.defaults.onMove = easyuiPanelOnMove;
//$.fn.dialog.defaults.onMove = easyuiPanelOnMove;


//自动计算dialog不超出iframe
function caculateDialog(left,top)
{
	var right,bottom;
	//1558--479--786=1265
	//alert($('body').width()+'---'+left +'----'+ $('#dlg-addDeclareCompany').width()+'='+($('#dlg-addDeclareCompany').width()+left));
	var bodyWidth = $('body').width();
	var bodyHeight = $('body').height();
	var dialogwidth = $('#dlg-addDeclareCompany').width();
	var dialogHeight = $('#dlg-addDeclareCompany').height();
	if(left < 0){
	    $('#dlg-addDeclareCompany').dialog('move',{left:0,top:top});
	}else if((left + dialogwidth) > (bodyWidth - 50)){
	    right = bodyWidth - dialogwidth - 50;
	    $('#dlg-addDeclareCompany').dialog('move',{left:right,top:top});
	}
	if(top < 0){
	    $('#dlg-addDeclareCompany').dialog('move',{left:left,top:0});
	}else if(top > (bodyHeight - dialogHeight - 50 )){
	    bottom = bodyHeight - dialogHeight - 50;
	    $('#dlg-addDeclareCompany').dialog('move',{left:left,top:bottom});
	}
	}
*/
/* 防止超出浏览器边界 */
var easyuiPanelOnMove = function(left, top) {
	if (left < 0) {
		$(this).window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;


 
 // 获取随机数根据时间戳
 function getRandomByTimeStamp()
 {
	 var timestamp=new Date().getTime();
	 return timestamp;
 }

 
 /* 页面渲染 js 开始 */ 
 
//用户状态
 function formatAccountState(val,row){  
	    if (val == 'enabled'){  
	        return '<font color=green>启用</font>';  
	    }if (val == 'disabled'){  
	        return '<font color=red>禁用</font>';  
	    }
	}  
 
	
function formatItem(row){  
    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +  
            '<span style="color:#888">' + row.desc + '</span>';  
    return s;  
}  

//性别
function formatSex(val,row){
	if(val=='0'){
		return '<font>男</font>'
	}if(val=='1'){
		return '<font>女</font>'
	}
}


function formatterOrderCarUser(val,row){
	return ''+row.carUser.userName+''
}
function formatterOrderCar(val,row){
	return ''+row.car.carName+''
}
function formatterOrderRentUser(val,row){
	return ''+row.rentCarUser.userName+''
}



//项目状态格式化
function formatterProjectStatus(val, row) {
	var projectType = row.projectType;
	if(projectType == 8){
		var arr=[{'key':1,'value':'待提交'},
		         {'key':2,'value':'待评估'},
		         {'key':3,'value':'待下户'},
		         {'key':6,'value':'待复审'},
		         {'key':7,'value':'待终审'},
		         {'key':8,'value':'待放款申请'},
		         {'key':9,'value':'待放款复核'},
		         {'key':10,'value':'待资金放款'},
		         {'key':11,'value':'还款中'},
		         {'key':12,'value':'已结清'},
		         {'key':13,'value':'面签'}
		         ];
	}else if(projectType == 10){
		var arr=[{'key':1,'value':'待提交'},
		         {'key':2,'value':'待初审'},
		         {'key':3,'value':'待下户'},
		         {'key':4,'value':'待复审'},
		         {'key':5,'value':'复审通过'}
		         ];
	}else{
		var arr=[{'key':1,'value':'待客户经理提交'},
		         {'key':2,'value':'待部门经理审批'},
		         {'key':3,'value':'待业务总监审批'},
		         {'key':4,'value':'待审查员审批'},
		         {'key':5,'value':'待风控初审'},
		         {'key':6,'value':'待风控复审'},
		         {'key':7,'value':'待风控终审'},
		         {'key':8,'value':'待风控总监审批'},
		         {'key':9,'value':'待总经理审批'},
		         {'key':10,'value':'已审批'},
		         {'key':11,'value':'已放款'},
		         {'key':12,'value':'业务办理已完成'},
		         {'key':13,'value':'已归档（解保）'},
		         {'key':14,'value':'待审查主管审批'},
		         {'key':15,'value':'待合规复审'}
		         ];
	}
	
	for (var i = 0; i < arr.length; i++) {
		if (arr[i].key==val) {
			return arr[i].value;
		}
	}
}
//收费到帐状态格式化
function formatterRecStatus(val, row) {
	if (val == 1) {
		return "未到账";
	} else if (val == 2) {
		return "已到账";
	} else {
		return "未知";
	}
}
//撤单状态格式化
function formatterIsChechan(val, row) {
	if (val == 1) {
		return "是";
	} else if (val == 0) {
		return "否";
	} else {
		return "未知";
	}
}
//查档审批状态格式化
function formatterCheckDocumentApprovalStatus(val, row) {
	if (val == 1) {
		return "未审批";
	} else if (val == 2) {
		return "不通过";
	} else if (val == 3) {
		return "通过";
	} else if (val == 4) {
		return "重新查档";
	} else {
		return "--";
	}
}
function formatterBizForeclosureStatus(val, row) {
	if (val == 1||val == null||val == '') {
		return "未赎楼";
	} else if (val == 2) {
		return "已赎楼";
	}  else if (val == 3) {
		return "已驳回";
	}  else {
		return "--";
	}
}
//审批状态：未审批=1，不通过=2，通过=3
function formatterCheckLitigationApprovalStatus(val, row) {
	if (val == 1) {
		return "未审批";
	} else if (val == 2) {
		return "不通过";
	} else if (val == 3) {
		return "通过";
	} else if (val == 4) {
		return "重新查诉讼";
	} else {
		return "--";
	}
}
//诉讼状态：未查诉讼=1，无新增诉讼=2，有增诉讼=3，有新增诉讼非本人=4
function formatterCheckLitigationStatus(val, row) {
	if (val == 1) {
		return "未查诉讼";
	} else if (val == 2) {
		return "无新增诉讼";
	} else if (val == 3) {
		return "有增诉讼";
	} else if (val == 4) {
		return "有新增诉讼非本人";
	} else {
		return "--";
	}
}
//申请办理状态格式化
function formatterApplyHandleStatus(val, row) {
	if (val == 1) {
		return "未申请";
	} else if (val == 2) {
		return "已申请";
	} else if (val == 3) {
		return "已完成";
	} else if (val == 4) {
		return "已归档";
	} else {
		return "未知";
	}
}
function formatterOverdueFeeConfirm(val, row) {
	if (val == 1) {
		return "未确认";
	} else if (val == 2) {
		return "已确认";
	} else {
		return "未知";
	}
}
//常用的确认格式化
function formatterCommonConfirm(val, row) {
	if (val == 1) {
		return "未确认";
	} else if (val == 2) {
		return "已确认";
	} else {
		return "未知";
	}
}
//公用申请状态格式化
function formatterCommonApplyHandleStatus(val, row) {
	if (val == 1) {
		return "未申请";
	} else if (val == 2) {
		return "申请中";
	} else if (val == 3) {
		return "驳回";
	} else if (val == 4) {
		return "审核中";
	} else if (val == 5) {
		return "审核通过";
	} else if (val == 6) {
		return "审核不通过";
	} else if (val == 7) {
		return "已归档";
	} else {
		return "未知";
	}
}
//
function formatterspecialType(val, row) {
	if (val == 1) {
		return "客户自由资金到账";
	} else if (val == 2) {
		return "客户首期款优先";
	}else {
		return "未知";
	}
}
//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4
function formatterReCheckStatus(val, row) {
	if (val == 1) {
		return "未复核";
	} else if (val == 2) {
		return "重新复核";
	} else if (val == 3) {
		return "同意";
	} else if (val == 4) {
		return "不同意";
	}  else {
		return "--";
	}
}
//机构认证状态
function formatterOrgAuditStatus(val,row){
	if(val == 1){
		return "未认证";
	}else if(val == 2){
		return "认证中";
	}else if(val == 3){
		return "已认证";
	}
}
//机构合作状态
function formatterOrgCooperateStatus(val,row){
	if(val == 1){
		return "未合作";
	}else if(val == 2){
		return "已合作";
	}else if(val == 3){
		return "合作已过期";
	}else if(val == 4){
		return "合作待确认";
	}
}
//回款状态
function formatRepaymentStatus(val,row){
	if(val == 1){
		return "未回款";
	}else if(val == 2){
		return "已回款";
	}else{
		return "未回款";
	}
}
//查档查诉讼方式：1=人工查询,2=系统查询
function formatterCheckWay(val,row){
	if(val == 1){
		return "人工";
	}else if(val == 2){
		return "系统";
	}else{
		return "未知";
	}
}
//赎楼贷后监控状态：无异常=1，有异常=2
function formatterForeAfterMonitorStatus(val,row){
	if(val == 1){
		return "<span style='color:blue;'>无异常</span>";
	}else if(val == 2){
		return "<span style='color:red;'>有异常</span>";
	}else if(val == 3){
		return "<span style='color:red;'>异常转正常</span>";
	}else{
		return "<span style='color:blue;'>无异常</span>";
	}
}
//赎楼贷后异常类型：1=展期，2=逾期，3=房产查封，4=新增诉讼，100=其他
function formatterAfterExceptionReportType(val,row){
	if(val == 1){
		return "展期";
	}else if(val == 2){
		return "逾期";
	}else if(val == 3){
		return "房产查封";
	}else if(val == 4){
		return "新增诉讼";
	}else if(val == 5){
		return "其他";
	}else{
		return "未知";
	}
}
//面签 抵押状态 1: 已抵押 2： 未抵押
function formatterInterviewMortgageStatus(val,row){
	if(val == 1){
		return "已抵押";
	}else if(val == 2){
		return "未抵押";
	}else{
		return "--";
	}
}
//面签 公证状态
function formatterInterviewNotarizationStatus(val,row){
	if(val == 1){
		return "已公证";
	}else if(val == 2){
		return "未公证";
	}else{
		return "--";
	}
}
//面签 面签状态
function formatterInterviewInterviewStatus(val,row){
	if(val == 1){
		return "已面签";
	}else if(val == 2){
		return "未面签";
	}else{
		return "--";
	}
}

	 /* 页面渲染 js 结束 */ 

/**
 * 通用的查询提交from表单
 * @param datagrid datagrid ID
 * @param fromId From ID
 * 2015年2月27日 合并
 */
function ajaxSearch(datagrid,fromId){
	$(fromId).form('submit',{
        success:function(data){
        	$(datagrid).datagrid('loadData', eval("("+data+")"));
        	$(datagrid).datagrid('clearChecked');
        }
	});
}

/**
 * 通用的分页查询提交from表单
 * @param datagrid datagrid ID
 * @param formId Form ID
 * 2015年4月22日 修改
 */
function ajaxSearchPage(datagrid,formId){
	// 重新加载数据
	$(datagrid).datagrid('load', $.serializeObject($(formId)));
	// 清空所选择的行数据
	clearSelectRows(datagrid);
}

/**
 * 将form表单元素的值序列化成对象
 */
$.serializeObject = function(formId) {
	var o = {};
	$.each(formId.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

// 系统用户状态格式化
function formatStatus(val,row){
	if (val == 0){
        return '已删除';
    } else if(val == 1){
        return '正常';
    }else if (val == -1){
    	return '新用户';
    }else if (val == 2){
    	return '无效';
    }else{
    	return val;
    }
}

// 数据字典状态格式化
function formatLookupStatus(val,row){
	if (val == 0){
        return '已删除';
    } else if(val == 1){
        return '正常';
    } else{
    	return val;
    }
}
//收费状态
function formatterCollectFeeStatus(val, row) {
	if (val == 1) {
		return "未收费";
	} else if (val == 2) {
		return "已收费";
	} else {
		return "未知";
	}
}
//图片上传
function openFileUpload(imgurl,topwid){
	$('<div id="winUploadFile"/>').dialog({
			href : 'uploadFile.action?imgurl='+imgurl,
			width : 520,
			height : 180,
			top : topwid,
			modal : true,
			title : '附件上传',
			buttons : [ {
				text : '上传',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-cancel',
					handler : function() {
						$("#winUploadFile").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});	
}

// 收起窗口
function putAway(divId){
	$(divId).hide();
	$(divId).parent().parent().parent().find('.upbtn').addClass('none');
	$(divId).parent().parent().parent().find('.openbtn').removeClass('none');
}

// 打开窗口
function openUp(divId){
	$(divId).show();
	$(divId).parent().parent().parent().find('.openbtn').addClass('none');
	$(divId).parent().parent().parent().find('.upbtn').removeClass('none');
}
//formatMoney
function formatMoney(value,row,index){
	if(value){
		return accounting.formatMoney(value, "", 2, ",", ".");
	}else{
		return "-";
	}
}
function formatTextarea(value,row,index){
	if(value){
		return '<textarea disabled="disabled" cols="40" rows="5">'+value+'</textarea>';
	}else{
		return "";
	}
}

//时间格式转换
function convertDate(val,row){
	// 判断是否存在数据  如果不存在，直接退出方法 
	if(null == val || "" == val){
		return "";
	}
	// 去掉时间后面的  .加数字 
	var index = val.indexOf(".");
	// 如果不存在,那index就等于总长度
	if(index == -1){
		index = val.length;
	}
	// 截取时间字符串,并转换
	var str=val.substring(0,index).toString();
	// 把时间里面的  -  转换城 /
	str = str.replace(/-/g,"/");
	// 转换成时间
	var date = new Date(str);
	// 获取时间的年月日
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    // 返回指定的时间格式
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}


function convertDateTime(strDate){
	// 判断是否存在数据  如果不存在，直接退出方法 
	if(null == strDate || "" == strDate){
		return "";
	}
	// 去掉时间后面的  .加数字 
	var index = strDate.indexOf(".");
	// 如果不存在,那index就等于总长度
	if(index == -1){
		index = strDate.length;
	}
	// 截取时间字符串,并转换
	var str=strDate.substring(0,index).toString();
	// 把时间里面的  -  转换城 /
	str = str.replace(/-/g,"/");
	// 转换成时间
	var date = new Date(str);
	// 获取时间的年月日时分秒
	var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    // 拼接年月日
    var strDateTime = year+'-'+(month<10?('0'+month):month)+'-'+(day<10?('0'+day):day);
    // 拼接时分秒
    strDateTime += " "+(hour<10?('0'+hour):hour) + ':' + (minute<10?('0'+minute):minute) + ':' + (second<10?('0'+second):second);
    // 返回 YYYY-MM-dd hh:mm:ss
    return strDateTime;
}

/**
 * 刷新datagrid
 * @param datagrid datagrid ID
 */
function publicRefresh(datagrid){
	// 清除所有选择的行。
	$(datagrid).datagrid("clearSelections");
	// 清除所有勾选的行。
	$(datagrid).datagrid("clearChecked");
	// 刷新
	$(datagrid).datagrid('reload')
}
 
/**
 * 清除datagrid的选中数据
 * @param datagrid datagrid ID
 */
function clearSelectRows(datagrid){
	// 清除所有选择的行。
	$(datagrid).datagrid("clearSelections");
	// 清除所有勾选的行。
	$(datagrid).datagrid("clearChecked");
}

/**
 * 格式化项目名称的跳转
 * @param value 当前的项目名称
 * @param row project对象
 * @returns {String} 超链接A标签
 */
function projectNameHyperlink(value, row) {
	// 拼接A标签
	var btn = "<a href='#' class='easyui-linkbutton' onclick='clickProjectName("+JSON.stringify(row)+")' >" +
			"<span style='color:blue; '>"+value+"</span></a>";  
	return btn;   
}

/**
 * 单击项目名称跳转的页面
 * @param row project对象
 */
function clickProjectName(row) {
	// 需要跳转的页面
	url = BASE_PATH + "beforeLoanController/addNavigation.action?projectId="+row.projectId+"&param='refId':'"+row.projectId+"','projectName':'"+row.projectName+"'";
	// 打开新的tab
	parent.openNewTab(url,"项目详情-"+row.projectName,true);
}

/**
 * 格式化抵质押物名称的跳转
 * @param value 当前的抵质押物名称
 * @param row 抵质押物对象
 * @returns  超链接A标签
 */
function projectAssBaseHyperlink(value,row){
	// 拼接A标签
	var btn = "<a href='#' class='easyui-linkbutton' onclick='clickProjectAssBaseName("+JSON.stringify(row)+")' >" +
			"<span style='color:blue; '>"+value+"</span></a>";  
	return btn;   
}

/**
 * 单击抵质押物名称打开的窗口
 * @param row 抵质押物对象
 */
function clickProjectAssBaseName(row){
	// 重置form数据
	$("#ck_seeAssBaseForm").form('reset');
	// 赋值
	$('#ck_seeAssBaseForm').form('load', row);
	// 拼接物件地点-- 省份和城市必填
	var address = row.addressProvince+row.addressCity;
	// 判断区是否选择,如果没选,不拼接进去
	if(row.addressArea != "请选择"){
		address += row.addressArea;
	}
	// 拼接物件地址的详细地点
	address  += row.addressDetail;
	$("#seeAssBase_address").val(address);
	// 所有权人类型
	if(row.ownType == 1){
		// 个人
		$("#ck_seeAssBaseForm input[name='ownTypeText']").val("个人");
	}else {
		// 企业
		$("#ck_seeAssBaseForm input[name='ownTypeText']").val("企业");
	}
	// 格式化登记时间
	$("#ck_seeAssBaseForm input[name='regDt']").val(convertDate(row.regDt));
	// 查询出所有的共有人信息
	var url = BASE_PATH+"mortgageController/getProjectAssOwnByBaseId.action?baseId="+row.pid+"&ownType="+row.ownType;
	$('#seeAssBase_grid').datagrid('options').url = url;
	$('#seeAssBase_grid').datagrid('reload');
	// post请求获取选择的担保方式的详细信息数据
	$.post(BASE_PATH+"mortgageController/getProjectAssDtlByBaseId.action",{baseId : row.pid}, 
		function(data) {
			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
			if(data){
				// 动态生成table and 标题
				var dom = "<h4>查看"+row.mortgageTypeText+"详细信息</h4><hr />"
				dom = dom + "<table id='add_mortgageTypeDiv_seeAssBase' class='datagrid-btable'> <tr  style='line-height:30px'>";
	        	for (var i = 0; i < data.length; i++) {
        			// 第一个 <td> 显示文字
   		 			dom = dom+'<td align="right" width="160">'+data[i].lookupDesc+':</td>';
   		 			// 第二个 <td> 显示input输入框和输入的值
	   		 		dom = dom+'<td align="left" width="160"><input class="easyui-textbox text2_style" disabled="true" id="'+data[i].lookupId+'" title="'+data[i].lookupDesc+'" value="'+(data[i].infoVal==null?'':data[i].infoVal)+'" name="'+data[i].lookupVal+'" /></td>';
	   		 		// 判断是否换行
	   		 		if( i % 2 == 0 ){
	       		 		dom = dom+'</tr><tr style="line-height:30px">';
	       		 	}
				}
	        	dom = dom+"</table>";
	        	// 赋值到指定的div
	        	$("#mortgageTypeDiv_seeAssBase").html(dom); 
			}else{
				$.messager.alert('操作提示',"获取抵质押物详细信息失败!",'error');	
			}
		},'json');
	// 初始化抵质押物办理文件
	$('#ck_bldj-datagrid').datagrid({
		url:BASE_PATH+"mortgageController/getProjectAssFile.action?baseId="+row.pid+"&fileTyle=1",
		method:'post'
	});
	// 初始化抵质押物保管信息
	$('#ck_bgcl-datagrid').datagrid({
		url:BASE_PATH+"mortgageController/getListProjectAssKeepingByBaseId.action?baseId="+row.pid,
		method:'post'
	});
	// 初始化抵质押物提取信息
	$('#ck_tq-datagrid').datagrid({
		url:BASE_PATH+"mortgageController/getListProjectAssExtractionByBaseId.action?baseId="+row.pid,
		method:'post'
	});
	// 初始化抵质押物解除处理文件
	$('#ck_jc-datagrid').datagrid({
		url:BASE_PATH+"mortgageController/getProjectAssFile.action?baseId="+row.pid+"&fileTyle=3",
		method:'post'
	});
	$('#dlg_seeAssBase').dialog('open').dialog('setTitle', "查看抵质押物详情");
}

// 格式所有datebox的长度,默认全部设置为100px
$(document).ready(function(){
	$('.easyui-datebox').datebox({    
		width:135   
	});  

});

function operRepaymentList(url){
	var t=document.body.scrollTop;
	$('<div id="repaymentDiv"/>').dialog({
		href : url,
		width : 750,
		height : 380,
		resizable:true,
		modal:true,
		title : '还款计划表',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				window.saveRepaymentPlan();
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-no',
				handler : function() {
					$("#repaymentDiv").dialog("close");
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});
}

/**
 * 根据项目ID判断是否有流程在执行
 * projectId 项目ID
 * return bool (true:有流程在走、false:没有相关流程在走)
 */
function isWorkflowByStatus(projectId){
	var bool = true;
	$.ajax({
		type: "POST",
    	url : BASE_PATH+"workflowController/getWorkflowStatusByProjectId.action?projectId="+projectId,
		async:false, 
	    success: function(status){
	    	//status 状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	    	if(status == 1){
	    		$.messager.alert('操作提示',"该项目已有利率变更流程在執行!",'error');
	    	}else if(status == 2){
	    		$.messager.alert('操作提示',"该项目已有提前还款流程在執行!",'error');
	    	}else if(status == 3){
	    		$.messager.alert('操作提示',"该项目已有费用减免流程在執行!",'error');
	    	}else if(status == 4){
	    		$.messager.alert('操作提示',"该项目已有展期流程在執行!",'error');
	    	}else if(status == 5){
	    		$.messager.alert('操作提示',"该项目已有违约流程在執行!",'error');
	    	}else if(status == 6){
	    		$.messager.alert('操作提示',"该项目已有挪用流程在執行!",'error');
	    	}else if(status == 0){
	    		bool = false;
	    	}
		}
	});
	return bool
}

/**
 * 根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
 * @param workflowProcessDefkey 流程定义Key
 * @param refId 引用ID
 * @return 当前任务节点
 */
function getTaskVoByWPDefKeyAndRefId(workflowProcessDefkey , refId){
	var str ;
	$.ajax({
		  url: BASE_PATH+"workflowController/getTaskVoByWPDefKeyAndRefId.action",
		  data:{"workflowProcessDefkey":workflowProcessDefkey,"refId": refId},
		  async: false,
		  success: function(res){
			  var dataObject = eval("("+res+")");
			  if(dataObject.refId){
				  str = joinParam(dataObject);
			  }
		  }
	});
	return str;
}


var formatterProjectName={
	// 贷款申请详情
		////debugger;
	disposeClick:function (projectId,projectNumber) {
		//debugger;
		var url = "";
		var titleName = "";
		var projectType = "";
		$.ajax({
			url:BASE_PATH+'beforeLoanController/getProjectType.action',
			async:false, 
			data:{projectId:projectId},
			dataType:'json',
			success:function(data){
				projectType=data;
			}
		})
		if(projectType == 2 || projectType == 5){
			url = BASE_PATH+"beforeLoanController/addNavigation.action?biaoZhi=3&projectId="+projectId+"&param='projectId':'"+projectId+"','projectName':1";
			titleName = "贷款申请详情";
		}else if (projectType == 3){
			var oldProjectId = -1;
			$.ajax({
				type: "POST",
		    	url : BASE_PATH+"creditsController/listLoanProjectUrl.action?rows=10&page=1&projectNumber="+projectNumber+"&projectId="+1,
				async:false, 
			    success: function(data){
			    	var str = eval("("+data+")");
			    	oldProjectId = str.rows[0].oldProjectId;
				}
			});
			url = BASE_PATH+"creditsCustomerInfoController/toAddCredit.action?projectId="+oldProjectId+"&newProjectId="+projectId+"&biaoZhi=3"
			titleName = "额度提取申请详情";
		}else if (projectType == 4){
			// 展期申请需要查询被展期的项目ID
			var oldProjectId = -1;
			$.ajax({
				type: "POST",
		    	url : BASE_PATH+"loanExtensionController/getProjectExtension.action?projectId="+projectId,
				async:false, 
			    success: function(data){
			    	var str = eval("("+data+")");
			    	oldProjectId = str.extensionProjectId;
				}
			});
			url = BASE_PATH+"loanExtensionController/toEditLoanExtensionApply.action?projectId="+oldProjectId+"&newProjectId="+projectId+"&biaoZhi=3"
			titleName = "展期申请详情";
		}
		parent.openNewTab(url,titleName,true);
	}
}

var formatterProjectOverview={
		// 贷款申请详情
			////debugger;
		disposeClick:function (projectId) {
			//debugger;
			var url = "";
			var titleName = "";
			var projectType = "";
			var projectName = "";
			$.ajax({
				url:BASE_PATH+'beforeLoanController/getProjectType.action',
				async:false, 
				data:{projectId:projectId},
				dataType:'json',
				success:function(data){
					projectType=data.projectType;
					projectName=data.projectName;
				}
			})
			if(projectType == 2 || projectType == 5 || projectType == 6){
				url = BASE_PATH+"beforeLoanController/getProjectOverview.action?projectId="+projectId;
				titleName = "贷款申请详情";
			}else if (projectType == 4){
				url = BASE_PATH+"beforeLoanController/toExtensionProjectOverview.action?projectId="+projectId;
				titleName = "展期申请详情";
			}else if (projectType == 8){
				url = BASE_PATH+"projectInfoController/toEditProject.action?type=2&projectId="+projectId+"&param='projectId':'"+projectId+"','projectName':'"+projectName+"','WORKFLOW_ID':'mortgageLoanForeAppRequestProcess'";
				titleName = "抵押贷申请详情";
			}else if (projectType == 10){
				url = BASE_PATH+"consumeProjectInfoController/toEditConsumeProject.action?type=2&projectId="+projectId+"&param='projectId':'"+projectId+"','projectName':'"+projectName+"','WORKFLOW_ID':'consumeLoanForeAppRequestProcess'";
				titleName = "消费贷申请详情";
			}
			parent.openNewTab(url,titleName,true);
		}
	}

function getBrithDate(){
	var certType = $("#cert_type").combobox('getText');
	var certNumber = $("#cert_Number").val();
	var birthDate = $("#birth_Date").val();
	if(certType=="--请选择--"){
		alert("请选择证件类型！");
	}
	
	if(certType=='身份证'){
		$("#sex").combobox('disable');
		$("#age").attr('readonly',true);
	}else{
		$("#sex").combobox('enable');
		$("#age").attr('readonly',false);
	}
	
	if( certType=='身份证' &&(!IdCardValidate(certNumber)))
    {
        alert('输入的身份证号长度不对，或者身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
        $("#cert_Number").val('');
        return false;
    }
	if(certType=='身份证' && certNumber.length>='15'){
		if(certNumber.substring(12,14)> 31){
			alert("请输入正确的身份证号！");
		}
		else{
			var year=certNumber.substring(6,10);
			var month=certNumber.substring(10,12);
			var date=certNumber.substring(12,14);
			$("#birth_Date").datebox("setValue",year+"-"+month+"-"+date);
			//判断性别
			if (parseInt(certNumber.substr(16, 1)) % 2 == 1) {
				//男
				$("#sex").combobox("setValue","13098");
				} else {
				//女
				$("#sex").combobox("setValue","13099");
				} 
			//获取年龄
			var myDate = new Date();
			var mon = myDate.getMonth() + 1;
			var day = myDate.getDate();
			
			var age = myDate.getFullYear() - certNumber.substring(6, 10) - 1;
			if (certNumber.substring(10, 12) < mon || certNumber.substring(10, 12) == mon && certNumber.substring(12, 14) <= day) {
			age++;
			}
			$("#age").val(age);
			
			if($("#birth_Date").datebox("getValue")!=year+"-"+month+"-"+date){
				$("#birth_Date").datebox("setValue",'');
				alert("请输入正确的身份证号！");
				 $("#cert_Number").val('');
				 $("#cert_Number").focus();
			}
		}
	}
};


var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
function IdCardValidate(idCard) {
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证   
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/** 
 * 判断身份证号码为18位时最后的验证位是否正确 
 * @param a_idCard 身份证号码数组 
 * @return 
 */ 
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }   
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/** 
  * 验证18位数身份证号码中的生日是否是有效生日 
  * @param idCard 18位书身份证字符串 
  * @return 
  */ 
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /** 
   * 验证15位数身份证号码中的生日是否是有效生日 
   * @param idCard15 15位书身份证字符串 
   * @return 
   */ 
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}


//人民币金额转大写
function numToCny(num) {
	var strOutput = "";
	var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';
	num += "00";
	var intPos = num.indexOf('.');
	if (intPos >= 0)
		num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
	strUnit = strUnit.substr(strUnit.length - num.length);
	for (var i = 0; i < num.length; i++)
		strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i, 1), 1)
				+ strUnit.substr(i, 1);
	var cny=strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(
			/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元')
			.replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元")
	if (cny=='整') {
		cny='';
	}
	return cny;
};


function isWorkflowByPid(projectId){
	var bool = true;
	$.ajax({
		type: "POST",
    	url : BASE_PATH+"loanExtensionController/isWorkflowByStatus.action?projectId="+projectId,
		async:false, 
	    success: function(result){
	    	if(result != 0){
	    		$.messager.alert('操作提示',"该项目已有展期流程在執行!",'error');
	    	}else{
	    		bool = false;
	    	}
		}
	});
	return bool;
}

//下载文件，如果是图片则直接在浏览器显示
function downLoadFileByPath(path,fileName){
	window.location.href=BASE_PATH+"orgPartnerInfoController/download.action?path="+path;
}

//图片则直接在浏览器显示
function lookUpFileByPath(path,fileName){
	var fileType=path.substring(path.lastIndexOf(".")+1);
	fileType = fileType.toLowerCase();
	if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
	    parent.openNewTab(BASE_PATH+'orgPartnerInfoController/toLookFile.action?path='+path+'&fileName='+encodeURI(fileName), "文件查看", true);
	}
}

/** 使用方法: 
 * 开启:MaskUtil.mask(); 
 * 关闭:MaskUtil.unmask(); 
 *  
 * MaskUtil.mask('其它提示文字...'); 
 */
var MaskUtil = (function(){  
    var $mask,$maskMsg;  
    var defMsg = '正在处理，请稍待。。。';  
    function init(){  
        if(!$mask){  
            $mask = $("<div class=\"datagrid-mask mask_index\"></div>").appendTo("body");  
        }  
        if(!$maskMsg){  
            $maskMsg = $("<div class=\"datagrid-mask-msg mask_index\">"+defMsg+"</div>")  
                .appendTo("body").css({'font-size':'12px'});  
        }  
          
        $mask.css({width:"100%",height:$(document).height()});  
          
        var scrollTop = $(document.body).scrollTop();  
          
        $maskMsg.css({  
            left:( $(document.body).outerWidth(true) - 190 ) / 2  
            ,top:( ($(window).height() - 45) / 2 ) + scrollTop 
        });   
    }  
    return {  
        mask:function(msg){  
            init();  
            $mask.show();  
            $maskMsg.html(msg||defMsg).show();  
        }  
        ,unmask:function(){  
            $mask.hide();  
            $maskMsg.hide();  
        }  
    }  
}()); 

/**
 * 房抵贷还款计划分期状态格式化
 * @param val
 * @param row
 * @param index
 * @returns {String}
 */
function formatThisStatus(val,row,index){
	if (val == 1) {
		return "正常未还";
	} else if (val == 2) {
		return "<span style='color:#00CC00'>正常已还</span>";
	} else if (val == 3) {
		return "<span style='color:red'>逾期未还</span>";
	} else if (val == 4) {
		return "<span style='color:#00CC00'>逾期已还</span>";
	} else if (val == 5) {
		return "<span style='color:#FF9900'>提前还款</span>";
	}
}
/**
 * 房抵贷还款状态格式化
 * @param val
 * @param row
 * @returns {String}
 */
function formatterRepaymentStatus(val, row) {
	if (val == 1) {
		return "正常还款中";
	} else if (val == 2) {
		return "已结清";
	} else if (val == 3) {
		return "逾期还款中";
	} else if (val == 4) {
		return "已逾期";
	}
}
function formatOverdueDt(val,row,index){
	var actualOverdueMoney = row.actualOverdueMoney;
	if(actualOverdueMoney>0){
		return val;
	}else{
		return "";
	}
}