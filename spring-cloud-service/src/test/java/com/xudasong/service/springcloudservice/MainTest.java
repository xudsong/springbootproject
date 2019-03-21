package com.xudasong.service.springcloudservice;

import com.xudasong.service.springcloudservice.util.MD5Util;
import com.xudasong.service.springcloudservice.util.UicodeBackslashU;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringCloudServiceApplication.class)
public class MainTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test(){
        String mobile = "18328002268";
        String privateKey = "wXyQtcMJIO0Rrs0DRFgnLovVGOGimbJj";

        String timestamp = getSecondTimestampTwo(new Date())+"";
        String code = "345678";
        String signature = "code="+code+"&sjhm="+mobile+"&timestamp="+timestamp+privateKey;
        String str = MD5Util.getMD5(signature,false,32);
        String url = "http://gzjjwx.gzjd.gov.cn/sandbox/GzjjPingAnXcxApiServer/PA/SendMessage";
        MultiValueMap<String,Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("code",code);
        postParameters.add("sjhm",mobile);
        postParameters.add("timestamp",timestamp);
        postParameters.add("signature",str);
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        org.springframework.http.HttpEntity<MultiValueMap<String,Object>> r = new org.springframework.http.HttpEntity<>(postParameters);
        String data = restTemplate.postForObject(url,r,String.class);
        data = UicodeBackslashU.unicodeToCn(data);
        System.out.println(data);

    }

    public static void main(String[] args)throws Exception{
        //String idCard = "362321199011015934";
        //String mobile = "13632947598";
        String mobile = "13632762050";
        String appliName = "张三";
        Date accidentDate = new Date();
        String accidentNo = "1234567890";


        //身份证验证
//        System.out.println(CommonUtils.idEncrypt(idCard));
//        System.out.println(CommonUtils.mobileEncrypt(mobile));
//        System.out.println(IdcardUtil.isValidCard(idCard));

        String privateKey = "wXyQtcMJIO0Rrs0DRFgnLovVGOGimbJj";

        String timestamp = getSecondTimestampTwo(new Date())+"";
        //String code = "345678";
        //String signature = "code="+code+"&sjhm="+mobile+"&timestamp="+timestamp+privateKey;
        String signature = "appliName="+appliName+"&accidentDate="+accidentDate+"&accidentNo="+accidentNo+"&sjhm="+mobile+"&timestamp="+timestamp+privateKey;
        String str = MD5Util.getMD5(signature,false,32);
        String url = "http://gzjjwx.gzjd.gov.cn/sandbox/GzjjPingAnXcxApiServer/PA/SendMessage1";
        Map<String,String> map = new HashMap<>(8);
       // map.put("code",code);
        map.put("appliName",appliName);
        map.put("accidentDate",String.valueOf(accidentDate));
        map.put("accidentNo",accidentNo);
        map.put("sjhm",mobile);
        map.put("timestamp",timestamp);
        map.put("signature",str);


    }

    /**
    获取精确到秒的时间戳
    @param date
    @return
     */
    public static int getSecondTimestampTwo(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }



}
