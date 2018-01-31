//初始化默认选择 
function check(row) {
	if ($("#currency").combobox('getValue') == "" || $("#currency").combobox('getValue') == 0) {
		if (row.lookupDesc == "人民币") {
			row.selected = true;
		}
	}
	if ($("#repayCycleType").combobox('getValue') == "" || $("#repayCycleType").combobox('getValue') == 0) {
		if (row.lookupDesc == "月") {
			row.selected = true;
		}
	}

	if ($("#dateMode").combobox('getValue') == "" || $("#dateMode").combobox('getValue') == 0) {
		if (row.lookupDesc == "自然年月日") {
			row.selected = true;
		}
	}
	if ($("#repayFun").combobox('getValue') == "" || $("#repayFun").combobox('getValue') == 0) {
		if (row.lookupDesc == "按月-先息后本") {
			row.selected = true;
		}
	}
	if ($("#circulateType").combobox('getValue') == "" || $("#circulateType").combobox('getValue') == 0) {
		if (row.lookupDesc == "最高额授信") {
			row.selected = true;
		}
	}
	return row.lookupDesc;
}

// 每期还款设置，赋值
function isRepayDate() {
	var val = $('input:radio[name="eachissueOption"]:checked').val();
	if (val == 1) {
		$("#repayDate").numberbox("readonly",false);
	} else {
		var outloanDt = $('#planOutLoanDt').datebox('getValue');
		var date = dateInfo(outloanDt);
		var str = date.getDate() - 1;
		if (str == 0) {
			$("#repayDate").numberbox('setValue', 1);
		} else {
			$("#repayDate").numberbox('setValue', str);// 按实际放款日对日还款
		}
		$("#repayDate").numberbox("readonly"); 
	}
}

// 是否循环选项 动态显示 最高额授信
function isCheckHoop() {
	var val = $('input:radio[name="isHoop"]:checked').val();
	if (val == 2) {
		$(".selectlist1").css("display", 'none');
		$("#circulateType").val("");
	} else {
		$(".selectlist1").css("display", 'block');
	}
}
 
// *计划放款日期 动态计算还款日期或者期限
function setValue1Do(newValue, oldValue) {
	var outloanDt = $('#planOutLoanDt').datebox('getValue');//放款开始日期
	applyCommon.repayDateInfo(outloanDt);//根据放款日期判断自动给每期还款日赋值
	//**************************
	var str = $('#planOutLoanDt').datebox('getValue');//放款开始日期
	var repayloanDt = $('#planRepayLoanDt').datebox('getValue');//放款结束日期
	var str2 = $('#planRepayLoanDt').datebox('getValue');//放款结束日期
	var StartDt = $('#credtiStartDt').datebox('getValue');//授信开始
	var EndDt = $('#credtiEndDt').datebox('getValue');//授信结束
	var testInfo = $('#repayFun').combobox('getText');
	var v = $('#repayCycle').textbox('getValue');// 获取还款期限
	if (StartDt <= outloanDt) {
		if (EndDt < outloanDt) {
			alert('计划放款日期必须在授信日期之间');
			$('#planOutLoanDt').datebox('setValue', '');
		} else {
			if (outloanDt != '' && repayloanDt != '') {
				if (repayloanDt > outloanDt) {
					var days = 0;
					if (testInfo == "等额本金" || testInfo == "按月-先息后本"
							|| testInfo == "等额本息" || testInfo == "等本等息"
							|| testInfo == "其他还款方式") {
						checkLoanDate(outloanDt, repayloanDt, str, str2);
					} else if (testInfo == "按日-5天收息，到期还本") {
						days = 5;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-7天收息，到期还本") {
						days = 7;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-10天收息，到期还本") {
						days = 10;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-15天收息，到期还本") {
						days = 15;
						selectRepayFun(days, outloanDt, repayloanDt);
					}
				} else {
					alert('计划还款日期要大于计划放款日期');
					$('#planRepayLoanDt').datebox('setValue', '');
				}
			} else {
				if (testInfo == "等额本金" || testInfo == "按月-先息后本"
						|| testInfo == "等额本息" || testInfo == "等本等息") {
					if (v != "" && v != 0) {// 还款期限不等于空
						if (outloanDt != "" && repayloanDt == "") {// 还款期限加计划放款日期得出
							// 还款日期
							var newMonth = addMoth(outloanDt, Number(v));
							
							var splitdate = newMonth.split("-");
							var startDateInfo = new Date(parseInt(splitdate[0],10), parseInt(splitdate[1],10)-1, parseInt(splitdate[2], 10)-1); // 在加一天
							var dates =  startDateInfo.format('yyyy-MM-dd');// 转换格式
							// 赋值
							if (dates >= StartDt && dates <= EndDt) {
								$('#planRepayLoanDt')
										.datebox('setValue', dates);
							} else {
								alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
								$('#planOutLoanDt').datebox('setValue', "");
							}
						}
					}
				} else {// 按天去计算
					Date.prototype.diff = function(date) {
						return (this.getTime() - date.getTime())
								/ (24 * 60 * 60 * 1000);
					}
					if (testInfo != "其他还款方式") {
						var str = 0;
						if (testInfo == "按日-5天收息，到期还本") {
							str = 5;
						}
						if (testInfo == "按日-7天收息，到期还本") {
							str = 7;
						}
						if (testInfo == "按日-10天收息，到期还本") {
							str = 10;
						}
						if (testInfo == "按日-15天收息，到期还本") {
							str = 15;
						}
						if (v != "") {
							var outloanDtv = dateInfo(outloanDt);
							var newDate = DateAdd("d", Number(v * str + 1),outloanDtv);
							/*var newDatev = dateInfo(newDate);*/
							var dates = newDate .format('yyyy-MM-dd');
							$('#planRepayLoanDt').datebox('setValue', dates);
						}
					}
				}
			}
		}
	} else {
		alert('计划放款日期必须在授信日期之间');
		$('#planOutLoanDt').datebox('setValue', '');
	}
}

// *计划还 款日期 动态计算放款日期或者期限
function setValue2Do(newValue, oldValue) {
	var outloanDt = $('#planOutLoanDt').datebox('getValue');// 放款日期
	var str = $('#planOutLoanDt').datebox('getValue');// 放款日期
	var repayloanDt = $('#planRepayLoanDt').datebox('getValue');// 还款日期
	var str2 = $('#planRepayLoanDt').datebox('getValue');// 还款日期
	var StartDt = $('#credtiStartDt').datebox('getValue');// 授信开始日期
	var EndDt = $('#credtiEndDt').datebox('getValue');// 授信结束日期
	var testInfo = $('#repayFun').combobox('getText');// 还款方式
	var v = $('#repayCycle').textbox('getValue');// 获取还款期限
	if (StartDt <= repayloanDt) {//
		// 判断提取结束日期是否超过授信日期
		if (EndDt < repayloanDt) {			
			alert('计划还款日期必须在授信日期之间');
			$('#planRepayLoanDt').datebox('setValue', '');
		} else {
			if (outloanDt != '' && repayloanDt != '') {// 如果两个值不为空直接根据还款方式去操作
				var days = 0;
				if (repayloanDt > outloanDt) {
					if (testInfo == "等额本金" || testInfo == "按月-先息后本"
							|| testInfo == "等额本息" || testInfo == "等本等息"
							|| testInfo == "其他还款方式") {
						checkLoanDate(outloanDt, repayloanDt, str, str2);
					} else if (testInfo == "按日-5天收息，到期还本") {
						days = 5;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-7天收息，到期还本") {
						days = 7;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-10天收息，到期还本") {
						days = 10;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-15天收息，到期还本") {
						days = 15;
						selectRepayFun(days, outloanDt, repayloanDt);
					}
				} else {
					alert('计划还款日期要大于计划放款日期');
					$('#planRepayLoanDt').datebox('setValue', '');
				}
			} else {// 否则 直接 还款日期 与还款期限计算出 放款日期
				if (testInfo == "等额本金" || testInfo == "按月-先息后本"
						|| testInfo == "等额本息" || testInfo == "等本等息"
						|| testInfo == "其他还款方式") {
					if (v != "" && v != 0) {// 还款期限不等于空
						if (repayloanDt != "") {
							var newMonth = addMoth(repayloanDt, -v);
							var startDateInfo = DateAdd("d", 1,dateInfo(newMonth)); // 在加一天
							var dates = startDateInfo.format('yyyy-MM-dd');// 转换格式
							// 赋值
							if (dates >= StartDt && dates <= EndDt) {
								$('#planOutLoanDt').datebox('setValue', dates);
							} else {
								alert("通过还款期限与还款日期得出的放款开始日期不在授信时间范围内");
								$('#planRepayLoanDt').datebox('setValue', "");
							}
						}
					}
				} else {// 按天去计算
					Date.prototype.diff = function(date) {
						return (this.getTime() - date.getTime())
								/ (24 * 60 * 60 * 1000);
					}
					if (testInfo != "其他还款方式") {
						var daysInfo = 0;
						if (testInfo == "按日-5天收息，到期还本") {
							daysInfo = 5;
						}
						if (testInfo == "按日-7天收息，到期还本") {
							daysInfo = 7;
						}
						if (testInfo == "按日-10天收息，到期还本") {
							daysInfo = 10;
						}
						if (testInfo == "按日-15天收息，到期还本") {
							daysInfo = 15;
						}
						if (v != "") {
							var newDate = DateAdd("-d", Number(v * daysInfo), dateInfo(repayloanDt)); // 减去按标准的天数
							var dates = newDate.format('yyyy-MM-dd');
							var newstartDate = DateAdd("d", 1, dateInfo(dates)); // 给当前时间加1
							var values =  newstartDate.format('yyyy-MM-dd');// 转换格式
							$('#planOutLoanDt').datebox('setValue', values);
						}
					}
				}
			}
		}
	} else {
		alert('计划还款日期必须在授信日期之间');
		$('#planRepayLoanDt').datebox('setValue', '');
	}
}
/*
 * 加五天. var newDate = DateAdd( "d ",5,new Date());
 * alert(newDate.toLocaleDateString())
 */
function DateAdd(interval, number, date) {
	switch (interval) {
	case "y": {
		date.setFullYear(date.getFullYear() + number); // 年
		return date;
		break;
	}
	case "q": {
		date.setMonth(date.getMonth() + number * 3);
		return date;
		break;
	}
	case "m": {
		date.setMonth(date.getMonth() + number); // 加一个月
		return date;
		break;
	}
	case "-m": {
		date.setMonth(date.getMonth() - number); // 减一个月
		return date;
		break;
	}
	case "d": {
		date.setDate(date.getDate() + number); // 加一天
		return date;
		break;
	}
	case "-d": {
		date.setDate(date.getDate() - number); // 减一天
		return date;
		break;
	}
	case "h": {
		date.setHours(date.getHours() + number);
		return date;
		break;
	}
	case "s": {
		date.setSeconds(date.getSeconds() + number);
		return date;
		break;
	}
	case "-": {
		date.setDate(date.getDate() - number);
		return date;
		break;
	}

	default: {
		date.setDate(d.getDate() + number);
		return date;
		break;
	}
	}
}

// 根据天得到还款期限
function selectRepayFun(str, outloanDt, repayloanDt) {
	Date.prototype.diff = function(date) {
		return (this.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
	}
	if (outloanDt != "" && repayloanDt != "") {
		var outDate = dateInfo(outloanDt);
		var reoayDate = dateInfo(repayloanDt);
		/*var a = new Date(outloanDt);
		var a1 = new Date(repayloanDt);*/
		var diff = reoayDate.diff(outDate);
		var times = (diff + 1) / str;
		$('#repayCycle').textbox('setValue',
				Math.ceil(times) == 0 ? 1 : Math.ceil(times));
	}
}
// 根据放款日期跟还款日期得到还款期限
function checkLoanDate(outloanDt, repayloanDt, str, str2) {
	outloanDt = outloanDt.split('-');
	// 得到月数
	//outloanDt = parseInt(outloanDt[0]) * 12 + parseInt(outloanDt[1]);
	// 拆分年月日
	repayloanDt = repayloanDt.split('-');
	// 得到月数
	//repayloanDt = parseInt(repayloanDt[0]) * 12 + parseInt(repayloanDt[1]);
	var m = Math.abs((parseInt(outloanDt[0],10) - parseInt(repayloanDt[0],10))*12 + (parseInt(outloanDt[1],10) - parseInt(repayloanDt[1],10)));// 获得月数
	var newDate = DateAdd("m", m, dateInfo(str)); // 根据获得的月数 添加月 M
	var newDate2 = dateInfo(str2);
	var s = DateAdd("-", 1, newDate);
	if (s.getTime() < newDate2.getTime() == true) {// 如果大于还款时间-1
		$('#repayCycle').textbox('setValue', m == 0 ? 1 : m + 1);
	} else {
		$('#repayCycle').textbox('setValue', m - 1 == 0 ? 1 : m);
	}
}
//
function repayCycleCheck() {
	var v = $('#repayCycle').textbox('getValue');// 获取还款期限
	var outloanDt = $('#planOutLoanDt').datebox('getValue');// 放款开始时间
	var repayloanDt = $('#planRepayLoanDt').datebox('getValue');// 放款结束时间
	var StartDt = $('#credtiStartDt').datebox('getValue');// 授信开始时间
	var EndDt = $('#credtiEndDt').datebox('getValue');// 授信开始结束
	var testInfo = $('#repayFun').combobox('getText');// 还款方式
	if (testInfo == "等额本金" || testInfo == "按月-先息后本" || testInfo == "等额本息"
			|| testInfo == "等本等息" || testInfo == "其他还款方式") {
		if (StartDt != "" && EndDt != "") {
			if (v != "") {
				if (outloanDt != "" && repayloanDt == "") {
					var newMonth = addMoth(outloanDt, v);
					if (dateInfo(newMonth) >= dateInfo(StartDt)
							&& dateInfo(newMonth) <= dateInfo(EndDt)) {
						$('#planRepayLoanDt').datebox('setValue', newMonth);
					} else {
						alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
						setTimeout(function() {// 让方法延迟100
							$('#repayCycle').textbox('setValue', "");
						}, 100);
						return;
					}
				} else if (outloanDt == "" && repayloanDt != "") {
					var newMonth = addMoth(repayloanDt, -v);
					if (dateInfo(newMonth) >= dateInfo(StartDt)
							&& dateInfo(newMonth) <= dateInfo(EndDt)) {
						$('#planOutLoanDt').datebox('setValue', newMonth);
					} else {
						alert("通过还款期限与放款结束时间得出的放款开始时间不在授信时间范围内");
						setTimeout(function() {
							$('#repayCycle').textbox('setValue', "");
						}, 100);
						return;
					}
				} else if (outloanDt != "" && repayloanDt != "") {// 如果两个有值 直接
					// 拿开始日期相加
					var newMonth = addMoth(outloanDt, v);
					var newDate = DateAdd("-d", 1, dateInfo(newMonth)); // 在减一天
					var dates =  newDate.format('yyyy-MM-dd');// 转换格式 赋值
					if (dates >= StartDt && dates <= EndDt) {
						$('#planRepayLoanDt').datebox('setValue', dates);
					} else {
						alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
						setTimeout(function() {
							$('#repayCycle').textbox('setValue', 1);
						}, 100);
						return;
					}
				}
			} else {
				return;
			}
		}
	} else {// 按天去计算
		Date.prototype.diff = function(date) {
			return (this.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
		}
		if (testInfo != "其他还款方式") {
			var str = 0;
			if (testInfo == "按日-5天收息，到期还本") {
				str = 5;
			}
			if (testInfo == "按日-7天收息，到期还本") {
				str = 7;
			}
			if (testInfo == "按日-10天收息，到期还本") {
				str = 10;
			}
			if (testInfo == "按日-15天收息，到期还本") {
				str = 15;
			}
			if (v != "") {
				if (outloanDt != "") {
					var newDate = DateAdd("d", Number(v * str - 1), dateInfo(outloanDt));
					var dates =  newDate.format('yyyy-MM-dd');
					if (dates >= StartDt && dates <= EndDt) {
						$('#planRepayLoanDt').datebox('setValue', dates);
					} else {
						alert("计划还款日期不在授信日期范围内");
						$('#planRepayLoanDt').datebox('setValue', "");
					}

				} else if (repayloanDt != "") {
					var newDate = DateAdd("-d", Number(v * str + 1), dateInfo(repayloanDt));
					var dates =  newDate.format('yyyy-MM-dd');
					if (dates >= StartDt && dates <= EndDt) {
						$('#planOutLoanDt').datebox('setValue', str);
					} else {
						alert("计划开始日期不在授信日期范围内");
						$('#planOutLoanDt').datebox('setValue', "");
					}
				}
			}
		}
	}
}

// 日期格式化2014-04-04
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

// 月份的相加减 例： 加 1 减 -1
function addMoth(d, m) {
	var ds = d.split('-'), _d = ds[2] - 0;
	var nextM = new Date(ds[0], ds[1] - 1 + m + 1, 0);
	var max = nextM.getDate();
	d = new Date(ds[0], ds[1] - 1 + m, _d > max ? max : _d);
	return d.toLocaleDateString().match(/\d+/g).join('-');
}

function checkInfo(newValue, oldValue) {
	var str = $("#credtiEndDt").datebox('getValue');
	var str1 = $("#credtiStartDt").datebox('getValue');
	var date = dateInfo(str);
	var date1 = dateInfo(str1);
	if (date < date1) {
		alert("授信结束日期必须要比开始时间大");
		$("#credtiEndDt").datebox('setValue', '');
		return;
	}

}
//IE版本转换date 直接new Date（）不支持
function dateInfo(date){
	var splitdate = date.split("-");
	return new Date(parseInt(splitdate[0],10), parseInt(splitdate[1],10)-1, parseInt(splitdate[2], 10));
}
function isNan(dates){
	if(dates=="NaN-aN-aN"){
		return;
	}
}