package com.deri.stream;

import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;

import java.awt.*;
import java.io.IOException;

/**
 * @ClassName: TestCaptcha
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/7/3 17:33
 * @Version: v1.0
 **/
public class TestCaptcha {
    public static void main(String[] args) throws IOException, FontFormatException {
        SpecCaptcha captcha = new SpecCaptcha(180, 40, 6);
//        GifCaptcha captcha = new GifCaptcha(180, 48, 6);
//        ChineseCaptcha captcha = new ChineseCaptcha(180, 48);
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(180, 48, 3);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
//        captcha.setFont(Captcha.FONT_1);
//        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        System.out.println(captcha.text());
        System.out.println(captcha.textChar());
        System.out.println(captcha.toBase64());

        // 如果不想要base64的头部data:image/png;base64,
        System.out.println(captcha.toBase64(""));

//        // gif类型
//        GifCaptcha captcha = new GifCaptcha(130, 48);
//
//        // 中文类型
//        ChineseCaptcha captcha = new ChineseCaptcha(130, 48);
//
//        // 中文gif类型
//        ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48);
//
//        // 算术类型
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
//        captcha.setLen(3);  // 几位数运算，默认是两位
//        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
//        captcha.text();  // 获取运算的结果：5


    }
}
