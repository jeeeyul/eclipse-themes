package net.jeeeyul.eclipse.themes.preference.actions;

import java.util.Properties;

import org.eclipse.jface.preference.PreferenceStore;

import net.jeeeyul.eclipse.themes.preference.JTPreperencePage;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;

public class LoadPresetAction extends AbstractPreferenceAction {
	private IJTPreset preset;

	public LoadPresetAction(JTPreperencePage root, IJTPreset preset) {
		super(root);
		this.preset = preset;
		this.setText(preset.getName());
	}

	@Override
	public void run() {
		PreferenceStore store = new PreferenceStore();
		Properties props = preset.getProperties();
		for (Object kObj : props.keySet()) {
			String key = (String) kObj;
			store.setValue(key, props.getProperty(key));
		}

		JThemePreferenceStore jtStore = new JThemePreferenceStore(store);
		getPage().loadFrom(jtStore);

	}
}
