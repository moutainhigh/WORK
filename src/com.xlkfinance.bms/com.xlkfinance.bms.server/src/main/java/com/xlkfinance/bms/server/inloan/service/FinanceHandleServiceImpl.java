package com.xlkfinance.bms.server.inloan.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHis;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService.Iface;
import com.xlkfinance.bms.rpc.inloan.FinanceIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionDTO;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.CollectFeeHisMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.ForeclosureRepaymentMapper;
import com.xlkfinance.bms.server.inloan.mapper.RefundFeeMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.repayment.mapper.LoanExtensionMapper;
import com.xlkfinance.bms.server.system.mapper.SysOrgInfoMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月11日下午8:54:39 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务受理  Service<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("financeHandleServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.FinanceHandleService")
public class FinanceHandleServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(FinanceHandleServiceImpl.class);

   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;

   @Resource(name = "bizHandleMapper")
   private BizHandleMapper bizHandleMapper;
   
   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;
   
   @Resource(name = "BizHandleServiceImpl")
   private BizHandleService.Iface bizHandleService;
   
   @Resource(name = "productMapper")
   private ProductMapper productMapper;
   
   @Resource(name = "sysUserMapper")
   private SysUserMapper sysUserMapper;
   
   @Resource(name = "sysOrgInfoMapper")
   private SysOrgInfoMapper sysOrgInfoMapper;
   
   @Resource(name = "projectForeclosureMapper")
   private ProjectForeclosureMapper projectForeclosureMapper;
   
   @Resource(name = "foreclosureRepaymentMapper")
   private ForeclosureRepaymentMapper foreclosureRepaymentMapper;
   
   @Resource(name="projectServiceImpl")
   private ProjectService.Iface projectServiceImpl;
   
   @Resource(name = "refundFeeMapper")
   private RefundFeeMapper refundFeeMapper;
   
   @Resource(name = "loanExtensionMapper")
   private LoanExtensionMapper loanExtensionMapper;
   @Resource(name = "collectFeeHisMapper")
   private CollectFeeHisMapper collectFeeHisMapper;
   /**
    * 查询所有财务办理
    *@author:liangyanjun
    *@time:2016年1月12日下午3:10:10
    *@param financeHandleDTO
    *@return
    */
   @Override
   public List<FinanceHandleDTO> findAllFinanceHandle(FinanceHandleDTO financeHandleDTO) throws TException {
      return financeHandleMapper.findAllFinanceHandle(financeHandleDTO);
   }

   /**
    *  查询所有财务的总数
    *@author:liangyanjun
    *@time:2016年1月12日下午3:10:13
    *@param financeHandleDTO
    *@return
    */
   @Override
   public int getFinanceHandleTotal(FinanceHandleDTO financeHandleDTO) throws TException {
      return financeHandleMapper.getFinanceHandleTotal(financeHandleDTO);
   }

   /**
    * 根据ID进行查询
    *@author:liangyanjun
    *@time:2016年1月12日下午4:11:59
    *@param pid
    *@return
    */
   @Override
   public FinanceHandleDTO getFinanceHandleById(int pid) throws TException {
      return financeHandleMapper.getFinanceHandleById(pid);
   }

   /**
    * 新增财务办理基本数据
    *@author:liangyanjun
    *@time:2016年1月12日下午10:53:08
    *@param financeHandleDTO
    *@return
    */
   @Override
   public boolean addFinanceHandle(FinanceHandleDTO financeHandleDTO) throws TException {
      financeHandleMapper.addFinanceHandle(financeHandleDTO);
      logger.info("增加财务办理数据成功："+financeHandleDTO.toString());
      List<FinanceHandleDTO> financeHandleList = financeHandleMapper.findAllFinanceHandle(financeHandleDTO);
      if (financeHandleList==null||financeHandleList.isEmpty()) {
         throw new RuntimeException("增加财务办理数据失败financeHandleList为空");
      }
      for (FinanceHandleDTO f : financeHandleList) {
         for (int i = 1; i <= Constants.REC_PRO_MAP.size(); i++) {
            ApplyFinanceHandleDTO applyFinanceHandleDTO = new ApplyFinanceHandleDTO();
            applyFinanceHandleDTO.setFinanceHandleId(f.getPid());
            applyFinanceHandleDTO.setRecPro(i);
            applyFinanceHandleDTO.setCreaterId(f.getCreaterId());
            financeHandleMapper.addApplyFinanceHandle(applyFinanceHandleDTO);
            logger.info("增加"+Constants.REC_PRO_MAP.get(new Long(i))+"成功："+applyFinanceHandleDTO.toString());
         }
      }
      return true;
   }

   /**
    * 根据id更新
    *@author:liangyanjun
    *@time:2016年1月12日下午5:53:45
    *@param financeHandleDTO
    */
   @Override
   public boolean updateFinanceHandle(FinanceHandleDTO financeHandleDTO) throws TException {
      financeHandleMapper.updateFinanceHandle(financeHandleDTO);
      return true;
   }
   /**
    * 根据条件查询申请财务受理信息（分页查询）
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public List<ApplyFinanceHandleDTO> findAllApplyFinanceHandle(ApplyFinanceHandleDTO applyFinanceHandleDTO) throws TException {
      return financeHandleMapper.findAllApplyFinanceHandle(applyFinanceHandleDTO);
   }
   /**
    * 根据条件查询申请财务受理信息条数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public int getApplyFinanceHandleTotal(ApplyFinanceHandleDTO applyFinanceHandleDTO) throws TException {
      return financeHandleMapper.getApplyFinanceHandleTotal(applyFinanceHandleDTO);
   }
   /**
    * 添加申请财务受理信息
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public boolean addApplyFinanceHandle(ApplyFinanceHandleDTO applyFinanceHandleDTO) throws TException {
      financeHandleMapper.addApplyFinanceHandle(applyFinanceHandleDTO);
      return true;
   }
   /**
    * 根据id查询申请财务受理信息
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public ApplyFinanceHandleDTO getApplyFinanceHandleById(int pid) throws TException {
      return financeHandleMapper.getApplyFinanceHandleById(pid);
   }
   /**
    * 根据id修改申请财务受理信息
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public boolean updateApplyFinanceHandle(ApplyFinanceHandleDTO applyFinanceHandleDTO) throws TException {
      financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandleDTO);
      return true;
   }
   /**
    * 财务放款
    *@author:liangyanjun
    *@time:2016年1月14日上午10:00:49
    *@param pid
    *@return
    */
   @Override
   @Transactional
   public boolean makeLoans(ApplyFinanceHandleDTO applyFinanceHandleDTO,int userId,int isLoanFinish,int houseClerkId) throws TException {
      logger.info("财务放款：入参："+ applyFinanceHandleDTO.toString());
      try {
         //执行更新财务办理信息
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandleDTO);
         logger.info("财务放款数据修改成功："+ applyFinanceHandleDTO);
         int financeHandleId = applyFinanceHandleDTO.getFinanceHandleId();
         FinanceHandleDTO financeHandle = financeHandleMapper.getFinanceHandleById(financeHandleId);
         financeHandle.setHouseClerkId(houseClerkId);//指定赎楼员id
         int recPro = applyFinanceHandleDTO.getRecPro();
         int status = financeHandle.getStatus();
         // 1.如果把收款项目为“第一次放款”填完
         if(recPro ==Constants.REC_PRO_3){
            //原状态为已收费时
            if (status==Constants.REC_STATUS_ALREADY_CHARGE) {
               //检查放款是否已完结
               if (isLoanFinish==Constants.LOAN_IS_FINISH) {
                  financeHandle.setStatus(Constants.REC_STATUS_LOAN_APPLY);
               }else{
                  financeHandle.setStatus(Constants.REC_STATUS_LOAN_NO_FINISH);
               }
               financeHandleMapper.updateFinanceHandle(financeHandle);
               //第一或第二次放款，并且为放款已完结，修改财务办理状态为放款申请
            }else if(isLoanFinish==Constants.LOAN_IS_FINISH&&status==Constants.REC_STATUS_LOAN_NO_FINISH){
               financeHandle.setStatus(Constants.REC_STATUS_LOAN_APPLY);
               financeHandleMapper.updateFinanceHandle(financeHandle);
            }
            //第一或第二次放款，并且为放款已完结，修改财务办理状态为放款申请
         }else if(recPro ==Constants.REC_PRO_5&&isLoanFinish==Constants.LOAN_IS_FINISH&&status==Constants.REC_STATUS_LOAN_NO_FINISH){
            financeHandle.setStatus(Constants.REC_STATUS_LOAN_APPLY);
            financeHandleMapper.updateFinanceHandle(financeHandle);
         }
      } catch (Exception e) {
         logger.error("根据id更新财务办理信息：入参：applyFinanceHandleDTO:"+ applyFinanceHandleDTO +",userId:"+userId+"。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }
   /**
    * 根据项目id获取最新的回款信息，如果有第二次回款则返回第二次，没有则返回第一次回款信息
    *@author:liangyanjun
    *@time:2016年3月4日下午2:12:36
    *@param applyFinanceHandleDTO
    *@return
    */
   @Override
   public ApplyFinanceHandleDTO getNewRecApplyFinance(int projectId) throws TException {
      return financeHandleMapper.getNewRecApplyFinance(projectId);
   }
   /**
    * 根据项目id和放款项目id获取金额总和
    *@author:liangyanjun
    *@time:2016年3月13日下午10:16:12
    *@param projectId
    *@param recPros
    *@return
    */
   @Override
   public double getRecMoney(int projectId, List<Integer> recPros) throws TException {
      return financeHandleMapper.getRecMoney(projectId, recPros);
   }
   /**
    * 财务放款列表分页查询
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public List<FinanceIndexDTO> queryFinanceIndex(FinanceIndexDTO financeIndexDTO) throws TException {
      return financeHandleMapper.queryFinanceIndex(financeIndexDTO);
   }
   /**
    * 财务放款列表查询数量
    *@author:liangyanjun
    *@time:2016年12月23日下午5:20:04
    */
   @Override
   public int getFinanceIndexTotal(FinanceIndexDTO financeIndexDTO) throws TException {
      return financeHandleMapper.getFinanceIndexTotal(financeIndexDTO);
   }

   /**
    * 在财务表添加一条展期收费的记录
    *@author:liangyanjun
    *@time:2016年4月13日上午9:52:30
    *@param projectId
    *@return
    */
   @Override
   public int addFinanceHandleByExtension(int projectId) throws TException {
      try {
    	  ApplyFinanceHandleDTO extensionApplyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_8);
    	  //判斷收費數據是否存在，存在則不操作
    	  if (extensionApplyFinanceHandle!=null&&extensionApplyFinanceHandle.getPid()>0) {
			return 1;
		   }
         //查询项目信息
         Project project = projectMapper.getProjectInfoById(projectId);
         //添加财务受理主表数据
         FinanceHandleDTO f = new FinanceHandleDTO();
         f.setProjectId(projectId);//
         f.setStatus(Constants.REC_STATUS_NO_CHARGE);//未收费
         f.setCreaterId(project.getPmUserId());
         financeHandleMapper.addFinanceHandle(f);
         //在财务表添加一条展期收费的记录
         ApplyFinanceHandleDTO applyFinanceHandleDTO = new ApplyFinanceHandleDTO();
         applyFinanceHandleDTO.setFinanceHandleId(f.getPid());
         applyFinanceHandleDTO.setRecPro(Constants.REC_PRO_8);
         applyFinanceHandleDTO.setCreaterId(f.getCreaterId());
         financeHandleMapper.addApplyFinanceHandle(applyFinanceHandleDTO);
         logger.info("项目ID"+projectId+"在财务表添加展期收费成功");
      } catch (Exception e) {
         logger.error("项目ID"+projectId+"在财务表添加展期收费的记录失败：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));     
      }
      return 1;
   }

   /**
    * 财务展期收费
    */
   @Override
   @Transactional
   public int collectExtensionFee(FinanceHandleDTO financeHandleDTO, ApplyFinanceHandleDTO applyFinanceHandleDTO) throws TException {
      int projectId = financeHandleDTO.getProjectId();
      try {
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandleDTO);
         financeHandleMapper.updateFinanceHandle(financeHandleDTO);
         // 展期申请,修改展期表数据
         ProjectExtensionDTO dto = new ProjectExtensionDTO();
         dto.setProjectId(projectId);
         // 根据项目ID查询展期信息
         List<ProjectExtensionView> views = loanExtensionMapper.getProjectExtensionList(dto);
         ProjectExtensionView extension = views.get(0);
         //修改回款时间
         ProjectForeclosure foreclosure = projectForeclosureMapper.getForeclosureByProjectId(extension.getExtensionProjectId());
         foreclosure.setPaymentDate(extension.getExtensionDate());
         int extensionDays = foreclosure.getExtensionDays()+extension.getExtensionDays();
         double extensionFee = applyFinanceHandleDTO.getExtensionFee()+foreclosure.getExtensionFee();
         foreclosure.setExtensionDays(extensionDays);
         foreclosure.setExtensionFee(extensionFee);
         projectForeclosureMapper.updateByPrimaryKey(foreclosure);
         //修改项目状态为已归档
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_13, DateUtil.format(new Date()));
      } catch (Exception e) {
         logger.error("项目ID"+projectId+"财务展期收费失败：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));     
      }
      return 1;
   }

   /**
    * 财务收费
    *@author:liangyanjun
    *@time:2016年5月30日下午3:00:13
    */
   @Override
   @Transactional
   public boolean collectFee(List<ApplyFinanceHandleDTO> applyFinanceHandleList) throws TException {
      try {
         // 执行更新财务办理信息
         for (ApplyFinanceHandleDTO newDto:applyFinanceHandleList) {
            financeHandleMapper.updateApplyFinanceHandle(newDto);
         }
         int financeHandleId = applyFinanceHandleList.get(0).getFinanceHandleId();
         FinanceHandleDTO financeHandle = financeHandleMapper.getFinanceHandleById(financeHandleId);
         int projectId = financeHandle.getProjectId();
         int status = financeHandle.getStatus();

         // 则把财务办理收费状态改为“已收费”
         if (status == Constants.REC_STATUS_NO_CHARGE) {
            financeHandle.setStatus(Constants.REC_STATUS_ALREADY_CHARGE);
            financeHandle.setCollectFeeStatus(Constants.REC_STATUS_ALREADY_CHARGE);
            financeHandleMapper.updateFinanceHandle(financeHandle);
         }
         if (status == Constants.REC_STATUS_NO_CHARGE) {
            // 已收费之后，给退咨询费，退佣金，增加一条初始化数据
            List<Integer> asList = Arrays.asList(Constants.REFUND_FEE_TYPE_2, Constants.REFUND_FEE_TYPE_4);
            for (Integer type : asList) {
               RefundFeeDTO dto = new RefundFeeDTO();
               dto.setProjectId(projectId);
               dto.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_1);
               dto.setType(type);
               // 退佣金
               if (type == Constants.REFUND_FEE_TYPE_4) {
                  double commission = 0.0;
                  commission = getRecMoney(projectId, Arrays.asList((Constants.REC_PRO_7)));
                  dto.setReturnFee(commission);
               }
               refundFeeMapper.addRefundFee(dto);
               logger.info("项目ID：" + projectId + "添加" + Constants.REFUND_FEE_MAP.get(new Long(type)) + "信息成功："+ dto);
            }
         }
      } catch (Exception e) {
         logger.error("财务收费：入参：applyFinanceHandleList:"+ applyFinanceHandleList + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }

   /**
    * 根据项目ID和收款项目进行查询
    *@author:liangyanjun
    *@time:2016年8月8日上午10:29:20
    */
   @Override
   public ApplyFinanceHandleDTO getByProjectIdAndRecPro(int projectId, int recPro) throws TException {
      ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId,recPro);
      if (applyFinanceHandle == null || applyFinanceHandle.getPid()<=0) {
         return new ApplyFinanceHandleDTO();
      }
      return applyFinanceHandle;
   }


   /**
    * 根据项目id获取财务主表信息
    *@author:liangyanjun
    *@time:2016年8月8日下午2:10:41
    */
   @Override
   public FinanceHandleDTO getFinanceHandleByProjectId(int projectId) throws TException {
      FinanceHandleDTO financeHandleDTO = financeHandleMapper.getFinanceHandleByProjectId(projectId);
      if (financeHandleDTO==null) {
         return new FinanceHandleDTO();
      }
      return financeHandleDTO;
   }

    /** 
     * 收费列表分页查询
     * @author:liangyanjun
     * @time:2016年10月27日上午10:09:46 */
    @Override
    public List<FinanceIndexDTO> queryFinanceCollectFeeIndex(FinanceIndexDTO financeIndexDTO) throws TException {
        return financeHandleMapper.queryFinanceCollectFeeIndex(financeIndexDTO);
    }

    /** 
     * 
     *  收费列表查询总数
     * @author:liangyanjun
     * @time:2016年10月27日上午10:09:46 */
    @Override
    public int getFinanceCollectFeeIndexTotal(FinanceIndexDTO financeIndexDTO) throws TException {
        return financeHandleMapper.getFinanceCollectFeeIndexTotal(financeIndexDTO);
    }

    /** 
     *  根据项目id和项目编号查询放款记录
     * @author:liangyanjun
     * @time:2016年10月27日上午10:09:46 */
	@Override
	public List<ApplyFinanceHandleDTO> getLoanHisByProjectId(int projectId,String productNum)
			throws TException {
		return financeHandleMapper.getLoanHisByProjectId(projectId,productNum);
	}

	/**
	 * 财务收费(房抵贷贷)
	 *@author:liangyanjun
	 *@time:2017年12月11日上午11:26:36
	 */
	@Transactional
   @Override
   public boolean collectFddFee(ApplyFinanceHandleDTO applyFinanceHandle) throws TException {
      double interest = applyFinanceHandle.getInterest();
      String recDate = applyFinanceHandle.getRecDate();
      String recAccount = applyFinanceHandle.getRecAccount();
      int recPro = applyFinanceHandle.getRecPro();
      int createrId = applyFinanceHandle.getCreaterId();
      // 执行更新财务办理信息
      financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
      int financeHandleId = applyFinanceHandle.getFinanceHandleId();
      FinanceHandleDTO financeHandle = financeHandleMapper.getFinanceHandleById(financeHandleId);
      int collectFeeStatus = financeHandle.getCollectFeeStatus();
      int projectId = financeHandle.getProjectId();
      int status = financeHandle.getStatus();//财务办理的收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4，放款申请中=5

      // 则把财务办理收费状态改为“已收费”
      if (collectFeeStatus == Constants.REC_STATUS_NO_CHARGE) {
         //修改收费状态
         financeHandle.setCollectFeeStatus(Constants.REC_STATUS_ALREADY_CHARGE);
         //只有未收费才改放款状态的收费状态
         if (status==Constants.REC_STATUS_NO_CHARGE) {
            financeHandle.setStatus(Constants.REC_STATUS_ALREADY_CHARGE);
         }
         financeHandleMapper.updateFinanceHandle(financeHandle);
      }
      //保存收费记录
      CollectFeeHis collectFeeHis=new CollectFeeHis();
      collectFeeHis.setFinanceHandleId(financeHandleId);
      collectFeeHis.setProjectId(projectId);
      collectFeeHis.setConsultingFee(interest);
      collectFeeHis.setRecDate(recDate);
      collectFeeHis.setRecAccount(recAccount);
      collectFeeHis.setCreaterId(createrId);
      collectFeeHis.setRecPro(recPro);
      collectFeeHisMapper.insert(collectFeeHis);
      return true;
   }


}
