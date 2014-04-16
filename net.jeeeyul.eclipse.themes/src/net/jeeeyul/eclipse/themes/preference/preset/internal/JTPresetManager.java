package net.jeeeyul.eclipse.themes.preference.preset.internal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager;
import net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManagerListener;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

@SuppressWarnings("javadoc")
public class JTPresetManager implements IJTPresetManager {
	private HashSet<IJTPresetManagerListener> listeners = new HashSet<IJTPresetManagerListener>();

	private List<ContributedPreset> contributedPresets;
	private List<UserPreset> userPresets;

	public JTPresetManager() {
	}

	public void addListener(IJTPresetManagerListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager#
	 * getContributedPresets()
	 */
	@Override
	public List<ContributedPreset> getContributedPresets() {
		if (contributedPresets == null) {
			loadPresetExtensions();
		}
		return contributedPresets;
	}

	public ContributedPreset getDefaultPreset() {
		if (contributedPresets == null) {
			loadPresetExtensions();
		}
		for (ContributedPreset each : contributedPresets) {
			if (DEFAULT_PRESET_ID.equals(each.getId())) {
				return each;
			}
		}
		return null;
	}

	private Comparator<IJTPreset> getPresetComparator() {
		return new Comparator<IJTPreset>() {
			@Override
			public int compare(IJTPreset o1, IJTPreset o2) {
				return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager#
	 * getUserPresetFolder()
	 */
	@Override
	public File getUserPresetFolder() {
		IPath path = JThemesCore.getDefault().getStateLocation();
		File stateFolder = path.toFile();
		File presetFolder = new File(stateFolder, "user-presets");
		if (presetFolder.exists() && !presetFolder.isDirectory()) {
			presetFolder.delete();
		}
		if (!presetFolder.exists()) {
			presetFolder.mkdir();
		}
		return presetFolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.jeeeyul.eclipse.themes.preference.preset.IJTPresetManager#getUserPresets
	 * ()
	 */
	@Override
	public List<UserPreset> getUserPresets() {
		if (userPresets == null) {
			loadUserPresets();
		}
		return userPresets;
	}

	@Override
	public void invalidateUserPreset() {
		if (userPresets != null) {
			userPresets.clear();
			userPresets = null;
		}

		for (IJTPresetManagerListener each : listeners) {
			try {
				each.userPresetModified();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void loadPresetExtensions() {
		contributedPresets = new ArrayList<ContributedPreset>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getExtensionPoint(ContributedPreset.EXTENSION_POINT).getConfigurationElements();
		for (IConfigurationElement each : elements) {
			if (each.getName().equals(ContributedPreset.ELEMENT_PRESET)) {
				contributedPresets.add(new ContributedPreset(each));
			}
		}

		Collections.sort(contributedPresets, getPresetComparator());
		ContributedPreset defaultPreset = getDefaultPreset();
		if (defaultPreset != null) {
			contributedPresets.remove(defaultPreset);
			contributedPresets.add(0, defaultPreset);
		}
	}

	private void loadUserPresets() {
		userPresets = new ArrayList<UserPreset>();
		File[] files = getUserPresetFolder().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".epf");
			}
		});

		for (File each : files) {
			try {
				userPresets.add(new UserPreset(each));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Collections.sort(userPresets, getPresetComparator());
	}

	public void removeListener(IJTPresetManagerListener listener) {
		listeners.remove(listener);
	}
}
