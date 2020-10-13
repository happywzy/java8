package com.deri.stream.util;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @ClassName: TestPojo
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/26 9:48
 * @Version: v1.0
 **/
public class TestPojo {
    private int id;
    private String name;
    @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;
    private Long age;

    public TestPojo() {
    }

    public TestPojo(int id, String name, Date date, Long age) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", age=" + age +
                '}';
    }
}
