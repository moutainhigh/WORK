define(function(require, exports, module) {
    require("l/SWFUpload/v2.2.0.1/swfupload.js");
    require("l/My97DatePicker/4.8/WdatePicker.js");
    require("m/sea_modules/handlers.js");

    var expoExcel = require("../js/order_export_basic");
    var proBatchUploadSuccess = expoExcel.proBatchUploadSuccess;
    
    new SWFUpload(uploadSettings({
        button_placeholder_id: "picUpload1",
        file_types: "*.xls;*.xlsx;",
        file_size_limit: "2MB",
        file_upload_limit: 0,
        button_text_left_padding: 5,
        button_text: "批量上传产品",
        upload_success_handler: proBatchUploadSuccess
    }));

    var proBatchUploadSuccess = function(file, serverData) {
        var response = eval('(' + serverData + ')');
        if (response.result) {
            var id = Number(file.id.split("_")[1]) + 1;
            $("#fileUrl" + id).val(response.filePath);
            $("#fileName" + id).val(response.fileName);


            var act = 'uploadProduct.do';
            var flag = true;
            var map = {
                'fileName1': $('#fileName1').val(),
                'fileUrl1': $('#fileUrl1').val()
            };
            var export_dom_name = {
                /*
                 * 将DOM元素Name与列表的列号对应
                 * 如:需将名称是X的DOM添加到列表的第几列
                 */
                '1': 'productModel',
                '2': 'productName',
                '3': 'productPlace',
                '4': 'productBrand',
                '5': 'opNumber',
                '6': 'unitPrice',
                '7': 'totalPrice',
                '8': 'boxNumber',
                '9': 'grossWeight',
                '10': 'netWeight',
                '11': 'shippingMark',
                '12': 'accessories',
                '13': 'opFunction',
                '14': 'remark',
                //'split':'productModel,|,0',
                'rows': 'maxProduct', //记录最大行数的DOM元素Name，在后台获取循环每行数据的时候需要
                /*
                 * 如果检测到存在以下参数,插件会自动触发临时暂存数据模式,
                 * 如果未检测到以下参数,插件只会在提交表单的时候提交数据
                 */
                'idn': 'optId', //暂存数据后台接收对象id的Name，暂存数据成功将把id返回给该名称的dom元素，用于识别每条数据唯一性
                'addAct': 'addOrderTemp.do', //暂存数据-添加操作的action
                'deleteAct': 'deleteOrderTemp.do', //暂存数据-删除操作的action
                'updateAct': 'updateOrderTemp.do' //暂存数据-修改操作的action
            };
            $o.ajax(act, map, flag, function(data) {
                for (var d in data) {
                    $o.operateDetail($('#o_detail')[0], 'detail-list', export_dom_name, data[d]);
                }
            })
        }
    }


    //公共模块
    var mod = require("./common/modules");

    //弹出窗模块
    var dialog = require('m/sea_modules/dialog');

    //供应商
    exports.getPartyData = function(val, list) {
        $.post(XLK.root + "party/findParty.do", {
            "partyName": val
        }, function(data) {
            var tmp = '';
            for (var i in data) {
                tmp += '<li class="g_e" data-id="' + data[i].partyId + '" data-info="' + data[i].partyContacts + '|' + data[i].partyTelephone + '|' + data[i].phone + '">' + data[i].partyName + '</li>';
            };
            list.html(tmp).show();
        }, "json");
    }
    mod.dropSelect("input[name='partyName']", exports.getPartyData);

    //表单验证
    exports.formValidate = function() {
        //供应商验证
        var partyName = $("input[name='partyName']");
        if (!mod.itemIsNull(partyName)) {
            dialog.tip("您还没填写供应商名称，请填写！", 3);
            return false;
        }

        //客户合同号验证
        var contractNo = $("input[name='contractNo']");
        if (!mod.itemIsNull(contractNo)) {
            dialog.tip("您还没填写客户合同号，请填写！", 3);
            return false;
        }

        //目的口岸验证
        var customsVal = $("input[name='customs']:checked").val();
        if (customsVal === "直接出口其他国家") {
            var exportToOther = $("input[name='exportToOther']");
            if (!mod.itemIsNull(exportToOther)) {
                dialog.tip("您选择的是目的口岸 -- 直接出口其他国家，请填写！", 3);
                return false;
            }
        }

        //产品明细验证
        var proList = $("tbody#detail-list");
        if (!proList.children("tr").length) {
            $("input[name='productModel']").focus();
            dialog.tip("您还没有添加产品信息 -- 请添加！", 3);
            return false;
        }

        //提货方式
        var pickingModeVal = $("input[name='pickingMode']:checked").val();
        if (pickingModeVal === "客户自送") {
            var khLinkMan = $("input[name='khLinkMan']"),
                khLinkPhone = $("input[name='khLinkPhone']");
            if (!mod.itemIsNull(khLinkMan) || !mod.itemIsNull(khLinkPhone)) {
                dialog.tip("您选择的是提货方式 -- 客户自送，请填写联系人和联系电话！", 3);
                return false;
            }
        } else if (pickingModeVal === "注册地提货") {
            var zcdPickingAddress = $("input[name='zcdPickingAddress']"),
                zcdLinkMan = $("input[name='zcdLinkMan']"),
                zcdLinkPhone = $("input[name='zcdLinkPhone']");
            if (!mod.itemIsNull(zcdPickingAddress) || !mod.itemIsNull(zcdLinkMan) || !mod.itemIsNull(zcdLinkPhone)) {
                dialog.tip("您选择的是提货方式 -- 注册地提货，请填写送货地址、联系人和联系电话！", 3);
                return false;
            }
        } else if (pickingModeVal === "加工厂提货") {
            // var plantModeBatch = $("input[name='plantModeBatch']");
            // if(!mod.itemIsNull(plantModeBatch)){
            //     dialog.tip("您选择的是提货方式 -- 加工场提货，请选择批次！",3);
            //     return false;
            // }
        }

        //深圳运输方式
        var transportModeVal = $("input[name='transportMode']:checked").val();
        if (transportModeVal === "散货拼车") {
            var carpoolTime = $("input[name='carpoolTime']");
            if (!mod.itemIsNull(carpoolTime)) {
                dialog.tip("您选择的是深圳运输方式 -- 散货拼车，请选择时间！", 3);
                return false;
            }
        } else if (transportModeVal === "包车") {
            var charteredType = $("input[name='charteredType']");
            if (!mod.itemIsNull(charteredType)) {
                dialog.tip("您选择的是深圳运输方式 -- 包车，请选择包车类型！", 3);
                return false;
            }
        }

        //香港交货方式
        var deliveryModeHKVal = $("input[name='deliveryModeHK']:checked").val();
        if (deliveryModeHKVal === "华科配送") {
            // var deliveryModeHKBatch = $("input[name='deliveryModeHKBatch']");
            // if(!mod.itemIsNull(deliveryModeHKBatch)){
            //     dialog.tip("您选择的是香港交货方式 -- 华科配送，请选择批次！",3);
            //     return false;
            // }
        } else if (deliveryModeHKVal === "货代自提") {
            var dmPickingMan = $("input[name='dmPickingMan']"),
                dmPickingPhone = $("input[name='dmPickingPhone']");
            if (!mod.itemIsNull(dmPickingMan) || !mod.itemIsNull(dmPickingPhone)) {
                dialog.tip("您选择的是提货方式 -- 货代自提，请填写提货人和联系电话！", 3);
                return false;
            }
        }
    }

    //提交与暂存
    $("input.submit_all").bind("click", function() {
        var _t = $(this),
            val = _t.val(),
            form = _t.parents("form");
        form.find(".submitType").val(val);
        if (val === "提交") {
            exports.formValidate() === undefined && form.submit();
        } else {
            form.submit();
        }
    });

    //进口下单列表 - 催单
    $(".order_list_tb").delegate("tr.t_bd a.urge", "click", function() {
        var _t = $(this),
            url = _t.data("url");
        $.get(url, {}, function(data) {
            dialog.tip(data, 3);
        });
    });

    //下单步骤
    mod.stepCtrl("ul.step_list", "span.step_num");
});
