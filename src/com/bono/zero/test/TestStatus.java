package com.bono.zero.test;

import com.bono.zero.api.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.Status;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class TestStatus {

    private static Endpoint endpoint;
    private static ServerStatus serverStatus;

    public static void main(String[] args) {
        endpoint = new Endpoint();
        endpoint.setHost("192.168.2.7");
        endpoint.setPort(6600);
        serverStatus = new ServerStatus();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverStatus.setStatus(endpoint.request(new Command("status")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(serverStatus.getStatus().toString());
            }
        });



        t2.start();


    }
}
