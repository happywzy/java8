package com.deri.pojo.util;

import com.alibaba.fastjson.JSON;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: MyNoParalleSource
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/4 10:15
 * @Version: v1.0
 **/
//使用并行度为1的source
public class MyPojoSource implements SourceFunction<String> {//1

    private boolean isRunning = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (isRunning) {
            BookPojo book = Books.getBook();
            book.setTs(new Date());
            ctx.collectWithTimestamp(JSON.toJSONString(book),book.getTs().getTime());
            Thread.sleep(new Random().nextInt(4000));
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

}


