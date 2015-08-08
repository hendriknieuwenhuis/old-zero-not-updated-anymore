package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.*;
import com.bono.zero.api.models.ServerProperty;
import com.bono.zero.control.CurrentSong;
import com.bono.zero.control.Idle;
import com.bono.zero.control.PlayerControl;
import com.bono.zero.control.SongScroller;
import com.bono.zero.laf.BonoIconFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 06/08/15.
 */
public class TestIdle {

    private static final String HOST = "192.168.2.2";
    private static final int PORT = 6600;

    public static void main(String[] args) {



        // status values.
        ServerStatus serverStatus = new ServerStatus();


        // playlist.
        Playlist playlist = new Playlist();
        Endpoint endpoint1 = new Endpoint(HOST, PORT);
        List<String> request = null;
        try {
            request = endpoint1.request(new Command(ServerProperties.LIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playlist.populatePlaylist(request);



        // frame holding the gui interface.
        JFrame frame = new JFrame();

        // panel that holds the interface parts.
        //
        //
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        // the player view/controller.
        PlayerControl playerControl = new PlayerControl(new Player(new Endpoint(HOST, PORT)));

        //constraints.weightx = 0.5;
        constraints.gridx = 0;

        // build interface.
        panel.add(playerControl.getView(), constraints);

        constraints.weightx = 0.5;
        constraints.gridx = 1;

        // the current song view/controller.
        CurrentSong currentSong = new CurrentSong(playlist);

        panel.add(currentSong.getView(), constraints);


        SongScroller songScroller = new SongScroller(new Player(new Endpoint(HOST, PORT)));

        constraints.weightx = 0.5;
        constraints.gridx = 2;

        panel.add(songScroller.getView(), constraints);


        // set the listeners to the server status.
        serverStatus.getStatus().getState().setChangeListener(new ListenerPrinter());
        serverStatus.getStatus().getSongid().setChangeListener(new ListenerPrinter());
        serverStatus.getStatus().getAudio().setChangeListener(new ListenerPrinter());
        serverStatus.getStatus().getState().setChangeListener(playerControl.getStateListener());
        serverStatus.getStatus().getSongid().setChangeListener(currentSong.getCurrentSongListener());
        serverStatus.getStatus().getTime().setChangeListener(songScroller.getCurrentTimeListener());

        // init status.
        List<String> statusList = null;
        Endpoint endpoint0 = new Endpoint(HOST, PORT);
        try {
            statusList = endpoint0.request(new Command(ServerProperties.STATUS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set status values.
        serverStatus.setStatus(statusList);

        // the idle thread, listens to the changes
        // in the server.
        Runnable idle = new Idle(serverStatus);
        new Thread(idle).start();

        frame.getContentPane().add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 200);
        //frame.pack();
        frame.setVisible(true);

    }

    private static class ListenerPrinter implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            ServerProperty serverProperty = (ServerProperty) e.getSource();
            System.out.printf("%s\n", (String) serverProperty.getValue());
        }
    }

    private static class InnerPlayer {

        private Player player;
        private Endpoint endpoint;

        private JPanel panel;
        private JButton next;

        public InnerPlayer() {
            endpoint = new Endpoint(HOST, PORT);
            player = new Player(endpoint);

            panel = new JPanel();
            next = new JButton(BonoIconFactory.getNextButtonIcon());
            next.addActionListener(getButtonListener());
            panel.add(next);

        }

        public JPanel getPanel() {
            return panel;
        }

        private ActionListener getButtonListener() {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // new runnable next command.
                    Runnable run = () -> {
                        try {
                            player.next();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    };

                    new Thread(run).start();
                }
            };
        }
    }
}
