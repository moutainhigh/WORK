package com.xlkfinance.bms.server.inloan;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-13 10:19:59 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 差诉讼历史表<br>
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
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationHisService;

public class CheckLitigationHisServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private CheckLitigationHisService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("inloan", "CheckLitigationHisService");
        try {
            client = (CheckLitigationHisService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 10:19:59 */
    @Test
    public void test_insert() throws Exception {
        CheckLitigationDTO bizCheckLitigationHis = new CheckLitigationDTO();
        bizCheckLitigationHis.setPid(1);
        bizCheckLitigationHis.setProjectId(1);
        bizCheckLitigationHis.setCheckStatus(1);
        bizCheckLitigationHis.setApprovalStatus(1);
        bizCheckLitigationHis.setCheckDate(DateUtils.getCurrentDateTime());
        bizCheckLitigationHis.setCheckWay(1);
        bizCheckLitigationHis.setRemark("remark");
        bizCheckLitigationHis.setAttachmentId(1);
        bizCheckLitigationHis.setCreaterDate(DateUtils.getCurrentDateTime());
        bizCheckLitigationHis.setCreaterId(1);
        bizCheckLitigationHis.setUpdateId(1);
        bizCheckLitigationHis.setUpdateDate(DateUtils.getCurrentDateTime());
        client.insert(bizCheckLitigationHis);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 10:19:59 */
    @Test
    public void test_update() throws Exception {
        CheckLitigationDTO bizCheckLitigationHis = new CheckLitigationDTO();
        bizCheckLitigationHis.setPid(1);
        bizCheckLitigationHis.setProjectId(1);
        bizCheckLitigationHis.setCheckStatus(1);
        bizCheckLitigationHis.setApprovalStatus(1);
        bizCheckLitigationHis.setCheckDate(DateUtils.getCurrentDateTime());
        bizCheckLitigationHis.setCheckWay(1);
        bizCheckLitigationHis.setRemark("remark");
        bizCheckLitigationHis.setAttachmentId(1);
        bizCheckLitigationHis.setCreaterDate(DateUtils.getCurrentDateTime());
        bizCheckLitigationHis.setCreaterId(1);
        bizCheckLitigationHis.setUpdateId(1);
        bizCheckLitigationHis.setUpdateDate(DateUtils.getCurrentDateTime());
        client.update(bizCheckLitigationHis);
    }

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-13 10:19:59 */
    @Test
    public void test_getAll() throws Exception {
        CheckLitigationDTO bizCheckLitigationHis = new CheckLitigationDTO();
        List<CheckLitigationDTO> list = client.getAll(bizCheckLitigationHis);
        for (CheckLitigationDTO obj : list) {
            System.out.println(obj);
        }
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-13 10:19:59 */
    @Test
    public void test_getById() throws Exception {
        CheckLitigationDTO obj = client.getById(9);
        System.out.println(obj);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-13 10:19:59 */
    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(8);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-13 10:19:59 */
    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(3);
        pids.add(2);
        client.deleteByIds(pids);
    }
    @Test
    public void test_queryCheckLitigationHis() throws Exception {
        CheckLitigationDTO query=new CheckLitigationDTO();
        query.setPage(1);
        List<CheckLitigationDTO> list = client.queryCheckLitigationHisIndex(query);
        int total = client.getCheckLitigationHisIndexTotal(query);
        System.out.println("total:"+total);
        for (CheckLitigationDTO obj : list) {
            System.out.println(obj);
        }
    }
}
