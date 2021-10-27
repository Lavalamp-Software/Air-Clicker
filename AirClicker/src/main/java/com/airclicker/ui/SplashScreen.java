package com.airclicker.ui;

import com.airclicker.managers.StateManager;
import com.airclicker.templates.FrameViewer;
import com.airclicker.templates.constants.AirClickerConstants;

import com.formdev.flatlaf.FlatDarkLaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Random;

/**
 * The {@code SplashScreen} class is the first UI element to appear on the screen.
 *
 */
public class SplashScreen implements FrameViewer {
    /**
     * The width of the initial size for the {@code SplashScreen} element.
     */
    private final int width;
    /**
     * The height of the initial size for the {@code SplashScreen} element.
     */
    private final int height;
    /**
     * The logger tracker to log information to the console.
     *
     * @apiNote There is no java doc available for {@code Logger} and {@code LoggerFactory}
     */
    private final Logger logger = LoggerFactory.getLogger(SplashScreen.class);
    /**
     * The window object, all tree components are parented onto this window object.
     */
    private JFrame window;
    /**
     * The JPanel is a generic lightweight container to hold components inside its tree.
     */
    private JPanel panel;
    /**
     * Responsible for describing fonts and graphic device objects on a specific platform
     * for the Java™ Application.
     *
     * @see     java.awt.GraphicsDevice
     * @see     java.awt.GraphicsConfiguration
     */
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    /**
     * The GraphicsDevice describes the particular graphics device available in the specified
     * GraphicsEnvironment. This may include Screen or printer devices.
     *
     * @see     java.awt.GraphicsEnvironment
     * @see     java.awt.GraphicsConfiguration
     */
    private final GraphicsDevice[] gs = ge.getScreenDevices();
    /**
     * Instantiate the {@code SplashScreen} UI component with the width and height
     * for the primary display screen to see.
     *
     * @param width The width of the {@code JFrame} object.
     * @param height The height of the {@code JFrame} object.
     */
    public SplashScreen(int width, int height) {
        this.width = width;
        this.height = height;

        Taskbar taskbar = Taskbar.getTaskbar();
        taskbar.setIconImage(this.resizeLogo().getImage());
    }
    /**
     * The create method instantiates the JFrame object and constructs its properties.
     * The specified GraphicsDevice is selected and instructs the JFrame object to be displayed to
     * the primary display screen, which could be an external monitor device. The Look and Feel
     * of this UI Element is set to FlatDarkLaf to give it more of a modern java look. The JFrame object's
     * relative location is set to null which forces it to be presented in the middle of the screen.
     *
     * @see     javax.swing.JFrame
     * @see     java.awt.GraphicsDevice
     * @see     com.formdev.flatlaf.FlatDarkLaf
     */
    @Override
    public void create() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            this.logger.error(e.getMessage());
        }

        GraphicsDevice gd = (gs.length > 1) ? gs[1] : gs[0];

        this.window = new JFrame(gd.getDefaultConfiguration());
        this.window.setSize(width, height);
        this.window.setLayout(new BorderLayout());

        this.window.setLocationRelativeTo(this.window);
        this.window.setUndecorated(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        this.window.add(this.panel, BorderLayout.CENTER);

        JLabel image = new JLabel();
        image.setIcon(this.resizeLogo());

        this.panel.add(image, BorderLayout.CENTER);
    }
    /**
     * Displays the JFrame object to the primary screen selected.
     */
    public void show() {
        this.window.setVisible(true);
        try {
            Thread.sleep(new Random().nextInt(9) * 1000);
            new StateManager().swapState(this.window, new ClickerScreen(585, 360));
        } catch (InterruptedException e) {
            this.logger.error(e.getMessage());
        }
    }
    /**
     *  Resizes the image to a specified width and height
     * @return {@code ImageIcon} a resized image
     */
    public ImageIcon resizeLogo() {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(AirClickerConstants.LOGO_PATH));
            return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            this.logger.error(e.getMessage());
            return null;
        }
    }
    /**
     * The main JFrame object which is targeted to be displayed to the
     * primary screen.
     *
     * @return {@code JFrame} the window object
     */
    public JFrame getWindow() {
        return this.window;
    }
    /**
     * Fetches the current container holding all components inside its tree.
     * @return {@code JPanel} the container.
     */
    public JPanel getPanel() {
        return this.panel;
    }
    /**
     * The width of the JFrame window.
     * @return {@code int} width of the window.
     */
    public int getWidth() {
        return this.width;
    }
    /**
     * The height of the JFrame window.
     * @return {@code int} height of the window.
     */
    public int getHeight() {
        return this.height;
    }
}
