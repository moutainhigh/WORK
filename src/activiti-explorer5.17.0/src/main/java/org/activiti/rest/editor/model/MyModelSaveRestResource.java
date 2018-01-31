package org.activiti.rest.editor.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xk.hlc.common.encrypt.RSAUtils;
import com.xk.hlc.date.util.DateUtils;
import com.xk.hlc.md5.util.MD5;
import com.xk.hlc.str.util.StringUtils;
import com.xk.hlc.system.dto.SysLog;
import com.xk.hlc.system.dto.SysUser;
import com.xk.hlc.system.service.SysUserService;
import com.xk.hlc.workflow.dto.BizProcessConfigInfo;
import com.xk.hlc.workflow.service.BizProcessConfigService;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年6月13日上午9:57:11 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
@RestController
public class MyModelSaveRestResource implements ModelDataJsonConstants {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MyModelSaveRestResource.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private BizProcessConfigService bizProcessConfigService ;
    
    @Autowired
    private SysUserService  sysUserService;
    @Autowired
    private com.xk.hlc.system.service.SysLogService  sysLogService;

    @RequestMapping(value = "/model/{modelId}/save_my", method = RequestMethod.PUT)
    public void saveModel(HttpServletRequest request,@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
        try {
            Model model = repositoryService.getModel(modelId);
            //获取密文
            String encrypt = values.getFirst("encrypt");
            if (StringUtils.isBlank(encrypt)) {
                throw new RuntimeException();
            }
            SysLog sysLog=new SysLog();
            sysLog.setLogMsg("encrypt:"+encrypt);
            sysLogService.addSysLog(sysLog);
            //私钥
            String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK8ZgTtDP3bDPgQoAeZa+1rvQrhe2QyO3SXfw2dMz5TO3fIQa9/L9o/IQDNyItHjyvtDK5iDDJ9Bbl9PZpRkmXEsrqffLWWBi8yuZSz7LDIoIi0tbyOQxysfKB2STnoTWdOB369tmEEattEkOGL5n+U/Oid0rtGa6MHFh4TZjk5vAgMBAAECgYEAgsOqVka3a4sNkm8KVCzYECYkjqoOsCk1EKbWOBwN3hTLfY4z8ZNR1Dj5cTIovNTAbvrP4Prlfz2PHtCeS+4BBF0SIbIAp9FgblrdbAss3EK+ByoHGOOmsZELGxYl7EidjpcHE6Trj1CsEH6gzQXd3pnHIcPJneKNPaQXGEVVMCECQQDrh0f1j86SVArxrulmkD1BSlsHBIb/lBnKH+thotqbPk4Bezc5vNHp1ecY+3P8FeT8Jq40g5b9dC7iyjUQ2kcRAkEAvlGg4tRm1tIzjDRtCPAL2waSf7jPIekl6R3UO2NGzuDaswVbxj/ZhE855pNMIgngK0dCOw0mTyB5wEV6MPY9fwJAWpQoRHQNyZCwtMnD0UHfjOB/qW9AS8I1ONqVt3LeB54qeb9dOcM4J2mv+peZv3TcH2zwyhlhVA/+iBGj3ttTQQJBAKWRcfujeIvudjXE7g5Wdpnmz9AZQOlpYTe6YYYAyfr0Fo6jXMHNjjfSjLaQ8iyylrhP18hzmh5ATGr+hlzN+5UCQBzxaAQngW6S/D4KrFgqo9zVXiW1V+lN3iZ3DB2qbNUS1iI/aqYdn1Vsj6qrViqV8Eg6zT9z4J+4NIeQ1g4tSP4=";
            //解密
            encrypt=new String(RSAUtils.decryptDataOnJava(URLDecoder.decode(encrypt), privateKey));
            sysLog.setLogMsg("解密encrypt:"+encrypt);
            sysLogService.addSysLog(sysLog);
            //转成对象
            JSONObject parseObject = com.alibaba.fastjson.JSON.parseObject(encrypt);
            //获取登录用户信息
            String loginUserId = (String) parseObject.get("uid");
            //时间戳
            String timestamp = (String) parseObject.get("timestamp");
            //时间戳超过30分钟，则请求无效
            if (StringUtils.isBlank(timestamp)||DateUtils.minutesDifference(DateUtils.stringToDate(timestamp), new Date())>30) {
                throw new RuntimeException();
            }
            SysUser sysUser = sysUserService.getUserById(loginUserId);
            if (sysUser==null) {
                throw new RuntimeException();
            }
            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            String name = values.getFirst("name");
            modelJson.put(MODEL_NAME, name);
            modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
            model.setMetaInfo(modelJson.toString());
            model.setName(name);
            repositoryService.saveModel(model);

            String jsonXml = values.getFirst("json_xml");
            System.out.println("jsonXml:"+jsonXml);
            repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));

            String svgXml = values.getFirst("svg_xml");
            System.out.println("svgXml:"+svgXml);
            InputStream svgStream = new ByteArrayInputStream(svgXml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
            BizProcessConfigInfo query=new BizProcessConfigInfo();
            query.setProcessModelId(modelId);
            List<BizProcessConfigInfo> list = bizProcessConfigService.getAll(query);
            if (list!=null&&!list.isEmpty()) {
                BizProcessConfigInfo updateConfigInfo = list.get(0);
                updateConfigInfo.setProcessName(name);
                updateConfigInfo.setUpdateId(loginUserId);
                bizProcessConfigService.update(updateConfigInfo);
            } else {
                BizProcessConfigInfo configInfo=new BizProcessConfigInfo();
                configInfo.setProcessModelId(modelId);
                configInfo.setProcessName(name);
                configInfo.setProcessCategrory("5158990FDDF5D103E0536400A8C00370");//流程分类
                configInfo.setTennatEnterpriesId(sysUser.getTennatEnterpriesId());//企业id
                configInfo.setProductId("setProductId");//产品id
                configInfo.setCreateId(loginUserId);
                configInfo.setUpdateId(loginUserId);
                configInfo.setModuleId("setModuleId");//模块编号
                bizProcessConfigService.add(configInfo);
            }
        } catch (Exception e) {
            LOGGER.error("Error saving model", e);
            throw new ActivitiException("Error saving model", e);
        }
    }
}
