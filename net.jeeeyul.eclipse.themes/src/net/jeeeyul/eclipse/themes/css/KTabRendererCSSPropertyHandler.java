package net.jeeeyul.eclipse.themes.css;

import net.jeeeyul.eclipse.themes.rendering.KTabRenderer;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.e4.ui.css.core.dom.properties.Gradient;
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CTabFolderElement;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
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

		else if (property.equals("jeeeyul-tab-selected-border-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setSelectedBorderColor(new HSB(color.getRGB()));
			folder.redraw();
			return true;
		}
		
		else if (property.equals("jeeeyul-tab-unselected-border-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setUnselectedBorderColor(new HSB(color.getRGB()));
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

		else if (property.equals("jeeeyul-tab-border-width")) {
			if (value instanceof CSSPrimitiveValue) {
				int borderWidth = (int) ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
				kTabRenderer.getSettings().setBorderWidth(borderWidth);
				folder.redraw();
			} else {
				return false;
			}
		}

		else if (property.equals("jeeeyul-tab-spacing")) {
			if (value instanceof CSSPrimitiveValue) {
				int spacing = (int) ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
				kTabRenderer.getSettings().setTabSpacing(spacing);
				folder.redraw();
			} else {
				return false;
			}
		}

		else if (property.equals("jeeeyul-tab-close-button-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setCloseButtonColor(new HSB(color.getRGB()));
			folder.redraw();
			return true;
		}

		else if (property.equals("jeeeyul-tab-close-button-hot-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setCloseButtonHotColor(new HSB(color.getRGB()));
			folder.redraw();
			return true;
		}

		else if (property.equals("jeeeyul-tab-close-button-active-color")) {
			Color color = (Color) engine.convert(value, Color.class, folder.getDisplay());
			if (color == null) {
				color = folder.getDisplay().getSystemColor(SWT.COLOR_BLACK);
			}
			kTabRenderer.getSettings().setCloseButtonActiveColor(new HSB(color.getRGB()));
			folder.redraw();
			return true;
		}

		else if (property.equals("jeeeyul-tab-close-button-line-width")) {
			if (value instanceof CSSPrimitiveValue) {
				int lineWidth = (int) ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);
				kTabRenderer.getSettings().setCloseButtonLineWidth(lineWidth);
				folder.redraw();
			} else {
				return false;
			}
		}

		else if (property.equals("jeeeyul-tab-unselected-tabs-background")) {
			if (value.getCssValueType() == CSSValue.CSS_VALUE_LIST) {
				Gradient grad = (Gradient) engine.convert(value, Gradient.class, folder.getDisplay());
				HSB[] hsb = new HSB[grad.getRGBs().size()];

				for (int i = 0; i < hsb.length; i++) {
					RGB rgb = (RGB) grad.getRGBs().get(i);
					hsb[i] = new HSB(rgb);
				}
				int[] percents = CSSSWTColorHelper.getPercents(grad);
				kTabRenderer.getSettings().setUnselectedBackgroundColors(hsb);
				kTabRenderer.getSettings().setUnselectedBackgroundPercents(percents);
				folder.redraw();
				return true;
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
