/**
 * @Title: ContractInfoController.java
 * @Package com.xlkfinance.bms.web.controller.contract
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳前海小科互联网金融服务有限公司
 * 
 * @author: huxinlong
 * @date: 2015年1月14日 下午8:00:17
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.contract;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.achievo.framework.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.Contract;
import com.xlkfinance.bms.rpc.contract.ContractAccessorie;
import com.xlkfinance.bms.rpc.contract.ContractAttachment;
import com.xlkfinance.bms.rpc.contract.ContractDynamicTableParameter;
import com.xlkfinance.bms.rpc.contract.ContractNumberService;
import com.xlkfinance.bms.rpc.contract.ContractParameter;
import com.xlkfinance.bms.rpc.contract.ContractProject;
import com.xlkfinance.bms.rpc.contract.ContractService;
import com.xlkfinance.bms.rpc.contract.ContractTempLate;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBase;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBaseService;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssDtl;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.web.constant.Constants;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.DocxToHtml;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;

/**
 * 
 * @ClassName: ContractInfoController
 * @Description: 合同信息和生成合同编号 Controll
 * @author: huxinlong
 * @date: 2015年4月19日 下午3:38:31
 */
@Controller
@RequestMapping("/contractInfoController")
public class ContractInfoController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(ContractInfoController.class);

	@Resource(name = "contractGenerator")
	private ContractGenerator contractGenerator;

	@RequestMapping(value = "toContractList")
	public String toContractInfoList() {
		return "contract/contract_list";
	}

	/**
	 * @Description: 查询合同信息
	 * @param request
	 * @param response
	 * @return
	 * @author: baogang
	 * @date: 2015年1月6日 下午2:34:20
	 */
	@RequestMapping(value = "pageContractList", method = RequestMethod.POST)
	public void pageContractList(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("page") int page,
			@RequestParam("rows") int rows) {
		BaseClientFactory c = null;
		try {
			Contract contract = new Contract();
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			contract.setRows(rows);
			contract.setPage(page);
			List<Contract> list = client.pageContractList(contract);
			int count = client.pageContractCount(contract);
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
	 * @Description: 模糊查询合同信息
	 * @param request
	 * @param response
	 * @return
	 * @author: baogang
	 * @date: 2015年1月6日 下午2:34:20
	 */
	@RequestMapping(value = "pageContractLike", method = RequestMethod.POST)
	public void pageContractLike(HttpServletRequest request,
			HttpServletResponse response, Contract contract) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			List<Contract> list = client.pageContractList(contract);
			int count = client.pageContractCount(contract);
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
	 * @Description: 根据项目ID查询合同项目信息
	 * @param request
	 * @param response
	 * @param projectId
	 * @param modelMap
	 * @author: baogang
	 * @date: 2015年1月19日 上午11:10:33
	 */
	@RequestMapping(value = "projectInfo", method = RequestMethod.POST)
	public void getProjectInfoById(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("projectId") int projectId) {
		BaseClientFactory c = null;
		try {
			Contract contract = new Contract();
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			contract.setProjectId(projectId);
			List<ContractProject> cp = client.getProJectInfoById(contract);
			outputJson(cp, response);
		} catch (Exception e) {
			logger.error("查询合同的项目信息出错" + e.getMessage());
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
	 * @Description: 删除合同附件
	 * @param request
	 * @param response
	 * @param pid
	 * @author: baogang
	 * @date: 2015年1月19日 下午5:32:18
	 */
	@RequestMapping(value = "deleteAccess", method = RequestMethod.POST)
	public void deleteAccess(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "pidArray[]") Integer[] pidArray) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < pidArray.length; i++) {
				list.add(pidArray[i]);
			}
			boolean cp = client.deleteAccessorie(list);
			outputJson(cp, response);
		} catch (Exception e) {
			logger.error("删除合同附件出错" + e.getMessage());
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
	 * @Description: 新增合同附件
	 * @param request
	 * @param response
	 * @param pid
	 * @author: baogang
	 * @date: 2015年1月19日 下午5:32:18
	 */
	@RequestMapping(value = "addAccess", method = RequestMethod.POST)
	public void addAccess(HttpServletRequest request,
			HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		ContractAccessorie conAcc = new ContractAccessorie();
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM,
					"BizFileService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory
					.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response,
						BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(),
						getFileSize(), getFileDateDirectory(),
						getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					bizFile.setUploadUserId(userId);
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("fileType")) {
								conAcc.setFileType(ParseDocAndDocxUtil
										.getStringCode(item));
							} else if (item.getFieldName().equals("fileName")) {
								conAcc.setFileName(ParseDocAndDocxUtil
										.getStringCode(item));
							} else if (item.getFieldName().equals("fileDesc")) {
								conAcc.setFileDesc(ParseDocAndDocxUtil
										.getStringCode(item));
							} else if (item.getFieldName().equals("contractId")) {
								conAcc.setContractId(Integer
										.parseInt(ParseDocAndDocxUtil
												.getStringCode(item)));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName()
										.toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0,
										dotLocation);
								fileType = fileFullName.substring(dotLocation)
										.substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName)
							|| StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					conAcc.setFileId(fileId);
					// 保存资料表信息
					count = client.addAccessorie(conAcc);
					if (count == 0) {
						tojson.put("uploadStatus", "上传资料失败");
					} else {
						tojson.put("uploadStatus", "上传资料数据成功");
					}
				}
			}
		} catch (Exception e) {
			logger.error("上传合同附件出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				} finally {
					if (bizFileClientFactory != null) {
						try {
							bizFileClientFactory.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		outputJson(tojson, response);
	}

	@RequestMapping(value = "editAccess", method = RequestMethod.POST)
	public void editAccess(HttpServletRequest request,
			HttpServletResponse response) {
		int count = 0;
		int fileSize = 0;
		String fileName = null;
		String fileType = null;
		BaseClientFactory c = null;
		BaseClientFactory bizFileClientFactory = null;
		ContractAccessorie conAcc = new ContractAccessorie();
		BizFile bizFile = new BizFile();
		Map<String, Object> tojson = new HashMap<String, Object>();
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			bizFileClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM,
					"BizFileService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			BizFileService.Client bizFileClient = (BizFileService.Client) bizFileClientFactory
					.getClient();
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 资料上传得到的信息集合
				map = FileUtil.processFileUpload(request, response,
						BusinessModule.MODUEL_BEFORELOAN, getUploadFilePath(),
						getFileSize(), getFileDateDirectory(),
						getUploadFileType());
				if ((Boolean) map.get("flag") == false) {
					logger.error("上传出错");
					tojson.put("uploadStatus", "文件上传失败or文件格式不合法");
				} else {
					// 拿到当前用户
					ShiroUser shiroUser = getShiroUser();
					int userId = shiroUser.getPid();
					bizFile.setUploadUserId(userId);
					@SuppressWarnings("rawtypes")
					List items = (List) map.get("items");
					for (int j = 0; j < items.size(); j++) {
						FileItem item = (FileItem) items.get(j);
						// 获取其他信息
						if (item.isFormField()) {
							if (item.getFieldName().equals("pid")) {
								conAcc.setPid(Integer
										.parseInt(ParseDocAndDocxUtil
												.getStringCode(item)));
							} else if (item.getFieldName().equals("fileName")) {
								conAcc.setFileName(ParseDocAndDocxUtil
										.getStringCode(item));
							}
						} else {
							if ("file2".equals(item.getFieldName())) {
								// 获取文件上传的信息
								bizFile.setUploadUserId(userId);
								fileSize = (int) item.getSize();
								bizFile.setFileSize(fileSize);
								String fileFullName = item.getName()
										.toLowerCase();
								int dotLocation = fileFullName.lastIndexOf(".");
								fileName = fileFullName.substring(0,
										dotLocation);
								fileType = fileFullName.substring(dotLocation)
										.substring(1);
								bizFile.setFileType(fileType);
								bizFile.setFileName(fileName);
							}
						}
					}
					String uploadDttm = DateUtil.format(new Date());
					bizFile.setUploadDttm(uploadDttm);
					String fileUrl = String.valueOf(map.get("path"));
					bizFile.setFileUrl(fileUrl);
					int uploadUserId = getShiroUser().getPid();
					bizFile.setUploadUserId(uploadUserId);
					bizFile.setStatus(1);
					if (fileSize == 0) {
						response.getWriter().write("fileSizeIsZero");
					}
					if (StringUtils.isEmpty(fileName)
							|| StringUtils.isEmpty(fileType)) {
						response.getWriter().write("fileIsEmpty");
					}
					// 保存文件信息
					int fileId = bizFileClient.saveBizFile(bizFile);
					// 获取文件Id
					conAcc.setFileId(fileId);
					// 保存资料表信息
					count = client.editAccessorie(conAcc);
					if (count == 0) {
						tojson.put("uploadStatus", "重新上传资料失败");
					} else {
						tojson.put("uploadStatus", "重新上传资料数据成功");
					}
				}
			}
		} catch (Exception e) {
			logger.error("重新上传合同附件出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				} finally {
					if (bizFileClientFactory != null) {
						try {
							c.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		outputJson(tojson, response);
	}

	/**
	 * 
	 * @Description: 获取合同所有附件
	 * @param request
	 * @param response
	 * @param contractId
	 * @param page
	 * @param rows
	 * @author: baogang
	 * @date: 2015年1月19日 下午5:42:58
	 */
	@RequestMapping(value = "pageAccesstList", method = RequestMethod.POST)
	public void pageAccesstList(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("pid") int contractId,
			@RequestParam("page") int page, @RequestParam("rows") int rows) {
		BaseClientFactory c = null;
		try {
			Contract contract = new Contract();
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			contract.setPid(contractId);
			contract.setPage(page);
			contract.setRows(rows);
			List<ContractAccessorie> list = client
					.pageFileAccessorieList(contract);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", client.pageAccessorieCount(contract));
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("删除合同附件出错" + e.getMessage());
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
	 * @Description: 预览合同
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2015年1月21日 上午9:20:00
	 */
	@RequestMapping(value = "reviewDocument", method = RequestMethod.POST)
	public void reviewDocument(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("url") String srcFile) {
		String htmlName = "";
		try {
			String realPath = CommonUtil.getRootPath();

			htmlName = DocxToHtml.reviewWord(realPath, realPath + srcFile);
		} catch (Exception e) {
			logger.error("文档预览失败" + e.getMessage());
		}
		outputJson(htmlName, response);
	}

	/**
	 * 
	 * @Description: 编辑合同时 获取合同的参数值
	 * @param request
	 * @param response
	 * @param srcFile
	 * @author: baogang
	 * @date: 2015年1月30日 下午5:01:25
	 */
	@RequestMapping(value = "editDocument", method = RequestMethod.POST)
	public void editDocument(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("contractUrl") String contractUrl,
			@RequestParam("tempLateId") int tempLateId,
			@RequestParam("contractId") int contractId) {
		BaseClientFactory c = null;
		try {
			c = getFactory(BusinessModule.MODUEL_CONTRACT, "ContractService");
			ContractService.Client client = (ContractService.Client) c
					.getClient();
			TempLateParmDto tempLateParmDto = new TempLateParmDto();
			tempLateParmDto.setContractTemplateId(tempLateId);
			tempLateParmDto.setContractId(contractId);
			List<TempLateParmDto> list = client
					.getAllTempParmValue(tempLateParmDto);
			outputJson(list, response);
		} catch (Exception e) {
			logger.error("编辑合同失败" + e.getMessage());
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

	@RequestMapping(value = "saveContract")
	public void saveContract(String content, Integer tempLateId,
			String contractName, String contractCatelog, Integer projectId,
			String contractTableContent, int contractParentId,
			@RequestParam(value = "refId", required = false) Integer refId,
			HttpServletResponse response, HttpServletRequest request) {
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_CONTRACT,
				"ContractService");
		Json j = super.getSuccessObj();
		try {
			ContractService.Client client = (ContractService.Client) c
					.getClient();

			// 服务器的路径
			String realPath = CommonUtil.getRootPath();

			// 根据模板ID获取模板
			ContractTempLate contractTempLate = client
					.getContractTempLateInfoById(tempLateId);
			List<TempLateParmDto> templateParamList = client
					.getTempParmListByTemplateId(tempLateId);

			if (contractTempLate != null && contractTempLate.getPid() > 0) {
				// 模板地址
				String templatePath = new StringBuffer().append(realPath)
						.append(contractTempLate.getTemplateUrl()).toString();

				// 服务器上的合同名称
				String realFileName = genFileName(contractTempLate
						.getTemplateUrl());

				// 合同的相对路径
				StringBuffer sb = new StringBuffer();
				sb.append(super.getUploadFilePath()).append("/");
				sb.append(BusinessModule.MODUEL_CONTRACT).append("/");
				sb.append(getFileDateDirectory());

				String relativelyPath = sb.toString();

				// 保存合同信息
				Contract contract = new Contract();
				contract.setParentId(contractParentId);
				contract.setContractTemplateId(tempLateId);// 合同模板ID
				contract.setContractName(contractName);// 合同名称
				contract.setContractCatelogKey(contractCatelog);// 合同类别
				contract.setProjectId(projectId);// 项目ID
				contract.setContractUrl(relativelyPath + "/" + realFileName);// 合同存放相对路径
				if (refId != null) {
					contract.setRefId(refId);// 引用Id
				}
				// 将字符串变成数组
				// Map:ID:VALUE。
				Map<Integer, String> parameterMap = new HashMap<Integer, String>();

				// Map:KEY:VALUE.
				Map<String, String> processedParameterMap = new HashMap<String, String>();
				if (content != null && !"".equals(content)) {
					String[] macth = content.substring(0,
							content.lastIndexOf("###")).split("###");
					for (int i = 0; i < macth.length; i++) {
						String[] value = macth[i].split(":");
						processedParameterMap.put(value[2], value[1]);
						parameterMap.put(Integer.parseInt(value[0]), value[1]);
					}
				}

				// 临时合同号
				contract.setTempContractNo(processedParameterMap
						.get("@CONTRACT_NUMBER@"));

				// 保存合同数据
				int contractId = client.insertContractInfo(contract,
						parameterMap);

				String folder = new StringBuffer().append(realPath).append("/")
						.append(relativelyPath).toString();
				File folderFile = new File(folder);
				if (!folderFile.exists()) {
					folderFile.mkdirs();
				}

				StringBuffer contractFilePathSb = new StringBuffer();
				contractFilePathSb.append(folder);// 目录
				contractFilePathSb.append("/");
				contractFilePathSb.append(realFileName);// 文件名

				List<ContractDynamicTableParameter> contractTabs = null;
				if (contractTableContent != null
						&& !"".equals(contractTableContent)) {
					URLDecoder.decode(contractTableContent, "utf-8");
					contractTabs = JSON
							.parseObject(
									contractTableContent,
									new TypeToken<List<ContractDynamicTableParameter>>() {
										private static final long serialVersionUID = -6822918771393132681L;
									}.getType());

					if (contractTabs != null && contractTabs.size() > 0) {
						for (int i = 0; i < contractTabs.size(); i++) {
							contractTabs.get(i).setContractId(contractId);
						}
					}
					// 新增合同表格参数信息
					client.addContractTableParam(contractTabs);

				}

				// 模板表格的标签
				String tableLabel = this.getContractTableLabel(tempLateId);
				// 生成合同文件
				contractGenerator.generate(this, templateParamList,
						templatePath, contractFilePathSb.toString(),
						processedParameterMap, contractTabs, tableLabel);
			}

			j.getHeader().put("flag", "success");
		} catch (Exception e) {
			j = this.getFailedObj("文档生成失败");
			// j.getHeader().put("flag", "error");
			if (logger.isDebugEnabled()) {
				logger.error("文档生成失败" + e.getMessage());
			}
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		outputJson(j, response);
	}

	/**
	 * 删除合同信息
	 */
	@RequestMapping(value = "deleteContracts")
	public void deleteContracts(String pids, HttpServletResponse response)
			throws Exception {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_CONTRACT, "ContractService");
		try {
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			client.deleteContracts(pids);
			outputJson("success", response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 存储编辑合同后的值
	 * @param request
	 * @param response
	 * @param content
	 * @param contractId
	 * @author: baogang
	 * @date: 2015年2月2日 下午3:08:27
	 */
	@RequestMapping(value = "saveEditDocument", method = RequestMethod.POST)
	public void saveEditDocument(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("content") String content,
			@RequestParam("contractId") int contractId,
			@RequestParam("tempLateId") int tempLateId,
			@RequestParam(value = "contractName", required = false) String contractName,
			@RequestParam("contractUrl") String contractFilePath,
			@RequestParam(value = "contractTableContent", required = false) String contractTableContent) {
		BaseClientFactory contractServiceFactory = null;
		try {
			contractServiceFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) contractServiceFactory
					.getClient();
			String realPath = CommonUtil.getRootPath();

			// Map:KEY:VALUE.
			Map<String, String> processedParameterMap = new HashMap<String, String>();
			List<ContractParameter> contractParameterList = new ArrayList<ContractParameter>();

			// 将字符串变成数组
			String[] macth = content.substring(0, content.lastIndexOf("###"))
					.split("###");
			for (int i = 0; i < macth.length; i++) {

				String[] value = macth[i].split(":");

				processedParameterMap.put(value[2], value[1]);

				ContractParameter contractParameter = new ContractParameter();
				contractParameter.setContractId(contractId);
				contractParameter.setParameterId(Integer.parseInt(value[0]));
				contractParameter.setParameterVal(value[1]);
				contractParameter.setMatchFlag(value[2]);

				contractParameterList.add(contractParameter);
			}

			// 修改合同名称
			Contract c = new Contract();
			c.setPid(contractId);
			c.setContractName(contractName);

			client.updateContractParameter(contractParameterList, c);

			// 根据模板ID获取模板地址
			ContractTempLate contractTempLate = client
					.getContractTempLateInfoById(tempLateId);
			List<TempLateParmDto> templateParamList = client
					.getTempParmListByTemplateId(tempLateId);

			String templatePath = realPath + contractTempLate.getTemplateUrl();
			contractFilePath = realPath + contractFilePath;

			// 表格处理
			List<ContractDynamicTableParameter> contractTabs = null;
			if (contractTableContent != null
					&& !"".equals(contractTableContent)) {
				URLDecoder.decode(contractTableContent, "utf-8");
				contractTabs = JSON.parseObject(contractTableContent,
						new TypeToken<List<ContractDynamicTableParameter>>() {
							private static final long serialVersionUID = -6822918771393132681L;
						}.getType());

				// 修改合同表格参数信息
				client.updateContractTableParam(contractTabs);

			}

			// 表格标签
			String tableLabel = this.getContractTableLabel(tempLateId);
			contractGenerator.generate(this, templateParamList, templatePath,
					contractFilePath, processedParameterMap, contractTabs,
					tableLabel);

			outputJson(true, response);
		} catch (Exception e) {
			logger.error("编辑合同失败" + e.getMessage());
			outputJson(false, response);
		} finally {
			if (contractServiceFactory != null) {
				try {
					contractServiceFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 合同修改 表格加载
	 * 
	 * @param
	 * @param
	 */
	@RequestMapping(value = "/listContractTabUrl")
	@ResponseBody
	public void listContractTabUrl(int contractId, HttpServletResponse response) {
		List<ContractDynamicTableParameter> list = null;
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_CONTRACT, "ContractService");
		try {

			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			list = client.getContractTabs(contractId);
		} catch (Exception e) {
			logger.error("查询合同表格失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(list, response);
	}

	/**
	 * @Description: 查询合同模版数据
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author: Simon.Hoo
	 * @date: 2015年4月11日 下午3:29:17
	 */
	private List<TempLateParmDto> getContractParmList(
			Map<String, String> paramMap) throws Exception {
		List<TempLateParmDto> list = new ArrayList<TempLateParmDto>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			list = client.getTempLateParmList(paramMap);

			for (TempLateParmDto dto : list) {
				if (dto.getFixedVal() != null && !"".equals(dto.getFixedVal())) {
					if (dto.getFixedVal().startsWith(".")) {
						dto.setFixedVal("0" + dto.getFixedVal());
					}
					String matchValue = contractGenerator.convertVal(
							dto.getFixedVal(), dto.getValConvertFlag());
					dto.setFixedVal(matchValue);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询合同模版数据:" + e.getMessage());
			}
			throw e;
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * @Description: 根据合同模版ID查询合同所需参数
	 * @param tempLateId
	 *            合同模板ID
	 * @param debtorProjectId
	 * @param creditProjectId
	 * @param response
	 * @author: Simon.Hoo
	 * @date: 2015年4月11日 下午3:24:28
	 */
	@RequestMapping(value = "getContractDynamicParmList")
	public void getContractDynamicParmList(
			String contractType,
			Integer tempLateId,
			Integer debtorProjectId,
			Integer creditProjectId,
			Integer projectId,
			Integer mortgagorId,
			Integer suretyId,
			Integer exdProjectId,
			String commonDebtorPids,
			@RequestParam(value = "rateChgId", required = false) Integer rateChgId,
			HttpServletResponse response) {
		List<TempLateParmDto> list = null;
		try {

			if (!StringUtil.isEmpty(contractType)) {

				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("tempLateId", String.valueOf(tempLateId));

				if (Constants.CONTRACT_CATELOG_DK
						.equalsIgnoreCase(contractType)) {
					paramMap.put("debtorProjectId",
							String.valueOf(debtorProjectId));
					paramMap.put("creditProjectId",
							String.valueOf(creditProjectId));
					paramMap.put("commonDebtorPids", commonDebtorPids);
				} else if (Constants.CONTRACT_CATELOG_SX
						.equalsIgnoreCase(contractType)) {
					paramMap.put("debtorProjectId",
							String.valueOf(debtorProjectId));
					paramMap.put("creditProjectId",
							String.valueOf(creditProjectId));
					paramMap.put("commonDebtorPids", commonDebtorPids);
				} else if (Constants.CONTRACT_CATELOG_JJ
						.equalsIgnoreCase(contractType)) {
					paramMap.put("debtorProjectId",
							String.valueOf(debtorProjectId));
					paramMap.put("creditProjectId",
							String.valueOf(creditProjectId));
					paramMap.put("commonDebtorPids", commonDebtorPids);
				} else if (Constants.CONTRACT_CATELOG_DY
						.equalsIgnoreCase(contractType)) {
					paramMap.put("projectId", String.valueOf(projectId));
					paramMap.put("mortgagorId", String.valueOf(mortgagorId));
				} else if (Constants.CONTRACT_CATELOG_ZY
						.equalsIgnoreCase(contractType)) {
					paramMap.put("projectId", String.valueOf(projectId));
					paramMap.put("mortgagorId", String.valueOf(mortgagorId));
				} else if (Constants.CONTRACT_CATELOG_BZ
						.equalsIgnoreCase(contractType)) {
					paramMap.put("projectId", String.valueOf(projectId));
					paramMap.put("suretyId", String.valueOf(suretyId));
				} else if (Constants.CONTRACT_CATELOG_ZK
						.equalsIgnoreCase(contractType)) {
					paramMap.put("projectId", String.valueOf(projectId));
					paramMap.put("exdProjectId", String.valueOf(exdProjectId));
					paramMap.put("suretyId", String.valueOf(suretyId));
					paramMap.put("commonDebtorPids", commonDebtorPids);
				} else if (Constants.CONTRACT_CATELOG_ZKBZ
						.equalsIgnoreCase(contractType)) {
					paramMap.put("projectId", String.valueOf(projectId));
					paramMap.put("exdProjectId", String.valueOf(exdProjectId));
					paramMap.put("suretyId", String.valueOf(suretyId));
					paramMap.put("commonDebtorPids", commonDebtorPids);
				} else if (Constants.CONTRACT_CATELOG_LLBG
						.equalsIgnoreCase(contractType)) {
					paramMap.put("projectId", String.valueOf(projectId));
					paramMap.put("rateChgId", String.valueOf(rateChgId));
					paramMap.put("commonDebtorPids", commonDebtorPids);
				}

				list = getContractParmList(paramMap);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询借款类合同模版数据:" + e.getMessage());
			}
		}
		// 输出
		outputJson(list, response);
	}

	/**
	 * @Description: 根据合同模版ID查询合同表格所需参数
	 * @param projectId
	 * @param tempLateId
	 * @param response
	 * @author: Simon.Hoo
	 * @date: 2015年4月13日 下午9:41:35
	 */
	@RequestMapping(value = "getContractTableDynamicParmList")
	public void getContractTableDynamicParmList(Integer projectId,
			Integer tempLateId, Integer mortgagorType,
			HttpServletResponse response) {
		List<ContractDynamicTableParameter> list = null;

		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("tempLateId", String.valueOf(tempLateId));
			paramMap.put("projectId", String.valueOf(projectId));
			paramMap.put("mortgagorType", String.valueOf(mortgagorType));

			list = client.getTempLateTableParmList(paramMap);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询合同模版表格数据:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		// 输出
		outputJson(list, response);
	}

	/**
	 * 
	 * @Description: 根据项目ID查询合同信息
	 * @param projectId
	 *            项目ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: huxinlong
	 * @date: 2015年4月19日 下午3:36:12
	 */
	@RequestMapping(value = "getAllContractListByProjectId")
	public void getAllContractListByProjectId(Integer projectId,
			HttpServletResponse response) {
		List<Contract> list = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractNumberService");
			ContractNumberService.Client client = (ContractNumberService.Client) clientFactory
					.getClient();
			list = client.getAllContractListByProjectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("根据项目ID查询合同信息:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(list, response);
	}

	/**
	 * 
	 * @Description: 生成合同编号
	 * @param contractId
	 *            合同ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: huxinlong
	 * @date: 2015年5月3日 下午9:05:24
	 */
	@RequestMapping(value = "genContractNumber")
	public void genContractNumber(String contractIds,
			HttpServletRequest request, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		BaseClientFactory clientFactory2 = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractNumberService");
			ContractNumberService.Client client = (ContractNumberService.Client) clientFactory
					.getClient();

			clientFactory2 = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client contractClient = (ContractService.Client) clientFactory2
					.getClient();
			int rows = client.genContractNumber(contractIds);
			if (rows != 0) {

				String[] ids = contractIds.split(",");
				for (String id : ids) {
					Contract c = contractClient.getContractByContractId(Integer
							.parseInt(id));
					String tempContractNo = c.getTempContractNo();
					String contractNo = c.getContractNo();

					// 查询 该合同 是否 为 授信或借款合同，是则改变（合同参数表）引用该合同的合同编号，返回相关联的合同
					List<Contract> contracts = contractClient
							.getContractGenerateNumber(Integer.parseInt(id));
					if (contracts == null) {
						contracts = Lists.newArrayList();
					} else {
						if (contracts.size() == 1
								&& contracts.get(0).getPid() == 0) {
							contracts = Lists.newArrayList();
						}
					}
					contracts.add(c);

					for (Contract contract : contracts) {
						if (tempContractNo != null
								&& !"".equals(tempContractNo)) {
							String realPath = CommonUtil.getRootPath();

							// 服务器上的合同名称
							String realFileName = genFileName(contract
									.getContractUrl());

							// 合同的相对路径
							StringBuffer sb = new StringBuffer();
							sb.append(super.getUploadFilePath()).append("/");
							sb.append(BusinessModule.MODUEL_CONTRACT).append(
									"/");
							sb.append(getFileDateDirectory());

							String relativelyPath = sb.toString();

							// 新创建合同文件地址
							String contractFilePathSb = contractGenerator
									.getFilePath(relativelyPath, realFileName,
											realPath);

							// 原合同文件路径
							String templateFile = realPath
									+ contract.getContractUrl();

							Map<String, String> map = Maps.newHashMap();
							map.put(tempContractNo, contractNo);
							contractGenerator.generateContractNo(templateFile,
									contractFilePathSb, map);

							contract.setContractUrl(relativelyPath + "/"
									+ realFileName);// 合同存放相对路径
						}
						// 修改合同文件地址
						if (c.getPid() == contract.getPid()) {
							contract.setIsSigned(1);
							contract.setIsLegalConfirmation(1);
							contract.setSignedDate(DateUtil.format(new Date(),
									"yyyy-MM-dd"));
						}
						contractClient.updateContractUrlOrName(contract);
					}
				}

				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_CONTRACT,
						SysLogTypeConstant.LOG_TYPE_UPDATE, "生成合同编号");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "生成合同编号成功!");

			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "生成合同编号失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("生成合同编号:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (clientFactory2 != null) {
				try {
					clientFactory2.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}

		}
		// 输出
		outputJson(j, response);
	}

	/**
	 * 父合同下拉框
	 * 
	 * @param response
	 */
	@RequestMapping(value = "getParentContracts")
	@ResponseBody
	public void getParentContracts(
			@RequestParam(value = "projectId", required = false) int projectId,
			@RequestParam(value = "isParent", required = false) Integer isParent,
			HttpServletResponse response) {
		List<Contract> list = new ArrayList<Contract>();
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_CONTRACT, "ContractService");

		try {
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			Contract contract = new Contract();
			contract.setPid(0);
			contract.setContractName("--请选择--");
			list.add(contract);
			if (null != isParent && isParent == 1) {
				list.addAll(client.getParentContracts(projectId));
			}
		} catch (Exception e) {
			logger.error("查询父合同下拉列表失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(list, response);
	}

	/**
	 * 获取项目的合同数量
	 */
	@RequestMapping(value = "/getContractCount")
	public void getContractCount(Integer projectId, Integer templateId,
			Integer contractType, HttpServletResponse response)
			throws Exception {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_CONTRACT, "ContractService");
		int count = 0;
		try {
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			Map<String, Integer> map = Maps.newHashMap();
			map.put("projectId", projectId);
			map.put("templateId", templateId);

			switch (contractType) {
			case 1:
				count = client.getCreditContractCount(map);
				break;
			case 2:
				count = client.getJkContractCount(map);
				break;
			case 3:
				count = client.getZkContractCount(map);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			logger.error("获取项目合同数量失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Json j = super.getSuccessObj();
		j.getHeader().put("count", count);
		outputJson(j, response);
	}

	/**
	 * 获取子合同名称
	 */
	@RequestMapping(value = "/getChildContractName")
	public void getChildContractName(Integer contractId,
			HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_CONTRACT, "ContractService");
		String contractName = "";
		try {
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			contractName = client.getChildContract(contractId);

		} catch (Exception e) {
			logger.error("查询子合同名称失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		Json j = super.getSuccessObj();
		j.getHeader().put("contractName", contractName);
		outputJson(j, response);
	}

	@RequestMapping(value = "/getContractAttachment")
	public void getContractAttachment(int pid, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		ContractAttachment ca = new ContractAttachment();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			ca = client.getContractAttachment(pid);
		} catch (Exception e) {
			logger.error("查询合同附件信息失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		outputJson(ca, response);
	}

	@RequestMapping(value = "/addContractAttachment")
	public void addContractAttachment(ContractAttachment contractAttachment,
			HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int count = 0;
		ContractAttachment ca = new ContractAttachment();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			ca = client.getContractAttachment(contractAttachment
					.getContractId());
			if (ca.getPid() == 0) {
				count = client.addContractAttachment(contractAttachment);
			} else {
				count = client.editContractAttachment(contractAttachment);
			}
		} catch (Exception e) {
			logger.error("新增合同附件信息失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		outputJson(count, response);
	}

	@RequestMapping(value = "/editAccessDesc")
	public void editAccessDesc(ContractAccessorie contractAccessorie,
			HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		int count = 0;
		Map<String, String> tojson = new HashMap<String, String>();
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			count = client.editAccessorie(contractAccessorie);
			if (count == 0) {
				tojson.put("uploadStatus", "上传资料失败");
			} else {
				tojson.put("uploadStatus", "上传资料数据成功");
			}
		} catch (Exception e) {
			logger.error("编辑合同资料信息失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		outputJson(tojson, response);
	}

	/**
	 * 获取合同模板中的标签
	 * 
	 * @param tempLateId
	 * @param response
	 * @author: liangyanjun
	 * @date: 2015年6月3日 上午9:52:40
	 */
	public String getContractTableLabel(int tempLateId) {
		BaseClientFactory clientFactory = null;
		// 模板中的表格标签
		String tableLabel = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) clientFactory
					.getClient();
			List<TempLateParmDto> tempLateParmDtoList = client
					.getTempParmListByTemplateId(tempLateId);

			tableLabel = contractGenerator
					.getContractTableLabel(tempLateParmDtoList);
		} catch (Exception e) {
			logger.error("查询合同模板中的表格标签失败" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		return tableLabel;
	}

	/**
	 * 获取合同模板中的标签
	 * 
	 * @param tempLateId
	 * @param response
	 * @author: liangyanjun
	 * @date: 2015年6月3日 上午9:52:40
	 */
	@RequestMapping(value = "/getContractTableLabel")
	public void ajaxContractTableLabel(int tempLateId,
			HttpServletResponse response) {
		// 模板中的表格标签
		String tableLabel = this.getContractTableLabel(tempLateId);
		outputJson(tableLabel, response);
	}

	/**
	 * 获取合同模板中的表头
	 * 
	 * @param tableLabel
	 *            表格的标签
	 * @param response
	 * @author: liangyanjun
	 * @date: 2015年6月3日 上午9:53:11
	 */
	@RequestMapping(value = "/getContractTableTitle")
	public void getContractTableTitle(String tableLabel,
			HttpServletResponse response) {
		List<String> contractTableList = contractGenerator
				.getContractTableTitle(this, tableLabel);
		outputJson(contractTableList, response);
	}

	/**
	 * 获取合同表格中展现的行数据
	 * 
	 * @param mortgageGuaranteeType
	 *            担保类型
	 * @param projectId
	 *            项目ID
	 * @author: liangyanjun
	 * @date: 2015年6月1日 下午8:29:53
	 */
	public List<Map<String, String>> getContractTableRows(String tableLabel,
			int mortgageGuaranteeType, int projectId, int assBaseIds) {
		// 查询指定担保类型的所有记录
		BaseClientFactory clientFactory = null;
		// 表格中的数据
		List<Map<String, String>> resultMap = new ArrayList<Map<String, String>>();

		try {
			clientFactory = getFactory(BusinessModule.MODUEL_MORTGAGE,
					"ProjectAssBaseService");
			ProjectAssBaseService.Client client = (ProjectAssBaseService.Client) clientFactory
					.getClient();
			// 页面只能选择一个附件
			// List<ProjectAssBase> list =
			// client.getProjectAssBaseByMortgageGuaranteeType(mortgageGuaranteeType,String.valueOf(projectId));
			List<ProjectAssBase> list = client
					.getCommonProjectAssBaseByPid(assBaseIds);

			// 担保对应的详情信息
			Map<Integer, List<ProjectAssDtl>> assDtlMap = new HashMap<Integer, List<ProjectAssDtl>>();
			if (null != list) {
				for (ProjectAssBase base : list) {
					assDtlMap.put(base.getPid(),
							client.getProjectAssDtlByBaseId(base.getPid()));
				}
			}

			resultMap = contractGenerator.getContractTableRowsDate(this,
					tableLabel, list, assDtlMap);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询抵质押物列表:" + e.getMessage());
			}
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

		return resultMap;
	}

	/**
	 * 获取合同表格中展现的行数据
	 * 
	 * @param mortgageGuaranteeType
	 *            担保类型
	 * @param projectId
	 *            项目ID
	 * @author: liangyanjun
	 * @date: 2015年6月1日 下午8:29:53
	 */
	@RequestMapping(value = "/getContractTableRows")
	public void ajaxGetContractTableRows(String tableLabel,
			int mortgageGuaranteeType, int projectId, int assBaseIds,
			HttpServletResponse response) {
		// 表格中的数据
		List<Map<String, String>> resultMapList = this.getContractTableRows(
				tableLabel, mortgageGuaranteeType, projectId, assBaseIds);
		// 转成json格式
		JSONArray array = new JSONArray();
		for (Map<String, String> resultMap : resultMapList) {
			// 行数据
			JSONObject rowdate = new JSONObject();

			Iterator<String> itor = resultMap.keySet().iterator();
			while (itor.hasNext()) {
				String colName = itor.next();
				rowdate.put(colName, resultMap.get(colName));
			}

			array.add(rowdate);
		}
		outputJson(array, response);
	}

	/**
	 * 修改合同
	 */
	@RequestMapping(value = "/editContractData")
	public String editContractData(
			@RequestParam(value = "contractId", required = false) Integer contractId,
			@RequestParam(value = "projectId", required = false) Integer projectId,
			@RequestParam(value = "contractType", required = false) String contractType,
			@RequestParam(value = "tempId", required = false) Integer tempId,
			@RequestParam(value = "contractName", required = false) String contractName,
			ModelMap model) throws Exception {
		if (contractName != null) {
			contractName = new String(contractName.getBytes("ISO8859-1"),
					"UTF-8");
		}
		model.put("contractId", contractId);
		model.put("contractType", contractType);
		model.put("tempId", tempId);
		model.put("projectId", projectId);
		model.put("contractName", contractName);
		return "contract/edit_contract";
	}

	@RequestMapping(value = "saveContractData", method = RequestMethod.POST)
	public void saveContractData(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("content") String content,
			@RequestParam("contractId") int contractId,
			@RequestParam("tempLateId") int tempLateId) {
		BaseClientFactory contractServiceFactory = null;
		try {
			contractServiceFactory = getFactory(BusinessModule.MODUEL_CONTRACT,
					"ContractService");
			ContractService.Client client = (ContractService.Client) contractServiceFactory
					.getClient();

			// Map:KEY:VALUE.
			List<ContractParameter> contractParameterList = new ArrayList<ContractParameter>();

			// 将字符串变成数组
			String[] macth = content.substring(0, content.lastIndexOf("###"))
					.split("###");
			for (int i = 0; i < macth.length; i++) {

				String[] value = macth[i].split(":");

				ContractParameter contractParameter = new ContractParameter();
				contractParameter.setContractId(contractId);
				contractParameter.setParameterId(Integer.parseInt(value[0]));
				contractParameter.setParameterVal(value[1]);
				contractParameter.setMatchFlag(value[2]);

				contractParameterList.add(contractParameter);
			}

			client.updateContractParameterData(contractParameterList,
					contractId);

			outputJson(true, response);
		} catch (Exception e) {
			logger.error("编辑合同失败" + e.getMessage());
			outputJson(false, response);
		} finally {
			if (contractServiceFactory != null) {
				try {
					contractServiceFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
