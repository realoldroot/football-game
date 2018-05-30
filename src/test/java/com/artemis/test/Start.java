package com.artemis.test;

/**
 * @author zhengenshen
 * @date 2018-05-30 10:06
 */

public class Start {

    public static void main(String[] args) throws InterruptedException {

        new Start().init();

        new Thread(() -> System.out.println(StaticFactory.get(1))).start();

        new Thread(() -> System.out.println(StaticFactory.get(2))).start();


        Thread.sleep(5000);
    }


    public void init() {
        Customer c1 = new Customer(1, "张");
        Customer c2 = new Customer(2, "李");
        StaticFactory.add(c1, c2);
    }
}
