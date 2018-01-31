package com.xlkfinance.bms.server.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService.Iface;
import com.xlkfinance.bms.server.system.mapper.BizFileMapper;
 
/**   
* @Title: BizFileServiceImpl.java 
* @Package com.xlkfinance.bms.server.system.service 
* @Description: 文件信息服务类，数据库表：BIZ_FILE  
* @author Ant.Peng   
* @date 2015年2月6日 下午6:06:47 
* @version V1.0   
*/ 
@Service("bizFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.BizFileService")
public class BizFileServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(BizFileServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "bizFileMapper")
	private BizFileMapper bizFileMapper;

	/**
	 * @Description:  保存文件信息
	 * @return int
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int saveBizFile(BizFile bizFile) throws ThriftServiceException,
			TException {
		try {
			bizFileMapper.saveBizFile(bizFile);
			return bizFile.getPid();
		} catch (Exception e) {
			logger.error("保存文件信息失败",e);
			throw new ThriftServiceException(ExceptionCode.CREDIT_ADD,"保存文件信息失败！");
		}
	}

	/**
	 * @Description:  删除文件信息 根据主键ID
	 * @return int
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int deleteBizFileByPid(int pid) throws ThriftServiceException,
			TException {
		try {
			return bizFileMapper.deleteBizFileByPid(pid);
			//return bizFile.getPid();
		} catch (Exception e) {
			logger.error("删除文件信息失败",e);
			throw new ThriftServiceException(ExceptionCode.CREDIT_DELETE,"删除文件信息失败！");
		}
	}

	/**
	 * @Description:  更新文件信息
	 * @return int
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@Override
	public int updateBizFile(BizFile bizFile) throws ThriftServiceException,
			TException {
		try {
			return bizFileMapper.updateBizFile(bizFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CREDIT_LIMIT_UPDATE,"更新文件信息失败！");
		}
	}

	@Override
	public int obtainBizFile(BizFile bizFile) throws ThriftServiceException,
			TException {
		// TODO Auto-generated method stub
		return 0;
	}

   @Override
   public List<BizFile> findAllBizFile(BizFile bizFile) throws ThriftServiceException, TException {
      return bizFileMapper.findAllBizFile(bizFile);
   }

   @Override
   public BizFile getBizFileById(int id) throws ThriftServiceException, TException {
      BizFile bizFile=new BizFile();
      bizFile.setPid(id);
      List<BizFile> list = bizFileMapper.findAllBizFile(bizFile);
      if (list==null||list.isEmpty()) {
         return null;
      }
      return list.get(0);
   }

   /**
    *@author:liangyanjun
    *@time:2016年9月6日下午5:02:48
    */
   @Override
   public BizFile getBizFileByUrl(String fileUrl) throws ThriftServiceException, TException {
      BizFile bizFile=new BizFile();
      bizFile.setFileUrl(fileUrl);
      List<BizFile> list = bizFileMapper.findAllBizFile(bizFile);
      if (list==null||list.isEmpty()) {
         return new BizFile();
      }
      return list.get(0);
   }

}
