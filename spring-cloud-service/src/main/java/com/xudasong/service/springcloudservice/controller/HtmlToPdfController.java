package com.xudasong.service.springcloudservice.controller;

import com.xudasong.service.springcloudservice.service.Impl.PdfService;
import com.xudasong.service.springcloudservice.util.CreatePdfUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api("html转pdf")
public class HtmlToPdfController {

    @Autowired
    private PdfService pdfService;

    /**
     * 根据模板动态生成的pdf
     * 参考实例：https://github.com/zhj526/htmlConvertPDF/tree/master/src/main/java/cn/edu/hpu
     * @param response
     * @throws Exception
     */
    @GetMapping (value = "api/release/createPDF")
    public void downPDF(HttpServletResponse response) throws Exception {
        String file=pdfService.createPdfByTemplate();
        if(!StringUtils.isEmpty(file)){
            //设置下载格式
            CreatePdfUtil.downLoad(file,"pdf","保险单.pdf",response);
            //删除生成的文件
//            CreatePdfUtil.deletePDF(file);
        }
    }

}
