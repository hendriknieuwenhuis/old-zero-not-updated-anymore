package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Player;
import com.bono.zero.api.models.ServerProperty;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.view.ScrollView;
import com.bono.zero.view.SongView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

/**
 * Created by hendriknieuwenhuis on 04/08/15.
 *
 * A callable future inner class for optaining server state change.
 *
 */
public class SongScroller {

    // the scrollview depicting the scroller.
    private ScrollView scrollView = new ScrollView();

    private String songId = null;

    private Player player;

    private Timer timer;



    public SongScroller(Player player) {
        this.player = player;
        scrollView.getSlider().addMouseListener(this.getMouseListener());

    }



    public JPanel getView() {
        return scrollView.getPanel();
    }

    /*
    ----> !!!! Find solution for non update in-case of a
    next song having the same time values as last set. <----
     */
    // listens to server property 'time' holding the played time of
    // the current playing song and total time.
    public ChangeListener getCurrentTimeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                System.out.println("getCurrentTime() called;");

                ServerProperty serverProperty = (ServerProperty) e.getSource();
                String s = (String) serverProperty.getValue();

                initTimer(s);

            }
        };
    }

    private void initTimer(String value) {

        // end the running timer thread.
        if (timer != null) {
            timer.setRunning(false);
        }

        String[] time = value.split(":");

        scrollView.getSlider().setMinimum(0);
        scrollView.getSlider().setMaximum(Integer.parseInt(time[1]));
        scrollView.getSlider().setValue(Integer.parseInt(time[0]));

        scrollView.getTime().setText(time(time[0]));

        scrollView.getPlayTime().setText(time(time[1]));

        // create new timer runnable. Old one
        // should be gone!
        timer = new Timer();

        Thread thread = new Thread(timer);
        thread.start();

    }

    /*
    Listener for the state of player. 'Play', 'Pause' or 'Stop'.
     */
    public ChangeListener getStateListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ServerProperty serverProperty = (ServerProperty) e.getSource();
                String state = (String) serverProperty.getValue();

                switch (state) {
                    case PlayerProperties.STOP:

                        // set everything '0'.
                        scrollView.getSlider().setMinimum(0);
                        scrollView.getSlider().setMaximum(0);
                        scrollView.getSlider().setValue(0);
                        scrollView.getTime().setText(time("000000"));
                        scrollView.getPlayTime().setText(time("000000"));
                        break;

                    case PlayerProperties.PAUSE:

                        // stop the timer.
                        if (timer != null) {
                            timer.setRunning(false);
                        }
                        break;

                    case PlayerProperties.PLAY:

                        // init the timer.
                        //initTimer();
                        break;
                    default:
                        break;

                }
            }
        };
    }


    private String time(String seconds) {
        int time = Integer.parseInt(seconds);
        int hour = time/3600;
        int rem = time%3600;
        int min = rem/60;
        int sec = rem%60;
        return ((hour < 10 ? "0" : "") + hour) + ":" + ((min < 10 ? "0" : "") + min) + ":" + ((sec < 10 ? "0" : "") + sec);
    }

    // notify the scroller view that the song is changed,
    // to reset the counter.
    public ChangeListener getCurrentSongListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ServerProperty serverProperty = (ServerProperty) e.getSource();
                songId = (String) serverProperty.getValue();
            }
        };
    }



    // mouse listener gives player seekcur command
    // on mouse release.
    private MouseListener getMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                JSlider slider = (JSlider) e.getSource();

                if (!slider.getValueIsAdjusting()) {
                    try {
                        player.seekcur(Integer.toString(slider.getValue()));
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            }
        };

    }

    private class Timer implements  Runnable {

        private boolean running = true;

        @Override
        public void run() {

            while (isRunning()) {
                if (scrollView.getSlider().getValue() < scrollView.getSlider().getMaximum()) {

                    long currTime = System.currentTimeMillis();

                    int currValue = scrollView.getSlider().getValue();

                    // add one to the value.
                    scrollView.getSlider().setValue(currValue + 1);
                    scrollView.getSlider().repaint();

                    // change time.
                    scrollView.getTime().setText(time(Integer.toString(currValue)));

                    // calculate sleep time.
                    long sleep = 1000 - (System.currentTimeMillis() - currTime);

                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } else {
                    setRunning(false);
                }
            }
        }

        private boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
    }
}
