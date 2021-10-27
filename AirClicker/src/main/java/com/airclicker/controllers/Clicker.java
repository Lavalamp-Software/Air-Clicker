package com.airclicker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * The {@code Clicker} class is responsible for controlling the Device Mouse.
 *
 * @author shabman
 * @version 1.0.0
 */
public class Clicker {
    /**
     * Determines if the robot should run
     */
    public boolean shouldRun = true;
    /**
     * The logger tracker to log information to the console.
     *
     * @apiNote There is no java doc available for {@code Logger} and {@code LoggerFactory}
     */
    private static final Logger logger = LoggerFactory.getLogger(Clicker.class);
    /**
     * Prevents the class from being instantiated
     */
    private Clicker() { }

    public static Clicker build() {
        return new Clicker();
    }

    /**
     * Begins the auto clicker using the {@code Robot} class.
     *
     * @param speed The speed of the clicker in milliseconds.
     */
    public void begin(int speed, String side) {
        int btn = 0;
        if (side.equalsIgnoreCase("default") || side.equalsIgnoreCase("left"))
            btn = 1024;
        else if (side.equalsIgnoreCase("right"))
            btn = 2048;
        try {
            Robot robot = new Robot();
            while (shouldRun) {
                robot.mousePress(btn);
                robot.delay(speed);
                robot.mouseRelease(btn);
                if (!shouldRun) break;
            }
        } catch (AWTException e) {
            logger.error(e.getMessage());
        }
    }
    /**
     * Begins the auto clicker using the {@code Robot} class.
     *
     * @param speed The speed of the clicker in milliseconds.
     * @param times Amount of times the clicker should be triggered.
     */
    public void begin(int speed, int times, String side) {
        int btn = 0;
        if (side.equalsIgnoreCase("default") || side.equalsIgnoreCase("left"))
            btn = 1024;
        else if (side.equalsIgnoreCase("right"))
            btn = 2048;
        int index = 0;
        try {
            Robot robot = new Robot();
            PointerInfo info = MouseInfo.getPointerInfo();
            Point point = info.getLocation();

            int x = (int) point.getX();
            int y = (int) point.getY();

            robot.mouseMove(x - 100, y);

            while (index < times && shouldRun) {
                robot.mousePress(btn);
                robot.delay(speed);
                robot.mouseRelease(btn);
                index++;
                System.out.println(index);
                if (index == times || !shouldRun) break;
            }
        } catch (AWTException e) {
            logger.error(e.getMessage());
        }
    }
}
