package com.yekai.cn.zookeeper.fenbushi;


public class ZkLock {
    static int inventory = 1;
    private static final int NUM = 10;
    //private static CountDownLatch cdl = new CountDownLatch(NUM);


    public static void main(String[] args) {
        try {
            for (int i = 0; i < NUM; i++) {
                //cdl.countDown();
                new Thread(new Runnable() {
                    Zk zk = null;
                    public void run() {
                        try {
                            //cdl.await();
                            zk = new Zk();
                            zk.lock();
                            Thread.sleep(1000);
                            if (inventory > 0) {
                                inventory--;
                            }
                            System.out.println(inventory);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            zk.unlock();
                            //zk.close();
                            System.out.println("释放锁");
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class zkTest01 implements Runnable {

        public void run() {

        }
    }

}
