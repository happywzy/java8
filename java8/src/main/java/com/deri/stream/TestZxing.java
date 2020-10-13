package com.deri.stream;

import java.util.UUID;

import static com.deri.stream.util.QRCodeUtil.decodeQRCode;
import static com.deri.stream.util.QRCodeUtil.encodeQRCode;

/**
 * @ClassName: TestZxing
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/7/10 10:05
 * @Version: v1.0
 **/
public class TestZxing {
    public static void main(String[] args) {
//         生成二维码
        System.out.println(encodeQRCode(UUID.randomUUID().toString()));

//         解码二维码
//        需要去掉data:image/png;base64,
        System.out.println(decodeQRCode("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"));

    }
}
