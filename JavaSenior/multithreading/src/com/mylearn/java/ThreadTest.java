package com.mylearn.java;

/**
 * @Description 多线程的创建 方式一：继承于Thread类
 * 1.创建一个继承于Thread类的子类
 * 2.重写Thread类的run()
 * 3.创建Thread类的子类的对象
 * 4.通过此对象调用start()
 * @author Zeqing Li Email:lizeqing77@163.com
 * @date 2022-04-02 10:50
 */
/*
* 例子：遍历100以内的所有偶数
* */
//1.创建一个继承于Thread类的子类
class MyThread extends Thread{
    //2.重写Thread类的子类run()
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0){
                System.out.println(i);
            }
        }

    }
}

public class ThreadTest {
    public static void main(String[] args) {
        //3.创建Thread类的子类的对象
        MyThread t1 = new MyThread();
        //4.通过此对象调用start():1.启动当前线程2.调用当前线程的run()
        t1.start();

        
        System.out.println("Hello");
    }
}
