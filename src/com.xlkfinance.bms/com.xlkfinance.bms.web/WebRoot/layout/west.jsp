<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>

<%
    String gsonStr = "";
%>
<style type="text/css">
	.d{
		font-size: larger;
		font-size: large;s
	}
</style>
<script type="text/javascript">
/***********************************************************/
 	/**
	 * 设置字体  
	 */
	function setFontCss(treeId, treeNode) {
		var node = null;
		/* if(treeNode){
			if(treeNode.level == 0){
				return {"color":"black","font-weight":"bold","font-size":"20px"};
			}else if(treeNode.level == 1){
				return {'font-weight' : 'bold','color': '#DDDFFF'};
			}else{
				return null;
			}
		} */
		
	};
	// 提供打开新页签的方法
	function openNewTab(url,name,falg,key)
	{
		layout_center_addTabFun({
			falg : falg,
			title : name, //tab的名称
			closable : true, //是否显示关闭按钮
			content : '<iframe src="'
					+ url
					+ '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>' //加载链接
		});
	}
	//点击旗下公司关闭按钮，跳转到父页面
// 	function doSomething(key)
// 	{
// 		obj.get(key).doSomething();
// 		obj.remove(key);
// 	}
	
	function layout_center_refreshTab(title) {
		$('#layout_center_tabs').tabs('getTab', title).panel('refresh');
	}
	//关闭tab页签 
	function removePanel(){
        var tab = $('#layout_center_tabs').tabs('getSelected');
        if(tab){
            var index = $('#layout_center_tabs').tabs('getTabIndex',tab);
            $('#layout_center_tabs').tabs('close',index);
        }
    }
	
	function refreshTab(title) {
		$('#layout_center_tabs').tabs('getTab', title).panel('refresh');
	}


	 function onClick(e, treeId, node) {
		var url;
		if (node.url) {		
			url = node.url;
			if (url != '#') {
				url = sy.bpName()+url;
				layout_center_addTabFun({
					title : node.name, //tab的名称
					closable : true, //是否显示关闭按钮
					iconCls : node.iconCls,//图标
					content : '<iframe src="'
							+ url
							+ '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>' //加载链接
				});
			}
		}
	} 
	/* function createTab(e, treeId, node){
		var url = sy.bpName()+ node.url;
		$.ajax({
			url: url,
			type: 'get',
			contentType: 'text/html',
			success: function(result) {
				var subbody = $('#subbody').css('visibility', 'hidden').html(result);
				$.parser.parse(subbody);
				var div = $('<div></div>');
				$('#layout_center_tabs').tabs('add',{
					id: node.pid,
				    title: node.name,
				    content: div,
				    closable:true,
				    tools:[{
				        iconCls:'icon-mini-refresh',
				        handler:function(){
				            
				        }
				    }]
				});
				div.append(subbody.children().css('visibility', 'visible'));
			}	
		});
	} */
	/**
	 * zTree 属性设置
	 */
	var setting = {
		view : {
			selectedMulti : false,
			showLine:false,
			/* expandSpeed: "slow",  */
			dblClickExpand:false,
			fontCss : setFontCss
		},
		data : {
			key : {
				name : 'name',
				url: null
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : null
			}
		},
		edit : {
			enable : false,
			editNameSelectAll : true
		},
		callback: {
			onNodeCreated: this.onNodeCreated,
			beforeClick: this.beforeClick,
			onClick: onClick
		}
	};
	/**
	 * 初始化加载数据(构造树)
	 */
	function initTree() {
		$.ajax({
			url : "<%=basePath %>sysMenuController/queryMenuTree.action",
			type : 'post',
			cache: false,
			dataType : "json",
			success : function(result) {
				zTreeNodes = result;
				zTreeObj = $.fn.zTree.init($("#treeMenu"), setting, zTreeNodes);
				zTreeObj.expandAll(false);
				//zTreeObj.addNodes(null,{"id": "addParentNodeId","menuName": "添加根节点"});
			}
		});
	};
	
	$(document).ready(function(){
		initTree();	
		$('#treeMenu > li > span').each(function(){
			$(this).css('display','none');
		});
	});
/********************/
</script>
<script>
$(document).ready(function(){
	$('.level0').unbind('dblclick');
	$('.level0 > a,#treeMenu,.level0 > a > span').unbind('dblclick');
	$('body').delegate('.level0 > a ','click',function(){
		if(!$('.noline_open').parent().hasClass('active')){
			$('.noline_open').parent().find(' > ul').css('display','none');
			$('.noline_open').addClass('noline_close').removeClass('noline_open');
			}
		if($('#treeMenu .active').length){
			if(($(this).parent().attr('id')!=$('#treeMenu .active').attr('id'))){
				
				$('#treeMenu .active > span').trigger('click');	
				$('#treeMenu .level0').removeClass('active');
				$(this).parent().addClass('active');
				$(this).parent().find('> span').stop().trigger('click');	
			}else{
				$('#treeMenu .level0').removeClass('active');
				$(this).parent().find('> span').stop().trigger('click');
			}	
		}else{
			$('#treeMenu .level0').removeClass('active');
			$(this).parent().addClass('active');
			$(this).parent().find('> span').stop().trigger('click');	
		}
		
	});
	$('body').delegate('.level0 > ul > li > a ','click',function(){		
		$(this).parent().find('> span').stop().trigger('click');		
	});
});
</script>

<ul id="treeMenu" class="ztree" style="width: 198px; overflow: hidden;padding:0;"></ul>
