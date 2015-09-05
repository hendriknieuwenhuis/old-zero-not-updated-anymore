package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ExecuteCommand;
import com.bono.zero.api.models.commands.Command;
import com.bono.zero.api.models.commands.Executor;
import com.bono.zero.api.properties.PlayerProperties;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 26/08/15.
 */
public class PlayerExecutor extends Executor<String> {

    private String reply;


    public PlayerExecutor(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    protected String executeCommand(Command command) throws IOException {
        return endpoint.sendCommand(command);
    }

    @Override
    public void run() {
        setRunning(true);
        while (isRunning()) {
            if (commandsList.size() == 0) {
                wait = true;
                goWait();
            }

            if (commandsList.size() > 0) {
                reply = new String("");

                // catch exception met settings view.
                try {
                    reply = executeCommand(commandsList.removeFirst());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.notifyObservers(reply);
                // act on server reply.
                //System.out.println(reply);
                if (!reply.equals("OK")) {
                    System.out.println("WHAAAAT!");
                }
            }
        }
    }



    public void next() {

        addCommand(new ExecuteCommand(PlayerProperties.NEXT));

    }

    public void pause(String pause) {
        addCommand(new ExecuteCommand(PlayerProperties.PAUSE, pause));
    }

    public void play() {
        addCommand(new ExecuteCommand(PlayerProperties.PLAY));
    }

    public void playid(String songid) {
        addCommand(new ExecuteCommand(PlayerProperties.PLAYID, songid));
    }

    public void previous() {
        addCommand(new ExecuteCommand(PlayerProperties.PREVIOUS));
    }

    public void seek(String songposition, String time) {
        String[] args = {songposition, time};
        addCommand(new ExecuteCommand(PlayerProperties.SEEK, args));
    }

    public void seekid(String songid, String time) {
        String[] args = {songid, time};
        addCommand(new ExecuteCommand(PlayerProperties.SEEKID, args));
    }

    public void seekcur(String time) {
        addCommand(new ExecuteCommand(PlayerProperties.SEEKCUR, time));
    }

    public void stop() {
        addCommand(new ExecuteCommand(PlayerProperties.STOP));
    }
}
