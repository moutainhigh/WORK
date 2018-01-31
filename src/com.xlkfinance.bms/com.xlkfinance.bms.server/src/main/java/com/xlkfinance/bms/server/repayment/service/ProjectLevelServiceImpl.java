/**
 * @Title: SysConfigServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月15日 下午4:52:19
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.ProjectLevel;
import com.xlkfinance.bms.rpc.repayment.ProjectLevelService.Iface;
import com.xlkfinance.bms.server.repayment.mapper.ProjectLevelMapper;

/**
 * 
 * @ClassName: ProjectLevelServiceImpl
 * @Description: 项目级别分类
 * @author: Rain.Lv
 * @date: 2015年6月1日 下午8:03:07
 */
@SuppressWarnings("unchecked")
@Service("projectLevelServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.repayment.ProjectLevelService")
public class ProjectLevelServiceImpl implements Iface {
	private Logger logger = LoggerFactory
			.getLogger(ProjectLevelServiceImpl.class);
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectLevelMapper")
	private ProjectLevelMapper projectLevelMapper;
	/**
	 * 
	  * @Description: 获取五级分类的信息
	  * @param projectLevel
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:33:10
	 */
	@Override
	public List<ProjectLevel> getProjectLevelInfo(ProjectLevel projectLevel)
			throws ThriftServiceException, TException {
		List<ProjectLevel> list = null;
		try {
			list = projectLevelMapper.getProjectLevelInfo(projectLevel);
			if (null != list && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取数据失败" + e.getMessage());
		}

		return new ArrayList<ProjectLevel>();
	}
	/**
	 * 
	  * @Description: 获取五级分类纪录数
	  * @param projectLevel
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:33:51
	 */
	@Override
	public int getProjectLevelCount(ProjectLevel projectLevel)
			throws ThriftServiceException, TException {
		int result = 0;
		try {
			result = projectLevelMapper.getProjectLevelCount(projectLevel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获取记录数失败" + e.getMessage());
		}
		return result;
	}
	/**
	 * 
	  * @Description: 保存，修改自定义类型
	  * @param listCtp
	  * @return
	  * @throws TException
	  * @author: Rain.Lv
	  * @date: 2015年6月30日 下午3:34:07
	 */
	@Override
	public boolean saveProjectLevelInfo(List<ProjectLevel> listCtp)
			throws TException {
		int pid = 0;
		 try {
			 for (ProjectLevel projectLevel2 : listCtp) {
				 if(projectLevel2.getPid()>0){//如果为0就删除   0表示请选择
					  if(projectLevel2.getProjectLevel()==0){
						  projectLevelMapper.delProjectLevelInfo(projectLevel2.getPid());
					  }else{//如果不等于0（请选择）表示修改为其它自定义类型
						  projectLevelMapper.editProjectLevel(projectLevel2);
					  }
				 }else{
					 pid= projectLevelMapper.addProjectLevel(projectLevel2);
				 }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(pid==0?"修改数据数据失败":"保存数据失败" + e.getMessage());
			return false;
		}
		return true;
	}

}
