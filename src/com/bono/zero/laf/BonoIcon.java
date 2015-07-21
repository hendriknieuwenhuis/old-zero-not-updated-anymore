package com.bono.zero.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public interface BonoIcon extends Icon {
	
	void paintIcon(Component c, Graphics g, int x, int y);
	
	int getIconWidth();
	
	void setIconWidth(int width);
	
	int getIconHeight();
	
	void setIconHeight(int height);
	
	Color getUnpressedColor(); 
	
	void setUnpressedColor(Color newColor);
	
	Color getPressedColor();
	
	void setPressedColor(Color newColor);

}
