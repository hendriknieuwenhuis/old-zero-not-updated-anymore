package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Player;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.view.PlayerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class PlayerControl implements ActionListener {

    private PlayerView playerView = new PlayerView();

    private Player player;

    private Endpoint endpointPlayer;

    public PlayerControl(String host, int port) {
        endpointPlayer = new Endpoint(host, port);
        player = new Player(endpointPlayer);
        init();
    }

    private void init() {
        playerView.getButton(PlayerView.PREVIOUS).addActionListener(this);
        playerView.getButton(PlayerView.STOP).addActionListener(this);
        playerView.getButton(PlayerView.PLAY).addActionListener(this);
        playerView.getButton(PlayerView.NEXT).addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton model = (JButton) e.getSource();
        String action = model.getActionCommand();

        switch (action) {
            case PlayerView.PREVIOUS:
                Runnable previous = () -> {
                    try {
                        player.previous();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                };
                new Thread(previous).start();
                break;
            case PlayerView.STOP:
                Runnable stop = () -> {
                    try {
                        player.stop();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                };
                new Thread(stop).start();
                break;
            case PlayerProperties.PLAY:
                Runnable play = () -> {
                    try {
                        player.play();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                };
                new Thread(play).start();
                break;
            case PlayerView.NEXT:
                Runnable next = () -> {
                    try {
                        player.next();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                };
                new Thread(next).start();
                break;
            default:
                break;
        }
    }

    public JPanel getPlayerPanel() {
        return playerView.getPanel();
    }
}
