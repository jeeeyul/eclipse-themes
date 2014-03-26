package net.jeeeyul.eclipse.themes.preference.actions;

import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;

import org.eclipse.jface.action.Action;

public class AbstractPreferenceAction extends Action {
	private JTPreferencePage page;

	public JTPreferencePage getPage() {
		return page;
	}

	public AbstractPreferenceAction(JTPreferencePage page) {
		this.page = page;
	}
	
	public AbstractPreferenceAction(JTPreferencePage page, String text, int style) {
		super(text, style);
		this.page = page;
	}

}
