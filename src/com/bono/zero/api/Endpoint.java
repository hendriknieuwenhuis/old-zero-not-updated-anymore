package com.bono.zero.api;

import com.bono.zero.api.models.Command;
import com.bono.zero.api.models.commands.AbstractCommand;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 24/07/15.
 */
public class Endpoint {

    public static final String COMMAND_LIST_OK_BEGIN = "command_list_begin";
    public static final String COMMAND_LIST_END = "command_list_end";

    private String host;
    private int port;
    private String user;
    private String password;

    private Socket socket;
    private String version;

    public Endpoint() {}

    public Endpoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Send a request to the server and give back the
     * answer.
     * The argument is a command object with the request
     * and if needed the arguments to that request. The
     * server's response is returned as an array of Strings.
     * @param command
     * @return List of Strings
     */
    @Deprecated
    public List<String> request(Command command) throws IOException {

        /*
        ------    make a mpd error exception?    ------
         */
        List<String> response = new ArrayList<String>();
        String line;
        BufferedReader reader;
        try {
            connect();
            writer(command.getCommand());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (line.equals("OK") || line.startsWith("ACK")) {
                    break;
                }
                response.add(line);
            }
            socket.close();
        } catch (IOException e) {
            new IOException("check connection settings");
        }
        return response;

    }

    /**
     * Send a request to the server and give back the
     * answer.
     * The argument is a command object with the request
     * and if needed the arguments to that request. The
     * server's response is returned as an array of Strings.
     * @param command
     * @return List of Strings
     */
    public List<String> sendRequest(com.bono.zero.api.models.commands.Command command) throws IOException {

        /*
        ------    make a mpd error exception?    ------
         */
        List<String> response = new ArrayList<String>();
        String line;
        BufferedReader reader;
        try {
            connect();
            writer(command.getCommandBytes());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (line.equals("OK") || line.startsWith("ACK")) {
                    break;
                }
                response.add(line);
            }
        } catch (IOException e) {
            new IOException("check connection settings");
        } finally {
            socket.close();
        }
        return response;
    }


    /**
     * Send a command that returns a boolean for simple
     * command that only get an OK response when worked.
     * @param command
     * @return String
     */
    @Deprecated
    public String command(Command command) throws IOException {
        /*
        ------    make a mpd error exception?    ------
         */
        BufferedReader reader;
        String line = null;
        try {
            connect();
            writer(command.getCommand());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = reader.readLine()) != null) {

                // print.
                //System.out.printf("%s: %s\n", getClass().getName(), line);

                if (line.equals("OK") || line.startsWith("ACK")) {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            new IOException("check connection settings");
        }
        return line;
    }

    /**
     * Send a command that returns a boolean for simple
     * command that only get an OK response when worked.
     * @param command
     * @return String
     */
    public String sendCommand(com.bono.zero.api.models.commands.Command command) throws IOException {
        /*
        ------    make a mpd error exception?    ------
         */
        BufferedReader reader;
        String line = null;
        try {
            connect();
            writer(command.getCommandBytes());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = reader.readLine()) != null) {



                if (line.equals("OK") || line.startsWith("ACK")) {
                    break;
                }
            }


        } catch (IOException e) {
            new IOException("check connection settings");
        } finally {
            socket.close();
        }
        return line;
    }

    /*
    private class DynamicBytes creates an object that
    holds a byte array, a new byte array can be added
    to that array by the addBytes method.
    So in case of a list of commands this class
    can create the commands list as one array of bytes
    to be send to the server.
     */
    private class DynamicBytes {

        private byte[] data;

        public DynamicBytes() {
            data = new byte[0];
        }

        public void addBytes(byte[] value) {
            byte[] temp = new byte[(data.length + value.length)];
            System.arraycopy(data, 0, temp, 0, data.length);
            System.arraycopy(value, 0, temp, data.length, value.length);
            data = temp;
        }

        public byte[] getBytes() {
            return data;
        }
    }

    /*
    The list must be converted to one line of bytes to send.
     */
    public String sendCommand(List<com.bono.zero.api.models.commands.Command> commands) throws IOException, NullPointerException {
        BufferedReader reader;
        OutputStream writer = null;
        String line = null;

        try {
            if (commands.get(0).getCommandString().equals(COMMAND_LIST_OK_BEGIN)) {
                connect();
                writer = socket.getOutputStream();

                DynamicBytes dynamicBytes = new DynamicBytes();
                String send = "";

                for (com.bono.zero.api.models.commands.Command command : commands) {
                    //send = send + new String(command.getCommandBytes());
                    dynamicBytes.addBytes(command.getCommandBytes());
                }
                writer(dynamicBytes.getBytes());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((line = reader.readLine()) != null) {

                    if (line.equals("OK") || line.startsWith("ACK")) {
                        break;
                    }

                }

            } else {
                new NullPointerException();
            }
        } finally {
            writer.flush();
            socket.close();
        }
        return line;
    }

    private void connect() throws IOException {
        SocketAddress address = new InetSocketAddress(host, port);
        socket = new Socket();
        socket.connect(address);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        version = reader.readLine();
    }

    private void writer(byte[] bytes) throws IOException {
        OutputStream writer = socket.getOutputStream();
        writer.write(bytes);
        writer.flush();
    }

    public String getVersion() throws IOException {
        connect();
        if (socket != null) socket.close();
        return version;
    }

    public void close() throws IOException {
        if (socket != null) socket.close();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
