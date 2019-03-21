package com.xudasong.service.springcloudservice.config.filter;

import ch.qos.logback.classic.ClassicConstants;
import com.xudasong.service.springcloudservice.util.AddressUtils;
import com.xudasong.service.springcloudservice.util.UUIDGenerator;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从MDCInsertingServletFilter 再追加SessionId方便追踪
 * @see ch.qos.logback.classic.helpers.MDCInsertingServletFilter
 */
public class LogFilter implements Filter{

    private static final String REQ_FULLSTR = "req.fullstr";
    private static final String REQ_CLIENT_IP = "req.clientIp";
    private static final String REQ_SESSION_ID = "req.sessionId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        insertIntoMDC(request,response);
        try{
            chain.doFilter(request,response);
        }finally {
            clearMDC();
        }

    }

    void insertIntoMDC(ServletRequest request, ServletResponse response){

        MDC.put(ClassicConstants.REQUEST_REMOTE_HOST_MDC_KEY,request.getRemoteHost());

        if(request instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            //sessionId改成用自己生成的UUID，避免用户用页面提交，存在相同的sessionId
            String sessionId = UUIDGenerator.getUUID();
            String clientIp = AddressUtils.getIpAdr(httpServletRequest);

            MDC.put(ClassicConstants.REQUEST_REQUEST_URI,httpServletRequest.getRequestURI());
            StringBuffer requestURL = httpServletRequest.getRequestURL();
            if(requestURL != null){
                MDC.put(ClassicConstants.REQUEST_REQUEST_URI,requestURL.toString());
            }
            MDC.put(ClassicConstants.REQUEST_METHOD,httpServletRequest.getMethod());
            MDC.put(ClassicConstants.REQUEST_QUERY_STRING,httpServletRequest.getQueryString());
            MDC.put(ClassicConstants.REQUEST_USER_AGENT_MDC_KEY,httpServletRequest.getHeader("User-Agent"));
            MDC.put(ClassicConstants.REQUEST_X_FORWARDED_FOR,httpServletRequest.getHeader("X-Forwarded-For"));

            MDC.put(REQ_FULLSTR,String.format(" [%s %s]",httpServletRequest.getMethod(),httpServletRequest.getRequestURI()));
            MDC.put(REQ_CLIENT_IP,String.format(" [%s]",clientIp));
            MDC.put(REQ_SESSION_ID,String.format(" [%s]", sessionId));

            //设置一个请求编号方便日志查询
            if(response instanceof HttpServletResponse){
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("x-request", sessionId);
            }
        }

    }

    void clearMDC(){
        MDC.remove(ClassicConstants.REQUEST_REMOTE_HOST_MDC_KEY);
        MDC.remove(ClassicConstants.REQUEST_REQUEST_URI);
        MDC.remove(ClassicConstants.REQUEST_QUERY_STRING);
        MDC.remove(ClassicConstants.REQUEST_REQUEST_URI);
        MDC.remove(ClassicConstants.REQUEST_METHOD);
        MDC.remove(ClassicConstants.REQUEST_USER_AGENT_MDC_KEY);
        MDC.remove(ClassicConstants.REQUEST_X_FORWARDED_FOR);
        MDC.remove(REQ_FULLSTR);
        MDC.remove(REQ_CLIENT_IP);
        MDC.remove(REQ_SESSION_ID);
    }

    @Override
    public void destroy() {
         //do nothing
    }
}
