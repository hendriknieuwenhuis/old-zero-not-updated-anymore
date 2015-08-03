package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.*;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.api.properties.StatusProperties;
import com.bono.zero.control.CurrentSong;
import com.bono.zero.control.PlayerControl;
import com.bono.zero.laf.BonoIconFactory;
import com.bono.zero.view.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class TestPlayerView {

    private static final String HOST = "192.168.2.2";
    private static final int PORT = 6600;


    private static Playlist playlist;
    private static CurrentSong currentSong;
    private static ServerStatus serverStatus;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // endpoint to server for gettinng the init status and playlist.
        Endpoint endpoint = new Endpoint(HOST, PORT);


        // status of server.
        serverStatus = new ServerStatus();

        // playlist.
        playlist = new Playlist();
        List<String> request = null;
        try {
            request = endpoint.request(new Command(ServerProperties.LIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playlist.populatePlaylist(request);

        // the current song view/controller.
        currentSong = new CurrentSong(playlist);

        // init status.
        List<String> statusList = null;
        try {
            statusList = endpoint.request(new Command(ServerProperties.STATUS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set status listeners.
        serverStatus.getStatus().getSongid().setChangeListener(currentSong.getCurrentSongListener());

        // set status values.
        serverStatus.setStatus(statusList);

        // idle thread.
        Idle idle = new Idle(serverStatus);
        Thread thread = new Thread(idle);
        thread.start();

        // JPanel to containing
        // the elements of the player.
        // JPanel panel = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setEnabled(false);
        splitPane.setDividerSize(0);
        // the player view/controller.
        PlayerControl playerControl = new PlayerControl(HOST, PORT);

        // build interface.
        splitPane.add(playerControl.getPlayerPanel());
        splitPane.add(currentSong.getView().getSongView());
        //panel.add(playerControl.getPlayerPanel());
        //panel.add(currentSong.getView().getSongView());
        frame.getContentPane().add(splitPane);
        frame.setSize(800, 200);
        frame.setVisible(true);

    }




    /*
    Een runnable idle die status update triggert!
     */
    private static class Idle implements Runnable {

        private static ServerStatus serverStatus;

        public Idle(ServerStatus serverStatus) {
            this.serverStatus = serverStatus;
        }

        @Override
        public void run() {
            Endpoint endpointIdle = new Endpoint(HOST, PORT);
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
            final Endpoint endpointUpdate = new Endpoint(HOST, PORT);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.printf("%s, active: %s, %s\n", Thread.currentThread().getName(), Thread.activeCount(), getClass().getName());
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


 }
