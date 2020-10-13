package com.deri.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @ClassName: Test2
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/16 14:33
 * @Version: v1.0
 **/
public class Test2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10, 9, 2, 4, 6, 7, 8, 7, 8, 5);
        list.stream()
                .filter(x -> x != 5)
                .map(x -> x + 10)
                .sorted()
                .distinct()
                .forEach(System.out::println);
    }
}
