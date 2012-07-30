package net.jeeeyul.eclipse.themes.preference;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.decorator.EmptyDecorator;
import net.jeeeyul.eclipse.themes.decorator.GradientDecorator;
import net.jeeeyul.eclipse.themes.decorator.ICTabFolderDecorator;
import net.jeeeyul.eclipse.themes.decorator.InactiveDecorator;
import net.jeeeyul.eclipse.themes.e4.ActiveThemeTracker;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.RGB;

/**
 * 6: Colors and Fonts should be customized in runtime!
 * https://github.com/jeeeyul/eclipse-themes/issues/issue/6
 * 
 * @author Jeeeyul
 * 
 */
public class ChromeThemeConfig implements IPropertyChangeListener {
	private static ChromeThemeConfig INSTANCE;

	public static ChromeThemeConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChromeThemeConfig();
		}
		return INSTANCE;
	}

	ApplyChromeThemePreferenceJob updateJob = new ApplyChromeThemePreferenceJob();

	ICTabFolderDecorator activeDecorator;
	ICTabFolderDecorator inactiveDecorator;
	ICTabFolderDecorator emptyDecorator;

	Integer sashWidth = null;

	Boolean partShadow = null;

	private RGB activePartGradientStart;

	private RGB activePartGradientEnd;

	private ChromeThemeConfig() {
		ChromeThemeCore.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	public ICTabFolderDecorator getActiveDecorator() {
		if (activeDecorator == null) {
			IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
			float start[] = new float[3];
			start[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_HUE);
			start[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_SATURATION);
			start[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_START_BRIGHTNESS);

			float end[] = new float[3];
			end[0] = store.getFloat(ChromeConstants.CHROME_ACTIVE_END_HUE);
			end[1] = store.getFloat(ChromeConstants.CHROME_ACTIVE_END_SATURATION);
			end[2] = store.getFloat(ChromeConstants.CHROME_ACTIVE_END_BRIGHTNESS);
			activeDecorator = new GradientDecorator(start, end);
		}
		return activeDecorator;
	}

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

	public ICTabFolderDecorator getEmptyDecorator() {
		if (emptyDecorator == null) {
			emptyDecorator = new EmptyDecorator();
		}
		return emptyDecorator;
	}

	public ICTabFolderDecorator getInactiveDecorator() {
		if (inactiveDecorator == null) {
			inactiveDecorator = new InactiveDecorator();
		}
		return inactiveDecorator;
	}

	public int getSashWidth() {
		if (sashWidth == null) {
			IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
			String sashPreset = store.getString(ChromeConstants.CHROME_SASH_PRESET);
			if (ChromeConstants.CHROME_SASH_PRESET_ADVANCED.equals(sashPreset)) {
				sashWidth = store.getInt(ChromeConstants.CHOME_PART_CONTAINER_SASH_WIDTH);
			} else if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(sashPreset)) {
				sashWidth = 2;
			} else {
				sashWidth = 4;
			}
		}
		return sashWidth;
	}

	private void invalidate() {
		if (activeDecorator != null) {
			activeDecorator.dispose();
			activeDecorator = null;
		}
		activePartGradientEnd = null;
		activePartGradientStart = null;
		sashWidth = null;
		partShadow = null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		invalidate();
		if (ActiveThemeTracker.getInstance().isChromeThemeActive()) {
			updateJob.schedule();
		}
	}

	public boolean usePartShadow() {
		if (partShadow == null) {
			IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
			String sashPreset = store.getString(ChromeConstants.CHROME_SASH_PRESET);
			if (ChromeConstants.CHROME_SASH_PRESET_ADVANCED.equals(sashPreset)) {
				partShadow = store.getBoolean(ChromeConstants.CHOME_PART_SHADOW);
			} else if (ChromeConstants.CHROME_SASH_PRESET_THIN.equals(sashPreset)) {
				partShadow = false;
			} else {
				partShadow = true;
			}
		}
		return partShadow;
	}
}
