package net.jeeeyul.eclipse.themes.userpreset;

import net.jeeeyul.eclipse.themes.preference.ChromePage;
import net.jeeeyul.eclipse.themes.preference.ChromePreferenceInitializer;
import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;
import net.jeeeyul.eclipse.themes.preference.action.PreferenceAction;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferenceStore;

public class AddUserPresetAction extends PreferenceAction {

	public AddUserPresetAction(ChromeThemePrefererncePage prefererncePage) {
		super(prefererncePage);
		setText("Adds an user preset with current settings");
	}

	@Override
	public void run() {
		PreferenceStore store = new PreferenceStore();
		new ChromePreferenceInitializer().initializeDefaultPreferences(store);
		for (ChromePage eachPage : getPrefererncePage().getPages()) {
			eachPage.save(store);
		}
		InputDialog dialog = new InputDialog(getPrefererncePage().getShell(), "Jeeeyul's Eclipse Themes", "Enter a preset's name:", "My preset", null);
		if (dialog.open() == IDialogConstants.OK_ID) {
			UserPresetRepository.INSTANCE.addPreset(dialog.getValue(), store);
			UserPresetRepository.INSTANCE.save();
		}
	}
}
