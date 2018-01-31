package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfo;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoIndexDTO;

/**
 *  @author： qqw <br>
 *  @time：2017-12-20 09:34:54 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 面签管理信息<br>
 */
@MapperScan("bizInterviewInfoMapper")
public interface BizInterviewInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    public List<BizInterviewInfo> getAll(BizInterviewInfo bizInterviewInfo);

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    public BizInterviewInfo getById(int pid);

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    public int insert(BizInterviewInfo bizInterviewInfo);

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    public int update(BizInterviewInfo bizInterviewInfo);
    
    /**
     * 根据项目id获取面签信息
     * @param projectId
     * @return
     */
	public BizInterviewInfo getByProjectId(int projectId);
	
	/**
	 * 面签首页
	 * @param info
	 * @return
	 */
	public List<BizInterviewInfoIndexDTO> getProjectByPage(BizInterviewInfoIndexDTO info);
	
	/**
	 * 面签首页
	 * @param info
	 * @return
	 */
	public int getProjectCount(BizInterviewInfoIndexDTO info);

}
