package com.xudasong.service.springcloudservice.util;

import java.util.Iterator;
import java.util.Map;

/**
 * @author song
 */
public class StringUtil {

    public static String temp(String str,String inxl){
        return str.replaceFirst("\\{\\}",inxl);
    }

    public static String temp(String str,String inxl,String inx2){
        return (str.replaceFirst("\\{\\}",inxl)).replaceFirst("\\{\\}",inx2);
    }

    public static String temp(String str,String inxl,String inx2,String inx3){
        return ((str.replaceFirst("\\{\\}",inxl)).replaceFirst("\\{\\}",inx2)).replaceFirst("\\{\\}",inx3);
    }

    public static String setContent(Map<String,Object> paramMap,String content){
        if(paramMap.size()<=0){
            return content;
        }
        Iterator<Map.Entry<String,Object>> it = paramMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> en = it.next();
            String key = "{" + en.getKey() + "}";
            content = content.replace(key,en.getValue()+"");
        }
        return content;
    }

}
