package com.xlkfinance.bms.server.workday.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.workday.WorkDay;
import com.xlkfinance.bms.rpc.workday.WorkDayService.Iface;
import com.xlkfinance.bms.server.workday.mapper.WorkDayMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： Jony <br>
 * ★☆ @time：2017年7月1日上午11:14:38 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 法定节假日service<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings("unchecked")
@Service("WorkDayService")
@ThriftService(service = "com.xlkfinance.bms.rpc.workday.WorkDayService")
public class WorkDayServiceImpl implements Iface {
   @Resource(name = "workDayMapper")
   private WorkDayMapper workDayMapper;

	@Override
	public int insert(WorkDay workDay) throws TException {
		return workDayMapper.insert(workDay);
	}
	
	@Override
	public int update(WorkDay workDay) throws TException {
		return workDayMapper.update(workDay);
	}
	
	@Override
	public int deleteById(int pid) throws TException {
		return workDayMapper.deleteById(pid);
	}
	
	@Override
	public List<WorkDay> getWorkDay(WorkDay workDay) throws TException {
		return workDayMapper.getWorkDay(workDay);
	}
	
	@Override
	public int geWorkDayTotal(WorkDay workDay) throws TException {
		return workDayMapper.getWorkDayTotal(workDay);
	}
  
}
