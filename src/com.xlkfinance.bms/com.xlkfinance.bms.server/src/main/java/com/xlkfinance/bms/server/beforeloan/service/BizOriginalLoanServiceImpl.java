package com.xlkfinance.bms.server.beforeloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoanService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.BizOriginalLoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;

/**
 *  @author： baogang <br>
 *  @time：2017-02-06 11:18:33 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 原贷款信息<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizOriginalLoanServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoanService")
public class BizOriginalLoanServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizOriginalLoanServiceImpl.class);

    @Resource(name = "bizOriginalLoanMapper")
    private BizOriginalLoanMapper bizOriginalLoanMapper;

	@Resource(name="projectForeclosureMapper")
	private ProjectForeclosureMapper projectForeclosureMapper;
    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    @Override
    public List<BizOriginalLoan> getAll(BizOriginalLoan bizOriginalLoan) throws ThriftServiceException, TException {
       return bizOriginalLoanMapper.getAll(bizOriginalLoan);
    }

    /**
     *根据id查询
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    @Override
    public BizOriginalLoan getById(int pid) throws ThriftServiceException, TException {
       BizOriginalLoan bizOriginalLoan = bizOriginalLoanMapper.getById(pid);
       if (bizOriginalLoan==null) {
          return new BizOriginalLoan();
       }
       return bizOriginalLoan;
    }

    /**
     *插入一条数据
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    @Override
    public int insert(BizOriginalLoan bizOriginalLoan) throws ThriftServiceException, TException {
    	int result = 0;
	    try {
		    result = bizOriginalLoanMapper.insert(bizOriginalLoan);
		    if (result >= 1) {
			    result = bizOriginalLoan.getOriginalLoanId();
			    //获取关联项目ID，修改该项目的原贷款总借款金额以及总欠款金额
			    int projectId = bizOriginalLoan.getProjectId();
				if(projectId >0){
					updateProjectByOriginalLoan(projectId);
			    }
			 } else {
				 // 抛出异常处理
				 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			 }
		 } catch (Exception e) {
			 logger.error("新增原贷款信息:" + ExceptionUtil.getExceptionMessage(e));
			 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		 }
	    return result;
    }

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2017-02-06 11:18:33
     */
    @Override
    public int update(BizOriginalLoan bizOriginalLoan) throws ThriftServiceException, TException {
    	int rows  = bizOriginalLoanMapper.update(bizOriginalLoan);
    	if(rows >0){
    		//获取关联项目ID，修改该项目的原贷款总借款金额以及总欠款金额
		    int projectId = bizOriginalLoan.getProjectId();
			if(projectId >0){
				updateProjectByOriginalLoan(projectId);
		    }
    	}
    	return rows;
    }

    /**
     * 批量删除原贷款信息
     */
	@Override
	public int delOriginalLoan(List<Integer> originalLoanIds) throws TException {
		int rows = bizOriginalLoanMapper.delOriginalLoan(originalLoanIds);
		if(rows >0){
			BizOriginalLoan query = new BizOriginalLoan();
			query.setOriginalLoanIds(originalLoanIds);
			List<BizOriginalLoan> list = bizOriginalLoanMapper.getAllByCondition(query);
			if(list!=null && list.size()>0){
				int projectId = list.get(0).getProjectId();
				if(projectId >0){
					updateProjectByOriginalLoan(projectId);
				}
			}
		}
		return rows;
	}

	/**
	 * 原贷款银行信息变更后修改项目关联的原贷款信息
	 */
	@Override
	public int updateProjectByOriginalLoan(int projectId) throws TException {
		BizOriginalLoan query = new BizOriginalLoan();
		query.setProjectId(projectId);
		query.setStatus(1);
		List<BizOriginalLoan> originalLoanList = bizOriginalLoanMapper.getAllByCondition(query);
		String oldLoanBank = "";
		double oldLoanMoney = 0.0;
		double oldOwedAmount = 0.0;
		if(originalLoanList != null && originalLoanList.size()>0){
			for(BizOriginalLoan bizOriginalLoan : originalLoanList){
				oldLoanBank += bizOriginalLoan.getOldLoanBank()+",";
				oldLoanMoney += bizOriginalLoan.getOldLoanMoney();
				oldOwedAmount += bizOriginalLoan.getOldOwedAmount();
			}
			oldLoanBank = oldLoanBank.substring(0, oldLoanBank.length()-1);
		}
		ProjectForeclosure foreclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
		foreclosure.setOldLoanBank(oldLoanBank);
		foreclosure.setOldLoanMoney(oldLoanMoney);
		foreclosure.setOldOwedAmount(oldOwedAmount);
		
		projectForeclosureMapper.updateByPrimaryKey(foreclosure);
		return 0;
	}

	@Override
	public List<BizOriginalLoan> getAllByCondition(
			BizOriginalLoan bizOriginalLoan) throws TException {
		return bizOriginalLoanMapper.getAllByCondition(bizOriginalLoan);
	}
}
