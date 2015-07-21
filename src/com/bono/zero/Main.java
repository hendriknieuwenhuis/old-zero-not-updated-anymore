package com.bono.zero;

import com.bono.zero.laf.ZeroTheme;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.util.Arrays;


/**
 * Created by hennihardliver on 21/05/14.
 */
public class Main {



    /**
     * Method setLook(), sets the look and feel of the program. This method
     * is called first so the look is set before the application is instantiated.
     */
    public static void setLook() {
        Color gray = Color.LIGHT_GRAY;
        Color darkGray = Color.DARK_GRAY;
        Color lGray = new Color(185, 185, 185);
        Color lGrayDarker = new Color(155, 155, 155);
        Border none = BorderFactory.createEmptyBorder();
        Border dark = BorderFactory.createLineBorder(Color.darkGray, 1);
        Border pink = BorderFactory.createLineBorder(Color.PINK, 1);
        java.util.List buttonGradient = Arrays.asList(new Object[]{new Float(.3f), new Float(0f),
                new ColorUIResource(0xDDE8F3), Color.WHITE, gray});

        MetalLookAndFeel.setCurrentTheme(new ZeroTheme());
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        UIManager.put("Panel.border", none);
        UIManager.put("SplitPane.dividerSize", 1);
        UIManager.put("SplitPane.border", none);
        //UIManager.put("Button.border", dark);
        UIManager.put("ScrollPane.border", dark);
        UIManager.put("ScrollPane.background", lGray);
        UIManager.put("ScrollBar.squareButtons", false);
        UIManager.put("ScrollBar.width", 15);
        UIManager.put("ScrollBar.track", lGray);
        UIManager.put("ScrollBar.thumb", lGrayDarker);
        UIManager.put("ScrollBar.border", none);
        UIManager.put("slider.trackBorder", none);
        UIManager.put("Tree.background", gray);
        UIManager.put("Table.background", gray);
        UIManager.put("MenuBar.gradient", buttonGradient);
    }

    /**
     * Method launch launches the application. First it will call setLook().
     */
    private void launch(String[] args) {

        Main.setLook();


        Launcher.launchApplication(args);

    }





    public static void main(String[] args) {
        Application.launch(args);
        //new Main().launch(args);
    }
}
