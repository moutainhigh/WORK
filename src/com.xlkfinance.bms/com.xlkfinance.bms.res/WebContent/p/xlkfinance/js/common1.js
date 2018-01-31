
var checkInfo =1;
$(function(){
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
	if(checkInfo==1){//利率为0时做判断 让界面能正常显示0
		$(".easyui-numberbox").each(function(i) {
			var v=$(this).numberbox('getValue');
		    if( v=='0' ||v=='0.0'||v=='0.00'||v=='0.0000'){
		    	$(this).numberbox('setValue','');
		    };       
		}); 
	}
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

//用户审核
function formatCarUserCheck(val,row){
	if(val=='0'){
		return '<font color=gray>未提交审核</font>';
	}else if(val =='1'){
        return '<font color=blue>已提交审核</font>';		
	}else if(val =='2'){
        return '<font color=purple>开始审核</font>';		
	}else if(val =='3'){
		return '<font color=red>未通过</font>';
	}else if(val =='4'){
		return '<font color=green>车主(已通过)</font>';
	}
}
function formatTeantUserCheck(val,row){
	if(val=='0'){
		return '<font color=gray>未提交审核</font>';
	}else if(val =='1'){
        return '<font color=blue>已提交审核</font>';		
	}else if(val =='2'){
        return '<font color=purple>开始审核</font>';		
	}else if(val =='3'){
		return '<font color=red>未通过</font>';
	}else if(val =='4'){
		return '<font color=green>租客(已通过)</font>';
	}
}

//广告图片编辑
function  formatIMG(val,row){
	if (row.isStop == "1") {
		return '<font color="green">已上架</font>'
	}else if(row.isStop == "0") {
		return '<font color="red">已下架</font>'
	}else{
		return '<font color="bulle">未处理</font>'
	}
}
//性别
function formatSex(val,row){
	if(val=='0'){
		return '<font>男</font>'
	}if(val=='1'){
		return '<font>女</font>'
	}
}

//车辆状态
function formatCar(val,row){
	if(val=='0'){
		return '<font color="gray">未提交审核</font>'
	}else if (val=='1'){
		return '<font color="blue">已提交车审核</font>'
	}else if(val=='2'){
		return '<font color="purple">开始审核</font>'
	}else if(val=='3'){
		return '<font color="green">审核通过</font>'
	}else if(val=='4'){
		return '<font color="red">审核未通过</font>'
	}
}
//不可租时间
function formatNotRentTime(val,row){
	if(val.length==0){
		return '';
	}else{
			num=val.split(",");
			var str='';
			for(var i=0;i<num.length;i++){
				str = str + num[i]+" ";
//				if(i==3){
//					str = str + '</br>'
//				}
//					if(num[i]=='1'){
//						str=str+"周一"+" ";
//					}
//					if(num[i]=='2'){
//						str=str+"周二"+" ";
//					}
//					if(num[i]=='3'){
//						str=str+"周三"+" ";
//					}
//					if(num[i]=='4'){
//						str=str+"周四"+" ";
//					}
//					if(num[i]=='5'){
//						str=str+"周五"+" ";
//					}
//					if(num[i]=='6'){
//						str=str+"周六"+" ";
//					}
//					if(num[i]=='7'){
//						str=str+"周日"+" ";
//					}
			}
			return '<font color="red">'+str+'</font>'
	}
}
//车主状态
function formatTBUser(val,row){
	if(val==1){
		return '<font color="green">已注册成功用户</font>'
	}else{
		return '<font color="red">未验证用户</font>'
	}
}
//车主
function formatCarUser(val,row){
	return ''+row.commentUser.userName+''
}
//车名称
function formatCarName(val,row){
	return ''+row.tCar.carName+''
}
//车牌照
function formatPlateNum(val,row){
	return ''+row.tCar.plateNum+''
}
//车主电话
function formatPhone(val,row){
	return ""+row.commentUser.phone+""
}
//评论状态
function formatterCommentState(val,row){
	if(val=='0'){
		return '<font color="green">通过</font>'
	}else{
		return '<font color="red">未通过</font>'
	}
	
}
//订单状态
function formatterOrderState(val,row){
	if(val=='-2'){
		return '<font color="violet">续租车主未确认</font>'
	}else if(val=='-1'){
		return '<font color="violet">续租确认未付款</font>'
	}else if(val=='0'){
		return '<font color="green">已下单(未付款等待车主确认)</font>'
	}else if(val=='1'){
		return '<font color="violet">车主确认(未付款)</font>'
	}else if(val=='2'){
		return '<font color="purple">已付款(未支付押金)</font>'		
	}else if(val=='3'){
		return '<font color="purple">已支付押金(取车中)</font>'
	}else if(val=='4'){
		return '<font color="blue">已取车</font>'
	}else if(val=='5'){
		return '<font color="brown">租客确认已还车</font>'
	}else if(val=='6'){
		return '<font color="orange">车主确认已还车(已完成)</font>'
	}else if(val=='7'){
		return '<font color="gray">过期</font>'
	}else if(val=='8'){
		if(row.cancelPerson=='0'){
			return '<font color="red">取消(车主取消)</font>'
		}else if(row.cancelPerson=='1'){
			return '<font color="red">取消(租客取消)</font>'
		}else{
			return '<font color="red">取消</font>'
		}
	}else if(val=='9')
		return '<font color="yellowgreen">已结算</font>'
}

//押金（预授权）处理情况
function formatterMoneyState(val,row){
	if(val=='0'){
		return '<font color="blue">已完成</font>'
	}else if(val=='1'){
		return '<font color="red">未处理</font>'
	}else if(val=='2'){
		return '<font color="brown">已撤销</font>'
	}else if(val=='3'){
		return '<font color="purple">未收取</font>'
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
//账单状态
function formatterUserBillState(val,row){
	if(val=='0'){
		 return '<font color="red">冻结中</font>'
   }else if(val=='1'){
		 return '<font color="green">已完成</font>'
   }else if(val=='2'){
		 return '<font color="blue">取消入账</font>'
   }
}
//事故信息
function accidentState(val,row){
	if(val=='0'){
		return '<font color="red">否</font>'
	}else if(val=='1'){
		return '<font color="green">是</font>'
	}else{
		return '<font color="gray">未处理</font>'
	}
}
//故障跟进状态 ：0,已提交故障信息 1,跟进中 2,已跟进
function formatffollow(val,row){
	if(val=='0'){
		return '<font color="violet">已提交故障信息</font>'
	}else if(val=='1'){
		return '<font color="green">跟进中</font>'		
	}else if(val=='2'){
		return '<font color="purple">已跟进</font>'
	}
}
//事故跟进状态：0.事故信息已录入，1.已通知租客交纳资料，2.理赔资料已收集，3.已交案，4.已赔付，5.已结案
function formatAccidentfollow(val,row){
	if(val=='0'){ 
		return '<font color="violet">事故信息已录入</font>'
	}else if(val=='1'){
		return '<font color="purple">已通知租客交纳资料</font>'		
	}else if(val=='2'){
		return '<font color="blue">理赔资料已收</font>'
	}else if(val=='3'){
		return '<font color="gray">已交案</font>'
	}else if(val=='4'){
		return '<font color="red">已赔付</font>'
	}else if(val=='5'){
		return '<font color="green">已结案</font>'
	}
}
//变速箱
function formatTransMission(val,row){
	if(val=='1'){
		return '<font color="violet">不限</font>'
	}else if(val=='2'){
		return '<font color="green">自动挡</font>'		
	}else if(val=='3'){
		return '<font color="purple">手动挡</font>'
	}
}
//车排量
function formatCarEmission(val,row){
	if(val=='1'){
		return '<font color="violet">不限</font>'
	}else if(val=='2'){
		return '<font color="green">1.6L及以下</font>'		
	}else if(val=='3'){
		return '<font color="purple">1.6L~2.0L</font>'
	}else if(val=='4'){
		return '<font color="orange">2.0L~2.5L</font>'
	}else if(val=='5'){
		return '<font color="blue">2.5L及以上</font>'
	}
}
//车状态
function carState(val,row){
	if(val=='0'){
		return '<font color="green">可租</font>'
	}else if(val=='1'){
		return '<font color="violet">在租</font>'		
	}else if(val=='2'){
		return '<font color="orange">维修</font>'
	}else if(val=='3'){
		return '<font color="red">停用</font>'
	}
}
//提现状态
function formatterApplyState(val,row){
	if(val=='0'){
		return '<font color="orange">审核中</font>'
	}else if(val=='1'){
		return '<font color="green">提现完成</font>'
	}else if(val=='2'){
		return '<font color="red">审核失败</font>'
	}
	
}
//注册渠道
function formatRegisterChannel(val,row){
	if(val=='0'){//注册渠道 0.微信 1.官网 2.第三方合作商 3.APP 4.线下
		return '<font color="violet">微信</font>'
	}else if(val=='1'){
		return '<font color="green">官网</font>'		
	}else if(val=='2'){
		return '<font color="purple">第三方合作商</font>'
	}else if(val=='3'){
		return '<font color="orange">APP</font>'
	}else if(val=='4'){
		return '<font color="blue">线下</font>'
	} 
}
//续租状态      续租状态，0：车主未确认，1未付款，2已付款，3支付过期 4车主拒绝续约 ，5已结算
function formatReletType(val,row){
	if(val=='0'){ 
		return '<font color="violet">车主未确认</font>'
	}else if(val=='1'){
		return '<font color="purple">确认未付款</font>'		
	}else if(val=='2'){
		return '<font color="blue">已付款</font>'
	}else if(val=='3'){
		return '<font color="gray">支付过期</font>'
	}else if(val=='4'){
		return '<font color="red">车主拒绝续约</font>'
	}else if(val=='5'){
		return '<font color="green">已结算</font>'
	}
}
//违章处理状态
function formatterChuliState(val,row){
	if(val=='0'){
		return '<font color="red">未处理</font>'
	}else if(val=='3'){
		return '<font color="green">已处理</font>'
	}
	
}
//出账入账审核状态
function formattercZrZ(val,row){
	if(val=='0'){
		return '<font color="red">未审核</font>'
	}else if(val=='1'){
	    return '<font color="purple">不同意</font>'
	}else if(val=='2'){
	    return '<font color="orange">同意</font>'
	}
}	
//证件类型
function formatterIDState(val,row){
	if(val=='1'){
		return '<font color="violet">身份证</font>'
	}else if(val=='2'){
		return '<font color="purple">护照</font>'
	}else if(val=='3'){
		return '<font color="blue">军官证</font>'
	}
	
}
//优惠券状态
function formatterYHQ(val,row){
	if(val=='0'){
		return '<font color="red">未激活</font>'
	}else if(val=='1'){
		return '<font color="blue">已激活/未使用</font>'
	}else if(val=='2'){
		return '<font color="purple">使用中</font>'
	}else if(val=='3'){
		return '<font color="orange">已使用</font>'
	}else if(val=='4'){
		return '<font>已过期</font>'
	}
	
}
//可载人数
function formatterUserNum(val,row){
	if(val=='1'){
		return '<font>2人</font>'
	}else if(val=='2'){
		return '<font>4人</font>'
	}else if(val=='3'){
		return '<font>5人</font>'
	}else if(val=='4'){
		return '<font>7人及以上</font>'
	}
	
}

//行驶里程
function formatterMileage(val,row){
	if(val=='1'){
		return '<font>不限</font>'
	}else if(val=='2'){
		return '<font>低于2万公里</font>'
	}else if(val=='3'){
		return '<font>2-4万公里</font>'
	}else if(val=='4'){
		return '<font>4-6万公里</font>'
	}else if(val=='5'){
		return '<font>6-9万公里</font>'
	}else if(val=='6'){
		return '<font>9-12万公里</font>'
	}else if(val=='7'){
		return '<font>12-15万公里</font>'
	}else if(val=='8'){
		return '<font>15-20万公里</font>'
	}else if(val=='9'){
		return '<font>超过20万公里</font>'
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

//图片上传
function openFileUpload(imgurl,topwid){
	$('<div id="winUploadFile"/>').dialog({
			href : 'uploadFile.action?imgurl='+imgurl,
			width : 500,
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
	disposeClick:function (projectId,projectNumber) {
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
			url = BASE_PATH+"beforeLoanController/addNavigation.action?biaoZhi=3&projectId="+projectId+"&param='refId':'"+projectId+"','projectName':1";
			titleName = "贷款申请详情";
		}else if (projectType == 3){
			var oldProjectId = -1;
			$.ajax({
				type: "POST",
		    	url : BASE_PATH+"creditsController/listLoanProjectUrl.action?rows=10&page=1&projectNumber="+projectNumber,
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
function getBrithDate(){
	var certType = $("#cert_type").combobox('getText');
	var certNumber = $("#cert_Number").val();
	var birthDate = $("#birth_Date").val();
	if(certType=="--请选择--"){
		alert("请选择证件类型！");
	}
	if( certType=='身份证' &&(!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(certNumber))))
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
			if($("#birth_Date").datebox("getValue")!=year+"-"+month+"-"+date){
				$("#birth_Date").datebox("setValue",'');
				alert("请输入正确的身份证号！");
				 $("#cert_Number").val('');
				 $("#cert_Number").focus();
			}
		}
	}
};