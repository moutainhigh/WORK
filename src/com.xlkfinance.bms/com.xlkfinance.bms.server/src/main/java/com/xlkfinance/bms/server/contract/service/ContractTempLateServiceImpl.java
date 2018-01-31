/**
 * @ClassName: SysPermissionServiceImpl
 * @Description: 
 * @author: Chong.Zeng
 * @date: 2015年1月6日 下午4:05:04
 */
package com.xlkfinance.bms.server.contract.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.ContractTempLate;
import com.xlkfinance.bms.rpc.contract.ContractTempLateParm;
import com.xlkfinance.bms.rpc.contract.ContractTempLateService.Iface;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.server.contract.mapper.ContractMapper;
import com.xlkfinance.bms.server.contract.mapper.ContractTempLateMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;

@Service("contractTempLateServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.contract.ContractTempLateService")
public class ContractTempLateServiceImpl implements Iface {
	@SuppressWarnings("rawtypes")
	@Resource(name = "contractTempLateMapper")
	private ContractTempLateMapper contractTempLateMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "contractMapper")
	private ContractMapper contractMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;

	private Logger logger = LoggerFactory.getLogger(ContractTempLateServiceImpl.class);

	/**
	 * @Description: 增加合同模板
	 * @param contractTempLate
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2015年1月6日 下午5:09:50
	 */
	@Override
	public int addContractTempLate(ContractTempLate contractTempLate) throws ThriftServiceException, TException {
		logger.info("新增合同模板信息   ContractTempLateServiceImpl.addContractTempLate pid["+contractTempLate.getPid()+"] contractName["+contractTempLate.getTemplateName()+"] templateParFun["+contractTempLate.getTemplateParFun()+"]");
		
		try {
			boolean status = contractTempLateMapper.addTempLate(contractTempLate);
			if (!status) {
				return 0;
			}
			return contractTempLate.getPid();
		} catch (Exception e) {
			logger.error("插入数据库错误" + e.getMessage());
			return 0;
		}
	}

	/**
	 * @Description: 查询所有模板信息
	 * @param c
	 * @return List<ContractTempLate>
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 上午10:11:56
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContractTempLate> pageTempLateList(ContractTempLate contractTempLate) throws TException {
		return contractTempLateMapper.pageTempLateList(contractTempLate);
	}

	/**
	 * @Description: 获取总记录数
	 * @param c
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 上午10:12:32
	 */
	@Override
	public int pageTotalCount(ContractTempLate contractTempLate) throws TException {
		return contractTempLateMapper.pageTempLateCount(contractTempLate);
	}

	/**
	 * @Description: 修改合同模板文件存储地址
	 * @param contractTempLate
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 上午11:27:28
	 */
	@Override
	public boolean updateTempLate(ContractTempLate contractTempLate) throws TException {
		logger.info("修改合同模板信息   ContractTempLateServiceImpl.updateTempLate pid["+contractTempLate.getPid()+"] contractName["+contractTempLate.getTemplateName()+"] templateParFun["+contractTempLate.getTemplateParFun()+"]");
		return contractTempLateMapper.updateTempLateById(contractTempLate);
	}

	/**
	 * @Description: 修改模板状态
	 * @param pidArray
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年1月9日 下午3:59:41
	 */
	@Override
	public boolean deleteTempLate(String pidArray) throws TException {
		logger.info("删除合同模板信息 ContractTempLateServiceImpl.deleteTempLate pidArray["+pidArray+"]");
		boolean del = true;
		String[] pids = pidArray.split(",");
		for (String string : pids) {
			int pid = Integer.parseInt(string);
			del = contractTempLateMapper.deleteTempLate(pid);
		}
		return del;
	}

	/**
	 * @Description: 新增合同模板参数
	 * @param contractTempLateParm
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年1月10日 上午11:36:36
	 */
	@Override
	public boolean addTempLateParm(List<ContractTempLateParm> contractTempLateParm) throws TException {
		boolean status = false;

		for (ContractTempLateParm contractTempLateParm2 : contractTempLateParm) {
			status = contractTempLateMapper.addTempLateParm(contractTempLateParm2);
		}
		return status;
	}

	private boolean hasMatchInList(List<ContractTempLateParm> listCtp, String match) {
		for (ContractTempLateParm p : listCtp) {
			if (p.getMatchFlag().equalsIgnoreCase(match)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Description: 根据模板ID查询该模板的所有匹配符
	 * @param tempLateId
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年2月3日 下午2:16:12
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateTempLateParm(List<ContractTempLateParm> listCtp) throws TException {
		if (listCtp != null) {
			logger.info("修改合同模板参数信息 ContractTempLateServiceImpl.updateTempLateParm contractTemplateId["+listCtp.get(0).getContractTemplateId()+"]");
			List<ContractTempLateParm> insertList = new ArrayList<ContractTempLateParm>();
			List<TempLateParmDto> oldList = contractTempLateMapper.getTempLateParmListTid(listCtp.get(0).getContractTemplateId());
			List<String> tempDontUpdateList = new ArrayList<String>();

			// 删除已存在但目前无效的参数
			if (oldList != null && oldList.size() > 0) {
				for (TempLateParmDto old : oldList) {
					if (!hasMatchInList(listCtp, old.getMatchFlag())) {
						// delete
						contractTempLateMapper.deleteTempLateParmByParmId(old.getPid());
					} else {
						tempDontUpdateList.add(old.getMatchFlag());
					}
				}
			}

			// 将已经存在的并且本次上传也有的参数去掉，避免重复上传
			if (tempDontUpdateList != null && tempDontUpdateList.size() > 0) {
				for (ContractTempLateParm p : listCtp) {
					if (!tempDontUpdateList.contains(p.getMatchFlag())) {
						insertList.add(p);
					}
				}
			} else {
				insertList = listCtp;
			}

			// 添加处理后的结果
			for (ContractTempLateParm parm : insertList) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("tempLateId", parm.getContractTemplateId());
				parameter.put("matchFlag", parm.getMatchFlag());

				contractTempLateMapper.addTempLateParm(parm);
			}
		}

		return true;
	}

	/**
	 * @Description: 根据模板ID查询模板信息
	 * @param t
	 * @return List<TempLateParmDto>
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午6:19:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TempLateParmDto> getTempParmList(TempLateParmDto tempLateParmDto) throws TException {
		return contractTempLateMapper.getTempParmList(tempLateParmDto);
	}

	/**
	 * @Description: 查询模板参数记录数
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午7:04:53
	 */
	@Override
	public int getTempTotaleCount(TempLateParmDto tempLateParmDto) throws TException {
		logger.info("查询合同模板统计数量  ContractTempLateServiceImpl.getTempTotaleCount contractTemplateId["+tempLateParmDto.getContractTemplateId()+"]");
		return contractTempLateMapper.getTempTotaleCount(tempLateParmDto.getContractTemplateId());
	}

	/**
	 * @Description: 修改模板文件参数
	 * @param t
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年1月14日 下午1:49:41
	 */
	@Override
	public boolean updateTempLateParmDto(List<TempLateParmDto> listCtp) throws TException {
		boolean status = false;
		for (TempLateParmDto tempLateParmDto : listCtp) {
			status = contractTempLateMapper.updateTempLateParmDto(tempLateParmDto);
		}
		return status;
	}

	/**
	 * @Description: 根据模板ID删除模板匹配符
	 * @param c
	 * @return boolean
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午3:57:42
	 */
	@Override
	public boolean delTempLateParm(int ContractTemplateId) throws TException {
		logger.info("删除该模板的所有匹配符数据 ContractTempLateServiceImpl.delTempLateParm ContractTemplateId["+ContractTemplateId+"]");
		boolean status = false;
		// 删除该模板的所有匹配符数据
		status = contractTempLateMapper.deleteTempLateParm(ContractTemplateId);
		return status;
	}

	/**
	 * @Description: 删除模板参数中的一个
	 * @param pid
	 * @return
	 * @author: xuweihao
	 * @date: 2015年3月13日 下午1:14:07
	 */
	@Override
	public boolean delTempLateOneParm(int pid) throws TException {
		logger.info("删除该模板的所有匹配符数据 ContractTempLateServiceImpl.delTempLateOneParm pid["+pid+"]");
		boolean status = false;
		// 删除该模板的所有匹配符数据
		status = contractTempLateMapper.delTempLateOneParm(pid);
		return status;
	}

}
