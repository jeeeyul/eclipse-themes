package net.jeeeyul.eclipse.themes.ui;

import java.util.ArrayList;
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

		String[] segments = str.split("\\|");

		for (String each : segments) {
			Scanner scanner = new Scanner(each);
			scanner.useDelimiter(",");
			ColorStop stop = new ColorStop();
			stop.color = new HSB();

			stop.color.hue = scanner.nextFloat();
			stop.color.saturation = scanner.nextFloat();
			stop.color.brightness = scanner.nextFloat();
			stop.percent = scanner.nextFloat();

			result.add(stop);
		}

		return result;
	}

	@Override
	public String toString() {
		return IterableExtensions.join(this, "|", new Functions.Function1<ColorStop, CharSequence>() {
			@Override
			public CharSequence apply(ColorStop stop) {
				return String.format("%f,%f,%f,%f", stop.color.hue, stop.color.saturation, stop.color.brightness, stop.percent);
			}
		});
	}

	public static void main(String[] args) {
		Gradient g = new Gradient();
		g.add(new ColorStop(360f, 0f, 0f, 0f));
		g.add(new ColorStop(170f, 0f, 1f, 1f));

		String notaion = g.getCopy().toString();

		System.out.println(Gradient.createFromString(notaion));
	}

	public Gradient getCopy() {
		Gradient copy = new Gradient();
		for (ColorStop each : this) {
			copy.add(each.getCopy());
		}

		return copy;
	}
}
