package com.xlkfinance.bms.server.workday.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.workday.WorkDay;

@MapperScan("workDayMapper")
public interface WorkDayMapper<T, PK> extends BaseMapper<T, PK> {
	   /**
	    * 节假日列表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2017年7月1日上午11:28:04
	    *@param WorkDay
	    *@return
	    */
	List<WorkDay> getWorkDay(WorkDay workDay);
	   /**
	    * 节假日列表 查询总数
	    *@author:Jony
	    *@time:2017年7月1日上午11:28:04
	    *@param WorkDay
	    *@return
	    */
	int getWorkDayTotal(WorkDay workDay);
	   /**
	    * 新增法定节假日
	    *@author:Jony
	    *@time:2017年7月1日上午11:28:04
	    *@param WorkDay
	    *@return
	    */
	int insert (WorkDay workDay);
	   /**
	    * 修改法定节假日
	    *@author:Jony
	    *@time:2017年7月3日上午11:28:04
	    *@param WorkDay
	    *@return
	    */
	int update(WorkDay workDay);
	
	   /**
	    * 修改法定节假日
	    *@author:Jony
	    *@time:2017年7月3日上午11:28:04
	    *@param WorkDay
	    *@return
	    */
	int deleteById(@Param("pid") int pid);
}
