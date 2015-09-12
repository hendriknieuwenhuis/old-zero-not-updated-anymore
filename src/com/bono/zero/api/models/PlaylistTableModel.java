package com.bono.zero.api.models;

import com.bono.zero.api.Playlist;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

/**
 * Created by hendriknieuwenhuis on 12/09/15.
 */
public class PlaylistTableModel extends DefaultTableModel {

    public PlaylistTableModel() {
        super();
    }

    public PlaylistTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public PlaylistTableModel(Vector columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public PlaylistTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public PlaylistTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    public PlaylistTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public void setPlaylistTableModel(Playlist playlist) {
        setRowCount(playlist.getSize());
        for (int i = 0; i < playlist.getList().size(); i++) {
            setValueAt(playlist.getList().get(i).getPos(), i, 0);
            setValueAt(playlist.getList().get(i).getTitle(), i, 1);
            setValueAt(playlist.getList().get(i).getArtist(), i, 2);
        }
    }
}
