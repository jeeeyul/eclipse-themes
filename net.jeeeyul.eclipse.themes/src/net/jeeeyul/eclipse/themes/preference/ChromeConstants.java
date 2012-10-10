package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.preference.annotations.ValueType;
import net.jeeeyul.eclipse.themes.ui.HSB;

public interface ChromeConstants {
	public static final String CHROME_THEME_ID = "net.jeeeyul.eclipse.themes.chrome";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_TOOLBAR_START_BRIGHTNESS = "chrome-toolbar-start-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_TOOLBAR_START_SATURATION = "chrome-toolbar-start-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_TOOLBAR_START_HUE = "chrome-toolbar-start-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_TOOLBAR_END_BRIGHTNESS = "chrome-toolbar-end-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_TOOLBAR_END_SATURATION = "chrome-toolbar-end-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_TOOLBAR_END_HUE = "chrome-toolbar-end-hue";

	@ValueType(boolean.class)
	public static final String CHROME_LOCK_ACTIVE_END_HUE = "chrome-lock-active-end-hue";

	@ValueType(boolean.class)
	public static final String CHROME_AUTO_ACTIVE_END_COLOR = "chrome-auto-active-end-color";

	@ValueType(boolean.class)
	public static final String CHROME_LOCK_ACTIVE_OUTLINE_HUE = "chrome-lock-active-outline-hue";

	@ValueType(boolean.class)
	public static final String CHROME_AUTO_ACTIVE_OUTLINE_COLOR = "chrome-auto-active-outline-color";

	@ValueType(boolean.class)
	public static final String CHROME_LOCK_INACTIVE_END_HUE = "chrome-lock-inactive-end-hue";

	@ValueType(boolean.class)
	public static final String CHROME_AUTO_INACTIVE_END_COLOR = "chrome-auto-inactive-end-color";

	@ValueType(boolean.class)
	public static final String CHROME_LOCK_INACTIVE_OUTLINE_HUE = "chrome-lock-inactive-outline-hue";

	@ValueType(boolean.class)
	public static final String CHROME_AUTO_INACTIVE_OUTLINE_COLOR = "chrome-auto-inactive-outline-color";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_END_BRIGHTNESS = "chrome-active-end-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_END_SATURATION = "chrome-active-end-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_END_HUE = "chrome-active-end-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_START_BRIGHTNESS = "chrome-active-start-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_START_SATURATION = "chrome-active-start-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_START_HUE = "chrome-active-start-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS = "chrome-active-selected-title-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_SELECTED_TITLE_SATURATION = "chrome-active-selected-title-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_SELECTED_TITLE_HUE = "chrome-active-selected-title-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS = "chrome-active-unselected-title-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_UNSELECTED_TITLE_SATURATION = "chrome-active-unselected-title-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_UNSELECTED_TITLE_HUE = "chrome-active-unselected-title-hue";

	@ValueType(boolean.class)
	public static final String CHROME_ACTIVE_UNSELECTED_TITLE_SHINY_SHADOW = "chrome-active-unselected-title-shiny-shadow";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_OUTLINE_BRIGHTNESS = "chrome-active-outline-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_OUTLINE_SATURATION = "chrome-active-outline-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_OUTLINE_HUE = "chrome-active-outline-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_END_BRIGHTNESS = "chrome-inactive-end-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_END_SATURATION = "chrome-inactive-end-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_END_HUE = "chrome-inactive-end-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_START_BRIGHTNESS = "chrome-inactive-start-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_START_SATURATION = "chrome-inactive-start-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_START_HUE = "chrome-inactive-start-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_OUTLINE_BRIGHTNESS = "chrome-inactive-outline-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_OUTLINE_SATURATION = "chrome-inactive-outline-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_OUTLINE_HUE = "chrome-inactive-outline-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS = "chrome-inactive-selected-title-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_SELECTED_TITLE_SATURATION = "chrome-inactive-selected-title-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_SELECTED_TITLE_HUE = "chrome-inactive-selected-title-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS = "chrome-inactive-unselected-title-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_UNSELECTED_TITLE_SATURATION = "chrome-inactive-unselected-title-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_UNSELECTED_TITLE_HUE = "chrome-inactive-unselected-title-hue";

	@ValueType(boolean.class)
	public static final String CHROME_INACTIVE_UNSELECTED_TITLE_SHINY_SHADOW = "chrome-inactive-unselected-title-shiny-shadow";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_EMPTY_PART_SATURATION = "chrome-empty-part-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_EMPTY_PART_HUE = "chrome-empty-part-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_EMPTY_PART_BRIGHTNESS = "chrome-empty-part-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_EMPTY_PART_OUTLINE_SATURATION = "chrome-empty-part-outline-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_EMPTY_PART_OUTLINE_HUE = "chrome-empty-part-outline-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS = "chrome-empty-part-outline-brightness";

	@ValueType(String.class)
	public static final String CHROME_SASH_PRESET = "chrome-sash-preset";

	public static final String CHROME_SASH_PRESET_STANDARD = "chrome-sash-preset-standard";
	public static final String CHROME_SASH_PRESET_THIN = "chrome-sash-preset-thin";
	public static final String CHROME_SASH_PRESET_ADVANCED = "chrome-sash-preset-advanced";

	@ValueType(int.class)
	public static final String CHROME_PART_CONTAINER_SASH_WIDTH = "chrome-part-container-sash-width";

	@ValueType(boolean.class)
	public static final String CHROME_PART_SHADOW = "chrome-part-shadow";

	@ValueType(String.class)
	public static final String CHROME_PART_FONT_NAME = "chrome-part-font-name";

	@ValueType(float.class)
	public static final String CHROME_PART_FONT_SIZE = "chrome-part-font-size";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_WINDOW_BACKGROUND_BRIGHTNESS = "chrome-window-background-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_WINDOW_BACKGROUND_SATURATION = "chrome-window-background-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_WINDOW_BACKGROUND_HUE = "chrome-window-background-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_PART_SHADOW_BRIGHTNESS = "chrome-part-shadow-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_PART_SHADOW_SATURATION = "chrome-part-shadow-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_PART_SHADOW_HUE = "chrome-part-shadow-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_PERSPECTIVE_START_BRIGHTNESS = "chrome-perspective-start-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_PERSPECTIVE_START_SATURATION = "chrome-perspective-start-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_PERSPECTIVE_START_HUE = "chrome-perspective-start-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_PERSPECTIVE_END_BRIGHTNESS = "chrome-perspective-end-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_PERSPECTIVE_END_SATURATION = "chrome-perspective-end-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_PERSPECTIVE_END_HUE = "chrome-perspective-end-hue";

	@ValueType(HSB.class)
	public static final String CHROME_USE_WINDOW_BACKGROUND_COLOR_AS_PERSPECTIVE_END_COLOR = "chrome-use-window-background-color-as-perspective-end-color";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_PERSPECTIVE_OUTLINE_BRIGHTNESS = "chrome-perspective-outline-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_PERSPECTIVE_OUTLINE_SATURATION = "chrome-perspective-outline-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_PERSPECTIVE_OUTLINE_HUE = "chrome-perspective-outline-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_SELECTED_TAB_START_BRIGHTNESS = "chrome-active-selected-tab-start-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_SELECTED_TAB_START_SATURATION = "chrome-active-selected-tab-start-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_SELECTED_TAB_START_HUE = "chrome-active-selected-tab-start-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_ACTIVE_SELECTED_TAB_END_BRIGHTNESS = "chrome-active-selected-tab-end-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_ACTIVE_SELECTED_TAB_END_SATURATION = "chrome-active-selected-tab-end-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_ACTIVE_SELECTED_TAB_END_HUE = "chrome-active-selected-tab-end-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_SELECTED_TAB_START_BRIGHTNESS = "chrome-inactive-selected-tab-start-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_SELECTED_TAB_START_SATURATION = "chrome-inactive-selected-tab-start-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_SELECTED_TAB_START_HUE = "chrome-inactive-selected-tab-start-hue";

	@ValueType(value = float.class, semantics = "brightness")
	public static final String CHROME_INACTIVE_SELECTED_TAB_END_BRIGHTNESS = "chrome-inactive-selected-tab-end-brightness";

	@ValueType(value = float.class, semantics = "saturation")
	public static final String CHROME_INACTIVE_SELECTED_TAB_END_SATURATION = "chrome-inactive-selected-tab-end-saturation";

	@ValueType(value = float.class, semantics = "hue")
	public static final String CHROME_INACTIVE_SELECTED_TAB_END_HUE = "chrome-inactive-selected-tab-end-hue";

	@ValueType(int.class)
	public static final String CHROME_PART_STACK_PADDING = "chrome-part-stack-padding";

	/*
	 * 35: Expose the mru-visible css property
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/35
	 */
	@ValueType(boolean.class)
	public static final String CHROME_PART_STACK_USE_MRU = "chrome-part-stack-use-mru";

	/*
	 * 43: Flag to disable round corners
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/43
	 */
	@ValueType(int.class)
	public static final String CHROME_PART_STACK_CORNER_RADIUS = "chrome-part-stack-corner-radius";

	@ValueType(boolean.class)
	public static final String CHROME_USE_WINDOW_BACKGROUND_AS_STATUS_BAR_BACKGROUND = "chrome-use-window-background-as-status-bar-background";

	@ValueType(boolean.class)
	public static final String CHROME_USE_STATUS_BAR_OUTLINE = "chrome-use-status-bar-outline";

	@ValueType(HSB.class)
	public static final String CHROME_STATUS_BAR_BACKGROUND_COLOR = "chrome-status-bar-background-color";

	@ValueType(HSB.class)
	public static final String CHROME_STATUS_BAR_OUTLINE_COLOR = "chrome-status-bar-outline-color";

	@ValueType(boolean.class)
	public static final String CHROME_USE_TRIMSTACK_IMAGE_BORDER = "chrome-use-trimstack-image-border";

	@ValueType(boolean.class)
	public static final String CHROME_USE_EMBOSSED_DRAG_HANDLE = "chrome-use-embossed-drag-handle";

	/**
	 * 58: User Custom CSS
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/58
	 */
	@ValueType(String.class)
	public static final String CHROME_USER_CSS = "chrome-user-css";
}
