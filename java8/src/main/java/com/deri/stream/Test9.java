package com.deri.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Test9
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/5/18 11:35
 * @Version: v1.0
 **/
public class Test9 {
    public static void main(String[] args) throws IOException {
        System.out.println(readLineFromFile("D:/js/test/main.js"));

    }
    static List<String> readLineFromFile(String path) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String str = null;
            while ((str = br.readLine()) != null){
                result.add(str);
            }
        }
        return result;
    }
}

