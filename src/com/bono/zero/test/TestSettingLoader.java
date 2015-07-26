package com.bono.zero.test;

import com.bono.zero.SettingsLoader;
import com.bono.zero.model.Settings;

import java.io.FileNotFoundException;

/**
 * Created by hendriknieuwenhuis on 26/07/15.
 */
public class TestSettingLoader {

    public static void main(String[] args) {
        try {
            Settings settings = SettingsLoader.loadSettings();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
