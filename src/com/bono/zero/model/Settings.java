package com.bono.zero.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;

public class Settings implements Serializable {

    public static final int STANDARD_PORT = 6600;

	private String host;
	private int port;
	private String user;
	private String password;
	private InetSocketAddress address;

	private static Settings settings;

	private Settings() {}
	
	// creates and gives back an instance of this class.
	public static Settings getSettings() {
		
		if (settings == null) {
			settings = new Settings();
		}
		return settings;
	}


	/**
	 * Method loads the saved instance variables of this object (file: settings.set)
	 * and returns them as an Settings object. When the saved settings are not
	 * found or can not be loaded a ClassNotFoundException is thrown.
	 * 
	 * @return instance of Settings
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Settings loadSettings() throws ClassNotFoundException, IOException {
		try {
			FileInputStream file = new FileInputStream("settings.set");
			ObjectInputStream objectIn = new ObjectInputStream(file);
			Object settingsFile = objectIn.readObject();
			settings = (Settings) settingsFile;
			objectIn.close();
		
		} catch (ClassNotFoundException c) {
			throw new ClassNotFoundException("No file!");
		} catch (IOException e) {
			throw new IOException();
		}
		return settings;
	}
	
	public void saveSettings() throws IOException {
		try {
			FileOutputStream file = new FileOutputStream("settings.set");
			ObjectOutputStream objectOut = new ObjectOutputStream(file);
			objectOut.writeObject(Settings.settings);
			objectOut.close();
		} catch (IOException e) {
			throw e;
		}
		
	}

	// creates and gives back an instance of InetSocketAddress
	// with the values of host and port, when they are not
	// null. If they are null, null will be returned.
	public InetSocketAddress getAddress() {
		
		if ((host != null) && (port != 0)) {
			try {
				address = new InetSocketAddress(host, port);
			} catch (IllegalArgumentException i) {
				i.printStackTrace();
			} catch (SecurityException s) {
				s.printStackTrace();
			}
			return address;
		}
		return null;		
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
