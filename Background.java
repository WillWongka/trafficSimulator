/*
*File: Background.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the background where most of the drawings for the traffic simulation takes place.
*/

package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Background extends JPanel{

    private final int rowCount;
    private final int columnCount;
    private final int carCount;
    ArrayList<Road> roads = new ArrayList<>();
    ArrayList<TrafficLight> trafficLights = new ArrayList<>();
    ArrayList<VerticalRoad> vRoads = new ArrayList<>();
    ArrayList<HorizontalRoad> hRoads = new ArrayList<>();
    Timer timer;
    ExecutorService executorService;

    /**
     * Main constructor for main frame of application
     */
    Background(int rowCount, int columnCount, int carCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.carCount = carCount;
        int CARS = 10;
        this.executorService = Executors.newFixedThreadPool((rowCount * columnCount) + carCount + CARS + 1);

        this.setPreferredSize(new Dimension(900, 700));
        this.setBackground(Color.darkGray);
        initialize();
    }

    /*
     * Paints the roads and cars on the panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        roads.forEach(road -> road.draw(g));
        trafficLights.forEach(trafficLight -> trafficLight.draw(g));
    }

    /**
     * Adds cars using addRandomCar()
     */
    public void addCars(){
        for (int i = 0; i < carCount ; i++) {
            addRandomCar();
        }
    }

    /**
     * adds a car at a random location on road
     */
    public Car addRandomCar(){
        Random random = new Random();
        Road road = roads.get(random.nextInt(roads.size()));
        Car car = new Car(road);
        road.addCar(car);
        this.revalidate();
        return car;
    }

    /**
     * Timer to pause, stop and play simulation (from Timer class)
     */
    public void createTimer(Timer timer){
        this.timer = timer;
        roads.forEach(road -> {
            ArrayList<Car> cars = (ArrayList<Car>) road.getCars();
            cars.forEach( car -> car.passTimer(timer));
        });
        trafficLights.forEach(trafficLight ->
                trafficLight.passTimer(timer)
        );
    }

    /**
     * initializes and draws the roads
     */
    public void initialize() {

        System.out.println("\nNew background initialized: ");
        int firstX = 900 / (columnCount+1);
        int firstY = 700 / (rowCount+1);

        for (int i = 1; i <= this.rowCount; i++) {
            int currentRoadY = firstY * i;
            HorizontalRoad road = new HorizontalRoad(currentRoadY, 900);
            roads.add(road);
            hRoads.add(road);
        }
        for (int i = 1; i <= this.columnCount; i++) {
            int currentRoadX = firstX * i;
            VerticalRoad road = new VerticalRoad(currentRoadX, 700);
            roads.add(road);
            vRoads.add(road);
        }
        vRoads.forEach(vRoad -> hRoads.forEach(hRoad -> {
            TrafficLight trafficLight = new TrafficLight(vRoad, hRoad);
            vRoad.addTrafficLight(trafficLight);
            hRoad.addTrafficLight(trafficLight);
            trafficLights.add(trafficLight);
        }));

        addCars();
    }

    /**
     * Executing cars and traffic lights
     */
    public void executeWorkers(){
        roads.forEach(road -> {
            ArrayList<Car> cars = (ArrayList<Car>) road.getCars();
            cars.forEach(car -> executorService.submit(car));
        });
        trafficLights.forEach(light -> executorService.submit(light));
    }
    public int getRowCount(){ return this.rowCount; }
    public int getColumnCount() { return columnCount; }
    public int getCarCount() { return carCount; }
}