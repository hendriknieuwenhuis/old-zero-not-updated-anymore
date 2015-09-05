package com.bono.zero.view;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Created by hendriknieuwenhuis on 05/09/15.
 */
public class PlaylistView extends JPanel {

    private JScrollPane scrollPane;
    private JTable table;

    private TableModel tableModel;

    public PlaylistView(TableModel tableModel) {
        super();
        this.tableModel = tableModel;
        init();
    }

    private void init() {
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JTable getTable() {
        return table;
    }
}
