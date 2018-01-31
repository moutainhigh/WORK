package com.xlkfinance.bms.server.system;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-18 10:07:56 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 邮件信息表<br>
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
import com.xlkfinance.bms.rpc.system.SysMailInfo;
import com.xlkfinance.bms.rpc.system.SysMailInfoService;
import com.xlkfinance.bms.common.util.DateUtils;

public class SysMailInfoServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private SysMailInfoService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("system", "SysMailInfoService");
        try {
            client = (SysMailInfoService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void test_sendMail() {
        try {
            SysMailInfo mail = new SysMailInfo();
            mail.setRecMail("995396426@qq.com");
            mail.setSubject("小贷业务审批");
            mail.setContent("<h1>主题：梁衍君空间都是付款就舍不得放假表空间</h1>");
            client.sendMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-18 10:07:56 */
    @Test
    public void test_insert() throws Exception {
        SysMailInfo sysMailInfo = new SysMailInfo();
        sysMailInfo.setPid(1);
        sysMailInfo.setSubject("subject");
        sysMailInfo.setContent("content");
        sysMailInfo.setSendMail("sendMail");
        sysMailInfo.setRecMail("recMail");
        sysMailInfo.setCreatorId(1);
        sysMailInfo.setCreatorDate(DateUtils.getCurrentDateTime());
        sysMailInfo.setCategory(1);
        sysMailInfo.setStatus(1);
        sysMailInfo.setIpAddress("ipAddress");
        client.insert(sysMailInfo);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-18 10:07:56 */
    @Test
    public void test_update() throws Exception {
        SysMailInfo sysMailInfo = new SysMailInfo();
        sysMailInfo.setPid(1);
        sysMailInfo.setSubject("subject");
        sysMailInfo.setContent("content");
        sysMailInfo.setSendMail("sendMail");
        sysMailInfo.setRecMail("recMail");
        sysMailInfo.setCreatorId(1);
        sysMailInfo.setCreatorDate(DateUtils.getCurrentDateTime());
        sysMailInfo.setCategory(1);
        sysMailInfo.setStatus(1);
        sysMailInfo.setIpAddress("ipAddress");
        client.update(sysMailInfo);
    }

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-18 10:07:56 */
    @Test
    public void test_getAll() throws Exception {
        SysMailInfo sysMailInfo = new SysMailInfo();
        List<SysMailInfo> list = client.getAll(sysMailInfo);
        for (SysMailInfo obj : list) {
            System.out.println(obj);
        }
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-18 10:07:56 */
    @Test
    public void test_getById() throws Exception {
        SysMailInfo obj = client.getById(9);
        System.out.println(obj);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-18 10:07:56 */
    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(8);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-18 10:07:56 */
    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(6);
        pids.add(7);
        client.deleteByIds(pids);
    }
}
