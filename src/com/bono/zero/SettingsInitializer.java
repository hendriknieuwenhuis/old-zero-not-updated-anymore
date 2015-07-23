package com.bono.zero;

import com.bono.zero.control.Server;
import com.bono.zero.model.Settings;
import com.bono.zero.view.SettingsDialog;

import java.awt.*;
import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 23/07/15.
 */
public class SettingsInitializer implements Runnable {

    private Object lock = new Object();
    private boolean waiting;

    private Server server;
    private Settings settings;
    private Rectangle bounds;

    public SettingsInitializer(Application application) {
        server = application.getServer();
        settings = application.getSettings();
        bounds = application.getBounds();
    }

    @Override
    public void run() {
        if (server == null) {
            server = new Server();
        }
        try {
            // loading the settings from settings.set file.
            settings = SettingsLoader.loadSettings();
            testSettings();
            //return settings;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // if there is no settings.set file
            settings = Settings.getSettings();
            new SettingsDialog(this);
            goWait();
            System.out.println("Continue, and go testing");
            testSettings();
            try {
                SettingsLoader.saveSettings(settings);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //return settings;
        }

        //return settings;

    }

    // test the settings that are given. When no connection
    // is established we will ask again for settings.
    private void testSettings() {

        try {
            server.setAddress(settings.getAddress());
            server.checkConnectionSettings();

        } catch (IllegalArgumentException e) {

            new SettingsDialog(this);
            goWait();
            testSettings();

        } catch (IOException e) {

            new SettingsDialog(this);
            goWait();
            testSettings();

        }

    }

    private void goWait() {
        synchronized (lock) {
            try {
                waiting = true;
                while (waiting) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void doNotify() {
        synchronized (lock) {
            waiting = false;
            lock.notify();
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
