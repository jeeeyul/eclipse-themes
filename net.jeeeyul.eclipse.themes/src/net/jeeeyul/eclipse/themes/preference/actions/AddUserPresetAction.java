package net.jeeeyul.eclipse.themes.preference.actions;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.preference.preset.internal.UserPreset;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;

/**
 * Saves a current preference settings to {@link UserPreset}.
 * 
 * @author Jeeeyul
 */
public class AddUserPresetAction extends AbstractPreferenceAction {

	/**
	 * @param page
	 */
	public AddUserPresetAction(JTPreferencePage page) {
		super(page);
		setText("Save as new preset");
		setImageDescriptor(SharedImages.getImageDescriptor(SharedImages.ADD));
	}

	@Override
	public void run() {

		InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "New Preset", "Enter a new preset name:", null,
				JTPUtil.getPresetNameValidator());
		if (dialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		UserPreset userPreset = new UserPreset(dialog.getValue().trim());
		Properties presetProps = userPreset.getProperties();
		JThemePreferenceStore store = new JThemePreferenceStore(new PreferenceStore());
		getPage().saveTo(store);

		List<String> presetKeys = JTPUtil.listPreferenceKeys(JTPUtil.FILTER_PRESET);
		for (String key : presetKeys) {
			presetProps.setProperty(key, store.getString(key));
		}

		try {
			userPreset.save();
		} catch (IOException e) {
			e.printStackTrace();
		}

		getPage().getPresetManager().invalidateUserPreset();
	}
}
