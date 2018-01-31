define(function(require,exports,module){
	require("l/SWFUpload/v2.2.0.1/swfupload.js");
    require("l/My97DatePicker/4.8/WdatePicker.js");
    require("m/sea_modules/handlers.js");
	//文件上传
    if($("div.add_product").length){
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload1",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传产品说明"
        }));	
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload2",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传产品说明"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload3",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传产品说明"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload4",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传产品说明"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload5",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传产品说明"
        }));
    }

    //验证
    require("l/jqueryValidation/dist/jquery.validate.min.js");
    seajs.use("l/jqueryValidation/dist/localization/messages_zh.min.js");
    //新增产品验证
    $(".add_product form").validate({
        rules: {
            product_type: {
                required: true
            },
            product_name: {
                required: true
            },
            product_model: {
                required: true
            },
            product_brand: {
                required: true
            },
            product_place: {
                required: true
            }
        },
        messages: {
            product_type: {
                required: "产品类型不可为空"
            },
            product_name: {
                required: "产品名称不可为空"
            },
            product_model: {
                required: "产品型号不可为空"
            },
            product_brand: {
                required: "产品品牌不可为空"
            },
            product_place: {
                required: "产品产地不可为空"
            }
        }
    });
});