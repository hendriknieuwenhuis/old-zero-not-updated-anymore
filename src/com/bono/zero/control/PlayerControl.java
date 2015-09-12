package com.bono.zero.control;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Endpoint;
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
import java.util.concurrent.ExecutorService;

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
    String state gets set by the <code>StateListener</code>
    class when this is set as listener of the 'state'
    server property.

    TODO may need a lock on this !!!!!!!!!!!!!!!!!!!!!!!!!

     */
    private String state;

    private PlayerExecutor executor;

    private ExecutorService executorService;

    private String host;

    private int port;

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

    public PlayerControl(String host, int port, ExecutorService executorService, ServerStatus serverStatus) {
        this.host = host;
        this.port = port;
        this.executorService = executorService;
        this.serverStatus = serverStatus;
    }



    public PlayerControl(PlayerExecutor executor, ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
        this.executor = executor;
    }



    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
        this.playerView.getButton("previous").addActionListener(getPreviousListener());
        this.playerView.getButton("stop").addActionListener(getStopListener());
        this.playerView.getButton("play").addActionListener(getPlayListener());
        this.playerView.getButton("next").addActionListener(getNextListener());
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
            Runnable runnable = () -> {
                String reply = null;
                ExecuteCommand executeCommand = new ExecuteCommand(PlayerProperties.PREVIOUS);
                executeCommand.addEndpoint(new Endpoint(host, port));
                try {
                    reply = executeCommand.execute();
                } catch (IOException e) {
                    e.printStackTrace();;
                }
            };
            executorService.execute(runnable);
        };
    }

    public ActionListener getStopListener() {
        return actionEvent -> {
            Runnable runnable = () -> {
                String reply = null;
                ExecuteCommand executeCommand = new ExecuteCommand(PlayerProperties.STOP);
                executeCommand.addEndpoint(new Endpoint(host, port));
                try {
                    reply = executeCommand.execute();
                } catch (IOException e) {
                    e.printStackTrace();;
                }
            };
            executorService.execute(runnable);
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
            if (((String)serverStatus.getStatus().getState()).equals(PlayerProperties.PLAY)) {
                Runnable runnable = () -> {
                    String reply = null;
                    ExecuteCommand executeCommand = new ExecuteCommand(PlayerProperties.PAUSE, "1");
                    executeCommand.addEndpoint(new Endpoint(host, port));
                    try {
                        reply = executeCommand.execute();
                    } catch (IOException e) {
                        e.printStackTrace();;
                    }
                };
                executorService.execute(runnable);
            } else if (((String)serverStatus.getStatus().getState()).equals(PlayerProperties.PAUSE)) {
                Runnable runnable = () -> {
                    String reply = null;
                    ExecuteCommand executeCommand = new ExecuteCommand(PlayerProperties.PAUSE, "0");
                    executeCommand.addEndpoint(new Endpoint(host, port));
                    try {
                        reply = executeCommand.execute();
                    } catch (IOException e) {
                        e.printStackTrace();;
                    }
                };
                executorService.execute(runnable);
            } else if (((String)serverStatus.getStatus().getState()).equals(PlayerProperties.STOP)) {
                Runnable runnable = () -> {
                    String reply = null;
                    ExecuteCommand executeCommand = new ExecuteCommand(PlayerProperties.PLAY);
                    executeCommand.addEndpoint(new Endpoint(host, port));
                    try {
                        reply = executeCommand.execute();
                    } catch (IOException e) {
                        e.printStackTrace();;
                    }
                };
                executorService.execute(runnable);
            }
        };

    }

    public ActionListener getNextListener() {
        return ActionEvent -> {
            Runnable runnable = () -> {
                String reply = null;
                ExecuteCommand executeCommand = new ExecuteCommand(PlayerProperties.NEXT);
                executeCommand.addEndpoint(new Endpoint(host, port));
                try {
                    reply = executeCommand.execute();
                } catch (IOException e) {
                    e.printStackTrace();;
                }
            };
            executorService.execute(runnable);
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
                        /*
                        try {
                            //player.previous();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }*/
                    };
                    new Thread(previous).start();
                    break;
                case PlayerView.STOP:
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_STOP");
                    Runnable stop = () -> {
                        /*
                        try {
                            //player.stop();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }*/
                    };
                    new Thread(stop).start();
                    break;
                case PlayerView.PLAY:
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_PLAY");
                    Runnable play = () -> {
                        /*
                        try {
                            // when state is 'play' the
                            // 'pause {1}' command is given,
                            // to pause the player.
                            if (state.equals(PlayerProperties.PLAY)) {
                                //player.pause("1");

                                // when state is 'pause' the
                                // 'pause {0}' command is given,
                                // to resume the player.
                            } else if (state.equals(PlayerProperties.PAUSE)) {
                                //player.pause("0");

                                // when state is 'stop' the
                                // 'play' command is given,
                                // to play the player.
                                // the player starts playing
                                // from the beginning of the
                                // first song of the playlist.
                            } else if (state.equals(PlayerProperties.STOP)) {
                                //player.play();
                            }
                        } catch (IOException io) {
                            io.printStackTrace();
                        }*/
                    };
                    new Thread(play).start();
                    break;
                case PlayerView.NEXT:
                    //System.out.printf("%s, %s\n", getClass().getName(), "PLAYERVIEW_NEXT");
                    Runnable next = () -> {
                        /*
                        try {
                            //player.next();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }*/
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
