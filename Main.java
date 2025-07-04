/*
*File: Main.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the Main class for starting the Traffic Simulator program
*/
package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}