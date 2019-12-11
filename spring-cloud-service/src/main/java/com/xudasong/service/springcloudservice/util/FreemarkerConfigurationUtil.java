package com.xudasong.service.springcloudservice.util;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * 单例模式获取Freemarker的Configuration
 * Created by hp on 2018/5/24.
 */
public class FreemarkerConfigurationUtil {
    private static Configuration config = null;
    public static synchronized Configuration getConfiguation() {
        if (config == null) {
            setConfiguation();
        }
        return config;
    }
    private static void setConfiguation() {
        config = new Configuration();
        String path = ResourceLoaderUtil.getPath("");
        System.out.println("path="+path);
        try {
            //设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
