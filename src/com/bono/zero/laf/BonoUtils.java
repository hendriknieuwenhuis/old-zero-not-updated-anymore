package com.bono.zero.laf;

import javax.swing.*;
import java.util.List;
import java.awt.*;

/**
 * Created by hennihardliver on 25/05/14.
 */
public class BonoUtils {

    public static void paintGradient(Graphics g, Component c, List gradient, int x, int y, int w, int h, boolean vertical) {



        int width;
        int height;

        if (vertical) {
            width = 64;
            height = h;
        } else {
            width = w;
            height = 64;
        }
        synchronized (c.getTreeLock()) {
            paintGradient(g, c, gradient, x, y, width, height, vertical);
        }

    }
}
