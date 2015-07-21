package com.bono.zero.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by hennihardliver on 23/05/14.
 */
public class SplashScreen extends JDialog {

    private static SplashScreen splashScreen;

    private SplashScreen(Rectangle rectangle) {
        super((Frame) null);
        setUndecorated(true);
        JLabel message = new JLabel("Loading application");
        add(message);
        setSize(200, 200);
        setLocation((rectangle.width/2)-(getWidth()/2), (rectangle.height/2)-(getHeight()/2));
        setVisible(true);
    }

    public static SplashScreen getSplashScreen(Rectangle rectangle) {
        if (splashScreen == null) {
            splashScreen = new SplashScreen(rectangle);
        }
       return splashScreen;
    }

    public static void close() {
        splashScreen.setVisible(false);
        splashScreen.dispose();
    }

}
