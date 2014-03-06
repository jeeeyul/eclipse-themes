package net.jeeeyul.eclipse.themes.preference.preset;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class JTPresetManager {
	private List<ContributedPreset> contributedPresets;

	public JTPresetManager() {
	}

	public List<ContributedPreset> getContributedPresets() {
		if (contributedPresets == null) {
			loadPresetExtensions();
		}
		return contributedPresets;
	}

	private void loadPresetExtensions() {
		contributedPresets = new ArrayList<ContributedPreset>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getExtensionPoint(ContributedPreset.EXTENSION_POINT).getConfigurationElements();
		for (IConfigurationElement each : elements) {
			if (each.getName().equals(ContributedPreset.ELEMENT_PRESET)) {
				contributedPresets.add(new ContributedPreset(each));
			}
		}
	}

}
