package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.ui.HSB;

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

	public void initializeDefaultPreferences(IPreferenceStore store) {
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

		store.setDefault(ChromeConstants.CHROME_EMPTY_PART_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_EMPTY_PART_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_EMPTY_PART_BRIGHTNESS, 0.87f);

		store.setDefault(ChromeConstants.CHROME_EMPTY_PART_OUTLINE_HUE, 221f);
		store.setDefault(ChromeConstants.CHROME_EMPTY_PART_OUTLINE_SATURATION, 0.1f);
		store.setDefault(ChromeConstants.CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS, 0.8f);

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
			store.setDefault(ChromeConstants.CHROME_PART_FONT_NAME, "Lucida Grande");
			store.setDefault(ChromeConstants.CHROME_PART_FONT_SIZE, 12f);
		} else {
			store.setDefault(ChromeConstants.CHROME_PART_FONT_NAME, "Verdana");
			store.setDefault(ChromeConstants.CHROME_PART_FONT_SIZE, 10f);
		}

		store.setDefault(ChromeConstants.CHROME_TOOLBAR_START_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_START_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_START_BRIGHTNESS, .93f);

		store.setDefault(ChromeConstants.CHROME_TOOLBAR_END_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_END_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_TOOLBAR_END_BRIGHTNESS, .86f);

		store.setDefault(ChromeConstants.CHROME_WINDOW_BACKGROUND_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_WINDOW_BACKGROUND_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_WINDOW_BACKGROUND_BRIGHTNESS, .93f);

		store.setDefault(ChromeConstants.CHROME_PART_SHADOW_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_PART_SHADOW_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_PART_SHADOW_BRIGHTNESS, .66f);

		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_START_HUE, 222f);
		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_START_SATURATION, 0.02f);
		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_START_BRIGHTNESS, .98f);

		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_END_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_END_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_END_BRIGHTNESS, .93f);

		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_OUTLINE_HUE, 222f);
		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_OUTLINE_SATURATION, 0.1f);
		store.setDefault(ChromeConstants.CHROME_PERSPECTIVE_OUTLINE_BRIGHTNESS, .74f);

		store.setDefault(ChromeConstants.CHROME_USE_WINDOW_BACKGROUND_COLOR_AS_PERSPECTIVE_END_COLOR, true);

		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_START_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_START_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_START_BRIGHTNESS, 1f);

		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_END_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_END_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_END_BRIGHTNESS, 1f);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_START_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_START_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_START_BRIGHTNESS, 1f);

		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_END_HUE, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_END_SATURATION, 0f);
		store.setDefault(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_END_BRIGHTNESS, 1f);

		store.setDefault(ChromeConstants.CHROME_PART_STACK_PADDING, 1);

		store.setDefault(ChromeConstants.CHROME_PART_STACK_USE_MRU, true);

		store.setDefault(ChromeConstants.CHROME_EDITOR_LINE_VISIBLE, false);
		store.setDefault(ChromeConstants.CHROME_EDITOR_LINE_DASH, true);
		store.setDefault(ChromeConstants.CHROME_EDITOR_LINE_COLOR, new HSB(0f, 0f, 0.94f).serialize());

		/*
		 * 43: Flag to disable round corners
		 * https://github.com/jeeeyul/eclipse-themes/issues/issue/43
		 */
		store.setDefault(ChromeConstants.CHROME_PART_STACK_CORNER_RADIUS, 14);
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
		initializeDefaultPreferences(store);
	}

}
