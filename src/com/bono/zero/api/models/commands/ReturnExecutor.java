package com.bono.zero.api.models.commands;

import com.bono.zero.api.Endpoint;

import java.lang.reflect.Type;
import java.util.Observable;
import java.util.concurrent.Callable;

/**
 * Created by hendriknieuwenhuis on 31/08/15.
 */
public class ReturnExecutor <T extends Command, R> extends Observable implements Callable<R> {

    private T command;
    private R reply;
    private Endpoint endpoint;

    public ReturnExecutor(T command, Endpoint endpoint) {
        this.command = command;
        this.endpoint = endpoint;
    }

    @Override
    public  R call() throws Exception {

        return reply;
    }
}
