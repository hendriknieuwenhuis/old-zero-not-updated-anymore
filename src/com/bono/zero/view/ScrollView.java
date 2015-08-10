package com.bono.zero.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hendriknieuwenhuis on 04/08/15.
 */
public class ScrollView extends JPanel{

    private JSlider slider;
    private JLabel time;
    private JLabel playTime;

    public ScrollView() {
        super();
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        time = new JLabel();
        slider = new JSlider();
        playTime = new JLabel();
        add(time);
        add(slider);
        add(playTime);
    }

    public JPanel getPanel() {
        return this;
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
