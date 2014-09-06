package net.jeeeyul.eclipse.themes.css.internal.elements;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.ui.internal.forms.widgets.FormHeading;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class FormHeadingElement extends CompositeElement {

	/**
	 * @param formHeading
	 * @param engine
	 */
	public FormHeadingElement(FormHeading formHeading, CSSEngine engine) {
		super(formHeading, engine);
	}

}
