package com.bono.zero.monitor;

import com.bono.zero.model.Settings;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * The thread that monitors the  server. When something changes in he
 * server the server responses with the subject that changed!
 * Created by hennihardliver on 20/06/14.
 */
public class MonitorThread implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private OutputStream writer;

    private ServerMonitor serverMonitor;

    public static int count;

    public MonitorThread(ServerMonitor serverMonitor) {
        this.serverMonitor = serverMonitor;
        count++;

    }

    @Override
    public void run() {
        System.out.println(count);
        try {
            connect();


        } catch (Exception e) {
            giveCommand("clearerror\n".getBytes());
            e.printStackTrace();
        //} finally {
           // giveCommand("idle\n".getBytes());

           // listenToServer();
        }


        //serverMonitor.newMonitorThread();

    }


    private void connect() throws Exception {

        if (Settings.getSettings().getAddress() != null) {
            socket = new Socket();
            try {
                socket.connect(Settings.getSettings().getAddress(), 5000);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String version = reader.readLine();

                // if the server returns ACK error
                if (version.startsWith("ACK")) {
                    throw new Exception("An ACK return of server");
                } else {
                    System.out.println(version);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("timeout");
                //serverMonitor.newMonitorThread();
                return;
                // -** this is run when the server is down at first when a time out time is used! **-
                // TODO -- no server running and connecting impossible --
                /*
                In this block the ServerMonitor thread must be prevented
                from endlessly starting a new monitoring thread.
                 */
                //e.printStackTrace();

            } catch (SocketException e) {
                // TODO -- no server running and connecting impossible --
                /*
                In this block the ServerMonitor thread must be prevented
                from endlessly starting a new monitoring thread.
                 */
                System.out.println(" socket exception");
                //serverMonitor.newMonitorThread();
                return;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void giveCommand(byte[] command) {
        System.out.println("giving command");
        try {
            writer = socket.getOutputStream();
            writer.write(command);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenToServer() {
        System.out.println("listening!");
        String read;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((read = reader.readLine()) != null) {
                System.out.println(read);
                if (read.startsWith("OK")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
