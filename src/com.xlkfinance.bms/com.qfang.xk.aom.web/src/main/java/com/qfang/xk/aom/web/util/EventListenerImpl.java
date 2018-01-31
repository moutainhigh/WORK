package com.qfang.xk.aom.web.util;

import javax.servlet.http.HttpServletRequest;

import com.enterprisedt.util.debug.Logger;

/**
  * @ClassName: EventListenerImpl
  * @Description: 监听程序
  * @author: Chong.Zeng
  * @date: 2015年1月5日 下午7:48:25
 */
public class EventListenerImpl {

	public static long transferedbyte=0;
	public static long totalfilesize=0;
	public static long flag=0;
    private Logger log = Logger.getLogger(EventListenerImpl.class);
    private static int cuNum=0;
    private HttpServletRequest request=null;
    public static long totalReadBytes=0;
    
    public static long totalReadByteses=0;
    public static int numflag=1;
   public EventListenerImpl(int cuNum,long filesize,HttpServletRequest request)
   {
	   EventListenerImpl.totalfilesize=0;
	   EventListenerImpl.transferedbyte=0;
	   EventListenerImpl.flag=0;
	   this.cuNum=cuNum;
	   this.request=request;
	   
	   EventListenerImpl.totalReadBytes=0;
	   
	   this.totalReadBytes=filesize;
       totalReadByteses+=totalReadBytes;
       
   }
   public static void setTotalSize(long temp)
   {
	   EventListenerImpl.totalfilesize=temp;   
   }
    public void bytesTransferred(String connId, String remoteFilename, long bytes) {
       
        EventListenerImpl.transferedbyte=bytes;
        FileUploadStatus statusBean= FileUtil.getStatusBean(request);
         statusBean.setUploadTotalSize(FileUploadListener.totalFileSize);
		
         
        //读取完成
	    if (FileUploadListener.totalFileSize ==this.transferedbyte) {
	       statusBean.setStatus("完成对" + FileUploadListener.currentFileNum +"个文件的FTP处理 " + FileUploadListener.totalFileSize + " bytes.");
	       statusBean.setReadTotalSize(this.transferedbyte);
	       statusBean.setSuccessUploadFileCount(FileUploadListener.currentFileNum);
	       statusBean.setProcessEndTime(System.currentTimeMillis());
	       statusBean.setProcessRunningTime(statusBean.getProcessEndTime());
	       statusBean.setUploadFlag("ftp");
	    //读取中
	    } else {
	       statusBean.setStatus("当前正在进行第" +cuNum +"个文件的FTP文件上传:已经处理了 " + (bytes+totalReadByteses-totalReadBytes) + " / " + FileUploadListener.totalFileSize+ " bytes.");
	       statusBean.setReadTotalSize(bytes+totalReadByteses-totalReadBytes);
	       statusBean.setCurrentUploadFileNum(cuNum);
	       statusBean.setProcessRunningTime(System.currentTimeMillis());
	       statusBean.setUploadFlag("ftp");
	    }
            FileUtil.saveStatusBean(request,statusBean);
        
    }
    
    /**
     * Log an FTP command being sent to the server. Not used for SFTP.
     * 
     * @param cmd   command string
     */
    public void commandSent(String connId, String cmd) {
       
    }
    
    /**
     * Log an FTP reply being sent back to the client. Not used for
     * SFTP.
     * 
     * @param reply   reply string
     */
    public void replyReceived(String connId, String reply) {
      
    }
        
    /**
     * Notifies that a download has started
     * 
     * @param remoteFilename   remote file name
     */
    public void downloadStarted(String connId, String remoteFilename) {
      
    }
    
    /**
     * Notifies that a download has completed
     * 
     * @param remoteFilename   remote file name
     */
    public void downloadCompleted(String connId, String remoteFilename) {
       
    }
    
    /**
     * Notifies that an upload has started
     * 
     * @param remoteFilename   remote file name
     */
    public void uploadStarted(String connId, String remoteFilename) {

        EventListenerImpl.transferedbyte=0;
       
    }
    
    /**
     * Notifies that an upload has completed
     * 
     * @param remoteFilename   remote file name
     */
    public void uploadCompleted(String connId, String remoteFilename) {

        EventListenerImpl.transferedbyte=0;
        EventListenerImpl.totalfilesize=0;
        EventListenerImpl.flag=-1;
    }
    public static long returnFlag()
    {
    	return EventListenerImpl.flag;
    }
	public static long getTransferedbyte() {
		//System.out.println("getTransferedbyte:"+transferedbyte);
		return transferedbyte;
	}

	
	public static long getTotalfilesize() {
		return totalfilesize;
	}
public static void main(String[] args) {
	long tt=129984080;
	long tt2=10485760;
	System.out.println(tt+tt2);
}
}
