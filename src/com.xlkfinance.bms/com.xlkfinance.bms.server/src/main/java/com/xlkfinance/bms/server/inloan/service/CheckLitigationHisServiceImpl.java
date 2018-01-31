package com.xlkfinance.bms.server.inloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationHisService.Iface;
import com.xlkfinance.bms.server.inloan.mapper.CheckLitigationHisMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-13 09:58:28 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 差诉讼历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Service("checkLitigationHisServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.CheckLitigationHisService")
public class CheckLitigationHisServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(CheckLitigationHisServiceImpl.class);

    @Resource(name = "checkLitigationHisMapper")
    private CheckLitigationHisMapper checkLitigationHisMapper;

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    @Override
    public List<CheckLitigationDTO> getAll(CheckLitigationDTO checkLitigation) throws ThriftServiceException, TException {
        return checkLitigationHisMapper.getAll(checkLitigation);
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    @Override
    public CheckLitigationDTO getById(int pid) throws ThriftServiceException, TException {
        CheckLitigationDTO checkLitigation = checkLitigationHisMapper.getById(pid);
        if (checkLitigation == null) {
            return new CheckLitigationDTO();
        }
        return checkLitigation;
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    @Override
    public int insert(CheckLitigationDTO checkLitigation) throws ThriftServiceException, TException {
        return checkLitigationHisMapper.insert(checkLitigation);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    @Override
    public int update(CheckLitigationDTO checkLitigation) throws ThriftServiceException, TException {
        return checkLitigationHisMapper.update(checkLitigation);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    @Override
    public int deleteById(int pid) throws ThriftServiceException, TException {
        return checkLitigationHisMapper.deleteById(pid);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-13 09:58:28 */
    @Override
    public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
        return checkLitigationHisMapper.deleteByIds(pids);
    }

    /** 差诉讼历史列表(分页查询)
     *
     * @author:liangyanjun
     * @time:2017年1月13日上午9:58:53 */
    @Override
    public List<CheckLitigationDTO> queryCheckLitigationHisIndex(CheckLitigationDTO query) throws TException {
        return checkLitigationHisMapper.queryCheckLitigationHisIndex(query);
    }

    /** 差诉讼历史列表总数
     *
     * @author:liangyanjun
     * @time:2017年1月13日上午9:58:53 */
    @Override
    public int getCheckLitigationHisIndexTotal(CheckLitigationDTO query) throws TException {
        return checkLitigationHisMapper.getCheckLitigationHisIndexTotal(query);
    }

	@Override
	public List<CheckLitigationDTO> queryCheckLitigationHisIndex1(
			CheckLitigationDTO query) throws TException {
		// TODO Auto-generated method stub
        return checkLitigationHisMapper.queryCheckLitigationHisIndex1(query);
	}

}
