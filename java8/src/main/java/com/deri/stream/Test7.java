package com.deri.stream;

import com.alibaba.fastjson.JSON;
import com.deri.stream.util.TestPojo;

import java.util.Date;

/**
 * @ClassName: Test7
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/26 9:49
 * @Version: v1.0
 **/
public class Test7 {

    public static void main(String[] args) {
        /*
        TestPojo pojo = new TestPojo(1,"wuzhiyong",new Date(),10L);
        System.out.println(JSON.toJSONString(pojo));
*/

        String json = "{\"age\":10,\"date\":\"2020-03-26T09:53:24Z\",\"id\":1,\"name\":\"wuzhiyong\"}";
        TestPojo pojo = JSON.parseObject(json, TestPojo.class);
        System.out.println(pojo.getDate().getTime());
    }
}
