package com.deri.pojo;

import com.deri.pojo.util.MyPojoSource;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

/**
 * @ClassName: KafkaProducer
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/4 10:16
 * @Version: v1.0
 **/
public class KafkaPojoProducer {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> text = env.addSource(new MyPojoSource()).setParallelism(1)/*设置并行度*/;

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.41.128:9092");
        //new FlinkKafkaProducer("topn",new KeyedSerializationSchemaWrapper(new SimpleStringSchema()),properties,FlinkKafkaProducer.Semantic.EXACTLY_ONCE);
        FlinkKafkaProducer<String> producer = new FlinkKafkaProducer<String>("pojo4", new SimpleStringSchema(), properties);

        //event-timestamp事件的发生时间
        producer.setWriteTimestampToKafka(true);
        text.addSink(producer);
        env.execute("POJO Kafka Source");
    }

}
