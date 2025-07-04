/*
*File: Timer.java
* Author: WillWongka
* Course: CMSC 335

Description:
This class represents a timer used in the simulation. It extends SwingWorker to
run tasks in the background, updating a JTextField with the elapsed time.
*/
package org.example;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Timer extends SwingWorker<Void, Integer> {

    // AtomicBooleans to control the timer state
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private final AtomicBoolean pause = new AtomicBoolean(false);

    private final JTextField textField;
    private final Background backgroundCanvas;
    private int intervalMillis = 1000;

    /**
     * Constructor for the Timer class.
     *
     * @param textField         The JTextField to display the elapsed time.
     * @param backgroundCanvas Reference to the Background canvas for updating.
     */
    public Timer(JTextField textField, Background backgroundCanvas) {
        this.textField = textField;
        this.backgroundCanvas = backgroundCanvas;
    }

    /**
     * Main background task of the timer. Updates elapsed time and publishes
     * updates to the UI at regular intervals until stopped.
     *
     * @return Void
     * @throws Exception if an error occurs during execution.
     */
    @Override
    protected Void doInBackground() throws Exception {
        int seconds = 0;
        while (!stop.get()) {
            if (!pause.get()) {
                seconds++;
                publish(seconds);
            }
            TimeUnit.MILLISECONDS.sleep(intervalMillis);
        }

        return null;
    }

    /**
     * Updates the UI with the elapsed time.
     *
     * @param chunks List of integers representing elapsed seconds.
     */
    @Override
    protected void process(List<Integer> chunks) {
        Integer second = chunks.get(chunks.size() - 1);
        String time = String.format("%02d:%02d", second / 60, second % 60);
        textField.setText(time);
        backgroundCanvas.repaint();
    }

    //Resumes the timer
    public void play() {
        pause.set(false);
    }

    //Pauses the timer
    public void pause() {
        pause.set(true);
    }

    //Stops the timer
    public void stop() {
        stop.set(true);
    }

    // Checks if the timer is currently paused.
    public boolean isPaused() {
        return pause.get();
    }

    // Checks if the timer has been stopped
    public boolean isStopped() {
        return stop.get();
    }

    //Sets the interval between timer updates
    public void setIntervalMillis(int intervalMillis) {
        this.intervalMillis = intervalMillis;
    }
}
