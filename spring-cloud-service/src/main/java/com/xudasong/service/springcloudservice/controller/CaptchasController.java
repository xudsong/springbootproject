package com.xudasong.service.springcloudservice.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.xudasong.service.springcloudservice.constants.RedisKeyConstants;
import com.xudasong.service.springcloudservice.service.IRedisService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 图形验证码
 */
@RestController
@Slf4j
public class CaptchasController {

    @Autowired
    private Producer producer;

    @Autowired
    private IRedisService redisServer;

    @ApiOperation("随机生成图片验证码")
    @GetMapping("/api/release/captchas/captchaImge")
    public void getKaptchaImge(HttpServletRequest request, HttpServletResponse response)throws Exception{
        response.setDateHeader("Expirse", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader)
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = producer.createText();
        log.info("******************验证码是: " + capText + "******************");
        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //缓存与redis 10分钟有效期
        redisServer.hset(RedisKeyConstants.CAPTCHASCODE_HEADER,"captchasId",capText,600, TimeUnit.SECONDS);
        // create the image with the text
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }

    }

}
