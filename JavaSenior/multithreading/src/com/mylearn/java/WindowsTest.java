package com.mylearn.java;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 创建三个窗口卖票，总票数为100，实现Runnable接口的方式
 * @date 2022-04-07 22:09
 */
class Windows implements Runnable{

    private int ticket = 100;
    @Override
    public void run() {
        while (true){
            if (ticket > 0){
                System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                ticket--;
            }else {
                break;
            }
        }
    }
}

public class WindowsTest {
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
