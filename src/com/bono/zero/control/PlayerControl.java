package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Player;
import com.bono.zero.api.models.ServerProperty;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.laf.BonoIconFactory;
import com.bono.zero.view.PlayerView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class PlayerControl implements ActionListener {

    private PlayerView playerView = new PlayerView();

    private Player player;

    /*
    String state gets set by the <code>StateListener</code>
    class when this is set as listener of the 'state'
    server property.
     */
    private String state;



    private Endpoint endpointPlayer;

    public PlayerControl(Player player) {
        this.player = player;
        init();
    }

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
                System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_PREVIOUS");
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
                System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_STOP");
                Runnable stop = () -> {
                    try {
                        player.stop();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                };
                new Thread(stop).start();
                break;
            case PlayerView.PLAY:
                System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_PLAY");
                Runnable play = () -> {
                    try {
                        // when state is 'play' the
                        // 'pause {1}' command is given,
                        // to pause the player.
                        if (state.equals(PlayerProperties.PLAY)) {
                            player.pause("1");

                        // when state is 'pause' the
                        // 'pause {0}' command is given,
                        // to resume the player.
                        } else if (state.equals(PlayerProperties.PAUSE)) {
                            player.pause("0");

                        // when state is 'stop' the
                        // 'play' command is given,
                        // to play the player.
                        // the player starts playing
                        // from the beginning of the
                        // first song of the playlist.
                        } else if (state.equals(PlayerProperties.STOP)) {
                            player.play();
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                };
                new Thread(play).start();
                break;
            case PlayerView.NEXT:
                System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_NEXT");
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

    public JPanel getView() {
        return playerView.getPanel();
    }

    public ChangeListener getStateListener() {
        return new StateListener();
    }

    // Listens to the state property of the server status.
    // when the state is changed the play icon changes:
    //
    //     to pause when state is 'play'.
    //
    //     to play when state is 'pause'.
    //
    private class StateListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            ServerProperty serverProperty = (ServerProperty) e.getSource();


            state = serverProperty.getValue().toString();
            System.out.printf("%s: %s\n", getClass().getName(), state);

            if (state.equals(PlayerProperties.PLAY)) {
                playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPauseButtonIcon());
            } else {
                playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPlayButtonIcon());
            }
        }
    }
}
