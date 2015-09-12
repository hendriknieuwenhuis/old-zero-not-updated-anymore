package com.bono.zero.test;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.properties.StatusProperties;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.api.ExecuteCommand;
import com.bono.zero.control.Idle;
import com.bono.zero.control.PlayerExecutor;
import com.bono.zero.laf.BonoIconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 14/08/15.
 */
public class ApiTestPlayer {

    private static final String HOST = "192.168.2.2";
    private static final int PORT = 6600;

    private PlayerExecutor executor;

    private Idle idle;

    private String state;

    private ServerStatus serverStatus;

    public ApiTestPlayer() {
        init();
    }

    private void init() {

        Runnable runnable = () -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.addWindowListener(new FrameListener());
            PlayerPanel panel = new PlayerPanel();

            panel.getPause().addActionListener(getPauseListener());
            panel.getPrevious().addActionListener(getPreviousListener());
            panel.getPlay().addActionListener(getPlayListener());
            panel.getNext().addActionListener(getNextListener());
            panel.getStop().addActionListener(getStopListener());

            frame.getContentPane().add(panel);

            frame.pack();
            frame.setVisible(true);
        };

        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private PropertyChangeListener getStateListener() {
        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                state = (String) evt.getNewValue();
                System.out.println(state);
            }
        };
    }

    private class PlayerPanel extends JPanel {

        private JButton previous = new JButton(BonoIconFactory.getPreviousButtonIcon());
        private JButton stop = new JButton(BonoIconFactory.getStopButtonIcon());
        private JButton play = new JButton(BonoIconFactory.getPlayButtonIcon());
        private JButton pause = new JButton(BonoIconFactory.getPauseButtonIcon());
        private JButton next = new JButton(BonoIconFactory.getNextButtonIcon());

        public PlayerPanel() {
            super();
            setLayout(new FlowLayout());
            previous.setActionCommand(PlayerProperties.PREVIOUS);
            add(previous);
            stop.setActionCommand(PlayerProperties.STOP);
            add(stop);
            play.setActionCommand(PlayerProperties.PLAY);
            add(play);
            pause.setActionCommand(PlayerProperties.PAUSE);
            add(pause);
            next.setActionCommand(PlayerProperties.NEXT);
            add(next);
        }

        public JButton getPrevious() {
            return previous;
        }

        public JButton getStop() {
            return stop;
        }

        public JButton getPlay() {
            return play;
        }

        public JButton getPause() {
            return pause;
        }

        public JButton getNext() {
            return next;
        }
    }

    private class FrameListener extends WindowAdapter {

        @Override
        public void windowDeactivated(WindowEvent e) {
            super.windowDeactivated(e);
            idle.stopRunning();
            executor.endExecutor();
        }

        /*
        Has to init the models and monitor threads.
         */
        @Override
        public void windowActivated(WindowEvent e) {
            super.windowActivated(e);

            // init status.
            serverStatus = new ServerStatus();
            serverStatus.getStatus().getStateProperty().setPropertyChangeListener(getStateListener());

            idle = new Idle(HOST, PORT, serverStatus, null);
            Thread threadIdle = new Thread(idle);
            threadIdle.start();

            Endpoint endpoint = new Endpoint(HOST, PORT);
            List<String> statusList = null;
            try {
                statusList = endpoint.sendRequest(new ExecuteCommand(StatusProperties.STATUS));
            } catch (IOException io) {
                io.printStackTrace();
            }

            // set status values.
            serverStatus.setStatus(statusList);


        /*
        Start the executor thread.
         */
            executor = new PlayerExecutor(new Endpoint(HOST, PORT));
            Thread thread = new Thread(executor);
            thread.start();

        }
    }



    public ActionListener getPlayListener() {
        return actionEvent -> {
            executor.addCommand(new ExecuteCommand(PlayerProperties.PLAY));
        };
    }

    public ActionListener getPauseListener() {
        return actionEvent -> {
            System.out.println("Pause!");
            // when state is 'play' the
            // 'pause {1}' command is given,
            // to pause the player.
            if (state.equals(PlayerProperties.PLAY)) {
                executor.addCommand(new ExecuteCommand(PlayerProperties.PAUSE, "1"));
                // when state is 'pause' the
                // 'pause {0}' command is given,
                // to resume the player.
            } else if (state.equals(PlayerProperties.PAUSE)) {
                executor.addCommand(new ExecuteCommand(PlayerProperties.PAUSE, "0"));
            }
        };
    }

    public ActionListener getStopListener() {
        return actionEvent -> {
            executor.addCommand(new ExecuteCommand(PlayerProperties.STOP));
        };
    }

    public ActionListener getPreviousListener() {
        return actionEvent -> {
            executor.addCommand(new ExecuteCommand(PlayerProperties.PREVIOUS));
        };
    }

    public ActionListener getNextListener() {
        return actionEvent -> {
            executor.addCommand(new ExecuteCommand(PlayerProperties.NEXT));
        };
    }

    public static void main(String[] args) {
        new ApiTestPlayer();
    }


}
