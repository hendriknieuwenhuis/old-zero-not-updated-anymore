package com.bono.zero;

import com.bono.zero.model.Settings;

import java.io.*;

/**
 * Created by hendriknieuwenhuis on 22/07/15.
 */
public class SettingsLoader {

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
        Settings settings;
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

    public static void saveSettings(Settings settings) throws IOException {
        try {
            FileOutputStream file = new FileOutputStream("settings.set");
            ObjectOutputStream objectOut = new ObjectOutputStream(file);
            objectOut.writeObject(settings);
            objectOut.close();
        } catch (IOException e) {
            throw e;
        }

    }
}
