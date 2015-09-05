package com.bono.zero.control;

import com.bono.zero.api.Playlist;
import com.bono.zero.api.RequestCommand;
import com.bono.zero.api.models.Song;
import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.view.PlaylistView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 05/09/15.
 */
public class PlaylistControl {

    private int row;

    private PlaylistView playlistView;

    private Playlist playlist;

    private PlayerExecutor playerExecutor;

    public PlaylistControl(PlayerExecutor playerExecutor, PlaylistView playlistView, Playlist playlist) {
        this.playerExecutor = playerExecutor;
        this.playlistView = playlistView;
        this.playlist = playlist;
    }

    public ListSelectionListener getListSelectionListener() {

        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()) {
                    ListSelectionModel listSelectionModel = (ListSelectionModel)e.getSource();
                    Song song = playlist.getList().get(listSelectionModel.getAnchorSelectionIndex());
                    playerExecutor.playid(song.getId());

                }
            }
        };

    }


}
