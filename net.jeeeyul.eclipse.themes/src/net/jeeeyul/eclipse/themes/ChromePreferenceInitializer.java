package net.jeeeyul.eclipse.themes;

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
		
		store.setDefault("chrome-inactive-hue", 22f);
		store.setDefault("chrome-inactive-saturation", 0f);
		store.setDefault("chrome-inactive-brightness", .93f);
	}

}
