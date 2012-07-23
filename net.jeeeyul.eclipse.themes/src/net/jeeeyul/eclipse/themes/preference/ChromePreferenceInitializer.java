package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class ChromePreferenceInitializer extends AbstractPreferenceInitializer {

	public ChromePreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault("chrome-active-hue", 221f);
		store.setDefault("chrome-active-saturation", .15f);
		store.setDefault("chrome-active-brightness", .96f);
	}

}
