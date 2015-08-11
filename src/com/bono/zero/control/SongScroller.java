package com.bono.zero.control;


import com.bono.zero.api.Player;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.view.ScrollView;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;



/**
 * Created by hendriknieuwenhuis on 04/08/15.
 *
 *
 *
 * this is basically solved
 * TODO When song is stopped property 'time' is not written so
 * TODO this controller should then only respond to the 'state'
 * TODO instead of both 'time' and 'state'.
 *
 * LOOK AT THIS!!!!
 * TODO latency in setting time when 'next' or 'previous' is
 * TODO hit must be removed.
 * TODO Possible solution complete different approach with
 * TODO the listeners.
 * TODO Could also be in the synchronization of processing
 * TODO the events!
 * TODO Latency might be just computer load, it occurs not
 * TODO every time.
 *
 * !!!!! Misschien moet inner class Timer wel volledig bij
 * een 'play' status de tijden schrijven in de view class.
 * Dus initieren met de tijd string dan view setten en gaan
 * tellen en updaten.
 *
 *
 */
public class SongScroller {

    // the scrollview depicting the scroller.
    private ScrollView scrollView;

    private Player player;

    private Timer timer;

    private Thread timerThread;

    public SongScroller(Player player) {
        this.player = player;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    @Deprecated
    public JPanel getView() {
        return scrollView.getPanel();
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
    This listener is called when the value changes in
    the 'time' property. Changes are needed to be known
    to set the time labels of the scroller and to put
    the scroller at its right point.
     */
    public PropertyChangeListener getCurrentTimeListener() {
        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(getClass().getName() + " Time listener called!");
                orderListeners(evt);
            }
        };
    }

    /*
    This listener is called when the value changes in
    the 'state' property. Changes are needed to be known
    to control the timer, whether it has to run or not.
     */
    public PropertyChangeListener getStateListener() {
        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(getClass().getName() + " State listener called!");
                orderListeners(evt);
            }
        };
    }


    /*
    The events array, to be set by the two listeners.
     */
    private PropertyChangeEvent[] events = new PropertyChangeEvent[2];
    /*
    orderListeners sorts both listeners and adds
    them to the events array.

    Each statement follows the same steps:
    - the event is added to the events array at a
    particular place.
    - the events array is checked whether its full.
    - if full, the events are executed in the specified
    order of placement.
    - if not full, the method ends.

    All the steps are followed within a synchronized on
    events array method so a event can not be set while
    processing previous event.
     */
    private void orderListeners(PropertyChangeEvent evt) {
        //synchronized (events) {
            if (evt.getPropertyName().equals("state")) {
                events[1] = evt;
                if (isEventsFull(events)) {
                    processEvents(events);
                    events = new PropertyChangeEvent[2];
                }
                if (((String)events[1].getNewValue()).equals("stop")) {
                    processEvents(events);
                    events = new PropertyChangeEvent[2];
                }
            } else if (evt.getPropertyName().equals("time")) {
                events[0] = evt;
                if (isEventsFull(events)) {
                    processEvents(events);
                    events = new PropertyChangeEvent[2];
                }
            }
        //}
    }

    /*
    Iterate over events array to check if both events
    are in the array.
     */
    private boolean isEventsFull(PropertyChangeEvent[] events) {
        for (PropertyChangeEvent event : events) {
            if (event == null) {
                System.out.println(getClass().getName() + "isEventFull: false!");
                return false;
            }
        }
        System.out.println(getClass().getName() + "isEventFull: true!");
        return true;
    }

    /*
    Execute the events in the given order.
     */
    private void processEvents(PropertyChangeEvent[] events) {
        String state = (String) events[1].getNewValue();

        System.out.println(getClass().getName() + " " + state);

        switch (state) {
            case PlayerProperties.STOP:
                // stop the timer.
                if (timer != null) {
                    timer.setRunning(false);
                    try {
                        timerThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timer = null;
                }

                // set everything 0
                initScrollView("000000:000000");

                break;

            case PlayerProperties.PAUSE:
                // stop the timer.
                if (timer != null) {
                    timer.setRunning(false);
                    try {
                        timerThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timer = null;
                }
                break;

            case PlayerProperties.PLAY:

                /*
                First kill an existing timer!
                */
                if (timer != null) {
                    timer.setRunning(false);
                    try {
                        timerThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timer = null;
                    System.out.println("!!!! Ended Timer !!!!");
                }
                /*
                Second set the scroll view! The
                timer reads its values from it!
                 */
                initScrollView((String) events[0].getNewValue());
                timer = new Timer();
                timerThread = new Thread(timer);
                timerThread.start();
                break;
            default:
                break;
        }

    }

    /*
    Init the scroll view with the current time and
    the total time given by the String value.
    'current time' and 'total time' split by ':'.

    example: 000000:000000
     */
    private void initScrollView(String value) {

        String[] time = value.split(":");

        // EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scrollView.getSlider().setMinimum(0);
                scrollView.getSlider().setMaximum(Integer.parseInt(time[1]));
                scrollView.getSlider().setValue(Integer.parseInt(time[0]));

                scrollView.getTime().setText(time(time[0]));

                scrollView.getPlayTime().setText(time(time[1]));
            }
        }); // END EDT

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

                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            scrollView.getSlider().setValue(currValue + 1);

                            scrollView.getTime().setText(time(Integer.toString(currValue)));
                        }
                    }); // END EDT

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
