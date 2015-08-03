package com.bono.zero.api.models;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class ServerProperty {

    // the value of this property.
    private Object value;

    // the listeners to this property.
    //private Listener listener;
    private List<ChangeListener> changeListeners;

    public ServerProperty() {}

    public Object getValue() {
        return value;
    }

    // when value is changed its set and
    // the listener is called.
    public void setValue(Object value) {

        if (!value.equals(this.value)) {
            this.value = value;

            invokeListeners();
        }
    }

    private void invokeListeners() {
        if (changeListeners != null) {
            for (ChangeListener listener : changeListeners) {
                listener.stateChanged(new ChangeEvent(this));
            }
        }
    }

    public List<ChangeListener> getChangeListener() {
        return changeListeners;
    }

    public void setChangeListener(ChangeListener changeListener) {
        if (changeListeners == null) {
            changeListeners = new ArrayList<>();
        }
        changeListeners.add(changeListener);
    }

    @Override
    public String toString() {
        return "ServerProperty{" +
                "value=" + value.toString() +
                '}';
    }
}
