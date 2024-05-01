/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caralarm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarAlarmSimulator extends JFrame implements ActionListener {
    private JToggleButton door1, door2, door3, door4, door5, door6, door7;
    private JToggleButton fullModeToggle, gasModeToggle;
    private JButton stopAlarmButton;
    private JLabel outputLabel;

    public CarAlarmSimulator() {
        setTitle("Car Alarm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // create doors panel
        JPanel doorsPanel1 = new JPanel(new GridLayout(2, 2));
        door1 = new JToggleButton("Door 1");
        door2 = new JToggleButton("Door 2");
        door3 = new JToggleButton("Door 3");
        door4 = new JToggleButton("Door 4");
        doorsPanel1.add(door1);
        doorsPanel1.add(door2);
        doorsPanel1.add(door3);
        doorsPanel1.add(door4);

        JPanel doorsPanel2 = new JPanel();
        door5 = new JToggleButton("Bonet");
        door6 = new JToggleButton("Boot");
        door7 = new JToggleButton("Fuel");
        doorsPanel2.add(door5);
        doorsPanel2.add(door6);
        doorsPanel2.add(door7);

        // create modes panel
        JPanel modesPanel = new JPanel();
        fullModeToggle = new JToggleButton("Full Mode");
        gasModeToggle = new JToggleButton("Gas Mode");
        stopAlarmButton = new JButton("Stop Alarm");
        modesPanel.add(fullModeToggle);
        modesPanel.add(gasModeToggle);
        modesPanel.add(stopAlarmButton);

        // create output panel
        JPanel outputPanel = new JPanel();
        outputLabel = new JLabel("No Alarm");
        outputPanel.add(outputLabel);

        // add panels to frame
        add(doorsPanel1, BorderLayout.NORTH);
        add(doorsPanel2, BorderLayout.CENTER);
        add(modesPanel, BorderLayout.SOUTH);
        add(outputPanel, BorderLayout.EAST);

        // add action listeners
        door1.addActionListener(this);
        door2.addActionListener(this);
        door3.addActionListener(this);
        door4.addActionListener(this);
        door5.addActionListener(this);
        door6.addActionListener(this);
        door7.addActionListener(this);
        fullModeToggle.addActionListener(this);
        gasModeToggle.addActionListener(this);
        stopAlarmButton.addActionListener(this);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stopAlarmButton) {
            outputLabel.setText("No Alarm");
        } else {
            boolean fuelSelected = door7.isSelected();
            boolean gasModeSelected = gasModeToggle.isSelected();
            boolean fullModeSelected = fullModeToggle.isSelected();

            boolean door1Selected = door1.isSelected();
            boolean door2Selected = door2.isSelected();
            boolean door3Selected = door3.isSelected();
            boolean door4Selected = door4.isSelected();
            boolean door5Selected = door5.isSelected();
            boolean door6Selected = door6.isSelected();

            if (fuelSelected && (door1Selected || door2Selected || door3Selected || door4Selected) && gasModeSelected) {
                outputLabel.setText("Alarm sounding - gas mode");
            } else if (fullModeSelected && (door1Selected || door2Selected || door3Selected || door4Selected || door5Selected || door6Selected || fuelSelected)) {
                outputLabel.setText("Alarm sounding - full mode");
            }
        }
    }

    public static void main(String[] args) {
        new CarAlarmSimulator();
    }
}
