/*
*File: HorizontalRoad.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the horizontal road class which extends the abstract Road class.
It draws road lines, borders, and contains cars.
*/

package org.example;

import java.awt.*;
import java.util.ArrayList;

public class HorizontalRoad extends Road{

    final int roadMedianY;
    final int canvasWidth;
    final int leftSideY;
    final int rightSideY;

    /**
     * Constructor for the horizontal road, initializing road properties and creating collections for cars and traffic lights.
     *
     * @param roadMedianY middle of the road
     * @param canvasWidth canvas dimension
     */
    public HorizontalRoad(int roadMedianY, int canvasWidth){
        this.roadMedianY = roadMedianY;
        this.canvasWidth = canvasWidth;
        this.leftSideY = roadMedianY-25;
        this.rightSideY = roadMedianY+25;
        this.length = canvasWidth;
        this.cars = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
    }

    /**
     * Draws the horizontal road, including road lines, borders, and cars.
     *
     * @param graphics to draw with
     */
    public void draw(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.white);
        g2d.drawLine(0, (leftSideY+rightSideY)/2, canvasWidth, (leftSideY+rightSideY)/2);

        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.ORANGE);
        g2d.drawLine(0, leftSideY, canvasWidth, leftSideY);
        g2d.drawLine(0, rightSideY, canvasWidth, rightSideY);

        for (Car car : cars) {
            car.draw(graphics);
        }
    }
}