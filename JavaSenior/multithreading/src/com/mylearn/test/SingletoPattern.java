package com.mylearn.test;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 单例模式懒汉式 解决该线程不安全问题
 * @date 2022-04-08 14:27
 */
public class SingletoPattern {

}


//懒汉式单例模式
class Bank{

    private Bank(){}
    private static Bank instance = null;

    //方式一：同步方法
    public static synchronized Bank getInstance(){//此处的同步显示器为：Bank.class
        if (instance == null){
            instance = new Bank();
        }
        return instance;

        //方式二：同步代码块
//        if (instance == null){
//            synchronized (Bank.class){
//                if (instance == null){
//                    instance = new Bank();
//                }
//            }
//        }
//        return instance;

    }


}