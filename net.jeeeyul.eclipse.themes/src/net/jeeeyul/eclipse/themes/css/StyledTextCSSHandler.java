package net.jeeeyul.eclipse.themes.css;

import net.jeeeyul.eclipse.themes.e4.StyledTextElement;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class StyledTextCSSHandler implements ICSSPropertyHandler {

	public StyledTextCSSHandler() {
	}

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof StyledTextElement)) {
			return false;
		}
		StyledTextElement styledTextElement = (StyledTextElement) element;
		StyledText text = (StyledText) styledTextElement.getNativeWidget();

		if (property.equals("chrome-line-color")) {
			ensureLiner(text).setLineColor((Color) engine.convert(value, Color.class, text.getDisplay()));
			return true;
		}

		else if (property.equals("chrome-line-dash")) {
			ensureLiner(text).setDash((Boolean) engine.convert(value, Boolean.class, text.getDisplay()));
			return true;
		}

		else if (property.equals("chrome-line-visible")) {
			Boolean visible = (Boolean) engine.convert(value, Boolean.class, text.getDisplay());
			ensureLiner(text).setVisible(visible);
			return true;
		}

		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		return null;
	}

	private ChromeEditorLiner ensureLiner(StyledText text) {
		ChromeEditorLiner liner = (ChromeEditorLiner) text.getData("chrome-liner");
		if (liner == null) {
			liner = new ChromeEditorLiner(text);
			text.setData("chrome-liner", liner);
		}
		return liner;
	}

}
