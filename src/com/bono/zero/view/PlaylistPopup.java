package com.bono.zero.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hennihardliver on 29/05/14.
 */
public class PlaylistPopup extends JPopupMenu{

    private JMenuItem remove;
    private JMenuItem clearAll;



    public PlaylistPopup() {
        super();

        remove = new JMenuItem("remove");
        remove.setActionCommand("remove");
        add(remove);
        clearAll = new JMenuItem("clear all");
        clearAll.setActionCommand("clearAll");
        add(clearAll);
    }


    public void addAllMenuItemListener(ActionListener actionListener) {
        remove.addActionListener(actionListener);
        clearAll.addActionListener(actionListener);
    }
}
