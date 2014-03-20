package net.jeeeyul.eclipse.themes.preference.actions;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.preference.preset.internal.JTPresetPreferencePage;

import org.eclipse.ui.dialogs.PreferencesUtil;

public class ManagePresetAction extends AbstractPreferenceAction {

	public ManagePresetAction(JTPreferencePage page) {
		super(page);
		setText("Mange Presets...");
		setImageDescriptor(SharedImages.getImageDescriptor(SharedImages.CONFIG));
	}
	
	@Override
	public void run() {
		PreferencesUtil.createPreferenceDialogOn(getPage().getShell(), JTPresetPreferencePage.ID, null, null);
	}

}
