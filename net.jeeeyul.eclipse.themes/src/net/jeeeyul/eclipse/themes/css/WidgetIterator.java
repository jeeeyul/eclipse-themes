package net.jeeeyul.eclipse.themes.css;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public class WidgetIterator extends AbstractTreeIterator<Widget> {

	private static final long serialVersionUID = -6984374341869788574L;

	public WidgetIterator(Widget object, boolean includeRoot) {
		super(object, includeRoot);
	}

	public WidgetIterator(Widget object) {
		super(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Iterator<? extends Widget> getChildren(Object object) {
		if (object instanceof Composite) {
			return Arrays.asList(((Composite) object).getChildren()).iterator();
		}
		return Collections.EMPTY_LIST.iterator();
	}

}
