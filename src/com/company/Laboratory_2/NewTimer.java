package com.company.Laboratory_2;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NewTimer {
//    Calendar date = Calendar.getInstance();
//        date.set(Calendar.YEAR,2022);
//        date.set(Calendar.MONTH,Calendar.JANUARY);
//        date.set(Calendar.DAY_OF_MONTH, 24);
//        date.set(Calendar.HOUR_OF_DAY, 19);
//        date.set(Calendar.MINUTE, 59);
//        date.set(Calendar.SECOND, 0);
//        date.set(Calendar.MILLISECOND, 0);
    Timer timer = new Timer();
    TimerTask task = new TimerTask(){ //anonymous inner class
        int counter = 10;
        @Override
        public void run() {
                if (counter > 0) {
                    System.out.println("t-minus " + counter + " seconds");
                    counter -= 1;
                } else {
                    System.out.println("The timer is out.");
//                    timer.cancel();
                }
                if (counter == 5) {
                    System.out.println("5 seconds remain.");
                }
//                if(counter == 0){
//                    break;
//                }
        }

    };

}
