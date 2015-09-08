package com.bono.zero.control;

import com.bono.zero.model.Directory;
import com.bono.zero.view.FolderView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 06/09/15.
 */
public class FolderControl {

    private FolderView folderView;

    private Directory directory;

    public FolderControl() {}

    public FolderControl(Directory directory) {
        this.directory = directory;
    }

    public FolderControl(Directory directory, FolderView folderView) {
        this.directory = directory;
        this.folderView = folderView;
    }

    public void updateDirectory(List<String> entry) {
        directory.setDirectory(entry);
    }

    public FolderView getFolderView() {
        return folderView;
    }

    public void setFolderView(FolderView folderView) {
        this.folderView = folderView;

        // set listeners for folderview
        this.folderView.getFolderTree().addTreeSelectionListener(getTreeSelectionListener());
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    private TreeSelectionListener getTreeSelectionListener() {
        return (TreeSelectionEvent event) -> {
            TreePath path = event.getPath();
            System.out.println(path.toString());
        };
    }
}
