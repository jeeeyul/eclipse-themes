package net.jeeeyul.eclipse.themes.css.internal;

import java.lang.reflect.Field;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;

/**
 * It hacks {@link DefaultRangeIndicator}'s palette data to use more suitable
 * color to display range indicator.
 * 
 * @author Jeeeyul
 */
public class RangeIndicatorHack {
	/**
	 * It hacks {@link DefaultRangeIndicator}'s palette data to use more
	 * suitable color to display range indicator.
	 */
	public static void update() {
		try {
			Field field = DefaultRangeIndicator.class.getDeclaredField("fgPaletteData");
			field.setAccessible(true);

			HSB rulerHSB = JThemesCore.getDefault().getPreferenceStore().getHSB(JTPConstants.TextEditor.RULER_COLOR);
			RGB selectionColor = Display.getDefault().getSystemColor(SWT.COLOR_LIST_SELECTION).getRGB();

			selectionColor = new HSB(selectionColor).getMixedWith(rulerHSB, 0.5f).toRGB();

			PaletteData paletteData = new PaletteData(new RGB[] { selectionColor, selectionColor });
			field.set(DefaultRangeIndicator.class, paletteData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
