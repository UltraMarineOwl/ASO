package com.company.Concurency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable{

    private CountDownLatch latch;

    public Processor(CountDownLatch latch){
        this.latch = latch;
    }

    public void run(){
        System.out.println("Started.");

        try{
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        latch.countDown();//count down by one
    }
}


public class App1 {
    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(3);
        //one or more latch waits till 0, then 0 thread will proceed

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for(int i=0; i<3; i++){
            executor.submit(new Processor(latch));
        }

        try {
            latch.await();//waits until latch counts to zero
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");


    }
}
