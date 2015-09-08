package com.bono.zero.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hendriknieuwenhuis on 06/09/15.
 */
public class ApplicationView extends JFrame {

    private PlayerView playerView;

    private ScrollView scrollView;

    private SongView songView;

    private PlaylistView playlistView;

    private FolderView folderView;

    public ApplicationView() throws HeadlessException {
        init();
        placePanels();
    }

    public ApplicationView(GraphicsConfiguration gc) {
        super(gc);
        init();
        placePanels();
    }

    public ApplicationView(String title) throws HeadlessException {
        super(title);
        init();
        placePanels();
    }

    public ApplicationView(String title, GraphicsConfiguration gc) {
        super(title, gc);
        init();
        placePanels();
    }

    private void init() {
        playerView = new PlayerView();
        scrollView = new ScrollView();
        songView = new SongView();
        playlistView = new PlaylistView();
        folderView = new FolderView();
    }


    private void placePanels() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout());
        playerPanel.add(playerView);
        playerPanel.add(songView);
        playerPanel.add(scrollView);
        this.getContentPane().add(playerPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setContinuousLayout(true);
        splitPane.setLeftComponent(folderView);
        splitPane.setRightComponent(playlistView);
        this.getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public SongView getSongView() {
        return songView;
    }

    public PlaylistView getPlaylistView() {
        return playlistView;
    }

    public FolderView getFolderView() {
        return folderView;
    }
}
