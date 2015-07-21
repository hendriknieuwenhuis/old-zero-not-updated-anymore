package com.bono.zero.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ServerMonitor is the monitor object for monitoring the
 * servers idle state. It launches the MonitorThread that listens
 * for changes in the server. The MonitorThread is launched and
 * runs only for the time till a change in the server occurs. After
 * the change the idle state is ended and the thread is closed.
 * A new launched MonitorThread will start a new idle state till
 * it ends.
 * Created by hennihardliver on 21/06/14.
 */
public class ServerMonitor implements Runnable {

    private boolean end;
    private boolean pause;              // in or out the wait() while loop.

    private Object lock = new Object();

    public ServerMonitor() {
        end = false;
    }

    @Override
    public void run() {
        while (true) {

            // the break out option
            if (end) {
                break;
            }

            // start a new thread that monitors the idle state
            // of the server.
            new Thread(new MonitorThread(this)).start();

            // the loop is paused till notified
            // for a new monitor thread or end.
            pause();

        }

    }

    /**
     * Pause the thread till a new cycle of the run loop
     * has to made to start a new monitor thread.
     */
    private void pause() {
        pause = true;
        synchronized (lock) {
            while (pause) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void newMonitorThread() {
        synchronized (lock) {
            pause = false;
            lock.notify();
        }
    }


}
