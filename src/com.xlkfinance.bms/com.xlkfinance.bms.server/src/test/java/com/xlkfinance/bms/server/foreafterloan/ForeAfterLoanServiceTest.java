package com.xlkfinance.bms.server.foreafterloan;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.foreafterloan.ForeAfterLoanService;
import com.xlkfinance.bms.rpc.foreafterloan.TransitMonitorIndex;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年1月12日上午10:32:27 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：赎楼贷后模块 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
public class ForeAfterLoanServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private ForeAfterLoanService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("foreafterloan", "ForeAfterLoanService");
        try {
            client = (ForeAfterLoanService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void test_queryTransitMonitorIndex() throws Exception {
        TransitMonitorIndex query = new TransitMonitorIndex();
        List<TransitMonitorIndex> list = client.queryTransitMonitorIndex(query);
        int total = client.getTransitMonitorIndexTotal(query);
        System.out.println("total:" + total);
        for (TransitMonitorIndex obj : list) {
            System.out.println(obj);
        }
    }
}
