package com.mylearn.test;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 创建三个窗口卖票，总票数为100，实现Runnable接口的方式
 * @date 2022-04-07 22:09
 */
/*
* 1.问题：卖票过程中，出现了重票、错票 -->出现了线程安全问题
* 2.出现问题的原因：当某个线程操作车票的过程中，尚未操作完成时其他线程参与进来，也操作车票。
* 3.如何解决：当一个线程a在操作ticket的时候，其他线程不能参与进来，直到线程a操作完ticket时，
*           其他线程才可以开始操作ticket。这种情况即使线程a出现了阻塞，也不能被改变。
* 4.在Java中，我们通过同步机制，来解决线程的安全问题。
*
* 方式一：同步代码块
*
*   synchronized(同步监视器){
*           //需要被同步的代码
*   }
* 说明：1.操作共享数据的代码，既为需要被同步的代码
*      2.共享数据：多个线程共同操作的变量，比如：ticket就是共享数据。
*      3.同步监视器，俗称：锁。任何一个类的对象，都可以充当锁。
*           要求：多个线程必须要共用同一把锁。
*
* 方式二：同步方法
*
* */
class Windows implements Runnable{

    private int ticket = 100;

    Object obj = new Object();
    @Override
    public void run() {
        while (true){
            synchronized(obj) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

public class WindowsTest3 {
    public static void main(String[] args) {

        Windows w = new Windows();

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
