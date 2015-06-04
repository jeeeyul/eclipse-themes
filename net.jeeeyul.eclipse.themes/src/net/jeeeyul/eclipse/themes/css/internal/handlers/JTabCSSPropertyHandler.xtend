package net.jeeeyul.eclipse.themes.css.internal.handlers

import java.util.ArrayList
import net.jeeeyul.eclipse.themes.css.internal.CSSCompabilityHelper
import net.jeeeyul.eclipse.themes.internal.CSSExtension
import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer
import net.jeeeyul.eclipse.themes.rendering.VerticalAlignment
import net.jeeeyul.eclipse.themes.rendering.internal.JTabRendererHelper
import net.jeeeyul.swtend.ui.HSB
import org.eclipse.e4.ui.css.core.dom.properties.Gradient
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.e4.ui.css.core.impl.dom.Measure
import org.eclipse.e4.ui.css.swt.dom.CTabFolderElement
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabFolderRenderer
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.graphics.Rectangle
import org.w3c.dom.css.CSSPrimitiveValue
import org.w3c.dom.css.CSSValue
import org.w3c.dom.css.CSSValueList

/**
 * A CSS property handlers for {@link CTabFolder} that uses {@link JeeeyulsTabRenderer} as renderer.
 * 
 * @see CTabFolderRenderer#setRenderer(CTabFolderRenderer)
 */
class JTabCSSPropertyHandler implements ICSSPropertyHandler {
	extension CSSExtension = new CSSExtension

	override applyCSSProperty(Object element, String property, CSSValue value, String pseudo, extension CSSEngine engine) throws Exception {
		var tabFolderElement = element as CTabFolderElement
		var tabFolder = tabFolderElement.nativeWidget as CTabFolder
		if(!(tabFolder.renderer instanceof JeeeyulsTabRenderer)) {
			return false;
		}
		var renderer = tabFolder.renderer as JeeeyulsTabRenderer
		var settings = renderer.settings

		var applied = switch (property) {
			case "jtab-selected-tab-background": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					var colors = CSSCompabilityHelper.getSWTColors(grad, tabFolder.display, engine)
					var percents = CSSCompabilityHelper.getPercents(grad)
					tabFolder.setSelectionBackground(colors, percents, true)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), tabFolder.display) as Color
					tabFolder.setSelectionBackground(#[newColor, newColor], #[100], true)
					true
				} else {
					false
				}
			}
			case "jtab-header-background": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					var colors = CSSCompabilityHelper.getSWTColors(grad, tabFolder.display, engine)
					var percents = CSSCompabilityHelper.getPercents(grad)
					tabFolder.setBackground(colors, percents, true)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var newColor = engine.convert(value, typeof(Color), tabFolder.display) as Color
					tabFolder.setBackground(#[newColor, newColor], #[100], true)
					true
				} else {
					false
				}
			}
			case "jtab-border-color": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					settings.borderColors = grad.toHSBArray()
					settings.borderPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var rgb = value.toRGB
					if(rgb != null) {
						var hsb = new HSB(rgb)
						settings.borderColors = #[hsb, hsb]
						settings.borderPercents = #[100]
						true
					} else if(value.cssText == "none") {
						settings.borderColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
			}
			case "jtab-selected-border-color": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					settings.selectedBorderColors = grad.toHSBArray()
					settings.selectedBorderPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var rgb = value.toRGB
					if(rgb != null) {
						var hsb = new HSB(rgb)
						settings.selectedBorderColors = #[hsb, hsb]
						settings.selectedBorderPercents = #[100]
						true
					} else if(value.cssText == "none") {
						settings.selectedBorderColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
			}
			case "jtab-unselected-border-color": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					settings.unselectedBorderColors = grad.toHSBArray()
					settings.unselectedBorderPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var rgb = value.toRGB
					if(rgb != null) {
						var hsb = new HSB(rgb)
						settings.unselectedBorderColors = #[hsb, hsb]
						settings.unselectedBorderPercents = #[100]
						true
					} else if(value.cssText == "none") {
						settings.unselectedBorderColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
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
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.closeButtonColor = hsb
				} else {
					settings.closeButtonColor = null
				}
				true
			}
			case "jtab-close-button-line-width": {
				if(value instanceof CSSPrimitiveValue) {
					var lineWidth = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.closeButtonLineWidth = lineWidth
					true
				} else {
					false
				}
			}
			case "jtab-hover-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.hoverForgroundColor = hsb
				} else {
					settings.hoverForgroundColor = null
				}
				true
			}
			case "jtab-hover-border-color": {
				if(value instanceof CSSValueList) {
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					settings.hoverBorderColors = grad.toHSBArray()
					settings.hoverBorderPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var rgb = value.toRGB
					if(rgb != null) {
						var hsb = new HSB(rgb)
						settings.hoverBorderColors = #[hsb, hsb]
						settings.hoverBorderPercents = #[100]
						true
					} else if(value.cssText == "none") {
						settings.hoverBorderColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
			}
			case "jtab-close-button-hot-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.closeButtonHotColor = hsb
				} else {
					settings.closeButtonHotColor = null
				}
				true
			}
			case "jtab-close-button-active-color": {
				var rgb = (value as CSSValue).toRGB
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
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					settings.unselectedBackgroundColors = grad.toHSBArray()
					settings.unselectedBackgroundPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var rgb = value.toRGB
					if(rgb != null) {
						var hsb = new HSB(rgb)
						settings.unselectedBackgroundColors = #[hsb, hsb]
						settings.unselectedBackgroundPercents = #[100]
						true
					} else if(value.cssText == "none") {
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
					var grad = CSSCompabilityHelper.getGradient(value as CSSValueList)
					settings.hoverBackgroundColors = grad.toHSBArray()
					settings.hoverBackgroundPercents = CSSSWTColorHelper.getPercents(grad)
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var rgb = value.toRGB
					if(rgb != null) {
						var hsb = new HSB(rgb)
						settings.hoverBackgroundColors = #[hsb, hsb]
						settings.hoverBackgroundPercents = #[100]
						true
					} else if(value.cssText == "none") {
						settings.hoverBackgroundColors = null
						true
					} else {
						false
					}
				} else {
					false
				}
			}
			case "jtab-margin": {
				if(value instanceof CSSValueList) {
					var insets = (value as CSSValueList).toInset()
					settings.margins = insets
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int margin = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.margins = new Rectangle(margin, margin, margin, margin);
					true
				} else {
					false
				}
			}
			case "jtab-padding": {
				if(value instanceof CSSValueList) {
					var insets = (value as CSSValueList).toInset()
					settings.paddings = insets
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int margin = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.paddings = new Rectangle(margin, margin, margin, margin);
					true
				} else {
					false
				}
			}
			case "jtab-shadow-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.shadowColor = hsb
				} else {
					settings.shadowColor = null
				}
				true
			}
			case "jtab-shadow-position": {
				if(value instanceof CSSValueList) {
					var position = (value as CSSValueList).toPoint()
					settings.shadowPosition = position
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int v = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.shadowPosition = new Point(v, v)
					true
				} else {
					false
				}
			}
			case "jtab-shadow-radius": {
				if(value instanceof CSSPrimitiveValue) {
					var v = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.shadowRadius = v;
					true
				} else {
					false
				}
			}
			case "jtab-selected-text-shadow-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.selectedTextShadowColor = hsb
				} else {
					settings.selectedTextShadowColor = null
				}
				true
			}
			case "jtab-selected-text-shadow-position": {
				if(value instanceof CSSValueList) {
					var position = (value as CSSValueList).toPoint()
					settings.selectedTextShadowPosition = position
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int v = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.selectedTextShadowPosition = new Point(v, v)
					true
				} else {
					false
				}
			}
			case "jtab-unselected-text-shadow-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.unselectedTextShadowColor = hsb
				} else {
					settings.unselectedTextShadowColor = null
				}
				true
			}
			case "jtab-unselected-text-shadow-position": {
				if(value instanceof CSSValueList) {
					var position = (value as CSSValueList).toPoint()
					settings.unselectedTextShadowPosition = position
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int v = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.unselectedTextShadowPosition = new Point(v, v)
					true
				} else {
					false
				}
			}
			case "jtab-hover-text-shadow-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.hoverTextShadowColor = hsb
				} else {
					settings.hoverTextShadowColor = null
				}
				true
			}
			case "jtab-hover-text-shadow-position": {
				if(value instanceof CSSValueList) {
					var position = (value as CSSValueList).toPoint()
					settings.hoverTextShadowPosition = position
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int v = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.hoverTextShadowPosition = new Point(v, v)
					true
				} else {
					false
				}
			}
			case "jtab-item-padding": {
				if(value instanceof CSSValueList) {
					var insets = (value as CSSValueList).toInset()
					settings.tabItemPaddings = insets
					true
				} else if(value instanceof CSSPrimitiveValue) {
					var int margin = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.tabItemPaddings = new Rectangle(margin, margin, margin, margin);
					true
				} else {
					false
				}
			}
			case "jtab-item-horizontal-spacing": {
				if(value instanceof CSSPrimitiveValue) {
					var radius = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
					settings.tabItemHorizontalSpacing = radius
					true
				} else {
					false
				}
			}
			case "jtab-chevron-color": {
				var rgb = (value as CSSValue).toRGB
				if(rgb != null) {
					var hsb = new HSB(rgb.red, rgb.green, rgb.blue)
					settings.chevronColor = hsb
				} else {
					settings.chevronColor = null
				}
				true
			}
			
			case "jtab-truncate-tab-items": {
				var truncateTabItems = engine.convert(value, typeof(Boolean), tabFolder.display) as Boolean
				settings.truncateTabItems = truncateTabItems
				true
			}
			case "jtab-use-ellipses": {
				var useEllipse = engine.convert(value, typeof(Boolean), tabFolder.display) as Boolean
				settings.useEllipses = useEllipse
				true
			}
			case "jtab-minimum-characters": {
				if(value instanceof CSSPrimitiveValue) {
					var v = (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_NUMBER) as int
					settings.minimumCharacters = v;
					true
				} else {
					false
				}
			}
			case "jtab-close-button-alignment" : {
				if(value instanceof Measure){
					var measureValue = value as Measure
					try{
						var alignment = VerticalAlignment.fromCSSValue(measureValue.stringValue)
						settings.closeButtonAlignment = alignment
					}catch(IllegalArgumentException e){
						return false
					}
				}
				false
			}
			
			default: {
				false
			}
		}

		return applied
	}

	override retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		val extension JTabRendererHelper = new JTabRendererHelper()

		var tabFolderElement = element as CTabFolderElement
		var tabFolder = tabFolderElement.nativeWidget as CTabFolder
		if(!(tabFolder.renderer instanceof JeeeyulsTabRenderer)) {
			return null;
		}
		var renderer = tabFolder.renderer as JeeeyulsTabRenderer
		var settings = renderer.settings

		return switch (property) {
			case "jtab-border-color":
				toHTML(settings.borderColors, settings.borderPercents)
			case "jtab-selected-border-color":
				toHTML(settings.selectedBorderColors, settings.selectedBorderPercents)
			case "jtab-unselected-border-color":
				toHTML(settings.unselectedBorderColors, settings.unselectedBorderPercents)
			case "jtab-hover-border-color":
				toHTML(settings.hoverBorderColors, settings.hoverBorderPercents)
			case "jtab-border-radius": '''«settings.borderRadius»px'''
			case "jtab-spacing": '''«settings.tabSpacing»px'''
			case "jtab-close-button-color":
				settings.closeButtonColor.safeHTML
			case "jtab-close-button-hot-color":
				settings.closeButtonHotColor.safeHTML
			case "jtab-close-button-active-color":
				settings.closeButtonActiveColor.safeHTML
			case "jtab-close-button-line-width": '''«settings.closeButtonLineWidth»px'''
			case "jtab-header-background":
				toHTML(tabFolder.gradientColor, tabFolder.gradientPercents)
			case "jtab-selected-tab-background":
				toHTML(tabFolder.selectionGradientColor, tabFolder.selectionGradientPercents)
			case "jtab-unselected-tabs-background":
				toHTML(settings.unselectedBackgroundColors, settings.unselectedBackgroundPercents)
			case "jtab-hover-tabs-background":
				toHTML(settings.hoverBackgroundColors, settings.hoverBackgroundPercents)
			case "jtab-hover-color":
				settings.hoverForgroundColor.safeHTML
			case "jtab-margin": '''«settings.margins.y»px «settings.margins.width»px «settings.margins.height»px «settings.margins.x»px'''
			case "jtab-padding": '''«settings.paddings.y»px «settings.paddings.width»px «settings.paddings.height»px «settings.paddings.x»px'''
			case "jtab-shadow-color":
				settings.shadowColor.safeHTML
			case "jtab-shadow-radius": '''«settings.shadowRadius»px'''
			case "jtab-shadow-position":
				settings.shadowPosition.safeHTML
			case "jtab-selected-text-shadow-color":
				settings.selectedTextShadowColor.safeHTML
			case "jtab-unselected-text-shadow-color":
				settings.unselectedTextShadowColor.safeHTML
			case "jtab-hover-text-shadow-color":
				settings.hoverTextShadowColor.safeHTML
			case "jtab-selected-text-shadow-position":
				settings.selectedTextShadowPosition.safeHTML
			case "jtab-unselected-text-shadow-position":
				settings.unselectedTextShadowPosition.safeHTML
			case "jtab-hover-text-shadow-position":
				settings.hoverTextShadowPosition.safeHTML
			case "jtab-item-padding": '''0px «settings.tabItemPaddings.width»px 0px «settings.tabItemPaddings.x»px'''
			case "jtab-item-horizontal-spacing": '''«settings.tabItemHorizontalSpacing»px'''
			case "jtab-chevron-color": '''«settings.chevronColor.toHTMLCode»'''
			case "jtab-truncate-tab-items" : '''«settings.truncateTabItems»'''
			case "jtab-use-ellipses": '''«settings.useEllipses»'''
			case "jtab-minimum-characters" : '''«settings.minimumCharacters»'''
			case "jtab-close-button-alignment" : settings.closeButtonAlignment.CSSValue
			default:
				null
		}
	}

	def private String safeHTML(HSB hsb) {
		if(hsb == null) {
			"none"
		} else {
			hsb.toHTMLCode
		}
	}

	def private String safeHTML(Point point) {
		if(point == null) {
			"none"
		} else {
			'''«point.x»px «point.y»px'''
		}
	}

	def private toHTML(HSB[] colors, int[] percents) {
		if(colors == null || percents == null) {
			return "none";
		} else {
			return '''«colors.join(' ')[it.toHTMLCode]» «percents.join(" ")[it + "%"]»'''
		}
	}

	def private toHTML(Color[] colors, int[] percents) {
		if(colors == null || percents == null) {
			return "none";
		} else {
			return '''«colors.join(' ')[new HSB(it.RGB).toHTMLCode]» «percents.join(" ")[it + "%"]»'''
		}
	}

	def private Rectangle toInset(CSSValueList list) {
		var int top = 0;
		var int right = 0;
		var int bottom = 0;
		var int left = 0;
		switch (list.length) {
			case 4: {
				top = list.item(0).asPX
				right = list.item(1).asPX
				bottom = list.item(2).asPX
				left = list.item(3).asPX
			}
			case 2: {
				top = list.item(0).asPX
				right = list.item(1).asPX
				bottom = list.item(0).asPX
				left = list.item(1).asPX
			}
		}
		return new Rectangle(left, top, right, bottom)
	}

	def private Point toPoint(CSSValueList list) {
		var int x = 0;
		var int y = 0;
		switch (list.length) {
			case 2: {
				x = list.item(0).asPX
				y = list.item(1).asPX
			}
		}
		return new Point(x, y)
	}

	def private int asPX(CSSValue value) {
		if(value instanceof CSSPrimitiveValue) {
			return (value as CSSPrimitiveValue).getFloatValue(CSSPrimitiveValue.CSS_PX) as int
		}

		return 0
	}

	private def HSB[] toHSBArray(Gradient grad) {
		val ArrayList<HSB> hsb = newArrayList()

		grad.RGBs.forEach [
			hsb.add(new HSB(it as RGB))
		]

		return hsb
	}
}
