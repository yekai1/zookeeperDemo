package com.yekai.cn.zookeeper;

/**
 * @author yekai
 * @date 2020/8/11 17:11
 */
public interface MyLock {
    //等待锁
    public void waitLock();
    //加锁
    public void lock();
    //尝试去获取锁
    public  boolean tryLock();
    //释放锁
    public void unlock();
}
