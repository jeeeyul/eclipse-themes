package net.jeeeyul.eclipse.themes.preference.internal;

import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;

public class ClosePrevent implements CTabFolder2Listener {

	public ClosePrevent() {
	}

	@Override
	public void close(CTabFolderEvent event) {
		event.doit = false;
	}

	@Override
	public void minimize(CTabFolderEvent event) {
	}

	@Override
	public void maximize(CTabFolderEvent event) {
	}

	@Override
	public void restore(CTabFolderEvent event) {
	}

	@Override
	public void showList(CTabFolderEvent event) {
	}

}
