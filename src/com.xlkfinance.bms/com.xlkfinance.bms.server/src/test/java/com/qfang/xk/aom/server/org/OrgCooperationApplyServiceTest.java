package com.qfang.xk.aom.server.org;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-06 15:29:25 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合作信息修改申请表<br>
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
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApplyService;
import com.xlkfinance.bms.common.util.DateUtils;

public class OrgCooperationApplyServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.qfang.xk.aom.rpc";
    private OrgCooperatCompanyApplyService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("org", "OrgCooperatCompanyApplyService");
        try {
            client = (OrgCooperatCompanyApplyService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }



    @Test
    public void test_getAll() throws Exception {
    	OrgCooperatCompanyApplyInf orgCooperationUpdateApply = new OrgCooperatCompanyApplyInf();
    	orgCooperationUpdateApply.setOrgId(4);
    	List<OrgCooperatCompanyApplyInf> list = client.getAll(orgCooperationUpdateApply);
        for (OrgCooperatCompanyApplyInf obj : list) {
            System.out.println(obj);
        }
    }

    @Test
    public void test_getById() throws Exception {
        OrgCooperatCompanyApplyInf obj = client.getByOrgId(4);
        System.out.println(obj);
    }

    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(1);
    }
}
