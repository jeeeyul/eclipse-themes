package net.jeeeyul.eclipse.themes.userpreset;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.preference.ChromeConstants;
import net.jeeeyul.eclipse.themes.preference.ChromePage;
import net.jeeeyul.eclipse.themes.preference.ChromePreferenceInitializer;
import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;
import net.jeeeyul.eclipse.themes.preference.action.PreferenceAction;
import net.jeeeyul.eclipse.themes.userpreset.UserPreset;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class LoadUserPresetAction extends PreferenceAction {

	private UserPreset userPreset;

	public LoadUserPresetAction(ChromeThemePrefererncePage prefererncePage, UserPreset userPreset) {
		super(prefererncePage);
		this.userPreset = userPreset;
		setText(userPreset.getName());
	}

	@Override
	public void run() {
		try {
			PreferenceStore store = new PreferenceStore();
			new ChromePreferenceInitializer().initializeDefaultPreferences(store);

			byte[] data = userPreset.getContents().getBytes("8859_1");
			InputStream stream = new ByteArrayInputStream(data);
			store.load(stream);
			stream.close();

			applyFallBackFont(store);

			ArrayList<ChromePage> pages = getPrefererncePage().getPages();
			for (ChromePage each : pages) {
				each.load(store);
			}
		} catch (IOException e) {
			ChromeThemeCore.getDefault().getLog()
					.log(new Status(IStatus.ERROR, ChromeThemeCore.PLUGIN_ID, "Applying preset results some errors, default settings was applied.", e));

			fallBackToDefaults();
		}
		super.run();
	}

	private void fallBackToDefaults() {
		IPreferenceStore store = ChromeThemeCore.getDefault().getPreferenceStore();
		ArrayList<ChromePage> pages = getPrefererncePage().getPages();
		for (ChromePage each : pages) {
			each.setToDefault(store);
		}
	}

	private void applyFallBackFont(PreferenceStore store) {
		String fontName = store.getString(ChromeConstants.CHROME_PART_FONT_NAME);
		FontData[] fontList = Display.getDefault().getFontList(fontName, true);
		if (fontList.length == 0) {
			String os = Platform.getOS();
			if (os.equals(Platform.OS_WIN32)) {
				store.setValue(ChromeConstants.CHROME_PART_FONT_NAME, "Segoe UI");
				store.setValue(ChromeConstants.CHROME_PART_FONT_SIZE, 9f);
			} else if (os.equals(Platform.OS_MACOSX)) {
				store.setValue(ChromeConstants.CHROME_PART_FONT_NAME, "Helvetica");
				store.setValue(ChromeConstants.CHROME_PART_FONT_SIZE, 12f);
			} else {
				store.setValue(ChromeConstants.CHROME_PART_FONT_NAME, "Arial");
				store.setValue(ChromeConstants.CHROME_PART_FONT_SIZE, 11f);
			}
		}
	}
}
