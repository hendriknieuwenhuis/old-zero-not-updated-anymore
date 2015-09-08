package com.bono.zero.view;

import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Created by hendriknieuwenhuis on 05/09/15.
 */
public class PlaylistView extends JPanel {

    //private JScrollPane scrollPane;
    private JTable table;



    public PlaylistView() {
        super();

        init();
    }

    private void init() {
        table = new JTable();
        add(table);
    }


    public void setTableModel(TableModel tableModel) {
        table.setModel(tableModel);
    }

    @Deprecated
    public JTable getTable() {
        return table;
    }
}
