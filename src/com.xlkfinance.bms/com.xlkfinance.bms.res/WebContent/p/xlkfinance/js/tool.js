$.extend($.fn.validatebox.defaults.rules, {
	selectValueRequired: {  
		validator: function(value,param){  
		return value!='请选择';  
		},  
		message: '请选择下拉数据.'  
		},

	isValidOrgCode : {
		validator : function(value, param) {
			return isValidOrgCode(value);
		},
		message : '请输入正确的组织机构代码证'
	},

	equals : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '请确认两次输入密码一致'
	},
	CHS : {
		validator : function(value, param) {
			return /^[\u0391-\uFFE5]+$/.test(value);
		},
		message : '请输入汉字'
	},
	ZIP : {
		validator : function(value, param) {
			return /^[1-9]\d{5}$/.test(value);
		},
		message : '邮政编码不存在'
	},
	QQ : {
		validator : function(value, param) {
			return /^[1-9]\d{4,10}$/.test(value);
		},
		message : 'QQ号码不正确'
	},
	mobile : {
		validator : function(value, param) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/.test(value);
		},
		message : '手机号码不正确'
	},
	loginName : {
		validator : function(value, param) {
			return /^[\u0391-\uFFE5\w]+$/.test(value);
		},
		message : '登录名称只允许汉字、英文字母、数字及下划线。'
	},
	safepass : {
		validator : function(value, param) {
			return safePassword(value);
		},
		message : '密码由字母和数字组成，至少6位'
	},
	equalTo : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '两次输入的字符不一至'
	},
	number : {
		validator : function(value, param) {
			return /^\d+$/.test(value);
		},
		message : '请输入数字'
	},
	numberYear : {
		validator : function(value, param) {
			return /^[1-9]{1}[0-9]{3}$/.test(value);
		},
		message : '请输入格式如：（2013）'
	},
	isPositiveInteger:{
		validator : function(value, param) {
			return /^((\d|[123456789]\d)|100)$/.test(value);
		},
		message : '请输入正确的分数'
	},
	ismoney : {
		validator : function(value, param) {
			return isMoney(value);
		},
		message : '请输入正确的金额'
	},

	isValidPhone : {
		validator : function(value, param) {
			return isValidPhone(value);
		},
		message : '请输入正确的固定电话号码'
	},
	isValidBusCode : {
		validator : function(value, param) {
			return isValidBusCode(value);
		},
		message : '请输入正确的营业执照'
	},
	isValidImageType : {
		validator : function(value, param) {
			return isValidImageType(value);
		},
		message : '请输入正确的图片格式,如：jpg,jpeg,png,gif'
	},
	isValidIdCard : {
		validator : function(value, param) {
			return isValidIdCard(value);
		},
		message : '请输入正确的身份证号'
	}

});




/**
 * 验证组织机构代码是否合法：组织机构代码为8位数字或者拉丁字母+“-”+1位校验码。 验证最后那位校验码是否与根据公式计算的结果相符。 编码规则请参看
 * http://wenku.baidu.com/view/d615800216fc700abb68fc35.html 例如：74371976-1
 * L1832212-3
 * 
 */
var isValidOrgCode = function(orgCode) {
	var ret = false;
	var codeVal = [ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
			"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" ];
	var intVal = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33,
			34, 35 ];
	var crcs = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
	if (!("" == orgCode) && orgCode.length == 10) {
		var sum = 0;
		for ( var i = 0; i < 8; i++) {
			var codeI = orgCode.substring(i, i + 1);
			var valI = -1;
			for ( var j = 0; j < codeVal.length; j++) {
				if (codeI == codeVal[j]) {
					valI = intVal[j];
					break;
				}
			}
			sum += valI * crcs[i];
		}
		var crc = 11 - (sum % 11);
		switch (crc) {
		case 10: {
			crc = "X";
			break;
		}
		default: {
			break;
		}
		}
		// alert("crc="+crc+",inputCrc="+orgCode.substring(9));
		if (crc == orgCode.substring(9)) {
			ret = true;
		}
	} else if ("" == orgCode) {
		ret = true;
	}
	return ret;
}


var isMoney = function(value) {
	var mny = /^\d+(.\d+)?$/;

	return mny.test(value)
}

/* 密码由字母和数字组成，至少6位 */
var safePassword = function(value) {
	return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/
			.test(value));
}


var isDateTime = function(format, reObj) {
	format = format || 'yyyy-MM-dd';
	var input = this, o = {}, d = new Date();
	var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format
			.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
	var len = f1.length, len1 = f3.length;
	if (len != f2.length || len1 != f4.length)
		return false;
	for ( var i = 0; i < len1; i++)
		if (f3[i] != f4[i])
			return false;
	for ( var i = 0; i < len; i++)
		o[f1[i]] = f2[i];
	o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
	o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
	o.dd = s(o.dd, o.d, d.getDate(), 31);
	o.hh = s(o.hh, o.h, d.getHours(), 24);
	o.mm = s(o.mm, o.m, d.getMinutes());
	o.ss = s(o.ss, o.s, d.getSeconds());
	o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
	if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0)
		return false;
	if (o.yyyy < 100)
		o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
	d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
	var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM
			&& d.getDate() == o.dd && d.getHours() == o.hh
			&& d.getMinutes() == o.mm && d.getSeconds() == o.ss
			&& d.getMilliseconds() == o.ms;
	return reVal && reObj ? d : reVal;
	function s(s1, s2, s3, s4, s5) {
		s4 = s4 || 60, s5 = s5 || 2;
		var reVal = s3;
		if (s1 != undefined && s1 != '' || !isNaN(s1))
			reVal = s1 * 1;
		if (s2 != undefined && s2 != '' && !isNaN(s2))
			reVal = s2 * 1;
		return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
	}
}

/**
 * 验证固定电话号码。固定电话号码格式为：区号-号码
 */
var isValidPhone = function(phone) {
	var ret = false;
	if ("" == phone
			|| (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?/
					.test(phone))) {
		ret = true;
	}
	return ret;
}

/**
 * 验证国税税务登记号是否合法:税务登记证是6位区域代码+组织机构代码
 */
var isValidTaxCode = function(taxCode) {
	var ret = true;
	if (isValidOrgCode($("#memberBase.eOrgCode").val())) {

	}
	return ret;
}

/**
 * 验证营业执照是否合法：营业执照长度须为15位数字，前14位为顺序码， 最后一位为根据GB/T 17710 1999(ISO
 * 7064:1993)的混合系统校验位生成算法 计算得出。此方法即是根据此算法来验证最后一位校验位是否政正确。如果
 * 最后一位校验位不正确，则认为此营业执照号不正确(不符合编码规则)。 以下说明来自于网络:
 * 我国现行的营业执照上的注册号都是15位的，不存在13位的，从07年开始国 家进行了全面的注册号升级就全部都是15位的了，如果你看见的是13位的注
 * 册号那肯定是假的。 15位数字的含义，代码结构工商注册号由14位数字本体码和1位数字校验码
 * 组成，其中本体码从左至右依次为：6位首次登记机关码、8位顺序码。 一、前六位代表的是工商行政管理机关的代码，国家工商行政管理总局用
 * “100000”表示，省级、地市级、区县级登记机关代码分别使用6位行 政区划代码表示。设立在经济技术开发区、高新技术开发区和保税区
 * 的工商行政管理机关（县级或县级以上）或者各类专业分局应由批准 设立的上级机关统一赋予工商行政管理机关代码，并报国家工商行政 管理总局信息化管理部门备案。
 * 二、顺序码是7-14位，顺序码指工商行政管理机关在其管辖范围内按照先 后次序为申请登记注册的市场主体所分配的顺序号。为了便于管理和
 * 赋码，8位顺序码中的第1位（自左至右）采用以下分配规则： 1）内资各类企业使用“0”、“1”、“2”、“3”； 2）外资企业使用“4”、“5”；
 * 3）个体工商户使用“6”、“7”、“8”、“9”。 顺序码是系统根据企业性质情况自动生成的。 三、校验码是最后一位，校验码用于检验本体码的正确性
 * 例如：441900000479695
 */
var isValidBusCode = function(busCode) {
	// return true;
	var ret = false;
	if (busCode.length == 15) {
		var sum = 0;
		var s = [];
		var p = [];
		var a = [];
		var m = 10;
		p[0] = m;
		for ( var i = 0; i < busCode.length; i++) {
			a[i] = parseInt(busCode.substring(i, i + 1), m);
			s[i] = (p[i] % (m + 1)) + a[i];
			if (0 == s[i] % m) {
				p[i + 1] = 10 * 2;
			} else {
				p[i + 1] = (s[i] % m) * 2;
			}
		}
		if (1 == (s[14] % m)) {
			// 营业执照编号正确!
			// alert("营业执照编号正确!");
			ret = true;
		} else {
			// 营业执照编号错误!
			ret = false;
			// alert("营业执照编号错误!");
		}
	} else if ("" == busCode) {
		ret = true;
	}
	return ret;
}

/**
 * 验证图片类型(jpg,jpeg,png,gif)
 */
var isValidImageType = function(fileName) {
	var temp = fileName.split(".");
	var length = temp.length;
	// alert("length= "+length+",name="+temp[length-1]);
	var ret = false;
	var typeName = temp[length - 1];// fileName.substring(fileName.length-4);
	// alert("typeName="+typeName);
	typeName = typeName.toLowerCase();
	// alert("typeName="+typeName);
	if ("" == fileName) {
		ret = true;
	}
	// alert("fileName="+fileName+",typeName="+typeName);
	else if ("jpg" == typeName || "png" == typeName || "gif" == typeName
			|| "jpeg" == typeName) {
		ret = true;
	}
	return ret;
}

/**
 * 检验身份证号码是否合法 15位身份证号码组成： ddddddyymmddxxs共15位，其中：
 * dddddd为6位的地方代码，根据这6位可以获得该身份证号所在地。 yy为2位的年份代码，是身份证持有人的出身年份。
 * mm为2位的月份代码，是身份证持有人的出身月份。 dd为2位的日期代码，是身份证持有人的出身日。 这6位在一起组成了身份证持有人的出生日期。
 * xx为2位的顺序码，这个是随机数。 s为1位的性别代码，奇数代表男性，偶数代表女性。
 * 18位身份证号码组成：ddddddyyyymmddxxsp共18位，其中： 其他部分都和15位的相同。年份代码由原来的2位升级到4位。最后一位为校验位。
 * 校验规则是： （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8
 * 7 6 5 4 3 2 也就是说，如果得到余数为1则最后的校验位p应该为对应的0.如果校验位不是，则该身份证号码不正确。
 */
var isValidIdCard = function(idCard) {
	var ret = false;
	var w = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
	if (idCard.length == 18) {
		// 身份证号码长度必须为18，只要校验位正确就算合法
		var crc = idCard.substring(17);
		var a = new Array();
		var sum = 0;
		for ( var i = 0; i < 17; i++) {
			a.push(idCard.substring(i, i + 1));
			sum += parseInt(a[i], 10) * parseInt(w[i], 10);
			// alert(a[i]);
		}
		sum %= 11;
		var res = "-1";
		switch (sum) {
		case 0: {
			res = "1";
			break;
		}
		case 1: {
			res = "0";
			break;
		}
		case 2: {
			res = "X";
			break;
		}
		case 3: {
			res = "9";
			break;
		}
		case 4: {
			res = "8";
			break;
		}
		case 5: {
			res = "7";
			break;
		}
		case 6: {
			res = "6";
			break;
		}
		case 7: {
			res = "5";
			break;
		}
		case 8: {
			res = "4";
			break;
		}
		case 9: {
			res = "3";
			break;
		}
		case 10: {
			res = "2";
			break;
		}
		}
		if (crc.toLowerCase() == res.toLowerCase()) {
			ret = true;
		}
		// ret=true;
	}
	/*
	 * else if(idCard.length == 15){ //15位的身份证号，只验证是否全为数字 var pattern = /\d/;
	 * ret=pattern.test(idCard); }
	 */
	return ret;
}