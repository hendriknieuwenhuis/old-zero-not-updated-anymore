package com.bono.zero.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bono.zero.Launcher;
import com.bono.zero.SettingsInitializer;
import com.bono.zero.model.Settings;

public class SettingsDialog extends WindowAdapter {

	private JDialog window;
	
	private JTextField hostField;
	private JTextField portField;
	private JPasswordField passwordField;
	private JTextField userField;
	
	private JButton button;	
	
	private Settings settings;

	private Rectangle bounds;

	private SettingsInitializer settingsInitializer;

    public SettingsDialog(SettingsInitializer settingsInitializer) {
		super();
		this.settingsInitializer = settingsInitializer;
		settings = settingsInitializer.getSettings();
		bounds = settingsInitializer.getBounds();
		init();
	}
	
	private void init() {

		window = new JDialog();

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
		
		
		window.setLayout(new GridLayout(5, 2));
		window.add(hostLabel);
		window.add(hostField);
		window.add(portLabel);
		window.add(portField);
		window.add(passwordLabel);
		window.add(passwordField);
		window.add(userLabel);
		window.add(userField);
		window.add(button);

		window.setAlwaysOnTop(true);
		window.pack();
		window.setLocation((bounds.width / 2) - (window.getWidth() / 2), (bounds.height / 2) - (window.getHeight() / 2));
		window.setVisible(true);

		window.addWindowListener(this);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		window.dispose();
		System.exit(0);
	}


	
	private void closeDialog() {
		window.dispose();
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

            settingsInitializer.doNotify();

			closeDialog();
		}
		
	}

}
