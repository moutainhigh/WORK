package com.qfang.xk.aom.web.controller.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.util.FileUtil;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月24日上午10:35:02 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 文件上传监听<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/filelistener")
public class FileUploadListenerController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(FileUploadListenerController.class);

   /**
    * 监听文件状态
    *@author:liangyanjun
    *@time:2016年8月24日上午10:34:51
    *@param response
    *@param request
    */
   @RequestMapping(value = "filestatus")
   public void uploadFile(HttpServletResponse response, HttpServletRequest request) {
      Map<String, Object> jsonMap = new HashMap<String, Object>();
      try {
         String string = null;
         request.setCharacterEncoding("utf-8");
         if (request.getParameter("uploadStatus") != null) {
            string = FileUtil.responseStatusQuery(request);
         }
         if (request.getParameter("cancelUpload") != null) {
            string = FileUtil.processCancelFileUpload(request);
         }
         jsonMap.put("filestatusObj", JSON.parseObject(string));
         jsonMap.put("success", true);
      } catch (Exception e) {
         logger.error("监听文件上传状态出错 方法： FileUploadListenerController.uploadFile");
         e.printStackTrace();
         jsonMap.put("success", false);
         jsonMap.put("msg", "监听文件上传状态出错");
      }
      outputJson(jsonMap, response);
   }
}
