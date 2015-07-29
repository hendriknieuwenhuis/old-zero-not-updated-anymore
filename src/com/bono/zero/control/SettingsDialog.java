package com.bono.zero.control;

import com.bono.zero.SettingsInitializer;
import com.bono.zero.view.SettingsDialogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by hendriknieuwenhuis on 26/07/15.
 */
public class SettingsDialog extends WindowAdapter implements ActionListener {

    // the view of the settings dialog
    private SettingsDialogView view;

    // the initializer for the settings.
    private SettingsInitializer settingsInitializer;

    public SettingsDialog(SettingsInitializer settingsInitializer) {
        this.settingsInitializer = settingsInitializer;
        view = new SettingsDialogView();
        view.getOkButton().addActionListener(this);
        view.getView().addWindowListener(this);
        view.getView().setVisible(true);
    }

    /*
    The window adapter method ends the program
    when the window is closed.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        view.getView().dispose();
        System.exit(0);
    }

    /*
    The Listener method for the OK button in the SettingsDialogView.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        settingsInitializer.getSettings().setHost(view.getHostField().getText());
        if (!view.getPortField().getText().equals("")) {
            settingsInitializer.getSettings().setPort(Integer.parseInt(view.getPortField().getText()));
        }
        settingsInitializer.getSettings().setPassword(view.getPasswordField().getPassword().toString());
        settingsInitializer.getSettings().setUser(view.getUserField().getText());

        settingsInitializer.continueThread();

        view.getView().dispose();
    }

}
