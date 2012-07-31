package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;

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

		store.setDefault(ChromeConstants.CHROME_AUTO_END_COLOR, true);
		store.setDefault(ChromeConstants.CHROME_LOCK_END_HUE, true);

		store.setDefault(ChromeConstants.CHROME_AUTO_OUTLINE_COLOR, true);
		store.setDefault(ChromeConstants.CHROME_LOCK_OUTLINE_HUE, true);

		store.setDefault(ChromeConstants.CRHOME_PART_CONTAINER_SASH_WIDTH, 4);
		store.setDefault(ChromeConstants.CRHOME_PART_SHADOW, true);
		store.setDefault(ChromeConstants.CHROME_SASH_PRESET, ChromeConstants.CHROME_SASH_PRESET_STANDARD);

		store.setDefault(ChromeConstants.CHROME_PART_FONT_NAME, "Segoe UI");
		store.setDefault(ChromeConstants.CHROME_PART_FONT_SIZE, 9f);

		store.setDefault(ChromeConstants.CHROME_PART_FONT_SHADOW, true);
	}

}
