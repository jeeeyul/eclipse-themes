package net.jeeeyul.eclipse.themes;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class CTabFolderGradient {
	private Display display = Display.getDefault();

	private float[] hsb;
	private Color[] colors;

	public CTabFolderGradient(float[] hsb) {
		Assert.isNotNull(hsb);
		this.hsb = hsb;
	}

	public void apply(CTabFolder tabFolder) {
		this.display = tabFolder.getDisplay();
		tabFolder.setBackground(getColors(), new int[] { 99, 100 }, true);
	}

	public void dispose() {
		invaidate();
	}

	private Color[] getColors() {
		if (colors == null) {
			colors = new Color[3];
			colors[0] = new Color(display, new RGB(hsb[0], hsb[1], hsb[2]));

			float saturation = Math.min(hsb[1] * 1.5f, 1f);
			float brightness = Math.max(hsb[2] * 0.99f, 0f);
			colors[1] = new Color(display, new RGB(hsb[0], saturation, brightness));
			colors[2] = new Color(display, 255, 255, 255);
		}
		return colors;
	}

	public float[] getHsb() {
		return hsb;
	}

	private void invaidate() {
		for (Color each : colors) {
			if (each != null && !each.isDisposed()) {
				each.dispose();
			}
		}
		colors = null;
	}

	public void setHsb(float[] hsb) {
		Assert.isNotNull(hsb);
		Assert.isLegal(hsb.length == 3);

		if (this.hsb.equals(hsb)) {
			return;
		}
		this.hsb = hsb;
		invaidate();
	}
}
