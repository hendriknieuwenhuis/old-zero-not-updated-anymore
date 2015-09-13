package com.bono.zero.control;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.events.PropertyEvent;
import com.bono.zero.api.events.PropertyListener;
import com.bono.zero.api.models.Property;
import com.bono.zero.api.ExecuteCommand;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.api.properties.PlaylistProperties;
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

    private String state;

    private PlayerExecutor executor;

    private ExecutorService executorService;

    private String host;

    private int port;


    private PropertyListener statePropertyListener;

    private ServerStatus serverStatus;



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


    public ActionListener getPreviousListener() {
        return actionEvent -> {
            executeCommand(PlayerProperties.PREVIOUS, null);
        };
    }

    public ActionListener getStopListener() {
        return actionEvent -> {
            executeCommand(PlayerProperties.STOP, null);
        };
    }

    public ActionListener getPlayListener() {
        return actionEvent -> {
            // when state is 'play' the
            // 'pause {1}' command is given,
            // to pause the player.
            if (((String)serverStatus.getStatus().getState()).equals(PlayerProperties.PLAY)) {
                executeCommand(PlayerProperties.PAUSE, "1");

            } else if (((String)serverStatus.getStatus().getState()).equals(PlayerProperties.PAUSE)) {
                executeCommand(PlayerProperties.PAUSE, "0");

            } else if (((String)serverStatus.getStatus().getState()).equals(PlayerProperties.STOP)) {
                executeCommand(PlayerProperties.PLAY, null);
            }
        };

    }

    private void executeCommand(String command, String arg) {
        Runnable runnable = () -> {
            String reply = null;
            try {
                reply = new ExecuteCommand(new Endpoint(host, port), command, arg).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executorService.execute(runnable);
    }

    public ActionListener getNextListener() {
        return ActionEvent -> {
            Runnable runnable = () -> {
                String reply = null;
                ExecuteCommand executeCommand = new ExecuteCommand(new Endpoint(host, port), PlayerProperties.NEXT);
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
    public JPanel getView() {
        return playerView.getPanel();
    }


    /*
    SYNC OP STATEPROPERTY !!!!!!!
     */
    public PropertyListener getStatePropertyListener() {

        if (statePropertyListener == null) {
            statePropertyListener = (PropertyEvent evt) -> {
                Property property = (Property) evt.getSource();

                if (property.getValue().equals(PlayerProperties.PLAY)) {

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


}
