package com.yekai.cn.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author yekai
 * @date 2020/8/12 13:17
 */
public class ZkLockForCurator  {
    private static final String IP_PORT = "127.0.0.1:2181";
    private static final String Z_NODE = "/LOCK";

    private CuratorFramework client;
    private InterProcessMutex lock;
    ZkLockForCurator(){
        //RetryPolicy为重试策略，第一个参数为baseSleepTimeMs初始的sleep时间
        //第二个参数为maxRetries，最大重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(IP_PORT,retryPolicy);
        lock = new InterProcessMutex(client,Z_NODE);
    }

    public void lock() throws Exception {
        lock.acquire(10, TimeUnit.SECONDS);
    }

    public void unLock() throws Exception {
        lock.release();
    }
}
