package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ui.Gradient;
import net.jeeeyul.eclipse.themes.ui.HSB;

import org.eclipse.jface.preference.IPreferenceStore;

public class ChromePreferenceExtensions {
	public void setValue(IPreferenceStore store, String key, Gradient gradient) {
		store.setValue(key, gradient.serialize());
	}

	public void setDefaultValue(IPreferenceStore store, String key, Gradient gradient) {
		store.setDefault(key, gradient.serialize());
	}

	public Gradient getGradient(IPreferenceStore store, String key) {
		return Gradient.createFromString(store.getString(key));
	}

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
