define(function(require, exports, module) {
    require("l/SWFUpload/v2.2.0.1/swfupload.js");
    require("l/My97DatePicker/4.8/WdatePicker.js");
    require("m/sea_modules/handlers.js");

    //文件上传
    if(location.href.indexOf("/user/detail") !== -1){
        new SWFUpload(uploadSettings({
            button_placeholder_id: "picUpload1",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit: "2MB",
            file_upload_limit: 0,
            button_text_left_padding: 0,
            button_text: "上传组织机构证"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id: "picUpload2",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit: "2MB",
            file_upload_limit: 0,
            button_text_left_padding: 16,
            button_text: "开票资料"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id: "picUpload3",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit: "2MB",
            file_upload_limit: 0,
            button_text_left_padding: 16,
            button_text: "营业执照"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id: "picUpload4",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit: "2MB",
            file_upload_limit: 0,
            button_text_left_padding: 12,
            button_text: "纳税登记证"
        }));
    }

    //客户信息tab切换
    var tab = require("m/sea_modules/tab.js"),
        tabIndex = $("input[name='cur']").val();
        switch(tabIndex){
            case "info" :
            tabIndex = 0;
            break;
            case "company" :
            tabIndex = 1;
            break;
            case "pwd" :
            tabIndex = 2;
            break;
            case "phone" :
            tabIndex = 3;
            break;
            case "email" :
            tabIndex = 4;
            break;
            case "verify" :
            tabIndex = 5;
            break;
        }
    tab(".tabs_hd li", ".tabs_bd .tabs_con", "click", "cur",tabIndex);

    //手机、邮箱验证
    var dialog = require('m/sea_modules/dialog');
    var verifyId = '';
    $(".m_base_info").delegate("a.email_verify,a.phone_verify,a.get_code","click",function(){
        var _t = $(this), url,
            data = _t.prev("span").text(),
            msg = '<form class="form_bd pop_form_bd"><ul>';
        if(_t.is(".phone_verify")){
            msg += '<li class="g_cf"><div class="key">手机号：</div><div class="value"><input id="phone_code" disabled value="'+ data +'"><a href="javascript:" class="get_code get_phone">获取验证码</a></div></li><li class="g_cf"><div class="key"><span class="star">*</span>验证码：</div><div class="value"><input id="verify_code"></div></li>';
        }else{
            msg += '<li class="g_cf"><div class="key">邮箱：</div><div class="value"><input id="email_code" disabled value="'+ data +'"><a href="javascript:" class="get_code get_email">获取验证码</a></div></li><li class="g_cf"><div class="key"><span class="star">*</span>验证码：</div><div class="value"><input id="verify_code"></div></li>';
        }
        msg += '<li id="msg_err"><div class="key"></div><div class="value g_fs12"></div></li></ul></form>';
        dialog.confirm(msg,function(){
            var verifyCode = $("#verify_code").val();
            if(verifyId == ''){
               $("#msg_err div.value").removeClass("green").addClass("red").html('请先获取验证码');
                return false; 
            }
            if(verifyCode == ''){
                $("#msg_err div.value").removeClass("green").addClass("red").html('请输入验证码');
                return false;
            }else{
                $.get("../verifyCode.do",{"codeKey":verifyCode,"codeValue":verifyId},function(data){
                    var url;
                    _t.is(".email_verify") ? url = "updateCustEmailState.do" : url = "updateCustMobileState.do";
                    if(data > 0){
                        $.post(url,{},function(data){
                            if(data > 0){
                                dialog.tip("验证成功",3);
                                _t.before('<span class="orange">已验证</span>').remove();
                            }
                        });
                        
                    }else{
                        dialog.tip("验证失败",3);
                    }
                });
            }
            verifyId ='';
        });
    });

    //获取验证码
    $(document).delegate(".get_code","click",function(){
        var _t = $(this),
            data = _t.prev().val(),
            url,
            para;
        if(_t.is(".get_email")){
            para = {"email": data};
            url = "../getEamilCode.do";
        }else{
            para = {"phone": data},
            url = "../getPhoneCode.do";
        }
        $.get(url,para,function(data){
            if(data){
                verifyId = data;
                $("#msg_err div.value").removeClass("red").addClass("green").html("验证码已发送，请注意查收");
            }
        });
    });

    //收货地址
    var area = require("m/sea_modules/area.js");
    area.getArea("#province","#city","#area","","","");

    //收货地址表单验证
    require("l/jqueryValidation/dist/jquery.validate.min.js");
    seajs.use("l/jqueryValidation/dist/localization/messages_zh.min.js");
    $(".add_address form").validate({
        rules: {
            custpicName: {
                required: true
            },
            province: {
                required: true
            },
            city: {
                required: true
            },
            area: {
                required: true
            },
            custAddress: {
                required: true
            },
            custpicPhone: {
                required: true,
                telphone: true
            },
            custpicTelephone: {
                required: true,
                phone: true
            }
        },
        messages: {
            custpicName: {
                required: "请输入收货人"
            },
            province: {
                required: "请选择省"
            },
            city: {
                required: "请选择市"
            },
            area: {
                required: "请选择区"
            },
            custAddress: {
                required: "请输入详细地址"
            },
            custpicPhone: {
                required: "请输入手机号码",
                telphone: "示例：13598765432"
            },
            custpicTelephone: {
                required: "请输入电话号码",
                phone: "示例：0755-8888888 / 13598765432"
            }
        }
    });
    //收货地址编辑删除
    var dialog = require('m/sea_modules/dialog');
    $("#address_manage_list").delegate("a.del,a.edit","click",function(){
        var _t = $(this),
            id = _t.data("id"),
            form = $(".add_address form");
        if(_t.is(".del")){
            dialog.confirm("确定要删除该条数据吗？",function(){
                $.post("deleteAddress.do",{"addressId":id},function(){
                    window.location.reload();
                });
            });
        }else if(_t.is(".edit")){
            var para = {},
                paraList = _t.parent("td").siblings();
            paraList.each(function(){
                var 
                _t = $(this),
                name = _t.data("name");
                para[name] = $.trim(_t.text());
                if(_t.is(".area_td")){
                    var areaPara = _t.data("area").split("|");
                    area.getArea("#province","#city","#area",areaPara[0],areaPara[1],areaPara[2]);
                }
                if(name === "isDefault"){
                    para[name] === "默认" ? form.find("input[name="+ name +"]").prop("checked",true) : form.find("input[name="+ name +"]").prop("checked",false); 
                }else{
                    form.find("input[name="+ name +"],textarea[name="+ name +"]").val(para[name]);
                }
            });
            form.find(".btn").val("保存");
            form.prop("action","updateAddress.do").append('<input name="addressId" type="hidden" value="'+ id +'" />');
        }
    });

    //公司信息验证
    $(".m_company_info form").validate({
        rules: {
            companyName: {
                required: true
            },
            companyAddress: {
                required: true
            },
            companyCorporate: {
                required: true
            },
            organCode: {
                required: true
            },
            businessLicenseNo: {
                required: true
            },
            businessLicense: {
                required: true
            },
            taxRegistration: {
                required: true
            },
            organFile: {
                required: true
            },
            invoice: {
                required: true
            },
            taxRegNo: {
                required: true
            },
            invoiceAddressPhone: {
                required: true
            },
            invoiceBankNo: {
                required: true
            },
            contacts: {
                required: true
            },
            companyTelephone: {
                required: true
            },
            companyFax: {
                required: true,
                phone: true
            }
        },
        messages: {
            companyName: {
                required: "请输入公司全称"
            },
            companyAddress: {
                required: "请输入公司地址"
            },
            companyCorporate: {
                required: "请输入公司法人"
            },
            organCode: {
                required: "请输入组织机构代码/海关注册编码"
            },
            businessLicenseNo: {
                required: "请输入营业执照号"
            },
            businessLicense: {
                required: "请上传营业执照"
            },
            taxRegistration: {
                required: "请上传纳税登记证"
            },
            organFile: {
                required: "请上传组织机构代码证"
            },
            organFile: {
                required: "请上传开票资料"
            },
            taxRegNo: {
                required: "请输入纳税登记证号"
            },
            invoiceAddressPhone: {
                required: "请输入开票地址及电话"
            },
            invoiceBankNo: {
                required: "请输入开户行名称及帐号"
            },
            contacts: {
                required: "请输入联系人"
            },
            companyTelephone: {
                required: "请输入联系电话",
                phone: "示例： 0755-8888888 / 13598765432"
            },
            companyFax: {
                required: "请输入传真",
                fixedphone: "示例： 0755-8888888"
            }
        }
    });
    
    //修改密码验证
    $(".m_psw_update form").validate({
        rules: {
            yPwd: {
                required: true
            },
            loginPwd: {
                required: true,
                minlength: 6
            },
            qLoginPwd: {
                required: true,
                equalTo: "#loginPwd"
            }
        },
        messages: {
            yPwd: {
                required: "请输入原密码"
            },
            loginPwd: {
                required: "请输入新密码",
                minlength: "6~16个字符，区分大小写"
            },
            qLoginPwd: {
                required: "请确认新密码",
                equalTo: "两次输入的密码不一致"
            }
        }
    });

    //修改手机号验证
    $(".m_tel_update form").validate({
        rules: {
            yPhone: {
                required: true,
                telphone : true
            },
            custMobile: {
                required: true,
                telphone : true
            }
        },
        messages: {
            yPhone: {
                required: "请输入原手机号",
                telphone: "示例：13598765432"
            },
            custMobile: {
                required: "请输入新手机号",
                telphone: "示例：13598765432"
            }
        }
    });

    //修改邮箱地址验证
    $(".m_email_update form").validate({
        rules: {
            yEmail: {
                required: true,
                email : true
            },
            custEmail: {
                required: true,
                email : true
            }
        },
        messages: {
            yEmail: {
                required: "请输入原邮箱地址",
                email: "请输入正确的邮箱地址"
            },
            custEmail: {
                required: "请输入新邮箱地址",
                email: "请输入正确的邮箱地址"
            }
        }
    });

    //银行账号管理验证
    $(".add_bankAccount form").validate({
        rules: {
            accountNo: {
                required: true,
                isBank : true
            },
            bankName: {
                required: true
            },
            bankBranchName: {
                required: true
            },
            bankAddress: {
                required: true
            },
            unionPayNo: {
                isBank : true
            },
        },
        messages: {
            accountNo: {
                required: "银行账号不可为空",
                isBank: "国外银行账号，由半角英文字符和数字组成"
            },
            bankName: {
                required: "银行名称不可为空"
            },
            bankBranchName: {
                required: "分行名称不可为空"
            },
            bankAddress: {
                required: "银行地址不可为空"
            },
            unionPayNo: {
                isBank: "国外账号，由半角英文字符和数字组成"
            }
        }
    });

    //银行账号删除、编辑
    $(".bank_account").delegate("a.del,a.edit","click",function(){
        var _t = $(this),
            id = _t.data("id"),
            form = $(".add_bankAccount form"),
            partyId = $("input[name='partyId']").val();
        if(_t.is(".del")){
            dialog.confirm("确定要删除该条数据吗？",function(){
                $.post("deleteBank.do",{"bankId":id},function(){
                    window.location.reload();
                });
            });
        }else if(_t.is(".edit")){
            var para = {},
                paraList = _t.parent("td").siblings();
            paraList.each(function(){
                var 
                _t = $(this),
                name = _t.data("name");
                para[name] = $.trim(_t.text());
                if(_t.is(".accountType")){
                    form.find("input[value='"+ para[name] +"']").prop("checked",true);
                }else if(name === "isDefault"){
                    para[name] === "默认" ? form.find("input[name="+ name +"]").prop("checked",true) : form.find("input[name="+ name +"]").prop("checked",false);
                }else{
                    form.find("input[name="+ name +"]").val(para[name]);
                }
            });
            form.find(".btn").val("保存");
            form.prop("action","updateBank.do").append('<input name="bankId" type="hidden" value="'+ id +'" />');
        }
    });
});
