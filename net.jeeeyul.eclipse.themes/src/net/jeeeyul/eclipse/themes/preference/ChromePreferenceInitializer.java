package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
public class ChromePreferenceInitializer extends AbstractPreferenceInitializer {

	public ChromePreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
		store.setDefault(ChromeConstants.CHROME_ACTIVE_START_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_START_SATURATION, .10f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_START_BRIGHTNESS, .97f);

		store.setDefault(ChromeConstants.CHROME_ACTIVE_END_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_END_SATURATION, .11f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_END_BRIGHTNESS, .92f);

		store.setDefault(ChromeConstants.CHROME_ACTIVE_OUTLINE_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_OUTLINE_SATURATION, .3f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_OUTLINE_BRIGHTNESS, .67f);

		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TITLE_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS, 0f);

		store.setDefault(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_SHINY_SHADOW, true);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_START_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_START_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_START_BRIGHTNESS, .93f);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_END_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_END_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_END_BRIGHTNESS, .86f);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_OUTLINE_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_OUTLINE_SATURATION, .1f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_OUTLINE_BRIGHTNESS, .8f);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TITLE_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS, 0f);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_SHINY_SHADOW, true);

		store.setDefault(ChromeConstants.CHROME_AUTO_ACTIVE_END_COLOR, true);
		store.setDefault(ChromeConstants.CHROME_LOCK_ACTIVE_END_HUE, true);
		store.setDefault(ChromeConstants.CHROME_AUTO_ACTIVE_OUTLINE_COLOR, true);
		store.setDefault(ChromeConstants.CHROME_LOCK_ACTIVE_OUTLINE_HUE, true);

		store.setDefault(ChromeConstants.CHROME_AUTO_INACTIVE_END_COLOR, true);
		store.setDefault(ChromeConstants.CHROME_LOCK_INACTIVE_END_HUE, true);
		store.setDefault(ChromeConstants.CHROME_AUTO_INACTIVE_OUTLINE_COLOR, true);
		store.setDefault(ChromeConstants.CHROME_LOCK_INACTIVE_OUTLINE_HUE, true);

		store.setDefault(ChromeConstants.CHROME_PART_CONTAINER_SASH_WIDTH, 4);
		store.setDefault(ChromeConstants.CHROME_PART_SHADOW, true);
		store.setDefault(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_STANDARD);

		/*
		 * 26: Default font should be different per platform
		 * https://github.com/jeeeyul/eclipse-themes/issues/issue/26
		 */
		String os = Platform.getOS();
		if (os.equals(Platform.OS_WIN32)) {
			store.setDefault(ChromeConstants.CHROME_PART_FONT_NAME, "Segoe UI");
			store.setDefault(ChromeConstants.CHROME_PART_FONT_SIZE, 9f);
		} else if (os.equals(Platform.OS_MACOSX)) {
			store.setDefault(ChromeConstants.CHROME_PART_FONT_NAME, "Helvetica");
			store.setDefault(ChromeConstants.CHROME_PART_FONT_SIZE, 11f);
		} else {
			store.setDefault(ChromeConstants.CHROME_PART_FONT_NAME, "Arial");
			store.setDefault(ChromeConstants.CHROME_PART_FONT_SIZE, 11f);
		}

		store.setDefault(ChromeConstants.CHROME_TOOLBAR_START_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_START_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_START_BRIGHTNESS, .93f);

		store.setDefault(ChromeConstants.CHROME_TOOLBAR_END_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_END_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_END_BRIGHTNESS, .86f);
	}

}
