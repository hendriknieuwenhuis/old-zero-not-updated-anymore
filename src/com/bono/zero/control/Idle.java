package com.bono.zero.control;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.RequestCommand;
import com.bono.zero.api.models.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;

import com.bono.zero.api.models.commands.Executor;
import com.bono.zero.api.models.commands.ReturnExecutor;
import com.bono.zero.api.properties.StatusProperties;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by hendriknieuwenhuis on 06/08/15.
 *
 * Idle extends executor en
 */
public class Idle implements Runnable {

    private ServerStatus serverStatus;
    private String host;
    private int port;

    private Endpoint endpointIdle;

    private boolean running = true;

    public Idle(String host, int port, ServerStatus serverStatus) {
        this.host = host;
        this.port = port;
        this.serverStatus = serverStatus;
        endpointIdle = new Endpoint(host, port);

    }


    @Override
    public void run() {

        // keeps running.
        while (running) {
            System.out.printf("%s, active: %s, %s\n", Thread.currentThread().getName(), Thread.activeCount(), getClass().getName());
            List<String> feedback = null;
            try {
                feedback = endpointIdle.sendRequest(new RequestCommand(StatusProperties.IDLE));
            } catch (IOException e) {
                e.printStackTrace();
            }

            print(feedback);



        }

    }



    public void stopRunning() {
        running = false;
        try {
            endpointIdle.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void print(List<String> list) {
        for (String s : list) {
            System.out.printf("%s\n", s);
        }
    }


}
