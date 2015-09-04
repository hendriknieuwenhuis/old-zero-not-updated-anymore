package com.bono.zero.control;


import com.bono.zero.api.events.PropertyListener;
import com.bono.zero.api.models.Property;
import com.bono.zero.api.ExecuteCommand;
import com.bono.zero.api.models.commands.Executor;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.model.ScrollTime;
import com.bono.zero.view.ScrollView;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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

    // initiate with zero's.
    private ScrollTime scrollTime = new ScrollTime("0:0");

    private Executor executor;

    private Timer timer;

    private Thread timerThread;

    private PropertyListener timePropertyListener;

    private PropertyListener statePropertyListener;

    private MouseAdapter mouseAdapter;

    // holding the properties given by the listeners.
    private Property<String>[] propertiesArray = new Property[2];



    public SongScroller(Executor executor, ScrollView scrollView) {
        this.scrollView = scrollView;
        this.executor = executor;
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
        if (mouseAdapter == null) {
            mouseAdapter = (new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    JSlider slider = (JSlider) e.getSource();

                    if (!slider.getValueIsAdjusting()) {

                        executor.addCommand(new ExecuteCommand(PlayerProperties.SEEKCUR, Integer.toString(slider.getValue())));
                    }
                }
            });
        }
        return mouseAdapter;
    }

    /*
    This listener is called when the value changes in
    the 'time' property. Changes are needed to be known
    to set the time labels of the scroller and to put
    the scroller at its right point.
     */
    public PropertyListener getTimePropertyListener() {
        if (timePropertyListener == null) {
            timePropertyListener = (evt) -> {
                synchronized (propertiesArray) {
                    propertiesArray[0] = (Property<String>) evt.getSource();
                    execute();
                }
            };
        }
        return timePropertyListener;
    }

    /*
    This listener is called when the value changes in
    the 'state' property. Changes are needed to be known
    to control the timer, whether it has to run or not.
     */
    public PropertyListener getStatePropertyListener() {
        if (statePropertyListener == null) {
            statePropertyListener = (evt) -> {
                synchronized (propertiesArray) {
                    propertiesArray[1] = (Property<String>) evt.getSource();
                    execute();
                }
            };
        }
        return statePropertyListener;
    }

    private void execute() {
        if (propertiesArray[1] != null) {
            if (propertiesArray[1].getValue().equals("stop")) {
                setScrollerValues();
            }
        } else if (propertiesArray[1] != null && propertiesArray[0] != null) {
            setScrollerValues();
        }
    }

    private void setScrollerValues() {
        try {
            switch (propertiesArray[1].getValue()) {
                case PlayerProperties.STOP:

                    killTimer();

                    // set everything 0
                    scrollTime.setTime("0:0");
                    initScrollView();

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

                    scrollTime.setTime(propertiesArray[0].getValue());
                    initScrollView();
                    timer = new Timer();
                    timerThread = new Thread(timer);
                    timerThread.start();

                    break;
                default:
                    break;
            }
        } finally {
            // remove the properties.
            propertiesArray[0] = null;
            propertiesArray[1] = null;
            propertiesArray = new Property[2];
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
    private void initScrollView() {

        //String[] time = value.split(":");

        // EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scrollView.getSlider().setMinimum(0);
                scrollView.getSlider().setMaximum(Integer.parseInt(scrollTime.getTotalTime()));
                scrollView.getSlider().setValue(Integer.parseInt(scrollTime.getCurrentTime()));

                scrollView.getTime().setText(scrollTime.getCurrentTimeHhMmSs());

                scrollView.getPlayTime().setText(scrollTime.getTotalTimeHhMmSs());
            }
        }); // END EDT

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

        private int currValue;

        public Timer() {}

        @Override
        public void run() {

            currValue = Integer.parseInt(scrollTime.getCurrentTime());

            while (isRunning()) {
                if (scrollView.getSlider().getValue() < scrollView.getSlider().getMaximum()) {

                    long currTime = System.currentTimeMillis();

                    // EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            scrollView.getSlider().setValue(currValue);

                            scrollView.getTime().setText(scrollTime.getCurrentTimeHhMmSs());
                        }
                    }); // END EDT

                    currValue++;
                    scrollTime.setCurrentTime(Integer.toString(currValue));

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
