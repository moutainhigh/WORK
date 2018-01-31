define(function(require, exports, modules) {

	//ajax匹配数据，下拉可选取
	modules.exports.dropSelect = function(obj,dataFun){
		var o = $(obj),
			width = o.outerWidth(),
			height = o.outerHeight(),
			wrap = $('<div class="paraWrap"></div>'),
			list = $('<ul class="paraList"></ul>').width(width-2).css("top",height);
			if(!o.closest("div").is(".paraWrap")){
				o.wrap(wrap);
				o.after(list);
			}
		var that = this;
		o.each(function(){
			$(this).on({
				"keyup":function(e){
					var val = $(this).val();
					if(that.keyIsNumOrChar(e)){
						dataFun(val,list);
					};
				},
				"focus":function(e){
					var val = $(this).val();
					dataFun(val,list);
				},
				"blur":function(){
					var _t =$(this);
					setTimeout(function(){
						_t.next().hide();
					},200);
				}
			});
		});

		o.next(".paraList").delegate("li","click",function(){
			var _t = $(this);
			o.val(_t.text());
			o.siblings("ul.paraList").hide();
		});
	}
	//返回键盘值为数字 字母 空格 退格
	modules.exports.keyIsNumOrChar = function(e){
		var val = (e || window.e).keyCode;
		return (val >= 96 && val<=105) || (val >= 48 && val<= 57) || (val >= 65 && val<= 90) || (val == 32) || (val == 8);
	}
	//placeholder占位
	modules.exports.placeHolder = function(label){
		$(label).each(function(){
			var _t = $(this),
				input = _t.next();
			_t.bind("click",function(){
				input.trigger("focus");
			});
			isNull(input);
			input.focus(function(){
				$(this).closest("div").addClass("ph_focus");
			}).blur(function(){
				$(this).closest("div").removeClass("ph_focus");
			}).keyup(function(){
				isNull($(this));
			}).change(function(){
				isNull($(this));
			});
		});
		function isNull(obj){
			obj.val() ? obj.closest("div").addClass("ph_hide") : obj.closest("div").removeClass("ph_hide");
		}
	}
	//子级项操作radio
	modules.exports.displaySubItems = function(radios){
		$(radios).each(function(){
	        var _t = $(this);
	        _t.is(":checked") ? _t.nextAll(".hide_item").show() : '';
	        _t.bind("click",function(){
				_t.is(":checked") ? _t.nextAll(".hide_item").slideDown() : '';
			});
	    });
	}
	modules.exports.subItemsFocus = function(obj){
		$(obj).bind("click",function(){
	        var _t = $(this);
	        _t.addClass("l_cur").siblings().removeClass("l_cur");
	    }).find("input[type='text']").bind("click",function(){
	        var _t = $(this);
	        _t.closest("label").find("input[type='radio']").prop("checked",true);
	        return false;
	    });
	}
	$("input[type='radio']").bind("click",function(e){
        var
        _t = $(this),
        name = _t.prop("name");
        $("input[name="+ name +"]").filter(function(){
            return !$(this).is(e.target);
        }).nextAll(".hide_item").slideUp();
    });

    //判断表单项是否为空  未选
    exports.itemIsNull = function(obj){
    	if(obj.is(":visible")){
	    	var type = obj.prop("type");
	    	if(type === "text" || obj.is("textarea")){
	    		if(obj.val() === ''){
	    			obj.focus();
					return;
	    		}else{
	    			return true;
	    		}
	    	}else if(type === "radio"){
	    		if(!obj.is(":checked")){
	    			obj.focus();
	    			return;
	    		}else{
					return true;
	    		}
	    	}
    	}else{
    		return true;
    	}
    }

    //下单步骤
    exports.stepCtrl = function(stepObj,targetObj){
    	var steps = $(stepObj),
    		targets = $(targetObj);
    	steps.delegate("li","click",function(){
    		var val = $(this).text();
    		var toScroll = targets.filter(function(){
    			var turn = $(this).data("turn");
    			if(val == turn){
    				return this;
    			}
    		}).offset().top - 10;
    		$("html,body").animate({scrollTop:toScroll},300);
    	});

    	var setStepPostion = function(){
    		var stepsTopPara =[], scrollTop = $(window).scrollTop();
	    	targets.each(function(i){
	    		stepsTopPara[i] = $(this).offset().top - 80;
	    	});

	    	for (var i=0; i<stepsTopPara.length; i++){
	    		if (i == stepsTopPara.length -1){
	    			if(scrollTop >= stepsTopPara[i]){
		    			steps.find("li").eq(i).addClass("cur").siblings().removeClass("cur");
		    		}
	    		}else{
	    			if(scrollTop >= stepsTopPara[i] && scrollTop < stepsTopPara[i+1]){
		    			steps.find("li").eq(i).addClass("cur").siblings().removeClass("cur");
		    		}
	    		}
	    	}
    	}
    	setStepPostion();
    	$(window).scroll(function(){
			setStepPostion();
    	});
    }
});