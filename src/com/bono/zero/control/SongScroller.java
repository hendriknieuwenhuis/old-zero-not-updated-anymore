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
    private ScrollView scrollView;

    private Player player;

    private Timer timer;



    public SongScroller(Player player) {
        this.player = player;
        //scrollView.getSlider().addMouseListener(this.getMouseListener());
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Deprecated
    public JPanel getView() {
        return scrollView.getPanel();
    }



    /*
    The 'time' property of the server status is always
    going to be set when a change in the server status
    occurs.
     */
    public ChangeListener getCurrentTimeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                System.out.println("getCurrentTime() called;");

                ServerProperty serverProperty = (ServerProperty) e.getSource();
                String s = (String) serverProperty.getValue();

                if (state.equals("play"))
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

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scrollView.getSlider().setMinimum(0);
                scrollView.getSlider().setMaximum(Integer.parseInt(time[1]));
                scrollView.getSlider().setValue(Integer.parseInt(time[0]));

                scrollView.getTime().setText(time(time[0]));

                scrollView.getPlayTime().setText(time(time[1]));
            }
        });



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
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.getSlider().setMinimum(0);
                                scrollView.getSlider().setMaximum(0);
                                scrollView.getSlider().setValue(0);
                                scrollView.getTime().setText(time("000000"));
                                scrollView.getPlayTime().setText(time("000000"));
                            }
                        });

                        break;

                    case PlayerProperties.PAUSE:

                        /*
                        stop the timer.
                        leave the values as they are.
                         */
                        if (timer != null) timer.setRunning(false);
                        break;

                    case PlayerProperties.PLAY:

                        /*
                        needs nothing here every time the
                        time property of the server state is
                        written a new timer is started.
                         */
                        break;
                    default:
                        break;

                }
            }
        };
    }

    /*
    Changes the 000000 second amount from the server
    to a 00:00:00 notation.
     */
    private String time(String seconds) {
        int time = Integer.parseInt(seconds);
        int hour = time/3600;
        int rem = time%3600;
        int min = rem/60;
        int sec = rem%60;
        return ((hour < 10 ? "0" : "") + hour) + ":" + ((min < 10 ? "0" : "") + min) + ":" + ((sec < 10 ? "0" : "") + sec);
    }

    // mouse listener gives player seekcur command
    // on mouse release.
    /*
    TODO ADD SOMETHING TO SHOW THE TIME WHEN SCROLLING!!!!!
     */
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

    /*
    Inner class 'Timer' adds every second a second to the
    current play time of the song and moves the scroller
    as an indication of play and played time.

    When the song is not running the 'running' boolean
    can be set to false so the 'Timer' ends its run
    method. This also happens when the play time exceeds.
     */
    private class Timer implements  Runnable {

        private boolean running = true;

        @Override
        public void run() {
            System.out.println("NEW timer object created!");
            while (isRunning()) {
                if (scrollView.getSlider().getValue() < scrollView.getSlider().getMaximum()) {

                    long currTime = System.currentTimeMillis();

                    int currValue = scrollView.getSlider().getValue();

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            // add one to the value.
                            scrollView.getSlider().setValue(currValue + 1);


                            // change time.
                            scrollView.getTime().setText(time(Integer.toString(currValue)));
                        }
                    });


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
