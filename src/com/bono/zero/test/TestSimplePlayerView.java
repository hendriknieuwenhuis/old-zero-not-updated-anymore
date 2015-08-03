package com.bono.zero.test;

import com.bono.zero.view.PlayerView;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hendriknieuwenhuis on 03/08/15.
 */
public class TestSimplePlayerView {

    public static void main(String[] args) {

        PlayerView playerView = new PlayerView();
        playerView.getButton(PlayerView.PREVIOUS).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(PlayerView.PREVIOUS);
            }
        });
        playerView.getButton(PlayerView.STOP).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(PlayerView.STOP);
            }
        });
        playerView.getButton(PlayerView.PLAY).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(PlayerView.PLAY);
            }
        });
        playerView.getButton(PlayerView.NEXT).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(PlayerView.NEXT);
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(playerView.getPanel());
        frame.setSize(300, 150);
        frame.setVisible(true);

    }
}
