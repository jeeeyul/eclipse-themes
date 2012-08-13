package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ui.Gradient;

import org.eclipse.jface.preference.IPreferenceStore;

public class ChromePreferenceExtensions {
	public void setValue(IPreferenceStore store, String key, Gradient gradient) {
		System.out.println(key);
		System.out.println(gradient.toString());
		store.setValue(key, gradient.toString());
	}

	public void setDefaultValue(IPreferenceStore store, String key, Gradient gradient) {
		store.setDefault(key, gradient.toString());
	}

	public Gradient getGradient(IPreferenceStore store, String key) {
		return Gradient.createFromString(store.getString(key));
	}
}
