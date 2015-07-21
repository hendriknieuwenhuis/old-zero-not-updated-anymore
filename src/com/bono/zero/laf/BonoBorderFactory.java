package com.bono.zero.laf;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

/**
 * Created by hennihardliver on 24/05/14.
 */
public class BonoBorderFactory {

    public final int TOP = 0;
    public final int RIGHT = 1;
    public final int BOTTOM = 2;
    public final int LEFT = 3;



    public static Border getThreeQuarterBorder(int gap, Color color, int thickness) {
        Border border = new ThreeQuarterBorder(gap, color, thickness);
        return border;
    }

    public static class ThreeQuarterBorder extends AbstractBorder implements UIResource {

        int gap;
        Color color;
        int thickness;

        public ThreeQuarterBorder(int gap, Color color, int thickness) {
            this.gap = gap;
            this.color = color;
            this.thickness = thickness;
        }


        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.translate(x, y);
            switch (gap) {
                case 0:
                    g.drawLine(x, y, x, height);
                    g.drawLine(x, height, width, height);
                    g.drawLine(width, height, width, y);
                    break;
                case 1:
                    g.drawLine(0, 0, width, 0);
                    g.drawLine(x, y, x, height);
                    g.drawLine(x, height, width, height);
            }
        }




    }
}
