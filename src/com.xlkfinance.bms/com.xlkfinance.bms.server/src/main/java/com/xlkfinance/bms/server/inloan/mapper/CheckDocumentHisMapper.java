package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
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
@MapperScan("checkDocumentHisMapper")
public interface CheckDocumentHisMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   public List<CheckDocumentDTO> getAll(CheckDocumentDTO checkDocument);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   public CheckDocumentDTO getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   public int insert(CheckDocumentDTO checkDocument);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   public int update(CheckDocumentDTO checkDocument);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-04 15:53:20
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 查档历史列表(分页查询)
    *@author:liangyanjun
    *@time:2017年1月4日下午5:31:55
    *@param checkDocument
    *@return
    */
   public List<CheckDocumentDTO> queryCheckDocumentHisIndex(CheckDocumentDTO checkDocument);
   
   /**
    * 查档历史列表总数
    *@author:liangyanjun
    *@time:2017年1月4日下午5:31:59
    *@param checkDocument
    *@return
    */
   public int getCheckDocumentHisIndexTotal(CheckDocumentDTO checkDocument);
   
   
   /**
    * 根据项目Id查询所有查档历史表
    *@author:liangyanjun
    *@time:2017年1月4日下午5:31:59
    *@param checkDocument
    *@return
    */
   public List<CheckDocumentDTO> getAllCdtoByProjectId(@Param("projectId")int projectId);
   /**
    * 查档历史列表(分页查询,根据查档时间)
    *@author:liangyanjun
    *@time:2017年1月4日下午5:31:55
    *@param checkDocument
    *@return
    */
   public List<CheckDocumentDTO> queryCheckDocumentHisIndex1(CheckDocumentDTO checkDocument);
}


