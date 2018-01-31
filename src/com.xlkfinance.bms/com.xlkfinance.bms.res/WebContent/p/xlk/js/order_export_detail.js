/**
 * 订单明细
 */
$(function(){

//  	var dataMap = {
//  					'id': id
//  					};
	var bHeight = $(document).height();
	$(document).find('body').append(
			'<iframe id="order_detail" style="display:none;" frameborder=no width="100%" height="'+bHeight+'px" '+
			'src="../order/edit_order_export.ftl"></iframe>'
			);
	$('.order-detail').unbind('click').bind('click',function(){
		var oId = $(this).parent().parent('tr').find('input[name=order_detail_id]').val();
		$.ajax({
			type : 'POST',
			//contentType : 'application/json',   
			url : 'configure.do?o_id='+oId,
			async: false,
			dataType : 'json',
			data: oId,
			error : function(data,textstatus){
				alert("错误:"+data.responseText);
			},
			success : function(data){
				$('.content').fadeOut('500',function(){
					$('#order_detail').fadeIn('500');
				});
				$(document).find('html').css('overflow-y','hidden');
				var iBody = document.getElementById('order_detail').contentWindow.document.body;
				$(iBody).find('input').each(function(){
					var iname = $(this).attr('name');
					for(var map in data){
						for(var od in data[map].orderDeliveryList){
							if(data[map].orderDeliveryList[od][iname]){
								$(this).val(data[map].orderDeliveryList[od][iname]);
							}
						}
						for(var od2 in data[map].orderExportList){
							if(data[map].orderExportList[od2][iname]){
								$(this).val(data[map].orderExportList[od2][iname]);
							}
						}
						for(var od3 in data[map].productDetailList){
							if(data[map].productDetailList[od3][iname]){
								$(this).val(data[map].productDetailList[od3][iname]);
							}
						}
					}
				})
			}
		})
		
	})
  		
})