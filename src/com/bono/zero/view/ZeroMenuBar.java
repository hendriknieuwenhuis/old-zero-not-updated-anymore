package com.bono.zero.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bono.zero.util.Observer;

import javax.swing.*;

import com.bono.zero.control.Server;
import com.bono.zero.model.ServerStatus;

public class ZeroMenuBar extends JMenuBar implements Observer {
	
	private JMenu config;
	private JCheckBoxMenuItem consume;
	private JCheckBoxMenuItem single;
    private List<JCheckBoxMenuItem> checkBoxList;

	public ZeroMenuBar() {
        super();
        checkBoxList = new ArrayList<JCheckBoxMenuItem>(4);
		makeBar();
	}
	
	private void makeBar() {
		add(configMenu());
	}
	
	private JMenu configMenu() {
		config = new JMenu("config");
		consume = new JCheckBoxMenuItem("consume");
		consume.setActionCommand(Server.CONSUME);
		checkBoxList.add(consume);
		single = new JCheckBoxMenuItem("single");
		single.setActionCommand(Server.SINGLE);
		checkBoxList.add(single);
		config.add(consume);
		config.add(single);
		return config;
	}

    public void addCheckBoxListeners(ActionListener listener) {
        Iterator i = checkBoxList.iterator();
        while (i.hasNext()) {
            ((JCheckBoxMenuItem)i.next()).addActionListener(listener);
        }
    }
	
	/*****************************************************
	 *Updates the state of the JCheckBoxMenuItems when an*
	 *update occurs in the ServerStatus.                 *
	 *String update tells the method witch button is to  *
	 *be updated. Although the buttons also update       *
	 *their state when the user interacts with them,     *
	 *to display the MPDserver state the buttons         *
	 *represent, the state must be set from the          *
	 *server finally.                                    *
	 *****************************************************/
	@Override
	public void update(String update, Object arg) {
		if (update.equals(ServerStatus.CONSUME)) {
			String state = (String) arg;
			if (state.equals("0")) {
				consume.setSelected(false);
			} else {
				consume.setSelected(true);
			}
		}else if (update.equals(ServerStatus.SINGLE)) {
			String state = (String) arg;
			if (state.equals("0")) {
				single.setSelected(false);
			} else {
				single.setSelected(true);
			}
		}
			
	}

}
