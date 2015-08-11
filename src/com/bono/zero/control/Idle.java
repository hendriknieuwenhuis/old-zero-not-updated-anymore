package com.bono.zero.control;

import com.bono.zero.api.models.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.properties.StatusProperties;

import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 06/08/15.
 */
public class Idle implements Runnable {

    private static ServerStatus serverStatus;

    public Idle(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    @Override
    public void run() {
        Endpoint endpointIdle = new Endpoint("192.168.2.2", 6600);
        // keeps running.
        while (true) {
            System.out.printf("%s, active: %s, %s\n", Thread.currentThread().getName(), Thread.activeCount(), getClass().getName());
            List<String> feedback = null;
            try {
                feedback = endpointIdle.request(new Command(StatusProperties.IDLE));
            } catch (IOException e) {
                e.printStackTrace();
            }

                /*
                Implement a read of the feedback
                and act to it.
                 */
            for (String line : feedback) {
                System.out.println(line);
            }

            updateServerStatus();
        }

    }

    private static void updateServerStatus() {
        final Endpoint endpointUpdate = new Endpoint("192.168.2.2", 6600);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("%s, active: %s, %s\n", Thread.currentThread().getName() + " update server status", Thread.activeCount(), null);
                List<String> statusList = null;
                try {
                    statusList = endpointUpdate.request(new Command(StatusProperties.STATUS));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverStatus.setStatus(statusList);
            }
        });
        thread.start();

    }
}
