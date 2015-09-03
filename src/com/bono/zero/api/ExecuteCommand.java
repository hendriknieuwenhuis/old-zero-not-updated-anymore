package com.bono.zero.api;

import com.bono.zero.api.models.commands.AbstractCommand;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 15/08/15.
 */
public class ExecuteCommand extends AbstractCommand<String> {

    // Command without arguments
    public ExecuteCommand(String command) {
        super(command);

    }

    // Command with single argument
    public ExecuteCommand(String command, String arg) {
        super(command, arg);

    }

    // Command with multiple arguments
    public ExecuteCommand(String command, String[] args) {
        super(command, args);

    }


    @Override
    public String execute() throws IOException {
        return endpoint.sendCommand(this);
    }
}
