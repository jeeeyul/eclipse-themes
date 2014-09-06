package net.jeeeyul.eclipse.themes.css.internal.handlers;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.ShellElement;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * Adds css properties to {@link Shell}
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class ShellCSSHandler implements ICSSPropertyHandler {

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof ShellElement)) {
			return false;
		}
		Shell shell = (Shell) ((ShellElement) element).getNativeWidget();

		if (property.equals("jswt-alpha")) {
			if (!(value instanceof CSSPrimitiveValue)) {
				return false;
			}

			float alpha = ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_NUMBER);
			shell.setAlpha((int) alpha);
			return true;
		}

		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof ShellElement)) {
			return null;
		}
		Shell shell = (Shell) ((ShellElement) element).getNativeWidget();
		if (property.equals("jswt-alpha")) {
			return Integer.toString(shell.getAlpha());
		}
		return null;
	}

}
