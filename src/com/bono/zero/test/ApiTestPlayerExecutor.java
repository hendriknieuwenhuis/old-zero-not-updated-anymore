package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.models.commands.Command;
import com.bono.zero.api.RequestCommand;
import com.bono.zero.api.properties.PlayerProperties;
import com.bono.zero.api.properties.PlaylistProperties;
import com.bono.zero.control.Idle;
import com.bono.zero.control.PlayerExecutor;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;


/**
 * Created by hendriknieuwenhuis on 28/08/15.
 */
public class ApiTestPlayerExecutor implements Observer {

    String host;
    int port;

    String reply;
    Endpoint endpoint = null;
    ServerStatus serverStatus = new ServerStatus();

    public ApiTestPlayerExecutor() {

        // get the host and port!
        String[] typed = null;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Type host-ip and port with a space between them.");
        typed = keyboard.nextLine().split(" ");
        if (typed.length == 2) {
            String version = null;
            host = typed[0];

            // catch when input for port is not a number format.
            try {
                port = Integer.parseInt(typed[1]);
            } catch (NumberFormatException e) {
                System.out.printf("Port no number!");
                System.exit(1);
            }

            endpoint = new Endpoint(host, port);
            try {
                version = endpoint.getVersion();
            } catch (IOException e) {
                System.exit(1);
            }
            if (!version.startsWith("OK")) {
                System.exit(1);
            }
        } else {
            System.exit(1);
        }

        // add listeners.
        serverStatus.getStatus().getSongid().setPropertyChangeListener(getSongidListener());

        // set the status.
        Thread thread1 = setServerStatus(endpoint);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // start the idle thread to listen for changes.
        Idle idle = new Idle(host, port, serverStatus);
        Thread idleThread = new Thread(idle);
        idleThread.start();


        PlayerExecutor playerExecutor = new PlayerExecutor(endpoint);
        playerExecutor.addObserver(this);
        Thread thread2 = new Thread(playerExecutor);
        thread2.start();



        while (true) {
            System.out.println("Type some: ");
            typed = keyboard.nextLine().split(" ");
            System.out.printf("You typed, %s\n", typed);
            switch (typed[0]) {
                case PlayerProperties.STOP:
                    playerExecutor.stop();
                    break;

                case PlayerProperties.PLAY:
                    playerExecutor.play();
                    break;

                case PlayerProperties.NEXT:
                    playerExecutor.next();
                    break;

                case PlayerProperties.PREVIOUS:
                    playerExecutor.previous();
                    break;

                case PlayerProperties.PLAYID:
                    playerExecutor.playid(typed[1]);
                    break;

                case PlayerProperties.PAUSE:
                    playerExecutor.pause(typed[1]);
                    break;

                case PlaylistProperties.PLAYLISTINFO:
                    executeRequest(new RequestCommand(PlaylistProperties.PLAYLISTINFO));
                    break;

                case ServerProperties.STATUS:
                    executeRequest(new RequestCommand(ServerProperties.STATUS));
                    break;

                default:
                    break;

            }

        }
    }

    private void executeRequest(Command<List<String>> command) {
        Runnable execute = () -> {
            List<String> feedback = null;
            command.addEndpoint(new Endpoint(host, port));
            try {
                feedback = command.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String line : feedback) {
                System.out.println(line);
            }
        };
        Thread executeThread = new Thread(execute);
        executeThread.start();
    }

    private Thread setServerStatus(Endpoint endpoint) {
        Thread thread = new Thread(() -> {
            List<String> feedback = null;
            try {
                feedback = endpoint.sendRequest(new RequestCommand(ServerProperties.STATUS));
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverStatus.setStatus(feedback);
        });
        return thread;
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.printf("Command replies: %s\n", (String) arg);
    }

    private PropertyChangeListener getSongidListener() {
        return (PropertyChangeEvent) -> {
          executeRequest(new RequestCommand(ServerProperties.CURRENTSONG));

        };
    }



    private void print(List<String> list) {

    }

    public static  void main(String[] args) {
        new ApiTestPlayerExecutor();

    }
}
