package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.e4.ActiveThemeTracker;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

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

	private Boolean partTextShadow = null;

	private RGB activePartGradientStart;

	private RGB activePartGradientEnd;

	private IPreferenceStore preferenceStore;

	private FontData partFontData;

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
			IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
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
			IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
			start[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_HUE);
			start[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_SATURATION);
			start[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_BRIGHTNESS);
			activePartGradientStart = new RGB(start[0], start[1], start[2]);
		}

		return activePartGradientStart;
	}

	@Override
	public FontData getPartFontData() {
		if (partFontData == null) {
			partFontData = new FontData();
			partFontData.setName(preferenceStore.getString(ChromeConstants.CHROME_PART_FONT_NAME));
			partFontData.height = preferenceStore.getFloat(ChromeConstants.CHROME_PART_FONT_SIZE);
		}

		return partFontData;
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
			IPreferenceStore store = preferenceStore;
			String sashPreset = store.getString(ChromeConstants.CHROME_SASH_PRESET);
			if (ChromeConstants.CHROME_SASH_PRESET_ADVANCED.equals(sashPreset)) {
				sashWidth = store.getInt(ChromeConstants.CRHOME_PART_CONTAINER_SASH_WIDTH);
			} else if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(sashPreset)) {
				sashWidth = 2;
			} else {
				sashWidth = 4;
			}
		}
		return sashWidth;
	}

	private void invalidate() {
		activePartGradientEnd = null;
		activePartGradientStart = null;
		sashWidth = null;
		partShadow = null;
		partFontData = null;
		partShadow = null;
		partTextShadow = null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		invalidate();
		if (ActiveThemeTracker.getInstance().isChromeThemeActive()) {
			updateJob.schedule();
		}
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
			IPreferenceStore store = preferenceStore;
			String sashPreset = store.getString(ChromeConstants.CHROME_SASH_PRESET);
			if (ChromeConstants.CHROME_SASH_PRESET_ADVANCED.equals(sashPreset)) {
				partShadow = store.getBoolean(ChromeConstants.CRHOME_PART_SHADOW);
			} else if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(sashPreset)) {
				partShadow = false;
			} else {
				partShadow = true;
			}

		}
		return partShadow;
	}

	public Boolean usePartTextShadow() {
		if (partTextShadow == null) {
			partTextShadow = preferenceStore.getBoolean(ChromeConstants.CHROME_PART_FONT_SHADOW);
		}
		return partTextShadow;
	}
}
