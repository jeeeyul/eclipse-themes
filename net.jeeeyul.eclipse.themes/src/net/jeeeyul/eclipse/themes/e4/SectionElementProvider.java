package net.jeeeyul.eclipse.themes.e4;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Element;

@SuppressWarnings("restriction")
public class SectionElementProvider implements IElementProvider {

	@Override
	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof Section) {
			return new SectionElement((Composite) element, engine);
		}
		return null;
	}

}
