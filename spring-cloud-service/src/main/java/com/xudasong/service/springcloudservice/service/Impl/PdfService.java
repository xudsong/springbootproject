package com.xudasong.service.springcloudservice.service.Impl;

import com.xudasong.service.springcloudservice.model.OverseaVo;
import com.xudasong.service.springcloudservice.util.CreatePdfUtil;
import com.xudasong.service.springcloudservice.util.FreeMarkerUtil;
import com.xudasong.service.springcloudservice.util.ResourceLoaderUtil;
import com.xudasong.service.springcloudservice.util.Tools;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class PdfService {

    /**
     * 方式1：自己通过拼接成字符串html
     * @return
     * @throws Exception
     */
    public String createPdfByHtmlStr() throws Exception {
        // classpath 路径
        String outputFileClass = ResourceLoaderUtil.getPath("");
        // 生成pdf路径
        String  outputFile =new File(outputFileClass).getParentFile().getParent()+ "/tmp/" + System.currentTimeMillis() + ".pdf";
        //html内容
        String htmlContent = null;
        String result = CreatePdfUtil.generate(1,htmlContent,outputFile);
        return result;
    }

    /**
     * 方式2：通过html模板填充内容并生成pdf
     * @return
     * @throws Exception
     */
    public String createPdfByTemplate() throws Exception{

        // 模板数据
        OverseaVo overseaVo = new OverseaVo();
        overseaVo.setPolicyNo("1234567890123456");
        overseaVo.setHolderName("丽丽张123丽丽张123");
        overseaVo.setInsuredName("丽丽张123丽丽张123丽丽张123丽丽张123");
        overseaVo.setBeneficiaryName("测试受益人姓名");
        overseaVo.setBranchName("北京");
        overseaVo.setCompanyName("科索沃公司");
        overseaVo.setDestination("英国,俄罗斯,冰岛,日内瓦,威尼斯小镇");
        overseaVo.setHolderAdress("北京市屋顶后街金融大街14号中国人寿广场xxx曾x101室");
        overseaVo.setHolderPostCode("123456");
        overseaVo.setInsuredBirthday("2013-11-10");
        overseaVo.setInsuredIDNo("123456789012345678");
        overseaVo.setInsuredName("爱新觉罗启星");
        overseaVo.setInsuredPassportNo("测试护照号码123456789");
        overseaVo.setInsuredPhone("13112345678");
        overseaVo.setInsuredPingyinName("aixinjuluoqixing");
        overseaVo.setInsuredSex("女");
        overseaVo.setIssueDate("2013-11-12");
        overseaVo.setPeriod("十一年");
        overseaVo.setPremium("1009.00");
        overseaVo.setRelation("子女");
        overseaVo.setRemarks("这是一张测试保单,仅为测试,学习所用,请勿转载");
        overseaVo.setAccidentalSumInsured("150000");
        overseaVo.setEmergencySumInsured("500000");
        overseaVo.setMedicalSumInsured("220000");
        overseaVo.setImagePath(ResourceLoaderUtil.getPath("static"));
        String templatePath = ResourceLoaderUtil.getPath("templates/");
        String templateName="overseaAssistance.html";
        // classpath 路径
        String outputFileClass = ResourceLoaderUtil.getPath("");
        // 生成pdf路径
        String  outputFile =new File(outputFileClass).getParentFile().getParent()+ "/tmp/" + System.currentTimeMillis() + ".pdf";
        Map<String,Object> root = Tools.java2Map(overseaVo);
        String htmlContent = FreeMarkerUtil.analysisTemplateToString(templatePath,templateName,root);
        String result = CreatePdfUtil.generate(2,htmlContent,outputFile);
        return result;

    }

}
