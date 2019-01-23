package springbootdemo;

import com.xiaoleilu.hutool.util.IdcardUtil;
import springbootdemo.util.CommonUtils;

public class MainTest {

    public static void main(String[] args){
        String idCard = "362321199011015934";
        String mobile = "18720061484";

        //身份证验证
        System.out.println(CommonUtils.idEncrypt(idCard));
        System.out.println(CommonUtils.mobileEncrypt(mobile));
        System.out.println(IdcardUtil.isValidCard(idCard));
    }
}
