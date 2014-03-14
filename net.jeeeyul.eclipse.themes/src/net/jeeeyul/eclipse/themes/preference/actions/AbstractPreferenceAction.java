package net.jeeeyul.eclipse.themes.preference.actions;

import net.jeeeyul.eclipse.themes.preference.internal.JTPreperencePage;

import org.eclipse.jface.action.Action;

public class AbstractPreferenceAction extends Action {
	private JTPreperencePage page;

	public JTPreperencePage getPage() {
		return page;
	}

	public AbstractPreferenceAction(JTPreperencePage page) {
		this.page = page;
	}

}
