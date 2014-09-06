package net.jeeeyul.eclipse.themes.css.internal.elements;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.ui.forms.widgets.Section;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings({ "restriction" })
public class SectionElement extends CompositeElement {

	/**
	 * @param section
	 * @param engine
	 */
	public SectionElement(Section section, CSSEngine engine) {
		super(section, engine);
	}

}
