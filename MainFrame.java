/*
*File: MainFrame.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the default JFrame for the program.
*/
package org.example;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        super("Traffic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        Initialize initial = new Initialize(this);
        add(initial);
        pack();
        setVisible(true);
    }
}
