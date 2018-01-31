package com.xlkfinance.bms.server.foreafterloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReport;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReportService.Iface;
import com.xlkfinance.bms.server.foreafterloan.mapper.AfterExceptionReportMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-16 10:47:58 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 贷后异常上报表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Service("afterExceptionReportServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReportService")
public class AfterExceptionReportServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(AfterExceptionReportServiceImpl.class);

    @Resource(name = "afterExceptionReportMapper")
    private AfterExceptionReportMapper afterExceptionReportMapper;

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    @Override
    public List<AfterExceptionReport> getAll(AfterExceptionReport afterExceptionReport) throws ThriftServiceException, TException {
        return afterExceptionReportMapper.getAll(afterExceptionReport);
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    @Override
    public AfterExceptionReport getById(int pid) throws ThriftServiceException, TException {
        AfterExceptionReport afterExceptionReport = afterExceptionReportMapper.getById(pid);
        if (afterExceptionReport == null) {
            return new AfterExceptionReport();
        }
        return afterExceptionReport;
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    @Override
    public int insert(AfterExceptionReport afterExceptionReport) throws ThriftServiceException, TException {
        return afterExceptionReportMapper.insert(afterExceptionReport);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    @Override
    public int update(AfterExceptionReport afterExceptionReport) throws ThriftServiceException, TException {
        return afterExceptionReportMapper.update(afterExceptionReport);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    @Override
    public int deleteById(int pid) throws ThriftServiceException, TException {
        return afterExceptionReportMapper.deleteById(pid);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    @Override
    public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
        return afterExceptionReportMapper.deleteByIds(pids);
    }

    /** 贷后异常上报列表(分页查询)
     *
     * @author:liangyanjun
     * @time:2017年1月16日上午11:09:07 */
    @Override
    public List<AfterExceptionReport> queryAfterExceptionReportHisList(AfterExceptionReport query) throws TException {
        return afterExceptionReportMapper.queryAfterExceptionReportHisList(query);
    }

    /** 贷后异常上报列表总数
     *
     * @author:liangyanjun
     * @time:2017年1月16日上午11:09:07 */
    @Override
    public int getAfterExceptionReportHisTotal(AfterExceptionReport query) throws TException {
        return afterExceptionReportMapper.getAfterExceptionReportHisTotal(query);
    }
    /** 根据项目Id查询贷后异常上报列表集合
    *
    * @author:liangyanjun
    * @time:2017年1月16日上午11:09:07 */
	@Override
	public List<AfterExceptionReport> getByProjectId(@Param(value = "projectId")int projectId)
			throws TException {
		 return afterExceptionReportMapper.getByProjectId(projectId);
	}

}
