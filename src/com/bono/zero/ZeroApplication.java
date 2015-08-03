package com.bono.zero;

import com.bono.zero.api.Endpoint;
import com.bono.zero.control.Controller;
import com.bono.zero.control.Server;
import com.bono.zero.control.ServerMonitor;
import com.bono.zero.control.UpdaterController;
import com.bono.zero.laf.ZeroTheme;
import com.bono.zero.model.Directory;
import com.bono.zero.model.Playlist;
import com.bono.zero.model.ServerStatus;
import com.bono.zero.model.Settings;
import com.bono.zero.view.*;
import com.bono.zero.view.SplashScreen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by hendriknieuwenhuis on 21/07/15.
 */
public class ZeroApplication extends Application {



    private Rectangle bounds;

    private Server server;
    private Directory directory;
    private Settings settings;
    private Controller controller;

    private Endpoint endpoint;


    @Override
    protected void start(JFrame frame) {
        ZeroFrame zeroFrame = new ZeroFrame(this.bounds, frame);

        //System.out.printf("%s\n", settings.getHost());

        // display the splash screen to indicate the application is starting
        SplashScreen.getSplashScreen(bounds);

        if (server == null) {
            server = new Server();
        }



        Directory directory = new Directory();

        // creating playlist model and setting the columns to be shown
        Playlist playlist = new Playlist();
        String[] columns = {"title", "album", "artist"};
        playlist.showColumns(columns);

        ServerStatus serverStatus = new ServerStatus();

        controller = new Controller();

        controller.addPlaylist(playlist);

        controller.addServerStatus(serverStatus);

        controller.addServer(server);

        controller.addDirectory(directory);

        controller.addSettings(settings);

        zeroFrame.getDirectoryPanel().addMouseAdapter(controller.getTreeMouseListener());
        zeroFrame.getDirectoryPanel().addTreeModel(directory.getModel());

        zeroFrame.getPlaylistPanel().addTableMouseListener(controller.getTableMouseListener());
        zeroFrame.getPlaylistPanel().setTableModel(playlist);
        playlist.addObserver("Playlist", zeroFrame.getPlaylistPanel());
        serverStatus.addObserver("Song", zeroFrame.getPlaylistPanel());

        zeroFrame.getPlaylistPopup().addAllMenuItemListener(controller.getPopupListener(zeroFrame.getPlaylistPanel().getSelectionModel()));
        //zeroFrame.getPlaylistPanel().addPopupMenu(playlistPopup);

        zeroFrame.getPlaybackControls().addAllButtonActionListener(controller.getControlsHandler());
        serverStatus.addObserver("State", zeroFrame.getPlaybackControls());
        serverStatus.addObserver("Repeat", zeroFrame.getPlaybackControls());
        serverStatus.addObserver("Random", zeroFrame.getPlaybackControls());


        serverStatus.addObserver("Consume", zeroFrame.getZeroMenuBar());
        serverStatus.addObserver("Single", zeroFrame.getZeroMenuBar());
        zeroFrame.getZeroMenuBar().addCheckBoxListeners(controller.getConfigListener());

        ServerMonitor serverMonitor = new ServerMonitor(new UpdaterController(controller));
        controller.addServerMonitor(serverMonitor);

        SplashScreen.close();

        zeroFrame.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        zeroFrame.getFrame().addWindowListener(this);
        zeroFrame.getFrame().setSize(screenSize());
        zeroFrame.getFrame().setVisible(true);
    }

    @Override
    protected void showSplashScreen() {
        SplashScreen.getSplashScreen(bounds);
    }


    /*
    Load the settings of the client. When there are no
    settings to load from file, initialize with user
    given settings.
     */
    @Override
    protected void init() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Settings> settingsFuture = executorService.submit(new SettingsInitializer(new Endpoint(), new Settings()));

        settings = null;
        try {
            settings = settingsFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }


    // Sets the look and feel
    @Override
    protected void setLookAndFeel() {
        Color gray = Color.LIGHT_GRAY;
        Color darkGray = Color.DARK_GRAY;
        Color lGray = new Color(185, 185, 185);
        Color lGrayDarker = new Color(155, 155, 155);
        Border none = BorderFactory.createEmptyBorder();
        Border dark = BorderFactory.createLineBorder(Color.darkGray, 1);
        Border pink = BorderFactory.createLineBorder(Color.PINK, 1);
        java.util.List buttonGradient = Arrays.asList(new Object[]{new Float(.3f), new Float(0f),
                new ColorUIResource(0xDDE8F3), Color.WHITE, gray});

        MetalLookAndFeel.setCurrentTheme(new ZeroTheme());
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        UIManager.put("Panel.border", none);
        UIManager.put("SplitPane.dividerSize", 1);
        UIManager.put("SplitPane.border", none);
        //UIManager.put("Button.border", dark);
        UIManager.put("ScrollPane.border", dark);
        UIManager.put("ScrollPane.background", lGray);
        UIManager.put("ScrollBar.squareButtons", false);
        UIManager.put("ScrollBar.width", 15);
        UIManager.put("ScrollBar.track", lGray);
        UIManager.put("ScrollBar.thumb", lGrayDarker);
        UIManager.put("ScrollBar.border", none);
        UIManager.put("slider.trackBorder", none);
        UIManager.put("Tree.background", gray);
        UIManager.put("Table.background", gray);
        UIManager.put("MenuBar.gradient", buttonGradient);
    }


    protected Dimension screenSize() {
        bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        return new Dimension(bounds.width, bounds.height);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "opened");
    }

    /*
    Save the settings of the client.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            SettingsLoader.saveSettings(settings);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void windowClosed(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "closed");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "iconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "deiconified");
    }

    /*
    When client is activated start "idle" mode if it was
    in such mode when deactivated!

    Initiate client from server status!
    */
    @Override
    public void windowActivated(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "activated");
    }

    /*
    When client is listening in "idle" state stop this.
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.printf("%s: %s\n", getClass().getName(), "deactivated");
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Server getServer() {
        return server;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }
}
