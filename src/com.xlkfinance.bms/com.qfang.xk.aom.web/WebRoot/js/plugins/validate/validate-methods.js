/*******************************************************************************
 * jQuery Validate扩展验证方法 
 ******************************************************************************/
$(function(){
    // 判断整数value是否等于0
    jQuery.validator.addMethod("isIntEqZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value==0;       
    }, "整数必须为0"); 
      
    // 判断整数value是否大于0
    jQuery.validator.addMethod("isIntGtZero", function(value, element) { 
          
         return this.optional(element) || (/^[1-9]\d*$/.test(value) && value>0);       
    }, "整数必须大于0"); 
     // 自定义邮箱检验
    jQuery.validator.addMethod("email", function(value, element) {    
        var email = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;    
        return this.optional(element) || (email.test(value));    
      }, "请输入有效的电子邮件");
      
    // 判断整数value是否大于或等于0
    jQuery.validator.addMethod("isIntGteZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value>=0;       
    }, "整数必须大于或等于0");   
    
    // 判断整数value是否不等于0
    jQuery.validator.addMethod("isIntNEqZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value!=0;       
    }, "整数必须不等于0");  
    
    // 判断整数value是否小于0
    jQuery.validator.addMethod("isIntLtZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value<0;       
    }, "整数必须小于0");  
    
    // 判断整数value是否小于或等于0
    jQuery.validator.addMethod("isIntLteZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value<=0;       
    }, "整数必须小于或等于0");  
    
    // 判断浮点数value是否等于0
    jQuery.validator.addMethod("isFloatEqZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value==0;       
    }, "浮点数必须为0"); 
      
    // 判断浮点数value是否大于0
    jQuery.validator.addMethod("isFloatGtZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value>0;       
    }, "浮点数必须大于0"); 
      
    // 判断浮点数value是否大于或等于0
    jQuery.validator.addMethod("isFloatGteZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value>=0;       
    }, "浮点数必须大于或等于0");   
    
    // 判断浮点数value是否不等于0
    jQuery.validator.addMethod("isFloatNEqZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value!=0;       
    }, "浮点数必须不等于0");  
    
    // 判断浮点数value是否小于0
    jQuery.validator.addMethod("isFloatLtZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value<0;       
    }, "浮点数必须小于0");  
    
    // 判断浮点数value是否小于或等于0
    jQuery.validator.addMethod("isFloatLteZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value<=0;       
    }, "浮点数必须小于或等于0");  
    
    // 判断浮点型
    jQuery.validator.addMethod("isFloat", function(value, element) {       
         return this.optional(element) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "只能包含数字、小数点等字符"); 
     
    // 匹配integer
    jQuery.validator.addMethod("isInteger", function(value, element) {       
         return this.optional(element) || (/^[-\+]?\d+$/.test(value) && parseInt(value)>=0);       
    }, "匹配integer");  
     
    // 判断数值类型，包括整数和浮点数
    jQuery.validator.addMethod("isNumber", function(value, element) {       
         return this.optional(element) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "匹配数值类型，包括整数和浮点数");  
    
    // 只能输入[0-9]数字
    jQuery.validator.addMethod("isDigits", function(value, element) {       
         return this.optional(element) || /^\d+$/.test(value);       
    }, "只能输入0-9数字");  
    
    // 判断中文字符
    jQuery.validator.addMethod("isChinese", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "只能包含中文字符。");   
 
    // 判断英文字符
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^[A-Za-z]+$/.test(value);       
    }, "只能包含英文字符。");   
 
     // 手机号码验证
    jQuery.validator.addMethod("isMobile", function(value, element) {    
      var length = value.length;    
      return this.optional(element) || (length == 11 && /^1[3456789][0-9]{9}$/.test(value));    
    }, "请正确填写您的手机号码。");

    // 电话号码验证
    jQuery.validator.addMethod("isPhone", function(value, element) {    
      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
      return this.optional(element) || (tel.test(value));    
    }, "请正确填写您的电话号码。");

    // 联系电话(手机/电话皆可)验证
    jQuery.validator.addMethod("isTel", function(value,element) {   
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请正确填写您的联系方式"); 
 
     // 匹配qq
    jQuery.validator.addMethod("isQq", function(value, element) {       
         return this.optional(element) || /^[1-9]\d{4,12}$/;       
    }, "匹配QQ");   
 
     // 邮政编码验证
    jQuery.validator.addMethod("isZipCode", function(value, element) {    
      var zip = /^[0-9]{6}$/;    
      return this.optional(element) || (zip.test(value));    
    }, "请正确填写您的邮政编码。");  
    
    // 匹配密码，只能包含英文、数字等字符，长度在6-18之间。
    jQuery.validator.addMethod("isPwd", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z0-9]{6,18}$/.test(value);       
    }, "只能包含英文、数字等字符，长度在6-18之间。");  
    
    // 只能包含英文、数字等字符。
    jQuery.validator.addMethod("isEnglishNumber", function(value, element) {       
    	return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);       
    }, "只能包含英文、数字等字符。");  
    
    // 身份证号码验证
    jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
      // var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;
      return this.optional(element) || isIdCardNo(value);    
    }, "请输入正确的身份证号码。"); 

    // IP地址验证
    jQuery.validator.addMethod("ip", function(value, element) {    
      return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
    }, "请填写正确的IP地址。");
   
    // 字符验证，只能包含中文、英文、数字、下划线等字符。
    jQuery.validator.addMethod("stringCheck", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
    }, "只能包含中文、英文、数字、下划线等字符");   
    
    // 登录名验证。
    jQuery.validator.addMethod("checkLoginName", function(value, element) {       
    	return this.optional(element) || /^[a-zA-Z0-9]{6,18}$/.test(value);       
    }, "只能包含英文、数字等字符，长度在6-18之间");   
   
    // 匹配english
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^[A-Za-z]+$/.test(value);       
    }, "匹配english");   
    
    // 匹配汉字
    jQuery.validator.addMethod("isChinese", function(value, element) {       
         return this.optional(element) || /^[\u4e00-\u9fa5]+$/.test(value);       
    }, "匹配汉字");   
    
    // 匹配中文(包括汉字和字符)
    jQuery.validator.addMethod("isChineseChar", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "匹配中文(包括汉字和字符) "); 
      
    // 判断是否为合法字符(a-zA-Z0-9-_)
    jQuery.validator.addMethod("isRightfulString", function(value, element) {       
         return this.optional(element) || /^[A-Za-z0-9_-]+$/.test(value);       
    }, "请输入合法字符(a-zA-Z0-9-_)");   
    
    // 判断是否包含中英文特殊字符，除英文"-_"字符外
    jQuery.validator.addMethod("isContainsSpecialChar", function(value, element) {  
         var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
         return this.optional(element) || !reg.test(value);       
    }, "含有中英文特殊字符");   
    //年龄
    jQuery.validator.addMethod("checkAge", function(value, element) {       
         return this.optional(element) || /^(?:[1-9][0-9]?|1[01][0-9]|120)$/.test(value);       
    }, "年龄格式有误");  
    // 检查机构代码是否存在
    jQuery.validator.addMethod("checkOrgCodeIsExist", function(value, element) { 
      return this.optional(element) || checkOrgCodeIsExist(value);    
    }, "该机构代码已经注册。"); 
    // 检查用户名是否已存在
    jQuery.validator.addMethod("checkUserNameIsExist", function(value, element) { 
    	return this.optional(element) || checkUserNameIsExist(value);    
    }, "该用户名已经注册。"); 
    // 检查手机号码是否已存在
    jQuery.validator.addMethod("checkPhoneIsExist", function(value, element) { 
    	return this.optional(element) || checkPhoneIsExist(value);    
    }, "该手机号码已经注册。"); 
    // 检查email是否已存在
    jQuery.validator.addMethod("checkEmailIsExist", function(value, element) { 
    	return this.optional(element) || checkEmailIsExist(value);    
    }, "该email已经注册。"); 
    jQuery.validator.addMethod("selectNone", function(value, element) {
        return this.optional(element) || value != -1;
    }, "必须选择一项");
    
 // 判断浮点数value是否大于0
    jQuery.validator.addMethod("isFloatGtMoney", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || (value>0 && value <9999999999);       
    }, "输入大于0的合法金额");
      
    // 判断浮点数value是否大于或等于0
    jQuery.validator.addMethod("isFloatGteMoney", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || (value>=0 && value <9999999999);       
    }, "输入大于等于0的合法金额");
    
    // 判断浮点数value是否大于0
    jQuery.validator.addMethod("isFloatGtRate", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || (value>0 && value <=100);       
    }, "输入大于0小于等于100的数字");
    // 判断浮点数value是否大于0
    jQuery.validator.addMethod("iszFloatGtRate", function(value, element) { 
    	value=parseFloat(value);      
    	return this.optional(element) || (value>=0 && value <=100);       
    }, "输入0到100的数字");
});
/*// 身份证号码的验证规则
function isIdCardNo(num){ 
	// if (isNaN(num)) {alert("输入的不是数字！"); return false;}
	var len = num.length, re; 
	if (len == 15) 
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
	else if (len == 18) 
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
	else {
		// alert("输入的数字位数不对。");
		return false;
	} 
	var a = num.match(re); 
	if (a != null) 
	{ 
		if (len==15) 
		{ 
			var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
			var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
		} 
		else 
		{ 
			var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
			var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
		} 
		if (!B) {
			// alert("输入的身份证号 "+ a[0] +" 里出生日期不对。");
			return false;
		} 
	} 
	if(!re.test(num)){
		// alert("身份证最后一位只能是数字和字母。");
		return false;
	}
	return true; 
} */

var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
function isIdCardNo(idCard) { 
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

// 车牌号校验
function isPlateNo(plateNo){
    var re = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
    if(re.test(plateNo)){
        return true;
    }
    return false;
}
// 检查机构代码是否存在
function checkOrgCodeIsExist(orgCode){
	var flag = false;
	var pid =getUserId();
	$.ajax({
	    url: getWebRootPath()+"/orgController/ignore/checkOrgCodeIsExist.action",
	    type: "POST",
	    data: {orgCode:orgCode,pid:pid},
	    async: false,
		success : function(result) { //表单提交后更新页面显示的数据
			var ret = eval("(" + result + ")");
			if (ret && !ret.header["success"]) {
				flag= true;
			}
		}
	});
	return flag
}
// 检查用户名是否已存在
function checkUserNameIsExist(userName){
	var flag = false;
	var pid =getUserId();
	$.ajax({
		url: getWebRootPath()+"/sysOrgUserController/ignore/checkUserNameIsExist.action",
		type: "POST",
		data: {userName:userName,pid:pid},
		async: false,
		success : function(result) { //表单提交后更新页面显示的数据
			var ret = eval("(" + result + ")");
			if (ret && !ret.header["success"]) {
				flag= true;
			}
		}
	});
	return flag
}
// 检查手机号码是否已存在
function checkPhoneIsExist(phone){
	var flag = false;
	var pid =getUserId();
	$.ajax({
		url: getWebRootPath()+"/sysOrgUserController/ignore/checkPhoneIsExist.action",
		type: "POST",
		data: {phone:phone,pid:pid},
		async: false,
		success : function(result) { //表单提交后更新页面显示的数据
			var ret = eval("(" + result + ")");
			if (ret && !ret.header["success"]) {
				flag= true;
			}
		}
	});
	return flag
}
// 检查email是否已存在
function checkEmailIsExist(email){
	var flag = false;
	var pid =getUserId();
	$.ajax({
		url: getWebRootPath()+"/sysOrgUserController/ignore/checkEmailIsExist.action",
		type: "POST",
		data: {email:email,pid:pid},
		async: false,
		success : function(result) { //表单提交后更新页面显示的数据
			var ret = eval("(" + result + ")");
			if (ret && !ret.header["success"]) {
				flag= true;
			}
		}
	});
	return flag
}
function getUserId(){
	var pid = 0;
	var temp = $("#pid").val();
	if (temp != undefined && temp != "") {
		pid = temp;
	}
	return pid;
}