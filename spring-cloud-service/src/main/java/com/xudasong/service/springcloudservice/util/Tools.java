package com.xudasong.service.springcloudservice.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Tools {

    /**
     * JavaBean对象转化成Map对象
     *
     * @param javaBean
     * @return
     * @author jqlin
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map java2Map(Object javaBean) {
        Map map = new HashMap();

        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (!propertyName.equals("class")) {
                        Method readMethod = pd.getReadMethod();
                        propertyValue = readMethod.invoke(javaBean, new Object[0]);
                        map.put(propertyName, propertyValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 设置下载的header
     * @param fileType
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     */
    public static void setDownFileCommonHttpHeader(String fileType, String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setHeader("Content-Type",getHttpFileContent(fileType)+"; charset=UTF-8");
        response.setHeader("Content-Transfer-Encoding","binary");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Content-Disposition", "attachment; filename="+ Tools.toUtf8String(fileName));
    }

    /**
     *导出中文文件时名称处理方法
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 设置下载的格式
     * @param fileType
     * @return
     */
    public static String getHttpFileContent(String fileType) {
        if (StringUtils.isEmpty(fileType)) {
            return "";
        }
        if(fileType.trim().contains("docx")){
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        if(fileType.trim().contains("xlsx")){
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if(fileType.trim().contains("pdf")){
            return "application/pdf";
        }
        if(fileType.trim().contains("doc")){
            return "application/msword";
        }
        if(fileType.trim().contains("xls")){
            return "application/msexcel";
        }
        if(fileType.trim().contains("zip")){
            return "application/zip";
        }
        if(fileType.trim().contains("rar")){
            return "application/x-rar-compressed";
        }
        return "application/octet-stream";
    }

}
