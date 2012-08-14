package net.jeeeyul.eclipse.themes.css;

import net.jeeeyul.eclipse.themes.e4.SectionElement;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class SectionCSSPropertyHandler implements ICSSPropertyHandler {

	public SectionCSSPropertyHandler() {
	}

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		SectionElement model = (SectionElement) element;
		Section section = (Section) model.getNativeWidget();

		if (property.equals("section-title-bar-background")) {
			Color color = (Color) engine.convert(value, Color.class, section.getDisplay());
			section.setTitleBarBackground(color);
		}

		if (property.equals("section-title-bar-border-color")) {
			Color color = (Color) engine.convert(value, Color.class, section.getDisplay());
			section.setTitleBarBorderColor(color);
		}

		if (property.equals("section-active-toggle-color")) {
			Color color = (Color) engine.convert(value, Color.class, section.getDisplay());
			section.setActiveToggleColor(color);
		}

		if (property.equals("section-title-bar-foreground")) {
			Color color = (Color) engine.convert(value, Color.class, section.getDisplay());
			section.setTitleBarForeground(color);
		}

		return true;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		/*
		 * It seems to be not used anywhere.
		 */
		return null;
	}

}
