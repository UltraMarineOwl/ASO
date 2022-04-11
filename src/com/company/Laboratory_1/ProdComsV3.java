package com.company.Laboratory_1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
//Основной класс для создания и запуска потоков исполнения программы
public class ProdComsV3 {
    //Создаются объекты классов потребителей и производителей с основным объектом ввиде хранилица
    static Storage storage = new Storage();
    static Producer producer1 = new Producer(storage, "Producer 1");
    static Producer producer2 = new Producer(storage, "Producer 2");
    static Consumer consumer1 = new Consumer(storage, "Consumer 1");
    static Consumer consumer2 = new Consumer(storage, "Consumer 2");
    static Consumer consumer3 = new Consumer(storage, "Consumer 3");
    static Consumer consumer4 = new Consumer(storage, "Consumer 4");
    public static void main(String[] args) throws InterruptedException {
        //создание потоков для каждого объекта, для симуляции потребления и производства нечётных чисел
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer1.join();
        consumer2.join();
        consumer3.join();
        consumer4.join();

    }
}
//основной класс в котором складируется и вычитается созданный "материал"
class Storage {
    //Склад реализован при помоци Linked list
    public final Queue<Integer> queue = new LinkedList<>();
    int element1;
    //Синхронизированный для всех производителей метод для производства "материала"
    public synchronized void produce(int a, String name) {
        //Если размер склада равен 6 или 7, что свойственно варианту, то склад будет полон, и производители не смогут поставить ещё один материал, и будет произведенно ожидание, пока не возьмут со склада элемент
        while (queue.size() == 6 || queue.size() == 7) {
            try {
                //Ожидание до потребления
                System.out.println(name  + " is waiting, because storage is full.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Добавление элемента в склад
        queue.add(a);
        System.out.println(name + " put in storage " + a + ". Storage is " + queue.toString());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }
//Метод свойственный для потребителя, для того чтобы взять элемент со склада
    public synchronized int get(String name) {
        //Если Linked list пустой, то потребитель не может взять ничего
        while (queue.size() == 0) {
            try {
                System.out.println(name + " doesn't take anything, storage is empty.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Записываетсся какой элемент взял потребитель и удаляется со склада
        element1 = queue.element();
        queue.remove();
        System.out.println(name+ " consume "+ element1);
        notifyAll();
        return element1;
    }
}
//Класс производитель наследует класс Thread для использования потоков
class Producer extends Thread{
    //Внутри класса создаётся объетка класса Storage, для получения доступа к методу produce
    private Storage storage;
    private String name;
    //В каждом конструкторе объекта класса Producer будет содержаться имя и Storage
    public Producer(Storage storage, String name){
        this.storage = storage;
        this.name = name;
    }
//Основной метод запускающий поток
    public void run(){
        //Используется случайный выбор продукта производимого Producer при помощи класса Random
        Random rand = new Random();
        int[] odd = new int[]{1,3,5,7,9,11,13,15,17,19,21,23,25};
        while (true){
            //Если один из потоков потребителей в текущий момент "живой", то производитель продолжает производить материал
            if(ProdComsV3.consumer1.isAlive() || ProdComsV3.consumer2.isAlive() || ProdComsV3.consumer3.isAlive() || ProdComsV3.consumer4.isAlive()){
                //Выбирается случайный элемент из массива и добавляется в Storage
                int index1 = rand.nextInt(odd.length);
                storage.produce(odd[index1], name);
            }
            else {
                //В ином случае производство останавливается
                System.out.println(name + " stopped.");
                currentThread().stop();
                break;
            }
        }
    }
}
//Класс потребителя, для ывчитания элементов из класса Storage и добавления в свой Linked list
class Consumer extends Thread{
    private Storage storage;
    private String name;
    //Необходим для удолетворения потребителей
    public Queue<Integer> consumerQueue = new LinkedList<>();

    public Consumer(Storage storage, String name){
        this.storage = storage;
        this.name = name;
    }

    public void run(){
        //Пока linked list потребителя не будет полон, поток будет действовать
        while(consumerQueue.size()!=47){
            //Вычитание одной единицы материала из Storage
            consumerQueue.add(storage.get(name));
        }
        System.out.println(name + " consume " + consumerQueue.toString() + " and is satisfied.");
    }
}
