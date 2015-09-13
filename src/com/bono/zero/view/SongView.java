package com.bono.zero.view;

import javax.swing.*;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 *
 * View displaying the current song.
 */
public class SongView extends JPanel {

    // JLabel for the artist.
    private JLabel artist;

    // JLabel for the song.
    private JLabel song;

    public SongView() {
        super();
        init();
    }

    // creates the view
    private void init() {
        System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        artist = new JLabel();
        add(artist);
        song = new JLabel();
        add(song);
    }


    /*
    Returning the Jpanel containig the components
    that make up this view.
     */
    public JPanel getSongView() {
        return this;
    }

    public JLabel getArtist() {
        return artist;
    }

    public JLabel getSong() {
        return song;
    }

    public void setArtist(String artist) {
        if (artist == null) artist = "";
        this.artist.setText("artist : " + artist);
    }

    public void setTitle(String title) {
        if (title == null) title = "";
        this.song.setText("title: " + title);
    }
}
