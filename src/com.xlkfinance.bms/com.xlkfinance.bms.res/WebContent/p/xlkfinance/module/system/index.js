var whichchannel=0;
document.addEvent('domready',function(){
	//隐藏网站名称
	//$("header").setStyle("display","none");
	//定位
	resetPosition();
	window.onresize=resetPosition;
	//随机背景色
	var colors=['ffffff','009cae','b63774','365aa7','06a1df','43a556','01bfe1','ff6501','237ae4','583bb4','5f9229'];
	var i=0;
	$$(".grid").each(function(item){
		item.setStyle('backgroundColor','#'+colors[i]);
		item.setStyle('color','#fff');
		item.setStyle('fontSize','16px');
		i++;
	});
	//滚动事件
	/* if(whichchannel==0) $("pre").setStyle("display","none");
	var myFx=new Fx.Tween("layout-middle",{
		duration:500,
		onComplete:function(){
			if(whichchannel>0) $("pre").setStyle("display","block");
			if(whichchannel<3) $("next").setStyle("display","block");
		}
	});
	document.addEvent("mousewheel",function(e){
		if(e.wheel<0){
			toLeft(myFx);	
		}else{
			toRight(myFx);
		}
	});
	$("pre").addEvent("click",function(){toRight(myFx);});
	$("next").addEvent("click",function(){toLeft(myFx);}); */
});
function resetPosition(){
	var clientWidth=document.body.clientWidth;
	var cW=$('#channel-list').width();
	clientWidth=clientWidth.toInt();
	//$$(".layout-middle")[0].style.marginLeft=(clientWidth-cW)/2-66;
}
function toLeft(myFx){
	if(myFx.isRunning()) return;
	if(whichchannel==3) return;
	whichchannel++;
	$("pre").setStyles({
		"display":"none",
		"marginLeft":$("pre").getStyle("marginLeft").toInt()+1040
	});	
	$("next").setStyles({
		"display":"none",
		"marginLeft":$("next").getStyle("marginLeft").toInt()+1040
	});
	mg=$$(".layout-middle")[0].getStyle("marginLeft");
	myFx.start("marginLeft",mg.toInt()-1040);
}
function toRight(myFx){
	if(myFx.isRunning()) return;
	if(whichchannel==0) return;
	whichchannel--;
	$("pre").setStyles({
		"display":"none",
		"marginLeft":$("pre").getStyle("marginLeft").toInt()-1040
	});	
	$("next").setStyles({
		"display":"none",
		"marginLeft":$("next").getStyle("marginLeft").toInt()-1040
	});
	mg=$$(".layout-middle")[0].getStyle("marginLeft");
	myFx.start("marginLeft",mg.toInt()+1040);
}
