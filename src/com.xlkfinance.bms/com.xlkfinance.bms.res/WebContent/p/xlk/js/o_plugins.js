/**
 * Author: YiXiao
 * Date: 2014.08.22
 * Describe: 订单Module总加载器、通用组建
 */

define(function(require,exports,modules){
	var $oeb = require('../js/order_export_basic');
	var $oed = require('../js/order_export_detail');
(function(){
	var _order = {},
	_version = 'v0.0.1',
	logs = [];
	/**
	 * 方法描述：计算
	 * 参数类型：(计算符号，要计算的DOM元素(可为多个，逗号分隔)，显示计算结果的DOM元素(可不传递))
	 * 返回值：计算结果
	 * Author：YiXiao
	 */
	_order.operation = function(type,dom_name,return_dom){
		var val = null,
			m_val,
			valArray = dom_name.split(',');
		if(type.length > 1 || "+-*/".indexOf(type) < 0){
			return false;
		}
		for(var i=0;i<valArray.length;i++){
			m_val = document.getElementsByName(valArray[i])[0].value;
			if(!isNaN(m_val)){
				switch(type){
				case '+' :
					m_val = m_val == ''|| m_val == null ? m_val = 0 : m_val;
					val = val == null ? 0 : val;
					val = val + m_val;
					break;
				case '-' :
					m_val = m_val == ''|| m_val == null ? m_val = 0 : m_val;
					val = val == null ? m_val*2 : val;
					val = val - m_val;
					break;
				case '*' :
					m_val = m_val == ''|| m_val == null ? m_val = 0 : m_val;
					val = val == null ? 1 : val;
					val = val * m_val;
					break;
				case '/' :
					m_val = m_val == ''|| m_val == null ? m_val = 1 : m_val;
					val = val == null ? m_val*m_val : val;
					val = val / m_val;
					break;
				}
			}else{
				return false;
			}
		}
		if(return_dom){
			document.getElementsByName(return_dom)[0].value = val;
		}
		return val;
	};
	
	/**
	 * AJAX提交数据
	 */
	_order.ajax = function(act,map,flag,fun){
		map = typeof(map) == "object" ? map : {};
		flag = typeof(flag) == "object" ? flag : false;
		$.ajax({
	  	   type: "POST",
	  	   url: act,
	  	   data: map,
	  	   contentType: "application/x-www-form-urlencoded; charset=utf-8",
	  	   async: flag,
	  	   dataType:"json",
	  	   error:function(data,textstatus) {
	  		 alert("错误:"+data.responseText);
		       },
	  	   success: fun
		       
		})
	};
	
	/**
	 * 动态添加产品明细
	 */
	_order.addDetail = function(ts,id,data,dom_name){
		var tr_len = $('#'+id+'').find('tr').length + 1;
		$('#'+id+'').append('<tr index="'+tr_len+'" class="detail-tr'+tr_len+'"></tr>');
		for(var d in data){
			$('#'+id+'').find('tr.detail-tr'+tr_len+'').append(
					'<td>'+
					'<input type="hidden" mapping="'+dom_name[d]+'"'+
					'name="'+dom_name[d]+''+tr_len+'" value="'+data[d]+'">'+
					data[d]+
					'</td>'
					);
			$('input[name='+dom_name[d]+']').val('');
			$('textarea[name='+dom_name[d]+']').val('');
		}
		$('#'+id+'').find('tr.detail-tr'+tr_len+'').append(
				'<td><a href="javascript:void(0)" class="pro_modify">修改</a>'+
				' | <a href="javascript:void(0)" class="pro_del">删除</a></td>'
				);
		_order.operationDetail(ts);
		_order.operationModel();
	};
	
	/**
	 * 动态修改产品明细
	 */
	_order.updateDetail = function(ts,id,data,dom_name){
		if(!$(ts).attr('index') && $(ts).attr('index').length < 1){
			return false;
		}
			var u_index = $(ts).attr('index')-1;
			var n_index = $(ts).attr('index');
			$('#'+id+'').find('tr:eq('+u_index+')').html('');
			for(var d in data){
				$('#'+id+'').find('tr:eq('+u_index+')').append(
						'<td>'+
						'<input type="hidden" mapping="'+dom_name[d]+'"'+
						'name="'+dom_name[d]+''+n_index+'" value="'+data[d]+'">'+
						data[d]+
						'</td>'
						);
				$('input[name='+dom_name[d]+']').val('');
				$('textarea[name='+dom_name[d]+']').val('');
			}
			$('#'+id+'').find('tr:eq('+u_index+')').append(
					'<td><a href="javascript:void(0)" class="pro_modify">修改</a>'+
					' | <a href="javascript:void(0)" class="pro_del">删除</a></td>'
					);
			$(ts).attr('index','');
			$(ts).html('添加');
			_order.operationDetail(ts);
			_order.operationModel();
	}
	
	/**
	 * 删除明细
	 */
	_order.deleteDetail = function(ts){
		
	}
	
	/**
	 * 操作明细列表
	 */
	_order.operationDetail = function(ts){
		$('.pro_modify').unbind('click').bind('click',function(){
			$(this).parent().parent('tr').find('td').each(function(){
				var i_name = $(this).find('input').attr('mapping');
				var i_val =  $(this).find('input').val();
				$('input[name='+i_name+']').val(i_val);
				$('textarea[name='+i_name+']').val(i_val);
				})
				$(ts).attr('index',$(this).parent().parent('tr').attr('index'));
				$(ts).html('修改');
				_order.operationModel(this);
		})
	}
	
	/**
	 * 根据型号动态查找产品（从详情列表添加）
	 */
	_order.operationModel = function(ts){
		if($('#productModel').parent('div').find('.pm-span').length > 0){
			$('#productModel').parent('div').find('.pm-span').remove();
		}
		if(ts){
		var productName = $(ts).parent().parent('tr').find('input[name^=productName]').val(),
		productPlace = $(ts).parent().parent('tr').find('input[name^=productPlace]').val(),
		productBrand = $(ts).parent().parent('tr').find('input[name^=productBrand]').val();
			$('#productModel').after(
			'<span class="pm-span">'+
			productName+'&nbsp;&nbsp;&nbsp;'+
			productPlace+'&nbsp;&nbsp;&nbsp;'+
			productBrand+'&nbsp;&nbsp;&nbsp;'+
			'<input type="hidden" name="productName" value="'+productName+'" />'+
			'<input type="hidden" name="productPlace" value="'+productPlace+'" />'+
			'<input type="hidden" name="productBrand" value="'+productBrand+'" />'+
			'</span>'
					);
		}
	}
	
	/**
	 * 根据型号动态查找产品
	 */
	_order.ajaxAddModel = function(data){
		if($('#productModel').parent('div').find('.pm-ul').length > 0){
			$('#productModel').parent('div').find('.pm-ul').remove();
		}
		$('#productModel').parent('div').append(
				'<ul class="pm-ul">'+
				'</ul>'
				);
		for(var d in data){
			$('#productModel').parent('div').find('.pm-ul').append(
					'<li style="cursor:pointer;" class="pm-li">'+
					'<input type="hidden" name="pName" value="'+data[d].PRODUCT_NAME+'" />'+
					'<input type="hidden" name="pPlace" value="'+data[d].PRODUCT_PLACE+'" />'+
					'<input type="hidden" name="pBrand" value="'+data[d].PRODUCT_BRAND+'" />'+
					data[d].PRODUCT_MODEL+
					'</li>'
					);
		}
		$('.pm-li').unbind('click').bind('click',function(){
			if($('#productModel').parent().find('.pm-span').length > 0){
				$('#productModel').parent().find('.pm-span').remove();
			}
			$('#productModel').val($.trim($(this).text()));
			$('#productModel').after(
			'<span class="pm-span">'+
			$(this).find('input[name=pName]').val()+'&nbsp;&nbsp;&nbsp;'+
			$(this).find('input[name=pPlace]').val()+'&nbsp;&nbsp;&nbsp;'+
			$(this).find('input[name=pBrand]').val()+'&nbsp;&nbsp;&nbsp;'+
			'<input type="hidden" name="productName" value="'+$(this).find('input[name=pName]').val()+'" />'+
			'<input type="hidden" name="productPlace" value="'+$(this).find('input[name=pPlace]').val()+'" />'+
			'<input type="hidden" name="productBrand" value="'+$(this).find('input[name=pBrand]').val()+'" />'+
			'</span>'
					);
			if($('#productModel').parent('div').find('.pm-ul').length > 0){
				$('#productModel').parent('div').find('.pm-ul').remove();
			}
		})
	}
	
	_order.oed = $oed;
	_order.oeb = $oeb;
	
	 window.$o = _order;
})();
	
})