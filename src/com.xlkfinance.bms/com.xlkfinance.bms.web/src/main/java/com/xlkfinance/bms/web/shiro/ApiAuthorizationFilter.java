package com.xlkfinance.bms.web.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.web.controller.BaseController;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年10月26日下午5:12:22 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：1.自定义请求认证提示功能; <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
public class ApiAuthorizationFilter extends FormAuthenticationFilter {
    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (subject.getPrincipal() == null) {//表示没有登录
            BaseController.returnJsonOfMobile(httpResponse, false, "您尚未登录或登录时间过长,请重新登录!", ExceptionCode.MOBILE_AUTHENTICATION_FAILURE);
        }
        return false;
    }
    
}
