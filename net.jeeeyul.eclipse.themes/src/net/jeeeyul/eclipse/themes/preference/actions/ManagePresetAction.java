package net.jeeeyul.eclipse.themes.preference.actions;

import org.eclipse.ui.dialogs.PreferencesUtil;

import net.jeeeyul.eclipse.themes.preference.JTPreperencePage;
import net.jeeeyul.eclipse.themes.preference.preset.JTPresetPreferencePage;

public class ManagePresetAction extends AbstractPreferenceAction {

	public ManagePresetAction(JTPreperencePage page) {
		super(page);
		setText("Mange Presets...");
	}
	
	@Override
	public void run() {
		PreferencesUtil.createPreferenceDialogOn(getPage().getShell(), JTPresetPreferencePage.ID, null, null);
	}

}
