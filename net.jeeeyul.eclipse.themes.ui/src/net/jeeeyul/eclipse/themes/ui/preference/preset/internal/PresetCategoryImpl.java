package net.jeeeyul.eclipse.themes.ui.preference.preset.internal;

import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPreset;
import net.jeeeyul.eclipse.themes.ui.preference.preset.IJTPresetCategory;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * 
 * @author Jeeeyul
 */
public class PresetCategoryImpl implements IJTPresetCategory {
	/**
	 * Element Node Name for preset-category contribution.
	 */
	public static final String ELEMENT_PRESET_CATEGORY = "preset-category";
	private static final String ATTR_NAME = "name";
	private static final String ATTR_ID = "id";

	private String name;
	private String id;
	private ArrayList<IJTPreset> presets = new ArrayList<IJTPreset>();

	/**
	 * Creates a new {@link IJTPresetCategory} from extension.
	 * 
	 * @param configurationElement
	 */
	public PresetCategoryImpl(IConfigurationElement configurationElement) {
		this.name = configurationElement.getAttribute(ATTR_NAME);
		this.id = configurationElement.getAttribute(ATTR_ID);
	}

	/**
	 * Creates a preset category.
	 * 
	 * @param name
	 * @param id
	 */
	public PresetCategoryImpl(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	@Override
	public List<IJTPreset> getPresets() {
		return presets;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return id;
	}
}
