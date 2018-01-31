package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFile;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-10-14 14:59:02 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档文件关联<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@MapperScan("checkDocumentFileMapper")
public interface CheckDocumentFileMapper<T, PK> extends BaseMapper<T, PK> {
    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    public List<CheckDocumentFile> getAll(CheckDocumentFile checkDocumentFile);

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    public CheckDocumentFile getById(int pid);

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    public int insert(CheckDocumentFile checkDocumentFile);

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    public int update(CheckDocumentFile checkDocumentFile);

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    public int deleteById(int pid);

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    public int deleteByIds(List<Integer> pids);
    /** 分页查询
     * 
     * @author:liangyanjun
     * @time:2016年10月14日下午3:02:11 */
    public List<CheckDocumentFile> queryCheckDocumentFile(CheckDocumentFile query);
    /** 
     * 获取查询条数
     * @author:liangyanjun
     * @time:2016年10月14日下午3:02:12 */
    public int getCheckDocumentFileTotal(CheckDocumentFile query);
}
