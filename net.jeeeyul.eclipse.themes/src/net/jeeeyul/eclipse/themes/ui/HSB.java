package net.jeeeyul.eclipse.themes.ui;

import java.util.Locale;
import java.util.Scanner;

import org.eclipse.swt.graphics.RGB;

public class HSB {
	public static HSB createFromString(String literal) {
		Scanner scanner = new Scanner(literal);
		scanner.useDelimiter("\\|");
		scanner.useLocale(Locale.US);

		HSB result = new HSB();
		try {
			result.hue = scanner.nextFloat();
			result.saturation = scanner.nextFloat();
			result.brightness = scanner.nextFloat();
		} catch (Exception e) {
			result = new HSB(0f, 1f, 1f);
		}
		return result;
	}

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

	public HSB(int red, int green, int blue) {
		this(new RGB(red, green, blue).getHSB());
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HSB) {
			HSB other = (HSB) obj;
			return this.hue == other.hue && this.saturation == other.saturation && this.brightness == other.brightness;
		}
		return super.equals(obj);
	}

	public HSB getCopy() {
		return new HSB(hue, saturation, brightness);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	private float limit(float original, float min, float max) {
		return Math.min(Math.max(original, min), max);
	}

	public HSB mixWith(HSB color, float strength) {
		RGB thisRGB = toRGB();
		RGB otherRGB = color.toRGB();
		RGB newRGB = new RGB(0, 0, 0);

		newRGB.red = (int) (thisRGB.red * (1f - strength) + otherRGB.red * strength);
		newRGB.green = (int) (thisRGB.green * (1f - strength) + otherRGB.green * strength);
		newRGB.blue = (int) (thisRGB.blue * (1f - strength) + otherRGB.blue * strength);

		float[] hsb = newRGB.getHSB();

		this.hue = hsb[0];
		this.saturation = hsb[1];
		this.brightness = hsb[2];

		return this;
	}

	public HSB rewriteHue(float newHue) {
		return new HSB(newHue, saturation, brightness);
	}

	public String serialize() {
		return String.format(Locale.ENGLISH, "%f|%f|%f", hue, saturation, brightness);
	}

	public float[] toArray() {
		return new float[] { hue, saturation, brightness };
	}

	public RGB toRGB() {
		return new RGB(hue, saturation, brightness);
	}

	@Override
	public String toString() {
		return String.format("%f|%f|%f", hue, saturation, brightness);
	}
}
