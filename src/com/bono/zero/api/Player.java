package com.bono.zero.api;

import com.bono.zero.api.properties.PlayerProperties;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 24/07/15.
 */
public class Player {

    private  Endpoint endpoint;

    public Player(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String next() throws IOException {
        return endpoint.command(new Command(PlayerProperties.NEXT));
    }

    public String pause() throws IOException {
        return endpoint.command(new Command(PlayerProperties.PAUSE));
    }

    public String play() throws IOException {
        return endpoint.command(new Command(PlayerProperties.PLAY));
    }

    public String playid(String songid) throws IOException {
        return endpoint.command(new Command(PlayerProperties.PLAYID, songid));
    }

    public String previous() throws IOException {
        return endpoint.command(new Command(PlayerProperties.PREVIOUS));
    }

    public String seek(String songposition, String time) throws IOException {
        String[] args = {songposition, time};
        return endpoint.command(new Command(PlayerProperties.SEEK, args));
    }

    public String seekid(String songid, String time) throws  IOException {
        String[] args = {songid, time};
        return endpoint.command(new Command(PlayerProperties.SEEKID, args));
    }

    public String seekcur(String time) throws IOException {
        return endpoint.command(new Command(PlayerProperties.SEEKCUR, time));
    }
}
