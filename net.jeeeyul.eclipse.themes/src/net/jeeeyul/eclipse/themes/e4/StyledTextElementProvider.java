package net.jeeeyul.eclipse.themes.e4;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.custom.StyledText;
import org.w3c.dom.Element;

@SuppressWarnings("restriction")
public class StyledTextElementProvider implements IElementProvider {

	public StyledTextElementProvider() {
	}

	@Override
	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof StyledText) {
			return new StyledTextElement((StyledText) element, engine);
		}
		return null;
	}
}
