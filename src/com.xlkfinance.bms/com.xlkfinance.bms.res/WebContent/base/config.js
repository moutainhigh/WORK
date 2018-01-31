/**
 * js配置文件
 * 
 */

seajs.config({
	// 别名配置
	alias: {
		'jquery': 'l/jquery/jquery.min.js'
	},
	// 路径配置
	paths: {
		
	},

	// 映射
	map: [
		['f=','../f=']
	],

	// 预加载项
	preload: [
		window.jQuery ? '' : 'jquery'
	],

	// Sea.js 的基础路径
	base: 'http://res.xinlikang.com/',

	// 调试模式
	debug: false
});

//地址配置
if (typeof csc=="undefined") var XLK = {};
XLK.onlineWebRoot = "http://127.0.0.1:8080/";
XLK.res="http://192.168.1.143/";