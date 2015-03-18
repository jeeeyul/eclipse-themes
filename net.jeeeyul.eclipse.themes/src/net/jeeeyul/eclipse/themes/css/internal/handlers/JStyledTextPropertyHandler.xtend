package net.jeeeyul.eclipse.themes.css.internal.handlers

import net.jeeeyul.eclipse.themes.css.EditorLineSupport
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.e4.ui.css.swt.dom.CompositeElement
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.widgets.Composite
import org.eclipse.ui.texteditor.DefaultRangeIndicator
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.CSSValue
import org.eclipse.swt.graphics.PaletteData

/**
 * CSS Property handler for {@link StyledText}.
 * 
 * @see EditorLineSupport
 */
class JStyledTextPropertyHandler implements ICSSPropertyHandler {

	override applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		var compositeElement = element as CompositeElement
		var composite = compositeElement.nativeWidget as Composite
		if (!(composite instanceof StyledText)) {
			return false
		}
		var styledText = composite as StyledText
		var els = EditorLineSupport.get(styledText)

		switch (property) {
			case "jeditor-line-style": {
				if (value instanceof CSSPrimitiveValue) {
					els.lineStyle = switch (value.cssText) {
						case "solid": SWT.LINE_SOLID
						case "dashed": SWT.LINE_DASH
						case "dotted": SWT.LINE_DOT
						default: SWT.NONE
					}
					true
				} else {
					false
				}
			}
			case "jeditor-line-color": {
				var rgba = CSSSWTColorHelper.getRGBA(value as CSSValue)
				if (rgba != null) {
					els.lineColor = new HSB(rgba.rgb);
					true
				} else {
					false
				}
			}
			case "jeditor-range-indicator-color": {
				var rgba = CSSSWTColorHelper.getRGBA(value as CSSValue)
				if (rgba != null) {
					var field = DefaultRangeIndicator.getDeclaredField("fgPaletteData");
					field.setAccessible(true);
					var paletteData = new PaletteData(newArrayList(rgba.rgb, rgba.rgb))
					field.set(DefaultRangeIndicator, paletteData)
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
		var compositeElement = element as CompositeElement
		var composite = compositeElement.nativeWidget as Composite
		if (!(composite instanceof StyledText)) {
			return null
		}
		var styledText = composite as StyledText
		var els = EditorLineSupport.get(styledText)

		switch (property) {
			case "jeditor-line-style": {
				switch (els.lineStyle) {
					case SWT.LINE_SOLID: "solid"
					case SWT.LINE_DASH: "dashed"
					case SWT.LINE_DOT: "dotted"
					default: "none"
				}
			}
			case "jeditor-line-color": {
				els.lineColor.toHTMLCode()
			}
			default:
				null
		}
	}

}
