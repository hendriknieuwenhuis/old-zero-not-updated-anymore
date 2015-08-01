package com.bono.zero.view;

import javax.swing.*;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 *
 * View displaying the current song.
 */
public class SongView {

    // JPanel holding all the components.
    private JPanel mainPanel;

    // JLabel for the artist.
    private JLabel artist;

    // JLabel for the song.
    private JLabel song;

    public SongView() {
        init();
    }

    // creates the view
    private void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        artist = new JLabel();
        mainPanel.add(artist);
        song = new JLabel();
        mainPanel.add(song);
    }

    /*
    Returning the Jpanel containig the components
    that make up this view.
     */
    public JPanel getSongView() {
        return mainPanel;
    }

    public JLabel getArtist() {
        return artist;
    }

    public JLabel getSong() {
        return song;
    }
}
