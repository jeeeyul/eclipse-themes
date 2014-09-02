package net.jeeeyul.eclipse.themes.css;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.css.internal.RangeIndicatorHack;
import net.jeeeyul.eclipse.themes.css.internal.ResourceRegistryHack;
import net.jeeeyul.eclipse.themes.internal.Debug;
import net.jeeeyul.eclipse.themes.preference.JTPConstants;
import net.jeeeyul.eclipse.themes.preference.JThemePreferenceStore;

import org.eclipse.e4.ui.css.core.dom.ExtendedCSSRule;
import org.eclipse.e4.ui.css.core.dom.ExtendedDocumentCSS;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.resources.IResourcesRegistry;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.e4.ui.workbench.renderers.swt.TrimmedPartLayout;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

/**
 * re-generate "jeeeyul-custom.css" and replace with existing style sheet.
 * 
 * @author Jeeeyul
 * @since 1.2
 */
@SuppressWarnings("restriction")
public class RewriteCustomTheme {
	/**
	 * A selector text to detect custom theme sheet.
	 * 
	 * @see #findCustomThemeSheet(DocumentCSS)
	 */
	public static final String CUSTOM_THEME_FIRST_SELECTOR = "*[class=\"jeeeyul-custom-theme\"]";

	private Display display = Display.getDefault();

	/**
	 * Rewrite "/css/jeeeyul-custom.css" with user preference.
	 */
	public void rewrite() {
		try {
			CustomThemeGenerator generator = new CustomThemeGenerator(JThemesCore.getDefault().getPreferenceStore());
			String newCSSContent = generator.generate().toString();
			applyTheme(newCSSContent);

			RangeIndicatorHack.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Rectangle windowMargins() {
		JThemePreferenceStore store = JThemesCore.getDefault().getPreferenceStore();
		Rectangle margins = store.getRectangle(JTPConstants.Window.MARGINS);
		if (store.getBoolean(JTPConstants.Layout.SHOW_SHADOW)) {
			margins.x = Math.max(margins.x - 1, 0);
			margins.width = Math.max(margins.width - 4, 0);
			margins.height = Math.max(margins.height - 4, 0);
		} else {
			margins.width = Math.max(margins.width - 1, 0);
			margins.height = Math.max(margins.height - 1, 0);
		}
		return margins;
	}

	private void applyTheme(String css) throws IOException {
		Debug.println("Theme is about to rewrite");
		CSSEngine cssEngine = WidgetElement.getEngine(display);

		for (IWorkbenchWindow each : PlatformUI.getWorkbench().getWorkbenchWindows()) {
			TrimmedPartLayout layout = (TrimmedPartLayout) each.getShell().getLayout();
			Rectangle margins = windowMargins();
			layout.gutterTop = margins.y;
			layout.gutterBottom = margins.height;
			layout.gutterLeft = margins.x;
			layout.gutterRight = margins.width;
		}

		IResourcesRegistry resourcesRegistry = cssEngine.getResourcesRegistry();
		new ResourceRegistryHack().disposeDynamicImages(resourcesRegistry);

		ExtendedDocumentCSS documentCSS = (ExtendedDocumentCSS) cssEngine.getDocumentCSS();
		StyleSheet customThemeSheet = findCustomThemeSheet(documentCSS);
		StyleSheet newSheet = cssEngine.parseStyleSheet(new StringReader(css));
		StyleSheetList oldSheetList = documentCSS.getStyleSheets();
		List<StyleSheet> newSheetList = new ArrayList<StyleSheet>();

		for (int i = 0; i < oldSheetList.getLength(); i++) {
			StyleSheet oldSheet = oldSheetList.item(i);
			if (oldSheet != customThemeSheet) {
				if (!newSheetList.contains(oldSheet))
					newSheetList.add(oldSheet);
			} else {
				if (!newSheetList.contains(newSheet))
					newSheetList.add(newSheet);
			}
		}

		documentCSS.removeAllStyleSheets();
		for (StyleSheet each : newSheetList) {
			documentCSS.addStyleSheet(each);
		}

		cssEngine.reapply();

		for (Shell each : Display.getDefault().getShells()) {
			each.layout(true, true);
		}

		Debug.println("Theme was re-written");
	}

	/**
	 * find and returns a {@link StyleSheet} which represent
	 * "jeeeyul-custom.css".
	 * 
	 * @param documentCSS
	 * @return
	 * @since 1.2
	 */
	private StyleSheet findCustomThemeSheet(DocumentCSS documentCSS) {
		StyleSheet customThemeSheet = null;
		StyleSheetList styleSheets = documentCSS.getStyleSheets();
		SearchCustomThemeSheet: for (int i = 0; i < styleSheets.getLength(); i++) {
			StyleSheet eachSheet = styleSheets.item(i);
			if (eachSheet instanceof CSSStyleSheet) {
				CSSRuleList cssRules = ((CSSStyleSheet) eachSheet).getCssRules();
				for (int j = 0; j < cssRules.getLength(); j++) {
					ExtendedCSSRule rule = (ExtendedCSSRule) cssRules.item(j);
					SelectorList selectorList = rule.getSelectorList();
					String selectorText = selectorList.item(0).toString();
					if (CUSTOM_THEME_FIRST_SELECTOR.equals(selectorText)) {
						customThemeSheet = eachSheet;
						break SearchCustomThemeSheet;
					}
				}
			}

		}
		return customThemeSheet;
	}
}
