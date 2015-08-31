package com.bono.zero.api.models.commands;

import com.bono.zero.api.Endpoint;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by hendriknieuwenhuis on 15/08/15.
 *
 * Executor must be able to be set to stop, block 'addCommand' calls
 * and exit 'run' method if commands list is empty.
 */
public abstract class Executor<T extends Command> extends Observable implements Runnable {

    protected LinkedList<T> commandsList = new LinkedList<>();

    private Endpoint endpoint;

    private Object lock = new Object();

    protected boolean wait;

    protected boolean end = false;

    protected boolean running;

    /*
    Constructor assigns the endpoint.
     */
    public Executor(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    /*
    @Override
    public void run() {

        running = true;
        while (running) {
            if (commandsList.size() == 0) {
                wait = true;
                goWait();
            }

            if (commandsList.size() > 0) {
                String reply = executeCommand(commandsList.removeFirst());
                if (!reply.equals("OK")) {
                    System.out.println("WHAAAAT!");
                }
            }
        }

    }*/

    // set boolean end true
    // so addcommand gets blocked.
    public void end() {
        end = true;

    }

    protected void goWait() {

        while (wait) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected Object executeCommand(T command) throws IOException {
        return command.execute();
    }

    public void addCommand(T command) {
        if (!end) {
            command.addEndpoint(endpoint);
            commandsList.add(command);  // returns boolean!
            wait = false;
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    // end the runnable.
    @Deprecated
    public void endExecutor() {
        running = false;
        wait = false;
        commandsList.clear();
        try {
            endpoint.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            lock.notify();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


}
