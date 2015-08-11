package com.bono.zero.test;

import com.bono.zero.api.models.Command;
import com.bono.zero.api.Endpoint;
import com.bono.zero.api.ServerStatus;
import com.bono.zero.api.models.Property;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class TestStatus {

    private static Endpoint endpoint;
    private static ServerStatus serverStatus;

    public static void main(String[] args) {
        endpoint = new Endpoint();
        endpoint.setHost("192.168.2.2");
        endpoint.setPort(6600);
        serverStatus = new ServerStatus();

        List<String> query = null;
        try {
            query = endpoint.request(new Command("status"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        serverStatus.getStatus().getState().setChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Property property = (Property) e.getSource();
                System.out.printf("%s: %s\n", getClass().getName(), property.toString());

            }



        });*/

        serverStatus.setStatus(query);




    }
}
