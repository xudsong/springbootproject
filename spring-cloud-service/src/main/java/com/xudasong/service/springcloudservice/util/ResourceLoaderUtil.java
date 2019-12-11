package com.xudasong.service.springcloudservice.util;

import java.net.URL;

public class ResourceLoaderUtil {

    public static String CLASS_PATH_PREFIX ="classpath:";

    /**
     * classpath 中搜索路径
     * @param resource
     * @return
     */
    public static String getPath(String resource){
        if(resource!=null){
            if(resource.startsWith(CLASS_PATH_PREFIX)){
                resource = getPath("")+resource.replaceAll(CLASS_PATH_PREFIX, "");
            }
        }

        URL url = getResource(resource);
        if(url==null)
            return null;
        return url.getPath().replaceAll("%20", " ");
    }

    /**
     * classpath中获取资源
     * @param resource
     * @return
     */
    public static URL getResource(String resource) {
        ClassLoader classLoader = null;
        classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(resource);
    }

}
