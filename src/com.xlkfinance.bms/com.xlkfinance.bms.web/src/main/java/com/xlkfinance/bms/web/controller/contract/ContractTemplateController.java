/**
 * @Title: ContractController.java
 * @Package com.xlkfinance.bms.web.controller.contract
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:51:52
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.contract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.thrift.TException;
import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.rpc.contract.ContractTempLate;
import com.xlkfinance.bms.rpc.contract.ContractTempLateParm;
import com.xlkfinance.bms.rpc.contract.ContractTempLateService;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

@Controller
@RequestMapping("/contractTemplateController")
public class ContractTemplateController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ContractTemplateController.class);

	@RequestMapping(value = "toTempLateList")
	public String toTempLateList() {
		return "contract/contract_template_list";
	}

	/**
	 * @Description: 查询模板信息
	 * @param request
	 * @param response
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月6日 下午2:34:20
	 */
	@RequestMapping(value = "pageTempLateList", method = RequestMethod.POST)
	public void pageTempLateList(HttpServletRequest request, HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			ContractTempLate contractTempLate = new ContractTempLate();
			contractTempLate.setRows(rows);
			contractTempLate.setPage(page);
			List<ContractTempLate> list = client.pageTempLateList(contractTempLate);
			int count = client.pageTotalCount(contractTempLate);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("查询模板列表出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 模糊查询模板列表
	 * @param request
	 * @param response
	 * @param page
	 * @param rows
	 * @param templateType
	 * @param templateName
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 上午11:42:18
	 */
	@RequestMapping(value = "pageTempLateListByLike", method = RequestMethod.POST)
	public void pageTempLateListByLike(HttpServletRequest request, HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("templateCatelog") Integer templateCatelog, @RequestParam("templateType") Integer templateType, @RequestParam("templateName") String templateName) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			ContractTempLate contractTempLate = new ContractTempLate();
			contractTempLate.setRows(rows);
			contractTempLate.setPage(page);
			contractTempLate.setTemplateCatelog(templateCatelog);
			contractTempLate.setTemplateType(templateType);
			contractTempLate.setTemplateName(templateName);
			List<ContractTempLate> list = client.pageTempLateList(contractTempLate);
			int count = client.pageTotalCount(contractTempLate);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("查询模板列表出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @Description: 增加模板
	 * @param request
	 * @param response
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月6日 下午2:34:20
	 */
	@RequestMapping(value = "addTemplate", method = RequestMethod.POST)
	public void addTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		BaseClientFactory c = null;
		try {
			request.setCharacterEncoding("UTF-8");
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();

				String modelPath = getUploadFilePath() + Constants.SEPARATOR + BusinessModule.MODUEL_CONTRACT + Constants.SEPARATOR + "template";
				map = FileUtil.fileUpload(request, response, BusinessModule.MODUEL_CONTRACT, modelPath, getUploadFilePath(), getFileSize(),getUploadFileType());

				ContractTempLate contractTempLate = new ContractTempLate();
				if ((Boolean) map.get("flag") == false) {
					model.put("uploadStatus", "文件上传失败");
				} else {
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						if (item.isFormField()) {
							if (item.getFieldName().equals("templateCatelog")) {
								contractTempLate.setTemplateCatelog(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("templateType")) {
								contractTempLate.setTemplateType(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("templateOwner")) {
								contractTempLate.setTemplateOwner(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("templateUseMode")) {
								contractTempLate.setTemplateUseMode(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("applyType")) {
								contractTempLate.setApplyType(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("cycleType")) {
								contractTempLate.setCycleType(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("templateName")) {
								contractTempLate.setTemplateName(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("contractNumberFun")) {
								contractTempLate.setContractNumberFun(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("templateParFun")) {
								contractTempLate.setTemplateParFun(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
							} else if (item.getFieldName().equals("contractTypeCode")) {
								contractTempLate.setContractTypeCode(ParseDocAndDocxUtil.getStringCode(item));
							} else if (item.getFieldName().equals("templateDesc")) {
								contractTempLate.setTemplateDesc(ParseDocAndDocxUtil.getStringCode(item));
							}
						}
					}
					contractTempLate.setUploadUserId(userId);
					contractTempLate.setTemplateUrl(String.valueOf(map.get("path")));
					contractTempLate.setUpdateDttm(DateUtil.format(new Date()));
					int tempLateId = client.addContractTempLate(contractTempLate);
					if (tempLateId == 0) {
						model.put("uploadStatus", "系统错误,保存模板信息失败，请稍后再试");
					}
					map.put("pid", tempLateId);
					if (!client.addTempLateParm(ParseDocAndDocxUtil.getParmList(map, request))) {
						model.put("uploadStatus", "系统错误,保存模板信息失败,请稍后再试");
					}
				}
			}
		} catch (Exception e) {
			logger.error("保存模板出错 ContractController.addTemplate" + e.getMessage());
			e.printStackTrace();
			model.put("uploadStatus", "系统错误,保存模板信息失败,请稍后再试");
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		model.put("uploadStatus", "保存模板信息成功");
	}

	@RequestMapping(value = "updateTempLate", method = RequestMethod.POST)
	public void updateTempLate(HttpServletRequest request, HttpServletResponse response, @RequestParam("templateName") String templateName, @RequestParam("templateCatelog") Integer templateCatelog, @RequestParam("templateType") Integer templateType, @RequestParam("templateOwner") Integer templateOwner, @RequestParam("templateUseMode") Integer templateUseMode, @RequestParam("contractNumberFun") String contractNumberFun, @RequestParam("templateParFun") Integer templateParFun, @RequestParam("contractTypeCode") String contractTypeCode, @RequestParam("templateDesc") String templateDesc, @RequestParam("pid") int pid, @RequestParam("applyType") Integer applyType, @RequestParam("cycleType") int cycleType) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();

			ContractTempLate contractTempLate = new ContractTempLate();

			contractTempLate.setTemplateName(templateName);
			contractTempLate.setTemplateCatelog(templateCatelog);
			contractTempLate.setTemplateType(templateType);
			contractTempLate.setContractNumberFun(contractNumberFun);
			contractTempLate.setTemplateOwner(templateOwner);
			contractTempLate.setTemplateUseMode(templateUseMode);
			contractTempLate.setTemplateParFun(templateParFun);
			contractTempLate.setContractTypeCode(contractTypeCode);
			contractTempLate.setTemplateDesc(templateDesc);
			contractTempLate.setApplyType(applyType);
			contractTempLate.setCycleType(cycleType);
			contractTempLate.setPid(pid);

			boolean status = client.updateTempLate(contractTempLate);
			outputJson(status, response);
		} catch (Exception e) {
			logger.error("修改模板信息出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 重新上传模板文件
	 * @param response
	 * @param request
	 * @param path
	 * @param model
	 * @author: Chong.Zeng
	 * @date: 2015年1月8日 下午7:46:27
	 */
	@RequestMapping(value = "upload")
	public void upload(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
		BaseClientFactory c = null;
		int fileSize = 0;
		try {
			request.setCharacterEncoding("UTF-8");
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			ContractTempLate contractTempLate = new ContractTempLate();
			Map<String, Object> map = new HashMap<String, Object>();

			String modelPath = getUploadFilePath() + Constants.SEPARATOR + BusinessModule.MODUEL_CONTRACT + Constants.SEPARATOR + "template";
			map = FileUtil.fileUpload(request, response, BusinessModule.MODUEL_CONTRACT, modelPath, getUploadFilePath(), getFileSize(),getUploadFileType());

			@SuppressWarnings("rawtypes")
			List items = (List) map.get("items");
			for (int j = 0; j < items.size(); j++) {
				FileItem item = (FileItem) items.get(j);
				if (item.isFormField()) {
					if (item.getFieldName().equals("pid")) {
						contractTempLate.setPid(Integer.parseInt(ParseDocAndDocxUtil.getStringCode(item)));
					}
				} else {
					if ("file1".equals(item.getFieldName())) {
						fileSize = (int) item.getSize();
					}
				}
			}
			contractTempLate.setTemplateUrl(String.valueOf(map.get("path")));
			boolean update = client.updateTempLate(contractTempLate);
			map.put("pid", contractTempLate.getPid());
			// 判断上传的文件是否为空文件
			if (fileSize == 0) {
				client.delTempLateParm(contractTempLate.getPid());
			} else {
				// 更新完毕后重新解析模板参数
				if (update) {
					// 判断模板参数是否为空
					List<ContractTempLateParm> parmList = ParseDocAndDocxUtil.getParmList(map, request);
					if (parmList.isEmpty()) {
						client.delTempLateParm(contractTempLate.getPid());
					} else {
						client.updateTempLateParm(parmList);
					}
				}
			}
		} catch (TException e) {
			logger.error("更新数据库异常" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("上传文件文件异常" + e.getMessage());
			e.printStackTrace();
		} catch (ServletException e) {
			logger.error("Servlet请求异常" + e.getMessage());
			e.printStackTrace();
		} catch (ThriftException e) {
			logger.error("调用service更新模板信息异常" + e.getMessage());
			e.printStackTrace();
		} catch (XmlException e) {
			logger.error("解析合同模板异常" + e.getMessage());
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			logger.error("需要打开的合同模板文件不存在" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 修改合同模板状态
	 * @param response
	 * @param request
	 * @param pidArray
	 * @author: Chong.Zeng
	 * @date: 2015年1月10日 上午10:52:40
	 */
	@RequestMapping(value = "updateStatus")
	public void updateStatus(HttpServletResponse response, HttpServletRequest request, @RequestParam("pidArray") String pidArray) {
		BaseClientFactory c = null;
		try {
			request.setCharacterEncoding("UTF-8");
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			boolean del = client.deleteTempLate(pidArray);
			if (del) {
				outputJson("删除合同模板成功", response);
			} else {
				outputJson("删除合同模板失败", response);
			}
		} catch (Exception e) {
			logger.error("删除合同模板异常" + e.getMessage());
			outputJson("删除合同模板异常", response);
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 查询模板文件参数
	 * @param request
	 * @param response
	 * @param page
	 * @param rows
	 * @param templateId
	 * @author: Chong.Zeng
	 * @date: 2015年1月12日 下午7:31:47
	 */
	@RequestMapping(value = "pageTempLateParmList", method = RequestMethod.POST)
	public void pageTempLateParmList(HttpServletRequest request, HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("templateId") int templateId) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			TempLateParmDto tempLateParmDto = new TempLateParmDto();
			tempLateParmDto.setContractTemplateId(templateId);
			tempLateParmDto.setRows(rows);
			tempLateParmDto.setPage(page);
			List<TempLateParmDto> list = client.getTempParmList(tempLateParmDto);
			int count = client.getTempTotaleCount(tempLateParmDto);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("查询模板参数出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "updateTempParm", method = RequestMethod.POST)
	@ResponseBody
	public void updateTempParm(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> array) {
		BaseClientFactory c = null;
		List<TempLateParmDto> list = new ArrayList<TempLateParmDto>();
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			JSONArray j = (JSONArray) JSONArray.toJSON(array);
			for (int i = 0; i < j.size(); i++) {
				JSONObject h = (JSONObject) JSONObject.toJSON(j.get(i));
				TempLateParmDto tempLateParmDto = JSONArray.toJavaObject(h, TempLateParmDto.class);
				list.add(tempLateParmDto);
			}
			boolean status = client.updateTempLateParmDto(list);
			outputJson(status, response);
		} catch (Exception e) {
			logger.error("保存模板参数信息出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(value = "delTempParm", method = RequestMethod.POST)
	@ResponseBody
	public void delTempParm(HttpServletRequest request, HttpServletResponse response, @RequestParam("pid") int pid) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractTempLateService");
			ContractTempLateService.Client client = (ContractTempLateService.Client) c.getClient();
			boolean status = client.delTempLateOneParm(pid);
			outputJson(status, response);
		} catch (Exception e) {
			logger.error("删除模板参数信息出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
