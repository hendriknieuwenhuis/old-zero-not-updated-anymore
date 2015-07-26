package com.bono.zero.control;

import com.bono.zero.SettingsInitializer;
import com.bono.zero.view.SettingsDialogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hendriknieuwenhuis on 26/07/15.
 */
public class SettingsDialog {

    // the view of the settings dialog
    private SettingsDialogView view;

    // the initializer for the settings.
    private SettingsInitializer settingsInitializer;

    public SettingsDialog(SettingsInitializer settingsInitializer) {
        this.settingsInitializer = settingsInitializer;
        view = new SettingsDialogView();
        view.getOkButton().addActionListener(new ButtonListener());
        view.getView().setVisible(true);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            settingsInitializer.getSettings().setHost(view.getHostField().getText());
            if (!view.getPortField().getText().equals("")) {
                settingsInitializer.getSettings().setPort(Integer.parseInt(view.getPortField().getText()));
            }
            settingsInitializer.getSettings().setPassword(view.getPasswordField().getPassword().toString());
            settingsInitializer.getSettings().setUser(view.getUserField().getText());

            settingsInitializer.doNotify();

            view.getView().dispose();
        }
    }
}
