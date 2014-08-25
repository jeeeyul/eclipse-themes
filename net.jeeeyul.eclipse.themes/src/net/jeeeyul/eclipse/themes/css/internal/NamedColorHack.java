package net.jeeeyul.eclipse.themes.css.internal;

import static org.eclipse.ui.texteditor.AbstractTextEditor.*;

import java.lang.reflect.Field;
import java.util.Map;

import net.jeeeyul.eclipse.themes.JThemesCore;
import net.jeeeyul.eclipse.themes.css.RewriteCustomTheme;
import net.jeeeyul.swtend.ui.HSB;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.AbstractTextEditor;

/**
 * 
 * @author Jeeeyul
 */
@SuppressWarnings("restriction")
public class NamedColorHack {
	private static HSB getHSBFromEditorPreference(String key) {
		String bgExp = EDITOR_STORE.getString(key);
		String[] segments = bgExp.split("[\\s,]+");
		int r = Integer.parseInt(segments[0], 10);
		int g = Integer.parseInt(segments[1], 10);
		int b = Integer.parseInt(segments[2], 10);
		HSB hsb = new HSB(r, g, b);
		return hsb;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getNameColorMap() throws NoSuchFieldException, IllegalAccessException {
		if (namedColorMap == null) {
			Field mapField = CSS2ColorHelper.class.getDeclaredField("colorNamesMap");
			mapField.setAccessible(true);
			namedColorMap = (Map<String, String>) mapField.get(CSS2ColorHelper.class);
		}
		return namedColorMap;
	}

	private static HSB getHSBFromSystemColor(int key) {
		return new HSB(Display.getDefault().getSystemColor(key).getRGB());
	}

	private synchronized static UIJob getUpdateJob() {
		if (updateJob == null) {
			updateJob = new UIJob(Display.getDefault(), "Update Named Colors") {
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					updateAll();
					return Status.OK_STATUS;
				}
			};
			updateJob.setSystem(true);
			updateJob.setUser(false);
		}
		return updateJob;
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

	/**
	 * Starts to watch {@link AbstractTextEditor}'s preferences and updates
	 * named colors in
	 */
	public static void start() {
		EDITOR_STORE.addPropertyChangeListener(listener);
		updateAll();
	}

	/**
	 * 
	 */
	public static void stop() {
		EDITOR_STORE.removePropertyChangeListener(listener);
	}

	private static void update(String colorname, String defaultKey, int defaultSWTKey, String valueKey) {
		try {
			Map<String, String> map = getNameColorMap();
			if (EDITOR_STORE.getBoolean(defaultKey)) {
				map.put(colorname, getHSBFromSystemColor(defaultSWTKey).toHTMLCode());
			} else {
				map.put(colorname, getHSBFromEditorPreference(valueKey).toHTMLCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void updateAll() {
		update("jtexteditor-background", PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_BACKGROUND, PREFERENCE_COLOR_BACKGROUND);
		update("jtexteditor-foreground", PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_FOREGROUND, PREFERENCE_COLOR_FOREGROUND);
		update("jtexteditor-selection-background", PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_SELECTION,
				PREFERENCE_COLOR_SELECTION_BACKGROUND);
		update("jtexteditor-selection-foreground", PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT, SWT.COLOR_LIST_SELECTION_TEXT,
				PREFERENCE_COLOR_SELECTION_FOREGROUND);

		updateThemeIfNeeded();
	}

	private static void updateThemeIfNeeded() {
		boolean isCustomThemeActivated = isCustomThemeActive();
		if (isCustomThemeActivated) {
			new RewriteCustomTheme().rewrite();
		}
	}

	private static Map<String, String> namedColorMap;

	private static UIJob updateJob;

	@SuppressWarnings("deprecation")
	private static final IPreferenceStore EDITOR_STORE = new ScopedPreferenceStore(new InstanceScope(), "org.eclipse.ui.editors");

	private static IPropertyChangeListener listener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			getUpdateJob().schedule();
		}
	};
}
