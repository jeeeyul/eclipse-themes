package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.jface.preference.IPreferenceStore;

public class ChromePreferenceExtensions {

	public HSB getHSB(IPreferenceStore store, String key) {
		return HSB.deserialize(store.getString(key));
	}

	public HSB getDefaultHSB(IPreferenceStore store, String key) {
		return HSB.deserialize(store.getDefaultString(key));
	}

	public void setDefaultValue(IPreferenceStore store, String key, HSB value) {
		store.setDefault(key, value.serialize());
	}

	public void setValue(IPreferenceStore store, String key, HSB value) {
		store.setValue(key, value.serialize());
	}
}
