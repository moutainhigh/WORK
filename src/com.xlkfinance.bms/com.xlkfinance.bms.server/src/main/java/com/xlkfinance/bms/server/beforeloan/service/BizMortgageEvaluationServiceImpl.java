package com.xlkfinance.bms.server.beforeloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.beforeloan.BizMortgageEvaluation;
import com.xlkfinance.bms.rpc.beforeloan.BizMortgageEvaluationService.Iface;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.BizMortgageEvaluationMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizSpotInfoMapper;

/**
 *  @author： qqw <br>
 *  @time：2017-12-16 09:25:32 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 抵押物评估信息<br>
 */
@Service("bizMortgageEvaluationServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizMortgageEvaluationService")
public class BizMortgageEvaluationServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizMortgageEvaluationServiceImpl.class);

    @Resource(name = "bizMortgageEvaluationMapper")
    private BizMortgageEvaluationMapper bizMortgageEvaluationMapper;

    @Resource(name = "bizProjectEstateMapper")
    private BizProjectEstateMapper bizProjectEstateMapper;
    
    @Resource(name = "bizSpotInfoMapper")
    private BizSpotInfoMapper bizSpotInfoMapper; 
    
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    @Override
    public List<BizMortgageEvaluation> getAll(BizMortgageEvaluation bizMortgageEvaluation) throws ThriftServiceException, TException {
       return bizMortgageEvaluationMapper.getAll(bizMortgageEvaluation);
    }

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    @Override
    public BizMortgageEvaluation getById(int pid) throws ThriftServiceException, TException {
       BizMortgageEvaluation bizMortgageEvaluation = bizMortgageEvaluationMapper.getById(pid);
       if (bizMortgageEvaluation==null) {
          return new BizMortgageEvaluation();
       }
       return bizMortgageEvaluation;
    }

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    @Override
    public int insert(BizMortgageEvaluation bizMortgageEvaluation) throws ThriftServiceException, TException {
       return bizMortgageEvaluationMapper.insert(bizMortgageEvaluation);
    }

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-16 09:25:32
     */
    @Override
    public int update(BizMortgageEvaluation bizMortgageEvaluation) throws ThriftServiceException, TException {
       return bizMortgageEvaluationMapper.update(bizMortgageEvaluation);
    }
    
    /**
     * 进行评估操作
     */
	@Override
	@Transactional
	public int eval(BizMortgageEvaluation bizMortgageEvaluation) throws TException {
		int result = 0;
		try {
			List<BizMortgageEvaluation>  evaluations = bizMortgageEvaluation.getEvaluations();
			
			int projectId = bizMortgageEvaluation.getProjectId();
			int estateId = bizMortgageEvaluation.getEstate();
			
			double sum = 0;
			BizMortgageEvaluation param = new BizMortgageEvaluation();
			param.setEstate(bizMortgageEvaluation.getEstate());
			List<BizMortgageEvaluation> evaluaList = bizMortgageEvaluationMapper.getAll(param);
			boolean isExitst = evaluaList != null && evaluaList.size() > 0;
			for(BizMortgageEvaluation item : evaluations) {
				//评估价 += 比列(不含百分比) * 评估价  / 100
				sum += item.getEvaluationPrice() * item.getEvaluationProportion() / 100;
				item.setEstate(estateId);
				if(isExitst) {
					item.setUpdateId(item.getUpdateId());
					bizMortgageEvaluationMapper.update(item);
				}else {
					item.setCreaterId(bizMortgageEvaluation.getCreaterId());
					bizMortgageEvaluationMapper.insert(item);
				}
			}
			//更新物业表评估价
			BizProjectEstate estate = new BizProjectEstate();
			estate.setHouseId(bizMortgageEvaluation.getEstate());
			estate.setEvaluationPrice(sum);
			bizProjectEstateMapper.update(estate);
			
			//下户调查 - 预计下户时间
			BizSpotInfo spotInfo = new BizSpotInfo();
			spotInfo.setProjectId(bizMortgageEvaluation.getProjectId());
			spotInfo.setEastateId(bizMortgageEvaluation.getEstate());
			
			spotInfo.setShouldSpotTime(DateUtils.getDateStartStr(bizMortgageEvaluation.getShouldSpotTime()));
			
			BizSpotInfo spotParam = new BizSpotInfo();
			spotParam.setProjectId(projectId);
			spotParam.setEastateId(estateId);
			List<BizSpotInfo> sportInfoList = bizSpotInfoMapper.getAll(spotParam);
			if(sportInfoList !=null && sportInfoList.size() >0) {
				BizSpotInfo info = new BizSpotInfo();
				info.setPid(sportInfoList.get(0).getPid());
				info.setShouldSpotTime(spotInfo.getShouldSpotTime());
				bizSpotInfoMapper.update(info);
			}else {
				bizSpotInfoMapper.insert(spotInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("评估失败：", e);
			result = -1;
		} 
		return result;
	}


}
