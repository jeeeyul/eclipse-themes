package net.jeeeyul.eclipse.themes.ui;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.eclipse.xtext.xbase.lib.Functions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * 
 * @author Jeeeyul
 * @since 1.4
 */
public class Gradient extends ArrayList<ColorStop> {
	private static final long serialVersionUID = 5637106474252642186L;

	public static Gradient createFromString(String str) {
		Gradient result = new Gradient();
		try {
			String[] segments = str.split("/");

			for (String each : segments) {
				Scanner scanner = new Scanner(each);
				scanner.useDelimiter("\\|");
				scanner.useLocale(Locale.ENGLISH);
				ColorStop stop = new ColorStop();
				stop.color = new HSB();

				stop.color.hue = scanner.nextFloat();
				stop.color.saturation = scanner.nextFloat();
				stop.color.brightness = scanner.nextFloat();
				stop.percent = scanner.nextFloat();

				result.add(stop);
				
				scanner.close();
			}
		} catch (Exception e) {
			// FIXME report error to user
			result = new Gradient();
			result.add(new ColorStop(0f, 1f, 1f, 0f));
		}

		return result;
	}

	public String serialize() {
		return IterableExtensions.join(this, "/", new Functions.Function1<ColorStop, CharSequence>() {
			@Override
			public CharSequence apply(ColorStop stop) {
				return String.format(Locale.ENGLISH, "%f|%f|%f|%f", stop.color.hue, stop.color.saturation, stop.color.brightness, stop.percent);
			}
		});
	}

	public Gradient getCopy() {
		Gradient copy = new Gradient();
		for (ColorStop each : this) {
			copy.add(each.getCopy());
		}
		return copy;
	}

	public static void main(String[] args) {
		Gradient g = new Gradient();
		g.add(new ColorStop(1f, 0f, 0f, 0.3f));
		g.add(new ColorStop(1f, 0f, 0f, 0.3f));
		g.add(new ColorStop(1f, 0f, 0f, 0.3f));
		System.out.println(g.serialize());
		System.out.println(Gradient.createFromString(g.serialize()).serialize());

	}
}
