package com.company.Laboratory_5_V3;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Philosopher philosopher[] = new Philosopher[15];
        Object fork[] = new Object[15];

//Every philosopher process
        for (int i = 0; i < philosopher.length; i++) {
            fork[i] = new Object();
        }
//Every fork for processes
        for (int i = 0; i < philosopher.length; i++) {
            Object leftFork = fork[i];
            Object rightFork = fork[(i + 1) % 5];

            //Initializing with if, for deadlock avoidance
            if (i == philosopher.length - 1) {
                philosopher[i] = new Philosopher(leftFork, rightFork);
            } else {
                philosopher[i] = new Philosopher(rightFork, leftFork);
            }
            Thread t = new Thread( philosopher[i], " Philosopher: " + (i + 1));
            t.start();

        }
    }

}

class Philosopher implements Runnable {
    private Object leftFork;
    private Object rightFork;
    private int calories = 600;
    String[] think = {"existence", "creation", "human", "theories", "milk", "cheese", "keys", "eeeee", "sandwich", "theology", "ontology", "half-life", "society",
    "more cheese", "politics"
//            , "epistemology", "culture", "bread", "forks", "anime", "hookup", "slavery", "maintenance"

    };

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
//Function of action simulation: eat, think or left fork
    public void doSomething(String action) throws InterruptedException{
            System.out.println(Thread.currentThread().getName() + " " + action);
            Thread.sleep((int) (Math.random() * 1000));
    }


    @Override
    public void run() {
        String thinkAbout = think[new Random().nextInt(think.length)];
        try {
            while(calories > 0){
                doSomething("Left: " + this.calories + " calories.  : Mediate about " + thinkAbout + ".");
                synchronized (leftFork){
                    doSomething("Left: " + this.calories + " : Picked up left fork.");
                    synchronized (rightFork){
                        doSomething("Left: " + this.calories + " : Picked up right fork, and start to eat.");
                        doSomething("Left: " + this.calories + " : left the right fork on table.");
                        this.calories-=300;
                    }
                    doSomething("Left: " + this.calories + " : left the left fork and start to think about " + thinkAbout + ".");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}
