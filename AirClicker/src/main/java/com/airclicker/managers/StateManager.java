package com.airclicker.managers;

import com.airclicker.templates.FrameViewer;

import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

/**
 * A state manager class that control states between different JFrames.
 * You can smoothly swap between one {@code JFrame} to another.
 * The {@code StateManager} class disposes a {@code JFrame} before swapping,
 * best to make sure that the current frame is no longer in use before swapping states.
 *
 * @author shabman
 * @version 1.0.1
 */
public class StateManager {
    /**
     * An instance of {@code StateManager} class. This is a singleton class and
     * should not be instantiated outside the package.
     */
    private static StateManager instance;

    public StateManager() { }
    /**
     * Swaps the current {@code JFrame} object to another {@code JFrame} object.
     *
     * @param current The current Frame.
     * @param next The new Frame to be displayed.
     */
    public void swapState(@NotNull JFrame current, @NotNull JFrame next) {
        current.dispose();
        next.setVisible(true);
    }
    /**
     * Swaps the current {@code JFrame} object to a new {@code FrameViewer} object.
     * This calls the {@code FrameViewer.create()} method so create our new {@code JFrame}
     * and display it to the screen.
     *
     * @param current The current Frame.
     * @param next The class to swap with the Frame's parent.
     */
    public void swapState(@NotNull JFrame current, @NotNull FrameViewer next) {
        current.dispose();
        next.create();
    }
}
