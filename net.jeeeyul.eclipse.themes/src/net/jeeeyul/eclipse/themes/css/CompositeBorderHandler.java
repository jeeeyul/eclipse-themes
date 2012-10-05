package net.jeeeyul.eclipse.themes.css;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class CompositeBorderHandler implements ICSSPropertyHandler {

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof CompositeElement)) {
			return false;
		}
		CompositeElement compositeElement = (CompositeElement) element;

		Composite comp = (Composite) compositeElement.getNativeWidget();

		if (property.equals("chrome-border-top-visible")) {
			Boolean visible = (Boolean) engine.convert(value, Boolean.class, comp.getDisplay());
			ChromeBorder.get(comp).setTopVisible(visible);
			return true;
		}

		if (property.equals("chrome-border-top-color")) {
			Color color = (Color) engine.convert(value, Color.class, comp.getDisplay());
			ChromeBorder.get(comp).setTopColor(color);
			return true;
		}

		if (property.equals("chrome-border-bottom-visible")) {
			Boolean visible = (Boolean) engine.convert(value, Boolean.class, comp.getDisplay());
			ChromeBorder.get(comp).setBottomVisible(visible);
			return true;
		}

		if (property.equals("chrome-border-bottom-color")) {
			Color color = (Color) engine.convert(value, Color.class, comp.getDisplay());
			ChromeBorder.get(comp).setBottomColor(color);
			return true;
		}

		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		return null;
	}

}
