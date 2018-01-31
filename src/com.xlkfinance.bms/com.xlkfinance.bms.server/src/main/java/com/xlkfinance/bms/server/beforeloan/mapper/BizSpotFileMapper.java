package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotFile;

/**
 *  @author： qqw <br>
 *  @time：2017-12-18 18:07:14 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 下户调查信息<br>
 */
@MapperScan("bizSpotFileMapper")
public interface BizSpotFileMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    public List<BizSpotFile> getAll(BizSpotFile bizSpotFile);

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    public BizSpotFile getById(int pid);

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    public int insert(BizSpotFile bizSpotFile);

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-18 18:07:14
     */
    public int update(BizSpotFile bizSpotFile);
    
    /**
     * 获取总记录数
     * @param bizSpotFile
     * @return
     */
	public int getAllCount(BizSpotFile bizSpotFile);

	/**
	 * 根据ID删除文件
	 * @param bizSpotFile
	 * @return 
	 */
	public int deleteById(BizSpotFile bizSpotFile);

	/**
	 * 根据IDs 删除文件
	 * @param ids
	 * @return
	 */
	public int deleteByIds(List<String> ids);

}
