package com.xlkfinance.bms.web.util;

import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


/**
 * sftp 工具类
 * @author chenzhuzhen
 * @date 2016年7月3日 下午6:02:04
 */
public class SFTPUtil {
	
	
	private Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
	
	private ChannelSftp channelSftp;
	private Session session;
	private JSch jsch;
	
	/**
	 * 初始化
	 * @param host	主机地址
	 * @param port	端口
	 * @param username 用户名
	 * @param pwd	密码
	 */
	public void init(String host, int port ,String username ,String pwd)throws Exception{
		
		
		logger.info(">>>>>>SFTP init begin...");
		
		
		jsch = new JSch();
		session = jsch.getSession(username, host, port);
		
		session.setPassword(pwd);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		channelSftp = (ChannelSftp) session.openChannel("sftp");
		channelSftp.connect();
//		channelSftp.setFilenameEncoding("gbk");
		
		logger.info(">>>>>>init end...");
	}
	
	
	public void close(){
		
		logger.info(">>>>>>SFTP close...");
		
		if(channelSftp != null  && channelSftp.isConnected()){
			channelSftp.disconnect();
		}
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
	}
	
	
	/**
	 * 下载文件到本地   
	 * @param src	目标服务器上的文件，不能为目录
	 * @param dst	本地文件路径	 若 dst 为目录，则本地文件名与目标服务器上的文件名一样。
	 * @return
	 * @throws SftpException
	 */
	public void downFile(String src, String dst) throws SftpException{
		
		channelSftp.get(src,dst);
	}
	
	/**
	 * 上传本地文件       采用默认的传输模式： OVERWRITE
	 * @param src	本地文件
	 * @param dst	目标服务器文件
	 * @throws SftpException 
	 */
	public void uploadFile(String src, String dst) throws SftpException{
		channelSftp.put(src, dst);
	}
	
	
	/**
	 * 显示当前目录所有目录和文件
	 * @param path
	 * @throws SftpException 
	 */
	public void lsFile(String path) throws SftpException{
		Vector vector  = channelSftp.ls(path);
		for (Object obj :vector){
			if (obj  instanceof com.jcraft.jsch.ChannelSftp.LsEntry){
			String fileName = ((com.jcraft.jsch.ChannelSftp.LsEntry)obj).getFilename();
			System. out .println(fileName);
			}
		}
	}
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
//		SFTPUtil sftpUtil = new SFTPUtil();
//		sftpUtil.init("unionfin.eicp.net", 1022, "fsppay", "fsppay");
//		sftpUtil.downFile("/home/fsppay/report/ifsp/pre/801160705100004/F50_20160707111446.zip","D:/20160333707.zip");
//		sftpUtil.close();
		
		
//		SFTPUtil sftpUtil = new SFTPUtil();
//		sftpUtil.init("116.7.225.98", 2222, "ifsp", "password");
//		sftpUtil.downFile("/home/ifsp/801161002100018/F50_20161002165543.zip","D:/20160714.zip");
//		sftpUtil.close();
		
		
		
		//生成扣款文件
		StringBuffer sb = new StringBuffer();
		
		
		//FileUtil.writeTxtFile(newStr, filePath, isAppend);
		
		
		
		
	}
	 

}
