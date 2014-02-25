package net.jeeeyul.eclipse.themes.css.internal;

import org.eclipse.e4.ui.css.core.dom.properties.Gradient;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.swt.graphics.RGB;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

@SuppressWarnings("restriction")
public class CSSCompabilityHelper {

	public CSSCompabilityHelper() {
		// TODO Auto-generated constructor stub
	}

	public static Gradient getGradient(CSSValueList list) {
		Gradient gradient = new Gradient();
		for (int i = 0; i < list.getLength(); i++) {
			CSSValue value = list.item(i);
			if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
				short primType = ((CSSPrimitiveValue) value).getPrimitiveType();

				if (primType == CSSPrimitiveValue.CSS_IDENT) {
					if (value.getCssText().equals("gradient")) {
						// Skip the keyword "gradient"
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
					RGB rgb = CSSSWTColorHelper.getRGB(value);
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

	public static Integer getPercent(CSSPrimitiveValue value) {
		int percent = 0;
		switch (value.getPrimitiveType()) {
		case CSSPrimitiveValue.CSS_PERCENTAGE:
			percent = (int) value.getFloatValue(CSSPrimitiveValue.CSS_PERCENTAGE);
		}
		return new Integer(percent);
	}
}
