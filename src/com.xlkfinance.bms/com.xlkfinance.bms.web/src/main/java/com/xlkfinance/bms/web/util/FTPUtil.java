package com.xlkfinance.bms.web.util;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ftp工具
 * 
 * @author chenzhuzhen
 * @date 2016年8月3日 下午4:15:49
 */
public class FTPUtil {

	private static Log logger = LogFactory.getLog(FTPUtil.class);

	private static FTPClient client = null;

	private static String ROOT_PATH = "/";// FTP服务器根目录的目录名

	/**
	 * 连接ftp服务器
	 * 
	 * @param host
	 *            主机地址
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码(明文)
	 */
	public static void init(String host, int port,String username,String pwd)
			throws Exception {
		
		try {
			
			if (client == null || client.isConnected() == false) {
				
				// 创建客户端
				client = new FTPClient();
				// 不指定端口，则使用默认端口21
				client.connect(host, port);
				client.setCharset("UTF-8");
				// 用户登录
				client.login(username, pwd);
				// 打印地址信息
				logger.info(">>>>>>ftp server login:" + client);
			}
		
		} catch (Exception e) {
			logger.error(">>>>>>login error", e);
		}
	}

	/**
	 * 安全登出
	 * 
	 * @throws Exception
	 */
	public static void close() throws Exception {
		
		if (client != null && client.isConnected() == true) {
			
			// 安全退出
			client.disconnect(true);
			logger.info(">>>>>>ftp server connection close");
		}
	}

	/**
	 * 获取当前用户根目录
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getRootPath() throws Exception {

		if (isWindowSystem()) {
			return ROOT_PATH;
		} else {
			return "/home/" + client.getUsername() + ROOT_PATH;
		}

	}

	/**
	 * 下载文件到本地
	 * 
	 * @param src
	 *            目标服务器上的文件，不能为目录
	 * @param dst
	 *            下载路径必须为本地全路径(带文件名),如:d:/ftp/2.jpg
	 * @return
	 * @throws Exception
	 */
	public static void downFile(String src, String dst) throws Exception {
	
		try {

			dst = dst.replace("\\", "/").replace("\\\\", "/");
			src = src.replace("\\", "/").replace("\\\\", "/");
			String tempArray[] = dst.split("\\\\");
			if (tempArray.length == 1) {
				tempArray = dst.split("/");
			}
			// 第一次当前路径默认根路径
			String rootPath = client.currentDirectory();
			String fileName = tempArray[tempArray.length - 1];
			String loaclFileDir = dst.split(fileName)[0];
			File dir = new File(loaclFileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (dst.startsWith("/")) {
				// 在ftp上创建目录
				mkDirs(loaclFileDir);
			}
			File f = new File(dst);
			String srcPath = rootPath + src;

			System.out.println("远程路径：" + srcPath + ",下载路径：" + dst);
			client.setType(FTPClient.TYPE_BINARY);		//定义编码格式 防止中文出现乱码  
			client.download(srcPath, f);
			close();
		} catch (FTPException ftpE) {
			if (ftpE.getCode() == 550) {
				logger.error(">>>>>>downFile error,文件不存在");
			} else {
				logger.error(">>>>>>downFile error", ftpE);
			}
		} catch (Exception e) {
			logger.error(">>>>>>downFile error", e);
		}
	}

	/**
	 * 上传文件到指定的目录下 被指定上传的目录
	 * 
	 * @param file
	 * @param sPath
	 *            保存相对路径 \\aaaa\\aaaa 为空获取文件相对路径 被上传的文件
	 */
	public static void upload(File file, String sPath) throws Exception {
		try {
			if (sPath == null || "".equals(sPath)) {
				sPath = getSavePath(file);
			}
			// 切换到上传目录
			if (sPath == null || "".equals(sPath)) {
				sPath = client.currentDirectory();
			} else {
				// 在ftp上创建目录
				mkDirs(sPath);
			}
			// 上传到client所在的目录
			client.upload(file);
			logger.info(">>>>>upload file [" + file.getAbsolutePath()
					+ "] to FTP directory: [" + client.currentDirectory() + "]");
			close();
		} catch (Exception e) {
			logger.error(">>>>>>upload error", e);
		}
	}

	/**
	 * 获取文件保存相对路径 截取路径包含ftp
	 * 
	 * @param file
	 * @return
	 */
	public static String getSavePath(File file) {
		// 如果是上传至根目录,则不创建目录
		if (file.getName().equals(ROOT_PATH)) {
			return null;
		}
		String absPath = file.getParent();
		int index = absPath.indexOf(ROOT_PATH);
		if (index > -1) {
			return absPath.substring(index + ROOT_PATH.length());
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void deleteFile(String filePath) throws Exception {
		client.deleteFile(filePath);
	}

	/**
	 * 获取当前目录
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getCurrDir() throws Exception {
		return client.currentDirectory();
	}

	/**
	 * 切换当前目录
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void changeDir(String dirPath) throws Exception {
		client.changeDirectory(dirPath);
	}

	/**
	 * 返回上一层
	 * 
	 * @throws Exception
	 */
	public static void changeDirUp() throws Exception {
		client.changeDirectoryUp();
	}

	/**
	 * 创建层级目录,并进入当前路径目录
	 * 
	 * @param path
	 */
	public static void mkDirs(String path) throws IllegalStateException,
			IOException, FTPIllegalReplyException, FTPException {
		try {
			if (null == path) {
				return;
			}
			

			StringTokenizer dirs = new StringTokenizer(path, "/");
			String temp = null;
			while (dirs.hasMoreElements()) {
				temp = dirs.nextElement().toString();
				if (!isDirExist(temp)) {
					client.createDirectory(temp);// 创建目录
					client.changeDirectory(temp);// 进入创建目录
					System.out.println(">>>>>create directory:[" + path + "]");
				}
			}
		} catch (Exception e) {
			logger.error(">>>>>>mkDirs error", e);
		}
	}

	/**
	 * 切换到ftp根目录
	 * 
	 * @return
	 */
	public static void changeRootDir() throws Exception {
		String rootPath = getRootPath();
		StringTokenizer dirs = new StringTokenizer(rootPath, File.separator);
		String temp = null;
		while (dirs.hasMoreElements()) {
			temp = dirs.nextElement().toString();
			if (!isDirExist(temp)) {
				client.createDirectory(temp);// 创建目录
				client.changeDirectory(temp);// 进入创建目录
			}
		}
	}

	/**
	 * 检查目录是否存在
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean isDirExist(String dir) {
		try {
			client.changeDirectory(dir);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断系统是否为window
	 * 
	 * @return
	 */
	public static boolean isWindowSystem() {
		String osName = System.getProperties().getProperty("os.name");
		if (osName != null && osName.toLowerCase().contains("window")) {
			return true;
		} else {
			return false;
		}
	}

}
