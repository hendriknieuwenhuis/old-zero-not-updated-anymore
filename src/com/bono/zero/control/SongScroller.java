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

    ! If state is 'STOP' time is NOT given and NOT set
    as a property so, NOT invoked either !
     */
    public PropertyChangeListener getCurrentTimeListener() {
        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //System.out.println(getClass().getName() + " Time listener called!");
                synchronized (lock) {
                    orderListeners(evt);
                }
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
                //System.out.println(getClass().getName() + " State listener called!");
                synchronized (lock) {
                    orderListeners(evt);
                }
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
    private Object lock = new Object();
    private EventBucket eventBucket = new EventBucket();
    private void orderListeners(PropertyChangeEvent evt) {

        synchronized (lock) {
            if (eventBucket.add(evt)) {
                processEvents(eventBucket);
                eventBucket = null;
                eventBucket = new EventBucket();
            }
        }

    }

    /*
    Inner class EventBucket is the bucket holding the
    two PropertyChangeEvent's.
    Method 'add()' returns a boolean true when the bucket
    is full or other demand is met.
     */
    private class EventBucket {

        private PropertyChangeEvent time = null;
        private PropertyChangeEvent state = null;

        public boolean add(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("time")) {
                time = evt;
                if (state != null && time != null) {
                    return true;
                }
                return false;
            } else if (evt.getPropertyName().equals("state")) {
                state = evt;
                if (state != null && time != null) {
                    return true;
                } else if (((String)state.getNewValue()).equals("stop")) {
                    return true;
                }
                return false;
            }
            return false;
        }

        public PropertyChangeEvent getState() {
            return state;
        }

        public PropertyChangeEvent getTime() {
            return time;
        }

    }


    /*
    Execute the events in the given order.
     */
    private void processEvents(EventBucket eventBucket) {
        String state = (String) eventBucket.getState().getNewValue();

        switch (state) {
            case PlayerProperties.STOP:

                killTimer();

                // set everything 0
                initScrollView("000000:000000");

                break;

            case PlayerProperties.PAUSE:

                killTimer();

                break;

            case PlayerProperties.PLAY:

                killTimer();
                /*
                Set the scroll view! The timer
                reads its values from it!
                 */
                initScrollView((String) eventBucket.getTime().getNewValue());
                timer = new Timer(eventBucket.getTime());
                timerThread = new Thread(timer);
                timerThread.start();
                break;
            default:
                break;
        }
    }

    /*
    Kill an existing timer!
     */
    private void killTimer() {

        if (timer != null) {
            timer.setRunning(false);
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer = null;
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

        private PropertyChangeEvent time;

        private int currValue;

        public Timer(PropertyChangeEvent time) {
            this.time = time;
            String[] initTime = ((String) time.getNewValue()).split(":");
            currValue = Integer.parseInt(initTime[0]);
        }

        @Override
        public void run() {

            while (isRunning()) {
                if (scrollView.getSlider().getValue() < scrollView.getSlider().getMaximum()) {

                    long currTime = System.currentTimeMillis();

                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            scrollView.getSlider().setValue(currValue);

                            scrollView.getTime().setText(time(Integer.toString(currValue)));
                        }
                    }); // END EDT

                    currValue++;

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
