package com.bono.zero.control;

import com.bono.zero.api.Playlist;
import com.bono.zero.api.RequestCommand;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;

import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.api.properties.StatusProperties;

import java.io.IOException;
import java.util.*;

/**
 * Created by hendriknieuwenhuis on 06/08/15.
 *
 * Idle extends executor en
 */
public class Idle implements Runnable {

    private ServerStatus serverStatus;
    private Playlist playlist;
    private String host;
    private int port;

    private Endpoint endpointIdle;

    private boolean running = true;

    public Idle(String host, int port, ServerStatus serverStatus, Playlist playlist) {
        this.host = host;
        this.port = port;
        this.serverStatus = serverStatus;
        this.playlist = playlist;
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
            for (String s : feedback) {
                switch (s) {
                    case "changed: playlist":
                        System.out.println(s);
                        new Thread(new UpdatePlaylist()).start();
                        break;
                    default:
                        break;
                }
            }

            new Thread(new UpdateStatus()).start();

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
            System.out.printf(getClass().getName() + ": %s\n", s);
        }
    }

    private class UpdateStatus implements Runnable {

        private Endpoint endpoint = new Endpoint(host, port);

        @Override
        public void run() {
            try {
                serverStatus.setStatus(endpoint.sendRequest(new RequestCommand(StatusProperties.STATUS)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdatePlaylist implements Runnable {

        private Endpoint endpoint = new Endpoint(host, port);

        @Override
        public void run() {
            try {
                playlist.populate(endpoint.sendRequest(new RequestCommand(PlaylistProperties.PLAYLISTINFO)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
