package net.jeeeyul.eclipse.themes.preference.internal;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

public class FontFactory implements Function1<FontData, Font> {
	@Override
	public Font apply(FontData data) {
		try {
			Font result = new Font(Display.getCurrent(), data);
			return result;
		} catch (Exception e) {
			FontData systemFontData = Display.getCurrent().getSystemFont().getFontData()[0];
			return new Font(Display.getCurrent(), systemFontData);
		}
	}
}
