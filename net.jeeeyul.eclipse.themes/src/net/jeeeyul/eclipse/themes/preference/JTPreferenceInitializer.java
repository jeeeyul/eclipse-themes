package net.jeeeyul.eclipse.themes.preference;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import net.jeeeyul.eclipse.themes.JThemesCore;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * loads default.epf file and sets its content to default settings for theme
 * plugin.
 * 
 * @author Jeeeyul
 * @since 2.0.0
 */
public class JTPreferenceInitializer extends AbstractPreferenceInitializer {
	public JTPreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		Properties properties = new Properties();
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		try {
			URL resource = JThemesCore.getDefault().getBundle().getResource("presets/default.epf");
			InputStream is = resource.openStream();
			properties.load(is);
			is.close();
			for (Object kObj : properties.keySet()) {
				String key = (String) kObj;
				store.setDefault(key, properties.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
