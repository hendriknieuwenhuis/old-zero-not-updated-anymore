package com.bono.zero.laf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.io.Serializable;

import javax.swing.AbstractButton;

import javax.swing.plaf.UIResource;

public class BonoIconFactory implements Serializable {
	
	// icons for music player buttons
	private static BonoIcon nextButtonIcon;
	private static BonoIcon pauseButtonIcon;
	private static BonoIcon playButtonIcon;
	private static BonoIcon previousButtonIcon;
	private static BonoIcon stopButtonIcon;
	private static BonoIcon repeatIcon;
	private static BonoIcon randomIcon;
	
	// icon for jtree
	private static BonoTreeIcon fileIcon;
	private static BonoTreeIcon folderClosed;
	private static BonoTreeIcon folderOpen;
	
	
	
	
	// returns a next button icon
	public static BonoIcon getNextButtonIcon() {
		if (nextButtonIcon == null) {
			nextButtonIcon = new NextButtonIcon();
		}
		return nextButtonIcon;
	}
	
	// return a pause button icon
	public static BonoIcon getPauseButtonIcon() {
		if (pauseButtonIcon == null) {
			pauseButtonIcon = new PauseButtonIcon();
		}
		return pauseButtonIcon;
	}
	
	// returns a play button icon
	public static BonoIcon getPlayButtonIcon() {
		if (playButtonIcon == null) {
			playButtonIcon = new PlayButtonIcon();
		}
		return playButtonIcon;
	}
	
	// returns a previous button icon
	public static BonoIcon getPreviousButtonIcon() {
		if (previousButtonIcon == null) {
			previousButtonIcon = new PreviousButtonIcon();
		}
		return previousButtonIcon;
	}
	
	// returns a stop button icon
	public static BonoIcon getStopButtonIcon() {
		if (stopButtonIcon == null) {
			stopButtonIcon = new StopButtonIcon();
		}
		return stopButtonIcon;
	}
	
	public static BonoIcon getRepeatIcon() {
		if (repeatIcon == null) {
			repeatIcon = new RepeatIcon();
		}
		return repeatIcon;
	}
	
	public static BonoIcon getRandomIcon() {
		if (randomIcon == null) {
			randomIcon = new RandomIcon();
		}
		return randomIcon;
	}
	
	// returns a file icon
	public static BonoTreeIcon getFileIcon() {
		if (fileIcon == null) {
			fileIcon = new FileIcon();
		}
		return fileIcon;
	}
	
	// return a folderclosed icon
	public static BonoTreeIcon getFolderClosed() {
		if (folderClosed == null) {
			folderClosed = new FolderClosed();
		}
		return folderClosed;
	}
	
	
	
	
	/**
	 * Defines a next button icon
	 * @author bono
	 *
	 */
	private static class NextButtonIcon implements BonoIcon, UIResource, Serializable {
		
		private int width  = 22;
		private int height = 22;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			Graphics2D g2d = (Graphics2D) g;
			
			int pointx1 = (int) ((c.getWidth()/2)-(this.getIconWidth()/2)*0.75); //0.75
			int pointx2 = (int) (c.getWidth()/2)-(this.getIconWidth()/8);
			int pointx3 = (int) (c.getWidth()/2)-(this.getIconWidth()/8);
			int pointx4 = (int) ((c.getWidth()/2)+(this.getIconWidth()/2)*1.17);  //1.17
			int pointx5 = (int) (c.getWidth()/2)-(this.getIconWidth()/8);
			int pointx6 = (int) (c.getWidth()/2)-(this.getIconWidth()/8);
			int pointx7 = (int) ((c.getWidth()/2)-(this.getIconWidth()/2)*0.75);
			int pointy1 = (int) (c.getHeight()/2)-(this.getIconHeight()/2);
			int pointy2 = (int) (c.getHeight()/2)-(this.getIconHeight()/4);
			int pointy3 = (int) (c.getHeight()/2)-(this.getIconHeight()/2);
			int pointy4 = (int) (c.getHeight()/2);
			int pointy5 = (int) (c.getHeight()/2)+(this.getIconHeight()/2);
			int pointy6 = (int) (c.getHeight()/2)+(this.getIconHeight()/4);
			int pointy7 = (int) (c.getHeight()/2)+(this.getIconHeight()/2);
			
			int[] pointsx = {pointx1, pointx2, pointx3, pointx4, pointx5, pointx6, pointx7};
			int[] pointsy = {pointy1, pointy2, pointy3, pointy4, pointy5, pointy6, pointy7};
			
			Polygon triangle = new Polygon(pointsx, pointsy, 7);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(this.getUnpressedColor());
			g2d.fillPolygon(triangle);
			
			if (model.getModel().isPressed()) {
				g2d.setColor(this.getPressedColor());
				g2d.fillPolygon(triangle);
			}
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
		}

		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			uc = newColor;
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			pc = newColor;
		}
		
	}
	
	/**
	 * Defines a pause button icon
	 * @author bono
	 *
	 */
	private static class PauseButtonIcon implements BonoIcon, UIResource, Serializable {
		
		private int width  = 22;
		private int height = 22;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			
			int x1 = (int) ((c.getWidth()/2)-(this.getIconWidth()/2));
			int y1 = (int) ((c.getHeight()/2)-(this.getIconHeight()/2));
			
			int x2 = (int) ((c.getWidth()/2)+((this.getIconWidth()/2)-(this.getIconWidth()/3)));
			int y2 = (int) ((c.getHeight()/2)-(this.getIconHeight()/2));
			
			g.setColor(getUnpressedColor());
			g.fillRect(x1, y1 , (this.getIconWidth()/3), this.getIconHeight());
			g.fillRect(x2, y2 , (this.getIconWidth()/3), this.getIconHeight());
			if (model.getModel().isPressed()) {
				g.setColor(getPressedColor());
				g.fillRect(x1, y1 , (this.getIconWidth()/3), this.getIconHeight());
				g.fillRect(x2, y2 , (this.getIconWidth()/3), this.getIconHeight());
			}
		}

		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			uc = newColor;
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			pc = newColor;
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}
		
	}
	
	/**
	 * Defines an icon for a play button.
	 * @author bono
	 *
	 */
	private static class PlayButtonIcon implements BonoIcon, UIResource, Serializable {
		
		private int width  = 22;
		private int height = 22;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;
		

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			Graphics2D g2d = (Graphics2D) g;
			
			int pointx1 = (int) ((c.getWidth()/2)-(this.getIconWidth()/2)*0.75); //0.75
			int pointx2 = (int) ((c.getWidth()/2)+(this.getIconWidth()/2)*1.17);  //1.17
			int pointx3 = pointx1;
			int pointy1 = (int) (c.getHeight()/2)-(this.getIconHeight()/2);
			int pointy2 = (int) (c.getHeight()/2);
			int pointy3 = (int) (c.getHeight()/2)+(this.getIconHeight()/2);
			
			int[] pointsx = {pointx1, pointx2, pointx3};
			int[] pointsy = {pointy1, pointy2, pointy3};
			
			Polygon triangle = new Polygon(pointsx, pointsy, 3);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(this.getUnpressedColor());
			g2d.fillPolygon(triangle);
			
			if (model.getModel().isPressed()) {
				g2d.setColor(this.getPressedColor());
				g2d.fillPolygon(triangle);
			}
		}
		
		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			uc = newColor;
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			pc = newColor;
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}
	}
	
	/**
	 * Defines a previous button icon.
	 * @author bono
	 *
	 */
	private static class PreviousButtonIcon implements BonoIcon, UIResource, Serializable {
		
		private int width  = 22;
		private int height = 22;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			Graphics2D g2d = (Graphics2D) g;
			
			int pointx1 = (int) ((c.getWidth()/2)+(this.getIconWidth()/2)*0.75); //0.75
			int pointx2 = (int) (c.getWidth()/2)+(this.getIconWidth()/8);
			int pointx3 = (int) (c.getWidth()/2)+(this.getIconWidth()/8);
			int pointx4 = (int) ((c.getWidth()/2)-(this.getIconWidth()/2)*1.17);  //1.17
			int pointx5 = (int) (c.getWidth()/2)+(this.getIconWidth()/8);
			int pointx6 = (int) (c.getWidth()/2)+(this.getIconWidth()/8);
			int pointx7 = (int) ((c.getWidth()/2)+(this.getIconWidth()/2)*0.75);
			int pointy1 = (int) (c.getHeight()/2)-(this.getIconHeight()/2);
			int pointy2 = (int) (c.getHeight()/2)-(this.getIconHeight()/4);
			int pointy3 = (int) (c.getHeight()/2)-(this.getIconHeight()/2);
			int pointy4 = (int) (c.getHeight()/2);
			int pointy5 = (int) (c.getHeight()/2)+(this.getIconHeight()/2);
			int pointy6 = (int) (c.getHeight()/2)+(this.getIconHeight()/4);
			int pointy7 = (int) (c.getHeight()/2)+(this.getIconHeight()/2);
			
			int[] pointsx = {pointx1, pointx2, pointx3, pointx4, pointx5, pointx6, pointx7};
			int[] pointsy = {pointy1, pointy2, pointy3, pointy4, pointy5, pointy6, pointy7};
			
			Polygon triangle = new Polygon(pointsx, pointsy, 7);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(this.getUnpressedColor());
			g2d.fillPolygon(triangle);
			
			if (model.getModel().isPressed()) {
				g2d.setColor(this.getPressedColor());
				g2d.fillPolygon(triangle);
			}
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
			
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
		}

		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			uc = newColor;
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			pc = newColor;
		}
		
	}
	
	/**
	 * Defines an icon for an stop button.
	 * @author bono
	 *
	 */
	private static class StopButtonIcon implements UIResource, Serializable, BonoIcon {
		
		private int width  = 22;
		private int height = 22;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			
			int xx = (int) ((c.getWidth()/2)-(this.getIconWidth()/2));
			int yy = (int) ((c.getHeight()/2)-(this.getIconHeight()/2));
			g.setColor(getUnpressedColor());
			g.fillRect(xx, yy , width, height);
			if (model.getModel().isPressed()) {
				g.setColor(getPressedColor());
				g.fillRect(xx, yy , width, height);
				
			}
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			uc = newColor;
			
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			pc = newColor;
			
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
			
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
			
		}
		
	}
	
	private static class RepeatIcon implements UIResource, Serializable, BonoIcon {

		private int width  = 10;
		private int height = 10;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			Graphics2D g2d = (Graphics2D) g;
			
			int w = c.getWidth()/2;
			int h = c.getHeight()/2;
			
			int[] pointsx = {(w-4), (w-1), (w-2), (w-1), (w-3), (w-3), (w+3), (w+3), (w+1), w, (w+1), (w+4), (w+5), (w+5), (w+4), (w-4), (w-5), (w-5)};
			int[] pointsy = {(h-5), (h-5), (h-4), (h-3), (h-3), (h+3), (h+3), (h-3), (h-3), (h-4), (h-5), (h-5), (h-4), (h+4), (h+5), (h+5), (h+4), (h-4)};
					
					
			Polygon triangle = new Polygon(pointsx, pointsy, 18);
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(this.getUnpressedColor());
			g2d.fillPolygon(triangle);
			
			if (model.getModel().isPressed()) {
				g2d.setColor(this.getPressedColor());
				g2d.fillPolygon(triangle);
			}
		}

		@Override
		public int getIconWidth() {
			return 10;
		}

		@Override
		public void setIconWidth(int width) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getIconHeight() {
			return 10;
		}

		@Override
		public void setIconHeight(int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static class RandomIcon implements UIResource, Serializable, BonoIcon {

		private int width  = 10;
		private int height = 10;
		private Color uc = (Color) Color.DARK_GRAY;
		private Color pc = (Color) Color.BLACK;
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			AbstractButton model = (AbstractButton) c;
			Graphics2D g2d = (Graphics2D) g;
			
			int w = c.getWidth()/2;
			int h = c.getHeight()/2;
			
			int[] points1x = {(w-5), (w-2), (w+2), (w+5), (w+5), (w+2), (w-2), (w-5)};
			int[] points1y = {(h-5), (h-5), (h+3), (h+3), (h+5), (h+5), (h-3), (h-3)};
					
			int[] points2x = {(w+5), (w+2), (w-2), (w-5), (w-5), (w-2), (w+2), (w+5)};
			int[] points2y = {(h-5), (h-5), (h+3), (h+3), (h+5), (h+5), (h-3), (h-3)};
			
			Polygon random1 = new Polygon(points1x, points1y, 8);
			Polygon random2 = new Polygon(points2x, points2y, 8);
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(this.getUnpressedColor());
			g2d.fillPolygon(random1);
			g2d.fillPolygon(random2);
			
			if (model.getModel().isPressed()) {
				g2d.setColor(this.getPressedColor());
				g2d.fillPolygon(random1);
				g2d.fillPolygon(random2);
			}
		}

		@Override
		public int getIconWidth() {
			return 10;
		}

		@Override
		public void setIconWidth(int width) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getIconHeight() {
			return 10;
		}

		@Override
		public void setIconHeight(int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getUnpressedColor() {
			return uc;
		}

		@Override
		public void setUnpressedColor(Color newColor) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getPressedColor() {
			return pc;
		}

		@Override
		public void setPressedColor(Color newColor) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * Defines a file icon for a tree.
	 * @author bono
	 *
	 */
	private static class FileIcon implements BonoTreeIcon, UIResource, Serializable {
		
		private int width = 0;
		private int height = 0;
		
		private Color outline = (Color) Color.MAGENTA.brighter();
		private Color filled = (Color) Color.GREEN.brighter();

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			Graphics2D g2d = (Graphics2D) g;
			
			int pointx1 = (int) ((int) (width/10));
			int pointy1 = 1 + (int) height - height;
			int pointx2 = (int) ((int)(width/10));
			int pointy2 = (int) height - 1;
			int pointx3 = (int) ((int) width*0.8);
			int pointy3 = (int) height - 1;
			int pointx4 = (int) ((int) width*0.8);
			int pointy4 = (int) ((int) height*0.3);
			int pointx5 = (int) ((int) (width*0.625));
			int pointy5 = 1 + (int) height - height;
			
			
			
			int[] psX = {pointx1, pointx2, pointx3, pointx4, pointx5};
			int[] psY = {pointy1, pointy2, pointy3, pointy4, pointy5};
			
			Polygon file = new Polygon(psX, psY, 5);
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(getLineColor());
			g2d.drawPolygon(file);
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
		}
		
		@Override
		public Color getLineColor() {
			return outline;
		}

		@Override
		public void setLineColor(Color newColor) {
			outline = newColor;
			
		}

		@Override
		public Color getFillColor() {
			return filled;
		}

		@Override
		public void setFillColor(Color newColor) {
			filled = newColor;
		}
		
	}
	
	
	
	/**
	 * Defines a folder (closed) icon.
	 * @author bono
	 *
	 */
	private static class FolderClosed implements BonoTreeIcon, UIResource, Serializable {
		
		private int width = 0;
		private int height = 0;
		private Color outline = (Color) Color.orange;
		private Color filled = (Color) Color.pink;

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			
			Graphics2D g2d = (Graphics2D) g;
			
			int pointx1 = (int) width/10; //
			int pointy1 = (int) ((int)(height*0.2)); //height/10;
			int pointx2 = (int) width/10;
			int pointy2 = (int) ((int)(height*0.8));
			int pointx3 = (int) ((int)(width*0.8));
			int pointy3 = (int) ((int)(height*0.8));
			int pointx4 = (int) ((int)(width*0.8));
		    int pointy4 = (int) height/10; //((int)(height*0.3));
		    int pointx5 = (int) ((int)(width*0.5)); //((int)(width*0.4));
		    int pointy5 = (int) height/10; //((int)(height*0.3));
		    int pointx6 = (int) ((int)(width*0.5));
		    int pointy6 = (int) ((int)(height*0.2));  // height/10;
		    
		    int[] psX = {pointx1, pointx2, pointx3, pointx4, pointx5, pointx6};
		    int[] psY = {pointy1, pointy2, pointy3, pointy4, pointy5, pointy6};
		    
		    Polygon folder = new Polygon(psX, psY, 6);
		    
		   
		    g2d.setColor(getFillColor());
		    g2d.fillPolygon(folder);
		    g2d.setColor(getLineColor());
		    g2d.drawPolygon(folder);
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public void setIconWidth(int width) {
			this.width = width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public void setIconHeight(int height) {
			this.height = height;
		}

		@Override
		public Color getLineColor() {
			return outline;
		}

		@Override
		public void setLineColor(Color newColor) {
			outline = newColor;
		}

		@Override
		public Color getFillColor() {
			return filled;
		}

		@Override
		public void setFillColor(Color newColor) {
			filled = newColor;
		}
		
	}
	
	
	private static class FolderOpen implements BonoTreeIcon, UIResource, Serializable {
		
		private int width = 0;
		private int height = 0;
		private Color outline;
		private Color filled;

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getIconWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setIconWidth(int width) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getIconHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setIconHeight(int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getLineColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setLineColor(Color newColor) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Color getFillColor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setFillColor(Color newColor) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
