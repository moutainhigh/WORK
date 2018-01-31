package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizInterviewFile;

/**
 *  @author： qqw <br>
 *  @time：2017-12-20 09:36:10 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 面签管理文件<br>
 */
@MapperScan("bizInterviewFileMapper")
public interface BizInterviewFileMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    public List<BizInterviewFile> getAll(BizInterviewFile bizInterviewFile);

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    public BizInterviewFile getById(int pid);

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    public int insert(BizInterviewFile bizInterviewFile);

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-20 09:36:10
     */
    public int update(BizInterviewFile bizInterviewFile);

    /**
     * 根据面签ID获取面前影像文件
     * @param pid
     * @return
     */
	public BizInterviewFile getByInteviewId(int interviewId);

}
