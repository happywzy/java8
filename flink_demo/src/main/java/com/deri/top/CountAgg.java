package com.deri.top;

import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * @ClassName: CountAgg
 * @Description: 实现了AggregateFunction接口，功能是统计窗口中的条数，即遇到一条数据就加一
 * @Author: wuzhiyong
 * @Time: 2020/3/24 11:28
 * @Version: v1.0
 **/
public class CountAgg  implements AggregateFunction<UserBehavior, Long, Long> {
    @Override
    public Long createAccumulator() {
        return 0L;
    }

    @Override
    public Long add(UserBehavior userBehavior, Long acc) {
        return acc + 1;
    }

    @Override
    public Long getResult(Long acc) {
        return acc;
    }

    @Override
    public Long merge(Long acc1, Long acc2) {
        return acc1 + acc2;
    }
}
