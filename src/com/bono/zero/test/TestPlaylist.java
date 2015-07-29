package com.bono.zero.test;

import com.bono.zero.ServerProperties;
import com.bono.zero.api.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.Playlist;
import com.bono.zero.api.Song;

import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 29/07/15.
 */
public class TestPlaylist {

    public static void main(String[] args) {
        Endpoint endpoint = new Endpoint();
        endpoint.setHost("192.168.2.7");
        endpoint.setPort(6600);
        List<String> request = null;
        try {
             request = endpoint.request(new Command(ServerProperties.LIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Playlist playlist = new Playlist();
        playlist.populatePlaylist(request);

        for (Song song : playlist.getPlaylist()) {
                System.out.println(song.toString());
        }

    }
}
