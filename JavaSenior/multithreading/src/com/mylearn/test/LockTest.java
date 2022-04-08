package com.mylearn.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 方式三：Lock锁 --- JDK5.0新增
 * @date 2022-04-08 15:29
 */

class window5 implements Runnable {
    private static int ticket = 100;

    //1.实例化ReentrantLock
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                //2.调用锁定方法：lock
                lock.lock();

                if (ticket > 0) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            } finally {
                //3.调用解锁方法：unlock
                lock.unlock();
            }

        }

    }
}

public class LockTest {
    public static void main(String[] args) {
        window5 w = new window5();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }

}
