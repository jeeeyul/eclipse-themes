package net.jeeeyul.eclipse.themes.ui.store;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

/**
 * Element Factory for {@link StoreEditorInput}
 * 
 * @author Jeeeyul
 */
public class StoreInputFactory implements IElementFactory {
	/**
	 * Element Factory ID
	 */
	public static final String ID = StoreInputFactory.class.getCanonicalName();

	@Override
	public IAdaptable createElement(IMemento memento) {
		return new StoreEditorInput();
	}

}
