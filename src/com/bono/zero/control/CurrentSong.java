package com.bono.zero.control;

import com.bono.zero.api.Playlist;
import com.bono.zero.api.models.ServerProperty;
import com.bono.zero.view.SongView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class CurrentSong {

    // the view for the current song.
    private SongView view;


    private Playlist playlist;

    public CurrentSong(Playlist playlist) {
        view = new SongView();
        this.playlist = playlist;
    }


    private String artist(String id) {
        return playlist.getPlaylist().get(id).getArtist();
    }

    private String song(String id) {
        return playlist.getPlaylist().get(id).getTitle();
    }

    public JPanel getView() {
        return view.getSongView();
    }

    public ChangeListener getCurrentSongListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ServerProperty serverProperty = (ServerProperty) e.getSource();
                String songid = (String) serverProperty.getValue();
                view.getArtist().setText("Artist: " + playlist.getPlaylist().get(songid).getArtist());
                view.getSong().setText("Song: " + playlist.getPlaylist().get(songid).getTitle());
            }
        };

    }


}
