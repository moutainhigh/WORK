package com.xlkfinance.bms.server.aom.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.system.OrgEmpIndex;
import com.qfang.xk.aom.rpc.system.OrgFnPermissionIndex;
import com.qfang.xk.aom.rpc.system.OrgUserFunAccount;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService.Iface;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserFunAccountMapper;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserInfoMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月4日下午4:44:35 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：机构端用户体系（机构用户，机构员工，合伙人）的service <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgUserInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.system.OrgUserService")
public class OrgUserInfoServiceImpl implements Iface {
   @Resource(name = "orgUserInfoMapper")
   private OrgUserInfoMapper orgUserInfoMapper;
   
   @Resource(name = "orgUserFunAccountMapper")
   private OrgUserFunAccountMapper orgUserFunAccountMapper;
   /**
    * 根据条件查询用户集合
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public List<OrgUserInfo> getAll(OrgUserInfo orgUser) throws ThriftServiceException, TException {
      return orgUserInfoMapper.getAll(orgUser);
   }

   /**
    * 根据id查询用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public OrgUserInfo getById(int pid) throws ThriftServiceException, TException {
      return orgUserInfoMapper.getById(pid);
   }

   /**
    * 添加一个用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public int insert(OrgUserInfo orgUser) throws ThriftServiceException, TException {
      return orgUserInfoMapper.insert(orgUser);
   }

   /**
    * 根据id更新用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public int update(OrgUserInfo orgUser) throws ThriftServiceException, TException {
      return orgUserInfoMapper.update(orgUser);
   }

   /**
    * 根据id删除用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    * 等级id集合删除多个用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    * 分页查询用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public List<OrgUserInfo> getOrgUserByPage(OrgUserInfo orgUser) throws ThriftServiceException, TException {
      return null;
   }

   /**
    * 查询用户总数
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public int getOrgUserCount(OrgUserInfo orgUser) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    * 修改用户状态：修改用户账号启用或者禁用
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   @Override
   public int updateStatus(int pid, int status) throws ThriftServiceException, TException {
      return status;
   }
   
   /**
    * 根据用户名获取用户
    *@author:liangyanjun
    *@time:2016年7月7日下午5:48:18
    */
   @Override
   public OrgUserInfo queryOrgUserByName(String userName) throws TException {
      OrgUserInfo userInfo = orgUserInfoMapper.queryOrgUserByName(userName);
      if (userInfo == null) {
         return new OrgUserInfo();
      }
      return userInfo;
   }

   /**
    *  根据手机号码获取用户
    *@author:liangyanjun
    *@time:2016年7月7日下午5:48:18
    */
   @Override
   public OrgUserInfo getUserByPhone(String phone) throws TException {
      OrgUserInfo userInfo = orgUserInfoMapper.getUserByPhone(phone);
      if (userInfo == null) {
         return new OrgUserInfo();
      }
      return userInfo;
   }

   /**根据邮箱获取用户
    * 根据邮箱获取用户
    *@author:liangyanjun
    *@time:2016年7月8日下午3:38:44
    */
   @Override
   public OrgUserInfo getUserByEmail(String email) throws TException {
      OrgUserInfo userInfo = orgUserInfoMapper.getUserByEmail(email);
      if (userInfo==null) {
         return new OrgUserInfo();
      }
      return userInfo;
   }

   /**
    * 检查用户名是否已存在
    *@author:liangyanjun
    *@time:2016年7月8日下午3:54:58
    */
   @Override
   public boolean checkUserNameIsExist(String userName) throws TException {
      return orgUserInfoMapper.queryOrgUserByName(userName)!=null;
   }

   /**
    * 检查手机号码是否已存在
    *@author:liangyanjun
    *@time:2016年7月8日下午3:54:58
    */
   @Override
   public boolean checkPhoneIsExist(String phone) throws TException {
      return orgUserInfoMapper.getUserByPhone(phone)!=null;
   }

   /**
    * 检查email是否已存在
    *@author:liangyanjun
    *@time:2016年7月8日下午3:54:58
    */
   @Override
   public boolean checkEmailIsExist(String email) throws TException {
      return orgUserInfoMapper.getUserByEmail(email)!=null;
   }

   /**
    * 员工列表（分页查询）
    *@author:liangyanjun
    *@time:2016年7月11日下午2:20:52
    */
   @Override
   public List<OrgEmpIndex> findOrgEmpIndexPage(OrgEmpIndex emp) throws TException {
      return orgUserInfoMapper.findOrgEmpIndexPage(emp);
   }

   /**
    * 员工列表总数（分页查询）
    *@author:liangyanjun
    *@time:2016年7月11日下午2:20:52
    */
   @Override
   public int getOrgEmpIndexTotal(OrgEmpIndex emp) throws TException {
      return orgUserInfoMapper.getOrgEmpIndexTotal(emp);
   }

   /**
    * 增加机构员工
    *@author:liangyanjun
    *@time:2016年7月12日下午3:14:10
    */
   @Override
   public int addOrgEmp(OrgUserInfo orgUser) throws TException {
      orgUserInfoMapper.insert(orgUser);
      int userId = orgUser.getPid();
      // 用户资金帐户信息入库
      OrgUserFunAccount funAccount = new OrgUserFunAccount();
      funAccount.setUserId(userId);
      funAccount.setStatus(AomConstant.STATUS_ENABLED);
      orgUserFunAccountMapper.insert(funAccount);
      return userId;
   }

   /**
    * 根据用户id集合更新用户的数据权限
    *@author:liangyanjun
    *@time:2016年7月12日下午4:25:29
    */
   @Override
   public int updateDateScopeByIds(List<Integer> idList, int dateScope, int orgId, int userType) throws TException {
      orgUserInfoMapper.updateDateScopeByIds(idList,dateScope,orgId,userType);
      return 1;
   }

   /**
    * 机构管理平台--功能权限列表(分页查询)
    *@author:liangyanjun
    *@time:2016年7月26日上午11:13:45
    */
   @Override
   public List<OrgFnPermissionIndex> findOrgFnPermissionIndexPage(OrgFnPermissionIndex orgFnPermissionIndex) throws TException {
      return orgUserInfoMapper.findOrgFnPermissionIndexPage(orgFnPermissionIndex);
   }

   /**
    * 机构管理平台--功能权限列表总数
    *@author:liangyanjun
    *@time:2016年7月26日上午11:13:45
    */
   @Override
   public int getOrgFnPermissionIndexTotal(OrgFnPermissionIndex orgFnPermissionIndex) throws TException {
      return orgUserInfoMapper.getOrgFnPermissionIndexTotal(orgFnPermissionIndex);
   }

}
