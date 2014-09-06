package net.jeeeyul.eclipse.themes.ui.preference.preset.internal;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManagerListener;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

@SuppressWarnings("javadoc")
public class JTPresetManager implements IJTPresetManager {
	private HashSet<IJTPresetManagerListener> listeners = new HashSet<IJTPresetManagerListener>();

	private Map<String, IJTPresetCategory> categories;
	private IJTPresetCategory defaultCategory;
	private IJTPresetCategory userCategory;
	private boolean isLoaded = false;
	private boolean needToLoadUserCategory = true;

	public JTPresetManager() {
		categories = new HashMap<String, IJTPresetCategory>();
		defaultCategory = new PresetCategoryImpl("Default Presets", IJTPresetCategory.DEFAULT_CATEGORY_ID);
		userCategory = new PresetCategoryImpl("User Presets", IJTPresetCategory.USER_CATEGORY_ID);

		categories.put(defaultCategory.getID(), defaultCategory);
		categories.put(userCategory.getID(), userCategory);
	}

	public void addListener(IJTPresetManagerListener listener) {
		listeners.add(listener);
	}

	public IJTPresetCategory getDefaultCategory() {
		if (!isLoaded) {
			loadPresetExtensions();
		}
		return defaultCategory;
	}

	public IJTPreset getDefaultPreset() {
		if (!isLoaded) {
			loadPresetExtensions();
		}
		for (IJTPreset each : getDefaultCategory().getPresets()) {
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

	public IJTPresetCategory getUserCategory() {
		if (needToLoadUserCategory) {
			loadUserPresets();
		}
		return userCategory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetManager#
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

	@Override
	public void invalidateUserPreset() {
		if (userCategory.getPresets() != null) {
			userCategory.getPresets().clear();
			needToLoadUserCategory = true;
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
		if (isLoaded) {
			return;
		}
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(ContributedPreset.EXTENSION_POINT);

		IConfigurationElement[] elements = extensionPoint.getConfigurationElements();
		for (IConfigurationElement each : elements) {
			if (each.getName().equals(PresetCategoryImpl.ELEMENT_PRESET_CATEGORY)) {
				PresetCategoryImpl category = new PresetCategoryImpl(each);
				categories.put(category.getID(), category);
			}
		}

		for (IConfigurationElement each : elements) {
			if (each.getName().equals(ContributedPreset.ELEMENT_PRESET)) {
				ContributedPreset preset = new ContributedPreset(each);
				IJTPresetCategory category = categories.get(preset.getCategoryID());
				category.getPresets().add(preset);
			}
		}
		isLoaded = true;

		for (IJTPresetCategory eachCategory : categories.values()) {
			Collections.sort(eachCategory.getPresets(), getPresetComparator());
		}

		IJTPreset defaultPreset = getDefaultPreset();
		if (defaultPreset != null) {
			defaultCategory.getPresets().remove(defaultPreset);
			defaultCategory.getPresets().add(0, defaultPreset);
		}
	}

	public IJTPresetCategory getCategory(String id) {
		if (id.equals(IJTPresetCategory.USER_CATEGORY_ID)) {
			return getUserCategory();
		}

		if (!isLoaded) {
			loadPresetExtensions();
		}

		return categories.get(id);
	}

	private void loadUserPresets() {
		File[] files = getUserPresetFolder().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".epf");
			}
		});

		for (File each : files) {
			try {
				userCategory.getPresets().add(new UserPreset(each));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Collections.sort(userCategory.getPresets(), getPresetComparator());
		needToLoadUserCategory = false;
	}

	public void removeListener(IJTPresetManagerListener listener) {
		listeners.remove(listener);
	}

	@Override
	public IJTPresetCategory[] getCategories() {
		if (!isLoaded) {
			loadPresetExtensions();
		}
		if (needToLoadUserCategory) {
			loadUserPresets();
		}

		ArrayList<IJTPresetCategory> values = new ArrayList<IJTPresetCategory>(categories.values());
		Collections.sort(values, new Comparator<IJTPresetCategory>() {
			@Override
			public int compare(IJTPresetCategory o1, IJTPresetCategory o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		values.remove(userCategory);
		values.remove(defaultCategory);
		values.add(0, defaultCategory);
		values.add(0, userCategory);

		IJTPresetCategory[] result = values.toArray(new IJTPresetCategory[categories.size()]);
		return result;
	}
}
