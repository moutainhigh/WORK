package com.xlkfinance.bms.server.inloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferIndexDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferService.Iface;
import com.xlkfinance.bms.server.inloan.mapper.IntermediateTransferMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年4月2日下午2:45:38 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：中途划转 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("intermediateTransferServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.IntermediateTransferService")
public class IntermediateTransferServiceImpl implements Iface {
   @Resource(name = "intermediateTransferMapper")
   private IntermediateTransferMapper intermediateTransferMapper;
    /**
     *根据条件查询中途划转（分页查询）
     *@author:liangyanjun
     *@time:2016年12月23日下午5:42:06
     *@param intermediateTransferDTO
     *@return
     *@throws TException
     */
   @Override
   public List<IntermediateTransferDTO> queryIntermediateTransfer(IntermediateTransferDTO intermediateTransferDTO) throws TException {
      return intermediateTransferMapper.queryIntermediateTransfer(intermediateTransferDTO);
   }
    /**
     * 根据条件查询中途划转总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:43:43
     *@param intermediateTransferDTO
     *@return
     *@throws TException
     */
   @Override
   public int getIntermediateTransferTotal(IntermediateTransferDTO intermediateTransferDTO) throws TException {
      return intermediateTransferMapper.getIntermediateTransferTotal(intermediateTransferDTO);
   }
    /**
     * 添加中途划转
     *@author:liangyanjun
     *@time:2016年12月23日下午5:41:59
     *@param intermediateTransferDTO
     *@return
     *@throws TException
     */
   @Override
   public int addIntermediateTransfer(IntermediateTransferDTO intermediateTransferDTO) throws TException {
      intermediateTransferMapper.addIntermediateTransfer(intermediateTransferDTO);
      return intermediateTransferDTO.getPid();
   }
    /**
     *根据id更新中途划转
     *@author:liangyanjun
     *@time:2016年12月23日下午5:41:56
     *@param intermediateTransferDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean updateIntermediateTransfer(IntermediateTransferDTO intermediateTransferDTO) throws TException {
      intermediateTransferMapper.updateIntermediateTransfer(intermediateTransferDTO);
      return true;
   }
    /**
     * 根据id获取中途划转
     *@author:liangyanjun
     *@time:2016年12月23日下午5:41:52
     *@param pid
     *@return
     *@throws TException
     */
   @Override
   public IntermediateTransferDTO getIntermediateTransferById(int pid) throws TException {
      IntermediateTransferDTO query = new IntermediateTransferDTO();
      query.setPid(pid);
      List<IntermediateTransferDTO> list = intermediateTransferMapper.queryIntermediateTransfer(query);
      if (list==null||list.isEmpty()) {
         return new IntermediateTransferDTO();
      }
      return list.get(0);
   }
    /**
     * 根据条件查询中途划转（分页查询）
     *@author:liangyanjun
     *@time:2016年12月23日下午5:41:48
     *@param intermediateTransferIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public List<IntermediateTransferIndexDTO> queryIntermediateTransferIndex(IntermediateTransferIndexDTO intermediateTransferIndexDTO) throws TException {
      return intermediateTransferMapper.queryIntermediateTransferIndex(intermediateTransferIndexDTO);
   }
    /**
     *根据条件查询中途划转总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:41:43
     *@param intermediateTransferIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public int getIntermediateTransferIndexTotal(IntermediateTransferIndexDTO intermediateTransferIndexDTO) throws TException {
      return intermediateTransferMapper.getIntermediateTransferIndexTotal(intermediateTransferIndexDTO);
   }

   /**
    * 根据项目id和状态（查询的条件为>=状态值） 检查是否有工作流在运行,返回值大于0则有工作流运行
    *@author:liangyanjun
    *@time:2016年4月3日上午2:26:21
    *@param intermediateTransferDTO
    *@return
    */
   @Override
   public int checkWorkFlowExist(IntermediateTransferDTO intermediateTransferDTO) throws TException {
      return intermediateTransferMapper.checkWorkFlowExist(intermediateTransferDTO);
   }
    /**
     * 
     *根据条件查询中途划转申请列表（分页查询）
     *@author:liangyanjun
     *@time:2016年12月23日下午5:42:34
     *@param intermediateTransferIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public List<IntermediateTransferIndexDTO> queryIntermediateTransferRequestIndex(IntermediateTransferIndexDTO intermediateTransferIndexDTO) throws TException {
      return intermediateTransferMapper.queryIntermediateTransferRequestIndex(intermediateTransferIndexDTO);
   }
    /**
     *根据条件查询中途划转申请列表总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:42:39
     *@param intermediateTransferIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public int getIntermediateTransferRequestIndexTotal(IntermediateTransferIndexDTO intermediateTransferIndexDTO) throws TException {
      return intermediateTransferMapper.getIntermediateTransferRequestIndexTotal(intermediateTransferIndexDTO);
   }

}
