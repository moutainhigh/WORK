package com.xlkfinance.bms.server.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfo;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfoService.Iface;
import com.xlkfinance.bms.server.system.mapper.BizFileMapper;
import com.xlkfinance.bms.server.system.mapper.SysAppVersionInfoMapper;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-11-01 09:49:42 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： APP版本信息（版本升级）<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Service("sysAppVersionInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysAppVersionInfoService")
public class SysAppVersionInfoServiceImpl implements Iface {
    @Resource(name = "sysAppVersionInfoMapper")
    private SysAppVersionInfoMapper sysAppVersionInfoMapper;
    
    @Resource(name = "bizFileMapper")
    private BizFileMapper bizFileMapper;

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2016-11-01 09:49:42 */
    @Override
    public List<SysAppVersionInfo> getAll(SysAppVersionInfo sysAppVersionInfo) throws ThriftServiceException, TException {
        return sysAppVersionInfoMapper.getAll(sysAppVersionInfo);
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2016-11-01 09:49:42 */
    @Override
    public SysAppVersionInfo getById(int pid) throws ThriftServiceException, TException {
        SysAppVersionInfo sysAppVersionInfo = sysAppVersionInfoMapper.getById(pid);
        if (sysAppVersionInfo == null) {
            return new SysAppVersionInfo();
        }
        return sysAppVersionInfo;
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2016-11-01 09:49:42 */
    @Override
    public int insert(SysAppVersionInfo sysAppVersionInfo) throws ThriftServiceException, TException {
        return sysAppVersionInfoMapper.insert(sysAppVersionInfo);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2016-11-01 09:49:42 */
    @Override
    public int update(SysAppVersionInfo sysAppVersionInfo) throws ThriftServiceException, TException {
        return sysAppVersionInfoMapper.update(sysAppVersionInfo);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2016-11-01 09:49:42 */
    @Override
    public int deleteById(int pid) throws ThriftServiceException, TException {
        return sysAppVersionInfoMapper.deleteById(pid);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2016-11-01 09:49:42 */
    @Override
    public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
        return sysAppVersionInfoMapper.deleteByIds(pids);
    }

    /** @author:liangyanjun
     * @time:2016年11月1日上午9:52:16 */
    @Override
    public List<SysAppVersionInfo> querySysAppVersionInfo(SysAppVersionInfo query) throws TException {
        // TODO Auto-generated method stub
        return sysAppVersionInfoMapper.querySysAppVersionInfo(query);
    }

    /** @author:liangyanjun
     * @time:2016年11月1日上午9:52:16 */
    @Override
    public int getSysAppVersionInfoTotal(SysAppVersionInfo query) throws TException {
        // TODO Auto-generated method stub
        return sysAppVersionInfoMapper.getSysAppVersionInfoTotal(query);
    }
    /** 
     * 检查版本是否最新，不是返回最新的版本信息，否则返回空对象
     * @author:liangyanjun
     * @time:2016年11月1日上午9:52:16 */
    @Override
    public SysAppVersionInfo checkVersion(SysAppVersionInfo version) throws TException {
        String tempappVersion = version.getAppVersion();
        version.setAppVersion(null);
        version.setStatus(Constants.STATUS_ENABLED);
        List<SysAppVersionInfo> list = sysAppVersionInfoMapper.getAll(version);
        if (list==null||list.isEmpty()) {
            return new SysAppVersionInfo();
        }
        SysAppVersionInfo latestAppVersionInfo = list.get(0);
        int appVersion = Integer.parseInt(tempappVersion.replace("v", "").replace(".", ""));//客户端app版本号
        int latestVersion = Integer.parseInt(latestAppVersionInfo.getAppVersion().replace("v", "").replace(".", ""));//服务端最新版本号
        //比较客户端app版本号和服务端最新版本号，如果小于则说明客户端app需要升级，此时返回最新app的版本信息
        if (appVersion<latestVersion) {
           return latestAppVersionInfo;
        }else{
            return new SysAppVersionInfo();
        }
    }

}
