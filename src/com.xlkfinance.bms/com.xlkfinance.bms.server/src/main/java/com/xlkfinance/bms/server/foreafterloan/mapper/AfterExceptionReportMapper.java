package com.xlkfinance.bms.server.foreafterloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReport;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-16 10:47:58 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 贷后异常上报表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@MapperScan("afterExceptionReportMapper")
public interface AfterExceptionReportMapper<T, PK> extends BaseMapper<T, PK> {
    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    public List<AfterExceptionReport> getAll(AfterExceptionReport afterExceptionReport);

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    public AfterExceptionReport getById(int pid);

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    public int insert(AfterExceptionReport afterExceptionReport);

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    public int update(AfterExceptionReport afterExceptionReport);

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    public int deleteById(int pid);

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-16 10:47:58 */
    public int deleteByIds(List<Integer> pids);

    /** 贷后异常上报列表(分页查询)
     *
     * @author:liangyanjun
     * @time:2017年1月16日上午11:09:07 */
    public List<AfterExceptionReport> queryAfterExceptionReportHisList(AfterExceptionReport query);

    /** 贷后异常上报列表总数
     *
     * @author:liangyanjun
     * @time:2017年1月16日上午11:09:07 */
    public int getAfterExceptionReportHisTotal(AfterExceptionReport query);
    /** 贷后异常上报列表(分页查询)
    *
    * @author:liangyanjun
    * @time:2017年1月16日上午11:09:07 */
   public List<AfterExceptionReport> getByProjectId(@Param(value = "projectId")int projectId);
}
