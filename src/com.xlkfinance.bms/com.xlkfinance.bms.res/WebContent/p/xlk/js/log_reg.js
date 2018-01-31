define(function(require, exports, modules) {
    require("l/jqueryValidation/dist/jquery.validate.min.js");
    seajs.use("l/jqueryValidation/dist/localization/messages_zh.min.js");

    //调用弹窗
    var dialog = require('m/sea_modules/dialog');

    //检测注册名重复性
    exports.checkLoginName = function(){
        var loginName = $("#loginName"),
            name = loginName.val(),
            status = false;
        $.ajax({
            async: false,
            type:"GET",
            url:"checkLoginName.do",
            data:{loginName : name},
            success:function(data){
                if(data == -1){
                    loginName.next("label").html("用户名已存在").show();
                }else{
                    status = true;
                }
            }
        });
        return status;
    }    

    //注册表单验证
    $(".register form").validate({
        rules: {
            loginName: {
                required: true,
                username: true
            },
            loginPwd: {
                required: true,
                minlength: 6
            },
            repassword: {
                required: true,
                equalTo: "#loginPwd"
            },
            custEmail: {
                required: true,
                email: true
            },
            custMobile: {
                required: true,
                telphone: true
            },
            realName: {
                required: true
            },
            companyName: {
                required: true
            },
            verifycode: {
                required: true
            },
            clause: "required"
        },
        messages: {
            loginName: {
                required: "请输入用户名",
                username: "英文字母或数字，且长度为6~40"
            },
            loginPwd: {
                required: "请输入密码",
                minlength: "6~16个字符，区分大小写"
            },
            repassword: {
                equalTo: "两次输入的密码不一致"
            },
            custEmail: {
                required: "请输入邮箱地址",
                email: "请输入正确的邮箱地址"
            },
            custMobile: {
                required: "请输入手机号码",
                telphone: "请输入正确的手机号码"
            },
            realName: {
                required: "请输入真实姓名"
            },
            companyName: {
                required: "请输入企业名称"
            },
            clause: "请接受服务条款"
        }
    });

    $(".register form").submit(function(){
        if(!exports.checkLoginName()){
            $("input[name='loginName']").focus();
            return false;
        }
    });

    //登录验证
    $(".login form").validate({
        rules: {
            loginName: "required",
            loginPwd: "required",
            verifycode: "required"
        },
        messages: {
            loginName: "请输入用户名",
            loginPwd: "请输入密码",
            verifycode: "请输入验证码"
        }
    });

    //保留cookie上一次登录名
    var cookies = require("m/sea_modules/cookie");
    $(".login .btn").bind("click",function(){
        var loginName = $("#loginName").val();

        //设置用户名写入cookie
        cookies.set("loginName",loginName,60);

        $(".login form").trigger("submit");
    });

    //刷新验证码
    var startTime = new Date().getTime(),
        captchaImageSrc = $("#captchaImage").attr("src");
    $("#captchaImage").bind("click", function() {
        var o = $(this),
            endTime = new Date().getTime();
        ms = endTime - startTime;
        o.attr("src", captchaImageSrc + "?ms=" + ms);
    });

    //记录登录名称，cookie
    if($("div.login").length){
        var username = cookies.get("loginName");
        $("#loginName").val(username);
    };

    //占位
    var mod = require("./common/modules");
    mod.placeHolder("span.ph_label");

    //邮箱后缀
    var suffixArr = [
        "163.com",
        "qq.com",
        "126.com",
        "hotmail.com",
        "gmail.com",
        "sohu.com",
        "sina.com",
        "sina.cn",
        "yahoo.com",
        "139.com.cn",
        "189.cn"
        ],
        suffixList = $("ul.suffix"),
        custEmail = $("input[name='custEmail']");
    custEmail.keyup(function(){
        var _t = $(this),
            val = _t.val(),
            tmp = '';
        if(/^([a-z0-9_\.-]+)@?([a-z0-9_\.-]+)?$/.test(val)){
            var suffix = val.split('@')[1];
            for(var i in suffixArr){
                if(suffix === undefined){
                    tmp += '<li>'+ val + '@' + suffixArr[i] +'</li>';
                }else if(suffix === ''){
                    tmp += '<li>'+ val + suffixArr[i] +'</li>';
                }else{
                    if(eval("/" + suffix + "/").test(suffixArr[i])){
                        tmp += '<li>'+ val.split('@')[0] + '@' + suffixArr[i] +'</li>';
                    }
                }
            }
            suffixList.html(tmp).show();
        }else{
            suffixList.html('').hide();
        }
    }).blur(function(){
        setTimeout(function(){
            suffixList.hide();
        },500);
    });
    suffixList.delegate("li","click",function(){
        var _t = $(this),
            text = _t.text();
        custEmail.val(text).trigger("blur");
        _t.parent().hide();
    });

    //密码强度
    exports.checkPswStrength = function (val){
        var strengthLv = 0;
        if(val.match(/[a-z]/g)){strengthLv++;}
        if(val.match(/[0-9]/g)){strengthLv++;}
        if(val.match(/(.[^a-z0-9])/g)){strengthLv++;}
        if(val.length < 6){strengthLv = 0;}
        if(strengthLv > 3){strengthLv = 3;}
        return strengthLv;
    };
    $(".register input[name='loginPwd'],.forgot_password input[name='newPwd']").keyup(function(){
        var _t =$(this),
            strength = exports.checkPswStrength(_t.val()),
            pswStrengthDiv = $(".psw_strength"),
            cname = 'psw_strength';
        switch(strength){
            case 0:
            case 1:
            cname += ' pweak';
            break;
            case 2:
            cname += ' pmiddle';
            break;
            case 3:
            cname += ' pstrength';
            break;
        }
        pswStrengthDiv.get(0).className = cname;
    });

    $("input[name='loginName']").blur(function(){
        exports.checkLoginName();
    });

    //找回密码
    $(".forgot_password form").validate({
        rules: {
            loginName: {
                required: true,
                username: true
            },
            registered: "required",
            verifycode: "required",
            newPwd: {
                required: true,
                minlength: 6
            },
            confirmPwd: {
                required: true,
                equalTo: "#newPwd"
            }
        },
        messages: {
            loginName: {
                required: "请输入用户名",
                username: "英文字母或数字，且长度为6~40"
            },
            registered: "请输入注册手机或者注册邮箱",
            verifycode: "请先获取验证码",
            newPwd: {
                required: "请输入新密码",
                minlength: "6~16个字符，区分大小写"
            },
            confirmPwd:{
                required: "请确认新密码",
                equalTo: "两次输入的密码不一致"
            }
        }
    });
    var verifyId = '';
    $(".forgot_password form").submit(function(){
        var _t = $(this),
            verifyCode = $("input[name='verifycode']"),
            verifyCodeVal = verifyCode.val();
        if(verifyId === ''){
            verifyCode.focus().next("label").html("请先获取验证码").show();
            return false;
        }else{
            $.ajax({
                async: false,
                type: "GET",
                url: "verifyCode.do",
                data: {"codeKey":verifyCodeVal,"codeValue":verifyId},
                success: function(data){
                    if(data == 0){
                        verifyCode.focus().next("label").html("验证码错误").show();                        
                        return false;
                    }
                    var para = _t.serialize();
                    $.post("doRretrievePwd.do",para,function(data){
                        if(data > 0){
                            dialog.tip("找回密码成功",3);
                            setTimeout(function(){
                                location.href="login.do";
                            },3000);
                        }
                    });
                }
            });
        }
        
        return false;
    }).delegate("a.getCode","click",function(){
        var registered = $("#registered"),
            registeredVal = registered.val();
        if(!(/^1[3|5|7|8]\d{9}$|^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(registeredVal))){
            registered.next("label").html("请输入正确的手机或者邮箱").show();
        }else{
            var para,url;
            if(/^1[3|5|7|8]\d{9}$/.test(registeredVal)){
                para = {"phone": registeredVal};
                url = "getPhoneCode.do";
            }
            if(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(registeredVal)){
                para = {"email": registeredVal},
                url = "getEamilCode.do";
            }
            $.get(url,para,function(data){
                if(data){
                    verifyId = data;
                    alert("验证码已发送，请注意查收");
                }
            });
        }
    });
});
