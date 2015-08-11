package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.*;
import com.bono.zero.api.models.Command;
import com.bono.zero.api.properties.StatusProperties;
import com.bono.zero.control.CurrentSong;
import com.bono.zero.control.PlayerControl;
import com.bono.zero.control.SongScroller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class TestPlayerView {

    private static final String HOST = "192.168.2.2";
    private static final int PORT = 6600;


    private static Playlist playlist;
    //private static CurrentSong currentSong;
    private static ServerStatus serverStatus;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // endpoint to server for getting the init status and playlist.
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
        CurrentSong currentSong = new CurrentSong(playlist);

        // the player
        Player playerPlayer = new Player(new Endpoint(HOST, PORT));
        // the player view/controller.
        PlayerControl playerControl = new PlayerControl(playerPlayer);

        Player playerScroller = new Player(new Endpoint(HOST, PORT));
        SongScroller songScroller = new SongScroller(playerScroller);

        // set status listeners.
       // serverStatus.getStatus().getState().setChangeListener(playerControl.getStateListener());
        //serverStatus.getStatus().getSongid().setChangeListener(currentSong.getCurrentSongListener());
        //serverStatus.getStatus().getSongid().setChangeListener(songScroller.getCurrentSongListener());
        //serverStatus.getStatus().getTime().setChangeListener(songScroller.getCurrentTimeListener());
        //serverStatus.getStatus().getState().setChangeListener(songScroller.getStateListener());

        // init status.
        List<String> statusList = null;
        try {
            statusList = endpoint.request(new Command(ServerProperties.STATUS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set status values.
        serverStatus.setStatus(statusList);

        // idle thread.
        Idle idle = new Idle(serverStatus);
        Thread thread = new Thread(idle);
        thread.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // JPanel to containing
                // the elements of the player.
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 0;

                // build interface.
                panel.add(playerControl.getPlayerView(), constraints);

                constraints.weightx = 0.5;
                constraints.gridx = 1;

                panel.add(currentSong.getSongView(), constraints);

                constraints.weightx = 0.5;
                constraints.gridx = 2;

                panel.add(songScroller.getScrollView(), constraints);
                //panel.add(playerControl.getPlayerPanel());
                //panel.add(currentSong.getView().getSongView());


                frame.getContentPane().add(panel);
                frame.setSize(800, 200);
                frame.setVisible(true);
            }
        });







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
                    System.out.printf("%s, active: %s\n", Thread.currentThread().getName() + " update server status", Thread.activeCount());
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
