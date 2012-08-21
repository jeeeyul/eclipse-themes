package net.jeeeyul.eclipse.themes.preference.action;

import net.jeeeyul.eclipse.themes.css.ChromeCSSGenerator;
import net.jeeeyul.eclipse.themes.preference.ChromePage;
import net.jeeeyul.eclipse.themes.preference.ChromePreferenceInitializer;
import net.jeeeyul.eclipse.themes.preference.ChromeThemeConfig;
import net.jeeeyul.eclipse.themes.preference.ChromeThemePrefererncePage;
import net.jeeeyul.eclipse.themes.preference.IChromeThemeConfig;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ShowCurrentCSSAction extends PreferenceAction {

	public ShowCurrentCSSAction(ChromeThemePrefererncePage prefererncePage) {
		super(prefererncePage);
		setText("Show CSS");
	}

	@Override
	public void run() {
		PreferenceStore store = new PreferenceStore();
		new ChromePreferenceInitializer().initializeDefaultPreferences(store);

		for (ChromePage each : getPrefererncePage().getPages()) {
			each.save(store);
		}

		IChromeThemeConfig config = new ChromeThemeConfig(store);
		ChromeCSSGenerator generator = new ChromeCSSGenerator();
		generator.setConfig(config);
		String cssContents = generator.generate().toString();

		Shell activeShell = Display.getDefault().getActiveShell();
		CSSDialog dialog = new CSSDialog(activeShell);
		dialog.setCssContent(cssContents);
		dialog.open();
	}

}
