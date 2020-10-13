package com.deri.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * @ClassName: Test4
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/16 15:27
 * @Version: v1.0
 **/
public class Test4 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt(x -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());

    }
}
