package com.company.Laboratory_3_V2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

//Основной класс, необходимый для создания хранилища и методов производства, потребления
class Storage{
    //Для реализации данного класса используется класс Queue
    public final Queue<Integer> queue = new LinkedList<>();
    //Из библиотеки concurent используются семафоры для распределения процессов производства и потребления
    Semaphore producer_sem = new Semaphore(1, true);
    Semaphore consumer_sem = new Semaphore(1, true);
    boolean response = false;
//Метод производства для производителей
    public boolean produce(int num, String name){
        try{
            producer_sem.acquire();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        if (response){
            producer_sem.release();
            return response;
        }
//Определяется моменты производства, если количество произвдённых элементов меньше максимума то будет производится
        if(queue.size() <= 6){
            queue.add(num);
            producer_sem.release();
            //В случае полного хранилища выдаётся сообщение о полном хранилище
        } else if(queue.size() == 7)
        {
            response = true;
            System.out.println("Storage is full.");
            producer_sem.release();
        } else {
            System.out.println("Error has acquired.");
        }
        return response;
    }
//Данный метод необходим для реализиции потребления
    public int get(String name){
        int number = 0;
        //Из хранилища вычитаются элементы, симулируя потребление
        try{
            consumer_sem.acquire();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        //При условии равное true, потребитель начнёт потребление и в случае пустого хранилища выведет сообшение
        if (response){
            if (queue.isEmpty()){
                //Для данного потока ответ выставляет false и семафор позволяет активировать в последющем потребителей после производства производителями
                response = false;
                System.out.println("Storage is empty.");
                consumer_sem.release();
                return number;
            }
            number = queue.element();
            queue.remove();
        }
        consumer_sem.release();
        return number;
    }

}
//Класс производителей
class Producer extends Thread{
    //Используется циклический барьер для контроля всех производителей после потребления
    private CyclicBarrier barrier;
    private Storage storage;
    private String name;
    int[] odd = new int[]{1,3,5,7,9,11,13,15,17,19,21,23,25};
    static int amountMax = 0;

    public Producer(Storage storage, CyclicBarrier barrier, String name){
        this.storage = storage;
        this.barrier = barrier;
        this.name = name;
    }

    public void run(){
        boolean response = true; //Need of change
        int choose;
        int produce = 0;
        while (true){
            try{
                //Производство максимального количества для всех производителей
                if (amountMax < 35){
                    //Добавление и выбор случайного элемента
                    choose = (int)(Math.random()*5);
                    response = storage.produce(odd[choose], name);
                    //В случае ответа true барьер активируется и остальные потоки будут производить до того пока каждый не произведёт элемент
                    if (response){
                        barrier.await();
                    } else {
                        System.out.println(name + " produced " + odd[choose]);
                        produce++; amountMax++;
                        sleep((int) (Math.random()*1000));
                    }

                } else {
                    choose = (int)(Math.random()*5);
                    response = storage.produce(odd[choose], name);
                    if (response){
                        barrier.await();
                    }
                    sleep(1000);
                    System.out.println(name + " produced " + produce + " odd numbers." + " And is ready.");
                    break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
//Класс потребитель
class Consumer extends Thread{

    //Используется класс циклический барьер для контроля потребления
    CyclicBarrier cyclicBarrier;
    private Storage storage;
    private String name;
    static int amountMax = 0;
    public Consumer(Storage storage, CyclicBarrier cyclicBarrier, String name){
        this.storage = storage;
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    public void run(){
        int temp = 0;
        int consumption = 0;

        while (true){
            try{
                if(amountMax < 35){
                    temp = storage.get(name);
                    if (temp == 0){
                        cyclicBarrier.await();
                    } else {
                        System.out.println("The "+ name +" consumed " + temp);
                        consumption++;
                        amountMax++;
                        System.out.println("Consumed: " + amountMax);
                        sleep((int) (Math.random() * 1000));
                    }
                } else {
                    temp = storage.get(name);
                    sleep(1000);
                    System.out.println(name + " consumed "+ consumption + " elements. And get what he get.");
                    break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
//Основной класс в котором активируются потоки производителей и потребителей
public class MainLaunch {
    public static void main(String[] args) {
        Storage storage = new Storage();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6);
        Producer producer1 = new Producer(storage,cyclicBarrier,"Producer 1");
        Producer producer2 = new Producer(storage,cyclicBarrier,"Producer 2");
        Consumer consumer1 = new Consumer(storage, cyclicBarrier, "Consumer 1");
        Consumer consumer2 = new Consumer(storage, cyclicBarrier, "Consumer 2");
        Consumer consumer3 = new Consumer(storage, cyclicBarrier, "Consumer 3");
        Consumer consumer4 = new Consumer(storage, cyclicBarrier, "Consumer 4");
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
    }
}
