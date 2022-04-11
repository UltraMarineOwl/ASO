package com.company.Laboratory_5_V1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread{

    int name;
    private enum state{
        Hungry,
        Eating,
        Thinking
    }


    public Philosopher(int name){
        this.name = name;

    }
}

class Forks{

    int numberOfForks;
    private enum state{
        Free,
        beingUsed
    }
    private state forks[];
    private Lock forkLock = new ReentrantLock();

    public Forks(int numberOfForks){
        this.numberOfForks = numberOfForks;
        this.forks = new state[numberOfForks];
        for (int i =0; i < numberOfForks; i++){
            forks[i]=state.Free;
            System.out.println("Fork number " +forks[i] + " is created.");
        }
    }

    public boolean takeForks (){
        return false;
    }

    public void leaveForks(){

    }
}



public class Phil_dinner {

}
