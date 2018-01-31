define(function(require,exports,modules){
	require("l/SWFUpload/v2.2.0.1/swfupload.js");
    require("l/My97DatePicker/4.8/WdatePicker.js");
    require("m/sea_modules/handlers.js");

    //文件上传
    if(location.href.indexOf("import/list") < 0){
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload1",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传提货文件"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload2",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传提货文件"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload3",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"上传到货通知"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload4",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:16,
            button_text:"点击上传"
        })); 
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload5",
            file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:12,
            button_text:"供应商发票"
        }));
        new SWFUpload(uploadSettings({
            button_placeholder_id:"picUpload6",
            file_types: "*.xls;*.xlsx;",
            file_size_limit : "2MB",
            file_upload_limit : 0,
            button_text_left_padding:5,
            button_text:"批量上传产品",
            upload_success_handler: proBatchUploadSuccess
        }));
    }

    //公共模块
    var mod = require("./common/modules");
    //调用弹窗模块
    var dialog = require('m/sea_modules/dialog');

    //供应商
    exports.getPartyData = function(val,list){
        $.post("../../party/findParty.do",{"partyName":val},function(data){
            var tmp = '';
            for(var i in data){
                tmp += '<li class="g_e" data-id="' + data[i].partyId + '" data-info="'+ data[i].partyContacts +'|'+ data[i].partyTelephone +'|'+ data[i].phone +'">' + data[i].partyName +'</li>';
            };
            list.html(tmp).show();
        },"json");
    }
    mod.dropSelect("input[name='partyName']",exports.getPartyData);

    //点选附带供应商信息
    $(".f_supplier").delegate("ul.paraList>li","click",function(){
        var _t = $(this),
        info = _t.data("info").split("|"),
        id = _t.data("id"),
        $payModePayee = $("input[name='payModePayee']");
        $("input[name='partyContacts']").val(info[0]);
        $("input[name='partyTelephone']").val(info[1]);
        $("input[name='phone']").val(info[2]);
        $("input[name='prtId']").val(id);
        $payModePayee.val(_t.text()).trigger("focus");
        setTimeout(function(){
            $payModePayee.next("ul.paraList").find("li:contains('"+ _t.text() +"')").trigger("click").end();
            $payModePayee.blur()
        },500);
    });
    
    //产品
    exports.getProductData = function(val,list){
        $.post("../../product/queryProductBasic.do?pType=import",{"pModel":val},function(data){
            var tmp = '';
            for(var i in data){
                tmp += '<li class="g_e"  data-info="' + data[i].productModel + '|' + data[i].productName +'|'+ data[i].productBrand +'|'+ data[i].productPlace +'">' + data[i].productModel +'|'+ data[i].productName +'|'+ data[i].productBrand +'|'+ data[i].productPlace +'</li>';
            };
            list.html(tmp).show();
        },"json");
    }
    mod.dropSelect("input[name='productModel']",exports.getProductData);

    //点选附带产品信息
    $("#import_prodetail").delegate("ul.paraList>li","click",function(){
        var info = $(this).data("info").split("|");
        $("input[name='productModel']").val(info[0]);
        $("input[name='productName']").val(info[1]);
        $("input[name='productBrand']").val(info[2]);
        $("input[name='productPlace']").val(info[3]);
    });

    //新增、更新产品信息
    $("#import_add_pro").bind("click",function(){
        //产品字段空验证
        var model = $("input[name='productModel']"),
            opNum = $("input[name='opNumber']"),
            unitPrice = $("input[name='unitPrice']"),
            productBrand = $("input[name='productBrand']"),
            productPlace = $("input[name='productPlace']"),
            materialNo = $("input[name='materialNo']"),
            contractNo = $("input[name='contractNo']");
        if(!mod.itemIsNull(model)){
            dialog.tip("产品型号不能为空，请选择！",3);
            return false;
        }
        if(!mod.itemIsNull(productBrand)){
            dialog.tip("产品品牌不能为空，请填写！",3);
            return false;
        }
        if(!mod.itemIsNull(productPlace)){
            dialog.tip("产品产地不能为空，请填写！",3);
            return false;
        }
        if(!mod.itemIsNull(opNum)){
            dialog.tip("产品数量不能为空，请填写！",3);
            return false;
        }else{
            if(!/^[0-9.]+$/.test(opNum.val())){
                dialog.tip("产品数量为数字！",3);
                return false;
            }
        }
        if(!mod.itemIsNull(unitPrice)){
            dialog.tip("产品单价不能为空，请填写！",3);
            return false;
        }else{
            if(!/^[0-9.]+$/.test(unitPrice.val())){
                dialog.tip("产品单价为数字！",3);
                return false;
            }
        }
        if(materialNo.val() !== '' && !/^[0-9a-zA-Z]+$/.test(materialNo.val())){
            materialNo.focus();
            dialog.tip("料号为数字或者字母组成！",3);
            return false;
        }
        if(contractNo.val() !== '' && !/^[0-9a-zA-Z]+$/.test(contractNo.val())){
            contractNo.focus();
            dialog.tip("合同号/订单号为数字或者字母组成！",3);
            return false;
        }
        var 
        _t = $(this),
        proArea = $("#import_prodetail"),
        para = {},
        optId = _t.data("optId") || '';
        proArea.find("input[type='text']").each(function(){
            var
            _t = $(this),
            name = _t.prop("name"),
            val = _t.val();
            para[name] = val;
           
        });
        if(optId === ''){
            if(!proArea.find("ul.paraList li").length){
                model.focus();
                dialog.tip("此产品型号不存在，请选择系统审核过的产品型号",3);
                return false;
            }
            $.ajax({
                async: false,
                url: "../findByProductModel.do",
                type: "POST",
                success: function(data){
                    for (var i in data){
                        if(para.productModel == data[i].productModel){
                            dialog.tip("此产品型号已存在",3);
                            return false;
                        }
                    }
                    $.post("../../order/addProduct.do",para,function(data){
                        _updateImportProlist(data);
                    });
                    
                },
                dataType: "json"
            });
        }else{
            para.optId = optId;
            $.post("../../order/editProduct.do",para,function(data){
                _updateImportProlist(data);
            });
        }
        


        //刷新产品列表，包括新增、修改
        function _updateImportProlist(status){
            if(status > 0){
                $.ajax({
                    async: false,
                    type: "GET",
                    url: "../../order/findProductList.do",
                    data: {orderId:$("input[name='orderId']").val()},
                    success: function(data){
                        if(data){
                            $("#import_prolist_tbody").html(data);
                            _t.data("optId","").val("保存");
                            proArea.find("input[type='text']").val("");
                            exports.getProModelInfo('product');
                        }
                    },
                    dataType: "html"
                });
            }
        }
        var logTbody = $("#log_list_tbody");
        logTbody.html('');
    });

    //处理产品型号对应批次信息,operArea操作区域,product-产品列表区域，还有一种是出库方式区域
    exports.getProModelInfo = function(operArea){
        $.ajax({
            async: false,
            type: "GET",
            url: "../../order/import/productNumberList.do",
            data: {operArea:operArea,orderId:$("input[name='orderId']").val()},
            success: function(data){
            var proBatchTbody = $("#log_pro_list_tbody"),
                tmp = '';
            if(data){
                for(var i in data){
                    tmp += '<tr id = "'+ data[i].optId +'"><td><input type="checkbox" class="checkModel"></td><td data-name="pdtModel">'+ data[i].productModel +'</td><td data-name="pdtNumber"><input type="text" name="pdtNumber" data-surplus="'+ data[i].opSurplusNumber +'" value="'+ data[i].opSurplusNumber +'" data-total="'+ data[i].opNumber +'" class="num_txt"> / '+ data[i].opNumber +'</td></td></tr>';
                }
                proBatchTbody.html(tmp);
            }
        },
        dataType: "json"
        });
    }

    //删除、编辑产品
    $("#import_prolist_tbody").delegate("a.del,a.edit","click",function(){
        var _t = $(this),
            id = _t.data("id"),
            proArea = $("#import_prodetail");
        if(_t.is(".del")){
            dialog.confirm("确定要删除该条数据吗？",function(){
                $.post("../../order/deleteProduct.do",{"optId":id},function(data){
                    if(data > 0){
                        _t.parents("tr").remove();
                        exports.getProModelInfo('product');
                    }
                });
                $("#import_add_pro").data("optId","").val("保存");
                proArea.find("input[type='text']").val("");
                var logTbody = $("#log_list_tbody");
                logTbody.html('');
            });
            
        }else if(_t.is(".edit")){
            var para = {},
                paraList = _t.parent("td").siblings();
            paraList.each(function(){
                var 
                _t = $(this),
                name = _t.data("name");
                para[name] = _t.text();
                proArea.find("input[name="+ name +"]").val(para[name]);
            });
            $("#import_add_pro").data("optId",id).val("修改");
        }
    });

    //产品批量上传
    function proBatchUploadSuccess(file, serverData) {
        var response = eval('(' + serverData + ')');
        if (window.console) {
            console.info(file,serverData);
        }
        if (response.result) {
            var id = Number(file.id.split("_")[1]) + 1;
            $("#fileUrl" + id).val(response.filePath);
            $("#fileName" + id).val(response.fileName);
            var orderId = $("input[name='orderId']").val();
            $.post("../../leadingInProduct.do",{"fileUrl":response.filePath,"fileName":response.fileName,"orderId":orderId},function(data){
                if(data){
                    $("#import_prolist_tbody").html(data);
                }
            },"html");
        }
    }

    //出库整分批折叠
    var outLibsMode = $("input[name='outLibsMode']"),
        outLibsModeVal = $("input[name='outLibsMode']:checked").val();
    function isEntire(type){
        if(type == "整批"){
            $("li.un_entire").slideUp();
        }else{
            $("li.un_entire").slideDown();
        }
    }
    isEntire(outLibsModeVal);
    outLibsMode.bind("click",function(){
        var _t = $(this),
            type = _t.val();
        isEntire(type);
    });

    //出库信息
    var logTbody = $("#log_list_tbody"),
        proBatchTbody = $("#log_pro_list_tbody");
    $("#save_log").bind("click",function(){
        var 
        _t = $(this),
        para = {};
        para.orderId=$("input[name='orderId']").val();
        para.orderAddressId = $("select[name='orderAddressId']").find(":selected").val();
        if(!proBatchTbody.find(":checked").length){
            dialog.tip("请选择产品信息",3);
            return false;
        }

        para.oddtProductList = $("input[name='oddtProductList']").val();
        //发货方式
        var deliverMode = $("input[name='deliverMode']");
        if(!mod.itemIsNull(deliverMode)){
            dialog.tip("您还没选择国内物流发货方式，请选择！",3);
            return false;
        }
        var deliverModeVal = $("input[name='deliverMode']:checked").val();
        para.deliverMode = deliverModeVal;
        if(deliverModeVal === "自提"){
            var idmBillLading1 = $("input[name='idmBillLading1']");
            if(!mod.itemIsNull(idmBillLading1)){
                dialog.tip("您选择的是国内物流发货方式 -- 自提，请上传提货委托书！",3);
                return false;
            }
            para.idmBillLading1 = idmBillLading1.val();
            para.fileUrl4 = $("input[name='fileUrl4']").val();

        }else if(deliverModeVal === "发货"){
            para.transportMode = $("select[name='transportMode']").find(":selected").val();
            para.freightPay = $("select[name='freightPay']").find(":selected").val();
            var carrier = $("input[name='carrier']");
            if(!mod.itemIsNull(carrier)){
                dialog.tip("您选择的是国内物流发货方式 -- 发货，请选择承运方！",3);
                return false;
            }
            var carrierVal = $("input[name='carrier']:checked").val();
            para.carrier = carrierVal;
            if(carrierVal === "其他"){
                var carrier1 = $("input[name='carrier1']");
                if(!mod.itemIsNull(carrier1)){
                    dialog.tip("您选择的是国内物流发货方式 -- 发货 -- 承运方 -- 其他，请填写承运方！",3);
                    return false;
                }
                para.carrier1 = carrier1.val();
            }

            var pickUpTime = $("input[name='pickUpTime']");
            if(!mod.itemIsNull(pickUpTime)){
                dialog.tip("您选择的是国内物流发货方式 -- 发货，请填写收货时间！",3);
                return false;
            }
            para.pickUpTime = pickUpTime.val();
            para.remark = $("textarea[name='remark']").val();
        }
        para.leRemarks = $("textarea[name='leRemarks']").val();
        var optId = _t.data("optId") || '';
        if(optId ===''){
            url = "addDomestic.do";
        }else{
            url = "updateDomestic.do";
            para.oddtId = optId;
        }
        $.post(url,para,function(data){
            if(data){
                logTbody.html(data);
                $("input[name='pdtNumber'],input[name='idmBillLading1'],input[name='fileUrl4'],input[name='carrier1'],input[name='pickUpTime'],textarea[name='remark'],textarea[name='leRemarks']").val('');
                $("input[name='deliverMode'][value='配送']").trigger("click").click();
                $("input[name='carrier']").prop("checked",false);
                var last_id ="";
			    logTbody.find('tr').each(function(){
					last_id = last_id + $(this).attr('data-id')+",";
				})
                exports.getProModelInfo(last_id);
            }
        },"html");
        _t.data("optId","").val("保存出库信息");
    });
    
    //出库信息删除、编辑
    $(".log_list").delegate("a","click",function(){
        var _t = $(this),
            parent = _t.parents("tr"),
            oid = parent.data("id");
        if(_t.is(".del")){
            dialog.confirm("确定要删除该条数据吗？",function(){
                $.post("deleteDomestic.do",{"oddtId":oid},function(data){
                    if(data > 0){
                        _t.parents("tr").remove();
						var last_id ="";
					    logTbody.find('tr').each(function(){
					        last_id = last_id + $(this).attr('data-id')+",";
				        })
                        exports.getProModelInfo(last_id);
                    }
                });
            });
        }else if(_t.is(".edit")){
            var deliverMode = parent.find(".deliver_mode").data("info"),
                addressId = parent.find(".addr").data("addressid"),
                note = parent.find(".note").val();
            $.post("domesticDetail.do",{"oddtId":oid},function(data){
                if(data){
                    var addrBox = $(".delivery_addr");
                    addrBox.find("option[value='"+ addressId +"']").prop("selected",true);                    
                    if(data.oddtProductList){
                        var proTmp = '',
                            proData = eval('('+ data.oddtProductList +')');
                            var last_id ="";
					        logTbody.find('tr').each(function(){
					            last_id = last_id + $(this).attr('data-id')+",";
				            })
                            exports.getProModelInfo(last_id);
                            for(var i in proData){
                                $("#"+proData[i].optId).find("input.checkModel").prop("checked",true).end().find("input.num_txt").val(proData[i].pdtNumber).data("num",proData[i].pdtNumber);
                            }
                        $("#pro_batch_info").val(JSON.stringify(proData));
                    }
                    switch(deliverMode){
                        case '配送':
                            break;
                        case '自提':
                            $("input[name='idmBillLading1']").val(data.idmBillLading1);
                            $("input[name='fileUrl4']").val(data.fileUrl4);
                            break;
                        case '发货':
                            $("select[name='transportMode']").val(data.transportMode);
                            $("select[name='freightPay']").val(data.freightPay);
                            $("input[name='carrier'][value='"+ data.carrier +"']").prop("checked",true);
                            $("input[name='pickUpTime']").val(data.pickUpTime);
                            if(data.carrier1 !== ''){
                                $("input[name='carrier1']").val(data.carrier1);
                            }
                            if(data.remark !== ''){
                                $("textarea[name='remark']").val(data.remark);
                            }
                            break;
                    }
                    $("[value='"+ deliverMode +"']").trigger("click").click();
                    $("textarea[name='leRemarks']").val(data.leRemarks);
                }
            },"json");
            $("#save_log").data("optId",oid).val("确认修改");
        }
    });
    
    //出货方式点选产品信息转json
    var proBatchInfo = $("#pro_batch_info");
    proBatchTbody.delegate("input.checkModel","click",function(){        
        proBatchInfo.val(entireBatchToJson());
    }).delegate("input.num_txt","keyup",function(){
        var _t = $(this),
            surplus = _t.data("surplus"),
            total = _t.data("total"),
            num = _t.data("num");
        _t.val(_t.val().replace(/\D/g,''));
        curNum = _t.val();
        if(num === undefined){
            if(parseInt(curNum) > parseInt(total)){
                _t.val(total);
                dialog.tip("输入的产品数量不能大于最大剩余数量！",3);
                return false;
            }
        }
        if(parseInt(curNum) > parseInt(num) + parseInt(surplus)){
            _t.val(surplus);
            dialog.tip("输入的产品数量不能大于最大剩余数量！",3);
        }
        proBatchInfo.val(entireBatchToJson());
    });

    //拼合整分批信息json
    function entireBatchToJson(){
        var para = [],
        trList = proBatchTbody.find("tr");
        for(var i=0; i< trList.length;i++){
            if(trList.eq(i).find(".checkModel").is(":checked")){
                para[i] = {};
                para[i].optId = trList.eq(i).get(0).id;
                var tdList = trList.eq(i).find("td");
                for(var j=1; j< tdList.length; j++){
                    var name = tdList.eq(j).data("name"),
                        td = tdList.eq(j);
                    name && (name == "pdtNumber") ? para[i][name] = td.find("input").val() : para[i][name] = td.text();
                }
            }
        }
        var newPara = [];
        for(var i in para){
            if(para[i] !== null){
                newPara.push(para[i]);
            }
        }
        return JSON.stringify(para);
    }
    //刷新收货地址
    $(".addr_refresh").bind("click",function(){
        $.get("../../order/import/addressList.do",{},function(data){
            if(data){
                $(".delivery_addr").html(data);
            }
        });
    });


    //垫付，供应商银行账号操作
    mod.dropSelect("#df_s_txt",exports.getPartyData);
    $("#df_supplier").delegate("ul.paraList>li","click",function(){
        var _t = $(this),
        partyId = _t.data("id"),
        accountBox = $(".df_supplier_account");
        $.post("../../party/partyBankList.do",{"partyId":partyId},function(data){
            if(data.length){
                var tmp ='';
                for (var i in data){
                    tmp += '<option>' + data[i].accountNo + '</option>';
                }
                accountBox.slideDown().find("select").html(tmp);
            }else{
                accountBox.slideUp();
                dialog.tip("该供应商暂无对应银行账号",3);
            }
        },"json");
    });

    //分批的产品型号选择
    exports.getBatchProductData = function(val,list){
        $.post("../findByProductModel.do",{"productModel":val},function(data){
            var tmp = '';
            for(var i in data){
                tmp += '<li class="g_e"  data-info="' + data[i].productModel + '|' + data[i].productName +'|'+ data[i].productBrand +'|'+ data[i].productPlace +'">' + data[i].productModel +'|'+ data[i].productName +'|'+ data[i].productBrand +'|'+ data[i].productPlace +'</li>';
            };
            list.html(tmp).show();
        },"json");
    }
    mod.dropSelect("input[name='pdtModel']",exports.getBatchProductData);

    //点选附带产品信息
    $("#entire_batch_box").delegate("ul.paraList>li","click",function(){
        var info = $(this).data("info").split("|");
        $("input[name='pdtModel']").val(info[0]);
    });

    //子级项操作
    mod.displaySubItems("input[type='radio'].has_item");
    mod.subItemsFocus(".hide_item li");

    if($("input[name='partyName']").val() !== ''){
        $("input[name='partyName']").focus().blur();
    }

    //表单验证
    exports.formValidate = function(){
        //供应商
        var partyName = $("input[name='partyName']");
        if(!mod.itemIsNull(partyName)){
            dialog.tip("您还没填写供应商名称，请填写！",3);
            return false;
        }
        if(!partyName.next("ul.paraList").find("li").length){
            partyName.focus();
            dialog.tip("此供应商名称不存在，请选择系统存在的供应商，或者新增供应商！",3);
            return false;
        }

        //货款支付
        var payMode = $("input[name='payMode']");
        if(!mod.itemIsNull(payMode)){
            dialog.tip("您还没选择货款支付类型，请选择！",3);
            return false;
        }

        var payModeVal = $("input[name='payMode']:checked").val();
        if(payModeVal === "垫付"){
            var payModePayee = $("input[name='payModePayee']");
            if(!mod.itemIsNull(payModePayee)){
                dialog.tip("您还没填写收款方名称，请填写！",3);
                return false;
            }

            var payMode1 = $("input[name='payMode1']");
            if(!mod.itemIsNull(payMode1)){
                dialog.tip("您还没选择垫付类型，请选择！",3);
                return false;
            }

            var payMode1Val = $("input[name='payMode1']:checked").val();
            if(payMode1Val === "COD"){
                var payMode2_1 = $("input[name='payMode2_1']");
                if(!mod.itemIsNull(payMode2_1)){
                    dialog.tip("您选择的是垫付 -- COD，请填写付款比例！",3);
                    return false;
                }
            }else if(payMode1Val === "TT"){
                var payMode3_1 = $("input[name='payMode3_1']");
                if(!mod.itemIsNull(payMode3_1)){
                    dialog.tip("您选择的是垫付 -- TT，请填写付款比例！",3);
                    return false;
                }
                var payMode3_2 = $("input[name='payMode3_2']");
                if(!mod.itemIsNull(payMode3_2)){
                    dialog.tip("您选择的是垫付 -- TT，请填写交期！",3);
                    return false;
                }
            }else if(payMode1Val === "期票"){
                var payMode4_1 = $("input[name='payMode4_1']");
                if(!mod.itemIsNull(payMode4_1)){
                    dialog.tip("您选择的是垫付 -- 期票，请填写期限！",3);
                    return false;
                }
            }
        }

        // //境内结算方式
        var countMode = $("input[name='countMode']");
        if(!mod.itemIsNull(countMode)){
            dialog.tip("您还没选择境内结算方式，请选择！",3);
            return false;
        }
        var countModeVal = $("input[name='countMode']:checked").val();
        if(countModeVal === "现结"){
            var countMode1 = $("input[name='countMode1']");
            if(!mod.itemIsNull(countMode1)){
                dialog.tip("您选择的是境内结算 -- 现结，请选择现结类型！",3);
                return false;
            }
        }else if(countModeVal === "账期"){
            var countMode1_1 = $("input[name='countMode1_1']");
            if(!mod.itemIsNull(countMode1_1)){
                dialog.tip("您选择的是境内结算 -- 账期，请填写期限！",3);
                return false;
            }
        }else if(countModeVal === "计息账期"){
            var countMode1_2 = $("input[name='countMode1_2']");
            if(!mod.itemIsNull(countMode1_2)){
                dialog.tip("您选择的是境内结算 -- 计息账期，请填写期限！",3);
                return false;
            }
        }else if(countModeVal === "税费账期"){
            var countMode1_3 = $("input[name='countMode1_3']");
            if(!mod.itemIsNull(countMode1_3)){
                
                dialog.tip("您选择的是境内结算 -- 税费账期，请填写期限！",3);
                return false;
            }
        }

        //过货方式
        var transportModeOption1 = $("input[name='transportModeOption1']");
        if(!mod.itemIsNull(transportModeOption1)){
            dialog.tip("您还没选择过货方式，请选择！",3);
            return false;
        }
        var transportModeOption1Val = $("input[name='transportModeOption1']:checked").val();
        if(transportModeOption1Val === "包车"){
            var transportModeOption2 = $("input[name='transportModeOption2']");
            if(!mod.itemIsNull(transportModeOption2)){
                dialog.tip("您选择的是过货方式 -- 包车，请选择包车类型！",3);
                return false;
            }
        }
        var odContractNo = $("input[name='odContractNo']");
        if(!mod.itemIsNull(odContractNo)){
            dialog.tip("您还没填写合同号，请填写！",3);
            return false;
        }
        

        //产品明细验证
        var proList = $("tbody#import_prolist_tbody");
        if(!proList.children("tr").length){
            $("input[name='productModel']").focus();
            dialog.tip("您还没有添加产品信息 -- 请添加！",3);
            return false;
        }

        // //合同号
        var odContractNo = $("input[name='odContractNo']");
        if(!mod.itemIsNull(odContractNo)){
            dialog.tip("您还没填写合同号，请填写！",3);
            return false;
        }

        //香港交货方式
        var idmMode = $("input[name='idmMode']");
        if(!mod.itemIsNull(idmMode)){
            dialog.tip("您还没选择香港交货方式，请选择！",3);
            return false;
        }

        var idmModeVal = $("input[name='idmMode']:checked").val();
        if(idmModeVal === "供应商配送"){
            var idmTelephone = $("input[name='idmTelephone']"),
                idmContacts = $("input[name='idmContacts']");
            if(!mod.itemIsNull(idmTelephone) || !mod.itemIsNull(idmContacts)){
                dialog.tip("您选择的是香港交货 -- 供应商配送，请填写联系电话和联系人！",3);
                return false;
            }
        }else if(idmModeVal === "快递送货"){
            var idmExpressName = $("input[name='idmExpressName']"),
                idmExpressNo = $("input[name='idmExpressNo']");
            if(!mod.itemIsNull(idmExpressName) || !mod.itemIsNull(idmExpressNo)){
                dialog.tip("您选择的是香港交货 -- 快递送货，请填写快递公司名称和快递单号！",3);
                return false;
            }
        }else if(idmModeVal === "华科代为提货"){
            var idmDeliveryAddress = $("input[name='idmDeliveryAddress']"),
                idmContacts1 = $("input[name='idmContacts1']"),
                idmTelephone1 = $("input[name='idmTelephone1']");
            if(!mod.itemIsNull(idmDeliveryAddress) || !mod.itemIsNull(idmContacts1) || !mod.itemIsNull(idmTelephone1)){
                dialog.tip("您选择的是香港交货 -- 华科代为提货，请填写提货地址、联系人和联系电话！",3);
                return false;
            }
        }

        //国内物流出货方式
        var outLibsMode = $("input[name='outLibsMode']");
        if(!mod.itemIsNull(outLibsMode)){
            dialog.tip("您还没选择国内物流出货方式，请选择！",3);
            return false;
        }

        var outLibsModeVal = $("input[name='outLibsMode']:checked").val();
        if(outLibsModeVal === "分批"){
            var log_list_tbody = $("#log_list_tbody");
            if(!log_list_tbody.find("tr").length){
                outLibsMode.focus();
                dialog.tip("您选择的是国内物流出货 -- 分批，请增加批次！",3);
                return false;
            }
        }else{
            //发货方式
            var deliverMode = $("input[name='deliverMode']");
            if(!mod.itemIsNull(deliverMode)){
                dialog.tip("您还没选择国内物流发货方式，请选择！",3);
                return false;
            }
            var deliverModeVal = $("input[name='deliverMode']:checked").val();
            if(deliverModeVal === "自提"){
                var idmBillLading1 = $("input[name='idmBillLading1']");
                if(!mod.itemIsNull(idmBillLading1)){
                    dialog.tip("您选择的是国内物流发货方式 -- 自提，请上传提货委托书！",3);
                    return false;
                }
            }else if(deliverModeVal === "发货"){
                var carrier = $("input[name='carrier']");
                if(!mod.itemIsNull(carrier)){
                    dialog.tip("您选择的是国内物流发货方式 -- 发货，请选择承运方！",3);
                    return false;
                }
                var carrierVal = $("input[name='carrier']:checked").val();
                if(carrierVal === "其他"){
                    var carrier1 = $("input[name='carrier1']");
                    if(!mod.itemIsNull(carrier1)){
                        dialog.tip("您选择的是国内物流发货方式 -- 发货 -- 承运方 -- 其他，请填写承运方！",3);
                        return false;
                    }
                }

                var pickUpTime = $("input[name='pickUpTime']");
                if(!mod.itemIsNull(pickUpTime)){
                    dialog.tip("您选择的是国内物流发货方式 -- 发货，请填写收货时间！",3);
                    return false;
                }
            }
        }
    }
    
    //提交与暂存
    $("input.submit_all").bind("click",function(){
        var _t = $(this),
            val = _t.val(),
            form = _t.parents("form");
        form.find(".submitType").val(val);
        if(val === "提交"){
            exports.formValidate() === undefined && form.submit();
        }else{
            form.submit();
        }
    });

    //进口下单列表 - 催单
    $(".order_list_tb").delegate("tr.t_bd a.urge","click",function(){
        var _t = $(this),
            url = _t.data("url");
        $.get(url,{},function(data){
            dialog.tip(data,3);
        });
    });

    //下单步骤
    mod.stepCtrl("ul.step_list","span.step_num");
});