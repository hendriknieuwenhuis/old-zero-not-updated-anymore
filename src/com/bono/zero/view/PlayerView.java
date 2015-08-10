package com.bono.zero.view;

import com.bono.zero.laf.BonoIconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class PlayerView extends JPanel {

    public static final String PREVIOUS = "previous";
    public static final String STOP = "stop";
    public static final String PLAY = "play";
    public static final String NEXT = "next";

    private JButton previous;
    private JButton stop;
    private JButton play;
    private JButton next;

    // map of the buttons for later access.
    private HashMap<String, JButton> buttonMap = new HashMap<>();

    public PlayerView() {
        super();
        init();
    }

    private void init() {
        System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());
        setLayout(new FlowLayout());
        newButton(BonoIconFactory.getPreviousButtonIcon(), PREVIOUS);
        newButton(BonoIconFactory.getStopButtonIcon(), STOP);
        newButton(BonoIconFactory.getPlayButtonIcon(), PLAY);
        newButton(BonoIconFactory.getNextButtonIcon(), NEXT);
    }

    private void newButton(Icon icon, String action) {
        JButton button = new JButton(icon);
        button.setActionCommand(action);
        buttonMap.put(action, button);
        add(button);
    }

    @Deprecated
    public JPanel getPanel() {
        return this;
    }

    public JButton getButton(String key) {
        return buttonMap.get(key);
    }

    public void addActionListener(String key, ActionListener actionListener) {
        buttonMap.get(key).addActionListener(actionListener);
    }
}
