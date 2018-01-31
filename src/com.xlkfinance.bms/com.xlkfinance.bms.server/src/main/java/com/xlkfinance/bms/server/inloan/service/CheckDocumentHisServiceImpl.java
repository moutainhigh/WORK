package com.xlkfinance.bms.server.inloan.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService.Iface;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectEstateMapper;
import com.xlkfinance.bms.server.inloan.mapper.CheckDocumentHisMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-04 15:53:20 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("checkDocumentHisServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService")
public class CheckDocumentHisServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(CheckDocumentHisServiceImpl.class);

   @Resource(name = "checkDocumentHisMapper")
   private CheckDocumentHisMapper checkDocumentHisMapper;
   
   @Resource(name = "bizProjectEstateMapper")
   private BizProjectEstateMapper bizProjectEstateMapper;
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   @Override
   public List<CheckDocumentDTO> getAll(CheckDocumentDTO checkDocument) throws ThriftServiceException, TException {
      return checkDocumentHisMapper.getAll(checkDocument);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   @Override
   public CheckDocumentDTO getById(int pid) throws ThriftServiceException, TException {
      CheckDocumentDTO checkDocument = checkDocumentHisMapper.getById(pid);
      if (checkDocument==null) {
         return new CheckDocumentDTO();
      }
      return checkDocument;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   @Override
   public int insert(CheckDocumentDTO checkDocument) throws ThriftServiceException, TException {
      return checkDocumentHisMapper.insert(checkDocument);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   @Override
   public int update(CheckDocumentDTO checkDocument) throws ThriftServiceException, TException {
      return checkDocumentHisMapper.update(checkDocument);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return checkDocumentHisMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return checkDocumentHisMapper.deleteByIds(pids);
   }
    /**
     * 查档历史列表(分页查询)
     *@author:liangyanjun
     *@time:2017年1月4日下午5:30:46
     *@param checkDocument
     *@return
     *@throws TException
     */
    @Override
    public List<CheckDocumentDTO> queryCheckDocumentHisIndex(CheckDocumentDTO checkDocument) throws TException {
        return checkDocumentHisMapper.queryCheckDocumentHisIndex(checkDocument);
    }
    /**
     * 查档历史列表总数
     *@author:liangyanjun
     *@time:2017年1月4日下午5:30:49
     *@param checkDocument
     *@return
     *@throws TException
     */
    @Override
    public int getCheckDocumentHisIndexTotal(CheckDocumentDTO checkDocument) throws TException {
        return checkDocumentHisMapper.getCheckDocumentHisIndexTotal(checkDocument);
    }
    /**
     * 根据项目id查询该项目的所有物业是否已查档
     * 返回结果为：未查档的物业，多个物业以英文逗号隔开
     *@author:liangyanjun
     *@time:2017年1月6日上午9:46:38
     *@param projectId
     *@return
     *@throws TException
     */
    @Override
    public String getNotCheckDocumentHouseName(int projectId) throws TException {
        StringBuffer notCheckDocumentHouse=new StringBuffer();
        List<BizProjectEstate> list = bizProjectEstateMapper.getAllByProjectId(projectId);//根据项目id查询该项目的所有物业
        CheckDocumentDTO query=new CheckDocumentDTO();
        BizProjectEstate bizProjectEstate=null;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            bizProjectEstate=list.get(i);
            int houseId = bizProjectEstate.getHouseId();
            query.setHouseId(houseId);
            List<CheckDocumentDTO> checkDocumentHisList = checkDocumentHisMapper.getAll(query);//根据物业id查询查档记录
            if (checkDocumentHisList==null||checkDocumentHisList.isEmpty()) {//判断是否存在查档记录
                if(StringUtil.isBlank(notCheckDocumentHouse.toString())){
                    notCheckDocumentHouse.append(bizProjectEstate.getHouseName());
                }else{
                    notCheckDocumentHouse.append(","+bizProjectEstate.getHouseName());
                }
            }
        }
        return notCheckDocumentHouse.toString();
    }
    /**
     *根据项目Id查询所有查档历史表
     *@author:liangyanjun
     *@time:2017年1月6日上午9:46:38
     *@param projectId
     *@return
     *@throws TException
     */
	@Override
	public List<CheckDocumentDTO> getAllCdtoByProjectId(@Param("projectId")int projectId)
			throws TException {
		 return checkDocumentHisMapper.getAllCdtoByProjectId(projectId);
	}

	@Override
	public List<CheckDocumentDTO> queryCheckDocumentHisIndex1(
			CheckDocumentDTO checkDocument) throws TException {
		// TODO Auto-generated method stub
	   return checkDocumentHisMapper.queryCheckDocumentHisIndex1(checkDocument);
	}

}
