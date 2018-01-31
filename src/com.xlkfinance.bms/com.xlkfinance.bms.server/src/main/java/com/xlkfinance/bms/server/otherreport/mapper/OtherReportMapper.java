package com.xlkfinance.bms.server.otherreport.mapper;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.otherReport.ForeclosureCapDetailsReport;
import com.xlkfinance.bms.rpc.otherReport.ForeclosureOrgDetailsReport;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： Jony <br>
 * ★☆ @time：2016年3月1日上午11:14:20 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("otherReportMapper")
public interface OtherReportMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param ForeclosureCapDetailsReport
    *@return
    */
   List<ForeclosureCapDetailsReport> queryForeclosureCapNewDetails(ForeclosureCapDetailsReport query);

   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param ForeclosureCapDetailsReport
    *@return
    */
   int queryForeclosureCapNewDetailsTotal(ForeclosureCapDetailsReport query);
   
   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param ForeclosureCapDetailsReport
    *@return
    */
   List<ForeclosureCapDetailsReport> queryForeclosureCapSquareDetails(ForeclosureCapDetailsReport query);

	/**
	*@author:jony
	*@time:2016年11月12日上午11:04:31
	*@param ForeclosureCapDetailsReport
	*@return
	*/
	int queryForeclosureCapSquareDetailsTotal(ForeclosureCapDetailsReport query);
	
   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param ForeclosureCapDetailsReport
    *@return
    */
   List<ForeclosureCapDetailsReport> queryForeclosureCapIngDetails(ForeclosureCapDetailsReport query);

	/**
	*@author:jony
	*@time:2016年11月12日上午11:04:31
	*@param ForeclosureCapDetailsReport
	*@return
	*/
	int queryForeclosureCapIngDetailsTotal(ForeclosureCapDetailsReport query);

	/**
	 *@author:jony
	 *@time:2016年11月12日上午11:04:31
	 *@param ForeclosureOrgDetailsReport
	 *@return
	 */
	List<ForeclosureCapDetailsReport> queryForeclosureOrgNewDetails(ForeclosureOrgDetailsReport query);

	/**
	*@author:jony
	*@time:2016年11月12日上午11:04:31
	*@param ForeclosureOrgDetailsReport
	*@return
	*/
	int queryForeclosureOrgNewDetailsTotal(ForeclosureOrgDetailsReport query);
	
	/**
	 *@author:jony
	 *@time:2016年11月12日上午11:04:31
	 *@param ForeclosureOrgDetailsReport
	 *@return
	 */
	List<ForeclosureCapDetailsReport> queryForeclosureOrgSquareDetails(ForeclosureOrgDetailsReport query);

	/**
	*@author:jony
	*@time:2016年11月12日上午11:04:31
	*@param ForeclosureOrgDetailsReport
	*@return
	*/
	int queryForeclosureOrgSquareDetailsTotal(ForeclosureOrgDetailsReport query);
	
	/**
	 *@author:jony
	 *@time:2016年11月12日上午11:04:31
	 *@param ForeclosureOrgDetailsReport
	 *@return
	 */
	List<ForeclosureCapDetailsReport> queryForeclosureOrgIngDetails(ForeclosureOrgDetailsReport query);

	/**
	*@author:jony
	*@time:2016年11月12日上午11:04:31
	*@param ForeclosureOrgDetailsReport
	*@return
	*/
	int queryForeclosureOrgIngDetailsTotal(ForeclosureOrgDetailsReport query);

}
