package com.xlkfinance.bms.server.beforeloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectPropertyMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 *  @author： baogang <br>
 *  @time：2016-12-26 17:27:32 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 项目物业信息表<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizProjectEstateServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService")
public class BizProjectEstateServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizProjectEstateServiceImpl.class);

    @Resource(name = "bizProjectEstateMapper")
    private BizProjectEstateMapper bizProjectEstateMapper;

	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	
	@Resource(name="projectPropertyMapper")
	private ProjectPropertyMapper projectPropertyMapper;
    
    /**
     *根据条件查询所有
     *@author:baogang
     *@time:2016-12-26 17:27:32
     */
    @Override
    public List<BizProjectEstate> getAll(BizProjectEstate bizProjectEstate) throws ThriftServiceException, TException {
       return bizProjectEstateMapper.getAll(bizProjectEstate);
    }

    /**
     *根据id查询
     *@author:baogang
     *@time:2016-12-26 17:27:32
     */
    @Override
    public BizProjectEstate getById(int pid) throws ThriftServiceException, TException {
       BizProjectEstate bizProjectEstate = bizProjectEstateMapper.getById(pid);
       if (bizProjectEstate==null) {
          return new BizProjectEstate();
       }
       return bizProjectEstate;
    }

    /**
     *插入一条数据
     *@author:baogang
     *@time:2016-12-26 17:27:32
     */
    @Override
    public int insert(BizProjectEstate bizProjectEstate) throws ThriftServiceException, TException {
	    int result = 0;
	    try {
		    result = bizProjectEstateMapper.insert(bizProjectEstate);
		    if (result >= 1) {
			    result = bizProjectEstate.getHouseId();
			    //获取关联项目ID，修改该项目的总评估价以及赎楼成数等信息
			    int projectId = bizProjectEstate.getProjectId();
				if(projectId >0){
			    	updateProjectByEstate(projectId);
			    }
			 } else {
				 // 抛出异常处理
				 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			 }
		 } catch (Exception e) {
			 logger.error("新增物业信息:" + ExceptionUtil.getExceptionMessage(e));
			 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		 }
       return result;
    }

    /**
     *根据id更新数据
     *@author:baogang
     *@time:2016-12-26 17:27:32
     */
    @Override
    public int update(BizProjectEstate bizProjectEstate) throws ThriftServiceException, TException {
    	int rows = bizProjectEstateMapper.update(bizProjectEstate);
    	if(rows >0){
        	//获取关联项目ID，修改该项目的总评估价以及赎楼成数等信息
    	    int projectId = bizProjectEstate.getProjectId();
    		if(projectId >0){
    	    	updateProjectByEstate(projectId);
    	    }
    	}
    	return rows;
    }

	/**
	 * 批量删除物业信息
	 */
	@Override
	public int delProjectEstate(List<Integer> houseIds) throws TException {
		int rows = bizProjectEstateMapper.delProjectEstate(houseIds);
		//获取删除物业关联的项目信息，并变更项目信息
		if(rows>0){
			BizProjectEstate query = new BizProjectEstate();
			query.setHouseIds(houseIds);
			List<BizProjectEstate> list = bizProjectEstateMapper.getAll(query );
			if(list != null && list.size()>0){
				int projectId = list.get(0).getProjectId();
				if(projectId >0){
					updateProjectByEstate(projectId);
				}
			}
		}
		return rows;
	}

	/**
	 * 在物业信息变更时变更项目信息，防止页面添加或修改物业后未保存项目信息
	 */
	@Override
	public int updateProjectByEstate(int projectId) throws TException {
		List<BizProjectEstate> estateList = bizProjectEstateMapper.getAllByProjectId(projectId);
		String houseName = "";
		String housePropertyCard = "";
		double tranasctionMoney = 0.0;//成交价
		double evaluationPrice = 0.0;//评估价
		double area=0.0;//面积
		double costMoney = 0.0;//登记价
		double evaluationNet = 0.0;//评估净值
		if(estateList != null && estateList.size()>0){
			for(BizProjectEstate bizProjectEstate : estateList){
				 houseName += bizProjectEstate.getHouseName()+",";
				 housePropertyCard += bizProjectEstate.getHousePropertyCard()+",";
				 tranasctionMoney += bizProjectEstate.getTranasctionMoney();
				 evaluationPrice += bizProjectEstate.getEvaluationPrice();
				 area +=bizProjectEstate.getArea();
				 costMoney +=bizProjectEstate.getCostMoney();
				 evaluationNet += bizProjectEstate.getEvaluationNet();
			}
			houseName = houseName.substring(0, houseName.length()-1);
			housePropertyCard = housePropertyCard.substring(0, housePropertyCard.length()-1);
		}
		ProjectGuarantee guarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
		ProjectProperty property = projectPropertyMapper.getPropertyByProjectId(projectId);
		double foreRate = property.getForeRate();//赎楼成数
		double loanMoney = guarantee.getLoanMoney();//借款金额
		if(evaluationPrice >0){
			foreRate = (loanMoney/evaluationPrice)*100;
		}
		property.setHouseName(houseName);
		property.setHousePropertyCard(housePropertyCard);
		property.setTranasctionMoney(tranasctionMoney);
		property.setEvaluationPrice(evaluationPrice);
		property.setForeRate(foreRate);
		property.setArea(area);
		property.setCostMoney(costMoney);
		property.setEvaluationNet(evaluationNet);
		projectPropertyMapper.updateByPrimaryKey(property);
		return 0;
	}

	/**
	 * 根据projectID查询物业信息
	 */
	@Override
	public List<BizProjectEstate> getAllByProjectId(int projectId)
			throws TException {
		return bizProjectEstateMapper.getAllByProjectId(projectId);
	}

	@Override
	public int originInsert(BizProjectEstate bizProjectEstate) throws TException {
		int result = 0;
	    try {
		    result = bizProjectEstateMapper.insert(bizProjectEstate);
		    if (result >= 1) {
			    result = bizProjectEstate.getHouseId();
			 } else {
				 // 抛出异常处理
				 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			 }
		 } catch (Exception e) {
			 logger.error("新增物业信息:" + ExceptionUtil.getExceptionMessage(e));
			 throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
		 }
       return result;
	}

	@Override
	public int originUpdate(BizProjectEstate bizProjectEstate) throws TException {
    	return bizProjectEstateMapper.update(bizProjectEstate);
	}

	@Override
	public int originDelProjectEstate(List<Integer> houseIds) throws TException {
		return bizProjectEstateMapper.delProjectEstate(houseIds);
	}

	/**
	 * 查询物业级联获取下户信息
	 */
	@Override
	public List<BizProjectEstate> getAllCascadeSpotInfoByProjectId(int projectId) throws TException {
		
		return bizProjectEstateMapper.getAllCascadeSpotInfoByProjectId(projectId);
	}

	/**
	 * 是否已评估
	 * 项目一定存在抵押物
	 * @return > 1 已评估 0 未评估
	 */
	@Override
	public int isPledgeEval(int projectId) throws TException {
		int result = 1;
		BizProjectEstate estate = new BizProjectEstate();
		estate.setProjectId(projectId);
		estate.setStatus(Constants.COMM_YES);
		List<BizProjectEstate> estateList = bizProjectEstateMapper.getAll(estate);
		for(BizProjectEstate item : estateList) {
			if(item.getEvaluationPrice() <= 0) {
				result--;
			}
		}
		return result;
	}


}
