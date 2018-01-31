package com.xlkfinance.bms.server.inloan;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-10-14 15:54:46 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 查档文件关联<br>
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
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFile;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFileService;

public class CheckDocumentFileServiceTest {
    private String serverIp = "127.0.0.1";
    private int serverPort = 19090;
    private String basePackage = "com.xlkfinance.bms.rpc";
    private CheckDocumentFileService.Client client;

    public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
        String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName)
                .toString();
        BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
        return clientFactory;
    }

    @Before
    public void init() {
        BaseClientFactory clientFactory = getFactory("inloan", "CheckDocumentFileService");
        try {
            client = (CheckDocumentFileService.Client) clientFactory.getClient();
        } catch (ThriftException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void test_insert() throws Exception {
        CheckDocumentFile checkDocumentFile = new CheckDocumentFile();
        checkDocumentFile.setPid(1);
        checkDocumentFile.setFileCategory(1);
        checkDocumentFile.setProjectId(1);
        checkDocumentFile.setFileId(10725);
        client.insert(checkDocumentFile);
    }

    @Test
    public void test_update() throws Exception {
        CheckDocumentFile checkDocumentFile = new CheckDocumentFile();
        checkDocumentFile.setPid(1);
        checkDocumentFile.setFileCategory(1);
        checkDocumentFile.setProjectId(1);
        checkDocumentFile.setFileId(1);
        client.update(checkDocumentFile);
    }

    @Test
    public void test_getAll() throws Exception {
        CheckDocumentFile checkDocumentFile = new CheckDocumentFile();
        List<CheckDocumentFile> list = client.getAll(checkDocumentFile);
        for (CheckDocumentFile obj : list) {
            System.out.println(obj);
        }
    }

    @Test
    public void test_getById() throws Exception {
        CheckDocumentFile obj = client.getById(9);
        System.out.println(obj);
    }

    @Test
    public void test_deleteById() throws Exception {
        client.deleteById(8);
    }

    @Test
    public void test_deleteByIds() throws Exception {
        List<Integer> pids = new ArrayList<Integer>();
        pids.add(6);
        pids.add(7);
        client.deleteByIds(pids);
    }

    @Test
    public void test_2() throws Exception {
        CheckDocumentFile query = new CheckDocumentFile();
        List<CheckDocumentFile> list = client.queryCheckDocumentFile(query);
        for (CheckDocumentFile obj : list) {
            System.out.println(obj);
        }
    }
}
