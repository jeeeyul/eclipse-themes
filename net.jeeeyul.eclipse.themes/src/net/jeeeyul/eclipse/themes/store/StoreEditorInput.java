package net.jeeeyul.eclipse.themes.store;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * editor input for {@link StoreClient}
 * 
 * @author Jeeeyul
 */
public class StoreEditorInput implements IEditorInput {

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
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
		return null;
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
