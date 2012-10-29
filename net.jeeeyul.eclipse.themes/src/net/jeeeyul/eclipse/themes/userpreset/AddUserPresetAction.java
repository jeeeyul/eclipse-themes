package net.jeeeyul.eclipse.themes.userpreset;

import net.jeeeyul.eclipse.themes.Messages;
import net.jeeeyul.eclipse.themes.SharedImages;
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
		setText(Messages.ADD_USER_PRESET);
		setImageDescriptor(SharedImages.getImageDescriptor(SharedImages.ADD));
	}

	@Override
	public void run() {
		PreferenceStore store = new PreferenceStore();
		new ChromePreferenceInitializer().initializeDefaultPreferences(store);
		for (ChromePage eachPage : getPrefererncePage().getPages()) {
			eachPage.save(store);
		}
		InputDialog dialog = new InputDialog(getPrefererncePage().getShell(), "Jeeeyul's Eclipse Themes", Messages.ENTER_PRESET_NAME + ":", Messages.MY_PRESET, null); //$NON-NLS-1$
		if (dialog.open() == IDialogConstants.OK_ID) {
			UserPresetRepository.INSTANCE.addPreset(dialog.getValue(), store);
			UserPresetRepository.INSTANCE.save();
		}
	}
}
