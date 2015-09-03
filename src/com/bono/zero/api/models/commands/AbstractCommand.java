package com.bono.zero.api.models.commands;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.models.commands.Command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 15/08/15.
 */
public abstract class AbstractCommand<T> implements Command<T> {

    protected String command;
    protected String[] args;

    protected Endpoint endpoint;

    public AbstractCommand(String command) {
        this.command = command;
    }

    public AbstractCommand(String command, String arg) {
        this.command = command;
        this.args = new String[]{arg};
    }

    public AbstractCommand(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    /**
     * Returns the command, request and arguments as a
     * array of bytes.
     * @return byte[]
     */
    @Override
    public byte[] getCommand() {

        String outCommand = null;
        if (command != null) {
            outCommand = command;
            if (args != null) {
                for (String arg : args) {
                    outCommand = outCommand + " " + arg;
                }
            }
        } else {
            return null;
        }
        outCommand += "\n";
        return outCommand.getBytes();
    }

    @Override
    public void addEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public abstract T execute() throws IOException;


    @Override
    public String toString() {
        return "AbstractCommand{" +
                "args=" + Arrays.toString(args) +
                ", request='" + command + '\'' +
                '}';
    }

}
