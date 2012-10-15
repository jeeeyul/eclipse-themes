package net.jeeeyul.eclipse.themes.css;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.util.PrefUtil;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class PerspectiveSwitcherCSSHandler implements ICSSPropertyHandler {
	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (property.equals("chrome-show-perspective-name")) {
			Boolean showText = (Boolean) engine.convert(value, Boolean.class, Display.getDefault());
			if (showText != PrefUtil.getAPIPreferenceStore().getDefaultBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR)) {
				PrefUtil.getInternalPreferenceStore().setValue("overridepresentation", true);
			}
			PrefUtil.getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR, showText);
			return true;
		}
		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof WidgetElement)) {
			return null;
		}

		WidgetElement model = (WidgetElement) element;
		Widget nativeWidget = (Widget) model.getNativeWidget();

		String id = WidgetElement.getID(nativeWidget);
		if (id == null || !id.equals("PerspectiveSwitcher")) {
			return null;
		}

		if (property.equals("chrome-show-perspective-name")) {
			return Boolean.toString(PrefUtil.getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR));
		}

		return null;
	}

}
