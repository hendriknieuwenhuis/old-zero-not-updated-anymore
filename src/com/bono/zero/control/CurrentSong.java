package com.bono.zero.control;

import com.bono.zero.api.Playlist;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.events.PropertyEvent;
import com.bono.zero.api.events.PropertyListener;
import com.bono.zero.api.models.Control;
import com.bono.zero.api.models.Property;
import com.bono.zero.api.models.Song;
import com.bono.zero.view.SongView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class CurrentSong {

    // the view for the current song.
    private SongView view;


    private Playlist playlist;

    private ServerStatus serverStatus;

    public CurrentSong(Playlist playlist, ServerStatus serverStatus) {
        //view = new SongView();
        this.playlist = playlist;
        this.serverStatus = serverStatus;
        this.serverStatus.getStatus().getSongProperty().addPropertyListeners(new SongPosListener());
    }

    public void  setView(SongView songView) {
        view = songView;

    }

    public SongView getSongView() {
        return view;
    }


    @Deprecated
    public JPanel getView() {
        return view.getSongView();
    }

    private class SongPosListener implements PropertyListener {

        @Override
        public void propertyChange(PropertyEvent propertyEvent) {
            Property property = (Property) propertyEvent.getSource();
            Song song = playlist.getSong(Integer.parseInt((String)property.getValue()));
            String artist = song.getArtist();
            String title = song.getTitle();

            if (view != null) {
                SwingUtilities.invokeLater(() -> {
                    view.setArtist(artist);
                    view.setTitle(title);
                });
            }
        }
    }
}
