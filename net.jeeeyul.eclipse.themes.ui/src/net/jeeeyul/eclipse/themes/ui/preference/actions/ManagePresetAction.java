package net.jeeeyul.eclipse.themes.ui.preference.actions;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.preset.internal.JTPresetPreferencePage;

import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * Let user jump to manage preset page.
 * 
 * @author Jeeeyul
 */
public class ManagePresetAction extends AbstractPreferenceAction {

	/**
	 * Creates {@link ManagePresetAction}.
	 * 
	 * @param page
	 *            preference page.
	 */
	public ManagePresetAction(JTPreferencePage page) {
		super(page);
		setText("Manage Presets...");
		setImageDescriptor(SharedImages.getImageDescriptor(SharedImages.CONFIG));
	}

	@Override
	public void run() {
		PreferencesUtil.createPreferenceDialogOn(getPage().getShell(), JTPresetPreferencePage.ID, null, null);
	}

}
