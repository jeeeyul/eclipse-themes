package net.jeeeyul.eclipse.themes.ui.preference.internal;

import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;

/**
 * Prevent closing tab item in preview.
 * 
 * @author Jeeeyul
 */
public class ClosePrevent extends CTabFolder2Adapter {

	@Override
	public void close(CTabFolderEvent event) {
		event.doit = false;
	}

}
