package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.models.Control;
import com.bono.zero.api.models.commands.Command;
import com.bono.zero.api.models.commands.ServerCommand;
import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.model.Directory;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by hendriknieuwenhuis on 06/09/15.
 */
public class FolderControl extends Control {

    //private FolderView folderView;
    private JTree folderView;

    private JPopupMenu popupMenu;

    private Directory directory;

    public FolderControl() {}

    public FolderControl(Directory directory) {
        this.directory = directory;
    }

    @Deprecated
    public FolderControl(Directory directory, JTree folderView) {
        this.directory = directory;
        setFolderView(folderView);
    }

    public FolderControl(String host, int port, ExecutorService executorService, Directory directory) {
        super(host, port, executorService);
    }

    public void updateDirectory(List<String> entry) {
        directory.setDirectory(entry);
    }

    public JTree getFolderView() {
        return folderView;
    }

    public void setFolderView(JTree folderView) {
        this.folderView = folderView;

        // set listeners for folderview
        folderView.addMouseListener(new TreeMouseListener(this.folderView));

        // create popup menu.
        popupMenu = new JPopupMenu();
        JMenuItem itemOne = new JMenuItem("add");
        itemOne.addActionListener(new MenuActionListener());
        popupMenu.add(itemOne);

    }


    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }



    private class TreeMouseListener extends MouseAdapter {

        private JTree tree;

        public TreeMouseListener(JTree tree) {
            this.tree = tree;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int row = tree.getRowForLocation(e.getX(), e.getY());
            if (row == -1) tree.clearSelection();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            doPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            doPopup(e);
        }

        private void doPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                if (tree.getSelectionCount() != 0) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }

    /*
    The MenuActionListener responses to the "add" menu
    being invoked. It gets the selected path and creates
    an uri of it to send as argument.
     */
    private class MenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //TreePath path = folderView.getSelectionPath();
            TreePath[] pathArray = folderView.getSelectionPaths();
            Endpoint endpoint = new Endpoint(host, port);


            // single selected path.
            if (pathArray.length == 1) {
                String pathString = makeURI(pathArray[0]);
                if (pathString != null) {
                    Runnable runnable = () -> {
                        String reply = null;
                        try {
                            reply = endpoint.sendCommand(new ServerCommand(PlaylistProperties.ADD, pathString));
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        System.out.println(reply);
                    };
                    executorService.execute(runnable);
                }


                // multiple selected paths.
            } else if (pathArray.length > 1) {
                System.out.println("More than one");
                List<Command> commands = new ArrayList<>();
                commands.add(new ServerCommand(Endpoint.COMMAND_LIST_OK_BEGIN));
                for (TreePath treePath : pathArray) {
                    String pathString = makeURI(treePath);
                    //System.out.println(pathString);
                    commands.add(new ServerCommand(PlaylistProperties.ADD, pathString));
                }
                commands.add(new ServerCommand(Endpoint.COMMAND_LIST_END));

                Runnable runnable = () -> {
                    String reply = null;
                    try {
                        reply = endpoint.sendCommand(commands);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    System.out.println(reply);
                };
                executorService.execute(runnable);

            }
        }

        /*
        * Method makes the path as a String ready
        * to be send to the server as a parameter,
        * to add the file or folder to a playlist.
        */
        private String makeURI(TreePath treePath) {

            // return a quotated string of the path.
            Object[] path = treePath.getPath();
            String returnPath = "\"";
            for (int i = 1; i < path.length; i++) {
                if (i == (path.length-1)) {
                    returnPath = returnPath + path[i];
                    returnPath =returnPath + "\"";
                    return returnPath;
                }
                returnPath = returnPath + path[i]+File.separator;
            }
            return null;
        }


    }
}
