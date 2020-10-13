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
public class Test6174 {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            // 随机生成四个数字，考虑到出现四个一样的概率非常低 没有处理
            List<Integer> meta = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                meta.add(new Random().nextInt(10));
            }
            System.out.print("原始数字:" + meta);
            int result = 0, count = 0;
            while (result != 6174) {
                // 获取四个数字组合的最大的数 和 最小的数
                int max = getMax(meta);
                int min = getMin(meta);
                result = Math.abs(max - min);
                count++;
                // 数字为啥是6174？
                if (result == 6174) {
                    System.out.println(",次数:" + count);
                }
                meta = getMeta(result);
            }
            Thread.sleep(1000);
        }
    }

    public static int getMax(List<Integer> meta) {
        List<Integer> tmp = meta.stream().sorted().collect(Collectors.toList());
        return tmp.get(0) * 1000 + tmp.get(1) * 100 + tmp.get(2) * 10 + tmp.get(3);
    }

    public static int getMin(List<Integer> meta) {
        List<Integer> tmp = meta.stream().sorted().collect(Collectors.toList());
        return tmp.get(0) + tmp.get(1) * 10 + tmp.get(2) * 100 + tmp.get(3) * 1000;
    }

    public static List<Integer> getMeta(int num) {
        List<Integer> tmp = new ArrayList<>();
        tmp.add(num / 1000 % 10);
        tmp.add(num / 100 % 10);
        tmp.add(num / 10 % 10);
        tmp.add(num % 10);
        return tmp;
    }

}
