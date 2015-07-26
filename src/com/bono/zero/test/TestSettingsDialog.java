package com.bono.zero.test;

import com.bono.zero.SettingsInitializer;
import com.bono.zero.control.SettingsDialog;
import com.bono.zero.model.Settings;

/**
 * Created by hendriknieuwenhuis on 26/07/15.
 */
public class TestSettingsDialog {

    public static void main(String[] args) {
        Settings settings = new Settings();
        SettingsInitializer initializer = new SettingsInitializer(null, settings);
        SettingsDialog s = new SettingsDialog(initializer);

    }
}
