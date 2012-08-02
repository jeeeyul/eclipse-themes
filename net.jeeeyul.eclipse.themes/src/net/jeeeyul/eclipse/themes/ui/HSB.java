package net.jeeeyul.eclipse.themes.ui;

import org.eclipse.swt.graphics.RGB;

public class HSB {
	public float hue;
	public float saturation;
	public float brightness;

	public HSB() {

	}

	public HSB(float hue, float saturation, float brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}

	public HSB(float[] hsb) {
		this(hsb[0], hsb[1], hsb[2]);
	}

	public HSB(RGB rgb) {
		this(rgb.getHSB());
	}

	public HSB ampBrightness(float amp) {
		return new HSB(hue, saturation, limit(this.brightness * amp, 0f, 1f));
	}

	public HSB ampSaturation(float amp) {
		return new HSB(hue, limit(this.saturation * amp, 0f, 1f), brightness);
	}

	private float limit(float original, float min, float max) {
		return Math.min(Math.max(original, min), max);
	}

	public HSB rewriteHue(float newHue) {
		return new HSB(newHue, saturation, brightness);
	}

	public float[] toArray() {
		return new float[] { hue, saturation, brightness };
	}

	public RGB toRGB() {
		return new RGB(hue, saturation, brightness);
	}

	@Override
	public String toString() {
		return "HSB [hue=" + hue + ", saturation=" + saturation + ", brightness=" + brightness + "]";
	}
}
