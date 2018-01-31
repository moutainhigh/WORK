package com.flow.web;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xk.hlc.workflow.service.BizProcessConfigService;

@Controller
public class FlowModelController {
	protected static final Logger LOGGER = LoggerFactory.getLogger(FlowModelController.class);

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private FormService formService;
	
    @Autowired
    private BizProcessConfigService bizProcessConfigService ;
	
	/**
	 * 查询模型列表
	 * @return
	 */
	@RequestMapping("modelList")
	@ResponseBody
	public List<Model> modelList(){
	    //return repositoryService.createModelQuery().list();
        return null;
	}
	
	/**
	 * 新建模型
	 * @return
	 */
	@RequestMapping("createModel")
	public String createFlow(HttpServletRequest request){
		Authentication.setAuthenticatedUserId("kermit");
		String name = "请修改流程名称";
        List<Model> modelList = repositoryService.createModelQuery().modelName(name).list();
        
        Model model = null;
        if (modelList == null || modelList.isEmpty()) {
        
          model = repositoryService.newModel();
          model.setName(name);
          
          ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
          modelObjectNode.put("name", name);
          modelObjectNode.put("description", "请修改流程描述信息");
          model.setMetaInfo(modelObjectNode.toString());
          
          repositoryService.saveModel(model);
          
          //初始化模型数据
          try {
            InputStream svgStream = this.getClass().getClassLoader().getResourceAsStream("org/activiti/explorer/demo/model/test.svg");
            repositoryService.addModelEditorSourceExtra(model.getId(), IOUtils.toByteArray(svgStream));
          } catch(Exception e) {
            LOGGER.warn("Failed to read SVG", e);
          }
          
          try {
        	
            InputStream editorJsonStream = this.getClass().getClassLoader().getResourceAsStream("org/activiti/explorer/demo/model/test.model.json");
            repositoryService.addModelEditorSource(model.getId(), IOUtils.toByteArray(editorJsonStream));
          } catch(Exception e) {
            LOGGER.warn("Failed to read editor JSON", e);
          }
        }else{
        	model = modelList.get(0);
        }
		return "redirect:/modeler.html?modelId=" +  model.getId()+"&encrypt="+URLEncoder.encode(request.getParameter("encrypt"))+"&style="+request.getParameter("style");
	}
	

	/**
	 * 删除模型
	 * @param id
	 * @return
	 */
	@RequestMapping("removeModel")
	public String removeModel(String id){
		//repositoryService.deleteModel(id);
		return "";
	}
}
