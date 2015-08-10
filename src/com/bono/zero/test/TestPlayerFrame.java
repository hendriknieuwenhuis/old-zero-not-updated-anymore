package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.*;
import com.bono.zero.control.CurrentSong;
import com.bono.zero.control.Idle;
import com.bono.zero.control.PlayerControl;
import com.bono.zero.control.SongScroller;
import com.bono.zero.view.PlayerView;
import com.bono.zero.view.ScrollView;
import com.bono.zero.view.SongView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 09/08/15.
 */
public class TestPlayerFrame {

    private static final String HOST = "192.168.2.2";
    private static final int PORT = 6600;

    private PlayerFrame playerFrame;

    private Player player;

    private PlayerControl playerControl;

    private PlayerView playerView;

    private Playlist playlist;

    private CurrentSong currentSong;

    private SongView songView;

    private ScrollView scrollView;

    private SongScroller songScroller;

    private ServerStatus serverStatus;

    private JFrame frame;

    public TestPlayerFrame() {
        init();
    }

    private void init() {
        initControll();


        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    initFrame();
                    showFrame();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        initModels();


    }

    private void initControll() {
        player = new Player(new Endpoint(HOST, PORT));
        playerControl = new PlayerControl(player);

        // playlist.
        playlist = new Playlist();

        currentSong = new CurrentSong(playlist);

        songScroller = new SongScroller(new Player(new Endpoint(HOST, PORT)));

        serverStatus = new ServerStatus();

        // set status listeners.
        serverStatus.getStatus().getState().setChangeListener(playerControl.getStateListener());
        serverStatus.getStatus().getSongid().setChangeListener(currentSong.getCurrentSongListener());
        serverStatus.getStatus().getTime().setChangeListener(songScroller.getCurrentTimeListener());
        serverStatus.getStatus().getState().setChangeListener(songScroller.getStateListener());




    }

    private void initModels() {
        Endpoint endpoint = new Endpoint(HOST, PORT);
        java.util.List<String> request = null;
        try {
            request = endpoint.request(new Command(ServerProperties.LIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playlist.populatePlaylist(request);

        // init status.
        List<String> statusList = null;
        try {
            statusList = endpoint.request(new Command(ServerProperties.STATUS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverStatus.setStatus(statusList);

        // idle thread.
        Idle idle = new Idle(serverStatus);
        Thread thread = new Thread(idle);
        thread.start();

    }

    private void initFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(1200, 200));
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        playerView = new PlayerView();
        panel.add(playerView);
        playerControl.setPlayerView(playerView);


        constraints.weightx = 0.5;
        constraints.gridx = 1;
        songView = new SongView();
        panel.add(songView);
        currentSong.setView(songView);

        constraints.weightx = 0.5;
        constraints.gridx = 2;
        scrollView = new ScrollView();
        panel.add(scrollView);
        songScroller.setScrollView(scrollView);

        Container container = frame.getContentPane();
        container.add(panel, BorderLayout.NORTH);


    }

    private void showFrame() {
        playerView.addActionListener(PlayerView.PREVIOUS, playerControl.getButtonsListener());
        playerView.addActionListener(PlayerView.STOP, playerControl.getButtonsListener());
        playerView.addActionListener(PlayerView.PLAY, playerControl.getButtonsListener());
        playerView.addActionListener(PlayerView.NEXT, playerControl.getButtonsListener());

        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new TestPlayerFrame();
    }

}
