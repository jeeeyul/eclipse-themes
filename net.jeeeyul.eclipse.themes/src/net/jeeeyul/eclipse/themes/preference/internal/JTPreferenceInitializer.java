package net.jeeeyul.eclipse.themes.preference.internal;

import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * loads default.epf file and sets its content to default settings for theme
 * plugin.
 * 
 * @author Jeeeyul
 * @since 2.0
 */
public class JTPreferenceInitializer extends AbstractPreferenceInitializer {
	public JTPreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
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
