package com.yekai.cn.zookeeper.linshiLock;

import com.yekai.cn.zookeeper.MyLock;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * @author yekai
 * @date 2020/8/11 12:52
 */
public class ZookeeperLock implements Lock {
    private static final String IP_PORT = "127.0.0.1:2181";
    private static final String Z_NODE = "/LOCK_01";
    private ZkClient zkClient;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    ZookeeperLock() {
        zkClient  = new ZkClient(IP_PORT);
    }


    public void waitLock() {

        IZkDataListener iZkDataListener = new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {

            }

            public void handleDataDeleted(String s) throws Exception {
                //System.out.println("释放锁");
                countDownLatch.countDown();

            }
        };
        zkClient.subscribeDataChanges(Z_NODE, iZkDataListener);
        if (zkClient.exists(Z_NODE)) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(Z_NODE, iZkDataListener);

    }

    public void lock() {
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName() + "获取到锁");
            return;
        }
        //等待锁
        waitLock();
        //重新尝试获取锁
        lock();
    }

    public void lockInterruptibly() throws InterruptedException {

    }


    public  boolean tryLock() {
        try {
            zkClient.createPersistent(Z_NODE);
            return true;
        } catch (ZkNodeExistsException e) {
            return false;
        }
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    public void unlock() {
        zkClient.delete(Z_NODE);
        System.out.println("释放锁");
    }

    public Condition newCondition() {
        return null;
    }


}
