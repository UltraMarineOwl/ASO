package com.company.Laboratory_4_V1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Public_Library{
    static int books;
    Writer[] writers;
    Reader[] readers;

    static ArrayList<String> library = new ArrayList<>();
    static final ReentrantReadWriteLock reLock = new ReentrantReadWriteLock(true);
    static final Lock writerBlock = reLock.writeLock();
    static final Lock readerBlock = reLock.readLock();

    public Public_Library(int writers, int readers, int books){
        this.readers = new Reader[readers];
        this.writers = new Writer[writers];
        Public_Library.books = books;
        for (int i = 0; i < writers; i++){
            this.writers[i] = new Writer("The writer "+ (i+1));
        }
        for (int i = 0; i < readers; i++){
            this.readers[i] = new Reader("The reader "+ (i+1));
        }

    }

    public void start(){
        for (Writer writer : this.writers){
            writer.start();
        }
        for (Reader reader : this.readers){
            reader.start();
        }
    }

}

class Writer extends Thread{
    String name;
    public final Lock writerBlock = Public_Library.writerBlock;
    ArrayList<String> publicLibrary = Public_Library.library;
    ArrayList<String> wBooks = new ArrayList<>();
    ArrayList<String> booksToWrite = new ArrayList<>(Arrays.asList("a","b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t"));
    static int written = 0;

    public Writer(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while (publicLibrary.size() < Public_Library.books){
            try{
                writerBlock.lock();
                if(publicLibrary.size() < Public_Library.books){
                    String temp = booksToWrite.get(written);
                    if(!publicLibrary.contains(temp)){
                        sleep(1000);
                        publicLibrary.add(temp);
                        wBooks.add(temp);
                        System.out.println(name + " just write " + temp);
                        written++;
                        sleep(100);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                System.out.println("This is finally");
                writerBlock.unlock();
            }
            if (publicLibrary.size() == Public_Library.books){
                System.out.println(name + " write: \n" + wBooks);
            }
        }
    }
}

class Reader extends Thread{
    String name;
    public final Lock readerBlock = Public_Library.readerBlock;
    ArrayList<String> bookReading = new ArrayList<>();
    ArrayList<String> publicLibrary = Public_Library.library;

    public Reader(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while(bookReading.size() < Public_Library.books){
            try {
                if(Public_Library.reLock.isWriteLocked()){
                    System.out.println("Is in the public library: " + name);
                }
                readerBlock.lock();
                int randChoose = (int)(Math.random()*publicLibrary.size());
                if (randChoose < publicLibrary.size()){
                    String temp = publicLibrary.get(randChoose);

                    if(bookReading.size() < Public_Library.books){
                        if(!bookReading.contains(temp)){
                            sleep(200);
                            bookReading.add(temp);
                            System.out.println("Reader "+ name+ " read " + temp);
                            sleep(500);
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                readerBlock.unlock();
            }
        }
        System.out.println(name + " finished reading \n" + bookReading);
    }
}


public class ReaderXWriter {
    public static void main(String[] args) {
        int writers = 20;
        int readers = 34;
        int books = 20;
        Public_Library public_library = new Public_Library(writers, readers, books);
        public_library.start();
    }
}
