package net.jeeeyul.eclipse.themes.internal

import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.RGBColor
import org.eclipse.swt.graphics.RGB
import org.w3c.dom.css.CSSValue

class CSSExtension {
	def dispatch toRGB(CSSPrimitiveValue value) {
		switch (value.primitiveType) {
			case CSSPrimitiveValue.CSS_IDENT,
			case CSSPrimitiveValue.CSS_STRING:
				value.stringValue?.asRGB
			case CSSPrimitiveValue.CSS_RGBCOLOR:
				value.RGBColorValue?.asRGB
			default:
				null
		}
	}
	
	def dispatch toRGB(CSSValue value){
		null
	}

	def private asRGB(String value) {
		CSS2ColorHelper.getRGBColor(value)?.asRGB
	}

	def private asRGB(RGBColor rgbValue) {
		new RGB(rgbValue.red.intValue, rgbValue.green.intValue, rgbValue.blue.intValue)
	}

	def private getIntValue(CSSPrimitiveValue value) {
		value.getFloatValue(CSSPrimitiveValue.CSS_NUMBER) as int
	}
}