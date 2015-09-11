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

    public static final String COMMAND_LIST_OK_BEGIN = "command_list_ok_begin";
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

                // print.
                //System.out.printf("%s: %s\n", getClass().getName(), line);

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

                //byte[] list = null;
                //List<Byte> byteList = new ArrayList<>();
                String args = null;

                // execute.
                // needs socket implementation in while loop.

                // TODO DIT MOET IN BYTES COMMAND.GETCOMMANDBYTES().
                for (com.bono.zero.api.models.commands.Command command : commands) {
                    String arg = null;
                    if (((AbstractCommand)command).getArgs() != null) {

                        arg = Arrays.toString(((AbstractCommand) command).getArgs());
                        arg = arg.substring(1, (arg.length() - 1));
                        args = args + command.getCommandString() + arg + File.separator;


                    } else {
                        if (command.getCommandString().equals(COMMAND_LIST_OK_BEGIN)) {
                            if (args != null) {
                                args = command.getCommandString() + File.separator + args;
                            } else {
                                args = command.getCommandString() + File.separator;
                            }
                        } else if (command.getCommandString().equals(COMMAND_LIST_END)) {
                            args = args + command.getCommandString();
                        }
                    }



                }
                args += "\n";
                System.out.println(args);
                writer(args.getBytes());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((line = reader.readLine()) != null) {

                    // print.
                    //System.out.printf("%s: %s\n", getClass().getName(), line);

                    //if (line.equals("OK") || line.startsWith("ACK")) {
                    //    break;
                    //}
                    System.out.println(line);
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
