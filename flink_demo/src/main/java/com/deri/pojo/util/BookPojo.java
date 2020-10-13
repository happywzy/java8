package com.deri.pojo.util;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName: BookPojo
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/20 9:48
 * @Version: v1.0
 **/
public class BookPojo {
    private int id;
    private String type;
    private int price;
    //Currently, JSON format only allowed the timestamp data is in RFC-3339, i.e. "2019-07-09 02:02:00.040Z".
    //UTC标准时间：2017-12-08T00:00:00.00Z
    // 2017-12-08T00:08:00.00+08:00
    //这个代表了同一时刻的，东八区北京时间（CST）表示的方法
    @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date ts;

    public BookPojo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}
