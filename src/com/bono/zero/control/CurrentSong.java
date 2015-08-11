package com.bono.zero.control;

import com.bono.zero.api.Playlist;
import com.bono.zero.api.models.Property;
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

    public CurrentSong(Playlist playlist) {
        //view = new SongView();
        this.playlist = playlist;
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

    public PropertyChangeListener getCurrentSongListener() {

        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String songid = (String) evt.getNewValue();
                String oldValue = (String) evt.getOldValue();

                // only run if value is changed.
                if (!songid.equals(oldValue)) {
                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());

                            view.getArtist().setText("Artist: " + playlist.getSong(songid).getArtist());
                            view.getSong().setText("Song: " + playlist.getSong(songid).getTitle());
                        }
                    }); // END EDT
                }
            }
        };

        /*
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Property property = (Property) e.getSource();
                String songid = (String) property.getValue();

                // EDT
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());

                        view.getArtist().setText("Artist: " + playlist.getSong(songid).getArtist());
                        view.getSong().setText("Song: " + playlist.getSong(songid).getTitle());
                    }
                }); // END EDT

            }
        };*/

    }


}
