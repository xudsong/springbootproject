package com.xudasong.service.springcloudservice.util;

import com.xudasong.service.springcloudservice.config.ITextRendererObjectFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class CreatePdfUtil {

    /**
     * 创建PDF文件到服务器
     * @param type 生成方式
     * @param htmlContent html内容字符串
     * @param outputFile  pdf输出路径
     * @return
     * @throws Exception
     */
    public static  String  generate(Integer type, String htmlContent, String outputFile)
            throws Exception {
        String pathFile ="";
        OutputStream out = null;
        ITextRenderer iTextRenderer = null;
        try {
            Document doc = null;
            if (type == 2) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
                doc = builder.parse(new ByteArrayInputStream(htmlContent
                        .getBytes("UTF-8")));
            }
            File f = new File(outputFile);
            if (f != null && !f.getParentFile().exists()) {
                f.getParentFile().mkdir();
            }
            out = new FileOutputStream(outputFile);

            iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();

            try {
                if (type == 2) {
                    iTextRenderer.setDocument(doc, null);
                }else if (type == 1) {
                    iTextRenderer.setDocumentFromString(htmlContent);
                }
                iTextRenderer.layout();
                iTextRenderer.createPDF(out);
                pathFile = outputFile;
            } catch (Exception e) {
                ITextRendererObjectFactory.getObjectPool().invalidateObject(iTextRenderer);
                iTextRenderer = null;
                return null;
            }

        } catch (Exception e) {
            return null;
        } finally {
            if (out != null)
                out.close();

            if (iTextRenderer != null) {
                try {
                    ITextRendererObjectFactory.getObjectPool().returnObject(
                            iTextRenderer);
                } catch (Exception ex) {
                    // logger.error("Cannot return object from pool.", ex);
                    return null;
                }
            }
        }
        return pathFile;
    }

    /**
     * 下载文件
     * @param filePath
     * @param pdfname
     * @param response
     */
    public static void downLoad(String filePath, String fileType, String pdfname, HttpServletResponse response){
        try {

            FileInputStream fileInputStream = new FileInputStream(filePath);
            ServletOutputStream outputStream = response.getOutputStream();
            Tools.setDownFileCommonHttpHeader(fileType,pdfname,response);
            //输出
            int len = 1;
            byte[] bs = new byte[1024];
            while((len = fileInputStream.read(bs)) != -1){
                outputStream.write(bs, 0, len);
            }
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param filePath
     */
    public static void deletePDF(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
    }

}
