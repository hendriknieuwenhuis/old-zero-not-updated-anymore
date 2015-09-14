package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.models.PlaylistTableModel;
import com.bono.zero.api.models.commands.ServerCommand;
import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.control.*;
import com.bono.zero.model.Directory;
import com.bono.zero.view.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hendriknieuwenhuis on 06/09/15.
 */
public class ViewTestApplicationView extends WindowAdapter {

    private final String HOST = "192.168.2.2";
    private final int PORT = 6600;

    private ApplicationView applicationView;

    private FolderControl folderControl;

    private Directory directory;

    private PlaylistControl playlistControl;

    private Playlist playlist;

    private PlaylistTableModel playlistTableModel;

    private ServerStatus serverStatus;

    private PlayerControl playerControl;

    private CurrentSong currentSong;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ViewTestApplicationView() {
        initModels();
        initControllers();
        init();
    }

    /*
    Initiate the models directory, playlist & tableModel.
     */
    private void initModels() {
        Endpoint endpoint = new Endpoint(HOST, PORT);
        List<String> entry = null;
        try {
            entry = endpoint.sendRequest(new ServerCommand(ServerProperties.LIST_ALL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        directory = new Directory(entry);

        entry = null;

        // get the playlist.
        try {
            entry = endpoint.sendRequest(new ServerCommand(PlaylistProperties.PLAYLISTINFO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create Playlist.
        playlist = new Playlist(entry);

        entry = null;
        try {
            entry = endpoint.sendRequest(new ServerCommand(ServerProperties.STATUS));
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverStatus = new ServerStatus(entry);

        String[] columnNames = {"", "title", "artist"};
        playlistTableModel = new PlaylistTableModel(null, columnNames);




        playlistTableModel.setColumnCount(3);
        //playlistTableModel.setRowCount(playlist.getList().size());

        System.out.println(playlistTableModel.getRowCount());

        playlistTableModel.setPlaylistTableModel(playlist);

    }

    /*
    Initiate the controllers folderControl, playerExecutor & playlistControl.
     */
    private void initControllers() {
        folderControl = new FolderControl(HOST, PORT, executorService, directory);

        // create PlaylistControl.
        playlistControl = new PlaylistControl(HOST, PORT, executorService, playlist, playlistTableModel);


        playerControl = new PlayerControl(HOST, PORT, executorService, serverStatus);
        serverStatus.getStatus().getStateProperty().addPropertyListeners(playerControl.getStatePropertyListener());

        currentSong = new CurrentSong(playlist, serverStatus);

        // thread idle.
        Idle idle = new Idle(HOST, PORT, serverStatus, playlistControl.getPlaylist());
        Thread thread = new Thread(idle);
        thread.start();
    }

    /*
    Initiate the frame.
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {
            applicationView = new ApplicationView();
            applicationView.addWindowListener(this);
            folderControl.setFolderView(applicationView.getFolderView());
            folderControl.init();
            applicationView.getFolderView().setModel(folderControl.getDirectory().getModel());

            applicationView.getPlaylistView().setModel(playlistTableModel);
            applicationView.getPlaylistView().getColumnModel().getColumn(0).setPreferredWidth(5);

            playlistControl.setPlaylistView(applicationView.getPlaylistView());
            playlistControl.init();
            playlistControl.setColumnWidth(0);
            playerControl.setPlayerView(applicationView.getPlayerView());
            playerControl.init();
            currentSong.setView(applicationView.getSongView());
            applicationView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            applicationView.pack();
            applicationView.setVisible(true);
        });
    }

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);

        System.out.println("Activated " + Thread.currentThread().getName());

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        super.windowDeactivated(e);

        System.out.println("Deactivated");

        /*
        playerExecutor.end();
        playerExecutorThread = null;

        directory = null;
        playlist = null;*/

    }

    @Override
    public void windowIconified(WindowEvent e) {
        super.windowIconified(e);

        System.out.println("Iconified");

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        super.windowDeiconified(e);

        System.out.println("Deiconified");



    }

    public static void main(String[] args) {
        new ViewTestApplicationView();
    }
}
