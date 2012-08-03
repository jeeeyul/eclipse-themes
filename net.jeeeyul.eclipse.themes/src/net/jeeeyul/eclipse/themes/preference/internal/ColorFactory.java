package net.jeeeyul.eclipse.themes.preference.internal;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

public class ColorFactory implements Function1<RGB, Color> {
	@Override
	public Color apply(RGB rgb) {
		return new Color(Display.getCurrent(), rgb);
	}

}
