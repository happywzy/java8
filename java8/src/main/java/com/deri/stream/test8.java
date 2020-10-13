package com.deri.stream;


import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: test8
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/4/9 15:31
 * @Version: v1.0
 **/
public class test8 {

    public static String lock = "lock";

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("192.168.41.128");
        while (true) {
            String value = UUID.randomUUID().toString();
            if(lock(jedis,value)) {
                System.out.println("B:" + System.currentTimeMillis());
//                Thread.sleep(2000);
            }
//            unLock(jedis,value);
        }
    }

    public static boolean lock(Jedis jedis, String value) {
        if ("OK".equalsIgnoreCase(jedis.set(lock, value, "NX", "EX", 10))) {
            return true;
        }
        return false;
    }

    public static boolean unLock(Jedis jedis, String value) {
        Long RELEASE_SUCCESS = 1L;
        String command = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        if (RELEASE_SUCCESS.equals(jedis.eval(command, Collections.singletonList(lock), Collections.singletonList(value)))) {
            return true;
        }
        return false;
    }

}
