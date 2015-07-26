package com.bono.zero.test;

import com.bono.zero.api.Endpoint;

import java.io.IOException;

/**
 * Created by hendriknieuwenhuis on 25/07/15.
 */
public class ApiTestEndpoint {

    public static void main(String[] args) {
        Endpoint endpoint = new Endpoint("192.168.2.2", 6600);
        String version = null;
        try {
            version = endpoint.getVersion();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(version);
    }
}
