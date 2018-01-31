define(function(require,exports,module){
	require("http://res.xinlikang.com/l/SWFUpload/v2.2.0.1/swfupload.js");
    require("http://res.xinlikang.com/l/My97DatePicker/4.8/WdatePicker.js");
    require("http://res.xinlikang.com/m/sea_modules/handlers.js");

    if(location.href.indexOf("party/toAddBank") !== -1){
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload1",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:16,
            button_text:"点击上传"
        }));
    }
    
    //验证
    require("l/jqueryValidation/dist/jquery.validate.min.js");
    seajs.use("l/jqueryValidation/dist/localization/messages_zh.min.js");

    //提交与暂存
    $("input.submit_all").bind("click",function(){
        var _t = $(this),
            val = _t.val(),
            form = _t.parents("form");
        form.find(".submitType").val(val); 
        if(val === "提交"){
            exports.addTransactionFormValidate("v") && form.submit();
        }else{
            exports.addTransactionFormValidate("r") && form.submit();
        }
    });
    exports.addTransactionFormValidate = function(para){
        var vForm = $(".add_transaction form");
        vForm.validate({
            rules: {
                partyName: {
                    required: true,
                    halfString: true
                },
                region: {
                    required: true
                }
            },
            messages: {
                partyName: {
                    required: "交易方名称不能为空",
                    halfString: "由半角字符组成"
                },
                region: {
                    required: "所在地区不可为空"
                }
            }
        });
        if(para === "v"){
            return vForm.valid();
        }else{
            $("input[name='partyName']").rules("remove");
            $("input[name='region']").rules("remove");
            return true;
        }
    }

    //新增交易方地址验证
    $(".add_address form").validate({
        rules: {
            custpicName: {
                required: true
            },
            custpicPhone: {
                required: true,
                phone: true
            },
            custAddress: {
                required: true
            }
        },
        messages: {
            custpicName: {
                required: "联系人不能为空"
            },
            custpicPhone: {
                required: "联系电话不可为空",
                phone: "示例：0755-8888888 / 13598765432"
            },
            custAddress: {
                required: "收货地址不可为空"
            }
        }
    });

    //新增交易方地址删除、编辑
    var dialog = require('m/sea_modules/dialog');
    $(".t_address_manage").delegate("a.del,a.edit","click",function(){
        var _t = $(this),
            id = _t.data("id"),
            form = $(".add_address form"),
            partyId = $("input[name='partyId']").val();
        if(_t.is(".del")){
            dialog.confirm("确定要删除该条数据吗？",function(){
                $.post("deleteAddress.do?partyId=" + partyId,{"paId":id},function(){
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
                if(name === "isDefault"){
                    para[name] === "默认" ? form.find("input[name="+ name +"]").prop("checked",true) : form.find("input[name="+ name +"]").prop("checked",false); 
                }else{
                    form.find("input[name="+ name +"]").val(para[name]);
                }
                
            });
            form.find(".btn").val("保存");
            form.prop("action","updateAddress.do").append('<input name="paId" type="hidden" value="'+ id +'" />');
        }
    });

    //新增交易方银行账号验证
    $(".add_bankAccount form").validate({
        rules: {
            bankName: {
                required: true
            },
            bankBranchName: {
                required: true,
                halfString: true
            },
            accountNo: {
                required: true,
                isBank: true
            },
            bankFile: {
                required: true
            }
        },
        messages: {
            bankName: {
                required: "银行名称不能为空"
            },
            bankBranchName: {
                required: "开户行不可为空",
                halfString: "由半角字符和符号组成"
            },
            accountNo: {
                required: "银行账号不可为空",
                isBank: "由半角英文字符和数字组成"
            },
            bankFile: {
                required: "请上传银行资料"
            }
        }
    });

    //新增交易方地址删除、编辑
    $("#bankAccountList").delegate("a.del,a.edit","click",function(){
        var _t = $(this),
            id = _t.data("id"),
            form = $(".add_bankAccount form"),
            partyId = $("input[name='partyId']").val();
        if(_t.is(".del")){
            dialog.confirm("确定要删除该条数据吗？",function(){
                $.post("deleteBank.do?partyId=" + partyId,{"pbId":id},function(){
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
                if(name === "isDefault"){
                    para[name] === "默认" ? form.find("input[name="+ name +"]").prop("checked",true) : form.find("input[name="+ name +"]").prop("checked",false); 
                }else{
                    form.find("input[name="+ name +"]").val(para[name]);
                }
            });
            form.find(".btn").val("保存");
            form.prop("action","updateBank.do").append('<input name="pbId" type="hidden" value="'+ id +'" />');
        }
    });


    //下拉选择国家
    exports.getCountryData = function(val,list){
        $.get("findFndCtry.do",{"fndName":val},function(data){
            var tmp = '';
            if(!data || data.length){
                for(var i in data){
                    tmp += '<li class="g_e" data-info="'+ data[i].ctryNameCh +'|'+ data[i].ctryNameEn +'">' + data[i].ctryNameCh +'['+ data[i].ctryNameEn +']' +'</li>';
                };
                list.html(tmp).show();
            }
        },"json");
    }
    var mod = require("./common/modules");
    mod.dropSelect("input[name='region']",exports.getCountryData);

    //点选国家信息
    $(".add_transaction").delegate("ul.paraList>li","click",function(){
        var _t = $(this),
        info = _t.data("info").split("|");
        $("input[name='region']").val(info[0]);
    });

    //新增交易方
    $(".add_party").bind("click",function(){
        exports.loadFrame("新增交易方","toAdd.do")
    });
    $(".add_transaction form").submit(function(){
        var _t = $(this),
            action = _t.prop("action"),
            para = _t.serialize();
        $.post(action,para,function(data){
            if(data > 0){
                window.parent.location.reload();
            }
        })
        return false;
    });

    //加载 iframe
    exports.loadFrame = function(title,url){
        //原生artDialog
        var artDialog = require('l/artDialog/src/dialog');
        var d = artDialog({
            title: title,
            content: '<iframe width="800" src="'+ url +'" name="frameCon" frameborder="no" scrolling="yes" border="0" height="550"></iframe>',
            fixed: true
        }).showModal();
    }
    
    //修改交易方
    $(".supplier_search").delegate("a.edit,a.addr,a.account","click",function(){
        var _t = $(this),
            url = _t.data("href"),
            title;
        if(_t.is(".edit")){
            title = "修改交易方";
        }else if(_t.is(".addr")){
            title = "地址管理";
        }else{
            title = "账号管理";
        }
        exports.loadFrame(title,url);
    })
});

