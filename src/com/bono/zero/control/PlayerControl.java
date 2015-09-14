package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.events.PropertyEvent;
import com.bono.zero.api.events.PropertyListener;
import com.bono.zero.api.models.Control;
import com.bono.zero.api.models.Property;
import com.bono.zero.api.models.Song;
import com.bono.zero.api.models.commands.ServerCommand;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.laf.BonoIconFactory;
import com.bono.zero.view.PlayerView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class PlayerControl extends Control {

    /*
    the panel containing the graphical
    representation of this class.
     */
    private PlayerView playerView;

    private PropertyListener statePropertyListener;

    private ServerStatus serverStatus;



    public PlayerControl() {}

    public PlayerControl(String host, int port, ExecutorService executorService, ServerStatus serverStatus) {
        super(host, port, executorService);
        this.serverStatus = serverStatus;

    }

    @Override
    public void init() {
        this.playerView.getButton("previous").addActionListener(getPreviousListener());
        this.playerView.getButton("stop").addActionListener(getStopListener());
        this.playerView.getButton("play").addActionListener(getPlayListener());
        this.playerView.getButton("next").addActionListener(getNextListener());
    }

    public void setPlayerView(PlayerView playerView) {
        this.playerView = playerView;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }


    public ActionListener getPreviousListener() {
        return actionEvent -> {
            executeCommand(new ServerCommand(PlayerProperties.PREVIOUS));
        };
    }

    public ActionListener getStopListener() {
        return actionEvent -> {
            executeCommand(new ServerCommand(PlayerProperties.STOP));
        };
    }

    public ActionListener getPlayListener() {
        return actionEvent -> {
            // when state is 'play' the
            // 'pause {1}' command is given,
            // to pause the player.
            if ((serverStatus.getStatus().getState()).equals(PlayerProperties.PLAY)) {
                executeCommand(new ServerCommand(PlayerProperties.PAUSE, "1"));

            } else if ((serverStatus.getStatus().getState()).equals(PlayerProperties.PAUSE)) {
                executeCommand(new ServerCommand(PlayerProperties.PAUSE, "0"));

            } else if ((serverStatus.getStatus().getState()).equals(PlayerProperties.STOP)) {
                executeCommand(new ServerCommand(PlayerProperties.PLAY));
            }
        };

    }

    public ActionListener getNextListener() {
        return ActionEvent -> {
            executeCommand(new ServerCommand(PlayerProperties.NEXT));
        };
    }

    private void executeCommand(ServerCommand serverCommand) {
        Runnable runnable = () -> {
            Endpoint endpoint = new Endpoint(host, port);
            String reply = null;
            try {
                reply = endpoint.sendCommand(serverCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // print.
            System.out.println(reply);
            //
        };
        executorService.execute(runnable);
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
                            playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPauseButtonIcon());
                        }
                    }); // END EDT

                } else {
                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            playerView.getButton(PlayerProperties.PLAY).setIcon(BonoIconFactory.getPlayButtonIcon());
                        }
                    }); // END EDT
                }
            };
        }
        return statePropertyListener;
    }


}
