package net.jeeeyul.eclipse.themes.ui.preference.internal;

import net.jeeeyul.eclipse.themes.rendering.JeeeyulsTabRenderer;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.widgets.Composite;

/**
 * Using dark theme force to use CTabRendering for preview, And it will crash
 * when users open preference dialog. This class provides that prevent it.
 * 
 * @author Jeeeyul
 */
public class PreviewTabFolder extends CTabFolder {
	/**
	 * @param parent
	 * @param style
	 */
	public PreviewTabFolder(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void setRenderer(CTabFolderRenderer renderer) {
		if (!(renderer instanceof JeeeyulsTabRenderer)) {
			return;
		}
		super.setRenderer(renderer);
	}

}
