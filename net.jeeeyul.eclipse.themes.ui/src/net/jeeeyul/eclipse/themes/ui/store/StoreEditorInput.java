package net.jeeeyul.eclipse.themes.ui.store;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

/**
 * editor input for {@link StoreClient}
 * 
 * @author Jeeeyul
 */
public class StoreEditorInput implements IEditorInput {
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Theme Store";
	}

	@Override
	public IPersistableElement getPersistable() {
		return new IPersistableElement() {
			@Override
			public void saveState(IMemento memento) {

			}

			@Override
			public String getFactoryId() {
				return StoreInputFactory.ID;
			}
		};
	}

	@Override
	public String getToolTipText() {
		return "Theme Store Client";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof StoreEditorInput;
	}

}
