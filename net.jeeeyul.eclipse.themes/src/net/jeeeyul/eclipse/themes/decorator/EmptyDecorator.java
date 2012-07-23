package net.jeeeyul.eclipse.themes.decorator;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class EmptyDecorator implements ICTabFolderDecorator {
	private static Color[] COLORS = { new Color(Display.getDefault(), 221, 221, 221), new Color(Display.getDefault(), 221, 221, 221),
			new Color(Display.getDefault(), 221, 221, 221) };

	@Override
	public void apply(CTabFolder tabFolder) {
		tabFolder.setBackground(COLORS, new int[] { 99, 100 }, true);
	}

	@Override
	public void dispose() {

	}

}
