package com.company.Laboratory_2;

public class Main {
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        StopwatchRevers stopwatchRevers = new StopwatchRevers();
        NewTimer newTimer = new NewTimer();
        newTimer.timer.schedule(newTimer.task, 1000, 2000);
    }
}
