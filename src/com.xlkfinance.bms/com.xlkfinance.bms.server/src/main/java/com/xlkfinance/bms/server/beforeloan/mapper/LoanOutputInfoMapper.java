package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfo;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImpl;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImplDTO;
import com.xlkfinance.bms.rpc.beforeloan.Project;
/**
 * 
*    
* 
* 类描述：   
* 创建人：gaoWen   
* 创建时间：2015年2月8日 上午11:54:37   

* 修改时间：2015年2月8日 上午11:54:37   
* 修改备注：   
* @version    
*
 */
@MapperScan("loanOutputInfoMapper")
public interface LoanOutputInfoMapper <T, PK> extends BaseMapper<T, PK> {
	/**
	 * @Description: 贷款放款
	 * @author: gaoWen
	 * @param project
	 * @return
	 */
	List<LoanOutputInfo> getLoadOutputList(int projectId);
	/**
	 * @Description: 贷款放款
	 * @author: gaoWen
	 * @param project
	 * @return
	 */
	List<LoanOutputInfoImpl> getLoadOutputListImpl(Project project);
	/**
	 * 
	* 类描述：   插入放款信息
	* 
	* 创建人：gaoWen   
	* 创建时间：2015年2月9日 上午10:40:27   
	*
	* 修改时间：2015年2月9日 上午10:40:27   
	* 修改备注：   
	* @version 
	 @param   
	* @return
	 */
	int insertLoadOutputinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO);
	/**
	 * 
	* 类描述：   插入放款信息
	* 
	* 创建人：gaoWen   
	* 创建时间：2015年2月9日 上午10:40:27   
	*
	* 修改时间：2015年2月9日 上午10:40:27   
	* 修改备注：   
	* @version 
	 @param   
	* @return
	 */
	int insertLoadFTinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO);

	/**
	 * @Description: 贷款放款
	 * @author: gaoWen
	 * @param project
	 * @return
	 */
	List<LoanOutputInfo> getLoadOutputinfoList(Project project);
	/**
	 * @Description: 贷款放款
	 * @author: gaoWen
	 * @param project
	 * @return
	 */
	List<LoanOutputInfoImpl> getLoadOutputinfo(Project project);
	 
	
	
	/**
	 * 
	* 类描述：  修改放款信息
	 */
	int updateLoadOutputinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO);
	int updateLoadFTinfo(LoanOutputInfoImplDTO loanOutputInfoImplDTO);
	
	/**
	 * 
	* 类描述： 删除放款信息
	 */
	int deleteLoadOutputinfo(int pId);
	int deleteLoadFTinfo(int pId);

}
