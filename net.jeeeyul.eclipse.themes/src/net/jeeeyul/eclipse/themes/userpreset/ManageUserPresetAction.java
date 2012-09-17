package net.jeeeyul.eclipse.themes.userpreset;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;

import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;
import net.jeeeyul.eclipse.themes.preference.action.PreferenceAction;

public class ManageUserPresetAction extends PreferenceAction {

	public ManageUserPresetAction(ChromeThemePrefererncePage prefererncePage) {
		super(prefererncePage);
		setText("Manage user presets");
	}

	@Override
	public void run() {
		List<UserPreset> workingCopy = new ArrayList<UserPreset>(UserPresetRepository.INSTANCE.getUserPresets(true));
		UserPresetDialog dialog = new UserPresetDialog(getPrefererncePage().getShell(), workingCopy);

		if (dialog.open() == IDialogConstants.OK_ID) {
			UserPresetRepository repo = UserPresetRepository.INSTANCE;
			repo.getUserPresets().clear();
			repo.getUserPresets().addAll(dialog.getWorkingCopy());
			repo.save();
		}
	}
}
