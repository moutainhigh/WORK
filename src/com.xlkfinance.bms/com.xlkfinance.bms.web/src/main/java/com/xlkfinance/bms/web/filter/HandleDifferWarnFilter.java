package com.xlkfinance.bms.web.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xlkfinance.bms.common.constant.Constants;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年2月18日上午10:12:57 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：检查未处理预警事项过滤器 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class HandleDifferWarnFilter implements Filter {
   //不拦截的url
   ArrayList<String> notfilterUrlList = new ArrayList<>();
   {
      notfilterUrlList.add("logout.action");
      notfilterUrlList.add("login.action");
      notfilterUrlList.add("checkUserLogin.action");
      notfilterUrlList.add("queryMenuTree.action");
      notfilterUrlList.add("searchTaskList.action");
      notfilterUrlList.add("findIndexHandleDifferWarn.action");
      notfilterUrlList.add("findSysMetroUiPage.action");
      notfilterUrlList.add("getTransactionIndexUrl.action");
      notfilterUrlList.add("selectMetroUiByUserName.action");
      notfilterUrlList.add("submitWarnRemark.action");
      notfilterUrlList.add("approval-res.action");
      notfilterUrlList.add("loan-voucher-confirm.action");
      notfilterUrlList.add("loan-confirm.action");
   }

   @Override
   public void init(FilterConfig config) throws ServletException {
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      String servletPath = httpServletRequest.getServletPath();
      for (String string : notfilterUrlList) {
         if (servletPath.endsWith(string)) {
            chain.doFilter(request, response);
            return;
         }
      }
      //获取未处理预警事项的数量
      HttpSession session = httpServletRequest.getSession(false);
      if (session==null) {
         chain.doFilter(request, response);
         return;
      }
      Object notHandleWarnCountObject = session.getAttribute(Constants.NOT_HANDLE_WARN_COUNT);
      int notHandleWarnCount = 0;
      if (notHandleWarnCountObject != null) {
         notHandleWarnCount = (int) notHandleWarnCountObject;
      }
      // 未处理预警事项的数量,该值大于0，则不允许点击其他功能
      if (servletPath.endsWith(".action") && notHandleWarnCount > 0) {
         response.setContentType("text/html;charset=UTF-8");
         response.getWriter().write("<script type='text/javascript'>alert('请完成未处理预警事项！')</script>");
         return;
      }
      chain.doFilter(request, response);
   }

   @Override
   public void destroy() {

   }

}
