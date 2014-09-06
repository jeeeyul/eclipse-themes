package net.jeeeyul.eclipse.themes.ui.preference.actions;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.SharedImages;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.internal.IPreferenceFilter;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPUtil;
import net.jeeeyul.eclipse.themes.ui.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.ui.preference.preset.internal.UserPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.internal.UserPresetDialog;

import org.eclipse.jface.dialogs.IDialogConstants;
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

		UserPresetDialog dialog = new UserPresetDialog(Display.getDefault().getActiveShell());
		
		if (dialog.open() == IDialogConstants.OK_ID) {
			String name = dialog.getSelectedPresetName();
			UserPreset userPreset = new UserPreset(name);
			Properties presetProps = userPreset.getProperties();
			JThemePreferenceStore store = new JThemePreferenceStore(new PreferenceStore());
			getPage().saveTo(store);

			List<String> presetKeys = JTPUtil.listPreferenceKeys(IPreferenceFilter.FILTER_PRESET);
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
}
