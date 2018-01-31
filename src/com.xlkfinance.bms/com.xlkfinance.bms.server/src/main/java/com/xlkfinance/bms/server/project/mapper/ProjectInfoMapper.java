package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.ProjectDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentDetailIndexDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;

@MapperScan("projectInfoMapper")
public interface ProjectInfoMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 * 根据条件分页查询抵押贷列表
	 * @param query
	 * @return
	 */
	public List<ProjectDTO> getProjectByPage(ProjectDTO query);
	/**
	 * 根据条件查询抵押贷总数
	 * @param query
	 * @return
	 */
	public int getProjectCount(ProjectDTO query);
	
	/**
	 * 
	 * queryRepayment:根据项目ID查询项目的财务明细. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param query
	 * @return
	 *
	 */
	public List<RepaymentDetailIndexDTO> queryRepayment (RepaymentDetailIndexDTO query);
	
	/**
	 * 
	 * queryOverdueByProject:根据项目ID查询项目逾期信息（抵押贷）. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param projectId
	 * @return
	 *
	 */
	public RepaymentDetailIndexDTO queryOverdueByProject(int projectId);
	
	/**
	 * 
	 * updateRepayments:批量修改还款计划中的本期状态以及对账状态. <br/>
	 * @author baogang
	 * @param planList
	 * @return
	 *
	 */
	public int updateRepayments(List<RepaymentPlanBaseDTO> list);
}
