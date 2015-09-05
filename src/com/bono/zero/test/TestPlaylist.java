package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.RequestCommand;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.models.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.models.Song;
import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.control.Idle;
import com.bono.zero.control.PlayerExecutor;
import com.bono.zero.control.PlaylistControl;
import com.bono.zero.view.PlaylistView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 29/07/15.
 */
public class TestPlaylist {

    private Endpoint endpoint;

    private PlayerExecutor playerExecutor;

    private DefaultTableModel tableModel;

    private Playlist playlist;

    private PlaylistView playlistView;

    private PlaylistControl playlistControl;

    private ListSelectionModel listSelectionModel;

    private ServerStatus serverStatus;

    public TestPlaylist() {
        init();
    }

    private void init() {
        endpoint = new Endpoint();
        endpoint.setHost("192.168.2.2");
        endpoint.setPort(6600);
        serverStatus = new ServerStatus();
        new Thread(new Idle(endpoint.getHost(), endpoint.getPort(), serverStatus)).start();
        playerExecutor = new PlayerExecutor(endpoint);
        new Thread(playerExecutor).start();
        List<String> request = null;
        try {
            request = endpoint.sendRequest(new RequestCommand(ServerProperties.LIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playlist = new Playlist();
        playlist.populate(request);

        tableModel = new DefaultTableModel();

        tableModel.setColumnCount(3);
        tableModel.setRowCount(playlist.getList().size());

        System.out.println(tableModel.getRowCount());

        for (int i = 0; i < playlist.getList().size(); i++) {
            tableModel.setValueAt(playlist.getList().get(i).getPos(), i, 0);
            tableModel.setValueAt(playlist.getList().get(i).getTitle(), i, 1);
            tableModel.setValueAt(playlist.getList().get(i).getArtist(), i, 2);
        }

        playlistControl = new PlaylistControl(playerExecutor, playlistView, playlist);



        SwingUtilities.invokeLater(() -> {
            playlistView = new PlaylistView(tableModel);
            //playlistView.getTable().setModel(tableModel);
            listSelectionModel = playlistView.getTable().getSelectionModel();
            listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listSelectionModel.addListSelectionListener(playlistControl.getListSelectionListener());
            JFrame frame = new JFrame("testplaylist");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(playlistView);
            frame.pack();
            frame.setVisible(true);
        });

    }

    private void print(List<String> entry) {
        for (String s : entry) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        new TestPlaylist();
    }
}
