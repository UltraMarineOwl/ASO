package com.company.Six;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Six {
    public static void main(String[] args){
        var philosopher = new ArrayList<Thread>();
        Eating eating = new Eating();
        eating.addFork();
        philosopher.add(new Thread(new Philosopher(1,2,eating)));
        philosopher.add(new Thread(new Philosopher(2,3,eating)));
        philosopher.add(new Thread(new Philosopher(3,4,eating)));
        philosopher.add(new Thread(new Philosopher(4,5,eating)));
        philosopher.add(new Thread(new Philosopher(5,6,eating)));
        philosopher.add(new Thread(new Philosopher(6,7,eating)));
        philosopher.add(new Thread(new Philosopher(7,1,eating)));

        philosopher.forEach(Thread::start);
    }
}
class Eating{

    private final HashMap<Integer,Integer> fork = new HashMap<>(); //0 - её нет в наличии , 1 - есть в наличии
    ReentrantLock locker;
    Condition condition;

    Eating(){
        locker = new ReentrantLock();
        condition = locker.newCondition();
    }

    void addFork() {
        fork.put(1, 1);
        fork.put(2, 1);
        fork.put(3, 1);
        fork.put(4, 1);
        fork.put(5, 1);
        fork.put(6, 1);
        fork.put(7, 1);
    }
    void getFork(int left,int right){

        locker.lock();
        try {

            while (true) {
                if (fork.get(left) == 1 && fork.get(right) == 1) {
                    fork.put(left, 0);
                    fork.put(right, 0);
                    System.out.println("Philosopher give " + left + " and " + right + " forks || Status table: "+fork);
                    Thread.sleep(4000);
                    fork.put(left, 1);
                    fork.put(right, 1);
                    System.out.println("Philosopher was eating         || Status table: "+fork);
                    condition.signalAll();

                }

                condition.await();
            }
        }
        catch (InterruptedException e){
            System.out.println("Error");
        }
        finally {
            locker.unlock();
        }


    }
}

class Philosopher implements Runnable{
    Eating eating;
    int right,left;
    public Philosopher(int left,int right,Eating eating){
        this.left=left;
        this.right=right;
        this.eating=eating;
    }
    @Override
    public void run(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eating.getFork(left,right);
    }
}
