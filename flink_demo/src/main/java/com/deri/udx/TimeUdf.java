package com.deri.udx;

import org.apache.flink.table.functions.ScalarFunction;

import java.sql.Timestamp;

/**
 * @ClassName: TimeUdf
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/25 14:25
 * @Version: v1.0
 **/
public class TimeUdf extends ScalarFunction {

    private Long timestamp;

    public TimeUdf(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp eval(Long timestamp){
        return new Timestamp(timestamp);
    }
}
