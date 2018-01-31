package com.xlkfinance.bms.server.inloan;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-04 16:03:11 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档历史表<br>
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
import com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService;

public class CheckDocumentHisServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private CheckDocumentHisService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("inloan", "CheckDocumentHisService");
        try {
            client = (CheckDocumentHisService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-04 16:03:11 */
    @Test
    public void test_insert() throws Exception {
        CheckDocumentDTO checkDocumentHis = new CheckDocumentDTO();
        checkDocumentHis.setPid(1);
        checkDocumentHis.setProjectId(1);
        checkDocumentHis.setHouseId(1);
        checkDocumentHis.setCheckStatus(1);
        checkDocumentHis.setApprovalStatus(1);
        checkDocumentHis.setCheckDate(DateUtils.getCurrentDateTime());
        checkDocumentHis.setCheckWay(1);
        checkDocumentHis.setRemark("remark");
        checkDocumentHis.setAttachmentId(1);
        checkDocumentHis.setCreaterDate(DateUtils.getCurrentDateTime());
        checkDocumentHis.setCreaterId(1);
        checkDocumentHis.setUpdateId(1);
        checkDocumentHis.setUpdateDate(DateUtils.getCurrentDateTime());
        client.insert(checkDocumentHis);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-04 16:03:11 */
    @Test
    public void test_update() throws Exception {
        CheckDocumentDTO checkDocumentHis = new CheckDocumentDTO();
        checkDocumentHis.setPid(1);
        checkDocumentHis.setProjectId(1);
        checkDocumentHis.setHouseId(1);
        checkDocumentHis.setCheckStatus(1);
        checkDocumentHis.setApprovalStatus(1);
        checkDocumentHis.setCheckDate(DateUtils.getCurrentDateTime());
        checkDocumentHis.setCheckWay(1);
        checkDocumentHis.setRemark("remark");
        checkDocumentHis.setAttachmentId(1);
        checkDocumentHis.setCreaterDate(DateUtils.getCurrentDateTime());
        checkDocumentHis.setCreaterId(1);
        checkDocumentHis.setUpdateId(1);
        checkDocumentHis.setUpdateDate(DateUtils.getCurrentDateTime());
        client.update(checkDocumentHis);
    }

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-04 16:03:11 */
    @Test
    public void test_getAll() throws Exception {
        CheckDocumentDTO checkDocumentHis = new CheckDocumentDTO();
        List<CheckDocumentDTO> list = client.getAll(checkDocumentHis);
        for (CheckDocumentDTO obj : list) {
            System.out.println(obj);
        }
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-04 16:03:11 */
    @Test
    public void test_getById() throws Exception {
        CheckDocumentDTO obj = client.getById(9);
        System.out.println(obj);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-04 16:03:11 */
    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(24);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-04 16:03:11 */
    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(21);
        pids.add(23);
        client.deleteByIds(pids);
    }
    @Test
    public void test_queryCheckDocumentHisIndex() throws Exception {
        CheckDocumentDTO checkDocument=new CheckDocumentDTO();
        checkDocument.setPage(1);
        List<CheckDocumentDTO> list = client.queryCheckDocumentHisIndex(checkDocument);
        int total = client.getCheckDocumentHisIndexTotal(checkDocument);
        System.out.println("total:"+total);
        for (CheckDocumentDTO obj : list) {
            System.out.println(obj);
        }
    }
    @Test
    public void test_getNotCheckDocumentHouseName() throws Exception {
        int projectId=14148;
        String notCheckDocumentHouseName = client.getNotCheckDocumentHouseName(projectId);
        System.out.println(notCheckDocumentHouseName);
    }
}
