/*
*File: Road.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the abstract class representing a road, to be extended by specific road implementations (VerticalRoad, HorizontalRoad).
Contains common functionalities for both types of roads.
*/

package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;


public abstract class Road {
    // Lists to hold traffic lights and cars on the road
    ArrayList<TrafficLight> trafficLights;
    List<Car> cars = Collections.synchronizedList(new ArrayList<>());
    int length;

    //Adds a car to the road
    public void addCar(Car car) { cars.add(car); }
    // Gets the list of cars on the road.
    public synchronized List<Car> getCars(){ return new ArrayList<>(cars); }
    //Adds a traffic light to the road.
    public void addTrafficLight(TrafficLight light){ trafficLights.add(light); }
    //Retrieves the list of traffic lights on the road.
    public ArrayList<TrafficLight> getTrafficLights() { return trafficLights; }
    //Abstract method to draw the road on a graphics canvas.
    public abstract void draw(Graphics g);
}