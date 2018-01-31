//文件上传SWFUpload默认配置
//参考网址 http://www.leeon.me/upload/other/swfupload.html#events 不再做详细说明

function fileDialogStart(file) {}

function fileQueued(file) {};

function fileQueueError(file, errorCode, message) {
    var msg = "您选择的文件错误，请重新选择文件上传！";
    switch (errorCode) {
        case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
            msg = "您选择的文件大小错误，请重新选择文件上传！";
            break;
        case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
            try {
                msg = "您选择的图片超过最大(" + this.settings.file_size_limit + ")限制，请处理后上传！"
            } catch (e) {
                msg = "您选择的图片大小超过最大限制，请处理后上传！";
            }
            break;
        case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
            msg = "您的文件格式有误！";
            break;
        case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
            msg = "您一次选择的文件太多，请重新选择文件上传！";
            break;
        default:
            msg = "您选择的文件错误，请重新选择文件上传！";
    };
    alert(msg);
};

function fileDialogComplete(numFilesSelected, numFilesQueued) {
    numFilesQueued > 0 && this.startUpload();
};

function uploadStart(file) {};

function uploadProgress(file, bytesLoaded) {
    try {
        var percent = Math.ceil((bytesLoaded / file.size) * 100);
        if (percent < 10) {
            percent = "  " + percent;
        } else if (percent < 100) {
            percent = " " + percent;
        }

        FeaturesDemo.selQueue.value = file.id;
        var queueString = file.id + ":" + percent + "%:" + file.name;
        FeaturesDemo.selQueue.options[FeaturesDemo.selQueue.selectedIndex].text = queueString;


        FeaturesDemo.selEventsFile.options[FeaturesDemo.selEventsFile.options.length] = new Option("Upload Progress: " + bytesLoaded, "");
    } catch (ex) {
        this.debug(ex);
    }
}

function uploadError(file, errorCode, message) {
    alert("文件上传失败，请重试！");
    //console.info("错误代码：" + errorCode + "，错误信息：" +message);开发人员用来查看上传失败具体信息。
}

function uploadSuccess(file, serverData) { //上传一张文件成功后的回调
    var response = eval('(' + serverData + ')');
    if (window.console) {
        console.info(serverData);
    }
    if (response.result) {
        var id = Number(file.id.split("_")[1]) + 1;
        $("#fileUrl" + id).val(response.filePath);
        $("#fileName" + id).val(response.fileName);
    }
}

function uploadComplete(file) {
    var stats = this.getStats();
    if (stats.files_queued == 0) { //上传队列中文件为0时;
        uploadProgressClose();
        this.settings.file_upload_limit == 1 && this.setStats({
            successful_uploads: 0
        });
    } else {
        this.startUpload();
    }
    /*
	if(this.settings.file_upload_limit == 1 && this.getStats().successful_uploads ==1){
		this.setStats({
			successful_uploads:0
		});
	}*/
}

function uploadSettings(type, btnID, successCallback) { //至少需要传入一个参数
    var _default = {
        flash_url: "http://res.xinlikang.com/l/SWFUpload/v2.2.0.1/swfupload.swf",
        upload_url: XLK.onlineWebRoot + "upload.do",
        file_types: "*.jpg;*.jpeg;*.png;*.gif;*.doc;*.docx;*.pdf;",
        file_size_limit: "3MB",
        file_upload_limit: 1,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_placeholder_id: btnID || "upload00",
        button_image_url: "http://res.xinlikang.com/img/upload_z.png",
        button_width: 86,
        button_height: 25,
        button_text: "文件上传",
        button_text_left_padding: 15,
        button_text_top_padding: /msie/i.test(navigator.userAgent) ? 2 : 4,
        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: -2,
        file_dialog_start_handler: fileDialogStart,
        file_queue_error_handler: fileQueueError,
        file_dialog_complete_handler: fileDialogComplete,
        upload_start_handler: uploadStart,
        upload_progress_handler: uploadProgress,
        upload_error_handler: uploadError,
        upload_success_handler: successCallback || uploadSuccess,
        upload_complete_handler: uploadComplete,
        debug: false
    };
    if (typeof(type) == "object") {
        return $.extend(true, _default, type);
    };
    return _default;
};

function uploadProgressClose() { //关闭上传进度弹窗
    try {
        if (window.art && artDialog.list['uploadProgress'] && artDialog.list['uploadProgress'].close) {
            artDialog.list['uploadProgress'].close();
        };
    } catch (err) {}
};
