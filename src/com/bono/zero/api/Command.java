package com.bono.zero.api;

/**
 * Created by hendriknieuwenhuis on 24/07/15.
 *
 * Class command is the command object that's send to the
 * server when a request is made. The request + arguments
 * is set as a String and array of Strings for the argument.
 * The request can be gotten as byte as the server requests
 * bytes.
 */
public class Command {

    private String request;
    private String[] args;

    // Command without arguments
    public Command(String request) {
        this.request = request;
    }

    // Command with single argument
    public Command(String request, String arg) {
        this.request = request;
        this.args = new String[]{arg};
    }

    // Command with multiple arguments
    public Command(String request, String[] args) {
        this.request = request;
        this.args = args;
    }

    /**
     * Returns the command, request and arguments as a
     * array of bytes.
     * @return byte[]
     */
    public byte[] getCommand() {

        String command = null;
        if (request != null) {
            command = request;
            if (args != null) {
                for (String arg : args) {
                    command = command + " " + arg;
                }
            }
        } else {
            return null;
        }
        command += "\n";
        return command.getBytes();
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String[] getArguments() {
        return args;
    }

    public void setArguments(String[] args) {
        this.args = args;
    }
}
