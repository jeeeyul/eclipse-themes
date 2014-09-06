package net.jeeeyul.eclipse.themes.internal;

import net.jeeeyul.eclipse.themes.css.internal.EditBoxTracker;
import net.jeeeyul.eclipse.themes.css.internal.NamedColorHack;

import org.eclipse.ui.IStartup;

public class StartUp implements IStartup {
	@Override
	public void earlyStartup() {
		EditBoxTracker.INSTANCE.beginTrack();
		NamedColorHack.INSTANCE.start();
	}
}
