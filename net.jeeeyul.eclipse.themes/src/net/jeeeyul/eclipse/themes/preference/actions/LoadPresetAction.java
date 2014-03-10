package net.jeeeyul.eclipse.themes.preference.actions;

import java.util.Properties;

import net.jeeeyul.eclipse.themes.internal.OSHelper;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.JTPreperencePage;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;

import org.eclipse.jface.preference.PreferenceStore;

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
			String value = props.getProperty(key);

			if (key.equals(JTPConstants.Layout.TAB_HEIGHT)) {
				int intValue = Integer.parseInt(value);
				store.setValue(key, Math.max(intValue, OSHelper.INSTANCE.getMinimumTabHeight()));
			} else {
				store.setValue(key, value);
			}
		}

		JThemePreferenceStore jtStore = new JThemePreferenceStore(store);
		getPage().loadFrom(jtStore);
	}
}
