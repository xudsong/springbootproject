package com.xudasong.service.springcloudservice.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * 创建FreeMarker模板文件解析器类FreeMarkertUtil
 **/
public class FreeMarkerUtil {

    /**
     *  生成html文件
     * @param templatePath  模板文件存放路径
     * @param templateName 模板文件名称
     * @param distFileName 生成的文件名称
     * @param root
     */
    public static void  analysisTemplate(String templatePath,String templateName,String distFileName,Map<?,?> root){
        try {
            Configuration config=FreemarkerConfigurationUtil.getConfiguation();
            //设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(new File(templatePath));
            //设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());
            //获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            //否则会出现乱码
            Template template=config.getTemplate(templateName,"UTF-8");
            //合并数据模型与模板
            FileOutputStream fos = new FileOutputStream(distFileName);
            Writer out = new OutputStreamWriter(fos,"UTF-8");
            template.process(root, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成FreeMarker模板文件的文件流
     * @param templatePath
     * @param templateName
     * @param variables
     * @return
     */
    public static String  analysisTemplateToString(String templatePath, String templateName, Map<?,?> variables){
        String htmlContent="";
        BufferedWriter writer = null;
        try {
            Configuration config=FreemarkerConfigurationUtil.getConfiguation();
            //设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(new File(templatePath));
            //设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());
            //获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            //否则会出现乱码
            Template template=config.getTemplate(templateName,"UTF-8");
            //合并数据模型与模板
            StringWriter stringWriter = new StringWriter();
            writer = new BufferedWriter(stringWriter);
            //合并数据模型与模板
            template.process(variables, writer);
            htmlContent = stringWriter.toString();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return htmlContent;
    }

}
