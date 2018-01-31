package com.xlkfinance.bms.server.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.project.BizProjectContacts;

/**
 *  @author： dongbiao <br>
 *  @time：2018-01-03 14:58:47 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 项目联系人信息<br>
 */
@MapperScan("bizProjectContactsMapper")
public interface BizProjectContactsMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     *根据条件查询所有
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    public List<BizProjectContacts> getAll(BizProjectContacts bizProjectContacts);

    /**
     *根据id查询
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    public BizProjectContacts getById(int pid);

    /**
     *插入一条数据
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    public int insert(BizProjectContacts bizProjectContacts);

    /**
     *根据id更新数据
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
    public int update(BizProjectContacts bizProjectContacts);

    /**
     *根据项目id查找项目联系人信息
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
	public List<BizProjectContacts> getContactsByProjectId(int projectId);

	 /**
     *批量删除联系人信息
     *@author:dongbiao
     *@time:2018-01-03 14:58:47
     */
	public int deleteByIds(List<Integer> contactsId);
	
	

}
