package com.company.Concurency;
//Semaphore

import java.util.concurrent.*;

public class App{
    public static void main(String[] args) throws Exception{

//        Semaphore sem = new Semaphore(0);
        ExecutorService executor = Executors.newCachedThreadPool();

        for(int i=0; i<200; i++){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);


//        sem.release();
//        sem.acquire();

//        System.out.println("Available permits: " + sem.availablePermits());
    }
}
