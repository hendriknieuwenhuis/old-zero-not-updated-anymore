package com.bono.zero.control;

import com.bono.zero.api.Endpoint;
import com.bono.zero.api.RequestCommand;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by hendriknieuwenhuis on 31/08/15.
 */
public class Request implements Callable<List<String>> {

    private RequestCommand requestCommand;

    public Request(Endpoint endpoint, RequestCommand requestCommand) {
        this.requestCommand = requestCommand;
        requestCommand.addEndpoint(endpoint);
    }

    @Override
    public List<String> call() throws Exception {

        return requestCommand.execute();

    }
}
