
function checkInfo(newValue,oldValue){
	var str = $("#credtiEndDt").datebox('getValue');
	var str1 = $("#credtiStartDt").datebox('getValue');
	var date = new Date(str);
	var date1 = new Date(str1);
	if(date<date1){
		alert("授信结束日期必须要比开始时间大");
		 $("#credtiEndDt").datebox('setValue','');
				return;
	}
}
  
		
 
//*计划放款日期 动态计算还款日期或者期限 
function setValue1Do(newValue,oldValue){
	var outloanDt = $('#planOutLoanDt').datebox('getValue');
	var str =  $('#planOutLoanDt').datebox('getValue');
	var repayloanDt = $('#planRepayLoanDt').datebox('getValue');
	var str2 = $('#planRepayLoanDt').datebox('getValue');
	var StartDt = $('#credtiStartDt').datebox('getValue');
	var EndDt = $('#credtiEndDt').datebox('getValue');
	var testInfo = $('#repayFun').combobox('getText');
	var v = $('#repayCycle').numberbox('getValue');//获取还款期限
	if(StartDt<=outloanDt){
		if(EndDt<outloanDt){
			alert('计划放款日期必须在授信日期之间');
			$('#planOutLoanDt').datebox('setValue','');
		}else{ 
			if(repayloanDt!=''){
				if(repayloanDt>outloanDt){
					if(testInfo=="等额本金"|| testInfo=="按月-先息后本" ||testInfo=="等额本息" ||testInfo=="等本等息" ){
						checkLoanDate(outloanDt,repayloanDt,str,str2);
					} else if(testInfo=="按日-5天收息，到期还本"){
						var str = 5;
						selectRepayFun(str,outloanDt,repayloanDt);
					}else if(testInfo=="按日-7天收息，到期还本"){
						var str = 7;
						selectRepayFun(str,outloanDt,repayloanDt);
					}else if(testInfo=="按日-10天收息，到期还本"){
						var str = 10;
						selectRepayFun(str,outloanDt,repayloanDt);
					}else if(testInfo=="按日-15天收息，到期还本"){
						var str = 15;
						selectRepayFun(str,outloanDt,repayloanDt);
					}
				}else{
					alert('计划还款日期要大于计划放款日期');
					$('#planRepayLoanDt').datebox('setValue','');
				}
			}else{
				if(testInfo=="等额本金"|| testInfo=="按月-先息后本" || testInfo=="等额本息" || testInfo=="等本等息"  ){
					if(StartDt!=""&&EndDt!=""){
						if(v!="" && v!=0){//还款期限不等于空
							 if(outloanDt!=""&&repayloanDt==""){//还款期限加计划放款日期得出  还款日期
								 var newDate   =   DateAdd( "m ",Number(v),new Date(outloanDt));  
								 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
								 if(str>=StartDt && str<=EndDt){
									 $('#planRepayLoanDt').datebox('setValue',str);
								 }else{
									 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
									 $('#planOutLoanDt').datebox('setValue',"");
								 }
							 }
							 if(outloanDt==""&&repayloanDt!=""){//还款期限加计划换款日期得出  计划日期
								 var newDate   =   DateAdd( "-m ",Number(v),new Date(repayloanDt));  //-m： 减月数   v：月        repayloanDt 日期 
								 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
								 if(str>=StartDt && str<=EndDt){
									 $('#planOutLoanDt').datebox('setValue',str);
								 }else{
									 alert("通过还款期限与放款结束时间得出的放款开始时间不在授信时间范围内");
									 $('#planRepayLoanDt').datebox('setValue',"");
								 }
							 }
						}
					}
				}else {//按天去计算
					Date.prototype.diff = function(date){
						  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
					}
					if(testInfo!="其他还款方式"){
						var str = 0;
						if(testInfo=="按日-5天收息，到期还本") {
							str = 5;
						}  
						if(testInfo=="按日-7天收息，到期还本" ) {
							str = 7;
						} 
						if(testInfo=="按日-10天收息，到期还本") {
							str = 10;
						} 
						if(testInfo=="按日-15天收息，到期还本") {
							str = 15;
						}
						if(v!=""){
							 var newDate   =   DateAdd( "d ",Number(v*str+1),new Date(outloanDt));  
							 var dates = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
							 $('#planRepayLoanDt').datebox('setValue',dates);
						}
					}
				}
			}
		}
	}else{
		alert('计划放款日期必须在授信日期之间');
		$('#planOutLoanDt').datebox('setValue','');
	}
}
		
//*计划还 款日期 动态计算放款日期或者期限 
function setValue2Do(newValue,oldValue){
	var outloanDt = $('#planOutLoanDt').datebox('getValue');
	var str =  $('#planOutLoanDt').datebox('getValue');
	var repayloanDt = $('#planRepayLoanDt').datebox('getValue');
	var str2 = $('#planRepayLoanDt').datebox('getValue');
	var StartDt = $('#credtiStartDt').datebox('getValue');
	var EndDt = $('#credtiEndDt').datebox('getValue');
	var testInfo = $('#repayFun').combobox('getText');//还款方式
	var v = $('#repayCycle').numberbox('getValue');//获取还款期限
	if(StartDt<=repayloanDt){
		if(EndDt<repayloanDt){
			alert('计划还款日期必须在授信日期之间');
			$('#planRepayLoanDt').datebox('setValue','');
		}else{
			if(outloanDt!=''){
				if(repayloanDt>outloanDt){
					if(testInfo=="等额本金"||testInfo=="按月-先息后本"||testInfo=="等额本息"||testInfo=="等本等息"){
						checkLoanDate(outloanDt,repayloanDt,str,str2);
					}else if(testInfo=="按日-5天收息，到期还本"){
						var str = 5;
						selectRepayFun(str,outloanDt,repayloanDt);
					}else if(testInfo=="按日-7天收息，到期还本"){
						var str = 7;
						selectRepayFun(str,outloanDt,repayloanDt);
					}else if(testInfo=="按日-10天收息，到期还本"){
						var str = 10;
						selectRepayFun(str,outloanDt,repayloanDt);
					}else if(testInfo=="按日-15天收息，到期还本"){
						var str = 15;
						selectRepayFun(str,outloanDt,repayloanDt);
					}
				}else{
					alert('计划还款日期要大于计划放款日期');
					$('#planRepayLoanDt').datebox('setValue','');
				}
			}else{
				if(testInfo=="等额本金"|| testInfo=="按月-先息后本" || testInfo=="等额本息" || testInfo=="等本等息"  ){
					if(StartDt!=""&&EndDt!=""){
						if(v!="" && v!=0){//还款期限不等于空
							 if(outloanDt!=""&&repayloanDt==""){
							 var newDate   =   DateAdd( "m ",Number(v),new Date(outloanDt));  
							 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
							 if(str>=StartDt && str<=EndDt){
								 $('#planRepayLoanDt').datebox('setValue',str);
							 }else{
								 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
								   $('#repayCycle').numberbox('setValue',"");
								 
							 }
							// $('#planRepayLoanDt').datebox('setValue',new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd'));
						 }
						 if(outloanDt==""&&repayloanDt!=""){
							 var newDate   =   DateAdd( "-m ",Number(v),new Date(repayloanDt));  
							 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
							 if(str>=StartDt && str<=EndDt){
								 $('#planOutLoanDt').datebox('setValue',str);
							 }else{
								 alert("通过还款期限与还款日期得出的放款开始日期不在授信时间范围内");
								  $('#planRepayLoanDt').datebox('setValue',"");
							 }
						 }
						}
					}
				}else {//按天去计算
					Date.prototype.diff = function(date){
						  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
					}
					if(testInfo!="其他还款方式"){
						var str = 0;
						if(testInfo=="按日-5天收息，到期还本") {
							str = 5;
						}  
						if(testInfo=="按日-7天收息，到期还本" ) {
							str = 7;
						} 
						if(testInfo=="按日-10天收息，到期还本") {
							str = 10;
						} 
						if(testInfo=="按日-15天收息，到期还本") {
							str = 15;
						}
						if(v!=""){
							 var newDate   =   DateAdd( "-d ",Number(v*str+1),new Date(repayloanDt));  
							 var dates = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
							 $('#planOutLoanDt').datebox('setValue',dates);
						}
					}
				}
			}
		}
	}else{
		alert('计划还款日期必须在授信日期之间');
		$('#planRepayLoanDt').datebox('setValue','');
	}
}
		
//日期的相加减 列：
/* 	var   now   =   new   Date();  
//加五天.  
var   newDate   =   DateAdd( "d ",5,now);  
alert(newDate.toLocaleDateString())  
//加两个月.  
newDate   =   DateAdd( "m ",2,now);  
alert(newDate.toLocaleDateString())  
//加一年  
newDate   =   DateAdd( "y ",1,now);  
alert(newDate.toLocaleDateString()) */
function   DateAdd(interval,number,date){  
	 /* 
	  *   功能:实现VBScript的DateAdd功能. 
	  *   参数:interval,字符串表达式，表示要添加的时间间隔. 
	  *   参数:number,数值表达式，表示要添加的时间间隔的个数. 
	  *   参数:date,时间对象. 
	  *   返回:新的时间对象. 
	  *   var   now   =   new   Date(); 
	  *   var   newDate   =   DateAdd( "d ",5,now); 
	  *---------------   DateAdd(interval,number,date)   ----------------- 
	  */  
        switch(interval)  
        {  
                case   "y "   :   {  
                        date.setFullYear(date.getFullYear()+number);  //年 
                        return   date;  
                        break;  
                }  
                case   "q "   :   {  
                        date.setMonth(date.getMonth()+number*3);  
                        return   date;  
                        break;  
                }  
                case   "m "   :   {  
                        date.setMonth(date.getMonth()+number);  //加一个月 
                        return   date;  
                        break;  
                }  
                case   "-m "   :   {  
                    date.setMonth(date.getMonth()-number);  //减一个月 
                    return   date;  
                    break;  
          	   }  
                case   "w "   :   {  
                        date.setDate(date.getDate()+number*7);  
                        return   date;  
                        break;  
                }  
                case   "d "   :   {  
                        date.setDate(date.getDate()+number);  //加一天 
                        return   date;  
                        break;  
                }  case   "-d "   :   {  
                    date.setDate(date.getDate()-number);  //减一天
                    return   date;  
                    break;  
            	}   
                case   "h "   :   {  
                        date.setHours(date.getHours()+number);  
                        return   date;  
                        break;  
                }  
                case   "s "   :   {  
                        date.setSeconds(date.getSeconds()+number);  
                        return   date;  
                        break;  
                }  
                case   "- "   :   {  
                	 date.setDate(date.getDate()-number);  
                    return   date;  
                    break;  
           		}  
                
                default   :   {  
                        date.setDate(d.getDate()+number);  
                        return   date;  
                        break;  
                }  
        }  
}  
		  
	
		
//根据天得到还款期限
function selectRepayFun(str,outloanDt,repayloanDt){
	Date.prototype.diff = function(date){
		  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
	}
	if(outloanDt!="" && repayloanDt!=""){
		var a = new Date(outloanDt);
		var a1 = new Date(repayloanDt);
		var diff = a1.diff(a);
		var times = (diff-1)/str;
		$('#repayCycle').textbox('setValue',Math.ceil(times)==0?1:Math.ceil(times));
	}
}

//根据放款日期跟还款日期得到还款期限
function checkLoanDate(outloanDt,repayloanDt,str,str2){
	outloanDt = outloanDt.split('-');
	// 得到月数
	outloanDt = parseInt(outloanDt[0]) * 12 + parseInt(outloanDt[1]);
	// 拆分年月日
	repayloanDt = repayloanDt.split('-');
	// 得到月数 
	repayloanDt = parseInt(repayloanDt[0]) * 12 + parseInt(repayloanDt[1]);
	var m = Math.abs(outloanDt - repayloanDt);//获得月数
	var newDate   =   DateAdd( "m ",m,new Date(str)); 
	var newDate2   =  new Date(str2);
	var s = DateAdd( "- ",1,new Date(newDate)); 
	if(s.getTime()<=newDate2.getTime()==true){
		$('#repayCycle').textbox('setValue',m==0?1:m);
	}else{
		$('#repayCycle').textbox('setValue',m-1==0?1:m);
	}
}

// 
function repayCycleCheck(){
	var v = $('#repayCycle').numberbox('getValue');//获取还款期限
	var outloanDt = $('#planOutLoanDt').datebox('getValue');//放款开始时间
	var repayloanDt = $('#planRepayLoanDt').datebox('getValue');//放款结束时间
	var StartDt = $('#credtiStartDt').datebox('getValue');//授信开始时间
	var EndDt = $('#credtiEndDt').datebox('getValue');//授信开始结束
	var testInfo = $('#repayFun').combobox('getText');//还款方式 
	if(testInfo=="等额本金"|| testInfo=="按月-先息后本" || testInfo=="等额本息" || testInfo=="等本等息"  ){
		if(StartDt!=""&&EndDt!=""){
			 if(v!=""){
				 if(outloanDt!=""&&repayloanDt==""){
					 var newDate   =   DateAdd( "m ",Number(v),new Date(outloanDt));  
					 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
					 if(str>=StartDt && str<=EndDt){
						 $('#planRepayLoanDt').datebox('setValue',str);
					 }else{
						 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
						 setTimeout(function(){
							 $('#repayCycle').numberbox('setValue',""); 
								},100);
						 return ;
					 }
				 }else  if(outloanDt==""&&repayloanDt!=""){
					 var newDate   =   DateAdd( "-m ",Number(v),new Date(repayloanDt));  
					 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
					 if(str>=StartDt && str<=EndDt){
						 $('#planOutLoanDt').datebox('setValue',str);
					 }else{
						 alert("通过还款期限与放款结束时间得出的放款开始时间不在授信时间范围内");
						 setTimeout(function(){
							 $('#repayCycle').numberbox('setValue',""); 
								},100);
						 return ;
					 }
				 }else if(outloanDt!=""&&repayloanDt!=""){//如果两个有值 直接 拿开始日期相加
					 var newDate   =   DateAdd( "m ",Number(v),new Date(outloanDt));  
					 var str = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
					 if(str>=StartDt && str<=EndDt){
						 $('#planRepayLoanDt').datebox('setValue',str);
					 }else{
						 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
						 setTimeout(function(){
						 $('#repayCycle').numberbox('setValue',1); 
							},100);
						 return ;
					 }
				 }
			}else{
				
				return ;
			}
		}
	}else {//按天去计算
		Date.prototype.diff = function(date){
			  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
		}
		if(testInfo!="其他还款方式"){
			var str = 0;
			if(testInfo=="按日-5天收息，到期还本") {
				str = 5;
			}  
			if(testInfo=="按日-7天收息，到期还本" ) {
				str = 7;
			} 
			if(testInfo=="按日-10天收息，到期还本") {
				str = 10;
			} 
			if(testInfo=="按日-15天收息，到期还本") {
				str = 15;
			}
			if(v!=""){
				if(outloanDt!=""){
					 var newDate   =   DateAdd( "d ",Number(v*str+1),new Date(outloanDt));  
					 var dates = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
					 if(dates>=StartDt && dates<=EndDt){
						 $('#planRepayLoanDt').datebox('setValue',dates);
					 }else{
						 alert("计划还款日期不在授信日期范围内");
						 $('#planRepayLoanDt').datebox('setValue',"");
					 }
					
				}else if(repayloanDt!=""){
					 var newDate   =   DateAdd( "-d ",Number(v*str+1),new Date(repayloanDt));  
					 var dates = new Date(newDate.toLocaleDateString()).format('yyyy-MM-dd');
					 if(dates>=StartDt && dates<=EndDt){
						 $('#planOutLoanDt').datebox('setValue',str);
					 }else{
						 alert("计划开始日期不在授信日期范围内");
						 $('#planOutLoanDt').datebox('setValue',"");
					 }
				}
			}
		}
	}
}
		
// 日期格式化2014-04-04
Date.prototype.format = function(format){
	  var o = {
		  "M+" : this.getMonth()+1, //month
		  "d+" : this.getDate(),    //day
		  "h+" : this.getHours(),   //hour
		  "m+" : this.getMinutes(), //minute
		  "s+" : this.getSeconds(), //second
		  "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		  "S" : this.getMilliseconds() //millisecond
	  }
	  if(/(y+)/.test(format)) 
		  format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  for(var k in o)
		  if(new RegExp("("+ k +")").test(format))
	  format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
	  return format;
 }
