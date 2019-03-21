package com.xudasong.service.springcloudservice.config.filter;

import com.xudasong.service.springcloudservice.util.ThreadLocalOwnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤获取固定参数存于ThreadLocal
 */
public class ParameterFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(ParameterFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        ThreadLocalOwnInfo.OwnInfo ownInfo = ThreadLocalOwnInfo.createOwnInfo(request);
        if (ownInfo.getCityId() == -10){
            filterChain.doFilter(request,servletResponse);
            return;
        }

        ThreadLocalOwnInfo.setOwnInfo(ownInfo);
        LOG.info("START filter, thread-> {}--------param->{}",Thread.currentThread().getName(),ThreadLocalOwnInfo.getOwnInfo());

        filterChain.doFilter(request,servletResponse);

        ThreadLocalOwnInfo.removeOwnInfo();
        LOG.info("end filter, thread->{}------------param->{}",Thread.currentThread().getName(),ThreadLocalOwnInfo.getOwnInfo());
    }
}
