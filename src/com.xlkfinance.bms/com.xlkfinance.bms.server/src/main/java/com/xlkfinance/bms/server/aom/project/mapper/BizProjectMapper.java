package com.xlkfinance.bms.server.aom.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.BizProjectDTO;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-19 11:43:41 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务申请表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizProjectMapper")
public interface BizProjectMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   public List<BizProject> getAll(BizProject bizProject);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   public BizProject getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   public int insert(BizProject bizProject);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-19 11:43:41
    */
   public int update(BizProject bizProject);
   /**
    * 分页查询所有业务申请
     * @param bizProjectDTO
     * @return
     * @author: baogang
     * @date: 2016年7月19日 上午11:46:58
    */
   List<BizProjectDTO> getBizProjectByPage(BizProjectDTO bizProjectDTO);
   
   /**
    * 查询业务申请总数
     * @param bizProjectDTO
     * @return
     * @author: baogang
     * @date: 2016年7月19日 上午11:47:15
    */
   int getBizProjectCount(BizProjectDTO bizProjectDTO);
   
	/**
	 * 查询当前年份的项目编号的最后5位自然数
	  * @return
	  * @author: baogang
	  * @date: 2016年7月19日 下午5:30:28
	 */
	public String getMaxProjectNumber();

	/**
	 * 查询当前日期的项目名称最大的最后2位自然数
	  * @param acctId
	  * @return
	  * @author: baogang
	  * @date: 2016年7月19日 下午5:30:37
	 */
	public int getMaxProjectName(int orgId);
	
	/**
	 * 修改业务申请关闭状态
	  * @param bizProject
	  * @return
	  * @author: baogang
	  * @date: 2016年7月20日 上午10:16:31
	 */
	public int updateProjectClosed(BizProject bizProject);
	
	/**
	 * 修改项目驳回状态
	  * @param bizProject
	  * @return
	  * @author: baogang
	  * @date: 2016年9月1日 下午2:32:02
	 */
	public int updateProjectReject(BizProject bizProject);
	
	/**
	 * 修改订单分配状态
	  * @param bizProject
	  * @return
	  * @author: baogang
	  * @date: 2016年9月8日 下午5:29:42
	 */
	public int updateProjectAssigned(BizProject bizProject);
	/**
	 * 项目经理查看自已维护机构订单功能
	 * @param bizProjectDTO
	 * @return
	 */
	public List<BizProjectDTO> getProjectListByPage(BizProjectDTO bizProjectDTO);
	/**
	 * 根据条件查询所有机构业务列表
	 * @param bizProjectDTO
	 * @return
	 */
	public int getProjectListCount(BizProjectDTO bizProjectDTO);
}
