package net.jeeeyul.eclipse.themes.ui.preference.preset;

import java.io.File;

/**
 * @since 2.1
 */
public interface IJTPresetManager {
	/**
	 * The id of default preset.
	 */
	public static final String DEFAULT_PRESET_ID = "net.jeeeyul.eclipse.themes.preset.default";

	/**
	 * 
	 * @return A java {@link File} that contains user preset files.
	 */
	public abstract File getUserPresetFolder();

	/**
	 * Fire a invalidate event that describe changes of user presets.
	 * 
	 * @see #addListener(IJTPresetManagerListener)
	 * @see #removeListener(IJTPresetManagerListener)
	 */
	public abstract void invalidateUserPreset();

	/**
	 * Adds a preset manager listener.
	 * 
	 * @param listener
	 *            listener to attach.
	 */
	public void addListener(IJTPresetManagerListener listener);

	/**
	 * Removes a preset manager listener.
	 * 
	 * @param listener
	 *            listener to detach.
	 */
	public void removeListener(IJTPresetManagerListener listener);

	/**
	 * 
	 * @return default preset.
	 */
	public IJTPreset getDefaultPreset();

	/**
	 * returns a {@link IJTPresetCategory} for given id.
	 * 
	 * @param id
	 *            The id of {@link IJTPresetCategory} to retrieve.
	 * @return {@link IJTPresetCategory}.
	 */
	public IJTPresetCategory getCategory(String id);

	/**
	 * 
	 * @return All {@link IJTPresetCategory}s.
	 */
	public IJTPresetCategory[] getCategories();

	/**
	 * 
	 * @return User Preset Category.
	 */
	public IJTPresetCategory getUserCategory();

	/**
	 * 
	 * @return Default category.
	 */
	public IJTPresetCategory getDefaultCategory();

}