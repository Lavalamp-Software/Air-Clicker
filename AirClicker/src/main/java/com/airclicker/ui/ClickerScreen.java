package com.airclicker.ui;

import com.airclicker.controllers.Clicker;
import com.airclicker.managers.RoundedBorder;
import com.airclicker.templates.FrameViewer;
import com.airclicker.templates.constants.AirClickerConstants;

import com.formdev.flatlaf.FlatDarkLaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@code ClickerScreen} class is the main UI for the clicker controls.
 */
public class ClickerScreen implements FrameViewer {
    /**
     * The width of the initial size for the {@code SplashScreen} element.
     */
    private final int width;
    /**
     * The height of the initial size for the {@code SplashScreen} element.
     */
    private final int height;
    /**
     * The speed of the clicker.
     */
    private int speed = 0;
    /**
     * The limit of the clicker.
     */
    private int limit = 0;
    /**
     * The side of the mouse
     */
    private String mouseSide = "Default";
    /**
     * The logger tracker to log information to the console.
     *
     * @apiNote There is no java doc available for {@code Logger} and {@code LoggerFactory}
     */
    private final Logger logger = LoggerFactory.getLogger(ClickerScreen.class);
    /**
     *
     */
    private final Clicker clicker = Clicker.build();
    /**
     * The {@code List<JComponent>} that holds TextFields and buttons.
     */
    private final List<JComponent> comps = new ArrayList<>();
    /**
     * The window object, all tree components are parented onto this window object.
     */
    private JFrame window;
    /**
     * The JPanel is a generic lightweight container to hold components inside its tree.
     */
    private JPanel panel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
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
    public ClickerScreen(int width, int height) {
        this.width = width;
        this.height = height;
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

        UIManager.put("Button.arc", 5);
        UIManager.put("TextField.arc", 250);

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            this.logger.error(e.getMessage());
        }

        GraphicsDevice gd = (gs.length > 1) ? gs[1] : gs[0];

        this.window = new JFrame(gd.getDefaultConfiguration());
        this.window.setSize(width, height);
        this.window.setLayout(new BorderLayout());

        this.window.setUndecorated(true);
        this.window.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        this.window.setResizable(false);
        this.window.setIconImage(this.resizeLogo().getImage());
        this.window.setTitle(AirClickerConstants.TITLE);

        this.window.setLocationRelativeTo(this.window);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        this.window.add(this.panel, BorderLayout.CENTER);

        this.show();
    }
    /**
     * Displays the JFrame object to the primary screen selected.
     */
    protected void show() {
        this.createTopPanel();
        this.createMiddlePanel();
        this.createBottomPanel();
        this.window.setVisible(true);
    }
    /**
     * TODO: Java Doc
     */
    protected void createTopPanel() {
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
        this.topPanel.setPreferredSize(new Dimension(this.window.getSize().width, 120));
        this.topPanel.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "Clicker Controls")
        ));
        this.window.add(this.topPanel, BorderLayout.NORTH);

        JTextField rate = new JTextField();
        rate.setPreferredSize(new Dimension(100, 70));
        rate.setOpaque(false);
        rate.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "Speed")
        ));
        this.comps.add(rate);
        rate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) { }

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
            }

            public void check() {
                try {
                    speed = Integer.parseInt(rate.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(window, "Invalid number: "+e.getMessage(), "Air Clicker", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> rate.setText(""));
                    logger.error(e.getMessage());
                }
            }
        });
        this.topPanel.add(rate);

        JTextField amountOfTimes = new JTextField();
        amountOfTimes.setPreferredSize(new Dimension(100, 70));
        amountOfTimes.setOpaque(false);
        amountOfTimes.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "Click limit")
        ));
        this.comps.add(amountOfTimes);
        amountOfTimes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) { }

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
            }

            public void check() {
                try {
                    if (
                            amountOfTimes.getText().equalsIgnoreCase("inf") ||
                            amountOfTimes.getText().equalsIgnoreCase("infinite") ||
                            Integer.parseInt(amountOfTimes.getText()) >= 999
                    ) {
                        SwingUtilities.invokeLater(() -> {
                            amountOfTimes.setText("INFINITE");
                            amountOfTimes.setEnabled(false);
                        });
                        return;
                    }
                    limit = Integer.parseInt(amountOfTimes.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(window, "Invalid number: "+e.getMessage(), "Air Clicker", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> amountOfTimes.setText(""));
                    logger.error(e.getMessage());
                }
            }
        });
        this.topPanel.add(amountOfTimes);

        JButton helpButton = new JButton();
        helpButton.setText("How to use");
        helpButton.setContentAreaFilled(false);
        helpButton.setPreferredSize(new Dimension(100, 60));
        helpButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));
        this.comps.add(helpButton);
        this.topPanel.add(helpButton);

        JButton resetButton = new JButton();
        resetButton.setText("Reset Fields");
        resetButton.setContentAreaFilled(false);
        resetButton.setPreferredSize(new Dimension(100, 60));
        resetButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));
        resetButton.addActionListener((ActionEvent e) -> {
            amountOfTimes.setEnabled(true);
            amountOfTimes.setText("");
            rate.setText("");
        });
        this.comps.add(resetButton);
        this.topPanel.add(resetButton);
    }
    /**
     * TODO: Java Doc
     */
    protected void createMiddlePanel() {
        this.middlePanel = new JPanel();
        this.middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.middlePanel.setPreferredSize(new Dimension(this.window.getSize().width, 120));
        this.middlePanel.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(4, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "Input Controls")
        ));
        this.window.add(this.middlePanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(true);
        //leftPanel.setPreferredSize(new Dimension(this.middlePanel.getSize().width, this.middlePanel.getSize().height));
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints config = new GridBagConstraints();

        String[] opts = new String[] {"Default", "Mouse Button Left", "Mouse Button Right"};
        JComboBox<String> options = new JComboBox<>(opts);
        options.setFocusable(false);
        options.setSelectedIndex(0);
            mouseSide = (String) options.getSelectedItem();
        options.addActionListener(e -> {
            mouseSide = (String) options.getSelectedItem();
        });
        System.out.println(mouseSide);
        this.comps.add(options);

        JLabel title = new JLabel("Mouse Options");
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));

        config.gridx = 0;
        config.gridy = 0;
        leftPanel.add(title, config);

        config.gridx = 1;
        config.gridy = 0;
        leftPanel.add(options, config);

        JCheckBox repeat = new JCheckBox("Repeat");
        repeat.setFocusable(false);
        repeat.setHorizontalTextPosition(SwingConstants.LEFT);
        repeat.setSelected(false);
        this.comps.add(repeat);

        config.gridx = 0;
        config.gridy = 1;
        leftPanel.add(repeat, config);

        this.middlePanel.add(leftPanel);
        this.middlePanel.add(Box.createHorizontalStrut(50));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());

        GridBagConstraints rightConfig = new GridBagConstraints();

        JLabel rightTitle = new JLabel("Mouse Position");

        rightConfig.gridx = 0;
        rightConfig.gridy = 0;

        rightPanel.add(rightTitle, rightConfig);

        JTextField xPos = new JTextField();
        xPos.setText("X: 0");
        xPos.setPreferredSize(new Dimension(50, 50));
        xPos.setOpaque(false);
        xPos.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "X Pos")
        ));
        this.comps.add(xPos);

        rightConfig.gridx = 1;
        rightConfig.gridy = 0;
        rightPanel.add(xPos, rightConfig);

        JTextField yPos = new JTextField();
        yPos.setText("Y: 0");
        yPos.setPreferredSize(new Dimension(50, 50));
        yPos.setOpaque(false);
        yPos.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "Y Pos")
        ));
        this.comps.add(yPos);

        rightConfig.gridx = 2;
        rightConfig.gridy = 0;
        rightPanel.add(yPos, rightConfig);

        rightConfig.gridx = 0;
        rightConfig.gridy = 1;
        rightPanel.add(Box.createVerticalStrut(10), rightConfig);

        this.middlePanel.add(rightPanel);
    }
    /**
     * TODO: Java doc
     */
    protected void createBottomPanel() {
        this.bottomPanel = new JPanel();
        this.bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        this.bottomPanel.setPreferredSize(new Dimension(this.window.getSize().width, 95));
        this.bottomPanel.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(4, 10, 10, 10),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1A1A1A), 1, true), "Main Controls")
        ));
        this.window.add(this.bottomPanel, BorderLayout.SOUTH);

        JButton startButton = new JButton();
        startButton.setText("Start");
        startButton.setContentAreaFilled(false);
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(3, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));

        startButton.addActionListener((ActionEvent e) -> {
            AtomicInteger i = new AtomicInteger();

            this.comps.forEach(event -> {
                this.comps.get(i.get()).setEnabled(false);
                i.getAndIncrement();
            });

            if (speed == 0 && limit == 0) {
                logger.info("Not available");
            } else if (speed > 0 && limit > 0) {
                clicker.shouldRun = true;
                clicker.begin(speed, limit, mouseSide);
            } else if (speed > 0 && limit == 0) {
                clicker.begin(speed, mouseSide);
            }
        });

        this.bottomPanel.add(startButton);

        JButton stopButton = new JButton();
        stopButton.setText("Stop");
        stopButton.setContentAreaFilled(false);
        stopButton.setPreferredSize(new Dimension(100, 50));
        stopButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(3, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));

        stopButton.addActionListener((ActionEvent e) -> {
            AtomicInteger i = new AtomicInteger();
            this.comps.forEach(event -> {
                this.comps.get(i.get()).setEnabled(true);
                i.getAndIncrement();
            });

            clicker.shouldRun = false;
        });

        this.bottomPanel.add(stopButton);

        JButton pluginsButton = new JButton();
        pluginsButton.setText("Plugins");
        pluginsButton.setContentAreaFilled(false);
        pluginsButton.setPreferredSize(new Dimension(100, 50));
        pluginsButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(3, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));

        this.comps.add(pluginsButton);
        this.bottomPanel.add(pluginsButton);

        JButton settingsButton = new JButton();
        settingsButton.setText("Settings");
        settingsButton.setContentAreaFilled(false);
        settingsButton.setPreferredSize(new Dimension(100, 50));
        settingsButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(3, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));

        this.comps.add(settingsButton);
        this.bottomPanel.add(settingsButton);

        JButton helpButton = new JButton();
        helpButton.setText("Help");
        helpButton.setContentAreaFilled(false);
        helpButton.setPreferredSize(new Dimension(100, 50));
        helpButton.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(3, 0, 5, 0),
                new RoundedBorder(5, new Color(0x1A1A1A))
        ));

        this.comps.add(helpButton);
        this.bottomPanel.add(helpButton);
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
    /**
     * The top panel on the UI.
     * @return {@code JPanel} the top panel on the UI.
     */
    public JPanel getTopPanel() {
        return this.topPanel;
    }
    /**
     * The middle panel on the UI.
     * @return {@code JPanel} the middle panel on the UI.
     */
    public JPanel getMiddlePanel() {
        return this.middlePanel;
    }
    /**
     * The bottom panel on the UI.
     * @return {@code JPanel} the bottom panel on the UI.
     */
    public JPanel getBottomPanel() {
        return this.bottomPanel;
    }
    /**
     * Gets the list which stores all the interactive components on the UI.
     * @return {@code List<JComponent>} a List object of all the components on the UI.
     */
    public List<JComponent> getComponents() {
        return this.comps;
    }
}
