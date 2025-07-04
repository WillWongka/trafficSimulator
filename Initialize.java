/*
*File: Initialize.java
* Author: WillWongka
* Course: CMSC 335

Description:
This file contains the initialization menu for the Traffic Simulator where users can select the number of rows, columns and cars.
*/
package org.example;

import javax.swing.*;
import java.awt.*;

public class Initialize extends JPanel {
    Integer[] range = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    /**
     * Constructor for creating the initialization panel with UI components.
     *
     * @param main the main frame of the application
     */
    public Initialize(MainFrame main) {
        // Set layout and constraints
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label indicating to select options
        JLabel info = new JLabel("Select options to begin simulation:");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 15, 15, 15);
        this.add(info, gbc);

        // Label and combo box for selecting row count
        JLabel rowLabel = new JLabel("Horizontal Road count: ");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 15, 0, 0);
        this.add(rowLabel, gbc);

        JComboBox<Integer> rowComboBox = new JComboBox<>(range);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 50, 5, 10);
        this.add(rowComboBox, gbc);

        // Label and combo box for selecting column count
        JLabel columnLabel = new JLabel("Vertical Road count: ");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 15, 0, 0);
        this.add(columnLabel, gbc);

        JComboBox<Integer> columnComboBox = new JComboBox<>(range);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 50, 5, 10);
        this.add(columnComboBox, gbc);

        // Label and combo box for selecting car count
        JLabel carLabel = new JLabel("Car count: ");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 15, 0,0);
        this.add(carLabel, gbc);

        JComboBox<Integer> carComboBox = new JComboBox<>(range);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 50, 5, 10);
        this.add(carComboBox, gbc);

        // Submit button for initiating the simulation
        JButton submitBtn = new JButton("Submit");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 10, 5);
        this.add(submitBtn, gbc);

        submitBtn.addActionListener( event -> {
            // Create a background with selected options
            Background background = new Background (
                    (int)rowComboBox.getSelectedItem(),
                    (int)columnComboBox.getSelectedItem(),
                    (int)carComboBox.getSelectedItem()
            );

            // Create simulation options menu
            SimulationOptions menu = new SimulationOptions(background);


            // Update the main frame with the simulation components
            main.remove(this);
            main.setLayout(new BorderLayout());
            main.add(menu, BorderLayout.NORTH);
            main.add(background, BorderLayout.CENTER);
            main.revalidate();
            main.pack();
        });

    }
}
