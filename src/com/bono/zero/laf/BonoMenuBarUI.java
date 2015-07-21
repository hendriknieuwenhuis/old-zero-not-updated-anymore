package com.bono.zero.laf;


import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;

import java.util.List;
import java.awt.Graphics;

/**
 * Created by hennihardliver on 25/05/14.
 */
public class BonoMenuBarUI extends BasicMenuBarUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    public void update(Graphics g, JComponent c) {
        boolean isOpaque = c.isOpaque();

        List gradient = (List) UIManager.get("MenuBar.gradient");

        //BonoUtils.paintGradient(g, c, gradient, 0, 0, c.getWidth(), c.getHeight(), true);
        //MetalUtils
        paint(g, c);
    }
}
