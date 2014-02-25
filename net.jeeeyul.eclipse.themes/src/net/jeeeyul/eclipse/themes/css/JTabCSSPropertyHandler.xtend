package net.jeeeyul.eclipse.themes.css

import net.jeeeyul.swtend.ui.HSB
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.e4.ui.css.swt.dom.CTabFolderElement
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper
import org.eclipse.swt.custom.CTabFolder
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.CSSValue
import org.w3c.dom.css.CSSValueList
import org.eclipse.e4.ui.css.core.dom.properties.Gradient
import org.eclipse.swt.graphics.RGB
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer

class JTabCSSPropertyHandler implements ICSSPropertyHandler {

	override applyCSSProperty(Object element, String property, CSSValue value, String pseudo, extension CSSEngine engine) throws Exception {
		var tabFolderElement = element as CTabFolderElement
		var tabFolder = tabFolderElement.nativeWidget as CTabFolder
		if(!(tabFolder.renderer instanceof JeeeyulsTabRenderer)) {
			return false;
		}
		var renderer = tabFolder.renderer as JeeeyulsTabRenderer
		var settings = renderer.settings

		var applied = switch (property) {
			case "jtab-border-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.borderColor = hsb
				} else {
					settings.borderColor = null
				}
				true
			}
			case "jtab-selected-border-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.selectedBorderColor = hsb
				} else {
					settings.selectedBorderColor = null
				}
				true
			}
			case "jtab-unselected-border-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.unselectedBorderColor = hsb
				} else {
					settings.unselectedBorderColor = null
				}
				true
			}
			case "jtab-hover-border-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.hoverBorderColor = hsb
				} else {
					settings.hoverBorderColor = null
				}
				true
			}
			case "jtab-border-radius": {
				if(value instanceof CSSPrimitiveValue) {
					var radius = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.borderRadius = radius
					true
				} else {
					false
				}
			}
			case "jtab-spacing": {
				if(value instanceof CSSPrimitiveValue) {
					var tabSpacing = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.tabSpacing = tabSpacing
					true
				} else {
					false
				}
			}
			case "jtab-close-button-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.closeButtonColor = hsb
				} else {
					settings.closeButtonColor = null
				}
				true
			}
			
			case "jtab-hover-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.hoverForgroundColor = hsb
				} else {
					settings.hoverForgroundColor = null
				}
				true
			}
			
			case "jtab-close-button-hot-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.closeButtonHotColor = hsb
				} else {
					settings.closeButtonHotColor = null
				}
				true
			}
			case "jtab-close-button-active-color": {
				var rgb = CSSSWTColorHelper.getRGB(value as CSSValue)
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.closeButtonActiveColor = hsb
				} else {
					settings.closeButtonActiveColor = null
				}
				true
			}
			case "jtab-unselected-tabs-background": {
				if(value instanceof CSSValueList) {
					var grad = CSSSWTColorHelper.getGradient(value as CSSValueList, tabFolder.display)
					settings.unselectedBackgroundColors = grad.toHSBArray()
					settings.unselectedBackgroundPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					if(value.cssText == "none") {
						settings.unselectedBackgroundColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
			}
			case "jtab-hover-tabs-background": {
				if(value instanceof CSSValueList) {
					var grad = CSSSWTColorHelper.getGradient(value as CSSValueList, tabFolder.display)
					settings.hoverBackgroundColors = grad.toHSBArray()
					settings.hoverBackgroundPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					if(value.cssText == "none") {
						settings.hoverBackgroundColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
			}
			default: {
				false
			}
		}

		if(applied) {
			tabFolder.redraw()
		}

		return applied
	}

	override retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		return null
	}

	private def HSB[] toHSBArray(Gradient grad) {
		val HSB[] hsb = newArrayList()

		grad.RGBs.forEach [
			hsb.add(new HSB(it as RGB))
		]

		return hsb
	}
}
