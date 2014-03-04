package net.jeeeyul.eclipse.themes.preference;

import java.io.IOException;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

public class JTPreferenceInitializer extends AbstractPreferenceInitializer {

	public JTPreferenceInitializer() {

	}

	@Override
	public void initializeDefaultPreferences() {
		Properties properties = new Properties();
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		try {
			properties.load(getClass().getResourceAsStream("default.epf"));
			for (Object kObj : properties.keySet()) {
				String key = (String) kObj;
				store.setDefault(key, properties.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
