package com.xlkfinance.bms.server.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBase;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerCredit;
import com.xlkfinance.bms.rpc.customer.CusPerCreditAddress;
import com.xlkfinance.bms.rpc.customer.CusPerCreditDTO;
import com.xlkfinance.bms.rpc.customer.CusPerCreditDef;
import com.xlkfinance.bms.rpc.customer.CusPerFamily;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyDTO;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyFinance;
import com.xlkfinance.bms.rpc.customer.CusPerService.Iface;
import com.xlkfinance.bms.rpc.customer.CusPerSocSec;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.customer.CusRelation;
import com.xlkfinance.bms.rpc.customer.QueryPersonDTO;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComBaseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerBaseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerCreditAddressMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerCreditDefMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerCreditMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerFamilyFinanceMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerFamilyMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerSocSecMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPersonMapper;
import com.xlkfinance.bms.server.customer.mapper.CusRelationMapper;

/**
 * 类描述<br>
 * 个人客户实现类
 * 
 * @author zhanghg
 * @version v1.0
 */
@SuppressWarnings("unchecked")
@Service("cusPerServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CusPerService")
public class CusPerServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(CusPerServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerBaseMapper")
	private CusPerBaseMapper cusPerBaseMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPersonMapper")
	private CusPersonMapper cusPersonMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerSocSecMapper")
	private CusPerSocSecMapper cusPerSocSecMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerFamilyMapper")
	private CusPerFamilyMapper cusPerFamilyMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerFamilyFinanceMapper")
	private CusPerFamilyFinanceMapper cusPerFamilyFinanceMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerCreditMapper")
	private CusPerCreditMapper cusPerCreditMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerCreditAddressMapper")
	private CusPerCreditAddressMapper cusPerCreditAddressMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusPerCreditDefMapper")
	private CusPerCreditDefMapper cusPerCreditDefMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusComBaseMapper")
	private CusComBaseMapper cusComBaseMapper;
	
	@Resource(name = "cusRelationMapper")
	private CusRelationMapper cusRelationMapper;
	
	@Override
	public int addCusPerBase(CusPerBaseDTO cusPerBaseDTO) throws ThriftServiceException, TException {
		int acctId = 0;
		try {
			
			
			// 保存客户账户信息
			CusAcct cusAcct = cusPerBaseDTO.getCusAcct();
			CusPerBase cusPerBase = cusPerBaseDTO.getCusPerBase();
			CusPerson cusPerson = cusPerBaseDTO.getCusPerson();
			CusPerSocSec cusPerSocSec = cusPerBaseDTO.getCusPerSocSec();
			CusRelation cusRelation = cusPerBaseDTO.getCusRelation();
			//配偶信息
			CusPerson spousePerson = cusPerBaseDTO.getSpousePerson();
			if(cusAcct.getPmUserId() <= 0){
				throw new ThriftServiceException(ExceptionCode.CUSPER_BASE_ADD, "新增个人客户基础信息失败,客户经理Id不能为空！");
			}
			cusAcctMapper.insert(cusAcct);
			acctId = cusAcct.getPid();
			// 保存个人客户基础信息
			cusPerBase.setCusAcct(cusAcct);
			cusPerBaseMapper.insert(cusPerBase);

			// 保存个人客户人员信息
			cusPerson.setCusAcct(cusAcct);
			cusPersonMapper.insert(cusPerson);

			// 保存个人配偶信息(婚姻状况已婚的前提)
			if(spousePerson != null){
				spousePerson.setCusAcct(cusAcct);
				cusPersonMapper.insert(spousePerson);
			}
			// 保存个人客户社保信息
			if(cusPerSocSec == null){
				cusPerSocSec = new CusPerSocSec();
			}
			cusPerSocSec.setCusPerBase(cusPerBase);
			cusPerSocSecMapper.insert(cusPerSocSec);
			
			if(cusRelation != null){
				//保存客户关系中间表数据
				cusRelation.setAcctId(acctId);
				cusRelationMapper.insert(cusRelation);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSPER_BASE_ADD, "新增个人客户基础信息失败！");
		}
		
		return acctId;
	}

	@Override
	public int updateCusPerBase(CusPerBaseDTO cusPerBaseDTO) throws ThriftServiceException, TException {
		
		CusAcct cusAcct = cusPerBaseDTO.getCusAcct();
		CusPerBase cusPerBase = cusPerBaseDTO.getCusPerBase();
		CusPerson cusPerson = cusPerBaseDTO.getCusPerson();
		CusPerSocSec cusPerSocSec = cusPerBaseDTO.getCusPerSocSec();
		CusRelation cusRelation = cusPerBaseDTO.getCusRelation();
		//配偶信息
		CusPerson spousePerson = cusPerBaseDTO.getSpousePerson();
		int acctId = 0;
		if(cusAcct != null){
			acctId = cusAcct.getPid();
			try {
				// 修改客户账户信息
				cusAcctMapper.updateByPrimaryKey(cusAcct);
				// 修改个人客户基础信息
				cusPerBaseMapper.updateByPrimaryKey(cusPerBase); 
				// 修改个人客户人员信息
				cusPersonMapper.updateByPrimaryKey(cusPerson);
				// 修改个人配偶信息(婚姻状况已婚的前提)
				if(spousePerson != null){
					int pid = spousePerson.getPid();
					if(pid == 0){
						spousePerson.setCusAcct(cusAcct);
						cusPersonMapper.insert(spousePerson);
					}else{
						cusPersonMapper.updateByPrimaryKey(spousePerson);
					}
				}
				// 修改个人客户社保信息
				if(cusPerSocSec != null && cusPerSocSec.getPid()>0){
					cusPerSocSecMapper.updateByPrimaryKey(cusPerSocSec);
				}else{
					if(cusPerSocSec == null){
						cusPerSocSec = new CusPerSocSec();
					}
					cusPerSocSec.setCusPerBase(cusPerBase);
					cusPerSocSecMapper.insert(cusPerSocSec);
				}
				// 保存客户经理ID
				if(cusPerSocSec != null){
					cusPerBaseMapper.updatePmUserId(cusAcct);
				}
				//判断当前登录用户是否与客户有关联，无关联添加一个关联
				if(cusRelation  != null){
					cusRelation.setAcctId(acctId);
					List<CusRelation> result = cusRelationMapper.getAll(cusRelation);
					if(result == null || result.size()==0){
						cusRelationMapper.insert(cusRelation);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new ThriftServiceException(ExceptionCode.CUSPER_BASE_UPDATE, "修改个人客户基础信息失败！");
			}
		}
		return acctId;
	}

	@Override
	public List<GridViewDTO> getCusPerBases(CusPerBaseDTO cusPerBaseDTO) throws ThriftServiceException, TException {
		List<GridViewDTO> listResult = cusPerBaseMapper.getCusPerBases(cusPerBaseDTO);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public CusPerBaseDTO getCusPerBase(int pid) throws ThriftServiceException, TException {
		CusPerBaseDTO cusPerBaseDTO = new CusPerBaseDTO();
		try {
			// 查询个人客户基础信息
			cusPerBaseDTO.setCusPerBase((CusPerBase) cusPerBaseMapper.selectByPrimaryKey(pid));
			// 查询个人客户客户账户信息
			cusPerBaseDTO.setCusAcct((CusAcct) cusAcctMapper.selectByPrimaryKey(cusPerBaseDTO.getCusPerBase().getCusAcct().getPid()));
			// 查询个人客户人员信息
			cusPerBaseDTO.setCusPerson((CusPerson) cusPersonMapper.selectMainByPerId(cusPerBaseDTO.getCusAcct().getPid()));
			// 查询个人客户社保信息
			cusPerBaseDTO.setCusPerSocSec((CusPerSocSec) cusPerSocSecMapper.selectByPrimaryKey(pid));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSPER_BASE_QUERY, "查询个人客户基础信息失败！");
		}
		return cusPerBaseDTO;
	}

	@Override
	public int addCusPerFamily(CusPerFamilyDTO cusPerFamilyDTO) throws ThriftServiceException, TException {

		int flag;
		try {
			// 保存个人客户家庭信息
			flag = cusPerFamilyMapper.insert(cusPerFamilyDTO.getCusPerFamily());
			// 保存个人客户家庭情况信息
			flag = flag == 0 ? 0 : cusPerFamilyFinanceMapper.insert(cusPerFamilyDTO.getCusPerFamilyFinance());
			// 保存个人客户配偶信息
			flag = flag == 0 ? 0 : cusPersonMapper.insert(cusPerFamilyDTO.getCusPerson());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSPER_FAMILY_ADD, "新增个人客户家庭信息失败！");
		}

		return flag;
	}

	@Override
	public int updateCusPerFamily(CusPerFamilyDTO cusPerFamilyDTO) throws ThriftServiceException, TException {

		int flag;
		try {
			// 修改个人客户家庭信息
			flag = cusPerFamilyMapper.updateByPrimaryKey(cusPerFamilyDTO.getCusPerFamily());

			// 修改个人客户家庭情况信息
			flag = flag == 0 ? 0 : cusPerFamilyFinanceMapper.updateByPrimaryKey(cusPerFamilyDTO.getCusPerFamilyFinance());

			// 修改个人客户配偶信息
			flag = flag == 0 ? 0 : cusPersonMapper.updateByPrimaryKey(cusPerFamilyDTO.getCusPerson());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSPER_FAMILY_UPDATE, "修改个人客户家庭信息失败！");
		}

		return flag;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int addCusPerCredit(CusPerCreditDTO cusPerCreditDTO) throws ThriftServiceException, TException {

		int flag;
		try {
			// 保存个人客户征信记录
			flag = cusPerCreditMapper.insert(cusPerCreditDTO.getCusPerCredit());

			// 保存个人客户征信记录地址信息
			List list = Lists.newArrayList();
			List list2 = Lists.newArrayList();
			if(cusPerCreditDTO.getCreditAddrs() != null){
				for (CusPerCreditAddress addr : cusPerCreditDTO.getCreditAddrs()) {
					addr.setCusPerCredit(cusPerCreditDTO.getCusPerCredit());
					list.add(addr);
				}
				// 保存个人客户地址信息
				flag = flag == 0 ? 0 : cusPerCreditAddressMapper.insertCreditAddrs(list);
			}
			
			if(cusPerCreditDTO.getDeficits() != null){
				for (CusPerCreditDef def : cusPerCreditDTO.getDeficits()) {
					def.setCusPerCredit(cusPerCreditDTO.getCusPerCredit());
					def.setDayType(1);
					list2.add(def);
				}
			}
			if(cusPerCreditDTO.getSettles() != null){
				for (CusPerCreditDef def : cusPerCreditDTO.getSettles()) {
					def.setCusPerCredit(cusPerCreditDTO.getCusPerCredit());
					def.setDayType(2);
					list2.add(def);
				}
			}
			if(list2 != null && list2.size()>0){
				// 保存个人客户清单信息
				flag = flag == 0 ? 0 : cusPerCreditDefMapper.insertCreditDefs(list2);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSPER_CREDIT_ADD, "新增个人客户征信记录失败！");
		}

		return flag;
	}

	@Override
	public int updateCusPerCredit(CusPerCreditDTO cusPerCreditDTO) throws ThriftServiceException, TException {

		int flag;
		try {
			// 修改个人客户征信记录
			flag = cusPerCreditMapper.updateByPrimaryKey(cusPerCreditDTO.getCusPerCredit());
			if(cusPerCreditDTO.getCreditAddrs() != null && cusPerCreditDTO.getCreditAddrs().size()>0){
				// 修改个人客户征信记录地址信息
				flag = flag == 0 ? 0 : cusPerCreditAddressMapper.updateCreditAddrs(cusPerCreditDTO.getCreditAddrs());
			}
			
			List<CusPerCreditDef> list = cusPerCreditDTO.getDeficits();
			if(list != null && list.size()>0){
				// 修改个人客户征信记录清单
				flag = flag == 0 ? 0 : cusPerCreditDefMapper.updateCreditDefs(list);
			}else{
				list = new ArrayList<CusPerCreditDef>();
			}
			list.addAll(cusPerCreditDTO.getSettles());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSPER_CREDIT_UPDATE, "修改个人客户征信记录失败！");
		}

		return flag;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int deleteCusPerCredit(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		// 批量删除征信记录信息
		return cusPerCreditMapper.deleteCusPerCredit(list);
	}

	@Override
	public List<GridViewDTO> getCusPerCredits(CusPerBase cusPerBase) throws ThriftServiceException, TException {
		// 查询征信记录列表
		List<GridViewDTO> listResult = cusPerCreditMapper.getCusPerCredits(cusPerBase);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public CusPerCreditDTO getCusPerCredit(int pid) throws ThriftServiceException, TException {

		CusPerCreditDef def = new CusPerCreditDef();

		CusPerCredit cusPerCredit = new CusPerCredit();
		cusPerCredit.setPid(pid);
		def.setCusPerCredit(cusPerCredit);
		def.setDayType(1);
		// 查询征信记录透支清单
		List<CusPerCreditDef> deficits = cusPerCreditDefMapper.selectDefByCreditId(def);

		// 查询征信记录结清清单
		def.setDayType(2);
		List<CusPerCreditDef> settles = cusPerCreditDefMapper.selectDefByCreditId(def);

		CusPerCreditDTO cusPerCreditDTO = new CusPerCreditDTO();

		// 查询征信记录
		cusPerCreditDTO.setCusPerCredit((CusPerCredit) cusPerCreditMapper.selectByPrimaryKey(pid));
		// 查询征信记录地址
		cusPerCreditDTO.setCreditAddrs(cusPerCreditAddressMapper.selectAddrByCreditId(pid));
		cusPerCreditDTO.setDeficits(deficits);
		cusPerCreditDTO.setSettles(settles);
		return cusPerCreditDTO;
	}

	@Override
	public CusPerFamilyDTO getCusPerFamily(int perId, int acctId) throws ThriftServiceException, TException {
		CusPerFamilyDTO cusPerFamilyDTO = new CusPerFamilyDTO();

		try {
			// 查询个人家庭信息
			Object cusPerFamily = cusPerFamilyMapper.selectByPrimaryKey(perId);
			// 查询家庭经济情况信息
			Object cusPerFamilyFinance = cusPerFamilyFinanceMapper.selectByPrimaryKey(perId);
			//查询配偶信息条件
			CusPerson query = new CusPerson();
			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(acctId);
			query.setCusAcct(cusAcct);
			// 查询配偶信息
			List<CusPerson> list = cusPersonMapper.getCusPersonByMarr(query );
			CusPerson cusPerson = new CusPerson();
			if(list != null && list.size()>0){
				cusPerson  = list.get(0);
			}
			cusPerFamilyDTO.setCusPerFamily(cusPerFamily == null ? null : (CusPerFamily) cusPerFamily);
			cusPerFamilyDTO.setCusPerFamilyFinance(cusPerFamilyFinance == null ? null : (CusPerFamilyFinance) cusPerFamilyFinance);
			cusPerFamilyDTO.setCusPerson(cusPerson);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			throw new ThriftServiceException(ExceptionCode.CUSPER_FAMILY_QUERY, "查询家庭信息错误！");
		}
		return cusPerFamilyDTO;
	}

	@Override
	public List<GridViewDTO> getCusUnderCom(CusPerBase cusPerBase) throws ThriftServiceException, TException {
		List<GridViewDTO> listResult = cusComBaseMapper.getCusUnderCom(cusPerBase);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public int selectPerIdByAcctId(int acctId) throws ThriftServiceException, TException {
		// 查询个人客户ID
		return cusPerBaseMapper.selectPerIdByAcctId(acctId);
	}

	/**
	 * 
	 * @Description: 查询个人信息
	 * @param cusDto
	 *            个人信息DTO 对象
	 * @return 个人信息集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:01:44
	 */
	@Override
	public List<CusDTO> getPersonalList(CusDTO cusDto) throws TException {
		List<CusDTO> list = new ArrayList<CusDTO>();
		try {
			list = cusPerBaseMapper.getPersonalList(cusDto);
		} catch (Exception e) {
			logger.error("查询个人信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(list.size()==0){
			return new ArrayList<CusDTO>();
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询个人信息--贷前申请
	 * @param cusDto
	 *            个人信息DTO
	 * @return 当前用户下的所有个人客户(包括他的下属的个人客户)的集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:00:45
	 */
	@Override
	public List<CusDTO> getPersonalListTwo(CusDTO cusDto) throws TException {
		List<CusDTO> list = new ArrayList<CusDTO>();
		try {
			list = cusPerBaseMapper.getPersonalListTwo(cusDto);
		} catch (Exception e) {
			logger.error("查询个人信息--贷前申请出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(list.size()==0){
			return new ArrayList<CusDTO>();
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询个人信息--贷前申请-总数
	 * @param cusDto
	 *            个人信息DTO
	 * @return 当前用户下的所有个人客户(包括他的下属的个人客户)的总数
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午1:16:38
	 */
	@Override
	public int getPersonalListTwoCount(CusDTO cusDto) throws TException {
		int count = 0;
		try {
			// 调用方法获取数量
			count = cusPerBaseMapper.getPersonalListTwoCount(cusDto);
		} catch (Exception e) {
			logger.error("查询个人信息--贷前申请-总数出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return count;
	}

	/**
	 * 
	 * @Description: 查询是个人客户 还是企业客户
	 * @param pid
	 *            个人信息主键
	 * @return 1.个人 2.企业
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午12:59:53
	 */
	@Override
	public int getByAcctTypeKeyPid(int pid) throws TException {
		int type = 0;
		try {
			type = cusPerBaseMapper.getByAcctTypeKeyPid(pid);
		} catch (Exception e) {
			logger.error("查询是个人客户 还是企业客户出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return type;
	}

	/**
	 * 
	 * @Description: 根据pid查询个人信息
	 * @param pid
	 *            个人信息主键
	 * @return 个人信息对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午12:59:23
	 */
	@Override
	public CusDTO getPersonalListKeyPid(int pid) throws TException {
		CusDTO cusdto = null;
		try {
			cusdto = cusPerBaseMapper.getPersonalListKeyPid(pid);
		} catch (Exception e) {
			logger.error("根据pid查询个人信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return cusdto == null ? new CusDTO() : cusdto;
	}

	/**
	 * 
	 * @Description: 根据客户ID查询个人信息
	 * @param acctId
	 *            客户ID
	 * @return 个人信息对象
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午2:55:59
	 */
	@Override
	public CusDTO getPersonalListByAcctId(int acctId) throws TException {
		CusDTO cusdto = null;
		try {
			cusdto = cusPerBaseMapper.getPersonalListByAcctId(acctId);
		} catch (Exception e) {
			logger.error("根据客户ID查询个人信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return cusdto == null ? new CusDTO() : cusdto;
	}

	/**
	 * 
	 * @Description: 查询个人非配偶关系人
	 * @param acctId
	 *            客户ID
	 * @return 客户信息DTO集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午12:58:53
	 */
	@Override
	public List<CusDTO> getNoSpouseList(int acctId) throws TException {
		List<CusDTO> list = new ArrayList<CusDTO>();
		try {
			list = cusPerBaseMapper.getNoSpouseList(acctId);
		} catch (Exception e) {
			logger.error("查询个人非配偶关系人出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(list.size()==0){
		   return new ArrayList<CusDTO>();	
		}
		return list;
	}

	@Override
	public int selectIsAcctManager(int currUserPid) throws ThriftServiceException, TException {
		return cusPerBaseMapper.selectIsAcctManager(currUserPid);
	}

	@Override
	public List<CusAcct> getUserName(int projectId) throws ThriftServiceException, TException {
		List<CusAcct> listResult = cusPerBaseMapper.getUserName(projectId);
		if(listResult.size()==0){
			return new ArrayList<CusAcct>();
		}
		return listResult;
	}

	@Override
	public int selectCredit(int projectId) throws ThriftServiceException, TException {
		return cusPerBaseMapper.selectCredit(projectId);
	}

	@Override
	public List<GridViewDTO> getPerBusiness(QueryPersonDTO queryPersonDTO) throws ThriftServiceException, TException {
		if(null==queryPersonDTO){
			return new ArrayList<GridViewDTO>();
		}
		queryPersonDTO.setAcctType(-1);
		queryPersonDTO.setBusinessVariet(-1);
		cusPerBaseMapper.getPerBusiness(queryPersonDTO);
		// 手动在查出来的数据前面加0
		for (int i = 0; i < queryPersonDTO.getResults().size(); i++) {
			String value = queryPersonDTO.getResults().get(i).getValue5().toString();
			boolean flag = value.startsWith(".");
			if (flag == true) {
				value = new StringBuffer("0").append(value).toString();
				queryPersonDTO.getResults().get(i).setValue5(value);
			}
		}
		return queryPersonDTO.getResults();
	}

	@Override
	public int getTotal(CusPerBaseDTO cusPerBaseDTO) throws ThriftServiceException, TException {

		return cusPerBaseMapper.getTotal(cusPerBaseDTO);
	}

	@Override
	public int getTotalUnderCom(CusPerBase cusPerBase) throws ThriftServiceException, TException {
		return cusComBaseMapper.getTotalUnderCom(cusPerBase);
	}

	@Override
	public int getTotalCusPerBase(CusPerBase cusPerBase) throws ThriftServiceException, TException {
		return cusPerCreditMapper.getTotalCusPerBase(cusPerBase);
	}

	/**
	 * 
	 * @Description: 条件查询所有人员信息
	 * @param cusPerson
	 *            人员对象
	 * @return 人员集合
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月9日 下午9:12:26
	 */
	@Override
	public List<CusPerson> getAllCusPerson(CusPerson cusPerson) throws ThriftServiceException, TException {
		List<CusPerson> list = new ArrayList<CusPerson>();
		try {
			// 查询所有的人员信息
			list = cusPersonMapper.getAllCusPerson(cusPerson);
		} catch (Exception e) {
			logger.error("条件查询所有人员信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(list.size()==0){
			return new ArrayList<CusPerson>();
		}
		return list;
	}

	/**
	 * 
	 * @Description: 条件查询人员数量
	 * @param cusPerson
	 *            人员对象
	 * @return 条件人员总数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月9日 下午9:12:53
	 */
	@Override
	public int getAllCusPersonCount(CusPerson cusPerson) throws ThriftServiceException, TException {
		int count = 0;
		try {
			// 调用方法获取数量
			count = cusPersonMapper.getAllCusPersonCount(cusPerson);
		} catch (Exception e) {
			logger.error("条件查询人员数量出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return count;
	}

	@Override
	public String selectMarrStatus(int acctId) throws TException {
		String marrStatus = cusPerFamilyMapper.selectMarrStatus(acctId);
		if(StringUtil.isBlank(marrStatus)){
			marrStatus = "";
		}
		return marrStatus;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<GridViewDTO> searchPerExportByPid(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		List<GridViewDTO> listResult = cusPerBaseMapper.searchPerExportByPid(list);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<GridViewDTO> listUnderExcelExportList(String pids) throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<GridViewDTO> listResult = cusPerBaseMapper.listUnderExcelExportList(list);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
		// return cusComBaseMapper.searchComExportByPid(list);
	}

	@Override
	public List<CusDTO> getNoSpouseLists(int projectId) throws TException {
		List<CusDTO> list = new ArrayList<CusDTO>();
		try {
			list = cusPerBaseMapper.getNoSpouseLists(projectId);
		} catch (Exception e) {
			logger.error("查询个人信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(list.size()==0){
			return new ArrayList<CusDTO>();
		}
		return list;
	}

	@Override
	public int getPersonalListCount(CusDTO cusDto)
			throws ThriftServiceException, TException {
		int rows = 0;
		try {
			rows = cusPerBaseMapper.getPersonalListCount(cusDto);
		} catch (Exception e) {
			logger.error("查询资信评估模板个人列表:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return rows;
	}

	@Override
	public List<CusAcct> getUserNames(int cusAcctManagerId)
			throws ThriftServiceException, TException {
		List<CusAcct> listResult = cusPerBaseMapper.getUserNames(cusAcctManagerId);
		if(listResult.size()==0){
			return new ArrayList<CusAcct>();
		}
		return listResult;
	}

	@Override
	public int selectProjectStatus(int acctID) throws ThriftServiceException,
			TException {
		int flag=0;
		flag=cusPerBaseMapper.selectProjectStatus(acctID);
		return flag;
	}

	/**
	 * 根据pid批量查询关系人信息
	 */
	@Override
	public List<CusDTO> getNoSpouseListByPid(String pids)
			throws ThriftServiceException, TException {
		String arry[] = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arry.length; i++) {
			list.add(arry[i]);
		}
		List<CusDTO> result = new ArrayList<CusDTO>();
		try {
			result = cusPerBaseMapper.getNoSpouseListByPid(list);
		} catch (Exception e) {
			logger.error("根据pid批量查询关系人信息出错:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
	        throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		if(list.size()==0){
			return new ArrayList<CusDTO>();
		}
		return result;
	}

	/**
	 * 根据证件号码查询客户信息
	 */
	@Override
	public List<CusPerson> getCusPersonByNumber(CusPerson cusPerson)throws TException {
		List<CusPerson> result= null;
		try {
			result = cusPersonMapper.getCusPersonByNumber(cusPerson);
		} catch (Exception e) {
			logger.error("根据证件号码查询客户信息:" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<GridViewDTO> getAomCusPerBases(CusPerBaseDTO cusPerBaseDTO) throws TException {
		List<GridViewDTO> listResult = cusPerBaseMapper.getAomCusPerBases(cusPerBaseDTO);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}

	@Override
	public int getAomTotal(CusPerBaseDTO cusPerBaseDTO) throws ThriftServiceException, TException {
		return cusPerBaseMapper.getAomTotal(cusPerBaseDTO);
	}

	@Override
	public List<CusPerson> getRelationCusPerson(CusPerson cusPerson) throws TException {
		return cusPersonMapper.getRelationCusPerson(cusPerson);
	}

	@Override
	public List<CusPerson> checkCusExist(Map<String, String> myMap) throws TException {
		return cusPersonMapper.checkCusExist(myMap);
	}

}
