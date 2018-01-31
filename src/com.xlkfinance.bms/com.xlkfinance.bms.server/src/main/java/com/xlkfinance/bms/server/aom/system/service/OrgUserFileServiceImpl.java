package com.xlkfinance.bms.server.aom.system.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.system.OrgUserFile;
import com.qfang.xk.aom.rpc.system.OrgUserFileService.Iface;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserFileMapper;
import com.xlkfinance.bms.server.system.mapper.BizFileMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-01 10:47:05 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构用户资料附件<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgUserFileServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.system.OrgUserFileService")
public class OrgUserFileServiceImpl implements Iface {
   @Resource(name = "orgUserFileMapper")
   private OrgUserFileMapper orgUserFileMapper;
   
   @Resource(name = "bizFileMapper")
   private BizFileMapper bizFileMapper;
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   @Override
   public List<OrgUserFile> getAll(OrgUserFile orgUserFile) throws ThriftServiceException, TException {
      return orgUserFileMapper.getAll(orgUserFile);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   @Override
   public OrgUserFile getById(int pid) throws ThriftServiceException, TException {
      OrgUserFile orgUserFile = orgUserFileMapper.getById(pid);
      if (orgUserFile==null) {
         return new OrgUserFile();
      }
      return orgUserFile;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   @Override
   public int insert(OrgUserFile orgUserFile) throws ThriftServiceException, TException {
      return orgUserFileMapper.insert(orgUserFile);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   @Override
   public int update(OrgUserFile orgUserFile) throws ThriftServiceException, TException {
      return orgUserFileMapper.update(orgUserFile);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgUserFileMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return orgUserFileMapper.deleteByIds(pids);
   }

   /**
    * 根据用户id机构用户资料附件
    *@author:liangyanjun
    *@time:2016年9月1日上午10:51:36
    */
   @Override
   public List<OrgUserFile> getByUserId(int userId) throws TException {
      return orgUserFileMapper.getByUserId(userId);
   }

   /**
    * 保存文件信息并关联到机构
    *@author:liangyanjun
    *@time:2016年9月1日上午11:12:48
    */
   @Override
   public int saveOrgUserFile(int userId, List<BizFile> fileList) throws TException {
      for (BizFile bizFile : fileList) {
         bizFileMapper.saveBizFile(bizFile);
         
         OrgUserFile orgUserFile=new OrgUserFile();
         orgUserFile.setFileId(bizFile.getPid());
         orgUserFile.setUserId(userId);
         orgUserFile.setStatus(AomConstant.STATUS_ENABLED);
         orgUserFileMapper.insert(orgUserFile);
      }
      return 1;
   }

   /**
    * 机构用户资料列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月2日上午10:06:29
    */
   @Override
   public List<BizFile> queryOrgUserFilePage(OrgUserFile query) throws TException {
      return orgUserFileMapper.queryOrgUserFilePage(query);
   }

   /**
    * 机构用户资料列表查询总数
    *@author:liangyanjun
    *@time:2016年9月2日上午10:06:29
    */
   @Override
   public int getOrgUserFileTotal(OrgUserFile query) throws TException {
      return orgUserFileMapper.getOrgUserFileTotal(query);
   }

}
