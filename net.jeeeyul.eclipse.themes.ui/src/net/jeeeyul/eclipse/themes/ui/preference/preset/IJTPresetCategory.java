package net.jeeeyul.eclipse.themes.ui.preference.preset;

import java.util.List;

/**
 * 
 * @author Jeeeyul
 */
public interface IJTPresetCategory {
	/**
	 * 
	 */
	public static final String DEFAULT_CATEGORY_ID = "net.jeeeyul.eclipse.themes.category.default";

	/**
	 * 
	 */
	public static final String USER_CATEGORY_ID = "net.jeeeyul.eclipse.themes.category.user";

	/**
	 * @return {@link IJTPreset} list that belongs to this category.
	 */
	public List<IJTPreset> getPresets();

	/**
	 * @return the name of this category that will be shown to user.
	 */
	public String getName();

	/**
	 * @return the ID of this category.
	 */
	public String getID();

}
