package com.mylearn.java;

/**
 * @author Zeqing Li Email:lizeqing77@163.com
 * @Description 生产者/消费者问题
 * 生产者Productor将产品交给店员Clerk，而消费者Customer从店员处取走产品，
 * 店员一次只能持有固定数量的产品(比如20)，如果生产者试图生产更多的产品，
 * 店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；
 * 如果店中没有产品了，店员会告诉消费者等一下，如果店中有了产品了，
 * 再通知消费者取走产品。
 *
 * 分析：
 * 是否是多线程问题: 生产者线程、消费者线程
 * 是否有共享数据： 店员Clerk 或产品
 * 如何解决线程安全问题：同步机制，三种方法 synchronized 同步代码块、同步方法，lock()方法
 * 是否涉及线程通信:是 wait()、notify()、notifyAll()
 * @date 2022-04-09 10:45
 */


class Clerk{

    private int productCount = 0;
    //生产产品
    public synchronized void produceProduct() {
        if (productCount < 20){
            productCount++;
            System.out.println(Thread.currentThread().getName() + "开始生产第" + productCount + "个产品");

            //唤醒消费产品
            notify();
        }else{
            //等待
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    //消费产品
    public synchronized void consumProduct() {
        if (productCount > 0){
            System.out.println(Thread.currentThread().getName() + "开始消费第" + productCount + "个产品");
            productCount--;

            //唤醒生产产品
            notify();
        }else{
            //等待
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//生产者
class Producer extends Thread{
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName() + ":开始消费产品。。。。。。");
        while (true){

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}

//消费者
class Consumer extends Thread{

    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName() + ":开始消费产品。。。。。。");
        while (true){

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumProduct();
        }
    }
}

public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer p1 = new Producer(clerk);
        p1.setName("生产者1");

        Consumer c1 = new Consumer(clerk);
        c1.setName("消费者1");


        p1.start();
        c1.start();
    }
}
