package com.mylearn.test;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 例子：创建三个窗口卖票，总票为100张，使用继承Thread类的方式
 * 使用方式一：同步代码块的方式解决线程安全问题
 * @date 2022-04-08 9:40
 */

class Window extends Thread {
    private static int ticket = 100;

    private static Object obj = new Object();

    @Override
    public void run() {


            while (true) {
                synchronized (obj) {
                if (ticket > 0) {
                    System.out.println(getName() + ":卖票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }

    }
}

public class WindowsTest4 {
    public static void main(String[] args) {
        Window w1 = new Window();
        Window w2 = new Window();
        Window w3 = new Window();

        w1.setName("窗口1");
        w2.setName("窗口2");
        w3.setName("窗口3");

        w1.start();
        w2.start();
        w3.start();
    }
}
