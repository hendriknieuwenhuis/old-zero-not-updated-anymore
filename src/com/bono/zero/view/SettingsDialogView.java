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

public class SettingsDialogView extends WindowAdapter {

	private JDialog window;
	
	private JTextField hostField;
	private JTextField portField;
	private JPasswordField passwordField;
	private JTextField userField;
	
	private JButton okButton;


	public SettingsDialogView() {
		super();
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
		
		okButton = new JButton("OK");

		window.setLayout(new GridLayout(5, 2));
		window.add(hostLabel);
		window.add(hostField);
		window.add(portLabel);
		window.add(portField);
		window.add(passwordLabel);
		window.add(passwordField);
		window.add(userLabel);
		window.add(userField);
		window.add(okButton);

		window.setAlwaysOnTop(true);
		window.pack();

		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		window.setLocation((bounds.width / 2) - (window.getWidth() / 2), (bounds.height / 2) - (window.getHeight() / 2));

		window.addWindowListener(this);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		window.dispose();
		System.exit(0);
	}

	public JDialog getView() {
		return window;
	}

	public JTextField getHostField() {
		return hostField;
	}

	public JTextField getPortField() {
		return portField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JTextField getUserField() {
		return userField;
	}

	public JButton getOkButton() {
		return okButton;
	}

}
