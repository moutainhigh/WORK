/**
 * @Title: ProductController.java
 * @Package com.xlkfinance.bms.web.controller.product
 * @Description: 
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年1月14日 下午5:23:45
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.product.ProductService.Client;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
/**
 * 产品管理controller
  * @ClassName: ProductController
  * @Description: 
  * @author: Administrator
  * @date: 2016年1月14日 下午5:25:38
 */
@Controller
@RequestMapping("/productController")
public class ProductController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	private ProductService.Client client;
	
	private ProductService.Client getService() {
	      BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
	      try {
	         client = (ProductService.Client) clientFactory.getClient();
	      } catch (ThriftException e) {
	         logger.error("ProductService获取失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
	      }
	      return client;
	   }
	
	/**
	 * 	产品列表查询
	  * @Description: TODO
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月14日 下午5:54:05
	 */
	@RequestMapping(value = "/index")
	public String toIndex(){
		return "product/index";
	}
	
	/**
	 * 产品列表查询
	  * @Description: TODO
	  * @param product
	  * @param response
	  * @author: Administrator
	  * @date: 2016年1月14日 下午6:36:33
	 */
	@RequestMapping(value = "/getProductListUrl")
	public void getProductListUrl(Product product, HttpServletRequest request, HttpServletResponse response){
		//添加数据权限
		//product.setUserIds(getUserIds(request));
		logger.info("产品管理查询列表，入参：" + product);
		Map<String, Object> map = new HashMap<String, Object>();
		List<GridViewDTO> productList = null;
		int total = 0;
		try {
			Client service = getService();
			productList = service.getAllProduct(product);
			total = service.getAllProductCount(product);
			logger.info("产品查询列表查询成功：total：" + total + ",productList:" + productList);
		} catch (Exception e) {
			logger.error("获取产品列表失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
		map.put("rows", productList);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 	跳转到新增或编辑产品详情
	  * @Description: TODO
	  * @author: Administrator
	  * @date: 2016年1月14日 下午7:13:51
	 */
	@RequestMapping(value = "/editProduct")
	public String editProduct(@RequestParam(value = "productId", required = false) Integer productId, HttpServletResponse response, ModelMap model){
		Product product = new Product();
		try {
			if(productId != null && productId>0){
				Client service = getService();
				product = service.getProductById(productId);
			}
		} catch (Exception e) {
			logger.error("获取产品详情失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
		model.put("product", product);
		return "product/edit_product";
	}
	
	/**
	 * 保存产品信息
	  * @Description: TODO
	  * @author: Administrator
	  * @date: 2016年1月14日 下午7:19:57
	 */
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public void saveProduct(Product product,HttpServletRequest request,HttpServletResponse response){
		Json j = super.getSuccessObj();
		int pid = 0;
		String productNum = product.getProductNumber();
		List<GridViewDTO> productList = null;
		List<String> productNumList = new ArrayList<String>();
		try {
			Client service = getService();
			pid = product.getPid();
			Product product1 = new Product();
			product1.setPage(1);
			product1.setRows(15);
			//查询所有产品
			productList = service.getAllProduct(product1);
			if(productList != null && productList.size() > 0){
				for(int i=0;i<productList.size();i++){
					productNumList.add(productList.get(i).getValue1());
				}
			}
			//更新或者查询是不是有重复的产品编码
			if(productNumList.contains(productNum)){
				//新增做编码唯一性校验，修改为不可编辑
				if(pid==0){
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "保存失败,已存在该产品编码，请重新录入!");
					// 输出
					outputJson(j, response);
					return;
				}
			}
			if(pid>0){//修改产品
				product.setUpdateDate(DateUtil.format(new Date()));
				int rows =service.updateProduct(product);
				if(rows != 0){
					j.getHeader().put("success", true);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改产品信息,产品编号:" + product.getProductNumber());
				}else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "修改失败,请重新操作!");
				}
			}else{//新增产品信息
				//设置当前登录者为产品的创建人
				product.setCreaterId(getShiroUser().getPid());
				product.setStatus(1);//产品有效
				// 获取系统时间,并设置
				Timestamp time = new Timestamp(new Date().getTime());
				product.setUpdateDate(time.toString());
				product.setCreateDate(time.toString());
				pid =service.addProduct(product);
				if(pid >0){
					j.getHeader().put("success", true);
					// 成功的话,就做业务日志记录
					recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_ADD, "新增产品信息,产品编号:" + product.getProductNumber());
				}else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "新增失败,请重新操作!");
				}
			}
			
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.debug("新增产品信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}
		j.getHeader().put("pid", pid);
		outputJson(j, response);
	}
	
	/**
	 * 	修改产品状态
	  * @Description: TODO
	  * @param productId
	  * @param status
	  * @param response
	  * @author: Administrator
	  * @date: 2016年1月15日 上午10:09:59
	 */
	@RequestMapping(value = "/updateProductStatus")
	public String updateProductStatus(Integer productId,Integer status, HttpServletResponse response){
		Json j = super.getSuccessObj();
		try {
			Product product = new Product();
			product.setPid(productId);
			product.setStatus(status);
			product.setUpdateDate(DateUtil.format(new Date()));
			Client service = getService();
			int rows =service.updateProduct(product);
			if(rows != 0){
				j.getHeader().put("success", true);
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_SYSTEM, SysLogTypeConstant.LOG_TYPE_UPDATE, "修改产品状态,产品ID:" + productId);
			}else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "修改失败,请重新操作!");
			}
			
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("修改产品信息:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作!");
		}
		
		outputJson(j, response);
		return "product/index";
	}
	
	/**
	 * 	查询所有的二级机构
	  * @Description: TODO
	  * @author: Administrator
	  * @date: 2016年1月18日 下午4:29:06
	 */
	@RequestMapping(value = "/getCityLists")
	public void getCityLists(HttpServletResponse response){
		List<SysOrgInfo> resultList = new ArrayList<SysOrgInfo>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		try {
			SysOrgInfoService.Client service = (SysOrgInfoService.Client) clientFactory.getClient();
			SysOrgInfo sysOrgInfo = new SysOrgInfo();
			sysOrgInfo.setId(-1);
			sysOrgInfo.setName("--请选择--");
			sysOrgInfo.setCategory(1);
			resultList.add(sysOrgInfo);
			//查询条件
			SysOrgInfo orgInfo = new SysOrgInfo();
			orgInfo.setCategory(1);//业务部门
			orgInfo.setLayer(2);//第二层
			//查询出所有的城市列表
			List<SysOrgInfo> list =service.listSysOrgByLayer(orgInfo);
			
			resultList.addAll(list);
		} catch (Exception e) {
			logger.error("获取机构列表失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}
		// 输出
		outputJson(resultList, response);
	}
	
	/**
	 * 通过用户的部门以及需要查询的部门层次，查询部门
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月28日 下午2:37:58
	 */
	public List<Integer> getCityLists(){
		List<Integer> cityList = new ArrayList<Integer>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		BaseClientFactory orgClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserOrgInfoService");
		try {
			//根据用户ID查出其所属城市ID
			SysOrgInfoService.Client orgService = (SysOrgInfoService.Client) clientFactory.getClient();
			SysUserOrgInfoService.Client userOrgService = (SysUserOrgInfoService.Client) orgClientFactory.getClient();
			
			SysOrgInfo sysOrgInfo = new SysOrgInfo();
			sysOrgInfo.setLayer(2);//城市或者二级部门
			SysUser sysUser = getSysUser();
			sysOrgInfo.setId(sysUser.getOrgId());//用户所在部门ID
			//通过用户的部门以及需要查询的部门层次，查询部门
			List<SysOrgInfo> orgList = orgService.listSysParentOrg(sysOrgInfo);
			if(orgList != null){
				for(SysOrgInfo org : orgList){
					cityList.add(org.getId());
				}
			}
			//根据登录用户的ID查询数据权限列表
			List<SysUserOrgInfo> listUserOrgInfo = userOrgService.listUserOrgInfo(sysUser.getPid());
			if(listUserOrgInfo != null && listUserOrgInfo.size()>0){
				for(SysUserOrgInfo orgInfo : listUserOrgInfo){
					cityList.add(orgInfo.getOrgId());
				}
			}
			
			
		} catch (Exception e) {
			logger.error("获取二级机构列表失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
		}
		return cityList;
	}
	
	/**
	 * 产品列表查询,用于贷款申请时的下拉选择
	  * @Description: TODO
	  * @param product
	  * @param response
	  * @author: Administrator
	  * @date: 2016年1月14日 下午6:36:33
	 */
	@RequestMapping(value = "/getProductLists")
	public void getProductLists(Integer productType, HttpServletResponse response){
		List<Product> resultList = new ArrayList<Product>();
		Product product = new Product();
		product.setPid(-1);
		product.setProductName("--请选择--");
		resultList.add(product);
		try {
			Client service = getService();
			//查询条件
			Product productDTO = new Product();
			//productDTO.setCityId(2);//暂时使用深圳的ID
			if (productType != null) {
				productDTO.setProductType(productType);
			}
			productDTO.setCityIds(getCityLists());//设置城市的ID
			productDTO.setStatus(1);//有效的产品
			resultList.addAll(service.getAllProductList(productDTO));
		} catch (Exception e) {
			logger.error("获取产品列表失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
		outputJson(resultList, response);
	}
	
	/**
	 * 产品列表查询,用于贷款详情页面的下拉
	  * @Description: TODO
	  * @param product
	  * @param response
	  * @author: Administrator
	  * @date: 2016年1月14日 下午6:36:33
	 */
	@RequestMapping(value = "/getAllProductList")
	public void getAllProductList(HttpServletResponse response){
		List<Product> resultList = new ArrayList<Product>();
		Product product = new Product();
		product.setPid(-1);
		product.setProductName("--请选择--");
		resultList.add(product);
		try {
			Client service = getService();
			//查询条件
			Product productDTO = new Product();
			productDTO.setStatus(1);//有效的产品
			resultList.addAll(service.getAllProductList(productDTO));
		} catch (Exception e) {
			logger.error("产品列表查询,用于贷款详情页面的下拉失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
		outputJson(resultList, response);
	}
	
	/**
	 * 查询产品类型（信用？赎楼）
	  * 
	  * @author: Administrator
	  * @date: 2016年1月26日 下午3:19:38
	 */
	@RequestMapping(value = "/getProductType")
	public void getProductType(Integer productId,HttpServletResponse response){
		int productType = 0;
		String loanWorkProcess = "";
		String productNumber = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(productId != null && productId>0){
				Client service = getService();
				Product product = service.getProductById(productId);
				productType = product.getProductType();
				loanWorkProcess = product.getLoanWorkProcessId();
				productNumber = product.getProductNumber();
			}
		} catch (Exception e) {
			logger.error("获取产品类型失败：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
		}
		map.put("productType", productType);
		map.put("loanWorkProcess", loanWorkProcess);
		map.put("productNumber", productNumber);
		outputJson(map, response);
	}
	
	/**
	 * 根据项目Id查询产品编号
	  * 
	  * @author: jony
	  * @date: 2017年7月10日 下午3:19:38
	 */
	@RequestMapping(value = "/getProductNumber")
	@ResponseBody
	public String getProductNumber(Integer projectId){
		Client service = getService();
		Product product = new Product();
		try {
			product = service.findProductByProjectId(projectId);
		} catch (TException e) {
			e.printStackTrace();
		}
		return product.getProductNumber() ;
		
	}
}
