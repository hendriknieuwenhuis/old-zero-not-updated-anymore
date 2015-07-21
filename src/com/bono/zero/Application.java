package com.bono.zero;

import com.bono.zero.control.Controller;
import com.bono.zero.control.Server;
import com.bono.zero.laf.ZeroTheme;
import com.bono.zero.model.Directory;
import com.bono.zero.model.Settings;
import com.bono.zero.view.ZeroFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

/**
 * Created by hendriknieuwenhuis on 21/07/15.
 */
public class Application implements WindowListener {

    private static Application application;

    private Server server;
    private Directory directory;
    private Settings settings;
    private Controller controller;

    private Application() {
        System.out.printf("%s\n", "initialized");
    }

    public static void launch(final String[] args) {
        application = new Application();

        application.setLookAndFeel();

        /*
        -> -> !!!! Needs a look at the logic of this !!!! <- <-
         */
        if (args != null) {
            new Arguments(application);
        }

        application.start(new ZeroFrame());
    }

    protected void start(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setSize(screenSize());
        frame.setVisible(true);
    }

    // Sets the look and feel
    protected void setLookAndFeel() {
        Color gray = Color.LIGHT_GRAY;
        Color darkGray = Color.DARK_GRAY;
        Color lGray = new Color(185, 185, 185);
        Color lGrayDarker = new Color(155, 155, 155);
        Border none = BorderFactory.createEmptyBorder();
        Border dark = BorderFactory.createLineBorder(Color.darkGray, 1);
        Border pink = BorderFactory.createLineBorder(Color.PINK, 1);
        java.util.List buttonGradient = Arrays.asList(new Object[]{new Float(.3f), new Float(0f),
                new ColorUIResource(0xDDE8F3), Color.WHITE, gray});

        MetalLookAndFeel.setCurrentTheme(new ZeroTheme());
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        UIManager.put("Panel.border", none);
        UIManager.put("SplitPane.dividerSize", 1);
        UIManager.put("SplitPane.border", none);
        //UIManager.put("Button.border", dark);
        UIManager.put("ScrollPane.border", dark);
        UIManager.put("ScrollPane.background", lGray);
        UIManager.put("ScrollBar.squareButtons", false);
        UIManager.put("ScrollBar.width", 15);
        UIManager.put("ScrollBar.track", lGray);
        UIManager.put("ScrollBar.thumb", lGrayDarker);
        UIManager.put("ScrollBar.border", none);
        UIManager.put("slider.trackBorder", none);
        UIManager.put("Tree.background", gray);
        UIManager.put("Table.background", gray);
        UIManager.put("MenuBar.gradient", buttonGradient);
    }

    protected Dimension screenSize() {
        final Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        return new Dimension((bounds.width / 2), (bounds.height / 2));
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "opened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "closing");
    }

    /*
    Save the settings of the client.
     */
    @Override
    public void windowClosed(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "closed" );
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "iconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "deiconified");
    }

    /*
    When client is activated start "idle" mode if it was
    in such mode when deactivated!

    Initiate client from server status!
    */
    @Override
    public void windowActivated(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "activated");
    }

    /*
    When client is listening in "idle" state stop this.
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "deactivated");
    }
}
