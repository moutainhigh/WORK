package com.xlkfinance.bms.server.task.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.dao.DataAccessException;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.task.TaskSettingVo;
import com.xlkfinance.bms.rpc.workflow.TaskVo;

@MapperScan("taskMapper")
public interface TaskMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	  * @Description: 根据用户Id查询任务列表
	  * @param taskVo 用户名
	  * @return
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:12:15
	  */
	public List<TaskVo> getTaskVosByTaskVo(TaskVo taskVo) throws DataAccessException;

	/**
	  * @Description: 新增任务集合
	  * @param taskVos  任务TaskVo实体类集合
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:11:55
	  */
	
	public void insertTaskVos(List<TaskVo> taskVos) throws DataAccessException;

	/**
	  * @Description: 新增任务
	  * @param taskVo 任务TaskVo实体类
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:11:26
	  */
	public void insertTaskVo(TaskVo taskVo) throws DataAccessException;

	/**
	  * @Description: 更改任表集合
	  * @param taskVos 任务列表集合
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:11:11
	 */
	public void updateTaskVos(List<TaskVo> taskVos) throws DataAccessException;

	/**
	  * @Description: 根据用户据Id查询设定任务列表
	  * @param userId
	  * @return List<TaskSettingVo>
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:10:47
	  */
	public List<TaskSettingVo> findTaskSettingsByUserId(int userId) throws DataAccessException;

	/**
	  * @Description: 新增设定任务
	  * @param settingVo
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:10:34
	  */
	public void insertTaskSetting(TaskSettingVo settingVo) throws DataAccessException;

	/**
	  * @Description:修改设定任务
	  * @param settingVo 
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:10:15
	  */
	public void updateTaskSetting(TaskSettingVo settingVo) throws DataAccessException;

	/**
	 * @Description: 查询用户的代办人员
	 * @param userId
	 * @param now
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年2月8日 上午11:45:08
	 */
	public List<TaskSettingVo> findTaskSettingsByTaskSetting(TaskSettingVo taskSettingVo);
	
	/**
	  * @Description: 根据用户名查询任务设定
	  * @param userName
	  * @param date
	  * @return List<TaskSettingVo>
	  * @author: Daijingyu
	  * @date: 2015年2月9日 上午10:35:42
	  */
	public List<TaskSettingVo> findTaskSettingsByUserIdInNow(Map<String,String> map);

	/**
	  * @Description: 根据Id 删除已设定的任务
	  * @param pid 
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:08:56
	  */
	public void deleteTaskSetting(int pid) throws DataAccessException;
	
	/**
	  * @Description: 根据pids 批量删除
	  * @param pids
	  * @throws DataAccessException
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 上午11:58:40
	  */
	public void deleteTaskSettings(List<Integer> pids) throws DataAccessException;
}
