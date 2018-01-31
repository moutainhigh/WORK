//扩展easyui表单的验证
	$.extend($.fn.validatebox.defaults.rules, {
	    //数值
	    number: {//value值为文本框中的值
	        validator: function (value) {
	            var reg = /^\d+(\.\d+)?$/;
	            return reg.test(value);
	        },
	        message: '请输入正确的数值!'
	    },
	    //整数
	    integer: {//value值为文本框中的值
	        validator: function (value) {
	            var reg = /^-?[1-9]\d*$/;
	            return reg.test(value);
	        },
	        message: '请输入正确的整数!'
	    },
	    //下拉框必选
	    selrequired: {//value值为文本框中的值
	        validator: function (value) {
	            return value !='--请选择--';
	        },
	        message: '请选择数据!'
	    },
	    //手机号码
	    telephone: {//value值为文本框中的值
	        validator: function (value) {
	            var reg = /^1[3|4|5|6|7|8|9]\d{9}$/;
	            return reg.test(value);
	        },
	        message: '请输入正确的手机号码!'
	    },
	    //移动电话
	    mobile: {//value值为文本框中的值
	    	validator: function (value) {
	    		var reg = /^(\d{3,4})?-?\d{7,8}$/;
	    		return reg.test(value);
	    	},
	    	message: '请输入正确的移动电话!'
	    },
	  //移动电话
	    phone: {//value值为文本框中的值
	    	validator: function (value) {
	    		var reg = /^(\d{3,4})?-?\d{7,8}$/;
	    		var reg2 = /^1[3|4|5|8|9]\d{9}$/;
	    		return (reg.test(value)||reg2.test(value));
	    	},
	    	message: '请输入正确的电话号码!'
	    },
	    //邮箱
	    email: {//value值为文本框中的值
	    	validator: function (value) {
	    		var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    		return reg.test(value);
	    	},
	    	message: '请输入正确的邮箱号码!'
	    },
	    
	  //证件号码验证
	    certNumber: {//value值为文本框中的值
	        validator: function (value) {
	            var reg = /^1[3|4|5|8|9]\d{9}$/;
	            return reg.test(value);
	        },
	        message: '请输入正确的证件号码!'
	    },
	    //传真号码验证
	    faxNumber: {//value值为文本框中的值
	        validator: function (value) {
	            var reg = /^1[3|4|5|8|9]\d{9}$/;
	            return reg.test(value);
	        },
	        message: '请输入正确的传真号码!'
	    },
	    //
	    char: {//value值为文本框中的值
	    	validator: function (value) {
	            if (value) {
	                var sz = /^[a-z]*$/; 
	                if (sz.test(value)) { return true;}
	                else {return false;}
	            }else {return true;}
	        },
	        message: '必须是字母!'
	    },
	  //国内邮编验证
	    zipcode: {
	        validator: function (value) {
	            var reg = /^[1-9]\d{5}$/;
	            return reg.test(value);
	        },
	        message: '请输入正确的邮编号码！'
	    },
	    //验证汉字
	    CHS: {
	        validator: function (value) {//param的值为[]中值
	            if(value){
	            	var sz = /^[\u0391-\uFFE5]+$/;
	            	if(sz.test(value)){
	            		return true;
	            	}else{
	            		return false;
	            	}
	            }else{
	            	return true;
	            }
	        },
	        message: '此处必须是汉字!'
	    },
	    englishOrNum : {// 只能输入英文和数字
            validator : function(value) {
            	if (value.length < 6 || value.length > 18) {  
                    message = '密码长度必须在6至18范围';  
                    return false;  
                } else {  
                    if (!/^[a-zA-Z0-9]{1,}$/.test(value)) {  
                        message = '密码只能英文、数字组成';  
                        return false;  
                    } else {  
                        return true;  
                    }  
                } 
            },
            message : '请输入数字或者字母，6到18个字符!'
        },integ:{
	    	 validator: function (value) {
		            var reg =/^(?:0|[1-9][0-9]?|100)$/;
		            return reg.test(value);
		        },
	            message: '请输入0到100的整数!'
	          }
        ,appVersion:{
	    	 validator: function (value) {
		            var reg =/^[v][0-9]+[.][0-9]+[.][0-9]+$/;
		            return reg.test(value);
		        },
	            message: '请输入格式vx.x.x'
	          }
        ,checkBox:{
        	validator: function (value, param) {
        		var inputs = $(param[0]), 
        		maxNum = param[1], 
        		checkNum = 0,
        		status=false;
        		
        		inputs.each(function () { 
                    if (this.checked) checkNum++;
                });
        		
        		inputs.off('.checkbox').on('click.checkbox',function(){
                    $(this).focus();
                    var bool = inputs.validatebox('isValid');
                    if(bool) $(this).parent().tooltip('destroy');
                    else {
                        var tipMsg = $(this).validatebox.defaults.missingMessage 
                        || $(this).validatebox.defaults.rules.checkbox.message;
                        $(this).parent().tooltip({
                            position: 'right',
                            content: '<span style="color:#fff">'+tipMsg+'</span>',
                            onShow: function(){
                                $(this).tooltip('tip').css({
                                    backgroundColor: '#666',
                                    borderColor: '#666'
                                });
                            }
                        }).tooltip('show');
                    }
                });
                status = checkNum > 0 && checkNum < inputs.length + 1;
                return status;
        	},
        	message: '该选择组为必填字段!'
        },
        inputRadio: {  
            validator: function(value, param){  
                var input = $(param[0]),status = false;
                input.off('.radio').on('click.radio',function(){
                    $(this).focus();
                    try{ cntObj.tooltip('hide'); }catch(e){}
                });
                var firstObj = $(input[0]),cntObj = firstObj.parent(),initCount = cntObj.attr("initCount") || 0;
                cntObj.off("mouseover mouseout").on("mouseover mouseout",function(event){
                    var bool = input.validatebox('isValid');
                    if(event.type == "mouseover"){
                        if(bool) try{ cntObj.tooltip('hide'); }catch(e){}
                        else try{ cntObj.tooltip('show');}catch(e){}
                    }else if(event.type == "mouseout") try{ cntObj.tooltip('hide'); }catch(e){}
                });

                var tipMsg = firstObj.validatebox.defaults.missingMessage || firstObj.validatebox.defaults.rules.checkbox.message;
                if(initCount-1<0){
                    firstObj.parent().tooltip({
                        position: 'right',
                        content: '<span style="color:#fff">'+tipMsg+'</span>',
                        onShow: function(){
                            $(this).tooltip('tip').css({
                                backgroundColor: '#666',
                                borderColor: '#666'
                            });
                        }
                    }).tooltip('hide');
                    initCount = initCount - 0 + 1;
                    cntObj.attr("initCount",initCount);
                }

                status = $(param[0] + ':checked').val() != undefined;
                    
                return status;
            },  
            message: '该选择组为必填字段!'  
        },
	});