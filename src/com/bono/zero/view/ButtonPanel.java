package com.bono.zero.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class ButtonPanel extends JPanel {
	
	protected JButton button;
	protected JToggleButton toggleButton;
	private HashMap<String, Object> buttonMap = new HashMap<String, Object>(6);
    private Insets insets = new Insets(5, 5, 5, 5);
	
	public ButtonPanel(LayoutManager layout) {
		super(layout);
	}
	
	protected void addButton(Icon icon, String key, String actionCommand, ActionListener listener) {
		Insets insets = new Insets(10,10,10,10);
        button = new JButton(icon);
        button.setMargin(insets);
        button.setActionCommand(actionCommand);
		button.addActionListener(listener);
		buttonMap.put(key, button);
		add(button);
	}

    protected void addButton(Icon icon, String actionCommand) {
        //Insets insets = new Insets(10, 10, 10, 10);
        button = new JButton(icon);
        button.setMargin(insets);
        button.setActionCommand(actionCommand);
        buttonMap.put(actionCommand, button);
        add(button);
    }

    protected void addToggleButton(Icon icon, String actionCommand) {
        toggleButton = new JToggleButton(icon);
        toggleButton.setMargin(insets);
        toggleButton.setActionCommand(actionCommand);
        buttonMap.put(actionCommand, toggleButton);
        add(toggleButton);
    }
	
	protected void addToggleButton(Icon icon, String actionCommand, ActionListener listener) {
		Insets insets = new Insets(5,5,5,5);
		toggleButton = new JToggleButton(icon);
		toggleButton.setMargin(insets);
		toggleButton.setActionCommand(actionCommand);
		toggleButton.addActionListener(listener);
		
		buttonMap.put(actionCommand, toggleButton);
		add(toggleButton);
	}
	
	protected void addSeperator(int constants) {
		JSeparator seperate = new JSeparator(constants);
		Dimension size =new Dimension(seperate.getPreferredSize().width +2, this.getPreferredSize().height);
		seperate.setPreferredSize(size);
		add(seperate);
	}

    public void addButtonActionListener(String key, ActionListener listener) {
        ((JButton)getButton(key)).addActionListener(listener);
    }

    public void addAllButtonActionListener(ActionListener listener) {
        Set<String> keys = buttonMap.keySet();
        Iterator i = keys.iterator();
        while (i.hasNext()) {
            ((AbstractButton)buttonMap.get(i.next())).addActionListener(listener);
        }
    }
	
	protected Object getButton(String key) {
		return buttonMap.get(key);
	}
		
}
