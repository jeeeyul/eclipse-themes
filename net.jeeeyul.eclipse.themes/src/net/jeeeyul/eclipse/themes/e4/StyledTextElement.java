package net.jeeeyul.eclipse.themes.e4;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.swt.custom.StyledText;

@SuppressWarnings("restriction")
public class StyledTextElement extends CompositeElement {

	public StyledTextElement(StyledText text, CSSEngine engine) {
		super(text, engine);
	}

}
