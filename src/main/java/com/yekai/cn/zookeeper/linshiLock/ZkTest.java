package com.yekai.cn.zookeeper.linshiLock;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author yekai
 * @date 2020/8/11 13:05
 */
public class ZkTest {
    static int inventory = 5;
    private static final int NUM = 10;

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {

            new Thread(new Runnable() {
                ZookeeperLock zookeeperLock = null;
                public void run() {
                    try {
                        zookeeperLock = new ZookeeperLock();
                        zookeeperLock.lock();
                        Thread.sleep(1000);
                        if (inventory > 0) {
                            inventory--;
                        }
                        System.out.println(inventory);
                    } catch (Exception e) {
                        System.out.println("---");
                    } finally {
                        zookeeperLock.unlock();
                    }
                }
            }).start();
        }
    }
}
