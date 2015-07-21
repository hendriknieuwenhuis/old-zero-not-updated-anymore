package com.bono.zero.control;

import com.bono.zero.model.*;
import com.bono.zero.view.ZeroFrame;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hennihardliver on 25/05/14.
 *
 * <p>Title: Controller.java</p>
 */
public class Controller {

    // The current playlist
    Playlist playlist;
    // The server status
    ServerStatus serverStatus;
    // the frame of the interface
    ZeroFrame zeroFrame;
    // the connection class
    Server server;
    // the connection settings
    Settings settings;
    // the directory structure
    Directory directory;

    ServerMonitor serverMonitor;

    // Listeners
    TreeMouseListener treeMouseListener;
    TableMouseListener tableMouseListener;
    ControlsListener controlsListener;
    ConfigListener configListener;
    PopupListener popupListener;

    public Controller() {}


    /**
     * Method sendCommand, calls the servers object's
     * sendCommand method and passes an command object
     * as argument.
     * @param command
     */
    private void sendCommand(Command command) {
        try {
            server.sendCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method sendCommand, calls the servers object's
     * sendCommand method and passes an List of command
     * objects as argument.
     * @param commandList List<Command> object.
     */
    private void sendCommand(List<Command> commandList) {
        try {
            server.sendCommand(commandList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ControlsListener getControlsHandler() {
        if (controlsListener == null) {
            controlsListener = new ControlsListener();
        }
        return controlsListener;
    }

    // instantiate if null and return the listener for the tree.
    public TreeMouseListener getTreeMouseListener() {
        if (treeMouseListener == null) {
            treeMouseListener = new TreeMouseListener();
        }
        return treeMouseListener;
    }

    // instantiate and return th listener for the table
    public TableMouseListener getTableMouseListener() {
        if (tableMouseListener == null) {
            tableMouseListener = new TableMouseListener();
        }
        return tableMouseListener;
    }

    public ConfigListener getConfigListener() {
        if (configListener == null) {
            configListener = new ConfigListener();
        }
        return configListener;
    }

    public ActionListener getPopupListener(ListSelectionModel selectionModel) {
        if (popupListener == null) {
            popupListener = new PopupListener(selectionModel);
        }
        return popupListener;
    }

    public WindowListener getZeroFrameListener() {
        return new ZeroFrameListener();
    }

    public void addPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void addServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    public void addZeroFrame(ZeroFrame zeroFrame) {
        this.zeroFrame = zeroFrame;
    }

    public void addServer(Server server) {
        this.server = server;
    }

    public void addSettings(Settings settings) {
        this.settings = settings;
    }

    public void addDirectory(Directory directory) {
        this.directory = directory;
    }

    public void addServerMonitor(ServerMonitor serverMonitor) {
        this.serverMonitor = serverMonitor;
    }

    public Server getServer() {
        return server;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
    
    public Directory getDirectory() {
        return directory;
    }


    private class ControlsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            AbstractButton button = (AbstractButton) e.getSource();

            if ((e.getActionCommand().equals(Server.REPEAT)) || (e.getActionCommand().equals(Server.RANDOM))) {

                if (!button.isSelected()) {
                    sendCommand(new Command(e.getActionCommand(), "0"));
                } else {
                    sendCommand(new Command(e.getActionCommand(), "1"));
                }
            } else if (e.getActionCommand().equals(Server.PAUSE)) {
                sendCommand(new Command(e.getActionCommand(), "1"));
            } else {
                sendCommand(new Command(e.getActionCommand()));
            }

        }

    }

    private class TreeMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {

            JTree tree = (JTree)  e.getSource();

            if (e.getClickCount() == 2) {
                TreePath selectedPath = tree.getSelectionPath();
                // TODO -- gives null pointer exceptions -- !!!!!
                Object[] path = selectedPath.getPath();
                String directoryPath = makePath(path);
                if ((directoryPath.endsWith("cue")) || (directoryPath.endsWith("m3u"))) {
                    //System.out.println(directoryPath);
                    sendCommand(new Command(Server.LOAD, directoryPath));
                    return;
                }
                //System.out.println(directoryPath);
                sendCommand(new Command(Server.ADD, directoryPath));
            }
        }

		/*
		 * Method makes the path as a String ready
		 * to be send to the server as a parameter,
		 * to add the file or folder to a playlist.
		 */
		private String makePath(Object[] path) {
			String returnPath = "";
			for (int i = 1; i < path.length; i++) {
				if (i == (path.length-1)) {
					returnPath = returnPath + path[i];
					return returnPath;
				}
				returnPath = returnPath + path[i]+"/";
			}
			return returnPath;
		}
	}

    private class TableMouseListener extends MouseAdapter {


        @Override
        public void mouseReleased(MouseEvent e) {

            JTable table = (JTable) e.getSource();
            ListSelectionModel selectionModel = table.getSelectionModel();
            int max=0;
            int min=0;

            if (e.getClickCount() == 2) {
                // play the double clicked song.
                int row = selectionModel.getLeadSelectionIndex();
                String song = (String) playlist.getElementAt(row, Playlist.ID_C);
                sendCommand(new Command("playid", song));
                selectionModel.clearSelection();
            } else {
                if (e.isShiftDown()) {
                    max = selectionModel.getMaxSelectionIndex();
                }
                min = selectionModel.getMinSelectionIndex();
                max = selectionModel.getMaxSelectionIndex();

                selectionModel.setSelectionInterval(min, max);
                table.repaint();
            }

        }
    }

    /**
     * The listener class for the popup menu of the playlist table. The
     * items "remove and "clearall" are given this listener.
     */
    private class PopupListener implements ActionListener {

        private ListSelectionModel selectionModel;   // the selection model of the table containing the popup menu.

        public PopupListener(ListSelectionModel selectionModel) {
            this.selectionModel = selectionModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // --- for the clear all menu item. ---
            if (e.getActionCommand().equals("clearAll")) {
                sendCommand(new Command(Server.CLEAR));
            // --- for the remove menu item. ---
            } else if (e.getActionCommand().equals("remove")) {
                if (selectionModel.isSelectionEmpty()) {
                    return;
                }else if (selectionModel.getMinSelectionIndex() == selectionModel.getMaxSelectionIndex()) {
                    // remove the selected song
                    sendCommand(new Command(Server.REMOVE_ID, (String) playlist.getElementAt(selectionModel.getMinSelectionIndex(), Playlist.ID_C)));
                    selectionModel.clearSelection();
                } else {
                    // remove multiple selected songs
                    int start = selectionModel.getMinSelectionIndex();
                    int end = selectionModel.getMaxSelectionIndex();
                    ArrayList<Command> ids = new ArrayList<Command>();
                    ids.add(new Command(Server.START_COM_LIST));
                    for (int i = start; i <= end; i++) {
                        String param = (String)playlist.getElementAt(i, Playlist.ID_C);
                        ids.add(new Command(Server.REMOVE_ID, param));
                    }
                    ids.add(new Command(Server.END_COM_LIST));
                    sendCommand(ids);
                }
            }

        }
    }

    /**
     * Listener for the config menu of the  menu bar.
     */
    private class ConfigListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem) e.getSource();

            // The command is send after the check box is selected
            // or deselected. The isSelected boolean gives back
            // the select or deselect action just performed.
            // At this point the server is not jet updated so the
            // consume variable in ServerStatus does not jet
            // correspondent with the button state. After the
            // ServerStatus is updated this is corrected.
            if (!checkBox.isSelected()) {
                sendCommand(new Command(checkBox.getActionCommand(), "0"));
            } else {
                sendCommand(new Command(checkBox.getActionCommand(), "1"));
            }

        }

    }

    private class ZeroFrameListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // is called on start and on deiconified
            serverMonitor.start();
        }

        @Override
        public void windowClosing(WindowEvent e) {
            // called on iconified
            System.out.println(e.toString());
            serverMonitor.stop();
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {
            // called on iconified
            System.out.println("icon");
            serverMonitor.stop();
            System.out.println("stop called in listener");

        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // is called on start and on deiconified
            serverMonitor.start();

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

}
