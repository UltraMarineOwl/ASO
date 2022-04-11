package com.company.Laboratory_5_V4;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws  Exception{
        Philosopher philosopher[] = new Philosopher[5];
        Object fork[] = new Object[5];

        for(int i=0;i<philosopher.length;i++){
            fork[i] = new Object();
        }

        for(int i=0;i<philosopher.length;i++){
            Object leftFork = fork[i];
            Object rightFork = fork[(i+1)%5];

            //utilizam if pentru a nu obtine cazul in care toti filozofii au luat cate un betisor si stau (deadlock)
            if(i == philosopher.length-1){
                philosopher[i] = new
                        Philosopher(leftFork,rightFork);
            }else{
                philosopher[i] = new
                        Philosopher(rightFork,leftFork);
            }
            Thread t = new Thread(
                    philosopher[i] ," Filozoful: "+(i+1));
            t.start();

        }
    }
}

class Philosopher implements Runnable{
    private Object leftFork;
    private Object rightFork;
    private int caloriesLeft = 1500;
    String[] motive = {"viata", "existenta", "bautura", "bani", "java"};

    public Philosopher(Object leftFork,Object rightFork){
        this.leftFork= leftFork;
        this.rightFork= rightFork;
    }
    // functia care prelucreaza actiunea mananca, gandeste, ia sau lasa betisorul
    public void doAction(String action) throws InterruptedException{
        System.out.println(
                Thread.currentThread().getName()+" "+action);
        Thread.sleep((int)(Math.random()*100));

    }
    public void run() {
        String motiv1 = motive[new Random().nextInt(motive.length)];
        try{
            while(caloriesLeft > 0){
                doAction(
                        "Au ramas: "+this.caloriesLeft+" calorii    : Mediteaza despre " +motiv1+".");
                synchronized (leftFork){
                    doAction(
                            "Au ramas: "+this.caloriesLeft+
                                    " : A ridicat betisorul stang");
                    synchronized (rightFork){
                        doAction(
                                "Au ramas: "+this.caloriesLeft+
                                        " : A ridicat betisorul drept, si incepe să manance");
                        doAction(
                                "Au ramas: "+this.caloriesLeft+
                                        " : pune jos betisorul drept");
                        this.caloriesLeft-=300;
                    }
                    doAction(
                            "Au ramas: "+this.caloriesLeft+
                                    " : pune jos betisorul stang si incepe sa mediteze despre " + motiv1+".");
                }
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }
    }
}