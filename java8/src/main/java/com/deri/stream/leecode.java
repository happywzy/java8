package com.deri.stream;

import javafx.scene.control.Alert;

import java.util.Stack;

/**
 * @ClassName: leecode
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/6/24 9:52
 * @Version: v1.0
 **/
class leecode {
    public static String reverseWords(String s) {
        s = s.trim().replaceAll(" +", " ");
        Stack<String> stack = new Stack<String>();
        String[] slist = s.split(" ");
        for (String tmp : slist) {
            stack.push(tmp);
        }
        StringBuffer buffer = new StringBuffer();
        while (stack.size() > 1) {
            buffer.append(stack.pop() + " ");
        }
        buffer.append(stack.pop());
        return buffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("  hello world!  "));
    }
}
