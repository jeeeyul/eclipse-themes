package net.jeeeyul.eclipse.themes.css.internal.handlers

import net.jeeeyul.eclipse.themes.css.EditorLineSupport
import net.jeeeyul.eclipse.themes.css.internal.elements.FormTextElement
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.Color
import org.eclipse.ui.forms.widgets.FormText
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.CSSValue

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
class FormTextCSSPropertyHandler implements ICSSPropertyHandler {

	override applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		var ftElement = element as FormTextElement
		var formText = ftElement.nativeWidget as FormText
		if (!(formText instanceof FormText)) {
			return false
		}

		switch (property) {
			case "hyperlink-color": {
				if (value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), formText.display) as Color
					formText.hyperlinkSettings.foreground = newColor
					true
				} else {
					false
				}
			}
			case "active-hyperlink-color": {
				if (value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), formText.display) as Color
					formText.hyperlinkSettings.activeForeground = newColor
					true
				} else {
					false
				}
			}
			default:
				false
		}
	}

	override retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		null
	}

}
