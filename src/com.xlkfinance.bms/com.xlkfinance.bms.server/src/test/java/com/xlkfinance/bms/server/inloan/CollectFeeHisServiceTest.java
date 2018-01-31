package com.xlkfinance.bms.server.inloan;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-03-24 11:05:28 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务收费历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHis;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHisService;
import com.xlkfinance.bms.common.util.DateUtils;

public class CollectFeeHisServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private CollectFeeHisService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("inloan", "CollectFeeHisService");
        try {
            client = (CollectFeeHisService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-03-24 11:05:28 */
    @Test
    public void test_insert() throws Exception {
        CollectFeeHis collectFeeHis = new CollectFeeHis();
        collectFeeHis.setPid(1);
        collectFeeHis.setFinanceHandleId(1);
        collectFeeHis.setProjectId(1);
        collectFeeHis.setConsultingFee(1);
        collectFeeHis.setPoundage(1);
        collectFeeHis.setBrokerage(1);
        collectFeeHis.setRecDate(DateUtils.getCurrentDateTime());
        collectFeeHis.setProResource("proResource");
        collectFeeHis.setCreaterId(1);
        collectFeeHis.setCreaterDate(DateUtils.getCurrentDateTime());
        client.insert(collectFeeHis);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-03-24 11:05:28 */
    @Test
    public void test_update() throws Exception {
        CollectFeeHis collectFeeHis = new CollectFeeHis();
        collectFeeHis.setPid(1);
        collectFeeHis.setFinanceHandleId(1);
        collectFeeHis.setProjectId(1);
        collectFeeHis.setConsultingFee(1);
        collectFeeHis.setPoundage(1);
        collectFeeHis.setBrokerage(1);
        collectFeeHis.setRecDate(DateUtils.getCurrentDateTime());
        collectFeeHis.setProResource("proResource");
        collectFeeHis.setCreaterId(1);
        collectFeeHis.setCreaterDate(DateUtils.getCurrentDateTime());
        client.update(collectFeeHis);
    }

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-03-24 11:05:28 */
    @Test
    public void test_getAll() throws Exception {
        CollectFeeHis collectFeeHis = new CollectFeeHis();
        List<CollectFeeHis> list = client.getAll(collectFeeHis);
        for (CollectFeeHis obj : list) {
            System.out.println(obj);
        }
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-03-24 11:05:28 */
    @Test
    public void test_getById() throws Exception {
        CollectFeeHis obj = client.getById(9);
        System.out.println(obj);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-03-24 11:05:28 */
    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(8);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-03-24 11:05:28 */
    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(6);
        pids.add(7);
        client.deleteByIds(pids);
    }

    @Test
    public void test_queryCollectFeeHis() {
       try {
           CollectFeeHis query = new CollectFeeHis();
          List<CollectFeeHis> list = client.queryCollectFeeHis(query);
          int total = client.getCollectFeeHisTotal(query);
          System.out.println("total:" + total);
          for (CollectFeeHis dto : list) {
             System.out.println(dto);
          }
       } catch (TException e) {
          e.printStackTrace();
       }
    }
    @Test
    public void test_queryCollectFeeHis1() throws TException {
        CollectFeeHis query=new CollectFeeHis();
        query.setProjectId(1);
        CollectFeeHis collectFeeHisMoney = client.getCollectFeeHisMoney(query);
        System.out.println(collectFeeHisMoney);
    }
}
