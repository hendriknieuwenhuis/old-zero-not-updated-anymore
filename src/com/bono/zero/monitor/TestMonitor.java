package com.bono.zero.monitor;

import com.bono.zero.model.Settings;

import java.io.IOException;

/**
 * Created by hennihardliver on 22/06/14.
 */
public class TestMonitor {

    public static void main(String[] args) {
        Settings settings = Settings.getSettings();
        settings.setHost("192.168.2.3");
        settings.setPort(6600);
        ServerMonitor s = new ServerMonitor();
        new Thread(s).start();
        /*try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //s.startNewMonitorThread();
        /*try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //s.startNewMonitorThread();
        /*try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //s.endServerMonitor();
    }
}
