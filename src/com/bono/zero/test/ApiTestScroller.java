package com.bono.zero.test;

import com.bono.zero.model.ScrollTime;
import com.bono.zero.view.ScrollView;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by hendriknieuwenhuis on 16/08/15.
 */
public class ApiTestScroller {

    private ScrollView scrollView;

    private ScrollTime scrollTime;

    private ScrollTimer scrollTimer;

    private JFrame frame;

    private Thread timerThread;

    public ApiTestScroller() {
        scrollTime = new ScrollTime("0:450");
        init();
    }

    private void init() {

        try {
            // EDT
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                scrollView = new ScrollView();
                scrollView.getTime().setText(scrollTime.getCurrentTimeHhMmSs());
                scrollView.getPlayTime().setText(scrollTime.getTotalTimeHhMmSs());
                scrollView.getSlider().setMinimum(0);
                scrollView.getSlider().setMaximum(Integer.parseInt(scrollTime.getTotalTime()));
                scrollView.getSlider().setValue(Integer.parseInt(scrollTime.getCurrentTime()));
                frame.getContentPane().add(scrollView);
                frame.pack();
                frame.setVisible(true);
            }); // END EDT
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        scrollTimer = new ScrollTimer(scrollTime);
        timerThread = new Thread(scrollTimer);
        timerThread.start();
    }

    public void update(ScrollTime scrollTime) {
        if (scrollTimer != null) {
            scrollTimer.setRunning(false);
        }
        if (timerThread != null) {
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.scrollTime = null;
        this.scrollTime = scrollTime;

        // EDT
        SwingUtilities.invokeLater(() -> {
            scrollView.getTime().setText(scrollTime.getCurrentTimeHhMmSs());
            scrollView.getPlayTime().setText(scrollTime.getTotalTimeHhMmSs());
            scrollView.getSlider().setMinimum(0);
            scrollView.getSlider().setMaximum(Integer.parseInt(scrollTime.getTotalTime()));
            scrollView.getSlider().setValue(Integer.parseInt(scrollTime.getCurrentTime()));
        }); // END EDT

        scrollTimer = new ScrollTimer(scrollTime);
        timerThread = new Thread(scrollTimer);
        timerThread.start();
    }

    private class ScrollTimer implements Runnable {

        private ScrollTime scrollTime;

        private boolean running;

        public ScrollTimer(ScrollTime scrollTime) {
            this.scrollTime = scrollTime;
            running = true;
        }

        @Override
        public void run() {
            while (isRunning()) {
                long currTime = System.currentTimeMillis();

                // EDT
                SwingUtilities.invokeLater(() -> {
                    scrollView.getSlider().setValue(Integer.parseInt(scrollTime.getCurrentTime()));
                    scrollView.getTime().setText(scrollTime.getCurrentTimeHhMmSs());
                }); // END EDT

                // add one.
                int addOne = Integer.parseInt(scrollTime.getCurrentTime());
                addOne++;
                scrollTime.setCurrentTime(Integer.toString(addOne));

                // calculate sleep time.
                long sleep = 1000 - (System.currentTimeMillis() - currTime);

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private boolean isRunning() {
            return running;
        }

        public  void setRunning(boolean running) {
            this.running = running;
        }
    }

    public static void main(String[] args) {
        ApiTestScroller apiTestScroller = new ApiTestScroller();
        String[] times = new String[]{"0:245", "34:2341", "11570:39864"};
        for (String time : times) {

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            apiTestScroller.update(new ScrollTime(time));

        }
    }

}
