package net.jeeeyul.eclipse.themes.css.internal.handlers;

import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.ToolBarElement;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.w3c.dom.css.CSSValue;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class ToolBarCSSPropertyHandler implements ICSSPropertyHandler {

	@Override
	public boolean applyCSSProperty(Object element, String property, CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof ToolBarElement)) {
			return false;
		}

		ToolBarElement toolBarElement = (ToolBarElement) element;
		ToolBar toolBar = (ToolBar) toolBarElement.getNativeWidget();

		if (property.equals("jtool-item-color")) {
			Color color = (Color) engine.convert(value, Color.class, toolBar.getDisplay());
			toolBar.setForeground(color);

			ToolItem[] items = toolBar.getItems();
			for (ToolItem each : items) {
				String text = each.getText();
				each.setText("");
				each.setText(text);
			}

			return true;
		}

		return false;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property, String pseudo, CSSEngine engine) throws Exception {
		if (!(element instanceof ToolBarElement)) {
			return null;
		}
		
		ToolBarElement toolBarElement = (ToolBarElement) element;
		ToolBar toolBar = (ToolBar) toolBarElement.getNativeWidget();
		
		if(property.equals("jtool-item-color")){
			return new HSB(toolBar.getForeground().getRGB()).toHTMLCode();
		}

		return null;
	}

}
