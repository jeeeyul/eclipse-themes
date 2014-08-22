package net.jeeeyul.eclipse.themes.css.internal;

import java.lang.reflect.Field;
import java.util.Map;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.css.RewriteCustomTheme;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.texteditor.AbstractTextEditor;

@SuppressWarnings("restriction")
public class NamedColorHack {
	private static HSB getHSB(String key) {
		String bgExp = EDITOR_STORE.getString(key);
		String[] segments = bgExp.split("[\\s,]+");
		int r = Integer.parseInt(segments[0], 10);
		int g = Integer.parseInt(segments[1], 10);
		int b = Integer.parseInt(segments[2], 10);
		HSB hsb = new HSB(r, g, b);
		return hsb;
	}

	private static boolean isCustomThemeActive() {
		IThemeEngine engine = (IThemeEngine) Display.getDefault().getData("org.eclipse.e4.ui.css.swt.theme");
		ITheme theme = engine.getActiveTheme();
		if (theme == null) {
			return false;
		}

		boolean isCustomThemeActivated = JThemesCore.CUSTOM_THEME_ID.equals(theme.getId());
		return isCustomThemeActivated;
	}

	public static void start() {
		EDITOR_STORE.addPropertyChangeListener(listener);
		update();
	}

	private static void update() {
		try {
			Field mapField = CSS2ColorHelper.class.getDeclaredField("colorNamesMap");
			mapField.setAccessible(true);

			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) mapField.get(CSS2ColorHelper.class);

			if (EDITOR_STORE.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)) {
				map.put("jtexteditor-background", "#ffffff");
			} else {
				HSB hsb = getHSB(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
				map.put("jtexteditor-background", hsb.toHTMLCode());
			}

			if (EDITOR_STORE.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT)) {
				map.put("jtexteditor-foreground", "#000000");
			} else {
				HSB hsb = getHSB(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND);
				map.put("jtexteditor-foreground", hsb.toHTMLCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final IPreferenceStore EDITOR_STORE = new ScopedPreferenceStore(new InstanceScope(), "org.eclipse.ui.editors");

	private static IPropertyChangeListener listener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			update();
			boolean isCustomThemeActivated = isCustomThemeActive();
			if (isCustomThemeActivated) {
				new RewriteCustomTheme().rewrite();
			}
		}
	};
}
