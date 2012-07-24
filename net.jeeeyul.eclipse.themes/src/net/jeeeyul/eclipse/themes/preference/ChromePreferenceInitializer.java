package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
public class ChromePreferenceInitializer extends AbstractPreferenceInitializer {

	public ChromePreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("chrome-active-start-hue", 221f);
		store.setDefault("chrome-active-start-saturation", .10f);
		store.setDefault("chrome-active-start-brightness", .97f);

		store.setDefault("chrome-active-end-hue", 222f);
		store.setDefault("chrome-active-end-saturation", .15f);
		store.setDefault("chrome-active-end-brightness", .96f);

		store.setDefault("chrome-auto-end-color", true);
		store.setDefault("chrome-lock-hue", true);
	}

}
