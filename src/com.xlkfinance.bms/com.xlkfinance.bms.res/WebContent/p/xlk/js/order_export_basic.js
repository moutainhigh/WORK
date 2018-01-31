
define(function(require,exports,modules){

/**
 * 出口订单
 */

$(function(){
	$('.picking-mode1,.picking-mode2,.picking-mode3').unbind('click').bind('click',function(){
		var pickingMode = $('input:radio[name=pickingMode]:checked').val();
		if(pickingMode == '客户自送'){
			if($(this).parent('div').find('.picking-mode-box').length > 0){
				return false;
			}
			removePicking('picking-mode2,picking-mode3');
			$(this).parent('div').append(
					'<div class="picking-mode-box" style="display:none;">'+
					'<ol class="hide_item">'+
					'<li class="g_cf">'+
                    '<div class="subkey">送货地址：</div>'+
                    '<div class="subvalue">'+
                    	'石岩'+
                        '<input type="hidden" name="linkMan" value="石岩" />'+
                    '</div>'+
	                '</li>'+
					'<li class="g_cf">'+
                    '<div class="subkey">联系人</div>'+
                    '<div class="subvalue">'+
                        '<input name="linkMan" type="text" />'+
                    '</div>'+
	                '</li>'+
	                '<li class="g_cf">'+
	                    '<div class="subkey">联系电话</div>'+
	                    '<div class="subvalue">'+
	                        '<input name="linkPhone" type="text" />'+
	                    '</div>'+
	                '</li>'+
                        '</ol>'+
                        '</div>'
					);
			$(this).parent('div').find('.picking-mode-box').slideDown('500');
		}else if(pickingMode == '注册地提货'){
			if($(this).parent('div').find('.picking-mode-box').length > 0){
				return false;
			}
			removePicking('picking-mode1,picking-mode3');
			$(this).parent('div').append(
					'<div class="picking-mode-box" style="display:none;">'+
					'<ol class="hide_item">'+
					'<li class="g_cf">'+
                    '<div class="subkey">送货地址：</div>'+
                    '<div class="subvalue">'+
                        '<input name="" type="hidden" />'+
                    '</div>'+
	                '</li>'+
					'<li class="g_cf">'+
                    '<div class="subkey">联系人</div>'+
                    '<div class="subvalue">'+
                        '<input name="linkMan" type="text" />'+
                    '</div>'+
	                '</li>'+
	                '<li class="g_cf">'+
	                    '<div class="subkey">联系电话</div>'+
	                    '<div class="subvalue">'+
	                        '<input name="linkPhone" type="text" />'+
	                    '</div>'+
	                '</li>'+
                        '</ol>'+
                        '</div>'
					);
			$(this).parent('div').find('.picking-mode-box').slideDown('500');
		}else if(pickingMode == '加工厂提货'){
			if($(this).parent('div').find('.picking-mode-box').length > 0){
				return false;
			}
			removePicking('picking-mode1,picking-mode2');
			$(this).parent('div').append(
					'<div class="picking-mode-box" style="display:none;">'+
					'<br />&nbsp;&nbsp;<input name="plantMode" type="radio" value="整批" />整批'+
                    '&nbsp;<input name="plantMode"  type="radio" value="分批" />分批'+
                    '<input type="hidden" name="maxPlantMode" value="1" />'+
                     '<ol class="plant-mode-box1 hide_item">'+
                        '<li class="g_cf">'+
                            '<div class="subkey">联系人</div>'+
                            '<div class="subvalue">'+
                                '<input name="linkMan1" type="text" />'+
                            '</div>'+
                        '</li>'+
                        '<li class="g_cf">'+
                            '<div class="subkey">联系电话</div>'+
                            '<div class="subvalue">'+
                                '<input name="linkPhone1" type="text" />'+
                            '</div>'+
                        '</li>'+
                         '<li class="g_cf">'+
                            '<div class="subkey">提货联系人</div>'+
                            '<div class="subvalue">'+
                                '<input name="pickingLinkMan1" type="text" />'+
                            '</div>'+
                        '</li>'+
                        '<li class="g_cf">'+
                            '<div class="subkey">提货联系电话</div>'+
                            '<div class="subvalue">'+
                                '<input name="pickingLinkPhone1" type="text" />'+
                            '</div>'+
                        '</li>'+
                        '<li class="g_cf">'+
                           '<div class="subkey">验货时间</div>'+
                            '<div class="subvalue">'+
                                '<input name="inspectionTime1" type="text" />'+
                            '</div>'+
                        '</li>'+
                    '</ol>'+
                    '</div>'
			);
			$(this).parent('div').find('.picking-mode-box').slideDown('500',function(){
				$('input[name=plantMode]').unbind('click').bind('click',function(){
					plantModeSel($(this));
				})
				
			});
		}
	})
	
	/**
	 * 根据型号动态查找产品绑定事件
	 */
	$('#productModel').unbind('input propertychange').bind('input propertychange',function(){
		var mval = $(this).val();
		var map = {'pModel': mval};
		$o.ajax("../product/queryProductBasic.do",map,true,$o.ajaxAddModel);
	})
	
	/**
	 * 金额计算totalPrice
	 */
	$('input[name=opNumber],input[name=unitPrice]').unbind('input propertychange').bind('input propertychange',function(){
		$o.operation("*","opNumber,unitPrice","totalPrice");
	})
	
	
	/**
	 * 添加产品明细
	 */
	$('#o_detail').unbind('click').bind('click',function(){
		var productModel = $('input[name=productModel]').val();
		var productName = $('input[name=productName]').val();
		var productPlace = $('input[name=productPlace]').val();
		var productBrand = $('input[name=productBrand]').val();
		var opNumber = $('input[name=opNumber]').val();
		var unitPrice = $('input[name=unitPrice]').val();
		var totalPrice = $('input[name=totalPrice]').val();
		var boxNumber = $('input[name=boxNumber]').val();
		var grossWeight = $('input[name=grossWeight]').val();
		var netWeight = $('input[name=netWeight]').val();
		var shippingMark = $('input[name=shippingMark]').val();
		var accessories = $('input[name=accessories]').val();
		var opFunction = $('input[name=opFunction]').val();
		var remark = $('textarea[name=remark]').text();
		var data = {
				'1':productModel,
				'2':productName,
				'3':productPlace,
				'4':productBrand,
				'5':opNumber,
				'6':unitPrice,
				'7':totalPrice,
				'8':boxNumber,
				'9':grossWeight,
				'10':netWeight,
				'11':shippingMark,
				'12':accessories,
				'13':opFunction,
				'14':remark
				};
		var dom_name = {
				'1':'productModel',
				'2':'productName',
				'3':'productPlace',
				'4':'productBrand',
				'5':'opNumber',
				'6':'unitPrice',
				'7':'totalPrice',
				'8':'boxNumber',
				'9':'grossWeight',
				'10':'netWeight',
				'11':'shippingMark',
				'12':'accessories',
				'13':'opFunction',
				'14':'remark'
				};
		if($(this).attr('index') &&  $(this).attr('index').length > 0){
			$o.updateDetail(this,'detail-list',data,dom_name);
		}else{
		$o.addDetail(this,'detail-list',data,dom_name);
		}
	})
	
})

function plantModeSel(ts){
			var plantMode = $('input:radio[name=plantMode]:checked').val();
			if(plantMode == '分批'){
				if(ts.parent('div').find('.add-pantmode-addr').length == 0){
				ts.parent('div').append('<a class="add-pantmode-addr" href="javascript:void(0)" ">新增地址</a>')
				}else{
					return false;
				}
				
				$('.add-pantmode-addr').unbind('click').bind('click',function(){
					addPlantMode($(this));
				})
				
			}else if(plantMode == '整批'){
				if(ts.parent('div').after().find('.add-pantmode-addr').length > 0){
					ts.parent('div').after().find('.add-pantmode-addr').remove();
				}
				ts.parent('div').find('ol').each(function(i){
					if(i != 0){
						$(this).remove();
					}
				})
			}
	}

function removePicking(cls){
	var d;
	for(var i = 0; i <cls.split(',').length;i++){
		var c = cls.split(',')[i];
		if($('.'+c).parent('div').find('.picking-mode-box').length > 0){
			d = c;
			$('.'+d).parent('div').find('.picking-mode-box').slideUp('500',function(){
				$('.'+d).parent('div').find('.picking-mode-box').remove();
				//alert($('.'+c).parent('div').html());
			});
		}
	}
}


function addPlantMode(ts){
	var pmNum = ts.parent('div').find('ol').length + 1;
	ts.parent('div').find('>ol:last').after(
			'<ol class="plant-mode-box'+pmNum+' hide_item" style="display:none;">'+
            '<li class="g_cf">'+
                '<div class="subkey">联系人</div>'+
                '<div class="subvalue">'+
                    '<input name="linkMan'+pmNum+'" type="text" />'+
                '</div>'+
            '</li>'+
            '<li class="g_cf">'+
                '<div class="subkey">联系电话</div>'+
                '<div class="subvalue">'+
                    '<input name="linkPhone'+pmNum+'" type="text" />'+
                '</div>'+
            '</li>'+
             '<li class="g_cf">'+
                '<div class="subkey">提货联系人</div>'+
                '<div class="subvalue">'+
                    '<input name="pickingLinkMan'+pmNum+'" type="text" />'+
                '</div>'+
            '</li>'+
            '<li class="g_cf">'+
                '<div class="subkey">提货联系电话</div>'+
                '<div class="subvalue">'+
                    '<input name="pickingLinkPhone'+pmNum+'" type="text" />'+
                '</div>'+
            '</li>'+
            '<li class="g_cf">'+
               '<div class="subkey">验货时间</div>'+
                '<div class="subvalue">'+
                    '<input name="inspectionTime'+pmNum+'" type="text" />'+
                '</div>'+
            '</li>'+
        '</ol>'
			);
	$('.plant-mode-box'+pmNum+'').slideDown('500');
	$('input[name=maxPlantMode]').val(pmNum);
}

})