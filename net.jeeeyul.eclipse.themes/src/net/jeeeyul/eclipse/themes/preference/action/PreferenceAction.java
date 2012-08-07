package net.jeeeyul.eclipse.themes.preference.action;

import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;

import org.eclipse.jface.action.Action;

public class PreferenceAction extends Action {
	private ChromeThemePrefererncePage prefererncePage;

	public PreferenceAction(ChromeThemePrefererncePage prefererncePage) {
		this.prefererncePage = prefererncePage;
	}

	public ChromeThemePrefererncePage getPrefererncePage() {
		return prefererncePage;
	}
}
