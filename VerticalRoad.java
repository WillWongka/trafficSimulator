/*
*File: VerticalRoad.java
* Author: WillWongka
* Course: CMSC 335

Description:
Represents a vertical road extending the abstract Road class. Draws road lines, borders, and contains cars.
*/

package org.example;

import java.awt.*;
import java.util.ArrayList;

public class VerticalRoad extends Road{

    final int roadMedianX;
    final int canvasHeight;
    final int leftSideX;
    final int rightSideX;

    /**
     * Constructor for the vertical road, initializing road properties and creating collections for cars and traffic lights.
     *
     * @param roadMedianX middle of the road
     * @param canvasHeight how tall the canvas is and how long the road will be
     */
    public VerticalRoad(int roadMedianX, int canvasHeight) {
        this.roadMedianX = roadMedianX;
        this.canvasHeight = canvasHeight;
        this.leftSideX = roadMedianX-25;
        this.rightSideX = roadMedianX+25;
        this.length = canvasHeight;
        this.cars = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
    }

    /**
     * Draws the vertical road, including road lines, borders, and cars.
     *
     * @param graphics to draw with
     */
    public void draw(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.white);
        g2d.drawLine((leftSideX+rightSideX)/2, 0, (leftSideX+rightSideX)/2, canvasHeight);

        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.ORANGE);
        g2d.drawLine(leftSideX, 0, leftSideX, canvasHeight);
        g2d.drawLine(rightSideX, 0, rightSideX, canvasHeight);

        for (Car car : this.cars) {
            car.draw(graphics);
        }
    }
}