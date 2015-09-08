package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.RequestCommand;
import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.control.FolderControl;
import com.bono.zero.control.PlayerExecutor;
import com.bono.zero.control.PlaylistControl;
import com.bono.zero.model.Directory;
import com.bono.zero.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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

    private PlayerExecutor playerExecutor;

    private Playlist playlist;

    private DefaultTableModel tableModel;

    private Thread playerExecutorThread;

    public ViewTestApplicationView() {
        //initModels();
        //initControllers();
        init();
    }

    /*
    Initiate the models directory, playlist & tableModel.
     */
    private void initModels() {
        Endpoint endpoint = new Endpoint(HOST, PORT);
        List<String> entry = null;
        try {
            entry = endpoint.sendRequest(new RequestCommand(ServerProperties.LIST_ALL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        directory = new Directory(entry);

        entry = null;

        // get the playlist.
        try {
            entry = endpoint.sendRequest(new RequestCommand(PlaylistProperties.PLAYLISTINFO));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create Playlist.
        playlist = new Playlist(entry);

        tableModel = new DefaultTableModel();

        tableModel.setColumnCount(3);
        tableModel.setRowCount(playlist.getList().size());

        System.out.println(tableModel.getRowCount());

        for (int i = 0; i < playlist.getList().size(); i++) {
            tableModel.setValueAt(playlist.getList().get(i).getPos(), i, 0);
            tableModel.setValueAt(playlist.getList().get(i).getTitle(), i, 1);
            tableModel.setValueAt(playlist.getList().get(i).getArtist(), i, 2);
        }

    }

    /*
    Initiate the controllers folderControl, playerExecutor & playlistControl.
     */
    private void initControllers() {

        folderControl = new FolderControl(directory);

        // create PlayerExecutor.
        playerExecutor = new PlayerExecutor(new Endpoint(HOST, PORT));
        playerExecutorThread = new Thread(playerExecutor);

        playerExecutorThread.start();

        // create PlaylistControl.
        playlistControl = new PlaylistControl(playerExecutor, playlist);


    }

    /*
    Initiate the frame.
     */
    private void init() {
        SwingUtilities.invokeLater(() -> {

            applicationView = new ApplicationView();
            applicationView.addWindowListener(this);
            // applicationView.getFolderView().getFolderTree().setModel(folderControl.getDirectory().getModel());
            // applicationView.getPlaylistView().getTable().setModel(tableModel);
            // folderControl.setFolderView(applicationView.getFolderView());
            // playlistControl.setPlaylistView(applicationView.getPlaylistView());
            applicationView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            applicationView.pack();
            applicationView.setVisible(true);
        });
    }

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);

        System.out.println("Activated " + Thread.currentThread().getName());

        initModels();
        initControllers();

        applicationView.getFolderView().getFolderTree().setModel(folderControl.getDirectory().getModel());
        applicationView.getPlaylistView().setTableModel(tableModel);
        folderControl.setFolderView(applicationView.getFolderView());
        playlistControl.setPlaylistView(applicationView.getPlaylistView());

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        super.windowDeactivated(e);

        System.out.println("Deactivated");

        playerExecutor.end();
        playerExecutorThread = null;

        directory = null;
        playlist = null;

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
