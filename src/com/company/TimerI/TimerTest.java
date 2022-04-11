package com.company.TimerI;

import java.util.*;

public class TimerTest {
    public static void main(String[]args){

        Timer timer = new Timer();

        TimerTask task = new TimerTask(){ //anonymous inner class
            int counter = 10;

            @Override
            public void run() {
                if(counter > 0){
                    System.out.println("t-minus "+counter+" seconds");
                    counter -=5;
                } else {
                    System.out.println("The bomb detonated.");
                    timer.cancel();
                }
            }

        };


        TimerTask task1 = new TimerTask() {

            @Override
            public void run() {
                Scanner myObj = new Scanner(System.in);
                System.out.println("To prevent explosion type 1.");
                int exp = myObj.nextInt();
                if(exp == 1){
                    System.out.println("You prevent the explosion.");
                    timer.cancel();
                } else{
                    System.out.println("Bomb just detonated!");
                    timer.cancel();
                }
            }
        };



        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR,2022);
        date.set(Calendar.MONTH,Calendar.JANUARY);
        date.set(Calendar.DAY_OF_MONTH, 24);
        date.set(Calendar.HOUR_OF_DAY, 19);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

//        timer.schedule(task, date.getTime());
//        timer.schedule(task, 3000);

//        timer.scheduleAtFixedRate(task, 0, 1000);
        timer.scheduleAtFixedRate(task, date.getTime(), 5000);
        timer.schedule(task1, date.getTime(), 5000);
//        timer.scheduleAtFixedRate(task, 1000, 1000);
//        timer.schedule(task1, 6000);


        }
}
