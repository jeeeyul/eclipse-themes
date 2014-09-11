package net.jeeeyul.eclipse.themes.ui.hotswap;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.ui.internal.Debug;
import net.jeeeyul.eclipse.themes.ui.internal.ENVHelper;

import org.eclipse.e4.ui.css.core.dom.ExtendedCSSRule;
import org.eclipse.e4.ui.css.core.dom.ExtendedDocumentCSS;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
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

	private boolean hotSwap;

	/**
	 * 
	 */
	public RewriteCustomTheme() {
		this(false);
	}

	/**
	 * @param hotSwap
	 */
	public RewriteCustomTheme(boolean hotSwap) {
		this.hotSwap = hotSwap;
	}

	private void applyTheme(String css) throws IOException {
		Debug.println("Theme is about to rewrite");
		CSSEngine cssEngine = WidgetElement.getEngine(display);

		if (hotSwap) {
			HotSwap.INSTANCE.fixWindowMargins();
		}

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

		if (hotSwap && ENVHelper.INSTANCE.isLunaOrAbove()) {
			HotSwap.INSTANCE.releaseHandleImages(cssEngine);
			Debug.println("Old handle and frame images are disposed.");
		}

		for (StyleSheet each : newSheetList) {
			documentCSS.addStyleSheet(each);
		}

		cssEngine.reapply();

		for (Shell each : Display.getDefault().getShells()) {
			each.layout(true, true);
		}

		if (hotSwap && ENVHelper.INSTANCE.isLunaOrAbove()) {
			HotSwap.INSTANCE.fixDragHandles(cssEngine);
			Debug.println("Old handle and frame images are hot-swapped.");
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

	/**
	 * Rewrite "/css/jeeeyul-custom.css" with user preference.
	 */
	public void rewrite() {
		try {
			CustomThemeGenerator generator = new CustomThemeGenerator(JThemesCore.getDefault().getPreferenceStore());
			String newCSSContent = generator.generate().toString();
			applyTheme(newCSSContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
