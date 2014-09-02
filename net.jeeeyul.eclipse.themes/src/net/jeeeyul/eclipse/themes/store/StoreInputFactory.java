package net.jeeeyul.eclipse.themes.store;

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
	public static final String ID = "net.jeeeyul.eclipse.themes.store.input.factory";

	@Override
	public IAdaptable createElement(IMemento memento) {
		return new StoreEditorInput();
	}

}
