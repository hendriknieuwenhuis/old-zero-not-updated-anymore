package com.bono.zero.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bono.zero.Launcher;
import com.bono.zero.model.Settings;

public class SettingsDialog extends JDialog {
	
	private JTextField hostField;
	private JTextField portField;
	private JPasswordField passwordField;
	private JTextField userField;
	
	private JButton button;	
	
	private Settings settings;

    public SettingsDialog(Settings settings) {

		this.settings = settings;
		init();
	}
	
	private void init() {
		JLabel hostLabel = new JLabel("host:");
		hostLabel.setHorizontalAlignment(JLabel.RIGHT);
		JLabel portLabel = new JLabel("port:");
		portLabel.setHorizontalAlignment(JLabel.RIGHT);
		JLabel passwordLabel = new JLabel("password:");
		passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
		JLabel userLabel = new JLabel("user:");
		userLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		hostField = new JTextField();
		portField = new JTextField("6600");
		passwordField = new JPasswordField();
		userField = new JTextField();
		
		button = new JButton("OK");
		button.addActionListener(new ButtonListener());
		
		
		setLayout(new GridLayout(5,2));
		add(hostLabel);
		add(hostField);
		add(portLabel);
		add(portField);
		add(passwordLabel);
		add(passwordField);
		add(userLabel);
		add(userField);
		add(button);
		
		setAlwaysOnTop(true);
		pack();
		setVisible(true);
	}


	
	private void closeDialog() {
		this.dispose();
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			settings.setHost(hostField.getText());
			if (!portField.getText().equals("")) {
				settings.setPort(Integer.parseInt(portField.getText()));
			}
			settings.setPassword(passwordField.getPassword().toString());
			settings.setUser(userField.getText());

            Launcher.doNotify();
            //go(run);
			closeDialog();
		}
		
	}

}
