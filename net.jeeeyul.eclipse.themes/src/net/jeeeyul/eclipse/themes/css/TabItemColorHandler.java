package net.jeeeyul.eclipse.themes.css;

import net.jeeeyul.eclipse.themes.rendering.ChromeTabRendering;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CTabFolderElement;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.graphics.Color;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class TabItemColorHandler implements ICSSPropertyHandler {

	public TabItemColorHandler() {
	}

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		CTabFolderElement model = (CTabFolderElement) element;
		CTabFolder folder = (CTabFolder) model.getNativeWidget();
		CTabFolderRenderer renderer = folder.getRenderer();

		if (!(renderer instanceof ChromeTabRendering)) {
			return false;
		}

		ChromeTabRendering rendering = (ChromeTabRendering) renderer;
		Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());

		if (property.equals("selected-tab-color")) {
			rendering.setSelectedTabItemColor(color);
		} else if (property.equals("unselected-tab-color")) {
			rendering.setUnselectedTabItemColor(color);
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
