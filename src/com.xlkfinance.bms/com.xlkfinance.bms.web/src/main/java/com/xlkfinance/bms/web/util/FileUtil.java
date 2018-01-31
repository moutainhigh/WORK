package com.xlkfinance.bms.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.web.shiro.ShiroUser;



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
	 * 从文件路径中取出文件名
	 */
	public static String takeOutFileName(String filePath,String fileName) {
		int pos = filePath.lastIndexOf(".");
		if (pos > 0) {
			String pressix = filePath.substring(pos + 1);
			return fileName + "." + pressix;
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

					String fileName = takeOutFileName(item.getName()).replaceAll(" ", "");
					boolean b=vlifiletype(fileName,filevlitype);
					if (!b) {
						result.put("flag", false);
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
			uploadExceptionHandle(request, "上传文件时发生错误:" + e.getMessage(), uploadPath);
			result.put("flag", false);
			result.put("path", "");
			return result;
		} catch (Exception e) {
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
	public static void responseStatusQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		FileUploadStatus satusBean = getStatusBean(request);
		response.getWriter().write(satusBean.toJSon());
	}

	/**
	 * 处理取消文件上传
	 */
	public static void processCancelFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FileUploadStatus satusBean = getStatusBean(request);
		satusBean.setCancel(true);
		saveStatusBean(request, satusBean);
		responseStatusQuery(request, response);
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
	 * 通过文件名，获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName){
		String type = "";
		
		String [] arrayStr = fileName.split("\\.");
		
		if(arrayStr != null && arrayStr.length > 1 ){
			type = arrayStr[arrayStr.length -1];
		}
		return type;
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
	public static List<Map<String,Object>> uploadFile(HttpServletRequest request, HttpServletResponse response, String type, String rootPath, HashMap<String, Integer> map,String formaterDate,String[] filevlitype) throws ServletException, IOException {
	   
		
		List<Map<String,Object>> fileMapList = new ArrayList<Map<String,Object>>();
		
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
       
       Map<String,Object> tempMap = null;
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

	           tempMap = new HashMap<String,Object>();
	           tempMap.put("fileName", getFileName(item.getName()));
	           tempMap.put("fileSize",item.getSize());
	           tempMap.put("fileUrl", modelPath + "/" + fileName);
	           fileMapList.add(tempMap);
	           row++;
           }  
       } 
       return fileMapList;
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
   public static Map<String, Object> uploadFile2(HttpServletRequest request, HttpServletResponse response, String type, String rootPath, HashMap<String, Integer> map,String formaterDate,String[] filevlitype) throws ServletException, IOException {
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
       Subject subject = SecurityUtils.getSubject();
       ShiroUser loginUser= (ShiroUser) subject.getSession().getAttribute(com.xlkfinance.bms.web.constant.Constants.SHIRO_USER);
       Integer userId = loginUser.getPid();
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
              bizFile.setUploadDttm(DateUtils.getCurrentDateTime());
              bizFile.setStatus(Constants.STATUS_ENABLED);
              bizFile.setUploadUserId(userId);
              bizFileList.add(bizFile);
              row++;
           }  
       } 
       result.put("bizFileList", bizFileList);
       return result;
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
    * 功能：Java读取txt文件的内容
    * 步骤：1：先获得文件句柄
    * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
    * 3：读取到输入流后，需要读取生成字节流
    * 4：一行一行的输出。readline()。
    * 备注：需要考虑的是异常情况
    * @param filePath
    */
   public static String readTxtFile(String filePath){
	   StringBuffer sb = new StringBuffer();
	   try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if(file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt);
				}
				read.close();
			} else {
				logger.error("找不到指定的文件");
			}
		} catch (Exception e) {
			logger.error("读取文件内容出错",e);
		}
	   return sb.toString();
   }
   
   /**
    * 
    * 功能：Java读取txt文件的内容  (保留换行)
    * 步骤：1：先获得文件句柄
    * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
    * 3：读取到输入流后，需要读取生成字节流
    * 4：一行一行的输出。readline()。
    * 备注：需要考虑的是异常情况
    * @param filePath
    */
   public static String readTxtFile2(String filePath){
	   StringBuffer sb = new StringBuffer();
	   try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if(file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					sb.append(lineTxt).append("\n");
				}
				read.close();
			} else {
				logger.error("找不到指定的文件");
			}
		} catch (Exception e) {
			logger.error("读取文件内容出错",e);
		}
	   return sb.toString();
   }
   

	/**
	 * 写文件
	 * @param newStr	新内容
	 * @param filePath 	文件路径
	 * @param isAppend 	是否追加
	 * @return
	 * @throws IOException
	 */
	public static boolean writeTxtFile(String newStr,String filePath,boolean isAppend,String encoding) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = newStr + "\r\n";
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filePath);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,encoding);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			
			if(isAppend){
				// 保存该文件原有的内容
				for (int j = 1; (temp = br.readLine()) != null; j++) {
					buf = buf.append(temp);
					// System.getProperty("line.separator")
					// 行与行之间的分隔符 相当于“\n”
					buf = buf.append(System.getProperty("line.separator"));
				}
				buf.append(filein);
			}else{
				buf.append(newStr);
			}

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}
	
	/**
	 * 创建文件
	 * 
	 * @throws IOException
	 */
	public static boolean creatFile(String filePath)
			throws IOException {
		boolean flag = false;
		File filename = new File(filePath);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}
	/**
	 * 创建文件(自动创建目录)
	 * 
	 * @throws IOException
	 */
	public static boolean creatFile2(String filePath)throws IOException {
		boolean flag = false;
		int lastIndex = filePath.lastIndexOf(File.separator);
		String dirPath = filePath.substring(0,lastIndex+1);
		//创建目录-------------- 
		File fileDir = new File(dirPath);
		//创建文件
		File file = new File(filePath);
		
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
			flag = true;
		}
		return flag;
	}
	
	/**
	 *  打包文件
	 * @param fileUrlList 文件路径
	 * @param fileNameList 文件名
	 * @param localRootPath 服务器本地根目录
	 * @param uploadFileRoot 上传根目录节点  nfs
	 * @param relativeZipPath  打包相对路径
	 * @param zipFileName  文件名.xxx
	 * @param isFileNameIndex  打包文件文件名是否需要下标
	 * @return 返回打包文件zip相对路径       /partner/nyyh/日期/项目id/
	 * 		   zipPath :/partner/nyyh/日期/项目id/
	 * 		   zipFileNameList : xxxx.jpg
	 */
	public static Map<String,Object> packageZipFile(List<String> fileUrlList,List<String> fileNameList ,
			String localRootPath,String uploadFileRoot,String relativeZipPath,String zipFileName,boolean isFileNameIndex)throws Exception{
		//本地目录
		String zipLocalDirPath = localRootPath +File.separator+uploadFileRoot+File.separator+ relativeZipPath;
		//本地文件
		String zipLocalFilePath = zipLocalDirPath +File.separator+ zipFileName ;
		//打包相对路径
		String uploadZipPath = uploadFileRoot + File.separator+relativeZipPath +File.separator+ zipFileName ;
		//创建目录-----------------创建zip文件------------------------------------------
		File file_zipLocalDirPath = new File(zipLocalDirPath);
		//创建文件
		File file_zipFilePath = new File(zipLocalFilePath);
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		List<String> zipFileNameList = new ArrayList<String>();
		
		
		try{
			if (!file_zipLocalDirPath.exists()) {
				file_zipLocalDirPath.mkdirs();
			}
			if (!file_zipFilePath.exists()) {
				file_zipFilePath.createNewFile();
			}
			//全路径
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file_zipFilePath)); 
			
			File tempFile = null;
			int fileIndex = 1;
			byte[] buffer = new byte[1024];   
			int len;
			boolean isHasZip  = false;
			String tempZipFileName = null;
			//需要打包的源文件路径
			for(int i = 0 ; i < fileUrlList.size() ; i ++ ){
				tempFile = new File(localRootPath+File.separator+fileUrlList.get(i));
				if(tempFile.exists()){
					FileInputStream fis = new FileInputStream(tempFile);  
					
					if(isFileNameIndex){
						tempZipFileName = fileIndex+"_"+fileNameList.get(i)+"."+FileUtil.getFileType(tempFile.getName());
					}else{
						tempZipFileName = fileNameList.get(i)+"."+FileUtil.getFileType(tempFile.getName());
					}
					
					zipFileNameList.add(tempZipFileName);
					
					out.putNextEntry(new ZipEntry(tempZipFileName)); 
					// 读入需要下载的文件的内容，打包到zip文件
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
					fileIndex++;
					isHasZip = true;
				}
			}
			//关闭zip输出流
			out.close();
			
			if(isHasZip){
				logger.info(">>>>>>packageZipFile package zip url:"+zipLocalFilePath);
			}else{
				//空文件的时候，返回空
				uploadZipPath = null;
				//并且删除文件
				file_zipFilePath.delete();
			}
		}catch(Exception e){
			logger.error(">>>>>>packageZipFile error",e);
			throw e;
		}
		returnMap.put("zipFilePath", uploadZipPath);
		returnMap.put("zipFileNameList", zipFileNameList);
		
		return returnMap;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		try{
			//当前项目路径
/*			String webClassPath =Thread.currentThread().getContextClassLoader().getResource("").getFile();
			webClassPath = webClassPath.substring(1);
			
			String filePath  = webClassPath +"test.txt";
			System.out.println(filePath);
			System.out.println(creatFile(filePath));
			System.out.println("readTxtFile1:"+readTxtFile(filePath));
			
			writeTxtFile("22222", filePath,false);
			
			System.out.println("readTxtFile2:"+readTxtFile(filePath));*/
			
			//打包文件
//			List<String> fileUrlList = Arrays.asList("nfs/aaaa.txt","nfs/aaab.txt");
//			List<String> fileNameList = Arrays.asList("aaaa","aaab");
//			String localRootPath = "D:/file_upload";
//			String uploadFileRoot = "nfs";
//			String relativeZipPath = "partner/20170219";
//			String zipFileName = "20170219.zip";
			
/*			String returnStr = packageZipFile(fileUrlList, fileNameList, localRootPath, uploadFileRoot, 
					relativeZipPath, zipFileName);*/
			
/*			File file = new File("D:/work/work_space/space3_bms/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/nfs/partner/20170219/nanyueyinhang/20170219180619453.zip");
			
			
			System.out.println(file.delete());*/
			
			
//			String filePath = "D:/work/work_space/space3_bms/.metadata/.plugins/org.eclipse.wst.server.core/tmp3/wtpwebapps/nfs/partner/loan/20170226/Loanqf20170226.txt";
			String filePath = "D:/Loanqf20170226.txt";
			
			String totalSb ="aaaaaaa\n中国";
			
			//存在覆盖
			FileUtil.creatFile2(filePath);
			FileUtil.writeTxtFile(totalSb.toString(), filePath, false,"UTF-8");
			
			
			
			System.out.println(readTxtFile(filePath));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	   

}
