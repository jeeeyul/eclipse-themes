package net.jeeeyul.eclipse.themes.preference.actions;

import java.io.IOException;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.preference.internal.UserPreset;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;

public class AddUserPresetAction extends AbstractPreferenceAction {

	public AddUserPresetAction(JTPreferencePage page) {
		super(page);
		setText("Save as new preset");
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

		for (String key : JTPUtil.listPreferenceKeys()) {
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
