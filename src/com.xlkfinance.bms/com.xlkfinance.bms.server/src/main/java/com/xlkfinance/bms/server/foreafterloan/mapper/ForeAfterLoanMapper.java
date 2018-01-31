package com.xlkfinance.bms.server.foreafterloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.foreafterloan.ExceptionMonitorIndex;
import com.xlkfinance.bms.rpc.foreafterloan.LegalIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitApplyReportIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitExceptionIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitMonitorIndex;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-04 15:53:20 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@MapperScan("foreAfterLoanMapper")
public interface ForeAfterLoanMapper<T, PK> extends BaseMapper<T, PK> {
    /**
     * 在途业务监控列表(分页查询)
     *@author:liangyanjun
     *@time:2017年1月12日上午10:09:10
     *@param query
     *@return
     */
    List<TransitMonitorIndex> queryTransitMonitorIndex(TransitMonitorIndex query);
    /**
     * 在途业务监控列表总数
     *@author:liangyanjun
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int getTransitMonitorIndexTotal(TransitMonitorIndex query);
    
    /**
     * 异常监控列表(分页查询)
     *@author:jony
     *@time:2017年1月12日上午10:09:10
     *@param query
     *@return
     */
    List<TransitExceptionIndex> queryTransitExceptionIndex(TransitExceptionIndex query);
    /**
     * 异常列表总数
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int getTransitExceptionIndexTotal(TransitExceptionIndex query);   
    
    /**
     * 添加申请报告表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int addTransitApplyReportIndex(TransitApplyReportIndex query);
    /**
     * 根据Id修改异常表的申请状态
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int updateExceptionIndex(@Param(value = "projectId")String projectId);
    /**
     * 添加申请报告表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int addLegalIndex(LegalIndex query);
    /**
     * 添加异常监控表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int addExceptionMonitorIndex(ExceptionMonitorIndex query);
    
    /**
     * 根据Id修改异常表的申请状态
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int updateLegalIndex(@Param(value = "exceptionId")String exceptionId);
    
    /**
     * 异常监控列表(分页查询)
     *@author:jony
     *@time:2017年1月12日上午10:09:10
     *@param query
     *@return
     */
    List<TransitExceptionIndex> queryExceptionMonitorIndex(ExceptionMonitorIndex query);
    /**
     * 异常列表总数
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int getExceptionMonitorIndexTotal(ExceptionMonitorIndex query); 
    
    /**
     * 根据Id查询申请报告
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    TransitApplyReportIndex getById(@Param(value = "projectId")int projectId); 
    
    /**
     * 根据Id查询诉讼表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    List<LegalIndex> getLegalListByReport(@Param(value = "reportId")int reportId);
    
    /**
     * 根据Id修改申请报告表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int updateTransitApplyReportIndex(TransitApplyReportIndex transitApplyReportIndex);
    
    /**
     * 根据pid删除诉讼表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int deleteLegalIndexByIds(List<Integer> pids);
    
    /**
     * 修改诉讼表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    int updateLegal(LegalIndex query);
    
    
    /**
     * 根据Id查询异常表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    List<ExceptionMonitorIndex> getExceptionMonitorIndexById(@Param(value = "projectId")int projectId);
    
    /**
     * 根据Id查询异常监控表
     *@author:jony
     *@time:2017年1月12日上午10:09:15
     *@param query
     *@return
     */
    TransitExceptionIndex getTransitExceptionIndexById(@Param(value = "projectId")int projectId);
}
