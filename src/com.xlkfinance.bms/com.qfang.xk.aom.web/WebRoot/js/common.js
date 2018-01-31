function  getWebRootPath(){
	var webroot = document.location.href;
	webroot = webroot.substring(webroot.indexOf('//') + 2, webroot.length);
	webroot = webroot.substring(webroot.indexOf('/') + 1, webroot.length);
	webroot = webroot.substring(0, webroot.indexOf('/'));
	rootpath = "/" + webroot;
	return rootpath;
}
/**
 * 设置未来(全局)的AJAX请求默认选项
 * 主要设置了AJAX请求遇到Session过期的情况
 */
$.ajaxSetup({
    type: 'POST',
    complete: function(xhr,status) {
        var sessionStatus = xhr.getResponseHeader('sessionstatus');
        if(sessionStatus == '-1') {
            layer.confirm('由于您长时间没有操作, session已过期, 请重新登录.', {
				icon : 0,
				btn : [ '是','否' ]
			//按钮
			}, function() {
				window.parent.location=getWebRootPath()+"/logout.action";
			}, function() {
			});
        }
    }
});

//重置按钮
function majaxReset() {
	$('#searchFrom').form('reset')
}
function searchList(tableId) { // 配置参数
	$(tableId).jqGrid('setGridParam', {
		datatype : 'json',
		postData : $.serializeObject($("#searchFrom")), //发送数据  
		page : 1
	}).trigger("reloadGrid"); //重新载入
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
function sendAjaxRequest(url){
	$.ajax({
	    url: url,
	    type: "POST",
		error : function() {// 请求失败处理函数
			layer.alert('请求失败', {
				icon : 5
			});
		},
	    success: function(data, status) {
	    	var ret = eval("(" + data + ")");
			if (ret && ret.header["success"]) {
				layer.confirm(ret.header["msg"], {
					icon : 6,
					btn : [ '是' ]
				//按钮
				}, function() {
				    refreshPage();
				});
			} else {
				layer.alert(ret.header["msg"], {
					icon : 5
				});
			}
	    }
	});
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

//发送短信验证码通用js start
//短信计时
var sendMsgCodeWait = 60;
var phoneId = "";
var category = 1;
function sendMsgCodeTime() {
	var senMsgBtn = document.getElementById("send_code_btn");
	if (sendMsgCodeWait == 0) {
		$(senMsgBtn).disabled = "false";
		$(senMsgBtn).attr("style",
				"background:#5BC0DE none repeat scroll 0 0");
		senMsgBtn.setAttribute("onclick", "sendMsg('"+phoneId+"',"+category+")");
		$(senMsgBtn).text("发送验证码");
		sendMsgCodeWait = 60;
	} else {
		$(senMsgBtn).disabled = "true";
		$(senMsgBtn).removeAttr('href');
		$(senMsgBtn).attr("style",
				"background:#A8A6A3 none repeat scroll 0 0");
		senMsgBtn.setAttribute("onclick", "");
		$(senMsgBtn).html("重新发送(" + sendMsgCodeWait + "秒)");
		sendMsgCodeWait--;
		setTimeout(function() {
			sendMsgCodeTime();
		}, 1000);
	}
}
// 发送验证码
function sendMsg(phoneId,category) {
	this.phoneId=phoneId;
	this.category=category;
	var phone = $(phoneId).val();
	if ($.trim(phone).length == 0) {// 手机号不能为空
		$("#msgCodeErr").html("请输入手机号码");
		$("#msgCodeErr").css("display", "");
	} else {
		if (isMobiel(phone)) {// 判断是否合法
			sendMsgCodeTime();
			$.ajax({
				url : getWebRootPath()+"/smsValidateCodeController/ignore/sendCodeMsg.action",
				type : 'post',
				data : 'phone=' + phone + '&category='+category,
				dataType : 'html',
				error : function() {// 请求失败处理函数
					$("#msgCodeErr").html("请求服务失败").show(300)
					.delay(8000).hide(300);
					sendMsgCodeWait = 0;
					setTimeout("$('#send_code_btn').text('发送验证码失败')",
							1000);
				},
				success : function(data, status) {
					var ret = eval("(" + data + ")");
					if (ret && ret.header["success"]) {
					} else {
						$("#msgCodeErr").html(ret.header["msg"]).show(300)
								.delay(8000).hide(300);
						sendMsgCodeWait = 0;
						setTimeout("$('#send_code_btn').text('发送验证码失败')",
								1000);
					}
				}
			});
		}
	}
}
function isMobiel(str) {
	var rst = false;
	if ($.trim(str) == '')
		return rst;
	var myReg = /^1[3456789][0-9]{9}$/;
	if (str.indexOf('请输入手机号码') < 0 && !myReg.test(str)) {
		$("#msgCodeErr").html("手机号码格式错误");
		$("#msgCodeErr").css({
			"display" : ""
		});
		return rst;
	} else {
		$("#msgCodeErr").css("display", "none");
		rst = true;
	}
	return rst;
}
//发送短信验证码通用js end

function isMobiel(str) {
	var rst = false;
	if ($.trim(str) == '')
		return rst;
	var myReg = /^1[3456789][0-9]{9}$/;
	if (str.indexOf('请输入手机号码') < 0 && !myReg.test(str)) {
		$("#msgCodeErr").html("手机号码格式错误");
		$("#msgCodeErr").css({
			"display" : ""
		});
		return rst;
	} else {
		$("#msgCodeErr").css("display", "none");
		rst = true;
	}
	return rst;
}
//刷新子页面
function refreshPage() {
	location.href=location.href;
}

/**
 * 只能输入数字
 */
function onlynum(event) {
	var code = event.charCode;
	if (code == 0) {
		code = event.keyCode;
	}

	if (code != 8 && code != 46 && code != 45 && (code < 48 || code > 57)) {
		alert("只能输入数字!");
		// errmessage("只能输入数字!");

		return false;
	} else {
		return true;
	}
}

function formatterMoney(cellvalue, options, rowObject) {
	if(cellvalue){
		return accounting.formatMoney(cellvalue, "", 2, ",", ".");
	}else{
		return "-";
	}
}
//机构或合伙人合作确认
function confirmCooperat(url){
	$.ajax({
	    url: url,
	    type: "POST",
		error : function() {// 请求失败处理函数
			layer.alert('请求失败', {
				icon : 5
			});
		},
	    success: function(data, status) {
	    	var ret = eval("(" + data + ")");
			if (ret && ret.header["success"]) {
				layer.confirm(ret.header["msg"], {
					icon : 6,
					btn : [ '是','否' ]
				//按钮
				}, function() {
					window.parent.location=getWebRootPath()+"/logout.action";
				}, function() {
				});
			} else {
				layer.alert(ret.header["msg"], {
					icon : 5
				});
			}
	    }
	});
}
//打开新的ifream的tab窗口
function openTab(url,tabName,dataId){
	var contentDiv=parent.document.getElementById("content-main");
	var iframe=null;
	$(contentDiv).children().each(function(){
        var dateIdTemp=$(this).attr("data-id");
        if (dataId==dateIdTemp) {//判断是否已存在tab
        	iframe=this;
        	return false;
		}
    });
	if (iframe==null) {//不已存在tab，则创建
        iframe = document.createElement('iframe'); //动态创建框架
        iframe.src=url;//框架中加载的页面 
        iframe.setAttribute('id',dataId);
        iframe.setAttribute('name',url);
        iframe.setAttribute('class','J_iframe');
        iframe.setAttribute('frameborder','0');
        iframe.setAttribute('data-id',dataId);
        iframe.style.width = "100%";
        iframe.style.height = "100%";
        contentDiv.appendChild(iframe);
	}else{//已存在tab，则把url改变
		iframe.src=url;//框架中加载的页面 
	}
	var tabsDiv=parent.document.getElementById("page-tabs-content");
	var a=null;
	$(tabsDiv).children().each(function(){
        var dateIdTemp=$(this).attr("data-id");
        if (dataId==dateIdTemp) {
        	a=this;
        	return false;
		}
    });
	if (a==null) {
		a = document.createElement('a'); //
		a.text=tabName;
		a.setAttribute('href','javascript:;');
		a.setAttribute('class','active J_menuTab');
		a.setAttribute('data-id',dataId);
		var i=document.createElement('i'); //<i class="fa fa-times-circle"></i>
		i.setAttribute('class','fa fa-times-circle');
		a.appendChild(i);
		tabsDiv.appendChild(a);
	}
	
    $(a).addClass("active").siblings(".J_menuTab").removeClass("active");
	$(iframe).show().siblings(".J_iframe").hide();
}
//刷新ifream的tab窗口
function refreshTab(dataId,url){
	var contentDiv=parent.document.getElementById("content-main");
	$(contentDiv).children().each(function(){
        var dateIdTemp=$(this).attr("data-id");
        if (dataId==dateIdTemp) {//判断是否已存在tab
        	this.src=url;//框架中加载的页面 
        	return false;
		}
    });
}
//关闭ifream的tab窗口
function closeTab(){
	var tabsDiv=parent.document.getElementById("page-tabs-content");
	var a=null;
	var dataId=null;
	$(tabsDiv).children().each(function(){
        var classValue=$(this).attr("class");
        if ("active J_menuTab"==classValue||"J_menuTab active"==classValue) {
        	dataId=$(this).attr("data-id");
        	a=this;
        	return false;
		}
    });
	$(a).prev().addClass("active").siblings(".J_menuTab").removeClass("active");
	$(a).remove();
	var contentDiv=parent.document.getElementById("content-main");
	$(contentDiv).children().each(function() {
		if ($(this).data("id") == dataId) {
			$(this).prev().show().siblings(".J_iframe").hide();
			$(this).remove();
			return false;
		}
	})
}

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
//下拉框请选择值为0
function setCombobox(inputId,lookupType,selVal){
	var inputElement = document.getElementById(inputId);
//	/alert(inputId + "  lookupType="+lookupType+"  selVal"+selVal);
	$.ajax({
		url : getWebRootPath()+'/sysLookupController/getSysLookupValByLookTypeThree.action?lookupType='+lookupType,    
		dataType : 'json',
		type : 'post',
		async : false,
		success : function(data) {
			inputElement.options.length=1;
			for(var da in data){
				//alert("da="+da+" data[da]="+data[da].lookupDesc);
				if(selVal != '' && data[da].pid == selVal){
					inputElement.options[da]=new Option(data[da].lookupDesc,data[da].pid,true,true);
				}else{
					inputElement.options[da]=new Option(data[da].lookupDesc,data[da].pid);
				}
			}  
		}
	});
}
//下拉框关系类型
function setReCombobox(inputId,lookupType,selVal){
	var inputElement = document.getElementById(inputId);
//	/alert(inputId + "  lookupType="+lookupType+"  selVal"+selVal);
	$.ajax({
		url : getWebRootPath()+'/sysLookupController/getSysLookupValByLookTypeThree.action?lookupType='+lookupType,    
		dataType : 'json',
		type : 'post',
		async : false,
		success : function(data) {
			inputElement.options.length=1;
			var i = 0;
			for(var da in data){
				if(lookupType == 'RELATION' && data[da].lookupVal < 5 && data[da].lookupVal != -1){
					continue;
				}
				//alert("da="+da+" data[da]="+data[da].lookupDesc); LOOKUP_VAL
				if(selVal != '' && data[da].lookupVal == selVal){
					inputElement.options[i]=new Option(data[da].lookupDesc,data[da].lookupVal,true,true);
				}else{
					inputElement.options[i]=new Option(data[da].lookupDesc,data[da].lookupVal);
				}
				i++;
			}  
		}
	});
}
//获取数据字典中对应的value
function getCombobox(inputId,lookupType,selVal){
	var selValShowArray = new Array();
	$.ajax({
		url : getWebRootPath()+'/sysLookupController/getSysLookupValByLookTypeThree.action?lookupType='+lookupType,    
		dataType : 'json',
		type : 'post',
		async : false,
		success : function(data) {
			for(var da in data){
				var selValShow = {pid:'',lookupDesc:""};
				selValShow.pid = data[da].pid;
				selValShow.lookupDesc = data[da].lookupDesc;
				//alert(data[da].pid + "  data[da].lookupDesc="+data[da].lookupDesc);
				selValShowArray[da] = selValShow;
			}  
		}
	});
	return selValShowArray;
}
//设置地区下拉框
function setAreaCombobox(inputId,levelNo,parentCode,selVal){
	if(isNaN(levelNo)){
		levelNo = "1";
	}
	var inputElement = document.getElementById(inputId);
	$.ajax({
		url:getWebRootPath()+'/sysAreaInfoController/getSysAreaInfo.action?levelNo='+levelNo+"&parentCode="+parentCode,    
		dataType : 'json',
		type : 'post',
		async : false,
		success : function(data) {
			inputElement.options.length=1;
			for(var da in data){
				//alert("da="+da+" data[da]="+data[da].lookupDesc);
				//inputElement.options[da]=new Option(data[da].areaName,data[da].areaCode);
				if(selVal != '' && data[da].areaCode == selVal){
					inputElement.options[da]=new Option(data[da].areaName,data[da].areaCode,true,true);
				}else{
					inputElement.options[da]=new Option(data[da].areaName,data[da].areaCode);
				}
			} 
			//inputElement.append('<option value="-1">--请选择--</option>');
			if(selVal == ''){
				inputElement.options[0]=new Option("--请选择--","-1",true,true);
			}
			//$("#province option[value=<%=orderInfo.getProvinceId()%>]").attr("selected",true) ;
		}
	});
}
