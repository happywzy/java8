package com.deri.stream.util;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具类
 *
 * @author yclimb
 * @date 2018/4/23
 */
public class QRCodeUtil {
    private static int width = 300;
    private static int height = 300;
    private static String format = "png";

    public static String encodeQRCode(String text) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String result = null;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // 设置字符集编码
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
            // 不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            // 设置二维码边距，单位像素，值越小，二维码距离四周越近
            hints.put(EncodeHintType.MARGIN, 1);
            // 生成二维码矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            // 写入文件
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
            bitMatrix.clear();
            result = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        return result;

    }

    public static String decodeQRCode(String base64) {
        Result result = null;
        BufferedImage image = null;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
        try {
            // 读取图片
            image = ImageIO.read(inputStream);
            // 多步解析
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            // 设置字符集编码
            Map<DecodeHintType, String> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            // 对图像进行解码
            result = new MultiFormatReader().decode(binaryBitmap, hints);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            image.getGraphics().dispose();
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        return result.getText();
    }

}

