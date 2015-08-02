package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.api.properties.StatusProperties;
import com.bono.zero.control.CurrentSong;
import com.bono.zero.laf.BonoIconFactory;

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

    private static Endpoint endpoint;
    private static Playlist playlist;
    private static CurrentSong currentSong;
    private static ServerStatus serverStatus;

    public static void main(String[] args) {


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // endpoint to server.
        endpoint = new Endpoint(HOST, PORT);


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
        serverStatus.getStatus().getSongid().setChangeListener(currentSong.getListener());

        // set status values.
        serverStatus.setStatus(statusList);

        // idle thread.
        Idle idle = new Idle(serverStatus);
        Thread thread = new Thread(idle);
        thread.start();

        // JPanel to containing
        // the elements of the player.
        JPanel panel = new JPanel();

        // the player view/controller.
        Player player = new Player();

        // build interface.
        panel.add(player.getPlayerPanel());
        panel.add(currentSong.getView().getSongView());
        frame.getContentPane().add(panel);
        frame.setSize(800, 200);
        frame.setVisible(true);

    }

    /*
    The controller/view class for the player panel.
     */
    private static class Player implements ActionListener {

        private static final String PREVIOUS = "previous";
        private static final String PLAY = "play";
        private static final String NEXT = "next";

        private JPanel playerPanel;
        private JButton previousButton;
        private JButton playButton;
        private JButton nextButton;

        private Endpoint endpoint;

        public Player() {
            endpoint = new Endpoint(HOST, PORT);
            init();
        }

        private void init() {
            playerPanel = new JPanel();
            playerPanel.setLayout(new FlowLayout());

            previousButton = newButton(BonoIconFactory.getPreviousButtonIcon(), PREVIOUS);
            playButton = newButton(BonoIconFactory.getPlayButtonIcon(), PLAY);
            nextButton = newButton(BonoIconFactory.getNextButtonIcon(), NEXT);

        }

        private JButton newButton(Icon icon, String action) {
            JButton button = new JButton();
            button.setIcon(icon);
            button.setActionCommand(action);
            button.addActionListener(this);
            playerPanel.add(button);
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton model = (JButton) e.getSource();
            String action = model.getActionCommand();

            switch (action) {
                case PREVIOUS:
                    Runnable previous = () -> {
                        try {
                            endpoint.command(new Command(PlayerProperties.PREVIOUS));
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    };
                    new Thread(previous).start();
                    break;
                case PLAY:
                    Runnable play = () -> {
                        try {
                            endpoint.command(new Command(PlayerProperties.PLAY));
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    };
                    new Thread(play).start();
                    break;
                case NEXT:
                    Runnable next = () -> {
                        try {
                            endpoint.command(new Command(PlayerProperties.NEXT));
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    };
                    new Thread(next).start();
                    break;
                default:
                    break;
            }
        }

        public JPanel getPlayerPanel() {
            return playerPanel;
        }
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
            Endpoint endpoint = new Endpoint(HOST, PORT);
            // keeps running.
            while (true) {
                System.out.printf("%s, active: %s, %s\n", Thread.currentThread().getName(), Thread.activeCount(), getClass().getName());
                List<String> feedback = null;
                try {
                    feedback = endpoint.request(new Command(StatusProperties.IDLE));
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
            final Endpoint endpoint = new Endpoint(HOST, PORT);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.printf("%s, active: %s, %s\n", Thread.currentThread().getName(), Thread.activeCount(), getClass().getName());
                    List<String> statusList = null;
                    try {
                        statusList = endpoint.request(new Command(StatusProperties.STATUS));
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
