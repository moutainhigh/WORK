package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-13 09:58:28 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 差诉讼历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@MapperScan("checkLitigationHisMapper")
public interface CheckLitigationHisMapper<T, PK> extends BaseMapper<T, PK> {
    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    public List<CheckLitigationDTO> getAll(CheckLitigationDTO checkLitigation);

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    public CheckLitigationDTO getById(int pid);

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    public int insert(CheckLitigationDTO checkLitigation);

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    public int update(CheckLitigationDTO checkLitigation);

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    public int deleteById(int pid);

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    public int deleteByIds(List<Integer> pids);

    /** 差诉讼历史列表(分页查询)
     *
     * @author:liangyanjun
     * @time:2017年1月13日上午9:58:53 */
    public List<CheckLitigationDTO> queryCheckLitigationHisIndex(CheckLitigationDTO query);

    /** 差诉讼历史列表总数
     *
     * @author:liangyanjun
     * @time:2017年1月13日上午9:58:53 */
    public int getCheckLitigationHisIndexTotal(CheckLitigationDTO query);
    
    /** 差诉讼历史列表(分页查询，根据查诉讼时间)
    *
    * @author:liangyanjun
    * @time:2017年1月13日上午9:58:53 */
   public List<CheckLitigationDTO> queryCheckLitigationHisIndex1(CheckLitigationDTO query);
}
