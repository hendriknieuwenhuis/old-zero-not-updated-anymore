package com.bono.zero.api.models.commands;

import com.bono.zero.api.Endpoint;

import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 14/08/15.
 */
public interface Command {

    byte[] getCommandBytes();

    String getCommandString();

}
