package net.jeeeyul.eclipse.themes.decorator;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class InactiveDecorator implements ICTabFolderDecorator {
	private static Color[] COLORS = { new Color(Display.getDefault(), 238, 238, 238), new Color(Display.getDefault(), 223, 223, 223),
			new Color(Display.getDefault(), 255, 255, 255) };

	@Override
	public void apply(CTabFolder tabFolder) {
		tabFolder.setBackground(COLORS, new int[] { 99, 100 }, true);
	}

	@Override
	public void dispose() {

	}

}
