package com.bono.zero;

import javax.swing.*;
import java.awt.event.WindowAdapter;

/**
 * Created by hendriknieuwenhuis on 03/08/15.
 */
public class Application extends WindowAdapter {

    private static Application application;

    public static void launch(final String[] args) {
        application = new Application();

        application.setLookAndFeel();

        application.showSplashScreen();

        application.init();

        application.start(new JFrame());
    }

    protected void showSplashScreen() {}

    protected void setLookAndFeel() {}

    protected void init() {}

    protected void start(JFrame frame) {}




}
