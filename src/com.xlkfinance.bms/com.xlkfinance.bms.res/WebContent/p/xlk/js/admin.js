define(function(require, exports, modules) {
	require("l/jqueryValidation/dist/jquery.validate.min.js");
    seajs.use("l/jqueryValidation/dist/localization/messages_zh.min.js");

    //新增请求验证
    $(".add_action form").validate({
        rules: {
            actName: {
                required: true
            },
            actPath: {
                required: true
            },
            actDescribe: {
            	required: true
            },
            actState: {
                required: true
            }
        },
        messages: {
            actName: {
            	required: "请求名称不可为空"
            },
            actPath: {
            	required: "请求路径不可为空"
            },
            actDescribe: {
            	required: "请求描述不可为空"
            },
            actState: {
                required: "状态不可为空"
            }
        }
    });

    //新增菜单
    $(".add_menu form").validate({
        rules: {
            menuPid: {
                required: true
            },
            menuName: {
                required: true
            },
            menuPath: {
                required: true
            },
            menuDescribe: {
                required: true
            },
            menuState: {
                required: true
            }
        },
        messages: {
            menuPid: {
                required: "父级不可为空"
            },
            menuName: {
                required: "名称不可为空"
            },
            menuPath: {
                required: "路径不可为空"
            },
            menuDescribe: {
                required: "描述不可为空"
            },
            menuState: {
                required: "状态不可为空"
            }
        }
    });
});