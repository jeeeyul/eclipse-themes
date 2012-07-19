package net.jeeeyul.eclipse.themes

import org.eclipse.swt.widgets.Widget
import org.eclipse.e4.ui.css.swt.CSSSWTConstants

/**
 * Gives css capability to other xtend files.
 */
class CSSExtension {
	def CSSClasses getStyleClasses(Widget w){
		var literal = w.getData(CSSSWTConstants::CSS_CLASS_NAME_KEY) as String
		return new CSSClasses(literal)
	}

	def void setStyleClasses(Widget w, CSSClasses newStyleClasses){
		w.setData(CSSSWTConstants::CSS_CLASS_NAME_KEY, newStyleClasses.toString())
	}
}