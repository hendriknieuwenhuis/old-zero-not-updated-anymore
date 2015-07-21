package com.bono.zero.view;

import java.awt.*;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * <p>Title: ZeroFrame.java</p>
 *
 * <p>Description: Class ZeroFrame.java is the JFrame of the GUI that
 * holds the components of the GUI.
 * It is initialised with a JPanel with a BorderLayout and an
 * EmptyBorder set (5,5,5,5).
 * A windows listener can be set and a JMenuBar can be  added.</p>
 */
public class ZeroFrame extends JFrame {
	
	private JPanel mainPanel;      // holds the components of the frame

    public ZeroFrame() {
        super();
        setTitle("Zero");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));
    }


    public void setWindowListener(WindowListener l) {
        this.addWindowListener(l);
    }

    public void addMenuBar(JMenuBar menuBar) {
        setJMenuBar(menuBar);
    }

    /**
     * Add a panel to the JFrame at the given index.
     * @param component a JPanel
     * @param location the location in the BorderLayout.
     */
    public void addPanel(Component component, String location) {
        mainPanel.add(component, location);

    }

    /**
     * Adds the <code>MainPanel</code>, makes the GUI visible and sets the size.
     * @param dimension
     * @param visible
     */
    public void showFrame(Dimension dimension, boolean visible) {
        getContentPane().add(mainPanel);
        setSize(dimension);
        setVisible(visible);
    }
	

}
