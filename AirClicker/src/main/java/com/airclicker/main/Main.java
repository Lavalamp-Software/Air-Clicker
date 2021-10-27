package com.airclicker.main;

import com.airclicker.ui.SplashScreen;

/**
 * The {@code Main} class is the root of this project.&nbsp;All code
 * is executed here
 *
 * @author shabman
 * @version 1.0.0
 */
public class Main {
    /**
     * The method that Java searches for and executes on the main thread.
     * @param args {@code args} is for command line arguments
     */
    public static void main(String[] args) {
        SplashScreen screen = new SplashScreen(420, 320);
        screen.create();
        screen.show();
    }
}
