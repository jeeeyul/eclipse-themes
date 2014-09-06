package net.jeeeyul.eclipse.themes.css.internal.handlers

import net.jeeeyul.eclipse.themes.css.internal.elements.FormHeadingElement
import net.jeeeyul.eclipse.themes.css.internal.CSSCompabilityHelper
import net.jeeeyul.eclipse.themes.css.EditorLineSupport
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.Color
import org.eclipse.ui.internal.forms.widgets.FormHeading
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.CSSValue
import org.w3c.dom.css.CSSValueList
import org.eclipse.ui.forms.IFormColors
import net.jeeeyul.swtend.ui.HSB

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
class FormHeadingCSSPropertyHandler implements ICSSPropertyHandler {

	override applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		var fhElement = element as FormHeadingElement
		var formHeading = fhElement.nativeWidget as FormHeading

		return switch (property) {
			case "jtext-background": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					var colors = CSSCompabilityHelper.getSWTColors(grad, formHeading.display, engine)
					var percents = CSSCompabilityHelper.getPercents(grad)
					formHeading.setTextBackground(colors, percents, true)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), formHeading.display) as Color
					formHeading.setTextBackground(#[newColor, newColor], #[100], true)
					true
				} else {
					false
				}
			}
			case "jbottom-keyline-1-color": {
				if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), formHeading.display) as Color
					formHeading.putColor(IFormColors.H_BOTTOM_KEYLINE1, newColor)
					true
				} else {
					false
				}
			}
			case "jbottom-keyline-2-color": {
				if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), formHeading.display) as Color
					formHeading.putColor(IFormColors.H_BOTTOM_KEYLINE2, newColor)
					true
				} else {
					false
				}
			}
			default: {
				false
			}
		}

	}

	override retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		var fhElement = element as FormHeadingElement
		var formHeading = fhElement.nativeWidget as FormHeading

		switch (property) {
			case "jbottom-keyline-1-color": {
				new HSB(formHeading.getColor(IFormColors.H_BOTTOM_KEYLINE1).getRGB).toHTMLCode
			}
			case "jbottom-keyline-2-color": {
				new HSB(formHeading.getColor(IFormColors.H_BOTTOM_KEYLINE2).getRGB).toHTMLCode
			}
			default:
				null
		}
	}

}
