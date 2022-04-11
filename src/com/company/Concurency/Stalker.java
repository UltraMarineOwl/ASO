package com.company.Concurency;

import java.util.concurrent.*;

class Traveler {
public static void main(String args[]) {
CyclicBarrier cb = new CyclicBarrier(3, new BarAction());

        System.out.println("Запуск группировок");

        new MyThread(cb, "Monolit");
        new MyThread(cb, "Svoboda");
        new MyThread(cb, "Dolg");
}
}

        // Поток исполнения, использующий барьер типа CyclicBarrier

class MyThread implements Runnable {
CyclicBarrier cbar;
String name;

        MyThread(CyclicBarrier c, String n) {
        cbar = c;

        name = n;
        new Thread(this).start();
}

        public void run() {
        System.out.println(name);

        try {
            cbar.await();
        } catch (BrokenBarrierException exc) {
	System.out.println(exc);
        } catch (InterruptedException exc) {
	System.out.println(exc);
        }
}
}

        // Объект этого класса вызывается по достижении
// барьера типа CyclicBarrier

class BarAction implements Runnable {
public void run() {
        System.out.println("Зона достигнута");
}
}