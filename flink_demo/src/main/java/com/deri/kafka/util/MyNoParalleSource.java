package com.deri.kafka.util;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.ArrayList;
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
public class MyNoParalleSource implements SourceFunction<String> {//1

    private long count = 1L;
    private boolean isRunning = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while(isRunning){
            List<String> books = new ArrayList<>();
            books.add("Pyhton is very good");//10
            books.add("Java is very good");//8
            books.add("Php is very good");//5
            books.add("C++ is very good");//3
            books.add("Scala is very good");//0-4
            int i = new Random().nextInt(5);
            ctx.collect(count + ":" +books.get(i));
//            ctx.collectWithTimestamp();
//            ctx.emitWatermark();
            Thread.sleep(2000);
            count ++;
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}


