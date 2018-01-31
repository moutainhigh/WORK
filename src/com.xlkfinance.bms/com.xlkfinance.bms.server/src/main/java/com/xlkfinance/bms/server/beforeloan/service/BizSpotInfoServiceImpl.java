package com.xlkfinance.bms.server.beforeloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfo;
import com.xlkfinance.bms.rpc.beforeloan.BizSpotInfoService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.BizSpotInfoMapper;

/**
 *  @author： qqw <br>
 *  @time：2017-12-16 14:54:16 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 下户调查信息<br>
 */
@Service("bizSpotInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizSpotInfoService")
public class BizSpotInfoServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(BizSpotInfoServiceImpl.class);

    @Resource(name = "bizSpotInfoMapper")
    private BizSpotInfoMapper bizSpotInfoMapper;
    
    @Resource(name = "bizProjectEstateMapper")
    private BizProjectEstateMapper bizProjectEstateMapper;
    
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    @Override
    public List<BizSpotInfo> getAll(BizSpotInfo bizSpotInfo) throws ThriftServiceException, TException {
       return bizSpotInfoMapper.getAll(bizSpotInfo);
    }

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    @Override
    public BizSpotInfo getById(int pid) throws ThriftServiceException, TException {
       BizSpotInfo bizSpotInfo = bizSpotInfoMapper.getById(pid);
       if (bizSpotInfo==null) {
          return new BizSpotInfo();
       }
       return bizSpotInfo;
    }

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    @Override
    public int insert(BizSpotInfo bizSpotInfo) throws ThriftServiceException, TException {
       return bizSpotInfoMapper.insert(bizSpotInfo);
    }

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-16 14:54:16
     */
    @Override
    public int update(BizSpotInfo bizSpotInfo) throws ThriftServiceException, TException {
       return bizSpotInfoMapper.update(bizSpotInfo);
    }

	@Override
	public int save(List<BizSpotInfo> spotInfos) throws TException {
		int result = 0;
		for(BizSpotInfo spotInfo : spotInfos) {
			spotInfo.setActualSpotTime(DateUtils.getDateStartStr(spotInfo.getActualSpotTime()));
			spotInfo.setLeaseTimeStart(DateUtils.getDateStartStr(spotInfo.getLeaseTimeStart()));
			spotInfo.setLeaseTimeEnd(DateUtils.getDateStartStr(spotInfo.getLeaseTimeEnd()));
			spotInfo.setShouldSpotTime(DateUtils.getDateStartStr(spotInfo.getShouldSpotTime()));
			if(spotInfo.getPid()>0) {
				bizSpotInfoMapper.update(spotInfo);
			}else {
				bizSpotInfoMapper.insert(spotInfo);
			}
			result ++;
		}
		return result;
	}

	/**
	 * 尽职报告是否已保存
	 * 一份抵押物一份尽职报告
	 * @return 1 已尽职调查 <=0 未尽职调查
	 */
	@Override
	public int isSave(int projectId) throws TException {
		int result = 1;
		List<BizProjectEstate> estateList = bizProjectEstateMapper.getAllCascadeSpotInfoByProjectId(projectId);
		for(BizProjectEstate item :estateList) {
			if(item.getBizSpotInfo() == null || StringUtil.isBlank(item.getBizSpotInfo().getActualSpotTime())) {
				result --;
			}
		}
		return result;
	}
	
}
