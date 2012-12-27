package net.jeeeyul.eclipse.themes.css;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class StyleTextLineHandler implements ICSSPropertyHandler {

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof CompositeElement)) {
			return false;
		}
		CompositeElement compositeElement = (CompositeElement) element;

		StyledText styledText = (StyledText) compositeElement.getNativeWidget();

		if (property.equals("chrome-line-style")) {
			String lineStyle = value.getCssText();
			ChromeEditorLiner.get(styledText).setLineStyle(lineStyle);
			return true;
		}

		if (property.equals("chrome-line-color")) {
			Color color = (Color) engine.convert(value, Color.class, styledText.getDisplay());
			ChromeEditorLiner.get(styledText).setColor(color);
			return true;
		}

		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {

		return null;
	}

}
