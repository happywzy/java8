package com.deri.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: Test1
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/16 14:29
 * @Version: v1.0
 **/
public class Test1 {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        strings.stream()
                .filter(string -> !string.isEmpty())
                .map(i -> i + 1)
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }
}
