package net.jeeeyul.eclipse.themes.ui.preference.actions;

import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;

import org.eclipse.jface.action.Action;

/**
 * Abstract class for {@link Action} that related with {@link JTPreferencePage}.
 * 
 * @author Jeeeyul
 */
public class AbstractPreferenceAction extends Action {
	private JTPreferencePage page;

	/**
	 * @return Belonging {@link JTPreferencePage}.
	 */
	public JTPreferencePage getPage() {
		return page;
	}

	/**
	 * Creates a {@link AbstractPreferenceAction}.
	 * 
	 * @param page
	 *            preference page.
	 */
	public AbstractPreferenceAction(JTPreferencePage page) {
		this.page = page;
	}

	/**
	 * Creates a {@link AbstractPreferenceAction}.
	 * 
	 * @param page
	 *            Preference page.
	 * @param text
	 *            name of the action.
	 * @param style
	 *            style of the action.
	 */
	public AbstractPreferenceAction(JTPreferencePage page, String text, int style) {
		super(text, style);
		this.page = page;
	}

}
