package com.xudasong.service.springcloudservice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlTest {

    public static void main(String[] args)throws Exception{
        String testString = "random word 拢500 bank $";
        try
        {
            String encoderString = URLEncoder.encode(testString, "utf-8");
            System.out.println(encoderString);
            String decodedString = URLDecoder.decode(encoderString, "utf-8");
            System.out.println(decodedString);
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
