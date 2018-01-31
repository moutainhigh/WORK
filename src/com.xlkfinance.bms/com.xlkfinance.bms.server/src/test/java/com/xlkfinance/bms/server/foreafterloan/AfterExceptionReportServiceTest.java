package com.xlkfinance.bms.server.foreafterloan;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-16 11:13:48 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 贷后异常上报表<br>
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
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReport;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReportService;
import com.xlkfinance.bms.rpc.foreafterloan.TransitMonitorIndex;
import com.xlkfinance.bms.common.util.DateUtils;

public class AfterExceptionReportServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private AfterExceptionReportService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("foreafterloan", "AfterExceptionReportService");
        try {
            client = (AfterExceptionReportService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /** 插入一条数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 11:13:48 */
    @Test
    public void test_insert() throws Exception {
        AfterExceptionReport afterExceptionReport = new AfterExceptionReport();
        afterExceptionReport.setPid(1);
        afterExceptionReport.setProjectId(1);
        afterExceptionReport.setMonitorUserId(1);
        afterExceptionReport.setExceptionType(1);
        afterExceptionReport.setStatus(1);
        afterExceptionReport.setMonitorDate(DateUtils.getCurrentDateTime());
        afterExceptionReport.setNoticeWay("noticeWay");
        afterExceptionReport.setRemark("remark");
        afterExceptionReport.setAttachmentId(1);
        afterExceptionReport.setCreaterDate(DateUtils.getCurrentDateTime());
        afterExceptionReport.setCreaterId(1);
        afterExceptionReport.setUpdateId(1);
        afterExceptionReport.setUpdateDate(DateUtils.getCurrentDateTime());
        client.insert(afterExceptionReport);
    }

    /** 根据id更新数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 11:13:48 */
    @Test
    public void test_update() throws Exception {
        AfterExceptionReport afterExceptionReport = new AfterExceptionReport();
        afterExceptionReport.setPid(1);
        afterExceptionReport.setProjectId(1);
        afterExceptionReport.setMonitorUserId(1);
        afterExceptionReport.setExceptionType(1);
        afterExceptionReport.setStatus(1);
        afterExceptionReport.setMonitorDate(DateUtils.getCurrentDateTime());
        afterExceptionReport.setNoticeWay("noticeWay");
        afterExceptionReport.setRemark("remark");
        afterExceptionReport.setAttachmentId(1);
        afterExceptionReport.setCreaterDate(DateUtils.getCurrentDateTime());
        afterExceptionReport.setCreaterId(1);
        afterExceptionReport.setUpdateId(1);
        afterExceptionReport.setUpdateDate(DateUtils.getCurrentDateTime());
        client.update(afterExceptionReport);
    }

    /** 根据条件查询所有
     *
     * @author:liangyanjun
     * @time:2017-01-16 11:13:48 */
    @Test
    public void test_getAll() throws Exception {
        AfterExceptionReport afterExceptionReport = new AfterExceptionReport();
        List<AfterExceptionReport> list = client.getAll(afterExceptionReport);
        for (AfterExceptionReport obj : list) {
            System.out.println(obj);
        }
    }

    /** 根据id查询
     *
     * @author:liangyanjun
     * @time:2017-01-16 11:13:48 */
    @Test
    public void test_getById() throws Exception {
        AfterExceptionReport obj = client.getById(5);
        System.out.println(obj);
    }

    /** 根据id删除数据
     *
     * @author:liangyanjun
     * @time:2017-01-16 11:13:48 */
    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(8);
    }

    /** 根据id集合删除
     *
     * @author:liangyanjun
     * @time:2017-01-16 11:13:48 */
    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(6);
        pids.add(7);
        client.deleteByIds(pids);
    }

    @Test
    public void test_queryAfterExceptionReportHisList() throws Exception {
        AfterExceptionReport query = new AfterExceptionReport();
        List<AfterExceptionReport> list = client.queryAfterExceptionReportHisList(query);
        int total = client.getAfterExceptionReportHisTotal(query);
        System.out.println("total:" + total);
        for (AfterExceptionReport obj : list) {
            System.out.println(obj);
        }
    }
}
