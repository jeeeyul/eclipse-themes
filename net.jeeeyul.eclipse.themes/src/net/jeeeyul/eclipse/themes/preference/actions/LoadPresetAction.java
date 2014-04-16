package net.jeeeyul.eclipse.themes.preference.actions;

import java.util.Properties;

import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;
import net.jeeeyul.eclipse.themes.preference.internal.JTPreferencePage;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;
import net.jeeeyul.swtend.SWTExtensions;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;

/**
 * Loads preset to prefernce page.
 * 
 * @author Jeeeyul
 */
public class LoadPresetAction extends AbstractPreferenceAction {
	private IJTPreset preset;

	/**
	 * Creates {@link LoadPresetAction}
	 * 
	 * @param root
	 *            preference page.
	 * @param preset
	 *            preset to load.
	 */
	public LoadPresetAction(JTPreferencePage root, IJTPreset preset) {
		super(root, preset.getName(), SWT.CHECK);
		this.preset = preset;
		this.setImageDescriptor(preset.getImageDescriptor());

		/*
		 * https://github.com/jeeeyul/eclipse-themes/issues/140
		 */
		String lastChoosedPresetId = root.getLastChoosedPresetId();
		if (lastChoosedPresetId == null) {
			lastChoosedPresetId = root.getPreferenceStore().getString(JTPConstants.Memento.LAST_CHOOSED_PRESET);
		}
		setChecked(preset.getId().equals(lastChoosedPresetId));
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
				store.setValue(key, Math.max(intValue, SWTExtensions.INSTANCE.getMinimumToolBarHeight()));
			} else {
				store.setValue(key, value);
			}
		}

		JThemePreferenceStore jtStore = new JThemePreferenceStore(store);
		getPage().loadFrom(jtStore);

		/*
		 * https://github.com/jeeeyul/eclipse-themes/issues/140
		 */
		getPage().setLastChoosedPresetId(preset.getId());
	}
}
