package com.xudasong.service.springcloudservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求头中的参数，这里以cityId和tenantId为例
 */
public class ThreadLocalOwnInfo {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadLocalOwnInfo.class);

    private static final ThreadLocal<OwnInfo> OWN_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static OwnInfo createOwnInfo(HttpServletRequest request){
        String cityId = request.getHeader("cityId");
        String tenantId = request.getHeader("tenantId");

        if(StringUtils.isBlank(cityId) || StringUtils.isBlank(tenantId)){
            LOG.error("cityId And tenantId can not be null, requestMethod:{}",request.getRequestURI());
            //给个默认值
            return new OwnInfo(3L,3L);
        }
        return new OwnInfo(Long.valueOf(cityId),Long.valueOf(tenantId));
    }

    public static void setOwnInfo(OwnInfo ownInfo){OWN_INFO_THREAD_LOCAL.set(ownInfo);}

    public static OwnInfo getOwnInfo(){return OWN_INFO_THREAD_LOCAL.get();}

    public static void removeOwnInfo(){OWN_INFO_THREAD_LOCAL.remove();}

    public static Long getTenantId(){return getOwnInfo().getTenantId();}

    public static Long getCityId(){return getOwnInfo().getCityId();}

    @Data
    @AllArgsConstructor
    public static class OwnInfo{
        private Long cityId;
        private Long tenantId;
    }

}
