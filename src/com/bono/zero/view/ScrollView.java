package com.bono.zero.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hendriknieuwenhuis on 04/08/15.
 */
public class ScrollView {

    private JPanel panel;

    private JSlider slider;
    private JLabel time;
    private JLabel playTime;

    public ScrollView() {
        init();
    }

    private void init() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        time = new JLabel();
        slider = new JSlider();
        playTime = new JLabel();
        panel.add(time);
        panel.add(slider);
        panel.add(playTime);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JSlider getSlider() {
        return slider;
    }

    public JLabel getTime() {
        return time;
    }

    public JLabel getPlayTime() {
        return playTime;
    }
}
