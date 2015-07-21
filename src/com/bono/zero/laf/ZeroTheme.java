package com.bono.zero.laf;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class ZeroTheme extends DefaultMetalTheme {
	

	
	private static final ColorUIResource primary1 = new ColorUIResource(55, 55, 55); // color of panels
	private static final ColorUIResource primary2 = new ColorUIResource(215, 215, 215); // focus and slider buttons
	private static final ColorUIResource primary3 = new ColorUIResource(250, 250, 250);
	private static final ColorUIResource secondary1 = new ColorUIResource(000, 000, 000);
	private static final ColorUIResource secondary2 = new ColorUIResource(100, 100, 100);
	private static final ColorUIResource secondary3 = new ColorUIResource(225, 225, 225); // light gray foreground panels

    @Override
    public String getName() {
        return "zerotheme";
    }

    protected ColorUIResource getPrimary1() {
		return primary1;
	}
	
	protected ColorUIResource getPrimary2() {
		return primary2;
	}
	
	protected ColorUIResource getPrimary3() {
		return primary3;
	}
	
	protected ColorUIResource getSecondary1() {
		return secondary1;
	}
	
	protected ColorUIResource getSecondary2() {
		return secondary2;
	}
	
	protected ColorUIResource getSecondary3() {
		return secondary3;
	}

    @Override
    protected ColorUIResource getWhite() {
        return primary3;
    }

    @Override
    protected ColorUIResource getBlack() {
        return secondary1;
    }

    @Override
    public ColorUIResource getControl() {
        return secondary3;
    }

    @Override
    public ColorUIResource getControlShadow() {
        return secondary2;
    }

    @Override
    public ColorUIResource getControlDarkShadow() {
        return secondary1;
    }

    @Override
    public ColorUIResource getControlInfo() {
        return secondary1;
    }

    @Override
    public ColorUIResource getControlHighlight() {
        return primary3;
    }

    @Override
    public ColorUIResource getControlDisabled() {
        return secondary2;
    }

    @Override
    public ColorUIResource getPrimaryControl() {
        return primary3;
    }

    @Override
    public ColorUIResource getPrimaryControlShadow() {
        return primary2;
    }

    @Override
    public ColorUIResource getPrimaryControlDarkShadow() {
        return primary1;
    }

    @Override
    public ColorUIResource getPrimaryControlInfo() {
        return super.getPrimaryControlInfo();
    }

    @Override
    public ColorUIResource getPrimaryControlHighlight() {
        return super.getPrimaryControlHighlight();
    }


}
