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
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApplyService;
import com.xlkfinance.bms.common.util.DateUtils;

public class OrgCooperationUpdateApplyServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.qfang.xk.aom.rpc";
    private OrgCooperationUpdateApplyService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("org", "OrgCooperationUpdateApplyService");
        try {
            client = (OrgCooperationUpdateApplyService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void test_insert() throws Exception {
        OrgCooperationUpdateApply orgCooperationUpdateApply = new OrgCooperationUpdateApply();
        orgCooperationUpdateApply.setOrgId(64);
        orgCooperationUpdateApply.setCreditLimit(1);
        orgCooperationUpdateApply.setActivateCreditLimit(1);
        orgCooperationUpdateApply.setPlanRate(1);
        orgCooperationUpdateApply.setFundSizeMoney(1);
        orgCooperationUpdateApply.setAssureMoneyProportion(1);
        orgCooperationUpdateApply.setAssureMoney(1);
        orgCooperationUpdateApply.setActualFeeRate(1);
        orgCooperationUpdateApply.setSingleUpperLimit(1);
        orgCooperationUpdateApply.setAssureMoneyRemark("assureMoneyRemark");
        orgCooperationUpdateApply.setApplyStatus(1);
        orgCooperationUpdateApply.setRemark("remark");
        orgCooperationUpdateApply.setCreaterDate(DateUtils.getCurrentDateTime());
        orgCooperationUpdateApply.setCreaterId(1);
        orgCooperationUpdateApply.setUpdateId(1);
        orgCooperationUpdateApply.setUpdateDate(DateUtils.getCurrentDateTime());
        client.insert(orgCooperationUpdateApply);
    }

    @Test
    public void test_update() throws Exception {
        OrgCooperationUpdateApply orgCooperationUpdateApply = new OrgCooperationUpdateApply();
        orgCooperationUpdateApply.setPid(1);
        orgCooperationUpdateApply.setOrgId(1);
        orgCooperationUpdateApply.setCreditLimit(1);
        orgCooperationUpdateApply.setActivateCreditLimit(1);
        orgCooperationUpdateApply.setPlanRate(1);
        orgCooperationUpdateApply.setFundSizeMoney(1);
        orgCooperationUpdateApply.setAssureMoneyProportion(1);
        orgCooperationUpdateApply.setAssureMoney(1);
        orgCooperationUpdateApply.setActualFeeRate(1);
        orgCooperationUpdateApply.setSingleUpperLimit(1);
        orgCooperationUpdateApply.setAssureMoneyRemark("assureMoneyRemark");
        orgCooperationUpdateApply.setApplyStatus(1);
        orgCooperationUpdateApply.setRemark("remark");
        orgCooperationUpdateApply.setCreaterDate(DateUtils.getCurrentDateTime());
        orgCooperationUpdateApply.setCreaterId(1);
        orgCooperationUpdateApply.setUpdateId(1);
        orgCooperationUpdateApply.setUpdateDate(DateUtils.getCurrentDateTime());
        client.update(orgCooperationUpdateApply);
    }

    @Test
    public void test_getAll() throws Exception {
        OrgCooperationUpdateApply orgCooperationUpdateApply = new OrgCooperationUpdateApply();
        List<OrgCooperationUpdateApply> list = client.getAll(orgCooperationUpdateApply);
        for (OrgCooperationUpdateApply obj : list) {
            System.out.println(obj);
        }
    }

    @Test
    public void test_getById() throws Exception {
        OrgCooperationUpdateApply obj = client.getById(9);
        System.out.println(obj);
    }

    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(1);
    }

    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(17);
        pids.add(18);
        client.deleteByIds(pids);
    }
    @Test
    public void test_getLastByOrgId() throws Exception {
        client.getLastByOrgId(1);
    }
    @Test
    public void test_queryApplyHisIndex() throws Exception {
        OrgCooperationUpdateApply query=new OrgCooperationUpdateApply();
        query.setOrgId(61);
        List<OrgCooperationUpdateApply> list = client.queryApplyHisIndex(query);
        int total = client.getApplyHisIndexTotal(query);
        System.out.println(total);
        for (OrgCooperationUpdateApply obj : list) {
            System.out.println(obj);
        }
    }
}
