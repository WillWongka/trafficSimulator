/*
*File: Car.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the Car class, which represents individual cars in a traffic simulation.
Each car has a unique identifier, a reference to the road it is on, and various attributes such as speed and color.
*/

package org.example;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/*
 * Enum representing various color options for cars.
 */
enum ColorSelection {
    PINK(Color.PINK),
    ORANGE(Color.ORANGE),
    WHITE(Color.WHITE),
    LIGHT_GRAY(Color.LIGHT_GRAY),
    BLUE(Color.BLUE),
    CYAN(Color.CYAN),
    MAGENTA(Color.MAGENTA);

    private final Color color;
    private final String colorName;

    ColorSelection(Color color) {
        this.color = color;
        this.colorName = name();
    }

    public Color getColor() {
        return color;
    }

    public String getColorName() {
        return this.colorName;
    }

    public static ColorSelection getRandomColor() {
        ColorSelection[] values = values();
        int choice = (int) (Math.random() * values.length);
        return values[choice];
    }

    public String toString() {
        return name();
    }

}

public class Car extends SwingWorker<Void, Void> {

    private final int carID;
    private static int count = 1;
    private Road road;
    private Timer timer;
    private int x;
    private int y;
    private final Random random;
    private int speed;
    private final ColorSelection colorChoice = ColorSelection.getRandomColor();
    private final Color color = colorChoice.getColor();

    /*
     * Constructor for the Car class.
     * Initializes car attributes such as ID, speed, and color based on the provided road.
     */
    public Car(Road road){
        this.road = road;
        this.carID = count++;
        this.random = new Random();
        this.speed = 50;
        System.out.println(String.format("Car %d [%s] has been created going %d mph.", this.carID, colorChoice.toString(), this.speed));

        if (road instanceof VerticalRoad){
            if (random.nextBoolean()) {
                this.x = ((VerticalRoad) road).leftSideX+5;
                this.y = road.length;
            } else {
                this.x = ((VerticalRoad) road).leftSideX+30;
                this.y = road.length;
            }
        }
        if (road instanceof HorizontalRoad){
            if (random.nextBoolean()) {
                this.x = 0;
                this.y = ((HorizontalRoad) road).leftSideY+5;
            } else {
                this.x = 0;
                this.y = ((HorizontalRoad) road).leftSideY+30;
            }
        }
    }

    /*
     * The main background task for the Car class.
     * Responsible for simulating car movement on the road while the timer is running.
     */
    @Override
    protected Void doInBackground() throws Exception {
        while (!timer.isStopped()) {
            if (!timer.isPaused()) {
                if (this.road instanceof VerticalRoad){
                    tryTurnRight();
                }else {
                    tryTurnLeft();
                }
            }
            Thread.sleep(1000);
        }
        return null;
    }

    /*
     * Attempts to turn right on a vertical road.
     * Checks for intersections, traffic lights, and decides whether to turn or go straight based on conditions.
     */
    public void tryTurnRight() {
        ArrayList<TrafficLight> trafficLights = road.getTrafficLights();
        ArrayList<TrafficLight> lightAhead = new ArrayList<>();

        int direction = this.y - speed;

        // intersection
        trafficLights.forEach(trafficLight -> {
            if (trafficLight.vLightY >= direction && trafficLight.vLightY < this.y){
                lightAhead.add(trafficLight);
            }
        });
        // Basic traffic functions ex. go, stop, yield
        if (lightAhead.size() > 0){
            TrafficLight light = lightAhead.get(0);
            if (light.vStatus == LightStatus.STOP) { //stop
                moveUp(this.y - light.vLightY - 10);
            } else if (light.vStatus == LightStatus.YIELD){ //decrease speed
                int tempSpeed = this.speed;
                this.speed /= 2;
                moveUp(this.speed);
                this.speed = tempSpeed;
            }else if(light.vStatus == LightStatus.GO){ //go or turn if in right lane (random)
                if(random.nextBoolean() && this.x == ((VerticalRoad) road).leftSideX+30){
                    changeDirection(light, "right");
                }else{
                    moveUp(this.speed);
                }
            }
        }else{
            moveUp(this.speed);
        }
    }

    /*
     * Attempts to turn left on a horizontal road.
     * Checks for intersections, traffic lights, and decides whether to turn or go straight based on conditions.
     */
    private void tryTurnLeft(){
        ArrayList<Car> cars = (ArrayList<Car>) road.getCars();
        ArrayList<TrafficLight> trafficLights = road.getTrafficLights();
        ArrayList<TrafficLight> lightAhead = new ArrayList<>();
        int direction = this.x + speed;

        trafficLights.forEach(trafficLight -> {
            if (trafficLight.hLightX <= direction && trafficLight.hLightX > this.x){
                lightAhead.add(trafficLight);
            }
        });

        // Basic traffic functions ex. go, stop, yield
        if (lightAhead.size() > 0){
            TrafficLight light = lightAhead.get(0);
            if (light.hStatus == LightStatus.STOP) { //stop
                moveLeft(light.hLightX - this.x - 10);
            } else if (light.hStatus == LightStatus.YIELD){ //decrease speed
                int tempSpeed = this.speed;
                this.speed /= 2;
                moveLeft(this.speed);
                this.speed = tempSpeed;
            } else if(light.hStatus == LightStatus.GO){ //turn if on left lane (random)
                if(random.nextBoolean() && this.y == ((HorizontalRoad) road).leftSideY+5){
                    moveLeft(this.speed);
                }else{
                    changeDirection(light, "left");
                }
            }
        }else{
            moveLeft(this.speed);
        }
    }

    private boolean isCarAhead(int distance) {
        for (Car  other : road.getCars()) {
            if (other == this) continue;

            if (road instanceof HorizontalRoad && this.y == other.y) {
                if (other.x > this.x && other.x - this.x < distance + 15) return true;
            }
            if (road instanceof VerticalRoad && this.x == other.x) {
                if (this.y > other.y && this.y - other.y < distance + 15) return true;
            }
        }
        return false;
    }

    /*
     * Moves the car to the left along the x-axis.
     */
    private void moveLeft(int distance){
        if (!isCarAhead(distance)) {
            this.x += distance;
            if (this.x >= this.road.length){
                this.x = 0;
            }
        }
    }

    /*
     * Moves the car upward along the y-axis.
     */
    private void moveUp(int distance){
        if (!isCarAhead(distance)) {
            this.y -= distance;
            if (this.y <= 0){
                this.y = this.road.length-10;
            }
        }
    }

    /*
     * Changes the direction of the car based on the provided traffic light and direction.
     */
    private void changeDirection(TrafficLight light, String direction){
        if (direction.equals("right")) {
            int distanceToLight = light.hLightX - this.x;
            int remainingDistance = this.speed - distanceToLight;
            moveLeft(distanceToLight);
            light.vRoad.addCar(this);
            this.road = light.vRoad;
            this.x = light.vRoad.leftSideX+5;//
            moveUp(remainingDistance);
        } else {
            int distanceToLight = this.y - light.vLightY;
            int remainingDistance = this.speed - distanceToLight;
            moveUp(distanceToLight);
            light.hRoad.addCar(this);
            this.road = light.hRoad;
            this.y = light.hRoad.leftSideY+30;//
            moveLeft(remainingDistance);
        }

    }

    /*
     * Draws the car on the graphics canvas.
     */
    public void draw(Graphics graphics){
        graphics.setColor(this.color);
        graphics.fillRect(this.x, this.y,15,15 );
    }

    /**
     * Sharing the timer with the car class object
     */
    public void passTimer(Timer timer){
        this.timer = timer;
    }

}