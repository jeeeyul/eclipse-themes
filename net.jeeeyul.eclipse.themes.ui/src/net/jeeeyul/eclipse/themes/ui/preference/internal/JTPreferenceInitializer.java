package net.jeeeyul.eclipse.themes.ui.preference.internal;

import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.ui.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * loads default.epf file and sets its content to default settings for theme
 * plugin.
 * 
 * @author Jeeeyul
 * @since 2.0
 */
public class JTPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		initializeDefaultPreset(store);
		store.setDefault(JTPConstants.Memento.LAST_CHOOSED_PRESET, IJTPresetManager.DEFAULT_PRESET_ID);
	}

	/**
	 * Initialize given {@link JThemePreferenceStore}.
	 * 
	 * @param store
	 *            store to initialize.
	 */
	public void initializeDefaultPreset(JThemePreferenceStore store) {
		IJTPreset defaultPreset = JThemesCore.getDefault().getPresetManager().getDefaultPreset();
		if (defaultPreset == null) {
			throw new IllegalStateException("Default Preset is not found!");
		}

		Properties props = defaultPreset.getProperties();
		for (Object kObj : props.keySet()) {
			String key = (String) kObj;
			store.setDefault(key, props.getProperty(key));
		}
	}
}
