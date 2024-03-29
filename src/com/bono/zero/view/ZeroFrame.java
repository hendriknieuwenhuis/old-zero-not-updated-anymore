package com.bono.zero.view;

import java.awt.*;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * <p>Title: ZeroFrame.java</p>
 *
 * <p>Description: Class ZeroFrame.java holds the JFrame of
 * the GUI with the components of the GUI.
 * It is initialised with a JPanel with a BorderLayout and an
 * EmptyBorder set (5,5,5,5).
 * </p>
 */
public class ZeroFrame {

    // The JFrame holding the interface
    private JFrame frame;

    // holds the components of the frame
    private JPanel mainPanel;

    // JSplitpane for the center view.
    private JSplitPane centerSplitPane;

    // The size of the screen.
    private Rectangle bounds;

    private DirectoryPanel directoryPanel;
    private PlaylistPanel playlistPanel;
    private PlaybackControls playbackControls;
    private PlaylistPopup playlistPopup;
    private ZeroMenuBar zeroMenuBar;

    private SongView songView;


    public ZeroFrame(Rectangle bounds, JFrame frame) {
        this.bounds = bounds;
        this.frame = frame;
        this.frame.setTitle("Zero");
        init();
    }

    // init the frame with the panels that display
    // the music map, playlist, controls, etc.
    private void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        directoryPanel = new DirectoryPanel();

        playlistPanel = new PlaylistPanel();

        playlistPopup = new PlaylistPopup();

        playlistPanel.addPopupMenu(playlistPopup);

        centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, directoryPanel, playlistPanel);
        centerSplitPane.setContinuousLayout(true);
        centerSplitPane.setDividerLocation((bounds.width/3));

        playbackControls = new PlaybackControls();

        // holdint he playback controls and the song info.
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout());
        songView = new SongView();
        playerPanel.add(playbackControls);
        playerPanel.add(songView.getSongView());

        zeroMenuBar = new ZeroMenuBar();

        mainPanel.add(centerSplitPane, BorderLayout.CENTER);
        mainPanel.add(playerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel);
        frame.setJMenuBar(zeroMenuBar);
    }




    public void addMenuBar(JMenuBar menuBar) {
        frame.setJMenuBar(menuBar);
    }

    /**
     * Add a panel to the JFrame at the given index.
     * @param component a JPanel
     * @param location the location in the BorderLayout.
     */
    public void addPanel(Component component, String location) {
        mainPanel.add(component, location);

    }

    /**
     * Adds the <code>MainPanel</code>, makes the GUI visible and sets the size.
     * @param dimension
     * @param visible
     */
    public void showFrame(Dimension dimension, boolean visible) {
        frame.getContentPane().add(mainPanel);
        frame.setSize(dimension);
        frame.setVisible(visible);
    }

    public DirectoryPanel getDirectoryPanel() {
        return directoryPanel;
    }

    public PlaylistPanel getPlaylistPanel() {
        return playlistPanel;
    }

    public PlaybackControls getPlaybackControls() {
        return playbackControls;
    }

    public PlaylistPopup getPlaylistPopup() {
        return playlistPopup;
    }

    public ZeroMenuBar getZeroMenuBar() {
        return zeroMenuBar;
    }

    public JFrame getFrame() {
        return frame;
    }

}
