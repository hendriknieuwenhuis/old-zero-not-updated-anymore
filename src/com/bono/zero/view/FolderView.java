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
        scrollPane = new JScrollPane(tree);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    public JTree getFolderTree() {
        return tree;

    }
}
