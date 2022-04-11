package com.company.Laboratory_5;

import java.util.concurrent.locks.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class Philosopher extends Thread {
    public Philosopher(Forks forks) {
        this.forks = forks;
        this.currentState = state.Hungry;
        this.name = currentName++;
        System.out.println("Philosopher #" + name + " has riched the table!");
    }

    @Override
    public void run() {
        while (true) {
            switch (currentState) {
                case Hungry:
                    if (wasHungry == false) {
                        System.out.println("Philosopher #" + name + " is hungry");
                    }
                    wasHungry = true;
                    if (forks.takeForks(name) == true) {
                        this.currentState = state.Eating;
                    }
                    break;
                case Eating:
                    System.out.println("Philosopher #" + name + " is eating");

                    try {
                        Thread.sleep((int) (Math.random() * 1000));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    forks.leaveForks(name);
                    this.currentState = state.Thinking;
                    wasHungry = false;
                    break;
                case Thinking:
                    System.out.println("Philosopher #" + name + " is thinking");

                    try {
                        Thread.sleep((int) (Math.random() * 2000));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.currentState = state.Hungry;
                    wasHungry = false;
                    break;
            }
        }
    }

    private int name;
    private state currentState;

    private enum state {Hungry, Eating, Thinking}

    ;
    private Forks forks;
    private boolean wasHungry = false;
    private static int currentName = 0;
}

class Forks {
    public Forks(int number) {
        this.number = number;
        this.forks = new state[number];
        for (int i = 0; i < number; i++) {
            forks[i] = state.Free;
        }
        System.out.println("Forks are created!");
        printState();
    }

    public boolean takeForks(int position) {
        boolean success = false;
        lock.lock();
        state leftState;
        state rightState;
        if (position == 0) {
            leftState = forks[number - 1];
        } else {
            leftState = forks[position - 1];
        }

        rightState = forks[position];

        if (leftState == state.Free && rightState == state.Free) {
            if (position == 0) {
                forks[number - 1] = state.inUse;
            } else {
                forks[position - 1] = state.inUse;
            }
            forks[position] = state.inUse;
            System.out.println("Philosopher #" + position + " has taken the forks.");
            printState();
            success = true;
        }
        lock.unlock();
        return success;
    }

    public void leaveForks(int position) {
        lock.lock();
        state leftState;
        state rightState;
        if (position == 0) {
            forks[number - 1] = state.Free;
        } else {
            forks[position - 1] = state.Free;
        }
        forks[position] = state.Free;
        System.out.println("Philosopher #" + position + " has left the forks.");
        printState();
        lock.unlock();
    }


    private void printState() {
        System.out.print("Current state: ");
        for (int i = 0; i < number; i++) {
            System.out.print(i + ":" + forks[i] + "  ");
        }
        System.out.println();
    }

    private int number;
    private state forks[];

    private enum state {Free, inUse}

    ;
    private Lock lock = new ReentrantLock();
}

public class Main {
    public static void main(String[] args) {
        int number = 5;
        Forks forks = new Forks(number);
        Philosopher[] philosophers = new Philosopher[number];
        for (int i = 0; i < number; i++) {
            philosophers[i] = new Philosopher(forks);
        }
        for (int i = 0; i < number; i++) {
            philosophers[i].start();
        }
    }
}
