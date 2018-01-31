define(function(require, exports, modules) {
	require("l/jqueryValidation/dist/jquery.validate.min.js");
    seajs.use("l/jqueryValidation/dist/localization/messages_zh.min.js");

    //站内信验证
    $(".add_internal form").validate({
        rules: {
            msgTitle: {
                required: true
            },
            msgPhone: {
                required: true,
                telphone: true
            },
            msgContent: {
            	required: true
            }
        },
        messages: {
            msgTitle: {
            	required: "标题不可为空"
            },
            msgPhone: {
            	required: "手机不可为空",
            	telphone: "示例：13598765432"
            },
            msgContent: {
            	required: "内容不可为空"
            }
        }
    });

    //留言评论验证
    $(".add_feedback form").validate({
        rules: {
            commentTitle: {
                required: true
            },
            commentContent: {
                required: true
            }
        },
        messages: {
            commentTitle: {
                required: "标题不可为空"
            },
            commentContent: {
                required: "内容不可为空"
            }
        }
    });
});