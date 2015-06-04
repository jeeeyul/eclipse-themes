package net.jeeeyul.eclipse.themes.css.internal;

import java.util.List;

import org.eclipse.e4.ui.css.core.dom.properties.Gradient;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

import net.jeeeyul.eclipse.themes.css.internal.handlers.JTabCSSPropertyHandler;
import net.jeeeyul.eclipse.themes.internal.CSSExtension;

/**
 * CSS Value Converter for {@link JTabCSSPropertyHandler}
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class CSSCompabilityHelper {
	private static CSSExtension cssExtension = new CSSExtension();
	/*
	 * Compute and return a default array of percentages based on number of
	 * colors o If two colors, {100} o if three colors, {50, 100} o if four
	 * colors, {33, 67, 100}
	 */
	private static int[] getDefaultPercents(Gradient grad) {
		// Needed to avoid /0 in increment calc
		if (grad.getRGBs().size() == 1) {
			return new int[0];
		}

		int[] percents = new int[grad.getRGBs().size() - 1];
		float increment = 100f / (grad.getRGBs().size() - 1);

		for (int i = 0; i < percents.length; i++) {
			percents[i] = Math.round((i + 1) * increment);
		}
		return percents;
	}

	/**
	 * Converts given {@link CSSValueList} to
	 * {@link net.jeeeyul.swtend.ui.Gradient}.
	 * 
	 * @param list
	 *            {@link CSSValueList} to convert.
	 * @return A {@link net.jeeeyul.swtend.ui.Gradient} object.
	 */
	public static Gradient getGradient(CSSValueList list) {
		Gradient gradient = new Gradient();
		for (int i = 0; i < list.getLength(); i++) {
			CSSValue value = list.item(i);
			if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
				short primType = ((CSSPrimitiveValue) value).getPrimitiveType();

				if (primType == CSSPrimitiveValue.CSS_IDENT) {
					if (value.getCssText().equals("gradient")) {
						continue;
					} else if (value.getCssText().equals("linear")) {
						gradient.setLinear(true);
						continue;
					} else if (value.getCssText().equals("radial")) {
						gradient.setLinear(false);
						continue;
					}
				}

				switch (primType) {
				case CSSPrimitiveValue.CSS_IDENT:
				case CSSPrimitiveValue.CSS_STRING:
				case CSSPrimitiveValue.CSS_RGBCOLOR:
					RGB rgb = cssExtension.toRGB(value);
					if (rgb != null) {
						gradient.addRGB(rgb, (CSSPrimitiveValue) value);
					} else {
						// check for vertical gradient
						gradient.setVertical(!value.getCssText().equals("false"));
					}
					break;
				case CSSPrimitiveValue.CSS_PERCENTAGE:
					gradient.addPercent(getPercent((CSSPrimitiveValue) value));
					break;
				}
			}
		}
		return gradient;
	}

	/**
	 * Converts css % value to int.
	 * 
	 * @param value
	 *            value to convert.
	 * @return An int value.
	 */
	public static Integer getPercent(CSSPrimitiveValue value) {
		int percent = 0;
		switch (value.getPrimitiveType()) {
		case CSSPrimitiveValue.CSS_PERCENTAGE:
			percent = (int) value.getFloatValue(CSSPrimitiveValue.CSS_PERCENTAGE);
		}
		return new Integer(percent);
	}

	/**
	 * Extract percent array(int) from {@link Gradient}.
	 * 
	 * @param grad
	 *            gradient to extract percent array.
	 * @return percent array.
	 */
	public static int[] getPercents(Gradient grad) {
		// There should be exactly one more RGBs. than percent,
		// in which case just return the percents as array
		if (grad.getRGBs().size() == grad.getPercents().size() + 1) {
			int[] percents = new int[grad.getPercents().size()];
			for (int i = 0; i < percents.length; i++) {
				int value = ((Integer) grad.getPercents().get(i)).intValue();
				if (value < 0 || value > 100) {
					// TODO this should be an exception because bad source
					// format
					return getDefaultPercents(grad);
				}
				percents[i] = value;
			}
			return percents;
		} else {
			// We can get here if either:
			// A: the percents are empty (legal) or
			// B: size mismatches (error)
			// TODO this should be an exception because bad source format

			return getDefaultPercents(grad);
		}
	}

	/**
	 * Converts {@link Gradient} to {@link Color} array.
	 * 
	 * @param grad
	 *            gradient to convert.
	 * @param display
	 *            context.
	 * @param engine
	 *            engine
	 * @return {@link Color} array.
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Color[] getSWTColors(Gradient grad, Display display, CSSEngine engine) throws Exception {
		List values = grad.getValues();
		Color[] colors = new Color[values.size()];

		for (int i = 0; i < values.size(); i++) {
			CSSPrimitiveValue value = (CSSPrimitiveValue) values.get(i);
			// We rely on the fact that when a gradient is created, it's colors
			// are converted and in the registry
			// TODO see bug #278077
			Color color = (Color) engine.convert(value, Color.class, display);
			colors[i] = color;
		}
		return colors;
	}
}
