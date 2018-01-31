package com.xlkfinance.bms.server.foreafterloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.foreafterloan.ExceptionMonitorIndex;
import com.xlkfinance.bms.rpc.foreafterloan.ForeAfterLoanService.Iface;
import com.xlkfinance.bms.rpc.foreafterloan.LegalIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitApplyReportIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitExceptionIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitMonitorIndex;
import com.xlkfinance.bms.server.foreafterloan.mapper.ForeAfterLoanMapper;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-04 15:53:20 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Service("foreAfterLoanServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.foreafterloan.ForeAfterLoanService")
public class ForeAfterLoanServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(ForeAfterLoanServiceImpl.class);

    @Resource(name = "foreAfterLoanMapper")
    private ForeAfterLoanMapper foreAfterLoanMapper;

    /** 在途业务监控列表(分页查询)
     *
     * @author:liangyanjun
     * @time:2017年1月12日上午10:07:24 */
    @Override
    public List<TransitMonitorIndex> queryTransitMonitorIndex(TransitMonitorIndex query) throws TException {
        return foreAfterLoanMapper.queryTransitMonitorIndex(query);
    }

    /** 在途业务监控列表总数
     *
     * @author:liangyanjun
     * @time:2017年1月12日上午10:07:24 */
    @Override
    public int getTransitMonitorIndexTotal(TransitMonitorIndex query) throws TException {
        return foreAfterLoanMapper.getTransitMonitorIndexTotal(query);
    }

    /** 异常监控列表(分页查询)
    *
    * @author:Jony
    * @time:2017年1月12日上午10:07:24 */
	@Override
	public List<TransitExceptionIndex> queryTransitExceptionIndex(
			TransitExceptionIndex query) throws TException {
		 return foreAfterLoanMapper.queryTransitExceptionIndex(query);
	}

    /** 异常监控列表总数
    *
    * @author:Jony
    * @time:2017年1月12日上午10:07:24 */
	@Override
	public int getTransitExceptionIndexTotal(TransitExceptionIndex query)
			throws TException {
		 return foreAfterLoanMapper.getTransitExceptionIndexTotal(query);
	}
	/** 插入申请报表表
    * @author:Jony
    * @time:2017年1月12日上午10:07:24 */
	@Override
	public int addTransitApplyReportIndex(TransitApplyReportIndex query)
			throws TException {
		int rows = 0;
		rows = foreAfterLoanMapper.addTransitApplyReportIndex(query);
		return rows;
	}
	
	  /** 根据Id修改异常表的申请状态
    *
    * @author:Jony
    * @time:2017年1月12日上午10:07:24 */
	@Override
	public int updateExceptionIndex(String id) throws TException {
		return foreAfterLoanMapper.updateExceptionIndex(id);
	}
	
	/** 添加诉讼表
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public int addLegalIndex(LegalIndex query) throws TException {
		int rows = 0;
		rows = foreAfterLoanMapper.addLegalIndex(query);
		return rows;
	}
	
	  /** 根据Id修改诉讼表
    * @author:Jony
    * @time:2017年1月12日上午10:07:24 */
	@Override
	public int updateLegalIndex(String id) throws TException {
		return foreAfterLoanMapper.updateLegalIndex(id);
	}
	
	/** 添加异常业务监控
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public int addExceptionMonitorIndex(ExceptionMonitorIndex query)
			throws TException {
		int rows = 0;
		rows = foreAfterLoanMapper.addExceptionMonitorIndex(query);
		return rows;
	}
	
	/** 异常业务监控
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public List<ExceptionMonitorIndex> queryExceptionMonitorIndex(
			ExceptionMonitorIndex query) throws TException {
		return foreAfterLoanMapper.queryExceptionMonitorIndex(query);
	}
	
	/** 异常业务监控总数
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public int getExceptionMonitorIndexTotal(ExceptionMonitorIndex query)
			throws TException {
		 return foreAfterLoanMapper.getExceptionMonitorIndexTotal(query);
	}

	/** 根据Id查询申请报告
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public TransitApplyReportIndex getById(int projectId) throws TException {
		return foreAfterLoanMapper.getById(projectId);
	}
	
	/** 根据Id查询诉讼表
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public List<LegalIndex> getLegalListByReport(int reportId)
			throws TException {
		 return foreAfterLoanMapper.getLegalListByReport(reportId);
	}
	
	/** 修改申请报告
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public int updateTransitApplyReportIndex(TransitApplyReportIndex transitApplyReportIndex) throws TException {
		 return foreAfterLoanMapper.updateTransitApplyReportIndex(transitApplyReportIndex);
	}
	
	/** 根据pid删除诉讼表
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public int deleteLegalIndexByIds(List<Integer> pids) throws TException {
		 return foreAfterLoanMapper.deleteLegalIndexByIds(pids);
	}
	
	/** 修改诉讼表
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public int updateLegal(LegalIndex query) throws TException {
		 return foreAfterLoanMapper.updateLegal(query);
	}
	/** 根据项目Id查询异常列表
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public List<ExceptionMonitorIndex> getExceptionMonitorIndexById(
			int projectId) throws TException {
		return foreAfterLoanMapper.getExceptionMonitorIndexById(projectId);
	}
	/** 根据项目Id查询异常监控列表
	 * @author:Jony
	* @time:2017年1月12日上午10:07:24 */
	@Override
	public TransitExceptionIndex getTransitExceptionIndexById(int projectId)
			throws TException {
		return foreAfterLoanMapper.getTransitExceptionIndexById(projectId);
	}
	
}
