package com.bono.zero.api;

import com.bono.zero.api.models.commands.ServerCommand;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 15/08/15.
 */
public class ExecuteCommand extends ServerCommand<String> {

    // Command without arguments
    public ExecuteCommand(Endpoint endpoint, String command) {
        super(endpoint, command);

    }

    // Command with single argument
    public ExecuteCommand(Endpoint endpoint, String command, String arg) {
        super(endpoint, command, arg);

    }

    // Command with multiple arguments
    public ExecuteCommand(Endpoint endpoint, String command, String[] args) {
        super(endpoint, command, args);

    }


    @Override
    public String execute() throws IOException {
        return endpoint.sendCommand(this);
    }
}
