package com.deri.pojo;

import com.alibaba.fastjson.JSON;
import com.deri.pojo.util.BookPojo;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

import java.util.Properties;

/**
 * @ClassName: KafkaWordCount
 * @Description: 从kafka主题中读取数据，进行word count
 * @Author: wuzhiyong
 * @Time: 2020/3/18 15:05
 * @Version: v1.0
 **/
public class KafkaPojoStream {

    public static void main(String[] args) throws Exception {
        // 创建Flink执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.41.128:9092");
        properties.setProperty("group.id", "flink-group");
        String inputTopic = "pojosource";
        String outputTopic = "pojosink";

        FlinkKafkaConsumer<String> consumer =
                new FlinkKafkaConsumer<>(inputTopic, new SimpleStringSchema(), properties);
        FlinkKafkaProducer<String> producer =
                new FlinkKafkaProducer<>(outputTopic, new SimpleStringSchema(), properties);

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<String> stream = env.addSource(consumer);

        DataStream<String> bookPojoDataStream = stream.map(s -> JSON.parseObject(s, BookPojo.class))
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<BookPojo>() {
                    @Override
                    public long extractAscendingTimestamp(BookPojo bookPojo) {
                        return bookPojo.getTs().getTime();
                    }

                    @Override
                    public AscendingTimestampExtractor<BookPojo> withViolationHandler(MonotonyViolationHandler handler) {
                        return super.withViolationHandler(handler);
                    }
                })
//                .filter(bookPojo -> bookPojo.getType().equalsIgnoreCase("cs"))
                .keyBy("id")
//                .timeWindow(Time.seconds(10))
                .timeWindow(Time.minutes(5), Time.seconds(10))
                .reduce(new ReduceFunction<BookPojo>() {
                    @Override
                    public BookPojo reduce(BookPojo bookPojo, BookPojo t1) throws Exception {
                        BookPojo book = new BookPojo();
                        book.setId(bookPojo.getId());
                        book.setPrice(bookPojo.getPrice()+ t1.getPrice());
                        book.setType(t1.getType());
                        book.setTs(t1.getTs());
                        return book;
                    }
                })
                .map(s -> JSON.toJSONString(s));

//        bookPojoDataStream.addSink(producer);
        bookPojoDataStream.print();
        // execute
        env.execute("kafka streaming pojo");

    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            String[] tokens = value.toLowerCase().split("\\W+");
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
