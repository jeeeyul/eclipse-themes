package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.e4.ActiveThemeTracker;
import net.jeeeyul.eclipse.themes.ui.HSB;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
public class ChromeThemeConfig implements IPropertyChangeListener, IChromeThemeConfig {
	private static IChromeThemeConfig INSTANCE;

	public static IChromeThemeConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChromeThemeConfig();
		}
		return INSTANCE;
	}

	private ApplyChromeThemePreferenceJob updateJob = new ApplyChromeThemePreferenceJob();

	private Integer sashWidth = null;
	private Boolean partShadow = null;
	private Boolean activePartTitleShadow = null;
	private Boolean inactivePartTitleShadow = null;

	private RGB toolbarGradientStart;
	private RGB toolbarGradientEnd;
	private RGB activePartGradientStart;
	private RGB activePartGradientEnd;
	private RGB activeOulineColor;
	private RGB activeSelectedTitleColor;
	private RGB activeUnselectedTitleColor;
	private RGB inactiveSelectedTitleColor;
	private RGB inactiveUnselectedTitleColor;
	private RGB inactivePartGradientStart;
	private RGB inactivePartGradientEnd;
	private RGB inactiveOulineColor;
	private IPreferenceStore preferenceStore;
	private FontData partFontData;
	private RGB windowBackgroundColor;
	private RGB perspectiveStartColor;
	private RGB perspectiveEndColor;
	private RGB perspectiveOutlineColor;
	private RGB partShadowColor;
	private RGB emptyPartBackgroundColor;
	private RGB emptyPartOutloneColor;
	private RGB activeSelectedTabStartColor;
	private RGB activeSelectedTabEndColor;
	private RGB inactiveSelectedTabStartColor;
	private RGB inactiveSelectedTabEndColor;
	private Boolean mruVisible;

	private Integer partStackPadding;

	private Boolean editorLineVisible;

	private Boolean editorLineDashed;

	private RGB editorLineColor;

	/**
	 * 43: Flag to disable round corners
	 * https://github.com/jeeeyul/eclipse-themes/issues/issue/43
	 */
	private Integer partStackCornerRadius;

	public ChromeThemeConfig() {
		this(ChromeThemeCore.getDefault().getPreferenceStore());
	}

	public ChromeThemeConfig(IPreferenceStore preferenceStore) {
		this.preferenceStore = preferenceStore;
		preferenceStore.addPropertyChangeListener(this);
	}

	public void dispose() {
		preferenceStore.removePropertyChangeListener(this);
	}

	@Override
	public RGB getActiveOulineColor() {
		if (activeOulineColor == null) {
			float outline[] = new float[3];
			IPreferenceStore store = getStore();
			outline[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_HUE);
			outline[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_SATURATION);
			outline[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_OUTLINE_BRIGHTNESS);
			activeOulineColor = new RGB(outline[0], outline[1], outline[2]);
		}
		return activeOulineColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig#
	 * getActivePartGradientEnd()
	 */
	@Override
	public RGB getActivePartGradientEnd() {
		if (activePartGradientEnd == null) {
			float start[] = new float[3];
			IPreferenceStore store = getStore();
			start[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_END_HUE);
			start[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_END_SATURATION);
			start[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_END_BRIGHTNESS);
			activePartGradientEnd = new RGB(start[0], start[1], start[2]);
		}

		return activePartGradientEnd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig#
	 * getActivePartGradientStart()
	 */
	@Override
	public RGB getActivePartGradientStart() {
		if (activePartGradientStart == null) {
			float start[] = new float[3];
			IPreferenceStore store = getStore();
			start[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_HUE);
			start[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_SATURATION);
			start[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_BRIGHTNESS);
			activePartGradientStart = new RGB(start[0], start[1], start[2]);
		}

		return activePartGradientStart;
	}

	@Override
	public RGB getActiveSelectedTabEndColor() {
		if (activeSelectedTabEndColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_END_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_END_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_END_BRIGHTNESS);
			activeSelectedTabEndColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return activeSelectedTabEndColor;
	}

	@Override
	public RGB getActiveSelectedTabStartColor() {
		if (activeSelectedTabStartColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_START_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_START_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TAB_START_BRIGHTNESS);
			activeSelectedTabStartColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return activeSelectedTabStartColor;
	}

	@Override
	public RGB getActiveSelectedTitleColor() {
		if (activeSelectedTitleColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TITLE_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TITLE_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_SELECTED_TITLE_BRIGHTNESS);
			activeSelectedTitleColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return activeSelectedTitleColor;
	}

	@Override
	public RGB getActiveUnselectedTitleColor() {
		if (activeUnselectedTitleColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_BRIGHTNESS);
			activeUnselectedTitleColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return activeUnselectedTitleColor;
	}

	@Override
	public RGB getEditorLineColor() {
		if (editorLineColor == null) {
			String exp = getStore().getString(ChromeConstants.CHROME_EDITOR_LINE_COLOR);
			editorLineColor = HSB.createFromString(exp).toRGB();
		}
		return editorLineColor;
	}

	@Override
	public Boolean getEditorLineDashed() {
		if (editorLineDashed == null) {
			editorLineDashed = getStore().getBoolean(ChromeConstants.CHROME_EDITOR_LINE_DASH);
		}
		return editorLineDashed;
	}

	@Override
	public Boolean getEditorLineVisible() {
		if (editorLineVisible == null) {
			editorLineVisible = getStore().getBoolean(ChromeConstants.CHROME_EDITOR_LINE_VISIBLE);
		}
		return editorLineVisible;
	}

	@Override
	public RGB getEmptyPartBackgroundColor() {
		if (emptyPartBackgroundColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_EMPTY_PART_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_EMPTY_PART_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_EMPTY_PART_BRIGHTNESS);
			emptyPartBackgroundColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return emptyPartBackgroundColor;
	}

	@Override
	public RGB getEmptyPartOutloneColor() {
		if (emptyPartOutloneColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_EMPTY_PART_OUTLINE_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_EMPTY_PART_OUTLINE_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_EMPTY_PART_OUTLINE_BRIGHTNESS);
			emptyPartOutloneColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return emptyPartOutloneColor;
	}

	@Override
	public RGB getInactiveOulineColor() {
		if (inactiveOulineColor == null) {
			float outline[] = new float[3];
			IPreferenceStore store = getStore();
			outline[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_OUTLINE_HUE);
			outline[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_OUTLINE_SATURATION);
			outline[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_OUTLINE_BRIGHTNESS);
			inactiveOulineColor = new RGB(outline[0], outline[1], outline[2]);
		}
		return inactiveOulineColor;
	}

	@Override
	public RGB getInactivePartGradientEnd() {
		if (inactivePartGradientEnd == null) {
			float start[] = new float[3];
			IPreferenceStore store = getStore();
			start[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_END_HUE);
			start[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_END_SATURATION);
			start[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_END_BRIGHTNESS);
			inactivePartGradientEnd = new RGB(start[0], start[1], start[2]);
		}

		return inactivePartGradientEnd;
	}

	@Override
	public RGB getInactivePartGradientStart() {
		if (inactivePartGradientStart == null) {
			float start[] = new float[3];
			IPreferenceStore store = getStore();
			start[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_START_HUE);
			start[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_START_SATURATION);
			start[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_START_BRIGHTNESS);
			inactivePartGradientStart = new RGB(start[0], start[1], start[2]);
		}

		return inactivePartGradientStart;
	}

	@Override
	public RGB getInactiveSelectedTabEndColor() {
		if (inactiveSelectedTabEndColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_END_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_END_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_END_BRIGHTNESS);
			inactiveSelectedTabEndColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return inactiveSelectedTabEndColor;
	}

	@Override
	public RGB getInactiveSelectedTabStartColor() {
		if (inactiveSelectedTabStartColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_START_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_START_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TAB_START_BRIGHTNESS);
			inactiveSelectedTabStartColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return inactiveSelectedTabStartColor;
	}

	@Override
	public RGB getInactiveSelectedTitleColor() {
		if (inactiveSelectedTitleColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TITLE_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TITLE_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_SELECTED_TITLE_BRIGHTNESS);
			inactiveSelectedTitleColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return inactiveSelectedTitleColor;
	}

	@Override
	public RGB getInactiveUnselectedTitleColor() {
		if (inactiveUnselectedTitleColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_BRIGHTNESS);
			inactiveUnselectedTitleColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return inactiveUnselectedTitleColor;
	}

	@Override
	public Boolean getMruVisible() {
		if (mruVisible == null) {
			mruVisible = getStore().getBoolean(ChromeConstants.CHROME_PART_STACK_USE_MRU);
		}
		return mruVisible;
	}

	@Override
	public FontData getPartFontData() {
		if (partFontData == null) {
			partFontData = new FontData();
			try {
				partFontData.setName(getStore().getString(ChromeConstants.CHROME_PART_FONT_NAME));
				partFontData.height = getStore().getFloat(ChromeConstants.CHROME_PART_FONT_SIZE);
			} catch (Exception e) {
				partFontData = Display.getDefault().getSystemFont().getFontData()[0];
				partFontData.height = getStore().getFloat(ChromeConstants.CHROME_PART_FONT_SIZE);
			}
		}

		return partFontData;
	}

	@Override
	public RGB getPartShadowColor() {
		if (partShadowColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_PART_SHADOW_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_PART_SHADOW_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_PART_SHADOW_BRIGHTNESS);
			partShadowColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return partShadowColor;
	}

	@Override
	public Integer getPartStackCornerRadius() {
		if (partStackCornerRadius == null) {
			IPreferenceStore store = getStore();
			partStackCornerRadius = store.getInt(ChromeConstants.CHROME_PART_STACK_CORNER_RADIUS);
		}
		return partStackCornerRadius;
	}

	@Override
	public Integer getPartStackPadding() {
		if (partStackPadding == null) {
			IPreferenceStore store = getStore();
			partStackPadding = store.getInt(ChromeConstants.CHROME_PART_STACK_PADDING);
		}
		return partStackPadding;
	}

	@Override
	public RGB getPerspectiveEndColor() {
		if (perspectiveEndColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_END_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_END_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_END_BRIGHTNESS);
			perspectiveEndColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return perspectiveEndColor;
	}

	@Override
	public RGB getPerspectiveOutlineColor() {
		if (perspectiveOutlineColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_OUTLINE_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_OUTLINE_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_OUTLINE_BRIGHTNESS);
			perspectiveOutlineColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return perspectiveOutlineColor;
	}

	@Override
	public RGB getPerspectiveStartColor() {
		if (perspectiveStartColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_START_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_START_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_PERSPECTIVE_START_BRIGHTNESS);
			perspectiveStartColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return perspectiveStartColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig#getSashWidth()
	 */
	@Override
	public int getSashWidth() {
		if (sashWidth == null) {
			IPreferenceStore store = getStore();
			String sashPreset = store.getString(ChromeConstants.CHROME_SASH_PRESET);
			if (ChromeConstants.CHROME_SASH_PRESET_ADVANCED.equals(sashPreset)) {
				sashWidth = store.getInt(ChromeConstants.CHROME_PART_CONTAINER_SASH_WIDTH);
			} else if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(sashPreset)) {
				sashWidth = 2;
			} else {
				sashWidth = 4;
			}
		}
		return sashWidth;
	}

	private IPreferenceStore getStore() {
		return preferenceStore;
	}

	@Override
	public RGB getToolbarGradientEnd() {
		if (toolbarGradientEnd == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_TOOLBAR_END_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_TOOLBAR_END_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_TOOLBAR_END_BRIGHTNESS);
			toolbarGradientEnd = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return toolbarGradientEnd;
	}

	@Override
	public RGB getToolbarGradientStart() {
		if (toolbarGradientStart == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_TOOLBAR_START_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_TOOLBAR_START_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_TOOLBAR_START_BRIGHTNESS);
			toolbarGradientStart = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return toolbarGradientStart;
	}

	@Override
	public RGB getWindowBackgroundColor() {
		if (windowBackgroundColor == null) {
			float hsb[] = new float[3];
			IPreferenceStore store = getStore();
			hsb[0] = store.getFloat(ChromeConstants.CHROME_WINDOW_BACKGROUND_HUE);
			hsb[1] = store.getFloat(ChromeConstants.CHROME_WINDOW_BACKGROUND_SATURATION);
			hsb[2] = store.getFloat(ChromeConstants.CHROME_WINDOW_BACKGROUND_BRIGHTNESS);
			windowBackgroundColor = new RGB(hsb[0], hsb[1], hsb[2]);
		}
		return windowBackgroundColor;
	}

	private void invalidate() {
		activePartGradientEnd = null;
		activePartGradientStart = null;
		activeOulineColor = null;
		activeSelectedTitleColor = null;
		activeUnselectedTitleColor = null;

		inactiveOulineColor = null;
		inactivePartGradientEnd = null;
		inactivePartGradientStart = null;
		inactiveSelectedTitleColor = null;
		inactiveUnselectedTitleColor = null;

		sashWidth = null;
		partShadow = null;
		partFontData = null;
		partShadow = null;
		activePartTitleShadow = null;
		inactivePartTitleShadow = null;

		toolbarGradientEnd = null;
		toolbarGradientStart = null;

		partShadowColor = null;
		perspectiveStartColor = null;
		perspectiveEndColor = null;
		perspectiveOutlineColor = null;
		windowBackgroundColor = null;

		emptyPartBackgroundColor = null;
		emptyPartOutloneColor = null;

		activeSelectedTabEndColor = null;
		activeSelectedTabStartColor = null;
		inactiveSelectedTabStartColor = null;
		inactiveSelectedTabEndColor = null;

		partStackPadding = null;
		mruVisible = null;

		editorLineColor = null;
		editorLineDashed = null;
		editorLineVisible = null;

		partStackCornerRadius = null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		invalidate();
		if (ActiveThemeTracker.getInstance().isChromeThemeActive()) {
			updateJob.schedule();
		}
	}

	@Override
	public Boolean useActivePartTitleShadow() {
		if (activePartTitleShadow == null) {
			activePartTitleShadow = getStore().getBoolean(ChromeConstants.CHROME_ACTIVE_UNSELECTED_TITLE_SHINY_SHADOW);
		}
		return activePartTitleShadow;
	}

	@Override
	public Boolean useInactivePartTitleShadow() {
		if (inactivePartTitleShadow == null) {
			inactivePartTitleShadow = getStore().getBoolean(ChromeConstants.CHROME_INACTIVE_UNSELECTED_TITLE_SHINY_SHADOW);
		}
		return inactivePartTitleShadow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig#usePartShadow()
	 */
	@Override
	public boolean usePartShadow() {
		if (partShadow == null) {
			IPreferenceStore store = getStore();
			String sashPreset = store.getString(ChromeConstants.CHROME_SASH_PRESET);
			if (ChromeConstants.CHROME_SASH_PRESET_ADVANCED.equals(sashPreset)) {
				partShadow = store.getBoolean(ChromeConstants.CHROME_PART_SHADOW);
			} else if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(sashPreset)) {
				partShadow = false;
			} else {
				partShadow = true;
			}

		}
		return partShadow;
	}
}
