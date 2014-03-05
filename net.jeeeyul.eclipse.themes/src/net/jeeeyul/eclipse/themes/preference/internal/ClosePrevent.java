package net.jeeeyul.eclipse.themes.preference.internal;

import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;

public class ClosePrevent extends CTabFolder2Adapter {

	public ClosePrevent() {
	}

	@Override
	public void close(CTabFolderEvent event) {
		event.doit = false;
	}

}
