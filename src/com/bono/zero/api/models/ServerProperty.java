package com.bono.zero.api.models;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class ServerProperty {

    // the value of this property.
    private Object value;

    // the listener to this property.
    //private Listener listener;
    private ChangeListener changeListener;

    public ServerProperty() {}

    public Object getValue() {
        return value;
    }

    // when value is changed its set and
    // the listener is called.
    public void setValue(Object value) {

        if (!value.equals(this.value)) {
            this.value = value;

            if (changeListener != null) {
                changeListener.stateChanged(new ChangeEvent(this));
            }
        }
    }

    public ChangeListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public String toString() {
        return "ServerProperty{" +
                "value=" + value.toString() +
                '}';
    }
}
