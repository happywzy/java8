package com.deri.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @ClassName: Test6174
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/7/2 15:26
 * @Version: v1.0
 **/
public class TestN {
    public static void main(String[] args) throws InterruptedException {
        // 输入几位数
        int digits = 9;
        List<Integer> meta = new ArrayList<>();
        for (int i = 0; i < digits; i++) {
            meta.add(new Random().nextInt(10));
        }
        if (getMax(meta) - getMin(meta) == 0){
            System.out.println("数字完全一样,不符合要求");
            System.exit(0);
        }
        System.out.println("原始数字:" + meta);
        int result = 0;
        while (true) {
            int max = getMax(meta);
            int min = getMin(meta);
            result = Math.abs(max - min);
            meta = getMeta(result, digits);
            System.out.println(result);
        }
    }

    // 获取数字组合的最大数
    public static int getMax(List<Integer> meta) {
        List<Integer> tmp = meta.stream().sorted().collect(Collectors.toList());
        double result = 0;
        for (int i = 0; i < tmp.size(); i++) {
            result = result + tmp.get(i) * (Math.pow(10.0, (double) (i)));
        }
        return (int) result;
    }

    // 获取数字组合的最小数
    public static int getMin(List<Integer> meta) {
        List<Integer> tmp = meta.stream().sorted().collect(Collectors.toList());
        double result = 0;
        for (int i = 0; i < tmp.size(); i++) {
            result = result + tmp.get(i) * (Math.pow(10.0, (double) (tmp.size() - i - 1)));
        }
        return (int) result;
    }

    // 获取数的各位数
    public static List<Integer> getMeta(int num, int c) {
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < c; i++) {
            tmp.add(num / ((int) Math.pow(10.0, i)) % 10);
        }
        return tmp;
    }

}
