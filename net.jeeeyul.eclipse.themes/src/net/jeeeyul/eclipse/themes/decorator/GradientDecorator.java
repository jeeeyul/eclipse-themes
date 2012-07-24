package net.jeeeyul.eclipse.themes.decorator;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class GradientDecorator implements ICTabFolderDecorator {
	private float[] startHSB;
	private float[] endHSB;
	private Color startColor;
	private Color endColor;

	private Display display;

	@Override
	public void apply(CTabFolder tabFolder) {
		display = tabFolder.getDisplay();
		Color white = display.getSystemColor(SWT.COLOR_WHITE);
		tabFolder.setBackground(new Color[] { getStartColor(), getEndColor(), white }, new int[] { 99, 100 }, true);
	}

	@Override
	public void dispose() {
		disposeStartColor();
		disposeEndColor();
	}

	private Color getEndColor() {
		if (endColor == null || endColor.isDisposed()) {
			endColor = new Color(display, new RGB(endHSB[0], endHSB[1], endHSB[2]));
		}
		return endColor;
	}

	public float[] getEndHSB() {
		return Arrays.copyOf(endHSB, 3);
	}

	private Color getStartColor() {
		if (startColor == null || startColor.isDisposed()) {
			startColor = new Color(display, new RGB(startHSB[0], startHSB[1], startHSB[2]));
		}
		return startColor;
	}

	public GradientDecorator(float[] startHSB, float[] endHSB) {
		super();
		this.startHSB = startHSB;
		this.endHSB = endHSB;
	}

	public float[] getStartHSB() {
		return Arrays.copyOf(startHSB, 3);
	}

	public void setEndHSB(float[] endHSB) {
		disposeEndColor();
		this.endHSB = endHSB;
	}

	private void disposeEndColor() {
		if (endColor != null && !endColor.isDisposed()) {
			endColor.dispose();
			endColor = null;
		}
	}

	public void setStartHSB(float[] startHSB) {
		disposeStartColor();
		this.startHSB = startHSB;
	}

	private void disposeStartColor() {
		if (startColor != null && !startColor.isDisposed()) {
			startColor.dispose();
			startColor = null;
		}
	}

}
