package com.deri.stream;

public class Test10 {
    static ThreadLocal<String> str = ThreadLocal.withInitial(() -> "a");

    public static void main(String[] args) {
        String a = "";
        System.out.println(a);

    }
}
