package com.deri.stream;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: Test6
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/25 14:42
 * @Version: v1.0
 **/
public class Test6 {

    public static void main(String[] args) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" );
        Date date = new Date();
        System.out.println(sdf.format(date));

    }
}
