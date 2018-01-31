package com.xlkfinance.bms.server.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.CapitalOrgInfo;
import com.xlkfinance.bms.rpc.system.CapitalOrgInfoService.Iface;
import com.xlkfinance.bms.rpc.system.OrgCapitalCost;
import com.xlkfinance.bms.server.system.mapper.CapitalOrgInfoMapper;
import com.xlkfinance.bms.server.system.mapper.OrgCapitalCostMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-09-14 09:56:26 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 资产机构管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("capitalOrgInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.CapitalOrgInfoService")
public class CapitalOrgInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(CapitalOrgInfoServiceImpl.class);

   @Resource(name = "capitalOrgInfoMapper")
   private CapitalOrgInfoMapper capitalOrgInfoMapper;

   @Resource(name = "orgCapitalCostMapper")
   private OrgCapitalCostMapper orgCapitalCostMapper;
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   @Override
   public List<CapitalOrgInfo> getAll(CapitalOrgInfo capitalOrgInfo) throws ThriftServiceException, TException {
      return capitalOrgInfoMapper.getAll(capitalOrgInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   @Override
   public CapitalOrgInfo getById(int pid) throws ThriftServiceException, TException {
      CapitalOrgInfo capitalOrgInfo = capitalOrgInfoMapper.getById(pid);
      if (capitalOrgInfo==null) {
         return new CapitalOrgInfo();
      }
      return capitalOrgInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   @Override
   public int insert(CapitalOrgInfo capitalOrgInfo) throws ThriftServiceException, TException {
	   try {
		   capitalOrgInfoMapper.insert(capitalOrgInfo);
		} catch (Exception e) {
			logger.error("添加资金机构信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	return capitalOrgInfo.getPid();
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   @Override
   public int update(CapitalOrgInfo capitalOrgInfo) throws ThriftServiceException, TException {
      return capitalOrgInfoMapper.update(capitalOrgInfo);
   }

	@Override
	public List<CapitalOrgInfo> getCapitalByPage(CapitalOrgInfo capitalOrgInfo)
			throws TException {
		return capitalOrgInfoMapper.getCapitalByPage(capitalOrgInfo);
	}
	
	@Override
	public int getCapitalCount(CapitalOrgInfo capitalOrgInfo) throws TException {
		return capitalOrgInfoMapper.getCapitalCount(capitalOrgInfo);
	}

	@Override
	public double getYearRate(int pid) throws TException {
		double yearRate = 0.00;
		try {
			OrgCapitalCost query = new OrgCapitalCost();
			query.setOrgId(pid);
			List<OrgCapitalCost> list = orgCapitalCostMapper.getAll(query);
			if(list == null || list.size() == 0){
				yearRate = 0.00;
			}else if(list.size() == 1){
				yearRate = list.get(0).getYearRate();
			}else{
				List<OrgCapitalCost> result = orgCapitalCostMapper.getYearRate(pid);
				if(result != null && result.size()>0){
					yearRate = result.get(0).getYearRate();
				}
			}
			yearRate = yearRate/100;
		} catch (Exception e) {
			logger.error("获取年利率出错:" + ExceptionUtil.getExceptionMessage(e));
		}
		return yearRate;
	}

	/**
	 * 修改放款总金额
	 */
	@Override
	public int updateLoanMoney(int pid,double loanMoney) throws TException {
		int rows = 0;
		try {
			CapitalOrgInfo capitalOrgInfo = capitalOrgInfoMapper.getById(pid);
			if(capitalOrgInfo != null){
				capitalOrgInfo.setLoanMoneyTotal(loanMoney+capitalOrgInfo.getLoanMoneyTotal());
			}
			rows = capitalOrgInfoMapper.updateLoanMoney(capitalOrgInfo);
		} catch (Exception e) {
			logger.error("修改放款金额出错:" + ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	/**
	 *@author:liangyanjun
	 *@time:2016年10月19日上午10:38:04
	 *
	 */
    @Override
    public CapitalOrgInfo getByOrgName(String orgName) throws TException {
        CapitalOrgInfo capitalOrgInfo = capitalOrgInfoMapper.getByOrgName(orgName);
        if (capitalOrgInfo==null) {
            return new CapitalOrgInfo();
        }
        return capitalOrgInfo;
    }

	@Override
	public List<CapitalOrgInfo> getAllByStatus() throws TException {
		return capitalOrgInfoMapper.getAllByStatus();
	}


}
