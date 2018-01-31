package com.xlkfinance.bms.server.inloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFile;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFileService.Iface;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.server.inloan.mapper.CheckDocumentFileMapper;
import com.xlkfinance.bms.server.system.mapper.BizFileMapper;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-10-14 14:59:02 <br>
 * ★☆ @version： 1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档文件关联<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@Service("checkDocumentFileServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.CheckDocumentFileService")
public class CheckDocumentFileServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(CheckDocumentFileServiceImpl.class);

    @Resource(name = "checkDocumentFileMapper")
    private CheckDocumentFileMapper checkDocumentFileMapper;
    @Resource(name = "bizFileMapper")
    private BizFileMapper bizFileMapper;

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    @Override
    public List<CheckDocumentFile> getAll(CheckDocumentFile checkDocumentFile) throws ThriftServiceException,
            TException {
        return checkDocumentFileMapper.getAll(checkDocumentFile);
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    @Override
    public CheckDocumentFile getById(int pid) throws ThriftServiceException, TException {
        CheckDocumentFile checkDocumentFile = checkDocumentFileMapper.getById(pid);
        if (checkDocumentFile == null) {
            return new CheckDocumentFile();
        }
        return checkDocumentFile;
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    @Override
    public int insert(CheckDocumentFile checkDocumentFile) throws ThriftServiceException, TException {
        return checkDocumentFileMapper.insert(checkDocumentFile);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    @Override
    public int update(CheckDocumentFile checkDocumentFile) throws ThriftServiceException, TException {
        return checkDocumentFileMapper.update(checkDocumentFile);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    @Override
    public int deleteById(int pid) throws ThriftServiceException, TException {
        return checkDocumentFileMapper.deleteById(pid);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2016-10-14 14:59:02 */
    @Override
    public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
        return checkDocumentFileMapper.deleteByIds(pids);
    }

    /** 分页查询
     * 
     * @author:liangyanjun
     * @time:2016年10月14日下午3:02:11 */
    @Override
    public List<CheckDocumentFile> queryCheckDocumentFile(CheckDocumentFile query) throws TException {
        return checkDocumentFileMapper.queryCheckDocumentFile(query);
    }

    /** 
     * 获取查询条数
     * @author:liangyanjun
     * @time:2016年10月14日下午3:02:12 */
    @Override
    public int getCheckDocumentFileTotal(CheckDocumentFile query) throws TException {
        return checkDocumentFileMapper.getCheckDocumentFileTotal(query);
    }

    /**
     * 新增
     *@author:liangyanjun
     *@time:2016年10月14日下午5:27:55
     *
     */
    @Override
    public int saveCheckDocumentFile(CheckDocumentFile checkDocumentFile, List<BizFile> fileList) throws TException {
        int projectId = checkDocumentFile.getProjectId();
        int fileCategory = checkDocumentFile.getFileCategory();
        for (BizFile bizFile : fileList) {
            bizFileMapper.saveBizFile(bizFile);
            CheckDocumentFile newDocumentFile = new CheckDocumentFile();
            newDocumentFile.setFileId(bizFile.getPid());
            newDocumentFile.setFileCategory(fileCategory);
            newDocumentFile.setProjectId(projectId);
            checkDocumentFileMapper.insert(newDocumentFile);
        }
        return 1;
    }

}
