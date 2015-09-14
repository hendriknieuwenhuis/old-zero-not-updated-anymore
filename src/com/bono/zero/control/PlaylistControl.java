package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.events.PlaylistEvent;
import com.bono.zero.api.events.PlaylistListener;
import com.bono.zero.api.models.Control;
import com.bono.zero.api.models.PlaylistTableModel;
import com.bono.zero.api.models.Song;
import com.bono.zero.api.models.commands.ServerCommand;
import com.bono.zero.api.properties.PlayerProperties;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by hendriknieuwenhuis on 05/09/15.
 */
public class PlaylistControl extends Control {

    private int row;

    private JTable playlistView;

    private Playlist playlist;

    private PlaylistTableModel playlistTableModel;

    public PlaylistControl() {}

    public PlaylistControl(String host, int port, ExecutorService executorService, Playlist playlist, PlaylistTableModel playlistTableModel) {
        super(host, port, executorService);
        this.playlist = playlist;
        this.playlistTableModel = playlistTableModel;
    }

    /*
    init(), creates a jpopupmenu, adds listeners to the popupmenu
    and the playlisttablemodel to redraw the playlistView.
     */
    @Override
    public void init() {
        playlist.addPlaylistListener(getPlaylistListener());
    }

    public void setColumnWidth(int column) {

        int width = 0;
        for (row = 0; row < playlistView.getRowCount(); row++) {
            TableCellRenderer renderer = playlistView.getCellRenderer(row, column);
            Component comp = playlistView.prepareRenderer(renderer, row, column);
            width = Math.max (comp.getPreferredSize().width, width);
        }
        playlistView.getColumnModel().getColumn(column).setPreferredWidth(width);
        System.out.println(getClass().getName() + " " + width);

    }



    public JTable getPlaylistView() {
        return playlistView;
    }

    public void setPlaylistView(JTable playlistView) {
        this.playlistView = playlistView;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }


    private PlaylistListener getPlaylistListener() {
        return (PlaylistEvent event) -> {
            playlistTableModel.setPlaylistTableModel(((Playlist) event.getSource()));
        };
    }



    public ListSelectionListener getListSelectionListener() {

        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {
                    ListSelectionModel listSelectionModel = (ListSelectionModel)e.getSource();
                    Song song = playlist.getList().get(listSelectionModel.getAnchorSelectionIndex());
                    //playerExecutor.playid(song.getId());

                    Runnable runnable = () -> {
                        String reply = null;
                        Endpoint endpoint = new Endpoint(host, port);
                        try {
                            reply = endpoint.sendCommand(new ServerCommand(PlayerProperties.PLAYID, song.getId()));
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        System.out.println(reply);
                    };
                executorService.execute(runnable);
                }
            }
        };

    }


}
