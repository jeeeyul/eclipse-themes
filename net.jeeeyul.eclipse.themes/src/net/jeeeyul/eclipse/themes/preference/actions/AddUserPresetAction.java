package net.jeeeyul.eclipse.themes.preference.actions;

import java.io.IOException;
import java.util.Properties;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;

import net.jeeeyul.eclipse.themes.preference.JTPreperencePage;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.preference.internal.UserPreset;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager;

public class AddUserPresetAction extends AbstractPreferenceAction {

	public AddUserPresetAction(JTPreperencePage page) {
		super(page);
		setText("Save as new preset");
	}

	@Override
	public void run() {
		IInputValidator presetNameValidator = new IInputValidator() {
			@Override
			public String isValid(String newText) {
				if (newText.trim().length() == 0) {
					return "Not entered.";
				}

				IJTPresetManager presetManager = getPage().getPresetManager();
				for (UserPreset each : presetManager.getUserPresets()) {
					if (each.getName().equalsIgnoreCase(newText)) {
						return "Already exist name";
					}
				}

				if (!UserPreset.isSafeName(newText)) {
					return "Unsafe characters are contained.";
				}
				return null;
			}
		};

		InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "New Preset", "Enter a new preset name:", null, presetNameValidator);
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
