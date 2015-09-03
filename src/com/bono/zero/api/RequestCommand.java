package com.bono.zero.api;

import com.bono.zero.api.models.commands.AbstractCommand;

import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 29/08/15.
 */
public class RequestCommand extends AbstractCommand<List<String>> {

    // Command without arguments
    public RequestCommand(String command) {
        super(command);

    }

    // Command with single argument
    public RequestCommand(String command, String arg) {
        super(command, arg);

    }

    // Command with multiple arguments
    public RequestCommand(String command, String[] args) {
        super(command, args);

    }


    @Override
    public List<String> execute() throws IOException {
        return endpoint.sendRequest(this);
    }
}
