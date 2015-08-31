package com.bono.zero.api.models.commands;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.models.commands.Command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 15/08/15.
 */
abstract class AbstractCommand<T> implements Command<T> {

    protected String request;
    protected String[] args;

    protected Endpoint endpoint;

    /**
     * Returns the command, request and arguments as a
     * array of bytes.
     * @return byte[]
     */
    @Override
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

    @Override
    public void addEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    @Override
    public String toString() {
        return "AbstractCommand{" +
                "args=" + Arrays.toString(args) +
                ", request='" + request + '\'' +
                '}';
    }
}
