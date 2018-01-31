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
package com.xlkfinance.bms.server.mortgage.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBase;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseService.Iface;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssDtl;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssExtraction;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssFile;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssKeeping;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssOwn;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssBaseMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssDtlMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssExtractionMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssKeepingMapper;
import com.xlkfinance.bms.server.mortgage.mapper.ProjectAssOwnMapper;

@SuppressWarnings("unchecked")
@Service("projectAssBaseServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseService")
public class ProjectAssBaseServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(ProjectAssBaseServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssBaseMapper")
	private ProjectAssBaseMapper projectAssBaseMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssDtlMapper")
	private ProjectAssDtlMapper projectAssDtlMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssOwnMapper")
	private ProjectAssOwnMapper projectAssOwnMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssKeepingMapper")
	private ProjectAssKeepingMapper projectAssKeepingMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectAssExtractionMapper")
	private ProjectAssExtractionMapper projectAssExtractionMapper;

	/**
	 * 
	 * @Description: 查询抵质押物列表
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 抵质押物集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:39:13
	 */
	@Override
	public List<ProjectAssBase> getAllProjectAssBase(ProjectAssBase projectAssBase) throws TException {
		List<ProjectAssBase> list = new ArrayList<ProjectAssBase>();
		try {
			list = projectAssBaseMapper.getAllProjectAssBase(projectAssBase);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询抵质押物列表:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 条件查询抵质押物数量
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 总数
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午3:17:02
	 */
	@Override
	public int getAllProjectAssBaseCount(ProjectAssBase projectAssBase) throws TException {
		int count = 0;
		try {
			count = projectAssBaseMapper.getAllProjectAssBaseCount(projectAssBase);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询抵质押物总数:" + e.getMessage());
			}
		}
		return count;
	}

	/**
	 * 
	 * @Description: 根据抵质押物类型和项目ID查询抵质押物列表
	 * @param mortgageGuaranteeType
	 *            抵质押物类型 (1.抵押 2.质押 -1.查所有)
	 * @param projectId
	 *            项目ID
	 * @return 抵质押物集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:38:41
	 */
	@Override
	public List<ProjectAssBase> getProjectAssBaseByMortgageGuaranteeType(int mortgageGuaranteeType, String projectIds) throws TException {
		List<ProjectAssBase> list = new ArrayList<ProjectAssBase>();
		try {
			// 组装查询条件HashMap
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mortgageGuaranteeType", mortgageGuaranteeType);
			map.put("projectIds", projectIds.split(","));
			list = projectAssBaseMapper.getProjectAssBaseByMortgageGuaranteeType(map);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询抵质押物列表:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 批量删除处理
	 * @param loanPids
	 *            抵质押物Pid数组
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:37:40
	 */
	@Override
	public int batchDelete(String loanPids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 把传过来的数据劈开成一个数组作为参数传过去
			String[] strArr = loanPids.split(",");
			rows = projectAssBaseMapper.batchDelete(strArr);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("删除抵质押物:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 新增抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:37:20
	 */
	@Override
	public int addProjectAssBase(ProjectAssBase projectAssBase) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 设置状态为1,新创建的数据默认为1(正常)
			projectAssBase.setStatus(1);
			rows = projectAssBaseMapper.insert(projectAssBase);
			// 获取批量插入的List对象
			List<ProjectAssDtl> list = createProjectAssDtls(projectAssBase.getContent(), projectAssBase.getPid());
			// 批量新增详细信息
			rows = projectAssDtlMapper.addProjectAssDtls(list);
			// 循环添加共有人信息
			String ownUserIds = projectAssBase.getOwns();
			// 判断是否存在共有人信息
			if (null != ownUserIds && !ownUserIds.equals("")) {
				// 劈开成数组
				String[] userPids = ownUserIds.split(",");
				// 循环添加数据
				for (int i = 0; i < userPids.length; i++) {
					// 判断当前共有人是否存在数字
					if (null != userPids[i] && !userPids[i].equals("")) {
						ProjectAssOwn projectAssOwn = new ProjectAssOwn();
						// 赋值
						projectAssOwn.setBaseId(projectAssBase.getPid());// 设置抵质押物ID
						projectAssOwn.setPublicOwnUserId(Integer.parseInt(userPids[i]));// 设置公有人ID
						projectAssOwn.setStatus(1);// 设置状态
						// 添加数据
						projectAssOwnMapper.addProjectAssOwn(projectAssOwn);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增抵质押物:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "添加失败,请重新添加!");
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 修改抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:36:59
	 */
	@Override
	public int updateProjectAssBase(ProjectAssBase projectAssBase) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = projectAssBaseMapper.updateByPrimaryKey(projectAssBase);
			// 先删除抵质押物详细信息
			projectAssDtlMapper.deleteProjectAssDtlByBaseId(projectAssBase.getPid());
			// 获取抵质押物详细信息
			List<ProjectAssDtl> list = createProjectAssDtls(projectAssBase.getContent(), projectAssBase.getPid());
			// 批量新增详细信息
			rows = projectAssDtlMapper.addProjectAssDtls(list);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改抵质押物:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 办理抵质押物处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:31:23
	 */
	@Override
	public int transactProjectAssBase(ProjectAssBase projectAssBase) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = projectAssBaseMapper.transactProjectAssBase(projectAssBase);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("办理抵质押物处理:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 抵质押物保管处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:31:52
	 */
	@Override
	public int safekeepingProjectAssBase(ProjectAssBase projectAssBase) throws ThriftServiceException, TException {
		logger.info("-------抵质押物保管处理开始,抵质押物ID:" + projectAssBase.getPid());
		int rows = 0;
		try {
			// 修改抵质押物状态为:已保管
			rows = projectAssBaseMapper.safekeepingProjectAssBase(projectAssBase);
			// 创建抵质押物保管信息表
			ProjectAssKeeping pak = new ProjectAssKeeping();
			pak.setBaseId(projectAssBase.getPid());// 赋值抵质押物ID
			pak.setSaveUserId(projectAssBase.getProposer());// 赋值保管人ID
			pak.setSaveDttm(projectAssBase.getSaveDttm());// 赋值保管时间
			pak.setSaveRemark(projectAssBase.getSaveRemark());// 赋值保管备注
			pak.setStatus(1);// 状态设置为正常:1
			// 新增抵质押物保管信息
			projectAssKeepingMapper.insert(pak);
			logger.info("抵质押物保管成功....");
		} catch (Exception e) {
			logger.error("抵质押物保管处理:" + e.getMessage());
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 抵质押物提取申请
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:33:30
	 */
	@Override
	public int applyExtractionProjectAssBase(ProjectAssBase projectAssBase) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 修改抵质押物状态
			projectAssBaseMapper.applyExtractionProjectAssBase(projectAssBase);
			// 创建抵质押物提取表
			ProjectAssExtraction pae = new ProjectAssExtraction();
			// 赋值
			pae.setBaseId(projectAssBase.getPid());// 抵质押物ID
			pae.setApplyRemark(projectAssBase.getExtRequestReason());// 提取申请事由
			// 获取系统时间,并设置
			Timestamp time = new Timestamp(new Date().getTime());
			pae.setApplyDttm(time.toString());// 设置申请时间,就是当前系统时间
			pae.setApplyUserId(projectAssBase.getProposer());// 设置申请人ID
			pae.setStatus(1);// 状态设置为正常：1
			// 新增
			rows = projectAssExtractionMapper.insert(pae);
			// 把新的抵质押物提取表的PID赋值给rows传到前台
			rows = pae.getPid();
		} catch (Exception e) {
			logger.error("抵质押物提取申请:" + e.getMessage());
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 抵质押物提取处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:33:33
	 */
	@Override
	public int applyManagetransactProjectAssBase(ProjectAssExtraction projectAssExtraction) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 创建修改抵质押物对象,修改抵质押物的状态为已提取处理
			ProjectAssBase p = new ProjectAssBase();
			p.setPid(projectAssExtraction.getBaseId());
			// 修改状态
			rows = projectAssBaseMapper.applyManagetransactProjectAssBase(p);
			// 修改提取信息
			projectAssExtractionMapper.updateProjectAssExtraction(projectAssExtraction);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("抵质押物提取处理:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 解除抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:33:36
	 */
	@Override
	public int relieveProjectAssBase(ProjectAssBase projectAssBase) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = projectAssBaseMapper.relieveProjectAssBase(projectAssBase);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("解除抵质押物:" + e.getMessage());
			}
		}
		return rows;
	}

	@Override
	public List<ProjectAssBase> getCommonProjectAssBaseByPid(int pid) throws TException {
		return projectAssBaseMapper.getCommonProjectAssBaseByPid(pid);
	}

	/**
	 * 
	 * @Description: 根据抵质押物ID查询抵质押物详细信息
	 * @param pid
	 *            抵质押物ID
	 * @return 抵质押物对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午4:36:09
	 */
	@Override
	public ProjectAssBase getProjectAssBaseByPid(int pid) throws TException {
		return projectAssBaseMapper.getProjectAssBaseByPid(pid);
	}

	@Override
	public int updateProjectAssBaseProcessing(ProjectAssBase projectAssBase) throws TException {
		return projectAssBaseMapper.updateProjectAssBaseProcessing(projectAssBase);
	}

	/**
	 * 
	 * @Description: 根据当前抵质押物ID获取抵质押物详情信息
	 * @param baseId
	 *            抵质押物信息ID
	 * @return 抵质押物详细信息数据集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 上午2:03:11
	 */
	@Override
	public List<ProjectAssDtl> getProjectAssDtlByBaseId(int baseId) throws TException {
		List<ProjectAssDtl> list = new ArrayList<ProjectAssDtl>();
		try {
			list = projectAssDtlMapper.getProjectAssDtlByBaseId(baseId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据当前抵质押物ID获取抵质押物详情信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 生成详细信息集合
	 * @param content
	 *            封装好的详细信息('pid1:zhi1:name1,pid2:zhi2:name2')
	 * @param baseId
	 *            抵质押物ID
	 * @return 详细信息集合
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 下午5:04:29
	 */
	public List<ProjectAssDtl> createProjectAssDtls(String content, int baseId) {
		// 劈开
		String[] assDtls = content.split(",");
		// 批量插入的List对象
		List<ProjectAssDtl> list = new ArrayList<ProjectAssDtl>();
		// 循环创建对象，存入List里面
		for (int i = 0; i < assDtls.length; i++) {
			// 再把每一个arr劈开成一个对象数据
			String[] arr = assDtls[i].split(":");
			// 创建对象
			ProjectAssDtl projectAssDtl = new ProjectAssDtl();
			// 赋值
			projectAssDtl.setBaseId(baseId);// 赋值抵质押物ID
			projectAssDtl.setLookupId(Integer.parseInt(arr[0]));// 赋值数据字典ID
			projectAssDtl.setLookupVal(arr[2]);// 赋值数据字典的val
			projectAssDtl.setInfoVal(arr[1]);// 赋值用户录入的值
			System.out.println("-----------------------------------------" + arr[1]);
			projectAssDtl.setStatus(1);// 设置状态为1,正常
			// 添加到List
			list.add(projectAssDtl);
		}
		return list;
	}

	/**
	 * 
	 * @Description: 批量撤销抵质押物状态(把状态修改为 等待抵质押物办理 状态)
	 * @param pids
	 *            抵质押物ID(1,2,3)
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年7月3日 上午11:30:41
	 */
	@Override
	public int revokeProjectAssBase(String pids) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 劈开成数组
			String[] pidArr = pids.split(",");
			rows = projectAssBaseMapper.revokeProjectAssBase(pidArr);
		} catch (Exception e) {
			logger.error("批量撤销抵质押物状态(把状态修改为 等待抵质押物办理 状态):" + e.getMessage());
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 抵质押办理资料上传
	 * @param projectAssFile
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年5月20日 下午11:01:03
	 */
	public int saveProjectAssFile(ProjectAssFile projectAssFile) throws ThriftServiceException, TException {
		int count = projectAssDtlMapper.saveProjectAssFile(projectAssFile);
		return count;
	}

	/**
	 * 
	 * @Description: 根据baseId,文件类型查询文件资料
	 * @param baseId
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年5月20日 下午11:27:43
	 */
	public List<ProjectAssFile> getProjectAssFile(int baseId, String fileType) throws ThriftServiceException, TException {
		List<ProjectAssFile> list = new ArrayList<ProjectAssFile>();
		list = projectAssDtlMapper.getProjectAssFile(baseId, fileType);
		return list == null ? new ArrayList<ProjectAssFile>() : list;
	}

	/**
	 * 
	 * @Description: 修改文件资料信息
	 * @param projectAssFile
	 * @return
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: xuweihao
	 * @date: 2015年5月20日 下午11:27:55
	 */
	public int editProjectAssFile(ProjectAssFile projectAssFile) throws ThriftServiceException, TException {
		int count = projectAssDtlMapper.editProjectAssFile(projectAssFile);
		return count;
	}

	@Override
	public int delProjectAssFile(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			count = projectAssDtlMapper.delProjectAssFile(Integer.parseInt(arr[i]));
		}
		return count;
	}

	/**
	 * 
	 * @Description: 新增抵质押物共有人
	 * @param projectAssOwn
	 *            抵质押物共有人对象
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午4:57:36
	 */
	@Override
	public int addProjectAssOwn(ProjectAssOwn projectAssOwn) throws ThriftServiceException, TException {
		int count = 0;
		try {
			// 循环新增抵质押物共有人信息
			// 获取抵质押物共有人ID
			String ownUserIds = projectAssOwn.getOwnUserIds();
			if (null != ownUserIds && !ownUserIds.equals("")) {
				// 劈开成数组
				String[] userPids = ownUserIds.split(",");
				// 循环添加数据
				for (int i = 0; i < userPids.length; i++) {
					// 判断当前抵质押物的公用人是否存在
					if (projectAssOwnMapper.checkBaseIdAndOwnUser(projectAssOwn.getBaseId(), Integer.parseInt(userPids[i])) == 0) {
						// 赋值
						projectAssOwn.setPublicOwnUserId(Integer.parseInt(userPids[i]));// 设置公有人ID
						projectAssOwn.setStatus(1);// 设置状态
						// 添加数据
						count = projectAssOwnMapper.addProjectAssOwn(projectAssOwn);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增抵质押物共有人:" + e.getMessage());
			}
		}
		return count;
	}

	/**
	 * 
	 * @Description: 删除抵质押物共有人-根据抵质押物ID
	 * @param baseId
	 *            抵质押物ID
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午4:58:12
	 */
	@Override
	public int deleteProjectAssOwn(int baseId) throws ThriftServiceException, TException {
		int count = 0;
		try {
			count = projectAssOwnMapper.deleteProjectAssOwn(baseId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("删除抵质押物共有人-根据抵质押物ID:" + e.getMessage());
			}
		}
		return count;
	}

	/**
	 * 
	 * @Description: 批量删除抵质押物共有人-根据主键
	 * @param pids
	 *            主键(1,2,3)
	 * @return 受影响的行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午4:58:38
	 */
	@Override
	public int batchDeleteProjectAssOwn(String pids) throws ThriftServiceException, TException {
		int count = 0;
		try {
			String[] arr = pids.split(",");
			count = projectAssOwnMapper.batchDeleteProjectAssOwn(arr);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("删除抵质押物共有人-根据抵质押物ID:" + e.getMessage());
			}
		}
		return count;
	}

	/**
	 * 
	 * @Description: 查询抵质押物共有人-根据抵质押物ID
	 * @param baseId
	 *            抵质押物ID
	 * @return 共有人列表集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午4:59:07
	 */
	@Override
	public List<ProjectAssOwn> getProjectAssOwnByBaseId(int baseId, int ownType) throws TException {
		List<ProjectAssOwn> list = new ArrayList<ProjectAssOwn>();
		try {
			list = projectAssOwnMapper.getProjectAssOwnByBaseId(baseId, ownType);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询抵质押物共有人-根据抵质押物ID:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询抵质押物共有人-根据类型人ID和所有人类型查询
	 * @param relaIds
	 *            人员ID字符串(1,2,3)
	 * @param ownType
	 *            所有人类型
	 * @return 共有人列表集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午12:00:42
	 */
	@Override
	public List<ProjectAssOwn> getProjectAssOwnByRelationId(String relaIds, int ownType) throws TException {
		List<ProjectAssOwn> list = new ArrayList<ProjectAssOwn>();
		try {
			String[] arr = relaIds.split(",");
			// 创建条件 Map
			Map<String, Object> map = new HashMap<String, Object>();
			// 赋值Map
			map.put("ownType", ownType);
			map.put("pids", arr);
			list = projectAssOwnMapper.getProjectAssOwnByRelationId(map);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询抵质押物共有人-根据类型人ID和所有人类型查询:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询所有-抵质押物共有人列表
	 * @param projectAssOwn
	 *            条件对象
	 * @return 共有人列表集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午12:43:17
	 */
	@Override
	public List<ProjectAssOwn> getAllProjectAssOwnByOwnType(ProjectAssOwn projectAssOwn) throws TException {
		List<ProjectAssOwn> list = new ArrayList<ProjectAssOwn>();
		try {
			list = projectAssOwnMapper.getAllProjectAssOwnByOwnType(projectAssOwn);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询所有-抵质押物共有人列表:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询所有-抵质押物共有人数量
	 * @param projectAssOwn
	 *            条件对象
	 * @return 共有人数量
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午1:02:12
	 */
	@Override
	public int getAllProjectAssOwnByOwnTypeCount(ProjectAssOwn projectAssOwn) throws TException {
		int count = 0;
		try {
			count = projectAssOwnMapper.getAllProjectAssOwnByOwnTypeCount(projectAssOwn);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询所有-抵质押物共有人数量:" + e.getMessage());
			}
		}
		return count;
	}

}
