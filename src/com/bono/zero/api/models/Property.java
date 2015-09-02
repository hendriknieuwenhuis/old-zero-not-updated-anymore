package com.bono.zero.api.models;

import com.bono.zero.api.events.PropertyEvent;
import com.bono.zero.api.events.PropertyListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class Property {

    // the name of the proprty.
    private String name;

    // the value of this property.
    private Object value;

    // the listeners to this property.
    //private Listener listener;
    @Deprecated
    private List<PropertyChangeListener> listeners;

    private List<PropertyListener> propertyListeners;

    public Property() {}

    public Property(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;

    }

    // when value is changed its set and
    // the listener is called.
    public void setValue(Object value) {

        //System.out.println(name + " " + (String) value);
        // listener must always be invoked
        // when value is set!!!!!!!!!!!!!
        //
        // In the object that reads the value
        // it can judged on being changed!
        //

        Object oldValue = this.value;
        this.value = value;
        invokeListeners(oldValue);
        callListeners();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Deprecated
    private void invokeListeners(Object oldValue) {
        if (listeners != null) {
            for (PropertyChangeListener listener : listeners) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listener.propertyChange(new PropertyChangeEvent(this, name, oldValue, value));
                    }
                }).start();

            }
        }
    }

    private void callListeners() {
        if (propertyListeners != null) {
            for (PropertyListener listener : propertyListeners) {
                new Thread(() -> {
                    listener.propertyChange(new PropertyEvent(this));
                }).start();
            }
        }
    }


    @Deprecated
    public List<PropertyChangeListener> getChangeListener() {
        return listeners;
    }


    @Deprecated
    public void setPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(propertyChangeListener);
    }

    public void setPropertyListeners(PropertyListener propertyListener) {
        if (propertyListeners == null) {
            propertyListeners = new ArrayList<>();
        }
        propertyListeners.add(propertyListener);
    }

    @Override
    public String toString() {
        return "ServerProperty{" +
                "value=" + value.toString() +
                '}';
    }
}
