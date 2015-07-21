package com.bono.zero.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public interface BonoTreeIcon extends Icon {
	
	void paintIcon(Component c, Graphics g, int x, int y);
	
	int getIconWidth();
	
	void setIconWidth(int width);
	
	int getIconHeight();
	
	void setIconHeight(int height);
	
	Color getLineColor(); 
	
	void setLineColor(Color newColor);
	
	Color getFillColor();
	
	void setFillColor(Color newColor);

}
