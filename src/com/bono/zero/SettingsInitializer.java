package com.bono.zero;

import com.bono.zero.api.Endpoint;
import com.bono.zero.control.Server;
import com.bono.zero.control.SettingsDialog;
import com.bono.zero.model.Settings;
import com.bono.zero.view.SettingsDialogView;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

/**
 *
 * Created by hendriknieuwenhuis on 23/07/15.
 */
public class SettingsInitializer implements Callable<Settings> {

    private Object lock = new Object();
    private boolean waiting;

    private Settings settings;

    private Endpoint endpoint;

    public SettingsInitializer(Endpoint endpoint, Settings settings) {
        this.endpoint = endpoint;
        this.settings = settings;
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
        waiting = false;
        synchronized (lock) {

            lock.notify();
        }
    }

    public Settings getSettings() {
        return settings;
    }


    @Override
    public Settings call() throws Exception {

        // check if endpoint is intialized
        if (endpoint == null) {
            endpoint = new Endpoint();
        }

        // try to load the settings.set file.
        try {
            settings = SettingsLoader.loadSettings();
            testSettings();
        } catch (FileNotFoundException e) {
            // create new settings object
            // and ask the user for the
            // servers address, port, etc
            // with the settings dialog view.
            newSettings();
        } finally {
            return settings;
        }

    }


    private void testSettings() {
        endpoint.setHost(settings.getHost());
        endpoint.setPort(settings.getPort());
        String version = null;

        // check if the version String is given
        // to test the settings.
        try {
            version = endpoint.getVersion();
            //System.out.println("Version: "+version);
        } catch (IllegalArgumentException e) {
            // call this method again!
            System.out.println(e.toString());
            newSettings();
        } catch (ConnectException e) {
            System.out.println(e.toString());
            newSettings();
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
            newSettings();
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        // call again, version wrong!
        if (!version.startsWith("OK MPD")) {
            newSettings();
        }
        try {
            SettingsLoader.saveSettings(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newSettings() {
        settings = new Settings();
        new SettingsDialog(this);
        goWait();
        testSettings();
    }
}
