package com.bono.zero.control;

import com.bono.zero.api.Player;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.models.Property;
import com.bono.zero.api.ExecuteCommand;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.laf.BonoIconFactory;
import com.bono.zero.view.PlayerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class PlayerControl  {

    /*
    the panel containing the graphical
    representation of this class.
     */
    private PlayerView playerView;

    /*
    Player controller sending the various
    command to control the player of the
    server to the server.
     */
    @Deprecated
    private Player player;

    /*
    String state gets set by the <code>StateListener</code>
    class when this is set as listener of the 'state'
    server property.

    TODO may need a lock on this !!!!!!!!!!!!!!!!!!!!!!!!!

     */
    private String state;

    private PlayerExecutor executor;

    // listener for the buttons in gui.
    @Deprecated
    private ActionListener buttonsListener = new ButtonsListener();

    // change listener for the 'state' property
    // of the server status.
    @Deprecated
    private PropertyChangeListener stateListener = new StateListener();

    private PropertyChangeListener statePropertyListener;

    private ServerStatus serverStatus;

    @Deprecated
    private Property stateProperty;

    public PlayerControl() {}

    @Deprecated
    public PlayerControl(Player player) {
        this.player = player;

    }

    public PlayerControl(PlayerExecutor executor, ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
        this.executor = executor;
    }

    @Deprecated
    public void setPlayer(Player player) {
        this.player = player;
    }


    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    @Deprecated
    public ActionListener getButtonsListener() {
        return buttonsListener;
    }


    public ActionListener getPreviousListener() {
        return actionEvent -> {

            executor.addCommand(new ExecuteCommand(PlayerProperties.PREVIOUS));
            /*
            Runnable runnable = () -> {
                /*
                try {
                    player.previous();
                } catch (IOException io) {
                    io.printStackTrace();
                }*/

            };/*
            new Thread(runnable).start();
        };*/

    }

    public ActionListener getStopListener() {
        return actionEvent -> {

            executor.addCommand(new ExecuteCommand(PlayerProperties.STOP));

            /*
            Runnable runnable = () -> {
                try {
                    player.stop();
                } catch (IOException io) {
                    io.printStackTrace();
                }

            };
            new Thread(runnable).start();
            */
        };

    }

    public ActionListener getPlayListener() {
        return actionEvent -> {
            /*
            Runnable runnable = () -> {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();
            */
            // when state is 'play' the
            // 'pause {1}' command is given,
            // to pause the player.
            if (((String)serverStatus.getStatus().getState().getValue()).equals(PlayerProperties.PLAY)) {

                executor.addCommand(new ExecuteCommand(PlayerProperties.PAUSE, "1"));

            } else if (((String)serverStatus.getStatus().getState().getValue()).equals(PlayerProperties.PAUSE)) {

                executor.addCommand(new ExecuteCommand(PlayerProperties.PAUSE, "0"));

            } else if (((String)serverStatus.getStatus().getState().getValue()).equals(PlayerProperties.STOP)) {

                executor.addCommand(new ExecuteCommand(PlayerProperties.PLAY));
            }
        };

    }
    @Deprecated
    public ActionListener getNextListener() {
        return actionEvent -> {
            Runnable runnable = () -> {
                try {
                    player.next();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            };
            new Thread(runnable).start();
        };

    }

    @Deprecated
    private class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton model = (JButton) e.getSource();
            String action = model.getActionCommand();

            switch (action) {
                case PlayerView.PREVIOUS:
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_PREVIOUS");
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
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_STOP");
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
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_PLAY");
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
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_NEXT");
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
    }


    @Deprecated
    public JPanel getView() {
        return playerView.getPanel();
    }

    @Deprecated
    public PropertyChangeListener getStateListener() {
        return stateListener;
    }

    /*
    SYNC OP STATEPROPERTY !!!!!!!
     */
    public PropertyChangeListener getStatePropertyListener(Property stateProperty) {
        if (stateProperty == null) {
            this.stateProperty = stateProperty;
        }
        if (statePropertyListener == null) {
            statePropertyListener = (evt) -> {

                if (((String)stateProperty.getValue()).equals(PlayerProperties.PLAY)) {

                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            //System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());

                            playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPauseButtonIcon());
                        }
                    }); // END EDT

                } else {
                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            //System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());

                            playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPlayButtonIcon());
                        }
                    }); // END EDT
                }
            };
        }
        return statePropertyListener;
    }

    // Listens to the state property of the server status.
    // when the state is changed the play icon changes:
    //
    //     to pause when state is 'play'.
    //
    //     to play when state is 'pause'.
    //
    private class StateListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String newValue = (String) evt.getNewValue();
            String oldValue = (String) evt.getOldValue();

            if (!newValue.equals(oldValue)) {
                state = newValue;
                // EDT
                if (state.equals(PlayerProperties.PLAY)) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            //System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());

                            playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPauseButtonIcon());
                        }
                    }); // END EDT

                } else {
                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            //System.out.printf("%s, this is the event dispatch thread: %s\n", getClass().getName(), SwingUtilities.isEventDispatchThread());

                            playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPlayButtonIcon());
                        }
                    }); // END EDT

                }
            }
        }

    }
}
