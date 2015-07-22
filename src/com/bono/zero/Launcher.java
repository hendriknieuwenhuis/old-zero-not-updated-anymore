package com.bono.zero;

import com.bono.zero.control.Controller;
import com.bono.zero.control.Server;
import com.bono.zero.control.ServerMonitor;
import com.bono.zero.control.UpdaterController;
import com.bono.zero.model.*;
import com.bono.zero.view.*;
import com.bono.zero.view.SplashScreen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * Created by hennihardliver on 23/05/14.
 */
public class Launcher {

    private static final String HOST_PREFIX = "host:";
    private static final String PORT_PREFIX = "port:";

    private static boolean waiting = false;

    private static final Object lock = new Object();

    public static void launch(final String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                launchApplication(args);
            }
        }).start();

    }


    /**
     * Launcher for the application. First there will be checked whether if there are
     * arguments used for the host and port.
     * The splashscreen is loaded / displayed while the settings are loaded if no
     * main arguments are used and the contact to the server is initialized.
     * @param args
     */
    public static void launchApplication(final String[] args) {
        // get the size of the desktop
        final Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        // display the splash screen to indicate the application is starting
        SplashScreen.getSplashScreen(bounds);

        // Initiate contact with the server.
        Server server = new Server();
        Settings settings = initConnection(args, server);

        Directory directory = new Directory();

        Controller controller = new Controller();

        // creating playlist model and setting the columns to be shown
        Playlist playlist = new Playlist();
        String[] columns = {"title", "album", "artist"};
        playlist.showColumns(columns);
        controller.addPlaylist(playlist);

        ServerStatus serverStatus = new ServerStatus();
        controller.addServerStatus(serverStatus);

        controller.addServer(server);

        controller.addDirectory(directory);

        controller.addSettings(settings);

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!
        // added this to zeroframe.
        //
        // create the directory panel
        DirectoryPanel directoryPanel = new DirectoryPanel();
        directoryPanel.addMouseAdapter(controller.getTreeMouseListener());  // not this
        directoryPanel.addTreeModel(directory.getDirectory());  // not this

        // create the playlist panel
        PlaylistPanel playlistPanel = new PlaylistPanel();
        playlistPanel.addTableMouseListener(controller.getTableMouseListener());  // not this
        playlistPanel.setTableModel(playlist);  // not this
        playlist.addObserver("Playlist", playlistPanel);  // not this
        serverStatus.addObserver("Song", playlistPanel);  // not this
        PlaylistPopup playlistPopup = new PlaylistPopup();
        //playlistPopup.addMenuItemListener("JMenuItem.remove", controller.getRemoveListener(playlistPanel.getSelectionModel()));
        playlistPopup.addAllMenuItemListener(controller.getPopupListener(playlistPanel.getSelectionModel()));  // not this
        playlistPanel.addPopupMenu(playlistPopup);

        // creating a splitpane for the center view
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, directoryPanel, playlistPanel);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation((bounds.width/3));

        PlaybackControls playbackControls = new PlaybackControls();
        playbackControls.addAllButtonActionListener(controller.getControlsHandler());  // not this
        serverStatus.addObserver("State", playbackControls);  // not this
        serverStatus.addObserver("Repeat", playbackControls);  // not this
        serverStatus.addObserver("Random", playbackControls);  // not this

        ZeroMenuBar zeroMenuBar = new ZeroMenuBar();
        serverStatus.addObserver("Consume", zeroMenuBar);  // not this
        serverStatus.addObserver("Single", zeroMenuBar);  // not this
        zeroMenuBar.addCheckBoxListeners(controller.getConfigListener());  // not this
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        ServerMonitor serverMonitor = new ServerMonitor(new UpdaterController(controller));
        controller.addServerMonitor(serverMonitor);

        ZeroFrame view = new ZeroFrame();
        //view.addWindowListener(controller.getZeroFrameListener());
        view.addPanel(splitPane, BorderLayout.CENTER);
        view.addPanel(playbackControls, BorderLayout.NORTH);
        view.addMenuBar(zeroMenuBar);

        controller.addZeroFrame(view);


        //serverMonitor.start();

        SplashScreen.close();

        view.showFrame(new Dimension(bounds.width, bounds.height), true);

    }

    private static Settings initConnection(String[] args, Server server) {
        Settings settings = null;
        if (args != null) {

            String host = null;
            int port = 6600;

            for (String arg : args) {
                if (arg.startsWith(HOST_PREFIX)) {
                    host = arg.substring(HOST_PREFIX.length());
                }
                if (arg.startsWith(PORT_PREFIX)) {
                    port = Integer.parseInt(arg.substring(PORT_PREFIX.length()));
                }
            }
            if (host != null) {
                settings = Settings.getSettings();
                settings.setHost(host);
                settings.setPort(port);

                // add a test for  the settings!
                testSettings(settings, server);

                return settings;
            }
        }
        // if no argument for the host is given!
        try {
            // loading the settings from settings.set file.
            settings = SettingsLoader.loadSettings();
            testSettings(settings, server);
            return settings;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // if there is no settings.set file
            settings = Settings.getSettings();
            new SettingsDialog(settings);
            goWait();
            System.out.println("Continue, and go testing");
            testSettings(settings, server);
            try {
                SettingsLoader.saveSettings(settings);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return settings;
        }

        return settings;
    }

    // test the settings that are given. When no connection
    // is established we will ask again for settings.
    private static void testSettings(Settings settings, Server server) {

        try {
            server.setAddress(settings.getAddress());
            server.checkConnectionSettings();

        } catch (IllegalArgumentException e) {

            new SettingsDialog(settings);
            goWait();
            testSettings(settings, server);

        } catch (IOException e) {

            new SettingsDialog(settings);
            goWait();
            testSettings(settings, server);

        }

    }

    private static void goWait() {
        synchronized (lock) {
            try {
                waiting = true;
                while (waiting) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void doNotify() {
        synchronized (lock) {
            waiting = false;
            lock.notify();
        }
    }

}
