package com.deri.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: Test5
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/3/24 14:09
 * @Version: v1.0
 **/
public class Test5 {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("zhang1", 14),
                new User("zhang2", 25),
                new User("zhang3", 16),
                new User("zhang4", 27),
                new User("zhang5", 18),
                new User("zhang6", 12)
        );

//        users.sort(new Comparator<User>() {
//            @Override
//            public int compare(User o1, User o2) {
//                return o1.age - o2.age;
//            }
//        });
//        users.sort((o1, o2) -> o1.age - o2.age);
//
//        users.forEach(System.out::print);

        users.stream().sorted((o1, o2) -> o1.age - o2.age).forEach(System.out::print);
    }

    public static class User {
        public String name;
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    "}\n";
        }
    }
}
