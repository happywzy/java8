package com.deri.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


public class Test {
    public static List<String> agent = Arrays.asList(
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)",
            "AppleWebKit/537.36 (KHTML, like Gecko)",
            "Chrome/45.0.2454.101", "Safari/537.36"
    );

    public static void main(String[] args) throws Exception {
        while (true) {
            Test.sendGet("http://loveroath.com");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendGet(String url) throws Exception {
        String result = "";
        BufferedReader in = null;
        URL realUrl = new URL(url);
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", agent.get(new Random().nextInt(4)));
        connection.setRequestProperty("HTTP_CLIENT_IP", randIP());
        connection.connect();
        // 获取所有响应头字段
        Map<String, List<String>> map = connection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : map.keySet()) {
            System.out.println(key + "--->" + map.get(key));
        }
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        in.close();
        System.out.println(result);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }


    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }

}