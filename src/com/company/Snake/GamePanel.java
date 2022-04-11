package com.company.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {


    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;//dimensions for height and the high
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static  final int DELAY = 75; //how fast the game is running
    final int x[] = new int[GAME_UNITS];//snake units
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten ;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);//action listener interface
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

    }
    public void newApple(){

    }
    public void move(){

    }
    public void checkApple(){

    }
    public void checkCollisions(){

    }
    public void gameOver(){

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){

        }
    }
}
