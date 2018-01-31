package com.xlkfinance.bms.server.inloan.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizInterviewFile;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfo;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoIndexDTO;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfoService.Iface;
import com.xlkfinance.bms.rpc.project.CusCardInfo;
import com.xlkfinance.bms.rpc.project.CusHisCardInfo;
import com.xlkfinance.bms.server.inloan.mapper.BizInterviewFileMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizInterviewInfoMapper;
import com.xlkfinance.bms.server.project.mapper.CusCardInfoMapper;
import com.xlkfinance.bms.server.project.mapper.CusHisCardInfoMapper;

/**
 *  @author： qqw <br>
 *  @time：2017-12-20 09:34:54 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 面签管理信息<br>
 */
@Service("bizInterviewInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.BizInterviewInfoService")
@SuppressWarnings("rawtypes")
public class BizInterviewInfoServiceImpl implements Iface {
    
    @Resource(name = "bizInterviewInfoMapper")
    private BizInterviewInfoMapper bizInterviewInfoMapper;
    
    @Resource(name = "bizInterviewFileMapper")
    private BizInterviewFileMapper bizInterviewFileMapper;
    
    @Resource(name = "cusCardInfoMapper")
	private CusCardInfoMapper cusCardInfoMapper;
    
	@Resource(name = "cusHisCardInfoMapper")
    private CusHisCardInfoMapper cusHisCardInfoMapper;
    

    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    
    /**
     *根据条件查询所有
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<BizInterviewInfo> getAll(BizInterviewInfo bizInterviewInfo) throws ThriftServiceException, TException {
       return bizInterviewInfoMapper.getAll(bizInterviewInfo);
    }

    /**
     *根据id查询
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    @Override
    public BizInterviewInfo getById(int pid) throws ThriftServiceException, TException {
       BizInterviewInfo bizInterviewInfo = bizInterviewInfoMapper.getById(pid);
       if (bizInterviewInfo==null) {
          return new BizInterviewInfo();
       }
       return bizInterviewInfo;
    }

    /**
     *插入一条数据
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    @Override
    public int insert(BizInterviewInfo bizInterviewInfo) throws ThriftServiceException, TException {
       return bizInterviewInfoMapper.insert(bizInterviewInfo);
    }

    /**
     *根据id更新数据
     *@author:qqw
     *@time:2017-12-20 09:34:54
     */
    @Override
    public int update(BizInterviewInfo bizInterviewInfo) throws ThriftServiceException, TException {
       return bizInterviewInfoMapper.update(bizInterviewInfo);
    }

    /**
     * 根据projectId获取面签信息
     */
	@Override
	public BizInterviewInfo getByProjectId(int projectId) throws TException {
		BizInterviewInfo info = bizInterviewInfoMapper.getByProjectId(projectId);
		if (info==null) {
			info = new BizInterviewInfo();
	    }else {
	    	info.setInterviewFile(bizInterviewFileMapper.getByInteviewId(info.getPid()));
	    }
		return info;
	}

	/**
	 * 面签管理首页List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BizInterviewInfoIndexDTO> getProjectByPage(BizInterviewInfoIndexDTO info) throws TException {
		List<BizInterviewInfoIndexDTO> list = null;
		
		list = bizInterviewInfoMapper.getProjectByPage(info);
		
		if(list == null) {
			list = new ArrayList<BizInterviewInfoIndexDTO>();
		}
		return list;
	}

	/**
	 * 面签管理首页List
	 */
	@Override
	public int getProjectCount(BizInterviewInfoIndexDTO info) throws TException {
		
		return bizInterviewInfoMapper.getProjectCount(info);
	}

	/**
	 * 面签管理 - 面签
	 */
	@Override
	public int asign(BizInterviewInfo bizInterviewInfo) throws TException {
		BizInterviewInfo dbInfo = bizInterviewInfoMapper.getById(bizInterviewInfo.getPid());
		if(dbInfo != null) {
			bizInterviewInfoMapper.update(bizInterviewInfo);
			return 1;
		}else {
			return 0;
		}
		
	}
	/**
	 * 领他证
	 */
	@Override
	@Transactional
	public int his(BizInterviewInfo bizInterviewInfo) throws TException {
		int projectId = bizInterviewInfo.getProjectId();
		BizInterviewInfo dbInfo = bizInterviewInfoMapper.getById(bizInterviewInfo.getPid());
		//只有 面签/公证/抵押 完成才能领他证
		if(dbInfo != null && (dbInfo.getMortgageStatus() == Constants.COMM_YES && dbInfo.getInterviewStatus() == Constants.COMM_YES && dbInfo.getNotarizationStatus() ==Constants.COMM_YES ) ) {
			
			bizInterviewInfo.setHisWarrantTime(bizInterviewInfo.getHisWarrantTime());
			bizInterviewInfoMapper.update(bizInterviewInfo);
			BizInterviewFile file = bizInterviewInfo.getInterviewFile();
			file.setInterviewId(bizInterviewInfo.getPid());
			file.setStatus(Constants.COMM_YES);
			//当前项目上传节点 ProjectId
			if(file.getPid() >0 ) {
				bizInterviewFileMapper.update(file);
			}else {
				bizInterviewFileMapper.insert(file);
			}
			bizInterviewInfo.setProjectId(projectId);
			addBizDynamic(4, bizInterviewInfo);
			return 1;
		}else {
			return 0;
		}
	}

	/**
	 * 面签
	 */
	@Override
	@Transactional
	public int interivew(BizInterviewInfoIndexDTO bizInterviewInfo) throws TException {
		
		BizInterviewInfo info = new BizInterviewInfo();
		info.setPid(bizInterviewInfo.getPid());
		info.setProjectId(bizInterviewInfo.getProjectId());
		//面签时间
		
		info.setInterviewTime(DateUtils.getDateStartStr(bizInterviewInfo.getInterviewTime()));
		//面签地址
		info.setInterviewPlace(bizInterviewInfo.getInterviewPlace());
		//面签状态
		info.setInterviewStatus(1);
		
		//更新银行卡信息
		CusCardInfo cusCardInfo = bizInterviewInfo.getCusCardInfo();
		CusCardInfo saveCard = new CusCardInfo();
		saveCard.setPid(cusCardInfo.getPid());
		saveCard.setReceBankCardName(cusCardInfo.getReceBankCardName());
		saveCard.setReceBankCardCode(cusCardInfo.getReceBankCardCode());
		saveCard.setReceBankName(cusCardInfo.getReceBankName());
		saveCard.setRepaymentBankCardName(cusCardInfo.getRepaymentBankCardName());
		saveCard.setRepaymentBankCardCode(cusCardInfo.getRepaymentBankCardCode());
		saveCard.setRepaymentBankName(cusCardInfo.getRepaymentBankName());
		
		cusCardInfoMapper.update(saveCard);
		//.
		CusCardInfo temp = cusCardInfoMapper.getById(saveCard.getPid());
		//同步历史记录
		CusHisCardInfo cusHisCardInfo = new CusHisCardInfo();
		cusHisCardInfo.setProjectId(temp.getProjectId());
		cusHisCardInfo.setCreaterId(temp.getCreaterId());
		cusHisCardInfo.setCreateNode(Constants.MORTGAGE_LOAN_TO_INTERVIEW);
		cusHisCardInfo.setCreaterId(bizInterviewInfo.getUpdateId());
		cusHisCardInfoMapper.insert(cusHisCardInfo);
		
		//添加业务动态
		addBizDynamic(1,info);
		
		return bizInterviewInfoMapper.update(info);
	}
	
	
	/**
	 * 增加业务动态
	 * @param type 1:面签：2：公证 3：抵押 4：领他证
	 * @param bizDynamic
	 * @throws TException 
	 */
	private void addBizDynamic(int type,BizInterviewInfo bizInterviewInfo) throws TException {
		
		BizDynamic bizDynamic = new BizDynamic();
		bizDynamic.setProjectId(bizInterviewInfo.getProjectId());
		bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
		bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
		bizDynamic.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW);
		switch (type) {
		case 1:
			bizDynamic.setDynamicName(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_MAP.get(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_1));
			bizDynamic.setRemark("面签");
			bizDynamic.setHandleAuthorId(bizInterviewInfo.getInterviewId());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_1);
			break;
		case 2:
			bizDynamic.setDynamicName(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_MAP.get(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_2));
			bizDynamic.setRemark("公证");
			bizDynamic.setHandleAuthorId(bizInterviewInfo.getNotarizationId());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_2);
			break;
		case 3:
			bizDynamic.setDynamicName(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_MAP.get(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_3));
			bizDynamic.setRemark("抵押");
			bizDynamic.setHandleAuthorId(bizInterviewInfo.getMortgageUser());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_3);
			break;
		case 4:
			bizDynamic.setDynamicName(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_MAP.get(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_4));
			bizDynamic.setRemark("领他证");
			bizDynamic.setHandleAuthorId(bizInterviewInfo.getHisWarrantUser());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.MODUEL_NUMBER_INTERVIEW_4);
			break;
		default:
			break;
		}
		bizDynamicService.addBizDynamic(bizDynamic);
	}

	/**
	 * 抵押
	 */
	@Override
	public int mortgage(BizInterviewInfo bizInterviewInfo) throws TException {
		BizInterviewInfo dbInfo = bizInterviewInfoMapper.getById(bizInterviewInfo.getPid());
		if(dbInfo != null && dbInfo.getMortgageStatus() == Constants.COMM_YES ) {
			return 0;
		}else {
			bizInterviewInfoMapper.update(bizInterviewInfo);
			addBizDynamic(3, bizInterviewInfo);
			return 1;
		}
	}
	
	/**
	 * 公证
	 */
	@Override
	public int notarization(BizInterviewInfo bizInterviewInfo) throws TException {
		BizInterviewInfo dbInfo = bizInterviewInfoMapper.getById(bizInterviewInfo.getPid());
		if(dbInfo != null && dbInfo.getNotarizationStatus() == Constants.COMM_YES ) {
			return 0;
		}else {
			bizInterviewInfoMapper.update(bizInterviewInfo);
			addBizDynamic(2, bizInterviewInfo);
			return 1;
		}
	}

	/**
	 * 获取银行卡信息
	 */
	@Override
	public CusCardInfo getCardInfoByProjectId(int projectId) throws TException {
		return cusCardInfoMapper.getCardInfoByProjectId(projectId);
	}


}
