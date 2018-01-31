package com.xlkfinance.bms.server.task.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.task.TaskService.Iface;
import com.xlkfinance.bms.rpc.task.TaskSettingVo;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.server.task.mapper.TaskMapper;
import com.xlkfinance.bms.server.workflow.service.WorkflowServiceImpl;

@SuppressWarnings("rawtypes")
@Service("taskServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.task.TaskService")
public class TaskServiceImpl implements Iface {

	private Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

	@Resource(name = "taskMapper")
	private TaskMapper taskMapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskVo> getTaskVosByTaskVo(TaskVo taskVo)
			throws ThriftServiceException, TException {
		try {
			return taskMapper.getTaskVosByTaskVo(taskVo);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertTaskVos(List<TaskVo> taskVos)
			throws ThriftServiceException, TException {
		taskMapper.insertTaskVos(taskVos);
	}

	@Override
	public void insertTaskVo(TaskVo taskVo) throws ThriftServiceException,
			TException {
		try {
			taskMapper.insertTaskVo(taskVo);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateTaskVos(List<TaskVo> taskVos)
			throws ThriftServiceException, TException {
		try {
			taskMapper.updateTaskVos(taskVos);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskSettingVo> findTaskSettingsByUserId(int userId)
			throws ThriftServiceException, TException {
		try {
			return taskMapper.findTaskSettingsByUserId(userId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			return null;
		}
	}

	@Override
	public void insertTaskSetting(TaskSettingVo settingVo)
			throws ThriftServiceException, TException {
		try {
			if (null != settingVo) {
				if (0 != settingVo.getPid()) {
					taskMapper.updateTaskSetting(settingVo);
				} else {
					settingVo.setCreDttm(DateUtil.format(new Date()));
					taskMapper.insertTaskSetting(settingVo);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	@Override
	public void updateTaskSetting(TaskSettingVo settingVo)
			throws ThriftServiceException, TException {
		try {
			taskMapper.updateTaskSetting(settingVo);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	@Override
	public void deleteTaskSetting(int pid) throws ThriftServiceException,
			TException {
		try {
			taskMapper.deleteTaskSetting(pid);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void deleteTaskSettings(List<Integer> pids) throws ThriftServiceException,
			TException {
		try {
			taskMapper.deleteTaskSettings(pids);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}
}
