package com.company.Laboratory_3;

import java.util.LinkedList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

class Stock {
    private LinkedList<Character> stock = new LinkedList<>();
    Semaphore producer_sem = new Semaphore(1, true);
    Semaphore consumer_sem = new Semaphore(1, true);
    boolean response = false;

    public boolean store (char n, String name) {
        try {
            producer_sem.acquire();

        } catch (InterruptedException e){
            e.printStackTrace();
        }
        if (response) {
            producer_sem.release();
            return  response;
        }
        if (stock.size() <= 8) {
            stock.add(n);
            producer_sem.release();
        } else if (stock.size() == 9) {
            response = true;
            System.out.println("Stock is full");
            producer_sem.release();
        } else {
            System.out.println("Error");
            producer_sem.release();
        }
        return response;
    }

    public Character load (String name) {
        Character character = null;
        try {
            consumer_sem.acquire();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        if (response) {
            if (stock.isEmpty()){
                response=false;
                System.out.println("Stock is empty");
                consumer_sem.release();
                return character;
            }
            character = stock.getFirst();
            stock.removeFirst();
        }
        consumer_sem.release();
        return character;
    }
}

class Consumer extends Thread {
    CyclicBarrier barr;
    private Stock data;
    private String name;
    static int max = 0;
    public Consumer(Stock data, CyclicBarrier barr, String name) {
        this.data = data;
        this.name = name;
        this.barr = barr;
    }

    public void run() {
        Character tmp=null;
        int cons = 0;
        while(true) {
            try {
                if (max < 54) {
                    tmp = data.load(name);
                    if (tmp == null) {
                        barr.await();
                    } else {
                        System.out.println("" + name + " consumed " + tmp);
                        cons++;
                        max++;
                        System.out.println("Consumed: " + max);
                        sleep((int) (Math.random() * 1000));
                    }
                } else {
                    tmp = data.load(name);
                    sleep(2000);
                    System.out.println(name+" consumed "+cons+" elements");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private CyclicBarrier barr;
    private Stock data;
    private String name;
    private char[] dep = new char []{'a','e','i','o','u'};
    static int max = 0;
    public Producer(Stock data, CyclicBarrier barr, String name) {
        this.data = data;
        this.name = name;
        this.barr = barr;
    }

    public void run() {
        int tmp;
        boolean response;
        int prod = 0;

        while(true){
            try {
                if(max < 54) {
                    tmp = (int) (Math.random() * 5);
                    response = data.store(dep[tmp], name);

                    if(response) {
                        barr.await();
                    }
                    else {
                        System.out.println(name + " produced " + dep[tmp]);
                        prod++;
                        max++;
                        sleep((int) (Math.random() * 1000));
                    }
                }
                else {
                    tmp = (int) (Math.random() * 5);
                    response = data.store(dep[tmp], name);
                    if(response) {
                        barr.await();
                    }

                    sleep(2000);
                    System.out.println(name+" produced "+prod+" elements");
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class Lab3 {
    public static void main (String[] args) {
        Stock data = new Stock();
        CyclicBarrier bar = new CyclicBarrier(6);
        Producer p1 = new Producer(data, bar, "Producer 1");
        Producer p2 = new Producer(data, bar, "Producer 2");
        Producer p3 = new Producer(data, bar, "Producer 3");
        Consumer c1 = new Consumer(data, bar, "Consumer 1");
        Consumer c2 = new Consumer(data, bar, "Consumer 2");
        Consumer c3 = new Consumer(data, bar, "Consumer 3");
        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }
}