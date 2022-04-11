package com.company.Laboratory_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch implements ActionListener {

    JFrame frame = new JFrame();
    JButton startButton = new JButton("START");
    JButton resetButton = new JButton("RESET");
    JLabel timeLabel = new JLabel();
    int elapsedTime = 0;
//    int elapsedTime = 60000; //hold milliseconds after timer starts
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    boolean started = false;
    //display 0's before timer start
    String seconds_string = String.format("%02d",seconds);
    String minutes_string = String.format("%02d",minutes);
    String hours_string = String.format("%02d",hours);
    //Timer do something every 1 second
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Display time in right format
            elapsedTime += 1000;
            hours = (elapsedTime/3600000);
            minutes = (elapsedTime/60000) % 60;
            seconds = (elapsedTime/1000) % 60;
            //Update string
            seconds_string = String.format("%02d",seconds);
            minutes_string = String.format("%02d",minutes);
            hours_string = String.format("%02d",hours);
            timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);

        }
    });


    Stopwatch(){
        //Put the text at the window
        timeLabel.setText(hours_string + ":" + minutes_string +":" + seconds_string);
        //Set bounds for the text
        timeLabel.setBounds(100,100,200,100);
        timeLabel.setFont(new Font("Ink Free", Font.PLAIN, 35));

        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

        startButton.setBounds(100,200,100,50);
        startButton.setFont(new Font("Ink Free", Font.PLAIN, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        resetButton.setBounds(200,200,100,50);
        resetButton.setFont(new Font("Ink Free", Font.PLAIN, 20));
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);


        frame.add(startButton);
        frame.add(resetButton);
        frame.add(timeLabel);

        //Ley you close the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //Returns the object on which the event occurred.
        if(e.getSource() == startButton){
            start();
            if(started == false){
                started=true;
                startButton.setText("STOP");
                start();
            } else {
                started=false;
                startButton.setText("START");
                stop();
            }
        }

        if(e.getSource() == resetButton){
            started = false;
            startButton.setText("START");
            reset();
        }
    }

    void start(){
        timer.start();

    }

    void stop(){
        timer.stop();
    }

    void reset(){
        timer.stop();
        //Set every value to zero to restart the timer
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        seconds_string = String.format("%02d",seconds);
        minutes_string = String.format("%02d",minutes);
        hours_string = String.format("%02d",hours);
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);

    }
}
