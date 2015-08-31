package com.bono.zero.api;

import com.bono.zero.api.models.commands.AbstractCommand;

import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 29/08/15.
 */
public class RequestCommand extends AbstractCommand<List<String>> {

    // Command without arguments
    public RequestCommand(String request) {
        super();
        this.request = request;
    }

    // Command with single argument
    public RequestCommand(String request, String arg) {
        super();
        this.request = request;
        this.args = new String[]{arg};
    }

    // Command with multiple arguments
    public RequestCommand(String request, String[] args) {
        super();
        this.request = request;
        this.args = args;
    }


    @Override
    public List<String> execute() throws IOException {
        return endpoint.sendRequest(this);
    }
}
