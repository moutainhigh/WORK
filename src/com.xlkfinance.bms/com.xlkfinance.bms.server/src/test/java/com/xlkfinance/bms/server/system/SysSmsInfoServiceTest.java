package com.xlkfinance.bms.server.system;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-18 09:28:47 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 短信表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.system.SysSmsInfo;
import com.xlkfinance.bms.rpc.system.SysSmsInfoService;
import com.xlkfinance.bms.common.util.DateUtils;

public class SysSmsInfoServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private SysSmsInfoService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("system", "SysSmsInfoService");
        try {
            client = (SysSmsInfoService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-18 09:28:47 */
    @Test
    public void test_insert() throws Exception {
        SysSmsInfo sysSmsInfo = new SysSmsInfo();
        sysSmsInfo.setPid(1);
        sysSmsInfo.setTelphone("telphone");
        sysSmsInfo.setContent("content");
        sysSmsInfo.setSendDate(DateUtils.getCurrentDateTime());
        sysSmsInfo.setCreateDate(DateUtils.getCurrentDateTime());
        sysSmsInfo.setCreatorId(1);
        sysSmsInfo.setCategory(1);
        sysSmsInfo.setStatus(1);
        sysSmsInfo.setIpAddress("ipAddress");
        client.insert(sysSmsInfo);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-18 09:28:47 */
    @Test
    public void test_update() throws Exception {
        SysSmsInfo sysSmsInfo = new SysSmsInfo();
        sysSmsInfo.setPid(1);
        sysSmsInfo.setTelphone("telphone");
        sysSmsInfo.setContent("content");
        sysSmsInfo.setSendDate(DateUtils.getCurrentDateTime());
        sysSmsInfo.setCreateDate(DateUtils.getCurrentDateTime());
        sysSmsInfo.setCreatorId(1);
        sysSmsInfo.setCategory(1);
        sysSmsInfo.setStatus(1);
        sysSmsInfo.setIpAddress("ipAddress");
        client.update(sysSmsInfo);
    }

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-18 09:28:47 */
    @Test
    public void test_getAll() throws Exception {
        SysSmsInfo sysSmsInfo = new SysSmsInfo();
        List<SysSmsInfo> list = client.getAll(sysSmsInfo);
        for (SysSmsInfo obj : list) {
            System.out.println(obj);
        }
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-18 09:28:47 */
    @Test
    public void test_getById() throws Exception {
        SysSmsInfo obj = client.getById(9);
        System.out.println(obj);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-18 09:28:47 */
    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(8);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-18 09:28:47 */
    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(1);
        pids.add(2);
        client.deleteByIds(pids);
    }
}
