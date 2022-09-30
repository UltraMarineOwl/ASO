package com.company.Laboratory_7;

import static java.lang.Thread.sleep;
import java.util.Scanner;
public class Barber{
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Inrodu numarul de barbieri:");
        int b = sc.nextInt();
        boolean[] isBarberFree = new boolean[b];
        for(int i = 0; i < b; i++){
            isBarberFree[i] = true;
        }
        System.out.println("Introdu numarul de locuri de asteptare:");
        int c = sc.nextInt();
        boolean[] isChairFree = new boolean[c];
        for(int i = 0; i < c; i++){
            isChairFree[i] = true;
        }
        endWorkDay end = new endWorkDay();
        end.start();
        int i = 1;
        while(end.isAlive()){
            Client c1 = new Client(isBarberFree, isChairFree, end);
            c1.setName("Client " + i);
            i++;
            c1.start();
            if(i>20){
                sleep(2500);
            }
        }
    }
}
class Client extends Thread{
    static boolean[] isBarberFree, isChairFree;
    static endWorkDay end;
    public Client(boolean[] isBarberFree, boolean[] isChairFree, endWorkDay end){
        Client.isBarberFree = isBarberFree;
        Client.isChairFree = isChairFree;
        Client.end = end;
    }
    @Override
    public void run(){
        try {
            while(true){
                int timeSleep = (int)(Math.random()*1000+5000);
                sleep(timeSleep);
                if(!end.isAlive()){
                    System.out.println(getName() + " nu poate fi servit, ziua de lucru sa sfirsit ");
                    break;
                }
                System.out.println(getName() + " a intrat în frezerie");
                int i;
                synchronized(this){
                    i = checkBarber();
                }
                if(i!=100){
                    System.out.println(getName() + " sa asezat in fotoliu " + (i+1));
                    timeSleep = (int)(Math.random()*10000+5000);
                    sleep(timeSleep);
                    System.out.println(getName() + " a eliberat fotoliu " + (i+1) + " a iesit din frezerie");
                    synchronized(isBarberFree){
                        isBarberFree[i] = true;
                    }
                    break;
                }else{
                    int j;
                    synchronized(this){
                        j = checkChair();
                    }
                    if(j!=100){
                        System.out.println(getName() + " a ocupat locul de asteptare " + (j+1) + " asteapta rindul");
                        int b = 100;
                        while(b==100){
                            sleep(1);
                            synchronized(this){
                                b = checkBarber();
                            }
                        }
                        synchronized(isChairFree){
                            isChairFree[j] = true;
                        }
                        if(endWorkDay.end>=105){
                            System.out.println(getName() + " nu poate fi servit, ziua de lucru sa sfirsit");
                            break;
                        }
                        System.out.println(getName() + " sa asezat in fotoliu " + (b+1));
                        timeSleep = (int)(Math.random()*10000+5000);
                        sleep(timeSleep);
                        System.out.println(getName() + " a eliberat fotoliu " + (b+1) + " a iesit din frezerie");
                        synchronized(isBarberFree){
                            isBarberFree[b] = true;
                        }
                        break;
                    }else{
                        System.out.println(getName() + " nu sunt locuri de asteptare, paraseste frezeria");
                        sleep(2000);
                    }
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    public synchronized int checkBarber(){
        for(int i = 0; i < isBarberFree.length; i++){
            if(isBarberFree[i]){
                isBarberFree[i] = false;
                return i;
            }
        }
        return 100;
    }
    public synchronized int checkChair(){
        for(int i = 0; i < isChairFree.length; i++){
            if(isChairFree[i]){
                isChairFree[i] = false;
                return i;
            }
        }
        return 100;
    }
}
class endWorkDay extends Thread{
    public static int end = 0;
    @Override
    public void run(){
        while(true){
            try {
                sleep(1000);
                end++;
                if(end>120)
                    break;
            } catch (InterruptedException ex) {}
        }
    }
}
