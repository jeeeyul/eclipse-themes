package net.jeeeyul.eclipse.themes.css.internal.handlers

import net.jeeeyul.eclipse.themes.css.EditorLineSupport
import net.jeeeyul.swtend.SWTExtensions
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.e4.ui.css.swt.dom.WidgetElement
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.GC
import org.eclipse.ui.forms.widgets.Section
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.CSSValue

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
class SectionCSSPropertyHandler implements ICSSPropertyHandler {
	extension SWTExtensions = SWTExtensions.INSTANCE

	override applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		var sElement = element as WidgetElement
		var section = sElement.nativeWidget as Section

		return switch (property) {
			case "jtitle-bar-border-color": {
				if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), section.display) as Color
					section.titleBarBorderColor = newColor
					section.redraw()
					true
				} else {
					false
				}
			}
			case "jtitle-bar-background-color": {
				if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), section.display) as Color
					if(section.titleBarBackground == newColor) {
						return true
					}
					section.titleBarBackground = newColor
					section.updateBackgroundImage
					section.redraw()
					true
				} else {
					false
				}
			}
			case "jtitle-bar-text-color": {
				if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), section.display) as Color
					section.titleBarForeground = newColor
					section.toggleColor = newColor
					true
				} else {
					false
				}
			}
			case "jtitle-bar-active-text-color": {
				if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), section.display) as Color
					section.activeToggleColor = newColor
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
		var sElement = element as WidgetElement
		var section = sElement.nativeWidget as Section

		switch (property) {
			case "jtitle-bar-border-color": 	new HSB(section.titleBarBorderColor.RGB).toHTMLCode
			case "jtitle-bar-background-color":	new HSB(section.titleBarBackground.RGB).toHTMLCode
			case "jtitle-bar-text-color":		new HSB(section.titleBarForeground.RGB).toHTMLCode
			default:
				null
		}
	}

	def private updateBackgroundImage(Section section) {
		if(!section.backgroundImage.alive) {
			return
		}

		var data = section.backgroundImage.imageData

		var bgColor = data.getPixel(0, data.height - 1)
		var from = 0
		var to = data.height - 1

		while(data.getPixel(0, from + 1) == bgColor) {
			from++
		}

		while(data.getPixel(0, to - 1) == bgColor) {
			to--;
		}

		var gc = new GC(section.backgroundImage)
		gc.foreground = section.titleBarBackground
		gc.background = section.background
		gc.fillGradientRectangle(0, from, data.width, to - from, true)
		gc.dispose()
	}

}
