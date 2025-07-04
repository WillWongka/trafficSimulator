/*
*File: SimulationOptions.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the menu panel which allows users to add cars, or start, pause, and stop the simulation.
*/
package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationOptions extends JPanel{

    private JTextField timerLabel;
    private Timer timer;
    private Background backgroundCanvas;
    private final int rows, columns, cars;

    /**
     * Constructor for the main menu panel.
     *
     * @param backgroundCanvas The background canvas for the simulation.
     */
    public SimulationOptions(Background backgroundCanvas) {
        this.backgroundCanvas = backgroundCanvas;
        this.rows = backgroundCanvas.getRowCount();
        this.columns = backgroundCanvas.getColumnCount();
        this.cars = backgroundCanvas.getCarCount();


        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField outputField = new JTextField(50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(outputField, gbc);

        JButton startBtn = new JButton("Start");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        this.add(startBtn, gbc);
        startBtn.addActionListener(event -> {
            if (timer == null || timer.isStopped()){
                timer = new Timer(this.timerLabel, this.backgroundCanvas);
                this.backgroundCanvas.executorService.submit(timer);
                this.backgroundCanvas.createTimer(timer);
                this.backgroundCanvas.executeWorkers();
            } else {
                timer.play();
            }
        });

        JButton pauseBtn = new JButton("Pause");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(pauseBtn, gbc);
        pauseBtn.addActionListener( event -> timer.pause());

        JButton stopBtn = new JButton("Stop");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(stopBtn, gbc);
        stopBtn.addActionListener(event -> {
            timer.stop();
            timerLabel.setText("00:00");

            List<TrafficLight>trafficLights = backgroundCanvas.trafficLights;
            this.getParent().remove(backgroundCanvas);
            this.backgroundCanvas = new Background(rows, columns, cars);
            this.getParent().add(this.backgroundCanvas, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        JButton addCarBtn = new JButton("Add a car");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(addCarBtn, gbc);
        addCarBtn.addActionListener(event -> {
            Car car = this.backgroundCanvas.addRandomCar();
            car.passTimer(timer);
            this.backgroundCanvas.executorService.submit(car);
            this.backgroundCanvas.repaint();
        });

        timerLabel = new JTextField(4);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(timerLabel, gbc);

    }
}