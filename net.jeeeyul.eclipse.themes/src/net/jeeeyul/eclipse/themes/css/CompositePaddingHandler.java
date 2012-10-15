package net.jeeeyul.eclipse.themes.css;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.swt.widgets.Composite;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class CompositePaddingHandler implements ICSSPropertyHandler {

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof CompositeElement)) {
			return false;
		}
		CompositeElement compositeElement = (CompositeElement) element;
		Composite comp = (Composite) compositeElement.getNativeWidget();
		int pixelValue = (int) ((CSSPrimitiveValue) value).getFloatValue(CSSPrimitiveValue.CSS_PX);

		if (property.equals("chrome-padding-top")) {
			ChromePadder.get(comp).setTop(pixelValue);
			return true;
		}

		if (property.equals("chrome-padding-left")) {
			ChromePadder.get(comp).setLeft(pixelValue);
			return true;
		}

		if (property.equals("chrome-padding-right")) {
			ChromePadder.get(comp).setRight(pixelValue);
			return true;
		}

		if (property.equals("chrome-padding-bottom")) {
			ChromePadder.get(comp).setBottom(pixelValue);
			return true;
		}

		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof CompositeElement)) {
			return null;
		}
		CompositeElement compositeElement = (CompositeElement) element;
		Composite comp = (Composite) compositeElement.getNativeWidget();
		if (!ChromePadder.exists(comp)) {
			return null;
		}

		if (property.equals("chrome-padding-top")) {
			return Integer.toString(ChromePadder.get(comp).getTop());
		}

		if (property.equals("chrome-padding-left")) {
			return Integer.toString(ChromePadder.get(comp).getLeft());
		}

		if (property.equals("chrome-padding-right")) {
			return Integer.toString(ChromePadder.get(comp).getRight());
		}

		if (property.equals("chrome-padding-bottom")) {
			return Integer.toString(ChromePadder.get(comp).getBottom());
		}

		return null;
	}

}
