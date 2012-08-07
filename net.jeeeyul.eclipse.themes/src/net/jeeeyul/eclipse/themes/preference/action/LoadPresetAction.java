package net.jeeeyul.eclipse.themes.preference.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import net.jeeeyul.eclipse.themes.ChromeThemeCore;
import net.jeeeyul.eclipse.themes.preference.ChromeConstants;
import net.jeeeyul.eclipse.themes.preference.ChromePage;
import net.jeeeyul.eclipse.themes.preference.ChromePreferenceInitializer;
import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class LoadPresetAction extends PreferenceAction {

	private URL presetContents;

	public LoadPresetAction(ChromeThemePrefererncePage prefererncePage, URL presetContents) {
		super(prefererncePage);
		this.presetContents = presetContents;
	}

	@Override
	public void run() {
		try {
			PreferenceStore store = new PreferenceStore();
			new ChromePreferenceInitializer().initializeDefaultPreferences(store);

			InputStream stream = presetContents.openStream();
			store.load(stream);
			stream.close();

			applyFallBackFont(store);

			ArrayList<ChromePage> pages = getPrefererncePage().getPages();
			for (ChromePage each : pages) {
				each.load(store);
			}
		} catch (IOException e) {
			ChromeThemeCore.getDefault().getLog().log(new Status(IStatus.ERROR, ChromeThemeCore.PLUGIN_ID, e.getLocalizedMessage(), e));
			e.printStackTrace();
		}
		super.run();
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
