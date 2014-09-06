package net.jeeeyul.eclipse.themes.css.internal.elements;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.internal.forms.widgets.FormHeading;
import org.w3c.dom.Element;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class ExtendedElementProvider implements IElementProvider {

	@Override
	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof FormHeading) {
			return new FormHeadingElement((FormHeading) element, engine);
		} else if (element instanceof Section) {
			return new SectionElement((Section) element, engine);
		} else if (element instanceof FormText) {
			return new FormTextElement((FormText) element, engine);
		}
		return null;
	}

}
