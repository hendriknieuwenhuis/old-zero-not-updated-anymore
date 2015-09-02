package com.bono.zero.test;

import com.bono.zero.api.events.PropertyEvent;
import com.bono.zero.api.events.PropertyListener;
import com.bono.zero.api.models.Property;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 02/09/15.
 */
public class ApiTestPropertyEvent {

    private Property property;
    //private MockProperty mockProperty;
    private MockListener mockListener;

    public ApiTestPropertyEvent() {
        //mockProperty = new MockProperty("hallo");
        mockListener = new MockListener();
        property = new Property("hallo");
        //mockProperty.addPropertyListener(mockListener);
        property.setPropertyListeners(mockListener);
    }

    public void setValue(String value) {
        //mockProperty.setValue(value);
        property.setValue(value);
    }

    public void sleepLittle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*
        TODO Er moet synchronizatie op property toegepastworden!
         */

        ApiTestPropertyEvent apiTestPropertyEvent = new ApiTestPropertyEvent();
        apiTestPropertyEvent.setValue("hello");
        apiTestPropertyEvent.sleepLittle();
        apiTestPropertyEvent.setValue("hullo");
        apiTestPropertyEvent.sleepLittle();
        apiTestPropertyEvent.setValue("hollo");
        apiTestPropertyEvent.sleepLittle();
        apiTestPropertyEvent.setValue("hillo");
        apiTestPropertyEvent.sleepLittle();
    }

    private class MockProperty {

        private String value = new String();

        private List<PropertyListener> listeners = new ArrayList<>();

        public MockProperty(String value) {
            this.value = value;
        }

        public void setValue(String value) {
            this.value = value;
            callListeners();
        }

        public String getValue() {
            return value;
        }

        public void addPropertyListener(PropertyListener propertyListener) {
            listeners.add(propertyListener);
        }

        protected void callListeners() {
            PropertyEvent event = new PropertyEvent(this);
            for (PropertyListener listener : listeners) {
                listener.propertyChange(event);
            }
        }
    }

    private class MockListener implements PropertyListener {


        @Override
        public void propertyChange(PropertyEvent propertyEvent) {
            Property source = (Property) propertyEvent.getSource();
            System.out.printf("%s\n", (String)source.getValue());
        }
    }
}
