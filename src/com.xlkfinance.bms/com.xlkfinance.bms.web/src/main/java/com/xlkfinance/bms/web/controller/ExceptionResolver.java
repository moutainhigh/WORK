package com.xlkfinance.bms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年10月28日上午10:08:05 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 全局异常处理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    /** 全局异常处理方法 */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        logger.error(ExceptionUtil.getExceptionMessage(e), e);
        String requestType = request.getHeader("X-Requested-With");//当以ajax的方式请求时，该值为“XMLHttpRequest”
        String shiroFlag = request.getParameter("SHIRO_FLAG");//shiro标识，该值为“1”，该值用户判断提示
        String requestURI = request.getRequestURI();
        //1.权限异常
        if (e instanceof UnauthorizedException) {
            if ("XMLHttpRequest".equalsIgnoreCase(requestType)||"1".equals(shiroFlag)||requestURI.contains("mobileApi")) {
                BaseController.fillReturnJson(response, false, "对不起,您无该权限!");
                return new ModelAndView();
            }else if(requestURI.contains("mobileApi")){
                BaseController.returnJsonOfMobile(response, false, "对不起,您无该权限!", ExceptionCode.MOBILE_AUTHENTICATION_FAILURE);
                return new ModelAndView();
            }
            return new ModelAndView("redirect:/sysUserController/toUnauthor.action");
        }
        //2.ajax请求异常
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
            BaseController.fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
            return new ModelAndView();
        }else if(requestURI.contains("mobileApi")){
            BaseController.returnJsonOfMobile(response, false, "服务器异常,请联系管理员!", ExceptionCode.MOBILE_SERVER_ERROR);
            return new ModelAndView();
        }
        return new ModelAndView("redirect:/sysUserController/toError.do");
    }

   
}
