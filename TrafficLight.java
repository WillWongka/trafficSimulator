/*
*File: TrafficLight.java
* Author: WillWongka
* Course: CMSC 335

Description:
Represents a traffic light controlled by a timer, managing the color changes of two lights for horizontal and
vertical roads. It extends SwingWorker to perform background tasks in a separate thread.
*/
package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TrafficLight extends SwingWorker<Void, Void> {

    private Timer timer;
    static int counter = 1;
    Random random;

    //Roads associated with the traffic light
    VerticalRoad vRoad;
    HorizontalRoad hRoad;

    // Coordinates and colors for horizontal and vertical lights
    int hLightX, hLightY, vLightX, vLightY, lightNumber;
    Color hLightColor = LightStatus.YIELD.getColor();
    LightStatus hStatus = LightStatus.STOP;
    Color vLightColor = LightStatus.GO.getColor();
    LightStatus vStatus = LightStatus.GO;

    // Locks for synchronization with the horizontal and vertical lights
    private final Object hLock = new Object();
    private final Object vLock = new Object();

    /**
     * Constructor for creating a traffic light associated with vertical and horizontal roads.
     *
     * @param vRoad vertical road
     * @param hRoad horizontal road
     */
    public TrafficLight(VerticalRoad vRoad, HorizontalRoad hRoad){
        this.vRoad = vRoad;
        this.hRoad = hRoad;
        this.hLightX = vRoad.leftSideX -12;
        this.hLightY = hRoad.rightSideY-58;
        this.vLightX = vRoad.rightSideX-5;
        this.vLightY = hRoad.rightSideY;
        this.lightNumber = counter++;
        random = new Random();
        System.out.println("Traffic light #" +lightNumber +" created");
    }

    /**
     * Background task for the traffic light, controlling the color changes based on timer intervals.
     *
     * @return null
     * @throws Exception if unable to compute a result
     */
    @Override
    protected Void doInBackground() throws Exception {
        while (!timer.isStopped()) {
            if (!timer.isPaused()) {
                try {
                    synchronized (hLock) {
                        if (hLightColor.equals(Color.YELLOW)) {
                            Thread.sleep(1000);
                        } else if (hLightColor.equals(Color.GREEN)) {
                            Thread.sleep(5000);
                        } else if (hLightColor.equals(Color.RED)) {
                            Thread.sleep(4000);
                        }
                        this.changeHColor();
                    }

                    synchronized (vLock) {
                        if (vLightColor.equals(Color.YELLOW)) {
                            Thread.sleep(1000);
                        } else if (vLightColor.equals(Color.GREEN)) {
                            Thread.sleep(5000);
                        } else if (vLightColor.equals(Color.RED)) {
                            Thread.sleep(4000);
                        }
                        this.changeVColor();
                    }
                    synchronized (this) {
                        notify();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return null;
    }

    /**
     * Changes the color and status of the traffic light based on the current state of horizontal and vertical lights.
     */

    synchronized void changeHColor() {
        if (hLightColor.equals(Color.GREEN)) {
            hLightColor = LightStatus.YIELD.getColor();
            hStatus = LightStatus.YIELD;
        } else if (hLightColor.equals(Color.YELLOW)) {
            hLightColor = LightStatus.STOP.getColor();
            hStatus = LightStatus.STOP;
        } else if (hLightColor.equals(Color.RED)) {
            hLightColor = LightStatus.GO.getColor();
            hStatus = LightStatus.GO;
        }
        notify();
    }

   synchronized void changeVColor() {
      if (vLightColor.equals(Color.GREEN)) {
          vLightColor = LightStatus.YIELD.getColor();
          vStatus = LightStatus.YIELD;
      } else if (vLightColor.equals(Color.YELLOW)) {
          vLightColor = LightStatus.STOP.getColor();
          vStatus = LightStatus.STOP;
      } else if (vLightColor.equals(Color.RED)) {
          vLightColor = LightStatus.GO.getColor();
          vStatus = LightStatus.GO;
      }
      notify();
  }


    /**
     * Draws the  horizontal and vertical traffic lights.
     *
     * @param graphics to draw with
     */
    public void draw(Graphics graphics){
        graphics.setColor(hLightColor);
        graphics.fillOval(hLightX, hLightY,12, 12);

        graphics.setColor(vLightColor);
        graphics.fillOval(vLightX, vLightY, 12, 12);
    }

    /**
     * Sharing the timer with the TrafficLight class objects
     */
    public void passTimer(Timer timer){ this.timer = timer; }
}