package com.xlkfinance.bms.server.repayment.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.repayment.ProjectLevel;
/**
 * 
  * @ClassName: ProjectLevelMapper
  * @Description: 项目级别分类
  * @author: Rain.Lv
  * @date: 2015年6月2日 上午11:35:14
 */
@MapperScan("projectLevelMapper")
public interface ProjectLevelMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 增加项目级别分类
	 * @param projectLevel
	 * @return
	 * @author: Rain.Lv
	 * @date: 2015年6月1日 下午8:16:29
	 */
	public int addProjectLevel(ProjectLevel projectLevel);

	/**
	 * 
	 * @Description: 修改项目级别分类
	 * @param projectLevel
	 * @return
	 * @author: Rain.Lv
	 * @date: 2015年6月1日 下午8:16:33
	 */
	public int editProjectLevel(ProjectLevel projectLevel);

	/**
	 * 
	 * @Description: 获取五级分类信息
	 * @param projectLevel
	 * @return
	 * @author: Rain.Lv
	 * @date: 2015年6月2日 上午11:20:49
	 */
	public List<ProjectLevel> getProjectLevelInfo(ProjectLevel projectLevel);

	/**
	 * 
	 * @Description: 获取记录数
	 * @param projectLevel
	 * @return
	 * @author: Rain.Lv
	 * @date: 2015年6月2日 上午11:34:58
	 */
	public int getProjectLevelCount(ProjectLevel projectLevel);
	
	/**
	 * 
	  * @Description: 删除自定义级别
	  * @param pid
	  * @author: Rain.Lv
	  * @date: 2015年6月12日 下午3:09:01
	 */
	
	public void delProjectLevelInfo(int pid);
}
