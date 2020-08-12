package com.yekai.cn.zookeeper;

/**
 * @author yekai
 * @date 2020/8/11 17:11
 */
public interface MyLock {
    public void waitLock();
    public void lock();
    public  boolean tryLock();
    public void unlock();
}
