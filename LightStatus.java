/*
*File: LightStatus.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains enum to enumerates different traffic light statuses with associated colors. Each enum constant represents a specific
traffic light status (GO, STOP, or YIELD) and its corresponding color.
*/
package org.example;

import java.awt.*;

public enum LightStatus {
    GO(Color.GREEN),
    STOP(Color.RED),
    YIELD(Color.YELLOW);

    private final Color color;
    private final String status;

    /**
     * Constructor for LightStatus enum.
     *
     * @param color The Color associated with the traffic light status.
     */
    LightStatus(Color color) {
        this.color = color;
        this.status = name();
    }

    public Color getColor() {
        return color;
    }

}