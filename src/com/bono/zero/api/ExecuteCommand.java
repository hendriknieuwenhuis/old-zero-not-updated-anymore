package com.bono.zero.api;

import com.bono.zero.api.models.commands.AbstractCommand;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 15/08/15.
 */
public class ExecuteCommand extends AbstractCommand<String> {

    // Command without arguments
    public ExecuteCommand(String request) {
        super();
        this.request = request;
    }

    // Command with single argument
    public ExecuteCommand(String request, String arg) {
        super();
        this.request = request;
        this.args = new String[]{arg};
    }

    // Command with multiple arguments
    public ExecuteCommand(String request, String[] args) {
        super();
        this.request = request;
        this.args = args;
    }


    @Override
    public String execute() throws IOException {
        return endpoint.sendCommand(this);
    }
}
