package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.OrgEmpIndex;
import com.qfang.xk.aom.rpc.system.OrgFnPermissionIndex;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-04 11:26:56 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构系统用户<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgUserInfoMapper")
public interface OrgUserInfoMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    * 根据条件查询用户集合
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   public List<OrgUserInfo> getAll(OrgUserInfo orgUser);

   /**
    * 根据id查询用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   public OrgUserInfo getById(int pid);

   /**
    * 添加一个用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   public int insert(OrgUserInfo orgUser);

   /**
    * 根据id更新用户
    *@author:liangyanjun
    *@time:2016年7月4日下午2:12:37
    */
   public int update(OrgUserInfo orgUser);

   /**
    * 根据用户名获取用户
    *@author:liangyanjun
    *@time:2016年7月7日下午6:24:17
    *@param userName
    *@return
    */
   public OrgUserInfo queryOrgUserByName(String userName);

   /**
    * 根据手机号码获取用户
    *@author:liangyanjun
    *@time:2016年7月7日下午6:24:36
    *@param phone
    *@return
    */
   public OrgUserInfo getUserByPhone(String phone);

   /**
    * 根据邮箱获取用户
    *@author:liangyanjun
    *@time:2016年7月8日下午3:39:11
    *@param email
    *@return
    */
   public OrgUserInfo getUserByEmail(String email);

   /**
    * 员工列表（分页查询）
    *@author:liangyanjun
    *@time:2016年7月11日下午2:21:35
    *@param emp
    *@return
    */
   public List<OrgEmpIndex> findOrgEmpIndexPage(OrgEmpIndex emp);

   /**
    * 员工列表总数（分页查询）
    *@author:liangyanjun
    *@time:2016年7月11日下午2:21:39
    *@param emp
    *@return
    */
   public int getOrgEmpIndexTotal(OrgEmpIndex emp);

   /**
    * 根据用户id集合更新用户的数据权限
    *@author:liangyanjun
    *@time:2016年7月12日下午4:26:36
    *@param idList
    *@param orgId
    *@param userType
    */
   public void updateDateScopeByIds(@Param(value = "idList")List<Integer> idList,@Param(value = "dateScope")int dateScope,@Param(value = "orgId")int orgId, @Param(value = "userType")int userType);

   /**
    * 机构管理平台--功能权限列表(分页查询)
    *@author:liangyanjun
    *@time:2016年7月26日上午11:16:52
    *@param orgFnPermissionIndex
    *@return
    */
   public List<OrgFnPermissionIndex> findOrgFnPermissionIndexPage(OrgFnPermissionIndex orgFnPermissionIndex);

   /**
    * 机构管理平台--功能权限列表总数
    *@author:liangyanjun
    *@time:2016年7月26日上午11:16:57
    *@param orgFnPermissionIndex
    *@return
    */
   public int getOrgFnPermissionIndexTotal(OrgFnPermissionIndex orgFnPermissionIndex);

   /**
    * 根据id删除用户
    *@author:liangyanjun
    *@time:2016年7月28日下午5:11:08
    *@param orgId
    */
   public void deleteById(int pid);
}
