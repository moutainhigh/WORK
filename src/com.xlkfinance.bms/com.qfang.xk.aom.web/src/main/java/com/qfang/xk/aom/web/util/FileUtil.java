package com.qfang.xk.aom.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.LoggerFactory;

import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.system.BizFile;





/**
 * @ClassName: FileUtil
 * @Description: 上传文件工具类
 * @author: Chong.Zeng
 * @date: 2015年1月5日 下午7:45:53
 */
public class FileUtil {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 从文件路径中取出文件名
	 */
	public static String takeOutFileName(String filePath) {
		int pos = filePath.lastIndexOf(".");
		if (pos > 0) {
			String pressix = filePath.substring(pos + 1);
			UUID uuid = UUID.randomUUID();
			return uuid + "." + pressix;
		} else {
			return filePath;
		}
	}

	/**
	 * 从request中取出FileUploadStatus Bean
	 */
	public static FileUploadStatus getStatusBean(HttpServletRequest request) {
		BeanControler beanCtrl = BeanControler.getInstance();
		return beanCtrl.getUploadStatus(request.getRemoteAddr());
	}

	/**
	 * 把FileUploadStatus Bean保存到类控制器BeanControler
	 */
	public static void saveStatusBean(HttpServletRequest request, FileUploadStatus statusBean) {
		statusBean.setUploadAddr(request.getRemoteAddr());
		BeanControler beanCtrl = BeanControler.getInstance();
		beanCtrl.setUploadStatus(statusBean);
	}

	/**
	 * 删除已经上传的文件
	 */
	public static void deleteUploadedFile(HttpServletRequest request, String uploadPath) {
		FileUploadStatus satusBean = getStatusBean(request);
		for (int i = 0; i < satusBean.getUploadFileUrlList().size(); i++) {
			File uploadedFile = new File(uploadPath + Constants.SEPARATOR + satusBean.getUploadFileUrlList().get(i));
			uploadedFile.delete();
		}
		satusBean.getUploadFileUrlList().clear();
		satusBean.setStatus("删除已上传的文件");
		saveStatusBean(request, satusBean);
	}

	/**
	 * 上传过程中出错处理
	 */
	public static void uploadExceptionHandle(HttpServletRequest request, String errMsg, String uploadPath) throws ServletException, IOException {
		// 首先删除已经上传的文件
		deleteUploadedFile(request, uploadPath);
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setStatus(errMsg);
		saveStatusBean(request, satusBean);
	}

	/**
	 * 初始化文件上传状态Bean
	 */
	public static FileUploadStatus initStatusBean(HttpServletRequest request, String uploadPath) {
		FileUploadStatus satusBean = new FileUploadStatus();
		satusBean.setStatus("正在准备处理");
		satusBean.setUploadTotalSize(request.getContentLength());
		satusBean.setProcessStartTime(System.currentTimeMillis());
		satusBean.setBaseDir(request.getContextPath() + uploadPath);
		return satusBean;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "deprecation" })
	public static Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response, String type, String modelPath, String rootPath, HashMap<String, Integer> map,String[] filevlitype) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		Map<String, Object> result = new HashMap<String, Object>();
			// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		String tempPath = CommonUtil.getRootPath() + rootPath + Constants.SEPARATOR + "temp";
		// 设置临时文件存储位置
		File f = new File(tempPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		factory.setRepository(f);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置上传目录位置
		String uploadPath = CommonUtil.getRootPath() + modelPath;
		File f1 = new File(uploadPath);
		if (!f1.exists()) {
			f1.mkdirs();
		}

		// 设置单个文件的最大上传值
		upload.setFileSizeMax(map.get("maxFileSize"));
		// 设置整个request的最大值
		upload.setSizeMax(map.get("maxReqSize"));
		upload.setProgressListener(new FileUploadListener(request));
		// 保存初始化后的FileUploadStatus Bean
		saveStatusBean(request, initStatusBean(request, uploadPath));
		FileUploadStatus satusBean = getStatusBean(request);
		String forwardURL = "";

		try {
			@SuppressWarnings("rawtypes")
			List items = upload.parseRequest(request);
			result.put("items", items);
			// 获得返回url
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {
					forwardURL = item.getString();
					break;
				}
			}

			// 处理文件上传
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);

				// 取消上传
				if (getStatusBean(request).getCancel()) {
					deleteUploadedFile(request, uploadPath);
					break;
				}
				// 保存文件
				else if (!item.isFormField() && item.getName().length() > 0) {

					String fileName = takeOutFileName(item.getName()).replaceAll(" ", "");;
					boolean b=vlifiletype(fileName,filevlitype);
					if (!b) {
						result.put("flag", false);
						result.put("errorMsg", "上传文件类型不合法");
						logger.info("上传文件类型不合法");
						return result;
					}
					File uploadedFile = new File(uploadPath + Constants.SEPARATOR + fileName);

					item.write(uploadedFile);
					// 更新上传文件列表
					satusBean.getUploadFileUrlList().add(fileName);
					saveStatusBean(request, satusBean);
					result.put("path", modelPath + "/" + fileName);
					result.put("flag", true);
					// Thread.sleep(500);
				}
			}
		} catch (FileUploadException e) {
		   e.printStackTrace();
			uploadExceptionHandle(request, "上传文件时发生错误:" + e.getMessage(), uploadPath);
			result.put("flag", false);
			result.put("path", "");
			return result;
		} catch (Exception e) {
		   e.printStackTrace();
			uploadExceptionHandle(request, "保存上传文件时发生错误:" + e.getMessage(), uploadPath);
			result.put("flag", false);
			result.put("path", "");
			return result;
		}
		if (forwardURL.length() == 0) {
			forwardURL = "";
		}
		request.setAttribute("msg", "<font size=2><b>文件上传成功!</b></font>");
		return result;
	}

	private static boolean vlifiletype(String fileName, String[] filevlitype) {
		for (int i = 0; i < filevlitype.length; i++) {
			if (fileName.toLowerCase().endsWith(filevlitype[i].toLowerCase())) {
			
				return true;
			
			}
		}
		return false;
	}

	/**
	 * @Description: 上传文件
	 * @param request
	 * @param response
	 * @param type
	 * @param rootPath
	 * @param map
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author: Chong.Zeng
	 * @date: 2015年1月6日 下午7:43:32
	 */
	public static Map<String, Object> processFileUpload(HttpServletRequest request, HttpServletResponse response, String type, String rootPath, HashMap<String, Integer> map, String formaterDate,String[] filevlitype) throws ServletException, IOException {
		String modelPath = rootPath + Constants.SEPARATOR + type + Constants.SEPARATOR + getDateToString(formaterDate);
		return fileUpload(request, response, type, modelPath, rootPath, map,filevlitype);
	}

	/**
	 * 回应上传状态查询
	 */
	public static String responseStatusQuery(HttpServletRequest request) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
      return satusBean.toJSon();
	}

	/**
	 * 处理取消文件上传
	 */
	public static String processCancelFileUpload(HttpServletRequest request) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setCancel(true);
		saveStatusBean(request, satusBean);
		return responseStatusQuery(request);
	}

	/**
	 * @Description: 获取String型的时间
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月7日 下午2:13:25
	 */
	public static String getDateToString(String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(new Date());
	}
   /**
    * 根据传入路径和去掉目录级别，返回处理后的目录
    *@author:liangyanjun
    *@time:2016年8月28日上午3:00:26
    *@param path
    *@param leval
    */
   public static String handlePathByLevel(String path,int leval) {
      if (StringUtil.isBlank(path)||leval<=0) {
         return "";
      }
      StringBuffer sb=new StringBuffer();
      String[] strings;
      if ("\\".equals(File.separator)) {
         strings = path.split(File.separator + File.separator);
      }else{
         strings = path.split("/");
      }
      for (int i = 0; i < strings.length-leval; i++) {
         sb.append(strings[i]);
         sb.append(File.separator);
      }
      return sb.toString();
   }
   /**
    * 上传文件
    *@author:liangyanjun
    *@time:2016年9月1日下午5:02:31
    *@param request
    *@param response
    *@param type
    *@param rootPath
    *@param map
    *@param formaterDate
    *@param filevlitype
    *@return
    *@throws ServletException
    *@throws IOException
    */
   public static Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response, String type, String rootPath, HashMap<String, Integer> map,String formaterDate,String[] filevlitype) throws ServletException, IOException {
      Map<String, Object> result=new HashMap<>();
      int row = 0;
      String modelPath = rootPath + Constants.SEPARATOR + type + Constants.SEPARATOR + getDateToString(formaterDate);
      // 设置上传目录位置
      String uploadPath = CommonUtil.getRootPath() + modelPath;
      File tempDirPath =new File(uploadPath);  
        if(!tempDirPath.exists()){  
            tempDirPath.mkdirs();  
        }    
       //创建磁盘文件工厂  
       DiskFileItemFactory fac = new DiskFileItemFactory();      
       //创建servlet文件上传组件  
       ServletFileUpload upload = new ServletFileUpload(fac);
      // 设置单个文件的最大上传值
      upload.setFileSizeMax(map.get("maxFileSize"));
      // 设置整个request的最大值
      upload.setSizeMax(map.get("maxReqSize"));
      //upload.setProgressListener(new FileUploadListener(request));
       
       //文件列表  
       List<FileItem> fileList = null;      
       //解析request从而得到前台传过来的文件  
       try {      
           fileList = upload.parseRequest(request);      
       } catch (FileUploadException ex) {      
           ex.printStackTrace();      
           return null;      
       }   
       //保存后的文件名  
       String imageName = null;  
       //便利从前台得到的文件列表  
       Iterator<FileItem> it = fileList.iterator();  
       
       List<BizFile> bizFileList = new ArrayList<BizFile>();
       File tempFile = null;
       while(it.hasNext()){      
           FileItem item =  it.next(); 
           //如果不是普通表单域，当做文件域来处理  
           if(!item.isFormField()){  
            String fileName = takeOutFileName(item.getName());
            fileName=fileName.replaceAll(" ", "");
            tempFile = new File(uploadPath+Constants.SEPARATOR+fileName);
            
            BufferedInputStream in = new BufferedInputStream(item.getInputStream());    
              BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile));  
              Streams.copy(in, out, true);  

              BizFile bizFile=new BizFile();
              bizFile.setFileName(getFileName(item.getName()));
              bizFile.setFileSize(Integer.parseInt(item.getSize()+""));
              bizFile.setFileUrl(modelPath + "/" + fileName);
              int dotLocation = fileName.lastIndexOf(".");
              String fileType = fileName.substring(dotLocation).substring(1);
              bizFile.setFileType(fileType);
              bizFile.setStatus(Constants.STATUS_ENABLED);
              bizFile.setUploadDttm(DateUtils.getCurrentDateTime());
              bizFileList.add(bizFile);
              row++;
           }  
       } 
       result.put("bizFileList", bizFileList);
       return result;
   }
   /**
    * 通过文件名，获取名称
    * @param fileName
    * @return
    */
   public static String getFileName(String fileName){
	  fileName=fileName.replaceAll(" ", "");
      String [] arrayStr = fileName.split("\\.");
      return arrayStr[0];
   }
}
