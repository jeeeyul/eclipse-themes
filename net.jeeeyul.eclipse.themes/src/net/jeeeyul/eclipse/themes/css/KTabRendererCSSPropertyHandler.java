package net.jeeeyul.eclipse.themes.css;

import net.jeeeyul.eclipse.themes.rendering.KTabRenderer;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CTabFolderElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.graphics.Color;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class KTabRendererCSSPropertyHandler implements ICSSPropertyHandler {

	public KTabRendererCSSPropertyHandler() {
	}

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		CTabFolderElement tabFolderElement = (CTabFolderElement) element;
		CTabFolder folder = (CTabFolder) tabFolderElement.getNativeWidget();
		CTabFolderRenderer renderer = folder.getRenderer();
		if (!(renderer instanceof KTabRenderer)) {
			return false;
		}
		KTabRenderer kTabRenderer = (KTabRenderer) renderer;
		if (property.equals("jeeeyul-tab-border-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setBorderColor(new HSB(color.getRGB()));
			folder.redraw();
			return true;
		}

		else if (property.equals("jeeeyul-tab-inner-border-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setInnerBorderColor(new HSB(color.getRGB()));
			folder.redraw();
			return true;
		}

		else if (property.equals("jeeeyul-tab-border-radius")) {
			if (value instanceof CSSPrimitiveValue) {
				int border = (int) ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
				kTabRenderer.getSettings().setBorderRadius(border);
				folder.redraw();
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		return null;
	}

}
