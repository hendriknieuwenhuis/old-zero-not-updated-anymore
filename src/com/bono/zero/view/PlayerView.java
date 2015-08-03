package com.bono.zero.view;

import com.bono.zero.laf.BonoIconFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class PlayerView {

    public static final String PREVIOUS = "previous";
    public static final String STOP = "stop";
    public static final String PLAY = "play";
    public static final String NEXT = "next";

    private JButton previous;
    private JButton stop;
    private JButton play;
    private JButton next;

    // panel holding the buttons
    private JPanel panel = new JPanel();

    // map of the buttons for later access.
    private HashMap<String, JButton> buttonMap = new HashMap<>();

    public PlayerView() {
        init();
    }

    private void init() {
        panel.setLayout(new FlowLayout());
        newButton(BonoIconFactory.getPreviousButtonIcon(), PREVIOUS);
        newButton(BonoIconFactory.getStopButtonIcon(), STOP);
        newButton(BonoIconFactory.getPlayButtonIcon(), PLAY);
        newButton(BonoIconFactory.getNextButtonIcon(), NEXT);
    }

    private void newButton(Icon icon, String action) {
        JButton button = new JButton(icon);
        button.setActionCommand(action);
        buttonMap.put(action, button);
        panel.add(button);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getButton(String key) {
        return buttonMap.get(key);
    }
}
