package com.bono.zero.view;

import javax.swing.*;

/**
 * Created by hendriknieuwenhuis on 06/09/15.
 */
public class FolderView extends JPanel {

    private JScrollPane scrollPane;
    private JTree tree;

    public FolderView() {
        super();
        init();
    }

    private void init() {
        tree = new JTree();


        add(tree);
    }

    public JTree getFolderTree() {
        return tree;

    }
}
